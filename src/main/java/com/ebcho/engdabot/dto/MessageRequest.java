package com.ebcho.engdabot.dto;

import com.ebcho.engdabot.telegram.Update;

public record MessageRequest(Long chatId, String message) {

	public static MessageRequest from(Update update) {
		return new MessageRequest(update.message().chat().id(), update.message().text());
	}
}
