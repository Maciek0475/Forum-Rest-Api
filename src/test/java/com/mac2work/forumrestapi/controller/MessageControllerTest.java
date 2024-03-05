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
import com.mac2work.forumrestapi.request.MessageRequest;
import com.mac2work.forumrestapi.response.ApiResponse;
import com.mac2work.forumrestapi.response.MessageResponse;
import com.mac2work.forumrestapi.service.MessageService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MessageController.class)
class MessageControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private MessageService messageService;

	private MessageRequest messageRequest;
	private MessageResponse messageResponse;
	private MessageResponse messageResponse2;
	private ApiResponse apiResponse;
	private int id;
	
	@BeforeEach
	void setUp() throws Exception {
		
		messageRequest = MessageRequest.builder()
				.content("Bilbo receives a nasty suprise. He has been presumed dead, and the contents of his hill are being auctioned off")
				.build();
		messageResponse = MessageResponse.builder()
				.threadName("Bilbo returning to the Shire")
				.userName("Maciej")
				.content("Bilbo receives a nasty suprise. He has been presumed dead, and the contents of his hill are being auctioned off")
				.build();
		messageResponse2 = MessageResponse.builder()
				.threadName("Bilbo returning to the Shire")
				.userName("Maciej")
				.content("Bilbo is never again really accepted by the other hobbits")
				.build();
		apiResponse = ApiResponse.builder()
				.isSuccess(Boolean.TRUE)
				.responseMessage("Message deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
		id = 1;
		
	}

	@Test
	final void messageController_getThreadMessages_ReturnMessageResponses() throws Exception {
		List<MessageResponse> messageResponses = List.of(messageResponse, messageResponse2);
		when(messageService.getThreadMessages(id)).thenReturn(messageResponses);
		
		ResultActions response = mockMvc.perform(get("/threads/"+id+"/messages")
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", CoreMatchers.is(messageResponses.size())));
	}

	@Test
	final void messageController_addThreadMessage_ReturnMessageResponse() throws Exception {
		when(messageService.addThreadMessage(messageRequest, id)).thenReturn(messageResponse);
		
		ResultActions response = mockMvc.perform(post("/threads/"+id+"/messages")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(messageRequest)));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.content", CoreMatchers.is(messageResponse.getContent())));	
		}

	@Test
	final void messageController_updateMessage_ReturnMessageResponse() throws Exception {
		when(messageService.updateMessage(messageRequest, id)).thenReturn(messageResponse);
		
		ResultActions response = mockMvc.perform(put("/messages/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(messageRequest)));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", CoreMatchers.is(messageResponse.getContent())));	
		}

	@Test
	final void messageController_deleteMessage_ReturnApiResponse() throws Exception {
		when(messageService.deleteMessage(id)).thenReturn(apiResponse);
		
		ResultActions response = mockMvc.perform(delete("/messages/"+id)
				.contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(status().isOk())
				.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(apiResponse.getIsSuccess())))
				.andExpect(jsonPath("$.responseMessage", CoreMatchers.is(apiResponse.getResponseMessage())))
				.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(apiResponse.getHttpStatus().name())));
		}	

}
