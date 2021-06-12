package it.besolution.controller.workflow;

import it.besolution.model.workflow.WorkFlowRoles;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.workflow.WorkFlowRoleService;
import it.besolution.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow/")
public class WorkFlowRolesController {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowRolesController.class);

    @Autowired
    private WorkFlowRoleService workFlowRoleService;

    @GetMapping("/all/workflow/{workFlowId}")
    public ResponseEntity<ApiRestResponse> getAllRolesForWorkFlow(@PathVariable Integer workFlowId) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Fetching all roles for Workflow Id: {}", workFlowId);
            List<WorkFlowRoles> allRoles = workFlowRoleService.getAllWorkFlowRoles(workFlowId);
            resp = new ApiRestResponse();
            resp.setData(allRoles);
            LOG.info("Fetching roles complete. Total Count: {}", allRoles.size());
        } catch (Exception e) {

            LOG.error("Error while fetching Roles for Workflow Id: {}", workFlowId);
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PostMapping("/save/workflow/{workFlowId}")
    public ResponseEntity<ApiRestResponse> saveAllRoles(@RequestBody List<WorkFlowRoles> rolesList,
                                                            @PathVariable Integer workFlowId) {
        ApiRestResponse resp = null;

        try {
            LOG.info("Saving roles for Workflow Id: {}", workFlowId);
            List<WorkFlowRoles> workFlowRoles = workFlowRoleService.saveWorkFlowRoles(rolesList, workFlowId);
            resp = new ApiRestResponse();
            resp.setData(workFlowRoles);
            LOG.info("Saved workflow master. Total Count after saving: {}", workFlowRoles.size());
        } catch (Exception e) {

            LOG.error("Error while saving roles. Workflow Id: {}", workFlowId);
            LOG.error("Error: {}", e.getMessage());
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
}
