package it.besolution.ui.objectclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.VaadinSession;

import it.besolution.api.ObjectClassApi;
import it.besolution.rest.ApiRestResponse;
import it.besolution.ui.solution.SolutionModel;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;

public class ObjectClassPresenter {


	public ArrayList<ObjectClassModel> getObjectClasses() {
		ArrayList<ObjectClassModel> listOfObjectClasses = new ArrayList<ObjectClassModel>();

		try {

			SolutionModel solutionModel = (SolutionModel) VaadinSession.getCurrent().getAttribute(Constants.SOLUTION_MODEL);

			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("solutionId", solutionModel.getId());

			RestTemplate restTemplate = new RestTemplate();
			String objects  = restTemplate.getForObject(ObjectClassApi.API_OBJECT_CLASS_GET,String.class,params);

			JSONObject obj = new  JSONObject(objects);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			listOfObjectClasses = objectMapper.readValue(data.toString(), new TypeReference<ArrayList<ObjectClassModel>>(){});


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, ObjectClassPresenter.class);


		}
		return listOfObjectClasses;
	}

	public ApiRestResponse  createNewObjectClass(ObjectClassModel newObject) {
		ApiRestResponse restResponse = new ApiRestResponse();

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<ObjectClassModel> entity = new HttpEntity<ObjectClassModel>(newObject,headers);

			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("solutionId", newObject.getSolutionId());

			String response = restTemplate.exchange(ObjectClassApi.API_OBJECT_CLASS_NEW, HttpMethod.POST, entity, String.class,params).getBody();

			JSONObject obj = new  JSONObject(response);

			restResponse.setIsSuccess(obj.getBoolean("isSuccess"));
			restResponse.setErrorMessage(String.valueOf(obj.get("errorMessage")));
			restResponse.setData(obj.get("data"));

		}
		catch (Exception e) {
			restResponse.setIsSuccess(false);
			CommonUtils.printStakeTrace(e, ObjectClassPresenter.class);

		}
		return restResponse;
	}

}
