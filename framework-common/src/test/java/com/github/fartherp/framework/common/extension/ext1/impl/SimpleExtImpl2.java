/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.extension.ext1.impl;

import com.github.fartherp.framework.common.extension.ext1.SimpleExt;

/**
 * @author CK
 */
public class SimpleExtImpl2 implements SimpleExt {
    public String echo(String s) {
        return "Ext1Impl2-echo";
    }

    public String yell(String s) {
        return "Ext1Impl2-yell";
    }

    public String bang(int i) {
        return "bang2";
    }

}