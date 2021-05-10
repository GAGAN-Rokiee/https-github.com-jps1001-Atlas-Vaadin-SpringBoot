package it.besolution.repository;

import it.besolution.mapper.SolutionMapper;
import it.besolution.model.Solution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class SolutionRepository {

	private static final Logger LOG = LoggerFactory.getLogger(ObjectClassRepository.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Solution save(Solution solution) throws Exception {

		String sql = "INSERT INTO solutions (template_name, prefix, description, last_updated) VALUES (?, ?, ?, ?)";

		LOG.info("Query: {}", sql);
		LOG.info("Params: {}", solution.toString());

		try {

			int val = jdbcTemplate.update(sql, solution.getTemplateName(), solution.getPrefix(),
					solution.getDescription(), solution.getLastUpdated());

			final Integer id = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
			solution.setId(id);


		} catch (Exception ex) {
            LOG.error("Cannot save the solution with template name: {}", solution.getTemplateName());
			throw new Exception(ex);
		}
		return solution;
	}

	public List<Solution> getAllSolutions() throws Exception {

		String sql = "SELECT * FROM solutions";
		LOG.info("Query: {}", sql);

		try {
			return jdbcTemplate.query(sql, new SolutionMapper());
		} catch (Exception ex) {
			LOG.error("Cannot fetch solution. Error :",ex);
			throw new Exception(ex);
		}
	}

	public Solution getSolutionById(Integer solutionId) throws Exception {

		String sql = "SELECT * FROM solutions WHERE id = " + solutionId;
		LOG.info("Query: {}", sql);

		try {
			return jdbcTemplate.queryForObject(sql, new SolutionMapper());
		} catch (Exception ex) {
			LOG.error("Cannot fetch solution. Error :",ex);
			throw new Exception(ex);
		}
	}
	
	public Solution update(Solution solution) throws Exception {
		String sql = "update solutions set template_name =? , prefix= ? ,description=? ,last_updated = ? where id = ?";

		LOG.info("Query: {}", sql);
		LOG.info("Params: {}", solution.toString());

		try {

			int val = jdbcTemplate.update(sql, solution.getTemplateName(), solution.getPrefix(),
					solution.getDescription(),new Date(System.currentTimeMillis()),solution.getId());

			LOG.info("value: {}", val);


		} catch (Exception ex) {
            LOG.error("Cannot update the solution with template name: {}", solution.getTemplateName());
			throw new Exception(ex);
		}
		return solution;
	}
}
