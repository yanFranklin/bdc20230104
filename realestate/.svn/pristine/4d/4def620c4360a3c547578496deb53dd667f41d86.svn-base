package cn.gtmap.realestate.common.util.encrypt;


import cn.gtmap.realestate.common.util.DataUtil;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * SM2密钥对Bean
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @Version 1.0
 * @description 1.0，2019/12/18
 */
public class SM2KeyPair {
    private volatile static SM2KeyPair instance;
    private final ECPoint publicKey;
    private final BigInteger privateKey;

    public SM2KeyPair(ECPoint publicKey, BigInteger privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static SM2KeyPair getInstance() {
        if (instance == null) {
            synchronized (SM2KeyPair.class) {
                if (instance == null) {
                    byte[] pubArr = DataUtil.hexStringToBytes(SM2Util.SM2_PUBLIC_KEY);
                    if (null != pubArr) {
                        ECPoint publicKey = SM2.curve.decodePoint(pubArr);
                        BigInteger privateKey = new BigInteger(DataUtil.hexStringToBytes(SM2Util.SM2_PRIVATE_KEY));
                        if (null != publicKey) {
                            instance = new SM2KeyPair(publicKey, privateKey);
                        }
                    }
                }
            }
        }
        return instance;
    }

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

}
