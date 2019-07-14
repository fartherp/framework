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
package com.github.fartherp.framework.poi.pdf.write;

import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/19
 */
public interface PdfWrite {
    /**
     * 文件名
     * @return 文件名
     */
    String getFileName();

    /**
     * 输出流
     * @return 输出流
     */
    OutputStream getOutputStream();

    /**
     * 创建输出流
     * @return PdfWrite
     */
    PdfWrite createOutputStream();

    /**
     * 写数据
     */
    void write();

    /**
     * 具体操作
     * @param deal 操作回调
     * @return PdfWrite
     */
    PdfWrite deal(PdfWriteDeal<?> deal);

    /**
     * 添加字体路径
     * @param path 路径
     * @return PdfWrite
     */
    PdfWrite addFontPath(String path);
}
