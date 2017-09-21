/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

public class MapDistanceTest {

    @Test
    public void testGetDistance() throws Exception {
        System.out.println(MapDistance.getDistance(116.327396, 39.938416, 120.332685, 37.617222));
        System.out.println(MapDistance.getDistance(120.332685, 37.617222, 116.327396, 39.938416));
    }

    @Test
    public void testGetAround() throws Exception {
        System.out.println(MapDistance.getAround(117.11811, 36.68484, 13000));
    }
}