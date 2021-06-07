package it.besolution.ui.workflow;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import it.besolution.utils.CommonUtils;

public class WorkflowFlowView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WorkflowFlowView() {
		
		setSizeFull();
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
			btnNew.addClickListener(event -> {
				try {
					
					UI.getCurrent().getPage().executeJs("openDiagram()");
					
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, WorkflowTabsView.class);
				}
			});

			hLayoutHeader.add(lblPageTitle,btnNew);
						
			HorizontalLayout horizontalLayout = new HorizontalLayout();
			horizontalLayout.setSizeFull();
			
			Label divBPMN = new Label();
			divBPMN.setWidth("80%");
			divBPMN.setHeightFull();
			divBPMN.setId("canvas");
			
			Tab tabOne = new Tab("One");
			VerticalLayout pageOne = new VerticalLayout();
			pageOne.setSizeFull();
			
			Tab tabTwo = new Tab("Two");
			VerticalLayout pageTwo = new VerticalLayout();
			pageTwo.setSizeFull();
			
			Tab tabThree = new Tab("Three");
			VerticalLayout pageThree = new VerticalLayout();
			pageThree.setSizeFull();
			
			Tabs tabs = new Tabs(tabOne,tabTwo, tabThree);
			
			Map<Tab, Component> tabsToPages = new HashMap<>();
			tabsToPages.put(tabOne, pageOne);
			tabsToPages.put(tabTwo, pageTwo);
			tabsToPages.put(tabThree, pageThree);
			
			Scroller panel = new Scroller();
			panel.setSizeFull();
			
			tabs.addSelectedChangeListener(event -> {

				panel.setContent(tabsToPages.get(tabs.getSelectedTab()));
				
			});

			VerticalLayout vLayoutTab = new VerticalLayout();
			vLayoutTab.setWidth(null);
			vLayoutTab.setHeightFull();
	
			vLayoutTab.add(tabs,panel);
			horizontalLayout.add(divBPMN,vLayoutTab);
			vLayoutView.add(hLayoutHeader,horizontalLayout);
			add(vLayoutView);
			
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowFlowView.class);
		}
		
	}

}
