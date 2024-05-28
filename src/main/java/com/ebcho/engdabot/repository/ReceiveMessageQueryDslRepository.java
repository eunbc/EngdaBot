package com.ebcho.engdabot.repository;

import org.springframework.data.domain.Page;

import com.ebcho.engdabot.entity.ReceiveMessage;

public interface ReceiveMessageQueryDslRepository {
	Page<ReceiveMessage> findReceivedMessageByKeywordWithPage(int page, int size, String query);

	Page<ReceiveMessage> findReceivedMessageByKeywordLikeWithPage(int page, int size, String query);
}
