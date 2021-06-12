package it.besolution.ui.Home;

import org.springframework.web.client.RestTemplate;

import it.besolution.api.HomeApi;
import it.besolution.utils.CommonUtils;

public class HomePresenter {

	public String getJarsPath() {
		String filePath =  new String();
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			filePath  = restTemplate.getForObject(HomeApi.API_HOME_GET_JARS_PATH,String.class);

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, HomePresenter.class);
		}
		return filePath;
	}
}
