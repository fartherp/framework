/*
 * Copyright (c) 2018. CK. All rights reserved.
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
 * @author: CK
 * @date: 2018/4/17
 */
public class ChannelWrapperFactory {

    public static final Logger LOGGER = LoggerFactory.getLogger(FtpUtils.class);

    @SuppressWarnings("all")
    public static <T extends ChannelWrapper> T create(String host, String user, String password, int port, ChannelType channelType)
            throws JSchException {
        LOGGER.info("prepare host:{}, user:{}, password:{}, port:{}", host, user, password, port);

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect();
        LOGGER.info("host:{} connect success", host);

        ChannelWrapper channelWrapper;
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
