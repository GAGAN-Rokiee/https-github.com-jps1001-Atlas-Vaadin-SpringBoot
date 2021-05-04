package it.besolution.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import it.besolution.ui.solution.NewSolutionView;
import it.besolution.utils.ScreenFactory;

public class HomeView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(MainView.class);


	public HomeView() {

		setSizeFull();
		createForm();
		setJustifyContentMode(JustifyContentMode.CENTER);
	}

	private void createForm() {
		try {

			H4 lblInfo = new H4("Open a solution or create a new one");
			lblInfo.getStyle().set("font-size", "1.5rem");

			HorizontalLayout hLayoutMain = new HorizontalLayout();
			hLayoutMain.setSpacing(true);
			hLayoutMain.setWidthFull();
			hLayoutMain.setJustifyContentMode(JustifyContentMode.CENTER);

			VerticalLayout vLayoutImport = new VerticalLayout();
			vLayoutImport.addClassName("buttonLayoutBlue");
			vLayoutImport.setWidth(null);

			Icon iconImport = new Icon(VaadinIcon.ADD_DOCK);
			iconImport.getStyle().set("margin-top", "auto");

			Label lblImport = new Label("Import solution");
			lblImport.getStyle().set("margin-bottom", "auto");

			vLayoutImport.add(iconImport,lblImport);

			vLayoutImport.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

			VerticalLayout vLayoutNew = new VerticalLayout();
			vLayoutNew.addClassName("buttonLayoutGreen");
			vLayoutNew.setWidth(null);
			vLayoutNew.getStyle().set("margin-left", "10%");

			vLayoutNew.addClickListener(event -> {
				try {

					ScreenFactory.getInstance().mainview.changeScreen(new NewSolutionView());

				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});


			Icon iconAdd = new Icon(VaadinIcon.PLUS);
			iconAdd.getStyle().set("margin-top", "auto");

			Label lblAdd = new Label("New solution");
			lblAdd.getStyle().set("margin-bottom", "auto");

			vLayoutNew.add(iconAdd,lblAdd);
			vLayoutNew.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

			hLayoutMain.add(vLayoutImport,vLayoutNew);

			add(lblInfo);
			add(hLayoutMain);

			setDefaultHorizontalComponentAlignment(Alignment.CENTER);

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}

	}


}
