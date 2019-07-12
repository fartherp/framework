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
package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MapUtilTest {

	@Test
	public void testMapPlainPut() {
		Map<String, Object> map = MapUtil.<String, Object>build(1).put("name", "name").get();
		assertEquals(map.get("name"), "name");
	}

	@Test
	public void testMapPlainPutAll() {
		Map<String, Object> mapAll = new HashMap<>();
		mapAll.put("name", "name");
		mapAll.put("age", 25);
		Map<String, Object> map = MapUtil.<String, Object>build().putAll(mapAll).get();
		assertEquals(map.get("name"), "name");
		assertEquals(map.get("age"), 25);
	}

	@Test
	public void testMapPlainPutAllEmpty() {
		Map<String, Object> mapAll = new HashMap<>();
		Map<String, Object> map = MapUtil.<String, Object>build().putAll(mapAll).get();
		assertEquals(map.size(), 0);
	}

	@Test
	public void testDefaultToMap() {
		A a = new A();
		a.setName("name");
		a.setAge(25);
		Map<String, Object> params = MapUtil.toMap(a);
		assertEquals(params.get("name"), "name");
		assertEquals(params.get("age"), 25);
	}

	@Test
	public void testAliasMapToMap() {
		Map<String, String> alias = new HashMap<>();
		alias.put("name", "testName");
		A a = new A();
		a.setName("name");
		a.setAge(25);
		Map<String, Object> params = MapUtil.toMap(a, alias);
		assertEquals(params.get("testName"), "name");
		assertEquals(params.get("age"), 25);
	}

    @Test
    public void testToObject() {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "name");
		params.put("age", 25);
		A a = new A();
		MapUtil.toObject(params, a);
		assertEquals(a.getName(), "name");
		assertEquals(a.getAge(), 25);
    }

	@Test
	public void testEmptyMapConvertToList() {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = MapUtil.mapConvertToList(map);
		assertEquals(list.size(), 0);
	}

	@Test
	public void testMapConvertToList() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "name");
		map.put("age", 25);
		List<Map<String, Object>> list = MapUtil.mapConvertToList(map);
		assertEquals(list.size(), 2);
		assertEquals(list.get(0).get("value"), "name");
		assertEquals(list.get(1).get("value"), "age");
	}

	@Test
	public void testMapConvertToListDefaultValue() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "name");
		map.put("age", 25);
		List<Map<String, Object>> list = MapUtil.mapConvertToList(map, "age");
		assertTrue((Boolean) list.get(1).get("selected"));
	}

    private static class A {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
