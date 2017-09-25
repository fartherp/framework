/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.extension.factory;

import com.github.fartherp.framework.common.extension.ExtensionFactory;
import com.github.fartherp.framework.common.extension.ExtensionLoader;
import com.github.fartherp.framework.common.extension.SPI;

/**
 * SpiExtensionFactory
 *
 * @author CK
 */
public class SpiExtensionFactory implements ExtensionFactory {

    public <T> T getExtension(Class<T> type, String name) {
        if (type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> loader = ExtensionLoader.getExtensionLoader(type);
            if (loader.getSupportedExtensions().size() > 0) {
                return loader.getAdaptiveExtension();
            }
        }
        return null;
    }

}
