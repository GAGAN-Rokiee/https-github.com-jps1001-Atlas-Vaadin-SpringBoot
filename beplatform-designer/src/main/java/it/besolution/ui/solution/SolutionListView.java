package it.besolution.ui.solution;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

import it.besolution.ui.HomeView;
import it.besolution.utils.Constants;
import it.besolution.utils.ScreenFactory;


public class SolutionListView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(HomeView.class);

	private HorizontalLayout hLayoutTemplates = null;



	public SolutionListView() {

		setSizeFull();
		createHeader();
		createForm();
		setJustifyContentMode(JustifyContentMode.CENTER);
	}


	private void createHeader() {

		try {
			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();
			hLayoutHeader.getStyle().set("margin-bottom", "auto");

			H2 lblPageTitle = new H2("Solution");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnNew = new Button("New");
			btnNew.setIcon(VaadinIcon.PLUS.create());
			btnNew.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

			btnNew.addClickListener(x -> {
				try {

					ScreenFactory.getInstance().mainview.changeScreen(ScreenFactory.getInstance().newSolutionView);

				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			Button btnImport  = new Button("Import");
			btnImport.setIcon(VaadinIcon.ADD_DOCK.create());
			btnImport.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

			hLayoutHeader.add(lblPageTitle,btnNew,btnImport);

			add(hLayoutHeader);
		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		}


	}

	private void createForm() {
		try {
			Scroller scroller = new Scroller();
			scroller.setWidthFull();
			scroller.setHeightFull();
			scroller.getStyle().set("margin-bottom", "auto");

			hLayoutTemplates = new HorizontalLayout();
			hLayoutTemplates.getStyle().set("flex-wrap", "wrap");
			hLayoutTemplates.setJustifyContentMode(JustifyContentMode.CENTER);
			hLayoutTemplates.setSpacing(false);

			scroller.setContent(hLayoutTemplates);
			add(scroller);
			setHorizontalComponentAlignment(Alignment.CENTER, scroller);

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		}
	}

	public void addTemplates(List<SolutionModel> solutionList) {
		try {

			hLayoutTemplates.removeAll();

			if(hLayoutTemplates.getComponentCount() == 0) {
				for(SolutionModel solution:solutionList) 
				{
					VerticalLayout vLayoutTemplate =  new VerticalLayout(); 
					vLayoutTemplate.setClassName("solutionTemplate");
					vLayoutTemplate.setWidth("300px");
					vLayoutTemplate.setMargin(true);
					vLayoutTemplate.getStyle().set("cursor", "pointer");

					vLayoutTemplate.addClickListener(event -> {
						try {

							VaadinSession.getCurrent().setAttribute(Constants.SOLUTION_MODEL, solution);
							ScreenFactory.getInstance().solutionDetailView.setData(solution);
							ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().solutionDetailView);
							ScreenFactory.getInstance().mainview.changeScreen(ScreenFactory.getInstance().mainNavigationView);


						} catch (Exception e) {
							LOG.error("Error: {}", e.getMessage());
						}
					});

					Label lblName = new Label(solution.getTemplateName());
					Label lblDescription =	new Label(solution.getDescription());
					Label lblDate = new Label(String.valueOf(solution.getLastUpdated())); 
					Label lblStatus = new Label("Status");
					lblStatus.getStyle().set("margin-top", "auto");

					vLayoutTemplate.add(lblName,lblDescription,lblDate,lblStatus);
					vLayoutTemplate.setHorizontalComponentAlignment(Alignment.END, lblStatus);

					hLayoutTemplates.add(vLayoutTemplate); 
				}
			}

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		}
	}


}
