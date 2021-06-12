package it.besolution.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.besolution.utils.CommonUtils;

@Component
public class SolutionApi {

	@Value("${server.port}")
	String serverPort;

	public static String API_SOLUTION_GET_ALL;
	public static String API_SOLUTION_NEW;

	@PostConstruct
	public void init() {
		try {
			API_SOLUTION_GET_ALL = "http://localhost:"+serverPort+"/designer/api/solution/all";
			API_SOLUTION_NEW = "http://localhost:"+serverPort+"/designer/api/solution/save";
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, SolutionApi.class);

		}

	}


}
