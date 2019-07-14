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
package com.github.fartherp.framework.net.sftp;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/4/3
 */
public enum ChannelType {
	/**
	 * 通道类型
	 */
    SESSION("session"),
    SHELL("shell"),
    EXEC("exec"),
    X11("x11"),
    AUTH_AGENT("auth-;agent@openssh.com"),
    DIRECT_TCP_IP("direct-tcpip"),
    FORWARDED_TCP_IP("forwarded-tcpip"),
    SFTP("sftp"),
    SUBSYSTEM("subsystem");
    String type;

    ChannelType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
