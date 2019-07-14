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
 * <p>TarGz压缩解压 在aix5测试通过</p>
 *
 * @author CK
 */
public class TarGzCompressShell {
    /**
     * @param localPath
     * @param sourceFile 源文件 包括路径
     * @param tarFile  压缩包内文件 包括路径
     * @param i   目标文件 包括路径
     */
    public void tarGz(String localPath, String sourceFile, String tarFile, int i) {
        //检查本地文件是否存在
        File file = new File(localPath + "/" + sourceFile.trim());
        if (!file.exists()) {
            throw new RuntimeException("压缩源文件不存在 ：" + sourceFile);
        }
        //打包的列表文件
        try {
            Tools.executeShell("sh /bcspapp/bcsptargz.sh " + localPath + " " + tarFile + " " + sourceFile + " " + i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sourceFile 源文件 包括路径
     */
    public void unTarGz(String sourceFile) {
        File file = new File(sourceFile);
        if (!file.exists()) {
            throw new RuntimeException("压缩文件不存在 ：" + sourceFile);
        }
        String path = sourceFile.substring(0, sourceFile.lastIndexOf("/"));
        try {
            Tools.executeShell("sh /bcspapp/bcspgztar.sh " + sourceFile + " "
				+ path + " " + sourceFile.substring(0, sourceFile.lastIndexOf(".")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
