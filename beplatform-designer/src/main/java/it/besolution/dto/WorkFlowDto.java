package it.besolution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkFlowDto {

    private Integer id;
    private Integer solutionId;
    private String name;
    private Date lastUpdated;
}
