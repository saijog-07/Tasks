package com.hexa.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ApiError> storeNotFound(
			TaskNotFoundException ex, HttpServletRequest req) {
		return buildError(ex, req, HttpStatus.CONFLICT);
	}
	
	
	private ResponseEntity<ApiError> buildError (
			Exception ex, HttpServletRequest req, HttpStatus status) {
		ApiError err = new ApiError();
		err.setStatus(status.value());
		err.setError(status.getReasonPhrase());
		err.setMessage(ex.getMessage());
		err.setPath(req.getRequestURI());
		
		return new ResponseEntity<>(err, status);
	}
}
