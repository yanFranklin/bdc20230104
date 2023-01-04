package cn.gtmap.realestate.exchange.service.impl.inf.standard.court;

import cn.gtmap.realestate.common.core.dto.exchange.court.*;
import cn.gtmap.realestate.common.core.dto.exchange.court.cx.CourtCxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.court.cx.CourtCxjgDTO;
import cn.gtmap.realestate.common.core.dto.exchange.court.cx.CourtFeedCxBdcQLDTO;
import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzDTO;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.exchange.service.inf.standard.court.BdcCourtKzJhService;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSON;
import org.apache.axis.AxisFault;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ParameterMode;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;

@Service
public class BdcCourtKzJhSoapServiceImpl implements BdcCourtKzJhService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCourtKzJhSoapServiceImpl.class);

    @Value("${CourtKz.url:}")
    private String url;


    @Value("${CourtKz.attr.encode:true}")
    private Boolean attrEncode;


    /**
     * 调用该接口返回法院请求的司法控制信息
     *
     * @param usermarker
     */
    @Override
    public CourtKzDTO kzBdcQL(CourtKzUserDTO usermarker) {
        LOGGER.info("法院请求{},参数:{}", "kzBdcQL", JSON.toJSONString(usermarker));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("usermarker", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(usermarker).getBytes(StandardCharsets.UTF_8)));
        String response = sendWebServiceReq(url, "kzBdcQL", requestParamMap);
        CourtKzDTO courtKzDTO = XmlEntityConvertUtil.toBean(response, CourtKzDTO.class);
        LOGGER.info("法院请求{},请求转换对象结果:{}", "kzBdcQL", JSON.toJSONString(courtKzDTO));
        //属性解码
        convertAttrToBase64(courtKzDTO, "decode");
        LOGGER.info("法院请求{},请求转换结果:{}", "kzBdcQL", JSON.toJSONString(courtKzDTO));
        return courtKzDTO;
    }

    /**
     * 调用该接口将司法控制结果信息反馈请求单位
     *
     * @param usermarker
     * @param feedbackinfo
     * @return
     */
    @Override
    public CourtKzjgDTO feedkzBdcQL(CourtKzUserDTO usermarker, CourtFeedKzBdcQLDTO feedbackinfo) {
        LOGGER.info("法院请求{},参数:{}", "feedkzBdcQL", JSON.toJSONString(feedbackinfo));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("usermarker", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(usermarker).getBytes(StandardCharsets.UTF_8)));
        //转换属性
        convertAttrToBase64(feedbackinfo, "encode");
        requestParamMap.put("feedbackinfo", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(feedbackinfo).getBytes(StandardCharsets.UTF_8)));
        String response = sendWebServiceReq(url, "feedkzBdcQL", requestParamMap);
        //转换为实体的对象
        CourtKzjgDTO courtKzjgDTO = XmlEntityConvertUtil.toBean(response, CourtKzjgDTO.class);
        //
        convertAttrToBase64(courtKzjgDTO, "decode");
        LOGGER.info("法院请求{},返回:{}", "feedkzBdcQL", JSON.toJSONString(courtKzjgDTO));
        return courtKzjgDTO;
    }

    /**
     * 调用该接口将司法控制结果信息反馈请求单位
     *
     * @param usermarker
     * @param feedbackinfo
     * @return
     */
    @Override
    public CourtKzjgDTO feedkzhzBdcQL(CourtKzUserDTO usermarker, CourtFeedKzBdcQLDTO feedbackinfo) {
        return null;
    }

    /**
     * 调用该接口获取请求单位各控制申请涉及的相关文书信息
     *
     * @param usermarker
     * @param courtKzdhDTO
     * @return
     */
    @Override
    public CourtKzBdcWsInfoDTO kzwsInfo(CourtKzUserDTO usermarker, CourtKzdhDTO courtKzdhDTO) {
        LOGGER.info("法院请求{},参数:{}", "kzwsInfo", JSON.toJSONString(courtKzdhDTO));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("usermarker", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(usermarker).getBytes(StandardCharsets.UTF_8)));
        //转换属性
        convertAttrToBase64(courtKzdhDTO, "encode");
        requestParamMap.put("wsbhinfo", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(courtKzdhDTO).getBytes(StandardCharsets.UTF_8)));
        String response = sendWebServiceReq(url, "kzwsInfo", requestParamMap);
        //转换为实体的对象
        CourtKzBdcWsInfoDTO courtKzBdcWsInfoDTO = XmlEntityConvertUtil.toBean(response, CourtKzBdcWsInfoDTO.class);
        //
        convertAttrToBase64(courtKzBdcWsInfoDTO, "decode");
        return courtKzBdcWsInfoDTO;
    }

    /**
     * 调用该接口获取请求单位各控制申请涉及的法官证件信息
     *
     * @param usermarker
     * @param courtKzdhDTO
     * @return
     */
    @Override
    public CourtKzBdcZjInfoDTO zjInfo(CourtKzUserDTO usermarker, CourtKzdhDTO courtKzdhDTO) {
        LOGGER.info("法院请求{},参数:{}", "zjInfo", JSON.toJSONString(courtKzdhDTO));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("usermarker", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(usermarker).getBytes(StandardCharsets.UTF_8)));
        //转换属性
        convertAttrToBase64(courtKzdhDTO, "encode");
        requestParamMap.put("wsbhinfo", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(courtKzdhDTO).getBytes(StandardCharsets.UTF_8)));
        String response = sendWebServiceReq(url, "zjInfo", requestParamMap);
        //转换为实体的对象
        CourtKzBdcZjInfoDTO courtKzBdcZjInfoDTO = XmlEntityConvertUtil.toBean(response, CourtKzBdcZjInfoDTO.class);
        //
        convertAttrToBase64(courtKzBdcZjInfoDTO, "decode");
        return courtKzBdcZjInfoDTO;
    }


    @Override
    public CourtCxDTO cxBdcQL(CourtKzUserDTO usermarker) {
        LOGGER.info("法院请求{},参数:{}", "cxBdcQL", JSON.toJSONString(usermarker));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("usermarker", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(usermarker).getBytes(StandardCharsets.UTF_8)));
        String response = sendWebServiceReq(url, "cxBdcQL", requestParamMap);
        LOGGER.info("法院请求{},返回:{}", "cxBdcQL", response);
        //转成对象
        CourtCxDTO courtKzDTO = XmlEntityConvertUtil.toBean(response, CourtCxDTO.class);
        LOGGER.info("法院请求{},xml转换后的对象为:{}", "cxBdcQL", JSON.toJSONString(courtKzDTO));
        //属性解码
        convertAttrToBase64(courtKzDTO, "decode");
        LOGGER.info("法院请求{},属性解码后的对象为:{}", "cxBdcQL", JSON.toJSONString(courtKzDTO));
        return courtKzDTO;
    }

    /**
     * 调用该接口将司法控制结果信息反馈请求单位
     *
     * @param usermarker
     * @param feedbackinfo
     * @return
     */
    @Override
    public CourtCxjgDTO feedCxBdcQL(CourtKzUserDTO usermarker, CourtFeedCxBdcQLDTO feedbackinfo) {
        LOGGER.info("法院请求{},参数:{}", "feedCxBdcQL", JSON.toJSONString(feedbackinfo));
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("usermarker", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(usermarker).getBytes(StandardCharsets.UTF_8)));
        //转换属性
        convertAttrToBase64(feedbackinfo, "encode");
        requestParamMap.put("feedbackinfo", Base64Utils.encodeByteToBase64Str(XmlEntityConvertUtil.convertObjectToXml(feedbackinfo).getBytes(StandardCharsets.UTF_8)));
        String response = sendWebServiceReq(url, "feedBdcQL", requestParamMap);
        //转换为实体的对象
        CourtCxjgDTO courtCxjgDTO = XmlEntityConvertUtil.toBean(response, CourtCxjgDTO.class);
        //转换属性
        convertAttrToBase64(courtCxjgDTO, "decode");
        return courtCxjgDTO;
    }


    /**
     * 将所有的属性都转换成base64,或者将属性base64值解码
     * !!!!!! 基础类型只能有字符串
     *
     * @param o
     * @return
     */
    Object convertAttrToBase64(Object o, String encodeType) {
        if(!attrEncode){
            return o;
        }
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(o);
                if (Objects.isNull(value)) {
                    continue;
                }
                String type = field.getGenericType().toString();
                if (type.equals("class java.lang.String")) {
                    String stringValue = (String) value;
                    if (encodeType.equals("encode")) {
                        field.set(o, Base64Utils.encodeByteToBase64Str(stringValue.getBytes(StandardCharsets.UTF_8)));
                    } else {
                        field.set(o, new String(Base64Utils.decodeBase64StrToByte(stringValue)));
                    }
                } else if (value instanceof List) {
                    List listValue = (List) value;
                    for (Object item : listValue) {
                        convertAttrToBase64(item, encodeType);
                    }
                } else {
                    convertAttrToBase64(value, encodeType);
                }
            }
        } catch (Exception e) {

        }
        return o;
    }

    /**
     * @param
     * @param url             请求地址
     * @param method          方法
     * @param requestParamMap 请求参数
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/9 8:54
     * @description 发送webservice请求
     **/
    private String sendWebServiceReq(String url, String method, Map<String, Object> requestParamMap) {
        LOGGER.info("方法{}请求{}", method, JSON.toJSONString(requestParamMap));
        List<String> paramNameList = new ArrayList<>();
        List<Object> paramValList = new ArrayList<>();
        if (MapUtils.isNotEmpty(requestParamMap)) {
            for (Map.Entry<String, Object> str : requestParamMap.entrySet()) {
                paramNameList.add(str.getKey());
                paramValList.add(str.getValue());
            }
        }
        String[] paramNameArray = paramNameList.toArray(new String[0]);
        Object[] paramValArray = paramValList.toArray();
        //String invoke = testResponse(method);
        String invoke = "";
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        Call call = null;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);//设置请求wsdl
            call.setOperationName(new QName("http://ip.service.webService.ckw.tdh/", method)); //设置请求命名空s间及具体方法
            // 设置入参
            for (int i = 0; i < paramNameArray.length; i++) {
                QName qName = XMLType.XSD_STRING;
                if (i < paramValArray.length) {
                    if (paramValArray[i] instanceof Integer) {
                        qName = XMLType.XSD_INTEGER;
                    }
                }
                call.addParameter(paramNameArray[i], qName, ParameterMode.IN);
            }
            call.setUseSOAPAction(true);
            //设置使用soap操作
