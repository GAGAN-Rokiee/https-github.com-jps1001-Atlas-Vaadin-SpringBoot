package it.besolution.ui.propertydefinition;

import java.util.HashMap;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

import it.besolution.ui.solution.NewSolutionView;
import it.besolution.ui.solution.SolutionModel;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;

public class PropertyDefinitionForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Binder<PropertyDefinitionModel> binder = null;
//	private Label lblBasePathError = null;
//	private Label lblCryptoError = null;

	public PropertyDefinitionForm() {

//		setSizeFull();
		setMargin(false);
		setPadding(true);
		
		createForm();
	}

	private void createForm() {
		try {

			HashMap<String, Boolean> mapTrueFalse = new HashMap<String, Boolean>();
			mapTrueFalse.put("True", true);
			mapTrueFalse.put("False", false);

			HashMap<String, Integer> mapType = new HashMap<String, Integer>();
			mapType.put("Table", 1);
			mapType.put("View", 2);

			binder = new Binder<PropertyDefinitionModel>(PropertyDefinitionModel.class);

			H3 heading = new H3("New Property Definition");

			TextField propertyNameField = new TextField("Property name");
			propertyNameField.setWidthFull();
			binder.forField(propertyNameField).asRequired("Property name is required").bind("className");

			ComboBox<String> propertyTypeCombo = new ComboBox<String>("Property type");
			propertyTypeCombo.setWidthFull();
			propertyTypeCombo.setItems(mapType.keySet());
			binder.forField(propertyTypeCombo).asRequired("Property type is required").bind("classTypeDesc");

			TextField labelField = new TextField("Label");
			labelField.setWidthFull();
			binder.forField(labelField).asRequired("Label is required").bind("entityName");

			TextField constraintKeyField = new TextField("Constraint key");
			constraintKeyField.setWidthFull();
			binder.forField(constraintKeyField).asRequired("Label is required").bind("entityName");

			TextField constraintValueField = new TextField("Constraint value");
			constraintValueField.setWidthFull();
			binder.forField(constraintValueField).asRequired("Label is required").bind("entityName");

			HorizontalLayout buttonsHLayout = new HorizontalLayout();
//			buttonsHLayout.getStyle().set("margin-top", "auto");
			buttonsHLayout.setWidthFull();

			Button cancelButton = new Button("Cancel");
			cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
			cancelButton.setIcon(VaadinIcon.CLOSE.create());
//			cancelButton.getStyle().set("margin-left", "auto");

			cancelButton.addClickListener(x -> {
				try {
					fireEvent(new CancelEvent(this, binder.getBean()));
//					ScreenFactory.getInstance().mainNavigationView
//							.changeContent(ScreenFactory.getInstance().objectClassGridView);

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);
				}
			});

			Button resetButton = new Button("Reset");

			resetButton.addClickListener(x -> {
				try {

					resetForm();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, NewSolutionView.class);
				}
			});

			Button saveButton = new Button("Save");
			saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			saveButton.setIcon(VaadinIcon.CHECK.create());

			saveButton.addClickListener(x -> {
				try {

					if (binder.validate().isOk()) {

//						lblBasePathError.setVisible(false);
//						lblCryptoError.setVisible(false);

						SolutionModel solution = (SolutionModel) VaadinSession.getCurrent()
								.getAttribute(Constants.SOLUTION_MODEL);

						binder.getBean().setSolutionId(solution.getId());
						binder.getBean().setClassType(mapType.get(binder.getBean().getClassTypeDesc()));
						binder.getBean()
								.setSecurityEnabled(mapTrueFalse.get(binder.getBean().getSecurityEnabledDesc()));
						binder.getBean().setHasContent(mapTrueFalse.get(binder.getBean().getHasContentDesc()));
						binder.getBean().setSystemClass(mapTrueFalse.get(binder.getBean().getSystemClassDesc()));

						fireEvent(new SaveEvent(this, binder.getBean()));
//						if("True".equalsIgnoreCase(radioBtnGrpContent.getValue())) {
//							if(!txtBasePath.isEmpty() && !radioBtnGrpCrypto.isEmpty()) {
//								binder.getBean().setCryptoContent(mapTrueFalse.get(binder.getBean().getCryptoContentDesc()));
//								ApiRestResponse response = new PropertyDefinitionPresenter().createNewObjectClass(binder.getBean());
//								if(!response.getIsSuccess()) {
//
//									Notification.show(response.getErrorMessage());
//
//								}else {
//
//									resetForm();
//									ScreenFactory.getInstance().objectClassGridView.populateDate();
//									ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().objectClassGridView);
//								}
//
//							}
//							else if(txtBasePath.isEmpty()) {
//
//								lblBasePathError.setVisible(true);
//
//							}else if(radioBtnGrpCrypto.isEmpty()) {
//								lblCryptoError.setVisible(true);
//							}
//						}else {
//							ApiRestResponse response = new PropertyDefinitionPresenter().createNewObjectClass(binder.getBean());
//							if(!response.getIsSuccess()) {
//
//								Notification.show(response.getErrorMessage());
//
//							}else {
//
//								resetForm();
//								ScreenFactory.getInstance().objectClassGridView.populateDate();
//								ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().objectClassGridView);
//							}
//
//						}

					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);

				}
			});

			Span blank = new Span();
			buttonsHLayout.add(cancelButton, blank, saveButton);
			buttonsHLayout.expand(blank);

			add(heading, propertyNameField, propertyTypeCombo, labelField, constraintKeyField, constraintValueField,
					buttonsHLayout);

			binder.bindInstanceFields(this);
			binder.setBean(new PropertyDefinitionModel());

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);

		}

	}

	public void resetForm() {
		try {

//			lblBasePathError.setVisible(false);
//			lblCryptoError.setVisible(false);
			binder.setBean(new PropertyDefinitionModel());

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);
		}
	}
	
	public static abstract class PropertyDefinitionFormEvent extends ComponentEvent<PropertyDefinitionForm> {
		private static final long serialVersionUID = 1L;
		private PropertyDefinitionModel propertyDefinitionModel;

		protected PropertyDefinitionFormEvent(PropertyDefinitionForm source, PropertyDefinitionModel propertyDefinitionModel) {

			super(source, false);
			this.propertyDefinitionModel = propertyDefinitionModel;
		}

		public PropertyDefinitionModel getPropertyDefinitionModel() {
			return propertyDefinitionModel;
		}
	}

	public static class SaveEvent extends PropertyDefinitionFormEvent {
		private static final long serialVersionUID = 1L;

		SaveEvent(PropertyDefinitionForm source, PropertyDefinitionModel propertyDefinitionModel) {
			super(source, propertyDefinitionModel);
		}
	}

	public static class CancelEvent extends PropertyDefinitionFormEvent {
		private static final long serialVersionUID = 1L;

		CancelEvent(PropertyDefinitionForm source, PropertyDefinitionModel propertyDefinitionModel) {
			super(source, propertyDefinitionModel);
		}
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
}
