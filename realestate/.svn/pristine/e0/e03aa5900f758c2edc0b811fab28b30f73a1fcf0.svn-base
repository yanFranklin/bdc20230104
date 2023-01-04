package cn.gtmap.realestate.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @program: realestate
 * @description: 常州查档打印校验二维码加密信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-27 17:14
 **/
public class EncodeQRUtil {
    private final static String DES = "DES";
    private final static String ENCODE = "utf-8";
    private final static String defaultKey = "czdj2017";
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    /**
     * name 权利人名称
     * zij 权利人证件号
     * type 汇总或者无房1  详情0
     * temp 汇总或者无房时为房屋数量 如：1套  详情时为坐落 BSIT
     * @throws Exception
     * */
    public static String doEncode(String name,String zjh,String type,String temp) throws Exception
    {
        Date time = new Date();
        String number = DateUtils.formateDateToString(new Date(), DateUtils.DATE_FORMATYMDHMS);
        String objid = "{\\\"number\\\":\\\"" + number + "\\\",\\\"name\\\":\\\"" + name + "\\\",\\\"zjh\\\":\\\"" + zjh + "\\\",\\\"type\\\":\\\"" + type + "\\\",\\\"temp\\\":\\\"" + temp + "\\\"}";
        String toencode = "{\"objid\":\"" + objid + "\",\"time\":\"" + number + "\",\"type\":\"TOKEN\"}";
        return EncodeQR(toencode);
    }

    public static String EncodeQR(String origintext) throws Exception
    {
        //加密
        origintext = encrypt(origintext,defaultKey);
        origintext = Base64Utils.encodeByteToBase64Str(origintext.getBytes());
        origintext = origintext.replace("/", "_a");
        origintext = origintext.replace("+", "_b");
        origintext = origintext.replace("=", "_c");
        return origintext;
    }

//    /**
//     * 对给定的字符串以指定的编码方式和密钥进行加密
//     * @param srcStr 待加密的字符串
//     * @param charset 字符集，如utf8
//     * @param sKey 密钥
//     */
//    public static String encrypt(String srcStr, Charset charset, String sKey) {
//        byte[] src = srcStr.getBytes(charset);
//        byte[] buf = Des.encrypt(src, sKey);
//        return Des.parseByte2HexStr(buf);
//    }


    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null){
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
