/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.filter.filter.mgt;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by framework .
 * Auth: hyssop
 * Date: 2016-12-15-16:03
 */
public class BasicFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("基础的过滤器初始化");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("基础的过滤器开始工作");
    }

    public void destroy() {
        System.out.println("基础的过滤器初被销毁");
    }
}
