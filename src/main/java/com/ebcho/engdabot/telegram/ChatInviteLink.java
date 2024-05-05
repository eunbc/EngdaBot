package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatInviteLink(
	@JsonProperty("invite_link") String invite_link,
	@JsonProperty("creator") User creator,
	@JsonProperty("creates_join_request") Boolean creates_join_request,
	@JsonProperty("is_primary") Boolean is_primary,
	@JsonProperty("is_revoked") Boolean is_revoked,
	@JsonProperty("name") String name,
	@JsonProperty("expire_date") Integer expire_date,
	@JsonProperty("member_limit") Integer member_limit,
	@JsonProperty("pending_join_request_count") Integer pending_join_request_count
) {
}
