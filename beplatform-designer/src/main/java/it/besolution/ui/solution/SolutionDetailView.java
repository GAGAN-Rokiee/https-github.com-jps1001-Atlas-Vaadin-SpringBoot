package it.besolution.ui.solution;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import it.besolution.utils.CommonUtils;

public class SolutionDetailView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Label lblID = null;
	private Label lblPrefix = null;

	private Label lblName = null;
	private Label lblDesc = null;

	private Label lblUpDT = null;
	private Label lblInsDT = null;

	public SolutionDetailView() {

		setSizeFull();
		createHeader();
		createForm();
	}

	private void createForm() {
		try {

			VerticalLayout vLayoutForm = new VerticalLayout();
			vLayoutForm.setSizeFull();

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
			lblNameCap.setText("Name:");
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

			Label lblUpDTCap = new Label();
			lblUpDTCap.setText("Last Update: ");
			lblUpDTCap.addClassName("lblSolutionDtl");

			lblUpDT = new Label();
			lblUpDT.addClassName("lblSolutionDtl");


			hLayoutUpDT.add(lblUpDTCap,lblUpDT);

			HorizontalLayout hLayoutInsDT = new HorizontalLayout();

			Label lblInsDTCap = new Label();
			lblInsDTCap.setText("Install Update: ");
			lblInsDTCap.addClassName("lblSolutionDtl");

			lblInsDT = new Label();
			lblInsDT.addClassName("lblSolutionDtl");


			hLayoutInsDT.add(lblInsDTCap,lblInsDT);

			H2 lblInfoChange = new H2("Info & Changelog");

			Label lblLogs = new Label("...Markdown...");
			lblLogs.addClassName("lblSolutionDtl");

			vLayoutForm.add(hLayoutIDPrfx,hLayoutName,vLayoutDesc,hLayoutUpDT,hLayoutInsDT,lblInfoChange,lblLogs);


			add(vLayoutForm);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, SolutionDetailView.class);

		}

	}

	private void createHeader() {
		try {

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.getStyle().set("margin-bottom", "auto");

			H2 lblPageTitle = new H2("Solution Details");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnExport  = new Button("Export");

			Button btnInstall = new Button("Install");

			Button btnDelete = new Button("Delete");
			btnDelete.addClassName("btnNavSol");

			MenuBar menuBar = new MenuBar();
			MenuItem menuItem = menuBar.addItem(new Label("..."));
			menuItem.getSubMenu().add(btnDelete);
			menuBar.setThemeName("menuSolDtl");

			hLayoutHeader.add(lblPageTitle,btnExport,btnInstall,menuBar);

			add(hLayoutHeader);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, SolutionDetailView.class);

		}

	}

	public void setData(SolutionModel solution) {
		try {

			lblID.setText(String.valueOf(solution.getId()));
			lblPrefix.setText(solution.getPrefix());

			lblName.setText(solution.getTemplateName());

			lblDesc.setText(solution.getDescription());

			lblUpDT.setText(solution.getLastUpdated());

			lblInsDT.setText(solution.getLastUpdated());


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, SolutionDetailView.class);	

		}
	}
}
