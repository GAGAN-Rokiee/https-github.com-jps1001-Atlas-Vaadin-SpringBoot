package it.besolution.service.workflow;

import it.besolution.model.workflow.WorkFlowAdvancedSettings;
import it.besolution.repository.workflow.WorkFlowAdvancedSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowAdvancedSettingsService {

    @Autowired
    private WorkFlowAdvancedSettingsRepository repository;

    public List<WorkFlowAdvancedSettings> getAllSettings(Integer workFlowId) throws Exception {

        return repository.getAllAdvancedSettings(workFlowId);
    }

    public List<WorkFlowAdvancedSettings> saveAll(List<WorkFlowAdvancedSettings> settings, Integer workFlowId)
            throws Exception {
        settings.forEach(setting -> setting.setWorkFlowId(workFlowId));
        return repository.saveSettings(settings, workFlowId);
    }

    public WorkFlowAdvancedSettings updateWorkFlowAdvancedSettings(WorkFlowAdvancedSettings settings)
            throws Exception {
        return repository.updateSettings(settings);
    }
}
