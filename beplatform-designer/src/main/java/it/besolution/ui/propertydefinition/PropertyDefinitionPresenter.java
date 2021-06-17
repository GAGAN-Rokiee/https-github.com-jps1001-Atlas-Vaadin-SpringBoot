package it.besolution.ui.propertydefinition;

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

import it.besolution.api.PropertyDefinitionApi;
import it.besolution.rest.ApiRestResponse;
import it.besolution.ui.solution.SolutionModel;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;

public class PropertyDefinitionPresenter {


	public ArrayList<PropertyDefinitionModel> getPropertyDefinitions(int objectClassId) {
		ArrayList<PropertyDefinitionModel> propertyDefinitionModels = new ArrayList<PropertyDefinitionModel>();

		try {

			SolutionModel solutionModel = (SolutionModel) VaadinSession.getCurrent().getAttribute(Constants.SOLUTION_MODEL);
			
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("solutionId", solutionModel.getId());
			params.put("objectClassId", objectClassId);
			
			RestTemplate restTemplate = new RestTemplate();
			String objects  = restTemplate.getForObject(PropertyDefinitionApi.API_PROPERTY_DEFINITION_GET,String.class,params);

			JSONObject obj = new  JSONObject(objects);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			propertyDefinitionModels = objectMapper.readValue(data.toString(), new TypeReference<ArrayList<PropertyDefinitionModel>>(){});


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, PropertyDefinitionPresenter.class);


		}
		return propertyDefinitionModels;
	}

	public ApiRestResponse  createNewPropertyDefinition(PropertyDefinitionModel newPropertyDefinition) {
		ApiRestResponse restResponse = new ApiRestResponse();

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<PropertyDefinitionModel> entity = new HttpEntity<PropertyDefinitionModel>(newPropertyDefinition,headers);

			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("solutionId", newPropertyDefinition.getSolutionId());
			params.put("objectClassId", newPropertyDefinition.getObjectClassId());
			
			String response = restTemplate.exchange(PropertyDefinitionApi.API_PROPERTY_DEFINITION_NEW, HttpMethod.POST, entity, String.class,params).getBody();

			JSONObject obj = new  JSONObject(response);

			restResponse.setIsSuccess(obj.getBoolean("isSuccess"));
			restResponse.setErrorMessage(String.valueOf(obj.get("errorMessage")));
			restResponse.setData(obj.get("data"));

		}
		catch (Exception e) {
			restResponse.setIsSuccess(false);
			CommonUtils.printStakeTrace(e, PropertyDefinitionPresenter.class);

		}
		return restResponse;
	}

}
