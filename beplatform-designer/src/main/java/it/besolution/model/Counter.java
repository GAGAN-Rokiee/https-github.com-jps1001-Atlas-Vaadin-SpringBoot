package it.besolution.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Counter {

	
	private Integer id;

	private String counterName;

	private Integer solutionId;

	private Date lastUpdated = new Date();
}
