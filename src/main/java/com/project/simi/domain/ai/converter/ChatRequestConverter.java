package com.project.simi.domain.ai.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.project.simi.domain.ai.dto.ChatRequest;

@Converter
public class ChatRequestConverter implements AttributeConverter<ChatRequest, String> {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
    }

    @Override
    public String convertToDatabaseColumn(ChatRequest attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting ChatRequest to JSON string", e);
        }
    }

    @Override
    public ChatRequest convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<ChatRequest>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to ChatRequest", e);
        }
    }
}
