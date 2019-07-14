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
 * @author CK
 * @date 2015/4/13
 */
public class DESTest {

    @Test
    public void testEncryptAndDecrypt() {
        byte[] key = "11111111".getBytes();
        String data = "2222222222222222";
        byte[] encryption = DES.encrypt(data.getBytes(), key);
        assertEquals(Base64.getEncoder().encodeToString(encryption), "+ScU6A6DLtz5JxToDoMu3K1qiLT6N4M9");
        assertEquals(new String(DES.decrypt(encryption, key)), data);
    }
}
