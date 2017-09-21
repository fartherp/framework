/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean.web;

import com.github.fartherp.framework.core.bean.ServiceLocator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化系统监听器
 * Author: CK
 * Date: 2015/6/24
 */
public class InitSystemListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        ServiceLocator.getInstance().setFactory(context);
    }

    public void contextDestroyed(ServletContextEvent event) {
        ServiceLocator.getInstance().setFactory(null);
    }
}
