package it.besolution.repository;

import it.besolution.mapper.CounterMapper;
import it.besolution.model.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CounterRepository {

	private static final Logger LOG = LoggerFactory.getLogger(CounterRepository.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Counter save(Counter counter) throws Exception {

		String checkCounterSql = "SELECT * FROM counter WHERE solution_id = " + counter.getSolutionId()
				+ " and counter_name ='" + counter.getCounterName() + "'";
		LOG.info("Query: {}", checkCounterSql);

		List<Counter> IsExist = jdbcTemplate.query(checkCounterSql, new CounterMapper());

		if ( !IsExist.isEmpty()) {
			throw new Exception(
					" Counter with name " + counter.getCounterName() + " already exist for this solution.");
		}
		String sql = "INSERT INTO counter (solution_id, counter_name,  last_updated) VALUES ( ?, ?, ?)";

		LOG.info("Query: {}", sql);
		LOG.info("Params: {}", counter.toString());

		try {

			int val = jdbcTemplate.update(sql, counter.getSolutionId(), counter.getCounterName(),
					counter.getLastUpdated());

			final Integer id = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
			counter.setId(id);

		} catch (Exception ex) {
			LOG.error("Cannot save the counter with name: {}", counter.getCounterName());
			throw new Exception(ex);
		}
		return counter;
	}

	public List<Counter> getCountersBySolutionId(Integer solutionId) throws Exception {

		String sql = "SELECT * FROM counter WHERE solution_id = " + solutionId;
		LOG.info("Query: {}", sql);

		try {
			return jdbcTemplate.query(sql, new CounterMapper());
		} catch (Exception ex) {
			LOG.error("Cannot fetch counter. Error :", ex);
			throw new Exception(ex);
		}
	}

	public Counter update(Counter counter) throws Exception {

		String checkCounterSql = "SELECT * FROM counter WHERE solution_id = " + counter.getSolutionId()
				+ " and counter_name ='" + counter.getCounterName() + "'";
		LOG.info("Query: {}", checkCounterSql);

		List<Counter> IsExist = jdbcTemplate.query(checkCounterSql, new CounterMapper());

		if (!(IsExist.isEmpty() || IsExist.get(0).getId().equals(counter.getId()))) {
			throw new Exception(
					"Counter with name " + counter.getCounterName() + " already exist for this solution.");
		}

		String sql = "update counter set counter_name =? ,last_updated = ? where id = ?";

		LOG.info("Query: {}", sql);
		LOG.info("Params: {}", counter.toString());

		try {

			int val = jdbcTemplate.update(sql, counter.getCounterName(), counter.getLastUpdated(),
					counter.getId());

			LOG.info("value: {}", val);

		} catch (Exception ex) {
			LOG.error("Cannot update the counter with counter name: {}", counter.getCounterName());
			throw new Exception(ex);
		}
		return counter;
	}

	public void delete(Integer id) throws Exception {
		String sql = "delete from  counter where id = " + id;

		LOG.info("Query: {}", sql);

		try {

			int val = jdbcTemplate.update(sql);

			LOG.info("value: {}", val);

		} catch (Exception ex) {
			LOG.error("Cannot delete the counter with counter id: {}", id);
			throw new Exception(ex);
		}
	}

	public Counter getCounterById(Integer id) throws Exception {

		String sql = "SELECT * FROM counter WHERE id = " + id;
		LOG.info("Query: {}", sql);

		try {
			return jdbcTemplate.queryForObject(sql, new CounterMapper());
		} catch (Exception ex) {
			LOG.error("Cannot fetch counter. Error :", ex);
			throw new Exception(ex);
		}

	}

}
