package cn.gtmap.realestate.exchange.util;



import org.apache.commons.lang.StringUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author <a href="mailto:dingweiwei@gtmap.cn">dingweiwei</a>
 * @version V1.0, 2021/10/21
 * @description 淮安水过户加解密
 */
public class Sha1Util {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /**
     * HMAC_SHA1加密字符串
     *
     * @param encryptText 待加密字符串
     * @param encryptKey  加密密码
     * @return 加密后内容
     */
    public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        byte[] bytes = mac.doFinal(text);
        return StringUtils.upperCase(DESPlus.byteArr2HexStr(bytes));
    }

    /**
     * SHA1加密字符串
     * @param encryptText 待加密字符串
     * @return 加密后内容
     */
    public static String SHA1Encrypt(String encryptText){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(encryptText.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();

            String shaHex = "";

            for(int i=0; i< digest.length; i++){
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if(shaHex.length() < 2){
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}