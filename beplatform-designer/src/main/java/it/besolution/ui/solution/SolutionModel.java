package it.besolution.ui.solution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolutionModel {

	private String id;

	private String templateName;

	private String description;

	private String lastUpdated;

	private String status;
}

