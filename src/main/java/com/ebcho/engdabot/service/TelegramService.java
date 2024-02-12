package com.ebcho.engdabot.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ebcho.engdabot.telegram.Message;
import com.ebcho.engdabot.telegram.TelegramResponse;
import com.ebcho.engdabot.telegram.Update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {

	private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot";
	private final RestTemplate restTemplate;
	private final OpenAIService openAIService;
	@Value("${telegram.bot-token}")
	private String botToken;

	@Async
	public void startPolling() {
		int lastUpdateId = 0;
		final int timeout = 30; // long-polling 주기

		while (true) {
			try {
				TelegramResponse response = getUpdates(lastUpdateId, timeout);
				assert response != null;
				if (response.ok() && response.result() != null) {
					for (Update update : response.result()) {
						handleUpdate(update);
						lastUpdateId = update.updateId();
					}
				}
			} catch (Exception e) {
				log.warn("polling failed : ", e);
			}
		}
	}

	public TelegramResponse getUpdates(int lastUpdateId, int timeout) {
		String requestUrl =
			TELEGRAM_API_URL + botToken + "/getUpdates?offset=" + (lastUpdateId + 1) + "&timeout=" + timeout;
		return restTemplate.getForObject(requestUrl, TelegramResponse.class);
	}

	public void sendResponse(Message message, String responseText) {
		// Telegram Bot API의 sendMessage 엔드포인트 URL 구성
		String sendMessageUrl = TELEGRAM_API_URL + botToken + "/sendMessage";

		// 요청 본문을 구성할 Map 생성
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("chat_id", message.chat().id().toString());
		requestBody.put("text", responseText);

		// HttpHeaders 객체 생성 및 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// HttpEntity 객체에 헤더와 요청 본문 설정
		org.springframework.http.HttpEntity<Map<String, Object>> requestEntity = new org.springframework.http.HttpEntity<>(
			requestBody, headers);

		try {
			// RestTemplate을 사용하여 HTTP POST 요청 실행
			ResponseEntity<String> response = restTemplate.postForEntity(sendMessageUrl, requestEntity, String.class);

			// 로그를 통해 응답 확인
			log.info("Message sent successfully: " + response.getBody());
		} catch (HttpClientErrorException e) {
			// HTTP 에러 처리
			log.error("Error sending message: ", e);
		}
	}

	private void handleUpdate(Update update) {
		if (update.hasMessage()) {
			Message message = update.message();
			if (message.hasText()) {
				String receivedText = message.text();
				String result = openAIService.editByOpenAI(receivedText);
				sendResponse(message, "첨삭 결과 \n\n" + result);
			}
		}
	}

}
