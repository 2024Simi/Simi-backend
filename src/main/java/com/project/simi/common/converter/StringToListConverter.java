package com.project.simi.common.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.project.simi.auth.enums.AuthoriryEnum;

@Converter
public class StringToListConverter implements AttributeConverter<List<AuthoriryEnum>, String> {

    @Override
    public String convertToDatabaseColumn(List<AuthoriryEnum> attribute) {
        return attribute.stream().map(AuthoriryEnum::name).reduce((a, b) -> a + "," + b).orElse("");
    }

    @Override
    public List<AuthoriryEnum> convertToEntityAttribute(String dbData) {
        return Optional.of(Stream.of(dbData.split(",")).map(AuthoriryEnum::valueOf).toList())
                .orElse(List.of());
    }
}
