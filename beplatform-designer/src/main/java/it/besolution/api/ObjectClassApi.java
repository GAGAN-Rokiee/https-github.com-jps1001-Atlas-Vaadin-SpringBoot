package it.besolution.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.besolution.utils.CommonUtils;

@Component
public class ObjectClassApi {

	@Value("${server.port}")
	String serverPort;

	public static String API_OBJECT_CLASS_GET;
	public static String API_OBJECT_CLASS_NEW;

	@PostConstruct
	public void init() {
		try {
			API_OBJECT_CLASS_GET = "http://localhost:"+serverPort+"/designer/api/object-class/all/{solutionId}";
			API_OBJECT_CLASS_NEW = "http://localhost:"+serverPort+"/designer/api/object-class/save/solution/{solutionId}";
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, ObjectClassApi.class);
		}


	}
}
