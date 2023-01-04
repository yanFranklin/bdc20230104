package cn.gtmap.realestate.exchange.util.sjrpt;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.SM4;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-09-28
 * @description 合肥省金融平台加解密工具类
 */
public class Sm4Util {

    private static final String ALGORITHM = "SM4";

    private static final int KEY_SIZE = 128;

    /**
     * 生成随机初始化向量
     *
     * @return 初始化向量byte[]
     */
    public static byte[] initIv() {
        return RandomUtil.randomBytes(16);
    }

    /**
     * 生成随机密钥
     *
     * @return 随机密钥byte[]
     */
    public static byte[] initKey() {
        return KeyUtil.generateKey(ALGORITHM, KEY_SIZE).getEncoded();
    }

    /**
     * 加密，SM4/CBC/PKCS5Padding
     *
     * @param data 明文，UTF-8
     * @param key  密钥
     * @param iv   初始化向量
     * @return 密文，BASE64编码
     */
    public static String encrypt(String data, byte[] key, byte[] iv) {
        return Base64.encode(initCipher(key, iv).encrypt(StrUtil.bytes(data)));
    }

    /**
     * 解密，SM4/CBC/PKCS5Padding
     *
     * @param data 密文，BASE64编码
     * @param key  密钥
     * @param iv   初始化向量
     * @return 明文，UTF-8
     */
    public static String decrypt(String data, byte[] key, byte[] iv) {
        return StrUtil.str(initCipher(key, iv).decrypt(Base64.decode(data)), CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 初始化SM4算法实现类，默认使用CBC和PKCS5Padding，128bit密钥
     *
     * @param key 密钥
     * @param iv  初始化向量
     * @return SM4算法实现
     */
    private static SM4 initCipher(byte[] key, byte[] iv) {
        return new SM4(Mode.CBC, Padding.PKCS5Padding, key, iv);
    }

}
