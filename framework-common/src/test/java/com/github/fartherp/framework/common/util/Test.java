/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/14
 */
public class Test {
    public static BigDecimal hundred = new BigDecimal(100);
    public static void main(String[] args) {
        BigDecimal acquirer_trans_fee = BigDecimal.ZERO;
        long [] arrays = {1,100,100,100,200,200,300,310,350,500,600,600,600,600,800,1100,1200,1500,1800,2100,2155,2500,2500,2600,2800,3600,3800,5900,8900,26300,45600,89600};
        for (int i = 0; i < arrays.length; i++) {
            Tran tran = new Tran(1,0.78,0,0,4,1,1,2, 3846.15,0.78, 30, "2016-07-10 00:00:02",arrays[i]);
            BigDecimal t = calculateAcqFee(tran, "2016-07-10 11:05:02");
            acquirer_trans_fee = BigDecimalUtil.add(acquirer_trans_fee, t);
        }
        System.out.println(acquirer_trans_fee.longValue());

        BigDecimal a = new BigDecimal(3.9);
        System.out.println(a.setScale(1, BigDecimal.ROUND_HALF_UP).longValue());
    }

    public static BigDecimal calculateAcqFee(Tran tran, String currentDate) {
        String effDate = tran.eff_date;
        if (currentDate.compareTo(effDate) < 0) {
            return BigDecimal.ZERO;
        }
        Data data = new Data(new BigDecimal(tran.fee_rate), new BigDecimal(tran.max_fee), new BigDecimal(tran.limit_val), tran.zone_fee_cnd_one, tran.zone_fee_cnd_two, tran.three_flag_one, tran.three_flag_two,
                new BigDecimal(tran.zone_fee), new BigDecimal(tran.zone_fee_one), new BigDecimal(tran.zone_fee_two), new BigDecimal(tran.amount));
        if (tran.fee_mod == 2) {
            return calculateMaxFeeRate(data);
        } else if (tran.fee_mod == 3) {
            return calculateThreeFeeRate(data);
        } else {
            return calculateFeeRate(data);
        }
    }

    /**
     * 费率
     * @param tran
     * @return
     */
    public static BigDecimal calculateFeeRate(Data tran) {
        // 交易金额(分) 费率(0.38)
        BigDecimal tmp_amount = BigDecimalUtil.mul(tran.amount, tran.fee_rate);
        BigDecimal acquirer_trans_fee = BigDecimalUtil.div(tmp_amount, hundred);
        return acquirer_trans_fee;
    }

    /**
     * 封顶
     * @param tran
     * @return
     */
    public static BigDecimal calculateMaxFeeRate(Data tran) {
        // 交易金额(分) 临界值(元)->分
        BigDecimal limit_val_amount = BigDecimalUtil.mul(tran.limit_val, hundred);
        boolean flag = tran.amount.compareTo(limit_val_amount) > 0;
        BigDecimal acquirer_trans_fee = null;
        if (flag) {
            // 封顶(元)->分
            acquirer_trans_fee = BigDecimalUtil.mul(tran.max_fee, hundred);
        } else {
            // 交易金额(分)
            BigDecimal tmp_amount = BigDecimalUtil.mul(tran.amount, tran.fee_rate);
            acquirer_trans_fee = BigDecimalUtil.div(tmp_amount, hundred);
        }
        return acquirer_trans_fee;
    }

    /**
     * 阶梯
     * @param tran
     * @return
     */
    public static BigDecimal calculateThreeFeeRate(Data tran) {
        BigDecimal acquirer_trans_fee = calculateThreeOneCondition(tran, true);
        if (acquirer_trans_fee.compareTo(BigDecimal.ZERO) > 0) {
            return acquirer_trans_fee;
        }
        acquirer_trans_fee = calculateThreeOneCondition(tran, false);
        return acquirer_trans_fee;
    }

