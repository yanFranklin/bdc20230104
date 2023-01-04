package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.exchange.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

@Service
public class BdcGxcxTokenServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(BdcGxcxTokenServiceImpl.class);

    /**
     * 获取token的url
     */
    @Value("${gxcx.token.appSecretUrl:http://59.255.22.70:8443/gateway/httpproxy}")
    private String appSecretUrl;

    @Autowired
    HttpClientUtils httpClientUtils;

    /*
     * 获取主键
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    /**
     * 随机生成秘钥
     */
    public static String getKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            //要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            return byteToHexString(b);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用指定的字符串生成秘钥
     */
    public static String getKeyByPass(String password) {
        //生成秘钥
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            // kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            //SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以生成的秘钥就一样。
            kg.init(128, new SecureRandom(password.getBytes()));
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            return byteToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * byte数组转化为16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    /**
     * aes加密
     *
     * @param appKey
     * @param content 明文
     * @return
     */
    public static String AESEncode(String appKey, String content) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(appKey.getBytes());
            // 生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            // 10.将加密后的数据转换为字符串
            // 这里用Base64Encoder中会找不到包
            // 解决办法：
            // 在项目的Build path中先移除JRE System Library，再添加库JRE System
            // Library，重新编译后就一切正常了。
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            // 11.将字符串返回
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 如果有错就返加nulll
        return null;
    }

    /**
     * 获得服务调用密钥（加密后的值）后，根据自己的appkey使用AES解密算法对返回值进行解密，最终获得真正密钥
     *
     * @param appkey
     * @param content 密文
     * @return
     */
    public static String AESDncode(String appkey, String content) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(appkey.getBytes());
            // 生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        // 如果有错就返加nulll
        return null;
    }

    /**
     * 验签方法
     *
     * @param content
     * @param appkey
     * @param sign
     * @return
     */
    public static boolean verifySign(String content, String appkey, String sign) {

        String result = null;
        result = sign(content, appkey);
        if (result.equals(sign)) {
            return true;
        }
        return false;
    }

    /**
     * 根据密钥（appsecret）对gjzwfwpt_rid，gjzwfwpt_sid和gjzwfwpt_rtime进行hmacsha256计算并进行base64转码获得gjzwfwpt_sign值
     *
     * @param content sid+rid+rtime
     * @param appkey
     * @return sign
     */
    public static String sign(String content, String appkey) {
        String result = null;
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = appkey.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
            logger.info("INPUT: " + content);
            byte[] hmacSha256Bytes = hmacSha256.doFinal(content.getBytes("UTF-8"));
            result = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
            logger.info("OUTPUT: " + result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String sign(JSONObject config) {
        String result = null;
        try {
            String appkey = config.getString("appkey");
            String rid = config.getString("rid");
            String sid = config.getString("sid");
            String rtime = config.getString("rtime");
            String content = sid + rid + rtime;
            String sign = sign(content, appkey);
            return getgjzwfwptSign(config,sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //
    public String getgjzwfwptSign(JSONObject config,String sign) {
        String result = null;
        try {
            String appkey = config.getString("appkey");
            String rid = config.getString("rid");
            String sid = config.getString("sid");
            String rtime = config.getString("rtime");

            JSONObject param = new JSONObject();
            param.put("gjzwfwpt_rid",rid);
            param.put("gjzwfwpt_sid",sid);
            param.put("gjzwfwpt_rtime",rtime);
            param.put("gjzwfwpt_sign",sign);
            String response = httpClientUtils.sendPostJsonRequest(appSecretUrl,
                    JSONObject.toJSONString(param), "国家政务服务平台");

            logger.info("国家政务服务平台获取token返回,{}",response);
            JSONObject responseObject = JSON.parseObject(response);
            String gjzwfwptSign = responseObject.getJSONObject("data").getString("secret");
            //解析
            return AESDncode(appkey,gjzwfwptSign);

            //return "testsign";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
