package com.project.simi.domain.user.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.project.simi.domain.user.domain.User;

public interface UserJpaRepository extends Repository<User, Long> {
    User save(User user);

    Optional<User> findById(Long userId);
}
