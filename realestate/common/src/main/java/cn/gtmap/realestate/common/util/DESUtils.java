package cn.gtmap.realestate.common.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @program: realestate
 * @description: des加密算法
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-03 12:30
 **/
public class DESUtils {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";

    private static final byte[] keybytes = {0x31, 0x32, 0x33, 0x34, 0x35, 0x50, 0x37, 0x38, 0x39, 0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46};

    /**
     * 生成key
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static Key generateKey(String password) throws Exception {
        DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    public static String encrypt(String value) {
        String s = null;

        int mode = Cipher.ENCRYPT_MODE;

        try {
            Cipher cipher = initCipher(mode);

            byte[] outBytes = cipher.doFinal(value.getBytes());

            s = String.valueOf(Hex.encodeHex(outBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


    /**
     *  
     *     * @Title: decrypt 
     *     * @author yunlin.liu
     *     * @Description: 解密 
     *     * @param @param value
     *     * @param @return    设定文件 
     *     * @return String    返回类型 
     *     * @throws 
     *    
     */
    public static String decrypt(String value) {
        String s = null;

        int mode = Cipher.DECRYPT_MODE;

        try {
            Cipher cipher = initCipher(mode);

            byte[] outBytes = cipher.doFinal(Hex.decodeHex(value.toCharArray()));

            s = new String(outBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


    /**
     *  
     *     * @Title: initCipher 
     *     * @author yunlin.liu
     *     * @Description: 初始化密码
     *     * @param @param mode
     *     * @param @return
     *     * @param @throws NoSuchAlgorithmException
     *     * @param @throws NoSuchPaddingException
     *     * @param @throws InvalidKeyException    设定文件 
     *     * @return Cipher    返回类型 
     *     * @throws 
     *    
     */
    private static Cipher initCipher(int mode) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key key = new SecretKeySpec(keybytes, "AES");
        cipher.init(mode, key);
        return cipher;
    }
}
