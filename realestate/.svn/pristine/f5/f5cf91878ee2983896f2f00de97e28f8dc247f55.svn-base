package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbCommonResponse;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.core.dto.yctb.YctbSginpriKeyInfo;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-06-22
 * @description
 */
@Service(value = "hfyctbPostService")
public class HfYctbInterfaceServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    private static final Logger logger = LoggerFactory.getLogger(HfYctbInterfaceServiceImpl.class);

    @Value("${yctb.interface.head.channel:on-line}")
    private String channel;
    @Value("${yctb.interface.head.version:1.0}")
    private String version;
    @Value("${yctb.interface.test.flag:false}")
    private String testFlag;
    @Value("#{${yctb.qxdm.appid:{'340101': 'hefei'}}}")
    private Map<String, String> appidMap;
    @Value("#{${yctb.qxdm.bmmc:{'340101': 'hefei'}}}")
    private Map<String, String> bmmcMap;
    @Value("#{${yctb.qxdm.bmbm:{'340101': 'hefei'}}}")
    private Map<String, String> bmbmMap;

    // 获取公私钥地址
    @Value("${yctb.get.certificate.url:http://192.167.71.8:8008/getSM2}")
    private String gsyHttpUrl;

    /**
     * 省平台获取公私钥接口
     *
     * @param qxdm      区县代码
     * @param timestamp 时间戳
     * @param type      类型
     * @return YctbSginpriKeyInfo
     */
    public YctbSginpriKeyInfo getSginpriKeyInfo(String qxdm, Integer type) {
        String timestamp = "" + System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", appidMap.get(qxdm));
        // type = 1是签名私钥，2是公钥加密，3是私钥解密
        jsonObject.put("type", type);
        String bodyStr = jsonObject.toJSONString();
        LOGGER.info("开始请求合肥省平台的公私钥接口，请求参数url:{},appid:{},bmbm:{},bmmc:{},channel:{},version:{},qxdm:{}",
                gsyHttpUrl, appidMap.get(qxdm), bmbmMap.get(qxdm), Base64Encoder.encode(bmmcMap.get(qxdm)), channel, version, qxdm);
        Object getCertificateResponse = HttpRequest.post(gsyHttpUrl)
                .header("appid", appidMap.get(qxdm))
                .header("bmbm", bmbmMap.get(qxdm))
                .header("bmmc", Base64Encoder.encode(bmmcMap.get(qxdm)))
                .header("channel", channel)
                .header("method", "getCertificate")
                .header("timestamp", timestamp)
                .header("version", version)
                .header("sign", "")
                .contentType("application/json")
                .body(bodyStr)
                .execute().body();
        String jsonString = JSON.toJSONString(getCertificateResponse);
        // 转义
        jsonString = StringEscapeUtils.unescapeJava(jsonString);
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        logger.info("getSginpriKeyInfo返回:{},type:{}", jsonString, type);
        YctbCommonResponse<YctbSginpriKeyInfo> response = JSON.parseObject(jsonString, new TypeReference<YctbCommonResponse<YctbSginpriKeyInfo>>() {
        });
        if (response != null && response.getCode().equals(200)) {
            return response.getData();
        }
        logger.info("getSginpriKeyInfo失败");
        return null;
    }

    /**
     * 调用http接口获取解密私钥
     */
    public YctbSginpriKeyInfo getsyInfo() {
        // 获取解密私钥，各个区县获取的私钥一样，随意取其中一个区县
        String timestamp = "" + System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", "BaoHeQu");
        // type = 1是签名私钥，2是公钥加密，3是私钥解密
        jsonObject.put("type", 3);
        String bodyStr = jsonObject.toJSONString();
        LOGGER.info("开始请求合肥省平台的公私钥接口，私钥解密，请求参数url:{},appid:{},bmbm:{},bmmc:{},channel:{},version:{}",
                gsyHttpUrl, "BaoHeQu", "BHBDC", Base64Encoder.encode(bmmcMap.get("包河区不动产")), "on-line", "1.0");
        Object getCertificateResponse = HttpRequest.post(gsyHttpUrl)
                .header("appid", "BaoHeQu")
                .header("bmbm", "BHBDC")
                .header("bmmc", Base64Encoder.encode("包河区不动产"))
                .header("channel", "on-line")
                .header("method", "getCertificate")
                .header("timestamp", timestamp)
                .header("version", "1.0")
                .header("sign", "")
                .contentType("application/json")
                .body(bodyStr)
                .execute().body();
        String jsonString = JSON.toJSONString(getCertificateResponse);
        // 转义
        jsonString = StringEscapeUtils.unescapeJava(jsonString);
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        logger.info("getSginpriKeyInfo返回:{},type:{}", jsonString, 3);
        YctbCommonResponse<YctbSginpriKeyInfo> response = JSON.parseObject(jsonString, new TypeReference<YctbCommonResponse<YctbSginpriKeyInfo>>() {
        });
        if (response != null && response.getCode().equals(200)) {
            return response.getData();
        }
        logger.info("获取解密私钥失败");
        return null;
    }

    /**
     * 拼接sign
     *
     * @param data      请求体
     * @param qxdm      区县代码
     * @param timestamp 时间戳
     * @param method    方法
     * @return sign
     */
    private String getSign(String data, String qxdm, String timestamp, String method) {
        StringBuilder str = new StringBuilder();
        str.append("appid").append(appidMap.get(qxdm));
        str.append("bmbm").append(bmbmMap.get(qxdm));
        str.append("bmmc").append(Base64Encoder.encode(bmmcMap.get(qxdm)));
        str.append("channel").append(channel);
        str.append("method").append(method);
        str.append("qxdm").append(qxdm);
        str.append("timestamp").append(timestamp);
        str.append("version").append(version);
        str.append(data);
        return str.toString();
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTmp.toUpperCase());
        }
        return sb.toString();
    }

    //加解密demo
    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:zedq@gtmap.cn">zedq</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        logger.info("hfyctb入参:{},testFlag标签:{}", JSON.toJSONString(builder.getRequestBody()), testFlag);
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
        String method = prop.getMethod();
        String timestamp = "" + System.currentTimeMillis();
        Object requestBody = builder.getRequestBody();
        Map<String, Object> requestParamMap = new HashMap<>();
        if (!(requestBody instanceof List) && !(requestBody instanceof JSONObject)) {
            if (requestBody instanceof Map) {
                requestParamMap = (HashMap) requestBody;
            } else {
                requestParamMap = CommonUtil.objectToMap(requestBody);
            }
        }
        if (requestBody instanceof JSONObject) {
            requestParamMap = CommonUtil.objectToMap(requestBody);
        }
        String qxdm = "";
        if (requestParamMap.containsKey("qxdm")) {
            qxdm = (String) requestParamMap.get("qxdm");
        } else if (StringUtils.isNotEmpty(prop.getQxdm())) {
            qxdm = prop.getQxdm();
        } else {
            throw new AppException("hfyctbPostService 请求异常,入参未传递qxdm参数");
        }
        if ("addTaxDetailandPayMentList".equals(method)) {
            requestParamMap.remove("qxdm");
        }
        //获取加密密钥
        YctbSginpriKeyInfo gyInfo = null;
        YctbSginpriKeyInfo syInfo = null;
        // testFlag = false 加密
        if (!"true".equals(testFlag)) {
            // 获取公钥
            gyInfo = getSginpriKeyInfo(qxdm, 2);
            // 获取签名私钥
            syInfo = getSginpriKeyInfo(qxdm, 1);
        }
        if (gyInfo != null || "true".equals(testFlag)) {
            String dataEncrypt = null;
            String smSign = null;
            if (!"true".equals(testFlag)) {
                logger.info("===============进行加密==============");
                // 公钥的sm2
                logger.info("签名公钥；{}", syInfo.getKey());
                SM2 gySm2 = SmUtil.sm2(null, Base64.decodeBase64(gyInfo.getPubkey()));
                logger.info("requestBody为:{}", JSONUtil.toJsonStr(requestBody));
                //共享接口不需要参数加密，只需要验签
                if(prop.isCsjm()){
                    dataEncrypt = StrUtil.utf8Str(gySm2.encryptHex(JSONUtil.toJsonStr(requestBody), KeyType.PublicKey));
                }else {
                    dataEncrypt = JSONUtil.toJsonStr(requestBody);
                }
                logger.info("dataEncrypt参数；" + dataEncrypt);
                logger.info("签名私钥；{}", syInfo.getKey());
                SM2 sySm2 = SmUtil.sm2(Base64.decodeBase64(syInfo.getKey()), null);
                String sign = getSign(dataEncrypt, qxdm, timestamp, method);
                logger.info("sign参数；" + sign);
                //转成16进制
                String sign16 = HexUtil.encodeHexStr(sign);
                logger.info("sign16参数；" + sign16);
                smSign = sySm2.signHex(sign16);
                logger.info("smSign参数；" + smSign);
            } else {
                logger.info("===============不加密==================");
                if ("addTaxDetailandPayMentList".equals(method)) {
                    dataEncrypt = JSONUtil.toJsonStr(requestParamMap);
                } else {
                    dataEncrypt = JSONUtil.toJsonStr(requestBody);
                }
            }
            String response = "";
            Exception logE = null;
            try {
                if ("true".equals(testFlag)) {
                    logger.info("开始不加密请求，请求参数url：{}，appid：{}，bmbm：{}，bmmc：{}，channel：{}，method：{}，qxdm:{},timestamp:{},version：{}，sign:{},bodyStr:{}",
                            prop.getUrl(), appidMap.get(qxdm), bmbmMap.get(qxdm), Base64Encoder.encode(bmmcMap.get(qxdm)), channel, method, qxdm, timestamp, version, "", dataEncrypt);
                    response = HttpRequest.post(prop.getUrl())
                            .header("appid", appidMap.get(qxdm))
                            .header("bmbm", bmbmMap.get(qxdm))
                            .header("bmmc", Base64Encoder.encode(bmmcMap.get(qxdm)))
                            .header("channel", channel)
                            .header("method", method)
                            .header("qxdm", qxdm)
                            .header("timestamp", timestamp)
                            .header("version", version)
                            .header("sign", "")
                            .contentType("application/json")
                            .body(dataEncrypt)
                            .execute().body();
                } else {
                    logger.info("开始加密请求，请求参数url：{}，appid：{}，bmbm：{}，bmmc：{}，channel：{}，method：{}，qxdm:{},timestamp:{},version：{}，sign:{},   bodyStr:{}",
                            prop.getUrl(), appidMap.get(qxdm), bmbmMap.get(qxdm), Base64Encoder.encode(bmmcMap.get(qxdm)), channel, method, qxdm, timestamp, version, smSign, dataEncrypt);
                    response = HttpRequest.post(prop.getUrl())
                            .header("qxdm", qxdm)
                            .header("method", method)
                            .header("channel", channel)
                            .header("bmbm", bmbmMap.get(qxdm))
                            .header("bmmc", Base64Encoder.encode(bmmcMap.get(qxdm)))
                            .header("appid", appidMap.get(qxdm))
                            .header("version", version)
                            .header("timestamp", timestamp)
                            .header("sign", smSign)
                            .contentType("application/json")
                            .body(dataEncrypt)
                            .execute().body();
                }
                logger.info("hfyctbPostService 返回:{}", response);
            } catch (Exception e) {
                logE = e;
                LOGGER.error("hfyctbPostService 请求异常：url:{} ", prop.getUrl(), e);
                throw new AppException("hfyctbPostService 请求异常");
            } finally {
                try {
                    AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                } catch (Exception e) {
                    LOGGER.error("记录请求日志异常", e);
                }
            }
            logger.info("hfyctbPostService 返回参数；" + response);
            super.setResponseBody(response, builder);
        }
