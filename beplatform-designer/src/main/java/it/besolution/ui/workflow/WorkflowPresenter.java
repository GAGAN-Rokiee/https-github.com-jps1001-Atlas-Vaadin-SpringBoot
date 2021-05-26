package it.besolution.ui.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.vaadin.flow.server.VaadinSession;

import it.besolution.api.WorkflowApi;
import it.besolution.model.workflow.WorkFlowMaster;
import it.besolution.model.workflow.WorkFlowProperty;
import it.besolution.model.workflow.WorkFlowRoles;
import it.besolution.rest.ApiRestResponse;
import it.besolution.ui.solution.SolutionModel;
import it.besolution.utils.CommonUtils;
import it.besolution.utils.Constants;

public class WorkflowPresenter {


	public ArrayList<WorkFlowMaster> getAllWorkflows() {
		ArrayList<WorkFlowMaster> listOfWorkflows = null;

		try {

			listOfWorkflows = new ArrayList<WorkFlowMaster>();
			SolutionModel solutionModel = (SolutionModel) VaadinSession.getCurrent().getAttribute(Constants.SOLUTION_MODEL);

			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("solutionId", solutionModel.getId());

			RestTemplate restTemplate = new RestTemplate();
			String objects  = restTemplate.getForObject(WorkflowApi.API_WORKFLOW_GET_ALL,String.class,params);

			JSONObject obj = new  JSONObject(objects);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.readTree(data.toString());


			listOfWorkflows = objectMapper.readValue(data.toString(), new TypeReference<ArrayList<WorkFlowMaster>>(){});


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowPresenter.class);


		}
		return listOfWorkflows;
	}

	public ApiRestResponse createWorkflowMaster(WorkFlowMaster bean) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<WorkFlowMaster> entity = new HttpEntity<WorkFlowMaster>(bean,headers);

			SolutionModel solutionModel = (SolutionModel) VaadinSession.getCurrent().getAttribute(Constants.SOLUTION_MODEL);

			bean.setSolutionId(solutionModel.getId());
			bean.setPrefix(solutionModel.getPrefix());

			String response = restTemplate.exchange(WorkflowApi.API_WORKFLOW_SAVE, HttpMethod.POST, entity, String.class,bean).getBody();

			JSONObject obj = new  JSONObject(response);

			ApiRestResponse restResponse = new ApiRestResponse();
			restResponse.setIsSuccess(obj.getBoolean("isSuccess"));
			restResponse.setErrorMessage(String.valueOf(obj.get("errorMessage")));
			restResponse.setData(obj.get("data"));

			return restResponse;
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowPresenter.class);

		}
		return null;

	}

	public ApiRestResponse createWorkflowMProperty(WorkFlowProperty bean) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			List<WorkFlowProperty> beanList = new ArrayList<WorkFlowProperty>();
			beanList.add(bean);
			HttpEntity<List<WorkFlowProperty>> entity = new HttpEntity<List<WorkFlowProperty>>(beanList,headers);
			
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("workFlowId", bean.getWorkFlowId());

			
			String response = restTemplate.exchange(WorkflowApi.API_WORKFLOW_PROPERTY_SAVE,HttpMethod.POST, entity, String.class,params).getBody();
			JSONObject obj = new  JSONObject(response);

			ApiRestResponse restResponse = new ApiRestResponse();
			restResponse.setIsSuccess(obj.getBoolean("isSuccess"));
			restResponse.setErrorMessage(String.valueOf(obj.get("errorMessage")));
			restResponse.setData(obj.get("data"));

			return restResponse;
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowPresenter.class);

		}
		return null;

	}
	
	public ArrayList<WorkFlowProperty> getWorkflowProperties(WorkFlowMaster bean) {
		ArrayList<WorkFlowProperty> listOfWorkflowsProperties = null;

		try {

			listOfWorkflowsProperties = new ArrayList<WorkFlowProperty>();
			
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("workFlowId", bean.getId());

			RestTemplate restTemplate = new RestTemplate();
			String objects  = restTemplate.getForObject(WorkflowApi.API_WORKFLOW_PROPERTY_GET_ALL,String.class,params);

			JSONObject obj = new  JSONObject(objects);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.readTree(data.toString());


			listOfWorkflowsProperties = objectMapper.readValue(data.toString(), new TypeReference<ArrayList<WorkFlowProperty>>(){});


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowPresenter.class);


		}
		return listOfWorkflowsProperties;
	}

	public ApiRestResponse createWorkflowRoles(WorkFlowRoles bean) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			List<WorkFlowRoles> beanList = new ArrayList<WorkFlowRoles>();
			beanList.add(bean);
			HttpEntity<List<WorkFlowRoles>> entity = new HttpEntity<List<WorkFlowRoles>>(beanList,headers);
			
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("workFlowId", bean.getWorkFlowId());

			
			String response = restTemplate.exchange(WorkflowApi.API_WORKFLOW_ROLE_SAVE,HttpMethod.POST, entity, String.class,params).getBody();
			JSONObject obj = new  JSONObject(response);

			ApiRestResponse restResponse = new ApiRestResponse();
			restResponse.setIsSuccess(obj.getBoolean("isSuccess"));
			restResponse.setErrorMessage(String.valueOf(obj.get("errorMessage")));
			restResponse.setData(obj.get("data"));

			return restResponse;
		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowPresenter.class);

		}
		return null;

	}
	
	public ArrayList<WorkFlowRoles> getWorkflowRoles(WorkFlowMaster bean) {
		ArrayList<WorkFlowRoles> listOfWorkflowsRoles = null;

		try {

			listOfWorkflowsRoles = new ArrayList<WorkFlowRoles>();
			
			HashMap<String, Integer> params = new HashMap<String, Integer>();
			params.put("workFlowId", bean.getId());

			RestTemplate restTemplate = new RestTemplate();
			String objects  = restTemplate.getForObject(WorkflowApi.API_WORKFLOW_ROLES_GET_ALL,String.class,params);

			JSONObject obj = new  JSONObject(objects);
			JSONArray data = obj.getJSONArray("data");

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.readTree(data.toString());


			listOfWorkflowsRoles = objectMapper.readValue(data.toString(), new TypeReference<ArrayList<WorkFlowRoles>>(){});


		} catch (Exception e) {
			CommonUtils.printStakeTrace(e, WorkflowPresenter.class);


		}
		return listOfWorkflowsRoles;
	}

}
