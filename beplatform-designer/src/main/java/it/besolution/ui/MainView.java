package it.besolution.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
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

/**
 * The main view contains a button and a click listener.
 */



@PWA(name = "analyticLabs",shortName = "analyticLabs", enableInstallPrompt = false)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@Theme(value = Lumo.class, variant = Material.LIGHT)
@PageTitle("Analytics-Labs")
@CssImport("./styles/customStyles.css")
@Route("")
public class MainView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Scroller panel = null;

	public MainView() {

		setSizeFull();
		createContent();

	}

	private void createContent() {
		try {
			
			panel = new Scroller();
			panel.setSizeFull();
			panel.setContent(new HomeView());
			
			add(panel);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void createHeader() {
		try {
			
			HorizontalLayout headerLayout = new HorizontalLayout();
			headerLayout.addClassName("header");
			headerLayout.setWidthFull();
			headerLayout.setHeight("200px");
			add(headerLayout);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
