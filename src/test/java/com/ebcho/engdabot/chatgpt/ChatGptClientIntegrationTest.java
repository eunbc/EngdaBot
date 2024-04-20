package com.ebcho.engdabot.chatgpt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
class ChatGptClientIntegrationTest {

	@Autowired
	ChatGptClient chatGptClient;

	@Test
	void chatCompletions() throws JsonProcessingException {
		// given
		ChatCompletionResponse response = new ChatCompletionResponse();
		ChatCompletionRequest request = ChatCompletionRequest.requestBuilder(
			"Please proofread and correct the English text: 'who is thet doctor? u kno?'");

		// then
		var result = chatGptClient.chatCompletions(request);
		System.out.println(result);
	}
}
