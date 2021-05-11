package it.besolution.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SolutionApi {

	@Value("${server:port}")
	String serverPort;
	
	public static String API_SOLUTION_GET_ALL = "http://localhost:8080/designer/api/solution/all";
	public static String API_SOLUTION_NEW = "http://localhost:8080/designer/api/solution/save";
	
}
