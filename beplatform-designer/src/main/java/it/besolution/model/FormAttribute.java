package it.besolution.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FormAttribute {

	private Integer id;

	private Integer masterId;

	private String attributeName;

	private String attributeValue;

	private Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());

}
