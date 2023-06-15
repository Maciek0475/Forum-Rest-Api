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
	
	@ExceptionHandler(NoAccessException.class)
	public ResponseEntity<ApiError> handleNoAccess(NoAccessException exc){
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, exc.getMessage());
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}
	
	@ExceptionHandler(IncorrectUserException.class)
	public ResponseEntity<ApiError> handleIncorrectUser(IncorrectUserException exc){
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, exc.getMessage());
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}
	
	@ExceptionHandler(UnloggedUserException.class)
	public ResponseEntity<ApiError> handleUnloggedUser(UnloggedUserException exc){
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, exc.getMessage());
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}
}
