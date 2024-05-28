package com.ebcho.engdabot.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ebcho.engdabot.dto.PageResponse;
import com.ebcho.engdabot.dto.ReceiveMessageResponse;
import com.ebcho.engdabot.entity.ReceiveMessage;
import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.repository.ReceiveMessageRepository;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

	@Mock
	private ReceiveMessageRepository receiveMessageRepository;

	@InjectMocks
	private AdminService adminService;

	private TelegramUser telegramUser;
	private ReceiveMessage message1;
	private ReceiveMessage message2;
	private List<ReceiveMessage> messageList;

	@BeforeEach
	void setUp() {
		telegramUser = new TelegramUser(0L, "TestUser");
		message1 = new ReceiveMessage("Test message 1", telegramUser);
		message2 = new ReceiveMessage("Another test message 2", telegramUser);
		messageList = Arrays.asList(message1, message2);
	}

	@Test
	@DisplayName("관리자 받은 메시지 페이징 조회")
	void testGetReceiveMessages() {
		// given
		int page = 1;
		int size = 10;
		String query = "test";

		Pageable pageable = PageRequest.of(page - 1, size);
		Page<ReceiveMessage> pageResult = new PageImpl<>(messageList, pageable, messageList.size());

		given(receiveMessageRepository.findReceivedMessageByKeywordWithPage(page, size, query))
			.willReturn(pageResult);

		// when
		PageResponse<ReceiveMessageResponse> result = adminService.getReceiveMessages(page, size, query);

		// then
		assertThat(result).isNotNull();
		assertThat(result.totalElements()).isEqualTo(messageList.size());
		assertThat(result.data()).hasSize(messageList.size());
		assertThat(result.data()).extracting("content")
			.containsExactlyInAnyOrder("Test message 1", "Another test message 2");

		verify(receiveMessageRepository).findReceivedMessageByKeywordWithPage(page, size, query);
	}
}
