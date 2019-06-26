/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/20
 */
public class DateValueValidator implements ConstraintValidator<DateValue, Object> {
    private String values;

    public void initialize(DateValue constraintAnnotation) {
        this.values = constraintAnnotation.values();
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return true;
        }
        Date d = (Date) obj;
        Date date = new Date();
        return ("before".equals(values) && date.getTime() >= d.getTime())
			|| ("after".equals(values) && date.getTime() <= d.getTime());
    }
}
