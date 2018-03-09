/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.web.handler;

import com.github.fartherp.framework.core.util.JsonResp;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 通用异常处理
 * Auth: CK
 * Date: 2016/9/11
 */
public class GeneralExceptionHandler implements CustomizeExceptionHandler {

    public boolean support(Exception ex, Class clazz) {
        return true;
    }

    public ModelAndView deal(HttpServletRequest request, HttpServletResponse response,
                             Object handler, Exception ex, CustomizeExceptionHandlerResolver resolver) {
        // 把漏网的异常信息记入日志
        try {
            String message = ex.getMessage();
            if (message == null) {
                message = resolver.getMessage(ex);
            }
            HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
            HttpHeaders headers = outputMessage.getHeaders();
            if (headers != null) {
                MediaType contentType = headers.getContentType();
                // 默认UTF-8
                Charset charset = Charset.forName("UTF-8");
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
                headers.setAcceptCharset(new ArrayList<Charset>(Charset.availableCharsets().values()));
                // 将错误信息传递给前端
                StreamUtils.copy(json, charset, outputMessage.getBody());
                outputMessage.getBody().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
