package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.bengbu.zwsdk.BengbuZwSdkAPi;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.zwsdk.HefeiZwSdkApi;
import cn.gtmap.realestate.common.core.dto.exchange.shucheng.zwsdk.ShuchengiZwSdkApi;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.bjxx.SbdjResquestBjxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj.SbdjResquestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj.SbdjResquestSbjbxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestSbjbxxCallInfoDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestSbjbxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.slxx.SbdjResquestSlxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.zwsdk.XuanchengZwSdkAPi;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.exchange.HefeiZwRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0  2020-12-15
 * @description 合肥政务
 */
@RestController
@Api(tags = "合肥政务")
public class HefeiZwRestController implements HefeiZwRestService {

    private static Logger LOGGER = LoggerFactory.getLogger(HefeiZwRestController.class);

    @Value("${data.version:hefei}")
    private String dataVersion;
    @Value("${bengbu.zwip:11.48.64.75}")
    private String bengbuzwsdkIp;
    @Value("${bengbu.zwport:37808}")
    private Integer bengbuzwsdkhttpPort;


    /**
     * @param sbdjfkResquestDTO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 4.2.4申报登记反馈
     */
    @Override
    public SbdjfkResponseDTO sbdjfk(@RequestBody SbdjfkResquestDTO sbdjfkResquestDTO) {
        try {
            SbdjfkResponseDTO responseDTO = new SbdjfkResponseDTO();
            SbdjfkResquestSbjbxxDTO sbjbxxDTO = sbdjfkResquestDTO.getSbjbxxDTO();

            // 创建输出流
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(sbjbxxDTO.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            ApiResponse apiResponse = new ApiResponse();
            if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_HF)) {
                marshaller.marshal(sbjbxxDTO, sw);

                HefeiZwSdkApi hefeiZwSdkApi = new HefeiZwSdkApi();
                LOGGER.info("合肥申报登记反馈请求参数:{}", JSONObject.toJSONString(sw.toString()));
                apiResponse = hefeiZwSdkApi.apasRegInfo(sw.toString(), null, null);
            } else if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_SC)) {
                SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
                callInfoDTO.setCallBackUrl("");
                sbjbxxDTO.setCallInfoDTO(callInfoDTO);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "gb2312");
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        Boolean.FALSE);
                marshaller.marshal(sbjbxxDTO, sw);
                ShuchengiZwSdkApi shuchengiZwSdkApi = new ShuchengiZwSdkApi();
                LOGGER.info("舒城申报登记反馈请求参数:{}", JSONObject.toJSONString(sw.toString()));
                LOGGER.info("舒城申报登记反馈请求xml参数:{}", sw.toString());
                apiResponse = shuchengiZwSdkApi.apasRegInfo(sw.toString(), null, null);
            } else if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_BB)) {
                SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
                callInfoDTO.setCallBackUrl("");
                sbjbxxDTO.setCallInfoDTO(callInfoDTO);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "gb2312");
                marshaller.marshal(sbjbxxDTO, sw);
                BengbuZwSdkAPi bengbuZwSdkApi = new BengbuZwSdkAPi();
                LOGGER.info("蚌埠申报登记反馈请求参数:{}", JSONObject.toJSONString(sw.toString()));
                LOGGER.info("蚌埠申报登记反馈请求xml参数:{}", sw.toString());
                apiResponse = bengbuZwSdkApi.apasRegInfo(sw.toString(), null, null);
            }else if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_XC)) {
                SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
                callInfoDTO.setCallBackUrl("");
                sbjbxxDTO.setCallInfoDTO(callInfoDTO);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "gb2312");
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        Boolean.FALSE);
                marshaller.marshal(sbjbxxDTO, sw);
                XuanchengZwSdkAPi xuanchengZwSdkAPi = new XuanchengZwSdkAPi();
                LOGGER.info("宣城申报登记反馈请求参数:{}", JSONObject.toJSONString(sw.toString()));
                LOGGER.info("宣城申报登记反馈请求xml参数:{}", sw.toString());
                apiResponse = xuanchengZwSdkAPi.apasRegInfo(sw.toString(), null, null);
            }


            // 处理执行结果
            if (apiResponse != null && apiResponse.getBody() != null) {
                LOGGER.info("响应结果:{}", JSONObject.toJSONString(apiResponse));
                String response = new String(apiResponse.getBody(), "UTF-8");
                LOGGER.info("响应数据:{}", response);
                responseDTO = JSONObject.parseObject(response, SbdjfkResponseDTO.class);
            }
            return responseDTO;
