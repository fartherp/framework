/*
 * Copyright (c) 2019. juzhen.io. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/1/7
 */
public class PatternsValidator implements ConstraintValidator<Patterns, Object> {

    private Pattern[] regexps;

    public void initialize(Patterns constraintAnnotation) {
        String[] values = constraintAnnotation.regexps();
        this.regexps = new Pattern[values.length];
        for (int i = 0; i < values.length; i++) {
            this.regexps[i] = Pattern.compile(values[i]);
        }
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        for (Pattern pattern : regexps) {
            if (pattern.matcher(value.toString()).matches()) {
                return true;
            }
        }
        return false;
    }
}
