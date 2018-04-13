/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.database.mybatis.plugin.search.enums;

/**
 * <p>User: hyssop
 * <p>Date: 13-2-7 上午11:44
 * <p>Version: 1.0
 */
public enum AvailableEnum {
    TRUE(Boolean.TRUE, "可用"),
    FALSE(Boolean.FALSE, "不可用");

    private  Boolean value;
    private  String info;

    private AvailableEnum(Boolean value, String info) {
        this.value = value;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public Boolean getValue() {
        return value;
    }
}
