package it.besolution.mapper;

import it.besolution.dto.ObjectClassDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectClassDtoMapper implements RowMapper<ObjectClassDto> {

    @Override
    public ObjectClassDto mapRow(ResultSet resultSet, int i) throws SQLException {

        ObjectClassDto dto = new ObjectClassDto();
        dto.setId(resultSet.getInt("id"));
        dto.setClassName(resultSet.getString("class_name"));
        dto.setEntityName(resultSet.getString("entity_name"));
        dto.setCounterName(resultSet.getString("counter_name"));
        dto.setSecurityEnabled(resultSet.getInt("security_enabled") == 1);
        dto.setSystemClass(resultSet.getInt("system_class") == 1);

        return dto;
    }
}
