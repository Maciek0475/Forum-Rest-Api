package com.mac2work.forumrestapi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

	private String threadName;
	private String userName;
	private String content;
}
