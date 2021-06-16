package it.besolution.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FormAttribute {

	private Integer id;

	private Integer masterId;

	private String attributeName;

	private String attributeValue;

	private Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());

}
