package it.besolution.ui.solution;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class SolutionView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SolutionView() {

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

			Label lblPageTitle = new Label("Solution");
			lblPageTitle.getStyle().set("flex-grow", "1");

			Button btnNew = new Button("New");
			btnNew.setIcon(VaadinIcon.PLUS.create());
			
			Button btnImport  = new Button("Import");
			btnImport.setIcon(VaadinIcon.ADD_DOCK.create());

			hLayoutHeader.add(lblPageTitle,btnNew,btnImport);

			add(hLayoutHeader);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	private void createForm() {
		try {
			Scroller scroller = new Scroller();
			scroller.setWidthFull();
			scroller.setHeightFull();
			scroller.getStyle().set("margin-bottom", "auto");
			
			HorizontalLayout hLayoutTemplates = new HorizontalLayout();
			hLayoutTemplates.getStyle().set("flex-wrap", "wrap");
			hLayoutTemplates.setJustifyContentMode(JustifyContentMode.CENTER);
			hLayoutTemplates.setSpacing(false);
			
			List<SolutionModel> solutionList = new SolutionPresenter().getSolutions();


			for(SolutionModel solution:solutionList) 
			{
				VerticalLayout vLayoutTemplate =  new VerticalLayout(); 
				vLayoutTemplate.setClassName("solutionTemplate");
				vLayoutTemplate.setWidth("300px");
				vLayoutTemplate.setMargin(true);

				Label lblName = new Label(solution.getTemplateName());
				Label lblDescription =	new Label(solution.getDescription());
				Label lblDate = new Label(String.valueOf(solution.getLastUpdated())); 
				Label lblStatus = new Label("Status");
				lblStatus.getStyle().set("margin-top", "auto");

				vLayoutTemplate.add(lblName,lblDescription,lblDate,lblStatus);
				vLayoutTemplate.setHorizontalComponentAlignment(Alignment.END, lblStatus);

				hLayoutTemplates.add(vLayoutTemplate); 
			}

			scroller.setContent(hLayoutTemplates);
			add(scroller);
			setHorizontalComponentAlignment(Alignment.CENTER, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
