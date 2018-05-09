/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/5/9
 */
public enum SheetsTitlesEnum {
    USER_LOGIN_LOG("系统登录记录", "登录时间,账号,访问终端,登录IP,状态"),
    ;
    private String head;

    private String[] title;

    private SheetsTitlesEnum(String head, String title) {
        this.head = head;
        this.title = title.split(",");
    }

    public String getHead() {
        return head;
    }

    public String[] getTitle() {
        return title;
    }
}
