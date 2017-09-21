/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.front;

import com.github.fartherp.framework.filter.filter.mgt.FilterChainResolver;
import com.github.fartherp.framework.filter.servlet.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-12-15-19:33
 */
public class AbstractFrontFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(AbstractFrontFilter.class);
    // Used to determine which chain should handle an incoming request/response
    private FilterChainResolver filterChainResolver;

    public FilterChainResolver getFilterChainResolver() {
        return filterChainResolver;
    }

    public void setFilterChainResolver(FilterChainResolver filterChainResolver) {
        this.filterChainResolver = filterChainResolver;
    }

    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        executeChain(request, response, chain);
    }

    /**
     * @param request   the incoming ServletRequest
     * @param response  the outgoing ServletResponse
     * @param origChain the original {@code FilterChain} provided by the Servlet Container
     * @return the {@link FilterChain} to execute for the given request
     * @since 1.0
     */
    protected FilterChain getExecutionChain(ServletRequest request, ServletResponse response, FilterChain origChain) {
        FilterChain chain = origChain;

        FilterChainResolver resolver = getFilterChainResolver();
        if (resolver == null) {
            log.debug("No FilterChainResolver configured.  Returning original FilterChain.");
            return origChain;
        }

        FilterChain resolved = resolver.getChain(request, response, origChain);
        if (resolved != null) {
            log.trace("Resolved a configured FilterChain for the current request.");
            chain = resolved;
        } else {
            log.trace("No FilterChain configured for the current request.  Using the default.");
        }

        return chain;
    }

    protected void executeChain(ServletRequest request, ServletResponse response, FilterChain origChain)
            throws IOException, ServletException {
        FilterChain chain = getExecutionChain(request, response, origChain);
        chain.doFilter(request, response);
    }
}
