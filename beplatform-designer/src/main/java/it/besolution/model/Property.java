package it.besolution.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Property {

    private Integer id;
    private Integer solutionId;
    private Integer objectClassId;
    private String propertyName;
    private String propertyType;
    private String label;
    private String constraintKey;
    private String constraintValue;
    private Boolean propertyIsNull = Boolean.FALSE;
    private String defaultValue;
    private Date lastUpdated;
    private Boolean toUpdate;
    private Boolean toDelete;
    private String objectClassName;
}
