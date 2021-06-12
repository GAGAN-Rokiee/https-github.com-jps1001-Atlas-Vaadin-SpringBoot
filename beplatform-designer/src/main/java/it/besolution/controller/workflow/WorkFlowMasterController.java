package it.besolution.controller.workflow;

import it.besolution.dto.WorkFlowDto;
import it.besolution.model.workflow.WorkFlowMaster;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.workflow.WorkFlowMasterService;
import it.besolution.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow/info")
public class WorkFlowMasterController {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowMasterController.class);

    @Autowired
    private WorkFlowMasterService workFlowMasterService;

    @PostMapping("/save")
    public ResponseEntity<ApiRestResponse> saveWorkFlow(@RequestBody WorkFlowMaster wfm) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Saving workflow master. Name: {}", wfm.getName());
            List<WorkFlowMaster> workFlowMasters = workFlowMasterService.saveWorkFlowMaster(wfm);
            resp = new ApiRestResponse();
            resp.setData(workFlowMasters);
            LOG.info("Saved workflow master. Name: {}", wfm.getName());
        } catch (Exception e) {

            LOG.error("Error while saving Workflow master. Name: {}", wfm.getName());
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @GetMapping("/all/solution/{solutionId}")
    public ResponseEntity<ApiRestResponse> getAllWorkFlows(@PathVariable Integer solutionId) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Fetching all workflows under. Solution Id: {}", solutionId);
            List<WorkFlowMaster> allWorkFlows = workFlowMasterService.getAllWorkFlows(solutionId, null);
            resp = new ApiRestResponse();
            resp.setData(allWorkFlows);
            LOG.info("Fetching workflow complete. Total Count: {}", allWorkFlows.size());
        } catch (Exception e) {

            LOG.error("Error while fetching Workflows for Solution Id: {}", solutionId);
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @GetMapping("/{workFlowId}/solution/{solutionId}")
    public ResponseEntity<ApiRestResponse> getWorkFlowDetails(@PathVariable Integer workFlowId,
                                                              @PathVariable Integer solutionId) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Fetching workflows with Solution Id: {} and Workflow Id: {}", solutionId, workFlowId);
            WorkFlowDto workFlowById = workFlowMasterService.getWorkFlowById(solutionId, workFlowId);
            resp = new ApiRestResponse();
            resp.setData(workFlowById);
            LOG.info("Fetched workflow with Solution Id: {} and Workflow Id: {}", solutionId, workFlowId);
        } catch (Exception e) {

            LOG.error("Error fetching Workflow for Solution Id: {} and Workflow Id: {}", solutionId, workFlowId);
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiRestResponse> updateWorkFlowDetails(@RequestBody WorkFlowMaster wfm) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Updating workflow for Id: {}", wfm.getId());
            WorkFlowMaster workFlowMaster = workFlowMasterService.updateWorkFlowMaster(wfm);
            resp = new ApiRestResponse();
            resp.setData(workFlowMaster);
            LOG.info("Updated workflow for Id: {}", wfm.getId());
        } catch (Exception e) {

            LOG.info("Error updating workflow for Id: {}", wfm.getId());
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @DeleteMapping("/delete/{workFlowId}")
    public void deleteWorkFlow(@PathVariable Integer workFlowId) {

    }
}
