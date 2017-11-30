/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.common.util.ISOUtil;
import com.github.fartherp.framework.security.ISecurity;
import org.testng.Assert;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class DESCBCTest {
    public static void main(String[] args) {
        String keyS = "9BED98891580C3B2";
        byte[] key = ISOUtil.hex2byte(keyS);
        String dataS = "F4F3E7B3566F6622098750B491EA8D5C61";
        byte[] data = ISOUtil.hex2byte(dataS);
        byte[] encryption = DESCBC.encrypt(data, key, ISecurity.DEFAULT_IV);
        Assert.assertEquals("5416B1A5537436105B6E2CAC0F87986F2968AC2DBFB464D0", ISOUtil.hexString(encryption));
        Assert.assertEquals(dataS, ISOUtil.hexString(DESCBC.decrypt(encryption, key, ISecurity.DEFAULT_IV)));
    }
}
