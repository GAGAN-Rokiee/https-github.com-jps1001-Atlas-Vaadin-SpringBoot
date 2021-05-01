package it.besolution.rest;

public class ApiResponseMessage {

    public ApiResponseMessage() {

    }

    public ApiResponseMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;
}
