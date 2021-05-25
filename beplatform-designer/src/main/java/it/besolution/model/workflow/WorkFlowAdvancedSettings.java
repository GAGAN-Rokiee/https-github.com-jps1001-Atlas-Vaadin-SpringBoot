package it.besolution.model.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkFlowAdvancedSettings {

    private Integer id;
    private Integer workFlowId;
    private Integer workFlowAdvancedId;
    private String settingName;
    private String settingValue;
}
