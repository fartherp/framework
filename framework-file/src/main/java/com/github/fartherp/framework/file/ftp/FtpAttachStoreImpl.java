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
 * @author CK
 * @date 2016/3/7
 */
public class FtpAttachStoreImpl implements FileStore<FtpConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpAttachStoreImpl.class);
    /**
     * Ftp配置
     */
    private FtpConfig config;

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
        return Joiner.on(".").join(withoutExt, Long.toString(System.currentTimeMillis()), ext);
    }

    @Override
	public void store(String dir, InputStream fileStream, String fileName) {
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        FtpUtils.store(config, dir, fileStream, fileName);
    }

    @Override
	public void fetch(String dir, String fileName, OutputStream output) {
        if (StringUtils.isBlank(dir)) {
            dir = config.getDefaultDir();
        }
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        String path = new File(dir, fileName).getPath();
        FtpUtils.fetch(config, path, output);
    }

    @Override
	public FtpConfig getStoreConfig() {
        return config;
    }

    @Override
	public void setStoreConfig(FtpConfig config) {
        this.config = config;
    }
}
