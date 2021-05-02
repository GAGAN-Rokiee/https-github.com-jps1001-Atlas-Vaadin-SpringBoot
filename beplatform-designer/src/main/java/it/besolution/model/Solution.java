package it.besolution.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Solution {

	private Integer id;

	private String templateName;

	private String prefix;

	private String description;

	private Date lastUpdated = new Date();
}
