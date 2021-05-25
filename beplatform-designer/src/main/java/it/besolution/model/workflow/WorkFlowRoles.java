package it.besolution.model.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkFlowRoles {

    private Integer id;
    private Integer workFlowId;
    private String roleName;
    private Integer workFlowPropertyId;
    private Boolean isDynamic = Boolean.FALSE;
    private Boolean isCreated = Boolean.FALSE;
}
