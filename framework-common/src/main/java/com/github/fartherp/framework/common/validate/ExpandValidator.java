/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.validate;

import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/22
 */
public class ExpandValidator implements ExpandValidationProvider<HibernateValidatorConfiguration> {

    public Configuration createSpecializedConfiguration(BootstrapState state) {
        return HibernateValidatorConfiguration.class.cast(new ExpandConfigurationImpl(this, null));
    }

    public HibernateValidatorConfiguration createSpecializedConfiguration(BootstrapState state, Locale locale) {
        return HibernateValidatorConfiguration.class.cast(new ExpandConfigurationImpl(this, locale));
    }

    public Configuration<?> createGenericConfiguration(BootstrapState state) {
        return new ExpandConfigurationImpl(state, null);
    }

    public Configuration<?> createGenericConfiguration(BootstrapState state, Locale locale) {
        return new ExpandConfigurationImpl(state, locale);
    }

    public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
        return new ValidatorFactoryImpl(configurationState);
    }
}
