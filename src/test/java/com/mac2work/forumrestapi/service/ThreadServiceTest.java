package com.mac2work.forumrestapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.repository.ThreadRepository;
import com.mac2work.forumrestapi.response.ThreadResponse;

@ExtendWith(MockitoExtension.class)
class ThreadServiceTest {
	
	@Mock
	private ThreadRepository threadRepository;
	@Mock 
	private BookRepository bookRepository;
	@Mock
	private UserService userService;
	@Mock
	private AuthorizationService authorizationService;
	
	@InjectMocks
	private ThreadService threadService;
	
	private Book book;
	private User user;
	private Thread thread;
	private Thread thread2;

	@BeforeEach
	void setUp() throws Exception {
		book = Book.builder()
				.id(1)
				.name("The Hobbit")
				.publicationYear(1937)
				.description("The bedtime story for his children famously begun on the blank page of an exam script that tells the tale of Bilbo Baggins and the dwarves in their quest to take back the Lonely Mountain from Smaug the dragon")
				.build();
		user = User.builder()
				.id(1)
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
				.password("Password123")
				.role(Role.ADMIN)
				.build();		
		thread = Thread.builder()
				.id(1)
				.name("Bilbo returning to the Shire")
				.book(book)
				.user(user)
				.content("What Bilbo find upon when he returned to the Shire")
				.build();
		thread2 = Thread.builder()
				.id(2)
				.name("Bilbo entering the lonely mountain")
				.book(book)
				.user(user)
				.content("What Bilbo find upon when he entered Erebor")
				.build();
	}

	@Test
	final void testGetThreads() {
		List<Thread> threads = List.of(thread, thread2);
		when(threadRepository.findAll()).thenReturn(threads);
		
		List<ThreadResponse> threadResponses = threadService.getThreads();
		
		assertThat(threadResponses).isNotNull();
		assertThat(threadResponses.size()).isGreaterThan(1);
	}

	@Test
	final void testGetSpecificThread() {
		ThreadService threadServiceSpy = Mockito.spy(threadService);
		doReturn(thread).when(threadServiceSpy).getThread(thread.getId());
		
		ThreadResponse threadResponse = threadServiceSpy.getSpecificThread(thread.getId());
		
		assertThat(threadResponse).isNotNull();
	}

	@Test
	final void testGetSpecificBookThreads() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testAddThread() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testUpdateThread() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testDeleteThread() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetThread() {
		fail("Not yet implemented"); // TODO
	}

}
