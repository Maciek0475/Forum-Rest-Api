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
import org.springframework.http.HttpStatus;

import com.mac2work.forumrestapi.exception.ResourceNotFoundException;
import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.BookRepository;
import com.mac2work.forumrestapi.repository.ThreadRepository;
import com.mac2work.forumrestapi.request.ThreadRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.BookResponse;
import com.mac2work.forumrestapi.response.ThreadResponse;
import com.mac2work.forumrestapi.response.UserResponse;

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
	@Mock
	private BookService bookService;
	
	@InjectMocks
	private ThreadService threadService;
	
	private Book book;
	private User user;
	private BookResponse bookResponse;
	private UserResponse userResponse;
	private Thread thread;
	private Thread thread2;
	private ThreadRequest threadRequest;
	private ThreadResponse threadResponse;
	private ApiResponse apiResponse;

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
		bookResponse = BookResponse.builder()
				.name("The Hobbit")
				.publication_year(1937)
				.description("The bedtime story for his children famously begun on the blank page of an exam script that tells the tale of Bilbo Baggins and the dwarves in their quest to take back the Lonely Mountain from Smaug the dragon")
				.build();
		userResponse = UserResponse.builder()
				.firstName("Maciej")
				.lastName("Jurczak")
				.email("mac2work@o2.pl")
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
				.name("Bilbo returning to the Shire")
				.bookId(book.getId())
				.content("What Bilbo find upon when he returned to the Shire")
				.build();
		threadResponse = ThreadResponse.builder()
				.name("Bilbo returning to the Shire")
				.book(bookResponse)
				.user(userResponse)
				.content("What Bilbo find upon when he returned to the Shire")
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Thread deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}

	@Test
	final void threadService_getThreads_ReturnMoreThanOneThreadResponse() {
		List<Thread> threads = List.of(thread, thread2);
		when(threadRepository.findAll()).thenReturn(threads);
		when(bookService.mapToBookResponse(Mockito.any(Book.class))).thenReturn(bookResponse);
		
		List<ThreadResponse> threadResponses = threadService.getThreads();
		
		assertThat(threadResponses).isNotNull();
		assertThat(threadResponses.size()).isGreaterThan(1);
	}

	@Test
	final void threadService_getSpecificThread_ReturnThreadResponse() {
		ThreadService threadServiceSpy = Mockito.spy(threadService);
		doReturn(thread).when(threadServiceSpy).getThread(thread.getId());
		when(bookService.mapToBookResponse(Mockito.any(Book.class))).thenReturn(bookResponse);
		when(userService.mapToUserResponse(Mockito.any(User.class))).thenReturn(userResponse);
		
		ThreadResponse threadResponse = threadServiceSpy.getSpecificThread(thread.getId());
		
		assertThat(threadResponse).isEqualTo(this.threadResponse);
	}

	@Test
	final void threadService_getSpecificBookThreads_ReturnMoreThanOneThreadResponse() {
		List<Thread> threads = List.of(thread, thread2);
		when(threadRepository.findAllByBookId(book.getId())).thenReturn(Optional.of(threads));
		when(bookService.mapToBookResponse(Mockito.any(Book.class))).thenReturn(bookResponse);
		
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
	final void threadService_addThread_ReturnThreadResponse() {
		when(bookRepository.findById(threadRequest.getBookId())).thenReturn(Optional.of(book));
		when(userService.getLoggedInUser()).thenReturn(user);
		doReturn(null).when(threadRepository).save(Mockito.any(Thread.class));
		when(bookService.mapToBookResponse(Mockito.any(Book.class))).thenReturn(bookResponse);
		when(userService.mapToUserResponse(Mockito.any(User.class))).thenReturn(userResponse);


		
		ThreadResponse threadResponse = threadService.addThread(threadRequest);
		
		assertThat(threadResponse).isEqualTo(this.threadResponse);
	}

	@Test
	final void threadService_updateThread_ReturnThreadResponse() {
		int id = 2;
		ThreadService threadServiceSpy = Mockito.spy(threadService);
		doReturn(thread2).when(threadServiceSpy).getThread(thread2.getId());
		when(bookRepository.findById(threadRequest.getBookId())).thenReturn(Optional.of(book));
		when(authorizationService.isCorrectUser(user.getId(), "thread")).thenReturn(true);
		doReturn(thread).when(threadRepository).save(Mockito.any(Thread.class));
		when(bookService.mapToBookResponse(Mockito.any(Book.class))).thenReturn(bookResponse);
		when(userService.mapToUserResponse(Mockito.any(User.class))).thenReturn(userResponse);

		ThreadResponse threadResponse = threadServiceSpy.updateThread(id, threadRequest);

		assertThat(threadResponse).isEqualTo(this.threadResponse);
	}

	@Test
	final void threadService_deleteThread_ReturnApiResponse() {
		ThreadService threadServiceSpy = Mockito.spy(threadService);
		doReturn(thread).when(threadServiceSpy).getThread(thread.getId());
		when(authorizationService.isCorrectUser(user.getId(), "thread")).thenReturn(true);
		doNothing().when(threadRepository).delete(thread);
		
		ApiResponse apiResponse = threadServiceSpy.deleteThread(thread.getId());
		
		assertThat(apiResponse).isEqualTo(this.apiResponse);
		
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
