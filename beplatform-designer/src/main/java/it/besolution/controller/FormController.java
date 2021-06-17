package it.besolution.controller;

import it.besolution.model.Form;
import it.besolution.model.FormAttribute;
import it.besolution.model.FormMaster;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.FormService;
import it.besolution.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/form/")
public class FormController {

	private static final Logger LOG = LoggerFactory.getLogger(FormController.class);

	@Autowired
	private FormService formService;

	@PostMapping("/save")
	public ResponseEntity<ApiRestResponse> createForm(@RequestBody Form form) {
		ApiRestResponse response = null;
		try {
			LOG.info("Saving a form");
			form = formService.createForm(form);
			response = new ApiRestResponse();
			response.setData(form);
			LOG.info("Saving the form complete");
		} catch (Exception ex) {

			LOG.error("Error while saving the form. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}
		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@PutMapping("/master/update")
	public ResponseEntity<ApiRestResponse> update(@RequestBody FormMaster form) {
		ApiRestResponse response = null;
		try {
			LOG.info("Update a Form Master ");
			form = formService.updateMaster(form);
			response = new ApiRestResponse();
			response.setData(form);
			LOG.info("Update the FormMaster complete");
		} catch (Exception ex) {

			LOG.error("Error while updating the FormMaster . Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@PutMapping("/attributes/update")
	public ResponseEntity<ApiRestResponse> update(@RequestBody List<FormAttribute> attributes) {
		ApiRestResponse response = null;
		try {
			LOG.info("Update a form attributes ");
			attributes = formService.updateAttributes(attributes);
			response = new ApiRestResponse();
			response.setData(attributes);
			LOG.info("Update the form attributes complete");
		} catch (Exception ex) {

			LOG.error("Error while updating the form attributes . Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@PutMapping("/{id}/attributes/add")
	public ResponseEntity<ApiRestResponse> addAttributes(@PathVariable(value="id") Integer Id, @RequestBody List<FormAttribute> attributes) {
		ApiRestResponse response = null;
		try {
			LOG.info("add form attributes ");
			attributes = formService.addAttributes(Id ,attributes);
			response = new ApiRestResponse();
			response.setData(attributes);
			LOG.info("Add the form attributes complete");
		} catch (Exception ex) {

			LOG.error("Error while adding the form attributes . Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@GetMapping("/master/{id}")
	public ResponseEntity<ApiRestResponse> getFormsByMasterId(@PathVariable("id") Integer objectId) {
		ApiRestResponse response = null;
		try {
			LOG.info("Getting form for  master Id: {}", objectId);
			Form formByMasterId = formService.getFormByMasterId(objectId);
			response = new ApiRestResponse();
			response.setData(formByMasterId);
			LOG.info("form  with master Id: {} fetched", objectId);
		} catch (Exception ex) {

			LOG.error("Error while fetching form. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	// Will uncomment if needed. Hope this endpoint will not be needed
	/*@GetMapping("/objectclass/{id}")
	public ResponseEntity<ApiRestResponse> getFormsByObjectId(@PathVariable("id") Integer objectId) {
		ApiRestResponse response = null;
		try {
			LOG.info("Getting form for  object Id: {}", objectId);
			List<Form> formsByObjectId = formService.getFormsByObjectId(objectId);
			response = new ApiRestResponse();
			response.setData(formsByObjectId);
			LOG.info("form with object Id: {} fetched", objectId);
		} catch (Exception ex) {

			LOG.error("Error while fetching form. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}*/

	@GetMapping("/solution/{id}")
	public ResponseEntity<ApiRestResponse> getFormsBySolutionId(@PathVariable("id") Integer solutionId) {
		ApiRestResponse response = null;
		try {
			LOG.info("Getting  all form  by solution id ");
			List<Form> forms = formService.getFormsBySolutionId(solutionId);
			response = new ApiRestResponse();
			response.setData(forms);
			LOG.info(" All forms  by solution id fetched");
		} catch (Exception ex) {

			LOG.error("Error while fetching form. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiRestResponse> deleteFrom(@PathVariable Integer id) {
		ApiRestResponse response = null;
		try {
			LOG.info("delete a form");
			formService.deleteFrom(id);
			response = new ApiRestResponse();
			LOG.info("delete the form complete");
		} catch (Exception ex) {

			LOG.error("Error while deleting the form. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@DeleteMapping("{id}/attribute/{attributeId}")
	public ResponseEntity<ApiRestResponse> delete(@PathVariable(value = "id") Integer id,
			@PathVariable(value = "attributeId") Integer attributeId) {
		ApiRestResponse response = null;
		try {
			LOG.info("delete a form attribute");
			formService.deleteFromAttribute(id,attributeId);
			response = new ApiRestResponse();
			LOG.info("delete the form attribute complete");
		} catch (Exception ex) {

			LOG.error("Error while deleting the form attribute. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
