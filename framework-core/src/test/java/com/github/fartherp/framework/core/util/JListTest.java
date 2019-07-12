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
package com.github.fartherp.framework.core.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class JListTest {

    @Test
    public void testAddAll() {
        List<Car> list  = new ArrayList<Car>();
        for (int i = 0; i < 3; i++) {
            Car car = new Car("name" + i);
            list.add(car);
        }
        String json = JsonResp.asList().addAll(list).toJson();
		Assert.assertEquals(json, "{\"status\":0,\"statusInfo\":\"\",\"data\":[{\"name\":\"name0\"},{\"name\":\"name1\"},{\"name\":\"name2\"}]}");
    }

    public static class Car {
        String name;

        public Car(String name) {
            this.name = name;
        }
    }
}
