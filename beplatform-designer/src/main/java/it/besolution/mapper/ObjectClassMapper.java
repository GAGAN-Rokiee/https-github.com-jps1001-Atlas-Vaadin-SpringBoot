package it.besolution.mapper;

import it.besolution.model.ObjectClass;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectClassMapper implements RowMapper<ObjectClass> {


    @Override
    public ObjectClass mapRow(ResultSet resultSet, int i) throws SQLException {

        ObjectClass objClass = new ObjectClass();
        objClass.setId(resultSet.getInt("id"));
        objClass.setUniqueId(resultSet.getString("unique_id"));
        objClass.setClassName(resultSet.getString("class_name"));
        objClass.setDescription(resultSet.getString("description"));
        objClass.setBaseType(resultSet.getInt("base_type"));
        objClass.setClassType(resultSet.getInt("class_type"));
        objClass.setBasePath(resultSet.getString("base_path"));
        objClass.setEntityName(resultSet.getString("entity_name"));
        objClass.setSecurityEnabled(resultSet.getInt("security_enabled") == 1);
        objClass.setCryptoContent(resultSet.getInt("crypto_content") == 1);
        objClass.setCounterName(resultSet.getString("counter_name"));
        objClass.setStorageType(resultSet.getInt("storage_type"));
        objClass.setSystemClass(resultSet.getInt("system_class") == 1);
        objClass.setDefaultWorkflow(resultSet.getString("default_workflow"));
        objClass.setLastUpdated(resultSet.getDate("last_updated"));
        objClass.setSolutionId(resultSet.getInt("solution_id"));

        return objClass;
    }
}
