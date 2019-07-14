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

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2017/12/19
 */
public class JsonUtilTest {
    @Test
    public void testToJson() {
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
