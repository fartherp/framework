/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter;

import com.github.fartherp.framework.core.web.filter.auth.AuthWrapper;
import com.github.fartherp.framework.core.web.util.RequestUtils;
import com.github.fartherp.framework.core.web.util.SessionHelper;
import com.github.fartherp.framework.core.web.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 权限过滤器
 * Auth: CK
 * Date: 2016/8/27
 */
public class AuthFilter extends JndiSupportFilter {

    /**
     * 地址模版
     */
    public static final String RESP_HTML_TPL =
            "<script type='text/javascript'>(window.parent||window).location.replace('%s');</script>";

    /**
     * 重定向地址
     */
    private String index;

    /**
     * 过滤路径(静态文件,登录地址)
     */
    private String excludePath;

    private SortedSet<String> excludePathv;

    /**
     * 权限开关
     */
    private boolean open;

    public void doInit(FilterConfig filterConfig) throws ServletException {
        excludePathv = new TreeSet<>();
        if (excludePath != null) {
            String[] paths = excludePath.split(";");
            for (String p : paths) {
                if (!StringUtils.isBlank(p)) {
                    excludePathv.add(p);
                }
            }
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!open) {
            chain.doFilter(request, response);
            return;
        }
        // 如果在excludePath中，则跳过
        String realPath = RequestUtils.getReqPath((HttpServletRequest) request);
        if (StringUtils.isNotEmpty(UrlUtils.urlMatch(excludePathv, realPath))) {
            chain.doFilter(request, response);
            return;
        }
        // 权限为null,说明session过期,返回自定义页面
        AuthWrapper authWrapper = SessionHelper.getAuthWrap();
        if (authWrapper == null) {
            SessionHelper.logout((HttpServletRequest) request);
            printMessage((HttpServletResponse) response, RESP_HTML_TPL, index);
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * 输出信息到响应包.
     *
     * @param response 响应头,非空
     * @param template 响应模版,非空
     * @param args 参数
     * @throws IOException 写流时,能抛该异常.
     */
    private void printMessage(HttpServletResponse response, String template, Object... args) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        writer.write(String.format(template, args));
        writer.flush();
        writer.close();
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
