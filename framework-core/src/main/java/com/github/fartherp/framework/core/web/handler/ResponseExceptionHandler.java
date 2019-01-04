/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.handler;

import com.github.fartherp.framework.core.util.JsonResp;
import com.github.fartherp.framework.exception.web.ResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 固定JSON返回格式
 * Auth: CK
 * Date: 2016/9/9
 */
public class ResponseExceptionHandler implements CustomizeExceptionHandler {

    public boolean support(Exception ex, Class clazz) {
        return ex instanceof ResponseException
                || void.class.isAssignableFrom(clazz)
                || String.class.isAssignableFrom(clazz);
    }

    public ModelAndView deal(HttpServletRequest request, HttpServletResponse response,
                     Object handler, Exception ex, CustomizeExceptionHandlerResolver resolver) {
        // 把漏网的异常信息记入日志
        try {
            String message = ex.getMessage();
            if (message == null) {
                Throwable e = ex.getCause();
                if (e == null) {
                    message = resolver.getMessage(ex);
                } else {
                    message = resolver.getMessage(e);
                }
            }
            HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
            HttpHeaders headers = outputMessage.getHeaders();
            MediaType contentType = headers.getContentType();
            // 默认UTF-8
            Charset charset = StandardCharsets.UTF_8;
            // 设置内容类型
            if (contentType == null) {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }
            // 设置内容长度
            String json = JsonResp.asEmpty().error(message).toJson();
            if (headers.getContentLength() == -1) {
                long contentLength = json.getBytes(charset.name()).length;
                if (contentLength > 0) {
                    headers.setContentLength(contentLength);
                }
            }
            // 设置接收编码类型
            headers.setAcceptCharset(new ArrayList<>(Charset.availableCharsets().values()));
            // 将错误信息传递给前端
            StreamUtils.copy(json, charset, outputMessage.getBody());
            outputMessage.getBody().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
