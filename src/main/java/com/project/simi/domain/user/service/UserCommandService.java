package com.project.simi.domain.user.service;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.repository.query.UserQueryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandService {
    private final UserQueryRepository userQueryRepository;

    public void updateNickname(Long id, String nickname) {
        User user = getUserById(id);
        user.updateNickname(nickname);
    }

    @Deprecated(since = "2024-11-07", forRemoval = true)
    public void agreePrivatePolicy(Long id) {
        User user = getUserById(id);
        user.agreePrivatePolicy();
    }

    private User getUserById(Long id) {
        return userQueryRepository.getUserById(id);
    }
}
