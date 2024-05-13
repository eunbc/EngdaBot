package com.ebcho.engdabot.chatgpt;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatCompletionResponse(
	@JsonProperty("id")
	String id,
	@JsonProperty("object")
	String object,
	@JsonProperty("created")
	long created,
	@JsonProperty("model")
	String model,
	@JsonProperty("system_fingerprint")
	String systemFingerprint,
	@JsonProperty("choices")
	List<Choice> choices,
	@JsonProperty("usage")
	Usage usage) {

	public String getResponse() {
		return this.choices().get(0).message().content();
	}

}


