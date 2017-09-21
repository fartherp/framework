/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import com.github.fartherp.framework.common.constant.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.velocity.VelocityEngineFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * 需要velocity.properties
 * Author: CK
 * Date: 2015/8/3
 */
public class VelocityUtils {

    /**
     * 计算工具
     */
    private MathTool mathTool = new MathTool();
    /**
     * 日期工具
     */
    private DateTool dateTool = new DateTool();
    /**
     * 数字工具
     */
    private NumberTool numberTool = new NumberTool();
    /**
     * 编码工具
     */
    private EscapeTool escapeTool = new EscapeTool();

    private VelocityEngine velocityEngine;

    private VelocityUtils(String resourceLoaderPath, String configLocation) {
        VelocityEngineFactory velocityEngineFactory = new VelocityEngineFactory();
        Resource resource = null;
        if (configLocation.startsWith("/WEB-INF")) {
            // web配置加载
            configLocation = configLocation.substring(8);
            resource = new FileSystemResource(VelocityUtils.class.getClassLoader().getResource(Constant.EMPTY)
                    .getPath() + ".." + configLocation);
        } else {
            // classPath加载
            resource = new DefaultResourceLoader(VelocityUtils.class.getClassLoader()).getResource(configLocation);
        }
        velocityEngineFactory.setConfigLocation(resource);
        velocityEngineFactory.setResourceLoaderPath(resourceLoaderPath);
        try {
            velocityEngine = velocityEngineFactory.createVelocityEngine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static VelocityUtils getInstance() {
        return new VelocityUtils("classpath:/", "classpath:velocity.properties");
    }

    public String parse(String vmFile, VelocityContext context) {
        if (StringUtils.isBlank(vmFile) || null == context) {
            return null;
        }
        setTool(context);
        Template template = velocityEngine.getTemplate(vmFile, Constant.UTF_8);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw.toString();
    }

    public String parse(String vmFile, Map<String, Object> context) {
        if (null == context) {
            return null;
        }
        return parse(vmFile, new VelocityContext(context));
    }

    public String parseFromString(String templateStr, VelocityContext context) {
        if (StringUtils.isBlank(templateStr) || null == context) {
            return null;
        }
        setTool(context);
        StringWriter sw = new StringWriter();
        velocityEngine.evaluate(context, sw, Constant.EMPTY, templateStr);
        return sw.toString();
    }

    private void setTool(VelocityContext context) {
        context.put("date", dateTool);
        context.put("math", mathTool);
        context.put("number", numberTool);
        context.put("escape", escapeTool);
    }
}
