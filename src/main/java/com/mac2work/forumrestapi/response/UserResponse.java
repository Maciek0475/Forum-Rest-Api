package com.mac2work.forumrestapi.response;

import com.mac2work.forumrestapi.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private String firstName;
	private String lastName;
	private String email;
	private Role role;

}
