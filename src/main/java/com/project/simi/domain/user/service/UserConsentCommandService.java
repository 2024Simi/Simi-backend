package com.project.simi.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.simi.domain.auth.enums.UserStatusEnum;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.domain.UserConsent;
import com.project.simi.domain.user.dto.RequestUser;
import com.project.simi.domain.user.dto.UserConsentDto.Request;
import com.project.simi.domain.user.repository.command.UserConsentCommandRepository;
import com.project.simi.domain.user.repository.query.UserConsentQueryRepository;
import com.project.simi.domain.user.repository.query.UserQueryRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserConsentCommandService {

    private final UserConsentCommandRepository userConsentCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final UserConsentQueryRepository userConsentQueryRepository;

    @Transactional
    public void updateConsent(RequestUser requestUser, Request request) {
        Long id = requestUser.getId();
        userConsentQueryRepository
                .findByUserId(id)
                .ifPresentOrElse(
                        userConsent -> {
                            userConsent.update(request);
                        },
                        () -> {
                            User user = userQueryRepository.getUserById(id);
                            UserConsent newUserConsent = UserConsent.createOf(user);
                            newUserConsent.update(request);
                            userConsentCommandRepository.save(newUserConsent);

                            if (user.getStatus().equals(UserStatusEnum.SIGN_UP)) {
                                user.updateStatus(UserStatusEnum.NICKNAME_PENDING);
                            }
                        });
    }
}
