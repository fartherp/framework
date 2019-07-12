/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
