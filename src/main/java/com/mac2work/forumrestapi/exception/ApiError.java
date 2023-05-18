package com.mac2work.forumrestapi.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiError {

	private HttpStatus httpStatus;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	
	private ApiError() {
		timestamp = LocalDateTime.now();
	}


	public ApiError(HttpStatus httpStatus, String message) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
	}
	
	

	
	
}
