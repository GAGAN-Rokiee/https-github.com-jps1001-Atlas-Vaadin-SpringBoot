package it.besolution.repository.workflow;

import it.besolution.mapper.workflow.WorkFlowPropertyMapper;
import it.besolution.model.workflow.WorkFlowProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class WorkFlowPropertyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowMasterRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<WorkFlowProperty> getAllProperties(Integer workFlowId) throws Exception {

        String sql = "SELECT * FROM workflow_property WHERE workflow_id = " + workFlowId;

        LOG.info("SQL: {}", sql);

        try {
            LOG.info("Fetching all properties for workflow Id: {}", workFlowId);
            List<WorkFlowProperty> properties = jdbcTemplate.query(sql, new WorkFlowPropertyMapper());
            LOG.info("Fetched all properties for workflow Id: {}. Total Count: {}", workFlowId, properties.size());
            return properties;
        } catch (Exception ex) {
            LOG.error("Error while fetching objects: Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public List<WorkFlowProperty> deleteProperty(Integer propertyId, Integer workFlowId) throws Exception {

        String sql = "DELETE FROM workflow_property WHERE id = " + propertyId + " AND workflow_id = " + workFlowId;

        LOG.info("SQL: {}", sql);

        try {
            LOG.info("Deleting property for Id: {}", propertyId);
            jdbcTemplate.update(sql);
            LOG.info("Deleted property for Id: {}", propertyId);
            return getAllProperties(workFlowId);
        } catch (Exception ex) {
            LOG.error("Error while fetching objects: Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }

    public List<WorkFlowProperty> saveWorkFlowProperty(List<WorkFlowProperty> properties, Integer workFlowId)
            throws Exception {

        String sql = "INSERT INTO workflow_property (workflow_id, property_name, property_type) VALUES (?, ?, ?)";
        LOG.info("SQL: {}", sql);

        List<Object[]> args = new ArrayList<>();

        for (WorkFlowProperty property : properties) {
            Object[] obj
                    = new Object[] {property.getWorkFlowId(), property.getPropertyName(), property.getPropertyType()};
            LOG.info("Params: {}", Arrays.toString(obj));
            args.add(obj);
        }

        try {
            LOG.info("Inserting Properties. Total: {}", args.size());
            int[] count = jdbcTemplate.batchUpdate(sql, args);
            LOG.info("Inserting Properties Done. Total: {}", Arrays.toString(count));
            return getAllProperties(workFlowId);
        } catch (Exception ex) {
            LOG.error("Cannot insert. Error: {}", ex.getMessage());
            throw new Exception(ex.getMessage());
        }
    }
}
