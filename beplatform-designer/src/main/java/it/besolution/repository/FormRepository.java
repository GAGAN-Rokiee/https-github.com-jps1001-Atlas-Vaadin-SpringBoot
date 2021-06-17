package it.besolution.repository;

import it.besolution.mapper.FormMapper;
import it.besolution.model.Form;
import it.besolution.model.FormAttribute;
import it.besolution.model.FormMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FormRepository {

	private static final Logger LOG = LoggerFactory.getLogger(FormRepository.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Form save(Form form) throws Exception {

		String sql = "INSERT INTO form_master (object_class_id, label, object_class_name, registry,file_name,last_updated,solution_id) VALUES (?, ?, ?, ?,?,?,?)";

		LOG.info("Query: {}", sql);
		LOG.info("Params: {}", form.toString());

		try {
			int val = jdbcTemplate.update(sql, form.getMaster().getObjectclassId(), form.getMaster().getLabel(),
					form.getMaster().getObjectClassName(), form.getMaster().getRegistry(),
					form.getMaster().getFileName(), form.getMaster().getLastUpdated(),
					form.getMaster().getSolutionId());
			final Integer masterId = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
			form.getMaster().setId(masterId);

			form.setAttributes(addAttributes(masterId, form.getAttributes()));

		} catch (Exception ex) {
			LOG.error("Cannot save the form with file name: {}", form.getMaster().getFileName());
			throw new Exception(ex);
		}
		return form;
	}

	public List<FormAttribute> addAttributes(Integer masterId, List<FormAttribute> attributes) throws Exception {

		try {
			String sqlAttribute = "INSERT INTO form_attributes (master_id, attribute_name, attribute_value,last_updated) VALUES (?, ?, ?, ?)";

			LOG.info("Query: {}", sqlAttribute);

			Connection connect = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = connect.prepareStatement(sqlAttribute, PreparedStatement.RETURN_GENERATED_KEYS);

			for (FormAttribute attribute : attributes) {
				ps.setInt(1, masterId);
				ps.setString(2, attribute.getAttributeName());
				ps.setString(3, attribute.getAttributeValue());
				ps.setTimestamp(4, attribute.getLastUpdated());
				ps.addBatch();

			}

			ps.executeBatch();

			ResultSet result = ps.getGeneratedKeys();
			int t = 0;
			while (result.next()) {
				attributes.get(t).setId(result.getInt(1));
				attributes.get(t).setMasterId(masterId);
				t++;
			}

		} catch (Exception ex) {
			LOG.error("Cannot add the form  attribute for master id : {}", masterId);
			throw new Exception(ex);
		}
		return attributes;
	}

	public FormMaster updateMaster(FormMaster master) throws Exception {

		String sql = " update form_master set  label = ? , object_class_name = ? , registry = ? ,file_name = ? ,last_updated = ? where id = ?";

		LOG.info("Query: {}", sql);
		LOG.info("Params: {}", master.toString());

		try {

			int val = jdbcTemplate.update(sql, master.getLabel(), master.getObjectClassName(), master.getRegistry(),
					master.getFileName(), new Date(System.currentTimeMillis()), master.getId());

			LOG.info("value: {}", val);

		} catch (Exception ex) {
			LOG.error("Cannot update the form with file name: {}", master.getFileName());
			throw new Exception(ex);
		}
		return master;

	}

	public List<FormAttribute> updateAttributes(List<FormAttribute> attributes) throws Exception {

		String sql = " update form_attributes  set  attribute_name = ? , attribute_value = ? , last_updated = ? where id = ?";

		LOG.info("Query: {}", sql);

		try {
			List<Object[]> objects = new ArrayList<>();

			for (FormAttribute attribute : attributes) {

				LOG.info("Params: {}", attribute.toString());
				Object[] object = { attribute.getAttributeName(), attribute.getAttributeValue(),
						attribute.getLastUpdated(), attribute.getId() };
				objects.add(object);
			}

			int[] values = jdbcTemplate.batchUpdate(sql, objects);
			
			LOG.info("values: {}", values);

		} catch (Exception ex) {
			LOG.error("Cannot update the form attribute .Error :", ex);
			throw new Exception(ex);
		}

		return attributes;

	}

	public List<Form> getFormsByObjectId(Integer objectId) throws Exception {

		String sql = " select * from form_master fm left join form_attributes fa on fm.id=fa.master_id   WHERE  fm.object_class_id = "
				+ objectId;
		LOG.info("Query: {}", sql);
		try {
			return jdbcTemplate.query(sql, new FormMapper());

		} catch (Exception ex) {
			LOG.error("Cannot fetch form. Error :", ex);
			throw new Exception(ex);
		}
	}

	public List<Form> getFormsBySolution(Integer solutionId) throws Exception {

		String sql = " select * from form_master fm left join form_attributes fa on fm.id=fa.master_id   where fm.solution_id = "
				+ solutionId;
		LOG.info("Query: {}", sql);
		try {
			return jdbcTemplate.query(sql, new FormMapper());

		} catch (Exception ex) {
			LOG.error("Cannot fetch form. Error :", ex);
			throw new Exception(ex);
		}
	}

	public Form getFormByMasterId(Integer objectId) throws Exception {

		String sql = " select * from form_master fm left join form_attributes fa on fm.id=fa.master_id   WHERE fm.id = "
				+ objectId;
		LOG.info("Query: {}", sql);
		try {
			List<Form> forms = jdbcTemplate.query(sql, new FormMapper());

			return forms.isEmpty() ? null : forms.get(0);
		
		} catch (Exception ex) {
			LOG.error("Cannot fetch solution. Error :", ex);
			throw new Exception(ex);
		}
	}

	public void deleteForm(Integer id) throws Exception {
		String sql = "delete from  form_attributes where master_id = " + id + "; "
				+ "delete from  form_master where id = " + id;

		LOG.info("Query: {}", sql);

		try {

			int val = jdbcTemplate.update(sql);

			LOG.info("value: {}", val);

		} catch (Exception ex) {
			LOG.error("Cannot delete the form with master id: {}", id);
			throw new Exception(ex);
		}
	}

	public void deleteFromAttribute(Integer masterId, Integer attributeId) throws Exception {
		String sql = "delete from  form_attributes where master_id = " + masterId + " and id = " + attributeId;

		LOG.info("Query: {}", sql);

		try {

			int val = jdbcTemplate.update(sql);

			LOG.info("value: {}", val);

		} catch (Exception ex) {
			LOG.error("Cannot delete the form attribute  with master id: {} and attrubute id : {}", masterId,
					attributeId);
			throw new Exception(ex);
		}
	}
}
