package com.mac2work.forumrestapi.response;

import com.mac2work.forumrestapi.model.Book;
import com.mac2work.forumrestapi.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadResponse {
	
	private Integer id;
	private String name;
	private Book book;
	private User user;
	private String content;

}
