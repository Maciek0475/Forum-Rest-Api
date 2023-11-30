package com.mac2work.forumrestapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mac2work.forumrestapi.config.JwtService;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.UserRepository;
import com.mac2work.forumrestapi.request.AuthenticationRequest;
import com.mac2work.forumrestapi.request.RegisterRequest;
import com.mac2work.forumrestapi.response.AuthenticationResponse;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private JwtService jwtService;
	@Mock 
	private AuthenticationManager authenticationManager;
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private AuthenticationService authenticationService;

	@Test
	final void authenticationService_register_ReturnAuthenticationResponseNotNull() {
		RegisterRequest registerRequest = RegisterRequest.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.build();
		doReturn(null).when(userRepository).save(Mockito.any(User.class));
		doReturn(null).when(passwordEncoder).encode(registerRequest.getPassword());
		when(jwtService.generateToken(Mockito.any(User.class)))
		.thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
		
		AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
		
		assertThat(authenticationResponse).isNotNull();
	}

	@Test
	final void authenticationService_authenticate_ReturnAuthenticationResponseNotNull() {
		AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
				.email("mac2work@o2.pl")
				.password("Password123")
				.build();
		User user = User.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();
		doReturn(null).when(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
		when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
		when(jwtService.generateToken(Mockito.any(User.class)))
		.thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
		
		AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
		
		assertThat(authenticationResponse).isNotNull();
	}

}
