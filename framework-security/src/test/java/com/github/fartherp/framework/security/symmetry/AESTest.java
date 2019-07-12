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
package com.github.fartherp.framework.security.symmetry;

import org.testng.annotations.Test;

import java.util.Base64;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class AESTest {
    @Test
    public void encrypt() {
        String data = "root1";
        byte[] key = "1111111111111111".getBytes();
        byte[] encryption = AES.encrypt(data.getBytes(), key);
        assertEquals(Base64.getEncoder().encodeToString(encryption), "8/mudtZ/bQOhcV/K6JFrug==");
        String decryptData = new String(AES.decrypt(encryption, key));
        assertEquals(decryptData, data);
    }
}
