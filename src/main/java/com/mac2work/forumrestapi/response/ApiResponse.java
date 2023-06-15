package com.mac2work.forumrestapi.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

	private Boolean isSuccess;
	private String responseMessage;
	private HttpStatus httpStatus;
	
	public ApiResponse(Boolean isSuccess, String responseMessage) {
		this.isSuccess = isSuccess;
		this.responseMessage = responseMessage;
	}
	
	
	
}
