package com.project.simi.mock.ai;

import com.project.simi.domain.ai.domain.AIPrompt;
import com.project.simi.domain.ai.dto.ChatMessage;
import com.project.simi.domain.ai.dto.ChatRequest;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockPromptFactory {
    @Bean(name = "defaultPrompt")
    public AIPrompt defaultPrompt() {
        return AIPrompt.createDefault(new ChatRequest(
            List.of(
                new ChatMessage("Hello", "user")
            ), "llama3.1-8b",
            false,
            0.8,
            0.8,
            200
        ));
    }
}
