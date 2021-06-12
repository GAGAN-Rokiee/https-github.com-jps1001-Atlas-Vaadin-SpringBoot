package it.besolution.ui.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import it.besolution.api.SolutionApi;
import it.besolution.model.Solution;
import it.besolution.rest.ApiRestResponse;
import it.besolution.utils.CommonUtils;

public class SolutionPresenter {

	public List<SolutionModel> getSolutions() {
		List<SolutionModel> listOfSolutions =  new ArrayList<SolutionModel>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			String solutions  = restTemplate.getForObject(SolutionApi.API_SOLUTION_GET_ALL,String.class);

			JSONObject obj = new  JSONObject(solutions);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			listOfSolutions = objectMapper.readValue(data.toString(), new TypeReference<List<SolutionModel>>(){});

		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, SolutionPresenter.class);
		}
		return listOfSolutions;
	}


	public ApiRestResponse  createNewSolution(Solution newSolution) {
		ApiRestResponse restResponse = new ApiRestResponse();

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<Solution> entity = new HttpEntity<Solution>(newSolution,headers);

			String response = restTemplate.exchange(SolutionApi.API_SOLUTION_NEW, HttpMethod.POST, entity, String.class).getBody();

			JSONObject obj = new  JSONObject(response);

			restResponse.setIsSuccess(obj.getBoolean("isSuccess"));
			restResponse.setErrorMessage(String.valueOf(obj.get("errorMessage")));
			restResponse.setData(obj.get("data"));

		}
		catch (Exception e) {
			restResponse.setIsSuccess(false);
			CommonUtils.printStakeTrace(e, SolutionPresenter.class);
		}
		return restResponse;
	}


}
