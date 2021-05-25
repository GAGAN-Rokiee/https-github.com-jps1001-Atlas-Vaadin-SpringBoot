package it.besolution.mapper.workflow;

import it.besolution.model.workflow.WorkFlowAdvanced;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkFlowAdvancedMapper implements RowMapper<WorkFlowAdvanced> {

    @Override
    public WorkFlowAdvanced mapRow(ResultSet rs, int i) throws SQLException {

        WorkFlowAdvanced wfa = new WorkFlowAdvanced();

        wfa.setId(rs.getInt("id"));
        wfa.setWorkFlowId(rs.getInt("workflow_id"));
        wfa.setJarType(rs.getString("jar_type"));
        wfa.setJarClass(rs.getString("jar_class"));
        wfa.setJarPath(rs.getString("jar_path"));

        return wfa;
    }
}
