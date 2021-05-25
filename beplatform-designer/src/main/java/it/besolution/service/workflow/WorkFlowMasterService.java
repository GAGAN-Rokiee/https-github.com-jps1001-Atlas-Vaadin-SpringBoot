package it.besolution.service.workflow;

import it.besolution.dto.WorkFlowDto;
import it.besolution.model.workflow.WorkFlowMaster;
import it.besolution.repository.workflow.WorkFlowMasterRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowMasterService {

    @Autowired
    private WorkFlowMasterRepository workFlowMasterRepository;

    public List<WorkFlowMaster> saveWorkFlowMaster(WorkFlowMaster workFlowMaster) throws Exception {

        return workFlowMasterRepository.saveWorkFlow(workFlowMaster);
    }

    public WorkFlowMaster updateWorkFlowMaster(WorkFlowMaster workFlowMaster) throws Exception {

        return workFlowMasterRepository.updateWorkFlow(workFlowMaster);
    }

    public List<WorkFlowMaster> getAllWorkFlows(Integer solutionId, Integer workFlowId) throws Exception {

        return workFlowMasterRepository.getWorkFlow(solutionId, workFlowId);
    }

    public WorkFlowDto getWorkFlowById(Integer solutionId, Integer workFlowId) throws Exception {

        List<WorkFlowMaster> allWorkFlows = getAllWorkFlows(solutionId, workFlowId);
        if (CollectionUtils.isEmpty(allWorkFlows)) {
            return null;
        }

        WorkFlowMaster wfm = allWorkFlows.get(0);
        return new WorkFlowDto(wfm.getId(), wfm.getSolutionId(), wfm.getName(), wfm.getLastUpdated());
    }
}
