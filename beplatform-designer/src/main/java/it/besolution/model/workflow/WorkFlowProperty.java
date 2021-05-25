package it.besolution.model.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkFlowProperty {

    private Integer id;
    private Integer workFlowId;
    private String propertyName;
    private String propertyType;
}
