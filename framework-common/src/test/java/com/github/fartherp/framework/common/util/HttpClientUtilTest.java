/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilTest {

//    @Test
    public void testExecute() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ak", "lTz3Ctx4eC9QAXl7ZjoyzyWb");
        params.put("location", "30.548397,104.04701");
        params.put("output", "json");
        params.put("pois", 0);
        String message = HttpClientUtil.execute(params, "http://api.map.baidu.com/geocoder/v2/");
        System.out.println(message);
    }
}
