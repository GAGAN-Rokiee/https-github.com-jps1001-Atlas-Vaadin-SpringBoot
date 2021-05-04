package it.besolution.ui.solution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;

public class NewSolutionView extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(NewSolutionView.class);

	
	public NewSolutionView() {
		
		setSizeFull();
		add(createMenu());
		add(createForm());
		
	}
	
	private Component createMenu() {
		VerticalLayout vLayoytMenu  = new VerticalLayout();
		try {
			vLayoytMenu.setHeightFull();
			vLayoytMenu.addClassName("rightNavigationLayout");
			vLayoytMenu.setWidth("20%");
			
			H2 heading = new H2("New Soltiion");
			heading.getStyle().set("color", "white");
			
			Checkbox chkBxTemp = new Checkbox("Template");
			chkBxTemp.addClassName("chkBxNewSolution");
			
			Checkbox chkBxInfo= new Checkbox("Info");
			chkBxInfo.addClassName("chkBxNewSolution");
			
			vLayoytMenu.add(heading,chkBxTemp,chkBxInfo);
			
		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}
		return vLayoytMenu;
		
	}

	private Component createForm() {
		VerticalLayout vLayoytForm  = new VerticalLayout();

		try {
			
			vLayoytForm.setHeightFull();
			vLayoytForm.setWidthFull();
			
			H2 heading = new H2("Template");
			
			H3 title = new H3("Choose a template or create a new solution from scracth");
			
			HorizontalLayout hLayoyt = new HorizontalLayout();
			
			RadioButtonGroup<String> radioBtn = new RadioButtonGroup<String>();
			radioBtn.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
			radioBtn.setItems("Create from scratch", "Create from template");
			radioBtn.setItemEnabledProvider(item -> !"Create from template".equals(item));

			ComboBox<String> cboTemplates = new ComboBox<String>();
			cboTemplates.setEnabled(false);
			
			hLayoyt.add(radioBtn,cboTemplates);
			hLayoyt.setVerticalComponentAlignment(Alignment.END, cboTemplates);
			
			HorizontalLayout hLayoutFooter = new HorizontalLayout();
			hLayoutFooter.getStyle().set("margin-top", "auto");
			hLayoutFooter.getStyle().set("margin-left", "auto");
			
			Button btnBack= new Button("Back");
			btnBack.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
			
			btnBack.addClickListener(x -> {
				try {
				
					
				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});
			
			Button btnNext  = new Button("Next");
			btnNext.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);

			hLayoutFooter.add(btnBack,btnNext);
			
			vLayoytForm.add(heading,title,hLayoyt,hLayoutFooter);
			
			
		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());

		}
		return vLayoytForm;
		
	}



}
