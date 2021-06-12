package it.besolution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyNameTypeDto {

    private Integer id;
    private String name;
    private String type;
}
