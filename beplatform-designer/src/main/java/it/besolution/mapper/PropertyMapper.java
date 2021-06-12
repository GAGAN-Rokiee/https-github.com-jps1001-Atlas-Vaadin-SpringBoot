package it.besolution.mapper;

import it.besolution.model.Property;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyMapper implements RowMapper<Property> {

    @Override
    public Property mapRow(ResultSet rs, int i) throws SQLException {

        Property property = new Property();
        property.setId(rs.getInt("id"));
        property.setSolutionId(rs.getInt("solution_id"));
        property.setObjectClassId(rs.getInt("object_class_id"));
        property.setPropertyName(rs.getString("property_name"));
        property.setPropertyType(rs.getString("property_type"));
        property.setLabel(rs.getString("label"));
        property.setConstraintKey(rs.getString("constraint_key"));
        property.setConstraintValue(rs.getString("constraint_value"));
        property.setPropertyIsNull(rs.getInt("is_null") == 1);
        property.setDefaultValue(rs.getString("default_value"));
        property.setLastUpdated(rs.getDate("last_updated"));
        property.setObjectClassName(rs.getString("class_name"));

        return property;
    }
}
