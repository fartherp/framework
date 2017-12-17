/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/10
 */
public class ServiceLocatorTest {
    @Test
    public void testGetFactory() throws Exception {
        try {
            ServiceLocator.getBean("test");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "没有注入spring factory");
        }
    }

}