//        String response = testResponse(builder.getExchangeBean().getId());
//        super.setResponseBody(response, builder);
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     *
     * @param response
     * @return
     */
    @Override
    public String dealWithResponse(String response) {
        return response;
    }

    private String testResponse(String beanid) {
        String response = "";
        if (StringUtils.equals(beanid, "bwgxjk_sfgzws")) {
            response = "{\n" +
                    "\"code\": 200,\n" +
                    "\"msg\": \"返回成功！\",\n" +
                    "\"data\": {\n" +
                    "\"msg\": \"请求成功\",\n" +
                    "\"code\": 200,\n" +
                    "\"data\": \"{\\\"XGSJ_1\\\":\\\"\\\",\\\"RECORD_VERSION_0\\\":\\\"\\\",\\\"SPRZYZBH_1\\\":\\\"\\\",\\\"pageSize\\\":\\\"1\\\",\\\"DJSJ_1\\\":\\\"\\\",\\\"BZGZSBH_1\\\":\\\"\\\",\\\"JAFS_1\\\":\\\"\\\",\\\"currentPageNo\\\":\\\"1\\\",\\\"UPDATED_BY_USER_0\\\":\\\"\\\",\\\"DELSTATUS_1\\\":\\\"\\\",\\\"GZYZBH_1\\\":\\\"\\\",\\\"totalRowCount\\\":\\\"1\\\",\\\"IS_HAND_1\\\":\\\"\\\",\\\"UPDATED_OFFICE_0\\\":\\\"\\\",\\\"UPDATED_DTM_LOC_0\\\":\\\"\\\",\\\"BJRQ_1\\\":\\\"2018-09-\n" +
                    "03 00:00:00.000000\\\",\\\"GZYID_1\\\":\\\"\\\",\\\"datas\\\":\\\"\\\",\\\"GZSBH_1\\\":\\\"（2018）徐睢证民内字第 1702 号\n" +
                    "\\\",\\\"ID_1\\\":\\\"\\\",\\\"SPR_1\\\":\\\"\\\",\\\"GGCS_1\\\":\\\"\\\",\\\"BZGZSBHHZ_1\\\":\\\"\\\",\\\"GZCMC_1\\\":\\\"江苏省睢宁县公证处\n" +
                    "\\\",\\\"GZSXGLXX_1\\\":\\\"\\\",\\\"CJSJ_1\\\":\\\"\\\",\\\"code\\\":\\\"DAS00000\\\",\\\"DJBLSH_1\\\":\\\"\\\",\\\"PERSON_NAME_0\\\":\\\"潘甜甜\n" +
                    "\\\",\\\"GZSBHHZ_1\\\":\\\"\\\",\\\"REGISTER_PERSON_ID_0\\\":\\\"\\\",\\\"GZYXM_1\\\":\\\"吕姗姗\\\",\\\"CERTIFICATE_TYPE_0\\\":\\\"\\\",\\\"SFBZJ_1\\\":\\\"\\\",\\\"totalPageCount\\\":\\\"1\\\",\\\"BZGZSBHND_1\\\":\\\"\\\",\\\"CREATED_DTM_LOC_0\\\":\\\"\\\",\\\"CREATED_OFFICE_0\\\":\\\"\\\",\\\"CERTIFICATE_NUM_0\\\":\\\"320324199705110625\\\",\\\"NATURE_TYPE_0\\\":\\\"\\\",\\\"GZSXMC_1\\\":\\\"其他合同（协议）\n" +
                    "15\n" +
                    "\\\",\\\"SF_1\\\":\\\"\\\",\\\"PERSON_TYPE_0\\\":\\\"\\\",\\\"REGISTER_FILE_ID_0\\\":\\\"\\\",\\\"LCZT_1\\\":\\\"\\\",\\\"GZSBHND_1\\\":\\\"\\\",\\\"message\\\":\\\"服务访问成功\n" +
                    "\\\",\\\"GZSXBH_1\\\":\\\"\\\",\\\"CREATED_BY_USER_0\\\":\\\"\\\",\\\"queryResponse\\\":\\\"\\\",\\\"GZCID_1\\\":\\\"\\\",\\\"SLRQ_1\\\":\\\"\\\",\\\"return\\\":\\\"\\\",\\\"NOTARY_ASSOCIATION_ID_0\\\":\\\"\\\",\\\"GJLX_1\\\":\\\"\\\"}\"\n" +
                    "}\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "bwgxjk_wjwcsyxzm")) {
            response = "{\n" +
                    "    \"code\":200,\n" +
                    "    \"msg\":\"返回成功！\",\n" +
                    "    \"data\":{\n" +
                    "        \"msg\":\"出生证明查询接口调用成功!\",\n" +
                    "        \"code\":200,\n" +
                    "        \"data\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF8\\\"?><MDEML templateVersion=\\\"1.0\\\"><resultinfo><success>0</success><message>成功</message></resultinfo><body><data><babyName>徐**</babyName><babySexCode>2</babySexCode><babySex>女</babySex><birthArea>湖南省邵阳市大祥区</birthArea><birthTime>2018-04-11 13:34:00</birthTime><birthCode>R43054****</birthCode><momName>岳*</momName><momIdCode>430502********2021</momIdCode><dadName>徐**</dadName><dadIdCode>320882********2410</dadIdCode></data></body></MDEML>\"\n" +
                    "    }\n" +
                    "}";
        }
        return response;
    }

}
