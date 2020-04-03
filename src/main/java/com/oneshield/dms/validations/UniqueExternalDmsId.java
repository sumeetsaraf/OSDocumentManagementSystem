package com.oneshield.dms.validations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Constraint(validatedBy = UniqueExternalDmsIdValidator.class)
public @interface UniqueExternalDmsId {

    public String message() default "Provided externalDmsId is already in use!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
