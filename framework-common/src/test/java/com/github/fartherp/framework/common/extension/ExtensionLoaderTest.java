/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.extension;

import com.github.fartherp.framework.common.extension.ext1.SimpleExt;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/9/25
 */
public class ExtensionLoaderTest {
    @Test
    public void testGetExtensionLoader() throws Exception {
        SimpleExt ext = ExtensionLoader.getExtensionLoader(SimpleExt.class, "generator/").getDefaultExtension();
        String bang = ext.bang(null, 0);
        assertEquals(bang, "bang1");
    }

    @Test
    public void testGetSupportedExtensions() {
        Set<String> strings = ExtensionLoader.getExtensionLoader(SimpleExt.class, "generator/").getSupportedExtensions();
        System.out.println(strings.toString());
    }

}