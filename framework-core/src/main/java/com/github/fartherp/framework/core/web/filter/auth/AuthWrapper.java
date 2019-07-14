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
package com.github.fartherp.framework.core.web.filter.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/12/14
 */
public class AuthWrapper {
    /**
     * session中的用户
     */
    public static final String SESSION_USER_MESSAGE = "WRAP_SESSION_USER_MESSAGE";
    /**
     * session中菜单
     */
    public static final String SESSION_MENU_MESSAGE = "WRAP_SESSION_MENU_MESSAGE";
    /**
     * session中权限
     */
    public static final String SESSION_AUTH_URL_MESSAGE = "SESSION_AUTH_URL_MESSAGE";
    /**
     * session中AuthWrapper
     */
    public static final String SESSION_AUTH_WRAPPER_MESSAGE = "SESSION_AUTH_WRAPPER_MESSAGE";
    /**
     * 显示在左侧菜单列表
     */
    private List menuList;
    /**
     * url
     */
    private SortedSet<String> authUrlSet;

    public List getMenuList() {
        return menuList;
    }

    public void setMenuList(List menuList) {
        this.menuList = menuList;
    }

    public SortedSet<String> getAuthUrlSet() {
        return authUrlSet;
    }

    public void setAuthUrlSet(SortedSet<String> authUrlSet) {
        this.authUrlSet = authUrlSet;
    }

    public void addMenu(Object menu) {
        if (menuList == null) {
            menuList = new ArrayList();
        }
        menuList.add(menu);
    }

    public void addUrl(String url) {
        if (authUrlSet == null) {
            authUrlSet = new TreeSet<>();
        }
        authUrlSet.add(url);
    }
}
