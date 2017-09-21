/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class MapUtilsTest {

    @Test
    public void testToObject() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "name");
        params.put("age", 25L);
        A a = new A();
        MapUtils.toObject(params, a);
        System.out.println(a.getName() + "|" + a.getAge());
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