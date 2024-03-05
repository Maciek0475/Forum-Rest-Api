package com.mac2work.forumrestapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.request.UserRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.UserResponse;
import com.mac2work.forumrestapi.service.UserService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private UserService userService;

	
	private UserRequest userRequest;
	private UserResponse userResponse;
	private UserResponse userResponse2;
	private ApiResponse apiResponse;
	private int id;

	

	@BeforeEach
	void setUp() throws Exception {
		id = 1;
		
		userResponse = UserResponse.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.role(Role.ADMIN)
				.build();
		userResponse2 = UserResponse.builder()
				.firstName("Maciej")
				.lastName("Inny")
				.email("mac3work@o2.pl")
				.role(Role.ADMIN)
				.build();
		userRequest = UserRequest.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("User deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	@Test
	final void userController_getUsers_ReturnListOfUserResponses() throws Exception {
		List<UserResponse> userResponses = List.of(userResponse, userResponse2);
		when(userService.getUsers()).thenReturn(userResponses);
		
		ResultActions response = mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(userResponses.size())));
	}

	@Test
	final void userController_getSpecificUser_ReturnUserResponse() throws Exception {
		when(userService.getSpecificUser(id)).thenReturn(userResponse);
		
		ResultActions response = mockMvc.perform(get("/users/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", CoreMatchers.is(userResponse.getFirstName())))
				.andExpect(jsonPath("$.lastName", CoreMatchers.is(userResponse.getLastName())))
				.andExpect(jsonPath("$.email", CoreMatchers.is(userResponse.getEmail())))
				.andExpect(jsonPath("$.role", CoreMatchers.is(userResponse.getRole().name())));
	}

	@Test
	final void userController_updateUser_ReturnUserResponse() throws Exception {
		when(userService.updateUser(id, userRequest)).thenReturn(userResponse);
		
		ResultActions response = mockMvc.perform(put("/users/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", CoreMatchers.is(userResponse.getFirstName())))
				.andExpect(jsonPath("$.lastName", CoreMatchers.is(userResponse.getLastName())))
				.andExpect(jsonPath("$.email", CoreMatchers.is(userResponse.getEmail())))
				.andExpect(jsonPath("$.role", CoreMatchers.is(userResponse.getRole().name())));
	}

	@Test
	final void userController_deleteUser_ReturnApiResponse() throws Exception {
		when(userService.deleteUser(id)).thenReturn(apiResponse);
		
		ResultActions response = mockMvc.perform(delete("/users/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
				.andExpect(jsonPath("$.responseMessage", CoreMatchers.is(apiResponse.getResponseMessage())))
				.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
	}

}
