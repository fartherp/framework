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

import com.github.fartherp.framework.common.util.JsonUtil;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/4/17
 */
public class ExecChannelWrapperTest {
//    @Test
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
