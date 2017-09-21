/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.filter.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/12/14
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
            authUrlSet = new TreeSet<String>();
        }
        authUrlSet.add(url);
    }
}
