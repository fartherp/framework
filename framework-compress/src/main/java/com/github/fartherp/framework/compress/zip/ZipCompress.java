/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.zip;

import com.github.fartherp.framework.compress.CommonCompress;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Zip的压缩、解压缩功能类</p>
 *
 * @author cyq
 */
public class ZipCompress extends CommonCompress {

    public ZipCompress(CommonCompress commonCompress) {
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

    /**
     * 取得文件流，开始压缩文件
     *
     * @param sourceFile  源文件
     * @param targetFile 目标文件
     */
    public void startCompress(File sourceFile, File targetFile) {
        try (ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(
                new CheckedOutputStream(new FileOutputStream(targetFile), new CRC32())))) {
            doCompress(outputStream, sourceFile, sourceFile.getName());
        } catch (IOException e) {
            throw new RuntimeException("ZIP 压缩文件或文件夹错误", e);
        }
    }

    /**
     * 取得文件流，开始压缩文件
     *
     * @param sourceFile  源文件
     * @param httpServletResponse 响应
     */
    public void startCompress(File sourceFile, HttpServletResponse httpServletResponse) {
        try (ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(
                new CheckedOutputStream(httpServletResponse.getOutputStream(), new CRC32())));) {
            doCompress(outputStream, sourceFile, sourceFile.getName());
        } catch (IOException e) {
            throw new RuntimeException("ZIP 压缩文件或文件夹错误", e);
        }
    }

    /**
     * 递归压缩文件夹 or 压缩文件
     *
     * @param outputStream 输出流
     * @param sourceFile 源文件名
     * @param path 压缩文件路径
     */
    public void doCompress(ZipOutputStream outputStream, File sourceFile, String path) {
        FileInputStream inputStream = null;
        try {
            if (sourceFile.isDirectory()) {
                File[] listFiles = sourceFile.listFiles();
                outputStream.putNextEntry(new ZipEntry(path + File.separator));
                path = path.equals(NULL_STR) ? NULL_STR : path + File.separator;
                if (null != listFiles) {
                    for (File file : listFiles) {
                        doCompress(outputStream, file, path + file.getName());
                    }
                }
            } else {
                outputStream.putNextEntry(new ZipEntry(path));
                inputStream = new FileInputStream(sourceFile);
                IOUtils.copy(inputStream, outputStream);
            }
        } catch (Exception e) {
            // ignore
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    protected void startUnCompress(File sourceFile, File target) {
        try (ZipArchiveInputStream is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(sourceFile)))) {
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File f = new File(target, entry.getName());
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                } else {
                    try (OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(target, entry.getName())))) {
                        IOUtils.copy(is, os);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("ZIP 解压缩文件或文件夹错误", e);
        }
    }
}
