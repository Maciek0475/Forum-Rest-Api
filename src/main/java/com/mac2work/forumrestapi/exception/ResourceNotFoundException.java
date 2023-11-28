package com.mac2work.forumrestapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 4915424559956198957L;
	
	private String resourceName; 
	private String fieldName;
	private Object fieldValue;
	private String message;
	
	

	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		formatMessage();
		
	}
	
	private void formatMessage() {
		this.message = String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue);
	}
	
	
}
