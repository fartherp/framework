/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.filter.mgt;

import com.github.fartherp.framework.filter.util.PatternMatcher;
import com.github.fartherp.framework.filter.util.WebUtils;
import com.google.inject.Injector;
import com.google.inject.Key;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

class SimpleFilterChainResolver implements FilterChainResolver {
    private final Map<String, Key<? extends Filter>[]> chains;
    private final Injector injector;
    private final PatternMatcher patternMatcher;

    SimpleFilterChainResolver(Map<String, Key<? extends Filter>[]> chains, Injector injector, PatternMatcher patternMatcher) {
        this.chains = chains;
        this.injector = injector;
        this.patternMatcher = patternMatcher;
    }

    public FilterChain getChain(ServletRequest request, ServletResponse response, final FilterChain originalChain) {
        String path = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
        for (final String pathPattern : chains.keySet()) {
            if (patternMatcher.matches(pathPattern, path)) {
                final Iterator<Key<? extends Filter>> chain = Arrays.asList(chains.get(pathPattern)).iterator();
                return new SimpleFilterChain(originalChain, new Iterator<Filter>() {
                    public boolean hasNext() {
                        return chain.hasNext();
                    }

                    public Filter next() {
                        return injector.getInstance(chain.next());
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                });
            }
        }
        return null;
    }

}
