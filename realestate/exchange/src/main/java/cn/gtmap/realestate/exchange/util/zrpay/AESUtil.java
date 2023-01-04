package cn.gtmap.realestate.exchange.util.zrpay;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-10-17
 * @description 加密工具类（主要用于政融平台相关接口）
 */
public class AESUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    private static final String ENCODING_CODE = "UTF-8";
    private static final String KEY_ALGORITHM = "AES";
    private static final int KEY_ALGORITHM_SIZE = 128;
    private static final String SECURE_RANDOM = "SHA1PRNG";
    private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    public AESUtil() {
    }

    public String encrypt(String count, String password) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(1, this.getKey(password));
            byte[] result = cipher.doFinal(count.getBytes(ENCODING_CODE));
            return Base64.encodeBase64String(result);
        } catch (Exception var5) {
            LOGGER.error("AES加密失败！");
            var5.printStackTrace();
            return "";
        }
    }

    public String decrypt(String count, String password) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(2, this.getKey(password));
            byte[] result = cipher.doFinal(Base64.decodeBase64(count));
            return new String(result);
        } catch (Exception var5) {
            LOGGER.error("AES解密失败！");
            var5.printStackTrace();
            return "";
        }
    }

    public String getAESSecureKey() {
        String aesKey = "";

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(KEY_ALGORITHM_SIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            aesKey = Hex.encodeHexString(secretKey.getEncoded());
            LOGGER.info("生成AES密钥：" + Hex.encodeHexString(secretKey.getEncoded()));
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

        return aesKey;
    }

    public SecretKey getKey(String password) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM);
            secureRandom.setSeed(password.getBytes());
            keyGenerator.init(KEY_ALGORITHM_SIZE, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
            return new SecretKeySpec(byteKey, KEY_ALGORITHM);
        } catch (Exception var6) {
            LOGGER.error("创建AES KEY失败！");
            var6.printStackTrace();
            return null;
        }
    }
}
