package it.besolution.ui;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;

import it.besolution.ui.solution.SolutionModel;
import it.besolution.ui.solution.SolutionPresenter;
import it.besolution.utils.Constants;
import it.besolution.utils.CustomIcon;
import it.besolution.utils.ScreenFactory;

/**
 * The main view contains a button and a click listener.
 */



@PWA(name = "atlas",shortName = "atlas", enableInstallPrompt = false)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0")
@Theme(value = Lumo.class, variant = Material.LIGHT)
@PageTitle("ATLAS")
@CssImport("./styles/customStyles.css")
@CssImport(value = "./styles/radioButton.css", themeFor = "vaadin-radio-button")
@CssImport(value = "./styles/checkbox.css", themeFor = "vaadin-checkbox")
@CssImport(value = "./styles/contextMenuOverlay.css", themeFor = "vaadin-context-menu-overlay")
@Route("")
public class MainView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(MainView.class);


	private Scroller panel = null;
	private Icon icoMenu = null;
	private Icon icoSettings = null;

	public MainView() {

		VaadinSession.getCurrent().setAttribute(Constants.CURRENT_USER_SCREEN, null);

		setPadding(false);
		setSpacing(false);
		setSizeFull();
		registerScreens();
		createHeader();
		createContent();


	}

	private void registerScreens() {
		try {

			ScreenFactory.getInstance().mainview = this;

			ScreenFactory.getInstance().getScreen(2);
			ScreenFactory.getInstance().getScreen(3);
			ScreenFactory.getInstance().getScreen(4);
			ScreenFactory.getInstance().getScreen(5);
			ScreenFactory.getInstance().getScreen(6);
			ScreenFactory.getInstance().getScreen(7);
			ScreenFactory.getInstance().getScreen(8);

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		}

	}

	private void createContent() {
		try {

			panel = new Scroller();
			panel.setSizeFull();

			List<SolutionModel>  solutionList = new SolutionPresenter().getSolutions();

			if(solutionList.size()>0) {

				icoMenu.setVisible(true);
				icoSettings.setVisible(true);
				
				ScreenFactory.getInstance().solutionListView.addTemplates(solutionList);

				panel.setContent(ScreenFactory.getInstance().solutionListView);

			}
			else {

				panel.setContent(ScreenFactory.getInstance().homeView);
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
			lblTitle.getStyle().set("margin-right", "auto");

			icoMenu = CustomIcon.MENU.create();
			icoMenu.setSize("2rem");
			icoMenu.setColor("white");
			icoMenu.setVisible(false);
			icoMenu.getStyle().set("cursor", "pointer");
			
			icoMenu.addClickListener(event -> {
				try {
					
					List<SolutionModel>  solutionList = new SolutionPresenter().getSolutions();
					
					ScreenFactory.getInstance().solutionListView.addTemplates(solutionList);
					panel.setContent(ScreenFactory.getInstance().solutionListView);
					
				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			icoSettings = CustomIcon.SETTINGS.create();
			icoSettings.setSize("2rem");
			icoSettings.setColor("white");
			icoSettings.setVisible(false);
			icoSettings.getStyle().set("cursor", "pointer");


			headerLayout.add(lblTitle,icoMenu,icoSettings);
			headerLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);


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