//            String response = "{\n" +
//                    "\t\"result\": \"01\",\n" +
//                    "\t\"resultmsg\": \"调用结果描述\",\n" +
//                    "\t\"data\": {\n" +
//                    "\t\t\"errorCode\": \"错误编码\",\n" +
//                    "\t\t\"errorMsg\": \"错误描述\",\n" +
//                    "\t\t\"unid\": \"接口参数唯一标识\",\n" +
//                    "\t\t\"sc\": \"事项编码\",\n" +
//                    "\t\t\"projid\": \"12313\",\n" +
//                    "\t\t\"projpwd\": \"查询密码\"\n" +
//                    "\t}\n" +
//                    "}";
//            responseDTO = JSONObject.parseObject(response,SbdjfkResponseDTO.class);
        } catch (Exception e) {
            LOGGER.error("---异常信息:{},异常堆栈:{},方法入参:{}", e.getMessage(), e, JSONObject.toJSONString(sbdjfkResquestDTO));
            throw new AppException(e.getMessage());
        }
    }

    /**
     * 4.3.1 申报登记
     *
     * @param resquestSbjbxxDTO
     * @return
     * @Date 2022/7/26
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public SbdjfkResponseDTO sbdj(@RequestBody SbdjResquestDTO resquestSbjbxxDTO) {
        try {
            SbdjfkResponseDTO responseDTO = new SbdjfkResponseDTO();
            SbdjResquestSbjbxxDTO sbjbxxDTO = resquestSbjbxxDTO.getSbjbxxDTO();

            // 创建输出流
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(sbjbxxDTO.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
            callInfoDTO.setCallBackUrl("");
            sbjbxxDTO.setCallInfoDTO(callInfoDTO);
            marshaller.marshal(sbjbxxDTO, sw);
            LOGGER.info("申报登记请求xml参数:{}", sw.toString());
            responseDTO = zwsb(Constants.SBDJ, sw.toString());

            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("---异常信息:{},申报登记入参:{}", e.getMessage(), JSONObject.toJSONString(resquestSbjbxxDTO));
            throw new AppException(e.getMessage());
        }
    }

    /**
     * 4.3.3 申报登记_受理或删除信息推送
     *
     * @param slxxDTO@return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public SbdjfkResponseDTO sbdjSlxx(@RequestBody SbdjResquestSlxxDTO slxxDTO) {
        try {
            SbdjfkResponseDTO responseDTO = new SbdjfkResponseDTO();
            if (null == slxxDTO) {
                throw new MissingArgumentException("申报受理信息不能为空！");
            }

            // 创建输出流
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(slxxDTO.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
            callInfoDTO.setCallBackUrl("");
            slxxDTO.setCallInfoDTO(callInfoDTO);
            marshaller.marshal(slxxDTO, sw);
            LOGGER.info("申报登记受理请求xml参数:{}", sw.toString());
            responseDTO = zwsb(Constants.SBDJ_SL, sw.toString());

            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("---异常信息:{},申报登记受理入参:{}", e.getMessage(), JSONObject.toJSONString(slxxDTO));
            throw new AppException(e.getMessage());
        }

    }

    /**
     * 4.3.8	办结
     *
     * @param bjxxDTO
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public SbdjfkResponseDTO sbdjBjxx(@RequestBody SbdjResquestBjxxDTO bjxxDTO) {
        try {
            SbdjfkResponseDTO responseDTO = new SbdjfkResponseDTO();
            if (null == bjxxDTO) {
                throw new MissingArgumentException("申报办结信息不能为空！");
            }

            // 创建输出流
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(bjxxDTO.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            SbdjfkResquestSbjbxxCallInfoDTO callInfoDTO = new SbdjfkResquestSbjbxxCallInfoDTO();
            callInfoDTO.setCallBackUrl("");
            bjxxDTO.setCallInfoDTO(callInfoDTO);
            marshaller.marshal(bjxxDTO, sw);
            LOGGER.info("申报登记办结请求xml参数:{}", sw.toString());
            responseDTO = zwsb(Constants.SBDJ_BJ, sw.toString());

            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("---异常信息:{},申报登记办结入参:{}", e.getMessage(), JSONObject.toJSONString(bjxxDTO));
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @param apiName 接口名
     * @return
     * @paramString paramString 请求参数
     * @Date 2022/7/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public SbdjfkResponseDTO zwsb(String apiName, String paramString) {
        try {
            SbdjfkResponseDTO responseDTO = new SbdjfkResponseDTO();
            ApiResponse apiResponse = new ApiResponse();
            if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_HF)) {
                HefeiZwSdkApi hefeiZwSdkApi = new HefeiZwSdkApi();
                LOGGER.info("合肥政务接口名称：{}，请求参数:{}", apiName, JSONObject.toJSONString(paramString));
                if (Constants.SBDJ.equals(apiName)) {
                    apiResponse = hefeiZwSdkApi.apasInfo(paramString, null, null);
                }
                if (Constants.SBDJ_SL.equals(apiName)) {
                    apiResponse = hefeiZwSdkApi.acceptInfo(paramString, null);
                }
                if (Constants.SBDJ_BJ.equals(apiName)) {
                    apiResponse = hefeiZwSdkApi.transactXml(paramString, null, null);
                }
            } else if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_SC)) {
                ShuchengiZwSdkApi shuchengiZwSdkApi = new ShuchengiZwSdkApi();
                LOGGER.info("舒城政务接口名称：{}，请求参数:{}", apiName, JSONObject.toJSONString(paramString));
                if (Constants.SBDJ.equals(apiName)) {
                    apiResponse = shuchengiZwSdkApi.apasInfo(paramString, null, null);
                }
                if (Constants.SBDJ_SL.equals(apiName)) {
                    apiResponse = shuchengiZwSdkApi.acceptInfo(paramString, null);
                }
                if (Constants.SBDJ_BJ.equals(apiName)) {
                    apiResponse = shuchengiZwSdkApi.transactXml(paramString, null, null);
                }
            } else if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_BB)) {

                BengbuZwSdkAPi bengbuZwSdkApi = new BengbuZwSdkAPi();
                bengbuZwSdkApi.setHost(bengbuzwsdkIp);
                bengbuZwSdkApi.setHttpPort(bengbuzwsdkhttpPort);
                LOGGER.info("蚌埠政务接口名称：{}，请求参数:{}", apiName, JSONObject.toJSONString(paramString));
                if (Constants.SBDJ.equals(apiName)) {
                    apiResponse = bengbuZwSdkApi.apasInfo(paramString, null, null);
                }
                if (Constants.SBDJ_SL.equals(apiName)) {
                    apiResponse = bengbuZwSdkApi.acceptInfo(paramString, null);
                }
                if (Constants.SBDJ_BJ.equals(apiName)) {
                    apiResponse = bengbuZwSdkApi.transactXml(paramString, null, null);
                }
            } else if (dataVersion.equals(CommonConstantUtils.SYSTEM_VERSION_XC)) {
                XuanchengZwSdkAPi xuanchengZwSdkAPi = new XuanchengZwSdkAPi();
                LOGGER.info("宣城政务接口名称：{}，请求参数:{}", apiName, JSONObject.toJSONString(paramString));
                if (Constants.SBDJ.equals(apiName)) {
                    apiResponse = xuanchengZwSdkAPi.projIdFeedback(paramString, null);
                }
                if (Constants.SBDJ_SL.equals(apiName)) {
                    apiResponse = xuanchengZwSdkAPi.apasInfo(paramString, null);
                }
                if (Constants.SBDJ_BJ.equals(apiName)) {
                    apiResponse = xuanchengZwSdkAPi.transactXml(paramString, null, null);
                }
            }
            // 处理执行结果
            if (apiResponse != null && apiResponse.getBody() != null) {
                LOGGER.info("执行结果响应结果:{}", JSONObject.toJSONString(apiResponse));
                String response = new String(apiResponse.getBody(), "UTF-8");
                LOGGER.info("执行结果响应数据:{}", response);
                responseDTO = JSONObject.parseObject(response, SbdjfkResponseDTO.class);
            }
            return responseDTO;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }


}
