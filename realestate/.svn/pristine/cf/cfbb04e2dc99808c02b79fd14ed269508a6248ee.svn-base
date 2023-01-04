package cn.gtmap.realestate.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/7/26 09:25
 * @description 证件号转换工具类
 */
public class CardNumberTransformation {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardNumberTransformation.class);

    private CardNumberTransformation() {

    }

    /**
     * 15位身份证号码转化为18位的身份证。如果是18位的身份证则直接返回，不作任何变化。
     *
     * @param idCard,15位的有效身份证号码
     * @return idCard18 返回18位的有效身份证
     *     
     */
    public static String idCard15to18(String idCard) {
        if(StringUtils.isBlank(idCard)){
            return idCard;
        }
        if (!idCard15(idCard)) {
            return idCard;
        }
        idCard = idCard.trim();
        StringBuilder idCard18 = new StringBuilder(idCard);
        //加权因子 int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        //校验码值
        char[] checkBit = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        //15位身份证
        if (idCard != null && idCard.length() == 15) {
            idCard18.insert(6, "19");
            for (int index = 0; index < idCard18.length(); index++) {
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(Character.toString(c));
                int wi = ((int) Math.pow(2, idCard18.length() - index)) % 11;
                sum = sum + ai * wi;
            }
            //取模
            int indexOfCheckBit = sum % 11;
            idCard18.append(checkBit[indexOfCheckBit]);
        }
        return idCard18.toString();
    }

    /**
     * 18位身份证转15
     *
     * @param zjh 证件号
     * @return 转换的15位身份证号
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public static String idCard18to15(String zjh) {
        String zjh15 = "";
        if (zjh.length() == 18) {
            zjh15 = zjh.substring(0, 6) + zjh.substring(8, 17);
        }
        return zjh15;
    }

    /**
     * 15位证件号验证是否是身份证
     *
     * @param zjh 证件号
     * @return 是身份证返回true   不是false
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public static boolean idCard15(String zjh) {
        String regex15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$";
        if (zjh.length() == 15) {
            if (zjh.matches(regex15)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 18位证件号验证是否是身份证
     *
     * @param zjh 证件号
     * @return 是身份证返回true   不是false
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public static boolean idCard18(String zjh) {
        String regex18 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        if (zjh.length() == 18) {
            if (zjh.matches(regex18)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证为15位或18位身份证号
     */
    public static boolean isIdCard(String zjh) {
        boolean result = false;
        String regex = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        if (!zjh.matches(regex)) {
            return result;
        }
        //6位，地区代码
        String address = zjh.substring(0, 6);
        String[] provinceArray = {"11:北京", "12:天津", "13:河北", "14:山西", "15:内蒙古", "21:辽宁", "22:吉林", "23:黑龙江", "31:上海", "32:江苏", "33:浙江", "34:安徽", "35:福建", "36:江西", "37:山东", "41:河南", "42:湖北 ", "43:湖南", "44:广东", "45:广西", "46:海南", "50:重庆", "51:四川", "52:贵州", "53:云南", "54:西藏 ", "61:陕西", "62:甘肃", "63:青海", "64:宁夏", "65:新疆", "71:台湾", "81:香港", "82:澳门", "91:国外"};
        boolean valideAddress = false;
        for (int i = 0; i < provinceArray.length; i++) {
            String provinceKey = provinceArray[i].split(":")[0];
            if (provinceKey.equals(address.substring(0, 2))) {
                valideAddress = true;
            }
        }
        if (valideAddress) {
            if (zjh.length() == 15) {
                //8位，出生日期
                String birthday = zjh.substring(6, 12);
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
                try {
                    sdf.parse(birthday);
                    result = true;
                } catch (ParseException e) {
                    LOGGER.info("身份证校验日期异常：" + birthday);
                    LOGGER.error("msg", e);
                }
            } else if (zjh.length() == 18) {
                //加权因子
                int[] weightedFactors = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
                //身份证验证位值，其中10代表X
                int[] valideCode = {1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
                //声明加权求和变量
                int sum = 0;
                String[] strArray = new String[zjh.length()];
                for (int i = 0; i < zjh.length(); i++) {
                    strArray[i] = String.valueOf(zjh.charAt(i));
                }
                if ("x".equals(strArray[17].toLowerCase())) {
                    //将最后位为x的验证码替换为10
                    strArray[17] = "10";
                }
                for (int i = 0; i < 17; i++) {
                    //加权求和
                    sum += weightedFactors[i] * Integer.parseInt(strArray[i]);
                }
                //得到验证码所在位置
                int valCodePosition = sum % 11;
                if (Integer.parseInt(strArray[17]) == valideCode[valCodePosition]) {
                    result = true;
                }

            }
        }
        return result;
    }

    /**
     * 验证是否是社会统一信用代码（18位）
     *
     * @param license 社会信用代码18位
     * @return true 是   false 否
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public static boolean isTyshxydm18(String license) {
        if (StringUtils.isEmpty(license)) {
            return false;
        }
        if (license.length() != 18) {
            return false;
        }

        String regex = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";
        if (!license.matches(regex)) {
            return false;
        }
        String str = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        int[] ws = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
        String[] codes = new String[2];
        codes[0] = license.substring(0, license.length() - 1);
        codes[1] = license.substring(license.length() - 1, license.length());
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += str.indexOf(codes[0].charAt(i)) * ws[i];
        }
        int c18 = 31 - (sum % 31);
        if (c18 == 31) {
            c18 = 'Y';
        } else if (c18 == 30) {
            c18 = '0';
        }
        if (str.charAt(c18) != codes[1].charAt(0)) {
            return false;
        }
        return true;
    }

    /**
     * 营业执照 统一社会信用代码（15位）
     *
     * @param license
     * @return
     */
    public static boolean isTyshxydm15(String license) {
        if (StringUtils.isEmpty(license)) {
            return false;
        }
        if (license.length() != 15) {
            return false;
        }

        // 获取营业执照注册号前14位数字用来计算校验码
        String businesslicensePrex14 = license.substring(0, 14);
        // 获取营业执照号的校验码
        String businesslicense15 = license.substring(14, license.length());
        char[] chars = businesslicensePrex14.toCharArray();
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
        }
        getCheckCode(ints);
        // 比较 填写的营业执照注册号的校验码和计算的校验码是否一致

        return businesslicense15.equals(getCheckCode(ints) + "");
    }

    /**
     * 获取 营业执照注册号的校验码
     *
     * @param ints
     * @return
     */
    public static int getCheckCode(int[] ints) {
        if (null != ints && ints.length > 1) {
            int ti = 0;
            // pi|11+ti
            int si = 0;
            // （si||10==0？10：si||10）*2
            int cj = 0;
            // pj=cj|11==0?10:cj|11
            int pj = 10;
            for (int i = 0; i < ints.length; i++) {
                ti = ints[i];
                pj = (cj % 11) == 0 ? 10 : (cj % 11);
                si = pj + ti;
                cj = (0 == si % 10 ? 10 : si % 10) * 2;
                if (i == ints.length - 1) {
                    pj = (cj % 11) == 0 ? 10 : (cj % 11);
                    return pj == 1 ? 1 : 11 - pj;
                }
            }
        }// end if
        return -1;
    }


    /**
     * 证件号转换,15为身份证号返回15+18身份证号字符串（转成大写）
     * 18位身份证号返回15+18身份证号字符串（转成大写）
     * 其他证件号则转成大写返回
     *
     * @param zjh 15或18位身份证号
     * @return 15+18身份证号
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public static String zjhTransformation(String zjh) {
        String regex15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$";
        String regex18 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        if (zjh.length() == 15) {
            if (zjh.matches(regex15)) {
                String zjh18 = idCard15to18(zjh);
                zjh = zjh + "," + zjh18;
                return StringUtils.upperCase(zjh);
            } else {
                return StringUtils.upperCase(zjh);
            }
        }
        if (zjh.length() == 18) {
            if (zjh.matches(regex18)) {
                String zjh15 = idCard18to15(zjh);
                zjh = zjh15 + "," + zjh;
                return StringUtils.upperCase(zjh);
            } else {
                return StringUtils.upperCase(zjh);
            }
        }
        return StringUtils.upperCase(zjh);
    }


}
