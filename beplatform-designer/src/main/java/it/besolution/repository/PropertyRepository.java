package it.besolution.repository;

import com.google.common.base.Joiner;

import it.besolution.dto.PropertyNameTypeDto;
import it.besolution.mapper.PropertyDtoMapper;
import it.besolution.mapper.PropertyMapper;
import it.besolution.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class PropertyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

	public List<Property> getAllPropertiesBySolutionAndObjectClassId(Integer solutionId, Integer objectClassId) {

        String sql = "SELECT * FROM PROPERTY P JOIN OBJECT_CLASS O WHERE  P. OBJECT_CLASS_ID=O.ID AND P.SOLUTION_ID = " + solutionId + " AND P.OBJECT_CLASS_ID = "
                + objectClassId;

        LOG.info("Query: {}", sql);
        LOG.info("Solution Id: {}, Object Class Id: {}", solutionId, objectClassId);

        try {
            return jdbcTemplate.query(sql, new PropertyMapper());
        } catch (Exception ex) {
            LOG.error("Error: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }
	public List<PropertyNameTypeDto> getNameAndTypeBySolutionAndObjectClassId(Integer solutionId,Integer objectClassId) {

        String sql = "SELECT * FROM PROPERTY WHERE SOLUTION_ID = " + solutionId+ " AND OBJECT_CLASS_ID ="+objectClassId ; 
        LOG.info("Query: {}", sql);
        LOG.info("Solution Id: {}", solutionId);

        try {
            return jdbcTemplate.query(sql, new PropertyDtoMapper());
           
        } catch (Exception ex) {
            LOG.error("Error: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }
    public List<Property> getAllPropertiesBySolutionId(Integer solutionId) {

        String sql = "SELECT * FROM PROPERTY WHERE SOLUTION_ID = " + solutionId ; 
        LOG.info("Query: {}", sql);
        LOG.info("Solution Id: {}", solutionId);

        try {
            return jdbcTemplate.query(sql, new PropertyMapper());
        } catch (Exception ex) {
            LOG.error("Error: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Property> deleteProperty(List<Integer> propertyIds, Integer solutionId, Integer objectClassId) {

        String ids = Joiner.on(", ").join(propertyIds);
        String sql = "DELETE FROM PROPERTY WHERE SOLUTION_ID = " + solutionId + " AND OBJECT_CLASS_ID = "
                + objectClassId + " AND ID IN (" + ids + ")";

        LOG.info("Query: {}", sql);
        LOG.info("Solution Id: {}, Object Class Id: {}", solutionId, objectClassId);

        try {
            final int deletedRows = jdbcTemplate.update(sql);
            LOG.info("Deleted {} rows", deletedRows);
            return getAllPropertiesBySolutionAndObjectClassId(solutionId, objectClassId);
        } catch (Exception ex) {
            LOG.error("Cannot delele. Error: {}", ex.getMessage());
            return null;
        }
    }

    public Boolean updatePropertyList(List<Property> propertyList) {

        String sql = "UPDATE PROPERTY SET PROPERTY_NAME = '__PROP_NAME__', PROPERTY_TYPE = '__PROP_TYPE__'" +
                ", LABEL = '__LABEL__', CONSTRAINT_KEY = '__CNS_KEY_', CONSTRAINT_VALUE = '__CNS_VAL__'" +
                ", IS_NULL = __IN__, DEFAULT_VALUE = '__DFV__', LAST_UPDATED = CURRENT_TIMESTAMP() WHERE " +
                "SOLUTION_ID = __SOL_ID__ AND OBJECT_CLASS_ID = __OBJ_ID__ AND ID = __ID__";

        List<String> sqlList = new ArrayList<>();
        for (Property property : propertyList) {

            String modSql = sql.replace("__ID__", property.getId().toString())
                    .replace("__OBJ_ID__", property.getObjectClassId().toString())
                    .replace("__SOL_ID__", property.getSolutionId().toString())
                    .replace("__PROP_NAME__", property.getPropertyName())
                    .replace("__PROP_TYPE__", property.getPropertyType())
                    .replace("__LABEL__", property.getLabel())
                    .replace("__CNS_KEY_", property.getConstraintKey())
                    .replace("__CNS_VAL_", property.getConstraintValue())
                    .replace("__IN__", property.getPropertyIsNull().toString())
                    .replace("__DFV__", property.getDefaultValue());
            LOG.info("SQL: {}", modSql);
            sqlList.add(modSql);
        }

        try {
            final int[] ints = jdbcTemplate.batchUpdate(sqlList.toArray(new String[0]));
            LOG.info("Result: {}", Arrays.toString(ints));
            return Boolean.TRUE;
        } catch (Exception ex) {
            LOG.error("Cannot update: {}", ex.getMessage());
            return Boolean.FALSE;
        }
    }

	public Property save(Property property) throws Exception {
		  String sql = "INSERT INTO PROPERTY  (ID, SOLUTION_ID, OBJECT_CLASS_ID , PROPERTY_NAME, PROPERTY_TYPE, LABEL" +
	                ", CONSTRAINT_KEY, CONSTRAINT_VALUE, IS_NULL, DEFAULT_VALUE, LAST_UPDATED )"+
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        LOG.info("Query: {}", sql);
	        LOG.info("Params: {}", property.toString());

	        try {

	            int val = jdbcTemplate.update(sql, property.getId(),property.getSolutionId(), property.getObjectClassId()
	                    , property.getPropertyName(),property.getPropertyType(), property.getLabel()
	                    , property.getConstraintKey(), property.getConstraintValue(), property.getPropertyIsNull(),property.getDefaultValue()
	                    , property.getLastUpdated() );

	            final Integer id = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
	            property.setId(id);

	            LOG.info("Property saving  with Id: {} ", property.getId());

	        } catch (Exception ex) {
	            LOG.error("Cannot save the Property with Id: {}", property.getId());
	            throw new Exception(ex);
	        }
	        return property;
	}
}
