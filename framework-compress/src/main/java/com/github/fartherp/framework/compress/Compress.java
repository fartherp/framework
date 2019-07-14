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
package com.github.fartherp.framework.compress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/4/14
 */
public interface Compress {
    String NULL_STR = "";
    /**
     * dot
     */
	String DOT = ".";
    /**
     * ZIP
     */
	String ZIP = "ZIP";
    /**
     * SUFFIX_ZIP
     */
	String SUFFIX_ZIP = "zip";
    /**
     * GZIP
     */
	String GZIP = "GZIP";
    /**
     * SUFFIX_GZIP
     */
	String SUFFIX_GZIP = "gz";
    /**
     * TAR
     */
	String TAR = "TAR";
    /**
     * SUFFIX_TAR
     */
	String SUFFIX_TAR = "tar";
    /**
     * RAR
     */
	String RAR = "RAR";
    /**
     * BZIP2
     */
	String BZIP2 = "BZIP2";
    /**
     * SUFFIX_BZIP2
     */
	String SUFFIX_BZIP2 = "bz2";
    /**
     * JAR
     */
	String JAR = "JAR";
    /**
     * SUFFIX_ZIP
     */
	String SUFFIX_JAR = "jar";
    /**
     * 缓冲大小
     */
	int BUFFER = 1024;
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
     * @param request 请求
     * @param response 响应
     * @return 压缩类型对象
     */
    CommonCompress response(HttpServletRequest request, HttpServletResponse response);

    /**
     * 设置响应
     * @param request 请求
     * @param response 响应
     * @param fileName 文件名
     * @return 压缩类型对象
     */
    CommonCompress response(HttpServletRequest request, HttpServletResponse response, String fileName);
}
