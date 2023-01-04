package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-21
 * @description 加密工具类（主要用于合肥电相关接口）
 */
public class AESUtil {

    private static final String KEY_AES = "AES";

    // 合肥电接口秘钥配置文件中的属性名
    private static final String HEFEI_DIAN_INTERFACE_KEY = "dian.interface.key";

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param src
     * @return java.lang.String
     * @description 合肥电接口 加密方法
     */
    public static String hefeiDianEncrypt(String src) throws Exception {
//        String key = "SGCC_ah_wechat_0";
        String key = EnvironmentConfig.getEnvironment().getProperty(HEFEI_DIAN_INTERFACE_KEY);
        return encrypt(src,key);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param src
     * @return java.lang.String
     * @description 合肥 电接口 解密方法
     */
    public static String hefeiDianDecrypt(String src){
//        String key = "SGCC_ah_wechat_0";
        String key = EnvironmentConfig.getEnvironment().getProperty(HEFEI_DIAN_INTERFACE_KEY);
        return decrypt(src,key);
    }

    public static String encrypt(String src, String key) throws Exception {
        if (key == null || key.length() != 16) {
            throw new Exception("key不满足条件");
        }
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes());
        return byte2hex(encrypted);
    }
 
    public static String decrypt(String src, String key) {
        String originalString = null;
        try{
            if (key == null || key.length() != 16) {
                throw new Exception("key不满足条件");
            }
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
            Cipher cipher = Cipher.getInstance(KEY_AES);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(src);
            byte[] original = cipher.doFinal(encrypted1);
            originalString = new String(original);
        }catch (Exception e){
            e.printStackTrace();
        }

        return originalString;
    }
 
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }
 
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }
 
}