package com.mac2work.forumrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ForumExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleEntityNotFound(ResourceNotFoundException exc){
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exc.getMessage());
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}
	
}
