/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.file;

import com.github.fartherp.framework.common.constant.Constant;
import com.github.fartherp.framework.common.util.PathUtils;
import com.github.fartherp.framework.core.ftp.FtpConfig;
import com.github.fartherp.framework.core.ftp.FtpUtils;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/3/7
 */
public class FtpAttachStoreImpl implements FileStore {
    private static final Logger log = LoggerFactory.getLogger(FtpAttachStoreImpl.class);
    /**
     * Ftp配置
     */
    private FtpConfig fileStoreConfig;

    public String partitionDir(Object mountDir) {
        return fileStoreConfig.getDefaultDir() + File.separator + mountDir;
    }

    public String generateFilename(String rawName) {
        if (StringUtils.isBlank(rawName)) {
            throw new RuntimeException("Raw file name is blank");
        }
        String withoutExt = Files.getNameWithoutExtension(rawName);
        String ext = Files.getFileExtension(rawName);
        return Joiner.on(".").join(withoutExt, Long.toString(System.currentTimeMillis()), ext);
    }

    public void store(String dir, InputStream fileStream, String fileName) {
        try {
            fileName = new String(fileName.getBytes(Constant.UTF_8), Constant.ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder.encode(rawName,utf-8) error: ", e);
        }
        FtpUtils.store(fileStoreConfig, dir, fileStream, fileName);
    }

    public void fetch(String dir, String fileName, OutputStream output) {
        if (StringUtils.isBlank(dir)) {
            dir = fileStoreConfig.getDefaultDir();
        }
        try {
            fileName = new String(fileName.getBytes(Constant.UTF_8), Constant.ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder.encode(rawName,utf-8) error: ", e);
        }
        String path = PathUtils.join(dir, fileName);
        FtpUtils.fetch(fileStoreConfig, path, output);
    }

    public FtpConfig getFileStoreConfig() {
        return fileStoreConfig;
    }

    public void setFileStoreConfig(FtpConfig fileStoreConfig) {
        this.fileStoreConfig = fileStoreConfig;
    }
}
