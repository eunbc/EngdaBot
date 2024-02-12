package com.ebcho.engdabot.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePollingService {

	private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot";
	private static final String API_URL = "https://api.openai.com/v1/chat/completions";
	private final RestTemplate restTemplate;
	@Value("${openai.api-key}")
	private String API_KEY;
	@Value("${telegram.bot-token}")
	private String botToken;

	@Async
	public void startPolling() {
		int lastUpdateId = 0;
		final int timeout = 30; // long-polling 주기

		while (true) {
			String requestUrl =
				TELEGRAM_API_URL + botToken + "/getUpdates?offset=" + (lastUpdateId + 1) + "&timeout=" + timeout;

			try {
				TelegramResponse response = restTemplate.getForObject(requestUrl, TelegramResponse.class);
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

	private void handleUpdate(Update update) {
		if (update.hasMessage()) {
			Message message = update.message();
			if (message.hasText()) {
				String receivedText = message.text();
				String result = editByOpenAI(receivedText);
				sendResponse(message, "첨삭 결과 \n\n" + result);
			}
		}
	}

	private String editByOpenAI(String receivedText) {
		String processedText = receivedText.replace(System.lineSeparator(), "\\n");
		String prompt = "Please proofread and correct the English text: '" + processedText + "'";
		String jsonBody = "{"
			+ "\"model\": \"gpt-3.5-turbo\","
			+ "\"messages\": ["
			+ "  {"
			+ "    \"role\": \"user\","
			+ "    \"content\": \"" + prompt + "\""
			+ "  }"
			+ "]"
			+ "}";

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// POST 요청 생성
			HttpPost request = new HttpPost(API_URL);
			request.setHeader("Authorization", "Bearer " + API_KEY);
			request.setHeader("Content-Type", "application/json");

			// 요청 본문 설정
			request.setEntity(new StringEntity(jsonBody));

			// 요청 전송 및 응답 수신
			try (CloseableHttpResponse response = httpClient.execute(request)) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity);
					log.info(result);
					return parseResult(result);
				}
			}
		} catch (Exception e) {
			log.warn("editByOpenAI failed", e);
		}
		return "edit failed";
	}

	private String parseResult(String openAIResult) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(openAIResult);
			JsonNode choicesNode = rootNode.path("choices");
			JsonNode firstChoiceNode = choicesNode.get(0);
			JsonNode messageNode = firstChoiceNode.path("message");
			String content = messageNode.path("content").asText();

			return content;
		} catch (IOException e) {
			log.warn("parseResult error : ", e);
		}
		return "";
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

}