    public static BigDecimal calculateThreeOneCondition(Data tran, boolean flag) {
        // 交易金额(分) 临界值(元)->分
        BigDecimal limit_val_amount = BigDecimalUtil.mul(tran.zone_fee, hundred);
        int diff = tran.amount.compareTo(limit_val_amount);
        BigDecimal acquirer_trans_fee = BigDecimal.ZERO;
        if (flag) {
            // 计算第一条阶梯
            if (tran.zone_fee_cnd_one == 1 && diff >= 0) {
                // 大于等于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            } else if (tran.zone_fee_cnd_one == 2 && diff > 0) {
                // 大于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            } else if (tran.zone_fee_cnd_one == 3 && diff <= 0) {
                // 小于等于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            } else if (tran.zone_fee_cnd_one == 4 && diff < 0) {
                // 小于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            }
        } else {
            // 计算第二条阶梯
            if (tran.zone_fee_cnd_two == 1 && diff >= 0) {
                // 大于等于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            } else if (tran.zone_fee_cnd_two == 2 && diff > 0) {
                // 大于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            } else if (tran.zone_fee_cnd_two == 3 && diff <= 0) {
                // 小于等于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            } else if (tran.zone_fee_cnd_two == 4 && diff < 0) {
                // 小于
                acquirer_trans_fee = calculateThreeActiveAmount(tran, flag);
            }
        }
        return acquirer_trans_fee;
    }

    public static BigDecimal calculateThreeActiveAmount(Data tran, boolean flag) {
        BigDecimal acquirer_trans_fee = null;
        if (flag) {
            if (tran.three_flag_one == 1) {
                // 按比例
                // 交易金额(分)
                BigDecimal tmp_amount = BigDecimalUtil.mul(tran.amount, tran.zone_fee_one);
                acquirer_trans_fee = BigDecimalUtil.div(tmp_amount, hundred);
                return acquirer_trans_fee;
            } else {
                // 固定值
                acquirer_trans_fee = BigDecimalUtil.mul(tran.zone_fee_one, hundred);
                return acquirer_trans_fee;
            }
        } else {
            if (tran.three_flag_two == 1) {
                // 按比例
                // 交易金额(分)
                BigDecimal tmp_amount = BigDecimalUtil.mul(tran.amount, tran.zone_fee_two);
                acquirer_trans_fee = BigDecimalUtil.div(tmp_amount, hundred);
                return acquirer_trans_fee;
            } else {
                // 固定值
                acquirer_trans_fee = BigDecimalUtil.mul(tran.zone_fee_two, hundred);
                return acquirer_trans_fee;
            }
        }
    }

    public static class Tran {
        public int fee_mod;
        public double fee_rate;
        public double max_fee;
        public double limit_val;
        public int zone_fee_cnd_one;
        public int zone_fee_cnd_two;
        public int three_flag_one;
        public int three_flag_two;
        public double zone_fee;
        public double zone_fee_one;
        public double zone_fee_two;
        public String eff_date;
        public double amount;

        public Tran(int fee_mod, double fee_rate, double max_fee, double limit_val, int zone_fee_cnd_one, int zone_fee_cnd_two, int three_flag_one, int three_flag_two, double zone_fee, double zone_fee_one, double zone_fee_two, String eff_date, double amount) {
            this.fee_mod = fee_mod;
            this.fee_rate = fee_rate;
            this.max_fee = max_fee;
            this.limit_val = limit_val;
            this.zone_fee_cnd_one = zone_fee_cnd_one;
            this.zone_fee_cnd_two = zone_fee_cnd_two;
            this.three_flag_one = three_flag_one;
            this.three_flag_two = three_flag_two;
            this.zone_fee = zone_fee;
            this.zone_fee_one = zone_fee_one;
            this.zone_fee_two = zone_fee_two;
            this.eff_date = eff_date;
            this.amount = amount;
        }
    }

    public static class Data {
        public BigDecimal fee_rate;
        public BigDecimal max_fee;
        public BigDecimal limit_val;
        public int zone_fee_cnd_one;
        public int zone_fee_cnd_two;
        public int three_flag_one;
        public int three_flag_two;
        public BigDecimal zone_fee;
        public BigDecimal zone_fee_one;
        public BigDecimal zone_fee_two;
        public BigDecimal amount;

        public Data(BigDecimal fee_rate, BigDecimal max_fee, BigDecimal limit_val, int zone_fee_cnd_one, int zone_fee_cnd_two, int three_flag_one, int three_flag_two, BigDecimal zone_fee, BigDecimal zone_fee_one, BigDecimal zone_fee_two, BigDecimal amount) {
            this.fee_rate = fee_rate;
            this.max_fee = max_fee;
            this.limit_val = limit_val;
            this.zone_fee_cnd_one = zone_fee_cnd_one;
            this.zone_fee_cnd_two = zone_fee_cnd_two;
            this.three_flag_one = three_flag_one;
            this.three_flag_two = three_flag_two;
            this.zone_fee = zone_fee;
            this.zone_fee_one = zone_fee_one;
            this.zone_fee_two = zone_fee_two;
            this.amount = amount;
        }
    }
}
