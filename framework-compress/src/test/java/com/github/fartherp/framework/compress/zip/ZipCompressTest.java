/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.zip;

import com.github.fartherp.framework.compress.Compress;
import org.testng.annotations.Test;

public class ZipCompressTest {

    public static Compress compress = new ZIP();

    @Test
    public void testCompress() throws Exception {
        // 压缩到当前目录
        compress.source("D:\\demo");
        compress.compress();
    }

    @Test
    public void testCompress1() {
        // 压缩文件到当前目录
        compress.source("D:\\demo\\test.js");
        compress.compress();
    }

    @Test
    public void testCompress2() throws Exception {
        // 压缩到指定文件名
        compress.source("D:\\demo").setTarget("D:\\a.zip");
        compress.compress();
    }

    @Test
    public void testCompress3() {
        // 压缩文件到指定文件名
        compress.source("D:\\demo\\test.js");
        compress.target("D:\\2\\a.zip");
        compress.compress();
    }

    @Test
    public void testUnCompress() throws Exception {
        // 解压到当前目录
        compress.source("D:\\demo.zip");
        compress.unCompress();
    }

    @Test
    public void testUnCompress1() throws Exception {
        // 解压新目录
        compress.source("D:\\demo.zip").setTarget("D:\\3\\");
        compress.unCompress();
    }
}