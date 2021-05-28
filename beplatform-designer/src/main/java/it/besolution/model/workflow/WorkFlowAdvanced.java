package it.besolution.model.workflow;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "Values are as follows: ACTION/MAIL/SYNC/AUDIT")
    private String jarType;

    private String jarClass;
    private String jarPath;

    public WorkFlowAdvanced(String jarType, String jarClass) {
        this.jarClass = jarClass;
        this.jarType = jarType;
    }
}
