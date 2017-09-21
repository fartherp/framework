/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <p>Session listener</p>
 *
 * @author CK
 */
public class CheckSessionListener implements HttpSessionListener {
    private static final Logger log = LoggerFactory.getLogger(CheckSessionListener.class);

    public void sessionCreated(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        log.info("[Session监听器]--Session启动了! " + sessionId + "上线了!");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        if (context != null) {
            log.info("session expired, logged user out");
        }
        String sessionId = session.getId();
        log.info("[Session监听器]--Session销毁了!" + sessionId + "上线了!");
    }
}
