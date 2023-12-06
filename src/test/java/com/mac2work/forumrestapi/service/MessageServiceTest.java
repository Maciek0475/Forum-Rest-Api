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
import com.mac2work.forumrestapi.model.Message;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.repository.MessageRepository;
import com.mac2work.forumrestapi.request.MessageRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.MessageResponse;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
	
	@Mock
	private ThreadService threadService;
	@Mock
	private UserService userService;
	@Mock
	private AuthorizationService authorizationService;
	@Mock
	private MessageRepository messageRepository;
	
	@InjectMocks
	private MessageService messageService;
	
	private Book book;
	private User user;
	private Thread thread;
	private Message message;
	private Message message2;
	private MessageRequest messageRequest;

	@BeforeEach
	void setUp() throws Exception {
		book = Book.builder()
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
		message = Message.builder()
				.id(1)
				.thread(thread)
				.user(user)
				.content("Bilbo receives a nasty suprise. He has been presumed dead, and the contents of his hill are being auctioned off")
				.build();
		message2 = Message.builder()
				.id(2)
				.thread(thread)
				.user(user)
				.content("Bilbo is never again really accepted by the other hobbits")
				.build();
		messageRequest = MessageRequest.builder()
				.content("Bilbo receives a nasty suprise. He has been presumed dead, and the contents of his hill are being auctioned off")
				.build();
	}

	@Test
	final void messageService_getThreadMessages_ReturnMoreThanOneMessageResponse() {
		List<Message> messages = List.of(message, message2);
		when(messageRepository.findAllByThreadId(thread.getId())).thenReturn(Optional.of(messages));
		
		List<MessageResponse> messageResponses = messageService.getThreadMessages(thread.getId());
		
		assertThat(messageResponses).isNotNull();
		assertThat(messageResponses.size()).isGreaterThan(1);
		
	}

	@Test
	final void messageService_addThreadMessage_ReturnMessageResponseNotNull() {
		when(userService.getLoggedInUser()).thenReturn(user);
		when(threadService.getThread(thread.getId())).thenReturn(thread);
		doReturn(null).when(messageRepository).save(Mockito.any(Message.class));
		
		MessageResponse messageResponse = messageService.addThreadMessage(messageRequest, thread.getId());
		
		assertThat(messageResponse).isNotNull();
	}

	@Test
	final void messageService_updateMessage_ReturnMessageResponseNotNull() {
		when(authorizationService.isCorrectUser(user.getId(), "message")).thenReturn(true);
		doReturn(null).when(messageRepository).save(Mockito.any(Message.class));
		when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

		MessageResponse messageResponse = messageService.updateMessage(messageRequest, message.getId());
		
		assertThat(messageResponse).isNotNull();
	}
	
	@Test
	final void messageService_updateMessage_ThrowResourceNotFoundException() {
		int id = 3;
		Message emptyMessage = null;
		when(messageRepository.findById(id)).thenReturn(Optional.ofNullable(emptyMessage));

		Exception exception = assertThrows(
				ResourceNotFoundException.class,
				() -> { messageService.updateMessage(messageRequest, id); });
		
		assertThat(exception).isNotNull();
	}

	@Test
	final void messageService_deleteMessage_ReturnApiResponseNotNull() {
		when(authorizationService.isCorrectUser(user.getId(), "message")).thenReturn(true);
		doNothing().when(messageRepository).delete(Mockito.any(Message.class));
		when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

		ApiResponse apiResponse = messageService.deleteMessage(message.getId());
		
		assertThat(apiResponse).isNotNull();
	}

}
