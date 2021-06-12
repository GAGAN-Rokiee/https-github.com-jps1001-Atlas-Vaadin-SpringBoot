package it.besolution.utils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@JsModule("./src/zzzz.js")
public class BPMN extends VerticalLayout {

	public BPMN() {
		setSizeFull();
		createForm();
	}

	private void createForm() {
		try {
			
			Label div = new Label();
			div.setSizeFull();
			div.setId("canvas");
			
			Button btnLoad = new Button("LOAD");
			btnLoad.addClickListener(x -> {
				try {
					
					UI.getCurrent().getPage().executeJs("loadScreen()");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			Button btnOpen = new Button("Open");
			btnOpen.addClickListener(x -> {
				try {
					
					UI.getCurrent().getPage().executeJs("lopenDiagram()");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			add(div,btnLoad,btnOpen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
