/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import javax.validation.Configuration;
import javax.validation.ValidationProviderResolver;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ValidationProvider;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/22
 */
public interface ExpandValidationProvider<T extends Configuration<T>> extends ValidationProvider {

    /**
     * Returns a {@link Configuration} instance. This instance is not bound to
     * use the current provider. The choice of provider follows the algorithm described
     * in {@code Configuration}
     * <p/>
     * The {@link ValidationProviderResolver} used by {@code Configuration}
     * is provided by {@code state}.
     * If null, the default {@code ValidationProviderResolver} is used.
     *
     * @param state bootstrap state
     * @param locale Locale
     * @return non specialized Configuration implementation
     */
    Configuration<?> createGenericConfiguration(BootstrapState state, Locale locale);

    /**
     * Returns a {@link Configuration} instance implementing {@code T},
     * the {@code Configuration} sub-interface.
     * The returned {@code Configuration} instance must use the current provider
     * ({@code this}) to build the {@code ValidatorFactory} instance.
     *
     * @param state bootstrap state
     * @param locale Locale
     * @return specific {@code Configuration} implementation
     */
    T createSpecializedConfiguration(BootstrapState state, Locale locale);
}
