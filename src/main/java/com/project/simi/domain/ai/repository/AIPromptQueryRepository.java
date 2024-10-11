package com.project.simi.domain.ai.repository;

import com.project.simi.domain.ai.domain.AIPrompt;

public interface AIPromptQueryRepository {

    AIPrompt findDefaultPrompt();
}
