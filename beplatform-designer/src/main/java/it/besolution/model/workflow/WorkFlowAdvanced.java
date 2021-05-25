package it.besolution.model.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkFlowAdvanced {

    private Integer id;
    private Integer workFlowId;
    private String jarType;
    private String jarClass;
    private String jarPath;

    public WorkFlowAdvanced(String jarType, String jarClass) {
        this.jarClass = jarClass;
        this.jarType = jarType;
    }
}
