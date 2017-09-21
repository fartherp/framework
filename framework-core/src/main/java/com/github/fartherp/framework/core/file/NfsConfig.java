/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.file;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/3/7
 */
public class NfsConfig implements FileStoreConfig {

    private String defaultDir;

    public String getDefaultDir() {
        return defaultDir;
    }

    public void setDefaultDir(String defaultDir) {
        this.defaultDir = defaultDir;
    }
}
