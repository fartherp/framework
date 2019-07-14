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

import com.github.fartherp.framework.common.util.ISOUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/1/29
 */
public class RSACTest {
    static String ns = "CCE61184008CA71FBB9934C5264A4AE4C85EAECACD8A2C2454F93E965C90AE8FA919FEAAE926A5D9F508BD625946F4F5E86DBA931629442D3A90B8F96CFB526F";
    static String es = "010001";
    static String ds = "536EC3671ACA2B2B32BA79D0657D0EEE05E0F80FC08E28D74A7A8B4FB2B57F3E695B841D0AD0B86DE1A938CEB903B283D5FB78FD733CE832D3F0D2642F285C21";

    @Test
    public void testEncryptByPublicKey() {
        String data = "abc";
        byte[] result = RSAC.encryptByPublicKey(data.getBytes(), ns, es);
        assertEquals(ISOUtil.hexString(result), "AD04F695A18D6C400F301C3704EA472F6AB875967B66A6F196558E163173F783C1BD8CADD277E518603C2BD819DCB3B8364C9B2E2A89B769A32A678EAD345A1F");
    }

    @Test
    public void testDecryptByPrivateKey() {
        String data = "AD04F695A18D6C400F301C3704EA472F6AB875967B66A6F196558E163173F783C1BD8CADD277E518603C2BD819DCB3B8364C9B2E2A89B769A32A678EAD345A1F";
        byte[] result = RSAC.decryptByPrivateKey(ISOUtil.hex2byte(data), ns, ds);
        assertEquals(new String(result).trim(), "abc");
    }

}
