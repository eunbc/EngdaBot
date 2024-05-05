package com.ebcho.engdabot.dto;

import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.telegram.ChatMember;
import com.ebcho.engdabot.telegram.Update;

public record RegisterUserRequest(
	Integer id,
	String firstName,
	ChatMember.Status status
) {

	public static RegisterUserRequest from(Update update) {
		return new RegisterUserRequest(
			update.myChatMember().from().id(),
			update.myChatMember().from().firstName(),
			update.myChatMember().newChatMember().status()
		);
	}

	public TelegramUser toEntity() {
		return new TelegramUser(id, firstName);
	}
}
