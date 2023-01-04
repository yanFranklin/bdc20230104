package cn.gtmap.realestate.exchange.util.zrpay;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 加解密
 */
public class EncryUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryUtil.class);

    /**
     * 此场景模拟用户请求统一支付平台 适配已优化Form表单Post请求的用户
     * 获取到加密的参数后请求统一支付平台
     *
     * @param oriParam 签名原串 按实际接口原串拼接
     * @param priKey   客户方私钥，这里使用原签名私钥对参数进行加签
     * @param pubKey   平台公钥，这里使用原验签公钥对AES密钥进行加签
     * @return
     */
    public static String[] newRequestZrzfDncryptParam(String oriParam, Set<String> set, String priKey, String pubKey) {
        AESUtil aesUtil = new AESUtil();
        LOGGER.info("请求参数:{}", oriParam);
        String originSignStr = SignUtils.createSign(oriParam, set);
        LOGGER.info("createSign:{}", originSignStr);
        // 用户签名 -> 私钥签名 -> 用于双方认证
        String signInf = SHA256withRSA.sign(priKey, originSignStr);

        // 拼接签名信息 用于加密
        JSONObject jsonObject = JSONObject.parseObject(oriParam);
        jsonObject.put("SIGN_INF", signInf);
        String str = JSONObject.toJSONString(jsonObject);

        LOGGER.info("参数信息用于加密:{}", str);

        String aesKey = aesUtil.getAESSecureKey();
        LOGGER.info("AES加密参数信息:{}", aesKey);

        String encryptParam = aesUtil.encrypt(str, aesKey);
        LOGGER.info("AES加密后参数:{}", encryptParam);

        //加密AES密钥 这里使用RSA加密 政融会解密获取AES密钥后再解密请求参数
        String rsaEncryKey = RSAUtilV2.encryptByPublic(aesKey.getBytes(), RSAUtilV2.getPublicKey(pubKey));
        LOGGER.info("RSA加密AES密钥:{}", rsaEncryKey);
        return new String[]{encryptParam, rsaEncryKey};
    }

    /**
     * 此场景模拟用户接收到返回数据后进行解密
     *
     * @param encryptedAesKey  加密后的aeskey
     * @param encryptedBsnData 加密后的bsndata
     * @param priKey           客户私钥
     * @param pubKey           平台公钥
     * @return 解密后的key
     */
    public static String decryption(String encryptedAesKey, Set<String> set, String encryptedBsnData, String priKey, String pubKey) {
        // Step1：使用商户私钥对加密的aeskey进行解密
        String rsaKey = RSAUtilV2.decryptByPrivate(encryptedAesKey, RSAUtilV2.getPrivateKey(priKey));
        LOGGER.info("获取到的aesKey:{}", rsaKey);

        // Step2:使用rsakey解密bsnData
        AESUtil aesUtil = new AESUtil();
        String decryptedData = aesUtil.decrypt(encryptedBsnData, rsaKey);
        LOGGER.info("获取到解密后的业务信息:{}", decryptedData);
        // Step3:使用平台公钥进行验签
        //获取签名
        //int startLocation = decryptedData.indexOf("&SIGN_INF=") + 10;
        //String sign = decryptedData.substring(startLocation);
        //String originStr = decryptedData.substring(0,startLocation-10);
        JSONObject jsonObject = JSONObject.parseObject(decryptedData);
        String sign = jsonObject.getString("SIGN_INF");
        jsonObject.remove("SIGN_INF");
        String originJson = JSONObject.toJSONString(jsonObject);
        // 生成原串
        String originStr = SignUtils.createSign(originJson, set);
        // 进行验签
        boolean result = SHA256withRSA.verifySign(pubKey, originStr, sign);
        if (result) {
            LOGGER.info("验签通过");
        } else {
            LOGGER.info("验签未通过");
        }
        return decryptedData;
    }
}
