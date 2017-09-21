/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.gzip;

import com.github.fartherp.framework.compress.CommonCompress;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/15
 */
public class GZIP extends CommonCompress {
    public GZIP() {
        setType(GZIP);
        setSuffix(SUFFIX_GZIP);
        commonCompress = new GzipCompress(this);
    }
}
