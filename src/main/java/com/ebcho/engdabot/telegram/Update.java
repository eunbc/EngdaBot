package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Update(
	@JsonProperty("update_id") Integer updateId,
	@JsonProperty("message") Message message
) {
	public boolean hasMessage() {
		return message != null;
	}
}
