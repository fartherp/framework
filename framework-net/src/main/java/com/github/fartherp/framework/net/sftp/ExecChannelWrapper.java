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
 * @author CK
 * @date 2018/4/17
 */
public class ExecChannelWrapper extends AbstractChannelWrapper<ChannelExec> {

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
