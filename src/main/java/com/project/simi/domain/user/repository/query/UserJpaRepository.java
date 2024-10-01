package com.project.simi.domain.user.repository.query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simi.domain.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {}
