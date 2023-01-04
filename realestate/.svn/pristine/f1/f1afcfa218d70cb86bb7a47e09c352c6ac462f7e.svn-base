package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/15
 * @description 盐城市级共享服务
 */
@Service
public class ShijgxServiceImpl {

    /**
     * 日志处理
     */
    private static final Logger logger = LoggerFactory.getLogger(ShijgxServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    /**
     * 客户端ID
     */
    @Value("${shijgx.appid:}")
    private String sjappid;

    /**
     * 应用公钥
     */
    @Value("${shijgx.appKey:}")
    private String sjappKey;

    /**
     * 工信局客户端ID
     */
    @Value("${gxj.appid:}")
    private String gxjAppid;

    /**
     * 工信局应用公钥
     */
    @Value("${gxj.appKey:}")
    private String gxjAppKey;
    /**
     * token缓存时间
     */
    @Value("${shijgx.sign.effective.time:512}")
    private Long effectiveTime;

    /**
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取市级共享sign
     */
    public String getSign(Map<String, String> map) {
        logger.info("开始getSign");
        String signInfo = "";
        String appid = "";
        String appKey = "";
        if (StringUtils.isNotBlank(map.get("beanId")) && "zjjg_tsdbxx".equals(map.get("beanId"))) {
            appid = gxjAppid;
            appKey = gxjAppKey;
//            signInfo = redisUtils.getStringValue(CommonConstantUtils.REDIS_YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY);
            signInfo = redisUtils.getStringValue(CommonConstantUtils.REDIS_YANCHENG_GGJ_INTERFACE_TOKEN_AND_CURRTIME_KEY);

            logger.info("redis中有工信局的token:{}", signInfo);
        } else {
            appid = sjappid;
            appKey = sjappKey;
            //获取当前时间戳
            signInfo = redisUtils.getStringValue(CommonConstantUtils.REDIS_YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY);
            logger.info("redis中有市级的token:{}", signInfo);

        }
        if (StringUtils.isBlank(appid) || StringUtils.isBlank(appKey)) {
            throw new AppException("市级共享客户端ID或应用公钥未配置");
        }

        String currTime = "";
        JSONObject signInfoJson = null;
        if (StringUtils.isNotBlank(signInfo) && isJson(signInfo)) {
            logger.info("redis中有:{}", signInfo);
            signInfoJson = JSON.parseObject(signInfo);
            currTime = signInfoJson.getString("currTime");
        } else {
            logger.info("redis中无");
            currTime = map.get("rtime");
        }
        //获取最终加密后的sign
        String sign = "";
        //获取加密后的sign
        String sign1 = "";
        if (StringUtils.isBlank(currTime)) {
            throw new AppException("redis获取currtime失败请核查YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY rediskey中存放的数据");
        }
        try {
            logger.info("第一次加密");
            sign1 = gatewaySignEncode(appid, appKey, currTime);
        } catch (Exception e) {
            logger.error("sign第一次加密失败,appid:{},appKey:{}", appid, appKey, e);
            throw new AppException("获取sign失败,sign第一次加密失败!");
        }
        //请求token地址
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gateway_appid", appid);
        paramMap.put("gateway_rtime", currTime);
        paramMap.put("gateway_sig", sign1);
        //41898 【盐城】市级接口获取时间戳与签名规则调整 redis获取token信息
        String token = "";
        if (signInfoJson != null && signInfoJson instanceof JSONObject) {
            logger.info("redis：token");
            token = signInfoJson.getString("token");
        }
        if (StringUtils.isBlank(token)) {
            token = exchangeBeanRequestService.request("shijjk_token", paramMap, String.class);
            if (StringUtils.isNotBlank(token)) {
                JSONObject jsonObject = JSON.parseObject(token);
                token = jsonObject.getJSONObject("body").getString("access_token");
            }
            if (StringUtils.isBlank(token)) {
                throw new RuntimeException("获取sign失败,sign第二次加密失败!");
            }
            JSONObject signInfo4SaveRedis = new JSONObject();
            signInfo4SaveRedis.put("currTime", currTime);
            signInfo4SaveRedis.put("token", token);
            if ("zjjg_tsdbxx".equals(map.get("beanId"))) {
//                redisUtils.addStringValue(CommonConstantUtils.REDIS_YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY, signInfo4SaveRedis.toJSONString(), effectiveTime != null && effectiveTime != 0 ? effectiveTime : 512);
                redisUtils.addStringValue(CommonConstantUtils.REDIS_YANCHENG_GGJ_INTERFACE_TOKEN_AND_CURRTIME_KEY, signInfo4SaveRedis.toJSONString(), effectiveTime != null && effectiveTime != 0 ? effectiveTime : 512);
            } else {
                redisUtils.addStringValue(CommonConstantUtils.REDIS_YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY, signInfo4SaveRedis.toJSONString(), effectiveTime != null && effectiveTime != 0 ? effectiveTime : 512);
            }
        }
        if (StringUtils.isNotBlank(token)) {
            //AES解密
            String secretKey = RSAEncryptUtils.AESDecode(appKey, token, "AESCBCPKCS5Paddi");
            //加密
            try {
                sign = gatewaySignEncode(appid, secretKey, currTime);
            } catch (Exception e) {
                logger.error("sign第二次加密失败,appid:{},appKey:{}", appid, appKey, e);
                throw new AppException("获取sign失败,sign第二次加密失败!");
            }
        } else {
            logger.error("请求token失败");
            throw new AppException("获取sign失败,请求token失败!");
        }
        if (StringUtils.isBlank(sign)) {
            logger.error("请求token失败");
            throw new AppException("获取sign失败,请求token失败!");
        }
        return sign;

    }

    public String getRtime(Map<String, String> map) {
        logger.info("开始getRtime");
        String signInfo = "";
        if (StringUtils.isNotBlank(map.get("beanId")) && "zjjg_tsdbxx".equals(map.get("beanId"))) {
            signInfo = redisUtils.getStringValue(CommonConstantUtils.REDIS_YANCHENG_GGJ_INTERFACE_TOKEN_AND_CURRTIME_KEY);
        } else {
            signInfo = redisUtils.getStringValue(CommonConstantUtils.REDIS_YANCHENG_CITY_INTERFACE_TOKEN_AND_CURRTIME_KEY);

        }
        String currTime = "";
        JSONObject signInfoJson = null;
        if (StringUtils.isNotBlank(signInfo) && isJson(signInfo)) {
            logger.info("开始getRtime redis中有:{}", signInfo);
            signInfoJson = JSON.parseObject(signInfo);
            currTime = signInfoJson.getString("currTime");
        } else {
            logger.info("开始getRtime redis中无");
            currTime = map.get("rtime");
        }
        return currTime;
    }

    /**
     * 密钥生成【核心网关】
     *
     * @param appKey 授权码
     * @param appId  请求者标识
     * @param rtime  请求时间戳
     * @return 加密后的sign，即head请求参数的gateway_sig
     */
    public static String gatewaySignEncode(String appId, String appKey,
                                           String rtime) throws Exception {
        String inputString = appId + rtime;
        return encode(appKey, inputString);
    }

    private static String encode(String appKey, String inputStr) throws
            Exception {
        String sign;
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = appKey.getBytes("UTF-8");
        hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length,
                "HmacSHA256"));
        byte[] hmacSha256Bytes = hmacSha256.doFinal(inputStr.getBytes("UTF-8"));
        sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
        return sign;
    }

    /**
     * 判断入参是否是json
     *
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        if (StringUtils.isEmpty(content)) {
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;
        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(content);
        } catch (Exception e) {
            isJsonArray = false;
        }
        if (!isJsonObject && !isJsonArray) { //不是json格式
            return false;
        }
        return true;
    }
}
