package it.besolution.controller;



import it.besolution.dto.PropertyNameTypeDto;
import it.besolution.model.Property;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.PropertyService;
import it.besolution.utils.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PropertyController.class);
    @Autowired
    private PropertyService propertyService;
	 
    @PostMapping("/save/property/{solutionId}")
    public ResponseEntity<ApiRestResponse> saveProperty(@RequestBody Property property, @PathVariable Integer solutionId) {

    	 ApiRestResponse response = null;
         try {
             LOG.info("Saving property");
             property.setSolutionId(solutionId);
             final Property savedProperty = propertyService.save(property);
             response = new ApiRestResponse();
             response.setData(savedProperty);
             LOG.info("Saving the property complete");
         } catch (Exception ex) {

             LOG.error("Error while saving the property. Error: {}", ex.getMessage());
             response = ResponseUtil.returnApiResponse(null, ex.getMessage());
         }

         return new ResponseEntity<>(response,
                 response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @GetMapping("/solution/{solutionId}/object/{objectClassId}")
    public ResponseEntity<ApiRestResponse> getPropertiesBySolutionAndObjectClassId(@PathVariable Integer solutionId,
                                                                         @PathVariable Integer objectClassId) {

        ApiRestResponse response = null;
        try {
            LOG.info("Getting properties for objectClassId : {}", objectClassId);
            final List<Property> allProperties = propertyService.findBySolutionIdAndObjectClassId(solutionId, objectClassId);
            response = new ApiRestResponse();
            response.setData(allProperties);
            LOG.info("Properties  fetched");
        } catch (Exception ex) {

            LOG.error("Error while fetching properties  for objectClassId: {}. Error: {}", objectClassId, ex.getMessage());
            response = ResponseUtil.returnApiResponse(null, ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

	
	 @GetMapping("/get-name-type/solution/{solutionId}/object/{objectClassId}")
	 public ResponseEntity<ApiRestResponse> getNameAndTypeBySolutionAndObjectClassId(@PathVariable Integer solutionId, 
	 @PathVariable Integer objectClassId) {

	        ApiRestResponse response = null;
	        try {
	            LOG.info("Getting properties for solutionId : {}", solutionId);
	            final List<PropertyNameTypeDto> allNameTypeBySid = propertyService.getNameAndTypeBySolutionAndObjectClassId(solutionId,objectClassId );
	            response = new ApiRestResponse();
	            response.setData(allNameTypeBySid);
	            LOG.info("Properties  fetched");
	        } catch (Exception ex) {

	            LOG.error("Error while fetching properties  for solutionId: {}. Error: {}", solutionId, ex.getMessage());
	            response = ResponseUtil.returnApiResponse(null, ex.getMessage());
	        }

	        return new ResponseEntity<>(response,
	                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	    }
	
    @GetMapping("/all/{solutionId}")
    public ResponseEntity<ApiRestResponse>  findAllPropertiesBySolutionId(@PathVariable Integer solutionId) {

        ApiRestResponse response = null;
        try {
            LOG.info("Getting properties for solutionId : {}", solutionId);
            final List<Property> allpropertyBySid = propertyService.getAllPropertiesBySolutionId(solutionId );
            response = new ApiRestResponse();
            response.setData(allpropertyBySid);
            LOG.info("Properties  fetched");
        } catch (Exception ex) {

            LOG.error("Error while fetching properties  for solutionId: {}. Error: {}", solutionId, ex.getMessage());
            response = ResponseUtil.returnApiResponse(null, ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<ApiRestResponse> updateProperty(@RequestBody List<Property> propertyList) {

        ApiRestResponse response = null;
        try {
            LOG.info("Updating the properties  for Id: {}", propertyList);
            final Boolean updatedProperty = propertyService.updateProperty(propertyList);
            response = new ApiRestResponse();
            response.setData(updatedProperty);
            LOG.info("Properties updated");
        } catch (Exception ex) {

            LOG.error("Error while updating the Properties. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(null, ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiRestResponse> deleteProperty(@RequestBody List<Integer> propertyIds,Integer solutionId, Integer objectClassId) {

        ApiRestResponse response = null;
        try {
            LOG.info("Updating the properties for Ids: {}",propertyIds);
            final List<Property> deleteProperty = propertyService.deleteProperty(propertyIds,solutionId,objectClassId);
            response = new ApiRestResponse();
            response.setData(deleteProperty);
            LOG.info("Properties  updated");
        } catch (Exception ex) {

            LOG.error("Error while deleting the Properties. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(null, ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
}
