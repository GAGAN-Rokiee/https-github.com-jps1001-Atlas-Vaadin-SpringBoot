package it.besolution.repository.workflow;

import it.besolution.mapper.workflow.WorkFlowAdvancedMapper;
import it.besolution.model.workflow.WorkFlowAdvanced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkFlowAdvancedRepository {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowAdvancedRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public WorkFlowAdvanced saveJarRecord(Integer workFlowId, WorkFlowAdvanced req) throws Exception {

        String sql = "INSERT INTO workflow_advanced (workflow_id, jar_type, jar_class, jar_path) VALUES (?, ?, ?, ?)";

        LOG.info("SQL: {}", sql);
        LOG.info("Params: {}", req);

        try {
            LOG.info("Saving jar type: {} for workflow id: {}", req.getJarType(), workFlowId);
            jdbcTemplate.update(sql, req.getWorkFlowId(), req.getJarType(), req.getJarClass(), req.getJarPath());
            Integer id = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
            LOG.info("Saved jar type: {} for workflow id: {} with Id: {}", req.getJarType(), workFlowId, id);
            req.setId(id);

            return req;
        } catch (Exception ex) {
            LOG.error("Cannot save jar file. Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public WorkFlowAdvanced updateJarRecord(WorkFlowAdvanced req) throws Exception {

        String sql = "UPDATE workflow_advanced SET jar_type = ?, jar_class = ?, jar_path = ? " +
                "WHERE id = ? AND workflow_id = ?";

        LOG.info("SQL: {}", sql);
        LOG.info("Params: {}", req);

        try {
            LOG.info("Updating jar with Id : {} for workflow id: {}", req.getId(), req.getWorkFlowId());
            jdbcTemplate.update(sql,
                    req.getJarType(), req.getJarClass(), req.getJarPath(), req.getId(), req.getWorkFlowId());
            LOG.info("Updated jar type: {} for workflow id: {} with Id: {}"
                    , req.getJarType(), req.getWorkFlowId(), req.getId());
            return req;
        } catch (Exception ex) {
            LOG.error("Cannot update jar file. Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public List<WorkFlowAdvanced> getAllJarForWorkFlow(Integer workFlowId, Integer propertyId) throws Exception {

        String sql = "SELECT * from workflow_advanced WHERE workflow_id = " + workFlowId;
        if (propertyId != null && propertyId > 0) {
            sql = sql + " AND id = " + propertyId;
        }

        LOG.info("SQL: {}", sql);

        try {
            LOG.info("Fetching all jars for workflow id: {}", workFlowId);
            List<WorkFlowAdvanced> allJars = jdbcTemplate.query(sql, new WorkFlowAdvancedMapper());
            LOG.info("Fetched all jars for workflow id: {}", workFlowId);
            LOG.info("Total size: {}", allJars.size());
            return allJars;
        } catch (Exception ex) {
            LOG.error("Cannot fetch jar records for workflow id: {}. Error: {}", workFlowId, ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }
}
