package it.besolution.controller.workflow;

import it.besolution.model.workflow.WorkFlowAdvancedSettings;
import it.besolution.rest.ApiRestResponse;
import it.besolution.service.workflow.WorkFlowAdvancedSettingsService;
import it.besolution.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflow/advanced-setting")
public class WorkFlowAdvancedSettingsController {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowAdvancedSettingsController.class);

    @Autowired
    private WorkFlowAdvancedSettingsService service;

    @GetMapping("/all/workflow/{workFlowId}/advance/{advanceId}")
    public ResponseEntity<ApiRestResponse> getAllSettings(@PathVariable Integer workFlowId,
                                                          @PathVariable Integer advanceId) {

        ApiRestResponse resp = null;
        try {
            LOG.info("Getting all settings for WFA Id: {}", advanceId);
            List<WorkFlowAdvancedSettings> allSettings = service.getAllSettings(workFlowId, advanceId);
            LOG.info("Getting all settings for WFA Id: {}, total: {}", advanceId, allSettings.size());
            resp = new ApiRestResponse();
            resp.setData(allSettings);
        } catch (Exception e) {
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PostMapping("/save/workflow/{workFlowId}/advance/{advanceId}")
    public ResponseEntity<ApiRestResponse> saveAllSettings(@RequestBody List<WorkFlowAdvancedSettings> settings,
                                                          @PathVariable Integer workFlowId,
                                                          @PathVariable Integer advanceId) {

        ApiRestResponse resp = null;
        try {
            LOG.info("Saving settings for WFA Id: {}, Total: {}", advanceId, settings.size());
            List<WorkFlowAdvancedSettings> allSettings = service.saveAll(settings, workFlowId, advanceId);
            LOG.info("Saved settings for WFA Id: {}, Total: {}", advanceId, settings.size());
            LOG.info("Total Count: {}", allSettings.size());
            resp = new ApiRestResponse();
            resp.setData(allSettings);
        } catch (Exception e) {
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiRestResponse> updateSettings(@RequestBody WorkFlowAdvancedSettings setting) {

        ApiRestResponse resp = null;
        try {
            LOG.info("Updating settings for WFA Id: {}, Id: {}", setting.getWorkFlowAdvancedId(), setting.getId());
            WorkFlowAdvancedSettings updatedSetting = service.updateWorkFlowAdvancedSettings(setting);
            LOG.info("Updated settings for WFA Id: {}, Id: {}", setting.getWorkFlowAdvancedId(), setting.getId());
            resp = new ApiRestResponse();
            resp.setData(updatedSetting);
        } catch (Exception e) {
            resp = ResponseUtil.returnApiResponse(resp, e.getMessage());
        }

        return new ResponseEntity<>(resp, resp.getIsError() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }
}
