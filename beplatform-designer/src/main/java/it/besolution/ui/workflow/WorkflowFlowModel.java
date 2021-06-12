package it.besolution.ui.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkflowFlowModel {
	
	private String elementName = null;
	private String elementID = null;
	private String screenName = null;
	private String name = null;
	private String desc = null;
	private boolean isSync = false;	

}
