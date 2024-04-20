package com.ebcho.engdabot.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ebcho.engdabot.dto.MessageRequest;

class MessageServiceTest {

	static private ChatGptService chatGptService;
	static private MessageService messageService;
	static private TelegramService telegramService;

	@BeforeAll
	static void setUp() {
		chatGptService = mock(ChatGptService.class);
		telegramService = mock(TelegramService.class);
		messageService = new MessageService(chatGptService, telegramService);
	}

	@Test
	void 메세지를_받고_첨삭한_결과를_리턴() {
		// given
		MessageRequest messageRequest = new MessageRequest(1L, "test message");

		// when
		boolean result = messageService.readMessageAndCorrectText(messageRequest);

		// then
		Assertions.assertTrue(result);
	}

}
