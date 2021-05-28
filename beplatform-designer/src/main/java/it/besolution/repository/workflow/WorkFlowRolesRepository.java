package it.besolution.repository.workflow;

import it.besolution.mapper.workflow.WorkFlowRoleMapper;
import it.besolution.model.workflow.WorkFlowRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class WorkFlowRolesRepository {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowRolesRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<WorkFlowRoles> getAllWorkFlowRoles(Integer workFlowId) throws Exception {

        String sql = "SELECT wr.*, wp.property_name FROM workflow_role wr " +
        "inner join WORKFLOW_PROPERTY wp on wr.workflow_property_id = wp.id " +
        "WHERE wr.workflow_id = " + workFlowId;

        LOG.info("SQL: {}", sql);

        try {
            LOG.info("Fetching all roles for workflow Id: {}", workFlowId);
            List<WorkFlowRoles> roles = jdbcTemplate.query(sql, new WorkFlowRoleMapper());
            LOG.info("Fetched all properties for workflow Id: {}. Total Count: {}", workFlowId, roles.size());
            return roles;
        } catch (Exception ex) {
            LOG.error("Error while fetching roles: Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public List<WorkFlowRoles> saveWorkFlowRoles(List<WorkFlowRoles> roles, Integer workFlowId) throws Exception {

        String sql = "INSERT INTO workflow_role (workflow_id, role_name, workflow_property_id, is_dynamic" +
                ", is_created) VALUES (?, ?, ?, ?, ?)";
        List<Object[]> args = new ArrayList<>();
        LOG.info("SQL: {}", sql);
        roles.forEach(role -> {

            Object[] obj = {role.getWorkFlowId(), role.getRoleName(), role.getWorkFlowPropertyId()
                    , role.getIsDynamic() ? 1 : 0, role.getIsCreated() ? 1 : 0};
            args.add(obj);
            LOG.info("Param: {}", Arrays.toString(obj));
        });

        try {
            LOG.info("Saving all roles for workflow Id: {}", workFlowId);
            int[] totalSaved = jdbcTemplate.batchUpdate(sql, args);
            LOG.info("Saved all properties for workflow Id: {}. Total Count: {}"
                    , workFlowId, Arrays.toString(totalSaved));
            return getAllWorkFlowRoles(workFlowId);
        } catch (Exception ex) {
            LOG.error("Error while saving roles: Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }
}
