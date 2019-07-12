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
package com.github.fartherp.framework.common.validate;

import org.hibernate.validator.HibernateValidator;

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

        Class<?>[] group = (groups == null || groups.length ==0) ? new Class[]{Default.class} : groups;

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(flag).buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, group);
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<T> constraint : constraintViolations) {
				sb.append(constraint.getMessage());
				sb.append(",");
            }
        }

        if (sb.length() > 0) {
            throw new IllegalArgumentException(sb.deleteCharAt(sb.length() - 1).toString());
        }
    }
}
