/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.util;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class JListTest {

    @Test
    public void testAddAll() throws Exception {
        List<Car> list  = new ArrayList<Car>();
        for (int i = 0; i < 3; i++) {
            Car car = new Car("name" + i);
            list.add(car);
        }
        String json = JsonResp.asList().addAll(list).toJson();
        System.out.println(json);
    }

    public static class Car {
        String name;

        public Car(String name) {
            this.name = name;
        }
    }
}