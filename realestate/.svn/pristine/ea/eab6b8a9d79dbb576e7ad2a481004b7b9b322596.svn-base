package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.request.FcjyskxxListQO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.response.FcjyskxxListbean;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.response.NtSwWsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.response.QswsfjxxListbean;
import cn.gtmap.realestate.common.util.XmlEntityCommonConvertUtil;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.NtSwWsxxWebservicePropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.service.sw.NKlsjkClientService;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nkstar.lsjkclient.bean.nklsjkResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-09-24
 * @description 南通 请求税务 webservice方式
 */
@Service(value = "ntSwWsxxWebservice")
public class NtSwWebserviceRequestServiceImpl extends InterfaceRequestService<NtSwWsxxWebservicePropBO> {

    @Autowired
    NKlsjkClientService cl;
    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        NtSwWsxxWebservicePropBO propBO = super.getRequestPropFromBuilder(builder);
        FcjyskxxListQO requestBody = JSONObject.parseObject(JSON.toJSONString(builder.getRequestBody()), FcjyskxxListQO.class);
//        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(requestBody);

        if (StringUtils.isNoneBlank(propBO.getClientid(), propBO.getUrl())) {
            String response = "";
            Exception logE = null;
            String result = "";
            JSONObject data = new JSONObject();

            try {
                String dataXml = XmlUtils.getXmlStrByObjectWithOutEncoding(requestBody, FcjyskxxListQO.class);
                result = dataXml.substring(55);
                result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TAXBIZML>" + result + "</TAXBIZML>";
                byte[] bytes = result.getBytes();
                String s2 = new String(bytes, "UTF-8");

                LOGGER.info("南通请求税务完税信息发票接口入参：{}", s2);
                LOGGER.info("南通请求税务完税信息发票接口入参：{}", System.getProperty("file.encoding"));
                nklsjkResponse res = cl.getResponse("QSWSXXHQ", s2);
//                nklsjkResponse res = new nklsjkResponse();
//                res.setResponseCode(0);
//                res.setResponseData("<TAXBIZML><FCJYSKXXLIST><HTBH>通售买卖202259692</HTBH><FHXX>查询成功</FHXX><FHM>0</FHM><FWUUID /><JYUUID /><SJBH>通售买卖202259692</SJBH></FCJYSKXXLIST><QSWSFJXXLIST><FJLX>1001</FJLX><FJID>0eeed20f-cd2d-43c4-a2e5-715886dee454</FJID><WJSJ>{\"YXWJ\":\"JVBERi0xGCg==\"}</WJSJ><WJRES>1</WJRES></QSWSFJXXLIST></TAXBIZML>");
//                res.setResponseData("<TAXBIZML><FCJYSKXXLIST><HTBH>通售买卖202259692</HTBH><FHXX>该纳税人编号及合同编号查询不到数据</FHXX><FHM>1</FHM><JYUUID /><FWUUID /><SJBH>通售买卖202259692</SJBH></FCJYSKXXLIST></TAXBIZML>");
                LOGGER.info("请求NtSwwsxx返回码：" + res.getResponseCode());
                LOGGER.info("请求NtSwwsxx返回信息：" + res.getResponseMessage());
                LOGGER.info("请求NtSwwsxx返回数据：" + res.getResponseData());
//                res.setResponseCode(200);
//               res.setResponseData("<?XML VERSION=\"1.0\" ENCODING=\"UTF-8\"?><TAXBIZML><FCJYSKXXLIST><FHM>返回码</FHM><FHXX>返回信息</FHXX><SJBH>收件编号</SJBH><HTBH>合同编号</HTBH><JYUUID>交易编号</JYUUID><FWUUID>房屋编号</FWUUID></FCJYSKXXLIST><QSWSFJXXLIST><FJLX>附件类型</FJLX><FJID>附件ID</FJID><WJSJ>文件数据</WJSJ></QSWSFJXXLIST></TAXBIZML>");
                if (Constants.RESPONSE_SUCCESS_0.equals(res.getResponseCode())
                        || Constants.RESPONSE_SUCCESS_200.equals(res.getResponseCode())) {
                    String resData = res.getResponseData().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
                    LOGGER.info("请求NtSWwsxx,返回信息,截取后：{}", resData);
                    String json = XmlEntityCommonConvertUtil.xmlToJson(res.getResponseData());
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    List<QswsfjxxListbean> qswsfjxxList = new ArrayList<>();

                    FcjyskxxListbean fcjyskxxListbean = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("FCJYSKXXLIST"), FcjyskxxListbean.class);
                    //fhm,0是成功，1是失败
                    if ("0".equals(fcjyskxxListbean.getFhm())) {
                        QswsfjxxListbean qswsfjxxListbean = JSONObject.parseObject(jsonObject.getJSONObject("TAXBIZML").getString("QSWSFJXXLIST"), QswsfjxxListbean.class);
                        if (null != qswsfjxxListbean) {
                            JSONObject jsonObject1 = JSONObject.parseObject(qswsfjxxListbean.getWjsj());
                            qswsfjxxListbean.setWjsj(jsonObject1.getString("YXWJ"));
                        }
                        qswsfjxxList.add(qswsfjxxListbean);
                    }
                    NtSwWsxxDTO ntSwWsxxDTO = new NtSwWsxxDTO();
                    ntSwWsxxDTO.setFcjyskxxList(fcjyskxxListbean);
                    ntSwWsxxDTO.setQswsfjxxList(qswsfjxxList);
                    LOGGER.info("请求NtSWwsxx,返回信息,转换后：{}", ntSwWsxxDTO.toString());
                    data.put("code", 200);
                    data.put("data", ntSwWsxxDTO);

                } else {
                    data.put("code", res.getResponseCode());
                    data.put("data", res.getResponseMessage());
                }
                response = JSONObject.toJSONString(data);
                super.setResponseBody(response, builder);

            } catch (Exception e) {
                logE = e;
                LOGGER.error("NtSwwsxx请求异常", e);
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
    public String dealWithResponse(String response) {
        return response;
    }


}
