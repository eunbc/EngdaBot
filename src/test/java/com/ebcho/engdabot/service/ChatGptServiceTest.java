package com.ebcho.engdabot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ebcho.engdabot.chatgpt.ChatCompletionRequest;
import com.ebcho.engdabot.chatgpt.ChatCompletionResponse;
import com.ebcho.engdabot.chatgpt.ChatGptClient;

class ChatGptServiceTest {

	@Mock
	private ChatGptClient chatGptClient;

	@InjectMocks
	private ChatGptService chatGptService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

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
		String result = chatGptService.correctEnglishText(originalText);

		// Assert
		assertEquals(correctedText, result, "The corrected text should match the expected text.");
	}
}
