package com.ebcho.engdabot.telegram;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TelegramClientIntegrationTest {

	@Autowired
	TelegramClient telegramClient;

	@Test
	void sendMessage() {
		String chatId = "";
		SendMessageRequest request = new SendMessageRequest(chatId, "Hello this is test message from Engdabot.");
		var result = telegramClient.sendMessage(request);
		System.out.println(result);
	}
}
