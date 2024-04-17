package com.ebcho.engdabot.service;

import org.springframework.stereotype.Service;

import com.ebcho.engdabot.chatgpt.ChatCompletionRequest;
import com.ebcho.engdabot.chatgpt.ChatCompletionResponse;
import com.ebcho.engdabot.chatgpt.ChatGptClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatGptService {

	private final ChatGptClient chatGptClient;

	public String correctEnglishText(String message) {
		String prompt = "Please proofread and correct the English text: " + message;
		ChatCompletionResponse response = chatGptClient.chatCompletions(ChatCompletionRequest.requestBuilder(prompt));
		return response.getChoices().get(0).message().content();
	}
}
