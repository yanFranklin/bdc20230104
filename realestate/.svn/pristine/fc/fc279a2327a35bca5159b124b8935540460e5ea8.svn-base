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
 * @author <a href="mailto:liyinqiao@gtmap.cn">huangjian</a>
 * @version 1.0  2022-03-01
 * @description HTTP 类型  GET方式 请求服务  舒城相关共享接口请求特殊处理
 */
@Service(value = "httpGetForShuChengGx")
public class HttpGetRequestForShuChengServiceImpl extends InterfaceRequestService<HttpReqPropBO> {


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
            HttpGet httpGet = null;
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
                    if (requestParamMap.containsKey("queryMap")) {
                        try {
                          /*  LOGGER.info("舒城接口入参1：{}", requestParamMap.get("queryMap"));
                            JSONObject param =JSONObject.parseObject(requestParamMap.get("queryMap").toString());
                            String map = JSONObject.toJSONString(param);
                            LOGGER.info("舒城接口入参2：{}", map);*/
                            requestParamMap.put("queryMap", URLEncoder.encode(requestParamMap.get("queryMap").toString(), CharEncoding.UTF_8));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("舒城共享接口url编码或参数强转异常", e);
                            e.printStackTrace();
                        }

                    }
                    urlParam = CommonUtil.mapToUrlParam(requestParamMap);
                    LOGGER.info("舒城接口入参：{}", urlParam);
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
                httpGet.setHeader("AppKey", prop.getAppKey());
            }
            String response = "";
            Exception logE = null;
            try {
                if (prop.isHttpsRequest()) {
                    try {
                        response = httpClientService.doHttpsGetHlzs(httpGet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    response = httpClientService.doGet(httpGet);
                }
//                response = testResponse(builder.getExchangeBean().getId());
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

    private static String testResponse(String beanid) {
        String response = "";
        // hfFcjyClfHtxx
        if (StringUtils.equals(beanid, "hfFcjyClfHtxx")) {
            response = "{\"code\":\"00000\",\"data\":{\"SuperMap\":{\"HT\":{\"FW\":{\"FCBM\":\"瑶120015426\",\"HTBH\":\"1447573794612\",\"XZQH\":\"瑶海区\",\"FWDZ\":\"合肥市瑶海区芜湖路\",\"ZCS\":31.0,\"SZC\":18.0,\"JZMJ\":93.48,\"JZJG\":\"钢筋混凝土结构\",\"FWYT\":\"住宅\",\"QSZYDX\":null,\"QSZYYT\":null,\"QSZYFS\":null,\"SYQQDFS\":null,\"QYRQ\":\"2015-11-15\",\"HTJE\":750000.0,\"FWDH\":\"1\",\"FJH\":\"1802\",\"TNMJ\":null,\"DJ\":null,\"ZJGSID\":null,\"FWNM\":null,\"HTNM\":null,\"XQMC\":\"御景湾\",\"BARQ\":\"2015-11-15\",\"QZTZRQ\":null,\"ZJGSDM\":\"340100000545777\",\"ZJGSMC\":\"安徽省义和房地产营销策划有限公司\"},\"JYSF\":[{\"XM\":\"侯泽华\",\"ZJLX\":\"身份证\",\"ZJHM\":\"41302719700120404X\",\"DH\":\"13956949776\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":0},{\"XM\":\"贺学武\",\"ZJLX\":\"身份证\",\"ZJHM\":\"342101197007150217\",\"DH\":\"13705586373\",\"DJ\":null,\"GJ\":null,\"SZFE\":null,\"BJ\":1}]}}},\"msg\":\"操作成功\"}";
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

        }else if ("bjjk_gzajgzjbxx".equals(beanid)) {
            //2.1 公证案件基本信息查询
            response = "{\n" +
                    "    \"code\":\"200\",\n" +
                    "    \"msg\":\"请求成功\",\n" +
                    "    \"data\":\"<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\"><soapenv:Body><ns:gzajjbxxggResponse xmlns:ns=\\\"http://hits.com\\\"><ns:return><?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><DATA><ZTM>0000</ZTM><gzcmc>安徽省金寨县公证处</gzcmc><gzy>贾世权</gzy><gzsbh>（2022）皖金公证字第253号</gzsbh><czsj>2022-05-30</czsj><gzsx>法定继承</gzsx><yt>继承</yt></DATA></ns:return></ns:gzajjbxxggResponse></soapenv:Body></soapenv:Envelope>\",\n" +
                    "    \"total\":0\n" +
                    "}";
        }else if ("bjjk_gzajgzsxx".equals(beanid)) {
            //2.2 公证案件书信息查询
            response = "{\n" +
                    "    \"code\":\"200\",\n" +
                    "    \"msg\":\"请求成功\",\n" +
                    "    \"data\":\"<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\"><soapenv:Body><ns:gzsxzggResponse xmlns:ns=\\\"http://hits.com\\\"><ns:return><?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><DATA><ZTM>0000</ZTM><file>JVBERi0xLjUNCjQgMCBvYmoNCjw8L1R5cGUgL1BhZ2UvUGFyZW50IDMgMCBSL0NvbnRlbnRzIDUg \\nMCBSL01lZGlhQm94IFswIDAgNTk1LjI5OTk4Nzc5IDg0MS45MDAwMjQ0MV0vUmVzb3VyY2VzPDwv \\nRm9udDw8L0ZBQUFBSCA3IDAgUi9GQUFBQkMgMTIgMCBSL0ZBQUFCRiAxNSAwIFI+Pj4+L0dyb3Vw \\nIDw8L1R5cGUvR3JvdXAvUy9UcmFuc3BhcmVuY3kvQ1MvRGV2aWNlUkdCPj4+Pg0KZW5kb2JqDQo1 \\nIDAgb2JqDQo8PC9MZW5ndGggMTkgMCBSL0ZpbHRlciAvRmxhdGVEZWNvZGU+PnN0cmVhbQ0KeJyl \\nldlv00AQxj9BabnUQqENlGs5mwLZ7uxlL3eTNpRyBgwEyBsIJKQ+8P+/MGunqd0kFRa2srG9O9/M \\n/mbGJqH4bBEPqSUZlFLaWhLfd8UfQfkkiaBEouOjdibWuxt8bAtKRfZzuILNKSHpbQjBBJ6SOiW+ \\ndkFkuzz/SzRxZC37XRi3O0Pjphg9GgMDAwMDAgbg0KMDAwMDAwNDA1NCAwMDAwMCBuDQow \\nMDAwMDA0MDc4IDAwMDAwIG4NCjAwMDAwMDQyNzAgMDAwMDAgbg0KMDAwMDAwNDQ3OSAwMDAwMCBu \\nDQowMDAwMDA3MjU4IDAwMDAwIG4NCjAwMDAwMDgxNDIgMDAwMDAgbg0KMDAwMDA1MzY5MiAwMDAw \\nMCBuDQowMDAwMDUzNjY2IDAwMDAwIG4NCjAwMDAwNTgyODUgMDAwMDAgbg0KMDAwMDA1ODI2MCAw \\nMDAwMCBuDQowMDAwMDc1MTM0IDAwMDAwIG4NCjAwMDAwNzUxMDggMDAwMDAgbg0KdHJhaWxlcg0K \\nPDwvU2l6ZSA0MC9JbmZvIDEgMCBSL1Jvb3QgMiAwIFI+Pg0Kc3RhcnR4cmVmDQo3NTE2MA0KJSVF \\nT0YNCg==</file></DATA></ns:return></ns:gzsxzggResponse></soapenv:Body></soapenv:Envelope>\",\n" +
                    "    \"total\":0\n" +
                    "}";
        }else if ("bjjk_gzajqtxx".equals(beanid)) {
            //2.3 公证案件其他信息查询
            response = "{\n" +
                    "    \"code\":\"200\",\n" +
                    "    \"msg\":\"请求成功\",\n" +
                    "    \"data\":\"<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\"><soapenv:Body><ns:zmdxwsxzggResponse xmlns:ns=\\\"http://hits.com\\\"><ns:return><?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><DATA><ZTM>9999</ZTM><ZTMS>该案件尚未拟定相关证明文书!</ZTMS></DATA></ns:return></ns:zmdxwsxzggResponse></soapenv:Body></soapenv:Envelope>\",\n" +
                    "    \"total\":0\n" +
                    "}";
        }else if ("bjjk_sycx".equals(beanid)) {
            //收养
            response = "{\n" +
                    "    \"code\":\"200\",\n" +
                    "    \"msg\":\"请求成功\",\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"SYRXM\":\"漆仲礼\",\n" +
                    "            \"SYRZZ\":null,\n" +
                    "            \"SYRSFZJH\":null,\n" +
                    "            \"SYRMZ\":\"汉\",\n" +
                    "            \"SYZZMC\":null,\n" +
                    "            \"SYRZZ1\":\"安徽省六安市金寨县斑竹园镇\",\n" +
                    "            \"SYRZY\":null,\n" +
                    "            \"SYRJKZK\":\"健康\",\n" +
                    "            \"SYRZY1\":null,\n" +
                    "            \"BSYRMZ\":\"汉\",\n" +
                    "            \"JBRXM\":\"滕光霞\",\n" +
                    "            \"BSYRGJ\":null,\n" +
                    "            \"SHFLJGMC\":\"六安市社会福利院\",\n" +
                    "            \"SYRGJ\":null,\n" +
                    "            \"SYRCSRQ\":null,\n" +
                    "            \"BSYRZY\":null,\n" +
                    "            \"BSYRCSRQ\":\"2014.02.01\",\n" +
                    "            \"BSYRWHCD\":null,\n" +
                    "            \"SYJGDYJ\":null,\n" +
                    "            \"BSYQDHJD\":null,\n" +
                    "            \"SYRWHCD\":null,\n" +
                    "            \"BSYRHYZK\":null,\n" +
                    "            \"SYRMZ1\":null,\n" +
                    "            \"BSYRXM\":\"陆仕途\",\n" +
                    "            \"BSYRJKZK\":null,\n" +
                    "            \"BSYRGMW\":\"漆猛\",\n" +
                    "            \"SYRHYZK\":\"已婚\",\n" +
                    "            \"SHFLJGYWZGJGYJ\":\"同意\",\n" +
                    "            \"BSYRGZDW\":null,\n" +
                    "            \"SYRJKZK1\":null,\n" +
                    "            \"SYRGZDW\":null,\n" +
                    "            \"SYRGJ1\":\"中国\",\n" +
                    "            \"BSYRSFZJH\":\"341502201402019450\",\n" +
                    "            \"SYRCSRQ1\":\"1970.10.11\",\n" +
                    "            \"SYRWHCD1\":null,\n" +
                    "            \"JBRZW\":\"办公室副主任\",\n" +
                    "            \"JTNSR\":null,\n" +
                    "            \"FDDBRXM\":\"汪波涛\",\n" +
                    "            \"ZNQK\":null,\n" +
                    "            \"SYRHYZK1\":null,\n" +
                    "            \"LXDH\":\"0564-3269703\",\n" +
                    "            \"SYRSFZJH1\":\"342426197010114317\",\n" +
                    "            \"SYRQK\":null,\n" +
                    "            \"SYRYBSYRGX\":\"监护\",\n" +
                    "            \"SYRGZDW1\":null,\n" +
                    "            \"SYRXM1\":null,\n" +
                    "            \"JBRSFZJH\":\"340222198610295023\",\n" +
                    "            \"DWDZ\":\"六舒路口向南1500米处\",\n" +
                    "            \"SYRDYJ\":\"同意\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"SYRXM\":\"漆仲礼\",\n" +
                    "            \"SYRZZ\":\"六安市金安区六舒路口向南500米\",\n" +
                    "            \"SYRSFZJH\":null,\n" +
                    "            \"SYRMZ\":\"汉\",\n" +
                    "            \"SYZZMC\":\"六安市社会福利院\",\n" +
                    "            \"SYRZZ1\":\"金寨县斑竹园镇漆店村前湾组\",\n" +
                    "            \"SYRZY\":\"务农\",\n" +
                    "            \"SYRJKZK\":\"健康\",\n" +
                    "            \"SYRZY1\":null,\n" +
                    "            \"BSYRMZ\":\"汉\",\n" +
                    "            \"JBRXM\":\"腾光霞\",\n" +
                    "            \"BSYRGJ\":\"中国\",\n" +
                    "            \"SHFLJGMC\":\"六安市社会福利院\",\n" +
                    "            \"SYRGJ\":\"中国\",\n" +
                    "            \"SYRCSRQ\":null,\n" +
                    "            \"BSYRZY\":\"无\",\n" +
                    "            \"BSYRCSRQ\":\"2014.2.1\",\n" +
                    "            \"BSYRWHCD\":\"无\",\n" +
                    "            \"SYJGDYJ\":\"同意\",\n" +
                    "            \"BSYQDHJD\":\"六安市社会福利院\",\n" +
                    "            \"SYRWHCD\":null,\n" +
                    "            \"BSYRHYZK\":\"无\",\n" +
                    "            \"SYRMZ1\":null,\n" +
                    "            \"BSYRXM\":\"陆仕途\",\n" +
                    "            \"BSYRJKZK\":\"健康\",\n" +
                    "            \"BSYRGMW\":\"漆猛\",\n" +
                    "            \"SYRHYZK\":\"已婚\",\n" +
                    "            \"SHFLJGYWZGJGYJ\":\"同意\",\n" +
                    "            \"BSYRGZDW\":\"无\",\n" +
                    "            \"SYRJKZK1\":null,\n" +
                    "            \"SYRGZDW\":\"无\",\n" +
                    "            \"SYRGJ1\":\"中国\",\n" +
                    "            \"BSYRSFZJH\":\"341502201402019450\",\n" +
                    "            \"SYRCSRQ1\":\"1970.10.11\",\n" +
                    "            \"SYRWHCD1\":\"初中\",\n" +
                    "            \"JBRZW\":\"寄养部副部长\",\n" +
                    "            \"JTNSR\":\"7万元\",\n" +
                    "            \"FDDBRXM\":\"汪波涛\",\n" +
                    "            \"ZNQK\":\"1个\",\n" +
                    "            \"SYRHYZK1\":null,\n" +
                    "            \"LXDH\":\"0564-3269703\",\n" +
                    "            \"SYRSFZJH1\":\"342426197010114317\",\n" +
                    "            \"SYRQK\":\"福利院\",\n" +
                    "            \"SYRYBSYRGX\":\"收养关系\",\n" +
                    "            \"SYRGZDW1\":\"六安市社会福利院\",\n" +
                    "            \"SYRXM1\":\"六安市社会福利院\",\n" +
                    "            \"JBRSFZJH\":\"340222198610295023\",\n" +
                    "            \"DWDZ\":\"六安市金安区六舒路口向南500米\",\n" +
                    "            \"SYRDYJ\":\"同意\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"total\":0\n" +
                    "}";
        }else if ("bjjk_swzmcx".equals(beanid)) {
            //2.2 公证案件书信息查询
            response = "{\n" +
                    "    \"code\":\"200\",\n" +
                    "    \"msg\":\"请求成功\",\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"XM\":\"赵立泳\",\n" +
                    "            \"SFZH\":\"342423196811180775\",\n" +
                    "            \"SWSJ\":\"2005-09-06\",\n" +
                    "            \"SJLY\":\"1\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"total\":0\n" +
                    "}";
        }

        return response;
    }
}
