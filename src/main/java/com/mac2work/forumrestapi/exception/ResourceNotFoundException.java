package com.mac2work.forumrestapi.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
	
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
