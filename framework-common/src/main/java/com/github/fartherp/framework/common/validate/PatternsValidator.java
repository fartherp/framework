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
