/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter;

import com.github.fartherp.framework.core.web.util.RequestUtils;
import com.github.fartherp.framework.core.web.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * record request time
 * Author: CK
 * Date:2014/10/6
 */
public class ProcessTimeFilter implements Filter {
    protected final Logger logger = LoggerFactory.getLogger(ProcessTimeFilter.class);

    public static final String START_TIME = ProcessTimeFilter.class.getName() + ".START_TIME";

    private String excludePath;

    private SortedSet<String> excludePathSet;

    public void init(FilterConfig filterConfig) throws ServletException {
        excludePath = filterConfig.getInitParameter("excludePath");
        excludePathSet = new TreeSet<>();
        if (excludePath != null) {
            String[] paths = excludePath.split(";");
            for (String p : paths) {
                if (!StringUtils.isBlank(p)) {
                    excludePathSet.add(p);
                }
            }
        }
        logger.info("ProcessTimeFilter init");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 如果在excludePath中，则跳过
        if (StringUtils.isNotBlank(UrlUtils.urlMatch(excludePathSet, RequestUtils.getReqPath(httpRequest)))) {
            chain.doFilter(request, response);
            return;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        request.setAttribute(START_TIME, stopWatch);
        chain.doFilter(request, response);
        stopWatch.stop();
        if (null != request.getAttribute(START_TIME)) {
            logger.info("URL[" + httpRequest.getRequestURI() + "]executeTime[" + stopWatch.toString() + "]");
        }
    }

    public void destroy() {
        logger.info("ProcessTimeFilter destroy");
    }
}
