package it.besolution.mapper;

import it.besolution.model.Solution;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SolutionMapper implements RowMapper<Solution> {

    @Override
    public Solution mapRow(ResultSet resultSet, int i) throws SQLException {

        Solution solution = new Solution();
        solution.setId(resultSet.getInt("id"));
        solution.setTemplateName(resultSet.getString("template_name"));
        solution.setPrefix(resultSet.getString("prefix"));
        solution.setDescription(resultSet.getString("description"));
        solution.setLastUpdated(resultSet.getDate("last_updated"));

        return solution;
    }
}
