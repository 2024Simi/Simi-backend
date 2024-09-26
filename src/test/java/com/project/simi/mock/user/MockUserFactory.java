package com.project.simi.mock.user;

import com.project.simi.auth.enums.AuthProviderEnum;
import com.project.simi.auth.enums.AuthoriryEnum;
import com.project.simi.user.domain.User;
import com.project.simi.user.repository.query.UserJpaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;

@TestComponent
public class MockUserFactory {
    @Autowired
    private UserJpaRepository userJpaRepository;



    @Bean(name = "mockDefaultUser")
    public User mockDefaultUser() {
        return createMockUser(
            User.createOf(
                "default",
                "password",
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
