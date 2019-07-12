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
package com.github.fartherp.framework.security.dissymmetry;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/9/13
 */
public class WeatherSecurityTest {

    @Test
    public void testExecuteGet() {
        try {
            String type = "forecast_f"; // forecast_f index_f
            String appid = "0efe9e3c08151b8d";
            String date = "201503030741";
            String areaid = "101010100";
            //密钥
            String key = "a0f6ac_SmartWeatherAPI_cd7e788";
            //需要加密的数据
            String data = "http://open.weather.com.cn/data/?areaid=" + areaid + "&type=" + type + "&date=" + date + "&appid=";
            String str = WeatherSecurity.standardURLEncoder(data + appid, key);
			assertEquals(str, "ocxOZEXG%2BM9aqzMKw0eZK0mXcaA%3D");
            String result = data + appid.substring(0, 6) + "&key=" + str;
			assertEquals(result, "http://open.weather.com.cn/data/?areaid=101010100&type=forecast_f&date=201503030741&appid=0efe9e&key=ocxOZEXG%2BM9aqzMKw0eZK0mXcaA%3D");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
