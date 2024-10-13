package com.project.simi.api.ai;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.simi.SuperIntegrationTest;
import com.project.simi.domain.ai.dto.ChatMessage;
import com.project.simi.domain.ai.dto.ChatRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

class AIPromptCommandTest extends SuperIntegrationTest {
    @Test
    void createAIPrompt() throws Exception {
        ChatRequest request = getRequest();

        mvc.perform(RestDocumentationRequestBuilders
                        .post("/api/v1/admin/ai/prompt")
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
                                fieldWithPath("messages").type(ARRAY).description("messages"),
                                fieldWithPath("messages.[].content").description("content"),
                                fieldWithPath("messages.[].role").description("role"),
                                fieldWithPath("model").type("String").description("model"),
                                fieldWithPath("stream").type("boolean").description("stream"),
                                fieldWithPath("temperature").type("double").description("temperature"),
                                fieldWithPath("top_p").type("double").description("top_p"),
                                fieldWithPath("max_tokens").type("int").description("max_tokens")
                        ),
                        responseFields(
                                commonResponseFields(
                                    fieldWithPath("id").description("id").type("Long")
                                )
                        )
                ));
    }

    private ChatRequest getRequest() {
        ChatMessage systemMessage =
            new ChatMessage(
                "Please try to provide useful, helpful and actionable answers.", "system");
        ChatMessage userMessage1 =
            new ChatMessage(
                "내가 일기를 주면 너는 거기에 공감하는 답변을 해줄 수 있어? 장원영의 말투를 따라하면 좋을 것 같아. 장원영의 말투는 이래: 아~ 그리구나 어제 완전 맛있는 스콘 먹었단말이야! 근데 역시 난 럭키비키인게, 딱 내가 갔더니 방금 막 나온 따뜻한 스콘이 나왔어 ><! 그래서 나 새스콘 받아가지고 나왔어. 완전 따뜻해서 스콘 안고 가는데, 이건 지금 먹어야겠는 거야! 그래서 바로 한입 와앙 깨물었어. 우오앙! 너무 맛있는거야!!",
                "user");
        ChatMessage userMessage2 =
            new ChatMessage(
                "오늘은 유난히 힘든 하루였다. 아침부터 모든 게 어긋나는 느낌이었다. 무심코 올려다본 하늘은 흐리고, 내 마음도 그 하늘처럼 무거웠다. 사람들이 웃고 떠들며 지나가지만, 그 속에서 나는 혼자인 것 같았다. 작은 일에도 눈물이 날 것 같고, 아무도 나를 이해하지 못하는 것 같아서 더 외롭고 슬펐다. 평소엔 괜찮을 것 같은 일들도 오늘은 더 크게 다가왔다. 어딘가 잘못된 것 같은 느낌이 나를 짓눌렀다. 괜히 울컥하는 마음에 혼자 눈물을 훔치고, 괜찮다고 스스로를 다독여보지만, 슬픔은 쉽게 가시지 않았다.",
                "user");
        ChatRequest request =
            new ChatRequest(
                List.of(systemMessage, userMessage1, userMessage2),
                "llama3.1-8b",
                false,
                0.2,
                1.0,
                1000);
        return request;
    }

}
