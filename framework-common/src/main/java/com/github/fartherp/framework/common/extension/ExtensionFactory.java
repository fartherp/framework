/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.extension;

/**
 * ExtensionFactory
 *
 * @author CK
 */
@SPI
public interface ExtensionFactory {

    /**
     * Get extension.
     *
     * @param type object type.
     * @param name object name.
     * @return object instance.
     */
    <T> T getExtension(Class<T> type, String name);

}
