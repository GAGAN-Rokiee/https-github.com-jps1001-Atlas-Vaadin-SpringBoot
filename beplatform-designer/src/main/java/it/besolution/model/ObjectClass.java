package it.besolution.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ObjectClass {

    private Integer id;
    private String uniqueId;
    private Integer solutionId;
    private String className;
    private String description;
    private Integer baseType;
    private Integer classType;
    private String basePath;
    private String entityName;
    private Boolean securityEnabled = Boolean.TRUE;
    private Boolean cryptoContent = Boolean.FALSE;
    private String counterName;
    private Integer storageType;
    private Boolean systemClass = Boolean.FALSE;
    private String defaultWorkflow = "OFF";
    private Date lastUpdated = new Date();
}
