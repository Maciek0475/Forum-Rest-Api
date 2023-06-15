package com.mac2work.forumrestapi.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadRequest {

	@NotBlank
	@Size(min=5, max = 45)
	private String name;
	@NotBlank
	private Integer bookId;
	@Size(max = 512)
	private String content;
}
