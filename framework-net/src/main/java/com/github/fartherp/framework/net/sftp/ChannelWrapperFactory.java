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

import com.github.fartherp.framework.net.ftp.FtpUtils;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/4/17
 */
public class ChannelWrapperFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtils.class);

    @SuppressWarnings("all")
    public static <T extends AbstractChannelWrapper> T create(String host, String user, String password,
			int port, ChannelType channelType) throws JSchException {
        LOGGER.info("prepare host:{}, user:{}, password:{}, port:{}", host, user, password, port);

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect();
        LOGGER.info("host:{} connect success", host);

        AbstractChannelWrapper channelWrapper;
        switch (channelType) {
            case SFTP:
                channelWrapper = new SftpChannelWrapper();
                break;
            case EXEC:
                channelWrapper = new ExecChannelWrapper();
                break;
            case SHELL:
                channelWrapper = new ShellChannelWrapper();
                break;
            case DIRECT_TCP_IP:
                channelWrapper = new DirectTCPIPChannelWrapper();
                break;
            case FORWARDED_TCP_IP:
                channelWrapper = new ForwardedTCPIPChannelWrapper();
                break;
            case SUBSYSTEM:
                channelWrapper = new SubsystemChannelWrapper();
                break;
            case AUTH_AGENT:
            case X11:
            case SESSION:
			default:
				throw new IllegalArgumentException("channel type is not support.");
        }
        LOGGER.info("host:{} create connect channel success", host);
        channelWrapper.setSession(session);
        channelWrapper.openChannel(channelType);
        return (T) channelWrapper;
    }
}
