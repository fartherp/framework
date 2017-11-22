/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.common.util.ISOUtil;
import com.github.fartherp.framework.security.ISecurity;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class DESTest {
    @Test
    public void testDES() {
        ISecurity aes = new DES(ISOUtil.hex2byte("1111111111111111"));
        String data = "2222222222222222";
        byte[] encryption = aes.encrypt(ISOUtil.hex2byte("2222222222222222"));
        Assert.assertEquals("950973182317F80BB95374BA8DDFF8C2", ISOUtil.hexString(encryption));
        Assert.assertEquals(data, ISOUtil.hexString(aes.decrypt(encryption)));
    }
}
