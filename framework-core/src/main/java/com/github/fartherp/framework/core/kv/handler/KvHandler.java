/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.kv.handler;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/11/13
 */
public interface KvHandler {
    /**
     * IN条件
     */
    public static final int IN = 1;
    /**
     * all
     */
    public static final int ALL = 2;
    /**
     * 前缀
     */
    public static final int PREFIX_SQL = 3;
    /**
     * 获得kvHandler的名称
     *
     * @return the name
     */
    Integer getName();

    /**
     * 扩展执行
     * @param params 参数
     * @return list
     */
    List<Map<String, Object>> extendExecute(Map<String, Object> params);

    /**
     * 通过dao执行SQL
     * @return
     */
    public boolean isDaoExecute();

    /**
     * 创建SQL
     * @return SQL
     */
    public String createSql(Map<String, Object> params);

    /**
     * 获取主键字段名称
     * @return 主键字段名称
     */
    String getKeyField();

    /**
     * 获取值名称
     * @return 值名称
     */
    String getValueField();

    /**
     * 获取默认值标志
     * @return 默认值标志
     */
    int getDefaultValueFlag();
}
