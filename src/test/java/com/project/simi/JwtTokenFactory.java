package com.project.simi;

import com.project.simi.domain.auth.provider.JwtTokenProvider;
import com.project.simi.domain.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class JwtTokenFactory {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    @Qualifier("mockDefaultUser")
    private User mockDefaultUser;

    @Autowired
    @Qualifier("mockUser")
    private User mockUser;

    public String createDefaultAuthentication() {
        return "Bearer " + jwtTokenProvider.generateAccessTokenValue(mockDefaultUser);
    }

    public String createUserAuthentication() {
        return "Bearer " + jwtTokenProvider.generateAccessTokenValue(mockUser);
    }
}
