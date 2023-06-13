package com.mac2work.forumrestapi.request;

import jakarta.validation.constraints.Email;
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
public class RegisterRequest {
	

	@NotBlank
	@Size(min=3, max=45)
	private String firstName;
	@NotBlank
	@Size(min=3, max=45)
	private String lastName;
	@Email
	private String email;
	@NotBlank
	@Size(min=3, max=45)
	private String password;

}
