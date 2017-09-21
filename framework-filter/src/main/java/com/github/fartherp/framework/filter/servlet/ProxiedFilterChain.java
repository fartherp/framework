/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.filter.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A proxied filter chain is a {@link FilterChain} instance that proxies an original {@link FilterChain} as well
 * as a {@link List List} of other {@link Filter Filter}s that might need to execute prior to the final wrapped
 * original chain.  It allows a list of filters to execute before continuing the original (proxied)
 * {@code FilterChain} instance.
 *
 * @since 0.9
 */
public class ProxiedFilterChain implements FilterChain {

    private static final Logger log = LoggerFactory.getLogger(ProxiedFilterChain.class);

    private FilterChain orig;
    private List<Filter> filters;
    private int index = 0;

    public ProxiedFilterChain(FilterChain orig, List<Filter> filters) {
        if (orig == null) {
            throw new NullPointerException("original FilterChain cannot be null.");
        }
        this.orig = orig;
        this.filters = filters;
        this.index = 0;
    }

    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (this.filters == null || this.filters.size() == this.index) {
            //we've reached the end of the wrapped chain, so invoke the original one:
            if (log.isTraceEnabled()) {
                log.trace("Invoking original filter chain.");
            }
            this.orig.doFilter(request, response);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Invoking wrapped filter at index [" + this.index + "]");
            }
            this.filters.get(this.index++).doFilter(request, response, this);
        }
    }
}
