package it.besolution.controller.workflow;

import it.besolution.model.workflow.WorkFlowProperty;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.workflow.WorkFlowPropertyService;
import it.besolution.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow/property")
public class WorkFlowPropertyController {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowPropertyController.class);

    @Autowired
    private WorkFlowPropertyService workFlowPropertyService;

    @GetMapping("/all/workflow/{workFlowId}")
    public ResponseEntity<ApiRestResponse> getAllPropertyForWorkFlow(@PathVariable Integer workFlowId) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Getting all properties for workflow id: {}", workFlowId);
            List<WorkFlowProperty> allProperties = workFlowPropertyService
                    .getAllWorkFlowPropertiesByWorkFlowId(workFlowId);
            resp = new ApiRestResponse();
            resp.setData(allProperties);
            LOG.info("Fetched all properties for workflow id: {}", workFlowId);
            LOG.info("Total properties: {}", allProperties.size());
        } catch (Exception e) {

            LOG.error("Error while fetching properties for Workflow Id: {}", workFlowId);
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PostMapping("/save/workflow/{workFlowId}")
    public ResponseEntity<ApiRestResponse> save(@RequestBody List<WorkFlowProperty> properties,
                                                @PathVariable Integer workFlowId) {

        ApiRestResponse resp = null;
        System.out.println("dfddssdfds>>> "+workFlowId);

        try {
            LOG.info("Saving all properties for workflow id: {}", workFlowId);
            List<WorkFlowProperty> allProperties = workFlowPropertyService
                    .saveWorkFlowProperties(properties, workFlowId);
            resp = new ApiRestResponse();
            resp.setData(allProperties);
            LOG.info("Saved all properties for workflow id: {}", workFlowId);
        } catch (Exception e) {

            LOG.error("Error while saving properties for Workflow Id: {}", workFlowId);
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @DeleteMapping("/delete/{propertyId}/workflow/{workFlowId}")
    public ResponseEntity<ApiRestResponse> deleteProperty(@PathVariable Integer propertyId,
                                                          @PathVariable Integer workFlowId) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Deleting property for workflow id: {} and property id: {}", workFlowId, propertyId);
            List<WorkFlowProperty> allProperties = workFlowPropertyService
                    .deleteWorkFlowProperty(propertyId, workFlowId);
            resp = new ApiRestResponse();
            resp.setData(allProperties);
            LOG.info("Deleted properties for workflow id: {}", workFlowId);
            LOG.info("Total available properties: {}", allProperties.size());
        } catch (Exception e) {

            LOG.error("Error while deleting properties for Workflow Id: {} with Property Id: {}"
                    , workFlowId, propertyId);
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
}
