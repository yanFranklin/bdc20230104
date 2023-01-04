package cn.gtmap.realestate.exchange.util.SM;

import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.qo.ggxypt.GgxyptCommonQO;
import cn.gtmap.realestate.exchange.web.rest.BdcGgrzptRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.xmsme.national.secrets.*;

import com.xmsme.national.secrets.util.encoders.Base64;
import com.xmsme.national.secrets.util.encoders.Hex;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/30
 * @description 对面加解密
 */
@Slf4j
@ComponentScan
public class EncryptDecryptRemote {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptDecryptRemote.class);

    private static final String USER_ID = "xmsme";

    public EncryptDecryptRemote() {
    }
/*
    @Test
    public void generateSm2Key() {
        //生成sm2秘钥对.
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecPriKey = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecPubKey = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecPriKey.getD();
        ECPoint publicKey = ecPubKey.getQ();

        log.info("公钥: " + NationalSecretsUtils.byteToHex(publicKey.getEncoded()));
        log.info("私钥: " + NationalSecretsUtils.byteToHex(privateKey.toByteArray()));
    }*/

   /* //本地sm2公钥,本地生成，见上生成实现
    private static String localPublicKey = "040469D4B903EF4985E932A9ABE5D33A35FA38CDC4DFE83B94B2D1B4BF6F0B1115CBAF4CA9BE42E6444DE8F4AC637B3BFBFE695D6E9634DFD5D5E900916C4AAD6C";
    //本地sm2私钥,本地生成，见上生成实现
    private static String localPrivateKey = "00FEDF50E767BC51435DF22656E46F48A1C0556B55F2BC3F8DDD75B0D622041FFC";

    //服务器sm2公钥，可用获取公钥接口获取
    private static String remotePublicKey = "04AADD8E258DD1F7D9178565E7FD643C29DFAE394EB74287FF13CA9CD3EB4EB6573C39DD64262BCCE43C80B890DA9AF79DBC026CA489C3F82BCCE77047A74CD4B3";
    //服务器sm2私钥, 测试需要
    private static String remotePrivateKey = "052D4FD7552D238EE8E379579B874235757E05877673F182C46F6A5FE472CFA2";*/

    //本地sm2公钥,本地生成，见上生成实现
    private static String localPublicKey = "04AADD8E258DD1F7D9178565E7FD643C29DFAE394EB74287FF13CA9CD3EB4EB6573C39DD64262BCCE43C80B890DA9AF79DBC026CA489C3F82BCCE77047A74CD4B3";
    //本地sm2私钥,本地生成，见上生成实现
    private static String localPrivateKey = "052D4FD7552D238EE8E379579B874235757E05877673F182C46F6A5FE472CFA2";

    //服务器sm2公钥，可用获取公钥接口获取
    private static String remotePublicKey = "04AADD8E258DD1F7D9178565E7FD643C29DFAE394EB74287FF13CA9CD3EB4EB6573C39DD64262BCCE43C80B890DA9AF79DBC026CA489C3F82BCCE77047A74CD4B3";
    //服务器sm2私钥, 测试需要
    private static String remotePrivateKey = "00FEDF50E767BC51435DF22656E46F48A1C0556B55F2BC3F8DDD75B0D622041FFC";

    @Test
    public void test_request_encrypt() throws Exception {
        //客户端加密请求生成示例.

        String requestData = "{\n" +
                "    \"QYMC\":\"刘龙\",\n" +
                "    \"QLRZJH\":\"3401231988****1113\",\n" +
                "    \"BDCQZSH\":\"\",\n" +
                "    \"AREACODE\":\"\",\n" +
                "    \"Bearer\":\"\"\n" +
                "}";
        Map<String, String> requestJsonObject = generateEncryptRequest(requestData);
        log.info("请求json:\n{}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(requestJsonObject));
    }

    @Test
    public void test_response_decrypt() throws Exception {
        //服务端加密返回解密示例
        Map<String, Object> encryptResponse = generateEncryptResponse("test response");
        String decryptSm4Key = SM2Utils.decrypt(localPrivateKey, encryptResponse.get("key").toString());
        log.info("请求key解密结果:{}", decryptSm4Key);

        SM4Utils decryptSm4 = new SM4Utils(decryptSm4Key);
        String decryptData = decryptSm4.decryptData_CBC(encryptResponse.get("data").toString());
        log.info("请求data解密结果:{}", decryptData);

        boolean verified = EncryptDecryptRemote.verifiedSign(decryptData, encryptResponse.get("signatureData").toString(), remotePublicKey);
        log.info("请求验签结果:{}", verified);
        if (verified) {
            log.info("正常请求:{}", decryptData);
        } else {
            log.info("验签错误");
        }
    }

    public static Map<String, Object> generateEncryptResponse(String responseData) throws Exception {
        String summary = summary(responseData);
        log.info("摘要:{}", summary);

        String sign = sign(summary, remotePrivateKey);
        log.info("请求signatureData:{}", sign);

        String randomSm4Key = RandomStringUtils.getRandomString();
        log.info("随机sm4密码:{}", randomSm4Key);
        SM4Utils sm4 = new SM4Utils(randomSm4Key);
        String encryptData = sm4.encryptData_CBC(responseData);
        log.info("请求中data:{}", encryptData);

        String key =
                SM2Utils.encrypt(
                        NationalSecretsUtils.hexToByte(localPublicKey),
                        randomSm4Key.getBytes(StandardCharsets.UTF_8));
        log.debug("请求中key：{}", key);

        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("success", true);
        returnMap.put("errorCode", "");
        returnMap.put("errorMessage", "");
        returnMap.put("timestamp", System.currentTimeMillis());
        returnMap.put("key", key);
        returnMap.put("signatureData", sign);
        returnMap.put("data", encryptData);
        return returnMap;
    }

    public static Map<String, String> generateEncryptRequest(String requestData) throws Exception {
        String summary = summary(requestData);
        log.info("摘要:{}", summary);

        String sign = sign(summary, localPrivateKey);
        log.info("请求signatureData:{}", sign);

        String randomSm4Key = RandomStringUtils.getRandomString();
        log.info("随机sm4密码:{}", randomSm4Key);
        SM4Utils sm4 = new SM4Utils(randomSm4Key);
        String encryptData = sm4.encryptData_CBC(requestData);
        log.info("请求中data:{}", encryptData);

        String key =
                SM2Utils.encrypt(
                        NationalSecretsUtils.hexToByte(remotePublicKey),
                        randomSm4Key.getBytes(StandardCharsets.UTF_8));
        log.debug("请求中key：{}", key);

        return ImmutableMap.of("publicKey", localPublicKey, "key", key, "signatureData", sign,
                "requestData", encryptData);
    }

    public static String summary(String msg) {
        byte[] md = new byte[32];
        SM3Digest sm = new SM3Digest();
        sm.update(msg.getBytes(StandardCharsets.UTF_8), 0, msg.getBytes(StandardCharsets.UTF_8).length);
        sm.doFinal(md, 0);
        String s = new String(Hex.encode(md));
        return s.toUpperCase();
    }

    public static String sign(String summaryString, String privateKey) throws IllegalArgumentException, IOException {
        String privateKeys = new String(Base64.encode(NationalSecretsUtils.hexToByte(privateKey)));
        byte[] sign =
                SM2Utils.sign(
                        USER_ID.getBytes(StandardCharsets.UTF_8),
                        Base64.decode(privateKeys.getBytes(StandardCharsets.UTF_8)),
                        NationalSecretsUtils.hexToByte(summaryString));
        return NationalSecretsUtils.getHexString(sign);
    }

    public static boolean verifiedSign(String decryptData, String sign, String publicKey) throws IOException {
        String summary = summary(decryptData);
        boolean verified =
                SM2Utils.verifySign(
                        USER_ID.getBytes(StandardCharsets.UTF_8),
                        NationalSecretsUtils.hexToByte(publicKey),
                        NationalSecretsUtils.hexToByte(summary),
                        NationalSecretsUtils.hexToByte(sign));
        return verified;
    }

}
