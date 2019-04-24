/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import java.io.OutputStream;

/**
 * 输出流创建代理
 * Author: CK
 * Date: 2019/4/24
 */
public interface OutputStreamDelegate {

    OutputStream createOutputStream(String fileName);
}
