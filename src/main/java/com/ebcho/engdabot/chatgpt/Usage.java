package com.ebcho.engdabot.chatgpt;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Usage(@JsonProperty("prompt_tokens") int promptTokens,
					@JsonProperty("completion_tokens") int completionTokens,
					@JsonProperty("total_tokens") int totalTokens) {
}
