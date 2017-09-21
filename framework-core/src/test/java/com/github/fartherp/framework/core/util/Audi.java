/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/9/12
 */
public class Audi extends Bmw {

    public Audi() {
        this.type = "奥迪";
    }

    public void get() {
        System.out.println(type);
    }
}
