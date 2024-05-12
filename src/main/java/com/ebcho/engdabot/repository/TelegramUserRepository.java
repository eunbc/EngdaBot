package com.ebcho.engdabot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ebcho.engdabot.entity.TelegramUser;
import com.ebcho.engdabot.enums.AlarmType;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {

	List<TelegramUser> findByAlarmType(AlarmType alarmType);
}
