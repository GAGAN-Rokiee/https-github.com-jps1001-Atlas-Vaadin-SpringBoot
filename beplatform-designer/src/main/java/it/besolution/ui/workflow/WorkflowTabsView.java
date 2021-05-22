package it.besolution.ui.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
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

	private VerticalLayout pageInfo = null;
	private VerticalLayout pageProperties = null;
	private VerticalLayout pageFlow = null;
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
	private Grid<WorkflowModel> gridAdvanced = null;

	public WorkflowTabsView(){

		setSizeFull();
		createHeader();
		createTabs();
		
		createInfoView();
		createPropertiesView();
		createFlowView();
		createRoleView();
		createAdvancedView();
		
	}


	private void createTabs() {
		try {

			tabInfo = new Tab("Info");
			pageInfo = new VerticalLayout();
			pageInfo.setSizeFull();

			tabProperties = new Tab("Properties");
			pageProperties = new VerticalLayout();
			pageProperties.setSizeFull();

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
			tabsToPages.put(tabInfo, pageInfo);
			tabsToPages.put(tabProperties, pageProperties);
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
			vLayoutView.setSizeFull();
			vLayoutView.setMargin(false);
			vLayoutView.setPadding(false);

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.setMargin(false);
			hLayoutHeader.setPadding(false);

			H3 lblPageTitle = new H3("Workflow Advanced");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnNew  = new Button("New");
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			hLayoutHeader.add(lblPageTitle,btnNew);
			
			gridAdvanced = new Grid<WorkflowModel>(WorkflowModel.class);
			gridAdvanced.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridAdvanced.setSizeFull();

			ArrayList<WorkflowModel> objects = new ArrayList<WorkflowModel>();

			gridAdvanced.setItems(objects);

			gridAdvanced.setColumns("roleName","dynamic","dynamicProperty","creation");
			gridAdvanced.getColumnByKey("roleName").setHeader("Role Name");
			gridAdvanced.getColumnByKey("dynamic").setHeader("Dynamic");
			gridAdvanced.getColumnByKey("dynamicProperty").setHeader("Dynamic Property");
			gridAdvanced.getColumnByKey("creation").setHeader("Creation");
			
			vLayoutView.add(hLayoutHeader,gridAdvanced);

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

			pageProperties.add(vLayoutView);
			
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

			Button btnNew  = new Button("New");
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

			hLayoutHeader.add(lblPageTitle,btnNew);


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

			pageInfo.add(vLayoutForm);

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

			hLayoutHeader.add(lblPageTitle,btnNew);


			vLayoutView.add(hLayoutHeader);

			pageRoles.add(vLayoutView);
			
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}
	
	
		
	}
	
	private Component createInfoForm() {
		
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeFull();
		
		try {

			
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
		}
		return vLayout;
	
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
