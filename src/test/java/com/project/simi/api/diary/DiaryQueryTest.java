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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.diary.domain.Diary;
import com.project.simi.domain.diary.domain.EmotionType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
                        queryParameters(
                              parameterWithName("startDate").description("시작 날짜"),
                                parameterWithName("endDate").description("종료 날짜")
                        ),
                        responseFields(
                            commonListResponseFields(
                                        fieldWithPath("diaryId").type(NUMBER).description("저장된 다이어리 ID"),
                                        fieldWithPath("createdAt").type(DATE).description("생성 날짜")
                            )
                        )
                ));
    }


    @Test
    void getDiaryById() throws Exception {
        Long diaryId = mockDefaultDiary.getId();

        mvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/diary/{diaryId}", diaryId)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, createDefaultAuthentication())
                .characterEncoding("utf-8"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("{ClassName}/{methodName}",
                requestHeaders(
                    headerWithName(ACCEPT).description("응답 데이터 형식"),
                    headerWithName(CONTENT_TYPE).description("요청 데이터 형식"),
                    headerWithName(AUTHORIZATION).description("Bearer 토큰")
                ),
                responseFields(
                    commonResponseFields(
                        fieldWithPath("diaryId").type(NUMBER).description("다이어리 ID"),
                        fieldWithPath("episode").type("String").description("에피소드 내용"),
                        fieldWithPath("thoughtOfEpisode").type("String").description("에피소드에 대한 생각"),
                        fieldWithPath("emotionOfEpisodes").type("Array").description("감정 리스트"),
                        fieldWithPath("emotionOfEpisodes[].type").type("String").description(Arrays.toString(EmotionType.values())),
                        fieldWithPath("emotionOfEpisodes[].details").type("Array").description("감정 상세 내용"),
                        fieldWithPath("primaryEmotion").type("String").description("주요 감정"),
                        fieldWithPath("resultOfEpisode").type("String").description("에피소드의 결과"),
                        fieldWithPath("empathyResponse").type("String").description("공감 응답")
                    )
                )
            ));
    }
}