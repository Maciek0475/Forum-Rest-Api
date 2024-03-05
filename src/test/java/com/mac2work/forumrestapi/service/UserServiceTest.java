package com.mac2work.forumrestapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.UserRepository;
import com.mac2work.forumrestapi.request.UserRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.UserResponse;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	private User user;
	private User user2;
	private UserRequest userRequest;
	private UserResponse userResponse;
	private ApiResponse apiResponse;

	@BeforeEach
	void setUp() throws Exception {
		user = User.builder()
				.id(1)
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();
		user2 = User.builder()
				.id(2)
				.firstName("Maciej")
				.lastName("Inny")
				.email("mac3work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();
		userRequest = UserRequest.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();
		userResponse = UserResponse.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.role(Role.ADMIN)
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("User deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	@Test
	final void userService_getUsers_ReturnMoreThanOneUserResponse() {
		List<User> users = List.of(user, user2);
		when(userRepository.findAll()).thenReturn(users);
		
		List<UserResponse> userResponses = userService.getUsers();
		
		assertThat(userResponses).isNotNull();
		assertThat(userResponses.size()).isGreaterThan(1);
	}

	@Test
	final void userService_getSpecificUser_ReturnUserResponseNotNull() {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		
		UserResponse userResponse = userService.getSpecificUser(user.getId());
		
		assertThat(userResponse).isEqualTo(this.userResponse);
	}

	@Test
	final void userService_getSpecificUser_ThrowResourceNotFoundExceptionNotNull() {
		int id = 3;
		User emptyUser = null;
		when(userRepository.findById(id)).thenReturn(Optional.ofNullable(emptyUser));
		
		Exception exception = assertThrows(
				ResourceNotFoundException.class,
				() -> { userService.getSpecificUser(id); });
				
		
		assertThat(exception).isNotNull();
	}
	
	@Test
	final void userService_updateUser_ReturnUserResponse() {
		when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
		doReturn(null).when(userRepository).save(user2);
		
		UserResponse userResponse = userService.updateUser(user2.getId(), userRequest);
		
		assertThat(userResponse).isEqualTo(this.userResponse);
	}

	@Test
	final void userService_deleteUser_ReturnApiResponse() {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		doNothing().when(userRepository).delete(user);
		
		ApiResponse apiResponse = userService.deleteUser(user.getId());
		
		assertThat(apiResponse).isEqualTo(this.apiResponse);
	}

}
