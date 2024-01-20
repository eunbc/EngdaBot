package com.ebcho.engdabot;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BatchController {

	private static final String BASE_URL = "https://api.telegram.org/bot";
	private static final String MESSAGE = "How was your day? \nWrite your English diary.";
	@Value("${telegram.bot-token}")
	private String BOT_TOKEN;
	@Value("${telegram.chat-id}")
	private String CHAT_ID;

	@GetMapping("/noti")
	public boolean sendNotification() {
		String encodedMessage = URLEncoder.encode(MESSAGE, StandardCharsets.UTF_8);
		String url = BASE_URL + BOT_TOKEN + "/sendMessage?chat_id=" + CHAT_ID + "&text=" + encodedMessage;

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.GET()
			.build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			log.info(response.body());
		} catch (Exception e) {
			log.error("message send failed : ", e);
		}
		return true;
	}

}
