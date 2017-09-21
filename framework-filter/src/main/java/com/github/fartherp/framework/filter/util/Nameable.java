/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.util;

/**
 * Interface implemented by components that can be named, such as via configuration, and wish to have that name
 * set once it has been configured.
 *
 * @since 0.9
 */
public interface Nameable {

    /**
     * Sets the (preferably application unique) name for this component.
     * @param name the preferably application unique name for this component.
     */
    void setName(String name);
}
