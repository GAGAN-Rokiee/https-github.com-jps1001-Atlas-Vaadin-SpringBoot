package it.besolution.utils;

import com.vaadin.flow.server.VaadinSession;

import it.besolution.ui.HomeView;
import it.besolution.ui.MainNavigationView;
import it.besolution.ui.MainView;
import it.besolution.ui.objectclass.ObjectClassFormView;
import it.besolution.ui.objectclass.ObjectClassGridView;
import it.besolution.ui.solution.NewSolutionView;
import it.besolution.ui.solution.SolutionDetailView;
import it.besolution.ui.solution.SolutionListView;

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
	public SolutionListView solutionListView = null;
	public NewSolutionView newSolutionView = null;
	public MainNavigationView mainNavigationView = null;
	public SolutionDetailView solutionDetailView = null;
	public ObjectClassGridView objectClassGridView = null;
	public ObjectClassFormView objectClassFormView = null;
	
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
			if(null == solutionListView) {
				solutionListView =  new SolutionListView();
			}
			return solutionListView;
		case 4:
			if(null == newSolutionView) {
				newSolutionView =  new NewSolutionView();
			}
			return newSolutionView;
		case 5:
			if(null == mainNavigationView) {
				mainNavigationView =  new MainNavigationView();
			}
			return mainNavigationView;
		case 6:
			if(null == solutionDetailView) {
				solutionDetailView =  new SolutionDetailView();
			}
			return solutionDetailView;		
		case 7:
			if(null == objectClassGridView) {
				objectClassGridView =  new ObjectClassGridView();
			}
			return objectClassGridView;	
		case 8:
			if(null == objectClassFormView) {
				objectClassFormView =  new ObjectClassFormView();
			}
			return objectClassFormView;	


		default:
			return null;
		}
	}

}
