package com.project.simi.user.repository.query;

import java.util.Optional;

import com.project.simi.user.domain.User;

public interface UserQueryRepository {
    User getUserById(Long userId);

    Optional<User> findByLoginId(String loginId);
}
