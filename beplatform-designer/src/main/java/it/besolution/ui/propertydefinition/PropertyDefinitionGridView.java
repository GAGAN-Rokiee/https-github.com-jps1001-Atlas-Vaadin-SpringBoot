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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import it.besolution.rest.ApiRestResponse;
import it.besolution.ui.objectclass.ObjectClassModel;
import it.besolution.ui.objectclass.ObjectClassPresenter;
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

			populateCombo();
			populateGrid();

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
			btnNew.setEnabled(false);
			btnNew.addClickListener(event -> {
				try {
					// add listeners for Cancel & Save events
					ScreenFactory.getInstance().propertyDefinitionForm
							.addListener(PropertyDefinitionForm.CancelEvent.class, this::handleCancelEvent);

					ScreenFactory.getInstance().propertyDefinitionForm
							.addListener(PropertyDefinitionForm.SaveEvent.class, this::handleSaveEvent);

					ScreenFactory.getInstance().propertyDefinitionForm.resetForm();
					ScreenFactory.getInstance().propertyDefinitionForm
							.setObjectClassId(objectClassCombo.getValue().getId());

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
			objectClassCombo.setItemLabelGenerator(item -> {
				return item.getClassName();
			});
			objectClassCombo.addValueChangeListener(e -> {
				if (e.getValue() == null) {
					btnNew.setEnabled(false);
				} else {
					btnNew.setEnabled(true);
				}
				populateGrid();
			});

			grid = new Grid<PropertyDefinitionModel>(PropertyDefinitionModel.class);
			grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_WRAP_CELL_CONTENT,
					GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
			grid.setSizeFull();

			ArrayList<PropertyDefinitionModel> objects = new ArrayList<PropertyDefinitionModel>();

			grid.removeAllColumns();
			grid.addColumn("propertyName").setHeader("Property name");
			grid.addColumn("propertyType").setHeader("Property type");
			grid.setItems(objects);

			add(heading, objectClassCombo, btnNew, grid);
			setAlignSelf(Alignment.END, btnNew);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionGridView.class);
		}

	}

	private void populateCombo() {
		List<ObjectClassModel> listObjectClasses = new ObjectClassPresenter().getObjectClasses();
		objectClassCombo.setItems(listObjectClasses);
	}

	private void populateGrid() {
		int selectedObjectClassId = 0;
		ObjectClassModel selectedObjectClassModel = objectClassCombo.getValue();
		if (selectedObjectClassModel != null) {
			selectedObjectClassId = selectedObjectClassModel.getId();
		}

		List<PropertyDefinitionModel> propertyDefinitions = new PropertyDefinitionPresenter()
				.getPropertyDefinitions(selectedObjectClassId);
		grid.setItems(propertyDefinitions);

	}

	private void handleCancelEvent(PropertyDefinitionForm.CancelEvent event) {
		dialog.close();
	}

	private void handleSaveEvent(PropertyDefinitionForm.SaveEvent event) {
		PropertyDefinitionModel model = event.getPropertyDefinitionModel();

		ApiRestResponse response = new PropertyDefinitionPresenter().createNewPropertyDefinition(model);
		if (response.getIsSuccess()) {
			Notification.show("Property Definition created successfully", 3000, Position.TOP_CENTER);
			ScreenFactory.getInstance().propertyDefinitionForm.resetForm();
			populateGrid();
		} else {
			Notification.show("Could not create..." + response.getErrorMessage());
		}
	}

}
