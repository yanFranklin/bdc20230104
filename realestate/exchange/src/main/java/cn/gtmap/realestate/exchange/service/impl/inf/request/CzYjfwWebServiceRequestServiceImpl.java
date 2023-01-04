package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.WebServiceUtils;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.CzYjfwWebServiceBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/30 10:59
 * @description
 */
@Service(value = "czYjfwWebService")
public class CzYjfwWebServiceRequestServiceImpl extends InterfaceRequestService<CzYjfwWebServiceBO> {
    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [builder]
     * @return void
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        CzYjfwWebServiceBO prop = super.getRequestPropFromBuilder(builder);
        if(requestBody != null && CheckParameter.checkAnyParameter(requestBody) && StringUtils.isNoneBlank(prop.getUrl(),
                prop.getMethodName())) {
            String response = null;
            Exception logE = null;

            try{
            response= WebServiceUtils.callWebService(prop.getUrl(),prop.getMethodName(),requestBody.toString());
            super.setResponseBody(response,builder);
            }catch (Exception e) {
                LOGGER.error("czYjfwWebService 请求异常：url:{},param:{}",prop.getUrl(),requestBody.toString(),e);
            }finally {
                // 记录 请求日志
                try{
                    AuditEventBO auditEventBO = new AuditEventBO(prop,builder);
                    auditEventBO.setRequest(JSONObject.toJSONString(requestBody));
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                }catch (Exception e){
                    LOGGER.error("记录请求日志异常",e);
                }
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

    private String testResponse(String beanid) {
        String response = "";
        if (StringUtils.equals(beanid, "dzh_jfbl")) {
            response = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+IAo8Uk9PVD4KPENPREU+LTE8L0NPREU+PE1TRz7osIPnlKjmiJDlip/vvIE8L01TRz48VElNRVNUQU1QPjIwMjAwNzAxMTIxMjAwPC9USU1FU1RBTVA+PERBVEE+PEJVU0lORVNTX0lEPjEwMDwvQlVTSU5FU1NfSUQ+PEJVU0lORVNTTlVNQkVSPjMyMDQwMDIwMDAwMDAwMDAyNTI3PC9CVVNJTkVTU05VTUJFUj48VVJMPmh0dHBzOi8vanNjei5nb3ZwYXkuY2NiLmNvbS9vbmxpbmUvZnNqZj9QeUZfQmlsbE5vPTMyMDQwMDIwMDAwMDAwMDAyNTI3JlZlcmZfQ0Q9YmxhbmsmQWRtbl9SZ29uX0NkPTMyMDQwMDwvVVJMPjwvREFUQT48L1JPT1Q+";
        } else if (StringUtils.equals(beanid, "jfqr")) {
            response = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+PFJPT1Q+PENPREU+LTE8L0NPREU+PE1TRz7osIPnlKjmiJDlip/vvIE8L01TRz48VElNRVNUQU1QPjIwMjAwNzAxMTIxMjAwPC9USU1FU1RBTVA+PERBVEE+PEJVU0lORVNTX0lEPjEwMDwvQlVTSU5FU1NfSUQ+PEJVU0lORVNTTlVNQkVSPjMyMDQwMDIwMDAwMDAwMDAyNTI3PC9CVVNJTkVTU05VTUJFUj48UEFZX0RBVEU+PC9QQVlfREFURT48SVNfU1VDQ0VTUz4xPC9JU19TVUNDRVNTPjxTVEFUVVNfTUM+PC9TVEFUVVNfTUM+PC9EQVRBPjwvUk9PVD4=";
        } else if (StringUtils.equals(beanid, "pjzf")) {
            response = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+PFJPT1Q+PENPREU+LTE8L0NPREU+PE1TRz7osIPnlKjmiJDlip/vvIE8L01TRz48VElNRVNUQU1QPjIwMjAwNzAxMTIxMjAwPC9USU1FU1RBTVA+PERBVEEvPjwvUk9PVD4=";
        } else if (StringUtils.equals(beanid, "dzpj")) {
            response = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0IyMzEyIj8+IDxST09UPjxDT0RFPi0xPC9DT0RFPjxNU0c+6LCD55So5oiQ5Yqf77yBPC9NU0c+PFRJTUVTVEFNUD4yMDIwMDcwMTEyMTIwMDwvVElNRVNUQU1QPjxEQVRBPjxCVVNJTkVTU19JRD4xMDA8L0JVU0lORVNTX0lEPjxCVVNJTkVTU05VTUJFUj4zMjA0MDAyMDAwMDAwMDAwMjUyNzwvQlVTSU5FU1NOVU1CRVI+PFVSTD5odHRwOi8vZmluYW5jei5jbi9mcy1nYXRld2F5L2Jhc2UvY29tbW9uL2Rvd25sb2FkRmlsZUZyb21GaWxlP2J1c2luZXNzbnVtYmVyPTMyMDQwMDIwMDAwMDAwMDAyNTI3PC9VUkw+PC9EQVRBPjwvUk9PVD4="; }
        return response;
    }
}
