/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.tar;

import com.github.fartherp.framework.compress.CommonCompress;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/15
 */
public class TAR extends CommonCompress {
    public TAR() {
        setType(TAR);
        setSuffix(SUFFIX_TAR);
        commonCompress = new TarCompress(this);
    }
}
