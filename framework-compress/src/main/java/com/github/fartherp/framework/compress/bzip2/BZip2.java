/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.bzip2;

import com.github.fartherp.framework.compress.CommonCompress;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/15
 */
public class BZip2 extends CommonCompress {
    public BZip2() {
        setType(BZIP2);
        setSuffix(SUFFIX_BZIP2);
        commonCompress = new BZip2Compress(this);
    }
}
