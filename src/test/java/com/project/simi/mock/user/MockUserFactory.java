package com.project.simi.mock.user;

import com.project.simi.domain.auth.enums.AuthProviderEnum;
import com.project.simi.domain.auth.enums.AuthoriryEnum;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.repository.UserJpaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestComponent
public class MockUserFactory {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean(name = "mockDefaultUser")
    public User mockDefaultUser() {
        return createMockUser(
            User.createOf(
                "groot",
                passwordEncoder.encode("password"),
                "url",
                "",
                "",
                List.of(AuthoriryEnum.ROLE_ADMIN),
                AuthProviderEnum.BASIC
            )
        );
    }


    public User createMockUser(User user) {
        return userJpaRepository.save(user);
    }
}
