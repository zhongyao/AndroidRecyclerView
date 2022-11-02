package com.hongri.recyclerview.utils;

import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * 通用数字处理工具类
 */
public final class CommonNumberUtil {

    private static final String TAG = "CommonNumberUtil";

    private CommonNumberUtil() {
    }

    private static NumberFormat nf = NumberFormat.getNumberInstance(Locale.CHINA);
    private static NumberFormat nfGroup = NumberFormat.getNumberInstance(Locale.CHINA);
    private static final NumberFormat NFGROUP_WORK_THREAD = NumberFormat
            .getNumberInstance(Locale.CHINA);
    private static final int INVALID_INT = Integer.MIN_VALUE;

    /**
     * 注意 获取十/百/千/万... 分位的数值【point 值分别对应 1 2 3 4 ...】
     */
    public static String formatNum(String numberStr, int point) {
        if (isNumeric(numberStr)) {
            String pattern;
            if (point == 1) {
                pattern = "#.0";
                if (numberStr.equals("0")) {
                    return "0.0";
                }
            } else if (point == 2) {
                pattern = "#.00";
                if (numberStr.equals("0")) {
                    return "0.00";
                }
            } else if (point == 3) {
                pattern = "#.000";
                if (numberStr.equals("0")) {
                    return "0.000";
                }
            } else if (point == 4) {
                pattern = "#.0000";
                if (numberStr.equals("0")) {
                    return "0.0000";
                }
            } else if (point == 5) {
                pattern = "#.00000";
                if (numberStr.equals("0")) {
                    return "0.00000";
                }
            } else if (point == 6) {
                pattern = "#.000000";
                if (numberStr.equals("0")) {
                    return "0.000000";
                }
            } else if (point == 7) {
                pattern = "#.0000000";
                if (numberStr.equals("0")) {
                    return "0.0000000";
                }
            } else {
                pattern = "#.00000000";
                if (numberStr.equals("0")) {
                    return "0.00000000";
                }
            }
            double doubleNum = Double.parseDouble(numberStr);
            return new DecimalFormat(pattern).format(doubleNum);
        }
        return numberStr;
    }

    /**
     * 去除千分符号
     *
     * @param number number
     * @return String
     */
    public static String formatDeleteGroup(String number){
        if (!TextUtils.isEmpty(number)&&number.contains(",")){
            number=number.replace(",","");
        }
        return number;
    }

