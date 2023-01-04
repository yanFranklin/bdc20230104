package cn.gtmap.realestate.inquiry.config;

import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.encrypt.SM2;
import cn.gtmap.realestate.common.util.encrypt.SM2KeyPair;
import cn.gtmap.realestate.common.util.encrypt.Signature;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @Version 1.0
 * @description 1.0，2019/12/18
 */
public class SM2Util {

    // 秘钥唯一ID
    public final static String SM2_KEY_ID = "DZZZ_GTMAP_SM2_ID";
    /**
     * 公钥十六进制字符串
     */
    public final static String SM2_PUBLIC_KEY = "04da8af16d13ec0f287fe2c54f4eac4936cd65c10d261050836bdd4806cb3124916ce12b3ff98659c3f93c38f1e925ef8cacc3a8151d5cd30d6eca125b613c1fb8";
    /**
     * 私钥十六进制字符串
     */
    public final static String SM2_PRIVATE_KEY = "00c0354ae90137495ca05c38cfc0c3c600a2694739c41ca19033ae1f5356a2181f";

    /**
     * 国密 SM2
     */
    private final static SM2 SM_2 = new SM2(false);
    public final static SM2KeyPair KEYPAIR = SM2KeyPair.getInstance();

    private SM2Util() {
    }

    public static SM2KeyPair getKeyPair() {
        return SM_2.generateKeyPair();
    }

    /**
     * 公钥加密
     *
     * @param inputStr
     * @return
     */
    public static String encrypt(String inputStr, String publicKeyStr) {
        String result = null;
        if (StringUtils.isNotBlank(inputStr) && StringUtils.isNotBlank(publicKeyStr)) {
            ECPoint publicKey = SM2.curve.decodePoint(Base64Utils.decodeBase64StrToByte(publicKeyStr));
            byte[] data = SM_2.encrypt(inputStr, publicKey);
            result = Base64Utils.encodeByteToBase64Str(data);
        }
        return result;
    }

    /**
     * 私钥解密
     *
     * @param encryptData
     * @return
     */
    public static String decrypt(String encryptData, String privateKeyStr) {
        String result = null;
        if (StringUtils.isNotBlank(encryptData) && StringUtils.isNotBlank(privateKeyStr)) {
            BigInteger privateKey = new BigInteger(Base64Utils.decodeBase64StrToByte(privateKeyStr));
            result = SM_2.decrypt(Base64Utils.decodeBase64StrToByte(encryptData), privateKey);
        }

        return result;
    }

    /**
     * 国密SM2 数字签名
     *
     * @param M
     * @param IDA
     * @return
     */
    public static String SM2Sign(String M, String IDA) {
        String signStr = null;
        if (StringUtils.isNotBlank(IDA)) {
            Signature signature = SM_2.sign(M, IDA, KEYPAIR);
            if (null != signature) {
                signStr = signature.getEncode();
            }
        }
        return signStr;
    }
}
