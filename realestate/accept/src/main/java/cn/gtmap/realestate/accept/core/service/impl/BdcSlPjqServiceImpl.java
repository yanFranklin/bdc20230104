package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.config.AcceptPjqConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlPjqService;
import cn.gtmap.realestate.accept.core.service.BdcSlSjclService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcMkPjqRequestDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcMkqmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPjqDTO;
import cn.gtmap.realestate.common.core.dto.accept.SlymPjqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.mkpjq.DataBean;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.mkpjq.MkPjqDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlPjqQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 受理评价器实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-08-08 15:05
 **/
@Service
public class BdcSlPjqServiceImpl implements BdcSlPjqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlPjqServiceImpl.class);

    @Autowired
    Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    BdcQzxxFeginService bdcQzxxFeginService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    AcceptPjqConfig acceptPjqConfig;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    /**
     * 附件上传通用服务
     */
    @Autowired
    private BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcDypzFeignService bdcDypzFeignService;

    @Autowired
    BdcSlJbxxService bdcSlJbxxService;

    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;

    @Autowired
    PdfController pdfController;

    /**
     * @param ywbh 交易信息id
     * @param jdmc
     * @return 不动产受理评价器信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据业务编号获取评价器信息
     */
    @Override
    public BdcSlPjqDO queryBdcSlPjqByYwbh(String ywbh, String jdmc) {
        try {
            jdmc = URLDecoder.decode(jdmc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("根据业务编号获取评价器信息,节点名称解码失败，业务编号为：{}，节点名称为：{}", ywbh, jdmc);
        }
        List<BdcSlPjqDO> bdcSlPjqDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(ywbh)) {
            Example example = new Example(BdcSlPjqDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ywbh", ywbh);
            if (StringUtils.isNotBlank(jdmc)) {
                criteria.andEqualTo("jdmc", jdmc);

            }
            bdcSlPjqDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isNotEmpty(bdcSlPjqDOList)) {
            return bdcSlPjqDOList.get(0);
        } else {
            return null;
        }
    }

    /**
     * @param bdcSlPjqDO 交易信息
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理评价器信息
     */
    @Override
    public BdcSlPjqDO insertBdcSlPjq(BdcSlPjqDO bdcSlPjqDO) {
        if (bdcSlPjqDO != null) {
            if (StringUtils.isBlank(bdcSlPjqDO.getPjjlid())) {
                bdcSlPjqDO.setPjjlid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlPjqDO);
        }
        return bdcSlPjqDO;
    }

    /**
     * @param bdcSlPjqDO 不动产受理评价器
     * @return 保存数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不动产受理评价器信息
     */
    @Override
    public Integer updateBdcSlPjq(BdcSlPjqDO bdcSlPjqDO) {
        int result;
        if (bdcSlPjqDO != null && StringUtils.isNotBlank(bdcSlPjqDO.getPjjlid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlPjqDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    /**
     * @param bdcSlPjqDTO
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlPjqDTO 不动产受理评价器DTO
     * @return: List<Map < String, Object>> 受理评价统计信息
     * @description 分组查询评价统计信息
     */
    @Override
    public Page<BdcSlPjqDO> listBdcSlPjTjByPage(BdcSlPjqDTO bdcSlPjqDTO, Integer pageSize, Integer pageNumber) {
        String jsonobj = JSONObject.toJSONString(bdcSlPjqDTO);
        Map<String, String> map = (Map<String, String>) JSON.parse(jsonobj);
        Pageable page = new PageRequest(pageNumber, pageSize);
        return repo.selectPaging("listBdcSlPjTjByPage", map, page);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlPjqQO 不动产受理评价器DTO
     * @return: List<Map < String, Object>> 受理评价统计信息
     * @description 分组分组查询评价统计信息
     */
    @Override
    public Page<BdcSlPjqDTO> listGroupBdcSlPjTjByPage(BdcSlPjqQO bdcSlPjqQO, Integer pageSize, Integer pageNumber) {
        String jsonobj = JSONObject.toJSONString(bdcSlPjqQO);
        Map<String, String> map = (Map<String, String>) JSON.parse(jsonobj);
        Pageable page = new PageRequest(pageNumber, pageSize);
        return repo.selectPaging("listGroupBdcSlPjTj", map, page);
    }

    /**
     * @param bdcSlPjqQO
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: bdcSlPjqDTO 不动产受理评价器DTO
     * @return: List<BdcSlPjqDTO> 受理评价统计信息
     * @description 分组查询评价统计信息
     */
    @Override
    public List<BdcSlPjqDTO> listGroupBdcSlPjTj(BdcSlPjqQO bdcSlPjqQO) {
        String jsonobj = JSONObject.toJSONString(bdcSlPjqQO);
        Map<String, String> map = (Map<String, String>) JSON.parse(jsonobj);
        return repo.selectList("listGroupBdcSlPjTj", map);
    }

    @Override
    public SlymPjqDTO commonPj(String gzlslid, String taskid,String clientip){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("评价器未获取到工作流实例ID参数");
        }
        UserDto userDto =userManagerUtils.getCurrentUser();
        if(userDto ==null ||CollectionUtils.isEmpty(userDto.getOrgRecordList())){
            throw new AppException("评价器未获取到当前用户或用户未关联组织信息");
        }
        String pjqbb =getPjqbb(userDto.getOrgRecordList().get(0).getRegionCode());
        if(StringUtils.isBlank(pjqbb)){
            throw new AppException("评价器未获取到评价器版本信息,请检查配置,组织结构代码："+userDto.getOrgRecordList().get(0).getRegionCode());
        }
        //评价是否成功
        boolean isSuccess =false;
        SlymPjqDTO slymPjqDTO =initSlymPjqDTO(gzlslid,taskid,userDto);
        slymPjqDTO.setUrl(getPjqUrl(userDto.getOrgRecordList().get(0).getRegionCode(),slymPjqDTO));
        slymPjqDTO.setPjqbb(pjqbb);
        if(StringUtils.equals(CommonConstantUtils.PJQBB_MK_L,pjqbb)){
            //摩科老版本-前台组织url跳转
            if(StringUtils.isBlank(slymPjqDTO.getUrl())){
                throw new AppException("评价器未获取到摩科评价器地址,请检查配置,组织结构代码："+userDto.getOrgRecordList().get(0).getRegionCode());
            }
            isSuccess=true;

        }else if(StringUtils.equals(CommonConstantUtils.PJQBB_MK_N,pjqbb)){
            //摩科新版本-后台调用服务请求
            pjqXmk(clientip,slymPjqDTO,userDto);
            isSuccess =true;
        }else if(StringUtils.equals(CommonConstantUtils.PJQBB_QDWS,pjqbb)){
            //青大维森-前台调用ajax请求
            if(StringUtils.isBlank(slymPjqDTO.getUrl())){
                throw new AppException("评价器未获取到青大维森评价器地址,请检查配置,组织结构代码："+userDto.getOrgRecordList().get(0).getRegionCode());
            }
            //ajax请求成功后记录评价记录,此处不记录
        }
        if(isSuccess) {
            //保存评价记录
            savePjjl(slymPjqDTO, userDto);
        }
        return slymPjqDTO;

    }

    @Override
    public Object mkrzdb(String qlrmc, String qlrzjh, String gzlslid,String clientip){
        BdcMkPjqRequestDTO bdcMkPjqRequestDTO = new BdcMkPjqRequestDTO();
        bdcMkPjqRequestDTO.setSysIp(clientip);
        String qxdm = "";
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                bdcMkPjqRequestDTO.setServiceId(bdcXmDTOList.get(0).getSlbh());
                qxdm = bdcXmDTOList.get(0).getQxdm();
            }
        }
        bdcMkPjqRequestDTO.setIdCard(qlrzjh);
        bdcMkPjqRequestDTO.setTimeOut("60");
        bdcMkPjqRequestDTO.setQxdm(qxdm);
        String beanName = getRzbdBeanName(qxdm);
        LOGGER.info("当前流程受理编号{},beanName:{},调用摩科人证对比参数{}", bdcMkPjqRequestDTO.getServiceId(), beanName, JSON.toJSONString(bdcMkPjqRequestDTO));
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcMkPjqRequestDTO);
        LOGGER.info("当前人员{}人证对比返回参数{}", qlrzjh, JSON.toJSONString(response));
        MkPjqDTO mkPjqDTO = JSON.parseObject(JSON.toJSONString(response), MkPjqDTO.class);
        if (Objects.nonNull(mkPjqDTO) && Objects.equals(1, mkPjqDTO.getCode())) {
            //调用成功获取data里面的图片上传文件中心
            DataBean dataBean = mkPjqDTO.getData();
            // 处理连云港的返回
            String fjmc = qlrzjh + "人证对比结果";
            if ("mk_facevalidateWithidCard".equals(beanName)){
                fjmc = dataBean.getName() +"_"+dataBean.getCardNum()+ "_现场照片";
            }
            if (Objects.nonNull(dataBean) && StringUtils.isNotBlank(dataBean.getRealPhoto())) {
                try {
                    bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_IMAGE + dataBean.getRealPhoto(), gzlslid, "人证对比结果", fjmc, ".jpg");
                }catch (Exception e){
                    LOGGER.error("人证对比附件上传失败",e);
                }
            }
            if (Objects.nonNull(dataBean) && StringUtils.isNotBlank(dataBean.getIDCardPhoto())) {
                try {
                    bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_IMAGE + dataBean.getIDCardPhoto(), gzlslid, "人证对比结果", dataBean.getName() +"_"+dataBean.getCardNum() + "_身份证头像照片", ".jpg");
                }catch (Exception e){
                    LOGGER.error("人证对比的证件头像附件上传失败",e);
                }
            }
            if (Objects.nonNull(dataBean) && StringUtils.isNotBlank(dataBean.getZfmPhoto())) {
                try {
                    bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_IMAGE + dataBean.getZfmPhoto(), gzlslid, "人证对比结果", dataBean.getName() +"_"+dataBean.getCardNum() + "_身份证正反面", ".jpg");
                }catch (Exception e){
                    LOGGER.error("人证对比的证件正反面附件上传失败",e);
                }
            }
            if ("mk_facevalidateWithidCard".equals(beanName)){
                // 连云港上传人证比对结果pdf
                uploadRzbdjgPdf(gzlslid);
            }
        }
        return mkPjqDTO;
    }

    private void uploadRzbdjgPdf(String gzlslid) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<fetchdatas>\n" +
                "    $page\n" +
                "</fetchdatas>";
        // 从BDC_DYSJ_PZ获取xml模板
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx("rzbdjg");
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)){
            throw new AppException("未获取到人证比对结果的打印配置,请检查配置");
        }
        String dyzd = bdcDysjPzDOList.get(0).getDyzd();
        String pageXmlMb = dyzd.substring(dyzd.indexOf("<page>"),dyzd.indexOf("</page>")+7);
        List<String> pageXmlList = new ArrayList<>();
        // 生成人证比对结果pdf
        List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", gzlslid, null, "人证对比结果", 1, 0);
        if (CollectionUtils.isNotEmpty(listFjcl)) {
            StorageDto dto = listFjcl.get(0);
            String id = dto.getId();
            List<StorageDto> listFiles = storageClient.listAllSubsetStorages(id, null, null, null, null,null);
            if (CollectionUtils.isNotEmpty(listFiles)) {
                // 过滤jpg文件
                listFiles = listFiles.stream().filter(s -> s.getName().endsWith(".jpg")).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(listFiles)){
                    LOGGER.info("组织人证比对结果pdf，相关照片不存在！");
                    return;
                }
                // 根据姓名_身份证号分组
                Map<String,List<StorageDto>> map=listFiles.stream().collect(Collectors.groupingBy(i->i.getName().substring(0,i.getName().lastIndexOf("_"))));
                for (String key:map.keySet()){
                    String pageXml  = pageXmlMb;
                    String qlrmcXml = key.substring(0,key.indexOf("_"));
                    String qlrzjhXml = key.substring(key.lastIndexOf("_") + 1);
                    List<StorageDto> list = map.get(key);
                    String timeXml = "";
                    if (StringUtils.isNotBlank(list.get(0).getCurrentKey())) {
                        long timestamp = Long.parseLong(list.get(0).getCurrentKey());
                        if (list.get(0).getCurrentKey().length() == 13) {
                            timeXml = DateUtils.formateYmdhms(new Date(timestamp));
                        } else {
                            // 单位是秒，乘1000变毫秒
                            timeXml = DateUtils.formateYmdhms(new Date(timestamp * 1000L));
                        }
                    }
                    String xczpXml = "";
                    String sfzzpXml = "";
                    String zfmzpXml = "";
                    for (StorageDto storageDto:list){
                        if (storageDto.getName().contains("现场照片")){
                            xczpXml = storageDto.getDownUrl();
                        }
                        if (storageDto.getName().contains("身份证头像照片")){
                            sfzzpXml = storageDto.getDownUrl();
                        }
                        if (storageDto.getName().contains("身份证正反面")){
                            zfmzpXml = storageDto.getDownUrl();
                        }
                    }
                    pageXml = StringUtils.replace(pageXml,"$qlrmc",qlrmcXml);
                    pageXml = StringUtils.replace(pageXml,"$qlrzjh",qlrzjhXml);
                    pageXml = StringUtils.replace(pageXml,"$time",timeXml);
                    pageXml = StringUtils.replace(pageXml,"$xczp",xczpXml);
                    pageXml = StringUtils.replace(pageXml,"$sfzzp",sfzzpXml);
                    pageXml = StringUtils.replace(pageXml,"$zfmzp",zfmzpXml);
                    pageXmlList.add(pageXml);
                }
                StringBuilder pageXmlAll = new StringBuilder();
                if(CollectionUtils.isNotEmpty(pageXmlList)){
                    for (String str: pageXmlList){
                        pageXmlAll.append(str).append("\n");
                    }
                }
                xml = StringUtils.replace(xml,"$page",pageXmlAll.toString());
                LOGGER.info("=======xmlData:{}",xml);
                String pdfFilePath = generatePdfFile(xml);
                try {
                    File pdfFile = new File(pdfFilePath);
                    //3.IO流读取文件内容到byte数组
                    byte[] pdfData = Base64Utils.getPDFByteArr(pdfFile);
                    String pdfBinary = Base64Utils.encodeByteToBase64Str(pdfData);
                    bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + pdfBinary, gzlslid, "人证对比结果", "人证对比结果", ".pdf");
                }catch (Exception e){
                    LOGGER.error("人证对比结果附件上传失败",e);
                }
            }

        }
    }

    /**
     * 生成PDF文件
     * @param xmlData XML数据
     * @return {String} PDF文件路径
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    private String generatePdfFile(String xmlData) {
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "rzbd.docx");
        pdfExportDTO.setFileName("人证对比结果");
        pdfExportDTO.setXmlData(xmlData);
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        LOGGER.info("连云港人证比对结果pdf文件路径：{}", pdfFilePath);
        return pdfFilePath;
    }

    @Override
    public Object commonRzdb(String qlrmc, String qlrzjh,String gzlslid,String clientip){
        JSONObject jsonObject =new JSONObject();
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("评价器人证对比未获取到工作流实例ID参数");
        }
        UserDto userDto =userManagerUtils.getCurrentUser();
        if(userDto ==null ||CollectionUtils.isEmpty(userDto.getOrgRecordList())){
            throw new AppException("评价器人证对比未获取到当前用户或用户未关联组织信息");
        }
        String rzdbbb =getPjqbb(userDto.getOrgRecordList().get(0).getRegionCode());
        jsonObject.put("rzdbbb",rzdbbb);
        if(StringUtils.isBlank(rzdbbb)){
            throw new AppException("评价器人证对比未获取到评价器版本信息,请检查配置,组织结构代码："+userDto.getOrgRecordList().get(0).getRegionCode());
        }
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            throw new AppException("人证对比未获取到项目信息");
        }
        Map<String,String> rzdbMap =new HashMap<>();
        rzdbMap.put("qlrmc",qlrmc);
        rzdbMap.put("qlrzjh",qlrzjh);
        rzdbMap.put("slbh",bdcXmDTOList.get(0).getSlbh());
        if(StringUtils.equals(CommonConstantUtils.PJQBB_MK_L,rzdbbb)){
            String url =getRzdbUrl(userDto.getOrgRecordList().get(0).getRegionCode(),rzdbMap);
            //摩科老版本-前台组织url跳转
            if(StringUtils.isBlank(url)){
                throw new AppException("评价器未获取到摩科人证比对地址,请检查配置,组织结构代码："+userDto.getOrgRecordList().get(0).getRegionCode());
            }
            jsonObject.put("url",url);
        }else if(StringUtils.equals(CommonConstantUtils.PJQBB_MK_N,rzdbbb)){
            //摩科新版本-后台调用服务请求
            Object response= mkrzdb(qlrmc, qlrzjh, gzlslid, clientip);
            jsonObject.put("mkPjqDTO",response);
        }else if(StringUtils.equals(CommonConstantUtils.PJQBB_QDWS,rzdbbb)){
            String url =getRzdbUrl(userDto.getOrgRecordList().get(0).getRegionCode(),rzdbMap);
            jsonObject.put("url",url);
            //青大维森-前台调用ajax请求
            if(StringUtils.isBlank(url)){
                throw new AppException("评价器未获取到青大维森评价器地址,请检查配置,组织结构代码："+userDto.getOrgRecordList().get(0).getRegionCode());
            }
        }
        return jsonObject;

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 初始化评价器信息
      */
    private SlymPjqDTO initSlymPjqDTO(String gzlslid,String taskid,UserDto userDto){
        SlymPjqDTO slymPjqDTO = new SlymPjqDTO();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("调用评价器未获取项目信息");
        }
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_QLR, "");
        //同时根据名称和证件号去重
        List<BdcQlrDO> bdcQlrDOS = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));
        slymPjqDTO.setBdcXmDO(bdcXmDOList.get(0));
        if(userDto != null) {
            slymPjqDTO.setSlr(userDto.getAlias());
        }else {
            slymPjqDTO.setSlr(bdcXmDOList.get(0).getSlr());
        }
        slymPjqDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
        slymPjqDTO.setQxdm(bdcXmDOList.get(0).getQxdm());
        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
            slymPjqDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getQlrmc", ","));
            slymPjqDTO.setQlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDh", ","));
            slymPjqDTO.setDlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDlrmc", ","));
            slymPjqDTO.setDlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDlrdh", ","));
        }
        if (StringUtils.isNotBlank(taskid)) {
            TaskData taskData = processTaskClient.getTaskById(taskid);
            if (Objects.nonNull(taskData)) {
                slymPjqDTO.setJdmc(taskData.getTaskName());
            }
        }

        return slymPjqDTO;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据组织机构代码获取评价器版本
     */
    private String getPjqbb(String regionCode){
        if(StringUtils.isBlank(regionCode)){
            return acceptPjqConfig.getPjqbb();
        }
        Map<String, String> qxdmPjqbbMap = acceptPjqConfig.getQxdmPjqbbMap();
        if(MapUtils.isNotEmpty(qxdmPjqbbMap) &&qxdmPjqbbMap.containsKey(regionCode)){
            return qxdmPjqbbMap.get(regionCode);
        }
        return acceptPjqConfig.getPjqbb();
    }

    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据组织机构代码获取评价器的评价beanName
     */
    private String getPjBeanName(String qxdm) {
        if (StringUtils.isBlank(qxdm)) {
            // 不配，默认mk_pj
            return "mk_pj";
        }
        Map<String, String> qxdmPjBeanNameMap = acceptPjqConfig.getQxdmPjBeanNameMap();
        if (MapUtils.isNotEmpty(qxdmPjBeanNameMap) && qxdmPjBeanNameMap.containsKey(qxdm)) {
            return qxdmPjBeanNameMap.get(qxdm);
        }
        return "mk_pj";
    }

    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据组织机构代码获取评价器的人证比对beanName
     */
    private String getRzbdBeanName(String qxdm) {
        if (StringUtils.isBlank(qxdm)) {
            // 不配，默认mk_rlsb
            return "mk_rlsb";
        }
        Map<String, String> qxdmRzbdBeanNameMap = acceptPjqConfig.getQxdmRzbdBeanNameMap();
        if (MapUtils.isNotEmpty(qxdmRzbdBeanNameMap) && qxdmRzbdBeanNameMap.containsKey(qxdm)) {
            return qxdmRzbdBeanNameMap.get(qxdm);
        }
        return "mk_rlsb";
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据组织机构代码获取评价器URL
     */
    private String getPjqUrl(String regionCode,SlymPjqDTO slymPjqDTO){
        String url =acceptPjqConfig.getUrl();
        if(StringUtils.isNotBlank(regionCode)){
            Map<String, String> qxdmUrlMap = acceptPjqConfig.getQxdmUrlMap();
            if (MapUtils.isNotEmpty(qxdmUrlMap) && qxdmUrlMap.containsKey(regionCode)) {
                url = qxdmUrlMap.get(regionCode);
            }
        }
        if(StringUtils.isNotBlank(url)) {
            //url匹配参数
            Map<String,Object> paramMap =Object2MapUtils.object2MapExceptNull(slymPjqDTO);
            if(MapUtils.isNotEmpty(paramMap)){
                for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    url =url.replace("@{" + entry.getKey() + "}", paramMap.get(entry.getKey()).toString());
                }
            }
        }
        return url;

    }

