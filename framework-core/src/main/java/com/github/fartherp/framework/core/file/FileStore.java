/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.file;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/17
 */
public interface FileStore<T> {
    /**
     * 根据环境参数,切分目录,返回目录路径.
     *
     * 路径内容如:
     *  /foo/bar/
     *
     * @param mountDir 二级目录
     * @return 路径
     */
    String partitionDir(Object mountDir);
    /**
     * 自动生成文件名
     * @param rawName 文件名
     * @return new文件名
     */
    String generateFilename(String rawName);
    /**
     * 存储文件.
     *
     * @param dir 目录
     * @param fileStream 文件流,非null
     * @param fileName 文件名
     */
    void store(String dir, InputStream fileStream, String fileName);
    /**
     * 获取文件.
     *
     * @param dir 目录
     * @param fileName 文件名
     * @param output 写入流,非null
     */
    void fetch(String dir, String fileName, OutputStream output);
    /**
     * 附件配置
     * @return 附件配置
     */
    T getStoreConfig();

    void setStoreConfig(T config);
}
