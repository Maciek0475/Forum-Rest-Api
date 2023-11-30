package com.mac2work.forumrestapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mac2work.forumrestapi.exception.IncorrectUserException;
import com.mac2work.forumrestapi.exception.NoAccessException;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.User;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private AuthorizationService authorizationService;
	
	private User userAdmin;
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		userAdmin = User.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();
		user = User.builder()
				.id(1)
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac3work@o2.pl")
				.password("Password123")
				.role(Role.USER)
				.build();
	}

	@Test
	final void authorizationService_isAdmin_ReturnTrue() {
		String path = "/test";
		String mappingMethod = "/GET";
		when(userService.getLoggedInUser()).thenReturn(userAdmin);
		
		boolean isAdmin = authorizationService.isAdmin(path, mappingMethod);
		
		assertThat(isAdmin).isTrue();
	}
	
	@Test
	final void authorizationService_isAdmin_ThrowNoAccessExceptions() {
		String path = "/test";
		String mappingMethod = "/GET";
		when(userService.getLoggedInUser()).thenReturn(user);
		
		Exception exception = assertThrows(
				NoAccessException.class, 
				() -> { authorizationService.isAdmin(path, mappingMethod); });

		assertThat(exception).isNotNull();
	}

	@Test
	final void authorizationService_isCorrectUser_ReturnTrue() {
		int id = user.getId();
		String resource = "Book";
		when(userService.getLoggedInUser()).thenReturn(user);

		boolean isCorrectUser = authorizationService.isCorrectUser(id, resource);
		
		assertThat(isCorrectUser).isTrue();
	}
	
	@Test
	final void authorizationService_isAdmin_ThrowIncorrectUserException() {
		int id = 2;
		String resource = "Book";
		when(userService.getLoggedInUser()).thenReturn(user);

		Exception exception = assertThrows(
				IncorrectUserException.class,
				() -> { authorizationService.isCorrectUser(id, resource); });
		
		assertThat(exception).isNotNull();
	}

}
