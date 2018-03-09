/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.ftp;

/**
 * FTP配置
 * Author: CK
 * Date: 2015/6/30.
 */
public class FtpConfig {

    /**
     * ftp服务器
     */
    private String host;

    /**
     * ftp控制端口
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * Keep-Alive Reply 等待超时时间.
     */
    private int keepAliveTimeout = 0;

    /**
     * 为true则使用binary transfer mode, 否则使用ascii mode
     */
    private boolean binaryMode = false;

    /**
     * 为true则local mode使用passive mode, 为false使用active mode.
     */
    private boolean localPassiveMode = true;

    /**
     * 默认ftp路径.
     */
    private String defaultDir = "/";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public void setKeepAliveTimeout(int keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public boolean isBinaryMode() {
        return binaryMode;
    }

    public void setBinaryMode(boolean binaryMode) {
        this.binaryMode = binaryMode;
    }

    public boolean isLocalPassiveMode() {
        return localPassiveMode;
    }

    public void setLocalPassiveMode(boolean localPassiveMode) {
        this.localPassiveMode = localPassiveMode;
    }

    public String getDefaultDir() {
        return defaultDir;
    }

    public void setDefaultDir(String defaultDir) {
        this.defaultDir = defaultDir;
    }
}
