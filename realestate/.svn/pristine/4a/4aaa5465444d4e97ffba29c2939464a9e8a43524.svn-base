package cn.gtmap.realestate.exchange.service.impl.inf.changzhou;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw.*;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.changzhou.ChangZhouYjfwService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/3/31 17:12
 * @description
 */
@Service
public class ChangZhouYjfwServiceImpl implements ChangZhouYjfwService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangZhouYjfwServiceImpl.class);

    private static String YJFW_SJCL_ZDBS = "DSF_ZD_SJCL";

    private static String YJFW_PK_ORG_ZDBS = "DSF_ZD_PK_ORG";

    private static String YJFW_PK_ORG_V_ZDBS = "DSF_ZD_PK_ORG_V";

    private static String YJFW_DSFXTBS = "yjfw";

    private static String FILE_TYPE = "img";

    private static String RESPONSE_PREFIX = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?>";

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcSdqghFeignService bdcSdqghFeignService;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcQzxxFeginService bdcQzxxFeginService;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    FwLjzFeginService fwLjzFeginService;

    @Override
    public void ysqTs(String processInsId) {
        LOGGER.info("常州易家服务预申请推送水电气过户信息开始");
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        LOGGER.info("bdcXmDOList的size:{}",bdcXmDOList.size());
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.error("该工作流实例id未查询到项目信息，工作流实例id：{}", processInsId);
            throw new AppException("该工作流实例id未查询到项目信息!");
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        YsqTsDTO ysqTsDTO = new YsqTsDTO();
        ysqTsDTO.setBUSINO(bdcXmDO.getSlbh());

        // PK_ORG和PK_ORG_V
        if(StringUtils.isNotBlank(bdcXmDO.getQxdm())) {
            Map<String, BdcZdDsfzdgxDO> pkorgZdMap = queryDsfZdMap(YJFW_PK_ORG_ZDBS);
            if(pkorgZdMap.get(bdcXmDO.getQxdm()) != null) {
                ysqTsDTO.setPK_ORG(pkorgZdMap.get(bdcXmDO.getQxdm()).getDsfzdz());
            }
            Map<String, BdcZdDsfzdgxDO> pkorgvZdMap = queryDsfZdMap(YJFW_PK_ORG_V_ZDBS);
            if(pkorgvZdMap.get(bdcXmDO.getQxdm()) != null) {
                ysqTsDTO.setPK_ORG_V(pkorgvZdMap.get(bdcXmDO.getQxdm()).getDsfzdz());
            }
        }
        // 添加街道
        if(StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
            FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(bdcXmDO.getBdcdyh(),"");
            if(fwLjzDO != null) {
                ysqTsDTO.setSTREET(fwLjzDO.getStreet());
            }
        }

        // 添加水电气信息
        ysqTsDTO = addSdqxx(processInsId, bdcXmDO, ysqTsDTO);

        // 添加房地产表信息
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
        if(bdcQl != null) {
            if(bdcQl instanceof BdcFdcqDO) {
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                ysqTsDTO.setLOCATION(bdcFdcqDO.getZl());
                if(bdcFdcqDO.getGhyt() != null){
                    ysqTsDTO.setHOUSEUSE(bdcFdcqDO.getGhyt().toString());
                }
            } else {
                LOGGER.warn("所查到的权利信息非房地产权！");
            }
        }else {
            LOGGER.error("未查询到权利信息，项目id：{}", bdcXmDO.getXmid());
        }

        // 添加权利人和义务人信息
        ysqTsDTO = addQlrAndYwrxx(processInsId,ysqTsDTO);

        // 添加签字图片
        addQzxx(processInsId, ysqTsDTO);

        String paramXmlStr = convertObjectToXml(ysqTsDTO);
        LOGGER.info("预申请推送参数：{}", paramXmlStr);
        Object response = exchangeBeanRequestService.request("yjfw_ysqTs", paramXmlStr);
        LOGGER.info("易家服务预申请推送响应结果为：{}", JSONObject.toJSONString(response));

        // 推送成功
//        String response = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><root><head><sender>61</sender><receiver>D1</receiver><appid>08</appid><transcode>8001</transcode><trantime>\"+dateName+\"</trantime><iseqno>20040916572331361594</iseqno><retcode>0001</retcode><retmsg>成功</retmsg><oseqno>20200409165754358</oseqno></head><body><head><BUSINO>\"+root.getBUSINO()+\"</BUSINO></head></body></root>\n";
//        // 推送失败
//        String response = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><root><head><retcode>0024</retcode><retmsg>\"+e.getMessage()+\"</retmsg></head><body><head><BUSINO>\"+root.getBUSINO()+\"</BUSINO></head></body></root>\n";

        YjfwResponseDTO yjfwResponseDTO = convertResponseXml(response);
        if(yjfwResponseDTO != null) {
            if(StringUtils.equals(yjfwResponseDTO.getHead().getRetcode(), "0001") && StringUtils.equals(yjfwResponseDTO.getHead().getRetmsg(), "成功")){
                LOGGER.info("该流程水电气过户预申请推送成功！流程id：{}", processInsId);
                sjclTs(processInsId, bdcXmDO, ysqTsDTO);
            }else{
                LOGGER.error("该流程水电气过户预申请推送失败！流程id：{}，原因：{}", processInsId, yjfwResponseDTO.getHead().getRetmsg());
            }
        }

    }

    @Override
    public void sjclTs(String processInsId, BdcXmDO bdcXmDO, YsqTsDTO ysqTsDTO) {
        LOGGER.info("常州易家服务收件材料推送开始");

        // 查询该流程的收件材料
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(processInsId);
        if(CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            LOGGER.warn("该流程未查询到收件材料无法推送!工作流id：{}",processInsId);
            throw new AppException("该流程未查询到收件材料无法推送!");
        }
        // 获取第三方附件类型转换字典表
        Map<String, BdcZdDsfzdgxDO> fjlxZdMap = queryDsfZdMap(YJFW_SJCL_ZDBS);

        String paramXmlStr = "";
        for(BdcSlSjclDO slSjclDO : bdcSlSjclDOList) {
            if(StringUtils.isNotBlank(slSjclDO.getWjzxid())) {
                List<StorageDto> storageDtoList = storageClient.listAllSubsetStorages(slSjclDO.getWjzxid(),null, 1,null,null,null);
                if(CollectionUtils.isNotEmpty(storageDtoList)) {
                    for(StorageDto storageDto : storageDtoList) {
                        SjclTsDTO sjclTsDTO = new SjclTsDTO();
                        sjclTsDTO.setBUSINO(bdcXmDO.getSlbh());
                        if(fjlxZdMap.get(slSjclDO.getClmc()) != null) {
                            sjclTsDTO.setFJLX(fjlxZdMap.get(slSjclDO.getClmc()).getDsfzdz());
                        }
                        sjclTsDTO.setFILETYPE(FILE_TYPE);
                        sjclTsDTO.setFILEDES(storageDto.getName());
                        String fileData = downloadBase64(storageDto.getId());
                        sjclTsDTO.setFILEDATA(fileData);
                        // storageDto.getFileSize()计算单位为B, 接口需换算成KB
                        sjclTsDTO.setFLENGTH(storageDto.getFileSize()/1024);
                        // FILEPATH格式要求：主键/类型/文件名
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(ysqTsDTO.getPK_APPLY())
                                .append("/")
                                .append(sjclTsDTO.getFJLX())
                                .append("/")
                                .append(sjclTsDTO.getFILEDES());
                        sjclTsDTO.setFILEPATH(stringBuilder.toString());
                        sjclTsDTO.setPK_FILE(storageDto.getId());

                        String sjclTsDTOXmlStr = convertObjectToXml(sjclTsDTO);
                        if(StringUtils.isNotBlank(paramXmlStr)) {
                            paramXmlStr = paramXmlStr + "\n" + sjclTsDTOXmlStr;
                        }else {
                            paramXmlStr = paramXmlStr + sjclTsDTOXmlStr;
                        }
                    }

                }
            }else{
                LOGGER.warn("该收件材料文件中心id为空,无法查询该文件夹下附件信息！收件材料：{}", JSONObject.toJSONString(slSjclDO));
            }
        }

        if(StringUtils.isNotBlank(paramXmlStr)) {
            LOGGER.info("收件材料推送参数：{}", paramXmlStr);
            Object response = exchangeBeanRequestService.request("yjfw_sjclTs", paramXmlStr);
            LOGGER.info("易家服务收件材料推送响应结果为：{}", JSONObject.toJSONString(response));

            YjfwResponseDTO yjfwResponseDTO = convertResponseXml(response);
            if(yjfwResponseDTO != null) {
                if (StringUtils.equals(yjfwResponseDTO.getHead().getRetcode(), "0001") && StringUtils.equals(yjfwResponseDTO.getHead().getRetmsg(), "成功")) {
                    LOGGER.info("该流程收件材料推送成功！流程id：{}", processInsId);
                } else {
                    LOGGER.error("该流程收件材料推送失败！流程id：{}，原因：{}", processInsId, yjfwResponseDTO.getHead().getRetmsg());
                }
            }
        }
        // 附件推送成功
//        String response = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><root><head><sender>61</sender><receiver>D1</receiver><appid>08</appid><transcode>8001</transcode><trantime>\"+dateName+\"</trantime><iseqno>20040916572331361594</iseqno><retcode>0001</retcode><retmsg>成功</retmsg><oseqno>20200409165754358</oseqno></head><body><head><BUSINO>\"+root.getBUSINO()+\"</BUSINO><PK_FILE>\"+root.getPK_FILE()+\"</PK_FILE></head></body></root>\n";
//        // 附件推送失败
//        String response = "<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\" standalone=\\\"yes\\\"?><root><head><retcode>0024</retcode><retmsg>\"+e.getMessage()+\"</retmsg></head><body><head><BUSINO>\"+root.getBUSINO()+\"</BUSINO><PK_FILE>\"+root.getPK_FILE()+\"</PK_FILE></head></body></root>";
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return java.util.Map<java.lang.String,cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO>
     * @description 查询第三方附件类型字典项
     */
    private Map<String, BdcZdDsfzdgxDO> queryDsfZdMap(String zdbs) {
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setDsfxtbs(YJFW_DSFXTBS);
        List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOS = bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
        if(CollectionUtils.isEmpty(bdcZdDsfzdgxDOS)) {
            LOGGER.warn("未查询到易家服务字典项，无法推送收件材料！字典标识：{}", zdbs);
            throw new AppException("未查询到易家服务字典项，无法推送收件材料！");
        }
        Map<String, BdcZdDsfzdgxDO> fjlxZdMap = bdcZdDsfzdgxDOS.stream().collect(Collectors.toMap(BdcZdDsfzdgxDO::getBdczdz, v -> v, (v1, v2) -> v2));
        return fjlxZdMap;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [response]
     * @return cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw.YjfwResponseDTO
     * @description 将相应XML转为对象
     */
    public YjfwResponseDTO convertResponseXml(Object response) {
        if(response != null) {
            String responseStr = (String) response;
            if(responseStr.startsWith(RESPONSE_PREFIX)){
                responseStr = responseStr.substring(responseStr.indexOf(">")+1);
            }
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(YjfwResponseDTO.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader sr = new StringReader(responseStr);
                YjfwResponseDTO yjfwResponseDTO = (YjfwResponseDTO) jaxbUnmarshaller.unmarshal(sr);
                return yjfwResponseDTO;
            } catch (JAXBException e) {
                LOGGER.error("易家服务接口响应解析失败！异常信息：{}", e);
                throw new AppException(e.getMessage());
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId, ysqTsDTO]
     * @return cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw.YsqTsDTO
     * @description 添加签字信息
     */
    private YsqTsDTO addQzxx(String processInsId, YsqTsDTO ysqTsDTO) {
        BdcQzxxDO bdcQzxxDO = new BdcQzxxDO();
        bdcQzxxDO.setGzlslid(processInsId);
        List<BdcQzxxDO> bdcQzxxDOList= bdcQzxxFeginService.queryBdcQzxx(bdcQzxxDO);
        if(CollectionUtils.isEmpty(bdcQzxxDOList)){
            LOGGER.error("该工作流实例id未查询到签字信息！");
            throw new AppException("该工作流实例id未查询到签字信息！");
        }
        LOGGER.info("bdcQzxxDOList的size:{}",bdcQzxxDOList.size());
        for(BdcQzxxDO qzxx: bdcQzxxDOList) {
            if(qzxx.getQzrlb() != null && StringUtils.isNotBlank(qzxx.getFid())) {
                // 权利人或权利人代理人签字
                if(qzxx.getQzrlb() == 1 || qzxx.getQzrlb() == 2) {
                    ysqTsDTO.setNEWPIC(downloadBase64(qzxx.getFid()));
                }
                // 义务人或义务人代理人签字
                if(qzxx.getQzrlb() == 3 || qzxx.getQzrlb() == 4) {
                    ysqTsDTO.setOLDPIC(downloadBase64(qzxx.getFid()));
                }
            }else{
                LOGGER.error("签字信息异常！qzxx:{}", JSONObject.toJSONString(qzxx));
            }
        }
        return ysqTsDTO;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId, ysqTsDTO]
     * @return cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw.YsqTsDTO
     * @description 预申请推送信息添加权利人和义务人信息
     */
    private YsqTsDTO addQlrAndYwrxx(String processInsId, YsqTsDTO ysqTsDTO) {
        List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listAllBdcQlr(processInsId, CommonConstantUtils.QLRLB_QLR,null);
        if(CollectionUtils.isEmpty(qlrDOList)){
            LOGGER.error("未查询到权利人信息，无法推送水电气过户信息！");
            throw new AppException("未查询到权利人信息，无法推送水电气过户信息！");
        }
        LOGGER.info("qlrDOList的size:{}",qlrDOList.size());
        StringBuffer qlrmcBuffer = new StringBuffer();
        StringBuffer zjhBuffer = new StringBuffer();
        StringBuffer dhBuffer = new StringBuffer();
        for (int i=0;i<qlrDOList.size();i++) {
            if(i != qlrDOList.size()-1){
                qlrmcBuffer.append(qlrDOList.get(i).getQlrmc()).append("、");
                zjhBuffer.append(qlrDOList.get(i).getZjh()).append("、");
                dhBuffer.append(qlrDOList.get(i).getDh()).append("、");
            }else {
                qlrmcBuffer.append(qlrDOList.get(i).getQlrmc());
                zjhBuffer.append(qlrDOList.get(i).getZjh());
                dhBuffer.append(qlrDOList.get(i).getDh());
            }
        }
        ysqTsDTO.setNEWCTM(qlrmcBuffer.toString());
        ysqTsDTO.setNEWID(zjhBuffer.toString());
        ysqTsDTO.setNEWTEL(dhBuffer.toString());

        List<BdcQlrDO> ywrDOList = bdcQlrFeignService.listAllBdcQlr(processInsId,CommonConstantUtils.QLRLB_YWR,null);
        if(CollectionUtils.isEmpty(ywrDOList)){
            LOGGER.error("未查询到义务人信息，无法推送水电气过户信息！");
            throw new AppException("未查询到义务人信息，无法推送水电气过户信息！");
        }
        LOGGER.info("ywrDOList的size:{}",ywrDOList.size());
        StringBuffer ywrQlrmcBuffer = new StringBuffer();
        StringBuffer ywrZjhBuffer = new StringBuffer();
        StringBuffer ywrDhBuffer = new StringBuffer();
        for (int i=0;i<ywrDOList.size();i++) {
            if(i != ywrDOList.size()-1){
                ywrQlrmcBuffer.append(ywrDOList.get(i).getQlrmc()).append("、");
                ywrZjhBuffer.append(ywrDOList.get(i).getZjh()).append("、");
                ywrDhBuffer.append(ywrDOList.get(i).getDh()).append("、");
            }else {
                ywrQlrmcBuffer.append(ywrDOList.get(i).getQlrmc());
                ywrZjhBuffer.append(ywrDOList.get(i).getZjh());
                ywrDhBuffer.append(ywrDOList.get(i).getDh());
            }
        }
        ysqTsDTO.setOLDCTM(ywrQlrmcBuffer.toString());
        ysqTsDTO.setOLDID(ywrZjhBuffer.toString());
        ysqTsDTO.setOLDTEL(ywrDhBuffer.toString());

        return ysqTsDTO;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [ysqTsDTO]
     * @return java.lang.String
     * @description 将预申请推送对象转为xml
     */
    public static <T> String convertObjectToXml(T obj) {
        String xmlStr="";
        try {
            StringWriter out = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj,out);
            xmlStr = out.toString();
        } catch (JAXBException e) {
            LOGGER.error("推送信息转Xml失败！异常信息：{}",e);
            throw new AppException("推送信息转Xml失败:" + e.getMessage());
        }
        return xmlStr;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId, bdcXmDO, ysqTsDTO]
     * @return cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw.YsqTsDTO
     * @description 预申请推送对象添加水电气信息
     */
    private YsqTsDTO addSdqxx(String processInsId, BdcXmDO bdcXmDO, YsqTsDTO ysqTsDTO) {
        // 查询水电气信息
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(processInsId);
        bdcSdqywQO.setSlbh(bdcXmDO.getSlbh());
        List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghFeignService.querySdqywOrder(bdcSdqywQO);
        LOGGER.info("bdcSdqghDOList的size:{}",bdcSdqghDOList.size());
        if(CollectionUtils.isEmpty(bdcSdqghDOList)) {
            LOGGER.error("未查询到水电气数据,无法推送水电气过户信息！工作流实例id: {}，受理编号：{}", processInsId, bdcXmDO.getSlbh());
            throw new AppException("未查询到水电气数据,无法推送水电气过户信息！");
        }

        BdcSdqghDO bdcSdqghDO = bdcSdqghDOList.get(0);
        ysqTsDTO.setAPPLYNO(bdcSdqghDO.getSlbh());
        ysqTsDTO.setPK_APPLY(bdcSdqghDO.getId());
        if(bdcSdqghDO.getSqsj() != null){
            ysqTsDTO.setAPPLYTIME(DateUtils.formateTimeYmdhms(bdcSdqghDO.getSqsj()));
        }

        ysqTsDTO.setWATERORG(CommonConstantUtils.WATER_ORG);
        ysqTsDTO.setELETRICORG(CommonConstantUtils.ELETRIC_ORG);
        ysqTsDTO.setGASORG(CommonConstantUtils.GAS_ORG);
        ysqTsDTO.setNETWORKORG(CommonConstantUtils.NETWORK_ORG);
        ysqTsDTO.setTVORG(CommonConstantUtils.TV_ORG);

        for(BdcSdqghDO param : bdcSdqghDOList){
            if(BdcSdqEnum.S.key().equals(param.getYwlx())){
                ysqTsDTO.setISWATER(bdcSdqghDO.getSfbl());
            }
            if(BdcSdqEnum.D.key().equals(param.getYwlx())){
                ysqTsDTO.setISELETRIC(bdcSdqghDO.getSfbl());
            }
            if(BdcSdqEnum.Q.key().equals(param.getYwlx())){
                ysqTsDTO.setISGAS(bdcSdqghDO.getSfbl());
            }
            if(BdcSdqEnum.WL.key().equals(param.getYwlx())){
                ysqTsDTO.setISNETWORK(bdcSdqghDO.getSfbl());
            }
            if(BdcSdqEnum.GD.key().equals(param.getYwlx())){
                ysqTsDTO.setISTV(bdcSdqghDO.getSfbl());
            }
        }
        return ysqTsDTO;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [fid]
     * @return java.lang.String
     * @description 下载签字信息，并十六进制编码
     */
    private String downloadBase64(String fid) {
        byte[] srtbyte = null;
        String picHexString = "";
        try {
            MultipartDto multipartDto = storageClient.download(fid);
            if (null != multipartDto) {
                srtbyte = multipartDto.getData();
                if(srtbyte.length > 0) {
                    picHexString = Base64Utils.encodeByteToBase64Str(srtbyte);
                }
            }
        } catch (Exception e) {
            LOGGER.error("下载转码失败！fid:{}", fid);
            LOGGER.error("下载转码异常信息:{}", e);
            throw new AppException(e.getMessage());
        }
        return picHexString;
    }
}
