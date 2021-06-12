package it.besolution.controller.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import it.besolution.model.workflow.WorkFlowAdvanced;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.workflow.WorkFlowAdvancedService;
import it.besolution.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/workflow/advanced")
public class WorkFlowAdvancedController {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowAdvancedController.class);

    @Autowired
    private WorkFlowAdvancedService workFlowAdvancedService;

    @PostMapping(value = "/save-jar-properties")
    public ResponseEntity<ApiRestResponse> saveJarForWorkFlow(@RequestBody WorkFlowAdvanced req,
          @RequestParam(value = "isUpdate", required = false, defaultValue = "false") Boolean isUpdate) {

        ApiRestResponse resp = null;

        if (req != null) {
            try {
                LOG.info( isUpdate
                        ? "Updating"
                        : "Saving" + " property type {} for WF Id: {}", req.getJarType(), req.getWorkFlowId());
                WorkFlowAdvanced ret = workFlowAdvancedService
                        .saveJarFileAndItsProperties(req, req.getWorkFlowId(), isUpdate);
                LOG.info("Saved property type {} for WF Id: {} with Id: {}",
                        req.getJarType(), req.getWorkFlowId(), ret.getId());
                resp = new ApiRestResponse();
                resp.setData(ret);
            } catch (Exception e) {
                resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
            }
        }

        return new ResponseEntity<>(resp, resp != null && resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @GetMapping("/all/workflow/{workFlowId}")
    public ResponseEntity<ApiRestResponse> getAllJarDetailsForWorkFlow(@PathVariable Integer workFlowId) {

        ApiRestResponse resp = null;

        try {
            LOG.info("Fetching all property records for WF Id: {}", workFlowId);
            List<WorkFlowAdvanced> allProps = workFlowAdvancedService.getAllJarPropertiesRelatedToWorkFlow(workFlowId);
            LOG.info("Fetched all property records for WF Id: {}", workFlowId);
            LOG.info("Total Size: {}", allProps.size());
            resp = new ApiRestResponse();
            resp.setData(allProps);
        } catch (Exception e) {
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }
        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
}
