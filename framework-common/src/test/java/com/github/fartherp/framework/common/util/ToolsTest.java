/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

public class ToolsTest {

//    @Test
    public void testExecuteShell() throws Exception {
        Tools.executeShell("notepad");
    }

//    @Test
    public void testExecuteShell1() throws Exception {
        Tools.executeShell("notepad", "help");
    }
}