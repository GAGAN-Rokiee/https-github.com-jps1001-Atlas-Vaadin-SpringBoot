package it.besolution.mapper.workflow;

import it.besolution.model.workflow.WorkFlowProperty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkFlowPropertyMapper implements RowMapper<WorkFlowProperty> {

    @Override
    public WorkFlowProperty mapRow(ResultSet rs, int i) throws SQLException {

        WorkFlowProperty wfp = new WorkFlowProperty();
        wfp.setId(rs.getInt("id"));
        wfp.setWorkFlowId(rs.getInt("workflow_id"));
        wfp.setPropertyName(rs.getString("property_name"));
        wfp.setPropertyType(rs.getString("property_type"));
        return wfp;
    }
}
