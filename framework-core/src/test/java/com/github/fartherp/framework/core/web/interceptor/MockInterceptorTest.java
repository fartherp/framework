/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.interceptor;

import com.github.fartherp.framework.core.mock.action.UserAction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/4/4
 */
@ContextConfiguration(locations = { "classpath:/applicationContext-mock.xml" }, inheritLocations = false)
public class MockInterceptorTest extends AbstractTestNGSpringContextTests {

    @Test
    public void testSendHtmlMail() {
        applicationContext.getBean("userAction", UserAction.class).getUser();
    }

}