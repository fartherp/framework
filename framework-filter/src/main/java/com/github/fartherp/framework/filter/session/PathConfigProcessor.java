/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.session;

import javax.servlet.Filter;

/**
 * A PathConfigProcessor processes configuration entries on a per path (url) basis.
 *
 * @since 0.9
 */
public interface PathConfigProcessor {

    /**
     * Processes the specified {@code config}, unique to the given {@code path}, and returns the Filter that should
     * execute for that path/config combination.
     *
     * @param path   the path for which the {@code config} should be applied
     * @param config the configuration for the {@code Filter} specific to the given {@code path}
     * @return the {@code Filter} that should execute for the given path/config combination.
     */
    Filter processPathConfig(String path, String config);
}
