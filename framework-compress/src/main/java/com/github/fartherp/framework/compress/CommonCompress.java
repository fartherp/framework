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

import com.github.fartherp.framework.common.util.FileUtilies;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/4/14
 */
public abstract class CommonCompress implements Compress {

    /**
     * 标识 0:文件夹，1:文件，2:HttpServletResponse
     */
    protected int flag;
    /**
     * 实现接口
     */
    protected CommonCompress commonCompress;
    /**
     * the compress type
     */
    protected String type;
    /**
     * the compress type suffix
     */
    protected String suffix;
    /**
     * the source file
     */
    protected String source;
    /**
     * the target file
     */
    protected String target;
    /**
     * the source file
     */
    protected File sourceFile;
    /**
     * the target file
     */
    protected File targetFile;
    /**
     * the HttpServletResponse
     */
    protected HttpServletResponse httpServletResponse;

    /**
     * 公共压缩调用
     * @return 输出路径字符串
     */
	@Override
    public void compress() {
        if (2 != this.flag) {
            validation(false);
        }
        setFlag(flag);
        commonCompress.compress();
    }

    /**
     * 解压
     * @return 输出路径字符串
     */
	@Override
    public String unCompress() {
        validation(true);
        return commonCompress.unCompress();
    }

    /**
     * 验证
     * @param flag false:压缩 true:解压缩
     */
    private void validation(boolean flag) {
        // 获取源文件路径
        String sourcePath = getSource();
        String targetPath = getTarget();
        if (StringUtils.isBlank(sourcePath)) {
            throw new RuntimeException("source file name[" + sourcePath + "] no exist");
        }
        String suffix = getSuffix();
        if (!flag && targetPath != null && !targetPath.endsWith(suffix)) {
            // 判断解析类型
            throw new RuntimeException("current class compress type [" + suffix + "] source file suffix error");
        }
        File source = getSourceFile();
        if (!source.exists()) {
            throw new RuntimeException("source file name[" + sourcePath + "] no exist");
        }
        dealTarget(flag);
        if (flag) {
            if (SUFFIX_BZIP2.equals(suffix) && (StringUtils.isBlank(targetPath)
                    || targetPath.startsWith(source.getParent()) || source.getParent().startsWith(targetPath))) {
                throw new RuntimeException(SUFFIX_BZIP2 + "后缀必须给出输出目录或目标目录与源文件目录不能在一个父目录上");
            }
        }
    }

    private void dealTarget(boolean flag) {
        File source = getSourceFile();
        if (StringUtils.isBlank(getTarget())) {
            // 目标路径不存在,(压缩/解压)设置目标目录为父目录
            this.target = source.getParent();
            File target = null;
            if (flag) {
                // 解压
                target = source.getParentFile();
            } else {
                // 压缩,文件夹,名称同文件夹名文件:文件,名称同文件名
                String fileName = source.isDirectory() ? source.getName()
					: StringUtils.substringBeforeLast(source.getName(), DOT);
                target = new File(source.getParentFile(), fileName + DOT + getSuffix());
            }
            setTargetFile(target);
        } else {
            // 解压时,目标路径必须是文件夹,压缩时,存在文件不覆盖报错
            File target = getTargetFile();
            if (flag && target.getName().endsWith(getSuffix())) {
                throw new RuntimeException(" target must is dir not file ");
            }
            if (target.getName().endsWith(getSuffix())) {
                // 指定文件名
                if (target.exists()) {
                    throw new RuntimeException("target file exist");
                } else {
                    // 目标不存在,创建
                    this.target = target.getAbsolutePath();
                    setTargetFile(target);
                }
            } else {
                // 指定目录
                if (!target.exists()) {
                    // 目标不存在,创建
                    File file = new File(target, File.separator);
                    file.mkdirs();
                    this.target = target.getAbsolutePath();
                    setTargetFile(target);
                }
            }
        }
    }

    /**
     * 设置源文件名称
     * @param source 源名称
     */
	@Override
    public CommonCompress source(String source) {
        setSource(source);
        setSourceFile(new File(source));
        return this;
    }

    /**
     * 设置目标文件名称
     * @param target 目标名称
     */
	@Override
    public CommonCompress target(String target) {
        this.target = target;
        setTargetFile(new File(target));
        return this;
    }

    /**
     * 设置响应
     * @param request 请求
     * @param response 响应
     */
	@Override
    public CommonCompress response(HttpServletRequest request, HttpServletResponse response) {
        this.response(request, response, System.currentTimeMillis() + "");
        return this;
    }

	@Override
    public CommonCompress response(HttpServletRequest request, HttpServletResponse response, String fileName) {
        this.flag = 2;
        response.reset();
        response.setContentType("application/octet-stream;charset=GBK");
        String filename = FileUtilies.getFileName(fileName, request);
        response.setHeader("content-disposition", "attachment; filename=" + filename + DOT + getSuffix());
        setHttpServletResponse(response);
        return this;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public File getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
}
