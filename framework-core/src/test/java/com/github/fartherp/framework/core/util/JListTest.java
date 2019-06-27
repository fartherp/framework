/*
 * Copyright (c) 2017. CK. All rights reserved.
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
