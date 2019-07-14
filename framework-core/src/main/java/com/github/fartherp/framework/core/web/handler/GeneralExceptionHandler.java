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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 通用异常处理
 * @author CK
 * @date 2016/9/11
 */
public class GeneralExceptionHandler implements CustomizeExceptionHandler {

    @Override
	public boolean support(Exception ex, Class clazz) {
        return true;
    }

    @Override
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
