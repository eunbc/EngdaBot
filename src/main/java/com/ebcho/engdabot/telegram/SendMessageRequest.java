package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SendMessageRequest(
	@JsonProperty("chat_id") Long chatId,
	@JsonProperty("text") String text,
	@JsonProperty("parse_mode") String parseMode
) {
}
