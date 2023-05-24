package com.mac2work.forumrestapi.response;

import com.mac2work.forumrestapi.model.Thread;
import com.mac2work.forumrestapi.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class MessageResponse {

	private Thread thread;
	private User user;
	private String content;
}
