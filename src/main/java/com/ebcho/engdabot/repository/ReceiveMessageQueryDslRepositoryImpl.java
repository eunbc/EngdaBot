package com.ebcho.engdabot.repository;

import static com.ebcho.engdabot.entity.QReceiveMessage.*;
import static com.querydsl.core.types.dsl.Expressions.*;
import static io.micrometer.common.util.StringUtils.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ebcho.engdabot.entity.ReceiveMessage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReceiveMessageQueryDslRepositoryImpl implements ReceiveMessageQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<ReceiveMessage> findReceivedMessageByKeywordWithPage(int page, int size, String query) {
		Pageable pageable = PageRequest.of(page - 1, size);

		BooleanExpression condition = contains(receiveMessage.content, query);

		List<ReceiveMessage> receiveMessages = queryFactory
			.selectFrom(receiveMessage)
			.where(condition)
			.orderBy(receiveMessage.receivedAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(receiveMessage.countDistinct())
			.from(receiveMessage)
			.where(condition);

		return PageableExecutionUtils.getPage(receiveMessages, pageable, countQuery::fetchOne);
	}

	@Override
	public Page<ReceiveMessage> findReceivedMessageByKeywordLikeWithPage(int page, int size, String query) {
		Pageable pageable = PageRequest.of(page - 1, size);

		BooleanExpression condition =
			StringUtils.hasText(query) ? receiveMessage.content.likeIgnoreCase("%" + query + "%") : null;

		List<ReceiveMessage> receiveMessages = queryFactory
			.selectFrom(receiveMessage)
			.where(condition)
			.orderBy(receiveMessage.receivedAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(receiveMessage.countDistinct())
			.from(receiveMessage)
			.where(condition);

		return PageableExecutionUtils.getPage(receiveMessages, pageable, countQuery::fetchOne);
	}

	private BooleanExpression contains(final StringPath target, final String searchWord) {
		if (isBlank(searchWord)) {
			return null;
		}

		final String formattedSearchWord = "\"" + searchWord + "\"";
		return numberTemplate(Double.class, "function('match_against', {0}, {1})",
			target, formattedSearchWord)
			.gt(0);
	}
}
