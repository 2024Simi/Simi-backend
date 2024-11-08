package com.project.simi.domain.diary.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.project.simi.domain.diary.domain.EmotionType;
import com.project.simi.domain.diary.dto.DiaryDto.EmotionOfEpisodeDto;

public class UniqueEmotionTypeValidator
        implements ConstraintValidator<UniqueEmotionTypeCheck, List<EmotionOfEpisodeDto>> {

    @Override
    public boolean isValid(
            List<EmotionOfEpisodeDto> emotionList, ConstraintValidatorContext context) {
        if (!isUniqueType(emotionList)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isUniqueType(List<EmotionOfEpisodeDto> emotionList) {
        Set<EmotionType> uniqueTypes = new HashSet<>();
        for (EmotionOfEpisodeDto emotion : emotionList) {
            if (!uniqueTypes.contains(emotion.type())) {
                uniqueTypes.add(emotion.type());
            } else {
                return false;
            }
        }
        return true;
    }
}
