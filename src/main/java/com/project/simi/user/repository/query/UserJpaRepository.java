package com.project.simi.user.repository.query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.simi.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {}
