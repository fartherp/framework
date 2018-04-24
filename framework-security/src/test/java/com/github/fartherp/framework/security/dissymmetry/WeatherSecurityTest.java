/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import com.github.fartherp.framework.common.util.HttpClientUtils;
import org.testng.annotations.Test;

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
            String result = data + appid.substring(0, 6);
            String json = HttpClientUtils.executeGet(result + "&key=" + str);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}