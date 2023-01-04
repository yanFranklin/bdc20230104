package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.exchange.config.filter.RequestWrapper;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.nantong.jsyh.*;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GrdacxService;
import cn.gtmap.realestate.exchange.util.BodyUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 海门-建设银行查询房屋信息
 *
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version v1.0, 2020/7/18
 */
//@OpenController(consumer = "建设银行查询房屋信息")
@Controller
@RequestMapping("/realestate-exchange/rest/v1.0/jsyh")
@Api(tags = "建设银行查询房屋信息")
public class JsyhFwxxRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsyhFwxxRestController.class);

    @Autowired
    private BodyUtil bodyUtil;

    @Autowired
    private GrdacxService grdacxService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private CommonService commonService;

    /**
     * 银行查询房屋信息接口
     *
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>  HttpServletRequest request
     */
    @OpenApi(apiDescription = "查询房屋信息接口", apiName = "", openLog = false)
    @PostMapping(value = "/fwxx")
    @DsfLog(logEventName = "查询房屋信息接口", dsfFlag = "JSYH", requester = "JSYH", responser = "BDC")
    @ApiImplicitParams({@ApiImplicitParam(name = "param", value = "请求参数体", required = true, dataType = "String", paramType = "body")})
    public void getFwxx(HttpServletRequest request, HttpServletResponse response) {
        String xml1 = "";
        xml1 = request.getParameter("requestXml");
        if (StringUtils.isBlank(xml1)) {
            Map<String, String[]> map = request.getParameterMap();
            StringBuffer xmlBuf = new StringBuffer();
            for (Map.Entry<String, String[]> m : map.entrySet()) {
                LOGGER.info("返回的报文 key=" + m.getKey());
                LOGGER.info("返回的报文 value=" + m.getKey() + "=" + m.getValue()[0]);

                //打印出来的key=<?xml version     value[0]="1.0" encoding="GBK"?><Advpay><PubInfo><ReturnCode>0000</ReturnCode>**其他节点略**</PubInfo</Advpay>
                /*xmlBuf.append(m.getKey());
                xmlBuf.append("=");*/
                xmlBuf.append(m.getKey() + "=" + m.getValue()[0]);
            }
            xml1 = xmlBuf.toString();
           /* xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<message>\n" +
                    "  <head>\n" +
                    "    <LH_VERSION>100</LH_VERSION>\n" +
                    "    <LH_TYPE>0200</LH_TYPE>\n" +
                    "    <LH_REQ_SYS_NO>A303501</LH_REQ_SYS_NO>\n" +
                    "    <LH_RES_SYS_NO/>\n" +
                    "    <LH_FORWARD_FLAG>1</LH_FORWARD_FLAG>\n" +
                    "    <LH_REQUEST_DATE>20220505</LH_REQUEST_DATE>\n" +
                    "    <LH_REQUEST_TIME>184140</LH_REQUEST_TIME>\n" +
                    "    <LH_REQUEST_FLOW_NO>f8e60b53-2ccc-4a75-b</LH_REQUEST_FLOW_NO>\n" +
                    "    <LH_TRAN_CD>A3038HY01</LH_TRAN_CD>\n" +
                    "    <LH_AGENT_NO/>\n" +
                    "    <LH_LEASING_COMPANY/>\n" +
                    "    <LH_PROV_NO/>\n" +
                    "    <LH_CITY_NO>320684</LH_CITY_NO>\n" +
                    "    <LH_AREA_NO/>\n" +
                    "    <LH_ORG_USER_ID/>\n" +
                    "    <LH_TERM_INF/>\n" +
                    "    <LH_ORG_NODE_ID/>\n" +
                    "    <LH_DES_NODE_ID/>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <XZQHDM>320684</XZQHDM>\n" +
                    "    <CQZLX>02</CQZLX>\n" +
                    "    <CQZBH>0037427</CQZBH>\n" +
                    "    <CQRZJLX>01</CQRZJLX>\n" +
                    "    <CQRZJHM>320625197105272173</CQRZJHM>\n" +
                    "    <CQRXM>陆汉荣</CQRXM>\n" +
                    "    <ZJLX>01</ZJLX>\n" +
                    "    <ZJHM/>\n" +
                    "    <XINGMING/>\n" +
                    "    <Usr_ID/>\n" +
                    "    <PARM_NAME/>\n" +
                    "    <PARM_VALUE/>\n" +
                    "    <LHCOM>\n" +
                    "      <RYLX1/>\n" +
                    "      <MINGCHENG>俞春婧</MINGCHENG>\n" +
                    "      <ZJLX/>\n" +
                    "      <ZJHM>320684199602276166</ZJHM>\n" +
                    "      <SJHM>15151816589</SJHM>\n" +
                    "      <QYBH/>\n" +
                    "      <RYLX2/>\n" +
                    "      <RYBH/>\n" +
                    "      <Usr_ID>c039f2a5-e9f6-48a8-bc68-7324295fea27</Usr_ID>\n" +
                    "    </LHCOM>\n" +
                    "  </body>\n" +
                    "</message>";*/
        }
        if (StringUtils.isBlank(xml1)) {
            try {
                RequestWrapper requestWrapper = new RequestWrapper(request);
                xml1 = requestWrapper.getRequestBody();
                LOGGER.info("建行参数，{}", xml1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String requestXml = xml1;
        LOGGER.info("建行参数1,{}", requestXml);

        XmlEntityConvertUtil xmlEntityConvertUtil = new XmlEntityConvertUtil();
        String xml = "";
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        RequestMessageModel requestMessageModel = new RequestMessageModel();
        RequestBodyModel paramBody1 = bodyUtil.parseXmlObj(request, RequestBodyModel.class);

        try {
            requestMessageModel =
                    (RequestMessageModel) xmlEntityConvertUtil.xmlToEntity1(RequestMessageModel.class, new ByteArrayInputStream(requestXml.getBytes(CharEncoding.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("requestMessageModel.toString()-=-={}", requestMessageModel.toString());
        LOGGER.info("requestXml-=-={}", requestXml);
        JsyhFwqsxxRequestDTo jsyhFwqsxxRequestDTo = new JsyhFwqsxxRequestDTo();

        LOGGER.info("-=-=-=-=", jsyhFwqsxxRequestDTo.toString());

        //请求参数封装
        JsyhFwqsxxResponseDTo jsyhFwqsxxResponseDTo = new JsyhFwqsxxResponseDTo();
        JsyhFwqsxxRequestDTo paramBody = new JsyhFwqsxxRequestDTo();
        dozerBeanMapper.map(requestMessageModel.getRequestBodyModel(), paramBody, "jsyh");
        ResponseMessageModel responseMessageModel = new ResponseMessageModel();
        ResponseBodyModel bodyModel = new ResponseBodyModel();
        List<ResponseGroupModel> responseGroupModelList = new ArrayList<>();
        LOGGER.info("封装解析后：{}", paramBody.toString());
        if (paramBody != null && paramBody.getBody() != null) {
            LOGGER.info("参数为：{}", paramBody.getBody().toString());
            //当产权证类型是证书时，产权证编号长度要超过7位，类型是坐落的话不进行验证
            if (StringUtils.equals(paramBody.getBody().getCqzlx(), "03") || StringUtils.length(paramBody.getBody().getCqzbh()) > 6) {
                jsyhFwqsxxResponseDTo = exchangeBeanRequestService.request("jsyh_fwxx", paramBody, JsyhFwqsxxResponseDTo.class);
                if (jsyhFwqsxxResponseDTo != null && jsyhFwqsxxResponseDTo.getBody() != null && CollectionUtils.isNotEmpty(jsyhFwqsxxResponseDTo.getBody().getGroup())) {
                    LOGGER.info("查到数据!!!{}", jsyhFwqsxxResponseDTo.toString());
                    List<JsyhFwqsxxGroupResponseDTo> list = jsyhFwqsxxResponseDTo.getBody().getGroup();
                    for (JsyhFwqsxxGroupResponseDTo jsyhFwqsxxGroupResponseDTo : list) {
                        if (paramBody.getBody() != null) {
                            jsyhFwqsxxGroupResponseDTo.setCqzlx(paramBody.getBody().getCqzlx());
                            //产权证号是03的话 产权证编号要传坐落
                            if (StringUtils.equals(paramBody.getBody().getCqzlx(), "03")) {
                                jsyhFwqsxxGroupResponseDTo.setCqzbh(jsyhFwqsxxGroupResponseDTo.getFwzl());
                            }
                            //产权人证件类型类型如果传了直接读传的
                            if (StringUtils.isNotBlank(paramBody.getBody().getCqrzjlx())) {
                                jsyhFwqsxxGroupResponseDTo.setCqrzjlx(jsyhFwqsxxGroupResponseDTo.getCqrzjlx());
                            }
                            if (StringUtils.indexOf(jsyhFwqsxxGroupResponseDTo.getCqrzjhm(), ",") >= 0) {
                                String[] cqrzjhms = StringUtils.split(jsyhFwqsxxGroupResponseDTo.getCqrzjhm(), ",");
                                String[] cqrmcs = StringUtils.split(jsyhFwqsxxGroupResponseDTo.getCqrxm(), ",");
                                StringBuilder gyrxx = new StringBuilder("");
                                for (int i = 0; i < cqrzjhms.length; i++) {
                                    if (StringUtils.equals(cqrzjhms[i], paramBody.getBody().getCqrzjhm())) {
                                        jsyhFwqsxxGroupResponseDTo.setCqrzjhm(cqrzjhms[i]);
                                        if (cqrmcs != null && i < cqrmcs.length) {
                                            jsyhFwqsxxGroupResponseDTo.setCqrxm(cqrmcs[i]);
                                        }
                                    } else if (cqrmcs != null && i < cqrmcs.length) {
                                        gyrxx.append(cqrmcs[i]).append(",");
                                    }
                                }
                                if (StringUtils.isNotBlank(gyrxx)) {
                                    jsyhFwqsxxGroupResponseDTo.setSfgy("1");
                                    jsyhFwqsxxGroupResponseDTo.setGyrxx(StringUtils.join(cqrmcs, ","));
                                } else {
                                    jsyhFwqsxxGroupResponseDTo.setSfgy("0");
                                }
                            }
                        }
                    }
                    responseMessageModel = setHeadSuccess("0000000000", "成功");
                } else {
                    jsyhFwqsxxResponseDTo = setJsyhFwqsxxResponseDTo("ZBLHY0001", "查询无对应的房屋信息");
                    responseMessageModel = setHead("ZBLHY0001", "查询无对应的房屋信息");
                }
            } else {
                jsyhFwqsxxResponseDTo = setJsyhFwqsxxResponseDTo("ZBLHY0000", "产权证号长度不足");
                responseMessageModel = setHead("ZBLHY0000", "产权证号长度不足");
            }
        } else {
            jsyhFwqsxxResponseDTo = setJsyhFwqsxxResponseDTo("500", "参数格式不正确，无法解析");
            responseMessageModel = setHead("500", "参数格式不正确，无法解析");
        }
        LOGGER.info("结果到这了");

        if (null != jsyhFwqsxxResponseDTo && null != jsyhFwqsxxResponseDTo.getBody()) {
            LOGGER.info("都不为空！");
            if (CollectionUtils.isNotEmpty(jsyhFwqsxxResponseDTo.getBody().getGroup())) {

                for (JsyhFwqsxxGroupResponseDTo jsyhFwqsxxGroupResponseDTo : jsyhFwqsxxResponseDTo.getBody().getGroup()) {
                    ResponseGroupModel groupModel = new ResponseGroupModel();
                    groupModel.setCqrxm(jsyhFwqsxxGroupResponseDTo.getCqrxm());
                    groupModel.setCqrzjhm(jsyhFwqsxxGroupResponseDTo.getCqrzjhm());
                    groupModel.setCqrzjlx(jsyhFwqsxxGroupResponseDTo.getCqrzjlx());
                    groupModel.setCqzbh(jsyhFwqsxxGroupResponseDTo.getCqzbh());
                    groupModel.setCqzlx(jsyhFwqsxxGroupResponseDTo.getCqzlx());
                    groupModel.setFwbh(jsyhFwqsxxGroupResponseDTo.getFwbh());
                    String dsf = dzFwyt(jsyhFwqsxxGroupResponseDTo);
//                    String dsf = commonService.bdcDmToDsfZd("DSF_YH","yh",jsyhFwqsxxGroupResponseDTo.getFwyt());
                    groupModel.setFwyt(dsf);
                    groupModel.setXzqhdm(jsyhFwqsxxGroupResponseDTo.getXzqhdm());
                    groupModel.setHyzt(jsyhFwqsxxGroupResponseDTo.getHyzt());
                    groupModel.setHysj(jsyhFwqsxxGroupResponseDTo.getHysj());
                    groupModel.setSfczcf(jsyhFwqsxxGroupResponseDTo.getSfczcf());
                    groupModel.setSfyydj(jsyhFwqsxxGroupResponseDTo.getSfyydj());
                    groupModel.setSfdy(jsyhFwqsxxGroupResponseDTo.getSfdy());
                    groupModel.setFwzl(jsyhFwqsxxGroupResponseDTo.getFwzl());
                    groupModel.setScjzmj(String.valueOf(jsyhFwqsxxGroupResponseDTo.getScjzmj()));
                    groupModel.setScftmj(String.valueOf(jsyhFwqsxxGroupResponseDTo.getScftmj()));
                    groupModel.setSctnmj(null == jsyhFwqsxxGroupResponseDTo.getSctnmj() ? "0" : String.valueOf(jsyhFwqsxxGroupResponseDTo.getSctnmj()));
                    groupModel.setSfgy(jsyhFwqsxxGroupResponseDTo.getSfgy());
                    groupModel.setGyrxx(jsyhFwqsxxGroupResponseDTo.getGyrxx());
                    groupModel.setJzjg(jsyhFwqsxxGroupResponseDTo.getJzjg());
                    groupModel.setParmName(jsyhFwqsxxGroupResponseDTo.getParmName());
                    groupModel.setParmValue(jsyhFwqsxxGroupResponseDTo.getParmValue());
                    responseGroupModelList.add(groupModel);
                }
            }

        }
        bodyModel.setResponseGroupModelList(responseGroupModelList);
        responseMessageModel.setResponseBodyModel(bodyModel);
//        dozerBeanMapper.map(jsyhFwqsxxResponseDTo.getBody(), responseMessageModel.getResponseBodyModel(), "jsyh1");
        LOGGER.info("responseMessageModelxml：{}", responseMessageModel.toString());
        xml = xmlEntityConvertUtil.entityToXmlYh(responseMessageModel);

        if (StringUtils.isNotBlank(xml)) {
            xml = xml.replaceAll("\\s*xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
            xml = xml.replaceAll("xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance", "");
            if (StringUtils.isNotBlank(xml)) {
                LOGGER.info("结果xml：{}", xml);

            }
            OutputStream outputStream;
            try {
                outputStream = response.getOutputStream();
                outputStream.write(xml.getBytes(CharEncoding.UTF_8));
                outputStream.close();
            } catch (IOException e) {
            }
        }
//        return XmlEntityConvertUtil.xstreamToXml(jsyhFwqsxxResponseDTo);
    }

    private String dzFwyt(JsyhFwqsxxGroupResponseDTo jsyhFwqsxxGroupResponseDTo) {
        String fwyt = "";
        switch (jsyhFwqsxxGroupResponseDTo.getFwyt()) {
            case "100101":
                fwyt = "01";
                break;
            case "100102":
                fwyt = "01";
                break;
            case "100109":
                fwyt = "01";
                break;
            case "100110":
                fwyt = "01";
                break;
            case "100114":
                fwyt = "01";
                break;
            case "201501":
                fwyt = "01";
                break;
            case "11":
                fwyt = "01";
                break;
            default:
                fwyt = "02";
                break;
        }

        return fwyt;
    }

    private JsyhFwqsxxResponseDTo setJsyhFwqsxxResponseDTo(String responseStatus, String responseMsg) {
        JsyhFwqsxxResponseDTo jsyhFwqsxxResponseDTo;
        jsyhFwqsxxResponseDTo = new JsyhFwqsxxResponseDTo();
        JsyhFwqsxxHeadResponseDTo head = new JsyhFwqsxxHeadResponseDTo();
        head.setLhResponseStatus("01");
        head.setLhResponseCode(responseStatus);
        head.setLhResponseMsg(responseMsg);
        jsyhFwqsxxResponseDTo.setHead(head);
        return jsyhFwqsxxResponseDTo;
    }

    private ResponseMessageModel setHead(String responseStatus, String responseMsg) {
        ResponseMessageModel responseMessageModel;
        responseMessageModel = new ResponseMessageModel();
        ResponseHeadModel head = new ResponseHeadModel();
        head.setLhResponseStatus("01");
        head.setLhResponseCode(responseStatus);
        head.setLhResponseMsg(responseMsg);
        responseMessageModel.setResponseHeadModel(head);
        return responseMessageModel;
    }

    private ResponseMessageModel setHeadSuccess(String responseStatus, String responseMsg) {
        ResponseMessageModel responseMessageModel;
        responseMessageModel = new ResponseMessageModel();
        ResponseHeadModel head = new ResponseHeadModel();
        head.setLhResponseStatus("00");
        head.setLhResponseCode(responseStatus);
        head.setLhResponseMsg(responseMsg);
        responseMessageModel.setResponseHeadModel(head);
        return responseMessageModel;
    }

}
