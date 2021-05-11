package it.besolution.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ObjectClassApi {

	@Value("${server:port}")
	String serverPort;
	
	public static String API_OBJECT_CLASS_NEW = "http://localhost:8080/designer/api/object-class/save/solution/{solutionId} ";
	public static String API_OBJECT_CLASS_GET = "http://localhost:8080/designer/api/object-class/all/{solutionId}";
	
}
