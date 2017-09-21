/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import com.github.fartherp.framework.common.constant.Constant;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 * Author: CK
 * Date: 2015/9/29
 */
public class ValidateUtils {
    private static final String DEFAULT_SEPARATOR = Constant.SEMICOLON_DELIMITER;

    private static final String EMAIL_PATTTERN = Constant.DEFAULT_EMAIL_PATTTERN;

    private static final String MOBILE_PATTERN = Constant.DEFAULT_MOBILE_PATTERN;

    /**
     * 验证Baidu邮箱
     *
     * @param mailStr 邮箱，以;分隔
     * @return 未通过的邮箱
     */
    public static String validateBaiduMail(String mailStr) {
        return validate(mailStr, EMAIL_PATTTERN);
    }

    /**
     * 验证手机号
     * @param mobileStr 手机号，以;分隔
     * @return 未通过的手机号
     */
    public static String validateMobile(String mobileStr) {
        return validate(mobileStr, MOBILE_PATTERN);
    }

    public static String validate(String compStr, String patternStr) {
        return validate(compStr, patternStr, DEFAULT_SEPARATOR);
    }

    private static String validate(String compStr, String patternStr, String separator) {
        StringBuilder msg = new StringBuilder();
        Pattern pattern = Pattern.compile(patternStr);
        String[] compStrs = compStr.split(separator);
        Matcher mat = null;
        for (String comp : compStrs) {
            if (StringUtils.isNotBlank(comp)) {
                mat = pattern.matcher(comp);
                if (!mat.find()) {
                    msg.append(comp).append(separator);
                }
            }
        }
        return msg.toString();
    }

    /**
     * 不能为空错误信息
     * @param o 对象
     * @param m map
     * @return list
     */
    public static List<String> isNullValidation(Object o, Map<String, String> m) {
        List<String> l = new ArrayList<String>();
        PropertyDescriptor[] ts = ReflectUtil.getPropertyDescriptors(o);
        for (PropertyDescriptor t : ts) {
            Method r = t.getReadMethod();
            if (r != null && m.containsKey(t.getName())) {
                try {
                    if (!Modifier.isPublic(r.getDeclaringClass().getModifiers())) {
                        r.setAccessible(true);
                    }
                    if (null == r.invoke(o)) {
                        l.add(m.get(t.getName()) + "不能为空");
                    }
                }
                catch (Throwable e) {
                    throw new RuntimeException("Could not copy property '" + t.getName()
                            + "' from source to target", e);
                }
            }
        }
        return l;
    }

    /**
     * 检验长度
     * @param o 对象
     * @param map map
     * @param params map
     * @return list
     */
    public static List<String> lengthValidation(Object o, Map<String, String> map, Map<String, Integer> params) {
        List<String> l = new ArrayList<String>();
        PropertyDescriptor[] ts = ReflectUtil.getPropertyDescriptors(o);
        for (PropertyDescriptor t : ts) {
            Method r = t.getReadMethod();
            if (r != null && params.containsKey(t.getName()) && map.containsKey(t.getName())) {
                try {
                    if (!Modifier.isPublic(r.getDeclaringClass().getModifiers())) {
                        r.setAccessible(true);
                    }
                    Object value = r.invoke(o);
                    if (null != value
                            && value.toString().length() > params.get(t.getName())) {
                        l.add(map.get(t.getName()) + "的实际长度为" + value.toString().length()
                                + "最大要求长度为" + params.get(t.getName()));
                    }
                }
                catch (Throwable e) {
                    throw new RuntimeException("Could not copy property '" + t.getName()
                            + "' from source to target", e);
                }
            }
        }
        return l;
    }
}
