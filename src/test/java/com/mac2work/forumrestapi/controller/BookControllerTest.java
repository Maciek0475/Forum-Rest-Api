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
import com.mac2work.forumrestapi.request.BookRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.BookResponse;
import com.mac2work.forumrestapi.service.AuthorizationService;
import com.mac2work.forumrestapi.service.BookService;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private BookService bookService;
	@MockBean
	private AuthorizationService authorizationService;
	
	private BookResponse bookResponse;
	private BookResponse bookResponse2;
	private BookRequest bookRequest;
	private ApiResponse apiResponse;

	@BeforeEach
	void setUp() throws Exception {
		bookResponse = BookResponse.builder()
				.name("The Hobbit")
				.publication_year(1937)
				.description("The bedtime story for his children famously begun on the blank page of an exam script that tells the tale of Bilbo Baggins and the dwarves in their quest to take back the Lonely Mountain from Smaug the dragon")
				.build();
		bookResponse2 = BookResponse.builder()
				.name("The Fellowship of the Ring")
				.publication_year(1954)
				.description("One of the world’s most famous books that continues the tale of the ring Bilbo found in The Hobbit and what comes next for it, him, and his nephew Frodo.")
				.build();
		bookRequest = BookRequest.builder()
				.name("The Hobbit")
				.publicationYear(1937)
				.description("The bedtime story for his children famously begun on the blank page of an exam script that tells the tale of Bilbo Baggins and the dwarves in their quest to take back the Lonely Mountain from Smaug the dragon")
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Book deleted successfully")
				.httpStatus(HttpStatus.NO_CONTENT)
				.build();
	}

	@Test
	final void BookController_getBooks_ReturnListOfBookResponses() throws Exception {
		List<BookResponse> bookResponses = List.of(bookResponse, bookResponse2);
		when(bookService.getBooks()).thenReturn(bookResponses);
		
		ResultActions response = mockMvc.perform(get("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bookResponses)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(bookResponses.size())));
	}

	@Test
	final void bookController_getSpecificBook_ReturnBookResponse() throws Exception {
		int id = 1;
		when(bookService.getSpecificBook(id)).thenReturn(bookResponse);
		
		ResultActions response = mockMvc.perform(get("/books/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", CoreMatchers.is(bookResponse.getName())))
				.andExpect(jsonPath("$.publication_year", CoreMatchers.is(bookResponse.getPublication_year())))
				.andExpect(jsonPath("$.description", CoreMatchers.is(bookResponse.getDescription())));
	}

	@Test
	final void bookController_addBook_ReturnBookResponse() throws Exception {
		when(bookService.addBook(bookRequest)).thenReturn(bookResponse);
		
		ResultActions response = mockMvc.perform(post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bookRequest)));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", CoreMatchers.is(bookResponse.getName())))
				.andExpect(jsonPath("$.publication_year", CoreMatchers.is(bookResponse.getPublication_year())))
				.andExpect(jsonPath("$.description", CoreMatchers.is(bookResponse.getDescription())));
	}

	@Test
	final void bookController_testUpdateBook_ReturnBookResponse() throws Exception {
		int id = 1;
		when(bookService.updateBook(id, bookRequest)).thenReturn(bookResponse);
		
		ResultActions response = mockMvc.perform(put("/books/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bookRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", CoreMatchers.is(bookResponse.getName())))
				.andExpect(jsonPath("$.publication_year", CoreMatchers.is(bookResponse.getPublication_year())))
				.andExpect(jsonPath("$.description", CoreMatchers.is(bookResponse.getDescription())));	
		}

	@Test
	final void bookController_deleteBook_ReturnApiResponse() throws Exception {
		int id = 1;
		when(bookService.deleteBook(id)).thenReturn(apiResponse);
		
		ResultActions response = mockMvc.perform(delete("/books/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
				.andExpect(jsonPath("$.responseMessage", CoreMatchers.is(apiResponse.getResponseMessage())))
				.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
	}

}
