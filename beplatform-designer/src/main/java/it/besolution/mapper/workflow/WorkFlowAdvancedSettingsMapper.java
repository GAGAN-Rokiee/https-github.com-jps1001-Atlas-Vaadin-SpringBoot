package it.besolution.mapper.workflow;

import it.besolution.model.workflow.WorkFlowAdvancedSettings;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkFlowAdvancedSettingsMapper implements RowMapper<WorkFlowAdvancedSettings> {

    @Override
    public WorkFlowAdvancedSettings mapRow(ResultSet rs, int i) throws SQLException {

        WorkFlowAdvancedSettings setting = new WorkFlowAdvancedSettings();
        setting.setId(rs.getInt("id"));
        setting.setWorkFlowId(rs.getInt("workflow_id"));
        setting.setSettingName(rs.getString("setting_name"));
        setting.setSettingValue(rs.getString("setting_value"));

        return setting;
    }
}
