package com.ebcho.engdabot.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebcho.engdabot.dto.PageResponse;
import com.ebcho.engdabot.dto.ReceiveMessageResponse;
import com.ebcho.engdabot.entity.ReceiveMessage;
import com.ebcho.engdabot.repository.ReceiveMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
	private final ReceiveMessageRepository receiveMessageRepository;

	@Transactional(readOnly = true)
	public PageResponse<ReceiveMessageResponse> getReceiveMessages(int page, int size, String query) {
		Page<ReceiveMessage> questions = receiveMessageRepository.findReceivedMessageByKeywordWithPage(page, size,
			query);
		return PageResponse.of(questions, ReceiveMessageResponse::from);
	}

	@Transactional(readOnly = true)
	public PageResponse<ReceiveMessageResponse> getReceiveMessagesLike(int page, int size, String query) {
		Page<ReceiveMessage> questions = receiveMessageRepository.findReceivedMessageByKeywordLikeWithPage(page, size,
			query);
		return PageResponse.of(questions, ReceiveMessageResponse::from);
	}

}
