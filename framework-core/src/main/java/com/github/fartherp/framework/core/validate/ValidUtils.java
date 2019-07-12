/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.core.validate;

import com.github.fartherp.framework.common.util.OutputUtil;
import org.hibernate.validator.HibernateValidator;
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

    /**
     * 默认模式（非快速失败，所有验证一起返回）
     * @param bean 对象
     * @param error spring错误对象
     * @param groups 分组
     * @param <T> 泛型
     */
    public static <T> void valid(T bean, BindingResult error, Class<?>... groups) {
        valid(bean, error, true, groups);
    }

    /**
     * 验证对象属性
     * @param bean 对象
     * @param error spring错误对象
     * @param flag true: 快速失败返回模式    false: 普通模式
     * @param groups 分组
     * @param <T> 泛型
     */
    public static <T> void valid(T bean, BindingResult error, boolean flag, Class<?>... groups) {
        StringBuilder sb = new StringBuilder();

        List<FieldError> fieldErrors = error.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError: fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
                OutputUtil.newLine(sb);
            }
        }

        if (groups != null && groups.length > 0) {
            ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(flag).buildValidatorFactory();
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, groups);
            if (!constraintViolations.isEmpty()) {
                for (ConstraintViolation<T> constraint : constraintViolations) {
                    sb.append(constraint.getMessage());
                    OutputUtil.newLine(sb);
                }
            }
        }

        if (sb.length() > 0) {
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
