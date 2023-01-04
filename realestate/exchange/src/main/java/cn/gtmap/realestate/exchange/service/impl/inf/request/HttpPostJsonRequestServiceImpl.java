package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpPostJsonPropBO;
import cn.gtmap.realestate.exchange.service.impl.inf.standard.ReplaceUrlByQxdmServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description
 */
@Service(value = "httpPostJson")
public class HttpPostJsonRequestServiceImpl extends InterfaceRequestService<HttpPostJsonPropBO> {
    @Autowired
    private HttpClientService httpClientService;
    /**
     * 外网申请通知地址，如果是https的，证书的密码
     */
    @Value("${wwsq.https.zsmm:}")
    private String zsmm;
    /**
     * 外网申请通知地址，如果是https的，证书的域名
     */
    @Value("${wwsq.https.zsym:}")
    private String zsym;

    /**
     * 合肥婚姻合并接口，默认抛出异常不再继续查询，配置为false可忽略异常，继续后续查询
     */
    @Value("${hyxxcx.throw.exception:true}")
    private String hyxxcxThrowException;

    @Value("${data.version:}")
    private String dataVersion;

    /**
     * 根据区县代码替换请求url
     */
    @Autowired
    private ReplaceUrlByQxdmServiceImpl replaceUrlByQxdmService;

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        LOGGER.info("请求requestBody:" + JSONObject.toJSONString(requestBody));
        HttpPostJsonPropBO prop = super.getRequestPropFromBuilder(builder);
        if (prop.isParamRequired() && !CheckParameter.checkAnyParameter(requestBody)) {
            return;
        }
        if (StringUtils.isNotBlank(prop.getUrl())) {
            String requestUrl = prop.getUrl();
            // 替换请求地址,根据requestBody里的qxdm获取配置的请求地址
            if (StringUtils.isNotBlank(prop.getReplaceUrl())
                    && StringUtils.isNotBlank(prop.getReplaceUrlHandlerType())
                    && prop.getReplaceUrlHandlerType().equals("ReplaceUrlByQxdmServiceImpl")
            ) {
                String newUrl = replaceUrlByQxdmService.getNewUrl(requestBody, prop.getReplaceUrl());
                //如果能取到新的url则替换
                if (StringUtils.isNotBlank(newUrl)) {
                    requestUrl = newUrl;
                }
            }
            LOGGER.info("拿到的URL配置是：{}",requestUrl);
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            httpPost.setHeader("unitCode", "LandResourcesBureau");
            if (StringUtils.isNotBlank(prop.getAppKey())) {
                httpPost.setHeader("appKey", prop.getAppKey());
            }

            String parameter = "";
            if ("JFPT_CZ".equals(prop.getDsfFlag())) {
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(requestBody));
                LOGGER.info("请求参数：json{}" + json.toJSONString());
                String data = json.get("param").toString();
//                parameter = JSONObject.toJSONString(data.toString());
                parameter = JSONObject.toJSON(data).toString();
                LOGGER.info("请求参数处理后：" + parameter);
            } else {
                parameter = JSONObject.toJSONString(requestBody);

            }
//            String parameter = JSONObject.toJSONString(requestBody);
            LOGGER.info("请求参数：" + parameter);
            // 判断是否需要加密
            if (StringUtils.isNotBlank(prop.getEncryptMethod())) {
                parameter = enOrDecrypt(prop.getEncryptMethod(), parameter);
            }
            StringEntity entity = new StringEntity(parameter, Charset.forName("UTF-8"));
            httpPost.setEntity(entity);
            if (StringUtils.isNotBlank(prop.getSoTimeout()) && NumberUtils.isNumber(prop.getSoTimeout())) {
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(NumberUtils.toInt(prop.getSoTimeout())).build();
                LOGGER.error("httpPost 请求url：{}，参数配置:{}", requestUrl, requestConfig.toString());
                httpPost.setConfig(requestConfig);
                LOGGER.info("http请求配置：{}",JSONObject.toJSONString(requestConfig));
            }
            String response = "";
            Exception logE = null;
            String paramLog = JSONObject.toJSONString(requestBody);
            if (StringUtils.isNotBlank(paramLog) && paramLog.length() <= 5000) {
                LOGGER.debug("httpPost beanId:{},param:{}", builder.getExchangeBean().getId(), JSONObject.toJSONString(requestBody));
            }
            try {
                if (StringUtils.isNoneBlank(zsmm, zsym) && StringUtils.contains(requestUrl, zsym)) {
                    response = httpClientService.doHttpsPost(httpPost, zsmm, zsym);
                } else {
                    response = httpClientService.doPost(httpPost, "UTF-8");
                    // 部级婚姻接口返回样例
                    //response = "{\"count\":1,\"rows\":[{\"dept_code\":\"34120201\",\"name_woman\":\"岳梦梦\",\"cert_no\":\"J341202-2017-008280\",\"cert_num_woman\":\"341202199412102120\",\"nation_woman\":\"156\",\"dept_name\":\"阜阳市颍州区民政局婚姻登记处\",\"op_date\":\"2017-08-04 00:00:00\",\"birth_woman\":\"1994-12-10 00:00:00\",\"cert_num_man\":\"341227199102254417\",\"birth_man\":\"1991-02-25 00:00:00\",\"nation_man\":\"156\",\"op_type\":\"IA\",\"name_man\":\"巩志强\"}]}";
                }
                // 判断是否需要解密
                if (StringUtils.isNotBlank(prop.getDecryptMethod())) {
                    response = enOrDecrypt(prop.getDecryptMethod(), response);
                }
            } catch (Exception e) {
                logE = e;
                //判断是合肥婚姻合并接口，根据hyxxcxThrowException=falze可不抛异常
                Map<String, Object> requestInfoMap = builder.getRequestInfo();
                String flag = "";
                if (requestInfoMap != null) {
                    flag = (String)requestInfoMap.get("hyjkFlag");
                }
                if(StringUtils.equals(dataVersion, CommonConstantUtils.SYSTEM_VERSION_HF) && StringUtils.isNotBlank(flag)
                        && StringUtils.equals(flag, "BJHYJK") && !Boolean.parseBoolean(hyxxcxThrowException)){
                    LOGGER.error("部级婚姻信息查询请求异常！");
                    LOGGER.error("请求地址：{},请求参数：{}",requestUrl, JSONObject.toJSONString(requestBody), e);
                    LOGGER.error("忽略异常，hyxxcxThrowException：{}", Boolean.parseBoolean(hyxxcxThrowException));
                }else{
                    LOGGER.error("httpPost 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                    throw new AppException("httpPost 请求异常");
                }

            } finally {
                try {
                    AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                    auditEventBO.setRequest(JSONObject.toJSONString(requestBody));
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                } catch (Exception e) {
                    LOGGER.error("记录请求日志异常", e);
                }
            }
//            response = testResponse(builder);
            LOGGER.debug("httpPost response:{}", response);
            String beanId = builder.getExchangeBean().getId();
            if ("swlpb_url".equals(beanId)) {
                JSONObject newResponse = new JSONObject();
                newResponse.put("url", response);
                response = JSONObject.toJSONString(newResponse);

            }
            super.setResponseBody(response, builder);
        }
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     *
     * @param response
     * @return
     */
    @Override
    public String dealWithResponse(String response) {return response;}


