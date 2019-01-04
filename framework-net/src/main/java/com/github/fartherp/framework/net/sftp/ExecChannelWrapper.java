/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.net.sftp;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/17
 */
public class ExecChannelWrapper extends ChannelWrapper<ChannelExec> {

    public Map<String, Object> execute(String command) throws JSchException {
        Map<String, Object> result = new HashMap<>();
        List<String> stdout = new ArrayList<>();
        List<String> stderror = new ArrayList<>();
        int returnCode = 0;
        ChannelExec channelExec = this.getChannel();
        channelExec.setCommand(command);
        channelExec.setInputStream(null);
        try (BufferedReader error = new BufferedReader(new InputStreamReader(channelExec.getErrStream()));
             BufferedReader input = new BufferedReader(new InputStreamReader(channelExec.getInputStream()))) {
            channelExec.connect();
            //接收远程服务器执行命令的结果
            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }

            String errorLine;
            while ((errorLine = error.readLine()) != null) {
                stderror.add(errorLine);
            }

            if (channelExec.isClosed()) {
                returnCode = channelExec.getExitStatus();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("returnCode", returnCode);
        result.put("stdout", stdout);
        result.put("stderror", stderror);
        return result;
    }
}
