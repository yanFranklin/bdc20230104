package cn.gtmap.realestate.exchange.util.sjrpt;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SmUtil;

import java.security.KeyPair;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-09-28
 * @description 合肥省金融平台加解密工具类
 */
public class Sm2Util {

    /**
     * 算法
     */
    private static final String ALGORITHM = "SM2";

    /**
     * 密钥长度
     */
    private static final int KEY_SIZE = 4096;

    /**
     * 生成SM2密钥对，长度4096
     *
     * @return 密钥对
     */
    public static KeyPair initKeyPair() {
        return KeyUtil.generateKeyPair(ALGORITHM, KEY_SIZE);
    }

    /**
     * 签名
     *
     * @param privateKey 私钥，BASE64编码
     * @param data       待签名数据，十六进制编码
     * @return 签名结果
     */
    public static String sign(String privateKey, String data) {
        return SmUtil.sm2(privateKey, null)
                .signHex(data);
    }

    /**
     * 公钥加密
     *
     * @param publicKey 公钥，BASE64编码
     * @param data      待加密数据，UTF-8
     * @return 密文，BASE64编码
     */
    public static String encrypt(String publicKey, String data) {
        return Base64.encode(SmUtil.sm2(null, publicKey)
                .encrypt(StrUtil.bytes(data)));
    }

    /**
     * 私钥解密到二进制明文
     *
     * @param privateKey 私钥，BASE64编码
     * @param data 密文，BASE编码
     * @return 明文，byte[]
     */
    public static byte[] decryptBinary(String privateKey, String data) {
        return SmUtil.sm2(privateKey, null)
                .decrypt(Base64.decode(data));
    }

    /**
     * 私钥解密
     *
     * @param privateKey 私钥，BASE64编码
     * @param data       密文数据，BASE64编码
     * @return 明文，UTF-8
     */
    public static String decrypt(String privateKey, String data) {
        return StrUtil.str(decryptBinary(privateKey, data), CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 校验签名
     *
     * @param publicKey 签名者公钥
     * @param data      待签名数据
     * @param sign      签名数据
     * @return 校验结果
     */
    public static boolean verify(String publicKey, String data, String sign) {
        return SmUtil.sm2(null, publicKey)
                .verify(HexUtil.decodeHex(data), HexUtil.decodeHex(sign));
    }

}
