/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.kv.service;

import com.github.fartherp.framework.core.kv.handler.KvHandler;
import com.github.fartherp.framework.core.kv.handler.KvHandlerSupport;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * service层动态SQL, 处理前缀, 后缀, 全模糊查询
 * <pre>
 * &lt;bean id="kvService" class="cn.vansky.framework.core.kv.service.impl.KvServiceImpl"&gt;
 *     &lt;property name="dao" ref="kvDao"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * Author: CK
 * Date: 2015/11/13
 */
public interface KvService {
    /**
     * 添加处理业务逻辑
     * @param handler handler
     */
    void addHandler(KvHandler handler);

    /**
     * 根据模块名查询相应的kvHandler, 如果没有,返回null.
     *
     * @param name 非空(不能为null && 不能为空串) 字符串.
     * @return 相应的kvHandler, 如果没有,返回null.
     */
    KvHandlerSupport lookup(Integer name);

    /**
     * IN查询
     * @param support support
     * @param keys 扩展信息
     * @param extraConds 扩展信息
     * @return
     */
    public List<Map<Object, Object>> in(KvHandlerSupport support, Collection<Object> keys,
                                        Map<String, Object> extraConds);

    /**
     * ALL查询
     * @param support support
     * @param extraConds 扩展信息
     * @return list
     */
    public List<Map<Object, Object>> all(KvHandlerSupport support, Map<String, Object> extraConds);

    /**
     * 全LIKE模糊查询
     * @param support support
     * @param key like关键字
     * @param extrConds 扩展信息
     * @param maxLimit 返回数据条数
     * @return list
     */
    public List<Map<Object, Object>> fuzzy(KvHandlerSupport support, String key,
                                           Map<String, Object> extrConds, Integer maxLimit);

    /**
     * 字段前面like
     * @param support support
     * @param prefix 前缀
     * @param extraConds 扩展信息
     * @param maxLimit 返回数据条数
     * @return list
     */
    public List<Map<Object, Object>> prefix(KvHandlerSupport support, String prefix,
                                            Map<String, Object> extraConds, Integer maxLimit);

    /**
     * 字段like
     * @param support support
     * @param prefix 关键字
     * @param extraConds 扩展信息
     * @param needPrefix 关键字在%前面
     * @param needPostfix 关键字在%后面
     * @param maxLimit 返回数据条数
     * @return list
     */
    public List<Map<Object, Object>> prefix(KvHandlerSupport support, String prefix,
                                            Map<String, Object> extraConds, boolean needPrefix,
                                            boolean needPostfix, Integer maxLimit);
}
