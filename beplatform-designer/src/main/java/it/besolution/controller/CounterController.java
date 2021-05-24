package it.besolution.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.besolution.model.Counter;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.CounterService;
import it.besolution.utils.ResponseUtil;

@RestController
@RequestMapping("/api/counter")
public class CounterController {
	private static final Logger LOG = LoggerFactory.getLogger(CounterController.class);

	@Autowired
	CounterService counterService;

	@PostMapping("/save")
	public ResponseEntity<ApiRestResponse> createCounter(@RequestBody Counter counter) {
		ApiRestResponse response = null;
		try {
			LOG.info("Saving a counter");
			counter = counterService.createCounter(counter);
			response = new ApiRestResponse();
			response.setData(counter);
			LOG.info("Saving the counter complete");
		} catch (Exception ex) {

			LOG.error("Error while saving the counter. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}
		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiRestResponse> getCountersById(@PathVariable Integer id) {

		ApiRestResponse response = null;
		try {
			LOG.info("Getting counter for Id: {}", id);
			Counter counter = counterService.getCounterById(id);
			response = new ApiRestResponse();
			response.setData(counter);
			LOG.info("counter with Id: {} fetched", id);
		}
		catch (Exception ex) {

			LOG.error("Error while fetching counters. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@GetMapping("/solution/{solutionId}")
	public ResponseEntity<ApiRestResponse> getCountersBySolutionId(@PathVariable Integer solutionId) {

		ApiRestResponse response = null;
		try {
			LOG.info("Getting counters for  solutionId: {}", solutionId);
			List<Counter> counters = counterService.getCountersBySolutionId(solutionId);
			response = new ApiRestResponse();
			response.setData(counters);
			LOG.info("counters with solutionId: {} fetched", solutionId);
		}
		catch (Exception ex) {

			LOG.error("Error while fetching counters. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ApiRestResponse> update(@RequestBody Counter counter) {
		ApiRestResponse response = null;
		try {
			LOG.info("Update a counter");
			counter = counterService.update(counter);
			response = new ApiRestResponse();
			response.setData(counter);
			LOG.info("Update the counter complete");
		} catch (Exception ex) {

			LOG.error("Error while updating the counter. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiRestResponse> deleteById(@PathVariable Integer id) {
		ApiRestResponse response = null;
		try {
			LOG.info("delete a counter");
			counterService.delete(id);
			response = new ApiRestResponse();
			LOG.info("delete the counter complete");
		} catch (Exception ex) {

			LOG.error("Error while deleting the counter. Error: {}", ex.getMessage());
			response = ResponseUtil.returnApiResponse(null, ex.getMessage());
		}

		return new ResponseEntity<>(response, response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	
}
