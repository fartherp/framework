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
package com.github.fartherp.framework.security.dissymmetry;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class DSATest {

	@Test
    public void testSignAndVerify() {
        String inputStr = "abc";

        // 构建密钥
        DisSymmetryKey disSymmetryKey = DSA.initKey();

        // 获得密钥
        byte[] publicKey = disSymmetryKey.getPublicKey();
        byte[] privateKey = disSymmetryKey.getPrivateKey();

        // 产生签名
        byte[] sign = DSA.sign(inputStr.getBytes(), privateKey);

        // 验证签名
        boolean status = DSA.verify(inputStr.getBytes(), publicKey, sign);
		assertTrue(status);
    }
}
