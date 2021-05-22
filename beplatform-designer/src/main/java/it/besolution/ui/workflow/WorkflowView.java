package it.besolution.ui.workflow;

import java.util.ArrayList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import it.besolution.utils.CommonUtils;
import it.besolution.utils.ScreenFactory;

public class WorkflowView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Grid<WorkflowModel> gridWorkflow = null;

	
	public WorkflowView() {
		setSizeFull();
		createTopMenu();
		createGrid();
	}

	private void createGrid() {
		try {
			
			VerticalLayout vLayoytGrid =  new VerticalLayout();
			vLayoytGrid.setSizeFull();

			gridWorkflow = new Grid<WorkflowModel>(WorkflowModel.class);
			gridWorkflow.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridWorkflow.setSizeFull();

			ArrayList<WorkflowModel> objects = new ArrayList<WorkflowModel>();

			gridWorkflow.setItems(objects);

			gridWorkflow.setColumns("workflowName","id","lastUpdated");
			gridWorkflow.getColumnByKey("workflowName").setHeader("Workflow");
			gridWorkflow.getColumnByKey("id").setHeader("ID");
			gridWorkflow.getColumnByKey("lastUpdated").setHeader("Last Updated");

			vLayoytGrid.add(gridWorkflow);
			add(vLayoytGrid);
			
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowView.class);

		}
		
	}

	private void createTopMenu() {
		try {
			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.getStyle().set("margin-bottom", "auto");

			H2 lblPageTitle = new H2("Workflow");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnNew = new Button("New");
			btnNew.setIcon(VaadinIcon.PLUS.create());
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			
			btnNew.addClickListener(x -> {
				try {
					ScreenFactory.getInstance().workflowTabsView.selectTab(WorkflowTabsView.INFO_TAB);
					ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().workflowTabsView);
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowView.class);
				}
			});
			
			hLayoutHeader.add(lblPageTitle,btnNew);

			add(hLayoutHeader);
		} catch (Exception exp) {
			CommonUtils.printStakeTrace(exp, WorkflowView.class);
		}
		
	}

}
