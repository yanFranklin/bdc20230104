package cn.gtmap.realestate.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

/**
 * @program: realestate
 * @description: 身份证号码验证工具
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-06-18 17:17
 **/
public class IDCardUtils {

    /**
     * 判断是否成年
     *
     * @param str
     * @return
     */
    public static boolean isAdult(String str) {
        if (StringUtils.isEmpty(str) || (15 != str.length() && 18 != str.length())) {
            return false;
        } else {
            String birthday = str.substring(6, str.length() - 4);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(birthday.substring(0, 4)) + 18,
                    Integer.parseInt(birthday.substring(4, 6)) - 1,
                    Integer.parseInt(birthday.substring(6)), 23, 59);
            long l = System.currentTimeMillis() - calendar.getTimeInMillis();
            return 0 <= l;
        }
    }

    /**
     * @param zjh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断性别
     * @date : 2022/5/17 8:38
     */
    public static Integer getSex(String zjh) {
        if (StringUtils.isNotBlank(zjh) && zjh.length() != 18 && zjh.length() != 15) {
            return 3;
        }
        int gender = 0;
        if (zjh.length() == 18) {
            //如果身份证号18位，取身份证号倒数第二位
            char c = zjh.charAt(zjh.length() - 2);
            gender = Integer.parseInt(String.valueOf(c));
        }
        if (zjh.length() == 15) {
            //如果身份证号15位，取身份证号最后一位
            char c = zjh.charAt(zjh.length() - 1);
            gender = Integer.parseInt(String.valueOf(c));
        }
        if (gender % 2 == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断年龄
     */
    public static Integer getAge(String zjh){
        if (StringUtils.isBlank(zjh)) {
            return null;
        }
        int yearBirth =0;
        if(zjh.length() ==18){
            yearBirth = Integer.valueOf(StringUtils.substring(zjh, 6, 10));
        }else if(zjh.length() ==15){
            yearBirth =Integer.valueOf("19"+StringUtils.substring(zjh, 6, 8));
        }
        Calendar today = Calendar.getInstance();
        Integer nowYear = today.get(Calendar.YEAR);
        Integer age = nowYear - yearBirth;
        return age;

    }

}
