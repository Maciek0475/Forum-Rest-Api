package com.mac2work.forumrestapi.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

	@NotBlank
	private String name;
	@Size(min=4, max=4)
	private Integer publicationYear;
	@NotBlank
	@Size(max = 512)
	private String description;
}
