/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.gzip;

import com.github.fartherp.framework.compress.Compress;

public class GzipCompressTest {

    public static Compress compress = new GZIP();

    public void testCompress() {
        compress.source("D:\\logs");
//        compress.setTarget("D:\\2\\");
        compress.compress();
    }

    public void testUnCompress() throws Exception {
        compress.source("D:\\2\\1.gz").target("D:\\3\\").unCompress();
    }
}