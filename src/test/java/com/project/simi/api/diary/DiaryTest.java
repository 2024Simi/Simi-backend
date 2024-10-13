package com.project.simi.api.diary;

import static java.sql.JDBCType.ARRAY;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.diary.dto.DiaryDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

class DiaryTest extends SuperIntegrationTest {
    @Test
    void createDiary() throws Exception {
        DiaryDto.DiaryRequest request = new DiaryDto.DiaryRequest(
                "사건",
                "생각",
                new DiaryDto.EmotionDto("기쁨", List.of("행복", "즐거움")),
                "결과",
                "GPT의 한마디"
        );

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/diary")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, createDefaultAuthentication())
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{ClassName}" + "/" + "{methodName}",
                        requestHeaders(
                                headerWithName(ACCEPT).description("Header"),
                                headerWithName(CONTENT_TYPE).description("Content type"),
                                headerWithName(AUTHORIZATION).description("Bearer token ")
                        ),
                        requestFields(
                                fieldWithPath("episode").type(STRING).description("Episode"),
                                fieldWithPath("thoughtOfEpisode").type(STRING).description("Thought of episode"),
                                fieldWithPath("emotionOfEpisode.type").type(STRING).description("Emotion type"),
                                fieldWithPath("emotionOfEpisode.details").type(ARRAY).description("Details of the emotion"),
                                fieldWithPath("resultOfEpisode").type(STRING).description("Result of the episode"),
                                fieldWithPath("empathyResponse").type(STRING).description("GPT's empathetic response")
                        ),
                        responseFields(
                                commonResponseFields(
                                        fieldWithPath("data.diaryId").type(NUMBER).description("저장된 다이어리 ID"),
                                        fieldWithPath("data.empathyResponse").type(STRING).description("지피티 결과")
                                )
                        )
                ));
    }
}