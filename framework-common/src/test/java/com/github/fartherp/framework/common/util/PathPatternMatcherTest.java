/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

public class PathPatternMatcherTest {

    @Test
    public void testMatch() throws Exception {
//        System.out.println("-- " + match("/**/*.jsp", "/e.jsp"));
        String s = "/time/;jsessionid=6E697A0D5DDDBC4F7206250E5E594305js/frame/menuModel.js";
        System.out.println(PathPatternMatcher.match("/**/*.js", s));
    }
}