/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/9/12
 */
public class Bmw implements Car {
    public Bmw() {
        this.type = "宝马";
    }

    protected String type;

    public void get() {
        System.out.println(type);
    }
}
