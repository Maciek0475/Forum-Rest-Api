package com.mac2work.forumrestapi.request;

import com.mac2work.forumrestapi.model.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

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
	@Enumerated(EnumType.STRING)
	private Role role;
	
}
