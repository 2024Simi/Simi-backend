package com.project.simi.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.auth.enums.UserStatusEnum;
import com.project.simi.domain.user.domain.UserConsent;
import com.project.simi.domain.user.repository.query.UserConsentQueryRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserLoginLogicTest {

    private User mockUser;
    private UserConsentQueryRepository mockUserConsentQueryRepository;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
        mockUserConsentQueryRepository = mock(UserConsentQueryRepository.class);
    }

    @Test
    void shouldReturnLoginFlagWithdrawal_WhenUserIsLockedOrExpired() {
        // Given
        when(mockUser.isNonLocked()).thenReturn(false);
        when(mockUser.isNotExpired()).thenReturn(true);

        // When
        UserStatusEnum result = calculateLoginFlag(mockUser);

        // Then
        assertThat(result).isEqualTo(UserStatusEnum.WITHDRAWAL);
    }

    @Test
    void shouldReturnNicknamePending_WhenConsentExistsAndNoNickname() {
        UserConsent mockConsent = mock(UserConsent.class);
        // Given
        when(mockUser.isNonLocked()).thenReturn(true);
        when(mockUser.isNotExpired()).thenReturn(true);
        when(mockUser.getNickname()).thenReturn("");
        when(mockUserConsentQueryRepository
                .findByUserId(mockUser.getId()))
                .thenReturn(Optional.of(mockConsent));

        // When
        UserStatusEnum result = calculateLoginFlag(mockUser);

        // Then
        assertThat(result).isEqualTo(UserStatusEnum.NICKNAME_PENDING);
    }

    @Test
    void shouldReturnLogin_WhenUserHasNicknameAndConsent() {
        UserConsent mockConsent = mock(UserConsent.class);

        // Given
        when(mockUser.isNonLocked()).thenReturn(true);
        when(mockUser.isNotExpired()).thenReturn(true);
        when(mockUser.getNickname()).thenReturn("nickname");
        when(mockUserConsentQueryRepository
                .findByUserId(mockUser.getId())
        ).thenReturn(Optional.of(mockConsent));

        // When
        UserStatusEnum result = calculateLoginFlag(mockUser);

        // Then
        assertThat(result).isEqualTo(UserStatusEnum.LOGIN);
    }

    @Test
    void shouldReturnSignIn_WhenNoConsentAndNoNickname() {
        // Given
        when(mockUser.isNonLocked()).thenReturn(true);
        when(mockUser.isNotExpired()).thenReturn(true);
        when(mockUser.getNickname()).thenReturn("");
        when(mockUserConsentQueryRepository
                .findByUserId(mockUser.getId()))
                .thenReturn(Optional.empty());

        // When
        UserStatusEnum result = calculateLoginFlag(mockUser);

        // Then
        assertThat(result).isEqualTo(UserStatusEnum.SIGN_IN);
    }

    private UserStatusEnum calculateLoginFlag(User user) {
        if (!user.isNonLocked() || !user.isNotExpired()) {
            return UserStatusEnum.WITHDRAWAL;
        }

        boolean hasUserConsent = !mockUserConsentQueryRepository.findByUserId(user.getId()).isEmpty();
        boolean hasUserNickname = !user.getNickname().isEmpty();

        if (hasUserConsent && !hasUserNickname) {
            return UserStatusEnum.NICKNAME_PENDING;
        }
        if (hasUserNickname) {
            return UserStatusEnum.LOGIN;
        }
        return UserStatusEnum.SIGN_IN;
    }
}