/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.http.session;

import com.github.fartherp.framework.core.web.ThreadLocalInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * HttpSession提供类
 * Author: CK
 * Date:2014/10/6
 */
public class HttpSessionProvider implements SessionProvider {
    public Serializable getAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Serializable) session.getAttribute(name);
        } else {
            return null;
        }
    }

    public Serializable getAttribute(String name) {
		HttpSession session = ThreadLocalInfo.currentHttpServletSession(false);
		if (session != null) {
			return (Serializable) session.getAttribute(name);
		} else {
			return null;
		}
	}

	public void setAttribute(HttpServletRequest request, HttpServletResponse response,
                             String name, Serializable value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

    public void setAttribute(String name, Serializable value) {
        HttpSession session = ThreadLocalInfo.currentHttpServletSession();
        session.setAttribute(name, value);
    }

    public void setAttribute(String name, Object value) {
        HttpSession session = ThreadLocalInfo.currentHttpServletSession();
        session.setAttribute(name, value);
    }

    public String getSessionId(HttpServletRequest request, HttpServletResponse response) {
		return request.getSession().getId();
	}

    public String getSessionId() {
        return ThreadLocalInfo.currentHttpServletSession().getId();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

    public void logout() {
        HttpSession session = ThreadLocalInfo.currentHttpServletSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
