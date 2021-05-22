package it.besolution.ui.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkflowModel{
	
	private int id;

	private String workflowName;

	private String lastUpdated;
	
	private String propertyName;
	
	private String propertyType;
	
	private String roleName;

	private String dynamic;
	
	private String dynamicProperty;
	
	private String creation;

}
