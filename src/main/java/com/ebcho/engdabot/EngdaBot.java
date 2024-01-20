package com.ebcho.engdabot;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EngdaBot extends TelegramLongPollingBot {

	private static final String API_URL = "https://api.openai.com/v1/chat/completions";
	private static String API_KEY;

	public EngdaBot(String botToken) {
		super(botToken);
	}

	public void setApiKey(String apiKey) {
		this.API_KEY = apiKey;
	}

	@Override
	public void onUpdateReceived(Update update) {
		// 메시지가 있는지 확인
		if (update.hasMessage()) {
			Message message = update.getMessage();

			// 메시지에 텍스트가 있는지 확인
			if (message.hasText()) {
				String receivedText = message.getText();

				// "첨삭"으로 시작하는 메시지에 대해 로그 남기기
				if (receivedText.startsWith("첨삭")) {
					log.info("첨삭 메시지 수신: " + receivedText);

					// gpt로 첨삭 받기
					String result = editByOpenAI(receivedText);

					// 사용자에게 특별한 응답 보내기
					sendResponse(message, "첨삭 결과 \n\n" + result);
				} else {
					// 일반 응답 보내기
					sendResponse(message, "첨삭을 원하시면 '첨삭 ' + 내용을 작성해주세요.");
				}
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

		// HttpClient 인스턴스 생성
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
					String res = parseResult(result);
					return res;
				}
			}
		} catch (Exception e) {
			log.warn("editByOpenAI failed", e);
		}
		return "";
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

	private void sendResponse(Message message, String response) {
		SendMessage sendMessageRequest = new SendMessage();
		sendMessageRequest.setChatId(message.getChatId().toString());
		sendMessageRequest.setText(response);
		try {
			execute(sendMessageRequest);
		} catch (TelegramApiException e) {
			log.warn("sendResponse error : ", e);
		}
	}

	@Override
	public String getBotUsername() {
		return "engda-bot";
	}

}
