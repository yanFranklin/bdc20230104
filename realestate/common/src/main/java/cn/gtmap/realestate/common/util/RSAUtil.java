package cn.gtmap.realestate.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);
    /**
     * 放置公钥与私钥
     *
     * volatile 保证当前的map在主内存中保存最新值
     *
     */
    private static volatile Map<String, String> keyMap = new HashMap<String, String>(2);
    static {
        keyMap.put("public","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIwF/9T0G3xfn0QwSre9L05/0xMLl7svSh/LMGnyotO+WXO79NeuTPq5+gw7l+XkhFv6tZtzB2jfloXOLBTlUBctnyZvEzOIKEvCSfJRU3xJrOk8tA4NHvDSXRL9s+nXCSkoblVF+3VwmwJ2Jz6vLbyZCdKrLOKtXhdveLBaeRxwIDAQAB");
        keyMap.put("private","MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMjAX/1PQbfF+fRDBKt70vTn/TEwuXuy9KH8swafKi075Zc7v0165M+rn6DDuX5eSEW/q1m3MHaN+Whc4sFOVQFy2fJm8TM4goS8JJ8lFTfEms6Ty0Dg0e8NJdEv2z6dcJKShuVUX7dXCbAnYnPq8tvJkJ0qss4q1eF294sFp5HHAgMBAAECgYAKU3LcibYcqM9JlCTqWS0pscEhRZtU1IimyGPBBHDb8MfHdqhJaPfaAr7sOwUQjfLzu38p1zVj87uPda+oN8pBeH8nGjxuMGXWNLUSyxjM/TAN+l2qJA1zoX7sdVh4BSjVbWvDkbwkUfOcMTKhaFNB8kv5xW0yuZDhscluJ0rcgQJBAOVdydjXRnI83F8OahAAGsiphpyn+WuWk1C1dhMmj2w5bAaeZIqLd/+no63P2KY1C376tKvEfspkdCIwD9GnbgcCQQDgD/jCFLqDl9ta8G0dUKUmFkCRLM8wAPuscvEAO65ilreAC/f6roAJZWEWtbi8wUlM8hiwODCjYLM5DQxszc5BAkEAwy4par3ec634tWcVuGHuaN1h8IM8S2KKH78bKDbDC4xdsdAtrHv8dHBaZ7kcGmu/SaHcBuMiJeaPsNwHShhZFwJBALXJv5YXo0vpBTu/HYhPSe6g1znyFRdpdMfxab86rL2ocrEdBNEKiy8UZbU7MutSFtqrAYTX0f58Em0TPSV5xQECQQCiUFT35WRIQ1me9/nglr0yLvdDPGbHfF6YBUmcpZJ720gCwQ8GT6iIPb0VV8zGQ94IUg8m1oICybpZ+c/p+ChZ");
    }

//    public static void test1() throws Exception {
//        //如果想换公钥和私钥要先把static{keyMap.put("public","xxx");keyMap.put("private","xxx")}进行注释掉,然后控制台会打印新的值进行复制替换旧的值关闭注释即可
//        System.out.println("随机生成的公钥为:" + keyMap.get("public"));
//        System.out.println("随机生成的私钥为:" + keyMap.get("private"));
//        String message="123";
//        String messageEn = encrypt(message,keyMap.get("public"));
//        System.out.println(message + "加密后的字符串为:" + messageEn);
//        String messageDe = decrypt(messageEn);
//        System.out.println("还原后的字符串为:" + messageDe);
//    }

    /**
     * 获取公钥
     *
     * @return
     */
    public static String getPublicKey() {
        String publicKey = keyMap.get("public");
        if (publicKey == null) {
            // 加锁 防止多线程生产多个密钥
            synchronized (keyMap.getClass()) {
                if (publicKey == null) {
                    try {
                        genKeyPair();
                        publicKey = keyMap.get("public");
                    } catch (NoSuchAlgorithmException e) {
                        logger.error("获取随机公私钥错误", e);
                    }
                }
            }
        }
        return publicKey;
    }

    /**
     * 获取私钥
     *
     * @return 私钥
     */
    private static String getPrivateKey() {
        String privateKey = keyMap.get("private");
        if (privateKey == null) {
            logger.error("未获取私钥!!!");
        }
        return privateKey;
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(org.apache.commons.codec.binary.Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(org.apache.commons.codec.binary.Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put("public", publicKeyString);
        keyMap.put("private", privateKeyString);
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     *
     * @param
     * @param str 加密字符串
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt( String str) {
        String outStr = "";
        //64位解码加密后的字符串
        byte[] inputByte = new byte[0];
        try {
            inputByte = org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(getPrivateKey());
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码", e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("未找到找到指定算法", e);
        } catch (InvalidKeyException e) {
            logger.error("无效的秘钥", e);
        } catch (NoSuchPaddingException e) {
            logger.error("请求特定填充机制, 但该环境中未提供时", e);
        } catch (BadPaddingException e) {
            logger.error("预期对输入数据使用特定填充机制, 但未正确填充数据", e);
        } catch (InvalidKeySpecException e) {
            logger.error("无效的密钥规范", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("非法的块大小", e);
        }
        return outStr;
    }

    /**
     * 解密
     * @param str
     * @return
     */
    public static String decode(String str) {
        return decrypt(str);
    }
}
