package it.besolution.repository.workflow;

import it.besolution.mapper.workflow.WorkFlowAdvancedSettingsMapper;
import it.besolution.model.workflow.WorkFlowAdvancedSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkFlowAdvancedSettingsRepository {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowAdvancedSettingsRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<WorkFlowAdvancedSettings> getAllAdvancedSettings(Integer workFlowId,
                                                                 Integer workFlowAdvancedId) throws Exception {

        String sql = "SELECT * FROM workflow_advanced_settings WHERE workflow_id = " + workFlowId
                + " AND workflow_advanced_id = " + workFlowAdvancedId;

        LOG.info("SQL: {}", sql);

        try {

            LOG.info("Fetching all settings for Workflow advanced id: " + workFlowAdvancedId);
            List<WorkFlowAdvancedSettings> settings = jdbcTemplate.query(sql, new WorkFlowAdvancedSettingsMapper());
            LOG.info("Fetched all settings for Workflow advanced id: " + workFlowAdvancedId);
            LOG.info("Total settings fetched: {}", settings.size());
            return settings;
        } catch (Exception ex) {
            LOG.error("Error fetching settings for workflow advanced id: {}", workFlowAdvancedId);
            LOG.error("Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public List<WorkFlowAdvancedSettings> saveSettings(List<WorkFlowAdvancedSettings> settings, Integer workFlowId,
                                                         Integer workFlowAdvancedId) throws Exception {

        String sql = "INSERT INTO workflow_advanced_settings (workflow_id, workflow_advanced_id" +
                ", setting_name, setting_value) VALUES (?, ?, ?, ?)";
        LOG.info("SQL: {}", sql);

        List<Object[]> args = new ArrayList<>();
        for (WorkFlowAdvancedSettings setting : settings) {

            Object[] obj = {setting.getWorkFlowId(), setting.getWorkFlowAdvancedId(),
                    setting.getSettingName(), setting.getSettingValue()};
            LOG.info("Args: {}", args);
            args.add(obj);
        }

        try {

            LOG.info("Saving settings for Workflow advanced id: {}", workFlowAdvancedId);
            int[] saved  = jdbcTemplate.batchUpdate(sql, args);
            LOG.info("Saving settings for Workflow advanced id: {}. Total saved " + workFlowAdvancedId);
            return getAllAdvancedSettings(workFlowId, workFlowAdvancedId);
        } catch (Exception ex) {
            LOG.error("Error Saving settings for workflow advanced id: {}", workFlowAdvancedId);
            LOG.error("Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public WorkFlowAdvancedSettings updateSettings(WorkFlowAdvancedSettings setting) throws Exception {

        String sql = "UPDATE workflow_advanced_settings SET setting_name = ?, setting_value = ? " +
                "WHERE id = ? AND workflow_id = ? AND workflow_advanced_id = ?";
        LOG.info("SQL: {}", sql);
        LOG.info("Params: {}", setting);

        try {

            LOG.info("Updating settings for setting Id: {}", setting.getId());
            jdbcTemplate.update(sql, setting.getSettingName(), setting.getSettingValue(), setting.getId(),
                    setting.getWorkFlowId(), setting.getWorkFlowAdvancedId());
            LOG.info("Updated settings for setting Id: {}", setting.getId());
            return setting;
        } catch (Exception ex) {
            LOG.error("Error Updating settings for settings id: {}", setting.getId());
            LOG.error("Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }
}
