package it.besolution.rest;

import lombok.Data;

@Data
public class ApiRestResponse {

    private Boolean isSuccess = true;

    private Boolean isError = false;

    private String errorMessage = null;

    private Object data;
}
