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

    public List<WorkFlowAdvancedSettings> getAllSettings(Integer workFlowId,
                                                         Integer workFlowAdvancedId) throws Exception {

        return repository.getAllAdvancedSettings(workFlowId, workFlowAdvancedId);
    }

    public List<WorkFlowAdvancedSettings> saveAll(List<WorkFlowAdvancedSettings> settings, Integer workFlowId,
                                                  Integer workFlowAdvancedId) throws Exception {
        settings.forEach(setting -> {
            setting.setWorkFlowId(workFlowId);
            setting.setWorkFlowAdvancedId(workFlowAdvancedId);
        });

        return repository.saveSettings(settings, workFlowId, workFlowAdvancedId);
    }

    public WorkFlowAdvancedSettings updateWorkFlowAdvancedSettings(WorkFlowAdvancedSettings settings)
            throws Exception {
        return repository.updateSettings(settings);
    }
}
