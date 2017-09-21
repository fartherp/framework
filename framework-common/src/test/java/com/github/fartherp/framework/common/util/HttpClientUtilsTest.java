/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilsTest {

    @Test
    public void testExecute() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ak", "lTz3Ctx4eC9QAXl7ZjoyzyWb");
        params.put("location", "30.548397,104.04701");
        params.put("output", "json");
        params.put("pois", 0);
        String message = HttpClientUtils.execute(params, "http://api.map.baidu.com/geocoder/v2/");
        System.out.println(message);
    }
}