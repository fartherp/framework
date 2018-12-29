/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.file.nfs;

import com.github.fartherp.framework.common.util.PathUtil;
import com.github.fartherp.framework.file.FileStore;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/17
 */
public class NfsFileStoreImpl implements FileStore<NfsConfig> {
    private static final Logger log = LoggerFactory.getLogger(NfsFileStoreImpl.class);

    private NfsConfig config;

    public String partitionDir(Object mountDir) {
        return config.getDefaultDir() + File.separator + mountDir;
    }

    public String generateFilename(String rawName) {
        if (StringUtils.isBlank(rawName)) {
            throw new RuntimeException("Raw file name is blank");
        }
        String withoutExt = Files.getNameWithoutExtension(rawName);
        String ext = Files.getFileExtension(rawName);
        return Joiner.on(".").join(withoutExt, System.currentTimeMillis() + "" + Thread.currentThread().getId(), ext);
    }

    public void store(String dir, InputStream fileStream, String fileName) {
        File directory = new File(dir);
        if (!directory.exists()) {
            log.error("nfsAttachStore.store[dir={},fileName={}]:not exists.", dir, fileName);
            throw new RuntimeException("Directory not exists." + dir);
        }
        if (!directory.isDirectory()) {
            log.error("nfsAttachStore.store[dir={},fileName={}]:not directory.", dir, fileName);
            throw new RuntimeException("Path is not directory." + dir);
        }

        String path = PathUtil.join(dir, fileName);
        File file = new File(path);
        if (file.exists()) {
            // 基本不可能出现, 除非时间戳重叠.
            log.error("nfsAttachStore.store[dir={},fileName={},filePath={}]:exists.",
                    dir, fileName, file.getAbsolutePath());
            throw new RuntimeException("File is exists." + file.getAbsolutePath());
        }

        OutputStream out = null;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            IOUtils.copy(fileStream, out);
            out.flush();
        } catch (IOException e) {
            log.error("nfsAttachStore.store[dir={},fileName={},filePath={}]:{}",
                    dir, fileName, file.getAbsolutePath(), e);
            throw new RuntimeException("IOExcepiton." + e.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public void fetch(String dir, String fileName, OutputStream output) {
        File f = new File(PathUtil.join(dir, fileName));
        if (!f.exists()) {
            log.error("nfsAttachStore.fetch[dir={},fileName={}]:not exists.", dir, fileName);
            throw new RuntimeException("file not exists." + f.getAbsolutePath());
        }

        InputStream in = null;
        try {
            in = new FileInputStream(f);
            IOUtils.copy(in, output);
        } catch (FileNotFoundException e) {
            log.error("nfsAttachStore.fetch[dir={},fileName={}]:{}", dir, fileName, e);
            throw new RuntimeException("file not found." + f.getAbsolutePath());
        } catch (IOException e) {
            log.error("nfsAttachStore.fetch[dir={},fileName={}]:{}", dir, fileName, e);
            throw new RuntimeException("IOException." + f.getAbsolutePath());
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public NfsConfig getStoreConfig() {
        return config;
    }

    public void setStoreConfig(NfsConfig config) {
        this.config = config;
    }
}
