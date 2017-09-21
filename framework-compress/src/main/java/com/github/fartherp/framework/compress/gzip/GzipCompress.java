/*
 * Copyright (c) 2017. CK. All rights reserved.
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

    private CommonCompress commonCompress;

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
        GzipCompressorOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new GzipCompressorOutputStream(httpServletResponse.getOutputStream());
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 压缩文件或文件夹错误", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    public void startCompress(File sourceFile, File targetFile) {
        GzipCompressorOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new GzipCompressorOutputStream(new FileOutputStream(targetFile));
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 压缩文件或文件夹错误", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    protected void startUnCompress(File sourceFile, File target) {
        GzipCompressorInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new GzipCompressorInputStream(new FileInputStream(sourceFile));
            outputStream = new FileOutputStream(target);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 解压缩文件或文件夹错误", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
