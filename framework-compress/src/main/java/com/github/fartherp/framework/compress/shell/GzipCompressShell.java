/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.compress.shell;

import com.github.fartherp.framework.common.util.Tools;

/**
 * <p>gzip压缩解压 在aix5测试通过</p>
 *
 * @author cyq
 */
public class GzipCompressShell extends CompressShell {
    /**
     * @param sourceFile 源文件
     */
    public void gzip(String sourceFile) {
        try {
            Tools.executeShell("gzip -f " + sourceFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sourceFile 源文件 包括路径
     */
    public void unGzip(String sourceFile) {
        try {
            Tools.executeShell("gzip -d -f " + sourceFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void compress(String source) {
        validation(source);
        gzip(source);
    }

    public void unCompress(String source) {
        validation(source);
        unGzip(source);
    }
}
