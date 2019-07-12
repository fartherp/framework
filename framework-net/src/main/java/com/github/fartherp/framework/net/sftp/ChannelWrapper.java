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

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/17
 */
public abstract class ChannelWrapper<T extends Channel> {

    private Session session;
    private T channel;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public T getChannel() {
        return channel;
    }

    /**
     * 打开通道
     * @param channelType 通道枚举
     * @throws JSchException the JSchException
     */
    @SuppressWarnings("all")
    public void openChannel(ChannelType channelType) throws JSchException {
        this.channel = (T) session.openChannel(channelType.getType());
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        // 关闭通道
        if (this.channel != null) {
            if (this.channel.isConnected()) {
                this.channel.disconnect();
            } else {
                this.channel.isClosed();
            }
        }
        // 关闭session
        if (this.session != null) {
            if (this.session.isConnected()) {
                this.session.disconnect();
            }
        }
    }
}
