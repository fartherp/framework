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
package com.github.fartherp.framework.compress.shell;

import com.github.fartherp.framework.common.util.Tools;

/**
 * <p>gzip压缩解压 在aix5测试通过</p>
 *
 * @author CK
 */
public class GzipCompressShell extends CompressShell {
    /**
     * @param sourceFile 源文件
     */
    public void gzip(String sourceFile) {
        try {
            Tools.executeShell("gzip -f " + sourceFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sourceFile 源文件 包括路径
     */
    public void unGzip(String sourceFile) {
        try {
            Tools.executeShell("gzip -d -f " + sourceFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	@Override
    public void compress(String source) {
        validation(source);
        gzip(source);
    }

	@Override
    public void unCompress(String source) {
        validation(source);
        unGzip(source);
    }
}
