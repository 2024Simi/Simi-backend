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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.diary.domain.EmotionOfEpisodes;
import com.project.simi.domain.diary.domain.EmotionType;
import com.project.simi.domain.diary.dto.DiaryDto;
import com.project.simi.domain.diary.dto.DiaryDto.EmotionOfEpisodeDto;
import com.project.simi.mock.ai.MockCerebrasConfig;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Import(MockCerebrasConfig.class)
class DiaryTest extends SuperIntegrationTest {
    @Test
    void createDiary() throws Exception {
        DiaryDto.DiaryRequest request = new DiaryDto.DiaryRequest(
                "사건",
                "생각",
                List.of(new EmotionOfEpisodeDto(EmotionType.HAPPY, List.of("행복", "즐거움"))),
                "결과"
        );

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/diary")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, createUserAuthentication())
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
                                fieldWithPath("emotionOfEpisodes[].type").type(STRING).description(Arrays.toString(EmotionType.values())),
                                fieldWithPath("emotionOfEpisodes[].details").type(ARRAY).description("Details of the emotion"),
                                fieldWithPath("resultOfEpisode").type(STRING).description("Result of the episode")
                        ),
                        responseFields(
                                commonResponseFields(
                                        fieldWithPath("diaryId").type(NUMBER).description("저장된 다이어리 ID"),
                                        fieldWithPath("empathyResponse").type(STRING).description("지피티 결과")
                                )
                        )
                ));
    }

    @Test
    void createDiary_with_alreadyExistDiary() throws Exception {
        DiaryDto.DiaryRequest request = new DiaryDto.DiaryRequest(
                "사건",
                "생각",
                List.of(new EmotionOfEpisodeDto(EmotionType.HAPPY, List.of("행복", "즐거움"))),
                "결과"
        );

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/diary")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, createDefaultAuthentication())
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].message")
//                        .value("이미 작성한 일기가 있습니다."))
                .andDo(document("{ClassName}" + "/" + "{methodName}",
                        requestHeaders(
                                headerWithName(ACCEPT).description("Header"),
                                headerWithName(CONTENT_TYPE).description("Content type"),
                                headerWithName(AUTHORIZATION).description("Bearer token ")
                        ),
                        requestFields(
                                fieldWithPath("episode").type(STRING).description("Episode"),
                                fieldWithPath("thoughtOfEpisode").type(STRING).description("Thought of episode"),
                                fieldWithPath("emotionOfEpisodes[].type").type(STRING).description(Arrays.toString(EmotionType.values())),
                                fieldWithPath("emotionOfEpisodes[].details").type(ARRAY).description("Details of the emotion"),
                                fieldWithPath("resultOfEpisode").type(STRING).description("Result of the episode")
                        ),
                        responseFields(
                                commonResponseFields(
                                        fieldWithPath("[].message").type(STRING).description("에러 메시지")
                                )
                        ))
                );
    }

    @Test
    void createDiary_withInvalidEmotionList() throws Exception {
        DiaryDto.DiaryRequest request = new DiaryDto.DiaryRequest(
                "사건",
                "생각",
                List.of(
                        new EmotionOfEpisodeDto(
                                EmotionType.HAPPY, List.of("행복", "즐거움", "행복", "즐거움")
                        ),
                        new EmotionOfEpisodeDto(
                                EmotionType.ANGRY, List.of("화남", "분노")
                        )
                ),
                "결과"
        );

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/diary")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, createDefaultAuthentication())
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].message")
                        .value("선택할 수 있는 감정은 최대 5개 입니다."))
                .andDo(document("{ClassName}" + "/" + "{methodName}",
                        requestHeaders(
                                headerWithName(ACCEPT).description("Header"),
                                headerWithName(CONTENT_TYPE).description("Content type"),
                                headerWithName(AUTHORIZATION).description("Bearer token ")
                        ),
                        requestFields(
                                fieldWithPath("episode").type(STRING).description("Episode"),
                                fieldWithPath("thoughtOfEpisode").type(STRING).description("Thought of episode"),
                                fieldWithPath("emotionOfEpisodes[].type").type(STRING).description(Arrays.toString(EmotionType.values())),
                                fieldWithPath("emotionOfEpisodes[].details").type(ARRAY).description("Details of the emotion"),
                                fieldWithPath("resultOfEpisode").type(STRING).description("Result of the episode")
                        ),
                        responseFields(
                                commonResponseFields(
                                        fieldWithPath("[].field").type(STRING).description("에러 필드(emotionOfEpisodes)"),
                                        fieldWithPath("[].message").type(STRING).description("에러 메시지")
                                )
                        ))
                );
    }

    @Test
    void createDiary_withDuplicateEmotionType() throws Exception {
        DiaryDto.DiaryRequest request = new DiaryDto.DiaryRequest(
                "사건",
                "생각",
                List.of(
                        new EmotionOfEpisodeDto(
                                EmotionType.HAPPY, List.of("행복", "즐거움")
                        ),
                        new EmotionOfEpisodeDto(
                                EmotionType.HAPPY, List.of("행복", "즐거움")
                        )
                ),
                "결과"
        );

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/diary")
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, createDefaultAuthentication())
                        .content(objectMapper.writeValueAsString(request))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].message")
                        .value("중복된 감정 타입이 있습니다."))
                .andDo(document("{ClassName}" + "/" + "{methodName}",
                        requestHeaders(
                                headerWithName(ACCEPT).description("Header"),
                                headerWithName(CONTENT_TYPE).description("Content type"),
                                headerWithName(AUTHORIZATION).description("Bearer token ")
                        ),
                        requestFields(
                                fieldWithPath("episode").type(STRING).description("Episode"),
                                fieldWithPath("thoughtOfEpisode").type(STRING).description("Thought of episode"),
                                fieldWithPath("emotionOfEpisodes[].type").type(STRING).description(Arrays.toString(EmotionType.values())),
                                fieldWithPath("emotionOfEpisodes[].details").type(ARRAY).description("Details of the emotion"),
                                fieldWithPath("resultOfEpisode").type(STRING).description("Result of the episode")
                        ),
                        responseFields(
                                commonResponseFields(
                                        fieldWithPath("[].field").type(STRING).description("에러 필드(emotionOfEpisodes)"),
                                        fieldWithPath("[].message").type(STRING).description("에러 메시지")
                                )
                        ))
                );
    }

    @Test
    void updateDiary() throws Exception {
        DiaryDto.DiaryUpdateRequest request = new DiaryDto.DiaryUpdateRequest(
                "사건 업데이트",
                "생각 업데이트",
                List.of(new EmotionOfEpisodeDto(EmotionType.ANGRY, List.of("화남", "재수없음"))),
                "결과 업데이트"
        );

        mvc.perform(RestDocumentationRequestBuilders
                        .put("/api/v1/diary/{diaryId}", 1)
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
                                fieldWithPath("emotionOfEpisodes[].type").type(STRING).description(Arrays.toString(EmotionType.values())),
                                fieldWithPath("emotionOfEpisodes[].details").type(ARRAY).description("Details of the emotion"),
                                fieldWithPath("resultOfEpisode").type(STRING).description("Result of the episode")
                        ),
                        responseFields(
                                commonResponseFields(
                                        fieldWithPath("id").type(NUMBER).description("변경된 다이어리 ID")
                                )
                        )
                ));
    }

    @Test
    void serializeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"emotionOfEpisodes\":[{\"type\":\"ANGRY\",\"details\":[\"details\"]}]}";

        EmotionOfEpisodes result = objectMapper.readValue(json, EmotionOfEpisodes.class);
    }
}