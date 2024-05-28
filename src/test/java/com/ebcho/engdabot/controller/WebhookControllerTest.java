package com.ebcho.engdabot.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.service.MessageService;

@WebMvcTest(WebhookController.class)
@ExtendWith(MockitoExtension.class)
class WebhookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageService messageService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	@DisplayName("메시지가 있을 때 메시지 서비스를 호출합니다")
	void whenUpdateWithMessage_thenCallsMessageService() throws Exception {
		// Arrange
		String updateJson = """
			{"message":{"from":{"id":1,"firstName":"John","lastName":"Doe"},"text":"/start"}}
			""";

		// Act & Assert
		mockMvc.perform(post("/api/v1/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateJson))
			.andExpect(status().isOk())
			.andExpect(result -> verify(messageService).handleMessage(any(MessageRequest.class)));
	}

	@Test
	@DisplayName("메시지가 없을 경우 예외가 발생합니다")
	void whenUpdateWithoutMessage_thenThrowsException() throws Exception {
		// Arrange
		String updateJson = "{}";

		// Act & Assert
		mockMvc.perform(post("/api/v1/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateJson))
			.andExpect(status().isOk())
			.andExpect(result -> verify(messageService, times(0)).handleMessage(any(MessageRequest.class)));
	}

}
