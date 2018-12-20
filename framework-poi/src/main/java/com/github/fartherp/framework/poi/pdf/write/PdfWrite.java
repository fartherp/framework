/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.pdf.write;

import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
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
