package com.project.simi.domain.user.repository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import org.springframework.stereotype.Repository;

import com.project.simi.common.exception.NotFoundException;
import com.project.simi.common.exception.code.NotFoundResourceCode;
import com.project.simi.domain.user.domain.QUser;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.repository.command.UserCommandRepository;
import com.project.simi.domain.user.repository.query.UserJpaRepository;
import com.project.simi.domain.user.repository.query.UserQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserQueryRepository, UserCommandRepository {
    @Delegate private final UserJpaRepository userJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User getUserById(Long userId) {
        return userJpaRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(NotFoundResourceCode.USER));
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(QUser.user)
                        .where(QUser.user.loginId.eq(loginId))
                        .fetchOne());
    }
}
