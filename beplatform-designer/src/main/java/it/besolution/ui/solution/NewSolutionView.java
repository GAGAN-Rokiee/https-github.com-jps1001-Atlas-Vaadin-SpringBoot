package it.besolution.ui.solution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import it.besolution.model.Solution;
import it.besolution.rest.ApiRestResponse;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.CustomIcon;

public class NewSolutionView extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(NewSolutionView.class);

	private Scroller panel = null;
	private VerticalLayout vLayoytFirstForm  = null;
	private VerticalLayout vLayoytSecondForm  = null;

	private Icon IcoChkBxTempChecked = null;
	private Icon IcoChkBxTempUnChecked = null;

	private Icon IcoChkBxInfoChecked = null;
	private Icon IcoChkBxInfoUnChecked  = null;

	public NewSolutionView() {

		setSizeFull();
		createMenu();
		createTemplateForm();
		createNewSolutionForm();
		createPanel();

	}

	private void createMenu() {
		try {

			VerticalLayout vLayoytMenu  = new VerticalLayout();

			vLayoytMenu.setHeightFull();
			vLayoytMenu.addClassName("rightNavigationLayout");
			vLayoytMenu.setWidth("20%");

			H2 heading = new H2("New Soltiion");
			heading.getStyle().set("color", "white");

			HorizontalLayout hLayoutTemp = new HorizontalLayout();
			hLayoutTemp.setWidthFull();

			IcoChkBxTempChecked = CustomIcon.CHECKBOX_CHECKED.create();
			IcoChkBxTempChecked.setVisible(false);
			IcoChkBxTempChecked.addClassName("chkBxIcon");
			IcoChkBxTempChecked.setColor("#1676f3");

			IcoChkBxTempUnChecked = CustomIcon.CHECKBOX_UNCHECKED.create();
			IcoChkBxTempUnChecked.setVisible(true);
			IcoChkBxTempUnChecked.addClassName("chkBxIcon");
			IcoChkBxTempUnChecked.setColor("white");

			Label lblTemp = new Label("Template");
			lblTemp.getStyle().set("color", "white");
			lblTemp.getStyle().set("font-size", "20px");

			HorizontalLayout hLayoutInfo = new HorizontalLayout();
			hLayoutInfo.setWidthFull();

			IcoChkBxInfoChecked = CustomIcon.CHECKBOX_CHECKED.create();
			IcoChkBxInfoChecked.setVisible(false);
			IcoChkBxInfoChecked.addClassName("chkBxIcon");
			IcoChkBxInfoChecked.setColor("#1676f3");

			IcoChkBxInfoUnChecked = CustomIcon.CHECKBOX_UNCHECKED.create();
			IcoChkBxInfoUnChecked.addClassName("chkBxIcon");
			IcoChkBxInfoUnChecked.setVisible(true);
			IcoChkBxInfoUnChecked.setColor("white");

			Label lblInfo = new Label("Info");
			lblInfo.getStyle().set("color", "white");
			lblInfo.getStyle().set("font-size", "20px");


			hLayoutTemp.add(IcoChkBxTempUnChecked,IcoChkBxTempChecked,lblTemp);
			hLayoutTemp.setDefaultVerticalComponentAlignment(Alignment.CENTER);
			
			hLayoutInfo.add(IcoChkBxInfoChecked,IcoChkBxInfoUnChecked,lblInfo);
			hLayoutInfo.setDefaultVerticalComponentAlignment(Alignment.CENTER);


			vLayoytMenu.add(heading,hLayoutTemp,hLayoutInfo);
			add(vLayoytMenu);
		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}

	}

	private void createPanel() {

		panel = new Scroller();

		try {

			panel.setSizeFull();
			panel.setContent(vLayoytFirstForm);
			add(panel);

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}

	}


	private void createTemplateForm(){


		try {

			vLayoytFirstForm  = new VerticalLayout();

			vLayoytFirstForm.setHeightFull();
			vLayoytFirstForm.setWidthFull();

			H1 heading = new H1("Template");

			H2 title = new H2("Choose a template or create a new solution from scracth");

			HorizontalLayout hLayoyt = new HorizontalLayout();

			RadioButtonGroup<String> radioBtnGrp = new RadioButtonGroup<String>();
			radioBtnGrp.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
			radioBtnGrp.setItems("Create from scratch", "Create from template");
			radioBtnGrp.setItemEnabledProvider(item -> !"Create from template".equals(item));
			radioBtnGrp.getChildren().forEach(radioBtn -> radioBtn.getElement().setAttribute("theme", "newSolutionRadioButton"));

			ComboBox<String> cboTemplates = new ComboBox<String>();
			cboTemplates.setEnabled(false);

			hLayoyt.add(radioBtnGrp,cboTemplates);
			hLayoyt.setVerticalComponentAlignment(Alignment.END, cboTemplates);

			HorizontalLayout hLayoutFooter = new HorizontalLayout();
			hLayoutFooter.getStyle().set("margin-top", "auto");
			hLayoutFooter.getStyle().set("margin-left", "auto");

			Button btnCancel= new Button("Cancel");
			btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);

			btnCancel.addClickListener(x -> {
				try {


				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			Button btnNext  = new Button("Next");
			btnNext.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

			btnNext.addClickListener(x -> {
				try {

					IcoChkBxTempUnChecked.setVisible(false);
					IcoChkBxTempChecked.setVisible(true);
					panel.setContent(vLayoytSecondForm);


				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			hLayoutFooter.add(btnCancel,btnNext);

			vLayoytFirstForm.add(heading,title,hLayoyt,hLayoutFooter);


		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}


	}


	private void createNewSolutionForm(){


		try {
			vLayoytSecondForm  = new VerticalLayout();

			vLayoytSecondForm.setHeightFull();
			vLayoytSecondForm.setWidthFull();

			H1 heading = new H1("Template");

			TextField txtName = new TextField("Name");
			txtName.setWidth("65%");
			txtName.setRequired(true);
			txtName.setMaxLength(64);
			txtName.setRequired(true);
			txtName.setErrorMessage("Name is required");

			TextField txtPrefix = new TextField("Prefix");
			txtPrefix.setWidth("50%");
			txtPrefix.setRequired(true);
			txtPrefix.setMaxLength(10);
			txtPrefix.setRequired(true);
			txtPrefix.setErrorMessage("Prefix is required");

			TextArea txtDesc = new TextArea("Description");
			txtDesc.setWidth("80%");
			txtDesc.setHeight("20%");
			txtDesc.setRequired(true);
			txtDesc.setMaxLength(500);

			HorizontalLayout hLayoutFooter = new HorizontalLayout();
			hLayoutFooter.getStyle().set("margin-top", "auto");
			hLayoutFooter.setWidthFull();

			Button btnCancel= new Button("Cancel");
			btnCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
			btnCancel.getStyle().set("margin-right", "auto");

			btnCancel.addClickListener(x -> {
				try {

					txtName.clear();
					txtPrefix.clear();
					txtDesc.clear();

				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			Button btnBack= new Button("Back");
			btnBack.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);

			btnBack.addClickListener(x -> {
				try {

					panel.setContent(vLayoytFirstForm);

				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			Button btnCreate  = new Button("Create");
			btnCreate.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

			btnCreate.addClickListener(x -> {
				try {

					if(!CommonUtils.isNullOrEmptyString(txtName.getValue()) && !CommonUtils.isNullOrEmptyString(txtPrefix.getValue())) {
						Solution solutionModel = new Solution();
						solutionModel.setTemplateName(txtName.getValue());
						solutionModel.setPrefix(txtPrefix.getValue());
						solutionModel.setDescription(txtDesc.getValue());

						SolutionPresenter presenter = new SolutionPresenter();
						ApiRestResponse response = presenter.createNewSolution(solutionModel);
						if(!response.getIsSuccess()) {
							Notification.show(response.getErrorMessage());
						}else {
							txtName.clear();
							txtPrefix.clear();
							txtDesc.clear();
							Notification.show("New Solution Has Been Created",1500,Position.TOP_CENTER);
						}
					}else if(CommonUtils.isNullOrEmptyString(txtName.getValue())) {
						txtName.setInvalid(true);
					}else if(CommonUtils.isNullOrEmptyString(txtPrefix.getValue())) {
						txtPrefix.setInvalid(true);
					}

				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			hLayoutFooter.add(btnCancel,btnBack,btnCreate);

			vLayoytSecondForm.add(heading,txtName,txtPrefix,txtDesc,hLayoutFooter);


		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}

	}



}
