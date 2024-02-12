package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(
	@JsonProperty("message_id") Integer messageId,
	@JsonProperty("from") User from,
	@JsonProperty("chat") Chat chat,
	@JsonProperty("date") Integer date,
	@JsonProperty("text") String text
) {

	public boolean hasText() {
		return text != null;
	}
}
