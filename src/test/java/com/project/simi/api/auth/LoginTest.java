package com.project.simi.api.auth;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.auth.domain.RefreshToken;
import com.project.simi.domain.auth.dto.LoginDto;
import com.project.simi.domain.auth.dto.LogoutDto;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.domain.UserConsent;
import com.project.simi.domain.user.repository.query.UserConsentQueryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

class LoginTest extends SuperIntegrationTest {

    @Autowired
    @Qualifier("mockRefreshToken")
    private RefreshToken mockRefreshToken;

    @Autowired
    @Qualifier("mockDefaultUser")
    private User mockUser;

    @InjectMocks
    private UserConsentQueryRepository userConsentQueryRepository;

    @Test
    void login() throws Exception {
        LoginDto.Request request = new LoginDto.Request("groot", "password");

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/login")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{ClassName}" + "/" + "{methodName}",
                        requestHeaders(
                                headerWithName(ACCEPT).description("accept")
                        ),
                        requestFields(
                                fieldWithPath("loginId").type(STRING).description("login id"),
                                fieldWithPath("password").type(STRING).description("password")
                        ),
                        responseFields(
                                commonResponseFields(
                                        fieldWithPath("accessToken").type(STRING).description("access token"),
                                        fieldWithPath("refreshToken").type(STRING).description("refresh token"),
                                        fieldWithPath("userId").type(NUMBER).description("user id"),
                                        fieldWithPath("loginFlag").type(STRING).description("login flag")
                                )
                        )
                ));
    }

    @Test
    void logout() throws Exception {
        LogoutDto.Request request = new LogoutDto.Request("##refresh token value##");

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/logout")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, createDefaultAuthentication())
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{ClassName}" + "/" + "{methodName}",
                        requestHeaders(
                                headerWithName(ACCEPT).description("accept")
                        ),
                        requestFields(
                                fieldWithPath("refreshToken").type(STRING).description("refresh token")
                        )
                ));
    }

    @Test
    void refresh() throws Exception {
        LoginDto.RefreshRequest request = new LoginDto.RefreshRequest(mockRefreshToken.getRefreshTokenValue());

        mvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/refresh")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("{ClassName}" + "/" + "{methodName}",
                requestHeaders(
                    headerWithName(ACCEPT).description("accept")
                ),
                requestFields(
                    fieldWithPath("refreshToken").type(STRING).description("refresh token")
                ),
                responseFields(
                    commonResponseFields(
                        fieldWithPath("accessToken").type(STRING).description("access token"),
                        fieldWithPath("refreshToken").type(STRING).description("refresh token"),
                        fieldWithPath("userId").type(NUMBER).description("user id")
                    )
                )
            ));
    }

}
