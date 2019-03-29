/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.kv.dao.impl;

import com.github.fartherp.framework.core.kv.dao.KvDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * The query of the database use the {@link NamedParameterJdbcTemplate} of the spring,
 * the {@link #dataSource} is setted through the XML injection of the spring.
 * <pre>
 * &lt;bean id="kvDao" class="com.github.fartherp.framework.core.kv.dao.impl.KvDaoImpl"&gt;
 *     &lt;property name="dataSource" ref="dataSource"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * Author: CK
 * Date: 2015/11/13
 */
public class KvDaoImpl implements KvDao {

    private DataSource dataSource;

    public List<Map<String, Object>> execute(String sql, Map<String, Object> namedParams) {
        NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(dataSource);
        return tpl.queryForList(sql, namedParams);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
