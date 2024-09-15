package com.project.simi.domain.ai.cerebras.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.project.simi.domain.ai.dto.AIModelInterface;

@Getter
@AllArgsConstructor
public enum CerebrasAIModel implements AIModelInterface {
    llama3_1_8b("llama3.1-8b"),
    llama3_1_80b("llama3.1-80b");

    private final String description;
}
