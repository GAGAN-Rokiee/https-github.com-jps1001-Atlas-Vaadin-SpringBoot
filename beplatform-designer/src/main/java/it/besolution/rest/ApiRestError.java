package it.besolution.rest;

import java.util.HashMap;

public class ApiRestError {

	private String message;
	private String status;

	private HashMap<String, String> info;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HashMap<String, String> getInfo() {
		return info;
	}

	public ApiRestError(String status, String message) {
		this.status = status;
		this.message = message;
		this.info = new HashMap<>();
	}

	public ApiRestError() {
	}

}
