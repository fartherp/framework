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

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/15
 */
public abstract class CompressShell {
    protected void compress(String command) {
        try {
            Tools.executeShell(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void unCompress(String command) {
        try {
            Tools.executeShell(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void validation(String source) {
        File file = new File(source);
        if (!file.exists()) {
            throw new RuntimeException("压缩文件不存在 ：" + file.getName());
        }
    }
}
