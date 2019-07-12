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

import com.github.fartherp.framework.common.util.ISOUtil;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class ThreeDESTest {
    /**
     * 双倍长
     */
    @Test
    public void testThreeDESOfTwo() {
        String keyS = "9BED98891580C3B245FE9EC58BFA8D2A";
        byte[] key = ISOUtil.hex2byte(keyS);
        String dataS = "F4F3E7B3566F6622098750B491EA8D5C";
        // encrypt
        byte[] encryption = ThreeDES.encrypt(ISOUtil.hex2byte(dataS), key);
        assertEquals(ISOUtil.hexString(encryption), "FE7B6C8A73167964798EBAC2BA4899AA");

        // decrypt
        assertEquals(ISOUtil.hexString(ThreeDES.decrypt(encryption, key)), dataS);
    }

    /**
     * 三倍长
     */
    @Test
    public void testThreeDESOfThree() {
        String keyS = "9BED98891580C3B245FE9EC58BFA8D2A2DC5A7FEAB967E40";
        byte[] key = ISOUtil.hex2byte(keyS);
        String dataS = "F4F3E7B3566F6622098750B491EA8D5C";
        // encrypt
        byte[] encryption = ThreeDES.encrypt(ISOUtil.hex2byte(dataS), key);
        assertEquals(ISOUtil.hexString(encryption), "738533847602379CDC0F3B7EF880C356");

        // decrypt
        assertEquals(ISOUtil.hexString(ThreeDES.decrypt(encryption, key)), dataS);
    }
}
