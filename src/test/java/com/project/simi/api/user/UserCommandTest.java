package com.project.simi.api.user;

import static javax.management.openmbean.SimpleType.STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.user.domain.User;
import com.project.simi.domain.user.repository.query.UserConsentQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

public class UserCommandTest extends SuperIntegrationTest {

    @Autowired
    private UserConsentQueryRepository userConsentQueryRepository;

    @Autowired
    @Qualifier("mockDefaultUser")
    private User user;

    @Test
    void updateNickname() throws Exception {

        String request = """
                {
                    "nickname": "nickname"
                }
            """;

        mvc.perform(RestDocumentationRequestBuilders
                .patch("/api/v1/users/nickname")
                .header(AUTHORIZATION, createDefaultAuthentication())
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(request)
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "{ClassName}" + "/" + "{methodName}",
                requestHeaders(
                    headerWithName(ACCEPT).description("accept"),
                    headerWithName(AUTHORIZATION).description("authorization")
                ),
                requestFields(
                    fieldWithPath("nickname").type(STRING).description("nickname")
                )
            ));
    }

    @Test
    @Deprecated
    void agreePrivatePolicy() throws Exception {
        mvc.perform(RestDocumentationRequestBuilders
                .patch("/api/v1/users/private-policy")
                .header(AUTHORIZATION, createDefaultAuthentication())
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "{ClassName}" + "/" + "{methodName}",
                requestHeaders(
                    headerWithName(ACCEPT).description("accept"),
                    headerWithName(AUTHORIZATION).description("authorization")
                )
            ));
    }


    @Test
    void updateConsent() throws Exception {
        String request = """
                {
                    "isAgreePrivatePolicy": true,
                    "isAgreeTermsOfService": true,
                    "isAgreeMarketing": true
                }
            """;
        mvc.perform(RestDocumentationRequestBuilders
                .put("/api/v1/users/consent")
                .header(AUTHORIZATION, createDefaultAuthentication())
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .content(request)
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "{ClassName}" + "/" + "{methodName}",
                requestHeaders(
                    headerWithName(ACCEPT).description("accept"),
                    headerWithName(AUTHORIZATION).description("authorization")
                ),
                requestFields(
                    fieldWithPath("isAgreePrivatePolicy").type(STRING).description("isAgreePrivatePolicy"),
                    fieldWithPath("isAgreeTermsOfService").type(STRING).description("isAgreeTermsOfService"),
                    fieldWithPath("isAgreeMarketing").type(STRING).description("isAgreeMarketing")
                )
            ));
        assertThat(userConsentQueryRepository.findByUserId(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("User not found"))
            .getIsAgreeMarketing()).isTrue();
    }
}
