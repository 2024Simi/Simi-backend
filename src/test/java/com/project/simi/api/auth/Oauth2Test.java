package com.project.simi.api.auth;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
public class Oauth2Test extends SuperIntegrationTest {

    @Test
    void oauth2Login() throws Exception {
        mvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/oauth2/KAKAO")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header("IdToken", "your_id_token")
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("{ClassName}" + "/" + "{methodName}",
                requestHeaders(
                    headerWithName(ACCEPT).description("accept"),
                    headerWithName(CONTENT_TYPE).description("content type"),
                    headerWithName("IdToken").description("id token")
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
