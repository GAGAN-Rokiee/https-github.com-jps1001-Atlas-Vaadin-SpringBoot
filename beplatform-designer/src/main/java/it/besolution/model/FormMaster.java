package it.besolution.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FormMaster {
	
	private Integer id;

	private String label;

	private String objectClassName;

	private String registry;

	private String fileName;

	private Integer objectclassId;
	
	private  Integer solutionId;
	
	private Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());


}
