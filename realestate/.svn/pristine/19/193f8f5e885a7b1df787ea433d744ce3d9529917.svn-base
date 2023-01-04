package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;
import cn.gtmap.realestate.common.core.dto.natural.*;
import cn.gtmap.realestate.common.core.dto.naturalresource.JbzkDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.naturalresource.NaturalJbzkFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.natural.config.NaturalBeanFactory;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import cn.gtmap.realestate.natural.core.service.ZrzyInitDataDealService;
import cn.gtmap.realestate.natural.core.service.pzxx.ZrzyXtLcpzService;
import cn.gtmap.realestate.natural.service.ZrzyInitService;
import cn.gtmap.realestate.natural.service.ZrzyYwxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/27
 * @description 自然资源初始化服务
 */
@Service
public class ZrzyInitServiceImpl implements ZrzyInitService {

    private final Logger logger = LoggerFactory.getLogger(ZrzyInitServiceImpl.class);

    @Autowired
    private ZrzyXtLcpzService zrzyXtLcpzService;

    @Autowired
    NaturalBeanFactory naturalBeanFactory;

    @Autowired
    NaturalJbzkFeignService naturalJbzkFeignService;

    @Autowired
    private ZrzyInitDataDealService zrzyInitDataDealService;

    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ZrzyYwxxService zrzyYwxxService;
    @Autowired
    StorageClientMatcher storageClient;

//    @Value("${init.mrml:市财政局审核表,市水利局审核表,市生态环境局审核表}")
    @Value("${init.mrml:}")
    private String mrml;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public String ywxxCsh(ZrzyCshDTO zrzyCshDTO) {
        if (StringUtils.isBlank(zrzyCshDTO.getGzldyid()) || CollectionUtils.isEmpty(zrzyCshDTO.getZrzyCshYwxxDTOList())) {
            throw new AppException("工作流定义ID为空或业务数据为空,无法创建");
        }
        TaskData taskData = processInstanceClient.directStartProcessInstance(
                zrzyCshDTO.getGzldyid(),
                userManagerUtils.getCurrentUserName(), "", "",
                0);

        if (Objects.nonNull(taskData)) {
            //转换数据
            List<ZrzyInitQO> zrzyInitQOList = changeCshDTO(zrzyCshDTO, taskData);

            //初始化信息
            ZrzyInitResultDTO zrzyInitResultDTO = initYwxx(zrzyInitQOList);


            if (zrzyInitResultDTO == null || CollectionUtils.isEmpty(zrzyInitResultDTO.getZrzyXmDOList())) {
                throw new AppException("初始化生成数据失败,请检查配置");
            }

            try {
                logger.info("流程创建事件开始：流程实例ID{}", taskData.getProcessInstanceId());
                processInstanceClient.exeAfterCreateForProcessStart(taskData.getProcessInstanceId());
                logger.info("流程创建事件结束：流程实例ID{}", taskData.getProcessInstanceId());
            } catch (Exception e) {
                logger.error("调用流程创建事件失败", e);
                throw new AppException(ErrorCode.CUSTOM, "初始化完成后，调用配置的流程创建事件。");
            }

            //回写信息到平台
            if (StringUtils.isNotBlank(zrzyInitResultDTO.getZrzyXmDOList().get(0).getGzlslid())) {
                try {
                    cshZrzyYwsj(zrzyInitResultDTO.getZrzyXmDOList().get(0).getGzlslid());
                }catch (Exception e){

                }
                logger.info("回写平台结束:{}", zrzyInitResultDTO.getZrzyXmDOList().get(0).getGzlslid());
            }

            if(StringUtils.isNotBlank(mrml)){
                List<String> mlList = Arrays.asList(mrml.split(","));
                for (String mlmc : mlList) {
                    storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID,
                            zrzyInitResultDTO.getZrzyXmDOList().get(0).getGzlslid(), mlmc, null);
                }
            }

            return taskData.getTaskId();
        }

