package com.ebcho.engdabot.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ebcho.engdabot.config.QueryDslConfig;
import com.ebcho.engdabot.entity.ReceiveMessage;
import com.ebcho.engdabot.entity.TelegramUser;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
class ReceiveMessageRepositoryTest {

	@Autowired
	private ReceiveMessageRepository repository;
	@Autowired
	private TelegramUserRepository telegramUserRepository;

	private TelegramUser telegramUser;
	private ReceiveMessage message1;
	private ReceiveMessage message2;

	@BeforeEach
	void setUp() {
		telegramUser = new TelegramUser(0L, "TestUser");
		TelegramUser telegramUser1 = telegramUserRepository.save(telegramUser);
		message1 = new ReceiveMessage("Test message 1", telegramUser1);
		message2 = new ReceiveMessage("Another test message 2", telegramUser1);

		repository.save(message1);
		repository.save(message2);
	}

	@Test
	@DisplayName("받은 메시지 페이징 조회 성공")
	void testFindReceivedMessageByKeywordWithPage() {
		// given
		int page = 1;
		int size = 10;
		String query = "test";

		// when
		Page<ReceiveMessage> result = repository.findReceivedMessageByKeywordWithPage(page, size, query);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getTotalElements()).isEqualTo(2);
		assertThat(result.getContent()).hasSize(2);
		assertThat(result.getContent()).extracting("content")
			.containsExactlyInAnyOrder("Test message 1", "Another test message 2");
	}

	@Test
	@DisplayName("존재하지 않는 결과 검색 조회")
	void testFindReceivedMessageByKeywordWithPage_NoResults() {
		// given
		int page = 1;
		int size = 10;
		String query = "nonexistent";

		// when
		Page<ReceiveMessage> result = repository.findReceivedMessageByKeywordWithPage(page, size, query);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getTotalElements()).isEqualTo(0);
		assertThat(result.getContent()).isEmpty();
	}

	@AfterEach
	public void tearDown() {
		repository.deleteAll();
		telegramUserRepository.deleteAll();
	}
}
