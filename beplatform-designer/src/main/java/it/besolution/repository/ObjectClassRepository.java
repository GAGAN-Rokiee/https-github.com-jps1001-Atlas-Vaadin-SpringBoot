package it.besolution.repository;

import it.besolution.dto.ObjectClassDto;
import it.besolution.mapper.ObjectClassDtoMapper;
import it.besolution.mapper.ObjectClassMapper;
import it.besolution.model.ObjectClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ObjectClassRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectClassRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ObjectClass save(ObjectClass objectClass) throws Exception {

        String sql = "INSERT INTO object_class (unique_id, class_name, description, base_type, class_type, base_path" +
                ", entity_name, security_enabled, crypto_content, counter_name, storage_type, system_class" +
                ", default_workflow, last_updated, solution_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        LOG.info("Query: {}", sql);
        LOG.info("Params: {}", objectClass.toString());

        try {

            int val = jdbcTemplate.update(sql, objectClass.getUniqueId(), objectClass.getClassName()
                    , objectClass.getDescription(), objectClass.getBaseType(), objectClass.getClassType()
                    , objectClass.getBasePath(), objectClass.getEntityName(), objectClass.getSecurityEnabled()
                    , objectClass.getCryptoContent(), objectClass.getCounterName(), objectClass.getStorageType()
                    , objectClass.getSystemClass(), objectClass.getDefaultWorkflow(), objectClass.getLastUpdated()
                    , objectClass.getSolutionId());

            final Integer id = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
            objectClass.setId(id);

            LOG.info("Object saving complete with Unique Id: {} and Id: {}"
                    , objectClass.getUniqueId(), objectClass.getId());

        } catch (Exception ex) {
            LOG.error("Cannot save the object with unique id: {}", objectClass.getUniqueId());
            throw new Exception(ex);
        }
        return objectClass;
    }

    public List<ObjectClassDto> findAllBySolutionId(Integer solutionId) throws Exception {

        String sql = "SELECT * FROM object_class WHERE solution_id = " + solutionId;
        LOG.info("Query: {}", sql);

        try {
            return jdbcTemplate.query(sql, new ObjectClassDtoMapper());
        } catch (Exception ex) {
            LOG.error("Cannot fetch objects.");
            throw new Exception(ex);
        }
    }

    public ObjectClass findBySolutionIdAndObjectClassId(Integer solutionId, Integer objectClassId) throws Exception {

        String sql = "SELECT * FROM object_class WHERE solution_id = " + solutionId + " AND id = " + objectClassId;
        LOG.info("Query: {}", sql);

        try {
            return jdbcTemplate.queryForObject(sql, new ObjectClassMapper());
        } catch (Exception ex) {
            LOG.error("Cannot fetch objects.");
            throw new Exception(ex);
        }
    }

    public ObjectClass updateObjectClass(ObjectClass objectClass) throws Exception {

        String sql = "UPDATE object_class SET unique_id = ?, class_name = ?, description = ?" +
                ", base_type = ?, class_type = ?, base_path = ?, entity_name = ?, security_enabled = ?" +
                ", crypto_content = ?, counter_name = ?, storage_type = ?, system_class = ?" +
                ", default_workflow = ?, last_updated = ? WHERE solution_id = ? AND id = ?";
        LOG.info("Query: {}", sql);
        LOG.info("Params: {}", objectClass);

        try {
            jdbcTemplate.update(sql, objectClass.getUniqueId(), objectClass.getClassName()
                    , objectClass.getDescription(), objectClass.getBaseType(), objectClass.getClassType()
                    , objectClass.getBasePath(), objectClass.getEntityName(), objectClass.getSecurityEnabled()
                    , objectClass.getCryptoContent(), objectClass.getCounterName(), objectClass.getStorageType()
                    , objectClass.getSystemClass(), objectClass.getDefaultWorkflow(), new Date()
                    , objectClass.getSolutionId(), objectClass.getId());
        } catch (Exception ex) {
            LOG.error("Cannot update object for Solution Id: {} and Unique Id: {}"
                    , objectClass.getSolutionId(), objectClass.getUniqueId());
            throw new Exception(ex);
        }

        return objectClass;
    }
}
