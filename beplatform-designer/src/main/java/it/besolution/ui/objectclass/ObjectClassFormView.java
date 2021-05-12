package it.besolution.ui.objectclass;

import java.util.HashMap;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.server.VaadinSession;

import it.besolution.rest.ApiRestResponse;
import it.besolution.ui.solution.NewSolutionView;
import it.besolution.ui.solution.SolutionModel;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;
import it.besolution.utils.ScreenFactory;

public class ObjectClassFormView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Binder<ObjectClassModel> binder = null;
	private Label lblBasePathError = null;
	private Label lblCryptoError = null;

	public ObjectClassFormView() {

		setSizeFull();
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

			binder = new Binder<ObjectClassModel>(ObjectClassModel.class);

			VerticalLayout vLayoutForm = new VerticalLayout();
			vLayoutForm.setSizeFull();

			H2 heading = new H2("General");

			TextField txtName = new TextField("Name");
			txtName.setWidth("40%");
			binder.forField(txtName).asRequired("Name is required").bind("className");

			TextArea txtDesc = new TextArea("Description");
			txtDesc.setWidth("80%");
			txtDesc.setHeight("20%");
			txtDesc.setMaxLength(500);
			binder.forField(txtDesc).asRequired("Description is required").bind("description");

			ComboBox<String> cboType = new ComboBox<String>("Type");
			cboType.setWidth("40%");
			cboType.setItems(mapType.keySet());
			binder.forField(cboType).asRequired("Type is required").bind("classTypeDesc");


			TextField txtEntityName = new TextField("Entity Name");
			txtEntityName.setWidth("40%");
			binder.forField(txtEntityName).asRequired("Entity Name is required").bind("entityName");

			ComboBox<String> cboCounter = new ComboBox<String>("Counter");
			cboCounter.setWidth("40%");
			cboCounter.setItems("Counter 1","Counter 2","Counter 3","Counter 4","Counter 5");
			binder.forField(cboCounter).asRequired("Counter is required").bind("counterName");

			VerticalLayout vLayoutRadioBtnGrpSecurity = new VerticalLayout();
			vLayoutRadioBtnGrpSecurity.setWidthFull();
			vLayoutRadioBtnGrpSecurity.setPadding(false);
			vLayoutRadioBtnGrpSecurity.setMargin(false);
			vLayoutRadioBtnGrpSecurity.setSpacing(false);

			Label lblSecurityEnabled = new Label("Secuirty enabled");

			RadioButtonGroup<String> radioBtnGrpSecuirty = new RadioButtonGroup<String>();
			radioBtnGrpSecuirty.setItems(mapTrueFalse.keySet());
			radioBtnGrpSecuirty.getChildren().forEach(radioBtn -> radioBtn.getElement().setAttribute("theme", "objectFormRadioButton"));
			binder.forField(radioBtnGrpSecuirty).asRequired("Please select one option").bind("securityEnabledDesc");

			vLayoutRadioBtnGrpSecurity.add(lblSecurityEnabled,radioBtnGrpSecuirty);


			H2 headingStorage = new H2("Storage Options");

			VerticalLayout vLayoutRadioBtnGrpContent = new VerticalLayout();
			vLayoutRadioBtnGrpContent.setWidthFull();
			vLayoutRadioBtnGrpContent.setPadding(false);
			vLayoutRadioBtnGrpContent.setMargin(false);
			vLayoutRadioBtnGrpContent.setSpacing(false);

			Label lblHasContent = new Label("Has content");

			RadioButtonGroup<String> radioBtnGrpContent = new RadioButtonGroup<String>();
			radioBtnGrpContent.setItems(mapTrueFalse.keySet());
			radioBtnGrpContent.getChildren().forEach(radioBtn -> radioBtn.getElement().setAttribute("theme", "objectFormRadioButton"));
			binder.forField(radioBtnGrpContent).asRequired("Please select one option").bind("hasContentDesc");


			vLayoutRadioBtnGrpContent.add(lblHasContent,radioBtnGrpContent);

			VerticalLayout vLayoutBasePath = new VerticalLayout();
			vLayoutBasePath.setWidthFull();
			vLayoutBasePath.setPadding(false);
			vLayoutBasePath.setMargin(false);
			vLayoutBasePath.setSpacing(false);

			TextField txtBasePath = new TextField("Base Path");
			txtBasePath.setWidth("40%");
			txtBasePath.setEnabled(false);
			binder.forField(txtBasePath).bind("basePath");

			lblBasePathError = new Label("Base Path is required");
			lblBasePathError.addClassName("lblError");
			lblBasePathError.setVisible(false);

			vLayoutBasePath.add(txtBasePath,lblBasePathError);

			VerticalLayout vLayoutRadioBtnGrpCrypto = new VerticalLayout();
			vLayoutRadioBtnGrpCrypto.setWidthFull();
			vLayoutRadioBtnGrpCrypto.setPadding(false);
			vLayoutRadioBtnGrpCrypto.setMargin(false);
			vLayoutRadioBtnGrpCrypto.setSpacing(false);

			Label lblCrypto = new Label("Crypto content");

			RadioButtonGroup<String> radioBtnGrpCrypto = new RadioButtonGroup<String>();
			radioBtnGrpCrypto.setItems(mapTrueFalse.keySet());
			radioBtnGrpCrypto.getChildren().forEach(radioBtn -> radioBtn.getElement().setAttribute("theme", "objectFormRadioButton"));
			radioBtnGrpCrypto.setEnabled(false);
			binder.forField(radioBtnGrpCrypto).bind("cryptoContentDesc");

			lblCryptoError = new Label("Please select one option");
			lblCryptoError.addClassName("lblError");
			lblCryptoError.setVisible(false);

			vLayoutRadioBtnGrpCrypto.add(lblCrypto,radioBtnGrpCrypto,lblCryptoError);

			radioBtnGrpContent.addValueChangeListener(x -> {
				try {
					if("True".equalsIgnoreCase(x.getValue())) {
						txtBasePath.setEnabled(true);
						radioBtnGrpCrypto.setEnabled(true);

					}else {
						txtBasePath.clear();
						radioBtnGrpCrypto.clear();
						txtBasePath.setEnabled(false);
						radioBtnGrpCrypto.setEnabled(false);
					}
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, ObjectClassFormView.class);
				}
			});

			H2 headingAdvance = new H2("Advanced Options");

			VerticalLayout vLayoutRadioBtnGrpSystem = new VerticalLayout();
			vLayoutRadioBtnGrpSystem.setWidthFull();
			vLayoutRadioBtnGrpSystem.setPadding(false);
			vLayoutRadioBtnGrpSystem.setMargin(false);
			vLayoutRadioBtnGrpSystem.setSpacing(false);

			Label lblSystem = new Label("System class");

			RadioButtonGroup<String> radioBtnGrpSystem = new RadioButtonGroup<String>();
			radioBtnGrpSystem.setItems(mapTrueFalse.keySet());
			radioBtnGrpSystem.getChildren().forEach(radioBtn -> radioBtn.getElement().setAttribute("theme", "objectFormRadioButton"));
			binder.forField(radioBtnGrpSystem).asRequired("Please select one option").bind("systemClassDesc");

			vLayoutRadioBtnGrpSystem.add(lblSystem,radioBtnGrpSystem);

			ComboBox<String> cboWorkflow = new ComboBox<String>("Default workflow");
			cboWorkflow.setWidth("40%");
			cboWorkflow.setItems("Off");
			binder.forField(cboWorkflow).asRequired("Default workflow is required").bind("defaultWorkflow");

			HorizontalLayout hLayoutFooter = new HorizontalLayout();
			hLayoutFooter.getStyle().set("margin-top", "auto");
			hLayoutFooter.setWidthFull();

			Button btnCancel= new Button("Cancel");
			btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
			btnCancel.getStyle().set("margin-left", "auto");

			btnCancel.addClickListener(x -> {
				try {

					ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().objectClassGridView);

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, ObjectClassFormView.class);
				}
			});

			Button btnReset= new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

			btnReset.addClickListener(x -> {
				try {

					resetForm();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, NewSolutionView.class);
				}
			});



			Button btnCreate  = new Button("Create");
			btnCreate.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

			btnCreate.addClickListener(x -> {
				try {

					if(binder.validate().isOk()) {

						lblBasePathError.setVisible(false);
						lblCryptoError.setVisible(false);

						SolutionModel solution = (SolutionModel) VaadinSession.getCurrent().getAttribute(Constants.SOLUTION_MODEL);

						binder.getBean().setSolutionId(solution.getId());
						binder.getBean().setClassType(mapType.get(binder.getBean().getClassTypeDesc()));
						binder.getBean().setSecurityEnabled(mapTrueFalse.get(binder.getBean().getSecurityEnabledDesc()));
						binder.getBean().setHasContent(mapTrueFalse.get(binder.getBean().getHasContentDesc()));
						binder.getBean().setSystemClass(mapTrueFalse.get(binder.getBean().getSystemClassDesc()));

						if("True".equalsIgnoreCase(radioBtnGrpContent.getValue())) {
							if(!txtBasePath.isEmpty() && !radioBtnGrpCrypto.isEmpty()) {
								binder.getBean().setCryptoContent(mapTrueFalse.get(binder.getBean().getCryptoContentDesc()));
								ApiRestResponse response = new ObjectClassPresenter().createNewObjectClass(binder.getBean());
								if(!response.getIsSuccess()) {

									Notification.show(response.getErrorMessage());

								}else {

									resetForm();
									ScreenFactory.getInstance().objectClassGridView.populateDate();
									ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().objectClassGridView);
								}

							}
							else if(txtBasePath.isEmpty()) {

								lblBasePathError.setVisible(true);

							}else if(radioBtnGrpCrypto.isEmpty()) {
								lblCryptoError.setVisible(true);
							}
						}else {
							ApiRestResponse response = new ObjectClassPresenter().createNewObjectClass(binder.getBean());
							if(!response.getIsSuccess()) {

								Notification.show(response.getErrorMessage());

							}else {

								resetForm();
								ScreenFactory.getInstance().objectClassGridView.populateDate();
								ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().objectClassGridView);
							}

						}



					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, ObjectClassFormView.class);

				}
			});

			hLayoutFooter.add(btnCancel,btnReset,btnCreate);

			vLayoutForm.add(heading,txtName,txtDesc,cboType,txtEntityName,cboCounter,vLayoutRadioBtnGrpSecurity,headingStorage,vLayoutRadioBtnGrpContent,vLayoutBasePath,vLayoutRadioBtnGrpCrypto,headingAdvance,vLayoutRadioBtnGrpSystem,cboWorkflow,hLayoutFooter);

			binder.bindInstanceFields(this);
			binder.setBean(new ObjectClassModel());

			add(vLayoutForm);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, ObjectClassFormView.class);

		}

	}

	public void resetForm() {
		try {

			lblBasePathError.setVisible(false);
			lblCryptoError.setVisible(false);
			binder.setBean(new ObjectClassModel());

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, ObjectClassFormView.class);
		}
	}
}
