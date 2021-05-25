package it.besolution.mapper.workflow;

import it.besolution.model.workflow.WorkFlowMaster;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkFlowMasterMapper implements RowMapper<WorkFlowMaster> {

    @Override
    public WorkFlowMaster mapRow(ResultSet rs, int i) throws SQLException {

        WorkFlowMaster master = new WorkFlowMaster();
        master.setId(rs.getInt("id"));
        master.setSolutionId(rs.getInt("solution_id"));
        master.setName(rs.getString("name"));
        master.setPrefix(rs.getString("prefix"));
        master.setDescription(rs.getString("description"));
        master.setLastUpdated(rs.getDate("last_updated"));

        return master;
    }
}
