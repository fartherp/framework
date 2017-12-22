/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/12/19
 */
public class JsonUtilTest {
    @Test
    public void testToJson() throws Exception {
        User user = new User();
        user.setName("名称");
        user.setDesc("测试");
        user.setAge(27);
        assertEquals("{\"name\":\"名称\",\"age\":27}", JsonUtil.toJson(user, "desc"));
    }

    public static class User {
        private String name;
        private int age;
        private String desc;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}