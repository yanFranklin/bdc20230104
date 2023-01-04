package cn.gtmap.realestate.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0  2019-05-03
 * @description
 */
public class DoubleUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleUtils.class);


    // 保留两位小数
    public static final DecimalFormat df2 = new DecimalFormat("0.00");


    /**
     * 计算double求和
     * @param doubles
     * @return
     */
    public static Double sum(Double... doubles) {
        LOGGER.info("---double求和参数:{}",doubles);
        List<Double> doubleList = new ArrayList<>();
        for (Double d : doubles){
            // 过滤非数字
            if(Objects.nonNull(d) && !Double.isNaN(d)){
                LOGGER.info("---double有效求和参数:{}",d);
                doubleList.add(d);
            }
        }
        double sum = doubleList.stream().map(t-> new BigDecimal(String.valueOf(t))).reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
        return sum;
    }


    /**
     * Double类型转String类型
     * @param d
     * @param decimalFormat
     * @return
     */
    public static String doubleToString(Double d,DecimalFormat decimalFormat){
        if(Objects.isNull(d) || Double.isNaN(d)){
            return "";
        }
        if(Objects.isNull(decimalFormat)){
            BigDecimal bd = new BigDecimal(d);
            return bd.toString();
        }
        return decimalFormat.format(d);
    }

    /**
     * double 转 string 去掉后面锝0
     *
     * @param i
     * @return
     */
    public static String getString(double i) {
        if(Objects.isNull(i)){
            return "";
        }
        String s = String.valueOf(i);
        if (s.indexOf(".") > 0) {
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s;
    }
}
