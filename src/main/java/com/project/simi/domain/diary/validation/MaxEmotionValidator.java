package com.project.simi.domain.diary.validation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.project.simi.domain.diary.dto.DiaryDto.EmotionOfEpisodeDto;

public class MaxEmotionValidator
        implements ConstraintValidator<MaxEmotionCheck, List<EmotionOfEpisodeDto>> {

    @Override
    public boolean isValid(
            List<EmotionOfEpisodeDto> emotionList, ConstraintValidatorContext context) {
        int totalEmotions =
                emotionList.stream().mapToInt(emotion -> emotion.details().size()).sum();
        System.out.println("totalEmotions = " + totalEmotions);
        if (totalEmotions > 5) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("선택할 수 있는 감정은 최대 5개 입니다.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
