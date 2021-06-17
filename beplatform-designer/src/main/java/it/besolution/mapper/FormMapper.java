package it.besolution.mapper;

import it.besolution.model.Form;
import it.besolution.model.FormAttribute;
import it.besolution.model.FormMaster;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormMapper implements ResultSetExtractor<List<Form>> {

	@Override
	public List<Form> extractData(ResultSet rs) throws SQLException, DataAccessException {

		Map<Integer, Form> formMap = new HashedMap<>();

		while (rs.next()) {
			Form form;
			if (formMap.containsKey(rs.getInt(1))) {
				form = formMap.get(rs.getInt(1));

			} else {
				FormMaster master = new FormMaster();
				master.setId(rs.getInt(1));
				master.setObjectclassId(rs.getInt("object_class_id"));
				master.setLabel(rs.getString("label"));
				master.setObjectClassName(rs.getString("object_class_name"));
				master.setRegistry(rs.getString("registry"));
				master.setFileName(rs.getString("file_name"));
				master.setLastUpdated(rs.getTimestamp("last_updated"));
				master.setSolutionId(rs.getInt("solution_id"));
				
				form = new Form(master);

				formMap.put(master.getId(), form);
			}

			if (0 != rs.getInt(9)) {

				FormAttribute attribute = new FormAttribute();

				attribute.setId(rs.getInt(9));
				attribute.setMasterId(rs.getInt("master_id"));
				attribute.setAttributeName(rs.getString("attribute_name"));
				attribute.setAttributeValue(rs.getString("attribute_value"));
				attribute.setLastUpdated(rs.getTimestamp(13));

				form.addAttribute(attribute);
			}

		}

		return new ArrayList<>(formMap.values());
	}

}
