package it.besolution.model.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkFlowMaster {

    private Integer id;
    private Integer solutionId;
    private String name;
    private String prefix;
    private String description;
    private Date lastUpdated = new Date();
}
