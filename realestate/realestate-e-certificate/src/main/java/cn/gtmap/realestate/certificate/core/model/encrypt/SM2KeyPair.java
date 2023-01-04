package cn.gtmap.realestate.certificate.core.model.encrypt;


import cn.gtmap.realestate.certificate.util.DataUtil;
import cn.gtmap.realestate.certificate.util.SM2Util;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * SM2密钥对Bean
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/24
 */
public class SM2KeyPair {
	private static SM2KeyPair instance;
	private final ECPoint publicKey;
	private final BigInteger privateKey;

	public static synchronized SM2KeyPair getInstance() {
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

	public SM2KeyPair(ECPoint publicKey, BigInteger privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public ECPoint getPublicKey() {
		return publicKey;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

}