    /**
     * @param methodProp, param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 加密或解密方法
     */
    private static String enOrDecrypt(String methodProp, String param) {
        String resultParam = param;
        Object result = CommonUtil.executeStaticMethod(methodProp, param);
        if (result != null) {
            resultParam = result.toString();
        }
        return resultParam;
    }


    /**
     * @param builder
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 测试数据
     */
    private static String testResponse(InterfaceRequestBuilder builder) {
        String response = "";
        String beanId = builder.getExchangeBean().getId();
        response = "{\n \"body\": {\n   \"crfjswsxq\": [\n     {\n       \"djxh\": \"20123400100010157617\",\n       \"jyfe\": \"100.0\",\n       \"nsrmc\": \"王户加\",\n       \"nsrsbh\": \"340122195812130332\",\n       \"se\": \"0.0\",\n       \"zspmDm\": \"101090101\",\n       \"zsxmDm\": \"10109\",\n       \"zszmDm\": \"\"\n     },\n     {\n       \"djxh\": \"20123400100010157617\",\n       \"jyfe\": \"100.0\",\n       \"nsrmc\": \"王户加\",\n       \"nsrsbh\": \"340122195812130332\",\n       \"se\": \"0.0\",\n       \"zspmDm\": \"101017714\",\n       \"zsxmDm\": \"10101\",\n       \"zszmDm\": \"\"\n     }\n   ],\n   \"fwdz\": \"大通路116号3幢202室\",\n   \"message\": \"验证通过\",\n   \"srfjswsxq\": [\n     {\n       \"djxh\": \"20123400000004599511\",\n       \"jyfe\": \"100.0\",\n       \"nsrmc\": \"李燕\",\n       \"nsrsbh\": \"342425197907085542\",\n       \"se\": \"4665.0\",\n       \"zspmDm\": \"101191211\",\n       \"zsxmDm\": \"10119\",\n       \"zszmDm\": \"\"\n     }\n   ],\n   \"wsqkjyjg\": \"1\"\n },\n \"head\": {\n   \"channel_id\": \"AHxxBDC\",\n   \"rtn_code\": \"200\",\n   \"rtn_msg\": \"成功\",\n   \"token\": \"GJSWZJAHSSWJ\",\n   \"tran_date\": \"20191127\",\n   \"tran_id\": \"ahsw.fcjy.yth.jswsyz\",\n   \"tran_seq\": \"3BMG4757DZOC069I\"\n }\n" +
                "}";
        if ("swsys".equals(beanId)) {
            response = "{\n\"body\": {\n  \"syslb\": [\n    {\n      \"dzsphm\": \"320180918000000039\",//税票号码\n      \"fcxx_id\": \"房产信息id\",\n      \"nsrsbh\": \"纳税人识别号\",\n      \"swjgDm\": \"税务机关代码\",\n      \"szxxLb\": { //该节点的值返回给一体化，自行决定是否需要\n        \"szxxGridlb\": [\n          {\n            \"czlxDm\": \"24\",\n            \"djxh\": \"20123400100010697653\",\n            \"djzclxDm\": \"431\",\n            \"dzsphm\": \"320180918000000039\",\n            \"dzspmxxh\": 0,\n            \"gldzspmxxh\": 0,\n            \"hyDm\": \"7040\",\n            \"jdxzDm\": \"340207007\",\n            \"jkqx\": \"2018-10-18\",\n            \"jsyj\": 997007.62,\n            \"kjrq\": \"2018-09-18 16:33:20\",\n            \"kssl\": 135.04,\n            \"pzzlDm\": \"000006010\",\n            \"sbfsDm\": \"32\",\n            \"sjje\": 14955.11,\n            \"skgkDm\": \"2340207001\",\n            \"skjnfsDm\": \"73\",\n            \"skssqq\": \"2018-09-18\",\n            \"skssqz\": \"2018-09-18\",\n            \"skssswjgDm\": \"23402070000\",\n            \"sksxDm\": \"0101\",\n            \"skzlDm\": \"10\",\n            \"sl1\": 0.04,\n            \"spuuid\": \"BDB93957EC58891E2D28AFAA1C91C0F9\",\n            \"ssglyDm\": \"23402220080\",\n            \"sybh1\": \"F34020220180000136\",\n            \"tzlxDm\": \"1\",\n            \"yjkqx\": \"2018-10-18\",\n            \"yjse\": 0.0,\n            \"ysfpblDm\": \"23440001\",\n            \"yskmDm\": \"1011901\",\n            \"zgswskfjDm\": \"23402071200\",\n            \"zsdlfsDm\": \"0\",\n            \"zsfsDm\": \"900\",\n            \"zspmDm\": \"101191111\",\n            \"zsswjgDm\": \"23402960000\",\n            \"zsuuid\": \"ADA7C7DE8968345540ACB39FD5E36312\",\n            \"zsxmDm\": \"10119\"\n          }\n        ]\n      },\n      \"yjse\": 14955.11\n    }\n  ]\n},\n\"head\": {\n  \"channel_id\": \" AHxxBDC\",\n  \"rtn_code\": \"200\", //200为成功，其他为失败\n  \"rtn_msg\": \"成功\",\n  \"tran_date\": \"20180919\",\n  \"tran_seq\": \"9d012e134185473daced37c1090df6d2\"\n}\n}";
        }
        if ("swTsFjcl".equals(beanId)) {
            response = "{\n\"head\": {\n  \"channel_id\": \"AHxxBDC\",\n  \"rtn_code\": \"200\", \n  \"rtn_msg\": \"成功\",\n  \"tran_date\": \"20181106\",\n  \"tran_seq\": \"9b5abd7c537a41febfd3e9784c291e62\"\n}\n}";
        }
        if ("tsSwxxSpf".equals(beanId)) {
            // 1.不能及时返回税款的情况
            response = "{\n\"body\": {\n  \"htbh\": \"HT234xxx2\",\n  \"jyuuid\": \"b99bccdefa08484c826165316368f607\",\n  \"ywsldh\": \"2018111300005\"\n},\n\"head\": {\n  \"channel_id\": \"AHxxBDC\",\n  \"rtn_code\": \"200\",\n  \"rtn_msg\": \"成功\",\n  \"tran_date\": \"20181119\",\n  \"tran_id\": \"ahsw.fcjy.yth.fcjyclfcj\",\n  \"tran_seq\": \"4bc12232e7907b56a6006ad3dba4\"\n}\n} ";

            // 能够及时返回税款的情况：
            response = "{\n\"body\": {\n  \"htbh\": \"HT2343242\",\n  \"jyuuid\": \"b99bccdefa08484c826165316368f607\",\n  \"sbmxcxlb\": [\n    {\n      \"cjjg\": 900000,\n      \"fwwzdz\": \"房屋完整地址\",\n      \"jyskblb\": [\n        {\n          \"fcxxId\": \"b99bccdefa08484c826165316368f607\",\n          \"jsje\": 0,\n          \"jyfe\": 0,\n          \"mmfbz\": \"1\",\n          \"nsrid\": \"4a9f8000cd59465eb05885b4daef034f\",\n          \"se\": 0,\n          \"sl\": 0.01,\n          \"zsxmDm\": \"10119\",\n          \"zsxmmc\": \"契税\"\n        }\n      ],\n      \"jyuuid\": \"b99bccdefa08484c826165316368f607\",\n      \"jzmj\": \"90\",\n      \"mmfbz\": \"1\",\n      \"nsrmc\": \"xxxx\",\n      \"nsrsbh\": \"341101xxxx01040567\",\n      \"ynse\": 0\n    },\n    {\n      \"cjjg\": 900000,\n      \"fwwzdz\": \"房屋完整地址\",\n      \"jyskblb\": [\n        {\n          \"fcxxId\": \"b99bccdefa08484c826165316368f607\",\n          \"jsje\": 0,\n          \"jyfe\": 0,\n          \"mmfbz\": \"0\",\n          \"nsrid\": \"c8e0d163092c4bb7add753e9ed2aaa6e\",\n          \"se\": 0,\n          \"sl\": 0.05,\n          \"zsxmDm\": \"10101\",\n          \"zsxmmc\": \"增值税\"\n        },\n        {\n          \"fcxxId\": \"b99bccdefa08484c826165316368f607\",\n          \"jsje\": 0,\n          \"jyfe\": 0,\n          \"mmfbz\": \"0\",\n          \"nsrid\": \"c8e0d163092c4bb7add753e9ed2aaa6e\",\n          \"se\": 0,\n          \"sl\": 0.02,\n          \"zsxmDm\": \"30216\",\n          \"zsxmmc\": \"地方教育附加\"\n        },\n        {\n          \"fcxxId\": \"b99bccdefa08484c826165316368f607\",\n          \"jsje\": 0,\n          \"jyfe\": 0,\n          \"mmfbz\": \"0\",\n          \"nsrid\": \"c8e0d163092c4bb7add753e9ed2aaa6e\",\n          \"se\": 0,\n          \"sl\": 0.07,\n          \"zsxmDm\": \"10109\",\n          \"zsxmmc\": \"城市维护建设税\"\n        },\n        {\n          \"fcxxId\": \"b99bccdefa08484c826165316368f607\",\n          \"jsje\": 0,\n          \"jyfe\": 0,\n          \"mmfbz\": \"0\",\n          \"nsrid\": \"c8e0d163092c4bb7add753e9ed2aaa6e\",\n          \"se\": 0,\n          \"sl\": 0.01,\n          \"zsxmDm\": \"10106\",\n          \"zsxmmc\": \"个人所得税\"\n        },\n        {\n          \"fcxxId\": \"b99bccdefa08484c826165316368f607\",\n          \"jsje\": 0,\n          \"jyfe\": 0,\n          \"mmfbz\": \"0\",\n          \"nsrid\": \"c8e0d163092c4bb7add753e9ed2aaa6e\",\n          \"se\": 0,\n          \"sl\": 0.03,\n          \"zsxmDm\": \"30203\",\n          \"zsxmmc\": \"教育费附加\"\n        }\n      ],\n      \"jyuuid\": \"b99bccdefa08484c826165316368f607\",\n      \"jzmj\": \"90\",\n      \"mmfbz\": \"0\",\n      \"nsrmc\": \"李xxxx\",\n      \"nsrsbh\": \"341001xxxx02050875\",\n      \"ynse\": 0\n    }\n  ],\n  \"ywsldh\": \"2018111300005\"\n},\n\"head\": {\n  \"channel_id\": \"AHxxBDC\",\n  \"rtn_code\": \"200\",\n  \"rtn_msg\": \"成功\",\n  \"tran_date\": \"20181119\",\n  \"tran_id\": \"ahsw.fcjy.yth.fcjyclfcj\",\n  \"tran_seq\": \"4bc12232e7907b56a6006ad3dba4\"\n}\n}";
        }

        if ("bjjk_zbb".equals(beanId)) {
            response = "{\n\"data\": \"uAWIe7v44WRLwtdaHeJxnkr9FhCxeh7cVOhBHtLDsVaFw02k7Y6RvvC5fkdZIMdmaLD58KZcnFTN2YchmNFRFE9Hirr-4_FS0ik7CEoTe3kEdGV3C9y3T_C9C4t29Cq2X6dYTff-WWcfm_Ff7Wgnt5flgJgyfUhSsQnLlxrX9xkh2-mWZ7ODAE4wXJcxwVCISlscE8spOmLgPZGOTrfSLwih1j2VZPl7MPAqwOESa7OQdJ_EvGI0SHiOf-a8swZWg79ol7bjOfRAecjbcdxGNwKaFV7s_ZfZr7RP3haThcV-RtCx70fpjNtVz8hMjndLstlDxmMZCWx3fir59EvXy2KSI6RtM9UN8_colWxkbHOuRKjolBGvxoVCPkvdQbE4UJoKI1pZjVbd-Re95-7GR4UzCCPyIry9BzXBn0iJGnvkRa9V6n6g7qrk8vPvbXTJnfm3z1_yktU6XXMtiIzpeus5a2DwnCwbJ6w9M_IhdzzcNdi9Pdw5a0vwcRYwUuGl\",\n\"head\": {\n  \"businessNumber\": \"21\",\n  \"cxqqdh\": \"20190909120102000005\",\n  \"msg\": \"正常\",\n  \"requestType\": \"ZB01\",\n  \"status\": \"0200\"\n}\n}";
        }
        if ("fgf_cxxwjg".equals(beanId)) {
            response = "{\n\t\"yxcode\": 200,\n\t\"yxdata\": \"2\",\n    \"yxms\":\"\"\n}";
        }
        if ("bjjk_sfpjcxfk".equals(beanId)) {
        }
        if ("nt_swspfj".equals(beanId)) {
            response = "{\n\"suc\": \"1\",\n\"msg\": \"查询成功\",\n\"data\": [\n  {\n    \"pzhm\": \"332021200100050607\",\n    \"file\": \"base64编码\"\n  },\n  {\n    \"pzhm \": \"332021200100050608 \",\n    \"file\": \"base64编码\"\n  }\n]\n}";
        }
        if ("nt_spfjyxx".equals(beanId)) {
            response = "[{\n\t\"agentname\": \"江苏炜赋集团建设开发有限公司\",\n\t\"bz\": \"\",\n\t\"ckid\": \"100002399598\",\n\t\"ckprice\": \"4920\",\n\t\"fsjz\": \"\",\n\t\"fsjzmj\": \"\",\n\t\"fwcx\": \"\",\n\t\"fwdy\": \"\",\n\t\"fwdz\": \"\",\n\t\"fwfh\": \"B104\",\n\t\"fwid\": \"100002399598\",\n\t\"fwxz\": \"低价位商品房\",\n\t\"fwyt\": \"半地下2米2以上车库或车位\",\n\t\"fwzh\": \"27\",\n\t\"glid\": \"\",\n\t\"glprice\": \"0\",\n\t\"gmfadd\": \"\",\n\t\"gmfgj\": \"\",\n\t\"gmfmc\": \"项建彬\",\n\t\"gmftel\": \"\",\n\t\"gmfzjhm\": \"320624196311269230\",\n\t\"gmfzjlx\": \"身份证\",\n\t\"htbh\": \"001020150116039\",\n\t\"htjg\": \"4920\",\n\t\"htqdrq\": \"2015-08-03 15:49:13.0\",\n\t\"jx\": \"\",\n\t\"jzmj\": \"9.84\",\n\t\"spfid\": \"100002400047\",\n\t\"spfprice\": \"145044\",\n\t\"tyshxydm\": \"\",\n\t\"xm\": \"\",\n\t\"xmmc\": \"龙田花苑\",\n\t\"xzq\": \"开发区\",\n\t\"yyzzzch\": \"3206911100592\",\n\t\"zjhm\": \"\"\n}, {\n\t\"agentname\": \"江苏炜赋集团建设开发有限公司\",\n\t\"bz\": \"\",\n\t\"ckid\": \"100002399598\",\n\t\"ckprice\": \"4920\",\n\t\"fsjz\": \"\",\n\t\"fsjzmj\": \"\",\n\t\"fwcx\": \"\",\n\t\"fwdy\": \"\",\n\t\"fwdz\": \"这是地址\",\n\t\"fwfh\": \"501\",\n\t\"fwid\": \"100002400047\",\n\t\"fwxz\": \"低价位商品房\",\n\t\"fwyt\": \"住宅\",\n\t\"fwzh\": \"27\",\n\t\"glid\": \"\",\n\t\"glprice\": \"0\",\n\t\"gmfadd\": \"\",\n\t\"gmfgj\": \"\",\n\t\"gmfmc\": \"项建彬\",\n\t\"gmftel\": \"身份证\",\n\t\"gmfzjhm\": \"320624196311269230\",\n\t\"gmfzjlx\": \"\",\n\t\"htbh\": \"001020150116039\",\n\t\"htjg\": \"145044\",\n\t\"htqdrq\": \"2015-08-03 15:49:13.0\",\n\t\"jx\": \"\",\n\t\"jzmj\": \"128.54\",\n\t\"spfid\": \"100002400047\",\n\t\"spfprice\": \"145044\",\n\t\"tyshxydm\": \"\",\n\t\"xm\": \"\",\n\t\"xmmc\": \"龙田花苑\",\n\t\"xzq\": \"开发区\",\n\t\"yyzzzch\": \"3206911100592\",\n\t\"zjhm\": \"\"\n}]";
        }
        if ("tsjkrk".equals(beanId)) {
            response = "{\n\"head\": {\n  \"regionCode\": \"地区编码\",\n  \" orgid \": \"机构编码\",\n  \" statusCode \": \"响应状态码\",\n  \" msg \": \"响应信息\"\n},\n\"data\": {\n  \"tsjg\": \"1\"\n}\n}";
        }
        if ("fcjySpfBaxx".equals(beanId)) {
            response = "{\n\"HEADER\": {\n  \"RET_CODE\": \"00000000\",\n  \"RET_MSG\": \"查询成功\"\n},\n\"BODY\": {\n  \"HTBH\": \"53670386139F495089D3\",\n  \"CMR\": \"常州中海置业有限公司\",\n  \"CMRZJZL\": \"身份证\",\n  \"CMRZJH\": \"91320400W1AXD9WQB4\",\n  \"MSRMC\": \"张三\",\n  \"MSRZJZL\": \"身份证\",\n  \"MSRZJH\": \"320482199210027952\",\n  \"MSRLXDZ\": \"江苏省常州市新北区传媒中心5号楼1108\",\n  \"MSRLXDH\": \"13951226580\",\n  \"WTDLR\": \"张小凡\",\n  \"WTDLRZJZL\": \"身份证\",\n  \"WTDLRZJH\": \"320482199210028525\",\n  \"FWBHLX\": \"1\",\n  \"ZL\": \"中海凤凰熙岸21幢乙单元403\",\n  \"JZMJ\": \"89.94\",\n  \"TNJZMJ\": \"68.84\",\n  \"FTJZMJ\": \"21.1\",\n  \"HTFWM\": \"\",\n  \"BASJ\": \"2019-06-06\",\n  \"ZJK\": \"950000\",\n  \"DZHT\": \"\",\n  \"BZ\": \"\"\n}\n" +
                    "}";
        }
        if ("fcjyClfHtxx".equals(beanId)) {
            response = "{\n\"HEADER\": {\n  \"RET_CODE\": \"00000000\",\n  \"RET_MSG\": \"查询成功\"\n},\n\"BODY\": {\n  \"htbh\": \"53670386139F495089D3\",\n  \"xzqy\": \"武进区\",\n  \"qlrxx\": [\n    {\n      \"qlrlb\": \"1\",\n      \"qlrmc\": \"李三,李四\",\n      \"qlrzjzl\": \"身份证\",\n      \"qlrzjh\": \"320481199006112365,320402199311060746\",\n      \"qlrlxdh\": \"13951226580,13965884732\",\n      \"qlrlxdz\": \"江苏省常州市新北区传媒中心5号楼1101\",\n      \"qlrdlr\": \"李五\",\n      \"qlrdlrzjzl\": \"身份证\",\n      \"qlrdlrzjh\": \"320483199007162365\"\n    },\n    {\n      \"qlrlb\": \"2\",\n      \"qlrmc\": \"张三,张四\",\n      \"qlrzjzl\": \"身份证\",\n      \"qlrzjh\": \"320482199210025841,320482199110301574\",\n      \"qlrlxdh\": \"13585140365,13401356985\",\n      \"qlrlxdz\": \"江苏省常州市新北区传媒中心5号楼1108\",\n      \"qlrdlr\": \"张五\",\n      \"qlrdlrzjzl\": \"身份证\",\n      \"qlrdlrzjh\": \"320404199204265841\"\n    }\n  ],\n  \"cqzh\": \"苏(2019)常州市不动产权第0012345号\",\n  \"cqr\": \"张三,张四\",\n  \"zl\": \"中海凤凰熙岸21幢乙单元403\",\n  \"jzmj\": \"89.94\",\n  \"fj\": \"\",\n  \"tdzh\": \"\",\n  \"cjje\": \"1000000\",\n  \"htfwm\": \"\",\n  \"basj\": \"2019-06-06\",\n  \"zjjgzt\": \"1\",\n  \"dzht\": \"\",\n  \"bz\": \"\"\n}\n" +
                    "}";
        }
        if ("ntyth_clfwqxx_zh".equals(beanId)) {
            response = "{\n\"mmqy\": [\n  {\n    \"wqMmqyId\": \"\",\n    \"jjbh\": \"\",\n    \"csgpdjbbh\": \"\",\n    \"msgpdjbbm\": \"\",\n    \"tgfs\": \"\",\n    \"fwje\": \"111111\",\n    \"fwJe\": \"\",\n    \"glJe\": \"\",\n    \"ckJe\": \"\",\n    \"cwJe\": \"\",\n    \"isJg\": \"\",\n    \"fifthZffs\": \"\",\n    \"fifthSf\": \"\",\n    \"fifthDk\": \"\",\n    \"firstZfDate\": \"\",\n    \"firstZfJe\": \"\",\n    \"secondZfDate\": \"\",\n    \"secondZfJe\": \"\",\n    \"thirdZfDate\": \"\",\n    \"thirdZfJe\": \"\",\n    \"fourthZfDate\": \"\",\n    \"fourthZfJe\": \"\",\n    \"sixRemark\": \"\",\n    \"ghDate\": \"\",\n    \"jfDate\": \"\",\n    \"wyj\": \"\",\n    \"baRemark\": \"\",\n    \"zyclfs\": \"\",\n    \"syiRemark\": \"\",\n    \"wtRen\": \"\",\n    \"cmfqdDate\": \"\",\n    \"msfqdDate\": \"\",\n    \"banm\": \"\",\n    \"fssbRemark\": \"\",\n    \"zxqkRemark\": \"\",\n    \"jdljg\": \"\",\n    \"jdljgLxdh\": \"\",\n    \"jdljgDate\": \"\",\n    \"jdljgQzdd\": \"\",\n    \"qyStatus\": \"\",\n    \"qybh\": \"2222\",\n    \"passDate\": \"\",\n    \"isZcq\": \"\",\n    \"sectId\": \"\",\n    \"qyrId\": \"\",\n    \"qyrName\": \"\",\n    \"htbah\": \"\",\n    \"createDate\": \"2020-09-09 15:23:23\",\n    \"djDate\": \"\",\n    \"cmrStr\": \"\",\n    \"msrStr\": \"\",\n    \"fwzlStr\": \"\",\n    \"fwqyStr\": \"\",\n    \"syqzhStr\": \"\",\n    \"shulouFlag\": \"\",\n    \"dkFlag\": \"\",\n    \"bdcPercent\": \"\",\n    \"fourthQtyd\": \"\",\n    \"dyzrFlag\": \"\",\n    \"sevenDyqr\": \"\",\n    \"sevenDyje\": \"\",\n    \"bdcdjzmh\": \"\",\n    \"dyyh\": \"\",\n    \"dkyh\": \"\",\n    \"isZjdz\": \"\",\n    \"jglx\": \"\",\n    \"szqy\": \"\",\n    \"dyyhbm\": \"\",\n    \"htType\": \"\",\n    \"house\": [\n      {\n        \"wqMmqyHouId\": \"\",\n        \"wqMmqyId\": \"\",\n        \"fwqy\": \"\",\n        \"fwzl\": \"\",\n        \"dh\": \"\",\n        \"zh\": \"\",\n        \"houNo\": \"\",\n        \"syqzh\": \"\",\n        \"tdsyh\": \"\",\n        \"tdhqfs\": \"\",\n        \"houArea\": \"\",\n        \"gelou\": \"\",\n        \"cheku\": \"\",\n        \"zxcheku\": \"\",\n        \"fwyt\": \"\",\n        \"jzjk\": \"\",\n        \"jcnf\": \"\",\n        \"fgfmj\": \"\",\n        \"je\": \"\",\n        \"houId\": \"\"\n      }\n    ],\n    \"person\": [\n      {\n        \"wqMmqyPersonId\": \"1\",\n        \"wqMmqyId\": \"1\",\n        \"personType\": \"1\",\n        \"name\": \"1\",\n        \"national\": \"1\",\n        \"idCard\": \"1\",\n        \"address\": \"1\",\n        \"tel\": \"1\",\n        \"postalCode\": \"1\",\n        \"remark\": \"1\"\n      }\n    ],\n    \"file\": [\n      {\n        \"fileType\": \"01\",\n        \"fileBase64\": \"909040954059405940594059\"\n      }\n    ]\n  }\n]\n" +
                    "}";
        }

        if ("clfwqxx".equals(beanId)) {
            response = "{\n" +
                    "  \"head\": {\n" +
                    "    \"returncode\": \"0000\",\n" +
                    "  \"returncodems\": \"成功\"\n" +
                    "  },\n" +
                    "  \"data\": \n" +
                    "   {\n" +
                    "\"mmhtzt\":\"1\",\n" +
                    "\"mmhtztms\":\"备案状态\"\n" +
                    "}\n" +
                    "}\n";
        }
        if ("dian_cxyhxx".equals(beanId) || "qi_cxyhxx".equals(beanId)) {
            response = "{\n" +
                    "\"code\":\"0000\",\n" +
                    "\"msg\":\"成功\",\n" +
                    "\"data\":\n" +
                    "{\n" +
                    "\"meter_period\":\"单月\",\n" +
                    "\"electricFeeAddr\":\"****滨新村一期8栋14号车库\",\n" +
                    "\"arrearage\":\"118\",\n" +
                    "\"electricFeeName\":\"张**\",\n" +
                    "\"electricFeeNum\":\"1000836333\",\n" +
                    "\"this_ymd\":\"2013-01-04 08:14:35.0\",\n" +
                    "\"arrearageMessage\":\"获取欠费信息成功！\",\n" +
                    "\"contract_cap\":\"8\"\n" +
                    "}\n" +
                    "}\n";
        }
        if ("shui_cxyhxx".equals(beanId)) {
            response = "{\n" +
                    "    \"rtnCode\": 0,\n" +
                    "    \"rtnMsg\": \"成功\",\n" +
                    "    \"Content\": {\n" +
                    "        \"UserID\": \"111\",\n" +
                    "        \"LimitFlag\": 0\n" +
                    "    }\n" +
                    "}";
        }
        if ("shuiPush".equals(beanId)) {
            response = "{\n" +
                    "    \"rtnCode\": 0,\n" +
                    "    \"rtnMsg\": \"成功\",\n" +
                    "    \"Content\": null\n" +
                    "}";
        }
        if ("hy_shuigh".equals(beanId)) {
            response = "{\n" +
                    "    \"code\": \"0000\",\n" +
                    "    \"msg\": \"成功\",\n" +
                    "    \"data\": {\n" +
                    "        \"message\": \"提交过户申请成功！\",\n" +
                    "        \"this_ymd\": \"2020-12-31 00:00:00\",\n" +
                    "        \"app_no\": \"3002154870123\",\n" +
                    "        \"result\": \"1\"\n" +
                    "    }\n" +
                    "}";
        }
        if ("dian_gh".equals(beanId) || "dianPush".equals(beanId) || "qiPush".equals(beanId)) {
            response ="{\n" +
                    "\"code\":\"0000\",\n" +
                    "\"msg\":\"成功\",\n" +
                    "\"data\":\n" +
                    "{\n" +
                    "\"message\":\"提交电费过户申请成功！\",\n" +
                    "\"this_ymd\":\"2020-12-31 00:00:00\",\n" +
                    "\"app_no\":\"3002154870123\",\n" +
                    "\"result\":\"1\"\n" +
                    "}\n" +
                    "}\n";
        }
        if("czFcjyClfHtxx".equals(beanId)){
            response ="{\"HEADER\":{\"RET_CODE\":\"00000000\",\"RET_MSG\":\"存量房合同信息查询成功！\"},\"BODY\":{\"htbh\":\"2016011410074\",\"xzqy\":\"新北区\",\"qlrxx\":[{\"qlrlb\":\"1\",\"qlrmc\":\"奚震峰,冉慧敏\",\"qlrzjzl\":\"身份证,身份证\",\"qlrzjh\":\"320404197810123138,422422198106224720\",\"qlrlxdh\":\"13584533668\",\"qlrlxdz\":\"嘉顺花园3幢12号\",\"qlrdlr\":\"\",\"qlrdlrzjzl\":\"\",\"qlrdlrzjh\":\"\"},{\"qlrlb\":\"2\",\"qlrmc\":\"曹裕兰,陶勇\",\"qlrzjzl\":\"身份证,身份证\",\"qlrzjh\":\"230405197107080740,320402196410241015\",\"qlrlxdh\":\"15094502335\",\"qlrlxdz\":\"嘉顺花园3幢12号\",\"qlrdlr\":\"\",\"qlrdlrzjzl\":\"\",\"qlrdlrzjh\":\"\"}],\"cqzh\":\"常房新字00050538\",\"cqr\":\"曹裕兰\",\"zl\":\"嘉顺花园3幢12号\",\"jzmj\":106.89,\"fj\":\"\",\"tdzh\":\"\",\"cjje\":850000,\"basj\":\"2016-01-14\",\"zjjgzt\":\"\",\"dzht\":\"\",\"bz\":\"\"}}";

        }
        if("czFcjySpfBaxx".equals(beanId)){
            response="{\n" +
                    "    \"HEADER\": {\n" +
                    "        \"RET_CODE\": \"00000000\",\n" +
                    "        \"RET_MSG\": \"商品房合同信息查询成功！\"\n" +
                    "    },\n" +
                    "    \"BODY\": {\n" +
                    "        \"HTBH\": \"2016011410074\",\n" +
                    "        \"TNJZMJ\": \"\",\n" +
                    "        \"MSRMC\": \"奚震峰,冉慧敏\",\n" +
                    "        \"MSRZJZL\": \"\",\n" +
                    "        \"MSRZJH\": \"320404197810123138,422422198106224720\",\n" +
                    "        \"FTJZMJ\": \"常房新字00050538\",\n" +
                    "        \"ZL\": \"嘉顺花园3幢12号\",\n" +
                    "        \"CMRZJH\": \"230405197107080740,320402196410241015\",\n" +
                    "        \"JZMJ\": 106.89,\n" +
                    "        \"CMRZJZL\": \"身份证,身份证\",\n" +
                    "        \"CMR\": \"曹裕兰,陶勇\",\n" +
                    "        \"ZJK\": 850000,\n" +
                    "        \"BASJ\": \"2016-01-14\",\n" +
                    "        \"HTFWM\": \"\",\n" +
                    "        \"DZHT\": \"\",\n" +
                    "        \"FWBHLX\": \"\"\n" +
                    "    }\n" +
                    "}";
        }
        if ("ahst_hydjxxcx".equals(beanId)) {
            response ="{\n" +
                    "    \"data\":{\n" +
                    "        \"count\":1,\n" +
                    "        \"rows\":[\n" +
                    "            {\n" +
                    "                \"birth_man\":\"1990-01-01 00:00:00\",\n" +
                    "                \"birth_woman\":\"1991-01-01 00:00:00\",\n" +
                    "                \"cert_no\":\"J320101-2019-001001\",\n" +
                    "                \"cert_num_man\":\"320101199001010101\",\n" +
                    "                \"cert_num_woman\":\"320101199001010102\",\n" +
                    "                \"dept_code\":\"32010601\",\n" +
                    "                \"dept_name\":\"南京市鼓楼区民政局婚姻登记处\",\n" +
                    "                \"name_man\":\"XXX\",\n" +
                    "                \"name_woman\":\"XXX\",\n" +
                    "                \"nation_man\":\"156\",\n" +
                    "                \"nation_woman\":\"156\",\n" +
                    "                \"op_date\":\"2018-05-20 00:00:00\",\n" +
                    "                \"op_type\":\"IA\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"birth_man\":\"1990-01-01 00:00:00\",\n" +
                    "                \"birth_woman\":\"1991-01-01 00:00:00\",\n" +
                    "                \"cert_no\":\"J320101-2019-001001\",\n" +
                    "                \"cert_num_man\":\"320101199001010101\",\n" +
                    "                \"cert_num_woman\":\"320101199001010102\",\n" +
                    "                \"dept_code\":\"32010601\",\n" +
                    "                \"dept_name\":\"南京市鼓楼区民政局婚姻登记处\",\n" +
                    "                \"name_man\":\"XXX\",\n" +
                    "                \"name_woman\":\"XXX\",\n" +
                    "                \"nation_man\":\"156\",\n" +
                    "                \"nation_woman\":\"156\",\n" +
                    "                \"op_date\":\"2018-05-20 00:00:00\",\n" +
                    "                \"op_type\":\"IA1\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"birth_man\":\"1990-01-01 00:00:00\",\n" +
                    "                \"birth_woman\":\"1991-01-01 00:00:00\",\n" +
                    "                \"cert_no\":\"J320101-2019-001001\",\n" +
                    "                \"cert_num_man\":\"320101199001010101\",\n" +
                    "                \"cert_num_woman\":\"320101199001010102\",\n" +
                    "                \"dept_code\":\"32010601\",\n" +
                    "                \"dept_name\":\"南京市鼓楼区民政局婚姻登记处\",\n" +
                    "                \"name_man\":\"XXX\",\n" +
                    "                \"name_woman\":\"XXX\",\n" +
                    "                \"nation_man\":\"156\",\n" +
                    "                \"nation_woman\":\"156\",\n" +
                    "                \"op_date\":\"2018-05-20 00:00:00\",\n" +
                    "                \"op_type\":\"IA2\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"businessNumber\":\"111111111\",\n" +
                    "        \"cxqqdh\":\"20191212320000111112\",\n" +
                    "        \"msg\":\"成功\",\n" +
                    "        \"requestType\":\"MZ01\",\n" +
                    "        \"status\":\"1\"\n" +
                    "    }\n" +
                    "}";
        }
        if ("csj_getSFMData".equals(beanId)) {
            response ="{\n" +
                    "    \"code\":\"000001\",\n" +
                    "    \"message\":\"处理成功\",\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"name\":\"中华人民共和国职业资格证书\",\n" +
                    "            \"pdf\":\"JVBERi0xLjQKJeLjz9MKNCAwIG9iago8PC9MZW5ndGgxIDIxNz......\",\n" +
                    "            \"liscenseInfo\":{\n" +
                    "                \"creation_time\":\"2020-10-20 00:00:00\",\n" +
                    "                \"issuer\":\"省人社\",\n" +
                    "                \"holder_identity_type\":\"10\",\n" +
                    "                \"issue_org_code\":\"113200012332674U\",\n" +
                    "                \"issue_date\":\"2013-01-01 00:00:00\",\n" +
                    "                \"holder_identity_num\":\"32048119990101618\",\n" +
                    "                \"data_fields\":\"\",\n" +
                    "                \"license_item_code\":\"100002101\",\n" +
                    "                \"division_code\":\"320000\",\n" +
                    "                \"creator\":\"省人社\",\n" +
                    "                \"license_type\":\"CERTIFICATE\",\n" +
                    "                \"biz_num\":\"20171111113\",\n" +
                    "                \"id_code\":\"1310021002400352\",\n" +
                    "                \"license_status\":\"ISSUED\",\n" +
                    "                \"issue_org_name\":\"江苏省人力资源和社会保障厅\",\n" +
                    "                \"name\":\"中华人民共和国职业资格证书\",\n" +
                    "                \"license_code\":\"32000020200018P0WR\"\n" +
                    "            }\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"name\":\"中华人民共和国职业资格证书1\",\n" +
                    "            \"pdf\":\"JVBERi0xLjQKJeLjz9MKNCAwIG9iago8PC9MZW5ndGgxIDIxNz\",\n" +
                    "            \"liscenseInfo\":{\n" +
                    "                \"creation_time\":\"2020-10-20 00:00:00\",\n" +
                    "                \"issuer\":\"省人社\",\n" +
                    "                \"holder_identity_type\":\"10\",\n" +
                    "                \"issue_org_code\":\"113200012332674U\",\n" +
                    "                \"issue_date\":\"2013-01-01 00:00:00\",\n" +
                    "                \"holder_identity_num\":\"32048119990101618\",\n" +
                    "                \"data_fields\":\"\",\n" +
                    "                \"license_item_code\":\"100002101\",\n" +
                    "                \"division_code\":\"320000\",\n" +
                    "                \"creator\":\"省人社\",\n" +
                    "                \"license_type\":\"CERTIFICATE\",\n" +
                    "                \"biz_num\":\"20171111113\",\n" +
                    "                \"id_code\":\"1310021002400352\",\n" +
                    "                \"license_status\":\"ISSUED\",\n" +
                    "                \"issue_org_name\":\"江苏省人力资源和社会保障厅\",\n" +
                    "                \"name\":\"中华人民共和国职业资格证书\",\n" +
                    "                \"license_code\":\"32000020200018P0WR\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }
        return response;
    }
}
