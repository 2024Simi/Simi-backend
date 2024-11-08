package com.project.simi.api.user;

import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
public class UserQueryTest extends SuperIntegrationTest {

    @Test
    void getUserById() throws Exception {
        mvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/users/me")
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
                ),
                responseFields(
                    commonResponseFields(
                        fieldWithPath("id").type(NUMBER).description("user id"),
                        fieldWithPath("loginId").type(STRING).description("login id == email"),
                        fieldWithPath("profileImageUrl").type(STRING).description("profile image url"),
                        fieldWithPath("nickname").type(STRING).description("nickname"),
                        fieldWithPath("provider").type(STRING).description("provider ${com.project.simi.domain.auth.enums.AuthProviderEnum.values().toString();}"  ),
                        fieldWithPath("isPrivatePolicyAgreed").type(BOOLEAN).description("is private policy agreed"),
                        fieldWithPath("isProfileSettings").type(BOOLEAN).description("is profile settings")
                    )
                )
            ));
    }
}
