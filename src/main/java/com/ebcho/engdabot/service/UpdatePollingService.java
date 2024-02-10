package com.ebcho.engdabot.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ebcho.engdabot.telegram.TelegramResponse;
import com.ebcho.engdabot.telegram.Update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePollingService {

	private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot";

	private final RestTemplate restTemplate;

	@Async
	public void startPolling(String botToken) {
		int lastUpdateId = 0;
		while (true) {
			String requestUrl = TELEGRAM_API_URL + botToken + "/getUpdates?offset=" + (lastUpdateId + 1);

			try {
				TelegramResponse response = restTemplate.getForObject(requestUrl, TelegramResponse.class);
				assert response != null;
				List<Update> updates = response.result();

				if (updates != null) {
					for (Update update : updates) {
						// 각 업데이트 처리
						handleUpdate(update);

						// 다음 폴링을 위해 lastUpdateId 업데이트
						lastUpdateId = update.updateId();
					}
				}
				Thread.sleep(1000);
				log.info("Polling...." + lastUpdateId);
			} catch (Exception e) {
				log.warn("polling failed : ", e);
			}
		}
	}

	private void handleUpdate(Update update) {
		log.info(update.toString());
	}
}
