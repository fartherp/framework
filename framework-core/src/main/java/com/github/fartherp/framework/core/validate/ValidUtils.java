/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.validate;

import com.github.fartherp.framework.common.util.OutputUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

/**
 * 基于spring验证注解并返回错误信息
 *
 * @author: CK
 * @date: 2018/3/12
 */
public class ValidUtils {

    public static <T> void valid(T bean, BindingResult error, Class<?>... groups) {
        StringBuilder sb = new StringBuilder();

        List<FieldError> fieldErrors = error.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError: fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
                OutputUtils.newLine(sb);
            }
        }

        if (groups != null && groups.length > 0) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, groups);
            if (!constraintViolations.isEmpty()) {
                for (ConstraintViolation<T> constraint : constraintViolations) {
                    sb.append(constraint.getMessage());
                    OutputUtils.newLine(sb);
                }
            }
        }

        if (sb.length() > 0) {
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
