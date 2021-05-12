package it.besolution.ui.objectclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ObjectClassModel {

	private String basePath = "";
	private int baseType;
	private String className;
	
	private int classType;
	private String classTypeDesc;
	
	private String counterName;
	
	private boolean cryptoContent = false;
	private String cryptoContentDesc;
	
	private String defaultWorkflow;
	private String description;
	private String entityName;
	
	private boolean securityEnabled;
	private String securityEnabledDesc;

	private int solutionId;
	private String storageType;
	
	private boolean systemClass;
	private String systemClassDesc;
	
	private boolean hasContent;
	private String hasContentDesc;

}
