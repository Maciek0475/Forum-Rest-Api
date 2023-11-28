package com.mac2work.forumrestapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	
	@BeforeEach
	public void setUpBeforeClass() {
		user = User.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();
		
		userRepository.save(user);
	}

	@Test
	final void userRepository_findByEmail_ReturnUserEqualToSavedUser() {
		User findedUser = userRepository.findByEmail(user.getEmail()).get();
		
		assertThat(findedUser).isNotNull();
		assertThat(findedUser).isEqualTo(user);
	}

}
