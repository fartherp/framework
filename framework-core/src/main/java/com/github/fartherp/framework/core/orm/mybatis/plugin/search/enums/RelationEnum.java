/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.orm.mybatis.plugin.search.enums;

/**
 * Created by IntelliJ IDEA .
 * Auth: hyssop
 * Date: 2016/9/6 0006
 */
public enum RelationEnum {
    AND("and"," and "),
    OR("or"," or ");
    private final String value;
    private final String info;

    private RelationEnum(String value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }
}
