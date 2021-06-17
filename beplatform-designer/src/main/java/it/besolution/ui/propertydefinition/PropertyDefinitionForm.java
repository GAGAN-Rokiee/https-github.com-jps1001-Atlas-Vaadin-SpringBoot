package it.besolution.ui.propertydefinition;

import java.util.ArrayList;
import java.util.List;

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
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.Registration;

import it.besolution.ui.solution.SolutionModel;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;

public class PropertyDefinitionForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Binder<PropertyDefinitionModel> binder = null;

	public PropertyDefinitionForm() {

//		setSizeFull();
		setMargin(false);
		setPadding(true);

		createForm();
	}

	private void createForm() {
		try {

			binder = new Binder<PropertyDefinitionModel>(PropertyDefinitionModel.class);

			H3 heading = new H3("New Property Definition");

			TextField propertyNameField = new TextField("Property name");
			TextField labelField = new TextField("Label");
			TextField defaultValueField = new TextField("Default Value");
			RadioButtonGroup<String> isNullRadioGroup = new RadioButtonGroup<>();
			TextField constraintKeyField = new TextField("Constraint key");
			TextField constraintValueField = new TextField("Constraint value");
			ComboBox<String> propertyTypeCombo = new ComboBox<String>("Property type");

			propertyNameField.setWidthFull();
			binder.forField(propertyNameField).asRequired("Property name is required").bind("propertyName");

			labelField.setWidthFull();
			binder.forField(labelField).asRequired("Label is required").bind("label");

			defaultValueField.setWidthFull();
			binder.forField(defaultValueField).withValidator(value -> {
				boolean valid = true;
				if (isNullRadioGroup.getValue() == "False") {
					// default value should not be blank
					if (value == null || value.length() == 0) {
						valid = false;
					}
				}
				return valid;
			}, "Default value can not be blank").bind("defaultValue");

			isNullRadioGroup.setLabel("isNull");
			isNullRadioGroup.setItems("True", "False");
			isNullRadioGroup.addValueChangeListener(e -> {
				if (e.getValue() == "True") {
					defaultValueField.setReadOnly(true);
					defaultValueField.clear();
				} else {
					defaultValueField.setReadOnly(false);
				}
			});
			isNullRadioGroup.setValue("True");

			constraintKeyField.setWidthFull();
			constraintKeyField.setReadOnly(true);
			binder.forField(constraintKeyField).bind("constraintKey");

			constraintValueField.setWidthFull();
			constraintValueField.setReadOnly(true);
			binder.forField(constraintValueField).withValidator(value -> {
				boolean valid = true;
				if (constraintKeyField.getValue() != null) {
					if (constraintKeyField.getValue() == Constraint.MAX_LENGTH.toString()
							|| constraintKeyField.getValue() == Constraint.PRECISION.toString()) {
						if (value == null || value.length() == 0) {
							valid = false;
						}
					}
				}
				return valid;
			}, "Constraint value can not be blank.").withValidator(value -> {
				boolean valid = true;
				if (constraintKeyField.getValue() != null) {
					if (constraintKeyField.getValue() == Constraint.MAX_LENGTH.toString()
							|| constraintKeyField.getValue() == Constraint.PRECISION.toString()) {
						if (value != null && value.length() > 0) {
							try {
								int intValue = Integer.parseInt(value);
								if (intValue <= 0 || intValue > 999999999) {
									valid = false;
								}
							} catch (Exception e) {
								valid = false;
							}
						}
					}
				}
				return valid;
			}, "Only integers permitted").bind("constraintValue");

			propertyTypeCombo.setWidthFull();

			List<String> propertyTypeValues = new ArrayList<>();
			for (PropertyType propertyType : PropertyType.values()) {
				propertyTypeValues.add(propertyType.toString());
			}

			propertyTypeCombo.setItems(propertyTypeValues);
			binder.forField(propertyTypeCombo).asRequired("Property type is required").bind("propertyType");

			propertyTypeCombo.addValueChangeListener(e -> {
				if (e.getValue() == null) {
					return;
				}
				constraintValueField.setReadOnly(true);

				if (e.getValue() == PropertyType.STRING.toString()) {
					constraintKeyField.setValue(Constraint.MAX_LENGTH.toString());
					constraintValueField.setReadOnly(false);

				} else if (e.getValue() == PropertyType.DECIMAL.toString()) {
					constraintKeyField.setValue(Constraint.PRECISION.toString());
					constraintValueField.setReadOnly(false);

				} else {
					constraintKeyField.clear();
					constraintValueField.setReadOnly(true);
				}

			});

			HorizontalLayout buttonsHLayout = new HorizontalLayout();
			buttonsHLayout.setWidthFull();

			Button cancelButton = new Button("Cancel");
			cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
			cancelButton.setIcon(VaadinIcon.CLOSE.create());

			cancelButton.addClickListener(x -> {
				try {
					fireEvent(new CancelEvent(this, binder.getBean()));

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);
				}
			});

//			Button resetButton = new Button("Reset");
//
//			resetButton.addClickListener(x -> {
//				try {
//
//					resetForm();
//
//				} catch (Exception e) {
//					CommonUtils.printStakeTrace(e, NewSolutionView.class);
//				}
//			});

			Button saveButton = new Button("Save");
			saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			saveButton.setIcon(VaadinIcon.CHECK.create());

			saveButton.addClickListener(x -> {
				try {

					if (binder.validate().isOk()) {

						SolutionModel solution = (SolutionModel) VaadinSession.getCurrent()
								.getAttribute(Constants.SOLUTION_MODEL);
						
						if(isNullRadioGroup.getValue()=="True") {
							binder.getBean().setPropertyIsNull(true);
						}else {
							binder.getBean().setPropertyIsNull(false);
						}

						binder.getBean().setSolutionId(solution.getId());

						fireEvent(new SaveEvent(this, binder.getBean()));

					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);

				}
			});

			Span blank1 = new Span();

			HorizontalLayout constraintHLayout = new HorizontalLayout();
			constraintHLayout.setWidthFull();
			constraintHLayout.add(constraintKeyField, blank1, constraintValueField);
			constraintHLayout.expand(blank1);

			constraintKeyField.setWidth("170px");
			constraintValueField.setWidth("120px");

			Span blank = new Span();
			buttonsHLayout.add(cancelButton, blank, saveButton);
			buttonsHLayout.expand(blank);

			add(heading, propertyNameField, labelField, propertyTypeCombo, isNullRadioGroup, defaultValueField,
					constraintHLayout, buttonsHLayout);

			binder.bindInstanceFields(this);
			binder.setBean(new PropertyDefinitionModel());

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);

		}

	}

	public void resetForm() {
		try {

			binder.setBean(new PropertyDefinitionModel());

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);
		}
	}

	public void setObjectClassId(int objectClassId) {
		try {

			binder.getBean().setObjectClassId(objectClassId);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionForm.class);
		}
	}

	public static abstract class PropertyDefinitionFormEvent extends ComponentEvent<PropertyDefinitionForm> {
		private static final long serialVersionUID = 1L;
		private PropertyDefinitionModel propertyDefinitionModel;

		protected PropertyDefinitionFormEvent(PropertyDefinitionForm source,
				PropertyDefinitionModel propertyDefinitionModel) {

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
