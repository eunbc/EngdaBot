package com.ebcho.engdabot.chatgpt;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatCompletionResponse {
	@JsonProperty("id")
	private String id;
	@JsonProperty("object")
	private String object;
	@JsonProperty("created")
	private long created;
	@JsonProperty("model")
	private String model;
	@JsonProperty("system_fingerprint")
	private String systemFingerprint;
	@JsonProperty("choices")
	private List<Choice> choices;
	@JsonProperty("usage")
	private Usage usage;

	public record Choice(@JsonProperty("index") int index,
						 @JsonProperty("message") Message message,
						 @JsonProperty("logprobs") Object logprobs,
						 @JsonProperty("finish_reason") String finishReason) {
	}

	public record Message(@JsonProperty("role") String role,
						  @JsonProperty("content") String content) {
	}

	public record Usage(@JsonProperty("prompt_tokens") int promptTokens,
						@JsonProperty("completion_tokens") int completionTokens,
						@JsonProperty("total_tokens") int totalTokens) {
	}
}
