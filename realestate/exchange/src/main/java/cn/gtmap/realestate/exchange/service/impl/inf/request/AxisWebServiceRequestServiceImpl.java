package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.WebServiceUtils;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.AxisWebServiceBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-07-06
 * @description axis webservice请求方式
 */
@Service(value = "axisWebService")
public class AxisWebServiceRequestServiceImpl extends InterfaceRequestService<AxisWebServiceBO> {

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        AxisWebServiceBO prop = super.getRequestPropFromBuilder(builder);
        if (requestBody != null && CheckParameter.checkAnyParameter(requestBody) && StringUtils.isNoneBlank(prop.getUrl(), prop.getUri(),
                prop.getMethodName())) {
            // 获取  请求 参数 键值对
            Map<String, Object> requestParamMap = CommonUtil.objectToMap(requestBody);
            String response = null;
            Exception logE = null;
            try {
                List<String> paramArray = new ArrayList<String>();
                List<Object> paramArrayVal = new ArrayList<Object>();
                if (MapUtils.isNotEmpty(requestParamMap)) {
                    for (Map.Entry<String, Object> str : requestParamMap.entrySet()) {
                        paramArray.add(str.getKey());
                        paramArrayVal.add(str.getValue());
                    }
                }
                //获取档案基本信息返回
//                response = "{\"Code\":0,\"Message\":\"获取成功！！\",\"Data\":\"<DataSet><xs:schema id=\\\"NewDataSet\\\" xmlns:xs=\\\"http:\\/\\/www.w3.org\\/2001\\/XMLSchema\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"><xs:element name=\\\"NewDataSet\\\" msdata:IsDataSet=\\\"true\\\" msdata:UseCurrentLocale=\\\"true\\\"><xs:complexType><xs:choice minOccurs=\\\"0\\\" maxOccurs=\\\"unbounded\\\"><xs:element name=\\\"ds\\\"><xs:complexType><xs:sequence><xs:element name=\\\"IMGID\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"PAGES\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"受理编号\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"产权证号\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"不动产权证号\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"产权人\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"坐落\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"业务类型\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><\\/xs:sequence><\\/xs:complexType><\\/xs:element><\\/xs:choice><\\/xs:complexType><\\/xs:element><\\/xs:schema><diffgr:diffgram xmlns:diffgr=\\\"urn:schemas-microsoft-com:xml-diffgram-v1\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"><NewDataSet><ds diffgr:id=\\\"ds1\\\" msdata:rowOrder=\\\"0\\\"><IMGID>3221722<\\/IMGID><PAGES>28<\\/PAGES><受理编号>W202009251558<\\/受理编号><不动产权证号>皖(2020)合肥市不动产权第5123024号<\\/不动产权证号><产权人>许丽霞<\\/产权人><坐落>包河区花园大道989号宝文财富中心2幢办1114<\\/坐落><业务类型>商品房买卖<\\/业务类型><\\/ds><\\/NewDataSet><\\/diffgr:diffgram><\\/DataSet>\"}";
                //获取分层分户图返回
//                response = "{\"Code\":0,\"Message\":\"获取数据成功！\",\"Data\":\"<DataSet><xs:schema id=\\\"NewDataSet\\\" xmlns:xs=\\\"http:\\/\\/www.w3.org\\/2001\\/XMLSchema\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"><xs:element name=\\\"NewDataSet\\\" msdata:IsDataSet=\\\"true\\\" msdata:UseCurrentLocale=\\\"true\\\"><xs:complexType><xs:choice minOccurs=\\\"0\\\" maxOccurs=\\\"unbounded\\\"><xs:element name=\\\"ds\\\"><xs:complexType><xs:sequence><xs:element name=\\\"RNAME\\\" type=\\\"xs:string\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"FROMPAGE\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/><xs:element name=\\\"ENDPAGE\\\" type=\\\"xs:decimal\\\" minOccurs=\\\"0\\\"\\/><\\/xs:sequence><\\/xs:complexType><\\/xs:element><\\/xs:choice><\\/xs:complexType><\\/xs:element><\\/xs:schema><diffgr:diffgram xmlns:diffgr=\\\"urn:schemas-microsoft-com:xml-diffgram-v1\\\" xmlns:msdata=\\\"urn:schemas-microsoft-com:xml-msdata\\\"><NewDataSet><ds diffgr:id=\\\"ds1\\\" msdata:rowOrder=\\\"0\\\"><RNAME>房屋平面图<\\/RNAME><FROMPAGE>32<\\/FROMPAGE><ENDPAGE>32<\\/ENDPAGE><\\/ds><ds diffgr:id=\\\"ds2\\\" msdata:rowOrder=\\\"1\\\"><RNAME>房屋分户图<\\/RNAME><FROMPAGE>64<\\/FROMPAGE><ENDPAGE>64<\\/ENDPAGE><\\/ds><ds diffgr:id=\\\"ds3\\\" msdata:rowOrder=\\\"2\\\"><RNAME>房屋分户图<\\/RNAME><FROMPAGE>96<\\/FROMPAGE><ENDPAGE>96<\\/ENDPAGE><\\/ds><ds diffgr:id=\\\"ds4\\\" msdata:rowOrder=\\\"3\\\"><RNAME>房屋分户图<\\/RNAME><FROMPAGE>128<\\/FROMPAGE><ENDPAGE>128<\\/ENDPAGE><\\/ds><ds diffgr:id=\\\"ds5\\\" msdata:rowOrder=\\\"4\\\"><RNAME>房屋分户图<\\/RNAME><FROMPAGE>160<\\/FROMPAGE><ENDPAGE>160<\\/ENDPAGE><\\/ds><ds diffgr:id=\\\"ds6\\\" msdata:rowOrder=\\\"5\\\"><RNAME>房屋分户图<\\/RNAME><FROMPAGE>192<\\/FROMPAGE><ENDPAGE>192<\\/ENDPAGE><\\/ds><\\/NewDataSet><\\/diffgr:diffgram><\\/DataSet>\"}";
                //获取单页信息的
//                response = "";
                response = WebServiceUtils.callWebService(prop.getUrl(), prop.getUri(), prop.getMethodName(), paramArray.toArray(new String[paramArray.size()]), paramArrayVal.toArray());
                if ("issaled".equals(prop.getMethodName())) {
                    JSONObject responseJson = new JSONObject(4);
                    responseJson.put("isSaled", response);
                    response = responseJson.toJSONString();
                }
                super.setResponseBody(response, builder);
            } catch (Exception e) {
                LOGGER.error("axisWebService 请求异常：url:{},reqMap:{}", prop.getUrl(), requestParamMap, e);
                throw new AppException("axisWebService 请求异常");
            } finally {
                // 记录 请求日志
                try {
                    AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                    auditEventBO.setRequest(JSONObject.toJSONString(requestParamMap));
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                } catch (Exception e) {
                    LOGGER.error("记录请求日志异常", e);
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
    public String dealWithResponse(String response) {
        return response;
    }

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
