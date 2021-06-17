package it.besolution.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.besolution.utils.CommonUtils;

@Component
public class PropertyDefinitionApi {

	@Value("${server.port}")
	String serverPort;

	public static String API_PROPERTY_DEFINITION_NAME_TYPE_GET;
	public static String API_PROPERTY_DEFINITION_GET;
	public static String API_PROPERTY_DEFINITION_GET_ALL;
	public static String API_PROPERTY_DEFINITION_NEW;

	@PostConstruct
	public void init() {
		try {
			API_PROPERTY_DEFINITION_NAME_TYPE_GET = "http://localhost:"+serverPort+"/designer/api/property/get-name-type/solution/{solutionId}/object/{objectClassId}";
			API_PROPERTY_DEFINITION_GET = "http://localhost:"+serverPort+"/designer/api/property/solution/{solutionId}/object/{objectClassId}";
			API_PROPERTY_DEFINITION_GET_ALL = "http://localhost:"+serverPort+"/designer/api/property/all/{solutionId}";
			API_PROPERTY_DEFINITION_NEW = "http://localhost:"+serverPort+"/designer/api/property/save/property/{solutionId}";
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionApi.class);
		}


	}
}
