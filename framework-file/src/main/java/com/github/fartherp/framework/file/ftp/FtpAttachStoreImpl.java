/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.file.ftp;

import com.github.fartherp.framework.file.FileStore;
import com.github.fartherp.framework.net.ftp.FtpConfig;
import com.github.fartherp.framework.net.ftp.FtpUtils;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/3/7
 */
public class FtpAttachStoreImpl implements FileStore<FtpConfig> {
    private static final Logger log = LoggerFactory.getLogger(FtpAttachStoreImpl.class);
    /**
     * Ftp配置
     */
    private FtpConfig config;

    public String partitionDir(Object mountDir) {
        return config.getDefaultDir() + File.separator + mountDir;
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
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        FtpUtils.store(config, dir, fileStream, fileName);
    }

    public void fetch(String dir, String fileName, OutputStream output) {
        if (StringUtils.isBlank(dir)) {
            dir = config.getDefaultDir();
        }
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        String path = new File(dir, fileName).getPath();
        FtpUtils.fetch(config, path, output);
    }

    public FtpConfig getStoreConfig() {
        return config;
    }

    public void setStoreConfig(FtpConfig config) {
        this.config = config;
    }
}
