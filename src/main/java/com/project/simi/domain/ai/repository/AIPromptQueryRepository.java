package com.project.simi.domain.ai.repository;

import com.project.simi.domain.ai.dto.ChatRequest;

public interface AIPromptQueryRepository {
    ChatRequest getDefaultAIPrompt();
}
