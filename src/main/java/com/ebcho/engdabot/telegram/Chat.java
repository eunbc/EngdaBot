package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Chat(
	@JsonProperty("id") Long id,
	@JsonProperty("first_name") String firstName,
	@JsonProperty("username") String username,
	@JsonProperty("type") String type
) {
}
