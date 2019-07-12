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
package com.github.fartherp.framework.database.mybatis;

import com.github.fartherp.framework.common.util.JsonUtil;
import com.github.fartherp.framework.database.mybatis.plugin.page.BaseVo;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class JDataTest {

    @Test
    public void testAddAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", "name1");
        map.put("2", "name2");
        map.put("3", "name3");
        String json = JsonUtil.toJson(map);
		assertEquals(json, "{\"1\":\"name1\",\"2\":\"name2\",\"3\":\"name3\"}");
    }

    @Test
    public void testObject() {
        PageTestVo vo = new PageTestVo(10);
        List<PageTestBo> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PageTestBo bo = new PageTestBo("name" + i);
            list.add(bo);
        }
        vo.setRows(list);
        String json = JsonUtil.toJson(vo);
        assertEquals(json, "{\"age\":10,\"firstPage\":1,\"total\":0,\"limit\":10,\"rows\":[{\"name\":\"name0\"},{\"name\":\"name1\"},{\"name\":\"name2\"}]}");
    }

    public static class PageTestVo extends BaseVo<PageTestBo> {
        int age;

        public PageTestVo(int age) {
            this.age = age;
        }
    }

    public static class PageTestBo implements Serializable {
        String name;

        public PageTestBo(String name) {
            this.name = name;
        }
    }
}
