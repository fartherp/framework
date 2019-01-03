/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.bzip2;

import com.github.fartherp.framework.compress.Compress;

public class BZip2CompressTest {

    public static Compress compress = new BZip2();

    public void testCompress() {
        compress.source("D:\\demo\\test.js").target("D:\\2\\1.bz2").compress();
    }

    public void testUnCompress() throws Exception {
        compress.source("D:\\2\\1.bz2").target("D:\\3").unCompress();
    }
}