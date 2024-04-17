package com.ebcho.engdabot.chatgpt;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ChatCompletionRequest {
	@JsonProperty("model")
	private String model;

	@JsonProperty("messages")
	private List<Message> messages;

	private ChatCompletionRequest(String model, List<Message> messages) {
		this.model = model;
		this.messages = messages;
	}

	public static ChatCompletionRequest requestBuilder(String content) {
		String defaultModel = "gpt-3.5-turbo";
		Message message = new Message("user", content);
		return new ChatCompletionRequest(defaultModel, Collections.singletonList(message));
	}

	public record Message(@JsonProperty("role") String role,
						  @JsonProperty("content") String content) {
	}

}
