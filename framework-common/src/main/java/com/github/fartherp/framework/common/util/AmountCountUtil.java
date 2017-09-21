/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import com.github.fartherp.framework.common.constant.Constant;

import java.math.BigDecimal;

/**
 * The amount calculate tool. the default scale is two precision.
 * Author: CK
 * Date: 2015/3/27.
 */
public class AmountCountUtil {

    /**
     * the add calculate by params is {@code double} type.
     * <pre>
     * 123 + 234 = 357.0
     * </pre>
     *
     * @param v1 the add is {@code double} type
     * @param v2 the added is {@code double} type
     * @return the calculate result
     */
    public static double add(double v1, double v2) {
        return add(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static double add(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.add(b2), scale);
    }

    /**
     * the add calculate by params is {@link BigDecimal} type.
     * <pre>
     *     123 + 234 = 357.00
     * </pre>
     * @param v1 the add is {@code BigDecimal} type
     * @param v2 the added is {@code BigDecimal} type
     * @return the calculate result
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return add(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static BigDecimal add(BigDecimal v1, BigDecimal v2, int scale) {
        return round1(v1.add(v2), scale);
    }

    /**
     * the sub calculate by params is {@code double} type.
     * <pre>
     * 123 - 234 = -111.0
     * </pre>
     *
     * @param v1 the sub is {@code double} type
     * @param v2 the subtracted is {@code double} type
     * @return the calculate result
     */
    public static double sub(double v1, double v2) {
        return sub(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static double sub(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.subtract(b2), scale);
    }

    /**
     * the sub calculate by params is {@link BigDecimal} type.
     * <pre>
     *     123 - 234 = -111.00
     * </pre>
     * @param v1 the sub is {@link BigDecimal} type
     * @param v2 the subtracted is {@link BigDecimal} type
     * @return the calculate result
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
        return sub(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static BigDecimal sub(BigDecimal v1, BigDecimal v2, int scale) {
        return round1(v1.subtract(v2), scale);
    }

    /**
     * the multiplicative calculate by params is {@code double} type.
     * <pre>
     * 10 * 23 = 230.0
     * </pre>
     *
     * @param v1 the multiplicative is {@code double} type
     * @param v2 the multiplicand is {@code double} type
     * @return the calculate result
     */
    public static double mul(double v1, double v2) {
        return mul(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static double mul(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.multiply(b2), scale);
    }

    /**
     * the multiplicative calculate by params is {@link BigDecimal} type.
     * <pre>
     *     10 * 23 = 230.00
     * </pre>
     * @param v1 the multiplicative is {@link BigDecimal} type
     * @param v2 the multiplicand is {@link BigDecimal} type
     * @return the calculate result
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        return mul(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale) {
        return round1(v1.multiply(v2), scale);
    }

    /**
     * the div calculate by params is {@code double} type.
     * <pre>
     * 23 * 10 = 2.3
     * </pre>
     *
     * @param v1 the div is {@code double} type
     * @param v2 the dividend is {@code double} type
     * @return the calculate result
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static double div(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.divide(b2, BigDecimal.ROUND_HALF_UP), scale);
    }

    /**
     * the div calculate by params is {@link BigDecimal} type.
     * @param v1 the div is {@link BigDecimal} type
     * @param v2 the dividend {@link BigDecimal} type
     * @return the calculate result
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, Constant.DEFAULT_SCALE);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
        if (BigDecimal.ZERO.compareTo(v2) == 0) {
            return BigDecimal.ZERO;
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    private static double round(BigDecimal value, int scale) {
        return round1(value, scale).doubleValue();
    }

    /**
     * the final {@link BigDecimal} value through the precision calculate.
     * @param value the {@link BigDecimal} value
     * @param scale the precision
     * @return the precision calculate result
     */
    private static BigDecimal round1(BigDecimal value, int scale) {
        return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
}