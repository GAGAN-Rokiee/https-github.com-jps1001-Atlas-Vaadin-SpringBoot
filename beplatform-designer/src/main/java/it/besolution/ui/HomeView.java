package it.besolution.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class HomeView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomeView() {

		setSizeFull();
		createForm();
		setJustifyContentMode(JustifyContentMode.CENTER);
	}

	private void createForm() {
		try {
			
			HorizontalLayout hLayoutMain = new HorizontalLayout();
			hLayoutMain.setSpacing(true);
			hLayoutMain.setWidthFull();
			hLayoutMain.setJustifyContentMode(JustifyContentMode.CENTER);
			
			VerticalLayout btnImport = new VerticalLayout();
			btnImport.addClassName("buttonLayout");
			btnImport.setWidth(null);

			Icon iconImport = new Icon(VaadinIcon.ADD_DOCK);
			iconImport.getStyle().set("margin-top", "auto");

			Label lblImport = new Label("Import solution");
			lblImport.getStyle().set("margin-bottom", "auto");

			btnImport.add(iconImport,lblImport);
			
			btnImport.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
			


			
			VerticalLayout btnNew = new VerticalLayout();
			btnNew.addClassName("buttonLayout");
			btnNew.setWidth(null);


			Icon iconAdd = new Icon(VaadinIcon.PLUS);
			iconAdd.getStyle().set("margin-top", "auto");
			
			Label lblAdd = new Label("New solution");
			lblAdd.getStyle().set("margin-bottom", "auto");

			btnNew.add(iconAdd,lblAdd);
			btnNew.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
			
			hLayoutMain.add(btnImport,btnNew);
			
			
			add(hLayoutMain);
			
			setDefaultHorizontalComponentAlignment(Alignment.CENTER);
			
		} catch (Exception e) {
			
		}

	}


}
