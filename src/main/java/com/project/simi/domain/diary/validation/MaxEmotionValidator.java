package com.project.simi.domain.diary.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.project.simi.domain.diary.domain.EmotionOfEpisode;

public class MaxEmotionValidator implements ConstraintValidator<MaxEmotionCheck, EmotionOfEpisode> {

    @Override
    public boolean isValid(EmotionOfEpisode emotion, ConstraintValidatorContext context) {
        if (emotion.getDetails().size() > 5) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("선택할 수 있는 감정은 최대 5개 입니다.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
