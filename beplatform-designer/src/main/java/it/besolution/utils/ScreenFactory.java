package it.besolution.utils;

import com.vaadin.flow.server.VaadinSession;

import it.besolution.ui.HomeView;
import it.besolution.ui.MainView;
import it.besolution.ui.solution.NewSolutionView;
import it.besolution.ui.solution.SolutionView;

public class ScreenFactory {

	private ScreenFactory() {}

	public static ScreenFactory getInstance() 
	{
		if (VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER_SCREEN) == null) 
		{
			VaadinSession.getCurrent().setAttribute(Constants.CURRENT_USER_SCREEN, new ScreenFactory());
		}
		return (ScreenFactory)VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER_SCREEN);
	}


	public MainView mainview = null;
	public HomeView homeView = null;
	public SolutionView solutionView = null;
	public NewSolutionView newSolutionView = null;

	public Object getScreen(int screenId) {

		switch (screenId) {
		case 1:
			if(null == mainview) {
				mainview =  new MainView();
			}
			return mainview;
		case 2:
			if(null == homeView) {
				homeView =  new HomeView();
			}
			return homeView;
		case 3:
			if(null == solutionView) {
				solutionView =  new SolutionView();
			}
			return solutionView;
		case 4:
			if(null == newSolutionView) {
				newSolutionView =  new NewSolutionView();
			}
			return newSolutionView;

		default:
			return null;
		}
	}

}
