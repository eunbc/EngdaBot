package com.ebcho.engdabot.service;

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
		final int timeout = 30; // long-polling 주기

		while (true) {
			String requestUrl =
				TELEGRAM_API_URL + botToken + "/getUpdates?offset=" + (lastUpdateId + 1) + "&timeout=" + timeout;

			try {
				TelegramResponse response = restTemplate.getForObject(requestUrl, TelegramResponse.class);
				assert response != null;
				if (response.ok() && response.result() != null) {
					for (Update update : response.result()) {
						handleUpdate(update);

						lastUpdateId = update.updateId();
					}
				}
				log.info("Polling...." + lastUpdateId);
			} catch (Exception e) {
				log.warn("polling failed : ", e);
			}
		}
	}

	private void handleUpdate(Update update) {
		log.info(update.toString());
		// TODO : read, proofread, response, save data
	}
}
