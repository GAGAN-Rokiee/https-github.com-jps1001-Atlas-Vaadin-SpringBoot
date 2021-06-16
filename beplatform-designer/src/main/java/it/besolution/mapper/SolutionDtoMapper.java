package it.besolution.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.besolution.dto.SolutionDto;

public class SolutionDtoMapper implements RowMapper<SolutionDto> {

	@Override
	public SolutionDto mapRow(ResultSet resultSet, int arg1) throws SQLException {
		SolutionDto solution = new SolutionDto();
		solution.setId(resultSet.getInt("id"));
		solution.setTemplateName(resultSet.getString("template_name"));
		solution.setDescription(resultSet.getString("description"));
		solution.setLastUpdated(resultSet.getDate("last_updated"));

		return solution;
	}

}
