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

import com.github.fartherp.framework.compress.CommonCompress;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <p>
 * Unix 的 gzip格式 压缩功能类。
 * gzip只提供单一文件的压缩、解压缩 ，不提供文件夹的压缩、解压缩
 * </p>
 *
 * Author: CK
 */
public class GzipCompress extends CommonCompress {

    public GzipCompress(CommonCompress commonCompress) {
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
        try (GzipCompressorOutputStream outputStream = new GzipCompressorOutputStream(httpServletResponse.getOutputStream());
             FileInputStream inputStream = new FileInputStream(sourceFile)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 压缩文件或文件夹错误", e);
        }
    }

    public void startCompress(File sourceFile, File targetFile) {
        try (GzipCompressorOutputStream outputStream = new GzipCompressorOutputStream(new FileOutputStream(targetFile));
             FileInputStream inputStream = new FileInputStream(sourceFile)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 压缩文件或文件夹错误", e);
        }
    }

    protected void startUnCompress(File sourceFile, File target) {
        try (FileOutputStream outputStream = new FileOutputStream(target);
             GzipCompressorInputStream inputStream = new GzipCompressorInputStream(new FileInputStream(sourceFile))) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 解压缩文件或文件夹错误", e);
        }
    }
}
