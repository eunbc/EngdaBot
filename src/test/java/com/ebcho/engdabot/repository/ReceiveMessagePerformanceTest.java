package com.ebcho.engdabot.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

@Disabled
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
class ReceiveMessagePerformanceTest {

	@Autowired
	private ReceiveMessageRepository repository;
	@Autowired
	private TelegramUserRepository telegramUserRepository;

	private TelegramUser telegramUser;

	@BeforeEach
	void setUp() {
		telegramUser = new TelegramUser(0L, "TestUser");
		TelegramUser telegramUser1 = telegramUserRepository.save(telegramUser);

		List<ReceiveMessage> messages = new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			messages.add(new ReceiveMessage("Test number " + (i + 1), telegramUser1));
		}
		repository.saveAll(messages);
	}

	/**
	 * Like 쿼리일 때만 수행 가능 (fulltext index 테스트 불가)
	 */
	@Test
	@DisplayName("십만건의 데이터에서 검색 조회시 성능 체크")
	void testFindReceivedMessageByKeywordWithPage() {
		// given
		int page = 1;
		int size = 10;
		String query = "Test";

		Instant start = Instant.now(); // 시작 시간 기록

		// when
		Page<ReceiveMessage> result = repository.findReceivedMessageByKeywordLikeWithPage(page, size, query);

		Instant end = Instant.now(); // 종료 시간 기록
		long elapsedTime = Duration.between(start, end).toMillis(); // 실행 시간 계산

		// then
		assertThat(result).isNotNull();
		assertThat(result.getTotalElements()).isEqualTo(100000); // 전체 결과의 개수 확인
		assertThat(result.getContent()).hasSize(size);

		System.out.println("테스트 수행 시간: " + elapsedTime + "ms");
	}

	@AfterEach
	public void tearDown() {
		repository.deleteAll();
		telegramUserRepository.deleteAll();
	}
}