//            call.setTimeout(soTimeout); // 设置超时时间
            call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
            invoke = (String) call.invoke(paramValArray);
            String soapPartAsString = call.getMessageContext().getRequestMessage().getSOAPPartAsString();
            LOGGER.error(method + "发送webservice请求报文:{}", soapPartAsString);
        } catch (Exception e) {
            String soapPartAsString = null;
            try {
                soapPartAsString = call.getResponseMessage().getSOAPPartAsString();
                LOGGER.error(method + "发送webservice返回报文:{}", soapPartAsString);
                soapPartAsString = call.getMessageContext().getRequestMessage().getSOAPPartAsString();
                LOGGER.error(method + "发送webservice请求报文:{}", soapPartAsString);
            } catch (AxisFault ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            LOGGER.error(method + "发送webservice请求远程调用失败", e);
        }
        // 解密并编码
        String result = StringUtils.isNotBlank(invoke) ? new String(Base64.decodeBase64(invoke.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8) : null;
        LOGGER.info("方法{}请求返回{}", method, result);
        return result;
    }

    public String testResponse(String method) {
        if ("cxBdcQL".equals(method)) {
            return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPE1lc3NhZ2UgRXJyb3JNU0c9IuWmguaenOayoeaciemUmeivr++8jOWImeaXoOivpee7k+eCueS/oeaBr++8jOWmguaenOaciemUmeivr++8jOWImeS4uuWFt+S9k+eahOS4reaWh+mUmeivr+S/oeaBr+aPj+i/sO+8jOS8muS9nOS4uuaPkOekuuS/oeaBr+WxleeOsOWcqOWuouaIt+erryI+CiAgICA8SGVhZD4KICAgICAgICA8Q1JFQVRFVElNRSBuYW1lPSLnlJ/miJDml7bpl7QiPjIwMTYvMDYvMDIgMDk6MTE6MjQ8L0NSRUFURVRJTUU+CiAgICAgICAgPERJR0lUQUxTSUdOIG5hbWU9IuaVsOWtl+etvuWQjSI+PC9ESUdJVEFMU0lHTj4KICAgIDwvSGVhZD4KICAgIDxEYXRhPgogICAgICAgIDxCRENDWFFRTElTVCBuYW1lPSLkuI3liqjkuqfmn6Xor6Lor7fmsYLliJfooagiPgogICAgICAgICAgICA8QkRDQ1hRUT4KICAgICAgICAgICAgICAgIDxDWFFRREggbmFtZT0i5p+l6K+i6K+35rGC5Y2V5Y+3Ij4yMDExMTEyODMyMDAwMDEwMDAwMjwvQ1hRUURIPgogICAgICAgICAgICAgICAgPFhaIG5hbWU9IuaAp+i0qCI+MTwvWFo+CiAgICAgICAgICAgICAgICA8WE0gbmFtZT0i6KKr5p+l6K+i5Lq65aeT5ZCNIj4yMjI8L1hNPgogICAgICAgICAgICAgICAgPEdKIG5hbWU9IuWbveWutiI+5Lit5Zu9PC9HSj4KICAgICAgICAgICAgICAgIDxaSlpMIG5hbWU9IuivgeS7tuenjeexuyI+MTwvWkpaTD4KICAgICAgICAgICAgICAgIDxaSkggbmFtZT0i6KKr5p+l6K+i5Lq66K+B5Lu2L+e7hOe7h+acuuaehOWPt+eggSI+MjMyMzwvWkpIPgogICAgICAgICAgICAgICAgPEZaSkcgbmFtZT0i5Y+R6K+B5py65YWz5omA5Zyo5ZywIj7msZ/oi4/ljZfkuqw8L0ZaSkc+CiAgICAgICAgICAgICAgICA8RllNQyBuYW1lPSLms5XpmaLlkI3np7AiPljluILkuK3nuqfkurrmsJHms5XpmaI8L0ZZTUM+CiAgICAgICAgICAgICAgICA8Q0JSIG5hbWU9IuaJv+WKnuazleWumCI+5p2O5ZubPC9DQlI+CiAgICAgICAgICAgICAgICA8QUggbmFtZT0i5omn6KGM5qGI5Y+3Ij7vvIgyMDEx77yJWOaJp+Wtl+esrDAwMDA15Y+3PC9BSD4KICAgICAgICAgICAgICAgIDxHWlpCSCBuYW1lPSLmib/lip7ms5Xlrpjlt6XkvZzor4HnvJblj7ciPlhBMTIzNDU8L0daWkJIPgogICAgICAgICAgICAgICAgPEdXWkJIIG5hbWU9IuaJv+WKnuazleWumOWFrOWKoeivgee8luWPtyI+MzcwMTAxMDwvR1daQkg+CiAgICAgICAgICAgICAgICA8Q0tIIG5hbWU9IuafpeivouazleW+i+aWh+S5puWQjeensCI+77yIMjAxNe+8ieaIv+afpeWtl+esrDAwMDAx5Y+3PC9DS0g+CiAgICAgICAgICAgICAgICA8V1NCSCBuYW1lPSLmlofkuabnvJblj7ciPkZDRkMwMDAwMDEyMDE0MDgxNTAwMDE8L1dTQkg+CiAgICAgICAgICAgIDwvQkRDQ1hRUT4KICAgICAgICA8L0JEQ0NYUVFMSVNUPgogICAgPC9EYXRhPgo8L01lc3NhZ2U+";
        }
        //feedBdcQL
        if ("feedBdcQL".equals(method)) {
            return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiID8+CjxSRVNVTFRMSVNUPgogICAgPGVyck1zZz7lpoLmnpzmsqHmnInplJnor6/vvIzliJnml6Dor6Xnu5Pngrnkv6Hmga/vvIzlpoLmnpzmnInplJnor6/vvIzliJnkuLrlhbfkvZPnmoTkuK3mlofplJnor6/kv6Hmga/mj4/ov7DvvIzkvJrkvZzkuLrmj5DnpLrkv6Hmga/lsZXnjrDlnKjlrqLmiLfnq6/vvJvlpoLmmL7npLrnlKjmiLflkI3lr4bnoIHpqozor4HplJnor6/miJbmlbDmja7lupPlvILluLgKPC9lcnJNc2c+CiAgICA8Q1hKR0xJU1Q+CiAgICAgICAgPEpHPgogICAgICAgICAgICA8Q1hRUURIPjIwMTExMDIxMzIwMDAwMTAwMDAxPC9DWFFRREg+CiAgICAgICAgICAgIDxSRVNVTFQ+c3VjY2VzczwvUkVTVUxUPgogICAgICAgICAgICA8TVNHPjwvTVNHPgogICAgICAgIDwvSkc+CiAgICAgICAgPEpHPgogICAgICAgICAgICA8Q1hRUURIPjIwMTExMDIxMzIwMDAwMTAwMDAyPC9DWFFRREg+CiAgICAgICAgICAgIDxSRVNVTFQ+ZmFpbDwvUkVTVUxUPgogICAgICAgICAgICA8TVNHPuiLpeaIkOWKn+WImeepuu+8jOWksei0peaYvuekuuWFt+S9k+S4reaWh+aPj+i/sDwvTVNHPgogICAgICAgIDwvSkc+CiAgICA8L0NYSkdMSVNUPgo8L1JFU1VMVExJU1Q+";
        }

        //kzBdcQL
        if ("kzBdcQL".equals(method)) {
            //查封
            return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPE1lc3NhZ2UgRXJyb3JNU0c9IuWmguaenOayoeaciemUmeivr++8jOWImeaXoOivpee7k+eCueS/oeaBr++8jOWmguaenOaciemUmeivr++8jOWImeS4uuWFt+S9k+eahOS4reaWh+mUmeivr+S/oeaBr+aPj+i/sO+8jOS8muS9nOS4uuaPkOekuuS/oeaBr+WxleeOsOWcqOWuouaIt+erryI+CiAgICA8SGVhZD4KICAgICAgICA8Q1JFQVRFVElNRSBuYW1lPSLnlJ/miJDml7bpl7QiPjIwMTYvMDYvMDIgMDk6MTE6MjQ8L0NSRUFURVRJTUU+CiAgICAgICAgPERJR0lUQUxTSUdOIG5hbWU9IuaVsOWtl+etvuWQjSI+PC9ESUdJVEFMU0lHTj4KICAgIDwvSGVhZD4KICAgIDxEYXRhPgogICAgICAgIDxCRENLWlFRTElTVCBuYW1lPSLkuI3liqjkuqfmjqfliLbor7fmsYLliJfooagiPgogICAgICAgICAgICA8QkRDS1pRUT4KICAgICAgICAgICAgICAgIDxDWFFRREggbmFtZT0i5o6n5Yi26K+35rGC5Y2V5Y+3Ij4yMDExMTEyODMyMDAwMDEwMDAwMjwvQ1hRUURIPgogICAgICAgICAgICAgICAgPFhaIG5hbWU9IuaAp+i0qCI+MTwvWFo+CiAgICAgICAgICAgICAgICA8WE0gbmFtZT0i6KKr5p+l6K+i5Lq65aeT5ZCNIj4yMjI8L1hNPgogICAgICAgICAgICAgICAgPEdKIG5hbWU9IuWbveWutiI+5Lit5Zu9PC9HSj4KICAgICAgICAgICAgICAgIDxaSlpMIG5hbWU9IuivgeS7tuenjeexuyI+MTwvWkpaTD4KICAgICAgICAgICAgICAgIDxaSkggbmFtZT0i6KKr5p+l6K+i5Lq66K+B5Lu2L+e7hOe7h+acuuaehOWPt+eggSI+MjMyMzwvWkpIPgogICAgICAgICAgICAgICAgPEZaSkcgbmFtZT0i5Y+R6K+B5py65YWz5omA5Zyo5ZywIj7msZ/oi4/ljZfkuqw8L0ZaSkc+CiAgICAgICAgICAgICAgICA8RllNQyBuYW1lPSLms5XpmaLlkI3np7AiPljluILkuK3nuqfkurrmsJHms5XpmaI8L0ZZTUM+CiAgICAgICAgICAgICAgICA8Q0JSIG5hbWU9IuaJv+WKnuazleWumCI+5p2O5ZubPC9DQlI+CiAgICAgICAgICAgICAgICA8QUggbmFtZT0i5omn6KGM5qGI5Y+3Ij7vvIgyMDEx77yJWOaJp+Wtl+esrDAwMDA15Y+3PC9BSD4KICAgICAgICAgICAgICAgIDxDS0ggbmFtZT0i5o6n5Yi26YCa55+l5Lmm5ZCN56ewIj7vvIgyMDE177yJ5oi/5p+l5a2X56ysMDAwMDHlj7c8L0NLSD4KICAgICAgICAgICAgICAgIDxCRENRTExJU1QgbmFtZT0i5LiN5Yqo5Lqn5p2D5Yip5o6n5Yi25L+h5oGv5YiX6KGoIj4KICAgICAgICAgICAgICAgICAgICA8QkRDUUw+CiAgICAgICAgICAgICAgICAgICAgICAgIDxCRENRTExYIG5hbWU9IuS4jeWKqOS6p+adg+WIqeexu+WeiyI+MTwvQkRDUUxMWD4KICAgICAgICAgICAgICAgICAgICAgICAgPEJEQ0RZSCBuYW1lPSLkuI3liqjkuqfljZXlhYPlj7ciPjMyMDQwMjAwMjAwMUdCMDA1NzNGNDczNzAwMDE8L0JEQ0RZSD4KICAgICAgICAgICAgICAgICAgICAgICAgPFpMIG5hbWU9IuWdkOiQvSI+5LiJ5Liw6LevNeWPtzwvWkw+CiAgICAgICAgICAgICAgICAgICAgICAgIDxCRENRWkggbmFtZT0i5LiN5Yqo5Lqn5p2D6K+B5Y+3Ij7nmpYoMjAyMinonIDlsbHljLrkuI3liqjkuqfor4HmmI7nrKwxMDAwMDU05Y+3PC9CRENRWkg+CiAgICAgICAgICAgICAgICAgICAgICAgIDxLWkNTIG5hbWU9IuaOp+WItuaOquaWvSI+MTE8L0taQ1M+CiAgICAgICAgICAgICAgICAgICAgICAgIDxDS1dIIG5hbWU9IuijgeWumuS5puaWh+WPtyI+MTIzPC9DS1dIPgogICAgICAgICAgICAgICAgICAgICAgICA8S1NSUSBuYW1lPSLnlLPor7fmjqfliLblvIDlp4vml7bpl7QiPjIwMjAvMDIvMTE8L0tTUlE+CiAgICAgICAgICAgICAgICAgICAgICAgIDxKU1JRIG5hbWU9IueUs+ivt+aOp+WItue7k+adn+aXtumXtCI+MjAyMC8wMi8xMjwvSlNSUT4KICAgICAgICAgICAgICAgICAgICAgICAgPFlESkFIIG5hbWU9IuWOn+WGu+e7k+ahiOWPtyI+MTIzMTIzMTI8L1lESkFIPgogICAgICAgICAgICAgICAgICAgICAgICA8WURKREggbmFtZT0i5Y6f5o6n5Yi26K+35rGC5Y2V5Y+3Ij4yMjIyMjIyMjI8L1lESkRIPgogICAgICAgICAgICAgICAgICAgICAgICA8WVdIIG5hbWU9IuS4muWKoeWPtyI+MzMzMzMzMzM8L1lXSD4KICAgICAgICAgICAgICAgICAgICAgICAgPFlDRllXSCBuYW1lPSLljp/mn6XlsIHkuJrliqHlj7ciPjIyMjIyMjIyMjIyMjI8L1lDRllXSD4KICAgICAgICAgICAgICAgICAgICAgICAgPERKSkcgbmFtZT0i55m76K6w5py65p6EIj4yMjIyMjIyMjIyPC9ESkpHPgogICAgICAgICAgICAgICAgICAgICAgICA8R0hEWExJU1QgbmFtZT0i6L+H5oi35a+56LGh5YiX6KGoIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxHSERYIG5hbWU9Iui/h+aIt+WvueixoSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPERYWEggbmFtZT0i5a+56LGh5bqP5Y+3Ij4xPC9EWFhIPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFhNIG5hbWU9IuWvueixoeWnk+WQjSI+MTwvRFhYTT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8RFhaSkxYIG5hbWU9IuWvueixoeivgeS7tuexu+WeiyI+MTwvRFhaSkxYPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFNGWkhNIG5hbWU9IuWvueixoeivgeS7tuWPt+eggSI+MTwvRFhTRlpITT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvR0hEWD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxHSERYIG5hbWU9Iui/h+aIt+WvueixoSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPERYWEggbmFtZT0i5a+56LGh5bqP5Y+3Ij4yPC9EWFhIPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFhNIG5hbWU9IuWvueixoeWnk+WQjSI+MjwvRFhYTT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8RFhaSkxYIG5hbWU9IuWvueixoeivgeS7tuexu+WeiyI+MjwvRFhaSkxYPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFNGWkhNIG5hbWU9IuWvueixoeivgeS7tuWPt+eggSI+MjwvRFhTRlpITT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvR0hEWD4KICAgICAgICAgICAgICAgICAgICAgICAgPC9HSERYTElTVD4KICAgICAgICAgICAgICAgICAgICA8L0JEQ1FMPgogICAgICAgICAgICAgICAgPC9CRENRTExJU1Q+CiAgICAgICAgICAgIDwvQkRDS1pRUT4KICAgICAgICA8L0JEQ0taUVFMSVNUPgogICAgPC9EYXRhPgo8L01lc3NhZ2U+";
            //解封
            //return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPE1lc3NhZ2UgRXJyb3JNU0c9IuWmguaenOayoeaciemUmeivr++8jOWImeaXoOivpee7k+eCueS/oeaBr++8jOWmguaenOaciemUmeivr++8jOWImeS4uuWFt+S9k+eahOS4reaWh+mUmeivr+S/oeaBr+aPj+i/sO+8jOS8muS9nOS4uuaPkOekuuS/oeaBr+WxleeOsOWcqOWuouaIt+erryI+CiAgICA8SGVhZD4KICAgICAgICA8Q1JFQVRFVElNRSBuYW1lPSLnlJ/miJDml7bpl7QiPjIwMTYvMDYvMDIgMDk6MTE6MjQ8L0NSRUFURVRJTUU+CiAgICAgICAgPERJR0lUQUxTSUdOIG5hbWU9IuaVsOWtl+etvuWQjSI+PC9ESUdJVEFMU0lHTj4KICAgIDwvSGVhZD4KICAgIDxEYXRhPgogICAgICAgIDxCRENLWlFRTElTVCBuYW1lPSLkuI3liqjkuqfmjqfliLbor7fmsYLliJfooagiPgogICAgICAgICAgICA8QkRDS1pRUT4KICAgICAgICAgICAgICAgIDxDWFFRREggbmFtZT0i5o6n5Yi26K+35rGC5Y2V5Y+3Ij4yMDExMTEyODMyMDAwMDEwMDAwMjE8L0NYUVFESD4KICAgICAgICAgICAgICAgIDxYWiBuYW1lPSLmgKfotKgiPjE8L1haPgogICAgICAgICAgICAgICAgPFhNIG5hbWU9Iuiiq+afpeivouS6uuWnk+WQjSI+MjIyPC9YTT4KICAgICAgICAgICAgICAgIDxHSiBuYW1lPSLlm73lrrYiPuS4reWbvTwvR0o+CiAgICAgICAgICAgICAgICA8WkpaTCBuYW1lPSLor4Hku7bnp43nsbsiPjE8L1pKWkw+CiAgICAgICAgICAgICAgICA8WkpIIG5hbWU9Iuiiq+afpeivouS6uuivgeS7ti/nu4Tnu4fmnLrmnoTlj7fnoIEiPjIzMjM8L1pKSD4KICAgICAgICAgICAgICAgIDxGWkpHIG5hbWU9IuWPkeivgeacuuWFs+aJgOWcqOWcsCI+5rGf6IuP5Y2X5LqsPC9GWkpHPgogICAgICAgICAgICAgICAgPEZZTUMgbmFtZT0i5rOV6Zmi5ZCN56ewIj5Y5biC5Lit57qn5Lq65rCR5rOV6ZmiPC9GWU1DPgogICAgICAgICAgICAgICAgPENCUiBuYW1lPSLmib/lip7ms5XlrpgiPuadjuWbmzwvQ0JSPgogICAgICAgICAgICAgICAgPEFIIG5hbWU9IuaJp+ihjOahiOWPtyI+77yIMjAxMe+8iVjmiaflrZfnrKwwMDAwNTHlj7c8L0FIPgogICAgICAgICAgICAgICAgPENLSCBuYW1lPSLmjqfliLbpgJrnn6XkuablkI3np7AiPu+8iDIwMTXvvInmiL/mn6XlrZfnrKwwMDAwMeWPtzwvQ0tIPgogICAgICAgICAgICAgICAgPEJEQ1FMTElTVCBuYW1lPSLkuI3liqjkuqfmnYPliKnmjqfliLbkv6Hmga/liJfooagiPgogICAgICAgICAgICAgICAgICAgIDxCRENRTD4KICAgICAgICAgICAgICAgICAgICAgICAgPEJEQ1FMTFggbmFtZT0i5LiN5Yqo5Lqn5p2D5Yip57G75Z6LIj4xPC9CRENRTExYPgogICAgICAgICAgICAgICAgICAgICAgICA8QkRDRFlIIG5hbWU9IuS4jeWKqOS6p+WNleWFg+WPtyI+MzIwNDAyMDAyMDAxR0IwMDU3M0Y0NzM3MDAwMTwvQkRDRFlIPgogICAgICAgICAgICAgICAgICAgICAgICA8WkwgbmFtZT0i5Z2Q6JC9Ij7kuInkuLDot6815Y+3PC9aTD4KICAgICAgICAgICAgICAgICAgICAgICAgPEJEQ1FaSCBuYW1lPSLkuI3liqjkuqfmnYPor4Hlj7ciPuealigyMDIyKeicgOWxseWMuuS4jeWKqOS6p+ivgeaYjuesrDEwMDAwNTTlj7c8L0JEQ1FaSD4KICAgICAgICAgICAgICAgICAgICAgICAgPEtaQ1MgbmFtZT0i5o6n5Yi25o6q5pa9Ij4xNDwvS1pDUz4KICAgICAgICAgICAgICAgICAgICAgICAgPENLV0ggbmFtZT0i6KOB5a6a5Lmm5paH5Y+3Ij4xMjM8L0NLV0g+CiAgICAgICAgICAgICAgICAgICAgICAgIDxLU1JRIG5hbWU9IueUs+ivt+aOp+WItuW8gOWni+aXtumXtCI+MjAyMC8wMi8xMTwvS1NSUT4KICAgICAgICAgICAgICAgICAgICAgICAgPEpTUlEgbmFtZT0i55Sz6K+35o6n5Yi257uT5p2f5pe26Ze0Ij4yMDIwLzAyLzEyPC9KU1JRPgogICAgICAgICAgICAgICAgICAgICAgICA8WURKQUggbmFtZT0i5Y6f5Ya757uT5qGI5Y+3Ij4xMjMxMjMxMjExMTwvWURKQUg+CiAgICAgICAgICAgICAgICAgICAgICAgIDxZREpESCBuYW1lPSLljp/mjqfliLbor7fmsYLljZXlj7ciPjIyMjIyMjIyMjExMTwvWURKREg+CiAgICAgICAgICAgICAgICAgICAgICAgIDxZV0ggbmFtZT0i5Lia5Yqh5Y+3Ij42QjRFMjMwMjJZTjAyMDFDMTExMTE8L1lXSD4KICAgICAgICAgICAgICAgICAgICAgICAgPFlDRllXSCBuYW1lPSLljp/mn6XlsIHkuJrliqHlj7ciPjZCNEkwOTUyNUkxSjUwQkg8L1lDRllXSD4KICAgICAgICAgICAgICAgICAgICAgICAgPERKSkcgbmFtZT0i55m76K6w5py65p6EIj4yMjIyMjIyMjIyPC9ESkpHPgogICAgICAgICAgICAgICAgICAgICAgICA8R0hEWExJU1QgbmFtZT0i6L+H5oi35a+56LGh5YiX6KGoIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxHSERYIG5hbWU9Iui/h+aIt+WvueixoSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPERYWEggbmFtZT0i5a+56LGh5bqP5Y+3Ij4xPC9EWFhIPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFhNIG5hbWU9IuWvueixoeWnk+WQjSI+MTwvRFhYTT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8RFhaSkxYIG5hbWU9IuWvueixoeivgeS7tuexu+WeiyI+MTwvRFhaSkxYPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFNGWkhNIG5hbWU9IuWvueixoeivgeS7tuWPt+eggSI+MTwvRFhTRlpITT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvR0hEWD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxHSERYIG5hbWU9Iui/h+aIt+WvueixoSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPERYWEggbmFtZT0i5a+56LGh5bqP5Y+3Ij4yPC9EWFhIPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFhNIG5hbWU9IuWvueixoeWnk+WQjSI+MjwvRFhYTT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8RFhaSkxYIG5hbWU9IuWvueixoeivgeS7tuexu+WeiyI+MjwvRFhaSkxYPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFNGWkhNIG5hbWU9IuWvueixoeivgeS7tuWPt+eggSI+MjwvRFhTRlpITT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvR0hEWD4KICAgICAgICAgICAgICAgICAgICAgICAgPC9HSERYTElTVD4KICAgICAgICAgICAgICAgICAgICA8L0JEQ1FMPgogICAgICAgICAgICAgICAgPC9CRENRTExJU1Q+CiAgICAgICAgICAgIDwvQkRDS1pRUT4KICAgICAgICA8L0JEQ0taUVFMSVNUPgogICAgPC9EYXRhPgo8L01lc3NhZ2U+";
            //续封
            //return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPE1lc3NhZ2UgRXJyb3JNU0c9IuWmguaenOayoeaciemUmeivr++8jOWImeaXoOivpee7k+eCueS/oeaBr++8jOWmguaenOaciemUmeivr++8jOWImeS4uuWFt+S9k+eahOS4reaWh+mUmeivr+S/oeaBr+aPj+i/sO+8jOS8muS9nOS4uuaPkOekuuS/oeaBr+WxleeOsOWcqOWuouaIt+erryI+CiAgICA8SGVhZD4KICAgICAgICA8Q1JFQVRFVElNRSBuYW1lPSLnlJ/miJDml7bpl7QiPjIwMTYvMDYvMDIgMDk6MTE6MjQ8L0NSRUFURVRJTUU+CiAgICAgICAgPERJR0lUQUxTSUdOIG5hbWU9IuaVsOWtl+etvuWQjSI+PC9ESUdJVEFMU0lHTj4KICAgIDwvSGVhZD4KICAgIDxEYXRhPgogICAgICAgIDxCRENLWlFRTElTVCBuYW1lPSLkuI3liqjkuqfmjqfliLbor7fmsYLliJfooagiPgogICAgICAgICAgICA8QkRDS1pRUT4KICAgICAgICAgICAgICAgIDxDWFFRREggbmFtZT0i5o6n5Yi26K+35rGC5Y2V5Y+3Ij4yMDExMTEyODMyMDAwMDEwMDAwMjE8L0NYUVFESD4KICAgICAgICAgICAgICAgIDxYWiBuYW1lPSLmgKfotKgiPjE8L1haPgogICAgICAgICAgICAgICAgPFhNIG5hbWU9Iuiiq+afpeivouS6uuWnk+WQjSI+MjIyPC9YTT4KICAgICAgICAgICAgICAgIDxHSiBuYW1lPSLlm73lrrYiPuS4reWbvTwvR0o+CiAgICAgICAgICAgICAgICA8WkpaTCBuYW1lPSLor4Hku7bnp43nsbsiPjE8L1pKWkw+CiAgICAgICAgICAgICAgICA8WkpIIG5hbWU9Iuiiq+afpeivouS6uuivgeS7ti/nu4Tnu4fmnLrmnoTlj7fnoIEiPjIzMjM8L1pKSD4KICAgICAgICAgICAgICAgIDxGWkpHIG5hbWU9IuWPkeivgeacuuWFs+aJgOWcqOWcsCI+5rGf6IuP5Y2X5LqsPC9GWkpHPgogICAgICAgICAgICAgICAgPEZZTUMgbmFtZT0i5rOV6Zmi5ZCN56ewIj5Y5biC5Lit57qn5Lq65rCR5rOV6ZmiPC9GWU1DPgogICAgICAgICAgICAgICAgPENCUiBuYW1lPSLmib/lip7ms5XlrpgiPuadjuWbmzwvQ0JSPgogICAgICAgICAgICAgICAgPEFIIG5hbWU9IuaJp+ihjOahiOWPtyI+77yIMjAxMe+8iVjmiaflrZfnrKwwMDAwNTHlj7c8L0FIPgogICAgICAgICAgICAgICAgPENLSCBuYW1lPSLmjqfliLbpgJrnn6XkuablkI3np7AiPu+8iDIwMTXvvInmiL/mn6XlrZfnrKwwMDAwMeWPtzwvQ0tIPgogICAgICAgICAgICAgICAgPEJEQ1FMTElTVCBuYW1lPSLkuI3liqjkuqfmnYPliKnmjqfliLbkv6Hmga/liJfooagiPgogICAgICAgICAgICAgICAgICAgIDxCRENRTD4KICAgICAgICAgICAgICAgICAgICAgICAgPEJEQ1FMTFggbmFtZT0i5LiN5Yqo5Lqn5p2D5Yip57G75Z6LIj4xPC9CRENRTExYPgogICAgICAgICAgICAgICAgICAgICAgICA8QkRDRFlIIG5hbWU9IuS4jeWKqOS6p+WNleWFg+WPtyI+MzIwNDAyMDAyMDAxR0IwMDU3M0Y0NzM3MDAwMTwvQkRDRFlIPgogICAgICAgICAgICAgICAgICAgICAgICA8WkwgbmFtZT0i5Z2Q6JC9Ij7kuInkuLDot6815Y+3PC9aTD4KICAgICAgICAgICAgICAgICAgICAgICAgPEJEQ1FaSCBuYW1lPSLkuI3liqjkuqfmnYPor4Hlj7ciPuealigyMDIyKeicgOWxseWMuuS4jeWKqOS6p+ivgeaYjuesrDEwMDAwNTTlj7c8L0JEQ1FaSD4KICAgICAgICAgICAgICAgICAgICAgICAgPEtaQ1MgbmFtZT0i5o6n5Yi25o6q5pa9Ij4xMjwvS1pDUz4KICAgICAgICAgICAgICAgICAgICAgICAgPENLV0ggbmFtZT0i6KOB5a6a5Lmm5paH5Y+3Ij4xMjM8L0NLV0g+CiAgICAgICAgICAgICAgICAgICAgICAgIDxLU1JRIG5hbWU9IueUs+ivt+aOp+WItuW8gOWni+aXtumXtCI+MjAyMC8wMi8xMTwvS1NSUT4KICAgICAgICAgICAgICAgICAgICAgICAgPEpTUlEgbmFtZT0i55Sz6K+35o6n5Yi257uT5p2f5pe26Ze0Ij4yMDIwLzAyLzEyPC9KU1JRPgogICAgICAgICAgICAgICAgICAgICAgICA8WURKQUggbmFtZT0i5Y6f5Ya757uT5qGI5Y+3Ij4xMjMxMjMxMjExMTwvWURKQUg+CiAgICAgICAgICAgICAgICAgICAgICAgIDxZREpESCBuYW1lPSLljp/mjqfliLbor7fmsYLljZXlj7ciPjIyMjIyMjIyMjExMTwvWURKREg+CiAgICAgICAgICAgICAgICAgICAgICAgIDxZV0ggbmFtZT0i5Lia5Yqh5Y+3Ij42QjRFMjMwMjJZTjAyMDFDMTExMTE8L1lXSD4KICAgICAgICAgICAgICAgICAgICAgICAgPFlDRllXSCBuYW1lPSLljp/mn6XlsIHkuJrliqHlj7ciPjZCNEkwOTUyNUkxSjUwQkg8L1lDRllXSD4KICAgICAgICAgICAgICAgICAgICAgICAgPERKSkcgbmFtZT0i55m76K6w5py65p6EIj4yMjIyMjIyMjIyPC9ESkpHPgogICAgICAgICAgICAgICAgICAgICAgICA8R0hEWExJU1QgbmFtZT0i6L+H5oi35a+56LGh5YiX6KGoIj4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxHSERYIG5hbWU9Iui/h+aIt+WvueixoSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPERYWEggbmFtZT0i5a+56LGh5bqP5Y+3Ij4xPC9EWFhIPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFhNIG5hbWU9IuWvueixoeWnk+WQjSI+MTwvRFhYTT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8RFhaSkxYIG5hbWU9IuWvueixoeivgeS7tuexu+WeiyI+MTwvRFhaSkxYPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFNGWkhNIG5hbWU9IuWvueixoeivgeS7tuWPt+eggSI+MTwvRFhTRlpITT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvR0hEWD4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxHSERYIG5hbWU9Iui/h+aIt+WvueixoSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPERYWEggbmFtZT0i5a+56LGh5bqP5Y+3Ij4yPC9EWFhIPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFhNIG5hbWU9IuWvueixoeWnk+WQjSI+MjwvRFhYTT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8RFhaSkxYIG5hbWU9IuWvueixoeivgeS7tuexu+WeiyI+MjwvRFhaSkxYPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxEWFNGWkhNIG5hbWU9IuWvueixoeivgeS7tuWPt+eggSI+MjwvRFhTRlpITT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvR0hEWD4KICAgICAgICAgICAgICAgICAgICAgICAgPC9HSERYTElTVD4KICAgICAgICAgICAgICAgICAgICA8L0JEQ1FMPgogICAgICAgICAgICAgICAgPC9CRENRTExJU1Q+CiAgICAgICAgICAgIDwvQkRDS1pRUT4KICAgICAgICA8L0JEQ0taUVFMSVNUPgogICAgPC9EYXRhPgo8L01lc3NhZ2U+Cg==";
        }

        //kzwsInfo
        if ("kzwsInfo".equals(method)) {
            return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiID8+CjxXU0xJU1QgbmFtZT0i5paH5Lmm5YiX6KGoIiBFcnJvck1TRz0i5aaC5p6c5rKh5pyJ6ZSZ6K+v77yM5YiZ5peg6K+l57uT54K55L+h5oGv77yM5aaC5p6c5pyJ6ZSZ6K+v77yM5YiZ5Li65YW35L2T55qE5Lit5paH6ZSZ6K+v5L+h5oGv5o+P6L+w77yM5Lya5L2c5Li65o+Q56S65L+h5oGv5bGV546w5Zyo5a6i5oi356uv77yI5aaC6aqM6K+B6ZSZ6K+v44CB5o6n5Yi25Y2V5Y+35LiN5a2Y5Zyo562J77yJIj4KICAgIDxXU0lORk8gbmFtZT0i5paH5Lmm5L+h5oGvIj4KICAgICAgICA8Q1hRUURIIG5hbWU9IuaOp+WItuivt+axguWNleWPtyI+IEEyMjAxNzAyMDgzNTAwMDAyMDAwMDE8L0NYUVFESD4KICAgICAgICA8WEggbmFtZT0i5bqP5Y+3Ij4xPC9YSD4KICAgICAgICA8V0pNQyBuYW1lPSLmlofku7blkI3np7AiPuWNj+WKqeaOp+WItumAmuefpeS5pjwvV0pNQz4KICAgICAgICA8V0pMWCBuYW1lPSLmlofku7bnsbvlnosiPnBkZjwvV0pMWD4KICAgICAgICA8REpSIG5hbWU9IueZu+iusOS6uiI+MTIxMjwvREpSPgogICAgICAgIDxESlJRIG5hbWU9IueZu+iusOaXpeacnyI+MjAxMS8wOS8wMyAwOToxMToyNDwvREpSUT4KICAgICAgICA8V1NOUiBuYW1lPSLmlofkuablhoXlrrkiPjExMTExMTExMTExMTExMTExMTExPC9XU05SPgogICAgPC9XU0lORk8+CjwvV1NMSVNUPg==";
        }
        //zjInfo
        if ("zjInfo".equals(method)) {
            return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiID8+CjxaSkxJU1QgbmFtZT0i6K+B5Lu25YiX6KGoIiAgRXJyb3JNU0c9IuWmguaenOayoeaciemUmeivr++8jOWImeaXoOivpee7k+eCueS/oeaBr++8jOWmguaenOaciemUmeivr++8jOWImeS4uuWFt+S9k+eahOS4reaWh+mUmeivr+S/oeaBr+aPj+i/sO+8jOS8muS9nOS4uuaPkOekuuS/oeaBr+WxleeOsOWcqOWuouaIt+err++8iOWmgumqjOivgemUmeivr+OAgeaOp+WItuWNleWPt+S4jeWtmOWcqOetie+8iSI+CjxaSklORk8gbmFtZT0i6K+B5Lu25L+h5oGvIj4KCTxDWFFRREggbmFtZT0i5o6n5Yi26K+35rGC5Y2V5Y+3Ij4yMDEyMDMyMzMzMDMxMTEwMDAwMzwvQ1hRUURIPgoJPFhIIG5hbWU9IuW6j+WPtyI+MTwvWEg+Cgk8R1paQk0gbmFtZT0i5bel5L2c6K+B57yW5Y+3Ij7lt6XkvZzor4HnvJblj7c8L0daWkJNPgoJPEdaWldKR1MgbmFtZT0i5bel5L2c6K+B5qC85byPIj5qcGc8L0daWldKR1M+Cgk8R1paIG5hbWU9IuW3peS9nOivgSI+MjIyMjIyMjIyMjIyMjIyMjwvR1paPgoJPEdXWkJNIG5hbWU9IuWFrOWKoeivgee8luWPtyI+5YWs5Yqh6K+B57yW5Y+3PC9HV1pCTT4KCTxHV1pXSkdTIG5hbWU9IuWFrOWKoeivgeagvOW8jyI+anBnPC9HV1pXSkdTPgoJPEdXWiBuYW1lPSLlhazliqHor4EiPjExMTExMTExMTExMTExPC9HV1o+CjwvWkpJTkZPPgo8L1pKTElTVD4=";
        }

        //feedkzBdcQL
        if ("feedkzBdcQL".equals(method)) {
            return "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiID8+CjxSRVNVTFRMSVNUPgogICAgPGVyck1zZz7lpoLmnpzmsqHmnInplJnor6/vvIzliJnml6Dor6Xnu5Pngrnkv6Hmga/vvIzlpoLmnpzmnInplJnor6/vvIzliJnkuLrlhbfkvZPnmoTkuK3mlofplJnor6/kv6Hmga/mj4/ov7DvvIzkvJrkvZzkuLrmj5DnpLrkv6Hmga/lsZXnjrDlnKjlrqLmiLfnq6/vvJvlpoLmmL7npLrnlKjmiLflkI3lr4bnoIHpqozor4HplJnor6/miJbmlbDmja7lupPlvILluLg8L2Vyck1zZz4KICAgIDxLWkpHTElTVD4KICAgICAgICA8Skc+CiAgICAgICAgICAgIDxDWFFRREg+MjAxMTEwMjEzMjAwMDAxMDAwMDE8L0NYUVFESD4KICAgICAgICAgICAgPFJFU1VMVD5zdWNjZXNzPC9SRVNVTFQ+CiAgICAgICAgICAgIDxNU0c+PC9NU0c+CiAgICAgICAgPC9KRz4KICAgICAgICA8Skc+CiAgICAgICAgICAgIDxDWFFRREg+MjAxMTEwMjEzMjAwMDAxMDAwMDI8L0NYUVFESD4KICAgICAgICAgICAgPFJFU1VMVD5mYWlsPC9SRVNVTFQ+CiAgICAgICAgICAgIDxNU0c+6Iul5oiQ5Yqf5YiZ56m677yM5aSx6LSl5pi+56S65YW35L2T5Lit5paH5o+P6L+wPC9NU0c+CiAgICAgICAgPC9KRz4KICAgIDwvS1pKR0xJU1Q+CjwvUkVTVUxUTElTVD4=";
        }
        return "";
    }

    /*
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> code = new HashSet<>();
        code.add("soap");
        return code;
    }
}
