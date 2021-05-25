package it.besolution.repository.workflow;

import it.besolution.mapper.workflow.WorkFlowMasterMapper;
import it.besolution.model.workflow.WorkFlowMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class WorkFlowMasterRepository {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowMasterRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<WorkFlowMaster> saveWorkFlow(WorkFlowMaster workFlowMaster) throws Exception {

        String sql = "INSERT INTO workflow_master (solution_id, name, prefix, description, last_updated) " +
                "VALUES (?, ?, ?, ?, ?)";

        LOG.info("Query: {}", sql);
        LOG.info("Params: {}", workFlowMaster.toString());

        try {
            jdbcTemplate.update(sql, workFlowMaster.getSolutionId(), workFlowMaster.getName()
                    , workFlowMaster.getPrefix(), workFlowMaster.getDescription(), workFlowMaster.getLastUpdated());

            final Integer id = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
            workFlowMaster.setId(id);

            LOG.info("Workflow master object saving complete with Id: {}", workFlowMaster.getId());
            return getWorkFlow(workFlowMaster.getSolutionId(), null);

        } catch (Exception ex) {
            LOG.error("Cannot save the workflow master object with Name: {}", workFlowMaster.getName());
            throw new Exception(ex.getMessage());
        }
    }

    public List<WorkFlowMaster> getWorkFlow(Integer solutionId, Integer workFlowId) throws Exception {

        String sql = "SELECT * FROM workflow_master WHERE solution_id = " + solutionId;
        if (workFlowId != null && workFlowId > 0) {
            sql = sql.concat(" AND id = " + workFlowId);
        }

        try {
            return jdbcTemplate.query(sql, new WorkFlowMasterMapper());
        } catch (Exception ex) {
            LOG.error("Error while fetching workflow master records. Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public WorkFlowMaster updateWorkFlow(WorkFlowMaster workFlowMaster) throws Exception {

        String sql = "UPDATE workflow_master SET name = ?, prefix = ?, description = ?" +
                ", last_updated = ? WHERE id = ?";

        LOG.info("Query: {}", sql);
        LOG.info("Params: {}", workFlowMaster.toString());

        try {
            jdbcTemplate.update(sql, workFlowMaster.getName(), workFlowMaster.getPrefix()
                    , workFlowMaster.getDescription(), new Date());
            return workFlowMaster;
        } catch (Exception ex) {
            LOG.error("Error while updating workflow master records for Id: {}. Error: {}", workFlowMaster.getId()
                    , ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public void deleteWorkFlow() {

    }
}
