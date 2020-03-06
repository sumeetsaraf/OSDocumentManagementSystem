/**
 * 
 */
package com.oneshield.dms.common;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, TYPE_PARAMETER })
@Constraint(validatedBy = ValidateByteArray.class)
/**
 * @author aasethi
 *
 */
public @interface ValidateByteArrayForSize {
    String message() default "Document Content size exceeds OS permissible size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
