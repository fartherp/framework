/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.tar;

import com.github.fartherp.framework.compress.CommonCompress;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/15
 */
public class TarCompress extends CommonCompress {

    public TarCompress(CommonCompress commonCompress) {
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
        try (TarArchiveOutputStream outputStream = new TarArchiveOutputStream(httpServletResponse.getOutputStream())) {
            doCompress(outputStream, sourceFile, sourceFile.getName());
        } catch (Exception e) {
            throw new RuntimeException("TAR 压缩文件或文件夹错误", e);
        }
    }

    public void startCompress(File sourceFile, File targetFile) {
        try (TarArchiveOutputStream outputStream = new TarArchiveOutputStream(new FileOutputStream(targetFile))) {
            // 解决文件内容过大
            outputStream.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR);
            // 解决文件名过长
            outputStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            doCompress(outputStream, sourceFile, sourceFile.getName());
        } catch (Exception e) {
            throw new RuntimeException("TAR 压缩文件或文件夹错误", e);
        }
    }

    public void doCompress(TarArchiveOutputStream tarArchiveOutputStream, File sourceFile, String path) throws Exception {
        if (sourceFile.isDirectory()) {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null) {
                throw new RuntimeException("源文件下没有文件可压缩");
            }
            if (listFiles.length < 1) {
                TarArchiveEntry entry = new TarArchiveEntry(path + File.separator);
                tarArchiveOutputStream.putArchiveEntry(entry);
                tarArchiveOutputStream.closeArchiveEntry();
            }
            path = path.equals(NULL_STR) ? NULL_STR : path + File.separator;
            for (File listFile : listFiles) {
                doCompress(tarArchiveOutputStream, listFile, path + listFile.getName());
            }
        } else {
            TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(path);
            tarArchiveEntry.setSize(sourceFile.length());
            tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(sourceFile));) {
                int count;
                byte [] bytes = new byte[BUFFER];
                while ((count = bufferedInputStream.read(bytes, 0, BUFFER)) != -1) {
                    tarArchiveOutputStream.write(bytes, 0, count);
                }
            }
            tarArchiveOutputStream.closeArchiveEntry();
        }
    }

    protected void startUnCompress(File sourceFile, File target) {
        try (TarArchiveInputStream inputStream = new TarArchiveInputStream(new FileInputStream(sourceFile))) {
            TarArchiveEntry entry = null;
            while (null != (entry = inputStream.getNextTarEntry())) {
                String path = target + File.separator + entry.getName();
                File dirFile = new File(path);
                if (entry.isDirectory()) {
                    dirFile.mkdirs();
                } else {
                    if (!dirFile.getParentFile().exists()) {
                        dirFile.getParentFile().mkdirs();
                    }
                    try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dirFile))) {
                        IOUtils.copy(inputStream, outputStream);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("TAR 解压缩文件或文件夹错误", e);
        }
    }
}
