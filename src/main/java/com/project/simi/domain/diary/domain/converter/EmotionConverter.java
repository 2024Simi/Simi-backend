package com.project.simi.domain.diary.domain.converter;

import java.io.IOException;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.simi.domain.diary.domain.Emotion;

@Converter(autoApply = true)
public class EmotionConverter implements AttributeConverter<Emotion, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Emotion emotion) {
        try {
            return objectMapper.writeValueAsString(emotion);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Emotion 객체를 JSON으로 변환하는 데 실패했습니다.");
        }
    }

    @Override
    public Emotion convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Emotion.class);
        } catch (IOException e) {
            throw new RuntimeException("JSON 데이터를 Emotion 객체로 변환하는 데 실패했습니다.");
        }
    }
}
