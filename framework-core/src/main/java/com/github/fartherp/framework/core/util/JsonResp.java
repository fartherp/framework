/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import com.google.gson.GsonBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前后端统一接口
 * Author: CK
 * Date: 2016/1/27
 */
public class JsonResp {
    /**
     * 成功标志
     */
    public static final int STATUS_SUCCESS = 0;
    /**
     * 失败标志
     */
    public static final int STATUS_ERROR = 1;
    /**
     * 返回状态代码
     */
    private int status = STATUS_SUCCESS;
    /**
     * 返回状态信息
     */
    private String statusInfo = "";
    /**
     * null序列化开关,默认关闭
     */
    private transient boolean showNull;
    /**
     * 日期格式化模版
     */
    private transient String datePattern;
    /**
     * 数据对象
     */
    private Object data;

    /**
     * 响应类型
     */
    public static enum RespType {
        /**
         * 数据列表
         */
        LIST,
        /**
         * 树形结构
         */
        TREE,
        /**
         * 空结构
         */
        EMPTY
    }

    /**
     * 响应类型
     */
    private transient RespType type;

    /**
     * 构造函数, 使用默认的信息.
     *
     * @param type 类型.
     */
    public JsonResp(RespType type) {
        this.type = type;
    }

    /**
     * 返回json串.
     *
     * @return json串.
     */
    public String toJson() {
        GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
        if (showNull) {
            builder.serializeNulls();
        }
        if (!StringUtils.isBlank(datePattern)) {
            builder.setDateFormat(datePattern);
        }
        return builder.create().toJson(this, JsonResp.class);
    }

    /**
     * 设置日期模式
     * @param datePattern 日期模式
     * @return 该响应对象.
     */
    public JsonResp setDatePattern(String datePattern) {
        this.datePattern = datePattern;
        return this;
    }

    /**
     * 空值序列化
     * @return 该响应对象.
     */
    public JsonResp withNulls() {
        this.showNull = true;
        return this;
    }

    /**
     * 设置响应成功标志.
     *
     * @return 该响应对象.
     */
    public JsonResp success() {
        this.status = STATUS_SUCCESS;
        return this;
    }

    /**
     * 设置响应失败标志.
     *
     * @param message 消息.
     * @return 该响应对象.
     */
    public JsonResp error(String message) {
        this.status = STATUS_ERROR;
        this.statusInfo = message;
        return this;
    }

    /**
     * 基础响应
     */
    public static class BaseResp {

        JsonResp resp;

        public BaseResp(JsonResp resp) {
            this.resp = resp;
        }

        /**
         * 设置响应成功标志.
         *
         * @return 该响应对象.
         */
        public JsonResp success() {
            resp.success();
            return resp;
        }

        /**
         * 设置响应失败标志.
         *
         * @param message 消息.
         * @return 该响应对象.
         */
        public JsonResp error(String message) {
            resp.error(message);
            return resp;
        }

        /**
         * 显示null
         *
         * @return 该响应对象
         */
        public JsonResp withNulls() {
            resp.withNulls();
            return resp;
        }

        /**
         * 设置日期格式
         *
         * @param pattern 格式,非null
         * @return 该响应对象
         */
        public JsonResp setDatePattern(String pattern) {
            resp.setDatePattern(pattern);
            return resp;
        }

        /**
         * 返回json串.
         * @return json串.
         */
        public String toJson() {
            return resp.toJson();
        }
    }

    /**
     * 创建列表响应
     *
     * @return 列表响应
     */
    public static JList asList() {
        JsonResp resp = new JsonResp(RespType.LIST);
        JList jList = new JList(resp);
        return jList;
    }

    /**
     *  列表类
     */
    public static class JList extends BaseResp {

        public JList(JsonResp resp) {
            super(resp);
            resp.data = new ArrayList<Object>();
        }

        /**
         * 增加数据集合到数据列表中.
         *
         * @param collection 数据集合
         * @return 该响应对象.
         */
        @SuppressWarnings("unchecked")
        public JList addAll(Collection collection) {
            if (CollectionUtils.isNotEmpty(collection)) {
                ((List) resp.data).addAll(collection);
            }
            return this;
        }
    }

    /**
     * 创建树形结构响应.
     *
     * @return 响应对象.
     */
    public static JsonResp asData(Object o) {
        JsonResp resp = new JsonResp(RespType.TREE);
        resp.data = o;
        return resp;
    }

    /**
     * 创建树形结构响应.
     *
     * @return 响应对象.
     */
    public static JData asData() {
        JsonResp resp = new JsonResp(RespType.TREE);
        JData jData = new JData(resp);
        return jData;
    }

    /**
     * 树形结构响应
     */
    public static class JData extends BaseResp {

        public JData(JsonResp resp) {
            super(resp);
            resp.data = new HashMap<String, Object>();
        }

        /**
         * 增加一个map的内容到数据对象中.
         *
         * @param data map内容
         * @return 返回该响应对象.
         */
        @SuppressWarnings("unchecked")
        public JData addAll(Map<String, Object> data) {
            if (MapUtils.isNotEmpty(data)) {
                ((Map) resp.data).putAll(data);
            }
            return this;
        }

        /**
         * 增加一个kv对到数据对象中.
         *
         * @param key key
         * @param value value
         * @return 返回该响应对象.
         */
        @SuppressWarnings("unchecked")
        public JData add(String key, Object value) {
            ((Map) resp.data).put(key, value);
            return this;
        }
    }

    /**
     * 创建空结构响应.
     *
     * @return 响应对象.
     */
    public static JEmpty asEmpty() {
        JsonResp resp = new JsonResp(RespType.EMPTY);
        JEmpty jEmpty = new JEmpty(resp);
        return jEmpty;
    }

    /**
     * 空结构响应
     */
    public static class JEmpty extends BaseResp {

        public JEmpty(JsonResp resp) {
            super(resp);
        }
    }
}
