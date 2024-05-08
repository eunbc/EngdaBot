CREATE TABLE IF NOT EXISTS `telegram_user`
(
    `id`         BIGINT PRIMARY KEY,
    `first_name` VARCHAR(100) NOT NULL,
    `alarm_type` VARCHAR(10)  NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `receive_message`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `content`     TEXT   NOT NULL,
    `sender_id`   BIGINT NOT NULL,
    `received_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `send_message`
(
    `id`           BIGINT PRIMARY KEY AUTO_INCREMENT,
    `content`      TEXT        NOT NULL,
    `status`       VARCHAR(10) NOT NULL,
    `fail_message` VARCHAR(255),
    `receiver_id`  BIGINT      NOT NULL,
    `sent_at`      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
