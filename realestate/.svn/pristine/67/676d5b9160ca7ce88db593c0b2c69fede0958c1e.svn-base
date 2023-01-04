package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.BdcJtcyCxService;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcFileBase64DTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlJtcyGroupDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryParamDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.PoliceHouseholdMembersRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.PoliceHouseholdMembersResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.PoliceHouseholdMembersResponseDataDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.GetJtcyxxQO;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NaturalResourcesFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ProvincialPublicSecurityDepartmentFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcJtcyCxYmxxVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/4/14
 * @description 家庭成员查询信息服务
 */
@Service
public class BdcJtcyCxServiceImpl implements BdcJtcyCxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcJtcyCxServiceImpl.class);

    @Autowired
    NaturalResourcesFeignService naturalResourcesFeignService;
    @Autowired
    ProvincialPublicSecurityDepartmentFeignService provincialPublicSecurityDepartmentFeignService;
    @Autowired
    BdcDypzFeignService bdcDypzFeignService;
    @Autowired
    BdcDysjPzService bdcDysjPzService;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    PdfController pdfController;
    @Autowired
    BdcSlJtcyService bdcSlJtcyService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    DozerBeanMapper acceptDozerMapper;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    BdcSlFwtcxxService bdcSlFwtcxxService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 打印模板地址
     */
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 水印图片地址
     */
    @Value("${watermark.image.path:}")
    private String watermarkImagePath;

    /**
     * 南通查询婚姻信息，并生成查询文件PDF
     */
    @Override
    public void queryHyxxAndGenerateFile(String gzlslid,boolean isFirstHand) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少工作流实例ID信息");
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(Objects.isNull(bdcSlJbxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理基本信息.");
        }
        List<BdcSlSqrDO> bdcSlSqrDOList = this.getSqrxxList(gzlslid,isFirstHand);
        if(CollectionUtils.isEmpty(bdcSlSqrDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到申请人信息.");
        }

        // 生成家庭成员信息数据，并保存
        this.generateJtcypoxx(bdcSlSqrDOList, bdcSlJbxxDO.getSlbh());

        // 生成PDF文件
        List<CivilMarriageQueryDataDTO> hyxxList = this.getHyxxCxxx(bdcSlSqrDOList, bdcSlJbxxDO.getSlbh());
        List<Map> dataList = JSONArray.parseArray(JSON.toJSONString(hyxxList), Map.class);
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "hyxxcx", "民政查询信息", "民政公安查询结果", CommonConstantUtils.WJHZ_PDF);
        this.generatePdfFile(bdcPdfDTO, dataList);
    }

    /**
     * 南通查询家庭成员信息，并生成查询信息文件PDF
     */
    @Override
    public void queryHjxxAndGenerateFile(String gzlslid,boolean isFirstHand) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少查询信息");
        }
        List<BdcSlSqrDO> bdcSlSqrDOList = this.getSqrxxList(gzlslid,isFirstHand);
        if(CollectionUtils.isEmpty(bdcSlSqrDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到申请人信息");
        }

        List<Map> dataList = new ArrayList<>();
        for(BdcSlSqrDO sqrxx: bdcSlSqrDOList){
            // 1、获取配偶 2、夫妻双方分别调用户籍信息接口，生成家庭成员信息
            List<BdcSlJtcyDO> sqrJtcyxxList = this.generateJtcyxxByHjxxCx(sqrxx);

            // 添加到查询信息pdf展示数据中
            sqrJtcyxxList.stream().filter(t-> StringUtils.isNotBlank(t.getHh())).collect(Collectors.groupingBy(BdcSlJtcyDO::getHh)).forEach((key, value)->{
                Map<String, Object> map = new HashMap<>(1);
                map.put("hh", key);
                map.put("jtcyxx", JSONArray.parseArray(JSON.toJSONString(value), Map.class));
                dataList.add(map);
            });
        }

        // 生成PDF文件
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "hjxxcx", "公安查询信息", "民政公安查询结果", CommonConstantUtils.WJHZ_PDF);
        this.generatePdfFile(bdcPdfDTO, dataList);
    }

    /**
     * 获取申请人信息
     */
    private List<BdcSlSqrDO> getSqrxxList(String gzlslid,boolean isFirstHand){
        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
            String xmid = bdcSlXmDOList.get(0).getXmid();
            List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
            if(isFirstHand){
                bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(xmid, CommonConstantUtils.QLRLB_QLR);
            }else{
                bdcSlSqrDOList = this.bdcSlSqrService.listBdcSlSqrByXmid(xmid, null);
            }
            if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
                return bdcSlSqrDOList;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 生成家庭成员配偶信息
     */
    private void generateJtcypoxx(List<BdcSlSqrDO> bdcSlSqrDOList, String slbh){
        for(BdcSlSqrDO sqrxx:bdcSlSqrDOList){
            // 先删除原有家庭成员信息
            this.bdcSlJtcyService.deleteBdcSlJtcyBySqrid(sqrxx.getSqrid());
            // 调用婚姻接口生成配偶信息
            GetJtcyxxQO jtcyxxQO = new GetJtcyxxQO(sqrxx.getSqrmc(), sqrxx.getZjh(), sqrxx.getSqrid()).withSlbh(slbh);
            BdcSlJtcyDO poJtcy = this.bdcSlJtcyService.requestHyxx(jtcyxxQO,true);
            if(Objects.nonNull(poJtcy)){
                poJtcy.setSqrid(sqrxx.getSqrid());
                bdcSlJtcyService.insertBdcSlJtcy(poJtcy);
            }
        }
    }

    /**
     * 调用南通婚姻省级接口获取婚姻信息
     */
    private List<CivilMarriageQueryDataDTO> getHyxxCxxx(List<BdcSlSqrDO> bdcSlSqrDOList, String slbh){
        CivilMarriageQueryRequestDTO info = new CivilMarriageQueryRequestDTO();
        List<CivilMarriageQueryParamDTO> paramDTOList = new ArrayList<>(bdcSlSqrDOList.size());
        for(BdcSlSqrDO bdcSlSqrDO: bdcSlSqrDOList){
            paramDTOList.add(new CivilMarriageQueryParamDTO(bdcSlSqrDO.getZjh(), bdcSlSqrDO.getSqrmc()));
        }
        info.setParamDTOList(paramDTOList);
        info.setSlbh(slbh);
        CivilMarriageQueryResponseDTO civilMarriageQueryResponseDTO = naturalResourcesFeignService.marriageQuery(info);
        if (civilMarriageQueryResponseDTO != null && CollectionUtils.isNotEmpty(civilMarriageQueryResponseDTO.getDataDTOList())) {
            return civilMarriageQueryResponseDTO.getDataDTOList();
        }
        return Collections.emptyList();
    }

    /**
     * 调用户籍信息接口，生成家庭成员信息
     */
    private List<BdcSlJtcyDO> generateJtcyxxByHjxxCx(BdcSlSqrDO sqrxx){
        // 1、获取申请人户籍信息
        List<BdcSlJtcyDO> sqrJtcyxxList = this.getHjxxCxxx(sqrxx.getZjh());

        // 过滤需要的家庭成员信息
        List<BdcSlJtcyDO> sqrJtcyxxListNeed = getFilterJtcy(sqrJtcyxxList, sqrxx.getZjh(),true);

        // 2、获取申请人的配偶信息, 在通过配偶信息调用户籍接口获取户籍信息
        BdcSlJtcyQO bdcSlJtcyQO = new BdcSlJtcyQO();
        bdcSlJtcyQO.setSqrid(sqrxx.getSqrid());
        bdcSlJtcyQO.setYsqrgx(CommonConstantUtils.YSQRGX_PO_MC);
        List<BdcSlJtcyDO> poxxList = this.bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);

        if(CollectionUtils.isNotEmpty(poxxList)){
            for(BdcSlJtcyDO poxx : poxxList){
                //添加配偶
                sqrJtcyxxListNeed.add(poxx);
                List<BdcSlJtcyDO> sqrPoJtcyxxList = this.getHjxxCxxx(poxx.getZjh());
                List<BdcSlJtcyDO> sqrPoJtcyxxListNeed = getFilterJtcy(sqrPoJtcyxxList, poxx.getZjh(),false);
                sqrJtcyxxListNeed.addAll(sqrPoJtcyxxListNeed);
            }
        }

        LOGGER.info("查询到户籍信息：{}", JSON.toJSONString(sqrJtcyxxListNeed));

        // 对获取到家庭成员进行去重，去重规则：去除证件号相同的，选取hh不为空的数据
        sqrJtcyxxListNeed = this.filterJtcyxx(sqrJtcyxxListNeed);

        if(CollectionUtils.isNotEmpty(sqrJtcyxxListNeed)){
            // 保存家庭成员信息
            GetJtcyxxQO jtcyxxQO = new GetJtcyxxQO(sqrxx.getSqrmc(), sqrxx.getZjh(), sqrxx.getSqrid());
            this.bdcSlJtcyService.saveJkJtcyxx(sqrJtcyxxListNeed, jtcyxxQO);
        }
        return sqrJtcyxxListNeed;
    }

    /**
     * 1、当前权利人若未成年，则查出并展示所有家庭成员信息
     * 2、当前权利人若已成年，则查出并展示所有未成年成员信息
     */
    private List<BdcSlJtcyDO> getFilterJtcy(List<BdcSlJtcyDO> sqrJtcyxxList,String zjh,boolean isSqr){
        boolean isAdult = IDCardUtils.isAdult(zjh);
        List<BdcSlJtcyDO> sqrJtcyxxListFilter = new ArrayList<>();
        if(isAdult){
            // 查询人已成年，则只要展示未成年的数据
            for(BdcSlJtcyDO bdcSlJtcyDO : sqrJtcyxxList){
                if(StringUtils.isNotBlank(bdcSlJtcyDO.getZjh())){
                    // 添加本人信息
                    if(zjh.equals(bdcSlJtcyDO.getZjh()) &&isSqr){
                        bdcSlJtcyDO.setYsqrgx(CommonConstantUtils.YSQRGX_BR_MC);
                        sqrJtcyxxListFilter.add(bdcSlJtcyDO);
                        continue;
                    }
                    boolean isAdultJtcy = IDCardUtils.isAdult(bdcSlJtcyDO.getZjh());
                    if(!isAdultJtcy){
                        bdcSlJtcyDO.setYsqrgx(CommonConstantUtils.YSQRGX_WCNZN_MC);
                        sqrJtcyxxListFilter.add(bdcSlJtcyDO);
                    }
                }
            }
        }else{
            // 查询人已成年，则展示所有
            sqrJtcyxxListFilter = sqrJtcyxxList;
        }
        return sqrJtcyxxListFilter;
    }

    /**
     * 调用户籍信息接口，获取家庭成员
     */
    private List<BdcSlJtcyDO> getHjxxCxxx(String qlrzjh){
        if(StringUtils.isNotBlank(qlrzjh)){
            PoliceHouseholdMembersRequestDTO queryInfo = new PoliceHouseholdMembersRequestDTO();
            queryInfo.setIdentityNumber(qlrzjh);
            PoliceHouseholdMembersResponseDTO policeHouseholdMembersResponseDTO = provincialPublicSecurityDepartmentFeignService.policeHouseholdMembers(queryInfo);

            if(policeHouseholdMembersResponseDTO != null &&CollectionUtils.isNotEmpty(policeHouseholdMembersResponseDTO.getResponseDataDTOList())){
                LOGGER.info("查询接口policeHouseholdMembers，返回值：{}",JSONObject.toJSONString(policeHouseholdMembersResponseDTO));
                List<BdcSlJtcyDO> jtcyDOList = new ArrayList<>();
                for(PoliceHouseholdMembersResponseDataDTO hjxx:policeHouseholdMembersResponseDTO.getResponseDataDTOList()){
                    BdcSlJtcyDO bdcSlJtcyDO = new BdcSlJtcyDO();
                    acceptDozerMapper.map(hjxx, bdcSlJtcyDO, "hjxxToJtcy");
                    jtcyDOList.add(bdcSlJtcyDO);
                }
                return jtcyDOList;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 家庭成员信息去重，相同证件号的保留有户号信息的数据
     */
    private List<BdcSlJtcyDO> filterJtcyxx(List<BdcSlJtcyDO> jtcyDOList){
        if(CollectionUtils.isNotEmpty(jtcyDOList)){
            jtcyDOList = jtcyDOList.stream().collect(Collectors.toMap(BdcSlJtcyDO::getZjh, a->a,(o1,o2)->{
                if(StringUtils.isNotBlank(o1.getHh())){
                    return o1;
                }else if(StringUtils.isNotBlank(o2.getHh())){
                    return o2;
                }
                return o1;
            })).values().stream().collect(Collectors.toList());
        }
        return jtcyDOList;
    }


    /**
     * 生成 PDF 文件
     */
    private void generatePdfFile(BdcPdfDTO bdcPdfDTO, List<Map> dataList){
        // 获取PDF打印数据
        String xmlData = this.getPrintData(bdcPdfDTO.getDylx(), bdcPdfDTO.getGzlslid(), dataList);

        // 生成PDF文件
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + bdcPdfDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX);
        pdfExportDTO.setFileName(bdcPdfDTO.getDylx() + bdcPdfDTO.getGzlslid());
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);

        // 5、上传pdf文件至大云
        try{
            bdcPdfDTO.setPdfFilePath(pdfFilePath);

            this.bdcUploadFileUtils.uploadPdfByFilePath(bdcPdfDTO);
        }catch (IOException e){
            throw new AppException("上传PDF文件至大云中心出错，错误信息为：" + e.getMessage());
        }
    }

    /**
     * 生成 XML 数据
     */
    private String getPrintData(String dylx, String gzlslid, List<Map> dataList){
        // 1、查询打印配置
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)){
            throw new AppException("未获取到打印配置,请检查配置");
        }
        // 2、获取主表数据
        Map<String, String> parentData = new HashMap<>(2);
        parentData.put("gzlslid", gzlslid);
        //通过打印数据源sql配置转换接口数据
        if(StringUtils.isNotBlank(bdcDysjPzDOList.get(0).getDysjy())) {
            Map sjldatas = bdcDysjPzService.queryPrintDatasList( parentData, "bdcSlConfigMapper", bdcDysjPzDOList);
            if (sjldatas != null) {
                parentData.putAll(sjldatas);
            }
        }

        // 3、获取子表数据
        Multimap<String,List> childData = ArrayListMultimap.create();
        if(CollectionUtils.isNotEmpty(dataList)){
            int zbxh =0;
            for(Map<Object,Object> zbData:dataList) {
                zbxh++;
                for (Map.Entry entry : zbData.entrySet()) {
                    Object entryValue = entry.getValue();
                    if (entryValue instanceof List) {
                        //添加zbxh
                        List<Map> zbList =(List) entryValue;
                        int finalZbxh = zbxh;
                        zbList.forEach(zb -> zb.put("zbxh", finalZbxh));
                        childData.put("ZB_" + entry.getKey().toString(), zbList);
                    }
                }
            }
        }
        childData.put("ZT_"+ dylx, dataList);

        // 4、设置打印模板格式
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>(1);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(parentData));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(childData));
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    @Override
    public void uploadScreenShot(BdcFileBase64DTO bdcFileBase64DTO) {
        if(StringUtils.isAnyBlank(bdcFileBase64DTO.getGzlslid(), bdcFileBase64DTO.getBase64())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例或图片base64字符串。");
        }
        try{
            if(StringUtils.isBlank(watermarkImagePath)){
                throw new AppException(ErrorCode.NPE_EX, "未配置水印图片的地址。");
            }
            if(!new File(watermarkImagePath).exists()){
                throw new AppException(ErrorCode.NPE_EX, "未获取到配置的水印图片，请检查。");
            }
            MultipartFile file = ImageUtils.addImageWaterMarkBottomRight(bdcFileBase64DTO.getBase64(), watermarkImagePath);
            if(Objects.isNull(file)){
                throw new AppException("Base64转图片失败!");
            }

            BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(bdcFileBase64DTO.getGzlslid(), "", "家庭主房查询信息", "家庭成员住房查询", CommonConstantUtils.WJHZ_PNG);
            this.bdcUploadFileUtils.upload(bdcPdfDTO, file);
        }catch(IOException e){
            throw new AppException(ErrorCode.IO_EX, "图片添加水印异常");
        }
    }

    @Override
    public BdcJtcyCxYmxxVO queryJtcyYmxx(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(Objects.isNull(bdcSlJbxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理基本信息.");
        }

        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcSlXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理项目信息.");
        }

        // 获取产权的不动产项目
        BdcSlXmDO fdcqxm = null;
        for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList){
            if(ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcSlXmDO.getQllx())){
                fdcqxm = bdcSlXmDO;
                break;
            }
        }
        if(Objects.isNull(fdcqxm)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到产权的项目信息。");
        }
        String xmid = fdcqxm.getXmid();
        BdcJtcyCxYmxxVO bdcJtcyCxYmxxVO = new BdcJtcyCxYmxxVO();
        bdcJtcyCxYmxxVO.setBdcSlJbxxDO(bdcSlJbxxDO);
        bdcJtcyCxYmxxVO.setBdcSlXmDO(fdcqxm);

        // 查询申请人分组信息
        List<List<BdcSlJtcyGroupDTO>> qlrGroupJtcyxxList = this.getSqrxxGroupByJtWithSqrlb(xmid, CommonConstantUtils.QLRLB_QLR);
        if(CollectionUtils.isNotEmpty(qlrGroupJtcyxxList)){
            bdcJtcyCxYmxxVO.setBdcQlrGroupDTOLists(qlrGroupJtcyxxList);
        }
        List<List<BdcSlJtcyGroupDTO>> ywrGroupJtcyxxList = this.getSqrxxGroupByJtWithSqrlb(xmid, CommonConstantUtils.QLRLB_YWR);
        if(CollectionUtils.isNotEmpty(ywrGroupJtcyxxList)){
            bdcJtcyCxYmxxVO.setBdcYwrGroupDTOLists(ywrGroupJtcyxxList);
        }

        // 生成一窗受理房屋信息
        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
            bdcJtcyCxYmxxVO.setBdcSlFwxxDO(bdcSlFwxxDOList.get(0));
        }

        // 生成买方、买方房屋套次信息
        List<BdcSlFwtcxxDO> zrffwtcxxList = bdcSlFwtcxxService.listBdcSlFwtcxxByXmid(xmid,CommonConstantUtils.QLRLB_QLR);
        if(CollectionUtils.isNotEmpty(zrffwtcxxList)){
            bdcJtcyCxYmxxVO.setBdcZrfZfxxDTOList(zrffwtcxxList);
        }
        List<BdcSlFwtcxxDO> zcffwtcxxList = bdcSlFwtcxxService.listBdcSlFwtcxxByXmid(xmid,CommonConstantUtils.QLRLB_YWR);
        if(CollectionUtils.isNotEmpty(zcffwtcxxList)){
            bdcJtcyCxYmxxVO.setBdcZcfZfxxDTOList(zcffwtcxxList);
        }
        return bdcJtcyCxYmxxVO;
    }

    /**
     * 获取申请人及其家庭成员信息
     */
    public List<List<BdcSlJtcyGroupDTO>> getSqrxxGroupByJtWithSqrlb(String xmid,String sqrlb){
        List<List<BdcSlJtcyGroupDTO>> lists =new ArrayList<>();
        // 获取申请人信息
        List<BdcSlSqrDO> bdcSlSqrDOList =this.bdcSlSqrService.listBdcSlSqrByXmid(xmid,sqrlb);
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
            // 申请人证件号集合
            List<String> sqrZjhList = bdcSlSqrDOList.stream().map(BdcSlSqrDO::getZjh).collect(Collectors.toList());

            // 家庭成员去重Map key: zjh  value：BdcSlJtcyGroupDTO, 对家庭成员进行去重过滤
            Map<String, BdcSlJtcyGroupDTO> bdcSlJtcyGroupDTOMap = new LinkedHashMap<>(10);

            // 添加申请人至家庭成员信息中，防止家庭成员中包含申请人信息导致重复
            for(BdcSlSqrDO bdcSlSqrDO:bdcSlSqrDOList){
                BdcSlJtcyGroupDTO bdcSlJtcyGroupDTO = new BdcSlJtcyGroupDTO();
                acceptDozerMapper.map(bdcSlSqrDO, bdcSlJtcyGroupDTO);
                bdcSlJtcyGroupDTOMap.put(bdcSlSqrDO.getZjh(), bdcSlJtcyGroupDTO);
            }

            // 根据xmid与sqrlb获取所有家庭成员信息
            BdcSlJtcyQO bdcSlJtcyQO = new BdcSlJtcyQO();
            bdcSlJtcyQO.setXmid(xmid);
            bdcSlJtcyQO.setSqrlb(sqrlb);
            List<BdcSlJtcyDO> slJtcyDOList = bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);
            if(CollectionUtils.isNotEmpty(slJtcyDOList)){
                for(BdcSlJtcyDO bdcSlJtcyDO : slJtcyDOList){
                    // 根据证件号，对家庭成员信息去重处理
                    if(bdcSlJtcyGroupDTOMap.containsKey(bdcSlJtcyDO.getZjh())){
                        if(sqrZjhList.contains(bdcSlJtcyDO.getZjh())){
                            if(StringUtils.isNotBlank(bdcSlJtcyDO.getHh())){
                                bdcSlJtcyGroupDTOMap.get(bdcSlJtcyDO.getZjh()).setHh(bdcSlJtcyDO.getHh());
                            }
                            if(StringUtils.isNotBlank(bdcSlJtcyDO.getXb())){
                                bdcSlJtcyGroupDTOMap.get(bdcSlJtcyDO.getZjh()).setXb(bdcSlJtcyDO.getXb());
                            }
                        }else{
                            bdcSlJtcyGroupDTOMap.get(bdcSlJtcyDO.getZjh()).getSqridList().add(bdcSlJtcyDO.getSqrid());
                        }
                    }else{
                        BdcSlJtcyGroupDTO jtcyGroupDTO = new BdcSlJtcyGroupDTO();
                        acceptDozerMapper.map(bdcSlJtcyDO, jtcyGroupDTO);
                        List<String> sqridList = Stream.of(bdcSlJtcyDO.getSqrid()).collect(Collectors.toList());
                        jtcyGroupDTO.setSqridList(sqridList);
                        bdcSlJtcyGroupDTOMap.put(bdcSlJtcyDO.getZjh(), jtcyGroupDTO);
                    }
                }
            }

            // 添加合并后的家庭成员信息
            if(MapUtils.isNotEmpty(bdcSlJtcyGroupDTOMap)){
                List<BdcSlJtcyGroupDTO> bdcSlJtcyGroupDTOList = new ArrayList<>();
                bdcSlJtcyGroupDTOMap.forEach((key, value)->{
                    bdcSlJtcyGroupDTOList.add(value);
                });
                lists.add(bdcSlJtcyGroupDTOList);
            }
        }
        return lists;
    }

    /**
     * sqr中没有户号 要查询一下jtcy表 获取hh
     */
    private void getJtcyHH(BdcSlJtcyGroupDTO bdcSlJtcyGroupDTO){
        if(null != bdcSlJtcyGroupDTO && StringUtils.isNotBlank(bdcSlJtcyGroupDTO.getZjh())){
            LOGGER.info("开始同步申请人中的hh");
            BdcSlJtcyQO bdcJtcyQO = new BdcSlJtcyQO();
            bdcJtcyQO.setZjh(bdcSlJtcyGroupDTO.getZjh());
            bdcJtcyQO.setJtcymc(bdcSlJtcyGroupDTO.getMc());
            LOGGER.info("查询家庭成员条件为{}，{}",bdcSlJtcyGroupDTO.getZjh(),bdcSlJtcyGroupDTO.getMc());
            List<BdcSlJtcyDO> listJtcy = bdcSlJtcyService.listBdcSlJtcy(bdcJtcyQO);
            if(CollectionUtils.isNotEmpty(listJtcy)){
                LOGGER.info("查询家庭成员返回值",JSONObject.toJSONString(listJtcy));
                bdcSlJtcyGroupDTO.setHh(listJtcy.get(0).getHh());
            }
        }
    }

}
