package com.project.simi.domain.user.repository.query;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.project.simi.domain.user.domain.User;
import com.project.simi.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryAdapter implements UserQueryRepository {

    private final UserJpaRepository userJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User getUserById(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow();
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
