package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SendMessageRequest(
	@JsonProperty("chat_id") String chatId,
	@JsonProperty("text") String text) {
}
