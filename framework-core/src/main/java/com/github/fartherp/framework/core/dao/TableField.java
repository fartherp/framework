/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.dao;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/5.
 */
public class TableField {

    //title: String 字段显示名
    private String title;
    //field: String 字段名
    private String field;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public TableField(String title, String field) {
        super();
        this.title = title;
        this.field = field;
    }

    public TableField() {
        super();
    }
}
