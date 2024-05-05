package com.ebcho.engdabot.dto;

import com.ebcho.engdabot.telegram.Update;

public record MessageRequest(Long chatId, String firstName, String message) {

	public static MessageRequest from(Update update) {
		return new MessageRequest(
			update.message().from().id(),
			update.message().from().firstName(),
			update.message().text());
	}
}
