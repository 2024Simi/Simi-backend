package com.project.simi.domain.ai.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

import com.project.simi.domain.ai.converter.ChatRequestConverter;
import com.project.simi.domain.ai.dto.ChatRequest;
import com.project.simi.domain.common.domain.AbstractJpaIdentityPersistable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ai_prompt")
@AttributeOverride(name = "id", column = @Column(name = "ai_prompt_id"))
@Entity
@SQLRestriction("deleted_at is null")
public class AIPrompt extends AbstractJpaIdentityPersistable {
    @Column(name = "chat_request", columnDefinition = "TEXT")
    @Convert(converter = ChatRequestConverter.class)
    private ChatRequest chatRequest;

    private boolean isDefault = false;

    public static AIPrompt createOf(ChatRequest request) {
        return new AIPrompt(request, false);
    }
}
