package it.besolution.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import it.besolution.utils.CommonUtils;
import it.besolution.utils.CustomIcon;
import it.besolution.utils.ScreenFactory;

public class MainNavigationView extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Scroller panel = null;


	public MainNavigationView() {

		setSizeFull();
		createMenu();
		createPanel();
	}

	private void createMenu() {
		try {

			Scroller scroller = new Scroller();
			scroller.addClassName("rightNavigationLayout");
			scroller.setWidth("20%");

			VerticalLayout vLayoytMenu  = new VerticalLayout();
			vLayoytMenu.setWidthFull();

			HorizontalLayout hLayoutSol = new HorizontalLayout();
			hLayoutSol.setWidthFull();

			Icon IcoSol = new Icon(VaadinIcon.HOME);
			IcoSol.addClassName("navIco");

			Label lblSol = new Label("Solution Overview");
			lblSol.addClassName("navLbl");

			hLayoutSol.add(IcoSol,lblSol);
			
			hLayoutSol.addClickListener(x -> {
				try {
					panel.setContent(ScreenFactory.getInstance().solutionDetailView);
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, MainNavigationView.class);
				}
			});

			HorizontalLayout hLayoutEnv = new HorizontalLayout();
			hLayoutEnv.setWidthFull();

			Icon icoEnv = CustomIcon.EARTH.create();
			icoEnv.addClassName("navIco");

			Label lblEnv = new Label("Environments");
			lblEnv.addClassName("navLbl");

			hLayoutEnv.add(icoEnv,lblEnv);


			HorizontalLayout hLayoutData = new HorizontalLayout();
			hLayoutData.setWidthFull();

			Icon icoData = CustomIcon.DATABASE.create();
			icoData.addClassName("navIco");

			Label lblData = new Label("Data defination");
			lblData.addClassName("navLbl");

			hLayoutData.add(icoData,lblData);

			Button btnObj= new Button("Object classes");
			btnObj.addClassName("btnNav");
			btnObj.addClickListener(event -> {
				try {

					ScreenFactory.getInstance().objectClassGridView.populateDate();
					panel.setContent(ScreenFactory.getInstance().objectClassGridView);

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, MainNavigationView.class);

				}
			});

			Button btnProp= new Button("Property definatons");
			btnProp.addClassName("btnNav");
			btnProp.addClickListener(event -> {
				try {

					ScreenFactory.getInstance().propertyDefinitionGridView.populateData();
					panel.setContent(ScreenFactory.getInstance().propertyDefinitionGridView);

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, MainNavigationView.class);

				}
			});

			Button btnCnt= new Button("Counters");
			btnCnt.addClassName("btnNav");

			Button btnSQL= new Button("SQL Views & Triggers");
			btnSQL.addClassName("btnNav");


			HorizontalLayout hLayoutProf = new HorizontalLayout();
			hLayoutProf.setWidthFull();

			Icon icoProf = CustomIcon.USERS.create();
			icoProf.addClassName("navIco");

			Label lblProf = new Label("Profiles");
			lblProf.addClassName("navLbl");

			hLayoutProf.add(icoProf,lblProf);

			HorizontalLayout hLayoutUI = new HorizontalLayout();
			hLayoutUI.setWidthFull();

			Icon icoUI = CustomIcon.DISPLAY.create();
			icoUI.addClassName("navIco");

			Label lblUI = new Label("UI");
			lblUI.addClassName("navLbl");

			hLayoutUI.add(icoUI,lblUI);

			Button btnFrms= new Button("Forms");
			btnFrms.addClassName("btnNav");

			Button btnDtls= new Button("Details");
			btnDtls.addClassName("btnNav");

			Button btnMnu= new Button("Menu");
			btnMnu.addClassName("btnNav");

			Button btnAct= new Button("Actions");
			btnAct.addClassName("btnNav");

			Button btnDSH= new Button("Dashboards");
			btnDSH.addClassName("btnNav");

			HorizontalLayout hLayoutWrkFlw = new HorizontalLayout();
			hLayoutWrkFlw.setWidthFull();
			
			Icon icoWrkFlw = CustomIcon.PLAY.create();
			icoWrkFlw.addClassName("navIco");

			Label lblWrkFlw = new Label("Workflows & automation");
			lblWrkFlw.addClassName("navLbl");

			hLayoutWrkFlw.add(icoWrkFlw,lblWrkFlw);

			Button btnWrkFlw= new Button("Workflows");
			btnWrkFlw.addClassName("btnNav");
			
			btnWrkFlw.addClickListener(x -> {
				try {
					
					ScreenFactory.getInstance().workflowView.populateWorkflows();
					panel.setContent(ScreenFactory.getInstance().workflowView);
					
				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, MainNavigationView.class);
				}
			});

			Button btnScript = new Button("Scripts");
			btnScript.addClassName("btnNav");

			Button btnJob = new Button("Jobs");
			btnJob.addClassName("btnNav");

			HorizontalLayout hLayoutGAW = new HorizontalLayout();
			hLayoutGAW.setWidthFull();

			Icon icoGAW = CustomIcon.POWER.create();
			icoGAW.addClassName("navIco");

			Label lblGAW = new Label("GAW");
			lblGAW.addClassName("navLbl");


			hLayoutGAW.add(icoGAW,lblGAW);

			Button btnGAW = new Button("Gaw mapping");
			btnGAW.addClassName("btnNav");

			vLayoytMenu.add(hLayoutSol,hLayoutEnv,hLayoutData,btnObj,btnProp,btnCnt,btnSQL,hLayoutProf,hLayoutUI,btnFrms,btnDtls,btnMnu,btnAct,btnDSH,hLayoutWrkFlw,btnWrkFlw,btnScript,btnJob,hLayoutGAW,btnGAW);
			scroller.setContent(vLayoytMenu);
			add(scroller);
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainNavigationView.class);

		}

	}

	private void createPanel() {

		panel = new Scroller();

		try {

			panel.setSizeFull();
			add(panel);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainNavigationView.class);

		}

	}

	public void changeContent(Component content) {

		try {

			panel.setContent(content);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainNavigationView.class);

		}


	}

}
