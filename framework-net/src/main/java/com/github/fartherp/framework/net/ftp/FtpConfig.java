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
package com.github.fartherp.framework.net.ftp;

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

    public String toString() {
        final StringBuilder sb = new StringBuilder("FtpConfig{");
        sb.append("host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", keepAliveTimeout=").append(keepAliveTimeout);
        sb.append(", binaryMode=").append(binaryMode);
        sb.append(", localPassiveMode=").append(localPassiveMode);
        sb.append(", defaultDir='").append(defaultDir).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
