package it.besolution.mapper;

import it.besolution.dto.PropertyNameTypeDto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyDtoMapper implements RowMapper<PropertyNameTypeDto> {

    @Override
    public PropertyNameTypeDto mapRow(ResultSet resultSet, int i) throws SQLException {

    	PropertyNameTypeDto dto = new PropertyNameTypeDto();
        dto.setId(resultSet.getInt("id"));
        dto.setName(resultSet.getString("Property_name"));
        dto.setType(resultSet.getString("Property_type"));
        return dto;
    }
}
