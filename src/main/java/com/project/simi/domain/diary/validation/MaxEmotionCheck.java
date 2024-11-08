package com.project.simi.domain.diary.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxEmotionValidator.class)
public @interface MaxEmotionCheck {
    String message() default "선택할 수 있는 감정은 최대 5개 입니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
