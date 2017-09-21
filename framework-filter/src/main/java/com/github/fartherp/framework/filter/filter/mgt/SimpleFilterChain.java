/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.filter.mgt;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Iterator;

class SimpleFilterChain implements FilterChain {
    private final FilterChain originalChain;
    private final Iterator<? extends Filter> chain;

    private boolean originalCalled = false;

    public SimpleFilterChain(FilterChain originalChain, Iterator<? extends Filter> chain) {
        this.originalChain = originalChain;
        this.chain = chain;
    }

    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (chain.hasNext()) {
            Filter filter = chain.next();
            filter.doFilter(request, response, this);
        } else if (!originalCalled) {
            originalCalled = true;
            originalChain.doFilter(request, response);
        }
    }

    /**
     * Exposed for testing, not part of public API.
     *
     * @return an Iterater of filters.
     */
    Iterator<? extends Filter> getFilters() {
        return chain;
    }

}
