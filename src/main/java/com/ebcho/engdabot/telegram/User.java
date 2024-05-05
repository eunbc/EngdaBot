package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record User(
	@JsonProperty("id") Integer id,
	@JsonProperty("is_bot") Boolean isBot,
	@JsonProperty("first_name") String firstName,
	@JsonProperty("username") String username,
	@JsonProperty("language_code") String languageCode
) {
}
