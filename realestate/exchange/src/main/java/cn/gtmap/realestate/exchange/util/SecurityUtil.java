package cn.gtmap.realestate.exchange.util;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class SecurityUtil {

    private static final String DEFAULT_CODING = "UTF-8";
    private static final String AES_ALGORITHM = "AES";
    private static final String RSA_ALGORITHM = "RSA";
    private static final int RSA_KEYSIZE = 1024;
    /**
     * 填充方式
     */
    private static final String AES_CIPHER_PADDING = "AES/ECB/PKCS5Padding";
    private static final int PASSWORD_LENGTH = 16;

    private SecurityUtil() {

    }

    /**
     * AES加密
     *
     * @param content
     * @param password
     * @return java.lang.String
     * @author
     * @date
     */
    public static String aes128Encrypt(String content, String password) {
        try {
            checkPassword(password);
            Cipher cipher = Cipher.getInstance(AES_CIPHER_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, buildAes128SecretKey(password));
            byte[] result = cipher.doFinal(getContentByte(content));
            return encodeContentByte(result);
        } catch (Exception e) {
            throw new RuntimeException("aes 128 encrypt error!", e);
        }
    }

    /**
     * @param content
     * @param password
     * @return java.lang.String
     * @author
     * @date
     */
    public static String aes128Decrypt(String content, String password) {
        try {
            checkPassword(password);
            Cipher cipher = Cipher.getInstance(AES_CIPHER_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, buildAes128SecretKey(password));
            //byte[] result = cipher.doFinal(decodeContentByte(content));
            byte[] result = cipher.doFinal(decodeContentByte(content));
            return new String(result, DEFAULT_CODING);
        } catch (Exception e) {
            throw new RuntimeException("aes 128 decrypt error!", e);
        }
    }


    /**
     * @param password
     * @author
     * @date
     */
    private static void checkPassword(String password) {
        if (StringUtils.isBlank(password) || StringUtils.length(password) != PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password not available!");
        }
    }

    /**
     * 生成加密秘钥
     *
     * @param password
     * @return javax.crypto.spec.SecretKeySpec
     * @author
     * @date
     */
    private static SecretKeySpec buildAes128SecretKey(String password) {
        try {
            //返回生成指定算法密钥生成器的 KeyGenerator 对象
            KeyGenerator kgen = KeyGenerator.getInstance(AES_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes(DEFAULT_CODING));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), AES_ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("build aes 128 secret key error!", e);
        }
    }

    /**
     * @return com.example.laboratory.security.util.SecurityUtils.RsaKey
     * @author
     * @date
     */
    public static RsaKey generateRsaKey() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("generate rsa key error!", e);
        }
        keyPairGen.initialize(RSA_KEYSIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RsaKey(encodeContentByte(publicKey.getEncoded()), encodeContentByte(privateKey.getEncoded()));
    }

    /**
     * @param publicKey
     * @return java.security.PublicKey
     * @author
     * @date
     */
    private static PublicKey buildRsaPublicKey(String publicKey) {
        try {
            //通过X509编码的Key指令获得公钥对象
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(decodeContentByte(publicKey));
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            throw new RuntimeException("build rsa public key error!", e);
        }
    }

    /**
     * @param privateKey
     * @return java.security.PrivateKey
     * @author
     * @date
     */
    private static PrivateKey buildRsaPrivateKey(String privateKey) {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decodeContentByte(privateKey));
            return keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            throw new RuntimeException("build rsa private key error!", e);
        }
    }

    /**
     * RSA默认对加密内容有最大限制,超出限制会出现"IllegalBlockSizeException: Data must not be longer than 117 bytes"
     * 如果要对长内容做加密，就用RSA分段加密的方式
     *
     * @param content
     * @param publicKey
     * @return java.lang.String
     * @author
     * @date
     */
    public static String rsaPublicEncrypt(String content, String publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, buildRsaPublicKey(publicKey));
            byte[] encryptedData = cipher.doFinal(getContentByte(content));
            return encodeContentByte(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("rsa public encrypt error!", e);
        }
    }

    /**
     * @param content
     * @param privateKey
     * @return java.lang.String
     * @author
     * @date
     */
    public static String rsaPrivateDecrypt(String content, String privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, buildRsaPrivateKey(privateKey));
            byte[] decryptData = cipher.doFinal(decodeContentByte(content));
            return new String(decryptData, DEFAULT_CODING);
        } catch (Exception e) {
            throw new RuntimeException("rsa public encrypt error!", e);
        }
    }

    /**
     * @param content
     * @return byte[]
     * @author
     * @date
     */
    private static byte[] getContentByte(String content) throws UnsupportedEncodingException {
        return content.getBytes(DEFAULT_CODING);
    }

    /**
     * @param bytes
     * @return java.lang.String
     * @author
     * @date
     */
    private static String encodeContentByte(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * @param content
     * @return byte[]
     * @author
     * @date
     */
    private static byte[] decodeContentByte(String content) {
        return Base64.decodeBase64(content);
    }

    /**
     * @author
     * @date
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class RsaKey {

        private String publicKey;
        private String privateKey;
    }


}
