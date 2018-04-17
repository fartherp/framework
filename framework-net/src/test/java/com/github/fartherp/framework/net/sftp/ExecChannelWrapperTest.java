/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.net.sftp;

import com.github.fartherp.framework.common.util.JsonUtil;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/17
 */
public class ExecChannelWrapperTest {
    @Test
    public void testExecute() throws Exception {
        ExecChannelWrapper channelWrapper = null;
        try {
            channelWrapper = ChannelWrapperFactory.create("192.168.9.31", "root", "Juzhen123!",
                    22, ChannelType.EXEC);
            Map<String, Object> map = channelWrapper.execute("icegridnode -v");
            System.out.println(JsonUtil.toJson(map));
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            if (channelWrapper != null) {
                channelWrapper.disconnect();
            }
        }
    }

}