package it.besolution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectClassDto {

    private Integer id;
    private String className;
    private String entityName;
    private Boolean securityEnabled;
    private Boolean systemClass;
    private String counterName;
}
