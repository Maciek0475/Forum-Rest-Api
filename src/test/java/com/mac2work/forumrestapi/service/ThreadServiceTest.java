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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.repository.ThreadRepository;
import com.mac2work.forumrestapi.request.ThreadRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
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
	private ThreadRequest threadRequest;

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
		threadRequest = ThreadRequest.builder()
				.name("Bilbo entering the lonely mountain")
				.bookId(book.getId())
				.content("What Bilbo find upon when he entered Erebor")
				.build();
	}

	@Test
	final void threadService_getThreads_ReturnMoreThanOneThreadResponse() {
		List<Thread> threads = List.of(thread, thread2);
		when(threadRepository.findAll()).thenReturn(threads);
		
		List<ThreadResponse> threadResponses = threadService.getThreads();
		
		assertThat(threadResponses).isNotNull();
		assertThat(threadResponses.size()).isGreaterThan(1);
	}

	@Test
	final void threadService_getSpecificThread_ReturnThreadResponseNotNull() {
		ThreadService threadServiceSpy = Mockito.spy(threadService);
		doReturn(thread).when(threadServiceSpy).getThread(thread.getId());
		
		ThreadResponse threadResponse = threadServiceSpy.getSpecificThread(thread.getId());
		
		assertThat(threadResponse).isNotNull();
	}

	@Test
	final void threadService_getSpecificBookThreads_ReturnMoreThanOneThreadResponse() {
		List<Thread> threads = List.of(thread, thread2);
		when(threadRepository.findAllByBookId(book.getId())).thenReturn(Optional.of(threads));
		
		List<ThreadResponse> threadResponses = threadService.getSpecificBookThreads(book.getId());
		
		assertThat(threadResponses).isNotNull();
		assertThat(threadResponses.size()).isGreaterThan(1);
	}
	
	@Test
	final void threadService_getSpecificBookThreads_ThrowResourceNotFoundException() {
		List<Thread> threads = null;
		when(threadRepository.findAllByBookId(book.getId())).thenReturn(Optional.ofNullable(threads));
		
		Exception exception = assertThrows(
				ResourceNotFoundException.class,
				() -> { threadService.getSpecificBookThreads(book.getId()); });
		
		assertThat(exception).isNotNull();
	}

	@Test
	final void threadService_addThread_ReturnThreadResponseNotNull() {
		when(bookRepository.findById(threadRequest.getBookId())).thenReturn(Optional.of(book));
		when(userService.getLoggedInUser()).thenReturn(user);
		doReturn(null).when(threadRepository).save(Mockito.any(Thread.class));
		
		ThreadResponse threadResponse = threadService.addThread(threadRequest);
		
		assertThat(threadResponse).isNotNull();
	}

	@Test
	final void threadService_updateThread_ReturnThreadResponseNotNull() {
		int id = 1;
		ThreadService threadServiceSpy = Mockito.spy(threadService);
		doReturn(thread).when(threadServiceSpy).getThread(thread.getId());
		when(bookRepository.findById(threadRequest.getBookId())).thenReturn(Optional.of(book));
		when(authorizationService.isCorrectUser(user.getId(), "thread")).thenReturn(true);
		doReturn(null).when(threadRepository).save(Mockito.any(Thread.class));
		
		ThreadResponse threadResponse = threadServiceSpy.updateThread(id, threadRequest);

		assertThat(threadResponse).isNotNull();
	}

	@Test
	final void threadService_deleteThread_ReturnApiResponseNotNull() {
		ThreadService threadServiceSpy = Mockito.spy(threadService);
		doReturn(thread).when(threadServiceSpy).getThread(thread.getId());
		when(authorizationService.isCorrectUser(user.getId(), "thread")).thenReturn(true);
		doNothing().when(threadRepository).delete(thread);
		
		ApiResponse apiResponse = threadServiceSpy.deleteThread(thread.getId());
		
		assertThat(apiResponse).isNotNull();
		
	}

	@Test
	final void threadService_GetThread_ReturnThreadNotNull() {
		when(threadRepository.findById(thread.getId())).thenReturn(Optional.of(thread));
		
		Thread findedThread = threadService.getThread(thread.getId());
		
		assertThat(findedThread).isNotNull();
	}
	
	@Test
	final void threadService_GetThread_ThrowResourceNotFoundException() {
		int id = 1;
		Thread emptyThread = null;
		when(threadRepository.findById(id)).thenReturn(Optional.ofNullable(emptyThread));
		
		Exception exception = assertThrows(
				ResourceNotFoundException.class,
				() -> { threadService.getThread(id); });
		
		assertThat(exception).isNotNull();
	}

}
