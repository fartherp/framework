/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.common.util.ISOUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        Assert.assertEquals("FE7B6C8A73167964798EBAC2BA4899AA", ISOUtil.hexString(encryption));

        // decrypt
        Assert.assertEquals(dataS, ISOUtil.hexString(ThreeDES.decrypt(encryption, key)));
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
        Assert.assertEquals("738533847602379CDC0F3B7EF880C356", ISOUtil.hexString(encryption));

        // decrypt
        Assert.assertEquals(dataS, ISOUtil.hexString(ThreeDES.decrypt(encryption, key)));
    }
}
