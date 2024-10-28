package com.project.simi.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.dto.UserDto.UserResponseDto;
import com.project.simi.domain.user.repository.query.UserQueryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {
    private final UserQueryRepository userQueryRepository;

    private User findById(Long id) {
        return userQueryRepository.getUserById(id);
    }

    public UserResponseDto getUserById(Long id) {
        User user = findById(id);
        return UserResponseDto.createOf(user);
    }
}
