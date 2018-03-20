/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ValueValidator implements ConstraintValidator<Value, Object> {

    private String[] values;

    public void initialize(Value constraintAnnotation) {
        this.values = constraintAnnotation.values();
        Arrays.sort(this.values);
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return (Arrays.binarySearch(values, value) >= 0);
    }
}
