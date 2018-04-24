/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.net.sftp;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/17
 */
public class SftpChannelWrapperTest {
//    @Test
    public void testMkdir() {
        SftpChannelWrapper channelWrapper = null;
        try {
            channelWrapper = ChannelWrapperFactory.create("192.168.9.141", "testdsf", "testdsf",
                    22, ChannelType.SFTP);
            channelWrapper.mkdir("/opt/work/HelloWorld1/Log1/Log1_ContextTest1");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            if (channelWrapper != null) {
                channelWrapper.disconnect();
            }
        }
    }

}