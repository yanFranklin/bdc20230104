package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.impl.inf.standard.ReplaceUrlByQxdmServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-16
 * @description HTTP 类型  GET方式 请求服务
 */
@Service(value = "httpGet")
public class HttpGetRequestServiceImpl extends InterfaceRequestService<HttpReqPropBO> {


    @Autowired
    private HttpClientService httpClientService;

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
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
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
            HttpGet httpGet = null;
            Map<String, Object> requestParamMap = new HashMap<>(12);
            if (prop.isGetUseNameValuePair()) {
                requestParamMap = CommonUtil.objectToMap(requestBody);
                List<NameValuePair> paramsList = new ArrayList<>();
                for (Map.Entry<String, Object> request : requestParamMap.entrySet()) {
                    paramsList.add(new BasicNameValuePair(request.getKey(), JSONObject.toJSONString(request.getValue())));
                }
                URIBuilder uriBuilder = null;
                try {
                    uriBuilder = new URIBuilder(requestUrl);
                    uriBuilder.addParameters(paramsList);
                    httpGet = new HttpGet(uriBuilder.build());
                } catch (URISyntaxException e) {
                    LOGGER.debug("httpGet 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                    httpGet = new HttpGet(requestUrl + "?" + CommonUtil.mapToUrlParam(requestParamMap));
                }
            } else {
                String urlParam = "";
                if (StringUtils.isNotBlank(prop.getParamClass())) {
                    // 需要强转
                    try {
                        Object castParam = JSONObject.parseObject(JSONObject.toJSONString(requestBody), Class.forName(prop.getParamClass()));
                        requestParamMap = JSONObject.parseObject(JSONObject.toJSONString(castParam, SerializerFeature.WriteNullStringAsEmpty), Map.class);
                        urlParam = CommonUtil.mapToUrlParam(requestParamMap);
                    } catch (ClassNotFoundException e) {
                        LOGGER.error("参数强转异常", e);
                    }
                } else {
                    requestParamMap = CommonUtil.objectToMap(requestBody);

                    if (prop.isGetUrlEncode()) {
                        if (requestParamMap.containsKey("params")) {
                            try {
                                requestParamMap.put("params", URLEncoder.encode(requestParamMap.get("params").toString(), CharEncoding.UTF_8));
                            } catch (UnsupportedEncodingException e) {
                                LOGGER.error("get方式url编码或参数强转异常", e);
                                e.printStackTrace();
                            }

                        }
                    }
                    urlParam = CommonUtil.mapToUrlParam(requestParamMap);
                }
                httpGet = new HttpGet(requestUrl + "?" + urlParam);
            }
            if (StringUtils.isNotBlank(prop.getContentType())) {
                httpGet.setHeader("Content-Type", prop.getContentType());
            } else {
                httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
            }
            if (StringUtils.isNotBlank(prop.getxPlatToken())) {
                httpGet.setHeader("X-PLAT-TOKEN", prop.getxPlatToken());
            }
            httpGet.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            httpGet.setHeader("unitCode", "LandResourcesBureau");
            if (StringUtils.isNotBlank(prop.getAppKey())) {
                if (StringUtils.isNotBlank(prop.getResponser()) && "baselibrary".equals(prop.getResponser())) {
                    httpGet.setHeader("X-H3C-APPKEY", prop.getAppKey());
                } else {
                    httpGet.setHeader("appKey", prop.getAppKey());
                }
            }
            if (StringUtils.isNotBlank(prop.getToken())) {
                httpGet.setHeader("token", prop.getToken());
            }
            if (StringUtils.isNotBlank(prop.getWorkspaceId())) {
                httpGet.setHeader("X-H3C-ID", prop.getWorkspaceId());
            }
            String response = "";
            Exception logE = null;
            try {
                if (prop.isHttpsRequest()) {
                    response = httpClientService.doHttpsGetHlzs(httpGet);
                } else {
                    response = httpClientService.doGet(httpGet);
                }
            } catch (IOException e) {
                logE = e;
                LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                throw new AppException("httpGet 请求异常");
            } finally {
                // 记录 请求日志
                AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                auditEventBO.setRequest(JSONObject.toJSONString(requestParamMap));
                auditEventBO.setResponse(response);
                auditEventBO.setException(logE);
                super.saveAuditLog(auditEventBO);
            }
//            response = testResponse(builder.getExchangeBean().getId());

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
    public String dealWithResponse(String response) {
        return response;
    }

    private String testResponse(String beanid) {
        String response = "";
        // hfFcjyClfHtxx
        if (StringUtils.equals(beanid, "hfFcjyClfHtxx")) {
            response = "{\"code\":\"00000\",\"data\":{\"SuperMap\":{\"HT\":{\"FW\":{\"FCBM\":\"瑶120015426\",\"HTBH\":\"1447573794612\",\"XZQH\":\"瑶海区\",\"FWDZ\":\"合肥市瑶海区芜湖路\",\"ZCS\":31.0,\"SZC\":18.0,\"JZMJ\":93.48,\"JZJG\":\"钢筋混凝土结构\",\"FWYT\":\"住宅\",\"QSZYDX\":null,\"QSZYYT\":null,\"QSZYFS\":null,\"SYQQDFS\":null,\"QYRQ\":\"2015-11-15\",\"HTJE\":750000.0,\"FWDH\":\"1\",\"FJH\":\"1802\",\"TNMJ\":null,\"DJ\":null,\"ZJGSID\":null,\"FWNM\":null,\"HTNM\":null,\"XQMC\":\"御景湾\",\"BARQ\":\"2015-11-15\",\"QZTZRQ\":null,\"ZJGSDM\":\"340100000545777\",\"ZJGSMC\":\"安徽省义和房地产营销策划有限公司\"},\"JYSF\":[{\"XM\":\"侯泽华\",\"ZJLX\":\"身份证\",\"ZJHM\":\"41302719700120404X\",\"DH\":\"13956949776\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":0},{\"XM\":\"贺学武\",\"ZJLX\":\"身份证\",\"ZJHM\":\"342101197007150217\",\"DH\":\"13705586373\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1}]}}},\"msg\":\"操作成功\"}";
        } else if (StringUtils.equals(beanid, "hfFcjySpfHtxx")) {
            // 房产 根据产权证号 和 证件号 查询备案信息

            response = "{\n \"code\": \"00000\",\n \"data\": [\n   {\n  \"SuperMap\": {\n \"HT\": {\n\"FW\": {\n  \"FCBM\": \"瑶120015426\",\n  \"HTBH\": \"1447573794612\",\n  \"XZQH\": \"瑶海区\",\n  \"FWDZ\": \"合肥市瑶海区芜湖路\",\n  \"ZCS\": 31.0,\n  \"SZC\": 18.0,\n  \"JZMJ\": 93.48,\n  \"JZJG\": \"钢筋混凝土结构\",\n  \"FWYT\": \"住宅\",\n  \"QSZYDX\": null,\n  \"QSZYYT\": null,\n  \"QSZYFS\": null,\n  \"SYQQDFS\": null,\n  \"QYRQ\": \"2015-11-15\",\n  \"HTJE\": 750000.0,\n  \"FWDH\": \"1\",\n  \"FJH\": \"1802\",\n  \"TNMJ\": null,\n  \"DJ\": null,\n  \"ZJGSID\": null,\n  \"FWNM\": null,\n  \"HTNM\": null,\n  \"XQMC\": \"御景湾\",\n  \"BARQ\": \"2015-11-15\",\n  \"QZTZRQ\": null,\n  \"ZJGSDM\": \"340100000545777\",\n  \"ZJGSMC\": \"安徽省义和房地产营销策划有限公司\"\n         },\n\"JYSF\": [\n           {\n    \"XM\": \"侯泽华\",\n    \"ZJLX\": \"身份证\",\n    \"ZJHM\": \"41302719700120404X\",\n    \"DH\": \"13956949776\",\n    \"DJ\": null,\n    \"GJ\": null,\n    \"SZFE\": null,\n    \"BJ\": 0\n           },\n           {\n    \"XM\": \"贺学武\",\n    \"ZJLX\": \"身份证\",\n    \"ZJHM\": \"342101197007150217\",\n    \"DH\": \"13705586373\",\n    \"DJ\": null,\n    \"GJ\": null,\n    \"SZFE\": null,\n    \"BJ\": 1\n           }\n         ]\n       }\n     }\n   }\n ],\n \"msg\": \"操作成功\"\n" + "}";
        } else if (StringUtils.equals(beanid, "hfFcjySpfHtxx") ||StringUtils.equals(beanid, "spfBaxxHttp")) {
            
            // 合肥 读取 商品房备案信息
            response = "{\n \"code\": \"00000\",\n \"data\": {\n\"Htbh\": \"20170963800986XL20180023\",\n\"Badh\": \"1808009133\",\n\"Fwbm\": 5463208,\n\"Xzqh\": \"新站区\",\n\"Xqmc\": \"学林花园\",\n\"Fwzh\": \"4幢\",\n\"Dscs\": 21,\n\"Szc\": 4,\n\"Fh\": \"402\",\n\"Tnmj\": 76.85,\n\"Jzmj\": 100.32,\n\"Fwzl\": \"合肥市新站区口孜路与浍水路交口学林花园4幢402室\",\n\"Cmr\": \"合肥蓝光盛景置业有限公司\",\n\"Cmrzjlx\": \"营业执照\",\n\"CmrZjhm\": \"91340100MA2NC0680W\",\n\"Gfr\": \"朱阿敏\",\n\"Gfrzjlx\": \"居民身份证\",\n\"Gfrzjhm\": \"342225198910081028\",\n\"Qyrq\": \"2018-08-20\",\n\"Barq\": \"2018-08-22\",\n\"Fkfs\": \"按揭付款\",\n\"Badj\": 12948.21,\n\"Bazj\": 1298964,\n\"Fwjg\": \"钢筋混凝土结构\",\n\"Fwyt\": \"住宅\",\n\"Bamj\": 100.32,\n\"Fwzt\": \"备案\",\n\"Members\": [\n     {\n \"Name\": \"邹荣乐\",\n" + "\t\t\"IdType\": \"居民身份证\",\n \"IdNo\": \"34222419860803171X\"\n     }\n   ]\n },\n \"msg\": \"操作成功\"\n" + "}";
        } else if (StringUtils.equals(beanid, "")) {
            // 合肥 房产根据FWBM 查询预查封数据
            response = "{\n \"code\": \"00000\",\n \"data\": [\n   {\n  \"Fwbm\": 2819080,\n  \"Fwzl\": \"合肥市马鞍山路与芜湖路交口合肥万达广场10#楼10-4101\",\n  \"Cfrq\": \"2013-09-13\",\n  \"Cfnr\": \"2013年9月13日合肥市瑶海区人民法院轮候查封\",\n  \"Jfrq\": \"2014-01-08\",\n  \"Jfnr\": \"已解封\",\n  \"Fwzt\": \"限制\"\n   },\n   {\n  \"Fwbm\": 2819080,\n  \"Fwzl\": \"合肥市马鞍山路与芜湖路交口合肥万达广场10#楼10-4101\",\n  \"Cfrq\": \"2013-09-11\",\n  \"Cfnr\": \"2013年9月11日瑶海区人民法院续封\",\n  \"Jfrq\": \"2014-01-06\",\n  \"Jfnr\": \"已解封\",\n  \"Fwzt\": \"限制\"\n   },\n   {\n  \"Fwbm\": 2819080,\n  \"Fwzl\": \"合肥市马鞍山路与芜湖路交口合肥万达广场10#楼10-4101\",\n  \"Cfrq\": \"2012-07-24\",\n  \"Cfnr\": \"两轮候查封：（2012）瑶民一初字第3009、3010号\",\n  \"Jfrq\": \"2014-03-20\",\n  \"Jfnr\": \"已解封\",\n  \"Fwzt\": \"限制\"\n   },\n   {\n  \"Fwbm\": 2819080,\n  \"Fwzl\": \"合肥市马鞍山路与芜湖路交口合肥万达广场10#楼10-4101\",\n  \"Cfrq\": \"2012-06-01\",\n  \"Cfnr\": \"瑶海区法院轮候查封，（2012）瑶民一初字第02602号，期限两年\",\n  \"Jfrq\": null,\n  \"Jfnr\": null,\n  \"Fwzt\": \"限制\"\n   }\n ],\n \"msg\": \"操作成功\"\n" + "}";
        } else if (StringUtils.equals(beanid, "")) {
            response = "{\n \"code\": \"00000\",\n \"data\": {\n\"Htbh\": \"20100907800679HS20100016\",\n\"Badh\": \"1012033070\",\n\"Fwbm\": 2699704,\n\"Xzqh\": \"庐阳区\",\n\"Xqmc\": \"恒盛皇家花园\",\n\"Fwzh\": \"1幢\",\n\"Dscs\": 33,\n\"Szc\": 28,\n\"Fh\": \"2801\",\n\"Tnmj\": 83.25,\n\"Jzmj\": 137.41,\n\"Fwzl\": \"合肥市庐阳区蒙城路与连水路交口东南侧恒盛皇家花园1#楼2801室\",\n\"Cmr\": \"恒盛颐丰(合肥)房地产开发有限公司\",\n\"Cmrzjlx\": \"营业执照\",\n\"CmrZjhm\": \"340100000453335\",\n\"Gfr\": \"陈\",\n\"Gfrzjlx\": \"居民身份证\",\n\"Gfrzjhm\": \"340103198309131519\",\n\"Qyrq\": \"2011-02-09\",\n\"Barq\": \"2011-12-24\",\n\"Badj\": 7326,\n\"Bazj\": 1006665,\n\"Fwjg\": \"钢、钢筋混凝土结构\",\n\"Fwyt\": \"住宅\",\n\"Bamj\": 137.41,\n\"Fwzt\": \"已办产权\",\n\"Members\": [\n     {\n \"Name\": \"柏珊\",\n \"IdNo\": \"340104198306112044\"\n     }\n   ]\n },\n \"msg\": \"操作成功\"\n" + "}";
        } else if (StringUtils.equals(beanid, "swsys_dk")) {
            response = "{\n \"result\": {\n   \"head\": {\n     \"tran_id\": \"TAX.INTEGRATE.HX.UNIONPAY\",\n     \"channel_id\": \"AH.TAX.BDCJY.XX\",\n     \"tran_seq\": \"e570d386-8f05-4af3-894e-167646dda582\",\n     \"tran_date\": \"20200117\",\n     \"rtn_code\": \"200\",\n     \"rtn_msg\": \"查询成功\"\n   },\n   \"body\": {\n     \"syslb\": [\n       {\n         \"swjgDm\": \"13416010000\",\n         \"nsrsbh\": \"340603199909074038\",\n         \"dzsphm\": \"334166200100000006\",\n         \"yjse\": 1345.03\n       },\n       {\n         \"swjgDm\": \"13416010000\",\n         \"nsrsbh\": \"340621199011061305\",\n         \"dzsphm\": \"334166200100000007\",\n         \"yjse\": 1345.03\n       }\n     ],\n     \"bz\": 200\n   }\n }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "swQxzf_dk")) {
            response = "{\n \"result\": {\n   \"head\": {\n     \"tran_id\": \"TAX.COMMON.HX.CANCLES\",\n     \"channel_id\": \"AH.TAX.BDCJY.XX\",\n     \"tran_seq\": \"9A5A1B5B44n4544fr45Fbv51654B358\",\n     \"tran_date\": \"20191226\",\n     \"rtn_code\": \"500\",\n     \"rtn_msg\": \"该办理件取消作废失败\"\n   },\n   \"body\": {\n     \"jyzt_dm\": \"900\",\n     \"jyzt_mc\": \"审核不通过，已退回\",\n     \"shyj\": \"\"\n   }\n }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "spfwszt_dk")) {
            response = "{\n \"result\": {\n   \"head\": {\n     \"tran_id\": \"TAX.COMMON.CX.GT3PAYMENT\",\n     \"channel_id\": \"AH.TAX.BDCJY.HS\",\n     \"tran_seq\": \"7bedfefeee764b33b3bd55fca8f51653\",\n     \"tran_date\": \"20200420\",\n     \"rtn_code\": \"200\",\n     \"rtn_msg\": \"已完税\",\n     \"expand\": [\n       {\n         \"name\": \"expandName\",\n         \"value\": \"1340HSBDCJY\"\n       },\n       {\n         \"name\": \"expandPwd\",\n         \"value\": \"1340HSBDCJY\"\n       }\n     ]\n   },\n   \"body\": {\n     \"csfskxxGrid\": {\n       \"nsrsbh\": \"340521197809246857\",\n       \"nsrmc\": \"周宏明\",\n       \"fwdz\": \"翠竹小区36栋505室\",\n       \"fybh\": \"F34052120180002841\",\n       \"djxh\": \"20123400100013433088\",\n       \"csfskxxGridlb\": [\n         {\n           \"se\": 0,\n           \"zspm\": \"产权转移书据\",\n           \"zsxm\": \"印花税\",\n           \"zsxm_dm\": \"10111\"\n         },\n         {\n           \"se\": 2500,\n           \"zspm\": \"存量房（商品住房买卖）\",\n           \"zsxm\": \"契税\",\n           \"zsxm_dm\": \"10119\"\n         }\n       ]\n     },\n     \"zrfskxxGrid\": {\n       \"nsrsbh\": \"340521197111070511\",\n       \"nsrmc\": \"方同华\",\n       \"fwdz\": \"翠竹小区36栋505室\",\n       \"fybh\": \"F34052120180002841\",\n       \"djxh\": \"20123400100008445304\",\n       \"zrfskxxGridlb\": [\n         {\n           \"se\": 0,\n           \"zspm\": \"建筑物（11%、10%、9%、5%）-二手房\",\n           \"zsxm\": \"增值税\",\n           \"zsxm_dm\": \"10101\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"产权转移书据\",\n           \"zsxm\": \"印花税\",\n           \"zsxm_dm\": \"10111\"\n         },\n         {\n           \"se\": 2500,\n           \"zspm\": \"个人房屋转让所得\",\n           \"zsxm\": \"个人所得税\",\n           \"zsxm_dm\": \"10106\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"县城、镇（增值税附征）\",\n           \"zsxm\": \"城市维护建设税\",\n           \"zsxm_dm\": \"10109\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"增值税教育费附加\",\n           \"zsxm\": \"教育费附加\",\n           \"zsxm_dm\": \"30203\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"增值税地方教育附加\",\n           \"zsxm\": \"地方教育附加\",\n           \"zsxm_dm\": \"30216\"\n         }\n       ]\n     },\n     \"wsbj\": \"Y\",\n     \"fwwzdz\": \"合肥市蜀山区蜀山庭院1栋2301\"\n   }\n }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "shijjk_zhrkk_dsrrk")) {
            response = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"message\": \"操作成功.\",\n" +
                    "    \"body\": {\n" +
                    "        \"code\": 0,\n" +
                    "        \"message\": \"操作成功\",\n" +
                    "        \"data\": [\n" +
                    "            {\n" +
                    "                \"CREAT_TIME\": \"2020-12-03T07:38:47.000+0000\",\n" +
                    "                \"EXTEND2\": null,\n" +
                    "                \"EXTEND3\": null,\n" +
                    "                \"EXTEND4\": null,\n" +
                    "                \"CD_TIME\": \"2020-10-14T08:54:32.000+0000\",\n" +
                    "                \"SFTP\": \"2017\",\n" +
                    "                \"CD_BATCH\": \"202010080001\",\n" +
                    "                \"EXTEND1\": null,\n" +
                    "                \"XM\": \"徐为奎\",\n" +
                    "                \"GMSFHM\": \"32092219501221241X\",\n" +
                    "                \"SYNC_SIGN\": 0,\n" +
                    "                \"DATASOURCE\": \"12309SNYNCJ\",\n" +
                    "                \"CD_OPERATION\": \"I\",\n" +
                    "                \"UUID\": \"E14449510DFA11EB917CFA163E091157\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"messageId\": null\n" +
                    "    }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "shijjk_zhrkk_swyxzm")) {
            response = "\t{\n" +
                    "    \"code\": 200,\n" +
                    "    \"message\": \"操作成功.\",\n" +
                    "    \"body\": {\n" +
                    "        \"code\": 0,\n" +
                    "        \"message\": \"操作成功\",\n" +
                    "        \"data\": [\n" +
                    "            {\n" +
                    "                \"SWDD\": \"家中\",\n" +
                    "                \"ZJSWYYJBMC\": \"带状疱疹，伴有其他并发症\",\n" +
                    "                \"SWRQ\": \"2019-09-08\",\n" +
                    "                \"CD_TIME\": \"2020-12-22T09:35:56.000+0000\",\n" +
                    "                \"CD_BATCH\": \"201212220001\",\n" +
                    "                \"BGDWSSXQMC\": null,\n" +
                    "                \"CZDZ\": \"江苏省盐城市亭湖区新城街道南映社区五组\",\n" +
                    "                \"DATASOURCE\": \"12309SWJW\",\n" +
                    "                \"CD_OPERATION\": \"I\",\n" +
                    "                \"XBDM\": \"1\",\n" +
                    "                \"UUID\": \"e2c6d1d6443811ebaf88fa163e58c24f\",\n" +
                    "                \"SZZJHM\": \"320911194812237013\",\n" +
                    "                \"SZJSXM\": \"陈建国\",\n" +
                    "                \"MZBM\": \"1\",\n" +
                    "                \"CREAT_TIME\": \"2020-12-22T10:45:07.000+0000\",\n" +
                    "                \"EXTEND2\": null,\n" +
                    "                \"EXTEND3\": null,\n" +
                    "                \"EXTEND4\": null,\n" +
                    "                \"SZYXSFZJLX\": \"身份证\",\n" +
                    "                \"XB\": \"男\",\n" +
                    "                \"MZ\": \"汉族\",\n" +
                    "                \"EXTEND1\": null,\n" +
                    "                \"CSRQ\": \"1948-12-23\",\n" +
                    "                \"LXDH\": null,\n" +
                    "                \"SYNC_SIGN\": 0,\n" +
                    "                \"GJHDQSZDM\": null,\n" +
                    "                \"JBICD10BM\": \"B02.8\",\n" +
                    "                \"SZSQCZDZ\": \"32090222\",\n" +
                    "                \"SZXM\": \"陈立文\",\n" +
                    "                \"NL\": \"70岁\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"messageId\": null\n" +
                    "    }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "shijjk_zhrkk_jzxx")) {
            response = "{\n" +
                    "    \"code\": 200,\n" +
                    "    \"message\": \"操作成功.\",\n" +
                    "    \"body\": {\n" +
                    "        \"code\": 0,\n" +
                    "        \"message\": \"操作成功\",\n" +
                    "        \"data\": [\n" +
                    "            {\n" +
                    "                \"CREAT_TIME\": \"2020-12-12T04:49:19.000+0000\",\n" +
                    "                \"EXTEND2\": null,\n" +
                    "                \"EXTEND3\": null,\n" +
                    "                \"EXTEND4\": null,\n" +
                    "                \"JZLB\": \"低保1\",\n" +
                    "                \"CD_TIME\": \"2020-11-18T06:51:57.000+0000\",\n" +
                    "                \"XB\": \"1\",\n" +
                    "                \"CD_BATCH\": \"202011180001\",\n" +
                    "                \"EXTEND1\": null,\n" +
                    "                \"CSRQ\": \"1979-02-19\",\n" +
                    "                \"LXDH\": null,\n" +
                    "                \"GMSFHM\": \"32090219790219355X\",\n" +
                    "                \"XM\": \"陈曙伟\",\n" +
                    "                \"SYNC_SIGN\": 0,\n" +
                    "                \"DATASOURCE\": \"12309SMZT\",\n" +
                    "                \"CD_OPERATION\": \"I\",\n" +
                    "                \"UUID\": \"C9242CCCF81000016F55BE70F9A01F60\",\n" +
                    "                \"JZFS\": null\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"CREAT_TIME\": \"2020-12-12T04:49:23.000+0000\",\n" +
                    "                \"EXTEND2\": null,\n" +
                    "                \"EXTEND3\": null,\n" +
                    "                \"EXTEND4\": null,\n" +
                    "                \"JZLB\": \"低保\",\n" +
                    "                \"CD_TIME\": \"2020-11-18T07:12:58.000+0000\",\n" +
                    "                \"XB\": \"1\",\n" +
                    "                \"CD_BATCH\": \"202011180001\",\n" +
                    "                \"EXTEND1\": null,\n" +
                    "                \"CSRQ\": \"1979-02-19\",\n" +
                    "                \"LXDH\": null,\n" +
                    "                \"GMSFHM\": \"32090219790219355X\",\n" +
                    "                \"XM\": \"陈曙伟\",\n" +
                    "                \"SYNC_SIGN\": 0,\n" +
                    "                \"DATASOURCE\": \"12309SMZT\",\n" +
                    "                \"CD_OPERATION\": \"I\",\n" +
                    "                \"UUID\": \"C9242D2727A00001ECA479205F3312B8\",\n" +
                    "                \"JZFS\": null\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"messageId\": null\n" +
                    "    }\n" +
                    "}";
        } else if ("getYbaHtxx".equals(beanid)) {
            response = " {\n" +
                    "  \"code\":1,\n" +
                    "  \"data\":{\n" +
                    "     \"code\":\"XXXX\",\n" +
                    "     \"record\":\"XXXX\",        \n" +
                    "     \"url\":\"http://XXXXXX/file/XXX.pdf\"\n" +
                    "     }\n" +
                    "  }";
        } else if ("hfFcfhtMlxx".equals(beanid)) {
            //分层分户图目录返回
//            response = "{\"Code\":0,\"Message\":\"获取数据成功！\",\"Data\":\"&lt;DataSet&gt;&lt;xs:schema id=\\\"NewDataSet\\\" xmlns:xs=\\\"http:\\/\\/www.w3.org\\/2001\\/XMLSchema\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"&gt;&lt;xs:element name=\\\"NewDataSet\\\" msdata:IsDataSet=\\\"true\\\" msdata:UseCurrentLocale=\\\"true\\\"&gt;&lt;xs:complexType&gt;&lt;xs:choice minOccurs=\\\"0\\\" maxOccurs=\\\"unbounded\\\"&gt;&lt;xs:element name=\\\"ds\\\"&gt;&lt;xs:complexType&gt;&lt;xs:sequence&gt;&lt;xs:element name=\\\"RNAME\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/&gt;&lt;xs:element name=\\\"FROMPAGE\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/&gt;&lt;xs:element name=\\\"ENDPAGE\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/&gt;&lt;\\/xs:sequence&gt;&lt;\\/xs:complexType&gt;&lt;\\/xs:element&gt;&lt;\\/xs:choice&gt;&lt;\\/xs:complexType&gt;&lt;\\/xs:element&gt;&lt;\\/xs:schema&gt;&lt;diffgr:diffgram xmlns:diffgr=\\\"urn:schemas-microsoft-com:xml-diffgram-v1\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"&gt;&lt;NewDataSet&gt;&lt;ds diffgr:id=\\\"ds1\\\" msdata:rowOrder=\\\"0\\\"&gt;&lt;RNAME&gt;房屋平面图&lt;\\/RNAME&gt;&lt;FROMPAGE&gt;32&lt;\\/FROMPAGE&gt;&lt;ENDPAGE&gt;32&lt;\\/ENDPAGE&gt;&lt;\\/ds&gt;&lt;ds diffgr:id=\\\"ds2\\\" msdata:rowOrder=\\\"1\\\"&gt;&lt;RNAME&gt;房屋分户图&lt;\\/RNAME&gt;&lt;FROMPAGE&gt;64&lt;\\/FROMPAGE&gt;&lt;ENDPAGE&gt;64&lt;\\/ENDPAGE&gt;&lt;\\/ds&gt;&lt;ds diffgr:id=\\\"ds3\\\" msdata:rowOrder=\\\"2\\\"&gt;&lt;RNAME&gt;房屋分户图&lt;\\/RNAME&gt;&lt;FROMPAGE&gt;96&lt;\\/FROMPAGE&gt;&lt;ENDPAGE&gt;96&lt;\\/ENDPAGE&gt;&lt;\\/ds&gt;&lt;ds diffgr:id=\\\"ds4\\\" msdata:rowOrder=\\\"3\\\"&gt;&lt;RNAME&gt;房屋分户图&lt;\\/RNAME&gt;&lt;FROMPAGE&gt;128&lt;\\/FROMPAGE&gt;&lt;ENDPAGE&gt;128&lt;\\/ENDPAGE&gt;&lt;\\/ds&gt;&lt;ds diffgr:id=\\\"ds5\\\" msdata:rowOrder=\\\"4\\\"&gt;&lt;RNAME&gt;房屋分户图&lt;\\/RNAME&gt;&lt;FROMPAGE&gt;160&lt;\\/FROMPAGE&gt;&lt;ENDPAGE&gt;160&lt;\\/ENDPAGE&gt;&lt;\\/ds&gt;&lt;ds diffgr:id=\\\"ds6\\\" msdata:rowOrder=\\\"5\\\"&gt;&lt;RNAME&gt;房屋分户图&lt;\\/RNAME&gt;&lt;FROMPAGE&gt;192&lt;\\/FROMPAGE&gt;&lt;ENDPAGE&gt;192&lt;\\/ENDPAGE&gt;&lt;\\/ds&gt;&lt;\\/NewDataSet&gt;&lt;\\/diffgr:diffgram&gt;&lt;\\/DataSet&gt;\"}";
            response = "{\"Code\":0,\"Message\":\"获取成功！！\",\"Data\":\"<DataSet><xs:schema id=\\\"NewDataSet\\\" xmlns:xs=\\\"http:\\/\\/www.w3.org\\/2001\\/XMLSchema\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"><xs:element name=\\\"NewDataSet\\\" msdata:IsDataSet=\\\"true\\\" msdata:UseCurrentLocale=\\\"true\\\"><xs:complexType><xs:choice minOccurs=\\\"0\\\" maxOccurs=\\\"unbounded\\\"><xs:element name=\\\"ds\\\"><xs:complexType><xs:sequence><xs:element name=\\\"IMGID\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"PAGES\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"受理编号\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"产权证号\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"不动产权证号\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"产权人\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"坐落\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"业务类型\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><\\/xs:sequence><\\/xs:complexType><\\/xs:element><\\/xs:choice><\\/xs:complexType><\\/xs:element><\\/xs:schema><diffgr:diffgram xmlns:diffgr=\\\"urn:schemas-microsoft-com:xml-diffgram-v1\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"><NewDataSet><ds diffgr:id=\\\"ds1\\\" msdata:rowOrder=\\\"0\\\"><IMGID>2832891<\\/IMGID><PAGES>23<\\/PAGES><受理编号>69667689<\\/受理编号><不动产权证号>皖(2019)合肥市不动产权第40046171号<\\/不动产权证号><产权人>陈磊<\\/产权人><坐落>蜀山区环湖东路398号丽苑1幢1A-2702室<\\/坐落><业务类型>存量房买卖<\\/业务类型><\\/ds><\\/NewDataSet><\\/diffgr:diffgram><\\/DataSet>\"}";

        } else if ("jgysbaFjxx".equals(beanid)) {
            //根据项目信息获取竣工备案
            response = "{\n" +
                    "    \"total\":2,\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"DOC_ID\":\"http://59.203.228.97:8084/WebDiskServerDemo/doc?doc_id=47bd2d70-712a-444b-bbc5-179deccce8c1\",\n" +
                    "            \"MODEL_NAME\":\"金安区竣工备案表\",\n" +
                    "            \"CREDIT_CODE\":\"91341502MA2NHL8R38\",\n" +
                    "            \"PROJECT_CODE\":\"2017-341574-54-03-008596-0001\",\n" +
                    "            \"CONTRACTOR\":\"安徽金石建材有限公司\",\n" +
                    "            \"BSNUM\":\"341502-003-XK-1618368012828-027488\",\n" +
                    "            \"PROJECT_NAME\":\"安徽金石建材有限公司2#厂房\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"DOC_ID\":\"http://59.203.228.97:8084/WebDiskServerDemo/doc?doc_id=aef60414-ceb7-4453-a8cd-0e734be372c8\",\n" +
                    "            \"MODEL_NAME\":\"金安区竣工备案表\",\n" +
                    "            \"CREDIT_CODE\":\"91341502MA2NHL8R38\",\n" +
                    "            \"PROJECT_CODE\":\"2017-341574-54-03-008596-0001\",\n" +
                    "            \"CONTRACTOR\":\"安徽金石建材有限公司\",\n" +
                    "            \"BSNUM\":\"341502-003-XK-1618368012828-027488\",\n" +
                    "            \"PROJECT_NAME\":\"安徽金石建材有限公司2#厂房\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"page\":1,\n" +
                    "    \"state\":\"200\",\n" +
                    "    \"rows\":6\n" +
                    "}";
        } else if ("ghxkzList".equals(beanid)) {
            //建设规划许可证证照列表
            response = "{\n" +
                    "    \"licenseArray\":[\n" +
                    "        {\n" +
                    "            \"licenseNo\":\"4be50783-7316-4519-96ab-a72f10874b03\",\n" +
                    "            \"relationLicenseNo\":\"\",\n" +
                    "            \"licenseTypeCode\":\"34150080113000000000-08F8F42D36BB41629467D14BB7ABB82E-20190826115005-1\",\n" +
                    "            \"licenseTypeName\":\"建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseName\":\"郑琴-建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseNumber\":\"341501201900145\",\n" +
                    "            \"certificateDate\":\"2019-11-08\",\n" +
                    "            \"validPeriodBegin\":\"2019-11-08\",\n" +
                    "            \"validPeriodEnd\":\"2020-11-08\",\n" +
                    "            \"warningDateBegin\":\"\",\n" +
                    "            \"warningDateEnd\":\"\",\n" +
                    "            \"holderName\":\"郑琴\",\n" +
                    "            \"certificateNo\":\"91341502MA2THTYY30\",\n" +
                    "            \"certificateType\":\"统一社会信用代码\",\n" +
                    "            \"deptOrganizeCode\":\"34150080113000000000\",\n" +
                    "            \"deptName\":\"六安市自然资源和规划局\",\n" +
                    "            \"projectName\":\"关于六安环球港8#、9#、13#、21#楼建设工程规划许可证（新建项目）的业务\",\n" +
                    "            \"certificateLevel\":\"\",\n" +
                    "            \"state\":\"valid\",\n" +
                    "            \"holderType\":\"enterprises\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"licenseNo\":\"CB93D8ADF8A8428885B55FEDC1FDA1AC\",\n" +
                    "            \"relationLicenseNo\":\"\",\n" +
                    "            \"licenseTypeCode\":\"341522003-40000675364F4F57B432DDC423888305-20200512094951-1\",\n" +
                    "            \"licenseTypeName\":\"建设工程规划许可证NEW\",\n" +
                    "            \"licenseName\":\"六安星际房地产开发有限公司-建设工程规划许可证NEW\",\n" +
                    "            \"licenseNumber\":\"341501202000123\",\n" +
                    "            \"certificateDate\":\"2020-07-08\",\n" +
                    "            \"validPeriodBegin\":\"2020-07-08\",\n" +
                    "            \"validPeriodEnd\":\"9999-12-31\",\n" +
                    "            \"warningDateBegin\":\"\",\n" +
                    "            \"warningDateEnd\":\"\",\n" +
                    "            \"holderName\":\"六安星际房地产开发有限公司\",\n" +
                    "            \"certificateNo\":\"91341502MA2THTYY30\",\n" +
                    "            \"certificateType\":\"统一社会信用代码\",\n" +
                    "            \"deptOrganizeCode\":\"34150080113000000000\",\n" +
                    "            \"deptName\":\"六安市自然资源和规划局\",\n" +
                    "            \"projectName\":\"关于六安环球港14#15#18#19#22#23#住宅楼建设工程规划许可证（新建项目）的业务\",\n" +
                    "            \"certificateLevel\":\"\",\n" +
                    "            \"state\":\"valid\",\n" +
                    "            \"holderType\":\"enterprises\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"licenseNo\":\"E273361B4971449094319300BA43E900\",\n" +
                    "            \"relationLicenseNo\":\"\",\n" +
                    "            \"licenseTypeCode\":\"34150080113000000000-08F8F42D36BB41629467D14BB7ABB82E-20190826115005-1\",\n" +
                    "            \"licenseTypeName\":\"建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseName\":\"六安星际房地产开发有限公司-建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseNumber\":\"341501202000091\",\n" +
                    "            \"certificateDate\":\"2020-06-05\",\n" +
                    "            \"validPeriodBegin\":\"2020-06-05\",\n" +
                    "            \"validPeriodEnd\":\"2021-06-05\",\n" +
                    "            \"warningDateBegin\":\"\",\n" +
                    "            \"warningDateEnd\":\"\",\n" +
                    "            \"holderName\":\"六安星际房地产开发有限公司\",\n" +
                    "            \"certificateNo\":\"91341502MA2THTYY30\",\n" +
                    "            \"certificateType\":\"统一社会信用代码\",\n" +
                    "            \"deptOrganizeCode\":\"34150080113000000000\",\n" +
                    "            \"deptName\":\"六安市自然资源和规划局\",\n" +
                    "            \"projectName\":\"关于六安环球港2#、6#楼建设工程规划许可证（新建项目）的业务\",\n" +
                    "            \"certificateLevel\":\"\",\n" +
                    "            \"state\":\"valid\",\n" +
                    "            \"holderType\":\"enterprises\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"licenseNo\":\"1D6FE0CC4D784772916E24646B143019\",\n" +
                    "            \"relationLicenseNo\":\"\",\n" +
                    "            \"licenseTypeCode\":\"34150080113000000000-08F8F42D36BB41629467D14BB7ABB82E-20190826115005-1\",\n" +
                    "            \"licenseTypeName\":\"建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseName\":\"六安星际房地产开发有限公司-建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseNumber\":\"341501202000092\",\n" +
                    "            \"certificateDate\":\"2020-06-05\",\n" +
                    "            \"validPeriodBegin\":\"2020-06-05\",\n" +
                    "            \"validPeriodEnd\":\"2021-06-05\",\n" +
                    "            \"warningDateBegin\":\"\",\n" +
                    "            \"warningDateEnd\":\"\",\n" +
                    "            \"holderName\":\"六安星际房地产开发有限公司\",\n" +
                    "            \"certificateNo\":\"91341502MA2THTYY30\",\n" +
                    "            \"certificateType\":\"统一社会信用代码\",\n" +
                    "            \"deptOrganizeCode\":\"34150080113000000000\",\n" +
                    "            \"deptName\":\"六安市自然资源和规划局\",\n" +
                    "            \"projectName\":\"关于六安环球港10#、16#楼建设工程规划许可证（新建项目）的业务\",\n" +
                    "            \"certificateLevel\":\"\",\n" +
                    "            \"state\":\"valid\",\n" +
                    "            \"holderType\":\"enterprises\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"licenseNo\":\"A08588002D904B08B6EA5691900A8068\",\n" +
                    "            \"relationLicenseNo\":\"\",\n" +
                    "            \"licenseTypeCode\":\"34150080113000000000-08F8F42D36BB41629467D14BB7ABB82E-20190826115005-1\",\n" +
                    "            \"licenseTypeName\":\"建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseName\":\"张浩-建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseNumber\":\"341501202000037\",\n" +
                    "            \"certificateDate\":\"2020-04-09\",\n" +
                    "            \"validPeriodBegin\":\"2020-04-09\",\n" +
                    "            \"validPeriodEnd\":\"2021-04-09\",\n" +
                    "            \"warningDateBegin\":\"\",\n" +
                    "            \"warningDateEnd\":\"\",\n" +
                    "            \"holderName\":\"张浩\",\n" +
                    "            \"certificateNo\":\"91341502MA2THTYY30\",\n" +
                    "            \"certificateType\":\"统一社会信用代码\",\n" +
                    "            \"deptOrganizeCode\":\"34150080113000000000\",\n" +
                    "            \"deptName\":\"六安市自然资源和规划局\",\n" +
                    "            \"projectName\":\"关于六安环球港S1#、S2#、S3#商业建设工程规划许可证（新建项目）的业务\",\n" +
                    "            \"certificateLevel\":\"\",\n" +
                    "            \"state\":\"valid\",\n" +
                    "            \"holderType\":\"enterprises\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"licenseNo\":\"EDA4157AE02447E4ABA51A0DC49F9B15\",\n" +
                    "            \"relationLicenseNo\":\"\",\n" +
                    "            \"licenseTypeCode\":\"34150080113000000000-08F8F42D36BB41629467D14BB7ABB82E-20190826115005-1\",\n" +
                    "            \"licenseTypeName\":\"建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseName\":\"郑琴-建设工程规划许可证（新建项目）\",\n" +
                    "            \"licenseNumber\":\"341501202000050\",\n" +
                    "            \"certificateDate\":\"2020-04-22\",\n" +
                    "            \"validPeriodBegin\":\"2020-04-22\",\n" +
                    "            \"validPeriodEnd\":\"2021-04-22\",\n" +
                    "            \"warningDateBegin\":\"\",\n" +
                    "            \"warningDateEnd\":\"\",\n" +
                    "            \"holderName\":\"郑琴\",\n" +
                    "            \"certificateNo\":\"91341502MA2THTYY30\",\n" +
                    "            \"certificateType\":\"统一社会信用代码\",\n" +
                    "            \"deptOrganizeCode\":\"34150080113000000000\",\n" +
                    "            \"deptName\":\"六安市自然资源和规划局\",\n" +
                    "            \"projectName\":\"关于六安环球港11#、20#楼建设工程规划许可证（新建项目）的业务\",\n" +
                    "            \"certificateLevel\":\"\",\n" +
                    "            \"state\":\"valid\",\n" +
                    "            \"holderType\":\"enterprises\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"retCode\":\"SUCCESS\",\n" +
                    "    \"state\":1,\n" +
                    "    \"statusCode\":200\n" +
                    "}";
        } else if ("ghxkzFile".equals(beanid)) {
            response = "{\n" +
                    "    \"hasFile\":\"1\",\n" +
                    "    \"fileType\":\"ofd\",\n" +
                    "    \"base64\":\"文件base64编码\",\n" +
                    "    \"retCode\":\"SUCCESS\",\n" +
                    "    \"state\":1,\n" +
                    "    \"statusCode\":200\n" +
                    "}";

        } else if ("fgj_getContractImage".equals(beanid)) {
            response = "{\n" +
                    "    \"code\":\"0\",\n" +
                    "    \"msg\":\"成功\",\n" +
                    "    \"data\":\"UEsDBBQACAAIAEZnmUwAAAAAAAAAAAAAAAATAAAAMTBfNzIwOTM5MDJfNzIwLnBkZty6dVRVXdc3\\nDCggLSKllHR3d3endHfnoZGW7u7u7haQ7pYG6e48xHv0iucK72/cz/j+et8xYJ+9195r5m/OuYpI\\nVlCYio6aEZYOjxbPWs8MlpMTlkbR1MHCEI/06ZkMlkbAzlDXwdruxxMYAMwczATMAcwSzAJ0tQaz\\nATMAMwLDA6MFowajA6MHXRlBPWTtrA0c9Q1/6yIH+hAPjBH0ihX0z/IHQVNrK0FdBxAPQXZ6WjpW\\nWkbQlZmOgZ6RgpaVhJaWhAyWmxvW0Mrgh0QMf5XMxcYQj0bI2UFEwQHUH5ZGgQ/Pwc7xx40UHi01\\nLT0sjb4uHh01LYgP32+/fGIKeEa6FvY/v9G1N8ejkba2Mvwf8oy/k1enkdV1cDC0s8KjETR0MtU3\\nlBfh1/zjI9Z/yfBRRs/MUN8BRNRRz+Fni5ilrjGIiYqpgYMJHh2Is6ihqbGJw8/bn+9+cv9NWkFD\\nfWsDQzx1kNU1YWkkDa2MQX3YQEzkYWmETS1AYuDRCFuANPztwx/WsHcAGc4S1jlVXwfM2xWKAwoT\\nChHqFRgYnDDY9x9i/v7+d4HZfheYnu6PFjra/5868Js62Msa2glYW9qADGjlgMcKMrK1hbWdgo2u\\nvuFfrAZL81NT1t/U+V05Orr/VjsMyK8QYOCUYOD/1oruD5oTRJFRWeIV/y/wBQSwcIyaw2Tw8vAgDsXgIA \"\n" +
                    "}";
        } else if ("yth_ywsjxx".equals(beanid)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"files\":[\n" +
                    "            {\n" +
                    "                \"fileName\":\"10.png\",\n" +
                    "                \"fileNetPath\":\"http://192.168.2.99:8030/storage/rest/files/download/402882e37c81942f017c9c8c3ddc009d\",\n" +
                    "                \"directoryName\":\"出让合同\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"fileName\":\"20.png\",\n" +
                    "                \"fileNetPath\":\"http://192.168.2.99:8030/storage/rest/files/download/402882e37c81942f017c9c8c3ddc009d\",\n" +
                    "                \"directoryName\":\"划拨决定书\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"fileName\":\"30.png\",\n" +
                    "                \"fileNetPath\":\"http://192.168.2.99:8030/storage/rest/files/download/402882e37c81942f017c9c8c3ddc009d\",\n" +
                    "                \"directoryName\":\"规划许可证\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"tdgyInfo\":{\n" +
                    "            \"xztk\":\"1\",\n" +
                    "            \"qllx\":\"出让\",\n" +
                    "            \"qlsdfs\":\"地上\",\n" +
                    "            \"qlr\":\"1\",\n" +
                    "            \"qlxz\":\"集体建设用地使用权\",\n" +
                    "            \"crj\":\"3\",\n" +
                    "            \"crnq\":\"3\",\n" +
                    "            \"zdyt\":\"3\",\n" +
                    "            \"jsdz\":\"3\",\n" +
                    "            \"pzwh\":\"sss\",\n" +
                    "            \"zdmj\":\"2\"\n" +
                    "        },\n" +
                    "        \"ghhsInfo\":{\n" +
                    "            \"projectname\":\"测试zk-07271022\",\n" +
                    "            \"bz\":\"111\",\n" +
                    "            \"hsjg\":\"通过\",\n" +
                    "            \"hsrq\":\"20220727\"\n" +
                    "        },\n" +
                    "        \"ghxkInfo\":{\n" +
                    "            \"ggnmj\":\"办公,12\",\n" +
                    "            \"dscs\":\"12\",\n" +
                    "            \"projectname\":\"fds21312\",\n" +
                    "            \"fzrq\":\"20220810\",\n" +
                    "            \"jzgn\":\"办公\",\n" +
                    "            \"ghxkbbgqk\":\"123\",\n" +
                    "            \"ghxkzh\":\"fff\",\n" +
                    "            \"dxcs\":\"3\",\n" +
                    "            \"jsdw\":\"123456789\"\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"message\":null,\n" +
                    "    \"stackTraceMessage\":null,\n" +
                    "    \"status\":\"success\"\n" +
                    "}";
        } else if ("gg_fjxx".equals(beanid)) {
            response = "{\n" +
                    "    \"success\":true,\n" +
                    "    \"msg\":\"success\",\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"localProjectCode\":\"项目地方代码\",\n" +
                    "            \"projectName\":\"项目名称\",\n" +
                    "            \"matterName\":\"事项名称\",\n" +
                    "            \"cwTime\":\"出文时间\",\n" +
                    "            \"attachList\":[\n" +
                    "                {\n" +
                    "                    \"fileName\":\"1.png\",\n" +
                    "                    \"url\":\"http://192.168.2.99:8030/storage/rest/files/download/402882e37c81942f017c9c8c3ddc009d\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"fileName\":\"2.png\",\n" +
                    "                    \"url\":\"http://192.168.2.99:8030/storage/rest/files/download/402882e37c81942f017c9c8c3ddc009d\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"localProjectCode\":\"项目地方代码\",\n" +
                    "            \"projectName\":\"项目名称\",\n" +
                    "            \"matterName\":\"事项名称\",\n" +
                    "            \"cwTime\":\"出文时间\",\n" +
                    "            \"attachList\":[\n" +
                    "                {\n" +
                    "                    \"fileName\":\"3.png\",\n" +
                    "                    \"url\":\"http://192.168.2.99:8030/storage/rest/files/download/402882e37c81942f017c9c8c3ddc009d\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"fileName\":\"4.png\",\n" +
                    "                    \"url\":\"http://192.168.2.99:8030/storage/rest/files/download/402882e37c81942f017c9c8c3ddc009d\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "swCxxx_dk")) {
          /*  response = "{\n \"result\": {\n   \"head\": {\n     \"tran_id\": \"TAX.COMMON.CX.TAXDETAIL\",\n     \"channel_id\": \"AH.TAX.BDCJY.GY\",\n     \"tran_seq\": \"80780f4bb2974140ad29f7e44fee1855\",\n     \"tran_date\": \"20191226\",\n     \"rtn_code\": \"200\",\n     \"rtn_msg\": \"查询成功\",\n     \"expand\": [\n       {\n         \"name\": \"expandName\",\n         \"value\": \"1340GYBDCJY\"\n       },\n       {\n         \"name\": \"expandPwd\",\n         \"value\": \"1340GYBDCJY\"\n       }\n     ]\n   },\n   \"body\": {\n     \"skmxcxlb\": [\n       {\n         \"nsrsbh\": \"340223198710292520\",\n         \"jyskblb\": [\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b22c4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10111\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"335\",\n             \"jsje\": \"1340000\",\n             \"sl\": 5.0E-4,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"印花税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b2ac4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10111\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"335\",\n             \"jsje\": \"1340000\",\n             \"sl\": 5.0E-4,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"印花税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b22c4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10119\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"40200\",\n             \"jsje\": \"1340000\",\n             \"sl\": 0.03,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"契税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b2ac4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10119\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"40200\",\n             \"jsje\": \"1340000\",\n             \"sl\": 0.03,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"契税\"\n           }\n         ],\n         \"nsrmc\": \"何樱红\",\n         \"dz\": \"合肥市\",\n         \"jyjg\": \"1\",\n         \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n         \"zrfcsfbz\": \"1\",\n         \"jzmj\": \"100\",\n         \"ynse\": \"81070.00\",\n         \"jsje\": \"2680000\"\n       },\n       {\n         \"nsrsbh\": \"441702197407131711\",\n         \"jyskblb\": [\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10106\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"2552380.95\",\n             \"sl\": 0.2,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"个人所得税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10109\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"0\",\n             \"sl\": 0.07,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"城市建设维护税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"30203\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"0\",\n             \"sl\": 0.03,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"教育费附加\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"30216\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"0\",\n             \"sl\": 0.02,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"地方教育附加\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10101\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"2552380.95\",\n             \"sl\": 0.05,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"增值税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10111\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"670\",\n             \"jsje\": \"2680000\",\n             \"sl\": 5.0E-4,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"印花税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10113\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"2552380.95\",\n             \"sl\": 0.3,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"土地增值税\"\n           }\n         ],\n         \"nsrmc\": \"冯秀祥\",\n         \"dz\": \"黄山市苹果路\",\n         \"jyjg\": \"1\",\n         \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n         \"zrfcsfbz\": \"0\",\n         \"jzmj\": \"100\",\n         \"ynse\": \"670.00\",\n         \"jsje\": \"2680000\"\n       }\n     ],\n     \"jyzt_mc\": \"审核通过待申报\",\n     \"jyzt_dm\": \"300\",\n     \"shyj\": \"通过\",\n     \"wsbj\": \"N\"\n   }\n }\n" +
                    "}";*/
            // 单个
//            response = "{\"result\":{\"head\":{\"tran_id\":\"TAX.COMMON.CX.TAXDETAIL\",\"channel_id\":\"AH.TAX.BDCJY.HS\",\"tran_seq\":\"233d79dfec4346838fe428fc74f92eeb\",\"tran_date\":\"20200420\",\"rtn_code\":\"200\",\"rtn_msg\":\"查询成功\",\"expand\":[{\"name\":\"expandName\",\"value\":\"1340HSBDCJY\"},{\"name\":\"expandPwd\",\"value\":\"1340HSBDCJY\"}]},\"body\":{\"skmxcxlb\":[{\"nsrsbh\":\"342701196608250625\",\"jyskblb\":[{\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"nsr_id\":\"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\"zsxm_dm\":\"10101\",\"zrfcsfbz\":\"0\",\"jsje\":\"550000\",\"sl\":0,\"jyfe\":1,\"zsxmmc\":\"增值税\",\"zspm_dm\":\"10101XXX\",\"zspmmc\":\"XXX\"},{\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"nsr_id\":\"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\"zsxm_dm\":\"10106\",\"zrfcsfbz\":\"0\",\"jsje\":\"550000\",\"sl\":0,\"jyfe\":1,\"zsxmmc\":\"个人所得税\",\"zspm_dm\":\"10101XXX\",\"zspmmc\":\"XXX\"}],\"nsrmc\":\"钱卫兵\",\"dz\":\"安徽省黄山市屯溪区长干东路１０８号１幢１０１室\",\"jyjg\":\"550000\",\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"sbtxhfj\":\"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\"zrfcsfbz\":\"0\",\"jzmj\":\"67.29\",\"ynse\":\"0.00\",\"jsje\":\"550000\"},{\"nsrsbh\":\"342723196906078913\",\"jyskblb\":[{\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"nsr_id\":\"6c0d472bde6049498749845cb7f5626f\",\"zsxm_dm\":\"10119\",\"zrfcsfbz\":\"1\",\"ynse\":\"5500.00\",\"jsje\":\"275000.00\",\"sl\":0.01,\"jyfe\":1,\"zsxmmc\":\"契税\",\"zspm_dm\":\"10119XXX\",\"zspmmc\":\"XXX\"}],\"nsrmc\":\"陆连众\",\"dz\":\"安徽省歙县绍濂乡庄头村 2 组\",\"jyjg\":\"550000\",\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"sbtxhfj\":\"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\"zrfcsfbz\":\"1\",\"jzmj\":\"67.29\",\"ynse\":\"5500.00\",\"jsje\":\"550000\"}],\"jyzt_mc\":\"已缴款\",\"qyjyzsbz\":\"Y\",\"sbtxhfj\":\"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\"jyzt_dm\":\"500\",\"wsbj\":\"Y\",\"xgr\":\"胡小芳\",\"jsje\":\"550000\"}}}\n";
            // 批量
            response="{\n" +
                    "    \"result\": {\n" +
                    "        \"head\": {\n" +
                    "            \"tran_id\": \"TAX.COMMON.CX.TAXDETAIL\",\n" +
                    "            \"channel_id\": \"AH.TAX.BDCJY.HS\",\n" +
                    "            \"tran_seq\": \"233d79dfec4346838fe428fc74f92eeb\",\n" +
                    "            \"tran_date\": \"20200420\",\n" +
                    "            \"rtn_code\": \"200\",\n" +
                    "            \"rtn_msg\": \"查询成功\",\n" +
                    "            \"expand\": [\n" +
                    "                {\n" +
                    "                    \"name\": \"expandName\",\n" +
                    "                    \"value\": \"1340HSBDCJY\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\": \"expandPwd\",\n" +
                    "                    \"value\": \"1340HSBDCJY\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"body\": {\n" +
                    "            \"skmxcxlb\": [\n" +
                    "                {\n" +
                    "                    \"nsrsbh\": \"342701196608250625\",\n" +
                    "                    \"jyskblb\": [\n" +
                    "                        {\n" +
                    "                            \"fwuuid\": \"111\",\n" +
                    "                            \"nsr_id\": \"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\n" +
                    "                            \"zsxm_dm\": \"10101\",\n" +
                    "                            \"zrfcsfbz\": \"0\",\n" +
                    "                            \"jsje\": \"550000\",\n" +
                    "                            \"sl\": 0,\n" +
                    "                            \"jyfe\": 1,\n" +
                    "                            \"zsxmmc\": \"增值税\",\n" +
                    "                            \"zspm_dm\": \"10101XXX\",\n" +
                    "                            \"zspmmc\": \"XXX\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"fwuuid\": \"111\",\n" +
                    "                            \"nsr_id\": \"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\n" +
                    "                            \"zsxm_dm\": \"10106\",\n" +
                    "                            \"zrfcsfbz\": \"0\",\n" +
                    "                            \"jsje\": \"550000\",\n" +
                    "                            \"sl\": 0,\n" +
                    "                            \"jyfe\": 1,\n" +
                    "                            \"zsxmmc\": \"个人所得税\",\n" +
                    "                            \"zspm_dm\": \"10101XXX\",\n" +
                    "                            \"zspmmc\": \"XXX\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"nsrmc\": \"钱卫兵\",\n" +
                    "                    \"dz\": \"安徽省黄山市屯溪区长干东路１０８号１幢１０１室\",\n" +
                    "                    \"jyjg\": \"550000\",\n" +
                    "                    \"fwuuid\": \"111\",\n" +
                    "                    \"sbtxhfj\": \"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\n" +
                    "                    \"zrfcsfbz\": \"0\",\n" +
                    "                    \"jzmj\": \"67.29\",\n" +
                    "                    \"ynse\": \"0.00\",\n" +
                    "                    \"jsje\": \"550000\"\n" +
                    "                },\n" +
                    "                 {\n" +
                    "                    \"nsrsbh\": \"342701196608250625\",\n" +
                    "                    \"jyskblb\": [\n" +
                    "                        {\n" +
                    "                            \"fwuuid\": \"222\",\n" +
                    "                            \"nsr_id\": \"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\n" +
                    "                            \"zsxm_dm\": \"10101\",\n" +
                    "                            \"zrfcsfbz\": \"0\",\n" +
                    "                            \"jsje\": \"550000\",\n" +
                    "                            \"sl\": 0,\n" +
                    "                            \"jyfe\": 1,\n" +
                    "                            \"zsxmmc\": \"增值税\",\n" +
                    "                            \"zspm_dm\": \"10101XXX\",\n" +
                    "                            \"zspmmc\": \"XXX\"\n" +
                    "                        },\n" +
                    "                        {\n" +
                    "                            \"fwuuid\": \"222\",\n" +
                    "                            \"nsr_id\": \"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\n" +
                    "                            \"zsxm_dm\": \"10106\",\n" +
                    "                            \"zrfcsfbz\": \"0\",\n" +
                    "                            \"jsje\": \"550000\",\n" +
                    "                            \"sl\": 0,\n" +
                    "                            \"jyfe\": 1,\n" +
                    "                            \"zsxmmc\": \"个人所得税\",\n" +
                    "                            \"zspm_dm\": \"10101XXX\",\n" +
                    "                            \"zspmmc\": \"XXX\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"nsrmc\": \"钱卫兵\",\n" +
                    "                    \"dz\": \"安徽省黄山市屯溪区长干东路１０８号１幢１０１室\",\n" +
                    "                    \"jyjg\": \"550000\",\n" +
                    "                    \"fwuuid\": \"222\",\n" +
                    "                    \"sbtxhfj\": \"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\n" +
                    "                    \"zrfcsfbz\": \"0\",\n" +
                    "                    \"jzmj\": \"67.29\",\n" +
                    "                    \"ynse\": \"0.00\",\n" +
                    "                    \"jsje\": \"550000\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nsrsbh\": \"342723196906078913\",\n" +
                    "                    \"jyskblb\": [\n" +
                    "                        {\n" +
                    "                            \"fwuuid\": \"111\",\n" +
                    "                            \"nsr_id\": \"6c0d472bde6049498749845cb7f5626f\",\n" +
                    "                            \"zsxm_dm\": \"10119\",\n" +
                    "                            \"zrfcsfbz\": \"1\",\n" +
                    "                            \"ynse\": \"5500.00\",\n" +
                    "                            \"jsje\": \"275000.00\",\n" +
                    "                            \"sl\": 0.01,\n" +
                    "                            \"jyfe\": 1,\n" +
                    "                            \"zsxmmc\": \"契税\",\n" +
                    "                            \"zspm_dm\": \"10119XXX\",\n" +
                    "                            \"zspmmc\": \"XXX\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"nsrmc\": \"陆连众\",\n" +
                    "                    \"dz\": \"安徽省歙县绍濂乡庄头村 2 组\",\n" +
                    "                    \"jyjg\": \"550000\",\n" +
                    "                    \"fwuuid\": \"111\",\n" +
                    "                    \"sbtxhfj\": \"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\n" +
                    "                    \"zrfcsfbz\": \"1\",\n" +
                    "                    \"jzmj\": \"67.29\",\n" +
                    "                    \"ynse\": \"5500.00\",\n" +
                    "                    \"jsje\": \"550000\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nsrsbh\": \"342723196906078913\",\n" +
                    "                    \"jyskblb\": [\n" +
                    "                        {\n" +
                    "                            \"fwuuid\": \"222\",\n" +
                    "                            \"nsr_id\": \"6c0d472bde6049498749845cb7f5626f\",\n" +
                    "                            \"zsxm_dm\": \"10119\",\n" +
                    "                            \"zrfcsfbz\": \"1\",\n" +
                    "                            \"ynse\": \"5500.00\",\n" +
                    "                            \"jsje\": \"275000.00\",\n" +
                    "                            \"sl\": 0.01,\n" +
                    "                            \"jyfe\": 1,\n" +
                    "                            \"zsxmmc\": \"契税\",\n" +
                    "                            \"zspm_dm\": \"10119XXX\",\n" +
                    "                            \"zspmmc\": \"XXX\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"nsrmc\": \"陆连众\",\n" +
                    "                    \"dz\": \"安徽省歙县绍濂乡庄头村 2 组\",\n" +
                    "                    \"jyjg\": \"550000\",\n" +
                    "                    \"fwuuid\": \"222\",\n" +
                    "                    \"sbtxhfj\": \"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\n" +
                    "                    \"zrfcsfbz\": \"1\",\n" +
                    "                    \"jzmj\": \"67.29\",\n" +
                    "                    \"ynse\": \"5500.00\",\n" +
                    "                    \"jsje\": \"550000\"\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"jyzt_mc\": \"已缴款\",\n" +
                    "            \"qyjyzsbz\": \"Y\",\n" +
                    "            \"sbtxhfj\": \"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\n" +
                    "            \"jyzt_dm\": \"500\",\n" +
                    "            \"wsbj\": \"Y\",\n" +
                    "            \"xgr\": \"胡小芳\",\n" +
                    "            \"jsje\": \"550000\",\n" +
                    "            \"mergebz\": \"1\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "queryTdcrList")) {
            response = "{\"type\":\"success\",\"result\":{\"data\":[{\"ydrjlsx\":\"\",\"qlsdfs\":\"\",\"qslycl\":\"\",\"bzqlrmc\":\"\",\"szdz\":\"宏业路\",\"yddgsj\":0,\"pzyt\":\"\",\"qsxz\":\"\",\"djzq\":\"\",\"syqzzrq\":0,\"pzmj\":\"\",\"tdzl\":\"蚌山区胜利路南侧、宏业路西侧\",\"bdcdyh\":\"\",\"syqlx\":\"出让\",\"id\":101574,\"ydjzgdxx\":\"\",\"szxz\":\"用地界限\",\"crsr\":[],\"syqksrq\":0,\"pzdw\":\"\",\"sjjdsj\":0,\"ssnf\":2017,\"xzq\":\"340303\",\"tzqd\":\"\",\"jlrq\":0,\"ydjdsj\":0,\"sjdgsj\":0,\"ydlhlsx\":\"\",\"rjl\":\"\",\"gjbgbah\":\"\",\"htqdsj\":0,\"bzzjrmc\":\"\",\"ydjzgdsx\":\"\",\"tdyt\":\"23693\",\"scrid\":0,\"status\":0,\"fzmj\":\"\",\"xzflag\":0,\"xmbh\":\"TDCR20220001\",\"fj\":[{\"fId\":\"101585\",\"fname\":\"土地出让合同\",\"path\":\"hebeUpload/bbcggl/tdcr/101574/101585/0147.jpg\",\"name\":\"0147.jpg\",\"id\":101594},{\"fId\":\"101585\",\"fname\":\"土地出让合同\",\"path\":\"hebeUpload/bbcggl/tdcr/101574/101585/0150.jpg\",\"name\":\"0150.jpg\",\"id\":101595},{\"fId\":\"101586\",\"fname\":\"土地出让宗地图\",\"path\":\"hebeUpload/bbcggl/tdcr/101574/101586/0164.jpg\",\"name\":\"0164.jpg\",\"id\":101623},{\"fId\":\"101627\",\"fname\":\"规划条件设计图\",\"path\":\"hebeUpload/bbcggl/tdcr/101574/101627/0166.jpg\",\"name\":\"0166.jpg\",\"id\":101630},{\"fId\":\"102278\",\"fname\":\"土地出让金缴纳凭证\",\"path\":\"hebeUpload/bbcggl/tdcr/101574/102278/0140.jpg\",\"name\":\"0140.jpg\",\"id\":102281}],\"sznz\":\"用地界限\",\"jzzmj\":\"\",\"zdbh\":\"蚌挂（2017）59号\",\"szbz\":\"胜利路\",\"dzjgh\":\"3403002017B01067\",\"crfs\":\"招标出让\",\"pzsj\":20170728000000,\"ztjzwxz\":\"\",\"crnx\":\"\",\"bz\":\"\",\"scmj\":\"\",\"createuserid\":0,\"crj\":[],\"ydjzmdxx\":\"\",\"ydlhlxx\":\"\",\"crjk\":\"\",\"crmj\":\"2.9742\",\"tdcjj\":\"\",\"htbh\":\"3403002017059\",\"ydrjlxx\":\"\",\"pzwh\":\"\",\"sjjgsj\":0,\"srr\":\"安徽宏茂置业有限公司\",\"ydjgsj\":0,\"xmmc\":\"蚌山区商住用地项目\",\"ydjzmjsx\":\"\",\"createTime\":0,\"sctime\":0,\"tdsyzmc\":\"蚌埠市国土资源局\"}]}}\n";
        } else if (StringUtils.equals(beanid, "queryTdhbList")) {
            response = "{\"type\":\"success\",\"result\":{\"data\":[{\"ydrjlsx\":0,\"hbjdsbh\":\"340700划拨20210010\",\"xzflag\":0,\"xmbh\":\"TDBH20210004\",\"fj\":[{\"fId\":\"37571\",\"fname\":\"土地划拨材料\",\"path\":\"hebeUpload/bbcggl/tdhb/17577/37571/划拨决定书.pdf\",\"name\":\"划拨决定书.pdf\",\"id\":37574},{\"fId\":\"37571\",\"fname\":\"土地划拨材料\",\"path\":\"hebeUpload/bbcggl/tdhb/17577/37571/宗地图.jpg\",\"name\":\"宗地图.jpg\",\"id\":37601},{\"fId\":\"37571\",\"fname\":\"土地划拨材料\",\"path\":\"hebeUpload/bbcggl/tdhb/17577/37571/划拨决定书-附件1.jpg\",\"name\":\"划拨决定书-附件1.jpg\",\"id\":37602}],\"jzzmj\":3560.78,\"zdbh\":\"铜划拨[2021]10\",\"hbjk\":\"\",\"dzjgh\":\"3407002021A00269\",\"gyfs\":\"划拨\",\"yddgsj\":0,\"ztjzxz\":\"\",\"tdzl\":\"翠湖四路以北、聚秀路以西\",\"pzsj\":20210419000000,\"bdcdyh\":\"340705007012GB00202W00000000\",\"bz\":\"\",\"createuserid\":0,\"id\":17577,\"ydjzmdxx\":0,\"ydlhlxx\":0,\"ydjzgdxx\":0,\"createtime\":0,\"hbmj\":0.7741,\"ydrjlxx\":0,\"pzdw\":\"铜陵市人民政府\",\"sjjdsj\":0,\"ssnf\":0,\"xzq\":\"340705\",\"pzwh\":\"铜划拨〔2021〕10号\",\"sjjgsj\":0,\"syqr\":\"铜陵市公共交通总公司\",\"ydjdsj\":20210601000000,\"ydjgsj\":20230601000000,\"sjdgsj\":0,\"xmmc\":\"市公交总公司金大地公交首末站项目\",\"ydlhlsx\":0,\"ydjzmjsx\":0,\"htqdsj\":20210428000000,\"sctime\":0,\"ydjzgdsx\":0,\"tdyt\":\"交通服务场站用地\",\"scrid\":0,\"status\":0}]}}\n";
        } else if (StringUtils.equals(beanid, "queryGcghList")) {
            response = "{\n" +
                    "    \"type\":\"success\",\n" +
                    "    \"result\":{\n" +
                    "        \"data\":[\n" +
                    "            {\n" +
                    "                \"lxrdh\":\"\",\n" +
                    "                \"ajbh\":\"\",\n" +
                    "                \"xmbh\":\"JSGCGHXK20221128\",\n" +
                    "                \"fj\":[\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102186\",\n" +
                    "                        \"fname\":\"建设工程规划许可材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102186/许可证.jpg\",\n" +
                    "                        \"name\":\"许可证.jpg\",\n" +
                    "                        \"id\":102189\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/《申请规划核实报告》规划许可证委托书.pdf\",\n" +
                    "                        \"name\":\"《申请规划核实报告》规划许可证委托书.pdf\",\n" +
                    "                        \"id\":102393\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/鸿运华府竣工测量成果报告PDF.pdf\",\n" +
                    "                        \"name\":\"鸿运华府竣工测量成果报告PDF.pdf\",\n" +
                    "                        \"id\":102400\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图二  竣工地形图.dwg\",\n" +
                    "                        \"name\":\"附图二  竣工地形图.dwg\",\n" +
                    "                        \"id\":102408\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图三   绿地面积统计图 .dwg\",\n" +
                    "                        \"name\":\"附图三   绿地面积统计图 .dwg\",\n" +
                    "                        \"id\":102409\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图四  公建配套统计图  .dwg\",\n" +
                    "                        \"name\":\"附图四  公建配套统计图  .dwg\",\n" +
                    "                        \"id\":102410\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图五   地下室平面图.dwg\",\n" +
                    "                        \"name\":\"附图五   地下室平面图.dwg\",\n" +
                    "                        \"id\":102411\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图一  竣工平面图.dwg\",\n" +
                    "                        \"name\":\"附图一  竣工平面图.dwg\",\n" +
                    "                        \"id\":102413\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图六  建筑物高度测量图.dwg\",\n" +
                    "                        \"name\":\"附图六  建筑物高度测量图.dwg\",\n" +
                    "                        \"id\":102414\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/竣工测量报告 - 副本.doc\",\n" +
                    "                        \"name\":\"竣工测量报告 - 副本.doc\",\n" +
                    "                        \"id\":102415\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/1-8#地下车库±0报告扫描.pdf\",\n" +
                    "                        \"name\":\"1-8#地下车库±0报告扫描.pdf\",\n" +
                    "                        \"id\":102417\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/规划验线.pdf\",\n" +
                    "                        \"name\":\"规划验线.pdf\",\n" +
                    "                        \"id\":102419\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/鸿运华府总平面.pdf\",\n" +
                    "                        \"name\":\"鸿运华府总平面.pdf\",\n" +
                    "                        \"id\":102440\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"xkzhbh\":\"皖N 3131402\",\n" +
                    "                \"ftfjmc\":\"\",\n" +
                    "                \"mlaqh\":\"\",\n" +
                    "                \"hfrq\":20180705000000,\n" +
                    "                \"ydmj\":\"105835.61\",\n" +
                    "                \"bdcdyh\":\"\",\n" +
                    "                \"createuserid\":0,\n" +
                    "                \"xkzh\":\"建字第340303201800223号\",\n" +
                    "                \"id\":102166,\n" +
                    "                \"jswz\":\"胜利路南侧，宏业路西侧\",\n" +
                    "                \"jsgm\":\"\",\n" +
                    "                \"createtime\":20220728161428,\n" +
                    "                \"ywjlrq\":0,\n" +
                    "                \"lxr\":\"\",\n" +
                    "                \"jsdw\":\"安徽宏茂置业有限公司\",\n" +
                    "                \"xzq\":\"340303\",\n" +
                    "                \"user_id\":1,\n" +
                    "                \"ydxz\":\"居住用地\",\n" +
                    "                \"nd\":\"2018\",\n" +
                    "                \"jsxmmc\":\"鸿运华府1--8楼、地下车库\",\n" +
                    "                \"sctime\":0,\n" +
                    "                \"ghspyj\":\"\",\n" +
                    "                \"scrid\":0,\n" +
                    "                \"status\":0,\n" +
                    "                \"username\":\"admin\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "queryYdhsList")) {
            response = "{\n" +
                    "    \"type\":\"success\",\n" +
                    "    \"result\":{\n" +
                    "        \"data\":[\n" +
                    "            {\n" +
                    "                \"lxrdh\":\"\",\n" +
                    "                \"ajbh\":\"\",\n" +
                    "                \"xmbh\":\"JSGCGHXK20221128\",\n" +
                    "                \"fj\":[\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102186\",\n" +
                    "                        \"fname\":\"建设工程规划许可材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102186/许可证.jpg\",\n" +
                    "                        \"name\":\"许可证.jpg\",\n" +
                    "                        \"id\":102189\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/《申请规划核实报告》规划许可证委托书.pdf\",\n" +
                    "                        \"name\":\"《申请规划核实报告》规划许可证委托书.pdf\",\n" +
                    "                        \"id\":102393\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/鸿运华府竣工测量成果报告PDF.pdf\",\n" +
                    "                        \"name\":\"鸿运华府竣工测量成果报告PDF.pdf\",\n" +
                    "                        \"id\":102400\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图二  竣工地形图.dwg\",\n" +
                    "                        \"name\":\"附图二  竣工地形图.dwg\",\n" +
                    "                        \"id\":102408\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图三   绿地面积统计图 .dwg\",\n" +
                    "                        \"name\":\"附图三   绿地面积统计图 .dwg\",\n" +
                    "                        \"id\":102409\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图四  公建配套统计图  .dwg\",\n" +
                    "                        \"name\":\"附图四  公建配套统计图  .dwg\",\n" +
                    "                        \"id\":102410\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图五   地下室平面图.dwg\",\n" +
                    "                        \"name\":\"附图五   地下室平面图.dwg\",\n" +
                    "                        \"id\":102411\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图一  竣工平面图.dwg\",\n" +
                    "                        \"name\":\"附图一  竣工平面图.dwg\",\n" +
                    "                        \"id\":102413\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/附图六  建筑物高度测量图.dwg\",\n" +
                    "                        \"name\":\"附图六  建筑物高度测量图.dwg\",\n" +
                    "                        \"id\":102414\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/竣工测量报告 - 副本.doc\",\n" +
                    "                        \"name\":\"竣工测量报告 - 副本.doc\",\n" +
                    "                        \"id\":102415\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/1-8#地下车库±0报告扫描.pdf\",\n" +
                    "                        \"name\":\"1-8#地下车库±0报告扫描.pdf\",\n" +
                    "                        \"id\":102417\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/规划验线.pdf\",\n" +
                    "                        \"name\":\"规划验线.pdf\",\n" +
                    "                        \"id\":102419\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102390\",\n" +
                    "                        \"fname\":\"申报材料\",\n" +
                    "                        \"path\":\"hebeUpload/bbcggl/gcghxk/102166/102390/鸿运华府总平面.pdf\",\n" +
                    "                        \"name\":\"鸿运华府总平面.pdf\",\n" +
                    "                        \"id\":102440\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"xkzhbh\":\"皖N 3131402\",\n" +
                    "                \"ftfjmc\":\"\",\n" +
                    "                \"mlaqh\":\"\",\n" +
                    "                \"hfrq\":20180705000000,\n" +
                    "                \"ydmj\":\"105835.61\",\n" +
                    "                \"bdcdyh\":\"\",\n" +
                    "                \"createuserid\":0,\n" +
                    "                \"xkzh\":\"建字第340303201800223号\",\n" +
                    "                \"id\":102166,\n" +
                    "                \"jswz\":\"胜利路南侧，宏业路西侧\",\n" +
                    "                \"jsgm\":\"\",\n" +
                    "                \"createtime\":20220728161428,\n" +
                    "                \"ywjlrq\":0,\n" +
                    "                \"lxr\":\"\",\n" +
                    "                \"jsdw\":\"安徽宏茂置业有限公司\",\n" +
                    "                \"xzq\":\"340303\",\n" +
                    "                \"user_id\":1,\n" +
                    "                \"ydxz\":\"居住用地\",\n" +
                    "                \"nd\":\"2018\",\n" +
                    "                \"jsxmmc\":\"鸿运华府1--8楼、地下车库\",\n" +
                    "                \"sctime\":0,\n" +
                    "                \"ghspyj\":\"\",\n" +
                    "                \"scrid\":0,\n" +
                    "                \"status\":0,\n" +
                    "                \"username\":\"admin\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
        }else if (StringUtils.equals(beanid, "queryTdcrList")) {
            response = "{\n" +
                    "    \"type\":\"success\",\n" +
                    "    \"result\":{\n" +
                    "        \"data\":[\n" +
                    "            {\n" +
                    "                \"ydrjlsx\":\"\",\n" +
                    "                \"qlsdfs\":\"\",\n" +
                    "                \"qslycl\":\"\",\n" +
                    "                \"bzqlrmc\":\"蚌埠子鑫建材科技有限责任公司\",\n" +
                    "                \"szdz\":\"\",\n" +
                    "                \"yddgsj\":20220909000000,\n" +
                    "                \"pzyt\":\"工业用地\",\n" +
                    "                \"qsxz\":\"\",\n" +
                    "                \"djzq\":\"\",\n" +
                    "                \"syqzzrq\":0,\n" +
                    "                \"pzmj\":\"1.386655\",\n" +
                    "                \"tdzl\":\"禹会区新居路南侧、富民南路东侧\",\n" +
                    "                \"bdcdyh\":\"340304003043GB01234\",\n" +
                    "                \"syqlx\":\"出让\",\n" +
                    "                \"id\":72,\n" +
                    "                \"ydjzgdxx\":\"\",\n" +
                    "                \"szxz\":\"\",\n" +
                    "                \"crsr\":[\n" +
                    "\n" +
                    "                ],\n" +
                    "                \"syqksrq\":0,\n" +
                    "                \"pzdw\":\"\",\n" +
                    "                \"sjjdsj\":0,\n" +
                    "                \"ssnf\":2022,\n" +
                    "                \"xzq\":\"340304\",\n" +
                    "                \"tzqd\":\"\",\n" +
                    "                \"jlrq\":0,\n" +
                    "                \"ydjdsj\":20220315000000,\n" +
                    "                \"sjdgsj\":0,\n" +
                    "                \"ydlhlsx\":\"\",\n" +
                    "                \"rjl\":\"≥1.2\",\n" +
                    "                \"gjbgbah\":\"\",\n" +
                    "                \"htqdsj\":20220315000000,\n" +
                    "                \"bzzjrmc\":\"\",\n" +
                    "                \"ydjzgdsx\":\"\",\n" +
                    "                \"tdyt\":\"23689\",\n" +
                    "                \"scrid\":0,\n" +
                    "                \"status\":0,\n" +
                    "                \"fzmj\":\"1.386655\",\n" +
                    "                \"xzflag\":0,\n" +
                    "                \"xmbh\":\"TDCR20220011\",\n" +
                    "                \"fj\":[\n" +
                    "                    {\n" +
                    "                        \"fId\":\"107607\",\n" +
                    "                        \"fname\":\"测试材料\",\n" +
                    "                        \"path\":\"hebeUpload/cggl/tdcr/72/107607/辅房审批表.pdf\",\n" +
                    "                        \"name\":\"辅房审批表.pdf\",\n" +
                    "                        \"id\":107609\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"sznz\":\"\",\n" +
                    "                \"jzzmj\":\"\",\n" +
                    "                \"zdbh\":\"蚌挂(2022)8号\",\n" +
                    "                \"szbz\":\"\",\n" +
                    "                \"dzjgh\":\"3403002022B00436\",\n" +
                    "                \"crfs\":\"挂牌出让\",\n" +
                    "                \"pzsj\":20220228000000,\n" +
                    "                \"ztjzwxz\":\"\",\n" +
                    "                \"crnx\":\"\",\n" +
                    "                \"bz\":\"\",\n" +
                    "                \"scmj\":\"1.386655\",\n" +
                    "                \"createuserid\":0,\n" +
                    "                \"crj\":[\n" +
                    "\n" +
                    "                ],\n" +
                    "                \"ydjzmdxx\":\"\",\n" +
                    "                \"ydlhlxx\":\"\",\n" +
                    "                \"crjk\":\"\",\n" +
                    "                \"crmj\":\"1.386655\",\n" +
                    "                \"tdcjj\":\"356\",\n" +
                    "                \"htbh\":\"3403002022008\",\n" +
                    "                \"ydrjlxx\":\"\",\n" +
                    "                \"pzwh\":\"蚌土上市审批字〔2022〕4号\",\n" +
                    "                \"sjjgsj\":0,\n" +
                    "                \"srr\":\"蚌埠子鑫建材科技有限责任公司\",\n" +
                    "                \"ydjgsj\":20240309000000,\n" +
                    "                \"xmmc\":\"年产60万吨干混砂浆生产线项目\",\n" +
                    "                \"ydjzmjsx\":\"\",\n" +
                    "                \"createTime\":0,\n" +
                    "                \"sctime\":0,\n" +
                    "                \"tdsyzmc\":\"蚌埠市国土资源局\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "queryYdghList")) {
            response = "{\n" +
                    "    \"type\":\"success\",\n" +
                    "    \"result\":{\n" +
                    "        \"data\":[\n" +
                    "            {\n" +
                    "                \"lxrdh\":\"\",\n" +
                    "                \"ajbh\":\"\",\n" +
                    "                \"xmbh\":\"JSYDHS20222120\",\n" +
                    "                \"fj\":[\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102144\",\n" +
                    "                        \"fname\":\"建设工程规划核实\",\n" +
                    "                        \"path\":\"hebeUpload/cggl/ydghhs/102137/102144/20220728100735.jpg\",\n" +
                    "                        \"name\":\"20220728100735.jpg\",\n" +
                    "                        \"id\":102146\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102144\",\n" +
                    "                        \"fname\":\"建设工程规划核实\",\n" +
                    "                        \"path\":\"hebeUpload/cggl/ydghhs/102137/102144/鸿运华府总平面.pdf\",\n" +
                    "                        \"name\":\"鸿运华府总平面.pdf\",\n" +
                    "                        \"id\":102568\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102144\",\n" +
                    "                        \"fname\":\"建设工程规划核实\",\n" +
                    "                        \"path\":\"hebeUpload/cggl/ydghhs/102137/102144/建 设 工 程 建 筑 面 积 核 算 表.png\",\n" +
                    "                        \"name\":\"建 设 工 程 建 筑 面 积 核 算 表.png\",\n" +
                    "                        \"id\":102570\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102144\",\n" +
                    "                        \"fname\":\"建设工程规划核实\",\n" +
                    "                        \"path\":\"hebeUpload/cggl/ydghhs/102137/102144/《申请规划核实报告》规划许可证委托书.pdf\",\n" +
                    "                        \"name\":\"《申请规划核实报告》规划许可证委托书.pdf\",\n" +
                    "                        \"id\":102572\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102144\",\n" +
                    "                        \"fname\":\"建设工程规划核实\",\n" +
                    "                        \"path\":\"hebeUpload/cggl/ydghhs/102137/102144/1-8#地下车库±0报告扫描.pdf\",\n" +
                    "                        \"name\":\"1-8#地下车库±0报告扫描.pdf\",\n" +
                    "                        \"id\":102574\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"fId\":\"102144\",\n" +
                    "                        \"fname\":\"建设工程规划核实\",\n" +
                    "                        \"path\":\"hebeUpload/cggl/ydghhs/102137/102144/规划验线.pdf\",\n" +
                    "                        \"name\":\"规划验线.pdf\",\n" +
                    "                        \"id\":102576\n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"xkzhbh\":\"编号第340303202200092号\",\n" +
                    "                \"ftfjmc\":\"\",\n" +
                    "                \"mlaqh\":\"\",\n" +
                    "                \"hfrq\":20220704000000,\n" +
                    "                \"ydmj\":\"\",\n" +
                    "                \"bdcdyh\":\"340304003043GB01236\",\n" +
                    "                \"createuserid\":0,\n" +
                    "                \"xkzh\":\"编号第340303202200092号\",\n" +
                    "                \"id\":102137,\n" +
                    "                \"jswz\":\"宏业路以西、胜利路以南\",\n" +
                    "                \"jsgm\":\"66350.32\",\n" +
                    "                \"createtime\":20220902144820,\n" +
                    "                \"ywjlrq\":0,\n" +
                    "                \"jsdw\":\"安徽宏茂置业有限公司\",\n" +
                    "                \"xzq\":\"340303\",\n" +
                    "                \"user_id\":101070,\n" +
                    "                \"ydxz\":\"\",\n" +
                    "                \"nd\":\"2022\",\n" +
                    "                \"jsxmmc\":\"鸿运华府1、2、4、6、8楼、地下车库\",\n" +
                    "                \"sctime\":0,\n" +
                    "                \"ghspyj\":\"\",\n" +
                    "                \"scrid\":0,\n" +
                    "                \"status\":0,\n" +
                    "                \"username\":\"张勐\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
        }else if(StringUtils.equals(beanid, "queryDchyCgfl")){
                response = "{\n" +
                        "\"msg\": \"success\", \n" +
                        " \n" +
                        "\"result\": [\n" +
                        "{\n" +
                        "\"chsx\": \"选址测绘\", \n" +
                        " \n" +
                        "\"type\": \"gcjsxk\", \n" +
                        " \n" +
                        "\"rowid\": \"d21915fc711f407ca697da247a655484\", \n" +
                        " \n" +
                        "\"project_id\": \"e3fff9c950504b9481ce46e7e0ef57b9\", \n" +
                        " \n" +
                        "\"chcgList\": [\n" +
                        "{\n" +
                        "\"attachname\": \"11.jpg\", \n" +
                        " \n" +
                        "\"filesize\": \"166.24 KB\", \n" +
                        " \n" +
                        "\"attachpath\": \"hebeUpload/bbdchy/gcjsxk/e3fff9c950504b9481ce46e7e0ef57b9/d21915fc711f407ca697da247a655484/62bf1edb36141f114521ec4bb4175579.jpg\", \n" +
                        " \n" +
                        "\"chcgRowid\": \"d21915fc711f407ca697da247a655484\", \n" +
                        " \n" +
                        "\"cgflname\": \"测绘成果\" \n" +
                        " \n" +
                        "}\n" +
                        "] \n" +
                        " \n" +
                        "},\n" +
                        "{\n" +
                        "\"chsx\": \"规划放线\", \n" +
                        " \n" +
                        "\"type\": \"sgxk\", \n" +
                        " \n" +
                        "\"rowid\": \"9fe02a9a87a546bab6e508f375452ef3\", \n" +
                        " \n" +
                        "\"project_id\": \"e3fff9c950504b9481ce46e7e0ef57b9\", \n" +
                        " \n" +
                        "\"chcgList\": [\n" +
                        "{\n" +
                        "\"attachname\": \"paper1.jpg\", \n" +
                        " \n" +
                        "\"filesize\": \"1.48 MB\", \n" +
                        " \n" +
                        "\"attachpath\": \"hebeUpload/bbdchy/sgxk/e3fff9c950504b9481ce46e7e0ef57b9/9fe02a9a87a546bab6e508f375452ef3/b0bf431382635d76f63bc74cc35a9137.jpg\", \n" +
                        " \n" +
                        "\"chcgRowid\": \"9fe02a9a87a546bab6e508f375452ef3\", \n" +
                        " \n" +
                        "\"cgflname\": \"拿地即办证\" \n" +
                        " \n" +
                        "},\n" +
                        "{\n" +
                        "\"attachname\": \"mysqldump.png\", \n" +
                        " \n" +
                        "\"filesize\": \"5.23 KB\", \n" +
                        " \n" +
                        "\"attachpath\": \"hebeUpload/bbdchy/sgxk/e3fff9c950504b9481ce46e7e0ef57b9/9fe02a9a87a546bab6e508f375452ef3/792e628e91d01cb024587124298fc9a7.png\", \n" +
                        " \n" +
                        "\"chcgRowid\": \"9fe02a9a87a546bab6e508f375452ef3\", \n" +
                        " \n" +
                        "\"cgflname\": \"测算合一\" \n" +
                        " \n" +
                        "}\n" +
                        "] \n" +
                        " \n" +
                        "}\n" +
                        "] \n" +
                        " \n" +
                        "}";
        }else if (StringUtils.equals(beanid, "mk_facevalidateWithidCard")) {
            // 房产 根据产权证号 和 证件号 查询备案信息
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"address\":\"江苏省东海县房山镇大穆村10－35号\",\n" +
                    "        \"birth\":\"1999年04月16日\",\n" +
                    "        \"cameraPhotoBase64\":\"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB\\nAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEB\\nAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQ6WV2GBtvtsWD2eLv2fXlK4E4m6wmxi4K/eidjHm5e3BL2n4oO69\\nJHHHW1Om9QyKwBlvWnIKYenGRJGI6fSVRpOmWRfmLSSQzyPvwzEoGae0HxdXeOBf3x2+tVfUMkQd\\n7\",\n" +
                    "        \"police\":\"东海县公安局\",\n" +
                    "        \"result\":\"1\",\n" +
                    "        \"score\":\"93.90121459960938\",\n" +
                    "        \"serviceId\":\"12321313-017\",\n" +
                    "        \"sex\":\"男\",\n" +
                    "        \"validPeriod\":\"2017.04.21-2027.04.21\",\n" +
                    "        \"zfmPhoto\":\"qOKkKj1b/vo02JQE4yPYGhwmCDz+tLqUhkJITkNnJ7VIdx6AD681HESBtwBTzt75Y+lDGQRtsdlIUnP3qmP+22fYUkiFhnO3HvUALxghRtA5y54Ip7l2uTkZXBwi+lV3t8cqxVen+0foeop6yksCo3k9HPAH+fahZwckfMw4Zuwplq62IgXTajFkz/AAj5i3vmpFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAH//2Q==\"\n" +
                    "    },\n" +
                    "    \"message\":\"SUCCESS\",\n" +
                    "    \"success\":true\n" +
                    "}";
        }
        if ("mk_evaluate".equals(beanid)) {
            response = "{\n" +
                    "    \"code\": 1,\n" +
                    "    \"message\": \"SUCCESS\",\n" +
                    "    \"data\": null\n" +
                    "}";
        }
        if ("sw_getZflj".equals(beanid)){
            response = "{\n" +
                    "    \"result\": {\n" +
                    "        \"head\": {\n" +
                    "            \"channel_id\": \"AH.TAX.BDCJY.HS\",\n" +
                    "            \"expand\": [\n" +
                    "                {\n" +
                    "                    \"name\": \"expandName\",\n" +
                    "                    \"value\": \"1340HSBDCJY\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\": \"expandPwd\",\n" +
                    "                    \"value\": \"1340HSBDCJY\"\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"tran_date\": \"20200420\",\n" +
                    "            \"tran_id\": \"TAX.INTEGRATE.HX.BILLPAY\",\n" +
                    "            \"tran_seq\": \"c8we340wer6i4573abr9323850e55420\"\n" +
                    "        },\n" +
                    "        \"body\": {\n" +
                    "            \"merId\": \"898340193111201\",\n" +
                    "            \"orderId\": \"697b9f787651407984056cb000a2462d\",\n" +
                    "            \"payUrl\": \"111\",\n" +
                    "            \"payUrlQrcode\": \"222\",\n" +
                    "            \"jkm\": \"\",\n" +
                    "            \"zfzt\": \"PROCESSING\",\n" +
                    "            \"billDetailInfo\": [\n" +
                    "                {\n" +
                    "                    \"code\": \"S0_9800_0003\",\n" +
                    "                    \"billQueryId\": null,\n" +
                    "                    \"title\": \"账单缴费 - 财政非税 (条码)\",\n" +
                    "                    \"action\": \"prepay\",\n" +
                    "                    \"billStatus\": null,\n" +
                    "                    \"index\": \"03\",\n" +
                    "                    \"form\": {\n" +
                    "                        \"usr_num\": \"11000021000149719015\",\n" +
                    "                        \"col_organ_cd\": null,\n" +
                    "                        \"proc_flg\": null,\n" +
                    "                        \"col_voucher_no\": null,\n" +
                    "                        \"amount\": \"0.00\",\n" +
                    "                        \"rel_inq_key\": null,\n" +
                    "                        \"col_stl_bank_cd\": \" \",\n" +
                    "                        \"tax_payer_id\": null,\n" +
                    "                        \"pay_trans_num\": \" \",\n" +
                    "                        \"entrust_date\": \" \",\n" +
                    "                        \"deduct_date\": \" \",\n" +
                    "                        \"tax_drawee_nm\": \" \",\n" +
                    "                        \"tax_tick_num\": \" \",\n" +
                    "                        \"col_organ_tp\": \" \",\n" +
                    "                        \"col_bank_id\": \" \",\n" +
                    "                        \"owe_tag\": \"C\"\n" +
                    "                    }\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"code\": \"S0_9800_0000\",\n" +
                    "                    \"billQueryId\": null,\n" +
                    "                    \"title\": \"账单缴费 - 全国财税\",\n" +
                    "                    \"action\": \"prepay\",\n" +
                    "                    \"billStatus\": null,\n" +
                    "                    \"index\": \"01\",\n" +
                    "                    \"form\": {\n" +
                    "                        \"usr_num\": \"342201197605010025\",\n" +
                    "                        \"col_organ_cd\": \"13413020000\",\n" +
                    "                        \"proc_flg\": \"03\",\n" +
                    "                        \"col_voucher_no\": null,\n" +
                    "                        \"amount\": \"0.00\",\n" +
                    "                        \"rel_inq_key\": null,\n" +
                    "                        \"col_stl_bank_cd\": \" \",\n" +
                    "                        \"tax_payer_id\": null,\n" +
                    "                        \"pay_trans_num\": \" \",\n" +
                    "                        \"entrust_date\": \" \",\n" +
                    "                        \"deduct_date\": \" \",\n" +
                    "                        \"tax_drawee_nm\": \" \",\n" +
                    "                        \"tax_tick_num\": \" \",\n" +
                    "                        \"col_organ_tp\": \" \",\n" +
                    "                        \"col_bank_id\": \" \",\n" +
                    "                        \"owe_tag\": \"C\"\n" +
                    "                    }\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"code\": \"S0_9800_0000\",\n" +
                    "                    \"billQueryId\": null,\n" +
                    "                    \"title\": \"账单缴费 - 全国财税\",\n" +
                    "                    \"action\": \"prepay\",\n" +
                    "                    \"billStatus\": null,\n" +
                    "                    \"index\": \"02\",\n" +
                    "                    \"form\": {\n" +
                    "                        \"usr_num\": \"342201197711180036\",\n" +
                    "                        \"col_organ_cd\": \"13413020000\",\n" +
                    "                        \"proc_flg\": \"03\",\n" +
                    "                        \"col_voucher_no\": null,\n" +
                    "                        \"amount\": \"0.00\",\n" +
                    "                        \"rel_inq_key\": null,\n" +
                    "                        \"col_stl_bank_cd\": \" \",\n" +
                    "                        \"tax_payer_id\": null,\n" +
                    "                        \"pay_trans_num\": \" \",\n" +
                    "                        \"entrust_date\": \" \",\n" +
                    "                        \"deduct_date\": \" \",\n" +
                    "                        \"tax_drawee_nm\": \" \",\n" +
                    "                        \"tax_tick_num\": \" \",\n" +
                    "                        \"col_organ_tp\": \" \",\n" +
                    "                        \"col_bank_id\": \" \",\n" +
                    "                        \"owe_tag\": \"C\"\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        }
        if ("sw_getZfzt".equals(beanid)){
            response="{\n" +
                    "    \"result\": {\n" +
                    "        \"head\": {\n" +
                    "            \"channel_id\": \"AH.TAX.BDCJY.HS\",\n" +
                    "            \"expand\": [\n" +
                    "                {\n" +
                    "                    \"name\": \"expandName\",\n" +
                    "                    \"value\": \"1340HSBDCJY\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\": \"expandPwd\",\n" +
                    "                    \"value\": \"1340HSBDCJY\"\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"tran_date\": \"20200420\",\n" +
                    "            \"tran_id\": \"TAX.INTEGRATE.HX.TRADE\",\n" +
                    "            \"tran_seq\": \"c8we340wer6i4573abr9323850e55420\"\n" +
                    "        },\n" +
                    "        \"body\": {\n" +
                    "            \"zfzt\": \"SUCCESS\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        }
        if ("gxzzzt_dk".equals(beanid)) {
            response = "{\n" +
                    "    \"result\":{\n" +
                    "        \"head\":{\n" +
                    "            \"channel_id\":\"AH.TAX.BDCJY.HS\",\n" +
                    "            \"expand\":[\n" +
                    "                {\n" +
                    "                    \"name\":\"expandName\",\n" +
                    "                    \"value\":\"1340HSBDCJY\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\":\"expandPwd\",\n" +
                    "                    \"value\":\"1340HSBDCJY\"\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"rtn_code\":\"200\",\n" +
                    "            \"rtn_msg\":\"制证标记更新成功\",\n" +
                    "            \"tran_date\":\"20200420\",\n" +
                    "            \"tran_id\":\"TAX.COMMON.TS.SFZZ\",\n" +
                    "            \"tran_seq\":\"c8we340wer6i4573abr9323850e55420\"\n" +
                    "        },\n" +
                    "        \"body\":{\n" +
                    "            \"fwuuid\":\"697b9f787651407984056cb000a2462d\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        }
        if ("queryWxjjxx".equals(beanid)){
            response= "{\n" +
                    "        \"HourseCount\": 1,\n" +
                    "        \"PaymentCount\": 1,\n" +
                    "        \"Data\": [\n" +
                    "                {\n" +
                    "                        \"Status\": \"Y\",\n" +
                    "          \"Date\": \"2022-11-02 21:10:21\",\n" +
                    "          \"Money\": 12345.6,\n" +
                    "          \"Operator\": \"李四\"\n" +
                    "                }\n" +
                    "        ]\n" +
                    "}";

        }
        return response;
    }
}
