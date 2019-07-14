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
package com.github.fartherp.framework.core.web.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/8/6
 */
public class I18nManager implements Serializable {

    private MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, checkLocale());
    }

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, checkLocale());
    }

    public Locale checkLocale() {
        return LocaleContextHolder.getLocale();
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }
}
