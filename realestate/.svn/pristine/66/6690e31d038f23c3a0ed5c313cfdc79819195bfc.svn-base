package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.etl.BdcJyGxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.*;
import cn.gtmap.realestate.common.core.enums.BdcZdEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.TransactionInquiryQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.WebServiceUtils;
import cn.gtmap.realestate.etl.convert.FcjyClfHtxxHandlerConverter;
import cn.gtmap.realestate.etl.core.domian.ClfHtbaDo;
import cn.gtmap.realestate.etl.core.domian.SpfHtbaDo;
import cn.gtmap.realestate.etl.core.mapper.exchange.BdcJyGxMapper;
import cn.gtmap.realestate.etl.core.mapper.sjgl.FwHsMapper;
import cn.gtmap.realestate.etl.core.service.FcjyHtxxService;
import cn.gtmap.realestate.etl.service.FcjyClfHtxxHandlerService;
import cn.gtmap.realestate.etl.util.XmlUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom.input.SAXBuilder;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/12
 * @description
 */
@Service
public class FcjyClfHtxxHandlerServiceImpl implements FcjyClfHtxxHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FcjyClfHtxxHandlerServiceImpl.class);

    @Autowired
    FcjyHtxxService fcjyHtxxService;
    @Resource(name = "dozerSameNullFMapper")
    private DozerBeanMapper dozerMapperF;

    @Resource(name = "fcjyEntityMapper")
    private EntityMapper fcjyEntityMapper;

    @Resource(name = "entityMapper")
    private EntityMapper entityMapper;

    @Autowired
    private BdcJyGxMapper bdcJyGxMapper;

    @Autowired
    private FwHsMapper fwHsMapper;

    @Autowired
    private FcjyClfHtxxHandlerConverter fcjyClfHtxxHandlerConverter;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Value("${mfjyxx.url:http://10.10.10.11:8005/wcflib?wsdl}")
    private String bangbuCxMfJyxxUrl;


    @Override
    public EtlClfHtbaResponseDTo clfHtxx(String contractNo, String yjybh, String fwbm) {
        EtlClfHtbaResponseDTo etlClfHtbaResponseDTo = new EtlClfHtbaResponseDTo();
        if (StringUtils.isBlank(contractNo) && StringUtils.isBlank(yjybh) && StringUtils.isBlank(fwbm)) {
            LOGGER.error("查询二手房交易信息缺失查询参数合同编号或房屋编码");
            return etlClfHtbaResponseDTo;
        }
        ClfHtbaDo clfHtbaDo = fcjyHtxxService.queryClfHtbaDoByHtbh(contractNo, yjybh, fwbm);
        if (clfHtbaDo != null) {
            dozerMapperF.map(clfHtbaDo, etlClfHtbaResponseDTo);
        }
        return etlClfHtbaResponseDTo;
    }

    @Override
    public EtlSpfHtbaResponseDTo spfHtxx(String contractNo, String fwbm) {
        EtlSpfHtbaResponseDTo etlSpfHtbaResponseDTo = new EtlSpfHtbaResponseDTo();
        if (StringUtils.isBlank(contractNo) && StringUtils.isBlank(fwbm)) {
            LOGGER.error("查询一手房交易信息缺失查询参数合同编号或房屋编码");
            return etlSpfHtbaResponseDTo;
        }
        SpfHtbaDo spfHtbaDo = fcjyHtxxService.querySpfHtbaDoByHtbh(contractNo, fwbm);
        if (spfHtbaDo != null) {
            dozerMapperF.map(spfHtbaDo, etlSpfHtbaResponseDTo);
        }
        return etlSpfHtbaResponseDTo;
    }

    /**
     * @param transactionInquiryQO
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @Override
    public EtlSpfHtbaResponseDTo spfHtxxForCf(TransactionInquiryQO transactionInquiryQO) {
        EtlSpfHtbaResponseDTo etlSpfHtbaResponseDTo = new EtlSpfHtbaResponseDTo();
        if (null == transactionInquiryQO) {
            LOGGER.error("查询一手房交易信息缺失查询参数");
            return etlSpfHtbaResponseDTo;
        }
        Example example = new Example(SpfHtbaDo.class);
        if (StringUtils.isNotBlank(transactionInquiryQO.getHtbh())) {
            example.createCriteria().andEqualTo("htbh",transactionInquiryQO.getHtbh());
        }
        if (StringUtils.isNotBlank(transactionInquiryQO.getXm())) {
            example.createCriteria().andEqualTo("msr",transactionInquiryQO.getXm());
        }
        if(StringUtils.isNotBlank(transactionInquiryQO.getZjhm())){
            example.createCriteria().andEqualTo("msrzjhm",transactionInquiryQO.getZjhm());
        }
        if(StringUtils.isNotBlank(transactionInquiryQO.getZl())){
            example.createCriteria().andEqualTo("fwzl",transactionInquiryQO.getZl());
        }
        List<SpfHtbaDo> spfHtbaDos = fcjyEntityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(spfHtbaDos)){
            dozerMapperF.map(spfHtbaDos.get(0), etlSpfHtbaResponseDTo);
            return etlSpfHtbaResponseDTo;
        }
        return null;
    }

    /**
     * @param transactionInquiryQO
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     * 二手房待定
     */
    @Override
    public EtlClfHtbaResponseDTo clfHtxxForCf(TransactionInquiryQO transactionInquiryQO) {
        EtlClfHtbaResponseDTo etlClfHtbaResponseDTo = new EtlClfHtbaResponseDTo();
        if (null == transactionInquiryQO) {
            LOGGER.error("查询二手房交易信息缺失查询参数");
            return etlClfHtbaResponseDTo;
        }

        return null;
    }

    /**
     * @param pageable
     * @param transactionInquiryQO
     * @author <a href="mailto:zqdeqiang@gtmap.cn">zedq</a>
     * @Date 2020/11/03
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @Override
    public EtlSpfHtbaListResponseDTO listSpfHtxxForCf(Pageable pageable, TransactionInquiryQO transactionInquiryQO) {
        List<EtlSpfHtbaResponseDTo> etlSpfHtbaResponseDTOList = new ArrayList<>(4);
        //分页
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        if (null == transactionInquiryQO) {
            LOGGER.error("查询一手房交易信息缺失查询参数");
            return null;
        }
        Example example = new Example(SpfHtbaDo.class);
        if (StringUtils.isNotBlank(transactionInquiryQO.getHtbh())) {
            example.createCriteria().andEqualTo("htbh", transactionInquiryQO.getHtbh());
        }
        if (StringUtils.isNotBlank(transactionInquiryQO.getXm())) {
            example.createCriteria().andEqualTo("msr", transactionInquiryQO.getXm());
        }
        if (StringUtils.isNotBlank(transactionInquiryQO.getZjhm())) {
            example.createCriteria().andEqualTo("msrzjhm", transactionInquiryQO.getZjhm());
        }
        if (StringUtils.isNotBlank(transactionInquiryQO.getZl())) {
            example.createCriteria().andEqualTo("fwzl", transactionInquiryQO.getZl());
        }

//        LambdaQueryWrapper<SpfHtbaDo> objectLambdaQueryWrapper = Wrappers.lambdaQuery();
//        if (StringUtils.isNotBlank(transactionInquiryQO.getHtbh())) {
//            objectLambdaQueryWrapper.eq(SpfHtbaDo::getHtbh,transactionInquiryQO.getHtbh());
//        }
//        if (StringUtils.isNotBlank(transactionInquiryQO.getXm())) {
//            objectLambdaQueryWrapper.eq(SpfHtbaDo::getMsr,transactionInquiryQO.getXm());
//        }
//        if(StringUtils.isNotBlank(transactionInquiryQO.getZjhm())){
//            objectLambdaQueryWrapper.eq(SpfHtbaDo::getMsrzjhm,transactionInquiryQO.getZjhm());
//        }
//        if(StringUtils.isNotBlank(transactionInquiryQO.getZl())){
//            objectLambdaQueryWrapper.eq(SpfHtbaDo::getFwzl,transactionInquiryQO.getZl());
//        }
//        List<SpfHtbaDo> spfHtbaDos = htba1Mapper.selectList(objectLambdaQueryWrapper);
        List<SpfHtbaDo> spfHtbaDos = fcjyEntityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(spfHtbaDos)) {
            PageInfo<SpfHtbaDo> spfHtbaPageInfo = new PageInfo(spfHtbaDos);
            etlSpfHtbaResponseDTOList = fcjyClfHtxxHandlerConverter.getEtlSpfHtbaResponseDToListBySpfHtbaDo(spfHtbaDos);
            EtlSpfHtbaListResponseDTO responseDTo = new EtlSpfHtbaListResponseDTO();
            responseDTo.setEtlSpfHtbaResponseDTos(etlSpfHtbaResponseDTOList);
            responseDTo.setTotal(spfHtbaPageInfo.getTotal());
            return responseDTo;
        }
        return null;
    }

    @Override
    public FcjyBaxxDTO zlfCxmfjyxx(String bdcdyh) {
        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();
        String houseID = getHouseIdByBdcdyh(bdcdyh);
        Map<String,Object> data = new HashMap<>(16);
        data.put("house_id",houseID);
        data.put("djbh","");
        data.put("slx","受理(产权登记)");
        String result = requestTransactionQueryInfo("22",data);
        try {
            StringReader reader = new StringReader(result);
            JAXBContext jaxbContext = JAXBContext.newInstance(EtlZydjCxMfjyxxResponseDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            EtlZydjCxMfjyxxResponseDTO etlZydjCxMfjyxxResponseDTO = (EtlZydjCxMfjyxxResponseDTO) jaxbUnmarshaller.unmarshal(reader);

            etlZydjCxMfjyxxResponseDTOConvertFcjyBaxxDTO(fcjyBaxxDTO,etlZydjCxMfjyxxResponseDTO);
            return fcjyBaxxDTO;
        }catch (Exception e){
            LOGGER.info("xml转对象失败:{}",e.getMessage(),e);
            return fcjyBaxxDTO;
        }
    }

    @Override
    public FcjyBaxxDTO clfCxmfjyxx(String cqzh) {
        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();
        Map<String,Object> data = new HashMap<>(16);
        data.put("cqzh",cqzh);
        data.put("slbh","");
        String result = requestTransactionQueryInfo("22",data);
        try {
            StringReader reader = new StringReader(result);
            JAXBContext jaxbContext = JAXBContext.newInstance(EtlZydjCxMfjyxxResponseDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            EtlZydjCxMfjyxxResponseDTO etlZydjCxMfjyxxResponseDTO = (EtlZydjCxMfjyxxResponseDTO) jaxbUnmarshaller.unmarshal(reader);
            etlZydjCxMfjyxxResponseDTOConvertFcjyBaxxDTO(fcjyBaxxDTO,etlZydjCxMfjyxxResponseDTO);
            return fcjyBaxxDTO;
        }catch (Exception e){
            LOGGER.info("xml转对象失败:{}",e.getMessage(),e);
            return fcjyBaxxDTO;
        }
    }

    @Override
    public FcjyBaxxDTO cxmfjyxxByHtbh(String htbh,String type) {

        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();

        EtlZydjCxMfjyxxResponseDTO responseDTO = new EtlZydjCxMfjyxxResponseDTO();

        EtlZydjCxMfjyxxResponseDataDTO dataDTO = new EtlZydjCxMfjyxxResponseDataDTO();
        EtlZydjCxMfjyxxResponseDataFcxxDTO fcxxDTO = new EtlZydjCxMfjyxxResponseDataFcxxDTO();
        EtlZydjCxMfjyxxResponseDataQlrxxDTO qlrxxDTO = new EtlZydjCxMfjyxxResponseDataQlrxxDTO();
//        EtlZydjCxMfjyxxResponseDataXzxxDTO xzxxDTO = new EtlZydjCxMfjyxxResponseDataXzxxDTO();

        if(StringUtils.equals("1",type)){
            Example spfExample = new Example(SpfHtbaDo.class);
            spfExample.createCriteria().andEqualTo("htbh", htbh);
            List<SpfHtbaDo> spfHtbaDos = fcjyEntityMapper.selectByExample(spfExample);
            if (CollectionUtils.isNotEmpty(spfHtbaDos)) {
                SpfHtbaDo spfHtbaDo = spfHtbaDos.get(0);
                fcxxDTO.setHtbh(spfHtbaDo.getHtbh());
                fcxxDTO.setFwzl(spfHtbaDo.getFwzl());
                fcxxDTO.setJzmj(spfHtbaDo.getJm());
                fcxxDTO.setHtje(spfHtbaDo.getHtje());
                fcxxDTO.setYt(spfHtbaDo.getFwyt());
                fcxxDTO.setBarq(spfHtbaDo.getBarq());
                fcxxDTO.setFwbm(spfHtbaDo.getFwbm());

                qlrxxDTO.setXm(spfHtbaDo.getMsr());
                qlrxxDTO.setZjhm(spfHtbaDo.getMsrzjhm());

                List<EtlZydjCxMfjyxxResponseDataQlrxxDTO> qlrxxDTOS = new ArrayList<>();
                qlrxxDTOS.add(qlrxxDTO);

                dataDTO.setFcxxDTO(fcxxDTO);
                dataDTO.setQlrxxDTOS(qlrxxDTOS);

                responseDTO.setDataDTO(dataDTO);
            }
        }
        if(StringUtils.equals("2",type)){
            Example clfExample = new Example(SpfHtbaDo.class);
            clfExample.createCriteria().andEqualTo("htbh", htbh);
            List<ClfHtbaDo> clfHtbaDos = fcjyEntityMapper.selectByExample(clfExample);
            if (CollectionUtils.isNotEmpty(clfHtbaDos)) {
                ClfHtbaDo clfHtbaDo = clfHtbaDos.get(0);
                fcxxDTO.setHtbh(clfHtbaDo.getHtbh());
                fcxxDTO.setFwzl(clfHtbaDo.getFwzl());
                fcxxDTO.setJzmj(clfHtbaDo.getJzmj());
                fcxxDTO.setHtje(clfHtbaDo.getHtje());
                fcxxDTO.setYt(clfHtbaDo.getYt());
                fcxxDTO.setBarq(clfHtbaDo.getBarq());
                fcxxDTO.setFwbm(clfHtbaDo.getFwbm());

                List<EtlZydjCxMfjyxxResponseDataQlrxxDTO> qlrxxDTOS = new ArrayList<>();
                EtlZydjCxMfjyxxResponseDataQlrxxDTO zrfQlrxxDTO = new EtlZydjCxMfjyxxResponseDataQlrxxDTO();
                zrfQlrxxDTO.setXm(clfHtbaDo.getZrfmc());
                zrfQlrxxDTO.setZjhm(clfHtbaDo.getZrfzjhm());
                zrfQlrxxDTO.setLxdh(clfHtbaDo.getZrflxdh());
                qlrxxDTOS.add(zrfQlrxxDTO);

                EtlZydjCxMfjyxxResponseDataQlrxxDTO srfQlrxxDTO = new EtlZydjCxMfjyxxResponseDataQlrxxDTO();
                srfQlrxxDTO.setXm(clfHtbaDo.getSrfmc());
                srfQlrxxDTO.setZjhm(clfHtbaDo.getSrfzjhm());
                srfQlrxxDTO.setLxdh(clfHtbaDo.getSrflxdh());
                qlrxxDTOS.add(srfQlrxxDTO);
                dataDTO.setFcxxDTO(fcxxDTO);
                dataDTO.setQlrxxDTOS(qlrxxDTOS);

                responseDTO.setDataDTO(dataDTO);
            }

        }
        etlZydjCxMfjyxxResponseDTOConvertFcjyBaxxDTO(fcjyBaxxDTO,responseDTO);
        return fcjyBaxxDTO;
    }

    @Override
    public FcjyBaxxDTO zlfca(String bdcdyh) {
        FcjyBaxxDTO fcjyBaxxDTO = new FcjyBaxxDTO();
        String houseID = getHouseIdByBdcdyh(bdcdyh);
        Map<String,Object> data = new HashMap<>(16);
        data.put("house_id",houseID);
        data.put("djbh","");
        data.put("slx","撤销(产权登记)");
        String result = requestTransactionQueryInfo("22",data);
        try {
            StringReader reader = new StringReader(result);
            JAXBContext jaxbContext = JAXBContext.newInstance(EtlZydjCxMfjyxxResponseDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            EtlZydjCxMfjyxxResponseDTO etlZydjCxMfjyxxResponseDTO = (EtlZydjCxMfjyxxResponseDTO) jaxbUnmarshaller.unmarshal(reader);
            etlZydjCxMfjyxxResponseDTOConvertFcjyBaxxDTO(fcjyBaxxDTO,etlZydjCxMfjyxxResponseDTO);
            return fcjyBaxxDTO;
        }catch (Exception e){
            LOGGER.info("xml转对象失败:{}",e.getMessage(),e);
            return fcjyBaxxDTO;
        }
    }

    /**
     * 根据bdcdyh查询houseId
     * @param bdcdyh
     * @return
     */
    private String getHouseIdByBdcdyh(String bdcdyh){
        String houseID = "";
        /**
         * 1.通过不动产单元查询登记与交易的中间关系表bdcdj_pz.temp_zh_gx_hb（旧）BDC_JY_GX（新），并将表中的fwbm作为houseID通过交易接口获取买方交易信息
         */
        List<BdcJyGxDO> bdcJyGxDOS = bdcJyGxMapper.listBdcJyGx(bdcdyh);
        if(CollectionUtils.isNotEmpty(bdcJyGxDOS)){
            BdcJyGxDO bdcJyGxDO = bdcJyGxDOS.get(0);
            houseID = bdcJyGxDO.getFwbm();
            LOGGER.info("BDC_JY_GX表fwbm:{}",houseID);
        }else{
            /**
             * 2.中间关系表bdcdj_pz.temp_zh_gx_hb中找不到对应记录，则通过不动产单元号查询权籍库房屋户室表bdcsjgl.fw_hs
             */
            List<FwHsDO> fwHsDOS = fwHsMapper.listFwHs(bdcdyh);
            houseID = CollectionUtils.isNotEmpty(fwHsDOS) ? fwHsDOS.get(0).getFwbm() : "";
            LOGGER.info("fw_hs表fwbm:{}",houseID);
        }
        return houseID;
    }

    /**
     * 获取交易买方信息
     * @param flag
     * @param paramMap
     * @return
     */
    private String requestTransactionQueryInfo(String flag, Map<String,Object> paramMap){
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("root");
            Element param = root.addElement("param");
            param.addAttribute("flag",flag);
            for(Map.Entry<String,Object> entry : paramMap.entrySet()){
                Element element = root.addElement(entry.getKey());
                element.setText(Objects.isNull(entry.getValue()) ? "" : entry.getValue().toString());
            }
            // 5、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");
            StringWriter stringWriter = new StringWriter();
            XMLWriter writer = new XMLWriter(stringWriter, format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            String xmlStr = stringWriter.toString();
            LOGGER.info("---webservice请求参数:{}",xmlStr);
            String paramStr = Base64.getEncoder().encodeToString(xmlStr.getBytes("UTF-8"));
            String result = WebServiceUtils.BengbuJy(bangbuCxMfJyxxUrl, paramStr);
            LOGGER.info("---webservice响应结果:{}",result);
            org.jsoup.nodes.Document d = Jsoup.parse(result, "", new Parser(new XmlTreeBuilder()));
            result =  d.select("DoAskResult").toString().replaceAll("\\s*", "");
            LOGGER.info("---DoAskResult:{}",result);
            Map map = xmlToMap(result);
            result = CommonUtil.formatEmptyValue(map.get("DoAskResult"));
            LOGGER.info("---加密data:{}",result);
            result = new String(Base64.getDecoder().decode(result.getBytes("UTF-8")), "UTF-8");
            LOGGER.info("---解密data:{}",result);
            return result;
        }catch (Exception e){
            LOGGER.error("获取交易买方信息异常,异常信息:{},异常堆栈:{},flag:{},参数:{}",e.getMessage(),e,flag,paramMap);
            throw new AppException(e.getMessage());
        }
    }

    private void etlZydjCxMfjyxxResponseDTOConvertFcjyBaxxDTO(FcjyBaxxDTO fcjyBaxxDTO,EtlZydjCxMfjyxxResponseDTO responseDTO){
        if(Objects.isNull(responseDTO.getDataDTO())){
            return;
        }

        if(Objects.nonNull(responseDTO.getDataDTO().getFcxxDTO())){
            BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();

            bdcSlJyxxDO.setHtbh(responseDTO.getDataDTO().getFcxxDTO().getHtbh());
            bdcSlJyxxDO.setHtbah(responseDTO.getDataDTO().getFcxxDTO().getHtbh());
            bdcSlJyxxDO.setFkfs(responseDTO.getDataDTO().getFcxxDTO().getFkfs());
            if(StringUtils.isNotEmpty(responseDTO.getDataDTO().getFcxxDTO().getHtje())){
                BigDecimal htje = new BigDecimal(responseDTO.getDataDTO().getFcxxDTO().getHtje());
                bdcSlJyxxDO.setJyje(htje.doubleValue());
            }
            if(StringUtils.isNotEmpty(responseDTO.getDataDTO().getFcxxDTO().getBarq())){
                bdcSlJyxxDO.setHtbasj(DateUtils.formatDate(responseDTO.getDataDTO().getFcxxDTO().getBarq()));
            }
            fcjyBaxxDTO.setBdcSlJyxx(bdcSlJyxxDO);

            BdcSlFwxxDO bdcSlFwxxDO = new BdcSlFwxxDO();
            bdcSlFwxxDO.setHsid(responseDTO.getDataDTO().getFcxxDTO().getHouseId());
            bdcSlFwxxDO.setFwbm(responseDTO.getDataDTO().getFcxxDTO().getFwbm());
            if(StringUtils.isNotEmpty(responseDTO.getDataDTO().getFcxxDTO().getJzmj())){
                BigDecimal jzmj = new BigDecimal(responseDTO.getDataDTO().getFcxxDTO().getJzmj());
                bdcSlFwxxDO.setJzmj(jzmj.doubleValue());
            }
            fcjyBaxxDTO.setBdcSlFwxx(bdcSlFwxxDO);

            BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
            bdcSlXmDO.setZl(responseDTO.getDataDTO().getFcxxDTO().getFwzl());
            fcjyBaxxDTO.setBdcSlXmDO(bdcSlXmDO);
        }


        if(CollectionUtils.isNotEmpty(responseDTO.getDataDTO().getQlrxxDTOS())){
            List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
            responseDTO.getDataDTO().getQlrxxDTOS().forEach(qlrxxDTO -> {
                BdcQlrDO bdcQlrDO = new BdcQlrDO();
                bdcQlrDO.setQlrlb(qlrxxDTO.getQlrlx());
                bdcQlrDO.setXb(Integer.parseInt(qlrxxDTO.getXh()));
                bdcQlrDO.setQlrmc(qlrxxDTO.getXm());
                String zddm = zdMcToDmConvert(BdcZdEnum.ZJZL.name().toLowerCase(),qlrxxDTO.getZjlb());
                if(StringUtils.isNotEmpty(zddm)){
                    bdcQlrDO.setZjzl(Integer.parseInt(zddm));
                }
                bdcQlrDO.setZjh(qlrxxDTO.getZjhm());
                bdcQlrDO.setTxdz(qlrxxDTO.getLxdh());
                bdcQlrDOS.add(bdcQlrDO);
            });
            fcjyBaxxDTO.setBdcQlr(bdcQlrDOS);
        }
    }

    /**
     * 转换字典值
     * @param param
     * @param value
     * @return
     */
    private String zdMcToDmConvert(String param,String value){
        List<Map> zdMapList = bdcZdFeignService.queryBdcZd(param);
        if(CollectionUtils.isEmpty(zdMapList)){
            return "";
        }
        for(Map map : zdMapList){
            if(Objects.nonNull(map.get("MC")) && StringUtils.equals(value,map.get("MC").toString())){
                return map.get("DM").toString();
            }
        }
        return "";
    }

    private Map<String, Object> xmlToMap(String responseXmlTemp){
        try{
            Document doc = DocumentHelper.parseText(responseXmlTemp);
            Element rootElement = doc.getRootElement();
            Map<String, Object> mapXml = new HashMap<>();
            elementToMap(mapXml, rootElement);
            return mapXml;
        }catch (Exception e){
            LOGGER.error("xml转map异常,异常信息:{},异常堆栈:{},参数:{}",e.getMessage(),e,responseXmlTemp);
            throw new AppException(e.getMessage());
        }
    }

    private void elementToMap(Map<String, Object> map, Element rootElement) {
        // 获得当前节点的子节点
        List<Element> childElements = rootElement.elements();
        if (childElements.size() > 0) {
            Map<String, Object> tempMap = new HashMap<>();
            for (Element e : childElements) {
                elementToMap(tempMap, e);
                map.put(rootElement.getName(), tempMap);
            }
        } else {
            map.put(rootElement.getName(), rootElement.getText());
        }
    }
}
