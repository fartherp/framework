/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;


import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JEmptyTest {

    @Test
    public void empty() {
        String json = JsonResp.asEmpty().error("Hello World").toJson();
		assertEquals(json, "{\"status\":1,\"statusInfo\":\"Hello World\"}");
    }
}
