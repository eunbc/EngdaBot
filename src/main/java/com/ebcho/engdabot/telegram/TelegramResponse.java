package com.ebcho.engdabot.telegram;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TelegramResponse(
	@JsonProperty("ok") boolean ok,
	@JsonProperty("result") List<Update> result
) {
}
