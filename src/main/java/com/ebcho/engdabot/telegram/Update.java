package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Update(
	@JsonProperty("update_id") Integer updateId,
	@JsonProperty("message") Message message,
	@JsonProperty("my_chat_member") ChatMemberUpdated myChatMember
) {
	public boolean hasMessage() {
		return message != null && message.hasText();
	}

	public boolean hasMyChatMember() {
		return myChatMember != null;
	}
}
