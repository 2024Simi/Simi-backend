package com.project.simi.mock.auth;

import com.project.simi.domain.auth.domain.RefreshToken;
import com.project.simi.domain.auth.repository.RefreshTokenJpaRepository;
import com.project.simi.domain.user.domain.User;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;

@TestComponent
public class MockRefreshTokenFactory {
    @Autowired
    private  RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Autowired
    @Qualifier("mockDefaultUser")
    private User mockUser;
    @Bean(name = "mockRefreshToken")
    public RefreshToken mockRefreshToken() {
        RefreshToken refreshTokenValue = RefreshToken.createOf(
            mockUser,
            "refreshTokenValue",
            10,
            ChronoUnit.DAYS
        );

        return createMockRefreshToken(refreshTokenValue);
    }

    private RefreshToken createMockRefreshToken(RefreshToken refreshTokenValue) {
        return refreshTokenJpaRepository.save(refreshTokenValue);
    }

}
