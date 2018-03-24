/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.ftp;

import com.jcraft.jsch.JSchException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/3/22
 */
public class SftpUtilsTest {
    @Test
    public void testGetSftpConnect() throws Exception {
        SftpUtils sftpUtils = new SftpUtils();
        try {
            sftpUtils.getSftpConnect("192.168.9.140", "testdsf", "testdsf111", 22);
        } catch (JSchException e) {
            System.out.println(e.getMessage());
        } finally {
            sftpUtils.disconnect();
        }
    }

    @Test
    public void testMkdir() throws Exception {
        SftpUtils sftpUtils = new SftpUtils();
        try {
            sftpUtils.getSftpConnect("192.168.9.141", "testdsf", "testdsf", 22);
            sftpUtils.mkdir("/opt/work/HelloWorld/Log1/Log1_ContextTest1");
        } catch (JSchException e) {
            System.out.println(e.getMessage());
        } finally {
            sftpUtils.disconnect();
        }
    }
}