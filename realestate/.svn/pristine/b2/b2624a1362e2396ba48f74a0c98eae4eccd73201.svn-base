package cn.gtmap.realestate.accept.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2017-12-5
 * @description 公用方法
 */
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    public static String showLocalDate(){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat.format(System.currentTimeMillis());
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrDay() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat(Constants.DATE_NYR);
        return format.format(date);
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrYearMonth() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat(Constants.DATE_NY);
        return format.format(date);
    }

    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrYearYear() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat(Constants.DATE_N);
        return format.format(date);
    }


    /**
     * @return 年月日
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据当天获取年月日格式字符串
     */
    public static String getCurrYearMonthDay(Date date, String dyr) {
        if (date == null) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(dyr);
        return format.format(date);
    }


    /**
     * @return float qlbl
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取权利比例 数字类型
     */
    public static float getQlbr(String qlbl) {
        float qlblRes = 0f;
        if(StringUtils.isEmpty(qlbl)){
            return qlblRes;
        }
        if(qlbl.indexOf("%") != -1){// 包含% 例如“87.23%”
            // 去掉%
            String qlblTemp = qlbl.replace("%","");
            qlblRes = Float.parseFloat(qlblTemp)/100;
        }else if(qlbl.indexOf("/") != -1){// 不包含%的直接转成float
            float fz = Float.parseFloat(qlbl.split("/")[0]);
            float fm = Float.parseFloat(qlbl.split("/")[1]);
            qlblRes = fz/fm;
            DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String bl = decimalFormat.format(qlblRes);//format 返回的是字符串
            qlblRes = Float.parseFloat(bl);
        }else{
            qlblRes = Float.parseFloat(qlbl);
        }
        return qlblRes;
    }


    /**
     * @return String qlbl
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取权利比例 字符串类型（小数）
     */
    public static String getQlbrStr(String qlbl) {
        if(StringUtils.isEmpty(qlbl)){
            return "";
        }
        String bl = "";
        if(qlbl.indexOf("%") != -1){// 包含% 例如“87.23%”
            // 去掉%
            String qlblTemp = qlbl.replace("%","");
            float qlblRes = Float.parseFloat(qlblTemp)/100;
            DecimalFormat decimalFormat=new DecimalFormat("0.0000");//构造方法的字符格式这里如果小数不足4位,会以0补足.
            bl = decimalFormat.format(qlblRes);//format 返回的是字符串
        }else if(qlbl.indexOf("/") != -1){// 不包含%的直接转成float
            float fz = Float.parseFloat(qlbl.split("/")[0]);
            float fm = Float.parseFloat(qlbl.split("/")[1]);
            float qlblRes = fz/fm;
            DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足4位,会以0补足.
            bl = decimalFormat.format(qlblRes);//format 返回的是字符串
        }else{
            bl = qlbl;//format 返回的是字符串
        }
        return bl;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 将json对象内容赋值给目标对象
     */
    public static void cloneJsonObject(JSONObject jsonObject, Object targetObject){
        Field[] fields = targetObject.getClass().getDeclaredFields();
        try {
            for (Field targetField : fields) {
                if (jsonObject.get(targetField.getName()) != null) {
                    targetField.setAccessible(true);
                    targetField.set(targetObject, jsonObject.get(targetField.getName()));
                }

            }
        } catch (IllegalAccessException e){
            LOGGER.error(e.getMessage(), e);



        }
    }

}
