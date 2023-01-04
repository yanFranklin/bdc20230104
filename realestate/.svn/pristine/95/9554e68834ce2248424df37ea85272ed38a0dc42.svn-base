package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.RestReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-23
 * @description REST POST方式请求数据
 */
@Service(value = "restPost")
public class RestPostRequestServiceImpl extends InterfaceRequestService<RestReqPropBO> {

    @Resource(name = "exchangeRestTemplate")
    private RestTemplate restTemplate;

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        // 获取请求属性
        RestReqPropBO prop = super.getRequestPropFromBuilder(builder);
        Object requestBody = builder.getRequestBody();
        if (StringUtils.isNotBlank(prop.getUrl())) {
            HttpHeaders headers = new HttpHeaders();
            String mediaType = StringUtils.isNotBlank(prop.getContentType()) ? prop.getContentType() : "application/json; charset=UTF-8";
            MediaType type = MediaType.parseMediaType(mediaType);
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> formEntity = new HttpEntity<String>(JSONObject.toJSONString(requestBody), headers);
            String response = null;
//            response = "{\n" +
//                    "  \"body\": {\n" +
//                    "    \"cqbcdmsye\": \"0.0\",\n" +
//                    "    \"sbmxcxlb\": [\n" +
//                    "      {\n" +
//                    "        \"cjjg\": 1709900,\n" +
//                    "        \"fwwzdz\": \"高新区石莲南路2426号悦湖山院6幢501\",\n" +
//                    "        \"jyskblb\": [\n" +
//                    "          {\n" +
//                    "            \"fcxxId\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "            \"jsje\": 1628476.1905,\n" +
//                    "            \"jyfe\": 0,\n" +
//                    "            \"mmfbz\": \"1\",\n" +
//                    "            \"nsrid\": \"650c363716974a358320980eab095d9f\",\n" +
//                    "            \"se\": 32569.52,\n" +
//                    "            \"sl\": 0.02,\n" +
//                    "            \"zsxmDm\": \"10119\",\n" +
//                    "            \"zsxmmc\": \"契税\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "        \"jyuuid\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "        \"jzmj\": \"107.04\",\n" +
//                    "        \"mmfbz\": \"1\",\n" +
//                    "        \"nsrmc\": \"程超英\",\n" +
//                    "        \"nsrsbh\": \"340103195810284021\",\n" +
//                    "        \"ynse\": 32569.52\n" +
//                    "      },\n" +
//                    "      {\n" +
//                    "        \"cjjg\": 1709900,\n" +
//                    "        \"fwwzdz\": \"高新区石莲南路2426号悦湖山院6幢501\",\n" +
//                    "        \"jyskblb\": [\n" +
//                    "          {\n" +
//                    "            \"fcxxId\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "            \"jsje\": 81423.81,\n" +
//                    "            \"jyfe\": 0,\n" +
//                    "            \"mmfbz\": \"0\",\n" +
//                    "            \"nsrid\": \"8f558cedec6e40c6bd09d78f04b1a9a4\",\n" +
//                    "            \"se\": 2849.83,\n" +
//                    "            \"sl\": 0.07,\n" +
//                    "            \"zsxmDm\": \"10109\",\n" +
//                    "            \"zsxmmc\": \"城市维护建设税\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"fcxxId\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "            \"jsje\": 1628476.1905,\n" +
//                    "            \"jyfe\": 0,\n" +
//                    "            \"mmfbz\": \"0\",\n" +
//                    "            \"nsrid\": \"8f558cedec6e40c6bd09d78f04b1a9a4\",\n" +
//                    "            \"se\": 16284.76,\n" +
//                    "            \"sl\": 0.01,\n" +
//                    "            \"zsxmDm\": \"10106\",\n" +
//                    "            \"zsxmmc\": \"个人所得税\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"fcxxId\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "            \"jsje\": 81423.81,\n" +
//                    "            \"jyfe\": 0,\n" +
//                    "            \"mmfbz\": \"0\",\n" +
//                    "            \"nsrid\": \"8f558cedec6e40c6bd09d78f04b1a9a4\",\n" +
//                    "            \"se\": 814.24,\n" +
//                    "            \"sl\": 0.02,\n" +
//                    "            \"zsxmDm\": \"30216\",\n" +
//                    "            \"zsxmmc\": \"地方教育附加\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"fcxxId\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "            \"jsje\": 81423.81,\n" +
//                    "            \"jyfe\": 0,\n" +
//                    "            \"mmfbz\": \"0\",\n" +
//                    "            \"nsrid\": \"8f558cedec6e40c6bd09d78f04b1a9a4\",\n" +
//                    "            \"se\": 1221.35,\n" +
//                    "            \"sl\": 0.03,\n" +
//                    "            \"zsxmDm\": \"30203\",\n" +
//                    "            \"zsxmmc\": \"教育费附加\"\n" +
//                    "          },\n" +
//                    "          {\n" +
//                    "            \"fcxxId\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "            \"jsje\": 1628476.1905,\n" +
//                    "            \"jyfe\": 0,\n" +
//                    "            \"mmfbz\": \"0\",\n" +
//                    "            \"nsrid\": \"8f558cedec6e40c6bd09d78f04b1a9a4\",\n" +
//                    "            \"se\": 81423.81,\n" +
//                    "            \"sl\": 0.05,\n" +
//                    "            \"zsxmDm\": \"10101\",\n" +
//                    "            \"zsxmmc\": \"增值税\"\n" +
//                    "          }\n" +
//                    "        ],\n" +
//                    "        \"jyuuid\": \"9bdb58fda40b4a6393d5edf725bfd076\",\n" +
//                    "        \"jzmj\": \"107.04\",\n" +
//                    "        \"mmfbz\": \"0\",\n" +
//                    "        \"nsrmc\": \"尹学虎\",\n" +
//                    "        \"nsrsbh\": \"340121198005164031\",\n" +
//                    "        \"ynse\": 102593.98999999999\n" +
//                    "      }\n" +
//                    "    ]\n" +
//                    "  },\n" +
//                    "  \"head\": {\n" +
//                    "    \"channel_id\": \"AHHFBDC\",\n" +
//                    "    \"rtn_code\": \"200\",\n" +
//                    "    \"rtn_msg\": \"成功\",\n" +
//                    "    \"token\": \"GJSWZJAHSSWJ\",\n" +
//                    "    \"tran_date\": \"20191218\",\n" +
//                    "    \"tran_id\": \"ahsw.fcjy.yth.fcjyxxcx\",\n" +
//                    "    \"tran_seq\": \"3CGJ0603DZBN40IE\"\n" +
//                    "  }\n" +
//                    "}";
            Exception logE = null;
            try {
                if (StringUtils.isNotBlank(prop.getSoTimeout()) && NumberUtils.isNumber(prop.getSoTimeout())) {
                    LOGGER.error("httpPost 请求url：{}，参数配置:{}", prop.getSoTimeout());
                }
                SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
                requestFactory.setConnectTimeout(NumberUtils.toInt(prop.getSoTimeout()));
                requestFactory.setReadTimeout(NumberUtils.toInt(prop.getSoTimeout()));

                RestTemplate restTemplate = new RestTemplate(requestFactory);
                response = restTemplate.postForObject(prop.getUrl(), formEntity, String.class);
            } catch (Exception e) {
                logE = e;
                LOGGER.error("restPost 请求异常：url:{},formEntity:{}", prop.getUrl(), JSONObject.toJSONString(formEntity), e);
                throw new AppException("restPost 请求异常");
            } finally {
                try {
                    AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                    auditEventBO.setRequest(JSONObject.toJSONString(formEntity));
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                } catch (Exception e) {
                    LOGGER.error("记录请求日志异常", e);
                }
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
    public String dealWithResponse(String response) {
        return response;
    }
}