    /**
     * 格式化数据显示千分位
     *
     * @param number 数字
     * @param defaultValue 转换失败的返回值
     * @return 转换后的数据
     */
    public static String formatGroup(String number, String defaultValue) {
        if (number != null && number.startsWith("0.")) {
            return number;
        }

        if (Looper.getMainLooper() != Looper.myLooper()) {
            //子线程执行
            return formatGroupNonMainThread(number, defaultValue);
        }

        try {
            nfGroup.setGroupingUsed(true);
            int i = number.indexOf(".");
            if (i != -1) {
                int decimalCount = number.substring(i).length() - 1;
                if (decimalCount != INVALID_INT) {
                    nfGroup.setMinimumFractionDigits(decimalCount);
                    nfGroup.setMaximumFractionDigits(decimalCount);
                }
            } else {
                nfGroup.setMinimumFractionDigits(0);
                nfGroup.setMaximumFractionDigits(0);
            }
            return nfGroup.format(new BigDecimal(number));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 格式化数据显示千分位(子线程) 子线程竞争共享资源{@link #NFGROUP_WORK_THREAD}，使用锁保证线程安全
     *
     * @param number 数字
     * @param defaultValue 转换失败的返回值
     * @return 转换后的数据
     */
    private static String formatGroupNonMainThread(String number, String defaultValue) {
        synchronized (NFGROUP_WORK_THREAD) {
            try {
                NFGROUP_WORK_THREAD.setGroupingUsed(true);
                int i = number.indexOf(".");
                if (i != -1) {
                    int decimalCount = number.substring(i).length() - 1;
                    if (decimalCount != INVALID_INT) {
                        NFGROUP_WORK_THREAD.setMinimumFractionDigits(decimalCount);
                        NFGROUP_WORK_THREAD.setMaximumFractionDigits(decimalCount);
                    }
                } else {
                    NFGROUP_WORK_THREAD.setMinimumFractionDigits(0);
                    NFGROUP_WORK_THREAD.setMaximumFractionDigits(0);
                }
                return NFGROUP_WORK_THREAD.format(new BigDecimal(number));
            } catch (Exception e) {
                return defaultValue;
            }
        }
    }

    /**
     * 截断输入框的值
     *
     * @param num 输入框值
     * @param intPartLength 整数位数
     * @param floatPartLength 小数位数
     * @return 截断值
     */
    public static String checkNumValid(Editable num, int intPartLength, int floatPartLength) {
        if (num == null) {
            return null;
        }
        if (num.length() > num.toString().trim().length()) {
            //有空格
            return num.toString().trim();
        }

        //没有输入
        if (num.toString().trim().length() == 0) {
            return null;
        }

        int dotPos = num.toString().indexOf(".");

        if (dotPos < 0) {
            dotPos = num.length();
        }
        //小数点开头
        if (dotPos == 0) {
            return num.delete(dotPos, dotPos + 1).toString();
        }
        //整数部分超长
        if (dotPos > intPartLength) {
            return num.delete(dotPos - 1, dotPos).toString();
        }
        //小数部分超长
        if (num.length() - dotPos - 1 > floatPartLength) {
            return num.delete(dotPos + floatPartLength + 1, dotPos + floatPartLength + 2)
                    .toString();
        }

        return null;
    }

    /**
     * 是否是数字(科学计数法不支持)
     *
     * @param str 字符串
     * @return 是否是数字
     */
    public static boolean isNumeric(String str) {
        if (str != null && !"".equals(str.trim())) {
            return str.matches("^(-?\\d+)(\\.\\d+)?$");
        }

        return false;
    }

    /**
     * 是否是正整数(科学计数法不支持)
     *
     * @param str 字符串
     * @return 是否是正整数
     */
    public static boolean isInteger(String str) {
        if (str != null && !"".equals(str.trim())) {
            return str.matches("^[1-9]\\d*$");
        }

        return false;
    }

    /**
     * 是否为纯数字（科学计数法不支持）
     *
     * @param str 字符串
     * @return 是否是数字
     */
    public static boolean isDigit(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static String roundFloor(String num, int len) {
        String strTemp = "";
        try {
            BigDecimal b = new BigDecimal(num);
            BigDecimal f1 = b.setScale(len, BigDecimal.ROUND_FLOOR);
            strTemp = f1.toPlainString();
        } catch (Exception ex) {
            strTemp = "";
        }

        return strTemp;
    }

    public static String roundFloor(BigDecimal num, int len) {
        String strTemp = "";
        try {
            BigDecimal f1 = num.setScale(len, BigDecimal.ROUND_FLOOR);
            strTemp = f1.toPlainString();
        } catch (Exception ex) {
            strTemp = "";
        }

        return strTemp;
    }


    public static String roundCeiling(String num, int len) {
        String strTemp = "";
        try {
            BigDecimal b = new BigDecimal(num);
            BigDecimal f1 = b.setScale(len, BigDecimal.ROUND_CEILING);
            strTemp = f1.toPlainString();
        } catch (Exception ex) {
            strTemp = "";
        }

        return strTemp;
    }

    public static String roundCeiling(BigDecimal num, int len) {
        String strTemp = "";
        try {
            BigDecimal f1 = num.setScale(len, BigDecimal.ROUND_CEILING);
            strTemp = f1.toPlainString();
        } catch (Exception ex) {
            strTemp = "";
        }

        return strTemp;
    }

    /**
     * string转换成BigDecimal
     *
     * @param num 字符
     * @return BigDecimal
     */
    public static BigDecimal bigDecimal(String num) {
        if (TextUtils.isEmpty(num)) {
            return BigDecimal.ZERO;
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(num);
            return bigDecimal;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 千分符的数字转成BigDecimal
     *
     * @param nun nun
     * @return BigDecimal
     */
    public static BigDecimal groupToBigDecimal(String nun) {
        if (!TextUtils.isEmpty(nun) && nun.contains(",")) {
            nun = nun.replace(",", "");
        }
        return bigDecimal(nun);
    }

    /**
     * 百分数转BigDecimal
     *
     * @param percent 百分数
     * @return BigDecimal
     */
    public static BigDecimal percentToBigDecimal(String percent) {
        if (TextUtils.isEmpty(percent)) {
            return BigDecimal.ZERO;
        } else {
            if (percent.lastIndexOf("%") == percent.length() - 1) {
                return bigDecimal(percent.substring(0, percent.length() - 1))
                        .divide(BigDecimal.valueOf(100), 32, BigDecimal.ROUND_DOWN);
            } else {
                return BigDecimal.ZERO;
            }
        }
    }

    /**
     * 获取小数的长度
     *
     * @param balance 数字
     * @return 小数长度
     */
    public static int getNumberDecimalDigits(String balance) {
        int dcimalDigits = -1;
        if (TextUtils.isEmpty(balance)) {
            return dcimalDigits;
        }
        String balanceStr = new BigDecimal(balance).toString();
        int indexOf = balanceStr.indexOf('.');
        if (indexOf > 0) {
            dcimalDigits = balanceStr.length() - 1 - indexOf;
        }
        return dcimalDigits;
    }


    /**
     * 按照格式转换数字
     *
     * @param num 传入的数据
     * @param totalLen 小数，小数点，整数部分的总和加起来最长长度
     * @param maxFractionalLen 小数部分的最长长度
     * @return 转换后的数字
     */
    public static String roundFormat(String num, int totalLen, int maxFractionalLen) {
        String result = "";
        if (TextUtils.isEmpty(num)) {
            return result;
        }

        int indexOf = num.indexOf('.');
        int size = totalLen - maxFractionalLen - 1;
        if (indexOf <= -1) {  //说明是整数
            int len = num.length();
            if (len >= totalLen) {
                return num;
            }
            size = totalLen - len - 1;
            if (size < maxFractionalLen) {
                result = formatNumberDown(num, size);
            } else {
                result = formatNumberDown(num, maxFractionalLen);
            }
        } else if (indexOf >= (totalLen - 1)) {//超过长度totalLen，变成整数
            result = formatNumberDown(num, 0);
        } else if (indexOf <= size) {//小数部分按照maxFractionalLen补齐
            result = formatNumberDown(num, maxFractionalLen);
        } else if (indexOf < (totalLen - 1)) {//小数部分按照totalLen补齐
            result = formatNumberDown(num, totalLen - 1 - indexOf);
        }

        return result;
    }

    /**
     * 买卖盘的数量折合
     *
     * @param amount 数量
     */
    public static String getTradeMarketAmount(String amount) {
        String result = "";
        if (TextUtils.isEmpty(amount)) {
            return result;
        }
        BigDecimal amountBigDecimal = new BigDecimal(amount);

        if (amountBigDecimal.compareTo(new BigDecimal("1000000000")) >= 0) {
            BigDecimal tAmountBigDecimal = amountBigDecimal.divide(new BigDecimal(1000000000));
            String tAmoutString = tAmountBigDecimal.toPlainString();
            //判断除十亿后的数字长度是否大于5位
            if (tAmoutString.length() > 5) {
                String sub = tAmoutString.substring(0, 5);
                //如果截取前五位后的数值最后一位是"."，则只截取前四位
                if (sub.endsWith(".")) {
                    result = tAmoutString.substring(0, 4) + "B";
                } else {
                    result = tAmoutString.substring(0, 5) + "B";
                }
            } else {
                result = tAmountBigDecimal.toPlainString() + "B";
            }
        } else if (amountBigDecimal.compareTo(new BigDecimal("1000000")) >= 0
                && amountBigDecimal.compareTo(new BigDecimal("1000000000")) < 0) {
            BigDecimal tAmountBigDecimal = amountBigDecimal.divide(new BigDecimal(1000000));
            String tAmoutString = tAmountBigDecimal.toPlainString();
            //判断除百万后的数字长度是否大于5位
            if (tAmoutString.length() > 5) {
                String sub = tAmoutString.substring(0, 5);
                //如果截取前5位后的数值最后一位是"."，则只截取前4位
                if (sub.endsWith(".")) {
                    result = tAmoutString.substring(0, 4) + "M";
                } else {
                    result = tAmoutString.substring(0, 5) + "M";
                }
            } else {
                result = tAmountBigDecimal.toPlainString() + "M";
            }
        } else if (amountBigDecimal.compareTo(new BigDecimal("1000")) >= 0
                && amountBigDecimal.compareTo(new BigDecimal("1000000")) < 0) {
            BigDecimal tAmountBigDecimal = amountBigDecimal.divide(new BigDecimal(1000));
            String tAmoutString = tAmountBigDecimal.toPlainString();
            //判断除千后的数字长度是否大于5位
            if (tAmoutString.length() > 5) {
                String sub = tAmoutString.substring(0, 5);
                //如果截取前5位后的数值最后一位是"."，则只截取前4位
                if (sub.endsWith(".")) {
                    result = tAmoutString.substring(0, 4) + "K";
                } else {
                    result = tAmoutString.substring(0, 5) + "K";
                }
            } else {
                result = tAmountBigDecimal.toPlainString() + "K";
            }
        } else if (amountBigDecimal.compareTo(new BigDecimal("1000")) < 0
                && amountBigDecimal.compareTo(BigDecimal.ONE) >= 0) {
            //数量在1到1000之间
            String tAmoutString = amountBigDecimal.toPlainString();
            //超过6位直接截取前6位，否者直接返回
            if (tAmoutString.length() > 6) {
                result = tAmoutString.substring(0, 6);
            } else {
                result = tAmoutString;
            }
        } else {//小于1的数量
            String tAmountString = amountBigDecimal.toPlainString();
            if (tAmountString.length() > 6) {
                result = tAmountString.substring(0, 6);
            } else {
                result = tAmountString;
            }
        }
        return result;
    }

    /**
     * 买卖盘的数量折合
     *
     * @param amount 数量
     * @param count 精度
     */
    public static String getTradeMarketSpotMarginAmount(String amount, int count) {
        String result = "";
        if (TextUtils.isEmpty(amount)) {
            return result;
        }
        BigDecimal amountBigDecimal = new BigDecimal(amount);

        if (amountBigDecimal.compareTo(new BigDecimal("1000000000")) >= 0) {
            BigDecimal tAmountBigDecimal = amountBigDecimal.divide(new BigDecimal(1000000000));
            String tAmountString = tAmountBigDecimal.toPlainString();
            double tAmountDouble = Double.parseDouble(tAmountString);
            result = CommonNumberUtil
                    .formatNumberDown(tAmountDouble, count) + "B";
        } else if (amountBigDecimal.compareTo(new BigDecimal("1000000")) >= 0
                && amountBigDecimal.compareTo(new BigDecimal("1000000000")) < 0) {
            BigDecimal tAmountBigDecimal = amountBigDecimal.divide(new BigDecimal(1000000));
            String tAmountString = tAmountBigDecimal.toPlainString();
            double tAmountDouble = Double.parseDouble(tAmountString);
            result = CommonNumberUtil
                    .formatNumberDown(tAmountDouble, count) + "M";
        } else if (amountBigDecimal.compareTo(new BigDecimal("1000")) >= 0
                && amountBigDecimal.compareTo(new BigDecimal("1000000")) < 0) {
            BigDecimal tAmountBigDecimal = amountBigDecimal.divide(new BigDecimal(1000));
            String tAmountString = tAmountBigDecimal.toPlainString();
            double tAmountDouble = Double.parseDouble(tAmountString);
            result = CommonNumberUtil
                    .formatNumberDown(tAmountDouble, count) + "K";
        } else {
            String tAmountString = amountBigDecimal.toPlainString();
            double tAmountDouble = Double.parseDouble(tAmountString);
            result = CommonNumberUtil.formatNumberDown(tAmountDouble, count);
        }
        return result;
    }

    /**
     * 数字格式化显示：
     *
     * <pre>
     * 1000.10 --> 1000.1
     * 1000.00 --> 1000
     * 9999.99 --> 9999.99
     * 10000.00 --> 1万
     * 11000.00 --> 1.1万
     * 10999.99 --> 1.09万
     * 100000.99 --> 10万
     * 109999.99 --> 10.99万
     * 1000000.99 --> 100万
     * 1099999.99 --> 109.99万
     * 10000000.99 --> 1000万
     * 10999999.99 --> 1099.99万
     * 100000000.99 --> 1亿
     * 109999999.99 --> 1.09亿
     * 199999999.99 --> 1.99亿
     * 1000000000.99 --> 10亿
     * 1099999999.99 --> 10.99亿
     * 1999999999.99 --> 19.99亿
     * 10000000000.99 --> 100亿
     * 10999999999.99 --> 109.99亿
     * 19999999999.99 --> 199.99亿
     * 100000000000.99 --> 1000亿
     * 109999999999.99 --> 1099.99亿
     * 199999999999.99 --> 1999.99亿
     * 1000000000000.99 --> 10000亿
     * 1099999999999.99 --> 10999.99亿
     * </pre>
     *
     * @param input 格式化的数字
     */
    public static String formatNumWithCnyUnit(String input) {
        // 判空
        if (input == null || "".equals(input.trim())) {
            return "--";
        }

        try {
            // 去掉逗号
            input = input.replaceAll(",", "");

            // 判断是否是数字
            if (!input.matches("^(-?\\d+)(\\.\\d+)?$")) {
                return "0";
            }

            BigDecimal bInput = new BigDecimal(input).setScale(2, BigDecimal.ROUND_DOWN);

            // 一万
            BigDecimal b1Wan = new BigDecimal("10000");
            // 一亿
            BigDecimal b1Yi = new BigDecimal("100000000");

            String number;
            String unit = "";

            // 以万为单位处理
            if (bInput.compareTo(b1Wan) < 0) {
                // 小于一万
                number = bInput.toPlainString();
            } else if (bInput.compareTo(b1Yi) < 0) {
                // 大于1万 && 小于一亿
                number = bInput.divide(b1Wan, 2, BigDecimal.ROUND_DOWN).toString();
                unit = "万";
            } else {
                // 大于一亿
                number = bInput.divide(b1Yi, 2, BigDecimal.ROUND_DOWN).toString();
                unit = "亿";
            }

            if (number.length() == 0) {
                return "0";
            }

            // 去掉多余的0
            number = new BigDecimal(number).stripTrailingZeros().toPlainString();
            number = formatGroup(number, number);
            return number + unit;
        } catch (Exception e) {
            return "0";
        }
    }


    /**
     * 成交量＜1按交易精度展示，小数点后末尾的0舍弃 成交量＜100按交易精度展示，小数点后末尾的0舍弃 成交量<10,000: 最大取两位小数展示，例如：779.01，9999。小数点后末尾的0舍弃
     * 成交量≥10,000: 单位用K，(K=千) 成交量≥1,000,000 单位用M，（M=百万）
     *
     * @param value 数值
     * @param precision 精度
     * @return 格式化后的数值
     */
    public static String formatVolValue(String value, int precision) {

        if (TextUtils.isEmpty(value)) {
            return value;
        } else {
            try {
                BigDecimal bigDecimal = new BigDecimal(value);
                //如果是0直接返回0
                if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                    return "0";
                } else if (bigDecimal.compareTo(new BigDecimal(100)) < 0) {
                    return bigDecimal.setScale(precision, BigDecimal.ROUND_DOWN)
                            .stripTrailingZeros().toPlainString();
                } else if (bigDecimal.compareTo(new BigDecimal(10000)) < 0) {
                    if (precision > 2) {
                        return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros()
                                .toPlainString();
                    } else {
                        return bigDecimal.setScale(precision, BigDecimal.ROUND_DOWN)
                                .stripTrailingZeros().toPlainString();
                    }
                } else if (bigDecimal.compareTo(new BigDecimal(1000000)) < 0) {
                    return bigDecimal.divide(new BigDecimal(1000))
                            .setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()
                            + "K";
                } else if (bigDecimal.compareTo(new BigDecimal(1000000000)) < 0) {
                    return bigDecimal.divide(new BigDecimal(1000000))
                            .setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()
                            + "M";
                } else {
                    return bigDecimal.divide(new BigDecimal(1000000000))
                            .setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()
                            + "B";
                }

            } catch (Exception e) {
                return value;
            }
        }


    }


    /**
     * 转带符号百分数
     *
     * @param ratio 转换的数字
     * @param precisionNum 截断位数
     * @return 转带符号百分数
     */
    public static String formatSignPct(double ratio, int precisionNum) {
        String sign = ratio > 0 ? "+" : "";
        String unit = "%";
        String pctValue = formatNumberDown(BigDecimal.valueOf(ratio).multiply(new BigDecimal(100)),
                precisionNum);
        return sign + pctValue + unit;
    }

    public static String formatPct(double ratio, int precisionNum) {
        String unit = "%";
        String pctValue = formatNumberDown(BigDecimal.valueOf(ratio).multiply(new BigDecimal(100)),
                precisionNum);
        return pctValue + unit;
    }

    /**
     * 转百分数去除末尾的零
     *
     * @param ratio BigDecimal类型的数
     * @return 百分数格式化
     */
    public static String formatPctExcludeEndZero(BigDecimal ratio) {
        BigDecimal origin = ratio == null ? BigDecimal.ZERO : ratio;
        String unit = "%";
        String pctValue = origin.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
        return pctValue + unit;
    }

    /**
     * 转百分数 （此方法存在-0.000001截断之后变成0.00，负号丢失）
     *
     * @param ratio 转换的数字
     * @param precision 截断的位数
     * @see CommonNumberUtil {@link CommonNumberUtil#formatPercent}
     */
    @Deprecated
    public static String formatPct(String ratio, int precision, int roundMode) {
        String unit = "%";
        String pctValue = CommonNumberUtil.bigDecimal(ratio).multiply(new BigDecimal(100))
                .setScale(precision, roundMode).toPlainString();
        return pctValue + unit;
    }

    /**
     * 转百分数
     *
     * @param ratio 转换的数字
     * @param precision 截断的位数
     * @param roundMode 截断类型
     * @return 转换后的数字
     */
    public static String formatPercent(String ratio, int precision, int roundMode) {
        String unit = "%";
        BigDecimal decimal = CommonNumberUtil.bigDecimal(ratio);
        BigDecimal percentDecimal = decimal.multiply(new BigDecimal(100))
                .setScale(precision, roundMode);
        String pctValue;
        //原始数据是负数，转换后的数据不是负数，则转换后的数据需要加上负号
        if (percentDecimal.signum() != 0 && percentDecimal.signum() != -1
                && decimal.signum() == -1) {
            pctValue = "-" + percentDecimal.toPlainString();
        } else {
            pctValue = percentDecimal.toPlainString();
        }

        return pctValue + unit;
    }

    /**
     * 转百分数
     *
     * @param ratio 转换的数字
     * @param precision 截断的位数
     * @param roundMode 截断类型
     * @param defaultTxt 默认文案
     * @return 转换后的数字
     */
    public static String formatPercent(String ratio, int precision, int roundMode,
            String defaultTxt) {
        if (TextUtils.isEmpty(ratio)) {
            return defaultTxt;
        }
        return formatPercent(ratio, precision, roundMode);
    }

    /**
     * 转百分数
     *
     * @param ratio 转换的数字
     * @param precision 截断的位数
     */
    public static String formatPct(BigDecimal ratio, int precision, int roundMode) {
        String unit = "%";
        String pctValue = ratio.multiply(new BigDecimal(100))
                .setScale(precision, roundMode).toPlainString();
        return pctValue + unit;
    }

    /**
     * 向下取整
     *
     * @param number BigDecimal
     * @param decimalCount 小数位
     * @param separateThousands 是否使用分组
     * @param defaultValue 默认值
     * @return 向下取整后的数
     */
    public synchronized static String formatNumberDown(BigDecimal number, int decimalCount,
            boolean separateThousands, String defaultValue) {
        try {
            if (decimalCount != INVALID_INT) {
                nf.setMinimumFractionDigits(decimalCount);
            }
            if (decimalCount != INVALID_INT) {
                nf.setMaximumFractionDigits(decimalCount);
            }
            nf.setRoundingMode(RoundingMode.DOWN);
            nf.setGroupingUsed(separateThousands);
            return nf.format(number);
        } catch (Exception ignored) {
            return defaultValue;
        }

    }

    /**
     * 向下取整,兼容-0的情况
     * <p>
     * Examples:number=-0.000096159998292173,decimalCount=4,返回-0.0000；而上面那个方法返回0.0000
     *
     * @param number BigDecimal
     * @param decimalCount 小数位
     * @param separateThousands 是否使用分组
     * @param defaultValue 默认值
     * @return 向下取整后的数
     */
    public synchronized static String formatNumberDownCompatNegative0(BigDecimal number,
            int decimalCount,
            boolean separateThousands, String defaultValue) {
        try {
            if (decimalCount != INVALID_INT) {
                nf.setMinimumFractionDigits(decimalCount);
                nf.setMaximumFractionDigits(decimalCount);
            }
            nf.setRoundingMode(RoundingMode.DOWN);
            nf.setGroupingUsed(separateThousands);
            String format = nf.format(number);
            boolean neg = number.compareTo(BigDecimal.ZERO) < 0;
            if (neg && !format.startsWith("-")) {
                format = "-" + format;
            }
            return format;
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static String formatNumberDown(BigDecimal decimal, int decimalCount,
            boolean separateThousands) {
        return formatNumberDown(decimal, decimalCount, separateThousands, "");
    }

    public static String formatNumberDown(BigDecimal decimal, int decimalCount,
            String defaultValue) {
        return formatNumberDown(decimal, decimalCount, false, defaultValue);
    }

    public static String formatNumberDown(BigDecimal decimal, int decimalCount) {
        return formatNumberDown(decimal, decimalCount, false);
    }

    public static String formatNumberDown(double number, int decimalCount,
            boolean separateThousands, String defaultValue) {
        try {
            return formatNumberDown(String.valueOf(number), decimalCount, separateThousands,
                    defaultValue);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static String formatNumberDown(double number, int decimalCount, String defaultValue) {
        return formatNumberDown(number, decimalCount, false, defaultValue);
    }

    public static String formatNumberDown(double number, int decimalCount,
            boolean separateThousands) {
        return formatNumberDown(number, decimalCount, separateThousands, "");
    }

    public static String formatNumberDown(double number, int decimalCount) {
        return formatNumberDown(number, decimalCount, false, "");
    }

    public static String formatNumberDown(String number, int decimalCount,
            boolean separateThousands, String defaultValue) {
        try {
            return formatNumberDown(new BigDecimal(number), decimalCount, separateThousands,
                    defaultValue);
        } catch (Exception e) {
            return formatNumberDown(BigDecimal.ZERO, decimalCount, separateThousands, defaultValue);
        }
    }

    public static String formatNumberDown(String number, int decimalCount,
            boolean separateThousands) {
        return formatNumberDown(number, decimalCount, separateThousands, "");
    }

    public static String formatNumberDown(String number, int decimalCount, String defaultValue) {
        return formatNumberDown(number, decimalCount, false, defaultValue);
    }

    public static String formatNumberDown(String number, int decimalCount) {
        return formatNumberDown(number, decimalCount, false);
    }

    /**
     * -0.000000001时，截取8位小数，需要处理成-0.00000000
     *
     * @param number 数字
     * @param decimalCount 小数位
     * @return 截断结果
     */
    public static String formatNumberDownKeepSign(String number, int decimalCount) {
        String s = formatNumberDown(number, decimalCount);
        if (CommonNumberUtil.bigDecimal(number).compareTo(BigDecimal.ZERO) < 0
                && CommonNumberUtil.bigDecimal(s).compareTo(BigDecimal.ZERO) == 0) {
            s = "-" + s;
        }
        return s;
    }

    /**
     * -0.000000001时，截取8位小数，需要处理成-0.00000000
     *
     * @param number 数字
     * @param decimalCount 小数位
     * @return 截断结果
     */
    public static String formatNumberDownKeepSign(String number,
            int decimalCount, boolean separateThousands) {
        String s = formatNumberDown(number, decimalCount, separateThousands);
        if (CommonNumberUtil.bigDecimal(number).compareTo(BigDecimal.ZERO) < 0
                && CommonNumberUtil.bigDecimal(formatNumberDown(number, decimalCount))
                .compareTo(BigDecimal.ZERO) == 0) {
            s = "-" + s;
        }
        return s;
    }


    /**
     * -0.000000001时，截取8位小数，需要处理成-0.00000000  四舍五入
     *
     * @param number 数字
     * @param decimalCount 小数位
     * @return 截断结果
     */
    public static String formatNumberHalfUpKeepSign(String number, int decimalCount) {
        String s = formatNumberHalfUp(number, decimalCount);
        if (CommonNumberUtil.bigDecimal(number).compareTo(BigDecimal.ZERO) < 0
                && CommonNumberUtil.bigDecimal(s).compareTo(BigDecimal.ZERO) == 0) {
            s = "-" + s;
        }
        return s;
    }

    /**
     * 向上取整
     *
     * @param number BigDecimal
     * @param decimalCount 小数位
     * @param separateThousands 是否使用分组
     * @param defaultValue 默认值
     * @return 向上取整后的数
     */
    public synchronized static String formatNumberUp(BigDecimal number, int decimalCount,
            boolean separateThousands, String defaultValue) {
        try {
            if (decimalCount != INVALID_INT) {
                nf.setMinimumFractionDigits(decimalCount);
            }
            if (decimalCount != INVALID_INT) {
                nf.setMaximumFractionDigits(decimalCount);
            }
            nf.setRoundingMode(RoundingMode.UP);
            nf.setGroupingUsed(separateThousands);
            return nf.format(number);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static String formatNumberUp(BigDecimal decimal, int decimalCount,
            boolean separateThousands) {
        return formatNumberUp(decimal, decimalCount, separateThousands, "");
    }

    public static String formatNumberUp(BigDecimal decimal, int decimalCount, String defaultValue) {
        return formatNumberUp(decimal, decimalCount, false, defaultValue);
    }

    public static String formatNumberUp(BigDecimal decimal, int decimalCount) {
        return formatNumberUp(decimal, decimalCount, false);
    }


    public static String formatNumberUp(double number, int decimalCount, boolean separateThousands,
            String defaultValue) {
        return formatNumberUp(String.valueOf(number), decimalCount, separateThousands,
                defaultValue);
    }

    public static String formatNumberUp(double number, int decimalCount, String defaultValue) {
        return formatNumberUp(number, decimalCount, false, defaultValue);
    }

    public static String formatNumberUp(double number, int decimalCount,
            boolean separateThousands) {
        return formatNumberUp(number, decimalCount, separateThousands, "");
    }

    public static String formatNumberUp(double number, int decimalCount) {
        return formatNumberUp(number, decimalCount, false);
    }

    public static String formatNumberUp(String number, int decimalCount, boolean separateThousands,
            String defaultValue) {
        try {
            return formatNumberUp(new BigDecimal(number), decimalCount, separateThousands,
                    defaultValue);
        } catch (Exception e) {
            return formatNumberUp(BigDecimal.ZERO, decimalCount, separateThousands, defaultValue);
        }
    }

    public static String formatNumberUp(String number, int decimalCount,
            boolean separateThousands) {
        return formatNumberUp(number, decimalCount, separateThousands, "");
    }

    public static String formatNumberUp(String number, int decimalCount, String defaultValue) {
        return formatNumberUp(number, decimalCount, false, defaultValue);
    }

    public static String formatNumberUp(String number, int decimalCount) {
        return formatNumberUp(number, decimalCount, false);
    }

    public static String formatNumberUpTripZero(String number, int decimalCount) {
        String s = formatNumberUp(number, decimalCount, false);
        boolean numeric = isNumeric(s);
        if (numeric) {
            s = new BigDecimal(s).stripTrailingZeros().toPlainString();
        }
        return s;
    }

    /**
     * 四舍五入
     *
     * @param number BigDecimal
     * @param decimalCount 小数位数
     * @param separateThousands 是否分组
     * @param defaultValue 默认值
     * @return 四舍五入后的数
     */
    public synchronized static String formatNumberHalfUp(BigDecimal number, int decimalCount,
            boolean separateThousands, String defaultValue) {
        try {
            if (decimalCount != INVALID_INT) {
                nf.setMinimumFractionDigits(decimalCount);
            }
            if (decimalCount != INVALID_INT) {
                nf.setMaximumFractionDigits(decimalCount);
            }
            nf.setRoundingMode(RoundingMode.HALF_UP);
            nf.setGroupingUsed(separateThousands);
            return nf.format(number);
        } catch (Exception ignored) {
            return defaultValue;
        }
    }

    public static String formatNumberHalfUp(BigDecimal decimal, int decimalCount,
            boolean separateThousands) {
        return formatNumberHalfUp(decimal, decimalCount, separateThousands, "");
    }

    public static String formatNumberHalfUp(BigDecimal decimal, int decimalCount,
            String defaultValue) {
        return formatNumberHalfUp(decimal, decimalCount, false, defaultValue);
    }

    public static String formatNumberHalfUp(BigDecimal decimal, int decimalCount) {
        return formatNumberHalfUp(decimal, decimalCount, false);
    }

    public static String formatNumberHalfUp(double number, int decimalCount,
            boolean separateThousands, String defaultValue) {
        return formatNumberHalfUp(String.valueOf(number), decimalCount, separateThousands,
                defaultValue);
    }

    public static String formatNumberHalfUp(double number, int decimalCount, String defaultValue) {
        return formatNumberHalfUp(number, decimalCount, false, defaultValue);
    }

    public static String formatNumberHalfUp(double number, int decimalCount,
            boolean separateThousands) {
        return formatNumberHalfUp(number, decimalCount, separateThousands, "");
    }

    public static String formatNumberHalfUp(double number, int decimalCount) {
        return formatNumberHalfUp(number, decimalCount, false, "");
    }

    public static String formatNumberHalfUp(String number, int decimalCount,
            boolean separateThousands, String defaultValue) {
        try {
            return formatNumberHalfUp(new BigDecimal(number), decimalCount, separateThousands,
                    defaultValue);
        } catch (Exception e) {
            return formatNumberHalfUp(BigDecimal.ZERO, decimalCount, separateThousands,
                    defaultValue);
        }
    }

    public static String formatNumberHalfUp(String number, int decimalCount,
            boolean separateThousands) {
        return formatNumberHalfUp(number, decimalCount, separateThousands, "");
    }

    public static String formatNumberHalfUp(String number, int decimalCount, String defaultValue) {
        return formatNumberHalfUp(number, decimalCount, false, defaultValue);
    }

    public static String formatNumberHalfUp(String number, int decimalCount) {
        return formatNumberHalfUp(number, decimalCount, false);
    }


    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    public static int parseInt(String str, int defaultV) {
        if (str == null || str.length() == 0) {
            return defaultV;
        }

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.toString());

        }
        return defaultV;
    }

    /**
     * 解析指数的 参数 ， 为0或者空都是1
     *
     * @param str 参数
     * @return 转化成数字
     */
    public static int parseIndexParamsInt(String str) {
        if (str == null || str.length() == 0) {
            return 1;
        }

        try {
            int v = Integer.parseInt(str);
            return v == 0 ? 1 : v;
        } catch (NumberFormatException e) {

        }
        return 1;
    }

    public static long parseLong(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    public static float parseFloat(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage() + "");

        }
        return 0;
    }

    public static double parseDouble(String str) {
        if (str == null || str.length() == 0) {
            return 0.0;
        }

        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.toString());

        }
        return 0.0;
    }

    /**
     * 在某些平台出现处理过后字符串还会显示位数超出的情况，因此需要截断
     *
     * @param originDec 小数
     * @param fractionDigitLength 小数位数
     * @return 处理过后的小数
     */
    public static String getSafeHandleDec(String originDec, int fractionDigitLength) {
        if (originDec == null) {
            return "";
        }
        int dotPos = originDec.indexOf(".");
        return dotPos == -1 ? originDec : originDec.substring(0,
                dotPos + fractionDigitLength + 1 > originDec.length() ? originDec.length()
                        : dotPos + fractionDigitLength + 1);
    }

    /**
     * 去除多余的0
     *
     * @param number 输入
     * @return 输出
     */
    public static String removeZero(String number) {
        return bigDecimal(number).stripTrailingZeros().toPlainString();
    }

    /**
     * 开n次方根号
     *
     * @param number 原数
     * @param n 几次方
     * @param scale 精度
     * @param roundingMode 取整方式
     * @return 结果
     */
    public static BigDecimal getBigRoot(BigDecimal number, int n, int scale, int roundingMode) {
        boolean negate = false;
        if (n < 0) {
            throw new ArithmeticException();
        }
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            if (n % 2 == 0) {
                throw new ArithmeticException();
            } else {
                number = number.negate();
                negate = true;
            }
        }

        BigDecimal root;

        if (n == 0) {
            root = BigDecimal.ONE;
        } else if (n == 1) {
            root = number;
        } else {
            final BigInteger tempN = BigInteger.valueOf(n);
            final BigInteger tempN2 = BigInteger.TEN.pow(n);
            final BigInteger tempN3 = BigInteger.TEN.pow(n - 1);
            final BigInteger tempNine = BigInteger.valueOf(9);

            BigInteger[] tempC = new BigInteger[n + 1];
            for (int i = 0; i <= n; i++) {
                tempC[i] = combination(n, i);
            }

            BigInteger integer = number.toBigInteger();
            String strInt = integer.toString();
            int lenInt = strInt.length();
            for (int i = lenInt % n; i < n && i > 0; i++) {
                strInt = "0" + strInt;
            }
            lenInt = (lenInt + n - 1) / n * n;
            BigDecimal fraction = number.subtract(number.setScale(0, BigDecimal.ROUND_DOWN));
            int lenFrac = (fraction.scale() + n - 1) / n * n;
            fraction = fraction.movePointRight(lenFrac);
            String strFrac = fraction.toPlainString();
            for (int i = strFrac.length(); i < lenFrac; i++) {
                strFrac = "0" + strFrac;
            }

            BigInteger res = BigInteger.ZERO;
            BigInteger rem = BigInteger.ZERO;
            for (int i = 0; i < lenInt / n; i++) {
                rem = rem.multiply(tempN2);

                BigInteger temp = new BigInteger(strInt.substring(i * n, i * n + n));
                rem = rem.add(temp);

                BigInteger j;
                if (res.compareTo(BigInteger.ZERO) != 0) {
                    j = rem.divide(res.pow(n - 1).multiply(tempN).multiply(tempN3));
                } else {
                    j = tempNine;
                }
                BigInteger test = BigInteger.ZERO;
                temp = res.multiply(BigInteger.TEN);
                while (j.compareTo(BigInteger.ZERO) >= 0) {
                    //test = res.multiply(BigInteger.TEN);
                    //test = ((test.add(j)).pow(n)).subtract(test.pow(n));
                    test = BigInteger.ZERO;
                    if (j.compareTo(BigInteger.ZERO) > 0) {
                        for (int k = 1; k <= n; k++) {
                            test = test.add(j.pow(k).multiply(tempC[k]).multiply(temp.pow(n - k)));
                        }
                    }
                    if (test.compareTo(rem) <= 0) {
                        break;
                    }
                    j = j.subtract(BigInteger.ONE);
                }

                rem = rem.subtract(test);
                res = res.multiply(BigInteger.TEN);
                res = res.add(j);
            }
            for (int i = 0; i <= scale; i++) {
                rem = rem.multiply(tempN2);

                if (i < lenFrac / n) {
                    BigInteger temp = new BigInteger(strFrac.substring(i * n, i * n + n));
                    rem = rem.add(temp);
                }

                BigInteger j;
                if (res.compareTo(BigInteger.ZERO) != 0) {
                    j = rem.divide(res.pow(n - 1).multiply(tempN).multiply(tempN3));
                } else {
                    j = tempNine;
                }
                BigInteger test = BigInteger.ZERO;
                BigInteger temp = res.multiply(BigInteger.TEN);
                while (j.compareTo(BigInteger.ZERO) >= 0) {
                    //test = res.multiply(BigInteger.TEN);
                    //test = ((test.add(j)).pow(n)).subtract(test.pow(n));
                    test = BigInteger.ZERO;
                    if (j.compareTo(BigInteger.ZERO) > 0) {
                        for (int k = 1; k <= n; k++) {
                            test = test.add(j.pow(k).multiply(tempC[k]).multiply(temp.pow(n - k)));
                        }
                    }
                    if (test.compareTo(rem) <= 0) {
                        break;
                    }
                    j = j.subtract(BigInteger.ONE);
                }

                rem = rem.subtract(test);
                res = res.multiply(BigInteger.TEN);
                res = res.add(j);
            }
            root = new BigDecimal(res).movePointLeft(scale + 1);
            if (negate) {
                root = root.negate();
            }
        }
        return root.setScale(scale, roundingMode);
    }

    /**
     * 计算
     *
     * @param n n
     * @param k k
     * @return 结果
     */
    public static BigInteger combination(int n, int k) {
        if (k > n || n < 0 || k < 0) {
            return BigInteger.ZERO;
        }
        if (k > n / 2) {
            return combination(n, n - k);
        }
        BigInteger tempN1 = BigInteger.ONE;
        BigInteger tempN2 = BigInteger.ONE;
        BigInteger tempN = BigInteger.valueOf(n);
        BigInteger tempK = BigInteger.valueOf(k);
        for (int i = 0; i < k; i++) {
            tempN1 = tempN1.multiply(tempN);
            tempN2 = tempN2.multiply(tempK);
            tempN = tempN.subtract(BigInteger.ONE);
            tempK = tempK.subtract(BigInteger.ONE);
        }
        return tempN1.divide(tempN2);
    }

    /**
     * 求和
     *
     * @param list 列表
     * @return 结果
     */
    public static BigDecimal sum(List<BigDecimal> list) {
        BigDecimal result = BigDecimal.ZERO;
        if (list == null) {
            return result;
        }
        for (BigDecimal bigDecimal : list) {
            result = result.add(bigDecimal);
        }
        return result;
    }

    /**
     * 判断是否等于零
     *
     * @param decimal 数字
     * @return boolean
     */
    public static boolean isZero(BigDecimal decimal) {
        return BigDecimal.ZERO.compareTo(decimal) == 0;
    }

    /**
     * 等于零判断
     *
     * @param s 数字字符串
     * @return boolean
     */
    public static boolean isZero(String s) {
        return isZero(bigDecimal(s));
    }

    /**
     * 大于比较
     *
     * @param a a
     * @param b b
     * @return boolean
     */
    public static boolean moreThan(BigDecimal a, BigDecimal b) {
        return a != null && a.compareTo(b) > 0;
    }

    /**
     * 大于等于
     *
     * @param a a
     * @param b b
     * @return boolean
     */
    public static boolean moreThanOrEqual(BigDecimal a, BigDecimal b) {
        return a != null && a.compareTo(b) >= 0;
    }

    /**
     * 小于比较
     *
     * @param a a
     * @param b b
     * @return boolean
     */
    public static boolean lessThan(BigDecimal a, BigDecimal b) {
        return a != null && a.compareTo(b) < 0;
    }

    /**
     * 小于等于
     *
     * @param a a
     * @param b b
     * @return boolean
     */
    public static boolean lessThanOrEqual(BigDecimal a, BigDecimal b) {
        return a != null && a.compareTo(b) <= 0;
    }

    /**
     * @param result 收益
     * @param fromat 拼接的字符
     * @param isStatr 是否拼接在正负号前 （+¥100 || ¥+100）
     * @return 根据收益添加+
     */
    public static String getTodayProfit(String result, String fromat, boolean isStatr) {
        if (CommonNumberUtil.isNumeric(result)) {
            boolean isPositive =
                    CommonNumberUtil.bigDecimal(result).compareTo(BigDecimal.ZERO) > 0;
            String addFromat = isPositive ? "+" : "-";
            // 设置 +¥100 / ¥+100
            addFromat = isStatr ? fromat + addFromat : addFromat + fromat;

            if (isPositive) {
                addFromat = addFromat + result;
            } else {
                addFromat = addFromat + CommonNumberUtil.bigDecimal(result).abs().toPlainString();
            }
            return addFromat;
        }
        return result;
    }

    /**
     * @param input 某个小于1的数
     * @return 获取输入数据的倒数，如果大于1，则返回1
     */
    public  static  String getOneDevideInput(String input) {
        String result = "1";
        BigDecimal bgInput = new BigDecimal(input);
        if (bgInput.compareTo(BigDecimal.ZERO) != 0) {
            bgInput = BigDecimal.ONE.divide(bgInput, 32, BigDecimal.ROUND_DOWN);
            bgInput = bgInput.divide(new BigDecimal(100));
            if (bgInput.compareTo(BigDecimal.ONE) > 0) {
                bgInput = BigDecimal.ONE;
            }
            result = bgInput.toPlainString();
        }
        return  result;
    }
}
