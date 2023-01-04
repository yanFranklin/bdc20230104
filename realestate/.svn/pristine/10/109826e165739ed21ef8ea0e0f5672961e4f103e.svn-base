package cn.gtmap.realestate.common.util.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @Version 1.0，2022/09/07
 * @description md5加密
 */
public class MD5Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
    private static final String[] HEXDIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5Util() {
    }

    public static String byteArrayToString(byte[] b) {
        StringBuffer bths = new StringBuffer();

        for (int i = 0; i < b.length; ++i) {
            bths.append(byteToHexString(b[i]));
        }

        return bths.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return HEXDIGITS[d1] + HEXDIGITS[d2];
    }

    /**
     * string类型md5加密，32位小写
     *
     * @param origin 字符串
     * @return
     */
    public static String stringToMD5Str(String origin) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToString(md.digest(resultString.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Md5Util:MD5:Exception", e);
        }
        return resultString;
    }

    /**
     * string类型md5加密，32位大写
     *
     * @param origin
     * @return
     */
    public static String upperStrToMD5Str(String origin) {
        return stringToMD5Str(origin).toUpperCase(Locale.ROOT);
    }


}
