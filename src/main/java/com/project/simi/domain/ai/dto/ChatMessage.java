package com.project.simi.domain.ai.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage {
    private String content;
    private String role;

    public static ChatMessage createOf(String content, String role) {
        return new ChatMessage(content, role);
    }
}
