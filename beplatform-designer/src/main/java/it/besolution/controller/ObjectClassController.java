package it.besolution.controller;

import it.besolution.dto.ObjectClassDto;
import it.besolution.model.ObjectClass;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.ObjectClassService;
import it.besolution.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/object-class")
public class ObjectClassController {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectClassController.class);

    @Autowired
    private ObjectClassService objectClassService;

    @GetMapping("/all/{solutionId}")
    public ResponseEntity<ApiRestResponse> getAllObjectClasses(@PathVariable Integer solutionId) {

        ApiRestResponse response = null;
        try {
            LOG.info("Getting all object classes");
            final List<ObjectClassDto> all = objectClassService.findAllBySolutionId(solutionId);
            response = new ApiRestResponse();
            response.setData(all);
            LOG.info("All object classes fetched");
        } catch (Exception ex) {

            LOG.error("Error while fetching all object classes. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @GetMapping("/{objectClassId}/solution/{solutionId}")
    public ResponseEntity<ApiRestResponse> getAllObjectClasses(@PathVariable Integer objectClassId,
                                                               @PathVariable Integer solutionId) {

        ApiRestResponse response = null;
        try {
            LOG.info("Getting object classes for Id: {}", objectClassId);
            final ObjectClass objectClass = objectClassService
                    .findBySolutionIdAndObjectClassId(solutionId, objectClassId);
            response = new ApiRestResponse();
            response.setData(objectClass);
            LOG.info("Object classes fetched");
        } catch (Exception ex) {

            LOG.error("Error while fetching Object classes for Id: {}. Error: {}", objectClassId, ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PostMapping("/save/solution/{solutionId}")
    public ResponseEntity<ApiRestResponse> saveObjectClass(@RequestBody ObjectClass objectClass,
                                                           @PathVariable Integer solutionId) {

        ApiRestResponse response = null;
        try {
            LOG.info("Saving a object classes");
            final ObjectClass savedObject = objectClassService.save(objectClass, solutionId);
            response = new ApiRestResponse();
            response.setData(savedObject);
            LOG.info("Saving the object classes complete");
        } catch (Exception ex) {

            LOG.error("Error while saving the object class. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiRestResponse> saveObjectClass(@RequestBody ObjectClass objectClass) {

        ApiRestResponse response = null;
        try {
            LOG.info("Updating the object classes for Id: {}", objectClass.getId());
            final ObjectClass updatedObject = objectClassService.updateObjectClass(objectClass);
            response = new ApiRestResponse();
            response.setData(updatedObject);
            LOG.info("Object classes updated");
        } catch (Exception ex) {

            LOG.error("Error while updating the object class. Error: {}", ex.getMessage());
            response = ResponseUtil.returnApiResponse(new ApiRestResponse(), ex.getMessage());
        }

        return new ResponseEntity<>(response,
                response.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
}
