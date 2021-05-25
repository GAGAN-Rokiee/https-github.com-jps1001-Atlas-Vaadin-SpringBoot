package it.besolution.mapper.workflow;

import it.besolution.model.workflow.WorkFlowRoles;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkFlowRoleMapper implements RowMapper<WorkFlowRoles> {

    @Override
    public WorkFlowRoles mapRow(ResultSet rs, int i) throws SQLException {

        WorkFlowRoles roles = new WorkFlowRoles();
        roles.setId(rs.getInt("id"));
        roles.setWorkFlowId(rs.getInt("workflow_id"));
        roles.setWorkFlowPropertyId(rs.getInt("workflow_property_id"));
        roles.setRoleName(rs.getString("role_name"));
        roles.setIsCreated(rs.getInt("is_created") == 1);
        roles.setIsDynamic(rs.getInt("is_dynamic") == 1);

        return roles;
    }
}
