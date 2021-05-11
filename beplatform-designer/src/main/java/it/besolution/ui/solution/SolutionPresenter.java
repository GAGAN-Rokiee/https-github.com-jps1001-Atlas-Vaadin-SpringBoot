package it.besolution.ui.solution;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.besolution.api.SolutionApi;
import it.besolution.model.Solution;
import it.besolution.rest.ApiRestResponse;

public class SolutionPresenter {

	private static final Logger LOG = LoggerFactory.getLogger(SolutionPresenter.class);

	public List<SolutionModel> getSolutions() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String solutions  = restTemplate.getForObject(SolutionApi.API_SOLUTION_GET_ALL,String.class);

			JSONObject obj = new  JSONObject(solutions);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<SolutionModel> listOfSolutions = objectMapper.readValue(data.toString(), new TypeReference<List<SolutionModel>>(){});

			return listOfSolutions;
		} catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
		}
		return null;
	}


	public ApiRestResponse  createNewSolution(Solution newSolution) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Solution> entity = new HttpEntity<Solution>(newSolution,headers);

			String response = restTemplate.exchange(SolutionApi.API_SOLUTION_NEW, HttpMethod.POST, entity, String.class).getBody();
		
			JSONObject obj = new  JSONObject(response);
						
			ApiRestResponse restResponse = new ApiRestResponse();
			restResponse.setIsSuccess(obj.getBoolean("isSuccess"));
			restResponse.setErrorMessage(String.valueOf(obj.get("errorMessage")));
			restResponse.setData(obj.get("data"));
		
			return restResponse;
		}
		catch (Exception e) {
			LOG.error("Error: {}", e.getMessage());
			
		}
		return null;
	}


}
