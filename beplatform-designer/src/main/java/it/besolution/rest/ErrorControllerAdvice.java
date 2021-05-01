package it.besolution.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorControllerAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(ErrorControllerAdvice.class);

	@Value("${debug:false}")
	boolean isDebug;

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiRestError> handleException(Exception exception) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ApiRestError("E_UNKNOWN_ERROR", isDebug ? exception.toString() : exception.getMessage()));
	}
}
