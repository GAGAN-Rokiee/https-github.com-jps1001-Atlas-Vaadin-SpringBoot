package it.besolution.ui.workflow;

import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;

import it.besolution.model.workflow.WorkFlowMaster;
import it.besolution.model.workflow.WorkFlowRoles;
import it.besolution.utils.CommonUtils;

public class WorkflowFlowView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VerticalLayout vLayoutUsrStg = null;
	private VerticalLayout vLayoutSystemStg = null;
	private WorkFlowMaster workflowMasterModel = null;
	private VerticalLayout vLayoutTodoRoles = null;
	private VerticalLayout vLayoutWatchRoles = null;

	private String usrStgElementID = null;
	private String sysStgElementID = null;
	private String elementName = null;

	private RadioButtonGroup<Boolean> rgUsrStgSync = null;
	private TextField tfUsrStgDesc = null;
	private TextField tfUsrStgName = null;
	private TextField tfSysStgName = null;
	private TextField tfSysStgDesc = null;

	private HashMap<String, WorkflowFlowModel> mapFlowStage = null;

	public WorkflowFlowView() {

		setSizeFull();
		createUsrStg();
		createSystemStg();
		createView();
	}

	private void createView() {
		try {


			
			mapFlowStage = new HashMap<String, WorkflowFlowModel>();

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

			SplitLayout horizontalSplitLayout = new SplitLayout();
			horizontalSplitLayout.setSizeFull();

			Label divBPMN = new Label();
			divBPMN.setSizeFull();
			divBPMN.setId("canvas");

			HorizontalLayout hLayoutTabBtn = new HorizontalLayout();
			hLayoutTabBtn.setWidthFull();
			hLayoutTabBtn.setSpacing(true);

			Button btnUsrStg = new Button("User Stage");
			btnUsrStg.setId("btnUsrStg");
			btnUsrStg.addClassName("btnTab");

			btnUsrStg.addClickListener(event -> {
				try {

					setUsrStgElementID(null);
					tfUsrStgName.clear();
					tfUsrStgDesc.clear();
					rgUsrStgSync.setValue(false);

					vLayoutUsrStg.setVisible(true);
					vLayoutSystemStg.setVisible(false);

					UI.getCurrent().getPage().executeJs("return getUsrStgIDValue()").then(String.class, result -> {

						setUsrStgElementID(result);

						WorkflowFlowModel workflowFlowModel = mapFlowStage.get(getUsrStgElementID());

						if(null != workflowFlowModel) {
							
							tfUsrStgName.setValue(workflowFlowModel.getName());
							tfUsrStgDesc.setValue(workflowFlowModel.getDesc());
							rgUsrStgSync.setValue(workflowFlowModel.isSync());
						}

					});


				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowFlowView.class);
				}
			});

			Button btnSysStg = new Button("System Stage");
			btnSysStg.setId("btnSysStg");
			btnSysStg.addClassName("btnTab");

			btnSysStg.addClickListener(event -> {
				try {



					vLayoutSystemStg.setVisible(true);
					vLayoutUsrStg.setVisible(false);

					UI.getCurrent().getPage().executeJs("return getUsrStgIDValue()").then(String.class, result -> {
					
						setSysStgElementID(null);
						tfSysStgName.clear();
						tfSysStgDesc.clear();
						
						setSysStgElementID(result);


						WorkflowFlowModel workflowFlowModel = mapFlowStage.get(getSysStgElementID());
						if(null != workflowFlowModel) {

							tfSysStgName.setValue(workflowFlowModel.getName());
							tfSysStgDesc.setValue(workflowFlowModel.getDesc());
						}

					});




				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowFlowView.class);
				}
			});

			hLayoutTabBtn.add(btnUsrStg,btnSysStg);

			VerticalLayout vLayoutTab = new VerticalLayout();
			vLayoutTab.setSizeFull();
			vLayoutTab.add(hLayoutTabBtn,vLayoutUsrStg,vLayoutSystemStg);
			vLayoutTab.setVisible(false);

			horizontalSplitLayout.addToPrimary(divBPMN);
			horizontalSplitLayout.addToSecondary(vLayoutTab);
			horizontalSplitLayout.setSplitterPosition(60);
			vLayoutView.add(hLayoutHeader,horizontalSplitLayout);
			add(vLayoutView);

			btnNew.addClickListener(event -> {
				try {

					vLayoutTab.setVisible(true);
					UI.getCurrent().getPage().executeJs("openDiagram()");

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
		}

	}

	private void createUsrStg() {

		vLayoutUsrStg = new VerticalLayout();
		vLayoutUsrStg.setSizeFull();	
		vLayoutUsrStg.getStyle().set("margin-bottom", "auto");


		try {

			H2 lblHeader = new H2("General");

			tfUsrStgName = new TextField("Name");
			tfUsrStgName.setRequired(true);
			tfUsrStgName.setErrorMessage("Name is required");

			tfUsrStgDesc = new TextField("Description");
			tfUsrStgDesc.setRequired(true);
			tfUsrStgDesc.setErrorMessage("Description is required");

			rgUsrStgSync = new RadioButtonGroup<>();
			rgUsrStgSync.setLabel("Sync Roles");
			rgUsrStgSync.setItems(true, false);
			rgUsrStgSync.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
			rgUsrStgSync.setValue(false);

			Icon icoInfoRoles = new Icon(VaadinIcon.INFO_CIRCLE);
			icoInfoRoles.setSize("16px");
			icoInfoRoles.getStyle().set("margin-bottom", "5px");
			
			H3 lblRolesTitles = new H3("Roles ");
			lblRolesTitles.add(icoInfoRoles);

			H4 lblRolesTodo = new H4("Todo Roles");

			vLayoutTodoRoles = new VerticalLayout();
			vLayoutTodoRoles.setWidthFull();
			vLayoutTodoRoles.add(lblRolesTodo);
			vLayoutTodoRoles.addClassName("layoutBox");

			H4 lblRolesWatch = new H4("Watch Roles");

			vLayoutWatchRoles = new VerticalLayout();
			vLayoutWatchRoles.setWidthFull();
			vLayoutWatchRoles.add(lblRolesWatch);
			vLayoutWatchRoles.addClassName("layoutBox");

			HorizontalLayout hLayoutBtn = new HorizontalLayout();
			hLayoutBtn.setWidthFull();

			Button btnSubmit = new Button("Submit");
			btnSubmit.addClickListener(event -> {
				try {

					UI.getCurrent().getPage().executeJs("return getUsrStgIDValue()").then(String.class, result -> {
						setUsrStgElementID(result);

					});
					UI.getCurrent().getPage().executeJs("return getUsrStgNameValue()").then(String.class, result -> {
						setElementName(result);
					});

					UI.getCurrent().getPage().executeJs("reset()");

					if(!tfUsrStgName.isEmpty() && !tfUsrStgDesc.isEmpty() && !CommonUtils.isNullOrEmptyString(getUsrStgElementID()) && !CommonUtils.isNullOrEmptyString(getElementName())) 
					{

						WorkflowFlowModel workflowFlowModel = new WorkflowFlowModel();
						workflowFlowModel.setElementID(getUsrStgElementID());
						workflowFlowModel.setElementName(getElementName());
						workflowFlowModel.setName(tfUsrStgName.getValue());
						workflowFlowModel.setDesc(tfUsrStgDesc.getValue());
						workflowFlowModel.setSync(rgUsrStgSync.getValue());
						workflowFlowModel.setScreenName("USER-STAGE");

						mapFlowStage.put(getUsrStgElementID(), workflowFlowModel);

						CommonUtils.showSuccessMsg("User Stage Configuration Saved Succesfully");
						tfUsrStgName.clear();
						tfUsrStgDesc.clear();
						rgUsrStgSync.setValue(false);

						setUsrStgElementID(null);
						setElementName(null);
						setSysStgElementID(null);

					}else if(tfUsrStgName.isEmpty()) {

						tfUsrStgName.setInvalid(true);

					}else if(tfUsrStgDesc.isEmpty()) {

						tfUsrStgDesc.setInvalid(true);

					}else if(CommonUtils.isNullOrEmptyString(getUsrStgElementID())) {

						CommonUtils.showErrorMsg("User Stage is not selected, kindly select User Stage");

					} 
					else if(CommonUtils.isNullOrEmptyString(getElementName())) {

						CommonUtils.showErrorMsg("User Stage is not selected, kindly select User Stage");

					}

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
				}
			});

			Button btnReset = new Button("Reset");
			btnReset.addClickListener(event -> {
				try {

					setUsrStgElementID(null);
					setElementName(null);
					setSysStgElementID(null);
					tfUsrStgName.clear();
					tfUsrStgDesc.clear();
					rgUsrStgSync.setValue(false);
					UI.getCurrent().getPage().executeJs("reset()");


				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
				}
			});

			hLayoutBtn.add(btnSubmit,btnReset);
			vLayoutUsrStg.add(lblHeader,tfUsrStgName,tfUsrStgDesc,rgUsrStgSync,lblRolesTitles,vLayoutTodoRoles,vLayoutWatchRoles,hLayoutBtn);


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
		}
	}

	private void createSystemStg() {

		vLayoutSystemStg = new VerticalLayout();
		vLayoutSystemStg.setSizeFull();
		vLayoutSystemStg.getStyle().set("margin-bottom", "auto");
		vLayoutSystemStg.setVisible(false);

		try {

			H2 lblHeader = new H2("System Stage");

			tfSysStgName = new TextField("Name");
			tfSysStgName.setRequired(true);
			tfSysStgName.setErrorMessage("Description is required");

			tfSysStgDesc = new TextField("Description");
			tfSysStgDesc.setRequired(true);
			tfSysStgDesc.setErrorMessage("Description is required");



			HorizontalLayout hLayoutBtn = new HorizontalLayout();
			hLayoutBtn.setWidthFull();

			Button btnSubmit = new Button("Submit");
			btnSubmit.addClickListener(event -> {
				try {

					UI.getCurrent().getPage().executeJs("return getUsrStgIDValue()").then(String.class, result -> {
						setUsrStgElementID(result);

					});
					UI.getCurrent().getPage().executeJs("return getUsrStgNameValue()").then(String.class, result -> {
						setElementName(result);
					});

					UI.getCurrent().getPage().executeJs("reset()");

					if(!tfSysStgName.isEmpty() && !tfSysStgDesc.isEmpty() && !CommonUtils.isNullOrEmptyString(getSysStgElementID()) && !CommonUtils.isNullOrEmptyString(getElementName())) 
					{

						WorkflowFlowModel workflowFlowModel = new WorkflowFlowModel();
						workflowFlowModel.setElementID(getSysStgElementID());
						workflowFlowModel.setElementName(getElementName());
						workflowFlowModel.setName(tfSysStgName.getValue());
						workflowFlowModel.setDesc(tfSysStgDesc.getValue());
						workflowFlowModel.setScreenName("SYSTEM-STAGE");

						mapFlowStage.put(getSysStgElementID(), workflowFlowModel);

						CommonUtils.showSuccessMsg("System Stage Configuration Saved Succesfully");
						tfSysStgName.clear();
						tfSysStgDesc.clear();
						setUsrStgElementID(null);
						setElementName(null);
						setSysStgElementID(null);

					}else if(tfSysStgName.isEmpty()) {

						tfSysStgName.setInvalid(true);

					}else if(tfSysStgDesc.isEmpty()) {

						tfSysStgDesc.setInvalid(true);

					}else if(CommonUtils.isNullOrEmptyString(getSysStgElementID())) {

						CommonUtils.showErrorMsg("System Stage is not selected, kindly select System Stage");

					} 
					else if(CommonUtils.isNullOrEmptyString(getElementName())) {

						CommonUtils.showErrorMsg("System Stage is not selected, kindly select System Stage");

					}
				}catch(Exception e3) {
					CommonUtils.printStakeTrace(e3, WorkflowFlowView.class);

				}

			});

			Button btnReset = new Button("Reset");
			btnReset.addClickListener(event -> {
				try {

					setUsrStgElementID(null);
					setElementName(null);
					setSysStgElementID(null);
					tfSysStgName.clear();
					tfSysStgDesc.clear();

					UI.getCurrent().getPage().executeJs("reset()");

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
				}
			});

			hLayoutBtn.add(btnSubmit,btnReset);
			vLayoutSystemStg.add(lblHeader,tfSysStgName,tfSysStgDesc,hLayoutBtn);


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
		}
	}


	public void setData(WorkFlowMaster workflowMasterModel) {
		try {

			this.workflowMasterModel = workflowMasterModel;

			ArrayList<WorkFlowRoles> listRoles = new WorkflowPresenter().getWorkflowRoles(workflowMasterModel);

			for(WorkFlowRoles roles:listRoles) {

				Label lblRoleNameTodo = new Label(roles.getRoleName());
				Label lblRoleNameWatch = new Label(roles.getRoleName());

				vLayoutTodoRoles.add(lblRoleNameTodo);
				vLayoutWatchRoles.add(lblRoleNameWatch);
			}

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
		}
	}

	public String getUsrStgElementID() {
		return usrStgElementID;
	}

	public void setUsrStgElementID(String usrStgElementID) {
		this.usrStgElementID = usrStgElementID;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getSysStgElementID() {
		return sysStgElementID;
	}

	public void setSysStgElementID(String sysStgElementID) {
		this.sysStgElementID = sysStgElementID;
	}



}
