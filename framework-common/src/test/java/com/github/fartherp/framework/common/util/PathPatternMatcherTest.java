/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PathPatternMatcherTest {

    @Test
    public void testMatch() {
        String s = "/time/;jsessionid=6E697A0D5DDDBC4F7206250E5E594305js/frame/menuModel.js";
		assertTrue(PathPatternMatcher.match("/**/*.js", s));
    }
}
