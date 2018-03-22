/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import javax.validation.Configuration;
import javax.validation.ValidationException;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/22
 */
public interface ExpandProviderSpecificBootstrap<T extends Configuration<T>> extends ProviderSpecificBootstrap<T> {

    /**
     * Determines the provider implementation suitable for {@code T} and delegates
     * the creation of this specific {@link Configuration} subclass to the provider.
     *
     * @param locale Locale
     * @return {@code Configuration} sub interface implementation
     *
     * @throws ValidationException if the {@code Configuration} object cannot be built;
     *         this is generally due to an issue with the {@code ValidationProviderResolver}
     */
    public T configure(Locale locale);
}
