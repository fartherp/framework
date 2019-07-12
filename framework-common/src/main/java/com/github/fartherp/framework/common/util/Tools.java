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
package com.github.fartherp.framework.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 进程执行操作类
 * Author: CK.
 * Date: 2015/3/28.
 */
public class Tools {

    /**
     * Execute shell.
     * @param shellCommand the shell command
     */
    public synchronized static void executeShell(String shellCommand) {
        int success = -1;
        BufferedReader bufferedReader = null;
        try {
            Process pid = Runtime.getRuntime().exec(shellCommand);
            bufferedReader = new BufferedReader(new InputStreamReader(pid.getErrorStream()), 1024);
            pid.waitFor();
            success = pid.exitValue();
            if (0 != success) {
                String line = null;
                StringBuilder errorMsg = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    errorMsg.append(line);
                }
                throw new Exception("执行" + shellCommand + "命令异常，异常信息" + errorMsg.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(bufferedReader);
        }
    }

    /**
     * Execute shell.
     *
     * @param shellCommand the shell command
     */
    public synchronized static void executeShell(String ... shellCommand) {
        int success = -1;
        BufferedReader bufferedReader = null;
        try {
            Process pid = Runtime.getRuntime().exec(shellCommand);
            bufferedReader = new BufferedReader(new InputStreamReader(pid.getErrorStream()), 1024);
            pid.waitFor();
            success = pid.exitValue();
            if (0 != success) {
                StringBuilder errorMsg = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    errorMsg.append(line);
                }
                throw new Exception("执行" + shellCommand + "命令异常，异常信息" + errorMsg.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(bufferedReader);
        }
    }

    /**
     * Close bufferReader.
     * @param bufferedReader the bufferedReader
     */
    private static void close(BufferedReader bufferedReader) {
        try {
            if (null != bufferedReader) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            // ignore
            bufferedReader = null;
        }
    }
}
