package cn.gtmap.realestate.exchange.util;

import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

/**
 * 字符串工具类
 */
public class StrUtil {

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String binToHex(byte[] bytes) {
        return Hex.toHexString(bytes).toUpperCase();
    }

    /**
     * 十六进制字符串转字节数组
     *
     * @param hex 字节数组
     * @return 十六进制字符串
     */
    public static byte[] hexToBin(String hex) {
        return Hex.decode(hex);
    }

    /**
     * 字节数组转UTF8字符串
     *
     * @param bytes 字节数组
     * @return UTF8字符串
     */
    public static String binToStr(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * UTF8字符串转字节数组
     *
     * @param str UTF8字符串
     * @return 字节数组
     */
    public static byte[] strToBin(String str) {
        return Strings.toUTF8ByteArray(str);
    }
}
