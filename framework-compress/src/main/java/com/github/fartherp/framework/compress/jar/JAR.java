/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.jar;

import com.github.fartherp.framework.compress.CommonCompress;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/6/30.
 */
public class JAR extends CommonCompress {
    public JAR() {
        setType(JAR);
        setSuffix(SUFFIX_JAR);
        commonCompress = new JarCompress(this);
    }
}
