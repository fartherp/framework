/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.zip;

import com.github.fartherp.framework.compress.Compress;

public class ZipCompressTest {

    public static Compress compress = new ZIP();

    public void testCompress() throws Exception {
        // 压缩到当前目录
        compress.source("D:\\demo").compress();
    }

    public void testCompress1() {
        // 压缩文件到当前目录
        compress.source("D:\\demo\\test.js").compress();
    }

    public void testCompress2() throws Exception {
        // 压缩到指定文件名
        compress.source("D:\\demo").target("D:\\a.zip").compress();
    }

    public void testCompress3() {
        // 压缩文件到指定文件名
        compress.source("D:\\demo\\test.js").target("D:\\2\\a.zip").compress();
    }

    public void testUnCompress() throws Exception {
        // 解压到当前目录
        compress.source("D:\\demo.zip").unCompress();
    }

    public void testUnCompress1() throws Exception {
        // 解压新目录
        compress.source("D:\\demo.zip").target("D:\\3\\").unCompress();
    }
}