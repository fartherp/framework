/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import com.github.fartherp.framework.common.util.OutputUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * 验证工具类
 * Author: CK
 * Date: 2015/9/29
 */
public class ValidateUtils {

    /**
     * 验证对象属性
     * @param bean 对象
     * @param groups 分组
     * @param <T> 泛型
     */
    public static <T> void validate(T bean, Class<?>... groups) {
        StringBuilder sb = new StringBuilder();

        Class<?>[] group;
        if (groups == null) {
            group = new Class<?>[1];
            group[0] = Default.class;
        } else {
            group = new Class<?>[groups.length + 1];
            System.arraycopy(groups, 0, group, 0, groups.length);
            group[groups.length] = Default.class;
        }

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, group);
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<T> constraint : constraintViolations) {
                sb.append(constraint.getMessage());
                OutputUtils.newLine(sb);
            }
        }

        if (sb.length() > 0) {
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
