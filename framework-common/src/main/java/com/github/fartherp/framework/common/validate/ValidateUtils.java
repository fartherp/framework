/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import com.github.fartherp.framework.common.util.OutputUtils;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Locale;
import java.util.Set;

/**
 * 验证工具类
 * Author: CK
 * Date: 2015/9/29
 */
public class ValidateUtils {

    /**
     * 默认模式（非快速失败，所有验证一起返回）
     * @param bean 对象
     * @param groups 分组
     * @param <T> 泛型
     */
    public static <T> void validate(T bean, Class<?>... groups) {
        validate(bean, true, groups);
    }

    /**
     * 返回模式
     * @param bean 对象
     * @param flag true: 快速失败返回模式    false: 普通模式
     * @param groups 分组
     * @param <T> 泛型
     */
    public static <T> void validate(T bean, boolean flag, Class<?>... groups) {
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

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(flag).buildValidatorFactory();
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

    /**
     * 验证对象属性
     * @param bean 对象
     * @param locale 区域
     * @param groups 分组
     * @param <T> 泛型
     */
    public static <T> void validate(T bean, Locale locale, Class<?>... groups) {
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

        ValidatorFactory validatorFactory = ExpandValidation.byProvider(ExpandValidator.class).configure(locale).buildValidatorFactory();
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
