/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.filter.mgt;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import java.util.List;

/**
 * A {@code NamedFilterList} is a {@code List} of {@code Filter} instances that is uniquely identified by a
 * {@link #getName() name}.  It has the ability to generate new {@link FilterChain} instances reflecting this list's
 * filter order via the {@link #proxy proxy} method.
 *
 * @since 1.0
 */
public interface NamedFilterList extends List<Filter> {

    /**
     * Returns the configuration-unique name assigned to this {@code Filter} list.
     *
     * @return the configuration-unique name assigned to this {@code Filter} list.
     */
    String getName();

    /**
     * Returns a new {@code FilterChain} instance that will first execute this list's {@code Filter}s (in list order)
     * and end with the execution of the given {@code filterChain} instance.
     *
     * @param filterChain the {@code FilterChain} instance to execute after this list's {@code Filter}s have executed.
     * @return a new {@code FilterChain} instance that will first execute this list's {@code Filter}s (in list order)
     * and end with the execution of the given {@code filterChain} instance.
     */
    FilterChain proxy(FilterChain filterChain);
}
