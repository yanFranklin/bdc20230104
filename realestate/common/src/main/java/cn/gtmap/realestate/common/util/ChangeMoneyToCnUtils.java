package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @program: realestate
 * @description: 转换金钱数字为中文大写
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-07-23 11:32
 **/
public class ChangeMoneyToCnUtils {
    /**
     * 转换为中国人民币大写字符串,精确到分
     * @param money 传入小写数字字符串
     * @return
     * @throws Exception
     */

    // 不考虑分隔符的正确性
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
    private static final char[] RMB_NUMS = "零壹贰叁肆伍陆柒捌玖".toCharArray();
    private static final String[] UNITS = {"元", "角", "分", "整"};
    private static final String[] U1 = {"", "拾", "佰", "仟"};
    private static final String[] U2 = {"", "万", "亿"};

    /**
     * 将金额（整数部分等于或少于12位，小数部分2位）转换为中文大写形式.
     *
     * @param money 金额数字
     * @return 中文大写
     * @throws IllegalArgumentException
     */
    public static String convert(String money) throws Exception {
        boolean lessZero = false;
        if(money.startsWith("-")) {
            money = money.substring(1);
            lessZero = true;
        }

        if (!money.matches("^[0-9]*$|^0+\\.[0-9]+$|^[1-9]+[0-9]*$|^[1-9]+[0-9]*.[0-9]+$")) {
            throw new AppException("金钱格式错误！");
        }
        String[] part = money.split("\\.");
        String integerData = part[0];
        String decimalData = part.length > 1 ? part[1] : "";
        //替换前置0
        if(integerData.matches("^0+$"))
        {
            integerData = "0";
        }else if(integerData.matches("^0+(\\d+)$")){
            integerData = integerData.replaceAll("^0+(\\d+)$", "$1");
        }

        StringBuilder integer = new StringBuilder();
        for (int i = 0; i < integerData.length(); i++) {
            char perchar = integerData.charAt(i);
            integer.append(upperNumber(perchar));
            integer.append(upperNumber(integerData.length() - i - 1));
        }
        StringBuilder decimal = new StringBuilder();
        if (part.length > 1 && !"00".equals(decimalData)) {
            int length = decimalData.length() >= 2 ? 2 : decimalData.length();
            for (int i = 0; i < length; i++) {
                char perchar = decimalData.charAt(i);
                decimal.append(upperNumber(perchar));
                if (i == 0) {
                    decimal.append('角');
                }
                if (i == 1) {
                    decimal.append('分');
                }
            }
        }
        String result = integer.toString() + decimal.toString();
        result = dispose(result);
        if(lessZero && !"零圆整".equals(result)) {
            result = "负" + result;
        }
        return result;
    }

    private static char upperNumber(char number) {
        switch (number) {
            case '0':
                return '零';
            case '1':
                return '壹';
            case '2':
                return '贰';
            case '3':
                return '叁';
            case '4':
                return '肆';
            case '5':
                return '伍';
            case '6':
                return '陆';
            case '7':
                return '柒';
            case '8':
                return '捌';
            case '9':
                return '玖';
            default:
                    break;
        }
        return '0';
    }

    private static char upperNumber(int index) {
        int realIndex =  index  % 9;
        if(index > 8) {//亿过后进入回归,之后是拾,佰...
            realIndex =  (index - 9) % 8;
            realIndex = realIndex + 1;
        }
        switch (realIndex) {
            case 0:
                return '圆';
            case 1:
                return '拾';
            case 2:
                return '佰';
            case 3:
                return '仟';
            case 4:
                return '万';
            case 5:
                return '拾';
            case 6:
                return '佰';
            case 7:
                return '仟';
            case 8:
                return '亿';
            default:
                break;
        }
        return '0';
    }

    private static String dispose(String result) {
        result = result.replaceAll("0", "");//处理
        result = result.replaceAll("零仟零佰零拾|零仟零佰|零佰零拾|零仟|零佰|零拾", "零");
        result = result.replaceAll("零+", "零").replace("零亿", "亿");
        result = result.matches("^.*亿零万[^零]仟.*$") ? result.replace("零万", "零") : result.replace("零万", "万");
        result = result.replace("亿万", "亿");
        //处理小数
        result = result.replace("零角", "零").replace("零分", "");
        result = result.replaceAll("(^[零圆]*)(.+$)", "$2");
        result = result.replaceAll("(^.*)([零]+圆)(.+$)", "$1圆零$3");

        //处理整数单位
        result = result.replaceAll("圆零角零分|圆零角$|圆$|^零$|圆零$|零圆$", "圆整");
        result = result.replaceAll("^圆整$", "零圆整");


        return result;
    }

    /**
     * 对金额的格式调整到分
     *
     * @param money
     * @return
     */
    public static String moneyFormat(String money) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(money)) {
            return "0.00";
        }
        int index = money.indexOf(".");
        if (index == -1) {
            return money + ".00";
        } else {
            //整数部分
            String s0 = money.substring(0, index);
            //小数部分
            String s1 = money.substring(index + 1);
            //小数点后一位
            if (s1.length() == 1) {
                s1 = s1 + "0";
            } else if (s1.length() > 2) {
                //如果超过3位小数，截取2位就可以了
                s1 = s1.substring(0, 2);
            }
            sb.append(s0);
            sb.append(".");
            sb.append(s1);
        }
        return sb.toString();
    }
}
