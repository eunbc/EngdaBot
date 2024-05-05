package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMemberUpdated(
	@JsonProperty("chat") Chat chat,
	@JsonProperty("from") User from,
	@JsonProperty("date") Integer date,
	@JsonProperty("old_chat_member") ChatMember oldChatMember,
	@JsonProperty("new_chat_member") ChatMember newChatMember,
	@JsonProperty("invite_link") ChatInviteLink inviteLink,
	@JsonProperty("via_chat_folder_invite_link") Boolean viaChatFolderInviteLink
) {
}
