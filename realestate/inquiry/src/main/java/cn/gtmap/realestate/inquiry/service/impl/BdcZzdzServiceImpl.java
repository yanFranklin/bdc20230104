package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.accept.AutoForwardTaskDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.moke.MokeZzdzjPlszExtendDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.hefei.moke.MokeHxyzhRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.shucheng.BdcZzdzRequestScDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcZzdzZmcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzZmxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzZmxxResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtZsyzhFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.core.entity.YhLqrDO;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZszmMapper;
import cn.gtmap.realestate.inquiry.service.BdcZzdzService;
import cn.gtmap.realestate.inquiry.util.Constants;
import cn.gtmap.realestate.inquiry.util.EndProcessUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BdcZzdzServiceImpl implements BdcZzdzService {

    @Autowired
    private Repo repo;
    /**
     * 用户操作
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    BdcXtZsyzhFeignService bdcXtZsyzhFeignService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcYzhFeignService bdcYzhFeignService;
    @Autowired
    EndProcessUtils endProcessUtils;
    @Autowired
    BdcZsFeignService BdcZsFeignService;
    @Autowired
    ECertificateFeignService eCertificateFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private BdcZszmMapper bdcZszmMapper;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;

    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    /**
     * 过滤掉不出证流程的证书信息
     */
    @Value("${zzdz.excludeDjxl:}")
    private String excludeDjxl;

    /**
     * 过滤掉不出证流程的证书信息 舒城
     */
    @Value("${zzdz.excludeDjxlSc:}")
    private String excludeDjxlSc;
    /**
     * 只保留领证方式为配置内容的证书信息
     */
    @Value("${zzdz.lzfs:}")
    private String lzfs;
    /**
     * 自助打证机相关接口，增加对于用户角色的权限配置
     */
    @Value("${zzdz.roleName:}")
    private String roleName;

    /**
     * 过滤掉不出证流程的证书信息 工作流定义id 舒城
     */
    @Value("${zzdz.gzldyidSc:}")
    private String gzldyid;
    /**
     * 自助打证机相关接口，转发到某个节点
     */
    @Value("${zzdz.forwardJdmc:发证}")
    private String forwardJdmc;


    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;

    /**
     * 是否需要先认领
     */
    @Value("${zzdz.sfrl: false}")
    private Boolean sfrl;

    /**
     * 是否需要先认领
     */
    @Value("#{'${zzdz.verify-yzh: }'.split(',')}")
    private List<String> verifyYzh;

    /**
     * 只查证书
     */
    @Value("#{'${zzdz.zdbj.zslx: }'.split(',')}")
    private List<Integer> zdbjZslx;

    /**
     * 自助打证管理员证件号,入参和配置相匹配不验证证件号
     */
    @Value("#{'${zzdz.admin.zjh:}'.split(',')}")
    private List<String> adminZjh;

    /**
     * 自助打证回写印制号是否自动办结
     */
    @Value("${zzdz.hxyzh.zdbj:true}")
    private boolean hxyzhZdbj;






    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZzdzServiceImpl.class);


    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 证书证明信息
     */
    @Override
    public List<BdcZzdzResponseDTO> getBdcZzdz(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        if (StringUtils.isNotEmpty(excludeDjxl)) {
            bdcZzdzRequestDTO.setExcludeDjxl(Arrays.asList(excludeDjxl.split(",")));
        }
        return repo.selectList("getZszmBybdcZzdzRequestDTO", bdcZzdzRequestDTO);
    }

    /**
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 回写印制号
     */
    @Override
    public Integer reWriteYzhToBdcZs(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        List<BdcZsidYzhGxDTO> bdcZsidYzhGxDTOList = bdcZzdzRequestDTO.getBdczsidYzhGxDto();
        Map map = new HashMap<>();
        int count = 0;
        String gzlslid = "";
        String gzldyid = "";
        for (int i = 0; i < bdcZsidYzhGxDTOList.size(); i++) {
            String yzhTemp = bdcZsidYzhGxDTOList.get(i).getQzysxlh();
            String zsidTemp = bdcZsidYzhGxDTOList.get(i).getZsid();

            BdcYzhQO bdcYzhQO = new BdcYzhQO();
            bdcYzhQO.setZslx(bdcZzdzRequestDTO.getZslx());
            bdcYzhQO.setQzysxlh(yzhTemp);
            List<BdcYzhDTO> listyzh = bdcYzhFeignService.queryListBdcYzh(bdcYzhQO);
            if (CollectionUtils.isEmpty(listyzh)) {
                throw new RuntimeException("本次回写的印制号不存在，请检查！");
            }

            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setZsid(zsidTemp);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> list = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(list)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(list.get(0).getXmid());
                List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(listxm)) {
                    map.put("slbh", listxm.get(0).getSlbh());
                    gzlslid = listxm.get(0).getGzlslid();
                    gzldyid = listxm.get(0).getGzldyid();
                }
            }
            int zslxTemp = bdcZzdzRequestDTO.getZslx();
            if (StringUtils.isEmpty(yzhTemp) || StringUtils.isEmpty(zsidTemp)) {
                throw new RuntimeException("参数不完整请检查！" + bdcZsidYzhGxDTOList.get(i).toString());
            }
            Date currentDate = new Date();
            String dateStr = DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm:ss");
            map.put("yzh", yzhTemp);
            map.put("zsid", zsidTemp);
            map.put("fzrq", dateStr);
            if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getZzdzjyhm()) && StringUtils.isNotBlank(bdcZzdzRequestDTO.getZzdzjIp())) {
                UserDto userDto = userManagerUtils.getUserByName(bdcZzdzRequestDTO.getZzdzjyhm());
                if (userDto != null) {
                    String fzridTemp = userDto.getId();
                    map.put("fzrid", fzridTemp);
                    map.put("fzr", userDto.getAlias());
                    map.put("zzdzjip", bdcZzdzRequestDTO.getZzdzjIp());

                }
            } else {
                map.put("fzr", Constants.ZZDZ_USERALIAS);
                map.put("fzrid", Constants.ZZDZ_USERID);
                // 合肥没有自助机的id回传
                UserDto userDto = userManagerUtils.getUserByName(Constants.ZZDZ_USERNAME);
                if (userDto != null) {
                    String fzridTemp = userDto.getId();
                    map.put("fzrid", fzridTemp);
                }

            }

            String sybmmc = userManagerUtils.getOrganizationByUserName(null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERNAME);// 使用部门名称
            map.put("sybmmc", sybmmc);
            map.put("zslx", zslxTemp);
            map.put("dyzt", CommonConstantUtils.SF_S_DM);
            // 预防回写的印制号是已使用的，这里需要加一个判断逻辑
            int isUsedQzysxlhCount = repo.selectOne("getCountByQzysxlh", map);
            if (isUsedQzysxlhCount > 0) {
                throw new RuntimeException("此次打证的印制号不可使用：" + bdcZsidYzhGxDTOList.get(i).getQzysxlh());
            }
            // 更新印制号的syqk为 已使用
            int yzhCount = repo.update("updateYzh", map);
            if (yzhCount == 0) {// 返回0，没有这条印制号
                throw new RuntimeException("此次打证的印制号不存在！");
            }

            BdcYzhQO yzhQo = new BdcYzhQO();
            yzhQo.setQzysxlh(yzhTemp);
            List<BdcYzhDTO> bdcYzhDTOList = bdcYzhFeignService.queryListBdcYzh(yzhQo);

            // 更新成功后需要新增一条使用明细数据
            // 生成使用明细
            BdcYzhsymxDO bdcYzhsymxDO = new BdcYzhsymxDO();
            bdcYzhsymxDO.setSyr(null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERALIAS);
            bdcYzhsymxDO.setSyrid(map.get("fzrid") == null ? "" : map.get("fzrid").toString());
            bdcYzhsymxDO.setSyyy(CommonConstantUtils.YZH_SYYY);
            bdcYzhsymxDO.setSyqk(CommonConstantUtils.SYQK_YSY);
            bdcYzhsymxDO.setSysj(new Date());
            bdcYzhsymxDO.setYzhid(bdcYzhDTOList.get(0).getYzhid());
            bdcYzhsymxDO.setSlbh(map.get("slbh") == null ? "" : map.get("slbh").toString());
            bdcYzhsymxDO.setSybmmc(sybmmc);
            bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcYzhsymxDO);

            // 更新证书表
            int updatezsCount = repo.update("reWriteYzhToBdcZs", map);
            if (updatezsCount > 0) {
                count++;
            }

            //开始上传附件，领证人的签字和人脸图片
            if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getLzrqzBase64())) {
                LOGGER.info("开始处理领证人签字附件 gzlslid{}", gzlslid);
                // 判断文件夹存不存在 不存在新建
                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "领证人签字文件", "");
                uploadLzrFj(bdcZzdzRequestDTO, zsidTemp, storageDto);
            }
            //领证人人像上传
            if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getLzrqzBase64())) {
                LOGGER.info("开始处理领证人签字附件 gzlslid{}", gzlslid);
                // 判断文件夹存不存在 不存在新建
                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "领证人人像文件", "");
                uploadLzrFj(bdcZzdzRequestDTO, zsidTemp, storageDto);
            }
        }

        try {
            //预告预抵押不验证证书是否都有印制号
            if (CollectionUtils.isNotEmpty(verifyYzh) && verifyYzh.contains(gzldyid)) {
                //舒城要求先认领到自助打证机下，再自动办结 查询该流程节点信息
                rlProcess(bdcZzdzRequestDTO, gzlslid);
                boolean flag = taskHandleClient.autoComplete(gzlslid, null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERNAME);
                LOGGER.info("不验证印制号是否都有值，手动办结结果{}，工作流实例id：{}", flag, gzlslid);
            } else {
                List<String> zsids = BdcZsFeignService.queryGzlZsid(gzlslid);
                LOGGER.info("获取当前流程下的所有zsid：{}", zsids);
                int zsCount = 0;
                // 多本证
                if (CollectionUtils.isNotEmpty(zsids) && zsids.size() > 1) {
                    Map zsMap = new HashMap();
                    zsMap.put("zsids", zsids);
                    zsCount = repo.selectOne("getAllzs", zsMap);
                }

                if (zsCount == 0) {
                    //舒城要求先认领到自助打证机下，再自动办结 查询该流程节点信息
                    rlProcess(bdcZzdzRequestDTO, gzlslid);
                    boolean flag = taskHandleClient.autoComplete(gzlslid, null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERNAME);
                    LOGGER.info("手动办结结果{}，工作流实例id：{}", flag, gzlslid);
                }
            }

        } catch (Exception e) {
            LOGGER.error("回写成功，手动办结失败，工作流实例id" + gzlslid, e);
        }


        return count;
    }

    /**
     * 是否进行认领流程操作
     *
     * @param bdcZzdzRequestDTO gzlslid
     * @return
     * @Date 2022/5/11
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void rlProcess(BdcZzdzRequestDTO bdcZzdzRequestDTO, String gzlslid) {
        if (sfrl) {
            LOGGER.info("开始认领！listTask{}", sfrl);

            List<TaskData> listTask = processTaskClient.processRunningTasks(gzlslid);
            if (CollectionUtils.isNotEmpty(listTask)) {
                LOGGER.info("查询节点信息！listTask{}", listTask.toString());

                //先认领流程
                String username = "";
                if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getZzdzjyhm())) {
                    username = bdcZzdzRequestDTO.getZzdzjyhm();
                } else {
                    username = Constants.ZZDZ_USERNAME;
                }

                boolean rl = taskHandleClient.taskClaim(username, Collections.singletonList(listTask.get(0).getTaskId()));
                if (!rl) {
                    LOGGER.info("认领失败！listTask{}", listTask.toString());
                }
            }
        }
    }

    private void uploadLzrFj(BdcZzdzRequestDTO bdcZzdzRequestDTO, String zsidTemp, StorageDto storageDto) {
        if (storageDto != null && CheckParameter.checkAnyParameter(storageDto)) {
            // 有图片内容(base64code)的上传
            LOGGER.info("自助打证机开始上传附件 fjmc:{}", zsidTemp);
            MultipartFile file = Base64Utils.base64ToMultipart(bdcZzdzRequestDTO.getLzrqzBase64());
            if (file != null) {
                try {
                    MultipartDto multipartDto = getUploadParamDto(storageDto, file.getBytes(), zsidTemp);
                    StorageDto dto = storageClient.multipartUpload(multipartDto);
                    LOGGER.info("附件上传成功 fjmc:{},storageid:{}", zsidTemp, dto.getId());
                } catch (IOException e) {
                    LOGGER.info("downFjcl {} 获取流异常：{}", zsidTemp, e);
                }
            }
        }
    }

    /**
     * @param bdcZzdzNtRequestDTO
     * @return List<BdcZzdzNtResultDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 证书证明信息
     */
    @Override
    public List<BdcZzdzNtResultDTO> getBdcZzdzNt(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO) {
        if (StringUtils.isNotEmpty(bdcZzdzNtRequestDTO.getExcludeWdid())) {
            bdcZzdzNtRequestDTO.setExcludeWdidList(Arrays.asList(bdcZzdzNtRequestDTO.getExcludeWdid().split(",")));
        }
        if (StringUtils.isNotBlank(lzfs)) {
            bdcZzdzNtRequestDTO.setLzfs(lzfs);
        }
        return repo.selectList("getZszmBybdcZzdzNtRequestDTO", bdcZzdzNtRequestDTO);
    }

    /**
     * @param bdcZzdzNtRequestDTO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 回写印制号
     */
    @Override
    @Transactional
    public BdcZzdzNtResponseDTO reWriteYzhToBdcZsNt(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO) {
        BdcZzdzNtResponseDTO dto = endProcessUtils.reWriteYzh(bdcZzdzNtRequestDTO);
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setQqsj(simpleDateFormat.format(today));
        dto.setData(null);

        //if(dto.getSuccess()){
        List<BdcXmDTO> listxm = bdcXmFeignService.listBdcXmBfxxBySlbh(bdcZzdzNtRequestDTO.getTranstionId());
        String gzlslid = "";
        if (CollectionUtils.isNotEmpty(listxm)) {
            gzlslid = listxm.get(0).getGzlslid();
        }
        List<String> zsids = BdcZsFeignService.queryGzlZsid(gzlslid);
        LOGGER.info("获取当前流程下的所有zsid：{}", zsids);
        int zsCount = 0;
        if (CollectionUtils.isNotEmpty(zsids) && zsids.size() > 1) {// 多本证
            Map zsMap = new HashMap();
            zsMap.put("zsids", zsids);
            zsCount = repo.selectOne("getAllzs", zsMap);
        }
        try {
            if (zsCount == 0) {// 所有证书都打过证了（有印制号）
                //  业务数据处理完后 需要调用办结接口办结这个流程
                //  改强制办结为自动办结，去除缮证环节出现办结按钮的情况
                //  taskHandleClient.mandatoryFinishProcess(gzlslid,Constants.ZZDZ_USERNAME);
                String username = StringUtils.isBlank(roleName) ? Constants.ZZDZ_USERNAME : bdcZzdzNtRequestDTO.getZzjUsername();
                //处理缮证人字段回写
                try {
                    Map<String, Object> paramMap = new HashMap<>(1);
                    paramMap.put("SZR", username);
                    LOGGER.info("-=-=-={}", paramMap.get("SZR").toString());
                    LOGGER.info("-=-=-=gzlslid{}", gzlslid);
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                } catch (Exception e) {
                    LOGGER.error("zs回写大云字段异常！gzlslid={},回写字段szr={}", bdcZzdzNtRequestDTO.getZzjUsername());
                }
                LOGGER.info("自主打证自动办结接口：{}，{}", gzlslid, username);
                taskHandleClient.autoComplete(gzlslid, username);
            }
        } catch (Exception e) {
            LOGGER.error("手动办结失败,工作流实例id" + gzlslid, e);
            dto.setMessage("操作成功，手动办结失败，请到登记系统办结！");
            dto.setSuccess(true);
            dto.setStatusCode(0);
            return dto;
        }
        //}
        return dto;
    }

    /**
     * @param bdcZzdzNtRequestDTO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 回写印制号
     */
    @Override
    @Transactional
    public BdcZzdzNtResponseDTO reWriteYzhToBdcZsYc(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO) {
        BdcZzdzNtResponseDTO dto = endProcessUtils.reWriteYzh(bdcZzdzNtRequestDTO);
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setQqsj(simpleDateFormat.format(today));
        dto.setData(null);

        //if(dto.getSuccess()){
        List<BdcXmDTO> listxm = bdcXmFeignService.listBdcXmBfxxBySlbh(bdcZzdzNtRequestDTO.getTranstionId());
        String gzlslid = "";
        if (CollectionUtils.isNotEmpty(listxm)) {
            gzlslid = listxm.get(0).getGzlslid();
        }
        List<String> zsids = BdcZsFeignService.queryGzlZsid(gzlslid);
        LOGGER.info("获取当前流程下的所有zsid：{}", zsids);
        int zsCount = 0;
        if (CollectionUtils.isNotEmpty(zsids) && zsids.size() > 1) {// 多本证
            List<BdcZsxxDTO> bdcZsxxDTOS = bdcZszmMapper.listBdcZsDOByZsids(zsids);
            zsCount = (int) bdcZsxxDTOS.stream().filter(bdcZsxxDTO -> !CommonConstantUtils.ZSLX_ZM.equals(bdcZsxxDTO.getZslx()) && bdcZsxxDTO.getQzysxlh() == null).count();
        }
        try {
            if (zsCount == 0) {// 所有证书都打过证了（有印制号）
                //  业务数据处理完后 需要调用办结接口办结这个流程
                //  改强制办结为自动办结，去除缮证环节出现办结按钮的情况
                //  taskHandleClient.mandatoryFinishProcess(gzlslid,Constants.ZZDZ_USERNAME);
                String username = StringUtils.isBlank(roleName) ? Constants.ZZDZ_USERNAME : bdcZzdzNtRequestDTO.getZzjUsername();
                //处理缮证人字段回写
                try {
                    Map<String, Object> paramMap = new HashMap<>(1);
                    paramMap.put("SZR", username);
                    LOGGER.info("-=-=-={}", paramMap.get("SZR").toString());
                    LOGGER.info("-=-=-=gzlslid{}", gzlslid);
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                } catch (Exception e) {
                    LOGGER.error("zs回写大云字段异常！gzlslid={},回写字段szr={}", bdcZzdzNtRequestDTO.getZzjUsername());
                }
                LOGGER.info("自主打证自动办结接口：{}，{}", gzlslid, username);
                taskHandleClient.autoComplete(gzlslid, username);
            }
        } catch (Exception e) {
            LOGGER.error("手动办结失败,工作流实例id" + gzlslid, e);
            dto.setMessage("操作成功，手动办结失败，请到登记系统办结！");
            dto.setSuccess(true);
            dto.setStatusCode(0);
            return dto;
        }
        //}
        return dto;
    }

    /**
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 回写印制号，值回写印制号
     */
    @Override
    public Integer reWriteYzhToBdcZsForStandard(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        List<BdcZsidYzhGxDTO> bdcZsidYzhGxDTOList = bdcZzdzRequestDTO.getBdczsidYzhGxDto();
        Map map = new HashMap<>();
        int count = 0;
        String gzlslid = "";
        for (int i = 0; i < bdcZsidYzhGxDTOList.size(); i++) {
            String yzhTemp = bdcZsidYzhGxDTOList.get(i).getQzysxlh();
            String zsidTemp = bdcZsidYzhGxDTOList.get(i).getZsid();

            BdcYzhQO bdcYzhQO = new BdcYzhQO();
            bdcYzhQO.setZslx(bdcZzdzRequestDTO.getZslx());
            bdcYzhQO.setQzysxlh(yzhTemp);
            List<BdcYzhDTO> listyzh = bdcYzhFeignService.queryListBdcYzh(bdcYzhQO);
            if (CollectionUtils.isEmpty(listyzh)) {
                throw new RuntimeException("本次回写的印制号不存在，请检查！");
            }

            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setZsid(zsidTemp);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> list = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(list)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(list.get(0).getXmid());
                List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(listxm)) {
                    map.put("slbh", listxm.get(0).getSlbh());
                    gzlslid = listxm.get(0).getGzlslid();
                }
            }
            int zslxTemp = bdcZzdzRequestDTO.getZslx();
            if (StringUtils.isEmpty(yzhTemp) || StringUtils.isEmpty(zsidTemp)) {
                throw new RuntimeException("参数不完整请检查！" + bdcZsidYzhGxDTOList.get(i).toString());
            }
            Date currentDate = new Date();
            String dateStr = DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm:ss");
            map.put("yzh", yzhTemp);
            map.put("zsid", zsidTemp);
            map.put("fzrq", dateStr);
            if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getZzdzjyhm()) && StringUtils.isNotBlank(bdcZzdzRequestDTO.getZzdzjIp())) {
                UserDto userDto = userManagerUtils.getUserByName(bdcZzdzRequestDTO.getZzdzjyhm());
                if (userDto != null) {
                    String fzridTemp = userDto.getId();
                    map.put("fzrid", fzridTemp);
                    map.put("fzr", userDto.getAlias());
                    map.put("zzdzjip", bdcZzdzRequestDTO.getZzdzjIp());

                }
            } else {
                map.put("fzr", Constants.ZZDZ_USERALIAS);
                map.put("fzrid", Constants.ZZDZ_USERID);
                // 合肥没有自助机的id回传
                UserDto userDto = userManagerUtils.getUserByName(Constants.ZZDZ_USERNAME);
                if (userDto != null) {
                    String fzridTemp = userDto.getId();
                    map.put("fzrid", fzridTemp);
                }

            }

            String sybmmc = userManagerUtils.getOrganizationByUserName(null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERNAME);// 使用部门名称
            map.put("sybmmc", sybmmc);
            map.put("zslx", zslxTemp);
            map.put("dyzt", CommonConstantUtils.SF_S_DM);
            // 预防回写的印制号是已使用的，这里需要加一个判断逻辑
            int isUsedQzysxlhCount = repo.selectOne("getCountByQzysxlh", map);
            if (isUsedQzysxlhCount > 0) {
                throw new RuntimeException("此次打证的印制号不可使用：" + bdcZsidYzhGxDTOList.get(i).getQzysxlh());
            }
            // 更新印制号的syqk为 已使用
            int yzhCount = repo.update("updateYzh", map);
            if (yzhCount == 0) {// 返回0，没有这条印制号
                throw new RuntimeException("此次打证的印制号不存在！");
            }

            BdcYzhQO yzhQo = new BdcYzhQO();
            yzhQo.setQzysxlh(yzhTemp);
            List<BdcYzhDTO> bdcYzhDTOList = bdcYzhFeignService.queryListBdcYzh(yzhQo);

            // 更新成功后需要新增一条使用明细数据
            // 生成使用明细
            BdcYzhsymxDO bdcYzhsymxDO = new BdcYzhsymxDO();
            bdcYzhsymxDO.setSyr(null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERALIAS);
            bdcYzhsymxDO.setSyrid(map.get("fzrid") == null ? "" : map.get("fzrid").toString());
            bdcYzhsymxDO.setSyyy(CommonConstantUtils.YZH_SYYY);
            bdcYzhsymxDO.setSyqk(CommonConstantUtils.SYQK_YSY);
            bdcYzhsymxDO.setSysj(new Date());
            bdcYzhsymxDO.setYzhid(bdcYzhDTOList.get(0).getYzhid());
            bdcYzhsymxDO.setSlbh(map.get("slbh") == null ? "" : map.get("slbh").toString());
            bdcYzhsymxDO.setSybmmc(sybmmc);
            bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcYzhsymxDO);

            // 更新证书表
            int updatezsCount = repo.update("reWriteYzhToBdcZs", map);
            if (updatezsCount > 0) {
                count++;
            }

            //开始上传附件，领证人的签字和人脸图片
            if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getLzrqzBase64())) {
                LOGGER.info("开始处理领证人签字附件 gzlslid{}", gzlslid);
                // 判断文件夹存不存在 不存在新建
                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "领证人签字文件", "");
                uploadLzrFj(bdcZzdzRequestDTO, zsidTemp, storageDto);
            }
            //领证人人像上传
            if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getLzrqzBase64())) {
                LOGGER.info("开始处理领证人签字附件 gzlslid{}", gzlslid);
                // 判断文件夹存不存在 不存在新建
                StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "领证人人像文件", "");
                uploadLzrFj(bdcZzdzRequestDTO, zsidTemp, storageDto);
            }
        }
        return count;
    }

    /**
     * @param bdcZzdzRequestDTO
     * @return 成功失败
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 自动办结
     */
    @Override
    public CommonResponse zdbjForZzdzj(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        List<BdcZsidYzhGxDTO> bdcZsidYzhGxDTOList = bdcZzdzRequestDTO.getBdczsidYzhGxDto();
        Map map = new HashMap<>();
        int count = 0;
        String gzlslid = "";
        for (int i = 0; i < bdcZsidYzhGxDTOList.size(); i++) {
            String zsidTemp = bdcZsidYzhGxDTOList.get(i).getZsid();
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setZsid(zsidTemp);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> list = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(list)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(list.get(0).getXmid());
                List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(listxm)) {
                    map.put("slbh", listxm.get(0).getSlbh());
                    gzlslid = listxm.get(0).getGzlslid();
                }
            }
        }
        try {
            List<String> zsids = BdcZsFeignService.queryGzlZsid(gzlslid);
            LOGGER.info("获取当前流程下的所有zsid：{}", zsids);
            int zsCount = 0;
            // 多本证
            if (CollectionUtils.isNotEmpty(zsids) && zsids.size() > 1) {
                Map zsMap = new HashMap();
                zsMap.put("zsids", zsids);
                zsCount = repo.selectOne("getAllzs", zsMap);
            }
            LOGGER.info("查出无印制号的证书数量为：{}，工作流实例id{}", zsCount, gzlslid);

            if (zsCount == 0) {
                boolean flag = taskHandleClient.autoComplete(gzlslid, null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERNAME);
                LOGGER.info("自助打证机办结结果{}，工作流实例id：{}", flag, gzlslid);
                if (flag) {
                    return CommonResponse.ok();
                } else {
                    return CommonResponse.fail("自动办结失败！gzlslid{},zsid{}", gzlslid, bdcZzdzRequestDTO.toString());
                }
            }

        } catch (Exception e) {
            LOGGER.info("办结失败，过程出错，工作流实例id" + gzlslid, e);
            return CommonResponse.fail("自动办结失败！");
        }
        return CommonResponse.fail("存在证书无印制！");
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private static MultipartDto getUploadParamDto(StorageDto storageDto, byte[] dataBytes, String fileName) throws
            IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        //盐城附件上传接口不覆盖重名文件
        multipartDto.setRename(0);
        if (dataBytes != null) {
            multipartDto.setData(dataBytes);
            // 因为外网附件有传excel等文件的可能性，所以contenttype设置为application/octet-stream
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(dataBytes.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(fileName);
        }
        return multipartDto;
    }

    /**
     * @param bdcZzdzRequestScDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 证书证明信息(舒城)
     */
    @Override
    public List<BdcZzdzResponseDTO> getBdcZzdzSc(BdcZzdzRequestScDTO bdcZzdzRequestScDTO) {
        if (StringUtils.isNotEmpty(excludeDjxlSc)) {
            bdcZzdzRequestScDTO.setExcludeDjxl(Arrays.asList(excludeDjxlSc.split(",")));
        }
        // 工作流定义id
        if (StringUtils.isNotEmpty(gzldyid)) {
            bdcZzdzRequestScDTO.setGzldyid(Arrays.asList(gzldyid.split(",")));
        }
        return repo.selectList("getZszmBybdcZzdzRequestScDTO", bdcZzdzRequestScDTO);
    }

    /**
     * @param bdcZzdzRequestDTO
     * @return BdcZzdzNtResponseDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 回写印制号_常州版本，特殊逻辑处理
     */
    @Override
    public Integer reWriteYzhToBdcZsForChangZhou(BdcZzdzRequestDTO bdcZzdzRequestDTO) {

        Map map = reWriteYzhRestructure(bdcZzdzRequestDTO);
        int count = (int) map.get("count");
        String gzlslid = (String) map.get("gzlslid");
        if (StringUtils.isNotBlank(gzlslid)) {
            try {
                //获取该流程所有证书证明信息，判断证书都有印制号，证明权利人是签约银行菜可办结
                BdcZsQO bdcZsQO = new BdcZsQO();
                bdcZsQO.setGzlslid(gzlslid);
                List<BdcZsDO> bdcZsDOList = BdcZsFeignService.listBdcZs(bdcZsQO);
                Boolean zdbj = true;
                zdbj = ForwardOrFinish(gzlslid, bdcZsDOList, zdbj);

                if (zdbj) {
                    LOGGER.info("可以自动办结zdbj{}，工作流实例id：{}", zdbj, gzlslid);
                    boolean flag = taskHandleClient.autoComplete(gzlslid, null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERNAME);
                    LOGGER.info("手动办结结果{}，工作流实例id：{}", flag, gzlslid);
                }

            } catch (Exception e) {
                LOGGER.error("回写成功，手动办结失败，工作流实例id" + gzlslid, e);
            }
        } else {
            LOGGER.info("工作流实例id为空，zsid为{}，未查到项目：{}", bdcZzdzRequestDTO.toString(), gzlslid);

        }


        return count;
    }

    /**
     * 判断权利人是否满足要求，能否去转发或者办结
     *
     * @param gzlslid gzlslid
     * @return Boolean
     * @Date 2021/12/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private Boolean ForwardOrFinish(String gzlslid, List<BdcZsDO> bdcZsDOList, Boolean zdbj) {
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            out:
            for (BdcZsDO bdcZsDO : bdcZsDOList) {
                if (CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx()) && StringUtils.isBlank(bdcZsDO.getQzysxlh())) {
                    LOGGER.info("该证书id无印刷号,zsid:{},证号：{}", bdcZsDO.getZsid(), bdcZsDO.getBdcqzh());
                    zdbj = false;
                    break;
                }
                //如果是证明，如果印制号为空，需要判断该证明的权利人是不是签约银行，不是不能自动办结！
                if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx()) && StringUtils.isBlank(bdcZsDO.getQzysxlh())) {
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setZsid(bdcZsDO.getZsid());
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> list = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if (CollectionUtils.isNotEmpty(list)) {
                        for (BdcQlrDO qlrDO : list) {
                            BdcXtJgDO bdcXtJgDO = new BdcXtJgDO();
                            bdcXtJgDO.setJgmc(qlrDO.getQlrmc());
                            bdcXtJgDO.setSfxyjg(CommonConstantUtils.SF_S_DM);
                            List<BdcXtJgDO> bdcXtJgList = bdcXtJgFeignService.queryBdcXtJg(bdcXtJgDO);
                            if (CollectionUtils.isEmpty(bdcXtJgList)) {
                                LOGGER.info("该证明的权利人不是签约银行！证明号为：{}，证书id为{}，权利人名称为：{}",
                                        bdcZsDO.getBdcqzh(), bdcZsDO.getZsid(), qlrDO.getQlrmc());
                                zdbj = false;
                                break out;
                            }
                        }
                    } else {
                        LOGGER.info("该证明的权利人为空！证明号为：{}，证书id为{}",
                                bdcZsDO.getBdcqzh(), bdcZsDO.getZsid());
                        zdbj = false;
                        break out;
                    }

                }
            }
        } else {
            zdbj = false;
            LOGGER.info("工作流实例id未查到证书信息：{},zdbj{}", gzlslid, zdbj);

        }
        return zdbj;
    }

    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:huangjian @gtmap.cn">huangjian</a>
     * @description 回写印制号_不办结！只转发到下个节点
     */
    @Override
    public Integer reWriteYzhToBdcZsForward(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        Map map = reWriteYzhRestructure(bdcZzdzRequestDTO);
        int count = (int) map.get("count");
        String gzlslid = (String) map.get("gzlslid");
        if (StringUtils.isNotBlank(gzlslid)) {
            try {
                //获取该流程所有证书证明信息，判断证书都有印制号，证明权利人是签约银行菜可办结
                BdcZsQO bdcZsQO = new BdcZsQO();
                bdcZsQO.setGzlslid(gzlslid);
                List<BdcZsDO> bdcZsDOList = BdcZsFeignService.listBdcZs(bdcZsQO);
                Boolean forWard = true;
                forWard = ForwardOrFinish(gzlslid, bdcZsDOList, forWard);

                if (forWard) {
                    LOGGER.info("可以转发下个节点{}，工作流实例id：{}", forWard, gzlslid);
                    AutoForwardTaskDTO autoForwardTaskDTO = new AutoForwardTaskDTO();
                    autoForwardTaskDTO.setJdmc(forwardJdmc);
                    try {
                        LOGGER.info("开始到受理那边转发：gzlslid{}", gzlslid);
                        LOGGER.info("开始到受理那边转发：gzlslid{},autoForwardTaskDTO", gzlslid, autoForwardTaskDTO);
                        bdcSlFeignService.wwsqAutoTurn(gzlslid, autoForwardTaskDTO);
                    } catch (Exception e) {
                        LOGGER.info("自助发证机批量打证转发到下一节点报错！", e);
                        e.printStackTrace();
                    }
                    LOGGER.info("转发结果结束，工作流实例id：{}", gzlslid);
                }

            } catch (Exception e) {
                LOGGER.error("回写成功，手动办结失败，工作流实例id" + gzlslid, e);
            }
        } else {
            LOGGER.info("工作流实例id为空，zsid为{}，未查到项目：{}", bdcZzdzRequestDTO.toString(), gzlslid);

        }


        return count;
    }

    /**
     * @param hxyzhRequestDTO@return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 合肥摩科回写印制号_此处为摩科新增专用
     */
    @Override
    public BdcCommonResponse mokeRewriteYzhToBdcZs(MokeHxyzhRequestDTO hxyzhRequestDTO) {
        Map map = new HashMap<>();
        int count = 0;
        String gzlslid = "";
        int zslxTemp = 0;
        if ("ZS".equals(hxyzhRequestDTO.getZSLX())) {
            zslxTemp = 1;
        } else {
            zslxTemp = 2;
        }
        //查询印制是否可用
        BdcYzhQO bdcYzhQO = new BdcYzhQO();
        bdcYzhQO.setZslx(zslxTemp);
        bdcYzhQO.setQzysxlh(hxyzhRequestDTO.getQZYSXLH());
        List<BdcYzhDTO> listyzh = bdcYzhFeignService.queryListBdcYzh(bdcYzhQO);
        if (CollectionUtils.isEmpty(listyzh)) {
            return BdcCommonResponse.fail("400", "本次回写的印制号不存在，请检查！");
        }
        //查询工作流实例id
        gzlslid = bdcXmFeignService.queryGzlslid(hxyzhRequestDTO.getYWBH());
        //根据证号查询证书表信息
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(hxyzhRequestDTO.getBDCQZH());
        List<BdcZsDO> zsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isNotEmpty(zsDOList)) {
            map.put("zsid", zsDOList.get(0).getZsid());
        } else {
            return BdcCommonResponse.fail("400", "这本证不存在，请检查！");

        }
        map.put("slbh", hxyzhRequestDTO.getYWBH());

        Date currentDate = new Date();
        String dateStr = DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm:ss");
        map.put("yzh", hxyzhRequestDTO.getQZYSXLH());
        map.put("fzrq", dateStr);
        UserDto zzdzUser =null;
        String sybmmc ="";
        //优先读取入参中的zzjid作为用户名
        if (StringUtils.isNotBlank(hxyzhRequestDTO.getZZJID())) {
            zzdzUser = userManagerUtils.getUserByName(hxyzhRequestDTO.getZZJID());
            if (zzdzUser != null) {
                String fzridTemp = zzdzUser.getId();
                map.put("fzrid", fzridTemp);
                map.put("fzr", zzdzUser.getAlias());
                //合肥要求将操作人记录在ip字段中
                map.put("zzdzjip", hxyzhRequestDTO.getCZRMC());
            }
        }
        if(zzdzUser ==null) {
            map.put("fzr", Constants.ZZDZ_USERALIAS);
            map.put("fzrid", Constants.ZZDZ_USERID);
            //合肥要求将操作人记录在ip字段中
            map.put("zzdzjip", hxyzhRequestDTO.getCZRMC());

            // 合肥没有自助机的id回传
            zzdzUser = userManagerUtils.getUserByName(Constants.ZZDZ_USERNAME);
            if (zzdzUser != null) {
                String fzridTemp = zzdzUser.getId();
                map.put("fzrid", fzridTemp);
                map.put("fzr",zzdzUser.getAlias());
            }
        }
        if(zzdzUser != null &&CollectionUtils.isNotEmpty(zzdzUser.getOrgRecordList())){
            sybmmc =zzdzUser.getOrgRecordList().get(0).getName();
        }
        map.put("sybmmc", sybmmc);
        map.put("zslx", zslxTemp);
        map.put("dyzt", CommonConstantUtils.SF_S_DM);
        // 预防回写的印制号是已使用的，这里需要加一个判断逻辑
        int isUsedQzysxlhCount = repo.selectOne("getCountByQzysxlh", map);
        if (isUsedQzysxlhCount > 0) {
            return BdcCommonResponse.fail("400", "此次打证的印制号不可使用：" + hxyzhRequestDTO.getQZYSXLH());
        }
        // 更新印制号的syqk为 已使用
        int yzhCount = repo.update("updateYzh", map);
        if (yzhCount == 0) {// 返回0，没有这条印制号
            throw new AppException(400, "此次打证的印制号不存在！");
        }
        BdcYzhQO yzhQo = new BdcYzhQO();
        yzhQo.setQzysxlh(hxyzhRequestDTO.getQZYSXLH());
        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhFeignService.queryListBdcYzh(yzhQo);

        // 更新成功后需要新增一条使用明细数据
        // 生成使用明细
        BdcYzhsymxDO bdcYzhsymxDO = new BdcYzhsymxDO();
        bdcYzhsymxDO.setSyr(null != hxyzhRequestDTO.getZZJID() ? hxyzhRequestDTO.getZZJID() : Constants.ZZDZ_USERALIAS);
        bdcYzhsymxDO.setSyrid(map.get("fzrid") == null ? "" : map.get("fzrid").toString());
        bdcYzhsymxDO.setSyyy(CommonConstantUtils.YZH_SYYY);
        bdcYzhsymxDO.setSyqk(CommonConstantUtils.SYQK_YSY);
        bdcYzhsymxDO.setSysj(new Date());
        bdcYzhsymxDO.setYzhid(bdcYzhDTOList.get(0).getYzhid());
        bdcYzhsymxDO.setSlbh(map.get("slbh") == null ? "" : map.get("slbh").toString());
        bdcYzhsymxDO.setSybmmc(sybmmc);
        bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate16());
        entityMapper.insertSelective(bdcYzhsymxDO);

        // 更新证书表
        int updatezsCount = repo.update("reWriteYzhToBdcZs", map);
        if (updatezsCount > 0) {
            count++;
        }
        //开始上传附件，领证人的签字和人脸图片
        if (StringUtils.isNotBlank(hxyzhRequestDTO.getLZRZP())) {
            LOGGER.info("moke开始处理领证人签字附件 gzlslid{}", gzlslid);
            // 判断文件夹存不存在 不存在新建
            StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "领证人签字文件", "");
            uploadLzrFjForMoke(hxyzhRequestDTO, hxyzhRequestDTO.getQZYSXLH(), storageDto);
        }
        //领证人人像上传
        if (StringUtils.isNotBlank(hxyzhRequestDTO.getLZRZP())) {
            LOGGER.info("moke开始处理领证人签字附件 gzlslid{}", gzlslid);
            // 判断文件夹存不存在 不存在新建
            StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "领证人人像文件", "");
            uploadLzrFjForMoke(hxyzhRequestDTO, hxyzhRequestDTO.getQZYSXLH(), storageDto);
        }
        if(hxyzhZdbj) {
            try {
                List<String> zsids = BdcZsFeignService.queryGzlZsid(gzlslid);
                LOGGER.info("moke获取当前流程下的所有zsid：{}", zsids);
                int zsCount = 0;
                // 多本证
                if (CollectionUtils.isNotEmpty(zsids) && zsids.size() >= 1) {
                    Map zsMap = new HashMap();
                    zsMap.put("zsids", zsids);
                    if (CollectionUtils.isNotEmpty(zdbjZslx) &&zdbjZslx.get(0) !=null) {
                        zsMap.put("zslx", zdbjZslx);
                        LOGGER.info("查询指定证书类型和zsid：{},{}", zdbjZslx.toString(), zsids);
                        zsCount = repo.selectOne("getAllzs", zsMap);
                        LOGGER.info("查询指定证书类型的条数为：{}", zsCount);
                    } else {
                        zsCount = repo.selectOne("getAllzs", zsMap);
                        LOGGER.info("查询证书的条数为：{},zsids:{}", zsCount, zsids);

                    }
                }

                if (zsCount == 0) {
                    boolean flag = taskHandleClient.autoComplete(gzlslid, null != hxyzhRequestDTO.getZZJID() ? hxyzhRequestDTO.getZZJID() : Constants.ZZDZ_USERNAME);
                    LOGGER.info("moke手动办结结果{}，工作流实例id：{}", flag, gzlslid);
                }

            } catch (Exception e) {
                LOGGER.error("moke回写成功，手动办结失败，工作流实例id" + gzlslid, e);
            }
        }
        return BdcCommonResponse.ok("success");
    }

    /**
     * @param hxyzhRequestDTO @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 合肥摩科查询打证信息接口
     */
    @Override
    public List<MokeZzdzjPlszExtendDTO> mokeQueryDzxx(MokeHxyzhRequestDTO hxyzhRequestDTO) {

        return bdcZszmMapper.queryMokeDzxx(hxyzhRequestDTO);
    }

    /**
     * @param yhLqrDO
     * @return YhLqrDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据领取人姓名和证件号查银行
     */
    @Override
    public YhLqrDO getYhBylqr(YhLqrDO yhLqrDO) {

        return bdcZszmMapper.queryYhByLqr(yhLqrDO);
    }

    @Override
    public BdcZzdzZmxxResponseDTO mokeQueryDjzmxx(BdcZzdzZmcxRequestDTO bdcZzdzZmcxRequestDTO){
        //参数校验
        if(bdcZzdzZmcxRequestDTO ==null ||StringUtils.isBlank(bdcZzdzZmcxRequestDTO.getYwlsh()) ||StringUtils.isBlank(bdcZzdzZmcxRequestDTO.getLzrzjh()) ||StringUtils.isBlank(bdcZzdzZmcxRequestDTO.getZzjId())){
            return BdcZzdzZmxxResponseDTO.fail("0", "缺失必要参数ywlsh,lzrzjh,zzjid");
        }
        try {
            //验证项目数据
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(bdcZzdzZmcxRequestDTO.getYwlsh());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                return BdcZzdzZmxxResponseDTO.fail("0", "业务编号错误");
            }
            //验证权利是否现势
            if (!CommonConstantUtils.QSZT_VALID.equals(bdcXmDOList.get(0).getQszt())) {
                return BdcZzdzZmxxResponseDTO.fail("0", "该业务权利不是现势状态");
            }
            //验证业务是否已办结
            if (!CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDOList.get(0).getAjzt())) {
                return BdcZzdzZmxxResponseDTO.fail("0", "该业务未缴费");
            }
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(bdcXmDOList.get(0).getGzlslid(), CommonConstantUtils.QLRLB_QLR, "");
            if (CollectionUtils.isEmpty(bdcQlrDOList)) {
                return BdcZzdzZmxxResponseDTO.fail("0", "该业务无权利人数据");
            }
            List<String> qlrmcList =bdcQlrDOList.stream().map(BdcQlrDO::getQlrmc).distinct().collect(Collectors.toList());
            Map<String,List<String>> jgLzrMap =new HashMap<>();
            for(String qlrmc:qlrmcList){
                BdcXtJgDO bdcXtJgDO =bdcXtJgFeignService.queryJgByJgmc(qlrmc,"1");
                if(bdcXtJgDO != null &&StringUtils.isNotBlank(bdcXtJgDO.getJgid())){
                    List<BdcJgLzrGxDO> jgLzrGxDOList =bdcXtJgFeignService.queryJgLzrByJgid(bdcXtJgDO.getJgid());
                    if(CollectionUtils.isNotEmpty(jgLzrGxDOList)){
                        List<String> lzrzjhList =jgLzrGxDOList.stream().map(BdcJgLzrGxDO::getLzrzjh).distinct().collect(Collectors.toList());
                        jgLzrMap.put(bdcXtJgDO.getJgmc(),lzrzjhList);
                    }
                }
            }
            //证件号验证
            List<BdcQlrDO> qlrList = new ArrayList<>();
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                //如果入参证件号为管理员证件号,无需验证证件号
                if (StringUtils.equals(bdcZzdzZmcxRequestDTO.getLzrzjh(), bdcQlrDO.getZjh()) || StringUtils.equals(bdcZzdzZmcxRequestDTO.getLzrzjh(), bdcQlrDO.getDlrzjh()) ||adminZjh.contains(bdcZzdzZmcxRequestDTO.getLzrzjh())) {
                    qlrList.add(bdcQlrDO);
                }else if(jgLzrMap.containsKey(bdcQlrDO.getQlrmc())){
                    //机构领证人
                    List<String> lzrzjhList =jgLzrMap.get(bdcQlrDO.getQlrmc());
                    if(lzrzjhList.contains(bdcZzdzZmcxRequestDTO.getLzrzjh())){
                        qlrList.add(bdcQlrDO);
                    }
                }

            }
            if (CollectionUtils.isEmpty(qlrList)) {
                return BdcZzdzZmxxResponseDTO.fail("0", "证件号错误");
            }
            List<String> zsidList = qlrList.stream().filter(t -> StringUtils.isNotBlank(t.getZsid()))
                    .map(BdcQlrDO::getZsid).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(zsidList)) {
                return BdcZzdzZmxxResponseDTO.fail("0", "该业务无证书数据");
            }
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setZsidList(zsidList);
            bdcZsQO.setGzlslid(bdcXmDOList.get(0).getGzlslid());
            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isEmpty(bdcZsDOList)) {
                return BdcZzdzZmxxResponseDTO.fail("0", "该业务无证书数据");
            }
            //查询证明
            bdcZsDOList = bdcZsDOList.stream().filter(zs -> CommonConstantUtils.ZSLX_ZM.equals(zs.getZslx())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(bdcZsDOList)) {
                return BdcZzdzZmxxResponseDTO.fail("0", "该业务不是证明类业务");
            }
            //查询未打证数据
            bdcZsDOList = bdcZsDOList.stream().filter(zs -> StringUtils.isBlank(zs.getQzysxlh()) && !CommonConstantUtils.SF_S_DM.equals(zs.getDyzt())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(bdcZsDOList)) {
                return BdcZzdzZmxxResponseDTO.fail("0", "该业务已打证");
            }
            List<String> bdcdyhList = bdcXmDOList.stream().filter(xm -> CommonConstantUtils.QLLX_DYAQ_DM.equals(xm.getQllx())).map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
            //抵押物清单
            String dywqd = "";
            if (bdcdyhList.size() > 1) {
                BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
                bdcPrintDTO.setDylx(CommonConstantUtils.DYWQD_DYLX);
                bdcPrintDTO.setGzlslid(bdcXmDOList.get(0).getGzlslid());
                bdcPrintDTO.setQlr(bdcXmDOList.get(0).getQlr());
                BdcUrlDTO bdcUrlDTO = new BdcUrlDTO();
                bdcUrlDTO.setRegisterUiUrl("/realestate-register-ui");
                bdcPrintDTO.setBdcUrlDTO(bdcUrlDTO);
                dywqd = bdcBdcdyFeignService.getDywqdPdf(bdcPrintDTO);
            }
            List<BdcZzdzZmxxDTO> bdcZzdzZmxxDTOList = new ArrayList<>();
            for (BdcZsDO bdcZsDO : bdcZsDOList) {
                BdcZzdzZmxxDTO bdcZzdzZmxxDTO = new BdcZzdzZmxxDTO();
                BeanUtils.copyProperties(bdcZsDO, bdcZzdzZmxxDTO);
                bdcZzdzZmxxDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
                bdcZzdzZmxxDTO.setDjsj(bdcXmDOList.get(0).getDjsj());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(bdcZzdzZmxxDTO.getDjsj());
                bdcZzdzZmxxDTO.setDjn(calendar.get(Calendar.YEAR) + "");
                bdcZzdzZmxxDTO.setDjy(calendar.get(Calendar.MONTH) + 1 + "");
                bdcZzdzZmxxDTO.setDjr(calendar.get(Calendar.DATE) + "");
                bdcZzdzZmxxDTO.setDywqd(dywqd);
                bdcZzdzZmxxDTOList.add(bdcZzdzZmxxDTO);
            }

            return BdcZzdzZmxxResponseDTO.ok(bdcZzdzZmxxDTOList, "success");
        }catch (Exception e){
            LOGGER.error("{}获取登记证明查询信息服务异常",bdcZzdzZmcxRequestDTO.getYwlsh(),e);
            return BdcZzdzZmxxResponseDTO.fail("0","获取登记证明查询信息服务异常");
        }
    }



    private void uploadLzrFjForMoke(MokeHxyzhRequestDTO hxyzhRequestDTO, String fjmc, StorageDto storageDto) {
        if (storageDto != null && CheckParameter.checkAnyParameter(storageDto)) {
            // 有图片内容(base64code)的上传
            LOGGER.info("摩科自助打证机开始上传附件 fjmc:{}", fjmc);
            MultipartFile file = Base64Utils.base64ToMultipart(hxyzhRequestDTO.getLZRZP());
            if (file != null) {
                try {
                    MultipartDto multipartDto = getUploadParamDto(storageDto, file.getBytes(), fjmc);
                    StorageDto dto = storageClient.multipartUpload(multipartDto);
                    LOGGER.info("摩科附件上传成功 fjmc:{},storageid:{}", fjmc, dto.getId());
                } catch (IOException e) {
                    LOGGER.info("摩科downFjcl {} 获取流异常：{}", fjmc, e);
                }
            }
        }
    }

    /**
     * 共用方法
     *
     * @param bdcZzdzRequestDTO
     * @return
     * @Date 2021/11/18
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Map reWriteYzhRestructure(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        List<BdcZsidYzhGxDTO> bdcZsidYzhGxDTOList = bdcZzdzRequestDTO.getBdczsidYzhGxDto();
        Map map = new HashMap<>();
        Map returnMap = new HashMap(2);
        int count = 0;
        String gzlslid = "";

        for (int i = 0; i < bdcZsidYzhGxDTOList.size(); i++) {
            String yzhTemp = bdcZsidYzhGxDTOList.get(i).getQzysxlh();
            String zsidTemp = bdcZsidYzhGxDTOList.get(i).getZsid();

            BdcYzhQO bdcYzhQO = new BdcYzhQO();
            bdcYzhQO.setZslx(bdcZzdzRequestDTO.getZslx());
            bdcYzhQO.setQzysxlh(yzhTemp);
            List<BdcYzhDTO> listyzh = bdcYzhFeignService.queryListBdcYzh(bdcYzhQO);
            if (CollectionUtils.isEmpty(listyzh)) {
                throw new RuntimeException("本次回写的印制号不存在，请检查！");
            }

            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setZsid(zsidTemp);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> list = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(list)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(list.get(0).getXmid());
                List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(listxm)) {
                    map.put("slbh", listxm.get(0).getSlbh());
                    gzlslid = listxm.get(0).getGzlslid();
                }
            } else {
                throw new RuntimeException("未查到项目信息！请检查！");
            }
            int zslxTemp = bdcZzdzRequestDTO.getZslx();
            if (StringUtils.isEmpty(yzhTemp) || StringUtils.isEmpty(zsidTemp)) {
                throw new RuntimeException("参数不完整请检查！" + bdcZsidYzhGxDTOList.get(i).toString());
            }
            Date currentDate = new Date();
            String dateStr = DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm:ss");
            map.put("yzh", yzhTemp);
            map.put("zsid", zsidTemp);
            map.put("fzrq", dateStr);
            if (StringUtils.isNotBlank(bdcZzdzRequestDTO.getZzdzjyhm()) && StringUtils.isNotBlank(bdcZzdzRequestDTO.getZzdzjIp())) {
                UserDto userDto = userManagerUtils.getUserByName(bdcZzdzRequestDTO.getZzdzjyhm());
                if (userDto != null) {
                    String fzridTemp = userDto.getId();
                    map.put("fzrid", fzridTemp);
                    map.put("fzr", userDto.getAlias());
                    map.put("zzdzjip", bdcZzdzRequestDTO.getZzdzjIp());

                }
            } else {
                map.put("fzr", Constants.ZZDZ_USERALIAS);
                map.put("fzrid", Constants.ZZDZ_USERID);
                // 合肥没有自助机的id回传
                UserDto userDto = userManagerUtils.getUserByName(Constants.ZZDZ_USERNAME);
                if (userDto != null) {
                    String fzridTemp = userDto.getId();
                    map.put("fzrid", fzridTemp);
                }

            }

            String sybmmc = userManagerUtils.getOrganizationByUserName(null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERNAME);// 使用部门名称
            map.put("sybmmc", sybmmc);
            map.put("zslx", zslxTemp);
            map.put("dyzt", CommonConstantUtils.SF_S_DM);
            // 预防回写的印制号是已使用的，这里需要加一个判断逻辑
            int isUsedQzysxlhCount = repo.selectOne("getCountByQzysxlh", map);
            if (isUsedQzysxlhCount > 0) {
                throw new RuntimeException("此次打证的印制号不可使用：" + bdcZsidYzhGxDTOList.get(i).getQzysxlh());
            }
            // 更新印制号的syqk为 已使用
            int yzhCount = repo.update("updateYzh", map);
            if (yzhCount == 0) {// 返回0，没有这条印制号
                throw new RuntimeException("此次打证的印制号不存在！");
            }

            BdcYzhQO yzhQo = new BdcYzhQO();
            yzhQo.setQzysxlh(yzhTemp);
            List<BdcYzhDTO> bdcYzhDTOList = bdcYzhFeignService.queryListBdcYzh(yzhQo);

            // 更新成功后需要新增一条使用明细数据
            // 生成使用明细
            BdcYzhsymxDO bdcYzhsymxDO = new BdcYzhsymxDO();
            bdcYzhsymxDO.setSyr(null != bdcZzdzRequestDTO.getZzdzjyhm() ? bdcZzdzRequestDTO.getZzdzjyhm() : Constants.ZZDZ_USERALIAS);
            bdcYzhsymxDO.setSyrid(map.get("fzrid") == null ? "" : map.get("fzrid").toString());
            bdcYzhsymxDO.setSyyy(CommonConstantUtils.YZH_SYYY);
            bdcYzhsymxDO.setSyqk(CommonConstantUtils.SYQK_YSY);
            bdcYzhsymxDO.setSysj(new Date());
            bdcYzhsymxDO.setYzhid(bdcYzhDTOList.get(0).getYzhid());
            bdcYzhsymxDO.setSlbh(map.get("slbh") == null ? "" : map.get("slbh").toString());
            bdcYzhsymxDO.setSybmmc(sybmmc);
            bdcYzhsymxDO.setYzhsymxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcYzhsymxDO);

            // 更新证书表
            int updatezsCount = repo.update("reWriteYzhToBdcZs", map);
            if (updatezsCount > 0) {
                count++;
            }
        }
        returnMap.put("count", count);
        returnMap.put("gzlslid", gzlslid);
        return returnMap;
    }
}
