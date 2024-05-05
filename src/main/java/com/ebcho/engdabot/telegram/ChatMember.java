package com.ebcho.engdabot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatMember(
	@JsonProperty("user") User user,
	@JsonProperty("status") Status status,
	@JsonProperty("custom_title") String custom_title,
	@JsonProperty("is_anonymous") Boolean is_anonymous,
	@JsonProperty("until_date") Integer until_date,
	@JsonProperty("can_be_edited") Boolean can_be_edited,
	@JsonProperty("can_manage_chat") Boolean can_manage_chat,
	@JsonProperty("can_post_messages") Boolean can_post_messages,
	@JsonProperty("can_edit_messages") Boolean can_edit_messages,
	@JsonProperty("can_delete_messages") Boolean can_delete_messages,
	@JsonProperty("can_manage_video_chats") Boolean can_manage_video_chats,
	@JsonProperty("can_restrict_members") Boolean can_restrict_members,
	@JsonProperty("can_promote_members") Boolean can_promote_members,
	@JsonProperty("can_change_info") Boolean can_change_info,
	@JsonProperty("can_invite_users") Boolean can_invite_users,
	@JsonProperty("can_pin_messages") Boolean can_pin_messages,
	@JsonProperty("can_post_stories") Boolean can_post_stories,
	@JsonProperty("can_edit_stories") Boolean can_edit_stories,
	@JsonProperty("can_delete_stories") Boolean can_delete_stories,
	@JsonProperty("can_manage_topics") Boolean can_manage_topics,
	@JsonProperty("is_member") Boolean is_member,
	@JsonProperty("can_send_messages") Boolean can_send_messages,
	@JsonProperty("can_send_audios") Boolean can_send_audios,
	@JsonProperty("can_send_documents") Boolean can_send_documents,
	@JsonProperty("can_send_photos") Boolean can_send_photos,
	@JsonProperty("can_send_videos") Boolean can_send_videos,
	@JsonProperty("can_send_video_notes") Boolean can_send_video_notes,
	@JsonProperty("can_send_voice_notes") Boolean can_send_voice_notes,
	@JsonProperty("can_send_polls") Boolean can_send_polls,
	@JsonProperty("can_send_other_messages") Boolean can_send_other_messages,
	@JsonProperty("can_add_web_page_previews") Boolean can_add_web_page_previews
) {

	public enum Status {
		creator, administrator, member, restricted, left, kicked;
	}
}
