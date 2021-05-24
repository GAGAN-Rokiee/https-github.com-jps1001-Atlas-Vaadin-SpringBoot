package it.besolution.ui.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;

import it.besolution.utils.CommonUtils;

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
	
	private VerticalLayout pageAdvanced = null;

	private Map<Tab, Component> tabsToPages = null;
	private Tabs tabs = null;

	private Scroller panel = null;


	private Label lblID = null;
	private Label lblPrefix = null;

	private Label lblName = null;
	private Label lblDesc = null;

	private Label lblUpDT = null;


	private Grid<WorkflowModel> gridProperty = null;
	private Grid<WorkflowModel> gridRoles = null;
	private Grid<WorkflowModel> gridSettings = null;
	
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

	}


	private void createTabs() {
		try {

			tabInfo = new Tab("Info");
			pageInfoForm = new VerticalLayout();
			pageInfoForm.setSizeFull();

			pageInfoView = new VerticalLayout();
			pageInfoView.setSizeFull();

			tabProperties = new Tab("Properties");
			pagePropertiesView = new VerticalLayout();
			pagePropertiesView.setSizeFull();

			tabFlow = new Tab("Flow");
			pageFlow = new VerticalLayout();
			pageFlow.setSizeFull();

			tabRoles = new Tab("Roles");
			pageRoles = new VerticalLayout();
			pageRoles.setSizeFull();

			tabAdvanced = new Tab("Advanced");
			pageAdvanced = new VerticalLayout();
			pageAdvanced.setSizeFull();

			tabsToPages = new HashMap<>();
			tabsToPages.put(tabInfo, pageInfoForm);
			tabsToPages.put(tabProperties, pagePropertiesView);
			tabsToPages.put(tabFlow, pageFlow);
			tabsToPages.put(tabRoles, pageRoles);
			tabsToPages.put(tabAdvanced, pageAdvanced);

			tabs = new Tabs(tabInfo,tabProperties, tabFlow,tabRoles,tabAdvanced);

			panel = new Scroller();
			panel.setSizeFull();

			tabs.addSelectedChangeListener(event -> {
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

			hLayoutHeader.add(lblPageTitle,btnSave,menuBar);

			add(hLayoutHeader);

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
			up1.setSizeFull();
			TextField tfUp1 = new TextField("Actions Class");
			tfUp1.setWidthFull();
			
			Label lblUp2 = new Label("Mail Jar");
			lblUp2.getStyle().set("margin-top", "2rem");
			Upload up2 = new Upload();
			up2.setSizeFull();
			TextField tfUp2 = new TextField("Mail Class");
			tfUp2.setWidthFull();

			Label lblUp3 = new Label("Sync Jar");
			lblUp3.getStyle().set("margin-top", "2rem");
			Upload up3 = new Upload();
			up3.setSizeFull();
			TextField tfUp3 = new TextField("Sync Class");
			tfUp3.setWidthFull();

			Label lblUp4 = new Label("Audit Jar");
			lblUp4.getStyle().set("margin-top", "2rem");
			Upload up4 = new Upload();
			up4.setSizeFull();
			TextField tfUp4 = new TextField("Audit Class");
			tfUp4.setWidthFull();

			
			gridSettings = new Grid<WorkflowModel>(WorkflowModel.class);
			gridSettings.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridSettings.setWidthFull();

			ArrayList<WorkflowModel> objects = new ArrayList<WorkflowModel>();

			gridSettings.setItems(objects);

			gridSettings.setColumns("settingName","settingValue");
			gridSettings.getColumnByKey("settingName").setHeader("Setting Name");
			gridSettings.getColumnByKey("settingValue").setHeader("Setting Value");
			
			
			vLayoutForm.add(lblUp1,up1,tfUp1,lblUp2,up2,tfUp2,lblUp3,up3,tfUp3,lblUp4,up4,tfUp4);
			
			HorizontalLayout hLayoutSettings = new HorizontalLayout();
			hLayoutSettings.setWidthFull();
			hLayoutSettings.setMargin(false);
			hLayoutSettings.setPadding(false);

			H3 lblPageSettings = new H3("Settings");
			lblPageSettings.getStyle().set("flex-grow", "1");


			hLayoutSettings.add(lblPageSettings);
			
			vLayoutView.add(hLayoutHeader,vLayoutForm,hLayoutSettings,gridSettings);
			
			pageAdvanced.add(vLayoutView);

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
						dialogPropertiesForm.open();
					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			hLayoutHeader.add(lblPageTitle,btnNew);

			gridProperty = new Grid<WorkflowModel>(WorkflowModel.class);
			gridProperty.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridProperty.setSizeFull();

			ArrayList<WorkflowModel> objects = new ArrayList<WorkflowModel>();

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
						dialogRolesForm.open();
					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			hLayoutHeader.add(lblPageTitle,btnNew);

			gridRoles = new Grid<WorkflowModel>(WorkflowModel.class);
			gridRoles.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridRoles.setSizeFull();

			ArrayList<WorkflowModel> objects = new ArrayList<WorkflowModel>();

			gridRoles.setItems(objects);

			gridRoles.setColumns("roleName","dynamic","dynamicProperty","creation");
			gridRoles.getColumnByKey("roleName").setHeader("Role Name");
			gridRoles.getColumnByKey("dynamic").setHeader("Dynamic");
			gridRoles.getColumnByKey("dynamicProperty").setHeader("Dynamic Property");
			gridRoles.getColumnByKey("creation").setHeader("Creation");

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
			TextField tfDynamic = new TextField("Dynamic");
			TextField tfDynamicProperty = new TextField("Dynamic Property");
			TextField tfCreation = new TextField("Creation");

			Button btnCancel = new Button("Cancel");
			btnCancel.addClickListener(eve -> {
				try {

					dialogRolesForm.close();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});


			Button btnReset = new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_ERROR);

			Button btnSubmit = new Button("Submit");
			btnSubmit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			HorizontalLayout hLayouBtn = new HorizontalLayout();
			hLayouBtn.add(btnCancel,btnReset,btnSubmit);

			formLayout.add(tfRoleName,tfDynamic,tfDynamicProperty,tfCreation,hLayouBtn);

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
			TextField tfType = new TextField("Type");

			Button btnCancel = new Button("Cancel");
			btnCancel.addClickListener(eve -> {
				try {

					dialogPropertiesForm.close();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});


			Button btnReset = new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_ERROR);

			Button btnSubmit = new Button("Submit");
			btnSubmit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			HorizontalLayout hLayouBtn = new HorizontalLayout();
			hLayouBtn.add(btnCancel,btnReset,btnSubmit);

			formLayout.add(tfPropName,tfType,hLayouBtn);

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
			TextField tfDescription = new TextField("Description");

			Button btnCancel = new Button("Cancel");
			btnCancel.addClickListener(eve -> {
				try {

					panel.setContent(pageInfoView);

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);

				}
			});


			Button btnReset = new Button("Reset");
			btnReset.addThemeVariants(ButtonVariant.LUMO_ERROR);

			Button btnSubmit = new Button("Submit");
			btnSubmit.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			HorizontalLayout hLayouBtn = new HorizontalLayout();
			hLayouBtn.add(btnCancel,btnReset,btnSubmit);

			formLayout.add(tfName,tfDescription,hLayouBtn);

			vLayout.add(hLayoutHeader,formLayout);

			pageInfoForm.add(vLayout);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}

	}

	public void selectTab(String tabName) {
		try {

			switch(tabName)
			{
			case "info":
				tabs.setSelectedTab(tabInfo);
				break;
			case "properties":
				tabs.setSelectedTab(tabProperties);
				break;
			case "flow":
				tabs.setSelectedTab(tabFlow);
				break;
			case "roles":
				tabs.setSelectedTab(tabRoles);
				break;
			case "advanced":
				tabs.setSelectedTab(tabAdvanced);
				break;
			default:
				System.out.println("no matching tab");
			}
			panel.setContent(tabsToPages.get(tabs.getSelectedTab()));

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}
	}

	public void loadDataTab(String tabName) {
		try {

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}
	}



}
