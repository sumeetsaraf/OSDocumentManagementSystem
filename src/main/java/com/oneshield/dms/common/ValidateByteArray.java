package com.oneshield.dms.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateByteArray implements ConstraintValidator<ValidateByteArrayForSize, byte[]>
{

    @Override
    public boolean isValid(
                           byte[] value,
                           ConstraintValidatorContext context)
    {
        return DMSHelper.getSizeOfByteArray(
                                            value);
    }

    @Override
    public void initialize(
                           ValidateByteArrayForSize constraintAnnotation)
    {

    }

}
