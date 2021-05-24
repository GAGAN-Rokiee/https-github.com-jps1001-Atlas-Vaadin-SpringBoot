package it.besolution.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.besolution.model.Counter;

public class CounterMapper implements RowMapper<Counter> {

	@Override
	public Counter mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Counter solution = new Counter();
		solution.setId(resultSet.getInt("id"));
		solution.setCounterName(resultSet.getString("counter_name"));
		solution.setSolutionId(resultSet.getInt("solution_id"));
		solution.setLastUpdated(resultSet.getTimestamp("last_updated"));

		return solution;
	}

}
