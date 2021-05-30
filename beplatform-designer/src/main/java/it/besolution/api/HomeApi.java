package it.besolution.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.besolution.utils.CommonUtils;

@Component
public class HomeApi {
	
		@Value("${server.port}")
		String serverPort;

		public static String API_HOME_GET_JARS_PATH;

		@PostConstruct
		public void init() {
			try {
				API_HOME_GET_JARS_PATH = "http://localhost:"+serverPort+"/designer/api/home/get-upload-jars-path";
			} catch (Exception e) {
				CommonUtils.printStakeTrace(e, HomeApi.class);
			}


		}

}
