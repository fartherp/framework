/*
 * Copyright (c) 2017. CK. All rights reserved.
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
