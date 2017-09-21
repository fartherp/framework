/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter;

import com.github.fartherp.framework.core.web.ThreadLocalInfo;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 请求绑定到线程级别
 * Author: CK
 * Date: 2015/8/17
 */
public class FrameWorkFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ThreadLocalInfo.set(request, response);
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
