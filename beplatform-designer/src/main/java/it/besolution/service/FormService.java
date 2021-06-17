package it.besolution.service;

import it.besolution.model.Form;
import it.besolution.model.FormAttribute;
import it.besolution.model.FormMaster;
import it.besolution.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {


	@Autowired
	private FormRepository formRepository;
	
	public Form createForm(Form solution) throws Exception {
		return formRepository.save(solution);
	}
	
	
	public FormMaster updateMaster(FormMaster formMaster) throws Exception {
		return formRepository.updateMaster(formMaster);
	}
	
	public List<FormAttribute>  updateAttributes(List<FormAttribute> attributes) throws Exception {
		return formRepository.updateAttributes(attributes);
	}
	
	public List<FormAttribute>  addAttributes(Integer masterId, List<FormAttribute> attributes) throws Exception {
		return formRepository.addAttributes(masterId ,attributes);
	}
	
	public List<Form> getFormsByObjectId(Integer objectId) throws Exception{
		return formRepository.getFormsByObjectId(objectId);
	}
	
	public List<Form> getFormsBySolutionId(Integer solutionId) throws Exception{
		return formRepository.getFormsBySolution(solutionId);
	}
	
	public Form getFormByMasterId(Integer masterId) throws Exception{
		return formRepository.getFormByMasterId(masterId);
	}

	public void deleteFrom(Integer masterId) throws Exception{
		 formRepository.deleteForm(masterId);
	}
	
	public void deleteFromAttribute(Integer masterId, Integer attributeId) throws Exception{
		 formRepository.deleteFromAttribute(masterId,attributeId);
	}
}
