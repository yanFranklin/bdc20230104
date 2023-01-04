package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.BbGspApiPropBO;
import cn.gtmap.realestate.exchange.core.dto.bengbu.gspapi.Cszm;
import cn.gtmap.realestate.exchange.core.dto.bengbu.gspapi.SwxxQO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.ursp.client.GsbService;
import com.iflytek.ursp.client.endpoint.ServiceEndPoint;
import com.iflytek.ursp.client.message.GsbDataType;
import com.iflytek.ursp.client.message.GsbResponse;
import com.iflytek.ursp.client.message.GsbValue;
import com.iflytek.ursp.client.session.ServiceSessionFactory;
import com.iflytek.ursp.client.session.Session;
import com.iflytek.ursp.client.transport.ITransport;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-27
 * @description 蚌埠 GSP API 方式调用接口
 */
@Service("bbGspApi")
public class BbGspApiRequestServiceImpl extends InterfaceRequestService<BbGspApiPropBO> {

    @Value("${gspapi.csxx.serviceCode:}")
    private String csxxCode;

    @Value("${gspapi.swxx.serviceCode:}")
    private String swxxCode;

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        BbGspApiPropBO prop = super.getRequestPropFromBuilder(builder);
        if (!CheckParameter.checkAnyParameter(requestBody)) {
            return;
        }

        //指定应用编码/授权码
        ServiceSessionFactory serviceFactory = new ServiceSessionFactory(prop.getYybm(), prop.getSqm());
        //指定URSP地址
        ServiceEndPoint serviceEndpoint = new ServiceEndPoint(prop.getUrl());
        serviceFactory.setServiceEndPoint(serviceEndpoint);

