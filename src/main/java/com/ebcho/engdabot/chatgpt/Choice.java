package com.ebcho.engdabot.chatgpt;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Choice(@JsonProperty("index") int index,
					 @JsonProperty("message") Message message,
					 @JsonProperty("logprobs") Object logprobs,
					 @JsonProperty("finish_reason") String finishReason) {
}
