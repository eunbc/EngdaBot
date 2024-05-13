package com.ebcho.engdabot.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.ebcho.engdabot.dto.MessageRequest;
import com.ebcho.engdabot.service.MessageService;

@WebMvcTest(WebhookController.class)
class WebhookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageService messageService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	void setUp() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
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
			.andExpect(result -> verify(messageService).readMessageAndCorrectText(any(MessageRequest.class)));
	}

	@Test
	void whenUpdateWithoutMessage_thenThrowsException() throws Exception {
		// Arrange
		String updateJson = "{}";

		// Act & Assert
		mockMvc.perform(post("/api/v1/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateJson))
			.andExpect(status().isServiceUnavailable())
			.andExpect(result -> verify(messageService, times(0)).readMessageAndCorrectText(any(MessageRequest.class)));
	}

}
