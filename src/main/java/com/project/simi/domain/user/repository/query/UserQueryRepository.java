package com.project.simi.domain.user.repository.query;

import java.util.Optional;

import com.project.simi.domain.user.domain.User;

public interface UserQueryRepository {
    User getUserById(Long userId);

    Optional<User> findByLoginId(String loginId);
}
