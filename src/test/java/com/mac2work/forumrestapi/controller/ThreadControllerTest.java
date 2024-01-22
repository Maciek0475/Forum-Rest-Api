package com.mac2work.forumrestapi.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.Role;
import com.mac2work.forumrestapi.model.User;
import com.mac2work.forumrestapi.request.ThreadRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.ThreadResponse;
import com.mac2work.forumrestapi.service.ThreadService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ThreadController.class)
class ThreadControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ThreadService threadService;
	
	private Book book;
	private User user;
	private ThreadResponse threadResponse;
	private ThreadResponse threadResponse2;
	private ThreadRequest threadRequest;
	private ApiResponse apiResponse;
	private int id;

	@BeforeEach
	void setUp() throws Exception {
		id = 1;
		
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
		threadRequest = ThreadRequest.builder()
				.name("Bilbo entering the lonely mountain")
				.bookId(book.getId())
				.content("What Bilbo find upon when he entered Erebor")
				.build();
		threadResponse = ThreadResponse.builder()
				.name("Bilbo returning to the Shire")
				.book(book)
				.user(user)
				.content("What Bilbo find upon when he returned to the Shire")
				.build();
		threadResponse2 = ThreadResponse.builder()
				.name("Bilbo entering the lonely mountain")
				.book(book)
				.user(user)
				.content("What Bilbo find upon when he entered Erebor")
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Thread deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

	@Test
	final void threadController_testGetThreads_ReturnListOfThreadResponses() throws Exception {
		List<ThreadResponse> threadResponses = List.of(threadResponse, threadResponse2);
		when(threadService.getThreads()).thenReturn(threadResponses);
		
		ResultActions response = mockMvc.perform(get("/threads")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(threadResponses.size())));
	}

	@Test
	final void threadController_testGetSpecificThread_ReturnThreadResponse() throws Exception {
		when(threadService.getSpecificThread(id)).thenReturn(threadResponse);
		
		ResultActions response = mockMvc.perform(get("/threads/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", CoreMatchers.is(threadResponse.getName())))
				.andExpect(jsonPath("$.book", CoreMatchers.is(objectMapper.readValue(
						objectMapper.writeValueAsString(threadResponse.getBook()), Object.class))))
				.andExpect(jsonPath("$.user", CoreMatchers.is(objectMapper.readValue(
						objectMapper.writeValueAsString(threadResponse.getUser()), Object.class))))
				.andExpect(jsonPath("$.content", CoreMatchers.is(threadResponse.getContent())));
	}

	@Test
	final void threadController_testGetSpecificBookThreads_ReturnListOfThreadResponses() throws Exception {
		List<ThreadResponse> threadResponses = List.of(threadResponse, threadResponse2);
		when(threadService.getSpecificBookThreads(id)).thenReturn(threadResponses);

		ResultActions response = mockMvc.perform(get("/books/"+id+"/threads")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(threadResponses.size())));
	}

	@Test
	final void threadController_testAddThread_ReturnThreadResponse() throws Exception {
		when(threadService.addThread(threadRequest)).thenReturn(threadResponse);

		ResultActions response = mockMvc.perform(post("/threads")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(threadRequest)));

		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", CoreMatchers.is(threadResponse.getName())))
				.andExpect(jsonPath("$.book", CoreMatchers.is(objectMapper.readValue(
						objectMapper.writeValueAsString(threadResponse.getBook()), Object.class))))
				.andExpect(jsonPath("$.user", CoreMatchers.is(objectMapper.readValue(
						objectMapper.writeValueAsString(threadResponse.getUser()), Object.class))))
				.andExpect(jsonPath("$.content", CoreMatchers.is(threadResponse.getContent())));
	}

	@Test
	final void threadController_testUpdateThread_ReturnThreadResponse() throws Exception {
		when(threadService.updateThread(id, threadRequest)).thenReturn(threadResponse);
		
		ResultActions response = mockMvc.perform(put("/threads/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(threadRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", CoreMatchers.is(threadResponse.getName())))
				.andExpect(jsonPath("$.book", CoreMatchers.is(objectMapper.readValue(
						objectMapper.writeValueAsString(threadResponse.getBook()), Object.class))))
				.andExpect(jsonPath("$.user", CoreMatchers.is(objectMapper.readValue(
						objectMapper.writeValueAsString(threadResponse.getUser()), Object.class))))
				.andExpect(jsonPath("$.content", CoreMatchers.is(threadResponse.getContent())));
	}

	@Test
	final void threadController_testDeleteThread_ReturnApiResponse() throws Exception {
		when(threadService.deleteThread(id)).thenReturn(apiResponse);
		
		ResultActions response = mockMvc.perform(delete("/threads/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isNoContent())
		.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
		.andExpect(jsonPath("$.responseMessage", CoreMatchers.is(apiResponse.getResponseMessage())))
		.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
	}

}
