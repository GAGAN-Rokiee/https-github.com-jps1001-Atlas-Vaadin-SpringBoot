package it.besolution.service.workflow;

import it.besolution.model.workflow.WorkFlowRoles;
import it.besolution.repository.workflow.WorkFlowRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowRoleService {

    @Autowired
    private WorkFlowRolesRepository workFlowRolesRepository;

    public List<WorkFlowRoles> getAllWorkFlowRoles(Integer workFlowId) throws Exception {
        return workFlowRolesRepository.getAllWorkFlowRoles(workFlowId);
    }

    public List<WorkFlowRoles> saveWorkFlowRoles(List<WorkFlowRoles> roles, Integer workFlowId) throws Exception {

        roles.forEach(role -> role.setWorkFlowId(workFlowId));
        return workFlowRolesRepository.saveWorkFlowRoles(roles, workFlowId);
    }
}
