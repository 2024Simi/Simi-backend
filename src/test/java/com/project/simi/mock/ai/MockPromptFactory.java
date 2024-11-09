package com.project.simi.mock.ai;

import com.project.simi.domain.ai.domain.AIPrompt;
import com.project.simi.domain.ai.dto.ChatMessage;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.ai.repository.AIPromptCommandRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
@Transactional
@TestConfiguration
public class MockPromptFactory {
    @Autowired
    private AIPromptCommandRepository aiPromptCommandRepository;
    @Bean(name = "defaultPrompt")
    public AIPrompt defaultPrompt() {
        return aiPromptCommandRepository.save(AIPrompt.createDefault(new ChatRequest(
            List.of(
                new ChatMessage("Hello", "user")
            ), "llama3.1-8b",
            false,
            0.8,
            0.8,
            200
        )));
    }
}
