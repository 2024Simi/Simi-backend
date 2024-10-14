package com.project.simi.api.diary;

import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.DATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.diary.domain.Diary;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

class DiaryQueryTest extends SuperIntegrationTest {
    @Autowired
    @Qualifier("mockDefaultDiary")
    private Diary mockDefaultDiary;

    @Test
    void getDiariesByDate() throws Exception {

        mvc.perform(RestDocumentationRequestBuilders
                        .get("/api/v1/diary")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, createDefaultAuthentication())
                .param("startDate", LocalDate.now().minusDays(10).format(DateTimeFormatter.ISO_DATE))
                .param("endDate", LocalDate.now().plusDays(10).atStartOfDay().format(DateTimeFormatter.ISO_DATE))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{ClassName}" + "/" + "{methodName}",
                        requestHeaders(
                                headerWithName(ACCEPT).description("Header"),
                                headerWithName(CONTENT_TYPE).description("Content type"),
                                headerWithName(AUTHORIZATION).description("Bearer token ")
                        ),
                        responseFields(
                            commonListResponseFields(
                                        fieldWithPath("diaryId").type(NUMBER).description("저장된 다이어리 ID"),
                                        fieldWithPath("createdAt").type(DATE).description("생성 날짜")
                            )
                        )
                ));
    }
}