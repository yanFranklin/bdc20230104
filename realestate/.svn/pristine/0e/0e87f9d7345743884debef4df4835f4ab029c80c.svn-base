package cn.gtmap.realestate.exchange.service.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
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
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.gtmap.realestate.common.core.dto.accept.BdcSfjfblResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sfxx.DzpjResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sfxx.JfqrResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sfxx.PjzfResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.fssr.JfblDTO;
import cn.gtmap.realestate.exchange.core.dto.fssr.JfblDataDTO;
import cn.gtmap.realestate.exchange.core.dto.fssr.JfblDataMainDTO;
import cn.gtmap.realestate.exchange.core.dto.fssr.JfblResponseDTO;

/**
 * <Description> <br>
 *
 * @author xz<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2022/5/24 17:07 <br>
 * @see cn.gtmap.realestate.exchange.service.impl <br>
 * @since V1.0<br>
 */
@Service(value = "bdcSfServiceImpl")
public class BdcSfServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSfServiceImpl.class);

    private static final String RESPONSE_PREFIX = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";

    @Value("${fssr.jfbl.url:}")
    private String url;
    @Value("${fssr.soTimeout:30000}")
    private Integer soTimeout;
    @Value("${fssr.jfbl.method:nontaxPayhandleSync}")
    private String jfblMethod;

    @Value("${fssr.jfqr.method:nontaxPayaffirmSync}")
    private String jfqrMethod;

    @Value("${fssr.pjzf.method:nontaxEincancelSync}")
    private String pjzfMethod;

    @Value("${fssr.dzpj.method:nontaxGeteinvoiceSync}")
    private String dzpjMethod;

    /**
     * @param jfblDTO
     * @return CommonResponse
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @params bdcSfxxDTO
     * @description 缴费办理
     */
    public BdcSfjfblResponseDTO jfbl(JfblDTO jfblDTO) {
        LOGGER.info("缴费办理接收参数：{}", jfblDTO);
        // 处理财政缴款识别码
        String hold1 = Optional.ofNullable(jfblDTO)
                .map(JfblDTO::getDATA)
                .map(JfblDataDTO::getMAIN)
                .map(JfblDataMainDTO::getHOLD1)
                .orElse(null);
        if (StringUtils.isBlank(hold1)) {
            throw new AppException("缴费办理未配置财政缴款识别码");
        }

        String[] hold1Arr = hold1.split(CommonConstantUtils.ZF_YW_DH);
        String finalHold1 = hold1Arr[0];
        Integer jklx = jfblDTO.getJklx();
        if (Objects.nonNull(jklx) && CommonConstantUtils.SF_S_DM.equals(jklx)) {
            finalHold1 = hold1Arr[1];
        }
        jfblDTO.getDATA().getMAIN().setHOLD1(finalHold1);

        String paramXmlStr = "";
        Map<String, Object> paramMap = new HashMap<>();
        try {
            // base64加密,指定编码
            paramXmlStr = new String(Base64.encodeBase64(convertObjectToXml(jfblDTO).getBytes(Charset.forName("GB2312"))), Charset.forName("GB2312"));
            paramMap.put("jfData", paramXmlStr);
            paramMap.put("dwdm", jfblDTO.getDwdm());
            paramMap.put("md5String", jfblDTO.getMd5String());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("缴费办理webservice接收参数：{}", paramMap);
        String response = sendCzWebService(url, jfblMethod, paramMap);
        JfblResponseDTO jfblResponseDTO = convertResponseXml(response, new JfblResponseDTO());
        LOGGER.info("缴费办理返回：{}", jfblResponseDTO);
        if (jfblResponseDTO != null
                && "00000".equals(jfblResponseDTO.getCODE())
                && null != jfblResponseDTO.getDATA()) {
            BdcSfjfblResponseDTO bdcSfjfblResponseDTO = new BdcSfjfblResponseDTO();
            bdcSfjfblResponseDTO.setCode("00000");
            bdcSfjfblResponseDTO.setMsg(jfblResponseDTO.getMSG());
            bdcSfjfblResponseDTO.setBussinessId(jfblResponseDTO.getDATA().getBUSINESS_ID());
            bdcSfjfblResponseDTO.setJfsbm(jfblResponseDTO.getDATA().getBUSINESSNUMBER());
            bdcSfjfblResponseDTO.setJfsewmurl(jfblResponseDTO.getDATA().getURL());
            return bdcSfjfblResponseDTO;
        }
        return null;
    }

    /**
     * @param
     * @param paramMap
     * @return cn.gtmap.realestate.common.core.dto.exchange.sfxx.JfqrResponseDTO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/9 8:45
     * @description 缴费确认
     **/
    public JfqrResponseDTO jfqr(Map<String, Object> paramMap) {
        LOGGER.info("缴费确认接收参数：{}", paramMap);
        String response = sendCzWebService(url, jfqrMethod, paramMap);
        JfqrResponseDTO jfqrResponseDTO = convertResponseXml(response, new JfqrResponseDTO());
        LOGGER.info("缴费确认参数返回：{}", jfqrResponseDTO);
        return jfqrResponseDTO;
    }

    /**
     * @param
     * @param paramMap
     * @return cn.gtmap.realestate.common.core.dto.exchange.sfxx.PjzfResponseDTO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/9 8:50
     * @description 票据作废
     **/
    public PjzfResponseDTO pjzf(Map<String, Object> paramMap) {
        LOGGER.info("票据作废接收参数：{}", paramMap);
        String response = sendCzWebService(url, pjzfMethod, paramMap);
        PjzfResponseDTO pjzfResponseDTO = convertResponseXml(response, new PjzfResponseDTO());
        LOGGER.info("票据作废返回：{}", pjzfResponseDTO);
        return pjzfResponseDTO;
    }

    /**
     * @param
     * @param paramMap
     * @return cn.gtmap.realestate.common.core.dto.exchange.sfxx.DzpjResponseDTO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/9 8:50
     * @description 电子票据
     **/
    public DzpjResponseDTO dzpj(Map<String, Object> paramMap) {
        LOGGER.info("电子票据接收参数：{}", paramMap);
        String response = sendCzWebService(url, dzpjMethod, paramMap);
        DzpjResponseDTO dzpjResponseDTO = convertResponseXml(response, new DzpjResponseDTO());
        LOGGER.info("电子票据返回：{}", dzpjResponseDTO);
        return dzpjResponseDTO;
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @params [ysqTsDTO]
     * @description 将预申请推送对象转为xml
     */
    private <T> String convertObjectToXml(T obj) {
        String xmlStr;
        try {
            StringWriter out = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GB2312");
            marshaller.marshal(obj, out);
            xmlStr = out.toString().replaceAll("\\s*standalone=\"yes\"", "");
        } catch (JAXBException e) {
            throw new AppException("推送信息转Xml失败:" + e.getMessage());
        }
        return xmlStr;
    }

    /**
     * @param
     * @param response
     * @param obj
     * @return T
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/9 9:03
     * @description 处理xml转对象
     **/
    private <T> T convertResponseXml(Object response, T obj) {
        if (response != null) {
            String responseStr = (String) response;
            responseStr = responseStr.replaceAll("&","&amp;");
            if (responseStr.startsWith(RESPONSE_PREFIX)) {
                responseStr = responseStr.substring(responseStr.indexOf(">") + 1);
            }
            LOGGER.info("responseStr{}", responseStr);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader sr = new StringReader(responseStr);
                return (T) jaxbUnmarshaller.unmarshal(sr);
            } catch (JAXBException e) {
                LOGGER.error("响应解析失败！异常信息：{1}", e);
                throw new AppException(e.getMessage());
            }
        }
        return null;
    }

    /**
     * @param
     * @param url 请求地址
     * @param method 方法
     * @param requestParamMap 请求参数
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/9 8:54
     * @description 发送财政webservice请求
     **/
    private String sendCzWebService(String url, String method, Map<String, Object> requestParamMap) {
        List<String> paramNameList = new ArrayList<>();
        List<Object> paramValList= new ArrayList<>();
        if (MapUtils.isNotEmpty(requestParamMap)) {
            for (Map.Entry<String, Object> str : requestParamMap.entrySet()) {
                paramNameList.add(str.getKey());
                paramValList.add(str.getValue());
            }
        }
        String[] paramNameArray = paramNameList.toArray(new String[0]);
        Object[] paramValArray = paramValList.toArray();
        String invoke = null;
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);//设置请求wsdl
            call.setOperationName(new QName("", method)); //设置请求命名空s间及具体方法
            // 设置入参
            for (int i = 0; i<paramNameArray.length; i++) {
                QName qName = XMLType.XSD_STRING;
                if (i < paramValArray.length) {
                    if (paramValArray[i] instanceof Integer) {
                        qName=XMLType.XSD_INTEGER;
                    }
                }
                call.addParameter(paramNameArray[i], qName, ParameterMode.IN);
            }
            call.setUseSOAPAction(true);  //设置使用soap操作
            call.setTimeout(soTimeout); // 设置超时时间
            call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
            invoke = (String)call.invoke(paramValArray);
        }
        catch (RemoteException | ServiceException e) {
            LOGGER.error(method + "发送财政webservice请求远程调用失败", e);
        }
        // 解密并编码
        String result = StringUtils.isNotBlank(invoke) ? new String(Base64.decodeBase64(invoke.getBytes(StandardCharsets.UTF_8)),Charset.forName("GB2312")) : null;
        LOGGER.info("方法{}请求财政返回{}", method, result);
        return result;
    }

}
