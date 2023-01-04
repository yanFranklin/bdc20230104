package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.NtCssnjWebservicePropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssnj.biz.wbservice.client.typt.CssnjWebService;
import com.cssnj.biz.wbservice.client.typt.CssnjWebService_Service;
import com.cssnj.biz.wbservice.client.typt.Exception_Exception;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-09-24
 * @description 南通 请求中软 webservice方式
 */
@Service(value = "ntCssnjWebservice")
public class NtCssnjWebserviceRequestServiceImpl extends InterfaceRequestService<NtCssnjWebservicePropBO> {
    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        NtCssnjWebservicePropBO propBO = super.getRequestPropFromBuilder(builder);
        Object requestBody = builder.getRequestBody();
        if (StringUtils.isNoneBlank(propBO.getMethodName(), propBO.getqName(), propBO.getUrl())) {
            String response = "";
            Exception logE = null;
            try {
                HashMap<String, Object> mapMes = new HashMap<String, Object>();
                mapMes.put("data", JSONObject.toJSONString(requestBody));
                String xmlStr = createSendSOAPPackageForMap(mapMes, propBO.getMethodName());
                Map<String, String> retmap = new HashMap<String, String>();
                //自定义地址
                URL url = null;
                URL baseUrl = CssnjWebService_Service.class.getResource(".");
                //这个是税务局内网地址需要改成政务网前置地址
                url = new URL(baseUrl, propBO.getUrl());
                CssnjWebService_Service client =
                        new CssnjWebService_Service(url, new QName(propBO.getqName(), "cssnjWebService"));
                CssnjWebService service = client.getCssnjHttpServicePort();
                String result = service.invokeTask(xmlStr);
//                String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><resws><tran_id>CSSNJ.WS.FCJY.QUERYDSF</tran_id><channel_id>TyWebService_saveClfxxNT</channel_id><tran_seq>0d4330221fd04c8197260ad1d3b84be2</tran_seq><rtn_msg><Code>1</Code><Message><![CDATA[service deal success.]]></Message></rtn_msg><body><![CDATA[<bizdata><message>保存成功!</message><success>1</success></bizdata>]]></body></resws>";
                LOGGER.info("请求NtCssnj，返回信息,转换前：{}", result);
                if (StringUtils.isNotBlank(result)) {
                    // 直接转换为 JSON结构
                    response = XmlEntityConvertUtil.xmlToJson(result);
                    LOGGER.info("请求NtCssnj，返回信息,转换后：{}", response);
                    if (StringUtils.isNotBlank(response)) {
                        JSONObject body = JSON.parseObject(response).getJSONObject("resws");
                        if (body != null && StringUtils.isNotBlank(body.getString("body"))) {
                            response = XmlEntityConvertUtil.xmlToJson(body.getString("body"));
                        }
                    }
                    super.setResponseBody(response, builder);
                }
            } catch (Exception_Exception e) {
                logE = e;
                LOGGER.error("NtCssnj请求异常", e);
            } catch (MalformedURLException e) {
                logE = e;
                LOGGER.error("NtCssnj请求异常", e);
            } finally {
                // 记录 请求日志
                AuditEventBO auditEventBO = new AuditEventBO(propBO, builder);
                auditEventBO.setRequest(JSONObject.toJSONString(requestBody));
                auditEventBO.setResponse(response);
                auditEventBO.setException(logE);
                super.saveAuditLog(auditEventBO);
            }
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


    public static String createSendSOAPPackageForMap(HashMap<String, Object> reqMap, String channelId) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<reqws>");
        sb.append("<tran_id>CSSNJ.WS.FCJY.QUERYDSF</tran_id>");
        sb.append("<channel_id>").append(channelId).append("</channel_id>");
        sb.append("<tran_seq>").append(UUIDGenerator.generate()).append("</tran_seq>");
        sb.append("<body>");
        sb.append("<![CDATA[");
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<taxxml>");
        sb.append("<inobj>");
        for (Iterator<String> iter = reqMap.keySet().iterator(); iter.hasNext(); ) {
            String key = iter.next();
            String value = (String) reqMap.get(key);
            sb.append("<" + key + ">" + value + "</" + key + ">");
        }
        sb.append("</inobj>");
        sb.append("</taxxml>");
        sb.append("]]>");
        sb.append("</body>");
        sb.append("</reqws>");
        return sb.toString();
    }

}
