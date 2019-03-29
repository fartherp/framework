/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.kv.service.impl;

import com.github.fartherp.framework.common.util.MapUtil;
import com.github.fartherp.framework.core.kv.dao.KvDao;
import com.github.fartherp.framework.core.kv.handler.KvHandler;
import com.github.fartherp.framework.core.kv.handler.KvHandlerSupport;
import com.github.fartherp.framework.core.kv.service.KvService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * &lt;bean id="kvService" class="com.github.fartherp.framework.core.kv.service.impl.KvServiceImpl"&gt;
 *     &lt;property name="dao" ref="kvDao"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * Author: CK
 * Date: 2015/11/13
 */
public class KvServiceImpl implements KvService {

    private KvDao dao;

    /**
     * kv配置记录表
     */
    private Map<Integer, KvHandler> kvHandlers = new HashMap<>();

    /**
     * Adds the kv handler.
     *
     * @param handler the kv handler
     */
    public void addHandler(KvHandler handler) {
        this.kvHandlers.put(handler.getName(), handler);
    }

    public KvHandlerSupport lookup(Integer name) {
        if (null != name && kvHandlers.containsKey(name)) {
            KvHandlerSupport support = new KvHandlerSupport();
            support.setKvHandler(kvHandlers.get(name));
            return support;
        }
        return null;
    }

    public List<Map<Object, Object>> in(KvHandlerSupport support, Collection<Object> keys,
                                        Map<String, Object> extraConds) {
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Object> context =
                MapUtil.<String, Object> build().put("inset", keys).putAll(extraConds).get();
        return execute(support, context);
    }

    public List<Map<Object, Object>> all(KvHandlerSupport support, Map<String, Object> extraConds) {
        Map<String, Object> context = MapUtil.<String, Object> build().putAll(extraConds).get();
        return execute(support, context);
    }

    public List<Map<Object, Object>> fuzzy(KvHandlerSupport support, String key,
                                           Map<String, Object> extrConds, Integer maxLimit) {
        return prefix(support, key, extrConds, true, true, maxLimit);
    }

    public List<Map<Object, Object>> prefix(KvHandlerSupport support, String prefix,
                                            Map<String, Object> extraConds, Integer maxLimit) {
        return prefix(support, prefix, extraConds, true, false, maxLimit);
    }

    public List<Map<Object, Object>> prefix(KvHandlerSupport support, String prefix,
                                            Map<String, Object> extraConds, boolean needPrefix,
                                            boolean needPostfix,  Integer maxLimit) {
        Preconditions.checkArgument(maxLimit > 0);
        if (StringUtils.isBlank(prefix)) {
            return Collections.emptyList();
        }

        String key = prefix;
        if (needPostfix) {
            key = "%" + key;
        }
        if (needPrefix) {
            key += "%";
        }

        Map<String, Object> context =
                MapUtil.<String, Object> build().put("prefix", key).put("maxLimit", maxLimit)
                        .putAll(extraConds).get();
        return execute(support, context);
    }

    public List<Map<Object, Object>> execute(KvHandlerSupport support, Map<String, Object> context) {
        List<Map<String, Object>> list =
                support.isDaoExecute() ? dao.execute(support.getSql(context), context) : support.execute(context);
        return transformResult(list, support);
    }

    /**
     * 将List<Map<>>结构打平成Map<>结构
     *
     * @param list List对象
     * @return Map对象
     */
    public List<Map<Object, Object>> transformResult(List<Map<String, Object>> list,
                                                     KvHandlerSupport support) {
        List<Map<Object, Object>> result = new ArrayList<Map<Object, Object>>();
        if (1 == support.getDefaultValueFlag()) {
            Map<Object, Object> m = new HashMap<Object, Object>();
            m.put("text", "全部");
            m.put("value", -1);
            if (support.getDefaultValue() == null) {
                // 前端无默认值
                m.put("selected", true);
            }
            result.add(m);
        }

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<Object, Object> m = new HashMap<>();
            Object text = map.get(support.getKvHandler().getKeyField());
            m.put("text", text);
            Object value = map.get(support.getKvHandler().getValueField());
            m.put("value", value);
            // 包括默认值
            if (support.getDefaultValue() == null && 2 == support.getDefaultValueFlag() && i == 0) {
                // 前端无默认值,取第一条为默认选择
                m.put("selected", true);
            } else if (support.getDefaultValue() != null
                    && support.getDefaultValue().toString().equals(value.toString())) {
                // 取前台默认值
                m.put("selected", true);
            }
            result.add(m);
        }
        return result;
    }

    public KvDao getDao() {
        return dao;
    }

    @Override
    public void setDao(KvDao dao) {
        this.dao = dao;
    }
}
