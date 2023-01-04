package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
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
@Service(value = "httpPostWithUrlParam")
public class HttpPostRequestWithUrlParamServiceImpl extends InterfaceRequestService<HttpReqPropBO> {


    @Autowired
    private HttpClientService httpClientService;


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
//            HttpGet httpGet = null;
            HttpPost httpPost = null;
            Map<String, Object> requestParamMap = new HashMap<>();
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
                    httpPost = new HttpPost(uriBuilder.build());
                } catch (URISyntaxException e) {
                    LOGGER.debug("httpGet 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                    httpPost = new HttpPost(requestUrl + "?" + CommonUtil.mapToUrlParam(requestParamMap));
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
                    urlParam = CommonUtil.mapToUrlParam(requestParamMap);
                }
                if (prop.isGetUrlEncode()) {
                    try {
                        urlParam = URLEncoder.encode(urlParam, CharEncoding.UTF_8);
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("postUrl方式url编码或参数强转异常", e);
                        e.printStackTrace();
                    }
                }
                LOGGER.info("postUrl方式请求入参：{}", requestUrl + "?" + urlParam);
                httpPost = new HttpPost(requestUrl + "?" + urlParam);
            }
            if (StringUtils.isNotBlank(prop.getContentType())) {
                httpPost.setHeader("Content-Type", prop.getContentType());
            } else {
                httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            }
            if (StringUtils.isNotBlank(prop.getxPlatToken())) {
                httpPost.setHeader("X-PLAT-TOKEN", prop.getxPlatToken());
            }
            httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            httpPost.setHeader("unitCode", "LandResourcesBureau");
            if (StringUtils.isNotBlank(prop.getAppKey())) {
                httpPost.setHeader("appKey", prop.getAppKey());
            }
            String response = "";
            Exception logE = null;
            try {
                response = httpClientService.doPost(httpPost, "UTF-8");
//                response=testResponse(builder.getExchangeBean().getId());
            } catch (IOException e) {
                logE = e;
                LOGGER.error("httpPost 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                throw new AppException("httpPost 请求异常");
            } finally {
                // 记录 请求日志
                AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                auditEventBO.setRequest(JSONObject.toJSONString(requestParamMap));
                auditEventBO.setResponse(response);
                auditEventBO.setException(logE);
                super.saveAuditLog(auditEventBO);
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

    private String testResponse(String beanid) {
        String response = "";
        // hfFcjyClfHtxx
        if (StringUtils.equals(beanid, "hfFcjyClfHtxx")) {
            response = "{\"code\":\"00000\",\"data\":{\"SuperMap\":{\"HT\":{\"FW\":{\"FCBM\":\"瑶120015426\",\"HTBH\":\"1447573794612\",\"XZQH\":\"瑶海区\",\"FWDZ\":\"合肥市瑶海区芜湖路\",\"ZCS\":31.0,\"SZC\":18.0,\"JZMJ\":93.48,\"JZJG\":\"钢筋混凝土结构\",\"FWYT\":\"住宅\",\"QSZYDX\":null,\"QSZYYT\":null,\"QSZYFS\":null,\"SYQQDFS\":null,\"QYRQ\":\"2015-11-15\",\"HTJE\":750000.0,\"FWDH\":\"1\",\"FJH\":\"1802\",\"TNMJ\":null,\"DJ\":null,\"ZJGSID\":null,\"FWNM\":null,\"HTNM\":null,\"XQMC\":\"御景湾\",\"BARQ\":\"2015-11-15\",\"QZTZRQ\":null,\"ZJGSDM\":\"340100000545777\",\"ZJGSMC\":\"安徽省义和房地产营销策划有限公司\"},\"JYSF\":[{\"XM\":\"侯泽华\",\"ZJLX\":\"身份证\",\"ZJHM\":\"41302719700120404X\",\"DH\":\"13956949776\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":0},{\"XM\":\"贺学武\",\"ZJLX\":\"身份证\",\"ZJHM\":\"342101197007150217\",\"DH\":\"13705586373\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1}]}}},\"msg\":\"操作成功\"}";
        } else if (StringUtils.equals(beanid, "hfFcjySpfHtxx")) {
            // 房产 根据产权证号 和 证件号 查询备案信息
            response = "{\n \"code\": \"00000\",\n \"data\": [\n   {\n  \"SuperMap\": {\n \"HT\": {\n\"FW\": {\n  \"FCBM\": \"瑶120015426\",\n  \"HTBH\": \"1447573794612\",\n  \"XZQH\": \"瑶海区\",\n  \"FWDZ\": \"合肥市瑶海区芜湖路\",\n  \"ZCS\": 31.0,\n  \"SZC\": 18.0,\n  \"JZMJ\": 93.48,\n  \"JZJG\": \"钢筋混凝土结构\",\n  \"FWYT\": \"住宅\",\n  \"QSZYDX\": null,\n  \"QSZYYT\": null,\n  \"QSZYFS\": null,\n  \"SYQQDFS\": null,\n  \"QYRQ\": \"2015-11-15\",\n  \"HTJE\": 750000.0,\n  \"FWDH\": \"1\",\n  \"FJH\": \"1802\",\n  \"TNMJ\": null,\n  \"DJ\": null,\n  \"ZJGSID\": null,\n  \"FWNM\": null,\n  \"HTNM\": null,\n  \"XQMC\": \"御景湾\",\n  \"BARQ\": \"2015-11-15\",\n  \"QZTZRQ\": null,\n  \"ZJGSDM\": \"340100000545777\",\n  \"ZJGSMC\": \"安徽省义和房地产营销策划有限公司\"\n         },\n\"JYSF\": [\n           {\n    \"XM\": \"侯泽华\",\n    \"ZJLX\": \"身份证\",\n    \"ZJHM\": \"41302719700120404X\",\n    \"DH\": \"13956949776\",\n    \"DJ\": null,\n    \"GJ\": null,\n    \"SZFE\": null,\n    \"BJ\": 0\n           },\n           {\n    \"XM\": \"贺学武\",\n    \"ZJLX\": \"身份证\",\n    \"ZJHM\": \"342101197007150217\",\n    \"DH\": \"13705586373\",\n    \"DJ\": null,\n    \"GJ\": null,\n    \"SZFE\": null,\n    \"BJ\": 1\n           }\n         ]\n       }\n     }\n   }\n ],\n \"msg\": \"操作成功\"\n" + "}";
        } else if (StringUtils.equals(beanid, "hfFcjySpfHtxx")) {
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
            response = "{\n \"result\": {\n   \"head\": {\n     \"tran_id\": \"TAX.COMMON.HX.CANCLES\",\n     \"channel_id\": \"AH.TAX.BDCJY.XX\",\n     \"tran_seq\": \"9A5A1B5B44n4544fr45Fbv51654B358\",\n     \"tran_date\": \"20191226\",\n     \"rtn_code\": \"200\",\n     \"rtn_msg\": \"该办理件取消作废成功\"\n   },\n   \"body\": {\n     \"jyzt_dm\": \"900\",\n     \"jyzt_mc\": \"审核不通过，已退回\",\n     \"shyj\": \"\"\n   }\n }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "spfwszt_dk")) {
            response = "{\n \"result\": {\n   \"head\": {\n     \"tran_id\": \"TAX.COMMON.CX.GT3PAYMENT\",\n     \"channel_id\": \"AH.TAX.BDCJY.HS\",\n     \"tran_seq\": \"7bedfefeee764b33b3bd55fca8f51653\",\n     \"tran_date\": \"20200420\",\n     \"rtn_code\": \"200\",\n     \"rtn_msg\": \"已完税\",\n     \"expand\": [\n       {\n         \"name\": \"expandName\",\n         \"value\": \"1340HSBDCJY\"\n       },\n       {\n         \"name\": \"expandPwd\",\n         \"value\": \"1340HSBDCJY\"\n       }\n     ]\n   },\n   \"body\": {\n     \"csfskxxGrid\": {\n       \"nsrsbh\": \"340521197809246857\",\n       \"nsrmc\": \"周宏明\",\n       \"fwdz\": \"翠竹小区36栋505室\",\n       \"fybh\": \"F34052120180002841\",\n       \"djxh\": \"20123400100013433088\",\n       \"csfskxxGridlb\": [\n         {\n           \"se\": 0,\n           \"zspm\": \"产权转移书据\",\n           \"zsxm\": \"印花税\",\n           \"zsxm_dm\": \"10111\"\n         },\n         {\n           \"se\": 2500,\n           \"zspm\": \"存量房（商品住房买卖）\",\n           \"zsxm\": \"契税\",\n           \"zsxm_dm\": \"10119\"\n         }\n       ]\n     },\n     \"zrfskxxGrid\": {\n       \"nsrsbh\": \"340521197111070511\",\n       \"nsrmc\": \"方同华\",\n       \"fwdz\": \"翠竹小区36栋505室\",\n       \"fybh\": \"F34052120180002841\",\n       \"djxh\": \"20123400100008445304\",\n       \"zrfskxxGridlb\": [\n         {\n           \"se\": 0,\n           \"zspm\": \"建筑物（11%、10%、9%、5%）-二手房\",\n           \"zsxm\": \"增值税\",\n           \"zsxm_dm\": \"10101\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"产权转移书据\",\n           \"zsxm\": \"印花税\",\n           \"zsxm_dm\": \"10111\"\n         },\n         {\n           \"se\": 2500,\n           \"zspm\": \"个人房屋转让所得\",\n           \"zsxm\": \"个人所得税\",\n           \"zsxm_dm\": \"10106\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"县城、镇（增值税附征）\",\n           \"zsxm\": \"城市维护建设税\",\n           \"zsxm_dm\": \"10109\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"增值税教育费附加\",\n           \"zsxm\": \"教育费附加\",\n           \"zsxm_dm\": \"30203\"\n         },\n         {\n           \"se\": 0,\n           \"zspm\": \"增值税地方教育附加\",\n           \"zsxm\": \"地方教育附加\",\n           \"zsxm_dm\": \"30216\"\n         }\n       ]\n     },\n     \"wsbj\": \"Y\",\n     \"fwwzdz\": \"合肥市蜀山区蜀山庭院1栋2301\"\n   }\n }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "swCxxx_dk")) {
          /*  response = "{\n \"result\": {\n   \"head\": {\n     \"tran_id\": \"TAX.COMMON.CX.TAXDETAIL\",\n     \"channel_id\": \"AH.TAX.BDCJY.GY\",\n     \"tran_seq\": \"80780f4bb2974140ad29f7e44fee1855\",\n     \"tran_date\": \"20191226\",\n     \"rtn_code\": \"200\",\n     \"rtn_msg\": \"查询成功\",\n     \"expand\": [\n       {\n         \"name\": \"expandName\",\n         \"value\": \"1340GYBDCJY\"\n       },\n       {\n         \"name\": \"expandPwd\",\n         \"value\": \"1340GYBDCJY\"\n       }\n     ]\n   },\n   \"body\": {\n     \"skmxcxlb\": [\n       {\n         \"nsrsbh\": \"340223198710292520\",\n         \"jyskblb\": [\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b22c4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10111\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"335\",\n             \"jsje\": \"1340000\",\n             \"sl\": 5.0E-4,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"印花税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b2ac4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10111\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"335\",\n             \"jsje\": \"1340000\",\n             \"sl\": 5.0E-4,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"印花税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b22c4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10119\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"40200\",\n             \"jsje\": \"1340000\",\n             \"sl\": 0.03,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"契税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"00377e05ea6748b2ac4e6bdaa1af9600\",\n             \"zsxm_dm\": \"10119\",\n             \"zrfcsfbz\": \"1\",\n             \"ynse\": \"40200\",\n             \"jsje\": \"1340000\",\n             \"sl\": 0.03,\n             \"jyfe\": 0.5,\n             \"zsxmmc\": \"契税\"\n           }\n         ],\n         \"nsrmc\": \"何樱红\",\n         \"dz\": \"合肥市\",\n         \"jyjg\": \"1\",\n         \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n         \"zrfcsfbz\": \"1\",\n         \"jzmj\": \"100\",\n         \"ynse\": \"81070.00\",\n         \"jsje\": \"2680000\"\n       },\n       {\n         \"nsrsbh\": \"441702197407131711\",\n         \"jyskblb\": [\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10106\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"2552380.95\",\n             \"sl\": 0.2,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"个人所得税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10109\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"0\",\n             \"sl\": 0.07,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"城市建设维护税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"30203\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"0\",\n             \"sl\": 0.03,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"教育费附加\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"30216\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"0\",\n             \"sl\": 0.02,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"地方教育附加\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10101\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"2552380.95\",\n             \"sl\": 0.05,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"增值税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10111\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"670\",\n             \"jsje\": \"2680000\",\n             \"sl\": 5.0E-4,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"印花税\"\n           },\n           {\n             \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n             \"nsr_id\": \"8c4c70fc6bb14a1f841da6e19dfa7219\",\n             \"zsxm_dm\": \"10113\",\n             \"zrfcsfbz\": \"0\",\n             \"ynse\": \"0\",\n             \"jsje\": \"2552380.95\",\n             \"sl\": 0.3,\n             \"jyfe\": 1.0,\n             \"zsxmmc\": \"土地增值税\"\n           }\n         ],\n         \"nsrmc\": \"冯秀祥\",\n         \"dz\": \"黄山市苹果路\",\n         \"jyjg\": \"1\",\n         \"fwuuid\": \"caa4bce16ab74abda6f57b5070d9241b\",\n         \"zrfcsfbz\": \"0\",\n         \"jzmj\": \"100\",\n         \"ynse\": \"670.00\",\n         \"jsje\": \"2680000\"\n       }\n     ],\n     \"jyzt_mc\": \"审核通过待申报\",\n     \"jyzt_dm\": \"300\",\n     \"shyj\": \"通过\",\n     \"wsbj\": \"N\"\n   }\n }\n" +
                    "}";*/
            response = "{\"result\":{\"head\":{\"tran_id\":\"TAX.COMMON.CX.TAXDETAIL\",\"channel_id\":\"AH.TAX.BDCJY.HS\",\"tran_seq\":\"233d79dfec4346838fe428fc74f92eeb\",\"tran_date\":\"20200420\",\"rtn_code\":\"200\",\"rtn_msg\":\"查询成功\",\"expand\":[{\"name\":\"expandName\",\"value\":\"1340HSBDCJY\"},{\"name\":\"expandPwd\",\"value\":\"1340HSBDCJY\"}]},\"body\":{\"skmxcxlb\":[{\"nsrsbh\":\"342701196608250625\",\"jyskblb\":[{\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"nsr_id\":\"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\"zsxm_dm\":\"10101\",\"zrfcsfbz\":\"0\",\"jsje\":\"550000\",\"sl\":0,\"jyfe\":1,\"zsxmmc\":\"增值税\",\"zspm_dm\":\"10101XXX\",\"zspmmc\":\"XXX\"},{\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"nsr_id\":\"a0acf8d3d45c4ff5b63e9fc451b25e7b\",\"zsxm_dm\":\"10106\",\"zrfcsfbz\":\"0\",\"jsje\":\"550000\",\"sl\":0,\"jyfe\":1,\"zsxmmc\":\"个人所得税\",\"zspm_dm\":\"10101XXX\",\"zspmmc\":\"XXX\"}],\"nsrmc\":\"钱卫兵\",\"dz\":\"安徽省黄山市屯溪区长干东路１０８号１幢１０１室\",\"jyjg\":\"550000\",\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"sbtxhfj\":\"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\"zrfcsfbz\":\"0\",\"jzmj\":\"67.29\",\"ynse\":\"0.00\",\"jsje\":\"550000\"},{\"nsrsbh\":\"342723196906078913\",\"jyskblb\":[{\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"nsr_id\":\"6c0d472bde6049498749845cb7f5626f\",\"zsxm_dm\":\"10119\",\"zrfcsfbz\":\"1\",\"ynse\":\"5500.00\",\"jsje\":\"275000.00\",\"sl\":0.01,\"jyfe\":1,\"zsxmmc\":\"契税\",\"zspm_dm\":\"10119XXX\",\"zspmmc\":\"XXX\"}],\"nsrmc\":\"陆连众\",\"dz\":\"安徽省歙县绍濂乡庄头村 2 组\",\"jyjg\":\"550000\",\"fwuuid\":\"12a8ae0127f6419d8aeadac1eca23513\",\"sbtxhfj\":\"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\"zrfcsfbz\":\"1\",\"jzmj\":\"67.29\",\"ynse\":\"5500.00\",\"jsje\":\"550000\"}],\"jyzt_mc\":\"已缴款\",\"qyjyzsbz\":\"Y\",\"sbtxhfj\":\"http://59.203.19.50:9790/sbtxh/xxxx.jpg\",\"jyzt_dm\":\"500\",\"wsbj\":\"Y\",\"xgr\":\"胡小芳\",\"jsje\":\"550000\"}}}\n";
        }
        return response;
    }
}
