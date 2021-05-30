package it.besolution.ui.workflow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.output.NullOutputStream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;

import it.besolution.model.workflow.WorkFlowAdvanced;
import it.besolution.model.workflow.WorkFlowAdvancedSettings;
import it.besolution.model.workflow.WorkFlowMaster;
import it.besolution.model.workflow.WorkFlowProperty;
import it.besolution.model.workflow.WorkFlowRoles;
import it.besolution.rest.ApiRestResponse;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.ScreenFactory;

public class WorkflowTabsView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String INFO_TAB = "info";
	public static String PROPERTIES_TAB = "properties";
	public static String FLOW_TAB = "flow";
	public static String ROLES_TAB = "roles";
	public static String ADVANCED_TAB = "advanced";
	public static String SETTINGS_TAB = "settings";
	
	private Tab tabInfo = null;
	private Tab tabProperties = null;
	private Tab tabFlow = null;
	private Tab tabRoles = null;
	private Tab tabAdvanced = null;

	private VerticalLayout pageInfoForm = null;
	private VerticalLayout pageInfoView = null;

	private Dialog dialogPropertiesForm = null;
	private VerticalLayout pagePropertiesView = null;

	private VerticalLayout pageFlow = null;

	private Dialog dialogRolesForm = null;
	private VerticalLayout pageRoles = null;


	private Dialog dialogSettingsForm = null;
	private VerticalLayout pageAdvanced = null;

	private Map<Tab, Component> tabsToPages = null;

	private Tabs tabs = null;

	private Scroller panel = null;


	private Label lblID = null;
	private Label lblPrefix = null;

	private Label lblName = null;
	private Label lblDesc = null;

	private Label lblUpDT = null;

	private ComboBox<WorkFlowProperty> tfDynamicProperty  = null;


	private Grid<WorkFlowProperty> gridProperty = null;
	private Grid<WorkFlowRoles> gridRoles = null;
	private Grid<WorkFlowAdvancedSettings> gridSettings = null;

	private Binder<WorkFlowMaster> binderWorkflowMaster = null;
	private Binder<WorkFlowProperty> binderWorkflowProperty = null;
	private Binder<WorkFlowRoles> binderWorkflowRole = null;
	private Binder<WorkFlowAdvancedSettings> binderAdvancedSettings = null;

	private WorkFlowMaster workflowMasterModel = null;

	private ArrayList<WorkFlowProperty> propertiesList = null;

	private String filePath = "D:\\UploadAtlas\\";

	private String uploadedFile = null; 

	private boolean isUploaded1 = false;
	private boolean isUploaded2 = false;
	private boolean isUploaded3 = false;
	private boolean isUploaded4 = false;

	private TextField tfUp1 = null;
	private TextField tfUp2 = null;
	private TextField tfUp3 = null;
	private TextField tfUp4 = null;

	public WorkflowTabsView(){

		setSizeFull();
		createHeader();
		createTabs();

		createInfoForm();

		createInfoView();
		createPropertiesView();
		createFlowView();
		createRoleView();
		createAdvancedView();

		createPropertyForm();
		createRoleForm();
		createSettingsForm();
	}





	private void createTabs() {
		try {

			binderWorkflowMaster = new Binder<WorkFlowMaster>(WorkFlowMaster.class);
			binderWorkflowProperty = new Binder<WorkFlowProperty>(WorkFlowProperty.class);
			binderWorkflowRole = new Binder<WorkFlowRoles>(WorkFlowRoles.class);
			binderAdvancedSettings = new Binder<WorkFlowAdvancedSettings>(WorkFlowAdvancedSettings.class);

			tabInfo = new Tab("Info");
			pageInfoForm = new VerticalLayout();
			pageInfoForm.setSizeFull();

			pageInfoView = new VerticalLayout();
			pageInfoView.setSizeFull();

			tabProperties = new Tab("Properties");
			tabProperties.setEnabled(false);

			pagePropertiesView = new VerticalLayout();
			pagePropertiesView.setSizeFull();

			tabFlow = new Tab("Flow");
			tabFlow.setEnabled(false);

			pageFlow = new VerticalLayout();
			pageFlow.setSizeFull();

			tabRoles = new Tab("Roles");
			tabRoles.setEnabled(false);

			pageRoles = new VerticalLayout();
			pageRoles.setSizeFull();

			tabAdvanced = new Tab("Advanced");
			tabAdvanced.setEnabled(false);

			pageAdvanced = new VerticalLayout();
			pageAdvanced.setSizeFull();

			tabsToPages = new HashMap<>();
			tabsToPages.put(tabInfo, pageInfoView);
			tabsToPages.put(tabProperties, pagePropertiesView);
			tabsToPages.put(tabFlow, pageFlow);
			tabsToPages.put(tabRoles, pageRoles);
			tabsToPages.put(tabAdvanced, pageAdvanced);

			tabs = new Tabs(tabInfo,tabProperties, tabFlow,tabRoles,tabAdvanced);

			panel = new Scroller();
			panel.setSizeFull();

			tabs.addSelectedChangeListener(event -> {

				if(event.getSelectedTab().equals(tabAdvanced)) {
					loadTabData(ADVANCED_TAB);
				}
				
				panel.setContent(tabsToPages.get(tabs.getSelectedTab()));
				
			});

			add(tabs, panel);	

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}

	}


	private void createHeader() {
		try {

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.getStyle().set("margin-bottom", "auto");

			H2 lblPageTitle = new H2("Workflow");
			lblPageTitle.getStyle().set("flex-grow", "1");


			Button btnSave = new Button("Save");

			Button btnDelete = new Button("Delete");
			btnDelete.addClassName("btnNavSol");

			MenuBar menuBar = new MenuBar();
			MenuItem menuItem = menuBar.addItem(new Label("..."));
			menuItem.getSubMenu().add(btnDelete);
			menuBar.setThemeName("menuSolDtl");

			hLayoutHeader.add(lblPageTitle,menuBar);

			add(hLayoutHeader);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

		}

	}


	private void createPropertiesView() {

		try {

			VerticalLayout vLayoutView = new VerticalLayout();
			vLayoutView.setSizeFull();
			vLayoutView.setMargin(false);
			vLayoutView.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Properties");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnNew  = new Button("New");
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			btnNew.addClickListener(event ->{
				try {

					if(!dialogPropertiesForm.isOpened()) {
						loadTabData(PROPERTIES_TAB);
						dialogPropertiesForm.open();
					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			hLayoutHeader.add(lblPageTitle,btnNew);

			gridProperty = new Grid<WorkFlowProperty>(WorkFlowProperty.class);
			gridProperty.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridProperty.setSizeFull();

			ArrayList<WorkFlowProperty> objects = new ArrayList<WorkFlowProperty>();

			gridProperty.setItems(objects);

			gridProperty.setColumns("propertyName","propertyType");
			gridProperty.getColumnByKey("propertyName").setHeader("Property Name");
			gridProperty.getColumnByKey("propertyType").setHeader("Property Type");

			vLayoutView.add(hLayoutHeader,gridProperty);

			pagePropertiesView.add(vLayoutView);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}

	}



	private void createFlowView() {


		try {

			VerticalLayout vLayoutView = new VerticalLayout();
			vLayoutView.setSizeFull();
			vLayoutView.setMargin(false);
			vLayoutView.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Flow");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnNew  = new Button("New");
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			hLayoutHeader.add(lblPageTitle,btnNew);


			vLayoutView.add(hLayoutHeader);

			pageFlow.add(vLayoutView);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}

	}

	private void createInfoView() {	

		try {

			VerticalLayout vLayoutForm = new VerticalLayout();
			vLayoutForm.setSizeFull();
			vLayoutForm.setMargin(false);
			vLayoutForm.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Info");
			lblPageTitle.getStyle().set("flex-grow", "1");

			hLayoutHeader.add(lblPageTitle);


			HorizontalLayout hLayoutIDPrfx = new HorizontalLayout();

			Label lblIDCap = new Label();
			lblIDCap.setText("ID: ");
			lblIDCap.addClassName("lblSolutionDtl");


			lblID = new Label();
			lblID.addClassName("lblSolutionDtl");

			Label lblPrefixCap = new Label();
			lblPrefixCap.setText("Prefix: ");
			lblPrefixCap.getStyle().set("margin-left", "2rem");
			lblPrefixCap.addClassName("lblSolutionDtl");

			lblPrefix = new Label();
			lblPrefix.addClassName("lblSolutionDtl");

			hLayoutIDPrfx.add(lblIDCap,lblID,lblPrefixCap,lblPrefix);


			HorizontalLayout hLayoutName = new HorizontalLayout();

			Label lblNameCap = new Label();
			lblNameCap.setText("Workflow Name:");
			lblNameCap.addClassName("lblSolutionDtl");

			lblName = new Label();
			lblName.addClassName("lblSolutionDtl");

			Icon icoNameEdit = new Icon(VaadinIcon.PENCIL);
			icoNameEdit.setSize("16px");

			hLayoutName.add(lblNameCap,lblName,icoNameEdit);
			hLayoutName.setVerticalComponentAlignment(Alignment.CENTER, icoNameEdit);


			VerticalLayout vLayoutDesc = new VerticalLayout();
			vLayoutDesc.setSpacing(false);
			vLayoutDesc.setMargin(false);
			vLayoutDesc.setPadding(false);

			HorizontalLayout hLayoutDesc = new HorizontalLayout();

			Label hDescCap = new Label();
			hDescCap.setText("Description: ");
			hDescCap.addClassName("lblSolutionDtl");

			lblDesc = new Label();
			lblDesc.addClassName("lblSolutionDtl");

			Icon icoDescEdit = new Icon(VaadinIcon.PENCIL);
			icoDescEdit.setSize("16px");

			hLayoutDesc.add(hDescCap,icoDescEdit);
			hLayoutDesc.setVerticalComponentAlignment(Alignment.CENTER, icoDescEdit);
			vLayoutDesc.add(hLayoutDesc,lblDesc);

			HorizontalLayout hLayoutUpDT = new HorizontalLayout();
			hLayoutUpDT.getStyle().set("margin-top", "auto");

			Label lblUpDTCap = new Label();
			lblUpDTCap.setText("Last Update: ");
			lblUpDTCap.addClassName("lblSolutionDtl");

			lblUpDT = new Label();
			lblUpDT.addClassName("lblSolutionDtl");

			hLayoutUpDT.add(lblUpDTCap,lblUpDT);

			vLayoutForm.add(hLayoutHeader,hLayoutIDPrfx,hLayoutName,vLayoutDesc,hLayoutUpDT);

			pageInfoView.add(vLayoutForm);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}

	}

	private void createRoleView() {



		try {

			VerticalLayout vLayoutView = new VerticalLayout();
			vLayoutView.setSizeFull();
			vLayoutView.setMargin(false);
			vLayoutView.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Roles");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnNew  = new Button("New");
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnNew.addClickListener(event ->{
				try {

					if(!dialogRolesForm.isOpened()) {
						loadTabData(ROLES_TAB);
						dialogRolesForm.open();
					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			hLayoutHeader.add(lblPageTitle,btnNew);

			gridRoles = new Grid<WorkFlowRoles>(WorkFlowRoles.class);
			gridRoles.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridRoles.setSizeFull();

			ArrayList<WorkFlowRoles> objects = new ArrayList<WorkFlowRoles>();

			gridRoles.setItems(objects);
			
			gridRoles.addColumn(obj -> obj.getRoleName()).setHeader("Role Name").setKey("RN");	
			gridRoles.addColumn(obj -> obj.getIsDynamic() ?"True":"False").setHeader("Dynamic").setKey("DN");	
			gridRoles.addColumn(obj -> obj.getWorkFlowPropertyName()).setHeader("Dynamic Property").setKey("DP");	
			gridRoles.addColumn(obj -> obj.getIsCreated() ?"True":"False").setHeader("Creation").setKey("CR");
			
			gridRoles.getColumnByKey("id").setVisible(false);
			gridRoles.getColumnByKey("workFlowId").setVisible(false);
			gridRoles.getColumnByKey("roleName").setVisible(false);
			gridRoles.getColumnByKey("workFlowPropertyId").setVisible(false);
			gridRoles.getColumnByKey("workFlowPropertyName").setVisible(false);
			gridRoles.getColumnByKey("isDynamic").setVisible(false);
			gridRoles.getColumnByKey("isCreated").setVisible(false);

			vLayoutView.add(hLayoutHeader,gridRoles);
			
			pageRoles.add(vLayoutView);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}



	}

	private void createRoleForm() {
		try {

			dialogRolesForm = new Dialog();
			dialogRolesForm.setCloseOnOutsideClick(false);
			dialogRolesForm.setCloseOnEsc(false);
			dialogRolesForm.setModal(true);

			VerticalLayout vLayout = new VerticalLayout();
			vLayout.setSizeFull();
			vLayout.setMargin(false);
			vLayout.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Roles");
			lblPageTitle.getStyle().set("flex-grow", "1");

			hLayoutHeader.add(lblPageTitle);

			VerticalLayout formLayout  = new VerticalLayout();

			TextField tfRoleName = new TextField("Role Name");
			tfRoleName.setMaxLength(100);
			binderWorkflowRole.forField(tfRoleName).asRequired("Name is required").bind("roleName");

			ComboBox<Boolean> tfDynamic = new ComboBox<Boolean>("Dynamic");
			tfDynamic.setItems(false,true);
			binderWorkflowRole.forField(tfDynamic).asRequired("Dynamic is required").bind("isDynamic");

			VerticalLayout vLayoutDynamicProperty = new VerticalLayout();
			vLayoutDynamicProperty.setWidthFull();
			vLayoutDynamicProperty.setPadding(false);
			vLayoutDynamicProperty.setMargin(false);
			vLayoutDynamicProperty.setSpacing(false);

			tfDynamicProperty = new ComboBox<WorkFlowProperty>("Dynamic Property");
			tfDynamicProperty.setItemLabelGenerator(WorkFlowProperty::getPropertyName);
			tfDynamicProperty.setRequired(true);

			Label lblDynamicPropertyError = new Label("Dynamic Property is required");
			lblDynamicPropertyError.addClassName("lblError");
			lblDynamicPropertyError.setVisible(false);

			vLayoutDynamicProperty.add(tfDynamicProperty,lblDynamicPropertyError);

			ComboBox<Boolean> tfCreation = new ComboBox<Boolean>("Creation");
			tfCreation.setItems(false,true);
			binderWorkflowRole.forField(tfCreation).asRequired("Creation is required").bind("isCreated");

			Button btnCancel = new Button("Cancel");
			btnCancel.addClickListener(eve -> {
				try {

					binderWorkflowRole.setBean(new WorkFlowRoles());
					dialogRolesForm.close();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});


			Button btnReset = new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_ERROR);
			btnReset.addClickListener(eve -> {
				try {

					lblDynamicPropertyError.setVisible(false);
					binderWorkflowRole.setBean(new WorkFlowRoles());

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});

			Button btnSubmit = new Button("Submit");
			btnSubmit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnSubmit.addClickListener(event -> {
				try {

					lblDynamicPropertyError.setVisible(false);

					if(binderWorkflowRole.validate().isOk() && !tfDynamicProperty.isEmpty()){
						binderWorkflowRole.getBean().setWorkFlowId(workflowMasterModel.getId());
						binderWorkflowRole.getBean().setWorkFlowPropertyId(tfDynamicProperty.getValue().getId());

						ApiRestResponse response = new WorkflowPresenter().createWorkflowRoles(binderWorkflowRole.getBean());
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());

						}else {

							binderWorkflowRole.setBean(new WorkFlowRoles());
							loadTabData(ROLES_TAB);
							dialogRolesForm.close();
						}

					}else if(tfDynamicProperty.isEmpty()) {
						lblDynamicPropertyError.setVisible(true);

					}
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});
			HorizontalLayout hLayouBtn = new HorizontalLayout();
			hLayouBtn.add(btnCancel,btnReset,btnSubmit);

			formLayout.add(tfRoleName,tfDynamic,vLayoutDynamicProperty,tfCreation,hLayouBtn);

			binderWorkflowRole.bindInstanceFields(formLayout);
			binderWorkflowRole.setBean(new WorkFlowRoles());

			vLayout.add(hLayoutHeader,formLayout);

			dialogRolesForm.add(vLayout);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

		}
	}


	private void createPropertyForm() {
		try {

			dialogPropertiesForm = new Dialog();
			dialogPropertiesForm.setCloseOnOutsideClick(false);
			dialogPropertiesForm.setCloseOnEsc(false);
			dialogPropertiesForm.setModal(true);

			VerticalLayout vLayout = new VerticalLayout();
			vLayout.setSizeFull();
			vLayout.setMargin(false);
			vLayout.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Property");
			lblPageTitle.getStyle().set("flex-grow", "1");

			hLayoutHeader.add(lblPageTitle);

			VerticalLayout formLayout  = new VerticalLayout();

			TextField tfPropName = new TextField("Name");
			tfPropName.setMaxLength(100);
			binderWorkflowProperty.forField(tfPropName).asRequired("Name is required").bind("propertyName");

			ComboBox<String> cboType = new ComboBox<String>("Type");
			cboType.setItems("String","Integer","Decimal","Date","Datetime");
			binderWorkflowProperty.forField(cboType).asRequired("Type is required").bind("propertyType");

			Button btnCancel = new Button("Cancel");
			btnCancel.addClickListener(eve -> {
				try {

					binderWorkflowProperty.setBean(new WorkFlowProperty());
					dialogPropertiesForm.close();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});


			Button btnReset = new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_ERROR);
			btnReset.addClickListener(eve -> {
				try {

					binderWorkflowProperty.setBean(new WorkFlowProperty());

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});

			Button btnSubmit = new Button("Submit");
			btnSubmit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnSubmit.addClickListener(event -> {
				try {

					if(binderWorkflowProperty.validate().isOk()){
						binderWorkflowProperty.getBean().setWorkFlowId(workflowMasterModel.getId());
						ApiRestResponse response = new WorkflowPresenter().createWorkflowMProperty(binderWorkflowProperty.getBean());
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());

						}else {

							binderWorkflowProperty.setBean(new WorkFlowProperty());
							loadTabData(PROPERTIES_TAB);
							dialogPropertiesForm.close();
						}
					}
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});
			HorizontalLayout hLayouBtn = new HorizontalLayout();
			hLayouBtn.add(btnCancel,btnReset,btnSubmit);

			formLayout.add(tfPropName,cboType,hLayouBtn);
			binderWorkflowProperty.bindInstanceFields(formLayout);
			binderWorkflowProperty.setBean(new WorkFlowProperty());

			vLayout.add(hLayoutHeader,formLayout);

			dialogPropertiesForm.add(vLayout);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

		}
	}

	private void createInfoForm() {

		try {

			VerticalLayout vLayout = new VerticalLayout();
			vLayout.setSizeFull();
			vLayout.setMargin(false);
			vLayout.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Info");
			lblPageTitle.getStyle().set("flex-grow", "1");

			hLayoutHeader.add(lblPageTitle);

			VerticalLayout formLayout  = new VerticalLayout();

			TextField tfName = new TextField("Name");
			tfName.setMaxLength(100);
			binderWorkflowMaster.forField(tfName).asRequired("Name is required").bind("name");

			TextField tfDescription = new TextField("Description");
			tfDescription.setMaxLength(500);
			binderWorkflowMaster.forField(tfDescription).asRequired("Description is required").bind("description");

			Button btnReset = new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_ERROR);
			btnReset.addClickListener(event -> {
				try {

					binderWorkflowMaster.setBean(new WorkFlowMaster());
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});
			Button btnSubmit = new Button("Submit");
			btnSubmit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnSubmit.addClickListener(event -> {
				try {

					if(binderWorkflowMaster.validate().isOk()){
						ApiRestResponse response = new WorkflowPresenter().createWorkflowMaster(binderWorkflowMaster.getBean());
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());

						}else {

							binderWorkflowMaster.setBean(new WorkFlowMaster());
							ScreenFactory.getInstance().workflowView.populateWorkflows();
							ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().workflowView);
						}
					}
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			HorizontalLayout hLayouBtn = new HorizontalLayout();
			hLayouBtn.add(btnReset,btnSubmit);

			formLayout.add(tfName,tfDescription,hLayouBtn);

			vLayout.add(hLayoutHeader,formLayout);

			binderWorkflowMaster.bindInstanceFields(formLayout);
			binderWorkflowMaster.setBean(new WorkFlowMaster());

			pageInfoForm.add(vLayout);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}

	}


	private void createSettingsForm() {

		try {

			dialogSettingsForm = new Dialog();
			dialogSettingsForm.setCloseOnOutsideClick(false);
			dialogSettingsForm.setCloseOnEsc(false);
			dialogSettingsForm.setModal(true);

			VerticalLayout vLayout = new VerticalLayout();
			vLayout.setSizeFull();
			vLayout.setMargin(false);
			vLayout.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Advanced Settings");
			lblPageTitle.getStyle().set("flex-grow", "1");

			hLayoutHeader.add(lblPageTitle);

			VerticalLayout formLayout  = new VerticalLayout();

			TextField tfName = new TextField("Name");
			tfName.setMaxLength(100);
			binderAdvancedSettings.forField(tfName).asRequired("Name is required").bind("settingName");

			TextField tfValue = new TextField("Value");
			tfValue.setMaxLength(100);
			binderAdvancedSettings.forField(tfValue).asRequired("Value is required").bind("settingValue");


			Button btnCancel = new Button("Cancel");
			btnCancel.addClickListener(eve -> {
				try {

					binderAdvancedSettings.setBean(new WorkFlowAdvancedSettings());
					dialogSettingsForm.close();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});

			Button btnReset = new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_ERROR);
			btnReset.addClickListener(eve -> {
				try {

					binderAdvancedSettings.setBean(new WorkFlowAdvancedSettings());

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});

			Button btnSubmit = new Button("Submit");
			btnSubmit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnSubmit.addClickListener(event -> {
				try {

					if(binderAdvancedSettings.validate().isOk()){

						binderAdvancedSettings.getBean().setWorkFlowId(workflowMasterModel.getId());

						ApiRestResponse response = new WorkflowPresenter().createWorkflowSettings(binderAdvancedSettings.getBean());
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());

						}else {

							binderAdvancedSettings.setBean(new WorkFlowAdvancedSettings());
							loadTabData(SETTINGS_TAB);
							dialogSettingsForm.close();
						}

					}
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});
			HorizontalLayout hLayouBtn = new HorizontalLayout();
			hLayouBtn.add(btnCancel,btnReset,btnSubmit);

			formLayout.add(tfName,tfValue,hLayouBtn);

			binderAdvancedSettings.bindInstanceFields(formLayout);
			binderAdvancedSettings.setBean(new WorkFlowAdvancedSettings());

			vLayout.add(hLayoutHeader,formLayout);

			dialogSettingsForm.add(vLayout);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

		}


	}


	public void loadInfoTab(WorkFlowMaster workflowModel) {
		try {

			tabProperties.setEnabled(true);
			tabFlow.setEnabled(true);
			tabRoles.setEnabled(true);
			tabAdvanced.setEnabled(true);

			workflowMasterModel = workflowModel;

			loadScreenData();

			tabs.setSelectedTab(tabInfo);

			lblID.setText(String.valueOf(workflowMasterModel.getId()));
			lblPrefix.setText(workflowMasterModel.getPrefix());

			lblName.setText(workflowMasterModel.getName());
			lblDesc.setText(workflowMasterModel.getDescription());

			lblUpDT.setText(String.valueOf(workflowMasterModel.getLastUpdated()));

			binderWorkflowMaster.setBean(workflowMasterModel);
			panel.setContent(pageInfoView);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}
	}

	public void loadTabData(String tabName) {
		try {

			switch(tabName)
			{
			case "info":

				break;
			case "properties":

				propertiesList = new WorkflowPresenter().getWorkflowProperties(workflowMasterModel);
				gridProperty.setItems(propertiesList);
				tfDynamicProperty.setItems(propertiesList);

				break;
			case "flow":

				break;
			case "roles":

				ArrayList<WorkFlowRoles> roles = new WorkflowPresenter().getWorkflowRoles(workflowMasterModel);
				gridRoles.setItems(roles);

				break;
			case "advanced":
				
				uploadedFile = null; 

				isUploaded1 = false;
				isUploaded2 = false;
				isUploaded3 = false;
				isUploaded4 = false;

				tfUp1.clear();
				tfUp2.clear();
				tfUp3.clear();
				tfUp4.clear();
				

				tfUp1.setInvalid(false);
				tfUp2.setInvalid(false);
				tfUp3.setInvalid(false);
				tfUp3.setInvalid(false);


				break;
			case "settings":
				
				WorkFlowAdvancedSettings workFlowAdvancedSettings =  new WorkFlowAdvancedSettings();
				workFlowAdvancedSettings.setWorkFlowId(workflowMasterModel.getId());
				ArrayList<WorkFlowAdvancedSettings> settings = new WorkflowPresenter().getWorkflowAdvanceSettings(workFlowAdvancedSettings);
				gridSettings.setItems(settings);

				break;
			default:
				System.out.println("no matching tab");
			}

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}
	}

	public void loadScreenData() {
		try {

			propertiesList = new WorkflowPresenter().getWorkflowProperties(workflowMasterModel);
			gridProperty.setItems(propertiesList);
			tfDynamicProperty.setItems(propertiesList);

			ArrayList<WorkFlowRoles> roles = new WorkflowPresenter().getWorkflowRoles(workflowMasterModel);
			gridRoles.setItems(roles);

			WorkFlowAdvancedSettings workFlowAdvancedSettings =  new WorkFlowAdvancedSettings();
			workFlowAdvancedSettings.setWorkFlowId(workflowMasterModel.getId());
			ArrayList<WorkFlowAdvancedSettings> settings = new WorkflowPresenter().getWorkflowAdvanceSettings(workFlowAdvancedSettings);
			gridSettings.setItems(settings);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}
	}

	public void resetScreen() {
		try {

			tabs.setSelectedIndex(0);

			panel.setContent(pageInfoForm);

			tabProperties.setEnabled(false);
			tabFlow.setEnabled(false);
			tabRoles.setEnabled(false);
			tabAdvanced.setEnabled(false);

			binderWorkflowMaster.setBean(new WorkFlowMaster());
			binderWorkflowProperty.setBean(new WorkFlowProperty());
			binderWorkflowRole.setBean(new WorkFlowRoles());
			binderAdvancedSettings.setBean(new WorkFlowAdvancedSettings());

			gridProperty.setItems(new ArrayList<WorkFlowProperty>());
			gridRoles.setItems(new ArrayList<WorkFlowRoles>());
			gridSettings.setItems(new WorkFlowAdvancedSettings());

			tfDynamicProperty.setItems(new ArrayList<WorkFlowProperty>());

			uploadedFile = null; 

			isUploaded1 = false;
			isUploaded2 = false;
			isUploaded3 = false;
			isUploaded4 = false;

			tfUp1.clear();
			tfUp2.clear();
			tfUp3.clear();
			tfUp4.clear();

			tfUp1.setInvalid(false);
			tfUp2.setInvalid(false);
			tfUp3.setInvalid(false);
			tfUp3.setInvalid(false);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

		}
	}

	private void createAdvancedView() {

		try {

			VerticalLayout vLayoutView = new VerticalLayout();
			vLayoutView.setWidthFull();
			vLayoutView.setMargin(false);
			vLayoutView.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Connettori");
			lblPageTitle.getStyle().set("flex-grow", "1");


			hLayoutHeader.add(lblPageTitle);

			VerticalLayout vLayoutForm = new VerticalLayout();
			vLayoutForm.setWidth(50, Unit.PERCENTAGE);
			vLayoutForm.setMargin(false);
			vLayoutForm.setPadding(false);

			Label lblUp1 = new Label("Actions Jar");
			lblUp1.getStyle().set("margin-top", "2rem");
			Upload up1 = new Upload();
			up1.setWidthFull();
			up1.setHeight("6rem");
			tfUp1 = new TextField("Actions Class");
			tfUp1.setWidthFull();
			tfUp1.setRequired(true);
			tfUp1.setErrorMessage("Actions Class is required");
			Button btn1 = new Button("Submit");
			btn1.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			Label lblUp2 = new Label("Mail Jar");
			lblUp2.getStyle().set("margin-top", "2rem");
			Upload up2 = new Upload();
			up2.setWidthFull();
			up2.setHeight("6rem");
			tfUp2 = new TextField("Mail Class");
			tfUp2.setWidthFull();
			tfUp2.setRequired(true);
			tfUp2.setErrorMessage("Mail Class is required");
			Button btn2 = new Button("Submit");
			btn2.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			Label lblUp3 = new Label("Sync Jar");
			lblUp3.getStyle().set("margin-top", "2rem");
			Upload up3 = new Upload();
			up3.setWidthFull();
			up3.setHeight("6rem");
			tfUp3 = new TextField("Sync Class");
			tfUp3.setWidthFull();
			tfUp3.setRequired(true);
			tfUp3.setErrorMessage("Sync Class is required");
			Button btn3 = new Button("Submit");
			btn3.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			Label lblUp4 = new Label("Audit Jar");
			lblUp4.getStyle().set("margin-top", "2rem");
			Upload up4 = new Upload();
			up4.setWidthFull();
			up4.setHeight("6rem");
			tfUp4 = new TextField("Audit Class");
			tfUp4.setWidthFull();
			tfUp4.setRequired(true);
			tfUp4.setErrorMessage("Audit Class is required");
			Button btn4 = new Button("Submit");
			btn4.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			gridSettings = new Grid<WorkFlowAdvancedSettings>(WorkFlowAdvancedSettings.class);
			gridSettings.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridSettings.setWidthFull();

			ArrayList<WorkFlowAdvancedSettings> objects = new ArrayList<WorkFlowAdvancedSettings>();

			gridSettings.setItems(objects);

			gridSettings.setColumns("settingName","settingValue");
			gridSettings.getColumnByKey("settingName").setHeader("Setting Name");
			gridSettings.getColumnByKey("settingValue").setHeader("Setting Value");

			vLayoutForm.add(lblUp1,up1,tfUp1,btn1,lblUp2,up2,tfUp2,btn2,lblUp3,up3,tfUp3,btn3,lblUp4,up4,tfUp4,btn4);

			HorizontalLayout hLayoutSettings = new HorizontalLayout();
			hLayoutSettings.setWidthFull();
			hLayoutSettings.setMargin(false);
			hLayoutSettings.setPadding(false);

			H3 lblPageSettings = new H3("Settings");
			lblPageSettings.getStyle().set("flex-grow", "1");

			Button btnNew  = new Button("New");
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnNew.addClickListener(event ->{
				try {

					if(!dialogSettingsForm.isOpened()) {
						dialogSettingsForm.open();
					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});


			hLayoutSettings.add(lblPageSettings,btnNew);

			vLayoutView.add(hLayoutHeader,vLayoutForm,hLayoutSettings,gridSettings);

			pageAdvanced.add(vLayoutView);

			up1.setMaxFileSize((int)1e+8);
			up2.setMaxFileSize((int)1e+8);
			up3.setMaxFileSize((int)1e+8);
			up4.setMaxFileSize((int)1e+8);

			up1.setReceiver(new Receiver() {


				private static final long serialVersionUID = 1L;

				@Override
				public OutputStream receiveUpload(String fileName, String mimeType) {

					FileOutputStream outputStream =null;
					try {

						String[] temp = fileName.split("\\.");
						String fileMimeType = temp[temp.length-1];
						if(fileMimeType.equalsIgnoreCase("jar")){


							outputStream = new FileOutputStream(new File(filePath+fileName));
							return outputStream;


						}
						else{
							up1.interruptUpload();
							Notification.show("Please select valid jar file",3000, Position.TOP_CENTER);

						}					

					} catch (Exception e) {
						up1.interruptUpload();
						CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
					}
					return new NullOutputStream();

				}
			});

			up1.addSucceededListener(suc ->{
				try {

					uploadedFile = filePath+suc.getFileName();
					isUploaded1 = true;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up1.addFileRejectedListener(rej ->{
				try {

					Notification.show("Max file size allowed 100MB",3000, Position.TOP_CENTER);
					isUploaded1 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up1.addFailedListener(failed ->{
				try {

					Notification.show("ACTION Jar File Uploading Failed",3000, Position.TOP_CENTER);
					isUploaded1 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up2.setReceiver(new Receiver() {


				private static final long serialVersionUID = 1L;

				@Override
				public OutputStream receiveUpload(String fileName, String mimeType) {

					FileOutputStream outputStream =null;
					try {

						String[] temp = fileName.split("\\.");
						String fileMimeType = temp[temp.length-1];
						if(fileMimeType.equalsIgnoreCase("jar")){


							outputStream = new FileOutputStream(new File(filePath+fileName));
							return outputStream;


						}
						else{
							up2.interruptUpload();
							Notification.show("Please select valid jar file",3000, Position.TOP_CENTER);

						}


					} catch (Exception e) {
						up2.interruptUpload();
						CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
					}
					return new NullOutputStream();


				}
			});

			up2.addSucceededListener(suc ->{
				try {

					uploadedFile = filePath+suc.getFileName();
					isUploaded2 = true;


				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up2.addFileRejectedListener(rej ->{
				try {

					Notification.show("Max file size allowed 100MB",3000, Position.TOP_CENTER);
					isUploaded2 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up2.addFailedListener(failed ->{
				try {

					Notification.show("MAIL Jar File Uploading Failed",3000, Position.TOP_CENTER);
					isUploaded2 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});


			up3.setReceiver(new Receiver() {


				private static final long serialVersionUID = 1L;

				@Override
				public OutputStream receiveUpload(String fileName, String mimeType) {

					FileOutputStream outputStream =null;
					try {


						String[] temp = fileName.split("\\.");
						String fileMimeType = temp[temp.length-1];
						if(fileMimeType.equalsIgnoreCase("jar")){


							outputStream = new FileOutputStream(new File(filePath+fileName));
							return outputStream;

						}
						else{
							up3.interruptUpload();
							Notification.show("Please select valid jar file",3000, Position.TOP_CENTER);

						}


					} catch (Exception e) {
						up3.interruptUpload();
						CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
					}
					return new NullOutputStream();

				}
			});

			up3.addSucceededListener(suc ->{
				try {

					uploadedFile = filePath+suc.getFileName();
					isUploaded3 = true;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up3.addFileRejectedListener(rej ->{
				try {

					Notification.show("Max file size allowed 100MB",3000, Position.TOP_CENTER);
					isUploaded3 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up3.addFailedListener(failed ->{
				try {

					Notification.show("SYNC Jar File Uploading Failed",3000, Position.TOP_CENTER);
					isUploaded3 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});


			up4.setReceiver(new Receiver() {


				private static final long serialVersionUID = 1L;

				@Override
				public OutputStream receiveUpload(String fileName, String mimeType) {

					FileOutputStream outputStream =null;
					try {

						String[] temp = fileName.split("\\.");
						String fileMimeType = temp[temp.length-1];
						if(fileMimeType.equalsIgnoreCase("jar")){


							outputStream = new FileOutputStream(new File(filePath+fileName));
							return outputStream;

						}
						else{
							up4.interruptUpload();
							Notification.show("Please select valid jar file",3000, Position.TOP_CENTER);

						}

					} catch (Exception e) {
						up4.interruptUpload();

						CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
					}
					return new NullOutputStream();


				}
			});

			up4.addSucceededListener(suc ->{
				try {

					uploadedFile = filePath+suc.getFileName();
					isUploaded4 = true;


				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up4.addFileRejectedListener(rej ->{
				try {

					Notification.show("Max file size allowed 100MB",3000, Position.TOP_CENTER);
					isUploaded4 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			up4.addFailedListener(failed ->{
				try {

					Notification.show("AUDIT Jar File Uploading Failed",3000, Position.TOP_CENTER);
					isUploaded4 = false;

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowTabsView.class);

				}
			});

			btn1.addClickListener(event -> {
				try {
					tfUp1.setInvalid(false);

					if(!tfUp1.isEmpty() && isUploaded1) {

						WorkFlowAdvanced bean = new WorkFlowAdvanced();
						bean.setJarPath(uploadedFile);
						bean.setJarClass(tfUp1.getValue());
						bean.setWorkFlowId(workflowMasterModel.getId());
						bean.setJarType("ACTION");

						ApiRestResponse response = new WorkflowPresenter().createWorkflowJar(bean);
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());
						}else {

							isUploaded1 = false;
							Notification.show(uploadedFile+" File Uploaded Successfully",3000, Position.TOP_CENTER);

						}

					}else if(tfUp1.isEmpty())
					{

						tfUp1.setInvalid(true);

					}else if(!isUploaded1)
					{

						Notification.show("Please select the file",3000, Position.TOP_CENTER);
					}
					uploadedFile = null;

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			btn2.addClickListener(event -> {
				try {
					tfUp2.setInvalid(false);

					if(!tfUp2.isEmpty() && isUploaded2) {

						WorkFlowAdvanced bean = new WorkFlowAdvanced();
						bean.setJarPath(uploadedFile);
						bean.setJarClass(tfUp2.getValue());
						bean.setWorkFlowId(workflowMasterModel.getId());
						bean.setJarType("MAIL");

						ApiRestResponse response = new WorkflowPresenter().createWorkflowJar(bean);
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());
						}else {

							isUploaded2 = false;
							Notification.show(uploadedFile+" File Uploaded Successfully",3000, Position.TOP_CENTER);

						}

					}else if(tfUp2.isEmpty())
					{

						tfUp2.setInvalid(true);

					}else if(!isUploaded2)
					{

						Notification.show("Please select the file",3000, Position.TOP_CENTER);
					}
					uploadedFile = null;

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			btn3.addClickListener(event -> {
				try {
					tfUp3.setInvalid(false);

					if(!tfUp3.isEmpty() && isUploaded3) {

						WorkFlowAdvanced bean = new WorkFlowAdvanced();
						bean.setJarPath(uploadedFile);
						bean.setJarClass(tfUp3.getValue());
						bean.setWorkFlowId(workflowMasterModel.getId());
						bean.setJarType("SYNC");

						ApiRestResponse response = new WorkflowPresenter().createWorkflowJar(bean);
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());
						}else {

							isUploaded3 = false;
							Notification.show(uploadedFile+" File Uploaded Successfully",3000, Position.TOP_CENTER);

						}

					}else if(tfUp3.isEmpty())
					{

						tfUp3.setInvalid(true);

					}else if(!isUploaded3)
					{

						Notification.show("Please select the file",3000, Position.TOP_CENTER);
					}
					uploadedFile = null;

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});			

			btn4.addClickListener(event -> {
				try {
					tfUp4.setInvalid(false);

					if(!tfUp4.isEmpty() && isUploaded4) {

						WorkFlowAdvanced bean = new WorkFlowAdvanced();
						bean.setJarPath(uploadedFile);
						bean.setJarClass(tfUp4.getValue());
						bean.setJarType("AUDIT");

						ApiRestResponse response = new WorkflowPresenter().createWorkflowJar(bean);
						if(!response.getIsSuccess()) {

							Notification.show(response.getErrorMessage());
						}else {

							isUploaded4 = false;
							Notification.show(uploadedFile+" File Uploaded Successfully",3000, Position.TOP_CENTER);

						}

					}else if(tfUp4.isEmpty())
					{

						tfUp4.setInvalid(true);

					}else if(!isUploaded1)
					{

						Notification.show("Please select the file",3000, Position.TOP_CENTER);
					}
					uploadedFile = null;

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}

	}

}
