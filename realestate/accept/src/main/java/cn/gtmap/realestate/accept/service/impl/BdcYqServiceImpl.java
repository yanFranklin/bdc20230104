package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.storage.clients.v1.StorageClient;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.config.YqDzclConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlYqFjxxService;
import cn.gtmap.realestate.accept.service.BdcYqService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYqFjxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPrintDTO;
import cn.gtmap.realestate.common.core.dto.exchange.standard.esign.cjqslc.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BdcYqServiceImpl implements BdcYqService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYczfServiceImpl.class);

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlYqFjxxService bdcSlYqFjxxService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    YqDzclConfig yqDzclConfig;
    @Autowired
    PdfController pdfController;
    @Autowired
    StorageClient storageClient;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 使用登记库打印数据源的打印类型配置
     */
    @Value("#{'${dysjy.djk.dylx:}'.split(',')}")
    private List<String> djkDylxList;

    @Override
    public Object tsYqDzclxx(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID。");
        }
        if(MapUtils.isEmpty(yqDzclConfig.getDzcl())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少云签电子材料配置，请检查 yq.dzcl 配置内容");
        }
        // 1、获取流程信息和权利人、义务人信息
        BdcXmQO bdcXmQO  = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到不动产项目信息。");
        }
        // 2、读取当前流程配置推送的文件
        Map<String, String> yqdzclConfig = yqDzclConfig.getDzcl().get(bdcXmDOList.get(0).getGzldyid());
        // yqdzclConfig 配置内容 key: 权利人类别  value: 电子材料信息
        if(MapUtils.isEmpty(yqdzclConfig)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少工作流定义ID:"+bdcXmDOList.get(0).getGzldyid()+" 的云签电子材料配置。");
        }
        if(MapUtils.isEmpty(yqDzclConfig.getMbxx())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少模板文件信息配置。");
        }

        // 3、获取权利人、义务人信息
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcXmDOList.get(0).getXmid());
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isEmpty(bdcQlrDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到权利人、义务人信息。");
        }

        // 4、根据配置信息生成文件
        List<BdcEsignFjxxDTO> bdcEsignFjxxDTOList = this.generateBdcEsignFjxx(gzlslid,  bdcXmDOList, yqdzclConfig);
        if(CollectionUtils.isEmpty(bdcEsignFjxxDTOList)){
            throw new AppException(ErrorCode.CUSTOM, "未获取到云签文件信息");
        }
        // 5、组织云签MultipartFile信息
        Map<String, MultipartFile> multipartFileMap = new HashMap<>(bdcEsignFjxxDTOList.size());
        for (BdcEsignFjxxDTO esignFjxx : bdcEsignFjxxDTOList) {
            multipartFileMap.put(esignFjxx.getFjmc(), esignFjxx.getMultipartFile());
        }

        // 组织云签请求参数
        EsignDataDTO esignDataDTO = new EsignDataDTO();
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            for (BdcQlrDO qlrDO : bdcQlrDOList) {
                if (CommonConstantUtils.QLRLB_QLR.equals(qlrDO.getQlrlb())) {
                    esignDataDTO.setFqrzjh(qlrDO.getZjh());
                }
            }
        }
        esignDataDTO.setHddz(yqDzclConfig.getHdtz());
        esignDataDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
        esignDataDTO.setLsh(UUIDGenerator.generate16());
        esignDataDTO.setLcmc(bdcXmDOList.get(0).getGzldymc());
        // 是否意愿认证： 0否 1是
        esignDataDTO.setSfyyrz(Optional.ofNullable(yqDzclConfig.getSfyyrz()).orElse("0"));
        // 意愿认证方式：DXYZM（短信验证码）ZFBSL	(支付宝刷脸) TXYSL(腾讯云刷脸) EQBSL(E签宝刷脸) WXXCXSL(微信小程序刷脸) ZFBZNSPRZ(支付宝智能视频认证) WXZNSPRZ(微信智能视频认证)
        esignDataDTO.setYyrzfs(Optional.ofNullable(yqDzclConfig.getYyrzfs()).orElse(""));
        // 云签厂商代码
        esignDataDTO.setCsdm(yqDzclConfig.getCsdm());

        // 组织附件信息
        List<EsignFjxxDTO> esignFjxxDTOList = this.convertFjxx(bdcEsignFjxxDTOList);
        esignDataDTO.setFjxxList(esignFjxxDTOList);

        // 组织签署人信息，签署人文件信息等
        esignDataDTO.setQsrxxList(this.handlerQsrxx(bdcQlrDOList, bdcEsignFjxxDTOList, yqdzclConfig));

        //保存不动产云签附件信息
        this.saveBdcSlYqFjxx(bdcXmDOList.get(0), bdcEsignFjxxDTOList);

        Map<String, Object> param = new HashMap<>(2);
        param.put("fileParams", multipartFileMap);
        param.put("otherParams", esignDataDTO);
        Object response = exchangeInterfaceFeignService.requestInterface("esign_create", param);
        LOGGER.info("云签推送签署流程返回值信息为：{}", response.toString());
        // 推送完成后删除原始模板文件
        this.deleteMbwj(bdcEsignFjxxDTOList);
        return response;
    }

    // 保存云签附件信息
    private void saveBdcSlYqFjxx(BdcXmDO bdcXmDO, List<BdcEsignFjxxDTO> bdcEsignFjxxDTOList){
        List<BdcSlYqFjxxDO> bdcSlYqFjxxDOList = new ArrayList<>(bdcEsignFjxxDTOList.size());
        for(BdcEsignFjxxDTO bdcEsignFjxxDTO: bdcEsignFjxxDTOList ){
            BdcSlYqFjxxDO bdcSlYqFjxxDO = new BdcSlYqFjxxDO();
            bdcSlYqFjxxDO.setFjmc(bdcEsignFjxxDTO.getFjmc());
            bdcSlYqFjxxDO.setGzlslid(bdcXmDO.getGzlslid());
            bdcSlYqFjxxDO.setSlbh(bdcXmDO.getSlbh());
            bdcSlYqFjxxDO.setWjzxid(bdcEsignFjxxDTO.getFjid());
            bdcSlYqFjxxDO.setWjjmc(bdcEsignFjxxDTO.getWjjmc());
            bdcSlYqFjxxDOList.add(bdcSlYqFjxxDO);
        }
        if(CollectionUtils.isNotEmpty(bdcSlYqFjxxDOList)){
            this.bdcSlYqFjxxService.batchSaveBdcSlYqFjxx(bdcSlYqFjxxDOList);
        }
    }

    // 获取当前签署人所需要签署的文件内容
    private List<BdcEsignFjxxDTO> filterQsrwjxx(String qlrlb, List<BdcEsignFjxxDTO> bdcEsignFjxxDTOList, Map<String, String> yqdzclConfig){
        if(CollectionUtils.isNotEmpty(bdcEsignFjxxDTOList)){
            List<BdcEsignFjxxDTO> qsrwjxxList = new ArrayList<>(bdcEsignFjxxDTOList.size());
            String[] clmcList = yqdzclConfig.get(qlrlb).split(",");
            Map<String, BdcEsignFjxxDTO> fjxxMap = bdcEsignFjxxDTOList.stream().collect(Collectors.toMap(BdcEsignFjxxDTO::getClmc, Function.identity()));
            for(String clmc: clmcList){
                if(fjxxMap.containsKey(clmc)){
                    qsrwjxxList.add(fjxxMap.get(clmc));
                }
            }
            return qsrwjxxList;
        }
        return null;
    }

    // 根据配置内容，组织签署人、签署文件等信息
    private List<EsignQsrxxDTO> handlerQsrxx(List<BdcQlrDO> bdcQlrDOList, List<BdcEsignFjxxDTO> bdcEsignFjxxDTOList, Map<String, String> yqdzclConfig){
        List<EsignQsrxxDTO> esignQsrxxDTOList = new ArrayList<>(bdcQlrDOList.size());
        for(BdcQlrDO bdcQlrDO : bdcQlrDOList){
            EsignQsrxxDTO esignQsrxxDTO = new EsignQsrxxDTO();
            esignQsrxxDTO.setXm(bdcQlrDO.getQlrmc());
            esignQsrxxDTO.setZjlx(this.getDsfZdDzxx("BDC_YQ_ZJZL","zjlx", bdcQlrDO.getZjzl().toString()));
            esignQsrxxDTO.setZjh(bdcQlrDO.getZjh());

            // 权利人类型为企业时，设置代理人信息
            if(CommonConstantUtils.QLRLX_QY.equals(bdcQlrDO.getQlrlx())){
                esignQsrxxDTO.setQsrlb("1");
                esignQsrxxDTO.setLxdh(bdcQlrDO.getDlrdh());
                esignQsrxxDTO.setDlrList(this.generateDlrxx(bdcQlrDO));
            }else{
                esignQsrxxDTO.setQsrlb("0");
                esignQsrxxDTO.setLxdh(bdcQlrDO.getDh());
            }
            esignQsrxxDTO.setQmsx(String.valueOf(bdcQlrDO.getSxh()));

            //添加签署文件内容
            List<BdcEsignFjxxDTO> qswjxxList = this.filterQsrwjxx(bdcQlrDO.getQlrlb(), bdcEsignFjxxDTOList, yqdzclConfig);
            esignQsrxxDTO.setQswjList(this.generateQswjxx(qswjxxList, bdcQlrDO));
            esignQsrxxDTOList.add(esignQsrxxDTO);
        }

        return esignQsrxxDTOList;
    }

    //  生成代理人信息
    private List<EsignDlrDTO> generateDlrxx(BdcQlrDO bdcQlrDO){
        List<EsignDlrDTO> esignDlrDTOList = new ArrayList<>(1);
        EsignDlrDTO esignDlrDTO = new EsignDlrDTO();
        esignDlrDTO.setDlrmc(bdcQlrDO.getDlrmc());
        esignDlrDTO.setDlrlxdh(bdcQlrDO.getDlrdh());
        esignDlrDTO.setDlrzjh(bdcQlrDO.getDlrzjh());
        esignDlrDTO.setDlrzjlx(this.getDsfZdDzxx("BDC_YQ_ZJZL","zjlx", bdcQlrDO.getDlrzjzl()));
        esignDlrDTO.setDlrlx("1");
        esignDlrDTOList.add(esignDlrDTO);
        return esignDlrDTOList;
    }

    // 生成签署文件信息
    private List<EsignQswjDTO> generateQswjxx(List<BdcEsignFjxxDTO> fjxxList, BdcQlrDO bdcQlrDO){
        List<EsignQswjDTO> esignQswjDTOList = new ArrayList<>(10);
        if(CollectionUtils.isNotEmpty(fjxxList)){
            for(BdcEsignFjxxDTO fjxx: fjxxList){
                EsignQswjDTO esignQswjDTO = new EsignQswjDTO();
                esignQswjDTO.setFjid(fjxx.getFjid());
                esignQswjDTO.setQsxxList(this.generateQsxx(fjxx, bdcQlrDO));
                esignQswjDTOList.add(esignQswjDTO);
            }
        }
        return esignQswjDTOList;
    }

    // 生成签署信息
    private List<EsignQsxxDTO> generateQsxx(BdcEsignFjxxDTO fjxxDTO, BdcQlrDO bdcQlrDO){
        List<EsignQsxxDTO> esignQsxxDTOList = new ArrayList<>();
        EsignQsxxDTO esignQsxxDTO = new EsignQsxxDTO();
        esignQsxxDTO.setKey("签字区_" + bdcQlrDO.getZjh());
        esignQsxxDTO.setKeyIndex("-1");
        // 签署类型；0-不限(需用 户手动拖拽印章完成签 署，自动签署不支持)、 1-单页签、2-多页签、3- 骑缝章、4关键字签；5-坐标
        esignQsxxDTO.setQslx("4");
        // 签署位置类型: PERSON-个人,ORGANIZE-企业,LEGAL-法定代表 人,NOLIMIT-不限制;多 个用','隔开,默认 NOLIMIT
        if(CommonConstantUtils.QLRLX_QY.equals(bdcQlrDO.getQlrlx())){
            esignQsxxDTO.setQswzlx("ORGANIZE");
        }else{
            esignQsxxDTO.setQswzlx("PERSON");
        }
        esignQsxxDTO.setPosX(Optional.ofNullable(fjxxDTO.getPosx()).orElse("0"));
        esignQsxxDTO.setPosY(Optional.ofNullable(fjxxDTO.getPosy()).orElse("0"));
        esignQsxxDTOList.add(esignQsxxDTO);
        return esignQsxxDTOList;
    }

    // 根据配置内容yqdzclConfig<qlrlb,收件材料名称>，生成附件模板内容
    private List<BdcEsignFjxxDTO> generateBdcEsignFjxx(String gzlslid, List<BdcXmDO> bdcXmDOList, Map<String, String> yqdzclConfig){
        List<BdcEsignFjxxDTO> bdcEsignFjxxDTOList = new ArrayList<>(10);
        // 获取流程收件材料，用于申请单等打印使用
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
        // 根据配置 生成所需的文件内容
        // 生成模板的材料集合, 相同的材料名称只生成一份模板,供权利人义务人一起签字
        List<String> clmcLists = new ArrayList<>(10);
        for(String qlrlb: yqdzclConfig.keySet()){
            String[] clmcList = yqdzclConfig.get(qlrlb).split(",");
            for(String clmc: clmcList){
                if(clmcLists.contains(clmc)){
                  continue;
                }
                try{
                    BdcSlPrintDTO bdcSlPrintDTO = new BdcSlPrintDTO();
                    bdcSlPrintDTO.setZxlc("false");
                    bdcSlPrintDTO.setGzlslid(gzlslid);
                    YqDzclConfig.MbInfo mbxx = yqDzclConfig.getMbxx().get(clmc);
                    bdcSlPrintDTO.setDylx(mbxx.getDylx());
                    bdcSlPrintDTO.setQlrlb(qlrlb);
                    if(CollectionUtils.isNotEmpty(bdcSlSjclDOList)){
                        bdcSlPrintDTO.setSjclids(bdcSlSjclDOList.stream().map(BdcSlSjclDO::getSjclid).collect(Collectors.toList()));
                    }
                    // 生成模板文件
                    String fileName = clmc + bdcXmDOList.get(0).getXmid();
                    String pdfPath = this.getPdfPath(bdcSlPrintDTO,  bdcXmDOList, fileName);
                    // 文件路径转 Multipart 文件
                    MultipartFile multipartFile = this.getMultipartFile(pdfPath, fileName, fileName+CommonConstantUtils.WJHZ_PDF);
                    // 上传文件至大云，并关联收件材料，后续签字完成后，将文件进行替换
                    BdcEsignFjxxDTO bdcEsignFjxxDTO = new BdcEsignFjxxDTO();
                    bdcEsignFjxxDTO.setFjmc(fileName + CommonConstantUtils.WJHZ_PDF);
                    bdcEsignFjxxDTO.setMultipartFile(multipartFile);
                    bdcEsignFjxxDTO.setQlrlb(qlrlb);
                    bdcEsignFjxxDTO.setWjjmc(mbxx.getWjjmc());
                    bdcEsignFjxxDTO.setClmc(clmc);

                    StorageDto storageDto = this.uploadFileToWjzx(multipartFile, gzlslid, mbxx.getWjjmc(), fileName);
                    bdcEsignFjxxDTO.setFjid(storageDto.getId());
                    bdcEsignFjxxDTOList.add(bdcEsignFjxxDTO);
                    clmcLists.add(clmc);
                }catch(Exception e){
                    LOGGER.error("生成材料模板失败，材料名称：{}", clmc);
                    e.printStackTrace();
                }
            }
        }
        return bdcEsignFjxxDTOList;
    }

    /**
     * 上传文件至大云文件中心
     */
    private StorageDto uploadFileToWjzx(MultipartFile multipartFile, String gzlslid, String foldName, String fileName) throws IOException {
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
        bdcPdfDTO.setFoldName(foldName);
        bdcPdfDTO.setGzlslid(gzlslid);
        bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);
        bdcPdfDTO.setPdffjmc(fileName);
        StorageDto storageDto = bdcUploadFileUtils.upload(bdcPdfDTO, multipartFile);
        return storageDto;
    }

    // 生成文件模板
    private String getPdfPath(BdcSlPrintDTO bdcSlPrintDTO, List<BdcXmDO> bdcXmDOList, String fileName) throws IOException {
        String path = "";
        //1.现获取准确的打印类型
        String dylx = "";
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(bdcSlPrintDTO.getGzlslid());
            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }

        // 获取申请书打印类型
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQS)
                || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQSPL)){
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
            if (StringUtils.isNotBlank(bdcDjxlPzDO.getSqsdylx())){
                dylx = bdcDjxlPzDO.getSqsdylx();
            }
        }
        bdcSlPrintDTO.setDylx(StringUtils.isNotBlank(dylx) ? dylx : bdcSlPrintDTO.getDylx());
        bdcSlPrintDTO.setZxlc(StringUtils.isNotBlank(bdcSlPrintDTO.getZxlc()) ? bdcSlPrintDTO.getZxlc() : CommonConstantUtils.BOOL_FALSE);
        //模板路径
        String modelPath = printPath + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_DOCX;
        //生成xml
        //sjd和sqs获取xml方法不同
        String xml = "";
        if (bdcSlPrintDTO.getDylx().indexOf("sqs")> -1) {
            xml = bdcSlPrintFeignService.packPrintXml(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getZxlc(),
                    StringUtils.join(bdcSlPrintDTO.getSjclids(), ","), userManagerUtils.getCurrentUserName());
        }else if (bdcSlPrintDTO.getDylx().indexOf("clfmmht") > -1 || bdcSlPrintDTO.getDylx().indexOf("dyht") > -1) {
            //合肥存量房买卖合同只展示产权的传xmid生成xml
            xml = bdcSlPrintFeignService.packPrintXmlToXmid(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getZxlc(),
                    StringUtils.join(bdcSlPrintDTO.getSjclids(), ","), userManagerUtils.getCurrentUserName(), bdcSlPrintDTO.getXmid());
        }else if (CollectionUtils.isNotEmpty(djkDylxList) && djkDylxList.contains(bdcSlPrintDTO.getDylx())) {
            // 使用登记库数据源打印
            xml = bdcSlPrintFeignService.packPrintXml(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getZxlc(),
                    StringUtils.join(bdcSlPrintDTO.getSjclids(), ","),userManagerUtils.getCurrentUserName());
        }else{
            xml = bdcSlPrintFeignService.packPrintDatas(bdcSlPrintDTO.getGzlslid(), bdcSlPrintDTO.getDylx(), bdcSlPrintDTO.getQlrlb(),
                    bdcXmDOList.get(0).getXmid(), StringUtils.join(bdcSlPrintDTO.getSjclids(), ","));
        }

        LOGGER.info("dylx:{}生成xml：{}",bdcSlPrintDTO.getDylx(), xml);
        OfficeExportDTO officeExportDTO = new OfficeExportDTO();
        officeExportDTO.setModelName(modelPath);
        officeExportDTO.setXmlData(xml);
        officeExportDTO.setFileName(fileName);
        path = pdfController.generatePdfFile(officeExportDTO);
        return path;
    }

    // 文件地址转 MultipartFile
    private MultipartFile getMultipartFile(String filePath, String name, String originalFilename) throws IOException {
        String base64String = Base64Utils.getPDFBinary(new File(filePath));
        if(!base64String.startsWith("data:")){
            base64String = CommonConstantUtils.BASE64_QZ_PDF + base64String;
        }
        MultipartFile file = Base64Utils.base64ToMultipart(base64String);
        MultipartFile multipartFile = new MockMultipartFile(name, originalFilename, file.getContentType(), file.getInputStream());
        return multipartFile;
    }

    // 转换附件信息内容,将 BdcEsignFjxxDTO 对象转换为 EsignFjxxDTO
    private List<EsignFjxxDTO> convertFjxx(List<BdcEsignFjxxDTO> bdcEsignFjxxDTOList) {
        List<EsignFjxxDTO> esignFjxxDTOList = new ArrayList<>(bdcEsignFjxxDTOList.size());
        for (BdcEsignFjxxDTO bdcEsignFjxxDTO : bdcEsignFjxxDTOList) {
            EsignFjxxDTO fjxxDTO = new EsignFjxxDTO();
            fjxxDTO.setFjid(bdcEsignFjxxDTO.getFjid());
            fjxxDTO.setFjmc(bdcEsignFjxxDTO.getFjmc());
            esignFjxxDTOList.add(fjxxDTO);
        }
        return esignFjxxDTOList;
    }

    // 删除模板文件
    private void deleteMbwj(List<BdcEsignFjxxDTO> bdcEsignFjxxDTOList) {
        if (CollectionUtils.isNotEmpty(bdcEsignFjxxDTOList)) {
            List<String> fjidList = bdcEsignFjxxDTOList.stream().map(t -> t.getFjid()).collect(Collectors.toList());
            storageClient.deleteStorages(fjidList);
        }
    }

    // 获取第三方对照信息
    public String getDsfZdDzxx(String zdbs, String dsfzdbs, String bdczdz) {
        //数据归属地区进行对照
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setBdczdz(bdczdz);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfzdbs);
        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        LOGGER.info("---第三方字典返回值:{},查询参数:{}", result, bdcZdDsfzdgxDO);
        if (null != result && org.apache.commons.lang3.StringUtils.isNotBlank(result.getDsfzdz())) {
            return result.getDsfzdz();
        }
        return "";
    }
}
