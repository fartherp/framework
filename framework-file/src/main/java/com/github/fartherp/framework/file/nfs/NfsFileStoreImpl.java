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
package com.github.fartherp.framework.file.nfs;

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
 * @author CK
 * @date 2016/1/17
 */
public class NfsFileStoreImpl implements FileStore<NfsConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NfsFileStoreImpl.class);

    private NfsConfig config;

    @Override
	public String partitionDir(Object mountDir) {
        return config.getDefaultDir() + File.separator + mountDir;
    }

    @Override
	public String generateFilename(String rawName) {
        if (StringUtils.isBlank(rawName)) {
            throw new RuntimeException("Raw file name is blank");
        }
        String withoutExt = Files.getNameWithoutExtension(rawName);
        String ext = Files.getFileExtension(rawName);
        return Joiner.on(".").join(withoutExt, System.currentTimeMillis() + ""
			+ Thread.currentThread().getId(), ext);
    }

    @Override
	public void store(String dir, InputStream fileStream, String fileName) {
        File directory = new File(dir);
        if (!directory.exists()) {
            LOGGER.error("nfsAttachStore.store[dir={},fileName={}]:not exists.", dir, fileName);
            throw new RuntimeException("Directory not exists." + dir);
        }
        if (!directory.isDirectory()) {
            LOGGER.error("nfsAttachStore.store[dir={},fileName={}]:not directory.", dir, fileName);
            throw new RuntimeException("Path is not directory." + dir);
        }

        String path = new File(dir, fileName).getPath();
        File file = new File(path);
        if (file.exists()) {
            // 基本不可能出现, 除非时间戳重叠.
            LOGGER.error("nfsAttachStore.store[dir={},fileName={},filePath={}]:exists.",
                    dir, fileName, file.getAbsolutePath());
            throw new RuntimeException("File is exists." + file.getAbsolutePath());
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("File create error." + file.getAbsolutePath());
        }

        try (OutputStream out = new FileOutputStream(file)) {
            IOUtils.copy(fileStream, out);
            out.flush();
        } catch (IOException e) {
            LOGGER.error("nfsAttachStore.store[dir={},fileName={},filePath={}]:{}",
                    dir, fileName, file.getAbsolutePath(), e);
            throw new RuntimeException("IOExcepiton." + e.getMessage());
        }
    }

    @Override
	public void fetch(String dir, String fileName, OutputStream output) {
        File f = new File(new File(dir, fileName).getPath());
        if (!f.exists()) {
            LOGGER.error("nfsAttachStore.fetch[dir={},fileName={}]:not exists.", dir, fileName);
            throw new RuntimeException("file not exists." + f.getAbsolutePath());
        }

        try (InputStream in = new FileInputStream(f)) {
            IOUtils.copy(in, output);
        } catch (FileNotFoundException e) {
            LOGGER.error("nfsAttachStore.fetch[dir={},fileName={}]:{}", dir, fileName, e);
            throw new RuntimeException("file not found." + f.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("nfsAttachStore.fetch[dir={},fileName={}]:{}", dir, fileName, e);
            throw new RuntimeException("IOException." + f.getAbsolutePath());
        }
    }

    @Override
	public NfsConfig getStoreConfig() {
        return config;
    }

    @Override
	public void setStoreConfig(NfsConfig config) {
        this.config = config;
    }
}
