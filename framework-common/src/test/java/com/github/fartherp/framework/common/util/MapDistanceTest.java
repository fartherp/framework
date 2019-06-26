/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class MapDistanceTest {

    @Test
    public void testGetDistance() {
		assertEquals(MapDistance.getDistance(116.327396, 39.938416, 120.332685, 37.617222), 462.0);
		assertEquals(MapDistance.getDistance(120.332685, 37.617222, 116.327396, 39.938416), 462.0);
    }

    @Test
    public void testGetAround() {
		Map<String, Double> map = MapDistance.getAround(117.11811, 36.68484, 13000);
    	assertEquals(map.get("maxLat"), Double.valueOf("36.941095784459634"));
    	assertEquals(map.get("minLat"), Double.valueOf("36.42858421554037"));
    	assertEquals(map.get("minLng"), Double.valueOf("117.001301883613"));
    	assertEquals(map.get("maxLng"), Double.valueOf("117.234918116387"));
    }
}
