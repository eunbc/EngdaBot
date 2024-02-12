package com.ebcho.engdabot.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIService {

	private static final String API_URL = "https://api.openai.com/v1/chat/completions";
	@Value("${openai.api-key}")
	private String API_KEY;

	public String editByOpenAI(String receivedText) {
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
}