        Session session = null;
        Exception logE = null;
        String response = "";
        try {
            //指定服务编码/版本号/方法名
            session = serviceFactory.openSession(new GsbService(prop.getJkbm(), 1, "requestQuery"));
            // 默认不加密，可以指定加密算法，必须指定安全码
            //session.setEncrypt(Algorithm.NONE);
            Long timeOut = prop.getTimeout();
            if (timeOut == null) {
                timeOut = 30000L;
            }
            session.setProperty(ITransport.TIME_OUT, timeOut);
            //添加调用参数
            session.addParameter("username", GsbDataType.GsbString, prop.getUsername());
            session.addParameter("password", GsbDataType.GsbString, prop.getPassword());
            session.addParameter("serviceCode", GsbDataType.GsbString, prop.getServiceCode());

            Object conditionJson = ((Map) requestBody).get("param");
            String conditionXml = "";
            if (prop.getServiceCode().equals(csxxCode)) {
                Cszm jsonObject = JSONObject.parseObject(conditionJson.toString(), Cszm.class);
                conditionXml = XmlEntityConvertUtil.convertObjectToXml(jsonObject);


            } else if (prop.getServiceCode().equals(swxxCode)) {
                SwxxQO jsonObject = JSONObject.parseObject(conditionJson.toString(), SwxxQO.class);
                conditionXml = XmlEntityConvertUtil.convertObjectToXml(jsonObject);
            } else if (requestBody instanceof Map && !prop.getServiceCode().equals(csxxCode) && !prop.getServiceCode().equals(swxxCode)) {
                // 传参信息   单个参数可以直接转xml，多个字段这样转xml会报错
                if (conditionJson != null && conditionJson instanceof JSONObject) {
                    conditionXml = XmlEntityConvertUtil.jsonToXml((JSONObject) conditionJson);
                }
            }
            session.addParameter("condition", GsbDataType.GsbString, "<condition><item>" + conditionXml + "</item></condition>");
            // 固定结构
            Object requiredItemsJson = ((Map) requestBody).get("item");
            if (requiredItemsJson != null && requiredItemsJson instanceof JSONObject) {
                JSONObject itemJson = new JSONObject();
                itemJson.put("item", requiredItemsJson);
                String requiredItemsXml = XmlEntityConvertUtil.jsonToXml(itemJson);
                session.addParameter("requiredItems", GsbDataType.GsbString, "<requiredItems>" + requiredItemsXml + "</requiredItems>");
            }
            session.addParameter("clientInfo", GsbDataType.GsbString, "<clientInfo><loginName>" + prop.getClientInfo() + "</loginName></clientInfo>");
            GsbResponse gsbResponse = session.request();
           /* GsbResponse gsbResponse = new GsbResponse();
            GsbValue gsbValue = new GsbValue();
            gsbValue.setValue("1");
            gsbResponse.setValue(gsbValue);*/
            //获取返回值
            if (gsbResponse.getValue() != null) {

                response = gsbResponse.getValue().toString();
               /* response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<response><head><username>bbsg</username><password>913fdd2942b4f91ce6dc3d2feb76aa63</password><serviceCode>340300000000_01_0000010385</serviceCode><condition><item><ZJHM>340322198707177416</ZJHM></item></condition><requiredItems><item><HYZK>婚姻状况</HYZK><HYZJZH>婚姻证件字号</HYZJZH><GJ>国籍</GJ><XXLX>信息类型</XXLX><DJRQ>登记日期</DJRQ><ZYZH>证印制号</ZYZH><POGJ>配偶国籍</POGJ><DJYXM>登记员姓名</DJYXM><XB>性别</XB><POZYZH>配偶证印制号</POZYZH><CBJGMC>承办机关名称</CBJGMC><POXB>配偶性别</POXB><CSRQ>出生日期</CSRQ><POXM>配偶姓名</POXM><POCSRQ>配偶出生日期</POCSRQ><POZJHM>配偶证件号码</POZJHM><XM>姓名</XM><POHYZK>配偶婚姻状况</POHYZK><ZJHM>证件号码</ZJHM></item></requiredItems><clientInfo><loginName>zzwk_sgtzyj</loginName></clientInfo></head><body><message>查询成功</message><resultCode>00</resultCode><resultList><result><DJYXM>蚌埠五河-吴胜</DJYXM><POXB>女</POXB><DJRQ>2013.01.04</DJRQ><POHYZK>未婚</POHYZK><HYZJZH>J340322-2013-209</HYZJZH><ZJHM>340322198707177416</ZJHM><ZYZH>0226026923</ZYZH><POZYZH>0226026924</POZYZH><CSRQ>1987.07.17</CSRQ><XM>纪号</XM><HYZK>未婚</HYZK><POZJHM>340322199108050020</POZJHM><CBJGMC>蚌埠市五河县民政局婚姻登记处</CBJGMC><XB>男</XB><POCSRQ>1991.08.05</POCSRQ><POGJ>中国</POGJ><XXLX>结婚信息</XXLX><GJ>中国</GJ><POXM>李航</POXM></result><result><DJYXM>蚌埠五河-姚会玲</DJYXM><POXB>女</POXB><DJRQ>2019.03.08</DJRQ><POHYZK>离婚</POHYZK><HYZJZH>J340322-2019-1693</HYZJZH><ZJHM>340322198707177416</ZJHM><ZYZH>0385500801</ZYZH><POZYZH>0385500802</POZYZH><CSRQ>1987.07.17</CSRQ><XM>纪号</XM><HYZK>离婚</HYZK><POZJHM>340322199108050020</POZJHM><CBJGMC>蚌埠市五河县民政局婚姻登记处</CBJGMC><XB>男</XB><POCSRQ>1991.08.05</POCSRQ><POGJ>中国</POGJ><XXLX>结婚信息</XXLX><GJ>中国</GJ><POXM>李航</POXM></result><result><DJYXM>蚌埠五河-吴胜</DJYXM><POXB>女</POXB><DJRQ>2014.03.17</DJRQ><POHYZK></POHYZK><HYZJZH>L340322-2014-352</HYZJZH><ZJHM>340322198707177416</ZJHM><ZYZH>0042986271</ZYZH><POZYZH>42986272</POZYZH><CSRQ>1987.07.17</CSRQ><XM>纪号</XM><HYZK>未婚</HYZK><POZJHM>340322199108050020</POZJHM><CBJGMC>蚌埠市五河县民政局婚姻登记处</CBJGMC><XB>男</XB><POCSRQ>1991.08.05</POCSRQ><POGJ>中国</POGJ><XXLX>离婚信息</XXLX><GJ>中国</GJ><POXM>李航</POXM></result></resultList></body></response>";
                */
                //出生证明返回
               /* response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><head><username>bbsg</username><password>913fdd2942b4f91ce6dc3d2feb76aa63</password><serviceCode>340300000000_01_0000010385</serviceCode><condition><item><ZJHM>340322198707177416</ZJHM></item></condition><requiredItems><item><HYZK>婚姻状况</HYZK><HYZJZH>婚姻证件字号</HYZJZH><GJ>国籍</GJ><XXLX>信息类型</XXLX><DJRQ>登记日期</DJRQ><ZYZH>证印制号</ZYZH><POGJ>配偶国籍</POGJ><DJYXM>登记员姓名</DJYXM><XB>性别</XB><POZYZH>配偶证印制号</POZYZH><CBJGMC>承办机关名称</CBJGMC><POXB>配偶性别</POXB><CSRQ>出生日期</CSRQ><POXM>配偶姓名</POXM><POCSRQ>配偶出生日期</POCSRQ><POZJHM>配偶证件号码</POZJHM><XM>姓名</XM><POHYZK>配偶婚姻状况</POHYZK><ZJHM>证件号码</ZJHM></item></requiredItems><clientInfo><loginName>zzwk_sgtzyj</loginName></clientInfo></head><body><message>查询成功</message><resultCode>00</resultCode><resultList><result>" +
                        "<CSZH>出生证号</CSZH> <XSEXM>新生儿姓名</XSEXM> <XSEXB>新生儿性别</XSEXB> <XSECSRQ>新生儿出生日期</XSECSRQ> <XSEFQXM>新生儿父亲姓名</XSEFQXM> <XSEFQZJLX>新生儿父亲证件类型</XSEFQZJLX> <XSEFQSFZH>新生儿父亲身份证号</XSEFQSFZH> <XSEMQXM>新生儿母亲姓名</XSEMQXM> <XSEMQZJLX>新生儿母亲证件类型</XSEMQZJLX> <XSEMQSFZH>新生儿母亲身份证号</XSEMQSFZH>" +
                        "</result></resultList></body></response>\n";*/
                //死亡医学证明返回
               /* response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><head><username>bbsg</username><password>913fdd2942b4f91ce6dc3d2feb76aa63</password><serviceCode>340300000000_01_0000010385</serviceCode><condition><item><ZJHM>340322198707177416</ZJHM></item></condition><requiredItems><item><HYZK>婚姻状况</HYZK><HYZJZH>婚姻证件字号</HYZJZH><GJ>国籍</GJ><XXLX>信息类型</XXLX><DJRQ>登记日期</DJRQ><ZYZH>证印制号</ZYZH><POGJ>配偶国籍</POGJ><DJYXM>登记员姓名</DJYXM><XB>性别</XB><POZYZH>配偶证印制号</POZYZH><CBJGMC>承办机关名称</CBJGMC><POXB>配偶性别</POXB><CSRQ>出生日期</CSRQ><POXM>配偶姓名</POXM><POCSRQ>配偶出生日期</POCSRQ><POZJHM>配偶证件号码</POZJHM><XM>姓名</XM><POHYZK>配偶婚姻状况</POHYZK><ZJHM>证件号码</ZJHM></item></requiredItems><clientInfo><loginName>zzwk_sgtzyj</loginName></clientInfo></head><body><message>查询成功</message><resultCode>00</resultCode><resultList><result>" +
                        "<CXRZJHM>查询人证件号码</CXRZJHM> <XM>姓名</XM> <XB>性别</XB> <MZ>民族</MZ> <SWRQ>死亡日期</SWRQ> <SWYY>死亡原因</SWYY>" +
                        "</result></resultList></body></response>\n";*/
                //土地划拨
              /*  response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><head><username>bbsg</username><password>913fdd2942b4f91ce6dc3d2feb76aa63</password><serviceCode>340300000000_01_0000010385</serviceCode><condition><item><ZJHM>340322198707177416</ZJHM></item></condition><requiredItems><item><WJH>文件号</WJH> <WJM>文件名</WJM> <HCF>划出方</HCF> <HRF>划入方</HRF> <ZCMC>资产名称</ZCMC> <SCWZ>所处位置</SCWZ> <BZ>备注</BZ> <FJMC>附件名称</FJMC> <FJ>附件</FJ></item></requiredItems><clientInfo><loginName>zzwk_sgtzyj</loginName></clientInfo></head><body><message>查询成功</message><resultCode>00</resultCode><resultList><result><WJH>文件号</WJH> <WJM>文件名</WJM> <HCF>划出方</HCF> <HRF>划入方</HRF> <ZCMC>资产名称</ZCMC> <SCWZ>所处位置</SCWZ> <BZ>备注</BZ> <FJMC>附件名称</FJMC> <FJ>附件</FJ></result></resultList></body></response>";
                response = "";*/
                LOGGER.info("返回值为：{}", response);
                // 直接转换为 JSON结构
                response = XmlEntityConvertUtil.xmlToJson(response);
                LOGGER.info("转json后返回值为：{}", response);
            }
            // 调用失败打印错误信息
            if (gsbResponse.getErrorMsg() != null) {
                response = JSONObject.toJSONString(gsbResponse);
            }
        } catch (Exception e) {
            logE = e;
            LOGGER.error("bbGspApi 请求异常：serviceCode:{},reqMap:{}", prop.getServiceCode(), JSONObject.toJSONString(requestBody), e);
            throw new AppException("bbGspApi 请求异常");
        } finally {
            if (session != null) {
                session.close();
            }
            try {
                AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                JSONObject logParam = JSONObject.parseObject(JSONObject.toJSONString(requestBody)).getJSONObject("log");
                if (StringUtils.isNotBlank(MapUtils.getString(logParam, "userName"))) {
                    auditEventBO.setUsername(MapUtils.getString(logParam, "userName"));
                }
                if (StringUtils.isNotBlank(MapUtils.getString(logParam, "slbh"))) {
                    auditEventBO.setSlbh(MapUtils.getString(logParam, "slbh"));
                }
                auditEventBO.setRequest(JSONObject.toJSONString(requestBody));
                auditEventBO.setResponse(response);
                auditEventBO.setException(logE);
                super.saveAuditLog(auditEventBO);
            } catch (Exception e) {
                LOGGER.error("记录请求日志异常", e);
            }
        }
        super.setResponseBody(response, builder);
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
