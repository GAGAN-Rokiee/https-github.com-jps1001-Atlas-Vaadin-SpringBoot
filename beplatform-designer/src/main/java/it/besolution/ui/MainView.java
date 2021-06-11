package it.besolution.ui;

import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import it.besolution.ui.Home.HomePresenter;
import it.besolution.ui.solution.SolutionModel;
import it.besolution.ui.solution.SolutionPresenter;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;
import it.besolution.utils.CustomIcon;
import it.besolution.utils.ScreenFactory;

/**
 * The main view contains a button and a click listener.
 */



@PageTitle("ATLAS")
@Route("")
@StyleSheet(value = "https://unpkg.com/bpmn-js@8.4.0/dist/assets/diagram-js.css")
@StyleSheet(value = "https://unpkg.com/bpmn-js@8.4.0/dist/assets/bpmn-font/css/bpmn.css")
@CssImport(value = "./styles/radioButton.css", themeFor = "vaadin-radio-button")
@CssImport(value = "./styles/checkbox.css", themeFor = "vaadin-checkbox")
@CssImport(value = "./styles/contextMenuOverlay.css", themeFor = "vaadin-context-menu-overlay")
public class MainView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Scroller panel = null;
	private Icon icoMenu = null;
	private Icon icoSettings = null;
	private Icon icoNavigationMenu = null;

	public MainView() {

		VaadinSession.getCurrent().setAttribute(Constants.CURRENT_USER_SCREEN, null);

		setPadding(false);
		setSpacing(false);
		setSizeFull();
		registerScreens();
		createHeader();
		createContent();
		getJarsFilePath();


	}

	private void getJarsFilePath() {
		try {
			Constants.JARS_FILE_PATH = new HomePresenter().getJarsPath();

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainView.class);
		}

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
			ScreenFactory.getInstance().getScreen(9);
			ScreenFactory.getInstance().getScreen(10);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainView.class);
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
			CommonUtils.printStakeTrace(e, MainView.class);


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

			icoNavigationMenu = VaadinIcon.ALIGN_JUSTIFY.create();
			icoNavigationMenu.setSize("1.5rem");
			icoNavigationMenu.setColor("azure");
			icoNavigationMenu.setVisible(false);
			icoNavigationMenu.getStyle().set("cursor", "pointer");
			icoNavigationMenu.getStyle().set("padding-top", "0.5rem");

			icoNavigationMenu.addClickListener(event -> {
				try {

					ScreenFactory.getInstance().mainNavigationView.hideShowSideMenu();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, MainView.class);
				}

			});

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
					icoNavigationMenu.setVisible(false);

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, MainView.class);

				}
			});

			icoSettings = CustomIcon.SETTINGS.create();
			icoSettings.setSize("2rem");
			icoSettings.setColor("white");
			icoSettings.setVisible(false);
			icoSettings.getStyle().set("cursor", "pointer");


			headerLayout.add(icoNavigationMenu,lblTitle,icoMenu,icoSettings);
			headerLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

			add(headerLayout);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainView.class);
		}

	}

	public void showNavigationMenu() {

		try {

			icoNavigationMenu.setVisible(true);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainView.class);

		}

	}

	public void changeScreen(Component content) {
		try {
			panel.setContent(content);
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, MainView.class);

		}
	}
}
