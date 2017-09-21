/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/14
 */
public interface Compress {
    public static final String NULL_STR = "";
    /**
     * dot
     */
    public static final String DOT = ".";
    /**
     * ZIP
     */
    public static final String ZIP = "ZIP";
    /**
     * SUFFIX_ZIP
     */
    public static final String SUFFIX_ZIP = "zip";
    /**
     * GZIP
     */
    public static final String GZIP = "GZIP";
    /**
     * SUFFIX_GZIP
     */
    public static final String SUFFIX_GZIP = "gz";
    /**
     * TAR
     */
    public static final String TAR = "TAR";
    /**
     * SUFFIX_TAR
     */
    public static final String SUFFIX_TAR = "tar";
    /**
     * RAR
     */
    public static final String RAR = "RAR";
    /**
     * BZIP2
     */
    public static final String BZIP2 = "BZIP2";
    /**
     * SUFFIX_BZIP2
     */
    public static final String SUFFIX_BZIP2 = "bz2";
    /**
     * JAR
     */
    public static final String JAR = "JAR";
    /**
     * SUFFIX_ZIP
     */
    public static final String SUFFIX_JAR = "jar";
    /**
     * 缓冲大小
     */
    public static final int BUFFER = 1024;
    /**
     * 压缩文件
     * @return 压缩文件名
     */
    void compress();

    /**
     * 解压文件
     * @return 解压文件名
     */
    String unCompress();

    /**
     * 设置源文件信息
     * @param source 源文件
     * @return 压缩类型对象
     */
    CommonCompress source(String source);

    /**
     * 设置目标文件信息
     * @param target 目标目录
     * @return 压缩类型对象
     */
    CommonCompress target(String target);

    /**
     * 设置响应
     * @param response 响应
     * @return 压缩类型对象
     */
    CommonCompress response(HttpServletResponse response);
}
