/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.compress.tar;

import com.github.fartherp.framework.compress.Compress;

public class TarCompressTest {

    public static Compress compress = new TAR();

    public void testCompress() {
        // 压缩目录到当前目录
        compress.source("D:\\demo").compress();
    }

    public void testCompress1() {
        // 压缩文件到当前目录
        compress.source("D:\\demo\\test.js").compress();
    }

    public void testCompress2() {
        // 压缩目录到指定文件名
        compress.source("D:\\demo").target("D:\\2\\a.tar").compress();
    }

    public void testCompress3() {
        // 压缩文件到指定文件名
        compress.source("D:\\demo\\test.js").target("D:\\2\\a.tar").compress();
    }

    public void testUnCompress() throws Exception {
        // 解压到当前目录
        compress.source("D:\\demo.tar").unCompress();
    }

    public void testUnCompress1() throws Exception {
        // 解压到指定文件夹
        compress.source("D:\\demo.tar").target("D:\\2\\").unCompress();
    }
}