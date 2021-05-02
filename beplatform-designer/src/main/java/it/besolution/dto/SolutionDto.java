package it.besolution.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDto {

	private Integer id;

	private String templateName;

	private String description;

	private Date lastUpdated;
}
