package it.besolution.ui.propertydefinition;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import it.besolution.ui.objectclass.ObjectClassModel;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.ScreenFactory;

public class PropertyDefinitionGridView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ComboBox<ObjectClassModel> objectClassCombo = null;
	Grid<PropertyDefinitionModel> grid = null;
	Dialog dialog;

	public PropertyDefinitionGridView() {

		setSizeFull();
		createDialog();
		createGrid();
	}

	public void populateData() {
		try {
			// populate combo
			// clear the grid
			// add value change listener to combo

			List<PropertyDefinitionModel> listObjectClasses = new PropertyDefinitionPresenter().getObjectClasses();
			grid.setItems(listObjectClasses);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionGridView.class);

		}
	}

	private void createDialog() {
		dialog = new Dialog();
		dialog.setWidth("400px");
		dialog.setDraggable(true);
		dialog.setCloseOnEsc(true);
		dialog.setModal(true);
	}

	private void createGrid() {
		try {

			H1 heading = new H1("Property Definitions");
			heading.getStyle().set("margin-right", "auto");

			Button btnNew = new Button("New");
			btnNew.setIcon(VaadinIcon.PLUS.create());
			btnNew.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			btnNew.addClickListener(event -> {
				try {
					// add listeners for Cancel & Save events
					ScreenFactory.getInstance().propertyDefinitionForm
							.addListener(PropertyDefinitionForm.CancelEvent.class, this::handleCancelEvent);

					ScreenFactory.getInstance().propertyDefinitionForm.resetForm();

//					ScreenFactory.getInstance().mainNavigationView
//							.changeContent(ScreenFactory.getInstance().propertyDefinitionFormView);
					dialog.add(ScreenFactory.getInstance().propertyDefinitionForm);
					dialog.open();

				} catch (Exception e) {
					CommonUtils.printStakeTrace(e, PropertyDefinitionGridView.class);
				}
			});

			// combo for object class
			objectClassCombo = new ComboBox<>();
			objectClassCombo.setWidthFull();
			objectClassCombo.setLabel("Object Class");
			objectClassCombo.setPlaceholder("Select an Object Class");

			grid = new Grid<PropertyDefinitionModel>(PropertyDefinitionModel.class);
			grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_WRAP_CELL_CONTENT,
					GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
			grid.setSizeFull();

			ArrayList<PropertyDefinitionModel> objects = new ArrayList<PropertyDefinitionModel>();

			grid.setItems(objects);

			grid.removeAllColumns();
			grid.setColumns("className", "entityName");
			grid.getColumnByKey("className").setHeader("Property name");
			grid.getColumnByKey("entityName").setHeader("Property type");

			add(heading, objectClassCombo, btnNew, grid);
			setAlignSelf(Alignment.END, btnNew);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionGridView.class);
		}

	}

	public void handleCancelEvent(PropertyDefinitionForm.CancelEvent event) {
		dialog.close();
	}
}
