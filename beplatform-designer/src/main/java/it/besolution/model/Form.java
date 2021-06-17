package it.besolution.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class Form {

	private FormMaster master;

	private List<FormAttribute> attributes = new ArrayList<>();

	public Form(FormMaster master) {
		this.master = master;
	}
	
	public void addAttribute(FormAttribute attribute) {
		attributes.add(attribute);
		
	}
}
