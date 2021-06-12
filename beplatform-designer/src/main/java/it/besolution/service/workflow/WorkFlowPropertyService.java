package it.besolution.service.workflow;

import it.besolution.model.workflow.WorkFlowProperty;
import it.besolution.repository.workflow.WorkFlowPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowPropertyService {

    @Autowired
    private WorkFlowPropertyRepository workFlowPropertyRepository;

    public List<WorkFlowProperty> getAllWorkFlowPropertiesByWorkFlowId(Integer workFlowId) throws Exception {
        return workFlowPropertyRepository.getAllProperties(workFlowId);
    }

    public List<WorkFlowProperty> deleteWorkFlowProperty(Integer propertyId, Integer workFlowId) throws Exception {
        return workFlowPropertyRepository.deleteProperty(propertyId, workFlowId);
    }

    public List<WorkFlowProperty> saveWorkFlowProperties(List<WorkFlowProperty> propertyList
            , Integer workFlowId) throws Exception {

        propertyList.forEach(property -> property.setWorkFlowId(workFlowId));
        return workFlowPropertyRepository.saveWorkFlowProperty(propertyList, workFlowId);
    }
}
