package com.mac2work.forumrestapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.forumrestapi.request.AuthenticationRequest;
import com.mac2work.forumrestapi.request.RegisterRequest;
import com.mac2work.forumrestapi.response.AuthenticationResponse;
import com.mac2work.forumrestapi.service.AuthenticationService;

@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	AuthenticationService authenticationService;
	
	AuthenticationResponse authenticationResponse;
	RegisterRequest registerRequest;
	AuthenticationRequest authenticationRequest;
	String token;
	
	@BeforeEach
	void setUp() throws Exception {
		token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
		authenticationResponse = AuthenticationResponse.builder()
				.token(token)
				.build();
		registerRequest = RegisterRequest.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.build();
		authenticationRequest = AuthenticationRequest.builder()
				.email("mac2work@o2.pl")
				.password("Password123")
				.build();
	}

	@Test
	final void AuthenticationController_register_ReturnAuthenticationResponse() throws Exception {
		when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);
		
		ResultActions response = mockMvc.perform(post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest)));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.token", CoreMatchers.is(authenticationResponse.getToken())));

	}

	@Test
	final void AuthenticationController_authenticate_ReturnAuthenticationResponse() throws Exception {
		when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);
		
		ResultActions response = mockMvc.perform(post("/auth/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(authenticationRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.token", CoreMatchers.is(authenticationResponse.getToken())));
		
		
	}

}
