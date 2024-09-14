package com.project.simi.domain.test.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.simi.domain.ai.cerebras.api.CerebrasRequestAPIClient;
import com.project.simi.domain.ai.dto.ChatMessage;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.dto.ChatResponse;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final CerebrasRequestAPIClient cerebrasRequestAPIClient;

    @GetMapping
    public String test() {
        return "Hello, world!";
    }

    @PostMapping
    public String testPost() {
        return "Hello, world!";
    }

    @PostMapping("/chat/completions")
    public ChatResponse testChat() {
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
        return cerebrasRequestAPIClient.getChatResponse(request).join();
    }
}
