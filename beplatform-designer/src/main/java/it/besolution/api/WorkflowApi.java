package it.besolution.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.besolution.utils.CommonUtils;

@Component
public class WorkflowApi {

	@Value("${server.port}")
	String serverPort;

	public static String API_WORKFLOW_GET_ALL;
	public static String API_WORKFLOW_SAVE;
	public static String API_WORKFLOW_PROPERTY_GET_ALL;
	public static String API_WORKFLOW_PROPERTY_SAVE;
	public static String API_WORKFLOW_ROLES_GET_ALL;
	public static String API_WORKFLOW_ROLE_SAVE;
	
	public static String API_WORKFLOW_ADVANCE_GET_ALL;
	public static String API_WORKFLOW_ADVANCE_SAVE;
	
	public static String API_WORKFLOW_SETTINGS_GET_ALL;
	public static String API_WORKFLOW_SETTINGS_SAVE;
	
	
	@PostConstruct
	public void init() {
		try {
			API_WORKFLOW_GET_ALL = "http://localhost:"+serverPort+"/designer/api/workflow/info/all/solution/{solutionId}";
			API_WORKFLOW_SAVE = "http://localhost:"+serverPort+"/designer/api/workflow/info/save";
			API_WORKFLOW_PROPERTY_GET_ALL = "http://localhost:"+serverPort+"/designer/api/workflow/property/all/workflow/{workFlowId}";
			API_WORKFLOW_PROPERTY_SAVE = "http://localhost:"+serverPort+"/designer/api/workflow/property/save/workflow/{workFlowId}";
			API_WORKFLOW_ROLES_GET_ALL = "http://localhost:"+serverPort+"/designer/api/workflow/all/workflow/{workFlowId}";
			API_WORKFLOW_ROLE_SAVE = "http://localhost:"+serverPort+"/designer/api/workflow/save/workflow/{workFlowId}";

			API_WORKFLOW_ADVANCE_GET_ALL = "http://localhost:"+serverPort+"/designer/api/workflow/advanced/all/workflow/{workFlowId}";
			API_WORKFLOW_ADVANCE_SAVE = "http://localhost:"+serverPort+"/designer/api/workflow/advanced/save-jar-properties/";

			API_WORKFLOW_SETTINGS_GET_ALL = "http://localhost:"+serverPort+"/designer/api/workflow/advanced-setting/all/workflow/{workFlowId}";
			API_WORKFLOW_SETTINGS_SAVE = "http://localhost:"+serverPort+"/designer/api/workflow/advanced-setting/save/workflow/{workFlowId}";

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowApi.class);

		}

	}

}
