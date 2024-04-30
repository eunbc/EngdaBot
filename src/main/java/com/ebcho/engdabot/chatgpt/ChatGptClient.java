package com.ebcho.engdabot.chatgpt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ChatGptClient {
	private final RestClient restClient;

	public ChatGptClient(RestClient.Builder builder, @Value("${openai.api-key}") String apiKey) {
		System.out.println("[apiKey] : " + apiKey);
		this.restClient = builder
			.baseUrl("https://api.openai.com")
			.defaultHeader("Authorization", "Bearer " + apiKey)
			.build();
	}

	public ChatCompletionResponse chatCompletions(ChatCompletionRequest request) {
		return restClient.post()
			.uri("/v1/chat/completions")
			.body(request)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(ChatCompletionResponse.class);
	}
}
