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
package com.github.fartherp.framework.compress.bzip2;

import com.github.fartherp.framework.compress.CommonCompress;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/15
 */
public class BZip2Compress extends CommonCompress {

    public BZip2Compress(CommonCompress commonCompress) {
        this.commonCompress = commonCompress;
    }

    public void compress() {
        if (2 == commonCompress.getFlag()) {
            startCompress(commonCompress.getSourceFile(), commonCompress.getHttpServletResponse());
        } else {
            startCompress(commonCompress.getSourceFile(), commonCompress.getTargetFile());
        }
    }

    public String unCompress() {
        File target = commonCompress.getTargetFile();
        startUnCompress(commonCompress.getSourceFile(), target);
        return target.getAbsolutePath();
    }

    public void startCompress(File sourceFile, HttpServletResponse httpServletResponse) {
        try (FileInputStream inputStream = new FileInputStream(sourceFile)) {
            doCompress(inputStream, httpServletResponse.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("ZIP 压缩文件或文件夹错误", e);
        }
    }

    public void startCompress(File sourceFile, File targetFile) {
        try (BZip2CompressorOutputStream outputStream = new BZip2CompressorOutputStream(new FileOutputStream(targetFile));
             FileInputStream inputStream = new FileInputStream(sourceFile)) {
            doCompress(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("ZIP 压缩文件或文件夹错误", e);
        }
    }

    public void doCompress(InputStream inputStream, OutputStream outputStream) throws Exception {
        IOUtils.copy(inputStream, outputStream);
    }

    protected void startUnCompress(File sourceFile, File target) {
        try (FileOutputStream outputStream = new FileOutputStream(target);
             BZip2CompressorInputStream inputStream = new BZip2CompressorInputStream(new FileInputStream(sourceFile));) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 解压缩文件或文件夹错误", e);
        }
    }
}
