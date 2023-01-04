package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.impl.hefei.ReplaceQighUrlServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.NtsqReplaceUrlServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.YanchengReplaceUrlServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-22
 * @description HTTP POST 类型请求
 */
@Service(value = "httpPost")
public class HttpPostRequestServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private YanchengReplaceUrlServiceImpl yanchengReplaceUrlService;
    @Autowired
    private ReplaceQighUrlServiceImpl replaceQighUrlService;
    @Autowired
    private NtsqReplaceUrlServiceImpl ntsqReplaceUrlService;

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
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
        if (StringUtils.isNotBlank(prop.getUrl())) {
            String requestUrl = prop.getUrl();
            //替换请求地址：盐城删除抵押业务时会回调不同的银行系统
            if (StringUtils.isNotBlank(prop.getReplaceUrl())
                    && StringUtils.isNotBlank(prop.getReplaceUrlHandlerType())
                    && prop.getReplaceUrl().equals("hfrqReplaceUrl")
            ) {
                String newUrl = replaceQighUrlService.getNewUrl(requestBody, prop.getReplaceUrlHandlerType());
                //如果能取到新的url则替换
                if (StringUtils.isNotBlank(newUrl)){
                    requestUrl = newUrl;
                }
            } else if(StringUtils.isNotBlank(prop.getReplaceUrl())
                    && StringUtils.isNotBlank(prop.getReplaceUrlHandlerType())){
                String newUrl = yanchengReplaceUrlService.getNewUrl(requestBody, prop.getReplaceUrlHandlerType());
                if (StringUtils.isNotBlank(newUrl)){
                    requestUrl = newUrl;
                }else {
                    return;
                }
            }
            // 南通水气获取地址
            if (StringUtils.isNotBlank(prop.getReplaceUrl())
                    && prop.getReplaceUrl().equals("ntsqReplaceUrl")
            ) {
                String newUrl = ntsqReplaceUrlService.getNewUrl(requestBody, prop.getReplaceUrl());
                //如果能取到新的url则替换
                if (StringUtils.isNotBlank(newUrl)){
                    requestUrl = newUrl;
                }
            }
            Map<String, Object> requestParamMap = new HashMap<>();
            if (!(requestBody instanceof List)) {
                requestParamMap = CommonUtil.objectToMap(requestBody);
//                LOGGER.info("requestParamMap为：" + StringUtils.left(JSONObject.toJSONString(requestParamMap),1000));
            }
            HttpPost httpPost = new HttpPost(requestUrl);
            if (StringUtils.isNotBlank(prop.getContentType())) {
                httpPost.setHeader("Content-Type", prop.getContentType());
            }
            httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            httpPost.setHeader("unitCode", "LandResourcesBureau");
            if (StringUtils.isNotBlank(prop.getAppKey())) {
                httpPost.setHeader("appKey", prop.getAppKey());
            }
            if (StringUtils.isNotBlank(prop.getxPlatToken())) {
                httpPost.setHeader("X-PLAT-TOKEN", prop.getxPlatToken());
            }
            if(requestParamMap.containsKey("Authorization")){
                httpPost.setHeader("Authorization", (String) requestParamMap.get("Authorization"));
            }
            if(requestParamMap.containsKey("qrctoken")){
                httpPost.setHeader("qrctoken", (String) requestParamMap.get("qrctoken"));
            }
            List<NameValuePair> parameters = Lists.newArrayList();
            if (requestParamMap != null && CollectionUtils.isNotEmpty(requestParamMap.keySet())) {
                for (String key : requestParamMap.keySet()) {
                    Object value = requestParamMap.get(key);
                    if (!(value instanceof String)) {
                        value = JSONObject.toJSONString(value);
                    }
                    parameters.add(new BasicNameValuePair(key, (String) value));
                }
            }
            for(NameValuePair pair: parameters){
                LOGGER.info("pair.name:" + pair.getName());
                LOGGER.info("pair.value:" + StringUtils.left(pair.getValue(), 1000));
            }
            if ("application/json".equals(prop.getContentType())) {
                StringEntity entity = new StringEntity(JSONObject.toJSONString(requestParamMap), Charset.forName("UTF-8"));
                entity.setContentType("application/json; charset=utf-8");
                httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
                httpPost.setEntity(entity);

            } else if ("application/text".equals(prop.getContentType())) {
                String param = JSONObject.toJSONString(requestBody);
                httpPost.setEntity(new StringEntity(param, Charsets.UTF_8));
            } else if ("multipart/form-data".equals(prop.getContentType())) {
                MultipartEntityBuilder formBuilder = MultipartEntityBuilder.create();
                for (String key : requestParamMap.keySet()) {
                    Object value = requestParamMap.get(key);
                    if (!(value instanceof String)) {
                        value = JSONObject.toJSONString(value);
                    }
                    formBuilder.addTextBody(key, (String) value);
                }
                HttpEntity entity = formBuilder.build();
                httpPost.setEntity(entity);
            }else {
                httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
            }
            if (StringUtils.isNotBlank(prop.getSoTimeout()) && NumberUtils.isNumber(prop.getSoTimeout())) {
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(NumberUtils.toInt(prop.getSoTimeout())).build();
                LOGGER.error("httpPost 请求url：{}，参数配置:{}", requestUrl, requestConfig.toString());
                httpPost.setConfig(requestConfig);
                LOGGER.info("http请求配置：{}", JSONObject.toJSONString(requestConfig));
            }
            String response = "";
            Exception logE = null;

            try {
                if (prop.isHttpsRequest()) {
                    LOGGER.info("开始执行忽略证书http请求，方法为：{}",httpPost.getMethod());
                    response = httpClientService.doHttpsPostHlzs(httpPost, "UTF-8");
                } else {
                    LOGGER.info("开始执行http请求，方法为：{}",httpPost.getMethod());
                    response = httpClientService.doPost(httpPost, "UTF-8");
                }
            } catch (IOException e) {
                logE = e;
                LOGGER.error("httpPost 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                throw new AppException("httpPost 请求异常");
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
            //response = testResponse(requestParamMap,builder);
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestParamMap, builder
     * @return java.lang.String
     * @description 测试数据
     */
    private static String testResponse(Map<String,Object> requestParamMap, InterfaceRequestBuilder builder){
        String beanId = builder.getExchangeBean().getId();
        // 省级平台测试数据
        String response = "{\n" +
                "  \"head\": {\n" +
                "    \"msg\": \"安全token错误\",\n" +
                "    \"code\": \"2002\"\n" +
                "  },\n" +
                "  \"data\": {}\n" +
                "}\n" +
                " ";
        if (requestParamMap.get("gxData") != null) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(requestParamMap.get("gxData")));
            JSONObject head = jsonObject.getJSONObject("head");
            if (head != null && StringUtils.equals("45678901", head.getString("token"))) {
                response = "{\n" +
                        "   \"data\": {\"cxjg\": [\n" +
                        "            {\n" +
                        "          \t\t\"xm\": \"张三\",\n" +
                        "         \t\t\"yhzgx\": \"母亲\",\n" +
                        "         \t\t\"zjhm\": \"320723197507121111\"\n" +
                        "      \t\t},\n" +
                        "            {\n" +
                        "         \t\t\"xm\": \"李四\",\n" +
                        "         \t\t\"yhzgx\": \"妻子\",\n" +
                        "         \t\t\"zjhm\": \"320723199003192222\"\n" +
                        "      \t\t},\n" +
                        "            {\n" +
                        "         \t\t\"xm\": \"王五\",\n" +
                        "         \t\t\"yhzgx\": \"女儿\",\n" +
                        "         \t\t\"zjhm\": \"320723199512263333\"\n" +
                        "      \t\t}\n" +
                        "   \t]},\n" +
                        "   \"head\":{\n" +
                        "      \"code\": \"0000\",\n" +
                        "      \"msg\": \"查询成功\"\n" +
                        "   }\n" +
                        "}";
            }
        }
        if (beanId.equals("sjpt_token")) {
            response = "{\n" +
                    "\"head\": {\n" +
                    "\"code\": \"0000\",\n" +
                    "\"msg\": \"success\"\n" +
                    "},\n" +
                    "\"data\":{\n" +
                    "\"token\": \"45678901\"\n" +
                    "}\n" +
                    "}";
        }
        if ("jfpt_tsdjfxx".equals(beanId)) {
            response = "{\"data\":{\"mpayCode\":\"34000019090000033396\",\"prePayRecordList\":[{\"adminDiv\":\"黄山市\",\"billAmount\":\"0.01\",\"billNo\":\"201908121712010000000235\",\"createTime\":\"20190806190048\",\"exempt\":\"0.00\",\"itemCode\":\"3401031811270000055\",\"itemName\":\"黄山缴费测试申请无线电频率招标、拍卖-刘贵02\",\"itemNum\":\"1\",\"money\":\"0.01\",\"overdueFine\":\"0.00\",\"payAreaCode\":\"0000000018\",\"payAreaName\":\"黄山市财政局\",\"payDeptCode\":\"06502\",\"payDeptName\":\"黄山市市政工程管理处\",\"payItemCode\":\"07613102\",\"payItemName\":\"城市道路占用费\",\"payor\":\"黄山缴费测试_刘贵\",\"payorIdNum\":\"340824199507016014\",\"pinCode\":\"34100019090000033418\",\"sourceId\":\"b11aad66443d43fda87fbdac51dda6ee\",\"stdFee\":\"0.01\",\"sysId\":\"hf0551\",\"sysSecId\":\"D812641AEDE35E51A150F2C2FDA60026\"},{\"adminDiv\":\"黄山市\",\"billAmount\":\"0.01\",\"billNo\":\"201908121712010000000368\",\"createTime\":\"20190806190048\",\"exempt\":\"0.00\",\"itemCode\":\"3401031811270000044\",\"itemName\":\"黄山缴费测试申请无线电频率招标、拍卖-刘贵03\",\"itemNum\":\"1\",\"money\":\"0.01\",\"overdueFine\":\"0.00\",\"payAreaCode\":\"0000000018\",\"payAreaName\":\"黄山市财政局\",\"payDeptCode\":\"06502\",\"payDeptName\":\"黄山市市政工程管理处\",\"payItemCode\":\"07613102\",\"payItemName\":\"城市道路占用费\",\"payor\":\"黄山缴费测试_刘贵\",\"payorIdNum\":\"340824199507016014\",\"pinCode\":\"34100019090000033405\",\"sourceId\":\"b11aad66443d43fda87fbdac51dda6dd\",\"stdFee\":\"0.01\",\"sysId\":\"hf0551\",\"sysSecId\":\"D812641AEDE35E51A150F2C2FDA60026\"}]},\"msg\":\"success\",\"status\":true}";
        }
        if ("jfpt_jfztcx".equals(beanId)) {
            response = "{\n" +
                    "    \"data\": {\n" +
                    "        \"paymentStatus\": \"01\",\n" +
                    "        \"transactionNo\": \"ZWJF1810170000000552\"\n" +
                    "    },\n" +
                    "    \"msg\": \"success\",\n" +
                    "    \"status\": true\n" +
                    "}";
        }
        if ("emsddjr".equals(beanId)) {
            response = "{\"response\":[{\"mailNo\":\"1103311486731\",\"success\":\"T\",\"txLogisticID\":\"BZL6207485221738977\"}],\"success\":\"T\"}";
        }
        if ("emsddjr".equals(beanId)) {
            response = "{\"response\":[{\"mailNo\":\"1103311486731\",\"success\":\"T\",\"txLogisticID\":\"BZL6207485221738977\"}],\"success\":\"T\"}";
        }
        if ("tsSwxxClf_dk".equals(beanId) || "tsSwxxSpf_dk".equals(beanId)) {
            response = "{\n" +
                    "\"result\": {\n" +
                    "          \"head\": {\n" +
                    "            \"channel_id\": \"AH.TAX.BDCJY.XX\",\n" +
                    "\"tran_id\": \"TAX.INTEGRATE.JY.SECONDHOUSEINFO\",\n" +
                    "\"tran_date\": \"20191227\",\n" +
                    "            \"tran_seq\": \"9A5A1B5B65994B4A97A9FDA52659B358\",\n" +
                    "            \"rtn_code\": \"200\",\n" +
                    "            \"rtn_msg\": \"存量房信息采集保存成功！\"\n" +
                    "          },\n" +
                    "\"body\": {\n" +
                    "\t           \"fwuuid\": \"d889bb7237594c3386fb28bcdd0fd245\",\n" +
                    "\"BDCDYH\": \"3416000151911220053768\",\n" +
                    "\t           \"htbh\": \"FWR40405333033\",\n" +
                    "\"sldh\": \"FWR40405333033\"\n" +
                    "}\n" +
                    "    }\n" +
                    "}";
        }
        if ("xgbmcx_sydw".equals(beanId) || "xgbmcx_sydw".equals(beanId)) {
            response = "{\n" +
                    "   \"data\": {\"cxjg\":    {\n" +
                    "      \"count\": 1,\n" +
                    "      \"datalist\": [      {\n" +
                    "         \"dz\": \"广东省韶关市武江区新华北路38号\",\n" +
                    "         \"fddbr\": \"蒋祖浩\",\n" +
                    "         \"jbdwmc\": \"广东省有色金属地质局\",\n" +
                    "         \"jfly\": \"其他\",\n" +
                    "         \"kbzj\": \"947万元\",\n" +
                    "         \"sydwmc\": \"广东省有色金属地质局九三二队\",\n" +
                    "         \"tyshxydm\": \"12440200455908904M\",\n" +
                    "         \"zsyxjzrq\": \"2021-04-17\",\n" +
                    "         \"zsyxqsrq\": \"2016-04-18\",\n" +
                    "         \"zzhywfw\": \"承担南岭成矿带中段广东省内北部地区有色金属资源勘查、开发及危机矿山接替资源及外围、深部找矿工作；参与地质灾害防治及其它基础性、公益性、战略性地质工作，为工程建设项目提供地质技术服务。\"\n" +
                    "      }]\n" +
                    "   }},\n" +
                    "   \"head\":    {\n" +
                    "      \"msg\": \"查询成功\",\n" +
                    "      \"status\": \"0\"\n" +
                    "   }\n" +
                    "}\n";
        }
        if ("xgbmcx_qyxx".equals(beanId)) {
            response = "{\n" +
                    "   \"data\": {\"cxjg\": [   {\n" +
                    "      \"apprdate\": 1550812808000,\n" +
                    "      \"dom\": \"南京市鼓楼区集慧路18号联创科技大厦12、13层\",\n" +
                    "      \"entname\": \"南京国图信息产业有限公司\",\n" +
                    "      \"enttypeCn\": \"有限责任公司（非自然人投资或控股的法人独资）\",\n" +
                    "      \"estdate\": 984672000000,\n" +
                    "      \"name\": \"孙XX\",\n" +
                    "      \"opfrom\": 984672000000,\n" +
                    "      \"opscope\": \"计算机软硬件开发与销售；系统集成；数据工程、网络工程、办公自动化、测绘工程设计、施工；不动产测绘；土地规划与整理；地图与地理信息服务；不动产评估与咨询；经济信息咨询服务；土地登记代理；农业规划设计与咨询；水利工程规划与设计；城市规划设计；地质灾害评估；档案整理；资产评估；林业调查规划设计。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n" +
                    "      \"regcap\": 2000,\n" +
                    "      \"regcapcurCn\": \"人民币元\",\n" +
                    "      \"regno\": \"32010200002XXXX\",\n" +
                    "      \"regorgCn\": \"南京市市场监督管理局\",\n" +
                    "      \"regstateCn\": \"存续（在营、开业、在册）\",\n" +
                    "      \"sExtNodenum\": \"320000\",\n" +
                    "      \"uniscid\": \"913201007260805485\"\n" +
                    "   }]},\n" +
                    "   \"head\":    {\n" +
                    "      \"code\": \"0000\",\n" +
                    "      \"msg\": \"success\"\n" +
                    "   }\n" +
                    "}\n";
        }
        if ("sw_wsxx_yc1".equals(beanId)) {
            response = "{\n" +
                    "    \"data\": {\n" +
                    "        \"fcwsxxdata\": [\n" +
                    "            {             \n" +
                    "                \"skmxxList\": [\n" +
                    "                    {                     \n" +
                    "                        \"dzsphm\": 332096201100126357,\n" +
                    "                        \"sz\": \"契税\",\n" +
                    "                        \"zspmmc\": \"存量房（商品住房买卖）\",\n" +
                    "                        \"kssl\": 130.9,\n" +
                    "                        \"jsje\": 1200000,\n" +
                    "                        \"sl\": 0.03,\n" +
                    "                        \"sksssq\": \"2020-11-12\",\n" +
                    "                        \"sksssz\": null,\n" +
                    "                        \"kce\": 0,\n" +
                    "                        \"sjje\": 36000                  \n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"dzsphm\": 332095201100019511,\n" +
                    "                \"htbh\": \"CL2020111200206\",\n" +
                    "                \"nsrmc\": \"郭敏\",\n" +
                    "                \"nsrsbh\": \"320902198805250043\",\n" +
                    "                \"pzzlDm\": \"000005013\",\n" +
                    "                \"pzzlmc\": null,\n" +
                    "                \"jehj\": 36000,\n" +
                    "                \"tpr\": \"胥珂\",\n" +
                    "                \"kprq\": \"2020-11-12\",\n" +
                    "                \"spxxbase64\": \"不动产登记部门\",\n" +
                    "                \"cxbm\": \"不动产登记部门\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"code\": 1,\n" +
                    "    \"msg\": \"查询房产交易完税信息成功!\"\n" +
                    "}\n";
        }
        if ("sw_wsxx_yc".equals(beanId)) {
            response = "{\n" +
                    "    \"data\": {\n" +
                    "        \"fcwsxxdata\": [\n" +
                    "            {             \n" +
                    "                \"skmxxList\": [\n" +
                    "                    {                     \n" +
                    "                        \"dzsphm\": 332096201100126357,\n" +
                    "                        \"sz\": \"契税\",\n" +
                    "                        \"zspmmc\": \"存量房（商品住房买卖）\",\n" +
                    "                        \"kssl\": 130.9,\n" +
                    "                        \"jsje\": 1200000,\n" +
                    "                        \"sl\": 0.03,\n" +
                    "                        \"sksssq\": \"2020-11-12\",\n" +
                    "                        \"sksssz\": null,\n" +
                    "                        \"kce\": 0,\n" +
                    "                        \"sjje\": 36000                  \n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"dzsphm\": 332095201100019511,\n" +
                    "                \"htbh\": \"CL2020111200206\",\n" +
                    "                \"nsrmc\": \"郭敏\",\n" +
                    "                \"nsrsbh\": \"320902198805250043\",\n" +
                    "                \"pzzlDm\": \"000005013\",\n" +
                    "                \"pzzlmc\": null,\n" +
                    "                \"jehj\": 36000,\n" +
                    "                \"tpr\": \"胥珂\",\n" +
                    "                \"kprq\": \"2020-11-12\",\n" +
                    "                \"spxxbase64\": \"不动产登记部门\",\n" +
                    "                \"cxbm\": \"不动产登记部门\"\n" +
                    "            },\n" +
                    "{             \n" +
                    "                \"skmxxList\": [\n" +
                    "                    {                     \n" +
                    "                        \"dzsphm\": 332096201100126357,\n" +
                    "                        \"sz\": \"契税\",\n" +
                    "                        \"zspmmc\": \"存量房（商品住房买卖）\",\n" +
                    "                        \"kssl\": 130.9,\n" +
                    "                        \"jsje\": 1200000,\n" +
                    "                        \"sl\": 0.03,\n" +
                    "                        \"sksssq\": \"2020-11-12\",\n" +
                    "                        \"sksssz\": null,\n" +
                    "                        \"kce\": 0,\n" +
                    "                        \"sjje\": 36000                  \n" +
                    "                    }\n" +
                    "                ],\n" +
                    "                \"dzsphm\": 332095201100019511,\n" +
                    "                \"htbh\": \"CL2020111200206\",\n" +
                    "                \"nsrmc\": \"郭敏\",\n" +
                    "                \"nsrsbh\": \"320902198805250043\",\n" +
                    "                \"pzzlDm\": \"000005013\",\n" +
                    "                \"pzzlmc\": null,\n" +
                    "                \"jehj\": 36000,\n" +
                    "                \"tpr\": \"胥珂\",\n" +
                    "                \"kprq\": \"2020-11-12\",\n" +
                    "                \"spxxbase64\": \"不动产登记部门111\",\n" +
                    "                \"cxbm\": \"不动产登记部门111\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"code\": 1,\n" +
                    "    \"msg\": \"查询房产交易完税信息成功!\"\n" +
                    "}\n";
        }
        if ("fs_jftb".equals(beanId)) {
            response = "{\n" +
                    "\"code\":\"00000\",\n" +
                    "\"msg\":\"操作成功\",\n" +
                    "\"timestamp\":\"20200701121200\",\n" +
                    "\"version \":\"1.0\",\n" +
                    "\"datakey\":\"69e9c4b8c0c03b821284ef2683df3333\",\n" +
                    "\"data\":[\n" +
                    "{\n" +
                    "     \"business_id\":\"100\",\n" +
                    "     \"businessnumber\":\"32040020000000002527\",  \"url\":\"https://jscz.govpay.ccb.com/online/fsjf?PyF_BillNo=32040020000000002527&Verf_CD=blank&Admn_Rgon_Cd=320400\"\n" +
                    "}\n" +
                    "]\n" +
                    "}\n";
        }
        if ("jfpt_jfztcx".equals(beanId)) {
            response = "{\n" +
                    "\"code\":\"00000\",\n" +
                    "\"msg\":\"操作成功\",\n" +
                    "\"timestamp\":\"20200701121200\",\n" +
                    "\"version \":\"1.0\",\n" +
                    "\"datakey\":\"69e9c4b8c0c03b821284ef2683df2b88\",\n" +
                    "\"data\":[\n" +
                    "{\n" +
                    "            \"business_id\":\"100\",\n" +
                    "\"businessnumber\": \"32040020000000002527\",\n" +
                    "\"pay_date\": \"20201029093030\",\n" +
                    "\"is_success\": \"1\",\n" +
                    "            \"hold1\": \"\",\n" +
                    "\t\t\t\"hold2\": \"\"\t\n" +
                    "}\n" +
                    "]\n" +
                    "}\n";
        }
        if ("clfbaxx".equals(beanId)) {
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
        if ("rd_spfhtxx".equals(beanId)) {
            if (requestParamMap.get("sjlx") != null) {
                if ("sy".equals(requestParamMap.get("sjlx").toString())) {
                    response = "{\n" +
                            "    \"res_error\": \"\",\n" +
                            "    \"res_time\": \"1620807206775\",\n" +
                            "    \"res_result\": [\n" +
                            "        {\n" +
                            "            \"ckh\": \"\",\n" +
                            "            \"fh\": \"402\",\n" +
                            "            \"UpZJ\": \"√\",\n" +
                            "            \"diy_djzx\": \"8011\",\n" +
                            "            \"diyqr\": \"中国银行\",\n" +
                            "            \"zhengcxdkje\": \"\",\n" +
                            "            \"diyqr_st1\": \"\",\n" +
                            "            \"djzmdh\": \"\",\n" +
                            "            \"diyfs\": \"2\",\n" +
                            "            \"diy\": \"已抵押\",\n" +
                            "            \"diy_dkfs\": \"\",\n" +
                            "            \"hsmj\": \"\",\n" +
                            "            \"qlrzjh\": \"320682198110275012;320122198506232824\",\n" +
                            "            \"anj\": \"已按揭\",\n" +
                            "            \"diyqr_lxdh\": \"051372827472\",\n" +
                            "            \"diy_bdcjz\": \"100\",\n" +
                            "            \"diyqr_zjzl\": \"7\",\n" +
                            "            \"diyqr_st\": \"2019-01-01\",\n" +
                            "            \"shangyxdkje\": \"80\",\n" +
                            "            \"htbh\": \"035020160929002\",\n" +
                            "            \"fpdm\": \"123456\",\n" +
                            "            \"chaf\": \"已查封\",\n" +
                            "            \"qlrmc\": \"柳志高;陶巧珍\",\n" +
                            "            \"hzdk\": \"九里香堤\",\n" +
                            "            \"diyqr_lxdh1\": \"\",\n" +
                            "            \"ckmj\": \"10\",\n" +
                            "            \"diyqr_zjhm1\": \"\",\n" +
                            "            \"UpFP\": \"√\",\n" +
                            "            \"zh\": \"80\",\n" +
                            "            \"bahth\": \"70AZ972I57M40245\",\n" +
                            "            \"UpHT\": \"√\",\n" +
                            "            \"zj\": \"700000\",\n" +
                            "            \"diyr\": \"\",\n" +
                            "            \"glmj\": \"15\",\n" +
                            "            \"diyqr_et\": \"2019-12-30\",\n" +
                            "            \"diyqr_zjhm\": \"1234567890\",\n" +
                            "            \"diyqr_et1\": \"\",\n" +
                            "            \"ckzh\": \"\",\n" +
                            "            \"diyqr1\": \"\",\n" +
                            "            \"basj\": \"2016-08-08 09:00:45\",\n" +
                            "            \"jyzt\": \"审核通过(归档)\",\n" +
                            "            \"diyqr_zjzl1\": \"\",\n" +
                            "            \"zjbhsj\": \"600000\",\n" +
                            "            \"fphm\": \"789456\"\n" +
                            "        }\n" +
                            "    ],\n" +
                            "    \"res_code\": 200\n" +
                            "}";

                } else if ("gjj".equals(requestParamMap.get("sjlx").toString())) {
                    response = "{\n" +
                            "    \"res_error\": \"\",\n" +
                            "    \"res_time\": \"1620807206775\",\n" +
                            "    \"res_result\": [\n" +
                            "        {\n" +
                            "            \"ckh\": \"\",\n" +
                            "            \"fh\": \"402\",\n" +
                            "            \"UpZJ\": \"√\",\n" +
                            "            \"diy_djzx\": \"8011\",\n" +
                            "            \"diyqr\": \"\",\n" +
                            "            \"zhengcxdkje\": \"80\",\n" +
                            "            \"diyqr_st1\": \"2019-01-01\",\n" +
                            "            \"djzmdh\": \"\",\n" +
                            "            \"diyfs\": \"2\",\n" +
                            "            \"diy\": \"已抵押\",\n" +
                            "            \"diy_dkfs\": \"公积金\",\n" +
                            "            \"hsmj\": \"\",\n" +
                            "            \"qlrzjh\": \"320682198110275012;320122198506232824\",\n" +
                            "            \"anj\": \"已按揭\",\n" +
                            "            \"diyqr_lxdh\": \"\",\n" +
                            "            \"diy_bdcjz\": \"100\",\n" +
                            "            \"diyqr_zjzl\": \"\",\n" +
                            "            \"diyqr_st\": \"\",\n" +
                            "            \"shangyxdkje\": \"\",\n" +
                            "            \"htbh\": \"035020160929002\",\n" +
                            "            \"fpdm\": \"123456\",\n" +
                            "            \"chaf\": \"已查封\",\n" +
                            "            \"qlrmc\": \"柳志高;陶巧珍\",\n" +
                            "            \"hzdk\": \"九里香堤\",\n" +
                            "            \"diyqr_lxdh1\": \"051372827472\",\n" +
                            "            \"ckmj\": \"10\",\n" +
                            "            \"diyqr_zjhm1\": \"1234567890\",\n" +
                            "            \"UpFP\": \"√\",\n" +
                            "            \"zh\": \"80\",\n" +
                            "            \"bahth\": \"70AZ972I57M40245\",\n" +
                            "            \"UpHT\": \"√\",\n" +
                            "            \"zj\": \"700000\",\n" +
                            "            \"diyr\": \"\",\n" +
                            "            \"glmj\": \"15\",\n" +
                            "            \"diyqr_et\": \"\",\n" +
                            "            \"diyqr_zjhm\": \"\",\n" +
                            "            \"diyqr_et1\": \"2019-12-30\",\n" +
                            "            \"ckzh\": \"\",\n" +
                            "            \"diyqr1\": \"南通公积金\",\n" +
                            "            \"basj\": \"2016-08-08 09:00:45\",\n" +
                            "            \"jyzt\": \"审核通过(归档)\",\n" +
                            "            \"diyqr_zjzl1\": \"7\",\n" +
                            "            \"zjbhsj\": \"600000\",\n" +
                            "            \"fphm\": \"789456\"\n" +
                            "        }\n" +
                            "    ],\n" +
                            "    \"res_code\": 200\n" +
                            "}";
                } else if ("zh".equals(requestParamMap.get("sjlx").toString())) {
                    response = "{\n" +
                            "    \"res_error\": \"\",\n" +
                            "    \"res_time\": \"1620807206775\",\n" +
                            "    \"res_result\": [\n" +
                            "        {\n" +
                            "            \"ckh\": \"\",\n" +
                            "            \"fh\": \"402\",\n" +
                            "            \"UpZJ\": \"√\",\n" +
                            "            \"diy_djzx\": \"8011\",\n" +
                            "            \"diyqr\": \"中国银行\",\n" +
                            "            \"zhengcxdkje\": \"100\",\n" +
                            "            \"diyqr_st1\": \"2020-01-01\",\n" +
                            "            \"djzmdh\": \"\",\n" +
                            "            \"diyfs\": \"2\",\n" +
                            "            \"diy\": \"未抵押\",\n" +
                            "            \"diy_dkfs\": \"\",\n" +
                            "            \"hsmj\": \"\",\n" +
                            "            \"qlrzjh\": \"320682198110275012;320122198506232824\",\n" +
                            "            \"anj\": \"已按揭\",\n" +
                            "            \"diyqr_lxdh\": \"051372827472\",\n" +
                            "            \"diy_bdcjz\": \"100\",\n" +
                            "            \"diyqr_zjzl\": \"7\",\n" +
                            "            \"diyqr_st\": \"2019-01-01\",\n" +
                            "            \"shangyxdkje\": \"80\",\n" +
                            "            \"htbh\": \"035020160929002\",\n" +
                            "            \"fpdm\": \"123456\",\n" +
                            "            \"chaf\": \"未查封\",\n" +
                            "            \"qlrmc\": \"柳志高;陶巧珍\",\n" +
                            "            \"hzdk\": \"九里香堤\",\n" +
                            "            \"diyqr_lxdh1\": \"051372827471\",\n" +
                            "            \"ckmj\": \"10\",\n" +
                            "            \"diyqr_zjhm1\": \"3205421111\",\n" +
                            "            \"UpFP\": \"√\",\n" +
                            "            \"zh\": \"80\",\n" +
                            "            \"bahth\": \"70AZ972I57M40245\",\n" +
                            "            \"UpHT\": \"√\",\n" +
                            "            \"zj\": \"700000\",\n" +
                            "            \"diyr\": \"\",\n" +
                            "            \"glmj\": \"15\",\n" +
                            "            \"diyqr_et\": \"2019-12-30\",\n" +
                            "            \"diyqr_zjhm\": \"1234567890\",\n" +
                            "            \"diyqr_et1\": \"2020-12-30\",\n" +
                            "            \"ckzh\": \"\",\n" +
                            "            \"diyqr1\": \"南通公积金\",\n" +
                            "            \"basj\": \"2016-08-08 09:00:45\",\n" +
                            "            \"jyzt\": \"审核通过(归档)\",\n" +
                            "            \"diyqr_zjzl1\": \"7\",\n" +
                            "            \"zjbhsj\": \"600000\",\n" +
                            "            \"fphm\": \"789456\"\n" +
                            "        }\n" +
                            "    ],\n" +
                            "    \"res_code\": 200\n" +
                            "}";

                }
            } else {
                response = "{\n" +
                        "    \"res_error\": \"\",\n" +
                        "    \"res_time\": \"1620807206775\",\n" +
                        "    \"res_result\": [\n" +
                        "        \n" +
                        "        {\n" +
                        "            \"ckh\": \"\",\n" +
                        "            \"fh\": \"402\",\n" +
                        "            \"UpZJ\": \"√\",\n" +
                        "            \"diy_djzx\": \"\",\n" +
                        "            \"diyqr\": \"\",\n" +
                        "            \"zhengcxdkje\": \"\",\n" +
                        "            \"diyqr_st1\": \"\",\n" +
                        "            \"djzmdh\": \"\",\n" +
                        "            \"diyfs\": \"\",\n" +
                        "            \"diy\": \"已抵押\",\n" +
                        "            \"diy_dkfs\": \"\",\n" +
                        "            \"hsmj\": \"\",\n" +
                        "            \"qlrzjh\": \"320682198110275012;320122198506232824\",\n" +
                        "            \"anj\": \"未按揭\",\n" +
                        "            \"diyqr_lxdh\": \"\",\n" +
                        "            \"diy_bdcjz\": \"\",\n" +
                        "            \"diyqr_zjzl\": \"\",\n" +
                        "            \"diyqr_st\": \"\",\n" +
                        "            \"shangyxdkje\": \"\",\n" +
                        "            \"htbh\": \"035020160806002\",\n" +
                        "            \"fpdm\": \"123456\",\n" +
                        "            \"chaf\": \"已查封\",\n" +
                        "            \"qlrmc\": \"柳志高;陶巧珍\",\n" +
                        "            \"hzdk\": \"九里香堤\",\n" +
                        "            \"diyqr_lxdh1\": \"\",\n" +
                        "            \"ckmj\": \"10\",\n" +
                        "            \"diyqr_zjhm1\": \"\",\n" +
                        "            \"UpFP\": \"√\",\n" +
                        "            \"zh\": \"80\",\n" +
                        "            \"bahth\": \"70AZ972I57M40245\",\n" +
                        "            \"UpHT\": \"√\",\n" +
                        "            \"zj\": \"700000\",\n" +
                        "            \"diyr\": \"\",\n" +
                        "            \"glmj\": \"15\",\n" +
                        "            \"diyqr_et\": \"\",\n" +
                        "            \"diyqr_zjhm\": \"\",\n" +
                        "            \"diyqr_et1\": \"\",\n" +
                        "            \"ckzh\": \"\",\n" +
                        "            \"diyqr1\": \"\",\n" +
                        "            \"basj\": \"2016-08-08 09:00:45\",\n" +
                        "            \"jyzt\": \"审核通过(归档)\",\n" +
                        "            \"diyqr_zjzl1\": \"\",\n" +
                        "            \"zjbhsj\": \"600000\",\n" +
                        "            \"fphm\": \"789456\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"res_code\": 200\n" +
                        "}";
            }

        }
        if ("rd_clfhtxx".equals(beanId)) {
            response = "{\n" +
                    "    \"res_error\": \"\",\n" +
                    "    \"res_time\": \"1620807206775\",\n" +
                    "    \"res_result\": [\n" +
                    "        {\n" +
                    "            \"htbh\": \"2021051411\",\n" +
                    "            \"ywrmc\": \"某1\",\n" +
                    "            \"glmj\": \"\",\n" +
                    "            \"basj\": \"\",\n" +
                    "            \"ywrzjh\": \"3206172222122\",\n" +
                    "            \"ckmj\": \"\",\n" +
                    "            \"jyzt\": \"审核通过\",\n" +
                    "            \"cwmj\": \"\",\n" +
                    "            \"bahth\": \"\",\n" +
                    "            \"ckzl\": \"车库坐落\",\n" +
                    "            \"UpHT\": \"√\",\n" +
                    "            \"BDCQZH\": \"证号1\",\n" +
                    "            \"cwzl\": \"\",\n" +
                    "            \"UpQLR\": \"√\",\n" +
                    "            \"qlrmc\": \"王锡\",\n" +
                    "            \"zj\": \"80000\",\n" +
                    "            \"UpYWR\": \"√\",\n" +
                    "            \"qlrzjh\": \"3204056766\",\n" +
                    "            \"hsmj\": \"\",\n" +
                    "            \"UpJHZ\": \"√\",\n" +
                    "            \"UpJHZ1\": \"√\",\n" +
                    "            \"UpHKB\": \"√\",\n" +
                    "            \"UpHKB1\": \"√\"\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"res_code\": 200\n" +
                    "}";

        }

        // 4.5
        if ("xgbmcx_shgx".equals(beanId)) {
            response = "{\n" +
                    "    \"head\":{\n" +
                    "        \"code\":\"0000\",\n" +
                    "        \"msg\":\"success\",\n" +
                    "        \"total\":10,\n" +
                    "        \"records\":1000,\n" +
                    "        \"page\":1,\n" +
                    "        \"pageSize\":10\n" +
                    "    },\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":[\n" +
                    "            {\n" +
                    "                \"CN_NAME\":\"爱德基金会\",\n" +
                    "                \"LEGAL_ID_CARD\":\"32*************005X\",\n" +
                    "                \"LEGAL_PEOPLE\":\"***\",\n" +
                    "                \"REGIST_TYPE\":\"1\",\n" +
                    "                \"REG_MON\":\"2500\",\n" +
                    "                \"RESIDENCE\":\"南京市汉口路71号\",\n" +
                    "                \"RN\":\"1\",\n" +
                    "                \"SORG_KIND\":\"1\",\n" +
                    "                \"SORG_PHONE\":\"83260888\",\n" +
                    "                \"SORG_TYPE\":\"J\",\n" +
                    "                \"UNIFIED_CODE\":\"5332000050917103XB\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
        }
        // 3.12
        if ("xgbmcx_rkkjzxxcx".equals(beanId)) {
            response = "{\n" +
                    "    \"data\": {\n" +
                    "        \"cxjg\": [\n" +
                    "            {\n" +
                    "                \"csd_gjdqm\": \"156\",\n" +
                    "                \"csd_ssxdqm\": \"320803\",\n" +
                    "                \"csrq\": \"1985-06-18\",\n" +
                    "                \"gmsfzh\": \"320198506185815\",\n" +
                    "                \"gmsfzh_ppddm\": \"1\",\n" +
                    "                \"mzdm\": \"01\",\n" +
                    "                \"swbs\": \"0\",\n" +
                    "                \"xbdm\": \"1\",\n" +
                    "                \"xm\": \"朱军\",\n" +
                    "                \"xm_ppddm\": \"1\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"head\": {\n" +
                    "        \"msg\": \"查询成功\",\n" +
                    "        \"status\": \"0\"\n" +
                    "    }\n" +
                    "}\n";
        }
        // 3.13
        if ("xgbmcx_rkksfhc".equals(beanId)) {
            response = "{\n" +
                    "    \"data\": {\n" +
                    "        \"cxjg\": [\n" +
                    "            {\n" +
                    "                \"gmsfzh\": \"32098506185815\",\n" +
                    "                \"gmsfzh_ppddm\": \"1\",\n" +
                    "                \"swbs\": \"0\",\n" +
                    "                \"xm\": \"朱军\",\n" +
                    "                \"xm_ppddm\": \"1\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"head\": {\n" +
                    "        \"msg\": \"查询成功\",\n" +
                    "        \"status\": \"0\"\n" +
                    "    }\n" +
                    "}\n";
        }

        // 3.14
        if ("xgbmcx_sydjxxcx".equals(beanId)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":[\n" +
                    "            {\n" +
                    "                \"dept_code\":\"xxx\",\n" +
                    "                \"adopt_cert_type_man_cn\":\"xxx\",\n" +
                    "                \"adopt_cert_num_man\":\"xxx\",\n" +
                    "                \"adopt_cert_type_woman\":\"xxx\",\n" +
                    "                \"baby_nation_cn\":\"xxx\",\n" +
                    "                \"op_type_cn\":\"xxx\",\n" +
                    "                \"op_date\":\"xxx\",\n" +
                    "                \"adopt_birth_woman\":\"xxx\",\n" +
                    "                \"baby_cert_num\":\"xxx\",\n" +
                    "                \"baby_birth\":\"xxx\",\n" +
                    "                \"adopt_birth_man\":\"xxx\",\n" +
                    "                \"baby_sex_cn\":\"xxx\",\n" +
                    "                \"adopt_name_woman\":\"xxx\",\n" +
                    "                \"adopt_cert_type_woman_cn\":\"xxx\",\n" +
                    "                \"adopt_nation_man_cn\":\"xxx\",\n" +
                    "                \"baby_name\":\"xxx\",\n" +
                    "                \"adopt_nation_woman_cn\":\"xxx\",\n" +
                    "                \"baby_nation\":\"xxx\",\n" +
                    "                \"adopt_name_man\":\"xxx\",\n" +
                    "                \"dept_code_cn\":\"xxx\",\n" +
                    "                \"adopt_cert_type_man\":\"xxx\",\n" +
                    "                \"adopt_nation_woman\":\"xxx\",\n" +
                    "                \"op_type\":\"xxx\",\n" +
                    "                \"adopt_nation_man\":\"xxx\",\n" +
                    "                \"adopt_cert_num_woman\":\"xxx\",\n" +
                    "                \"baby_sex\":\"xxx\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"msg\":\"查询成功\",\n" +
                    "        \"status\":\"0\"\n" +
                    "    }\n" +
                    "}";
        }

        // 3.15
        if ("xgbmcx_sydjxxjy".equals(beanId)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":\"匹配成功\"\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"msg\":\"查询成功\",\n" +
                    "        \"status\":\"0\"\n" +
                    "    }\n" +
                    "}";
        }

        // 3.16
        if ("xgbmcx_bzfwhhxx".equals(beanId)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":[\n" +
                    "            {\n" +
                    "                \"cremation_time\":\"2015-11-03\",\n" +
                    "                \"address\":\"铜山柳新镇口上村\",\n" +
                    "                \"death_time\":\"2015-11-03\",\n" +
                    "                \"sex\":\"1\",\n" +
                    "                \"name\":\"王淑宝\",\n" +
                    "                \"id_card\":\"320323195309201037\",\n" +
                    "                \"RN\":1\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"msg\":\"查询成功\",\n" +
                    "        \"status\":\"0\"\n" +
                    "    }\n" +
                    "}";
        }

        // 3.17
        if ("xgbmcx_wjwswyxzm".equals(beanId)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":[\n" +
                    "            {\n" +
                    "                \"patientname\":\"顾少山\",\n" +
                    "                \"causea\":\"颅内损伤，未特指\",\n" +
                    "                \"gendercode\":\"1\",\n" +
                    "                \"idcardcode\":\"xxxxxxx\",\n" +
                    "                \"nationcode\":\"01\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"msg\":\"查询成功\",\n" +
                    "        \"status\":\"0\"\n" +
                    "    }\n" +
                    "}";
        }

        // 3.18
        if ("xgbmcx_wjwswyxzmjy".equals(beanId)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":\"1\"\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"msg\":\"查询成功\",\n" +
                    "        \"status\":\"0\"\n" +
                    "    }\n" +
                    "}";
        }
        if ("ranqi_hf_ghcx".equals(beanId)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":\"1\"\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"msg\":\"查询成功\",\n" +
                    "        \"status\":\"0\"\n" +
                    "    }\n" +
                    "}";
        }
        if ("ranqi_wn_ghcx".equals(beanId)) {
            response = "{\n" +
                    "    \"result\":\"true\",\n" +
                    "    \"resultText\":\"可以过户\",\n" +
                    "    \"username\":\"刘德华\",\n" +
                    "    \"usersfid\":\"340100190010100021\",\n" +
                    "    \"daikou\":\"\",\n" +
                    "    \"address\":\"地址\",\n" +
                    "    \"qianfei\":\"\",\n" +
                    "    \"zhanghuyue\":\"\",\n" +
                    "    \"lastchaobiaoDate\":\"\",\n" +
                    "    \"lastBjs\":\"\"\n" +
                    "}";
        }
        if ("ranqi_hf_sqgh".equals(beanId)) {
            response = "{\n" +
                    "    \"data\":{\n" +
                    "        \"cxjg\":\"1\"\n" +
                    "    },\n" +
                    "    \"head\":{\n" +
                    "        \"msg\":\"查询成功\",\n" +
                    "        \"status\":\"0\"\n" +
                    "    }\n" +
                    "}";
        }
        if ("ranqi_wn_sqgh".equals(beanId)) {
            response = "\t{\n" +
                    "\"result\": \"true\",\n" +
                    "\"resultText\": \"提交成功，等待审核\",\n" +
                    "}";
        }
        if ("ranqi_kl_sqgh".equals(beanId)) {
            response = "{\n" +
                    "    \"code\":1,\n" +
                    "    \"msg\":\"提交成功，等待审核\",\n" +
                    "    \"time\":\"1656401581\",\n" +
                    "    \"data\":{\n" +
                    "        \"userhuhao\":\"1080002045\"\n" +
                    "    }\n" +
                    "}";
        }
        if("tsSwxxClf_http_dk".equals(beanId) || "tsSwxxSpf_http_dk".equals(beanId)) {
//            response="{\n" +
//                    "    \"result\": {\n" +
//                    "        \"head\": {\n" +
//                    "            \"channel_id\": \"AH.TAX.BDCJY.XX\",\n" +
//                    "            \"tran_id\": \"TAX.INTEGRATE.JY.SECONDHOUSEINFO\",\n" +
//                    "            \"tran_date\": \"20191227\",\n" +
//                    "            \"tran_seq\": \"9A5A1B5B65994B4A97A9FDA52659B358\",\n" +
//                    "            \"rtn_code\": \"200\",\n" +
//                    "            \"rtn_msg\": \"存量房信息采集保存成功！\"\n" +
//                    "        },\n" +
//                    "        \"body\": {\n" +
//                    "            \"fwuuid\": \"d889bb7237594c3386fb28bcdd0fd246\",\n" +
//                    "            \"BDCDYH\": \"3416000151911220053768\",\n" +
//                    "            \"htbh\": \"FWR40405333033\",\n" +
//                    "            \"sldh\": \"FWR40405333033\"\n" +
//                    "        }\n" +
//                    "    }\n" +
//                    "}";
            response = "{\n" +
                    "    \"result\": {\n" +
                    "        \"head\": {\n" +
                    "            \"channel_id\": \"AH.TAX.BDCJY.XX\",\n" +
                    "            \"tran_id\": \"TAX.INTEGRATE.JY.FIRSTHOUSEINFO\",\n" +
                    "            \"tran_date\": \"20191227\",\n" +
                    "            \"tran_seq\": \"9A5A1B5B65994B4A97A9FDA52659B358\",\n" +
                    "            \"rtn_code\": \"500\",\n" +
                    "            \"rtn_msg\": \"该商品房不动产单元号已存在，商品房采集失败！\"\n" +
                    "        },\n" +
                    "        \"body\": {\n" +
                    "            \"fwuuid\": \"d889bb7237594c3386fb28bcdd0fd245\",\n" +
                    "            \"bdcdyh\": \"3416000151911220053768\",\n" +
                    "            \"htbh\": \"FW0034939485\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

        }
        if("shuiGetToken".equals(beanId)){
            response="{\n" +
                    "    \"code\": 200,\n" +
                    "    \"data\": {\n" +
                    "        \"token\": \"SDFJLKJsfdsiojDFJDLK\"\n" +
                    "    },\n" +
                    "    \"msg\": \"成功\"\n" +
                    "}";
        }
        if ("csj_sfm_getZz".equals(beanId)) {
            response = "{\n" +
                    "    \"code\":\"000001\",\n" +
                    "    \"message\":\"处理成功\",\n" +
                    "    \"data\":[\n" +
                    "        {\n" +
                    "            \"name\":\"中华人民共和国职业资格证书\",\n" +
                    "            \"pdf\":\"JVBERi0xLjQKJeLjz9MKNCAwIG9iago8PC9MZW5ndGgxIDIxNz\",\n" +
                    "            \"liscenseInfo\":{\n" +
                    "                \"creation_time\":\"2020-10-20 00:00:00\",\n" +
                    "                \"issuer\":\"省人社\",\n" +
                    "                \"holder_identity_type\":\"10\",\n" +
                    "                \"issue_org_code\":\"113200012332674U\",\n" +
                    "                \"issue_date\":\"2013-01-01 00:00:00\",\n" +
                    "                \"holder_identity_num\":\"32048119990101618\",\n" +
                    "                \"holder_name\":\"test\",\n" +
                    "                \"data_fields\":\"{'ZZMC':'中华人民共和国职业资格证书','ZZHM':'13111111111','CYRMC':'xx','CYRSFZJLX':'10','CYRSFZJHM':'320582111111111111','FZRQ':'2013-01-01','XB':'1','CSRQ':'1994-06-10','ZYJDJ':'汽车维修工','LLZSKSCJ':'60','CZJNKHCJ':'74','ZYDJ':'4','FZJGMC':'江苏省人力资源和社会保障厅','FZJGZZJGDM':'113201232674U','FZJGSSXZQHDM':'320000'}\",\n" +
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
                    "    ],\n" +
                    "    \"agent\":\"\",\n" +
                    "    \"extra\":\"38_16421413381261\"\n" +
                    "}";
        }
        if ("yrb_htxx".equals(beanId)) {
            response = "{\n" +
                    "\t\"flag\": true,\n" +
                    "\t\"msg\": \"\",\n" +
                    "\t\"data\": {\n" +
                    "\t\t\"htxx\": {\n" +
                    "\t\t\t\"BH\": \"201709140020\",\n" +
                    "\t\t\t\"CMF\": \"海安市海纳置业投资有限公司\",\n" +
                    "\t\t\t\"FWQY\": \"海安区\",\t\t\t\n" +
                    "\t\t\t\"XMMC\": \"龙南御景苑四期\",\n" +
                    "\t\t\t\"FWZL\": \"龙南御景苑四期21幢601室\",\t\t\t\n" +
                    "\t\t\t\"FWLX\": \"1\",\n" +
                    "\t\t\t\"HTJE\": \"1211266\",\n" +
                    "\t\t\t\"BASJ\": \"2017-9-14 9:42:04\",\n" +
                    "\t\t\t\"HTZT\": \"1\",\n" +
                    "\t\t\t\"SFBL\": \"30.65\",\n" +
                    "\t\t\t\"JFRQ\": \"2018-5-30\",\n" +
                    "             \"ZXQK\": \"1\",\n" +
                    "\t\t\t\"DZHT\": \"\"\n" +
                    "\t\t},\n" +
                    "\t\t\"msfxx\": [{\n" +
                    "\t\t\t\"MSRXM\": \"李进丰\",\n" +
                    "\t\t\t\"MSRZJH\": \"320525197905165311\",\n" +
                    "\t\t\t\"MSRGX\": \"丈夫\"\n" +
                    "\t\t}],\n" +
                    "\t\t\"fwxx\": [{\n" +
                    "\t\t\t\"FWYT\": \"住宅\",\n" +
                    "\t\t\t\"FH\": \"601\",\n" +
                    "\t\t\t\"FWMJ\": \"132.05\",\n" +
                    "\t\t\t\"FWZJ\": \"820330\",\n" +
                    "\t\t\t\"XZZT\": \"0\",\n" +
                    "\"DYZT\": \"0\"\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"FWYT\": \"住宅\",\n" +
                    "\t\t\t\"FH\": \"601阁\",\n" +
                    "\t\t\t\"FWMJ\": \"68.73\",\n" +
                    "\t\t\t\"FWZJ\": \"390936\",\n" +
                    "\t\t\t\"XZZT\": \"0\",\n" +
                    "\"DYZT\": \"0\"\n" +
                    "\t\t}]\n" +
                    "\t}\n" +
                    "}\n";
        }
        if ("dsjj_gzajjbxx".equals(beanId)) {
            response = "{\n" +
                    "    \"code\": \"0\",\n" +
                    "    \"obligee\": [\n" +
                    "        {\n" +
                    "            \"name\": \"江希国\",\n" +
                    "            \"sex\": \"男\",\n" +
                    "            \"nation\": \"汉\",\n" +
                    "            \"idNo\": \"320822196410066314\",\n" +
                    "            \"birthday\": \"19641006\",\n" +
                    "            \"address\": \"江苏省灌南县新安镇新安北路10－2号\",\n" +
                    "            \"issueOrg\": \"灌南县公安局\",\n" +
                    "            \"ispass\": true,\n" +
                    "            \"beginDate\": \"20060308\",\n" +
                    "            \"endDate\": \"20260308\",\n" +
                    "            \"serviceId\": \"20221026163954686\",\n" +
                    "            \"idCardImg\": \"111\",\n" +
                    "            \"photoImg\": \"test\",\n" +
                    "            \"personType\": \"1\",\n" +
                    "            \"addTime\": \"2022-10-26 16:15:48\",\n" +
                    "            \"accountName\": \"嵇洁\",\n" +
                    "            \"mobilePhone\": \"\",\n" +
                    "            \"ownMethod\": \"\",\n" +
                    "            \"powerRate\": \"\",\n" +
                    "            \"powerArea\": \"\",\n" +
                    "            \"agentMessage\": null\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"obligor\": [\n" +
                    "        {\n" +
                    "            \"name\": \"江涛\",\n" +
                    "            \"sex\": \"男\",\n" +
                    "            \"nation\": \"汉\",\n" +
                    "            \"idNo\": \"320724198601140018\",\n" +
                    "            \"birthday\": \"19860114\",\n" +
                    "            \"address\": \"江苏省灌南县新安镇华景苑小区1幢1单元502室\",\n" +
                    "            \"issueOrg\": \"灌南县公安局\",\n" +
                    "            \"ispass\": true,\n" +
                    "            \"beginDate\": \"20160225\",\n" +
                    "            \"endDate\": \"20360225\",\n" +
                    "            \"serviceId\": \"20221026163954686\",\n" +
                    "            \"idCardImg\": \"333\",\n" +
                    "            \"photoImg\": \"test\",\n" +
                    "            \"personType\": \"2\",\n" +
                    "            \"addTime\": \"2022-10-26 16:15:48\",\n" +
                    "            \"accountName\": \"嵇洁\",\n" +
                    "            \"mobilePhone\": \"\",\n" +
                    "            \"ownMethod\": \"\",\n" +
                    "            \"powerRate\": \"\",\n" +
                    "            \"powerArea\": \"\",\n" +
                    "            \"agentMessage\": null\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"mandatary\": [\n" +
                    "        {\n" +
                    "            \"name\": \"胡静\",\n" +
                    "            \"sex\": \"女\",\n" +
                    "            \"nation\": \"汉\",\n" +
                    "            \"idNo\": \"320724198805290026\",\n" +
                    "            \"birthday\": \"19880529\",\n" +
                    "            \"address\": \"江苏省灌南县新安镇华景苑小区1幢1单元502室\",\n" +
                    "            \"issueOrg\": \"灌南县公安局\",\n" +
                    "            \"ispass\": true,\n" +
                    "            \"beginDate\": \"20170316\",\n" +
                    "            \"endDate\": \"20370316\",\n" +
                    "            \"serviceId\": \"20221026163954686\",\n" +
                    "            \"idCardImg\": \"111\",\n" +
                    "            \"photoImg\": \"test\",\n" +
                    "            \"personType\": \"3\",\n" +
                    "            \"addTime\": \"2022-10-26 16:15:48\",\n" +
                    "            \"accountName\": \"嵇洁\",\n" +
                    "            \"mobilePhone\": \"\",\n" +
                    "            \"ownMethod\": \"\",\n" +
                    "            \"powerRate\": \"\",\n" +
                    "            \"powerArea\": \"\",\n" +
                    "            \"agentMessage\": null\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }
        if ("yrb_zd_clfhqjg".equals(beanId)) {
            response = "{\n" +
                    "    \"flag\":true,\n" +
                    "    \"msg\":\"\",\n" +
                    "    \"data\":{\n" +
                    "        \"htxx\":{\n" +
                    "            \"BH\":\"2577574\",\n" +
                    "            \"FWQY\":\"婺城区\",\n" +
                    "            \"HTJE\":\"910330\",\n" +
                    "            \"FWZL\":\"元和街道乐苑新村1幢101室\",\n" +
                    "            \"YCQZH\":\"20167012125\",\n" +
                    "            \"CMRXM\":\"钱芳\",\n" +
                    "            \"CMRZJH\":\"310108196908274826\",\n" +
                    "            \"JJJG\":\"安美投资管理有限公司\",\n" +
                    "            \"JJJGBAH\":\"008347\",\n" +
                    "            \"BASJ\":\"2018-03-26 11:27:17\",\n" +
                    "            \"HTZT\":\"1\",\n" +
                    "            \"MSRXM\":\"李宏芳,郑一凡\",\n" +
                    "            \"MSRZJH\":\"320621196807174746,320621199801010525\",\n" +
                    "            \"DZHT\":\"\"\n" +
                    "        },\n" +
                    "        \"fwxx\":[\n" +
                    "            {\n" +
                    "                \"FH\":\"101\",\n" +
                    "                \"FWYZ\":\"住宅\",\n" +
                    "                \"FWMJ\":\"113.22\",\n" +
                    "                \"XZZT\":\"0\",\n" +
                    "                \"DYZT\":\"0\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"FH\":\"289\",\n" +
                    "                \"FWYT\":\"车位\",\n" +
                    "                \"FWMJ\":\"14.00\",\n" +
                    "                \"XZZT\":\"0\",\n" +
                    "                \"DYZT\":\"0\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
        }
        if("shui_ghhy".equals(beanId)){
            response = "{\n" +
                    "    \"code\": \"0000\",\n" +
                    "    \"msg\": \"成功\",\n" +
                    "    \"data\": {\n" +
                    "        \"electricFeeAddr\": \"****滨新村一期8栋14号车库\",\n" +
                    "        \"arrearage\": \"118\",\n" +
                    "        \"electricFeeName\": \"王**\",\n" +
                    "        \"electricFeeNum\": \"1000836333\",\n" +
                    "        \"this_ymd\": \"2013-01-04 08:14:35.0\",\n" +
                    "        \"arrearageMessage\": \"获取欠费信息成功！\"\n" +
                    "    }\n" +
                    "}";
        }
        if("tzshui_ghhy".equals(beanId) || "qi_ghhy".equals(beanId) || "tzqi_ghhy".equals(beanId)){
            response = "{\n" +
                    "    \"code\": \"1\",\n" +
                    "    \"msg\": \"成功\",\n" +
                    "    \"data\": {\n" +
                    "        \"originalUserName\": \"王**\",\n" +
                    "        \"address\": \"\",\n" +
                    "        \"sfgh\": \"1\",\n" +
                    "        \"yy\": \"欠费\"\n" +
                    "    }\n" +
                    "}";
        }
        if("qi_gh".equals(beanId) || "tzqi_gh".equals(beanId) || "shui_gh".equals(beanId) || "tzshui_gh".equals(beanId)){
            response = "{\n" +
                    "    \"code\": \"1\",\n" +
                    "    \"msg\": \"成功\"\n" +
                    "}";
        }
        return response;
    }
}