        return "";
    }

    @Override
    public void saveZrzyYwsj(String gzlslid, Map<String, Object> params, Boolean update) {
        if (update) {
            processInsCustomExtendClient.updateProcessInsCustomExtend(gzlslid, params);
        } else {
            params.put("PROC_INS_ID", gzlslid);
            processInsCustomExtendClient.addProcessInsCustomExtend(params);
        }
    }

    @Override
    public void cshZrzyYwsj(String gzlslid) {
        Map<String, Object> params = new HashMap<>();
        //根据工作流获取参数
        ZrzySlymYwxxDTO zrzySlymYwxxDTO = zrzyYwxxService.queryZrzySlymYwxxDTO(gzlslid);
        //填写回写数据
        params.put("ZL", zrzySlymYwxxDTO.getZrzyXm().getZl());
        params.put("ZRZYDJDYH", zrzySlymYwxxDTO.getZrzyXm().getZrzydjdyh());
        params.put("ZRZYDJDYMC", zrzySlymYwxxDTO.getZrzyXm().getDjdymc());
        params.put("ZRZYCQZH", zrzySlymYwxxDTO.getZrzyXm().getZrzycqzh());
        params.put("SLBH", zrzySlymYwxxDTO.getZrzyXm().getSlbh());
        saveZrzyYwsj(gzlslid, params, false);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转换初始化参数数据
     */
    private List<ZrzyInitQO> changeCshDTO(ZrzyCshDTO zrzyCshDTO, TaskData taskData) {
        //流程配置
        ZrzyXtLcpzDO zrzyXtLcpzDO = zrzyXtLcpzService.queryZrzyXtLcpz(zrzyCshDTO.getGzldyid());
        List<ZrzyInitQO> zrzyInitQOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(zrzyCshDTO.getZrzyCshYwxxDTOList())) {
            for (ZrzyCshYwxxDTO zrzyCshYwxxDTO : zrzyCshDTO.getZrzyCshYwxxDTOList()) {
                ZrzyInitQO zrzyInitQO = new ZrzyInitQO();
                if (zrzyXtLcpzDO != null) {
                    zrzyInitQO.setZrzyXtLcpzDO(zrzyXtLcpzDO);
                    zrzyInitQO.setSjly(zrzyXtLcpzDO.getSjly());
                    //地籍管理系统
                    if (StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyXtLcpzDO.getSjly())) {
                        JbzkDTO jbzkDTO = naturalJbzkFeignService.queryJbzkByZrzydjdyh(zrzyCshYwxxDTO.getZrzydjdyh());
                        if (jbzkDTO != null) {
                            zrzyInitQO.setJbzkDTO(jbzkDTO);
                        }
                    }
                }
                zrzyInitQO.setZrzyCshYwxxDTO(zrzyCshYwxxDTO);
                zrzyInitQO.setGzldyid(zrzyCshDTO.getGzldyid());
                zrzyInitQO.setGzldymc(zrzyCshDTO.getGzldymc());
                zrzyInitQO.setGzlslid(taskData.getProcessInstanceId());
                zrzyInitQO.setYxmid(zrzyCshYwxxDTO.getYxmid());
                zrzyInitQO.setXmid(UUIDGenerator.generate16());
                zrzyInitQOList.add(zrzyInitQO);

            }

        }
        return zrzyInitQOList;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化数据
     */
    private ZrzyInitResultDTO initYwxx(List<ZrzyInitQO> zrzyInitQOList) {
        ZrzyInitResultDTO zrzyInitResultDTO = new ZrzyInitResultDTO();
        Set<ZrzyCommonService> zrzyYwxxInitServiceSet = naturalBeanFactory.getZrzyYwxxInitServiceSet();

        for (ZrzyInitQO zrzyInitQO : zrzyInitQOList) {
            ZrzyInitServiceDTO zrzyInitServiceDTO = new ZrzyInitServiceDTO();
            if (CollectionUtils.isNotEmpty(zrzyYwxxInitServiceSet)) {
                for (ZrzyCommonService zrzyCommonService : zrzyYwxxInitServiceSet) {
                    zrzyCommonService.initYwxx(zrzyInitQO, zrzyInitServiceDTO);
                }
            }
            try {
                //转换业务生成的数据
                zrzyInitDataDealService.dealServiceDTO(zrzyInitServiceDTO, zrzyInitResultDTO);
            } catch (Exception e) {
                logger.error("初始化数据转换失败", e);
            }
        }
        //数据入库
        for (ZrzyCommonService zrzyCommonService : zrzyYwxxInitServiceSet) {
            zrzyCommonService.saveYwxx(zrzyInitResultDTO);
        }

        return zrzyInitResultDTO;

    }
}
