package com.project.simi.domain.diary.validation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.project.simi.domain.diary.dto.DiaryDto.EmotionOfEpisodeDto;

public class MaxEmotionValidator
        implements ConstraintValidator<MaxEmotionCheck, List<EmotionOfEpisodeDto>> {

    private static final int MAX_EMOTIONS = 5;

    @Override
    public boolean isValid(
            List<EmotionOfEpisodeDto> emotionList, ConstraintValidatorContext context) {
        if (getTotalEmotions(emotionList) > MAX_EMOTIONS) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "선택할 수 있는 감정은 최대 " + MAX_EMOTIONS + "개 입니다.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private int getTotalEmotions(List<EmotionOfEpisodeDto> emotionList) {
        return emotionList.stream().mapToInt(emotion -> emotion.details().size()).sum();
    }
}
