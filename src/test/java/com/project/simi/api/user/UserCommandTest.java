package com.project.simi.api.user;

import static javax.management.openmbean.SimpleType.STRING;
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
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

public class UserCommandTest extends SuperIntegrationTest {

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
            .andDo(document("{ClassName}" + "/" + "{methodName}",
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
    void agreePrivatePolicy() throws Exception {
        mvc.perform(RestDocumentationRequestBuilders
                .patch("/api/v1/users/private-policy")
                .header(AUTHORIZATION, createDefaultAuthentication())
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("{ClassName}" + "/" + "{methodName}",
                requestHeaders(
                    headerWithName(ACCEPT).description("accept"),
                    headerWithName(AUTHORIZATION).description("authorization")
                )
            ));
    }

}
