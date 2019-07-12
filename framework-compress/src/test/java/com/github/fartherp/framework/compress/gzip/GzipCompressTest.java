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