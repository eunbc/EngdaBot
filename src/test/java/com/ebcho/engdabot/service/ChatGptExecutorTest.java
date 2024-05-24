package com.ebcho.engdabot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ebcho.engdabot.chatgpt.ChatCompletionRequest;
import com.ebcho.engdabot.chatgpt.ChatCompletionResponse;
import com.ebcho.engdabot.chatgpt.ChatGptClient;

@ExtendWith(MockitoExtension.class)
class ChatGptExecutorTest {

	@Mock
	private ChatGptClient chatGptClient;

	@InjectMocks
	private ChatGptExecutor chatGptExecutor;

	@Test
	@DisplayName("chatgpt로 영어 문장을 첨삭합니다")
	void testCorrectEnglishText() {
		// Arrange
		String originalText = "This is a test messssssage";
		String correctedText = "This is a test message";

		ChatCompletionResponse response = mock(ChatCompletionResponse.class);
		when(chatGptClient.chatCompletions(any(ChatCompletionRequest.class))).thenReturn(response);
		when(response.getResponse()).thenReturn(correctedText);

		// Act
		String result = chatGptExecutor.correct(originalText);

		// Assert
		assertEquals(correctedText, result, "The corrected text should match the expected text.");
	}
}
