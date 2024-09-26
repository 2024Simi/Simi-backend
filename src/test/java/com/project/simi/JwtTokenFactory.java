package com.project.simi;

import com.project.simi.auth.provider.JwtTokenProvider;
import com.project.simi.user.domain.User;
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

    public String createDefaultAuthentication() {
        return "Bearer " + jwtTokenProvider.generateAccessTokenValue(mockDefaultUser);
    }
}
