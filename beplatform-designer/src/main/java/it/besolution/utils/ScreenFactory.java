package it.besolution.utils;

import com.vaadin.flow.server.VaadinSession;

import it.besolution.ui.MainView;

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

	public Object getScreen(int screenId) {

		switch (screenId) {
		case 1:
		if(null == mainview) {
			mainview =  new MainView();
		}
		return mainview;
				
		default:
			return null;
		}
	}

}
