/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.jar;

import com.github.fartherp.framework.compress.Compress;

public class JarCompressTest {

    public static Compress compress = new JAR();

    public void testCompress() {
        compress.source(JarCompressTest.class.getResource("/").getPath()).target("D:\\3\\");
        compress.compress();
    }

    public void testUnCompress() throws Exception {
        compress.source("D:\\3\\test-classes.jar").target("D:\\3\\").unCompress();
    }
}