package it.besolution.ui.workflow;

import java.util.ArrayList;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
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

	public WorkflowFlowView() {

		setSizeFull();
		createUsrStg();
		createSystemStg();
		createView();
	}

	private void createView() {
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
			btnUsrStg.addClickListener(event -> {
				try {

					vLayoutUsrStg.setVisible(true);
					vLayoutSystemStg.setVisible(false);

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowFlowView.class);
				}
			});

			Button btnGenStg = new Button("System Stage");
			btnGenStg.setId("btnSysStg");
			btnGenStg.addClickListener(event -> {
				try {

					vLayoutSystemStg.setVisible(true);
					vLayoutUsrStg.setVisible(false);

				} catch (Exception e2) {
					CommonUtils.printStakeTrace(e2, WorkflowFlowView.class);
				}
			});

			hLayoutTabBtn.add(btnUsrStg,btnGenStg);

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

			Label itmName = new Label();
			itmName.setId("bpmnUsrStgName");

			Label itmID = new Label();
			itmID.setId("bpmnUsrStgID");

			TextField tfName = new TextField("Name");
			TextField tfDesc = new TextField("Description");

			RadioButtonGroup<Boolean> radioGroup = new RadioButtonGroup<>();
			radioGroup.setLabel("Sync Roles");
			radioGroup.setItems(true, false);
			radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
			radioGroup.setValue(false);

			H3 lblRolesTitles = new H3("Roles");

			H4 lblRolesTodo = new H4("Todo Roles");

			vLayoutTodoRoles = new VerticalLayout();
			vLayoutTodoRoles.setWidthFull();
			vLayoutTodoRoles.add(lblRolesTodo);

			H4 lblRolesWatch = new H4("Watch Roles");

			vLayoutWatchRoles = new VerticalLayout();
			vLayoutWatchRoles.setWidthFull();
			vLayoutWatchRoles.add(lblRolesWatch);

			Button btnSubmit = new Button("Submit");
			btnSubmit.setId("submit");
			btnSubmit.addClickListener(event -> {
				try {


				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
				}
			});
			vLayoutUsrStg.add(lblHeader,tfName,tfDesc,radioGroup,lblRolesTitles,vLayoutTodoRoles,vLayoutWatchRoles,btnSubmit,itmName,itmID);


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

			Label itmName = new Label();
			itmName.setId("bpmnSysStgName");

			Label itmID = new Label();
			itmID.setId("bpmnSysStgID");

			TextField tfName = new TextField("Name");
			TextField tfDesc = new TextField("Description");


			vLayoutSystemStg.add(lblHeader,tfName,tfDesc,itmName,itmID);


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

}