//    /**
//     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
//     * @description  根据组织机构代码获取人证对比版本
//     */
//    private String getRzbdbb(String regionCode){
//        if(StringUtils.isBlank(regionCode)){
//            return acceptPjqConfig.getRzdbbb();
//        }
//        Map<String, String> qxdmRzdbbbMap = acceptPjqConfig.getQxdmRzdbbbMap();
//        if(MapUtils.isNotEmpty(qxdmRzdbbbMap) &&qxdmRzdbbbMap.containsKey(regionCode)){
//            return qxdmRzdbbbMap.get(regionCode);
//        }
//        return acceptPjqConfig.getRzdbbb();
//    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据组织机构代码获取人证对比URL
     */
    private String getRzdbUrl(String regionCode,Map<String,String> rzdbMap){
        String url =acceptPjqConfig.getUrl();
        if(StringUtils.isNotBlank(regionCode)){
            Map<String, String> qxdmUrlMap = acceptPjqConfig.getQxdmrzbdUrlMap();
            if (MapUtils.isNotEmpty(qxdmUrlMap) && qxdmUrlMap.containsKey(regionCode)) {
                url = qxdmUrlMap.get(regionCode);
            }
        }
        if(StringUtils.isNotBlank(url)) {
            //url匹配参数
            if(MapUtils.isNotEmpty(rzdbMap)){
                for (Map.Entry<String, String> entry : rzdbMap.entrySet()) {
                    url =url.replace("@{" + entry.getKey() + "}", rzdbMap.get(entry.getKey()));
                }
            }
        }
        return url;

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  摩科新版本-调用服务
      */
    private void pjqXmk(String clientip,SlymPjqDTO slymPjqDTO,UserDto userDto){
        BdcMkPjqRequestDTO bdcMkPjqRequestDTO =new BdcMkPjqRequestDTO();
        BeanUtils.copyProperties(slymPjqDTO, bdcMkPjqRequestDTO);
        bdcMkPjqRequestDTO.setYwbh(slymPjqDTO.getSlbh());
        if (StringUtils.isBlank(bdcMkPjqRequestDTO.getYwbh())) {
            throw new AppException("调用评价器缺失业务编号");
        }
        bdcMkPjqRequestDTO.setBlry(slymPjqDTO.getSlr());
        if(userDto != null){
            bdcMkPjqRequestDTO.setBlry(userDto.getAlias());
            bdcMkPjqRequestDTO.setName(userDto.getAlias());
            bdcMkPjqRequestDTO.setJobNum(userDto.getJobNumber());

        }
        bdcMkPjqRequestDTO.setSysIp(clientip);
        bdcMkPjqRequestDTO.setServiceId(slymPjqDTO.getSlbh());
        bdcMkPjqRequestDTO.setSqrxm(slymPjqDTO.getQlrmc());
        bdcMkPjqRequestDTO.setSqrlxfs(slymPjqDTO.getQlrlxfs());
        bdcMkPjqRequestDTO.setXzqdm(slymPjqDTO.getQxdm());
        String beanName = getPjBeanName(slymPjqDTO.getQxdm());
        LOGGER.info("当前流程受理编号{},调用摩科评价器业务参数{}", bdcMkPjqRequestDTO.getYwbh(), JSON.toJSONString(bdcMkPjqRequestDTO));
        LOGGER.info("beanName:{},调用摩科评价接口请求参数:{}", beanName, bdcMkPjqRequestDTO);
        Object response= exchangeInterfaceFeignService.requestInterface(beanName, bdcMkPjqRequestDTO);
        LOGGER.info("业务编号：{}调用摩科评价接口返回结果:{}",bdcMkPjqRequestDTO.getYwbh(),response);
        slymPjqDTO.setPjjg(response);
    }



    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  保存评价记录
      */
    @Override
    public void savePjjl(SlymPjqDTO slymPjqDTO,UserDto userDto){
        //先查询是否有数据，有数据更新评价时间，无数据，新增
        BdcSlPjqDO bdcSlPjqDO = queryBdcSlPjqByYwbh(slymPjqDTO.getSlbh(), slymPjqDTO.getJdmc());
        if (Objects.nonNull(bdcSlPjqDO)) {
            bdcSlPjqDO.setPjsj(new Date());
            updateBdcSlPjq(bdcSlPjqDO);
        } else {
            bdcSlPjqDO = new BdcSlPjqDO();
            bdcSlPjqDO.setPjsj(new Date());
            if(userDto != null) {
                bdcSlPjqDO.setBlryid(userDto.getId());
            }
            bdcSlPjqDO.setBlry(slymPjqDTO.getSlr());
            if(slymPjqDTO.getBdcXmDO() != null) {
                BdcXmDO bdcXmDO =slymPjqDTO.getBdcXmDO();
                bdcSlPjqDO.setYwmc(bdcXmDO.getGzldymc());
                bdcSlPjqDO.setYwblsj(bdcXmDO.getSlsj());
                bdcSlPjqDO.setDjbmdm(bdcXmDO.getDjbmdm());
                bdcSlPjqDO.setDjbmmc(bdcXmDO.getDjjg());
            }
            bdcSlPjqDO.setMyd(slymPjqDTO.getMyd());
            bdcSlPjqDO.setYwbh(slymPjqDTO.getSlbh());
            bdcSlPjqDO.setSqrxm(slymPjqDTO.getQlrmc());
            bdcSlPjqDO.setSqrlxfs(slymPjqDTO.getQlrlxfs());
            bdcSlPjqDO.setDlrmc(slymPjqDTO.getDlrmc());
            bdcSlPjqDO.setDlrlxfs(slymPjqDTO.getDlrlxfs());
            bdcSlPjqDO.setJdmc(slymPjqDTO.getJdmc());
            insertBdcSlPjq(bdcSlPjqDO);
        }
    }

    @Override
    public Object mkqz(String qlrmc, String qlrzjh,String gzlslid,String clientip){
        BdcMkPjqRequestDTO bdcMkPjqRequestDTO = new BdcMkPjqRequestDTO();
        bdcMkPjqRequestDTO.setSysIp(clientip);
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                bdcMkPjqRequestDTO.setServiceId(bdcXmDTOList.get(0).getSlbh());
                bdcMkPjqRequestDTO.setSignid(bdcXmDTOList.get(0).getXmid());
                bdcMkPjqRequestDTO.setQxdm(bdcXmDTOList.get(0).getQxdm());
            }
        }
        bdcMkPjqRequestDTO.setIdCard(qlrzjh);
        bdcMkPjqRequestDTO.setName(qlrmc);
        bdcMkPjqRequestDTO.setSignkey("");
        LOGGER.info("当前流程受理编号{},调用摩科签字参数{}", bdcMkPjqRequestDTO.getServiceId(), JSON.toJSONString(bdcMkPjqRequestDTO));
        Object response = exchangeInterfaceFeignService.requestInterface("mk_sendTxQm", bdcMkPjqRequestDTO);
        LOGGER.info("当前人员{}签字返回参数{}", qlrzjh, JSON.toJSONString(response));
        return response;

    }


    @Override
    public Object mktspj(String processInsId,String taskId,String clientip){
        BdcMkPjqRequestDTO bdcMkPjqRequestDTO = new BdcMkPjqRequestDTO();
        SlymPjqDTO slymPjqDTO =initSlymPjqDTO(processInsId,taskId,null);
        BeanUtils.copyProperties(slymPjqDTO, bdcMkPjqRequestDTO);
        bdcMkPjqRequestDTO.setYwbh(slymPjqDTO.getSlbh());
        bdcMkPjqRequestDTO.setXzqdm(slymPjqDTO.getQxdm());
        bdcMkPjqRequestDTO.setSqrxm(slymPjqDTO.getQlrmc());
        if (StringUtils.isBlank(bdcMkPjqRequestDTO.getYwbh())) {
            throw new AppException("调用评价器缺失业务编号");
        }
        bdcMkPjqRequestDTO.setBlry(slymPjqDTO.getSlr());
        bdcMkPjqRequestDTO.setSysIp(clientip);
        LOGGER.info("当前流程受理编号{},推送摩科评价业务参数{}", bdcMkPjqRequestDTO.getYwbh(), JSON.toJSONString(bdcMkPjqRequestDTO));
        LOGGER.info("推送摩科评价接口请求参数:{}",bdcMkPjqRequestDTO);
        Object response= exchangeInterfaceFeignService.requestInterface("mk_sendProv", bdcMkPjqRequestDTO);
        LOGGER.info("业务编号：{}推送摩科评价接口返回结果:{}",bdcMkPjqRequestDTO.getYwbh(),response);
        return response;
    }

    //
    @Override
    public Object mkqzhc(BdcMkqmDTO bdcMkqmDTO) throws IOException {
        if (Objects.isNull(bdcMkqmDTO)){
            throw new MissingArgumentException("摩科回传参数丢失");
        }
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        String gzlslid = "";
        if (StringUtils.isNotBlank(bdcMkqmDTO.getSlid())){
            bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(bdcMkqmDTO.getSlid(),null);
            gzlslid = bdcSlJbxxDO.getGzlslid();
        }

        //组织上传信息
        BdcPdfDTO bdcPdfDTO = new BdcPdfDTO();
        bdcPdfDTO.setBase64str(bdcMkqmDTO.getTablePdf());
        bdcPdfDTO.setFileSuffix(CommonConstantUtils.WJHZ_PDF);
        if (StringUtils.isNotBlank(gzlslid)){
            bdcPdfDTO.setGzlslid(gzlslid);
        }
        bdcPdfDTO.setPdffjmc(bdcMkqmDTO.getFjlx());
        LOGGER.info("组织上传附件中心工作流实例id,附件类型:{},{}", gzlslid, bdcMkqmDTO.getFjlx());
        bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
        return null;
    }
}
