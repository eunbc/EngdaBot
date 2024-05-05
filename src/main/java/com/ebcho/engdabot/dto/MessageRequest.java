package com.ebcho.engdabot.dto;

import com.ebcho.engdabot.telegram.Update;

public record MessageRequest(Integer chatId, String message) {

	public static MessageRequest from(Update update) {
		return new MessageRequest(update.message().from().id(), update.message().text());
	}
}
