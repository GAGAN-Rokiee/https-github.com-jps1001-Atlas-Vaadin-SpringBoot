package it.besolution.ui.objectclass;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import it.besolution.utils.ScreenFactory;

public class ObjectClassGridView extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(ObjectClassGridView.class);


	Grid<ObjectClassModel> gridObjects = null;

	public ObjectClassGridView() {

		setSizeFull();
		createGrid();
	}

	public void populateDate() {
		try {

			List<ObjectClassModel> listObjectClasses = new ObjectClassPresenter().getObjectClasses();
			gridObjects.setItems(listObjectClasses);

		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
			
		}
	}

	private void createGrid() {
		try {

			VerticalLayout vLayoytGrid =  new VerticalLayout();
			vLayoytGrid.setSizeFull();

			HorizontalLayout hLayoutHeader = new HorizontalLayout();
			hLayoutHeader.setWidthFull();

			H1 heading = new H1("Object Classes");
			heading.getStyle().set("margin-right", "auto");

			Button btnNew = new Button("New");
			btnNew.setIcon(VaadinIcon.PLUS.create());
			btnNew.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
			btnNew.addClickListener(event ->{
				try {

					ScreenFactory.getInstance().objectClassFormView.resetForm();
					ScreenFactory.getInstance().mainNavigationView.changeContent(ScreenFactory.getInstance().objectClassFormView);

				} catch (Exception e) {
					LOG.error("Error: {}", e.getMessage());
				}
			});

			hLayoutHeader.add(heading,btnNew);
			hLayoutHeader.setVerticalComponentAlignment(Alignment.END, btnNew);

			gridObjects = new Grid<ObjectClassModel>(ObjectClassModel.class);
			gridObjects.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.LUMO_WRAP_CELL_CONTENT,GridVariant.LUMO_ROW_STRIPES,GridVariant.LUMO_COMPACT);
			gridObjects.setSizeFull();

			ArrayList<ObjectClassModel> objects = new ArrayList<ObjectClassModel>();

			gridObjects.setItems(objects);

			gridObjects.setColumns("className","entityName","securityEnabled","counterName","systemClass");
			gridObjects.getColumnByKey("className").setHeader("Class Name");
			gridObjects.getColumnByKey("entityName").setHeader("Entity Name");
			gridObjects.getColumnByKey("securityEnabled").setHeader("Security Enabled");
			gridObjects.getColumnByKey("counterName").setHeader("Counter");
			gridObjects.getColumnByKey("systemClass").setHeader("System");


			vLayoytGrid.add(hLayoutHeader);
			vLayoytGrid.add(gridObjects);
			add(vLayoytGrid);


		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		
		}

	}


}
