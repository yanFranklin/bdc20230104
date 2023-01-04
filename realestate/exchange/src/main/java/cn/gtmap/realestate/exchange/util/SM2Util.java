package cn.gtmap.realestate.exchange.util;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-06-09
 * @description 省平台加解密工具类
 */
public class SM2Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(SM2Util.class);

    /**
     * 加密所需要jar
     * <dependency>
     *     <groupId>org.bouncycastle</groupId>
     *     <artifactId>bcprov-jdk15to18</artifactId>
     *     <version>1.68</version>
     * </dependency>
     *
     * <dependency>
     *     <groupId>cn.hutool</groupId>
     *     <artifactId>hutool-all</artifactId>
     *     <version>5.6.7</version>
     * </dependency>
     */

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static PrivateKey getPrivateKeyFromBigIntegers(BigInteger s) {
        //// 获取一条SM2曲线参数
        X9ECParameters ecCurve = GMNamedCurves.getByName("sm2p256v1");
        // 构造domain参数
        ECParameterSpec ecParameterSpec = new org.bouncycastle.jce.spec.ECParameterSpec(ecCurve.getCurve(),
                ecCurve.getG(), ecCurve.getN(), ecCurve.getH(), ecCurve.getSeed());
        ECPrivateKeySpec privateKeySpec = new org.bouncycastle.jce.spec.ECPrivateKeySpec(s, ecParameterSpec);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param str           加密信息
     * @param privateKeystr 私钥
     * @return 解密信息
     */
    public static String decryptInputParameters(String str, String privateKeystr) {
        Security.addProvider(new BouncyCastleProvider());
        ASN1Sequence seq = ASN1Sequence.getInstance(Base64.decodeBase64(privateKeystr));
        ASN1OctetString derOctetString = ASN1OctetString.getInstance(seq.getObjectAt(2));
        byte[] derByte = derOctetString.getOctets();
        ASN1Sequence asSeq = ASN1Sequence.getInstance(derByte);
        PrivateKey privateKey = getPrivateKeyFromBigIntegers(ASN1Integer.getInstance(asSeq.getObjectAt(1)).getValue());
        SM2 sm2privateKey = SmUtil.sm2(privateKey.getEncoded(), null);
        // 私钥解密
        String decryptStr = sm2privateKey.decryptStr(str, KeyType.PrivateKey);
        LOGGER.info("decryptStr:\n" + decryptStr + "\n");
        return decryptStr;
    }

    /**
     * 加密
     *
     * @param publicKeystr 加密公钥
     * @param str           需要加密的内容
     * @return 加密信息
     */
    public static String encryptedMealReturn(String str, String publicKeystr) {
        SM2 sm2publicKey = SmUtil.sm2(null, publicKeystr);
        String encryptStr = StrUtil.utf8Str(sm2publicKey.encryptHex(str, KeyType.PublicKey));
        return encryptStr;
    }

}
