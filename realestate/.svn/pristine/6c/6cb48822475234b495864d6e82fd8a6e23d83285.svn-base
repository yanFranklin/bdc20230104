package cn.gtmap.realestate.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * RSA 加密和解密
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/6/19 19:56
 */
public class RSAEncryptUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAEncryptUtils.class);

    //基础密钥
    private static final String baseKey = "2624930726249307";
    //加密向量
    private static final String ivParameter = "379722706@qq.com";

    public static String encrypt(String sSrc) {
        return encrypt(baseKey, sSrc);
    }

    // 加密
    public static String encrypt(String sKey, String sSrc) {
        String result = "";
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            result = new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用
        } catch (Exception e) {
            LOGGER.error(null, e);
        }
        return result;

    }

    public static String decrypt(String sSrc) {
        if(StringUtils.isBlank(sSrc)){
            return sSrc;
        }
        return decrypt(baseKey, sSrc);
    }

    // 解密
    public static String decrypt(String sKey, String sSrc) {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            LOGGER.error(null, ex);
            return null;
        }
    }

    /**
     * * ivStr 初始化向量
     * 加密 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串
     */
    public static String AESEncode(String encodeRules, String content,String ivStr) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //新增下面两行，处理 Linux 操作系统下随机数生成不一致的问题
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes());
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)
            // 操作，第二个参数为使用的KEY
            ////指定一个初始化向量 (Initialization vector，IV)， IV 必须是16位
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivStr
                    .getBytes("UTF-8")));
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byteAES = cipher.doFinal(byteEncode);
            // 10.将加密后的数据转换为字符串
            // 这里用Base64Encoder中会找不到包
            // 解决办法：
            // 在项目的Build path中先移除JRE System Library，再添加库JRE System
            // Library，重新编译后就一切正常了。
            String AESEncode = new String(new BASE64Encoder().encode
                    (byteAES));
            // 11.将字符串返回
            return AESEncode;
        } catch (Exception ex) {
            LOGGER.error(null, ex);
            return null;
        }
    }

    /**
     * ivStr 初始化向量
     * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密
     */
    public static String AESDecode(String encodeRules, String content,String ivStr) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //新增下面两行，处理 Linux 操作系统下随机数生成不一致的问题
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes());
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            ////指定一个初始化向量 (Initialization vector，IV)， IV 必须是16位
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivStr
                    .getBytes("UTF-8")));
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte[] byteDecode = cipher.doFinal(byteContent);
            String AESDecode = new String(byteDecode, "utf-8");
            return AESDecode;
        }catch (Exception ex) {
            LOGGER.error(null, ex);
            return null;
        }
    }

}
