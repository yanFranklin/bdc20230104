package cn.gtmap.realestate.certificate.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/20
 */
public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_GBK = "GBK";

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param str
     * @return
     * @description 字符串转byte数组 编码UTF-8
     */
    public static byte[] strToByteUtf8(String str){
        return strToByte(str, ENCODING_UTF8);
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param str
     * @return
     * @description 字符串转byte数组 编码GBK
     */
    public static byte[] strToByteGbk(String str){
        return strToByte(str, ENCODING_GBK);
    }


    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param str
     * @return
     * @description 字符串转byte数组
     */
    public static byte[] strToByte(String str, String encode){
        byte[] b = null;
        if (StringUtils.isNotBlank(str)) {
            try {
                b = str.getBytes(encode);
            } catch (UnsupportedEncodingException e) {
                logger.error("StringUtils:strToByteUtf8", e);
            }
        }
        return b;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param b
     * @return
     * @description byte数组转字符串 编码UTF-8
     */
    public static String byteToStrUtf8(byte[] b){
        return byteToStr(b, ENCODING_UTF8);
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param b
     * @return
     * @description byte数组转字符串 编码GBK
     */
    public static String byteToStrGbk(byte[] b){
        return byteToStr(b, ENCODING_GBK);
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param b
     * @return
     * @description byte数组转字符串
     */
    public static String byteToStr(byte[] b, String encode){
        String str = null;
        if (null != b) {
            try {
                str = new String(b, encode);
            } catch (UnsupportedEncodingException e) {
                logger.error("StringUtils:byteToStrUtf8", e);
            }
        }
        return str;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param jsonString
     * @return
     * @description 截取生成证照PDF接口加密字符串
     */
    public static String substringZzpdfJson(String jsonString){
        String sign = null;
        if (StringUtils.isNotBlank(jsonString)) {
            String str1 = jsonString.substring(jsonString.indexOf("\"data\""));
            if (StringUtils.isNotBlank(str1)) {
                String str2 = str1.substring(str1.indexOf("{"),str1.lastIndexOf("}"));
                if (StringUtils.isNotBlank(str2)) {
                    int headIndex = str2.indexOf("head");
                    if (headIndex > 0) {
                        str2 = str2.substring(0,headIndex);
                    }
                    sign = str2.substring(0,str2.lastIndexOf("}")+1);
                }
            }
        }
        return sign;
    }

    public static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {
        return String.valueOf(c).getBytes(ENCODING_GBK).length > 1;
    }

    /**
     *
     * @param orignal
     * @param count
     * @param countTotal
     * @return
     * @throws UnsupportedEncodingException
     * @description 根据字节数截取字符串
     */
    public static String substringByByte(String orignal, int count, int countTotal)
            throws UnsupportedEncodingException {
        if (count > 0 && count < countTotal) {
            StringBuilder buff = new StringBuilder();
            char c;
            for (int i = 0; i < count; i++) {
                c = orignal.charAt(i);
                buff.append(c);
                if (isChineseChar(c)) {
                    // 遇到中文汉字，截取字节总数减1
                    --count;
                }
            }
            return buff.toString();
        }
        return orignal;
    }

    public static int countTotal(String str, String encode) throws UnsupportedEncodingException {
        return str.getBytes(encode).length;
    }

    public static int countTotalGbk(String str) throws UnsupportedEncodingException {
        return countTotal(str, ENCODING_GBK);
    }

    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
