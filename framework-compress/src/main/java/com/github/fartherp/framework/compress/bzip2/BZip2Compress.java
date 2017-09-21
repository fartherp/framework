/*
 * Copyright (c) 2017. CK. All rights reserved.
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
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            doCompress(inputStream, httpServletResponse.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("ZIP 压缩文件或文件夹错误", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public void startCompress(File sourceFile, File targetFile) {
        FileInputStream inputStream = null;
        BZip2CompressorOutputStream outputStream = null;
        try {
            outputStream = new BZip2CompressorOutputStream(new FileOutputStream(targetFile));
            inputStream = new FileInputStream(sourceFile);
            doCompress(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("ZIP 压缩文件或文件夹错误", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    public void doCompress(InputStream inputStream, OutputStream outputStream) throws Exception {
        IOUtils.copy(inputStream, outputStream);
    }

    protected void startUnCompress(File sourceFile, File target) {
        FileOutputStream outputStream = null;
        BZip2CompressorInputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(target);
            inputStream = new BZip2CompressorInputStream(new FileInputStream(sourceFile));
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException("TAR 解压缩文件或文件夹错误", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
