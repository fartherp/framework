/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.tag;

import com.github.fartherp.framework.common.constant.Constant;
import com.github.fartherp.framework.core.web.util.SessionHelper;
import com.github.fartherp.framework.core.web.util.UrlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;
import java.util.SortedSet;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/14
 */
@DefaultKey("AuthCheck")
@ValidScope(Scope.APPLICATION)
public class AuthTag extends ConditionalTagSupport {

    public AuthTag() {
        super();
    }

    public void release() {
        super.release();
    }

    private String test;

    /**
     * 获取是否有权限访问这个path
     *
     * @param path url路径
     * @return 返回true或false
     */
    public boolean hasAuth(String path) {
        // 从缓存中获取当前用户所能操作的所有的url
        SortedSet<String> urls = SessionHelper.getAuthWrap().getAuthUrlSet();
        return CollectionUtils.isNotEmpty(urls) && StringUtils.isNotBlank(UrlUtils.urlMatch(urls, path));
    }

    /**
     * 判断是否可以访问 多个path
     *
     * @param paths 多个path用逗号隔开
     * @return 如果对其中有一个path有权限，则可访问，返回true
     */
    public boolean hasMultiAuth(String paths) {
        // 从缓存中获取当前用户所能操作的所有的url
        SortedSet<String> urls = SessionHelper.getAuthWrap().getAuthUrlSet();
        if (CollectionUtils.isNotEmpty(urls)) {
            StringTokenizer st = new StringTokenizer(paths, Constant.SEMICOLON_DELIMITER);
            while (st.hasMoreTokens()) {
                // 只要有一个url有权限则返回true
                if (StringUtils.isNotBlank(UrlUtils.urlMatch(urls, st.nextToken()))) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean condition() throws JspTagException {
        return hasAuth(test);
    }

    public void setTest(String test) {
        this.test = test;
    }
}
