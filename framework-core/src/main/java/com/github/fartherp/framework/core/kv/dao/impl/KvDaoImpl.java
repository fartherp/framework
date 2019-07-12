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
