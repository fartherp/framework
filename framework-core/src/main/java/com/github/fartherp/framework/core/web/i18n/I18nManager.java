/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/6
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
