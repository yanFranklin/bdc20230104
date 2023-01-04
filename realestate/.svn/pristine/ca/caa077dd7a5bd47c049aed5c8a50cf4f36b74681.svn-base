package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version V1.0, 2022/08/15
 * @description 淮安水过户加解密
 */
public class DESPlus {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    private static String strDefaultKey = "cn.gtmap.bs4.analysis";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;


    /**
     * 默认构造方法，使用默认密钥
     */
    public DESPlus() {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     *
     * @param strKey 指定的密钥
     */
    public DESPlus(String strKey) {
        Key key = null;
        try {
            key = getKey(strKey.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("msg", e);
        }
        try {
            encryptCipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("msg", e);

        } catch (NoSuchPaddingException e) {
            logger.error("msg", e);

        }
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            logger.error("msg", e);

        }

        try {
            decryptCipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("msg", e);

        } catch (NoSuchPaddingException e) {
            logger.error("msg", e);

        }
        try {
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            logger.error("msg", e);

        }
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = new byte[0];
        try {
            arrB = strIn.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("msg", e);
        }
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = null;
            try {
                strTmp = new String(arrB, i, 2, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("msg", e);
            }
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 加密字节数组
     *
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     */
    public byte[] encrypt(byte[] arrB) {
        byte[] bytes = new byte[0];
        try {
            bytes = encryptCipher.doFinal(arrB);
        } catch (IllegalBlockSizeException e) {
            logger.error("msg", e);

        } catch (BadPaddingException e) {
            logger.error("msg", e);

        }
        return bytes;
    }

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     */
    public String encrypt(String strIn) {
        String result;
        try {
            result = byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("msg", e);
            throw new AppException("DESPlus加密方法失败");
        }
        return result;
    }

    /**
     * 解密字节数组
     *
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     */
    public byte[] decrypt(byte[] arrB) {
        byte[] byte1 = null;
        try {
            byte1 = decryptCipher.doFinal(arrB);
        } catch (IllegalBlockSizeException e) {
            logger.error("msg", e);

        } catch (BadPaddingException e) {
            logger.error("msg", e);

        }
        return byte1;
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     */
    public String decrypt(String strIn) {
        if (strIn != null) {
            try {
                return new String(decrypt(hexStr2ByteArr(strIn)), "UTF-8");
            } catch (Exception e) {
                logger.error("msg", e);
            }
        }
        return null;
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     */

    private Key getKey(byte[] arrBTmp) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

   /* public static void main(String[] args) {
        try {
            DESPlus desplus = new DESPlus();
            if (StringUtils.isNotBlank(desplus.encrypt("gtis"))) {
                logger.info(desplus.encrypt("gtis"));
            } else {
                logger.info("null");
            }
        } catch (Exception e) {
            logger.error("msg", e);
        }
    }*/
}
