package it.besolution.ui;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;

import it.besolution.ui.solution.SolutionModel;
import it.besolution.ui.solution.SolutionPresenter;
import it.besolution.ui.solution.SolutionView;
import it.besolution.utils.ScreenFactory;

/**
 * The main view contains a button and a click listener.
 */



@PWA(name = "atlas",shortName = "atlas", enableInstallPrompt = false)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@Theme(value = Lumo.class, variant = Material.LIGHT)
@PageTitle("ATLAS")
@CssImport("./styles/customStyles.css")
@Route("")
public class MainView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(MainView.class);


	private Scroller panel = null;

	public MainView() {

		ScreenFactory.getInstance().mainview = this;

		setPadding(false);
		setSpacing(false);
		setSizeFull();
		createHeader();
		createContent();


	}

	private void createContent() {
		try {

			panel = new Scroller();
			panel.setSizeFull();

			List<SolutionModel> solutionList = new SolutionPresenter().getSolutions();

			if(solutionList.size()<0) {
				
				panel.setContent(new SolutionView(solutionList));
			}
			else {

				panel.setContent(new HomeView());
			}
			add(panel);

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}

	}

	private void createHeader() {
		try {

			HorizontalLayout headerLayout = new HorizontalLayout();
			headerLayout.addClassName("header");
			headerLayout.setWidthFull();
			headerLayout.setPadding(true);

			Label lblTitle = new Label("Be-Designer");
			lblTitle.getStyle().set("font-size", "2rem");
			lblTitle.getStyle().set("color", "white");

			headerLayout.add(lblTitle);


			add(headerLayout);

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		}

	}

	public void changeScreen(Component content) {
		try {
			panel.setContent(content);
		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		}
	}
}
