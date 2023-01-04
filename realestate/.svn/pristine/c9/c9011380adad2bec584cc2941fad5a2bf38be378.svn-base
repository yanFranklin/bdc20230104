package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;

import java.math.BigDecimal;

/**
 * @program: realestate
 * @description: 数字格式化处理
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-07-08 09:08
 **/
public class NumberUtil {

    /**
     * 采用四舍五入方式对小数位进行格式化
     * @param num           需要格式化的值
     * @param decimalPlace  格式化位数
     * @return double       格式化后的值
     */
    public static double formatDigit(double num, int decimalPlace) {
        if (decimalPlace <= 0) {
            throw new IllegalArgumentException("格式化位数不能小于0");
        }
        BigDecimal formatData = new BigDecimal(String.valueOf(num)).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return formatData.doubleValue();
    }

    //double 相加
    public static double add(double num1, double num2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return formatDigit(b1.add(b2).doubleValue(), scale);
    }

    //double 相减 scale 表示精度
    public static double subtract(double num1, double num2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return formatDigit(b1.subtract(b2).doubleValue(), scale);
    }

    //double 相乘
    public static double multiply(double num1, double num2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return formatDigit(b1.multiply(b2).doubleValue(), scale);
    }

    //double 相除
    public static double divide(double num1, double num2, int scale) {
        if (num2 == 0) {
            throw new AppException("被除数不能为0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return formatDigit(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue(), scale);
    }


//    //计算double精度丢失问题
//    public static double formatDigit(double num, int decimalPlace) {
//        DecimalFormat fm = null;
//        switch (decimalPlace) {
//            case 0:
//                fm = new DecimalFormat("##");
//                break;
//            case 1:
//                fm = new DecimalFormat("##.#");
//                break;
//            case 2:
//                fm = new DecimalFormat("##.##");
//                break;
//            case 3:
//                fm = new DecimalFormat("##.###");
//                break;
//            case 4:
//                fm = new DecimalFormat("##.####");
//                break;
//            default:
//                break;
//        }
//
//        if (fm == null) {
//            return num;
//        }
//
//        StringBuffer sbf = new StringBuffer();
//        //四舍五入
//        fm.setRoundingMode(HALF_UP);
//        fm.format(num, sbf, new FieldPosition(java.text.NumberFormat.FRACTION_FIELD));
//        return Double.parseDouble(sbf.toString());
//    }

}
