/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http client tools
 * Auth: CK
 * Date: 2016/4/28
 */
public class HttpClientUtil {

    /**
     * send http post
     * @param params the params
     * @param url the url
     * @return response
     */
    public static String execute(Map<String, Object> params, String url) {
        try {
            List<NameValuePair> form = new ArrayList<>();
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() != null) {
                        form.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                }
            }
            HttpEntity reqEntity = new UrlEncodedFormEntity(form, "utf-8");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();
            HttpPost post = new HttpPost(url);
            post.setEntity(reqEntity);
            post.setConfig(requestConfig);
            return HttpClientBuilder.create().build().execute(post, response -> {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * send http get
     * @param url the url and the params
     * @return the response
     */
    public static String executeGet(String url) {
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();
            HttpGet post = new HttpGet(url);
            post.setConfig(requestConfig);
            return HttpClientBuilder.create().build().execute(post, response -> {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
