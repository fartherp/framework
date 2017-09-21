/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.zip;

import com.github.fartherp.framework.compress.CommonCompress;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/14
 */
public class ZIP extends CommonCompress {
    public ZIP() {
        setType(ZIP);
        setSuffix(SUFFIX_ZIP);
        commonCompress = new ZipCompress(this);
    }
}
