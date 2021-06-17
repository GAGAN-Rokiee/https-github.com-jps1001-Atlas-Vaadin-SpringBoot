package it.besolution.ui.propertydefinition;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PropertyDefinitionModel {

	private int id;
	private int solutionId;
	private int objectClassId;
	private String propertyName;
	private String propertyType;
	private String label;
	private String constraintKey;
	private String constraintValue;
	private boolean propertyIsNull = Boolean.FALSE;
	private String defaultValue;
	private Date lastUpdated = new Date();
	private boolean toUpdate;
	private boolean toDelete;
	private String objectClassName;

}
