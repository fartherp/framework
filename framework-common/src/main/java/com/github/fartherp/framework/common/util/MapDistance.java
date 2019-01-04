/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 位置经纬度距离计算
 * Auth: CK
 * Date: 2016/6/15
 */
public class MapDistance {
    public static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     *
     * @param lng1 用户经度
     * @param lat1 用户纬度
     * @param lng2 商家经度
     * @param lat2 商家纬度
     * @return double
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lng1);
        double radLat2 = rad(lng2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lat1) - rad(lat2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        return distance;
    }

    /**
     * 获取当前用户一定距离以内的经纬度值
     * @param lng 经度
     * @param lat 纬度
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     * @return Map
     */
    public static Map<String, String> getAround(double lng, double lat, double raidusMile) {
        Map<String, String> map = new HashMap<>();

        Double degree = (24901 * 1609) / 360.0; // 获取每度

        Double mpdLng = Double.parseDouble((degree * Math.cos(lng * (Math.PI / 180)) + "").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = lat - radiusLng;
        // 获取最大经度
        Double maxLat = lat + radiusLng;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = lng - radiusLat;
        // 获取最大纬度
        Double maxLng = lng + radiusLat;

        map.put("minLat", minLat + "");
        map.put("maxLat", maxLat + "");
        map.put("minLng", minLng + "");
        map.put("maxLng", maxLng + "");

        return map;
    }
}
