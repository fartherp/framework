/*
 * Copyright (c) 2017. CK. All rights reserved.
 */
package com.github.fartherp.framework.common.extension.ext1.impl;

import com.github.fartherp.framework.common.extension.URL;
import com.github.fartherp.framework.common.extension.ext1.SimpleExt;

/**
 * @author CK
 */
public class SimpleExtImpl3 implements SimpleExt {
    public String echo(URL url, String s) {
        return "Ext1Impl3-echo";
    }

    public String yell(URL url, String s) {
        return "Ext1Impl3-yell";
    }

    public String bang(URL url, int i) {
        return "bang3";
    }

}