package com.project.simi.domain.diary.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmotionTypeValidator.class)
public @interface UniqueEmotionTypeCheck {
    String message() default "중복된 감정 타입이 있습니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}
