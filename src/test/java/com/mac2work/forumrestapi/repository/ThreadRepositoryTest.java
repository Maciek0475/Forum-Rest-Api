package com.mac2work.forumrestapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ThreadRepositoryTest {
	
	@Autowired
	private ThreadRepository threadRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserRepository userRepository;
	
	private Book book;
	private User user;
	private Thread thread;
	private Thread thread2;


	@BeforeEach
	public void setUpBeforeClass() {
		book = Book.builder()
				.name("The Hobbit")
				.publicationYear(1937)
				.description("The bedtime story for his children famously begun on the blank page of an exam script that tells the tale of Bilbo Baggins and the dwarves in their quest to take back the Lonely Mountain from Smaug the dragon")
				.build();
		user = User.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();		
		thread = Thread.builder()
				.name("Bilbo returning to the Shire")
				.book(book)
				.user(user)
				.content("What Bilbo find upon when he returned to the Shire")
				.build();
		thread2 = Thread.builder()
				.name("Bilbo entering the lonely mountain")
				.book(book)
				.user(user)
				.content("What Bilbo find upon when he entered Erebor")
				.build();
		bookRepository.save(book);
		userRepository.save(user);
		threadRepository.save(thread);
		threadRepository.save(thread2);

	}

	@Test
	final void threadRepository_findByBookId_ReturnMoreThanOneThread(){
		 List<Thread> threads = threadRepository.findAllByBookId(book.getId()).get();
		 
		 assertThat(threads).isNotNull();
		 assertThat(threads.size()).isGreaterThan(1);
	}


}
