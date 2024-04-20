package com.ebcho.engdabot.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TelegramClient {

	private final RestClient restClient;

	public TelegramClient(RestClient.Builder builder, @Value("${telegram.bot-token}") String botToken) {
		this.restClient = builder
			.baseUrl("https://api.telegram.org/bot" + botToken)
			.build();
	}

	public String sendMessage(SendMessageRequest request) {
		return restClient.post()
			.uri("/sendMessage")
			.body(request)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(String.class);
	}

}
