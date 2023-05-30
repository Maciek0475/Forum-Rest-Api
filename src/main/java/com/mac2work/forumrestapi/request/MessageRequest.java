package com.mac2work.forumrestapi.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {

	@NotBlank
	@Size(min=5, max=512)
	private String content; 
}
