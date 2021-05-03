package it.besolution.ui.solution;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.besolution.utils.Constants;

@Service
public class SolutionPresenter {


	public List<SolutionModel> getSolutions() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String solutions  = restTemplate.getForObject(Constants.SOLUTION_GET_ALL,String.class);

			JSONObject obj = new  JSONObject(solutions);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<SolutionModel> listOfSolutions = objectMapper.readValue(data.toString(), new TypeReference<List<SolutionModel>>(){});

			return listOfSolutions;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
