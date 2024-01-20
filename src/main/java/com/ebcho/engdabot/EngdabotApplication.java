package com.ebcho.engdabot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class EngdabotApplication {
	private static String botToken;
	private static OpenAIConfig openAIConfig;

	public EngdabotApplication(@Value("${telegram.bot-token}") String botToken, OpenAIConfig openAIConfig) {
		this.openAIConfig = openAIConfig;
		this.botToken = botToken;
	}

	public static void main(String[] args) {
		SpringApplication.run(EngdabotApplication.class, args);
		telegram();
	}

	private static void telegram() {
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new EngdaBotFactory(openAIConfig).createEngdaBot(botToken));
		} catch (TelegramApiException e) {
			log.warn("telegramBotsApi error : ", e);
		}
	}

}
