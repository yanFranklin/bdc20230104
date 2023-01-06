package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.clients.manage.StatisticsProcessClient;
import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.StatisticsProcDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.config.WwsqZdjsSfxmConfig;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.*;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.GltdzxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.portal.WorkFlowCommonDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.enums.BdcSplyQlrlyEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.DjxlPzCxQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.feign.portal.BdcCheckFeignService;
import cn.gtmap.realestate.common.core.service.feign.portal.BdcWorkFlowFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlRestService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcQllxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-11
 * @description 外网申请创建项目
 */
@Service
public class WwsqCjBdcXmServiceImpl implements WwsqCjBdcXmService {

    /**
     * 预转现
     */
    @Value("#{'${accept.lcbs.yzx:}'.split(',')}")
    private List<String> yzxlc;
    /**
     * 带抵押
     */
    @Value("#{'${accept.lcbs.withdy:}'.split(',')}")
    private List<String> withdylc;

    @Value("${slbhscfs.version:}")
    private String slbhgs;
    @Value("${wwsq.zfyzhl:false}")
    private boolean zfyzhl;

    // 外网申请是否初始化收费项目
    @Value("${wwsq.sfcshsfxm:false}")
    private boolean sfcshsfxm;
    /**
     * 自动转发，未被认领的节点，转发前先认领 zfrl 用户
     */
    @Value("${wwsq.zfrl:}")
    private String zfrl;

    @Value("${yslc.gzldyid:}")
    private String yslcGzldyid;
    @Value("#{'${zhdk.gzldyid:}'.split(',')}")
    private List<String> zhdkGzldyidList;

    @Value("${cjlc.verifyRoleDepartment:}")
    private Integer verifyRoleDepartment;

    @Value("${sfxx.djfjsff: 0}")
    private Integer djfjsff;

    /**
     * 受理时间以受理人认领时间为准,无受理人不生成受理时间
     */
    @Value("${sjd.rl.slsj:false}")
    private Boolean sjdrlslsj;

    /**
     * 自动计算收费项目
     */
    @Autowired
    WwsqZdjsSfxmConfig wwsqZdjsSfxmConfig;

    /**
     * 角色分组标识,默认筛选人员同组织下的角色
     */
    @Value("${forward.group:true}")
    private String forwardGroup;

    /**
     * 自动转发,转发角色默认筛选创建人指定地区下的角色,同组织排在前面
     */
    @Value("${zdzf.zfjs.cjrxzqdm:false}")
    private String zdzfStartRegion;


    //配置需要从机构表获取代理人的流程
    @Value("#{'${accept.qlrdlr.lcbs:}'.split(',')}")
    private List<String> dlrFromXtjg;

    /**
     * 预抵押登记小类
     */
    @Value("#{'${ydydjxl:}'.split(',')}")
    private List<String> ydydjxl;

    /** 外网创建，配置的审批来源，受理编号由系统自动生成，不继承接口的受理编号，针对所有创建接口 */
    @Value("#{'${wwsq.scslbh.sply:}'.split(',')}")
    private List<String> scslbhSply;

    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WwsqCjBdcXmServiceImpl.class);

    private static final String GZYZ_SUFIX = "_WWSQCJ";
    private static final String BEFORE_GZYZ_SUFIX = "_BEFOREWWSQCJ";


    @Autowired
    private BdcGzyzService bdcGzyzService;

    @Autowired
    private BdcDjxlPzService bdcDjxlPzService;

    @Autowired
    private BdcSlJyxxService bdcSlJyxxService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;

    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private BdcBhService bdcBhService;

    @Autowired
    private StatisticsProcessClient statisticsProcessClient;

    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    private BdcWlzsService bdcWlzsService;

    @Autowired
    private BdcInitFeignService bdcInitFeignService;

    @Autowired
    private TaskHandleClientMatcher taskHandleClient;

    @Autowired
    private BdcSfService bdcSfService;

    @Autowired
    private BdcQllxRestService bdcQllxRestService;

    @Autowired
    private BdcYcslManageService bdcYcslManageService;

    @Autowired
    private BdcSlJbxxService bdcSlJbxxService;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcSlLzrService bdcSlLzrService;

    @Autowired
    BdcSlYjxxService bdcSlYjxxService;

    @Autowired
    BdcSdqghFeignService bdcSdqghFeignService;

    @Autowired
    private TaskUtils taskUtils;

    @Autowired
    BdcCheckFeignService bdcCheckFeignService;

    @Autowired
    BdcShxxFeignService bdcShxxFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    private FlowableNodeClientMatcher flowableNodeClient;

    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;

    @Autowired
    private BdcSlRestService bdcSlRestService;

    @Autowired
    private BdcPpFeignService bdcPpFeignService;

    @Autowired
    private BdcXtMryjFeignService bdcXtMryjFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    BdcSlSfxxService bdcSlSfxxService;

    @Autowired
    BdcSlSfxmService bdcSlSfxmService;

    @Autowired
    BdcBhFeignService bdcBhFeignService;

    @Autowired
    BdcYxbdcdyKgPzService bdcYxbdcdyKgPzService;

    @Autowired
    BdcJbxxService bdcJbxxService;

    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;

    @Autowired
    BdcWorkFlowFeignService bdcWorkFlowFeignService;

    @Autowired
    BdcSlXmService bdcSlXmService;

    @Autowired
    BdcAddGwcSjclCommonService bdcAddGwcSjclCommonService;



    /**
     * @param wwsqCjBdcXmRequestDTO 外网请求实体
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 请求创建BDC项目
     * @modify 发票查询需求添加申请受理信息
     */
    @Override
    @Transactional
    public WwsqCjxmResponseDTO cjBdcXm(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception {
        WwsqCjxmResponseDTO wwsqCjxmResponseDTO = new WwsqCjxmResponseDTO();
        List<BdcXmDO> bdcXmDOList;
        TaskData taskData = null;
        // 反查流程信息
        ProcessInstanceData processInstanceData;
        BdcSlxxDTO bdcSlxxDTO = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO();
        LOGGER.info("外网创建接收数据wwsqCjBdcXmRequestDTO{}", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
        // 初始化
        try {
            // 向DTO 填充 关联关系主键 和项目必要字段（权利类型、登记小类）
            fillSlxxDTO(bdcSlxxDTO);
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlxxDTO.getBdcSlJbxx();
            String gzldyid = bdcSlJbxxDO.getGzldyid();
            String slrdlm = "";
            String spxtywh = "";
            if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList()) && bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm() != null) {
                spxtywh = bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm().getSpxtywh();
            }

            // 处理受理人
            if (StringUtils.isNotBlank(bdcSlJbxxDO.getSlr()) && !wwsqCjBdcXmRequestDTO.isJrrllb()) {
                LOGGER.info("包含受理人的创建，slr:{},外网受理编号：{}", bdcSlJbxxDO.getSlr(), spxtywh);
                String dsfSlrdlm = bdcSlJbxxDO.getSlr();
                bdcSlJbxxDO.setSlr(null);
                // 传递过来的受理人为 登录名
                UserDto userDto = userManagerUtils.getUserByNameAndXzqdm(dsfSlrdlm, wwsqCjBdcXmRequestDTO.getYhxzqdm());
                if (userDto != null) {
                    LOGGER.info("外网受理编号：{},受理人：{}查询到的用户信息：{}", spxtywh,dsfSlrdlm, userDto);
                    slrdlm = userDto.getUsername();
                    bdcSlJbxxDO.setSlr(userDto.getAlias());
                    bdcSlJbxxDO.setSlrid(userDto.getId());
                    // 有受理人场景，使用受理人组织机构赋值区县代码
                    if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                        throw new AppException("当前用户"+userDto.getUsername()+"缺失组织信息");
                    }
                    OrganizationDto organizationDto = userDto.getOrgRecordList().get(0);
                    if (organizationDto != null) {
                        bdcSlJbxxDO.setDjjg(bdcJbxxService.queryDjjgByUser(userDto));
                        bdcSlJbxxDO.setQxdm(organizationDto.getRegionCode());
                        bdcSlJbxxDO.setDjbmdm(organizationDto.getCode());
                        for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                            // 区县代码
                            bdcSlXmDTO.getDsfSlxxDTO().setQxdm(organizationDto.getRegionCode());
                            // 登记部门编码
                            bdcSlXmDTO.getDsfSlxxDTO().setDjbmdm(organizationDto.getCode());
                        }
                    }
                }
                // 创建流程
                taskData = processInstanceClient.directStartProcessInstance(gzldyid, slrdlm, "", wwsqCjBdcXmRequestDTO.getYhxzqdm(), null);
            } else {
                // 进入认领列表后 需要把slr置空 这样认领后才能更新 bdc_xm的slr
                if (wwsqCjBdcXmRequestDTO.isJrrllb()) {
                    bdcSlJbxxDO.setSlr(null);
                }
                String roleCode = wwsqCjBdcXmRequestDTO.getSlRoleCode();
                if (!wwsqCjBdcXmRequestDTO.isSljsbglqxdm() && StringUtils.isNotBlank(roleCode)) {
                    roleCode = wwsqCjBdcXmRequestDTO.getSlRoleCode() + (bdcSlJbxxDO.getQxdm() != null ? bdcSlJbxxDO.getQxdm() : "");
                }
                LOGGER.info("没有受理人的创建，slRoleCode:{},外网受理编号:{}", roleCode, spxtywh);
                // 创建认领列表流程 需要传递角色标志
                taskData = processInstanceClient.directStartByRole(gzldyid, roleCode, wwsqCjBdcXmRequestDTO.getYhxzqdm(), verifyRoleDepartment, "");
            }
            if (taskData == null || StringUtils.isBlank(taskData.getProcessInstanceId())
                    || StringUtils.isBlank(taskData.getTaskId())) {
                throw new AppException("创建流程失败");
            }
            // 反查流程信息
            processInstanceData = processInstanceClient.getProcessInstance(taskData.getProcessInstanceId());
            if (processInstanceData == null) {
                throw new AppException("流程实例查询失败");
            }
            // 填充 bdcSlJbxxDO
            fillBdcSlJbxxDO(processInstanceData, bdcSlxxDTO);

            //初始化邮寄信息
            boolean cshYjxx =false;
            for(BdcSlXmDTO bdcSlXmDTO:bdcSlxxDTO.getBdcSlXmList()){
                if (null != bdcSlXmDTO.getBdcSlYjxxDO() && CheckParameter.checkAnyParameter(bdcSlXmDTO.getBdcSlYjxxDO())) {
                    BdcSlYjxxDO bdcSlYjxxDO = bdcSlXmDTO.getBdcSlYjxxDO().withGzlslid(processInstanceData.getProcessInstanceId())
                            .withSlbh(bdcSlJbxxDO.getSlbh()).withQxdm(bdcSlJbxxDO.getQxdm());
                    bdcSlYjxxDO.setDjxl(bdcSlXmDTO.getBdcSlXm().getDjxl());
                    bdcSlYjxxService.initBdcYjxx(bdcSlYjxxDO);
                    cshYjxx=true;
                }
            }
            if (Boolean.FALSE.equals(cshYjxx) &&null != bdcSlxxDTO.getBdcSlYjxxDO() && CheckParameter.checkAnyParameter(bdcSlxxDTO.getBdcSlYjxxDO())) {
                BdcSlYjxxDO bdcSlYjxxDO = bdcSlxxDTO.getBdcSlYjxxDO().withGzlslid(processInstanceData.getProcessInstanceId())
                        .withSlbh(bdcSlJbxxDO.getSlbh()).withQxdm(bdcSlJbxxDO.getQxdm());
                bdcSlYjxxDO.setDjxl(bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm().getDjxl());
                bdcSlYjxxService.initBdcYjxx(bdcSlYjxxDO);
            }
            //处理sply,互联网+一窗
            if (wwsqCjBdcXmRequestDTO.isSfss()) {
                for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                    // 审批来源
                    if (CommonConstantUtils.SPLY_YCTB.equals(Integer.parseInt(bdcSlXmDTO.getDsfSlxxDTO().getSply()))) {
                        bdcSlXmDTO.getDsfSlxxDTO().setSply(CommonConstantUtils.SPLY_YCTB_SS.toString());
                    } else {
                        bdcSlXmDTO.getDsfSlxxDTO().setSply(CommonConstantUtils.SPLY_WWSQ_YCSL.toString());
                    }
                }
            } else {
                //组合流程根据项目区分是否涉税
                for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                    if (bdcSlXmDTO.getDsfSlxxDTO() != null && StringUtils.equals(CommonConstantUtils.SF_S_DM.toString(), bdcSlXmDTO.getDsfSlxxDTO().getSfss())) {
                        // 审批来源
                        if (CommonConstantUtils.SPLY_YCTB.equals(Integer.parseInt(bdcSlXmDTO.getDsfSlxxDTO().getSply()))) {
                            bdcSlXmDTO.getDsfSlxxDTO().setSply(CommonConstantUtils.SPLY_YCTB_SS.toString());
                        } else {
                            bdcSlXmDTO.getDsfSlxxDTO().setSply(CommonConstantUtils.SPLY_WWSQ_YCSL.toString());
                        }
                    }
                }
            }

            LOGGER.info("外网创建需要初始化的数据{}", JSONObject.toJSONString(bdcSlxxDTO));
            if(!wwsqCjBdcXmRequestDTO.isSfss()) {
                //非涉税流程,插入受理数据
                bdcSlXmService.saveBdcSlxx(changeBdcSlXxDTOToBdcSlxxInitDTOSingle(bdcSlxxDTO), Constants.FUN_INSERT);
            }
            bdcXmDOList = bdcInitFeignService.csh(bdcSlxxDTO);
            if (wwsqCjBdcXmRequestDTO.isSfss()) {
                LOGGER.info("涉税流程的创建，工作流实例ID:{}", taskData.getProcessInstanceId());
                //涉税流程生成一窗数据
                bdcYcslManageService.syncWwsqxxToYcslxx(taskData.getProcessInstanceId(), "", bdcSlxxDTO);

            } else {
                //组合流程根据项目区分是否涉税
                for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                    if (StringUtils.equals(CommonConstantUtils.SF_S_DM.toString(), bdcSlXmDTO.getDsfSlxxDTO().getSfss())) {
                        LOGGER.info("涉税项目的处理，工作流实例ID:{},项目ID:{}", taskData.getProcessInstanceId(),bdcSlXmDTO.getBdcSlXm() != null ? bdcSlXmDTO.getBdcSlXm().getXmid() : "");
                        bdcYcslManageService.syncWwsqxxToYcslxx(taskData.getProcessInstanceId(), bdcSlXmDTO.getBdcSlXm().getXmid(), bdcSlxxDTO);
                    }
                }


            }

        } catch (Exception e) {
            LOGGER.error("初始化项目失败", e);
            if (taskData != null) {
                // 删除任务
                taskUtils.deleteTask(taskData.getProcessInstanceId(), taskData.getReason());
            }
            throw e;
        }
        try {
            //处理收费信息
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                dealWwSfxx(bdcSlxxDTO, taskData.getProcessInstanceId(), bdcXmDOList,wwsqCjBdcXmRequestDTO.getYhxzqdm());

            }
        } catch (Exception e) {
            LOGGER.error("创建成功，但初始化收费信息失败", e);
        }

        try{
            //处理核税信息
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                dealWwHsxx(bdcSlxxDTO);
            }
        }catch(Exception e){
            LOGGER.error("创建成功，但初始化核税信息失败", e);
        }

        //初始化领证人信息
        bdcSlLzrService.initLzrxx(bdcSlxxDTO, taskData.getProcessInstanceId());
        //初始化水电气信息
        if (bdcSlxxDTO.getBdcSdqghDTO() != null && CheckParameter.checkAnyParameter(bdcSlxxDTO.getBdcSdqghDTO()) && CollectionUtils.isNotEmpty(bdcXmDOList)) {
            bdcSlxxDTO.getBdcSdqghDTO().setSlbh(bdcXmDOList.get(0).getSlbh());
            bdcSlxxDTO.getBdcSdqghDTO().setSqsj(bdcXmDOList.get(0).getSlsj());
            bdcSlxxDTO.getBdcSdqghDTO().setGzlslid(taskData.getProcessInstanceId());
            this.addBdcSdqGh(bdcSlxxDTO.getBdcSdqghDTO());
        }
        //处理组合流程附件信息
        List<WwsqZhlcSjclDTO> wwsqZhlcSjclDTOList = new ArrayList<>(2);
        if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getFjxx())) {
                    WwsqZhlcSjclDTO wwsqZhlcSjclDTO = new WwsqZhlcSjclDTO();
                    wwsqZhlcSjclDTO.setDjxl(bdcSlXmDTO.getBdcSlXm().getDjxl());
                    wwsqZhlcSjclDTO.setGzlslid(processInstanceData.getProcessInstanceId());
                    wwsqZhlcSjclDTO.setFjxx(bdcSlXmDTO.getFjxx());
                    wwsqZhlcSjclDTOList.add(wwsqZhlcSjclDTO);
                }
            }
        }
        wwsqCjxmResponseDTO.setBdcXmDOList(bdcXmDOList);
        wwsqCjxmResponseDTO.setWwsqZhlcSjclDTOList(wwsqZhlcSjclDTOList);
        return wwsqCjxmResponseDTO;
    }

    /**
     * 初始化不动产水电气过户信息
     */
    private void addBdcSdqGh(BdcSdqghDTO param) {
        BdcSdqghDO bdcSdqghDO = new BdcSdqghDO();
        bdcSdqghDO.setSlbh(param.getSlbh());
        bdcSdqghDO.setSqsj(param.getSqsj());
        bdcSdqghDO.setGzlslid(param.getGzlslid());
        bdcSdqghDO.setSfbl(CommonConstantUtils.SF_S_DM);
        // 水业务
        if (Objects.nonNull(param.getSfblsyw()) && param.getSfblsyw().equals(CommonConstantUtils.SF_S_DM)) {
            bdcSdqghDO.setId(UUIDGenerator.generate16());
            bdcSdqghDO.setYwlx(BdcSdqEnum.S.key());
            bdcSdqghDO.setConsno(param.getShh());
            bdcSdqghDO.setXhzmc(param.getXskhzmc());
            bdcSdqghDO.setHzmc(param.getYskhzmc());
            bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);
        }
        // 电业务
        if (Objects.nonNull(param.getSfbldyw()) && param.getSfbldyw().equals(CommonConstantUtils.SF_S_DM)) {
            bdcSdqghDO.setId(UUIDGenerator.generate16());
            bdcSdqghDO.setYwlx(BdcSdqEnum.D.key());
            bdcSdqghDO.setConsno(param.getDhh());
            bdcSdqghDO.setXhzmc(param.getXdkhzmc());
            bdcSdqghDO.setHzmc(param.getYdkhzmc());
            bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);
        }
        // 气业务
        if (Objects.nonNull(param.getSfblqyw()) && param.getSfblqyw().equals(CommonConstantUtils.SF_S_DM)) {
            bdcSdqghDO.setId(UUIDGenerator.generate16());
            bdcSdqghDO.setYwlx(BdcSdqEnum.Q.key());
            bdcSdqghDO.setConsno(param.getQhh());
            bdcSdqghDO.setXhzmc(param.getXrqkhzmc());
            bdcSdqghDO.setHzmc(param.getYrqkhzmc());
            bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);
        }
        // 广电业务
        if (Objects.nonNull(param.getSfblgdyw()) && param.getSfblgdyw().equals(CommonConstantUtils.SF_S_DM)) {
            bdcSdqghDO.setId(UUIDGenerator.generate16());
            bdcSdqghDO.setYwlx(BdcSdqEnum.GD.key());
            bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);
        }
        // 网络业务
        if (Objects.nonNull(param.getSfblwlyw()) && param.getSfblwlyw().equals(CommonConstantUtils.SF_S_DM)) {
            bdcSdqghDO.setId(UUIDGenerator.generate16());
            bdcSdqghDO.setYwlx(BdcSdqEnum.WL.key());
            bdcSdqghFeignService.insertSdqghDO(bdcSdqghDO);
        }
    }


    @Override
    @Transactional
    public WwsqCjBdcXmResponseDTO cjFdjlcXm(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception {
        WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO = new WwsqCjBdcXmResponseDTO();
        BdcSlxxInitDTO bdcSlxxInitDTO;
        TaskData taskData = null;
        String currentGzlslid;
        // 反查流程信息
        ProcessInstanceData processInstanceData;
        BdcSlxxDTO bdcSlxxDTO = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO();
        String slrdlm = "";

        String gzldyid = bdcSlxxDTO.getBdcSlJbxx().getGzldyid();
        String lastGzlslid = "";
        // 当当前流程是预审流程的时候，需要判断当前推送是否是第二次推送
        // 第二次创建流程 0：否，1：是
        Integer seacondCjLc = 0;
        if (yslcGzldyid.indexOf(gzldyid) > -1) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(bdcSlxxDTO.getBdcSlJbxx().getSlbh(), "");
            // 当前受理编号能查询到基本信息 则说明是第二次推送，需要删除jbxx和slxm
            if (null != bdcSlJbxxDO) {
                // 第一次的工作流实例id
                lastGzlslid = bdcSlJbxxDO.getGzlslid();
                bdcSlRestService.deleteBdcSlxx(lastGzlslid);
                seacondCjLc = 1;
            }
        }
        String spxtywh = "";
        // 初始化
        try {
            // 向DTO 填充 关联关系主键 和项目必要字段（权利类型、登记小类）
            fillSlxxDTO(bdcSlxxDTO);
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlxxDTO.getBdcSlJbxx();


            if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList()) && bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm() != null) {
                spxtywh = bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm().getSpxtywh();
            }


            // 处理受理人
            if (StringUtils.isNotBlank(bdcSlJbxxDO.getSlr()) && !wwsqCjBdcXmRequestDTO.isJrrllb()) {
                slrdlm = dealSlrxx(bdcSlxxDTO, wwsqCjBdcXmRequestDTO.getYhxzqdm());
                // 创建流程
                if (seacondCjLc == 0) {
                    taskData = processInstanceClient.directStartProcessInstance(gzldyid, slrdlm, "", wwsqCjBdcXmRequestDTO.getYhxzqdm(), null);
                }
            } else {
                // 进入认领列表后 需要把slr置空 这样认领后才能更新 bdc_xm的slr
                if (wwsqCjBdcXmRequestDTO.isJrrllb()) {
                    bdcSlJbxxDO.setSlr(null);
                }

                if (seacondCjLc == 0) {
                    String roleCode = wwsqCjBdcXmRequestDTO.getSlRoleCode();
                    if (!wwsqCjBdcXmRequestDTO.isSljsbglqxdm() && StringUtils.isNotBlank(roleCode)) {
                        roleCode = wwsqCjBdcXmRequestDTO.getSlRoleCode() + bdcSlJbxxDO.getQxdm();
                    }
                    LOGGER.info("没有受理人的创建，slRoleCode:{},外网受理编号:{}", roleCode, spxtywh);
                    // 创建认领列表流程 需要传递角色标志
                    taskData = processInstanceClient.directStartByRole(gzldyid, roleCode, wwsqCjBdcXmRequestDTO.getYhxzqdm(), verifyRoleDepartment, "");
                }
            }
            if (seacondCjLc == 0) {
                if (taskData == null || StringUtils.isBlank(taskData.getProcessInstanceId())
                        || StringUtils.isBlank(taskData.getTaskId())) {
                    throw new AppException("创建流程失败");
                }
            }

            currentGzlslid = taskData == null ? lastGzlslid : taskData.getProcessInstanceId();
            // 反查流程信息
            processInstanceData = processInstanceClient.getProcessInstance(currentGzlslid);
            if (processInstanceData == null) {
                throw new AppException("流程实例查询失败");
            }
            // 填充 bdcSlJbxxDO
            //处理受理编号
            if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getSjly()) && StringUtils.isBlank(bdcSlJbxxDO.getSlbh())) {
                bdcSlJbxxDO.setSlbh(wwsqCjBdcXmRequestDTO.getSjly() + bdcBhService.queryBh(Constants.BH_SL, ""));
            }
            fillBdcSlJbxxDO(processInstanceData, bdcSlxxDTO);

            //初始化邮寄信息
            if (null != bdcSlxxDTO.getBdcSlYjxxDO()) {
                BdcSlYjxxDO bdcSlYjxxDO = bdcSlxxDTO.getBdcSlYjxxDO().withGzlslid(processInstanceData.getProcessInstanceId())
                        .withSlbh(bdcSlJbxxDO.getSlbh()).withQxdm(bdcSlJbxxDO.getQxdm());
                bdcSlYjxxService.initBdcYjxx(bdcSlYjxxDO);
            }

            //调用初始化接口获取不动产单元信息
            List<BdcYwxxDTO> bdcYwxxDTOList = bdcInitFeignService.ycCsh(bdcSlxxDTO);
            //受理一窗信息初始化
            bdcSlxxInitDTO = bdcYcslManageService.cshBdcYcslXx(bdcYwxxDTOList, bdcSlxxDTO);
            //补充受理基本信息和项目历史关系
            if (CollectionUtils.isNotEmpty(bdcSlxxInitDTO.getBdcSlXmDOList())) {
                bdcSlxxInitDTO.setBdcSlJbxxDOList(Lists.newArrayList(bdcSlJbxxDO));
                //保存bdc_sl_jbxx
                bdcSlJbxxService.insertBdcSlJbxx(bdcSlJbxxDO);
                if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
                    for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                        if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())) {
                            bdcSlXmLsgxDOList.addAll(bdcSlXmDTO.getBdcSlXmLsgxList());
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                        //保存bdc_sl_xm_lsgx
                        entityMapper.insertBatchSelective(bdcSlXmLsgxDOList);
                    }
                    bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);

                }
            }
        } catch (Exception e) {
            LOGGER.error("初始化项目失败", e);
            if (taskData != null) {
                // 删除任务
                taskUtils.deleteTask(taskData.getProcessInstanceId(), taskData.getReason());
            }
            throw e;
        }
        try {
            // 初始化 收费信息
            bdcSfService.cshFdjlcSfxx(currentGzlslid);
        } catch (Exception e) {
            LOGGER.error("创建成功，但初始化收费信息失败", e);
        }
        //处理组合流程收件信息
        List<WwsqZhlcSjclDTO> wwsqZhlcSjclDTOList = new ArrayList<>(2);
        if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getFjxx())) {
                    WwsqZhlcSjclDTO wwsqZhlcSjclDTO = new WwsqZhlcSjclDTO();
                    wwsqZhlcSjclDTO.setGzlslid(currentGzlslid);
                    wwsqZhlcSjclDTO.setDjxl(bdcSlXmDTO.getBdcSlXm().getDjxl());
                    wwsqZhlcSjclDTO.setFjxx(bdcSlXmDTO.getFjxx());
                    wwsqZhlcSjclDTOList.add(wwsqZhlcSjclDTO);
                }
            }
        }
        LOGGER.info("自动办结标志:{},外网受理编号:{}", wwsqCjBdcXmRequestDTO.isBjYthlc(),spxtywh);
        //自动办结一体化流程并且自动创建登记流程
        if (wwsqCjBdcXmRequestDTO.isBjYthlc()) {
            try {
                BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO = new BdcTsDjxxRequestDTO();
                bdcTsDjxxRequestDTO.setGzlslid(currentGzlslid);
                bdcTsDjxxRequestDTO.setSlrdlm(slrdlm);
                BdcYcslPzDTO bdcYcslPzDTO = new BdcYcslPzDTO();
                //自动办结一体化
                bdcYcslPzDTO.setAutoComplete(true);
                //继承受理编号
                bdcYcslPzDTO.setGyslbh(true);
                //进入认领
                bdcYcslPzDTO.setJrrllb(true);
                bdcTsDjxxRequestDTO.setBdcYcslPzDTO(bdcYcslPzDTO);
                //一体化受理实体
                bdcTsDjxxRequestDTO.setBdcSlxxDTO(wwsqCjBdcXmRequestDTO.getBdcSlxxDTO());
                InitTsBdcDjxxResponseDTO initTsBdcDjxxResponseDTO = bdcYcslManageService.initTsBdcDjxx(bdcTsDjxxRequestDTO);
                if (CollectionUtils.isNotEmpty(initTsBdcDjxxResponseDTO.getBdcXmDOList())) {
                    wwsqCjBdcXmResponseDTO.setBdcXmDOList(initTsBdcDjxxResponseDTO.getBdcXmDOList());
                }
                if (StringUtils.isNotBlank(initTsBdcDjxxResponseDTO.getMsg())) {
                    LOGGER.error("创建成功，但自动办结并创建登记失败:{}", initTsBdcDjxxResponseDTO.getMsg());
                }
            } catch (Exception e) {
                LOGGER.error("创建成功，但自动办结并创建登记失败", e);
            }
        }

        wwsqCjBdcXmResponseDTO.setBdcSlxxInitDTO(bdcSlxxInitDTO);

        // 激活挂起的流程
        if (seacondCjLc == 1) {
            taskHandleClient.taskActivation(lastGzlslid, "激活流程");
        }
        wwsqCjBdcXmResponseDTO.setWwsqZhlcSjclDTOList(wwsqZhlcSjclDTOList);
        return wwsqCjBdcXmResponseDTO;
    }

    /**
     * @param wwsqCjBdcXmRequestDTO 外网请求实体
     * @param gzyzResult 规则验证结果
     * @return List<Map < String, Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 第三方创建  默认 带入规则验证带过来的外联项目ID （如果存在非WLZS验证不通过的子规则，则不处理，全部返回）
     */
    @Override
    public List<Map<String, Object>> dsfDealWlzsXmid(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, List<Map<String, Object>> gzyzResult) {
        List<BdcSlXmDTO> bdcSlXmDTOS = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList();
        Map<String, List<BdcSlXmLsgxDO>> bdcdyhToXmlsgxMap = new HashMap<>();
        Map<String,List<BdcSlYgDTO>> ygMap =new HashMap<>();
        for (Map<String, Object> map : gzyzResult) {
            String bdcdyh = MapUtils.getString(map, "bdcdyh");
            String msg = MapUtils.getString(map, "msg");
            if (StringUtil.isNotBlank(bdcdyh) && msg.contains("WLZS")
                    && map.get("xzxx") != null) {
                List xzxx = (List) map.get("xzxx");
                if (CollectionUtils.isNotEmpty(xzxx)) {
                    List<BdcSlXmLsgxDO> slXmList = new ArrayList<>();

                    for (Object temp : xzxx) {
                        Map<String, Object> xzxxMap = (Map) temp;
                        // 从验证返回中 获取XMID
                        String yxmid = MapUtils.getString(xzxxMap, "XMID");
                        if (StringUtil.isNotBlank(yxmid)) {
                            if(msg.contains("YGWLZS")){
                                Integer ygdjzl = MapUtils.getInteger(xzxxMap, "YGDJZL");
                                BdcSlYgDTO bdcSlYgDTO =new BdcSlYgDTO();
                                bdcSlYgDTO.setXmid(yxmid);
                                bdcSlYgDTO.setYgdjzl(ygdjzl);
                                bdcSlYgDTO.setBdcdyh(bdcdyh);
                                if(ygMap.containsKey(bdcdyh)){
                                    List<BdcSlYgDTO> bdcSlYgDTOS =ygMap.get(bdcdyh);
                                    List<String> xmidList =bdcSlYgDTOS.stream().map(BdcSlYgDTO::getXmid).collect(Collectors.toList());
                                    if(!xmidList.contains(bdcSlYgDTO.getXmid())) {
                                        bdcSlYgDTOS.add(bdcSlYgDTO);
                                        ygMap.put(bdcdyh, bdcSlYgDTOS);
                                    }
                                }else{
                                    List<BdcSlYgDTO> bdcSlYgDTOS =new ArrayList<>();
                                    bdcSlYgDTOS.add(bdcSlYgDTO);
                                    ygMap.put(bdcdyh,bdcSlYgDTOS);
                                }

                            }else {
                                BdcSlXmLsgxDO slXmLsgxDO = new BdcSlXmLsgxDO();
                                slXmLsgxDO.setYxmid(yxmid);
                                // 外网申请业务 外联证书 为否
                                slXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                                slXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                                slXmList.add(slXmLsgxDO);
                            }
                        }
                    }

                    if (CollectionUtils.isNotEmpty(slXmList)) {
                        bdcdyhToXmlsgxMap.put(bdcdyh, slXmList);
                    }

                }
            } else {
                return gzyzResult;
            }
        }

        // 循环 保存项目历史关系
        if (MapUtils.isNotEmpty(bdcdyhToXmlsgxMap)) {
            // 清空 规则验证结果
            gzyzResult = null;
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlXmDTOS) {
                if (bdcSlXmDTO.getBdcSlXm() != null &&
                        CollectionUtils.isNotEmpty(bdcdyhToXmlsgxMap.get(bdcSlXmDTO.getBdcSlXm().getBdcdyh()))) {
                    if (bdcSlXmDTO.getBdcSlXmLsgxList() == null) {
                        bdcSlXmDTO.setBdcSlXmLsgxList(new ArrayList<>());
                    }
                    bdcSlXmDTO.getBdcSlXmLsgxList().addAll(bdcdyhToXmlsgxMap.get(bdcSlXmDTO.getBdcSlXm().getBdcdyh()));
                }
            }
        }
        if(MapUtils.isNotEmpty(ygMap)){
            // 清空 规则验证结果
            gzyzResult = null;
            for(BdcSlXmDTO bdcSlXmDTO:bdcSlXmDTOS){
                if(bdcSlXmDTO.getBdcSlXm() !=null &&CollectionUtils.isNotEmpty(ygMap.get(bdcSlXmDTO.getBdcSlXm().getBdcdyh()))) {
                    //关联预告预抵押
                    if (bdcSlXmDTO.getGlygydy() == null) {
                        bdcSlXmDTO.setGlygydy(new ArrayList<>());
                    }
                    bdcSlXmDTO.getGlygydy().addAll(ygMap.get(bdcSlXmDTO.getBdcSlXm().getBdcdyh()));
                }
            }
        }
        return gzyzResult;
    }

    /**
     * 初始化 BdcSlJbxxDO
     *
     * @param bdcSlxxDTO 基本信息 ID
     */
    private void fillBdcSlJbxxDO(ProcessInstanceData processInstanceData, BdcSlxxDTO bdcSlxxDTO) {
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlxxDTO.getBdcSlJbxx();
        bdcSlJbxxDO.setGzlslid(processInstanceData.getProcessInstanceId());
        bdcSlJbxxDO.setGzldyid(processInstanceData.getProcessDefinitionKey());
        bdcSlJbxxDO.setSlsj(processInstanceData.getStartTime());
        if (StringUtils.isBlank(bdcSlJbxxDO.getSlr()) && Boolean.TRUE.equals(sjdrlslsj)) {
            //没有受理人,清空受理时间
            bdcSlJbxxDO.setSlsj(null);
        }
        bdcSlJbxxDO.setLcmc(processInstanceData.getProcessDefinitionName());
        String sply ="";
        if(CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList()) &&bdcSlxxDTO.getBdcSlXmList().get(0).getDsfSlxxDTO() != null) {
            sply = bdcSlxxDTO.getBdcSlXmList().get(0).getDsfSlxxDTO().getSply();
        }
        if (StringUtils.isBlank(bdcSlJbxxDO.getSlbh()) ||(CollectionUtils.isNotEmpty(scslbhSply) &&scslbhSply.contains(sply))) {
            //未传递受理编号,
            bdcSlJbxxDO.setSlbh(bdcBhService.queryBh(Constants.BH_SL, ""));
            String slbh = bdcSlJbxxDO.getSlbh();
            //南通的受理编号需要根据单元号加前缀
            if (StringUtils.equals(slbhgs, Constants.VERSION_NT)) {
                if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList()) && bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm() != null) {
                    String bdcdyh = bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm().getBdcdyh();
                    if (StringUtils.isNotBlank(bdcdyh)) {
                        slbh = bdcBhService.queryTzmByBdcdyh(bdcdyh) + slbh;
                        bdcSlJbxxDO.setSlbh(slbh);
                    }
                }
            }
        }

        StatisticsProcDto statisticsProcDto = statisticsProcessClient.queryProcessStaticsInfo(processInstanceData.getProcessInstanceId());
        if (statisticsProcDto != null && Objects.nonNull(statisticsProcDto.getProcDueLimitDouble())) {
            bdcSlJbxxDO.setCnqx(DoubleUtils.getString(statisticsProcDto.getProcDueLimitDouble()));
        }
    }

    /**
     * @param bdcSlxxDTO 受理实体
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 向DTO 填充 关联关系主键 和项目必要字段（权利类型、登记小类）
     */
    private void fillSlxxDTO(BdcSlxxDTO bdcSlxxDTO) {
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlxxDTO.getBdcSlJbxx();
        if (bdcSlJbxxDO == null || StringUtils.isBlank(bdcSlJbxxDO.getGzldyid())) {
            throw new AppException("工作流定义ID为空");
        }
        if (CollectionUtils.isEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            throw new AppException("待创建项目列表为空");
        }
        // 填充 关联主键 和 权利类型等内容
        String jbxxId = UUIDGenerator.generate16();
        bdcSlJbxxDO.setJbxxid(jbxxId);

        if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            //存储单元号与qjgldm关系,同一单元号qjgldm一致
            Map<String, String> dyhQjgldm = new HashMap<>(bdcSlxxDTO.getBdcSlXmList().size());
            Integer lzfs =null;
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                BdcSlXmDO bdcSlXmDO = bdcSlXmDTO.getBdcSlXm();
                DsfSlxxDTO dsfSlxxDTO = bdcSlXmDTO.getDsfSlxxDTO();
                bdcSlXmDO.setXmid(UUIDGenerator.generate16());
                bdcSlXmDO.setJbxxid(jbxxId);
                if (dyhQjgldm.containsKey(bdcSlXmDO.getBdcdyh())) {
                    bdcSlXmDO.setQjgldm(dyhQjgldm.get(bdcSlXmDO.getBdcdyh()));

                }
                if (dsfSlxxDTO != null) {
                    if (dsfSlxxDTO.getSply() != null && NumberUtils.isNumber(dsfSlxxDTO.getSply()) && !dsfSlxxDTO.getSply().contains(".")) {
                        bdcSlXmDO.setXmywlx(Integer.parseInt(dsfSlxxDTO.getSply()));
                    }
                    bdcSlXmDO.setSpxtywh(dsfSlxxDTO.getSpxtywh());
                    //根据定义id查找流程配置数据，设置登记小类和权利类型
                    setQllxAndDjxl(bdcSlJbxxDO.getGzldyid(), bdcSlXmDO, dsfSlxxDTO);
                    //组合贷款流程处理顺序号为1
                    if (zhdkGzldyidList.contains(bdcSlJbxxDO.getGzldyid())) {
                        LOGGER.warn("组合贷款流程处理顺序号为1");
                        dsfSlxxDTO.setSxh(1);
                    }
                    //领证方式处理：外网处理只有第一条项目有赋值lzfs，循环放入每个项目
                    if(dsfSlxxDTO.getLzfs() != null){
                        lzfs =dsfSlxxDTO.getLzfs();
                    }else if(lzfs != null){
                        dsfSlxxDTO.setLzfs(lzfs);
                    }
                }

                // 保存 bdc_sl_jyxx
                BdcSlJyxxDO bdcSlJyxxDO = bdcSlXmDTO.getBdcSlJyxxDO();
                if (bdcSlXmDTO.getBdcSlJyxxDO() != null
                        && CheckParameter.checkAnyParameter(bdcSlJyxxDO)) {
                    if (StringUtils.isNotBlank(bdcSlJyxxDO.getJyxxid())) {
                        bdcSlJyxxDO.setJyxxid(UUIDGenerator.generate16());
                    }
                    bdcSlJyxxDO.setXmid(bdcSlXmDO.getXmid());
                    bdcSlJyxxService.insertBdcSlJyxx(bdcSlJyxxDO);
                }

                //受理权利信息
                if (bdcSlXmDTO.getBdcSlQl() != null) {
                    bdcSlXmDTO.getBdcSlQl().setXmid(bdcSlXmDO.getXmid());
                }

                // 项目历史关系
                //关联预告预抵押,与当前权利类型匹配，符合带入预告并注销
                if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getGlygydy())){
                    for(BdcSlYgDTO bdcSlYgDTO:bdcSlXmDTO.getGlygydy()){
                        Boolean ppyg =bdcAddGwcSjclCommonService.checkPpYg(bdcSlYgDTO,bdcSlXmDO.getQllx());
                        if(Boolean.TRUE.equals(ppyg)) {
                            BdcSlXmLsgxDO wlxmlsgx = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcSlYgDTO.getXmid(), "", "", "");
                            wlxmlsgx.setSfwlzs(CommonConstantUtils.SF_S_DM);
                            wlxmlsgx.setZxyql(CommonConstantUtils.SF_S_DM);
                            if(CollectionUtils.isEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())){
                                bdcSlXmDTO.setBdcSlXmLsgxList(new ArrayList<>());
                            }
                            bdcSlXmDTO.getBdcSlXmLsgxList().add(wlxmlsgx);

                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())) {


                    //土地证信息,生成外联历史关系
                    dealTdzxx(bdcSlXmDTO, bdcSlXmDO, bdcSlJbxxDO.getGzldyid());

                    List<BdcSlXmLsgxDO> slXmLsgxDOList = new ArrayList<>();
                    for (BdcSlXmLsgxDO slXmLsgxDO : bdcSlXmDTO.getBdcSlXmLsgxList()) {
                        if (CheckParameter.checkAnyParameter(slXmLsgxDO)) {
                            String yxmid = slXmLsgxDO.getYxmid();
                            if (StringUtils.isNotBlank(yxmid)) {
                                // 验证 原项目的权利是否是现势状态
                                checkYxmIsXszt(yxmid);

                                slXmLsgxDO.setXmid(bdcSlXmDO.getXmid());
                                // 外网申请业务 外联证书 为否
                                if (slXmLsgxDO.getSfwlzs() == null) {
                                    if (!dyhQjgldm.containsKey(bdcSlXmDO.getBdcdyh())) {
                                        //根据上一手数据获取qjgldm
                                        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                                        bdcXmFbQO.setXmid(yxmid);
                                        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                                        if (CollectionUtils.isNotEmpty(bdcXmFbDOList) && StringUtils.isNotBlank(bdcXmFbDOList.get(0).getQjgldm())) {
                                            bdcSlXmDO.setQjgldm(bdcXmFbDOList.get(0).getQjgldm());
                                            dyhQjgldm.put(bdcSlXmDO.getBdcdyh(), bdcXmFbDOList.get(0).getQjgldm());
                                        }

                                    }

                                    slXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_F_DM);
                                }
                                slXmLsgxDO.setGxid(UUIDGenerator.generate16());
                                slXmLsgxDOList.add(slXmLsgxDO);
                            }
                        }
                    }
                    bdcSlXmDTO.setBdcSlXmLsgxList(slXmLsgxDOList);
                }

                // 申请人信息
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlSqrDOList())) {
                    for (BdcSlSqrDO bdcSlSqrDO : bdcSlXmDTO.getBdcSlSqrDOList()) {
                        bdcSlSqrDO.setXmid(bdcSlXmDO.getXmid());
                        bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
                        //处理权利人来源
                        if (dsfSlxxDTO != null && StringUtils.isNotBlank(dsfSlxxDTO.getSply())) {
                            Integer qlrly = BdcSplyQlrlyEnum.getQlrly(Integer.parseInt(dsfSlxxDTO.getSply()));
                            if (qlrly != null) {
                                bdcSlSqrDO.setQlrly(qlrly);
                            }
                        }
                        //处理是否月结
                        if (bdcSlSqrDO.getSfyj() != null) {
                            if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSqrDO.getSqrlb())) {
                                dsfSlxxDTO.setQlrsfyj(bdcSlSqrDO.getSfyj());
                            } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSqrDO.getSqrlb())) {
                                dsfSlxxDTO.setYwrsfyj(bdcSlSqrDO.getSfyj());
                            }

                        }
                        // 如果权利人类型为空，根据证件种类区分权利人类型
                        if (bdcSlSqrDO.getSqrlx() == null && bdcSlSqrDO.getZjzl() != null) {
                            if (ArrayUtils.contains(CommonConstantUtils.ZJZL_GR_DM, bdcSlSqrDO.getZjzl())) {
                                bdcSlSqrDO.setSqrlx(CommonConstantUtils.QLRLX_GR);
                            }
                            if (ArrayUtils.contains(CommonConstantUtils.ZJZL_QY_DM, bdcSlSqrDO.getZjzl())) {
                                bdcSlSqrDO.setSqrlx(CommonConstantUtils.QLRLX_QY);
                            }
                            if (CommonConstantUtils.ZJZL_QT.equals(bdcSlSqrDO.getZjzl())) {
                                bdcSlSqrDO.setSqrlx(CommonConstantUtils.QLRLX_QT);
                            }
                        }
                        //抵押的代理人从机构表查询 qllx=95 或者预抵押登记小类
                        if (StringUtils.isNotBlank(bdcSlSqrDO.getSqrmc()) && CommonConstantUtils.QLRLB_QLR.equals(bdcSlSqrDO.getSqrlb())
                                && CollectionUtils.isNotEmpty(dlrFromXtjg) && dlrFromXtjg.contains(bdcSlJbxxDO.getGzldyid())
                                && (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx()) || (CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcSlXmDO.getDjxl())))) {
                            //根据名称查询系统机构表
                            BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcSlSqrDO.getSqrmc(), "");
                            if (Objects.nonNull(bdcXtJgDO) && StringUtils.isNotBlank(bdcXtJgDO.getDlrmc())) {
                                bdcSlSqrDO.setDlrmc(bdcXtJgDO.getDlrmc());
                                bdcSlSqrDO.setDlrdh(bdcXtJgDO.getDlrdh());
                                bdcSlSqrDO.setDlrzjzl(Integer.valueOf(bdcXtJgDO.getDlrzjzl()));
                                bdcSlSqrDO.setDlrzjh(bdcXtJgDO.getDlrzjh());
                            }
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlSqrDTOList())) {
                    for (BdcSlSqrDTO bdcSlSqrDTO : bdcSlXmDTO.getBdcSlSqrDTOList()) {
                        BdcSlSqrDO bdcSlSqrDO = bdcSlSqrDTO.getBdcSlSqrDO();
                        if (bdcSlSqrDO != null) {
                            bdcSlSqrDO.setXmid(bdcSlXmDO.getXmid());
                            bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
                            //处理权利人来源
                            if (dsfSlxxDTO != null && StringUtils.isNotBlank(dsfSlxxDTO.getSply())) {
                                Integer qlrly = BdcSplyQlrlyEnum.getQlrly(Integer.parseInt(dsfSlxxDTO.getSply()));
                                if (qlrly != null) {
                                    bdcSlSqrDO.setQlrly(qlrly);
                                }
                            }
                            // 如果权利人类型为空，根据证件种类区分权利人类型
                            if (bdcSlSqrDO.getSqrlx() == null && bdcSlSqrDO.getZjzl() != null) {
                                if (ArrayUtils.contains(CommonConstantUtils.ZJZL_GR_DM, bdcSlSqrDO.getZjzl())) {
                                    bdcSlSqrDO.setSqrlx(CommonConstantUtils.QLRLX_GR);
                                }
                                if (ArrayUtils.contains(CommonConstantUtils.ZJZL_QY_DM, bdcSlSqrDO.getZjzl())) {
                                    bdcSlSqrDO.setSqrlx(CommonConstantUtils.QLRLX_QY);
                                }
                                if (CommonConstantUtils.ZJZL_QT.equals(bdcSlSqrDO.getZjzl())) {
                                    bdcSlSqrDO.setSqrlx(CommonConstantUtils.QLRLX_QT);
                                }
                            }
                            //抵押的代理人从机构表查询 qllx=95 或者预抵押登记小类
                            if (StringUtils.isNotBlank(bdcSlSqrDO.getSqrmc()) && CommonConstantUtils.QLRLB_QLR.equals(bdcSlSqrDO.getSqrlb())
                                    && CollectionUtils.isNotEmpty(dlrFromXtjg) && dlrFromXtjg.contains(bdcSlJbxxDO.getGzldyid())
                                    && (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx()) || (CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(bdcSlXmDO.getDjxl())))) {
                                //根据名称查询系统机构表
                                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcSlSqrDO.getSqrmc(), "");
                                if (Objects.nonNull(bdcXtJgDO) && StringUtils.isNotBlank(bdcXtJgDO.getDlrmc())) {
                                    bdcSlSqrDO.setDlrmc(bdcXtJgDO.getDlrmc());
                                    bdcSlSqrDO.setDlrdh(bdcXtJgDO.getDlrdh());
                                    bdcSlSqrDO.setDlrzjzl(Integer.valueOf(bdcXtJgDO.getDlrzjzl()));
                                    bdcSlSqrDO.setDlrzjh(bdcXtJgDO.getDlrzjh());
                                }
                            }
                        }
                    }
                }

                //领证人信息
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlLzrDOList())) {
                    for (BdcSlLzrDO bdcSlLzrDO : bdcSlXmDTO.getBdcSlLzrDOList()) {
                        if (bdcSlLzrDO != null && CheckParameter.checkAnyParameter(bdcSlLzrDO)) {
                            bdcSlLzrDO.setXmid(bdcSlXmDO.getXmid());
                            bdcSlLzrDO.setLzrid(UUIDGenerator.generate16());
                            if (dsfSlxxDTO != null && StringUtils.isNotBlank(bdcSlLzrDO.getLzrmc())) {
                                dsfSlxxDTO.setHasLzr(true);
                            }
                        }
                    }

                }

                //收费信息
                if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSfxxDTOList())){
                    for (BdcSfxxDTO bdcSfxxDTO : bdcSlXmDTO.getBdcSfxxDTOList()) {
                        if (bdcSfxxDTO != null && CheckParameter.checkAnyParameter(bdcSfxxDTO)) {
                            if(bdcSfxxDTO.getBdcSlSfxxDO() != null &&CheckParameter.checkAnyParameter(bdcSfxxDTO.getBdcSlSfxxDO())) {
                                bdcSfxxDTO.getBdcSlSfxxDO().setXmid(bdcSlXmDO.getXmid());
                                bdcSfxxDTO.getBdcSlSfxxDO().setSfxxid(UUIDGenerator.generate16());
                                //主表收费状态取值逻辑：
                                // 如果收费信息表有值以收费信息为准
                                //收费信息表状态为空,根据收费项目,收费项目存在未缴费的主表状态为未缴费,均为已缴费主表为已缴费
                                Integer sfzt =bdcSfxxDTO.getBdcSlSfxxDO().getSfzt();
                                //收费项目信息
                                if(CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmDOS())){
                                    for(BdcSlSfxmDO bdcSlSfxmDO:bdcSfxxDTO.getBdcSlSfxmDOS()){
                                        bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
                                        bdcSlSfxmDO.setSfxxid(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid());
                                        if(sfzt ==null &&!CommonConstantUtils.SFZT_YJF.equals(bdcSlSfxmDO.getSfzt())){
                                            sfzt =CommonConstantUtils.SFZT_WJF;
                                        }
                                    }
                                    if(sfzt ==null){
                                        sfzt =CommonConstantUtils.SFZT_YJF;
                                    }
                                    bdcSfxxDTO.getBdcSlSfxxDO().setSfzt(sfzt);
                                }
                            }
                        }
                    }
                }

                //税务信息
                if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSwxxDTOList())){
                    for (BdcSwxxDTO bdcSwxxDTO : bdcSlXmDTO.getBdcSwxxDTOList()) {
                        if(Objects.nonNull(bdcSwxxDTO) && CheckParameter.checkAnyParameter(bdcSwxxDTO)){
                            if(Objects.nonNull(bdcSwxxDTO.getBdcSlHsxxDO())&& CheckParameter.checkAnyParameter(bdcSwxxDTO.getBdcSlHsxxDO())){
                                bdcSwxxDTO.getBdcSlHsxxDO().setXmid(bdcSlXmDO.getXmid());
                                bdcSwxxDTO.getBdcSlHsxxDO().setHsxxid(UUIDGenerator.generate16());
                            }
                        }
                    }
                }

            }

            // 组合类业务 需要根据SXH再次处理 项目历史关系
            List<String> yxmids = new ArrayList<>(bdcSlxxDTO.getBdcSlXmList().size());
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                //主历史关系
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =new ArrayList<>();
                if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())) {
                    bdcSlXmLsgxDOList =bdcSlXmDTO.getBdcSlXmLsgxList().stream().filter(bdcSlXmLsgxDO -> !CommonConstantUtils.SF_S_DM.equals(bdcSlXmLsgxDO.getSfwlzs())).collect(Collectors.toList());
                }
                if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)
                        && bdcSlXmDTO.getDsfSlxxDTO() != null
                        && bdcSlXmDTO.getDsfSlxxDTO().isGlYxm()
                        && bdcSlXmDTO.getDsfSlxxDTO().getSxh() != null
                        && bdcSlXmDTO.getDsfSlxxDTO().getSxh() > 1) {
                    //获取所有当前单元号的项目
                    String yxmid = getYxmid(bdcSlxxDTO.getBdcSlXmList(), bdcSlXmDTO.getDsfSlxxDTO().getSxh() - 1, yxmids);
                    if (StringUtils.isNotBlank(yxmid)) {
                        yxmids.add(yxmid);
                        BdcSlXmLsgxDO slXmLsgxDO = new BdcSlXmLsgxDO();
                        slXmLsgxDO.setYxmid(yxmid);
                        slXmLsgxDO.setXmid(bdcSlXmDTO.getBdcSlXm().getXmid());
                        slXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_F_DM);
                        slXmLsgxDO.setGxid(UUIDGenerator.generate16());
                        if (bdcSlXmDTO.getBdcSlXmLsgxList() == null) {
                            bdcSlXmDTO.setBdcSlXmLsgxList(new ArrayList<>());
                        }
                        bdcSlXmDTO.getBdcSlXmLsgxList().add(slXmLsgxDO);
                    } else {
                        throw new AppException("组合流程，获取不到原项目");
                    }
                }
            }

            // 如果是预转现 则 向 xmlsgx列表中 新增 预告 、预抵押的历史关系 （包括预告的土地证）
            if (CollectionUtils.isNotEmpty(yzxlc) && yzxlc.contains(bdcSlJbxxDO.getGzldyid())) {
                dealYzxLsgx(bdcSlxxDTO);
            }else if (CollectionUtils.isNotEmpty(withdylc) && withdylc.contains(bdcSlJbxxDO.getGzldyid())) {
                //带抵押流程,处理历史关系（新抵押与新产权建立历史关系，与原抵押建立外联历史关系）,修改新抵押权利数据来源,权利人数据来源,读取原抵押
                dealWithdyLsgx(bdcSlxxDTO);
            }

        }
        //处理证书序号
        dealZsxh(bdcSlxxDTO);
    }

    /**
     * @param
     * @return 受理人
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理传递过来的受理人信息
     */
    private String dealSlrxx(BdcSlxxDTO bdcSlxxDTO, String yhxzqdm) {
        String slrdlm = "";
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlxxDTO.getBdcSlJbxx();
        LOGGER.info("包含受理人的创建，slr:{}", bdcSlJbxxDO.getSlr());
        String dsfSlrdlm = bdcSlJbxxDO.getSlr();
        bdcSlJbxxDO.setSlr(null);
        // 传递过来的受理人为 登录名
        UserDto userDto = userManagerUtils.getUserByNameAndXzqdm(dsfSlrdlm, yhxzqdm);
        if (userDto != null) {
            slrdlm = userDto.getUsername();
            bdcSlJbxxDO.setSlr(userDto.getAlias());
            bdcSlJbxxDO.setSlrid(userDto.getId());
            if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
                throw new AppException("当前用户缺失组织信息,用户名："+dsfSlrdlm+"行政区代码："+yhxzqdm);
            }
            // 有受理人场景，使用受理人组织机构赋值区县代码
            OrganizationDto organizationDto = userDto.getOrgRecordList().get(0);
            if (organizationDto != null) {
                bdcSlJbxxDO.setDjjg(bdcJbxxService.queryDjjgByUser(userDto));
                bdcSlJbxxDO.setQxdm(organizationDto.getRegionCode());
                bdcSlJbxxDO.setDjbmdm(organizationDto.getCode());
                for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                    // 区县代码
                    bdcSlXmDTO.getDsfSlxxDTO().setQxdm(organizationDto.getRegionCode());
                    // 登记部门编码
                    bdcSlXmDTO.getDsfSlxxDTO().setDjbmdm(organizationDto.getCode());
                }
            }
        }
        return slrdlm;

    }

    /**
     * @param bdcSlxxDTO 受理实体
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 如果是预抵押 需要 关联上一手预告
     */
    private void dealYzxLsgx(BdcSlxxDTO bdcSlxxDTO) {
        String bdcdyh = bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm().getBdcdyh();
        List<BdcYgDO> bdcYgDOList = listBdcYg(bdcdyh);
        for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
            Integer[] ygdjzl = CommonConstantUtils.YG_YGDJZL_ARR;
            // 如果是抵押权
            if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDTO.getBdcSlXm().getQllx())) {
                ygdjzl = CommonConstantUtils.YG_YGDJZL_YDY_ARR;
            }
            dealYgOrYdyLsgx(bdcSlXmDTO, bdcYgDOList, ygdjzl);
        }
    }

    /**
     * @param bdcSlXmDTO 受理项目实体
     * @param bdcYgDOList 预告信息
     * @param ygdjzl 预告登记种类
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理预告或者预抵押的 历史关系
     */
    private void dealYgOrYdyLsgx(BdcSlXmDTO bdcSlXmDTO, List<BdcYgDO> bdcYgDOList, Integer[] ygdjzl) {
        for (BdcYgDO bdcYgDO : bdcYgDOList) {
            if (ArrayUtils.contains(ygdjzl, bdcYgDO.getYgdjzl())) {
                if (bdcSlXmDTO.getBdcSlXmLsgxList() == null) {
                    bdcSlXmDTO.setBdcSlXmLsgxList(new ArrayList<>());
                    LOGGER.error("当前项目历史关系为空,项目ID:{}", bdcSlXmDTO.getBdcSlXm().getXmid());
                }
                //是否匹配预告
                boolean isppyg = false;
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())) {
                    List<BdcSlXmLsgxDO> slXmLsgxDOList = new ArrayList<>();
                    for (BdcSlXmLsgxDO xmLsgxDO : bdcSlXmDTO.getBdcSlXmLsgxList()) {
                        if (StringUtils.equals(bdcYgDO.getXmid(), xmLsgxDO.getYxmid())) {
                            //预告作为外联历史关系
                            xmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                            xmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                            slXmLsgxDOList.add(xmLsgxDO);
                            isppyg = true;
                            break;
                        }
                    }
                    if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                        bdcSlXmDTO.setBdcSlXmLsgxList(slXmLsgxDOList);
                    }
                }
                if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                    if (bdcSlXmDTO.getBdcSlXm() != null && bdcSlXmDTO.getBdcSlXm().getQllx() != null) {
                        //原产权作为作为转移的历史关系
                        BdcQl bdcQl = listBdcCq(bdcYgDO.getBdcdyh(), bdcSlXmDTO.getBdcSlXm().getQllx().toString());
                        if (bdcQl != null) {
                            BdcSlXmLsgxDO cqlsgx = new BdcSlXmLsgxDO(bdcSlXmDTO.getBdcSlXm().getXmid(),
                                    bdcQl.getXmid(), "", "", "");
                            cqlsgx.setZxyql(CommonConstantUtils.SF_S_DM);
                            cqlsgx.setSfwlzs(CommonConstantUtils.SF_F_DM);
                            bdcSlXmDTO.getBdcSlXmLsgxList().add(cqlsgx);
                        } else {
                            LOGGER.error("期转现流程未生成产权历史关系：{}", bdcSlXmDTO.getBdcSlXm());
                        }
                    } else {
                        LOGGER.error("期转现流程未生成产权历史关系：{}", bdcSlXmDTO.getBdcSlXm());
                    }

                }
                //外网申请没有传预告xmid,系统自动带入
                if (!isppyg) {
                    BdcSlXmLsgxDO wlbdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDTO.getBdcSlXm().getXmid(),
                            bdcYgDO.getXmid(), "", "", "");
                    //预告作为外联证书
                    wlbdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                    wlbdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                    bdcSlXmDTO.getBdcSlXmLsgxList().add(wlbdcSlXmLsgxDO);
                }

                //判断预告是否有 外联土地证
                BdcSlXmLsgxDO tdzLsgxDO = bdcWlzsService.wltdzNoInsert(bdcYgDO.getXmid(), "", bdcSlXmDTO.getBdcSlXm());
                if (tdzLsgxDO != null) {
                    bdcSlXmDTO.getBdcSlXmLsgxList().add(tdzLsgxDO);
                }

            }
        }
    }

    private List<BdcYgDO> listBdcYg(String bdcdyh) {
        List<BdcYgDO> bdcYgDOList = new ArrayList<>();
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, CommonConstantUtils.QLLX_YG_DM.toString(), qsztList);
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            for (BdcQl bdcQl : bdcQlList) {
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                bdcYgDOList.add(bdcYgDO);
            }
        }
        return bdcYgDOList;
    }

    private BdcQl listBdcCq(String bdcdyh, String qllx) {
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            //现势产权默认只一条
            return bdcQlList.get(0);
        }
        return null;

    }

    /**
     * @param bdcSlxxDTO 受理信息实体
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 带抵押流程,处理历史关系（新抵押与新产权建立历史关系，与原抵押建立外联历史关系）,修改新抵押权利数据来源,权利人数据来源,读取原抵押
     */
    private void dealWithdyLsgx(BdcSlxxDTO bdcSlxxDTO) {
        if(CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            for(BdcSlXmDTO bdcSlXmDTO:bdcSlxxDTO.getBdcSlXmList()) {
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDTO.getBdcSlXm().getQllx())) {
                    List<Integer> qsztList = new ArrayList(1);
                    qsztList.add(CommonConstantUtils.QSZT_VALID);
                    List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcSlXmDTO.getBdcSlXm().getBdcdyh(), CommonConstantUtils.QLLX_DYAQ_DM.toString(), qsztList);
                    if(CollectionUtils.isNotEmpty(bdcQlList)){
                        if(bdcQlList.size() >1){
                            throw new AppException("带抵押业务,不动产单元"+bdcSlXmDTO.getBdcSlXm().getBdcdyh()+"存在多条现势的抵押信息");
                        }
                        for(BdcSlXmLsgxDO slXmLsgxDO:bdcSlXmDTO.getBdcSlXmLsgxList()){
                            if(!CommonConstantUtils.SF_S_DM.equals(slXmLsgxDO.getSfwlzs())) {
                                //与新产权建立主历史关系,不注销原权利
                                slXmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                            }
                        }
                        //原抵押作为外联历史关系
                        BdcSlXmLsgxDO wlbdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDTO.getBdcSlXm().getXmid(),
                                bdcQlList.get(0).getXmid(), "", "", "");
                        wlbdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                        wlbdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                        bdcSlXmDTO.getBdcSlXmLsgxList().add(wlbdcSlXmLsgxDO);
                        //权利数据来源为原抵押
                        bdcSlXmDTO.getBdcSlXm().setQlsjly(CommonConstantUtils.QLSJLY_ZH_YDY);
                        //权利人数据来源为原权利权利人
                        bdcSlXmDTO.getBdcSlXm().setQlrsjly(Constants.QLRSJLY_YTQL_QLR);
                        //义务人数据来源(义务人数据来源为原权利人)
                        bdcSlXmDTO.getBdcSlXm().setYwrsjly(Constants.QLRSJLY_YQLR);
                    }
                }
            }
        }
    }

    /**
     * @param bdcSlXmDTOList 受理项目集合
     * @param sxh 顺序号
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据SXH 获取 xmid
     */
    private String getYxmid(List<BdcSlXmDTO> bdcSlXmDTOList, Integer sxh, List<String> yxmids) {
        String yxmid = "";
        if (CollectionUtils.isNotEmpty(bdcSlXmDTOList)) {
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlXmDTOList) {
                if (bdcSlXmDTO.getDsfSlxxDTO() != null && bdcSlXmDTO.getDsfSlxxDTO().getSxh() == sxh) {
                    //当项目数量大于3时，不会是单个组合按揭业务，yxmid 不会是已经成为原项目的数据
                    if (CollectionUtils.size(bdcSlXmDTOList) > 3) {
                        if (!yxmids.contains(bdcSlXmDTO.getBdcSlXm().getXmid())) {
                            yxmid = bdcSlXmDTO.getBdcSlXm().getXmid();
                            break;
                        }
                    } else {
                        yxmid = bdcSlXmDTO.getBdcSlXm().getXmid();
                        break;
                    }
                }
            }
        }
        return yxmid;
    }

    /**
     * @param yxmid 原项目ID
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证 原项目是否为现势状态
     */
    private void checkYxmIsXszt(String yxmid) {
        if (StringUtil.isNotBlank(yxmid)) {
            BdcQl bdcQl = bdcQllxRestService.queryQlxx(yxmid);
            if (bdcQl == null) {
                throw new AppException("外网创建，查不到原项目权利信息,yxmid=" + yxmid);
            }
            if(!CommonConstantUtils.QSZT_VALID.equals(bdcQl.getQszt())){
                throw new AppException("外网创建，原权利为非现势状态,qszt="+bdcQl.getQszt()+"yxmid="+yxmid);
            }
        }
    }

    /**
     * @param gzldyid 工作流定义ID
     * @param bdcSlXmDO 受理项目
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 向受理项目实体中填充 权利类型和登记小类,不动产类型
     */

    private void setQllxAndDjxl(String gzldyid, BdcSlXmDO bdcSlXmDO, DsfSlxxDTO dsfSlxxDTO) {
        if (StringUtils.isNotBlank(bdcSlXmDO.getBdcdyh()) && StringUtils.isNotBlank(gzldyid)) {
            Integer yqllx =null;
            if(!StringUtils.equals(CommonConstantUtils.DZWTZM_FW,BdcdyhToolUtils.getDzwTzm(bdcSlXmDO.getBdcdyh()))) {
                String qsxz = acceptBdcdyFeignService.getQsxzByBdcdyh(bdcSlXmDO.getBdcdyh());
                if (StringUtils.isNotBlank(qsxz)) {
                    yqllx = Integer.parseInt(qsxz);
                }
            }
            // 组合流程 BDCSLXMDO中会传递QLLX
            Integer qllx = bdcSlXmDO.getQllx();
            String djxl = bdcSlXmDO.getDjxl();
            DjxlPzCxQO djxlPzCxQO =new DjxlPzCxQO();
            djxlPzCxQO.setGzldyid(gzldyid);
            djxlPzCxQO.setBdcdyh(bdcSlXmDO.getBdcdyh());
            djxlPzCxQO.setYqllx(yqllx);
            djxlPzCxQO.setYxmid("");
            // dyhcxlx 默认为 1 ，暂时不支持 海域和构筑物 的判断
            djxlPzCxQO.setDyhcxlx("1");
            djxlPzCxQO.setQllx(qllx);
            djxlPzCxQO.setDjxl(djxl);
            List<BdcDjxlPzDO> bdcDjxlPzDOList =bdcDjxlPzService.queryBdcDjxlPz(djxlPzCxQO);
            BdcDjxlPzDO bdcDjxlPzDO = null;
            if (bdcDjxlPzDOList.size() > 1) {
                if (dsfSlxxDTO.getSxh() != null) {
                    int pzSize = bdcDjxlPzDOList.size();
                    int ysxh = dsfSlxxDTO.getSxh();
                    // 取余
                    int slXmSxh = ysxh > pzSize ? ysxh % pzSize : ysxh;
                    // 如果可以整除 则为 配置长度
                    slXmSxh = slXmSxh == 0 ? pzSize : slXmSxh;
                    for (BdcDjxlPzDO temp : bdcDjxlPzDOList) {
                        if (temp.getSxh().equals(slXmSxh)) {
                            bdcDjxlPzDO = temp;
                            break;
                        }
                    }
                } else {
                    throw new AppException("找到多条登记小类配置：查询条件-工作流定义ID:"+gzldyid+"dyhqllx:"+bdcDjxlPzDOList.get(0).getDyhqllx()+"djxl:"+djxl+"qllx:"+qllx);
                }
            } else {
                bdcDjxlPzDO = bdcDjxlPzDOList.get(0);
            }
            if (bdcDjxlPzDO != null) {
                bdcSlXmDO.setQllx(bdcDjxlPzDO.getQllx());
                bdcSlXmDO.setDjxl(bdcDjxlPzDO.getDjxl());
                //处理不动产类型
                if (bdcDjxlPzDO.getBdclx() != null) {
                    bdcSlXmDO.setBdclx(bdcDjxlPzDO.getBdclx());
                } else {
                    String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(bdcSlXmDO.getBdcdyh(), "");
                    if (StringUtils.isNotBlank(bdclx)) {
                        bdcSlXmDO.setBdclx(Integer.parseInt(bdclx.split("/")[0]));
                    }
                }
            } else {
                throw new AppException("匹配不到正确的登记小类配置：查询条件-工作流定义ID:"+gzldyid+"dyhqllx:"+bdcDjxlPzDOList.get(0).getDyhqllx()+"djxl:"+djxl+"qllx:"+qllx+"sxh:"+dsfSlxxDTO.getSxh());
            }
        }
    }


    /**
     * @param bdcSlxxDTO 受理信息
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证不动产单元
     */
    @Override
    public List<Map<String, Object>> yzBdcdy(BdcSlxxDTO bdcSlxxDTO) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 工作流定义ID
        String processDefKey;
        if (bdcSlxxDTO.getBdcSlJbxx() != null
                && StringUtils.isNotBlank(bdcSlxxDTO.getBdcSlJbxx().getGzldyid())) {
            // 工作流定义ID
            processDefKey = bdcSlxxDTO.getBdcSlJbxx().getGzldyid();
        } else {
            throw new AppException("工作流定义ID为空，可能原因：未传工作流定义ID或接口参数未按接口文档规范");
        }

        // 转换 DTO
        // 获取规则验证的参数列表
        List<Map<String, Object>> gzyzParamList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            for (BdcSlXmDTO slXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                Map<String, Object> paramMap = new HashMap<>();
                if (slXmDTO.getBdcSlXm() != null && StringUtils.isNotBlank(slXmDTO.getBdcSlXm().getBdcdyh())) {
                    paramMap.put("bdcdyh", slXmDTO.getBdcSlXm().getBdcdyh());
                    paramMap.put("zl", slXmDTO.getBdcSlXm().getZl());
                    paramMap.put("qjgldm",slXmDTO.getBdcSlXm().getQjgldm());

                    // 增加yxmid参数 用于验证外网创建的时候，ybdcdyh和传入的bdcdyh是否一致
                    List<BdcSlXmLsgxDO> listlsgx = slXmDTO.getBdcSlXmLsgxList();
                    if (CollectionUtils.isNotEmpty(listlsgx)) {
                        paramMap.put("yxmid", listlsgx.get(0).getYxmid());
                    }
                    //组织交易信息获取参数
                    JSONObject jsonObject =new JSONObject();
                    jsonObject.put("bdcdyh",slXmDTO.getBdcSlXm().getBdcdyh());
                    jsonObject.put("qjgldm",slXmDTO.getBdcSlXm().getQjgldm());
                    jsonObject.put("xmid",paramMap.getOrDefault("yxmid",""));
                    paramMap.put("obj",jsonObject);

                }
                if (slXmDTO.getDsfSlxxDTO() != null && StringUtils.isNotBlank(slXmDTO.getDsfSlxxDTO().getRoomid())) {
                    paramMap.put("roomid", slXmDTO.getDsfSlxxDTO().getRoomid());
                }

                // 其他一些需要走规则验证的参数
                paramMap.put("bdcSlXmDTO", slXmDTO);

                if (MapUtils.isNotEmpty(paramMap)) {
                    gzyzParamList.add(paramMap);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(gzyzParamList)
                && StringUtils.isNotBlank(processDefKey)) {
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            if (StringUtils.isNotBlank(bdcSlxxDTO.getZhbs())) {
                //调用方传组合标识,以调用方传的为准
                bdcGzYzQO.setZhbs(processDefKey + bdcSlxxDTO.getZhbs());
            } else {
                bdcGzYzQO.setZhbs(processDefKey + GZYZ_SUFIX);
                if (bdcSlxxDTO.isBeforeCjGzyz()) {
                    bdcGzYzQO.setZhbs(processDefKey + BEFORE_GZYZ_SUFIX);
                }
            }

            bdcGzYzQO.setParamList(gzyzParamList);
            List<Map<String, Object>> lcyzResultList = bdcGzyzService.yzBdcgz(bdcGzYzQO);
            if (CollectionUtils.isNotEmpty(lcyzResultList)) {
                resultList.addAll(lcyzResultList);
            }
        }
        //匹配土地证验证
        if (CollectionUtils.isNotEmpty(bdcSlxxDTO.getYzFctdList())) {
            List<Map<String, Object>> pptdzParamList = new ArrayList<>();
            for (GltdzxxDTO yzFctdxx : bdcSlxxDTO.getYzFctdList()) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("xnxmid", yzFctdxx.getFcqzxmid());
                paramMap.put("tdxmid", yzFctdxx.getXmid());
                if (MapUtils.isNotEmpty(paramMap)) {
                    pptdzParamList.add(paramMap);
                }
            }
            if (CollectionUtils.isNotEmpty(pptdzParamList)) {
                BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
                bdcGzYzQO.setZhbs(CommonConstantUtils.LC_ZHGZBS_PPTDZ);
                bdcGzYzQO.setParamList(pptdzParamList);
                List<Map<String, Object>> ppyzResultList = bdcGzyzService.yzBdcgz(bdcGzYzQO);
                if (CollectionUtils.isNotEmpty(ppyzResultList)) {
                    resultList.addAll(ppyzResultList);
                }

            }

        }
        return resultList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return java.lang.Boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请项目 根据工作流实例ID 自动转发
     */
    @Override
    public Boolean autoTurnWorkflow(String gzlslid, AutoForwardTaskDTO autoForwardTaskDTO) {
        LOGGER.info("流程自动转发调用转发接口开始,工作流实例ID:{},转发节点信息：{}", gzlslid, autoForwardTaskDTO);
        // 向审核节点转发
        try {
            if (StringUtils.isNotBlank(gzlslid)) {
                ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
                if (processInstanceData != null) {
                    List<TaskData> taskList = processTaskClient.processLastTasks(gzlslid);
                    if (CollectionUtils.isNotEmpty(taskList)) {
                        TaskData taskData = taskList.get(0);
                        String group = StringUtils.equals(forwardGroup, CommonConstantUtils.BOOL_TRUE) ? "organization" : null;
                        
                        if(StringUtils.equals(CommonConstantUtils.BOOL_TRUE,zdzfStartRegion)){
                            //自动转发,转发角色默认筛选创建人指定地区下的角色,同组织排在前面
                            group ="startRegion";
                        }
                        List<ForwardTaskDto> forwardTaskDtos = flowableNodeClient.getForwardUserTasks(taskData.getTaskId(), group);
                        if (CollectionUtils.isEmpty(forwardTaskDtos)) {
                            throw new AppException("自动转发失败,未获取到转发节点");
                        }
                        ForwardTaskDto forwardTaskDto = null;
                        if (autoForwardTaskDTO != null && StringUtils.isNotBlank(autoForwardTaskDTO.getJdmc())) {
                            for (ForwardTaskDto taskDto : forwardTaskDtos) {
                                if (StringUtils.equals(autoForwardTaskDTO.getJdmc(), taskDto.getActivityName())) {
                                    forwardTaskDto = taskDto;
                                    break;
                                }
                            }
                        } else {
                            //未指定节点,任取一个转发
                            forwardTaskDto = forwardTaskDtos.get(0);
                        }
                        if (forwardTaskDto == null) {
                            throw new AppException("自动转发失败,未获取到转发节点,检查转发节点配置");
                        }

                        forwardTaskDto.setTaskId(taskData.getTaskId());
                        //赋值默认角色
                        List<String> roleIds = forwardTaskDto.getRoleIds();
                        if (CollectionUtils.isNotEmpty(roleIds)) {
                            forwardTaskDto.setSelectRoleIds(roleIds.get(0));
                            LOGGER.info("工作流实例ID:{},默认转发角色ID:{}", gzlslid, roleIds.get(0));
                        }
                        if (autoForwardTaskDTO != null && StringUtils.isNotBlank(autoForwardTaskDTO.getRoleid())) {
                            forwardTaskDto.setSelectRoleIds(autoForwardTaskDTO.getRoleid());
                            LOGGER.info("工作流实例ID:{}, 转发给指定角色ID:{}", gzlslid, autoForwardTaskDTO.getRoleid());
                        }
                        if (autoForwardTaskDTO != null && StringUtils.isNotBlank(autoForwardTaskDTO.getUsername())) {
                            forwardTaskDto.setSelectUserNames(autoForwardTaskDTO.getUsername());
                            LOGGER.info("工作流实例ID:{}, 转发给指定用户:{}", gzlslid, autoForwardTaskDTO.getUsername());
                        }
                        LOGGER.info("{}自动转发消息：{}",gzlslid, JSON.toJSONString(forwardTaskDto));
                        boolean forwardResult = taskHandleClient.complete(Lists.newArrayList(forwardTaskDto));
                        if (!forwardResult) {
                            LOGGER.error("自动转发失败，工作流实例ID：{}", gzlslid);
                        } else {
                            LOGGER.info("自动转发成功，工作流实例ID：{}", gzlslid);
                        }
                        return forwardResult;
                    } else {
                        LOGGER.error("自动转发失败，找不到任务,工作流实例ID：{}", gzlslid);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("gzlslid:{}创建成功，但向审核节点转发失败",gzlslid, e);
            throw new AppException("创建成功，但向审核节点转发失败");
        }
        return Boolean.FALSE;
    }

    @Override
    public Map<String, String> autoTurnWithGzyz(String gzlslid, AutoForwardTaskDTO autoForwardTaskDTO) {
        HashMap resultMap = new HashMap();
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("自动转发缺失参数工作流实例ID");
        }
        List<TaskData> taskDataList = processTaskClient.processRunningTasks(gzlslid);
        if (CollectionUtils.isEmpty(taskDataList)) {
            throw new AppException("未获取到节点信息");
        }
        String taskid = taskDataList.get(0).getTaskId();
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("未生成项目信息");
        }
        //1.转发前验证
        if (bdcGzyz(gzlslid, bdcXmDTOList.get(0).getGzldyid(), resultMap, taskid)){ return resultMap;}

        LOGGER.info("开始自动签名了taskid{},taskData信息{}", taskid, JSON.toJSONString(taskDataList.get(0)));
        //2.自动签名
        signShxx(gzlslid, taskDataList.get(0), taskid);

        //3.自动转发
        // 判断下个节点是否办结
        Map<String, Boolean> isEnd = flowableNodeClient.isForwardEndOrForceClosedEvent(taskid);
        if (MapUtils.getBooleanValue(isEnd, "isNodeEnd")) {
            ForwardTaskDto forwardTaskDto = new ForwardTaskDto();
            forwardTaskDto.setTaskId(taskid);
            LOGGER.error("{}自动办结：{}",gzlslid, JSONObject.toJSONString(forwardTaskDto));
            taskHandleClient.processEnd(forwardTaskDto);
        } else {
            autoTurnWorkflow(gzlslid, autoForwardTaskDTO);
        }
        resultMap.put("msg", "成功");
        return resultMap;

    }

    /**
     * 外网申请自动转发
     * <ul>
     *     <li>如果节点未被认领，由配置中默认用户自动认领</li>
     *     <li>自动签名：签名信息取上一个节点用户，如果未被认领则取配置中默认用户</li>
     *     <li>转发前验证：必填项和规则验证</li>
     *     <li>转发到下个节点</li>
     * </ul>
     *
     * @param gzlslid            工作流实例ID
     * @param autoForwardTaskDTO 下一节点信息
     * @author <a href="mailto:liyinqiao@gtmap.cn">liaoxiang</a>
     */
    @Override
    public Map<String, String> autoTurn(String gzlslid, AutoForwardTaskDTO autoForwardTaskDTO) {
        HashMap resultMap = new HashMap();
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("自动转发缺失参数工作流实例ID");
        }
        List<TaskData> taskDataList = processTaskClient.processRunningTasks(gzlslid);
        if (CollectionUtils.isEmpty(taskDataList)) {
            throw new AppException("未获取到节点信息");
        }
        TaskData taskData = taskDataList.get(0);
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("未生成项目信息");
        }

        // 1. 认领节点
        if (StringUtils.isBlank(taskData.getTaskAssigin())) {
            if (StringUtils.isBlank(zfrl)) {
                throw new AppException("未配置默认认领用户，无法转发未被认领的件");
            }

            LOGGER.info("自动转发 taskid: {} 默认用户 {} 认领节点：{}", taskData.getTaskId(), zfrl, taskData.getTaskName());
            //feignClaimTask
            WorkFlowCommonDTO workFlowCommonDTO =new WorkFlowCommonDTO();
            workFlowCommonDTO.setCurrentUserName(zfrl);
            workFlowCommonDTO.setTaskId(taskData.getTaskId());
            workFlowCommonDTO.setProcessInstanceId(gzlslid);
            bdcWorkFlowFeignService.feignClaimTask(Collections.singletonList(workFlowCommonDTO));
            // 认领后重新获取任务信息
            taskDataList = processTaskClient.processRunningTasks(gzlslid);
            if (CollectionUtils.isEmpty(taskDataList)) {
                throw new AppException("未获取到节点信息");
            }
            taskData = taskDataList.get(0);
        }

        String taskid = taskData.getTaskId();
        LOGGER.info("自动签名 taskid: {} 签名用户：{} 签名节点：{}", taskid, taskData.getTaskAssigin(), taskData.getTaskName());

        //2.自动签名
        signShxx(gzlslid, taskData, taskid);

        //3. 必填项验证
        List btxyz = (List) (bdcCheckFeignService.feignBtxyz(taskData.getFormKey(), gzlslid, taskid));
        LOGGER.info("{}必填项验证返回值：{}", gzlslid,btxyz);
        if (CollectionUtils.isNotEmpty(btxyz)) {
            StringBuffer btxmsg = new StringBuffer();
            for (int i = 0; i < btxyz.size(); i++) {
                if (i == 0) {
                    btxmsg.append("以下表单有未填项，请填写完整：");
                }
                Map map = (Map) btxyz.get(i);
                btxmsg.append(MapUtils.getString(map, "bdmc")).append("、");
            }
            String msg = btxmsg.toString();
            //返回失败信息
            if (StringUtils.isNotBlank(msg)) {
                resultMap.put("msg", msg);
                LOGGER.info("{}必填项验证接口返回信息：{}", gzlslid,msg);
                return resultMap;
            }
        }

        //4. 规则验证
        if (bdcGzyz(gzlslid, bdcXmDTOList.get(0).getGzldyid(), resultMap, taskid)) {
            return resultMap;
        }

        //5.自动转发
        LOGGER.info("{}开始自动转发 taskid: {}", gzlslid,taskid);
        Boolean flag;
        // 判断下个节点是否办结
        Map<String, Boolean> isEnd = flowableNodeClient.isForwardEndOrForceClosedEvent(taskid);
        if (MapUtils.getBooleanValue(isEnd, "isNodeEnd")) {
            ForwardTaskDto forwardTaskDto = new ForwardTaskDto();
            forwardTaskDto.setTaskId(taskid);
            LOGGER.error("办结：{}", JSONObject.toJSONString(forwardTaskDto));
            flag = taskHandleClient.processEnd(forwardTaskDto);
        } else {
            flag = autoTurnWorkflow(gzlslid, autoForwardTaskDTO);
        }
        if (flag) {
            resultMap.put("msg", "成功");
        } else {
            resultMap.put("msg", "自动转发过程出现异常");
        }
        return resultMap;
    }

    /**
     * 审核信息签名「如果已经签名不再覆盖」
     * 增加审核意见的处理
     *
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private void signShxx(String gzlslid, TaskData taskData, String taskid) {
        BdcShxxDO bdcYShxxDO = bdcShxxFeignService.queryBdcShxxById(taskid);
        if (bdcYShxxDO != null && StringUtils.isNotBlank(bdcYShxxDO.getQmid())
                && StringUtils.isNotBlank(bdcYShxxDO.getShyj()) && bdcYShxxDO.getQmsj() != null) {
            return;
        }

        BdcShxxDO bdcShxxDO = new BdcShxxDO();
        BdcXtMryjDO bdcXtMryjDO = bdcXtMryjFeignService.queryMryj(null, taskData.getProcessKey(), taskData.getTaskName());
        //非空时判断是否需要执行 sql
        if (null != bdcXtMryjDO && StringUtils.isNotBlank(bdcXtMryjDO.getYj())) {
            if (CommonConstantUtils.MRYJ_SJLX_SQL.equals(bdcXtMryjDO.getSjlx())) {
                String[] sqlArr = StringUtils.split(bdcXtMryjDO.getYj(), CommonConstantUtils.ZF_YW_FH);
                if (sqlArr.length > 0 && StringUtils.isNotBlank(sqlArr[0])) {
                    bdcXtMryjDO.setYj(sqlArr[0]);
                    bdcShxxDO.setShyj(bdcShxxFeignService.generateMryjBySql(gzlslid, bdcXtMryjDO));
                }
            } else {
                bdcShxxDO.setShyj(bdcXtMryjDO.getYj());
            }
        }
        bdcShxxDO.setJdmc(taskData.getTaskName());
        bdcShxxDO.setShryxm(taskData.getTaskAssigin());
        bdcShxxDO.setShxxid(taskid);
        bdcShxxDO.setGzlslid(gzlslid);
        bdcShxxFeignService.getShxxSign(bdcShxxDO);
    }

    /**
     * 规则验证返回值处理逻辑
     *
     * @return boolean 规则验证是否异常
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private boolean bdcGzyz(String gzlslid, String gzldyid, HashMap resultMap, String taskid) {
        List list = (List) (bdcCheckFeignService.feignBdcgz(gzldyid, gzlslid, CommonConstantUtils.GZYZ_LX_ZF, taskid));
        LOGGER.info("{}转发验证返回值：{}", gzlslid,list);
        if (CollectionUtils.isNotEmpty(list)) {
            StringBuffer zfyzmsg = new StringBuffer(list.size());
            String resultmsg = "";
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                if (zfyzhl && StringUtils.isNotBlank(map.get("yzlx").toString()) && StringUtils.equals(CommonConstantUtils.YZLX_CONFIRM.toString(), map.get("yzlx").toString())) {
                    //验证类型为可忽略的时候，不放入提示信息
                    continue;
                }
                zfyzmsg.append(map.get("tsxx")).append(",");
                resultmsg = zfyzmsg.toString();
            }
            if (resultmsg.endsWith(",")) {
                resultmsg = resultmsg.substring(0, resultmsg.length() - 1);
            }
            //返回失败信息
            if (StringUtils.isNotBlank(resultmsg)) {
                resultMap.put("msg", resultmsg);
                return true;
            }
        }
        return false;
    }

    /**
     * @param
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理土地证信息, 并生成外联历史关系
     */
    private void dealTdzxx(BdcSlXmDTO bdcSlXmDTO, BdcSlXmDO bdcSlXmDO, String gzldyid) {
        //土地证信息
        if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getGltdzxxDTOList()) && StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid())) {
            //通过传参外联土地证信息
            StringBuilder ybdcqzBuilder = new StringBuilder();
            ybdcqzBuilder.append(bdcSlXmDTO.getBdcSlXm().getYbdcqz());
            for (GltdzxxDTO gltdzxxDTO : bdcSlXmDTO.getGltdzxxDTOList()) {
                if (StringUtils.isNotBlank(gltdzxxDTO.getXmid())) {
                    //查询房产匹配关系
                    List<BdcFctdPpgxDO> ppgxList = bdcPpFeignService.queryBdcFctdPpgx(bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid());
                    if (CollectionUtils.isEmpty(ppgxList)) {
                        List<BdcFctdPpgxDO> tdppgxList = bdcPpFeignService.queryBdcFctdPpgxByTdxmid(gltdzxxDTO.getXmid());
                        if (CollectionUtils.isEmpty(tdppgxList)) {
                            try {
                                bdcPpFeignService.pptd(bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid(), gltdzxxDTO.getXmid());
                                LOGGER.info("房产证项目ID:{}和土地证项目ID:{}匹配成功", bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid(), gltdzxxDTO.getXmid());
                                BdcSlXmLsgxDO wlxmlsgx = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), gltdzxxDTO.getXmid(), "", "", "");
                                wlxmlsgx.setSfwlzs(CommonConstantUtils.SF_S_DM);
                                bdcSlXmDTO.getBdcSlXmLsgxList().add(wlxmlsgx);
                            } catch (Exception e) {
                                LOGGER.error("房产证项目ID:{}和土地证项目ID:{}匹配失败", bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid(), gltdzxxDTO.getXmid(), e);
                            }
                        } else {
                            LOGGER.info("土地证项目ID:{}已存在匹配关系", gltdzxxDTO.getXmid());
                        }
                    } else {
                        LOGGER.info("房产证项目ID:{}已存在匹配关系", bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid());
                        if (StringUtils.isNotBlank(ppgxList.get(0).getTdcqxmid())) {
                            BdcSlXmLsgxDO wlxmlsgx = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), ppgxList.get(0).getTdcqxmid(), "", "", "");
                            wlxmlsgx.setSfwlzs(CommonConstantUtils.SF_S_DM);
                            bdcSlXmDTO.getBdcSlXmLsgxList().add(wlxmlsgx);
                        }
                    }
                    //ybdcqz 拼接土地证号
                    if (StringUtils.isNotBlank(gltdzxxDTO.getTdzh())) {
                        if (StringUtils.isNotBlank(ybdcqzBuilder)) {
                            ybdcqzBuilder.append(CommonConstantUtils.ZF_YW_DH).append(gltdzxxDTO.getTdzh());
                        } else {
                            ybdcqzBuilder.append(gltdzxxDTO.getTdzh());
                        }
                    }
                }
            }
            LOGGER.info("拼接后的ybdcqz:{}", ybdcqzBuilder);
            bdcSlXmDTO.getBdcSlXm().setYbdcqz(ybdcqzBuilder.toString());
        } else if (StringUtils.isNotBlank(bdcSlXmDO.getBdcdyh()) && CommonConstantUtils.DYBDCLX_FDYT.equals(bdcSlXmDO.getBdclx()) && StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid()) && !CommonConstantUtils.SF_S_DM.equals(bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getSfwlzs())) {
                wlTdz(bdcSlXmDTO,bdcSlXmDO,gzldyid);
        }

    }

    /**
      * @param
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 自动外联土地证
      */
    private void wlTdz(BdcSlXmDTO bdcSlXmDTO, BdcSlXmDO bdcSlXmDO,String gzldyid){
        //判断上一手是否为房产证
        BdcXmQO fcXmQO = new BdcXmQO();
        fcXmQO.setXmid(bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid());
        List<BdcXmDO> fcXmList = bdcXmFeignService.listBdcXm(fcXmQO);
        if (CollectionUtils.isNotEmpty(fcXmList) && ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, fcXmList.get(0).getQllx())) {
            String tdxmid ="";
            String tdzh ="";
            List<BdcFctdPpgxDO> bdcFctdPpgxDOS =bdcPpFeignService.queryBdcFctdPpgx(bdcSlXmDTO.getBdcSlXmLsgxList().get(0).getYxmid());
            if(CollectionUtils.isNotEmpty(bdcFctdPpgxDOS)){
                tdxmid =bdcFctdPpgxDOS.get(0).getTdcqxmid();
                if(StringUtils.isNotBlank(tdxmid)){
                    BdcXmQO bdcXmQO =new BdcXmQO();
                    bdcXmQO.setXmid(tdxmid);
                    List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        tdzh =bdcXmDOList.get(0).getBdcqzh();
                    }
                }
            }else {
                //自动外联土地证
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setBdcdyh(bdcSlXmDO.getBdcdyh());
                bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                bdcXmQO.setBdclx(CommonConstantUtils.DYBDCLX_CTD);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    bdcXmDOList = bdcXmDOList.stream().filter(bdcXmDO -> !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        if (bdcXmDOList.size() > 1) {
                            throw new AppException(bdcSlXmDO.getBdcdyh() + "存在多条现势土地产权,请检查数据");
                        }
                        //验证土地证是否存在限制权利
                        Boolean xzql = bdcGzyzService.checkWltdzXzQl(bdcXmDOList.get(0).getXmid(), gzldyid);
                        if (!Boolean.TRUE.equals(xzql)) {
                            tdxmid =bdcXmDOList.get(0).getXmid();
                            tdzh =bdcXmDOList.get(0).getBdcqzh();
                        } else {
                            LOGGER.error("单元号:{}自动外联土地证:{}失败,土地证存在限制权利", bdcSlXmDO.getBdcdyh(), bdcXmDOList.get(0).getXmid());
                        }
                    }
                }
            }
            if(StringUtils.isNotBlank(tdxmid)){
                BdcSlXmLsgxDO wlxmlsgx = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), tdxmid, "", "", "");
                wlxmlsgx.setSfwlzs(CommonConstantUtils.SF_S_DM);
                bdcSlXmDTO.getBdcSlXmLsgxList().add(wlxmlsgx);
                //拼接土地证号
                if (StringUtils.isNotBlank(tdzh)) {
                    StringBuilder ybdcqzBuilder = new StringBuilder();
                    ybdcqzBuilder.append(bdcSlXmDTO.getBdcSlXm().getYbdcqz());
                    if (StringUtils.isNotBlank(ybdcqzBuilder)) {
                        ybdcqzBuilder.append(CommonConstantUtils.ZF_YW_DH).append(tdzh);
                    } else {
                        ybdcqzBuilder.append(tdzh);
                    }
                    bdcSlXmDTO.getBdcSlXm().setYbdcqz(ybdcqzBuilder.toString());
                }
                LOGGER.info("单元号:{}自动外联土地证:{},拼接土地证号：{}", bdcSlXmDO.getBdcdyh(), tdxmid, tdzh);
            }
        }

    }


    /**
     * @param
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理是否月结
     */
    private void dealSfyj(List<BdcSlXmDTO> bdcSlXmDTOS, String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //项目ID/工作流实例ID与月结信息关系
            Map<String, DsfSlxxDTO> idSfyjMap = new HashMap<>();
            Map<String,BdcSlSfxxDO> sfxxMap =new HashMap<>();
            if (CollectionUtils.isNotEmpty(bdcSlXmDTOS)) {
                for (BdcSlXmDTO bdcSlXmDTO : bdcSlXmDTOS) {
                    if (bdcSlXmDTO.getDsfSlxxDTO() != null && (bdcSlXmDTO.getDsfSlxxDTO().getQlrsfyj() != null || bdcSlXmDTO.getDsfSlxxDTO().getYwrsfyj() != null)) {
                        idSfyjMap.put(bdcSlXmDTO.getBdcSlXm().getXmid(), bdcSlXmDTO.getDsfSlxxDTO());
                        idSfyjMap.put(gzlslid, bdcSlXmDTO.getDsfSlxxDTO());
                    }
                    //更新缴费状态和流水号
                    if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSfxxDTOList())){
                        for(BdcSfxxDTO bdcSfxxDTO:bdcSlXmDTO.getBdcSfxxDTOList()) {
                            BdcSlSfxxDO slSfxxDO =bdcSfxxDTO.getBdcSlSfxxDO();
                            if(slSfxxDO != null) {
                                sfxxMap.put(bdcSlXmDTO.getBdcSlXm().getXmid() + slSfxxDO.getQlrlb(),slSfxxDO);
                            }
                        }
                    }
                }
            }
            if (MapUtils.isNotEmpty(idSfyjMap) ||MapUtils.isNotEmpty(sfxxMap)) {
                List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                    for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                        boolean updateSf = false;
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getXmid()) && idSfyjMap.containsKey(bdcSlSfxxDO.getXmid())) {
                            DsfSlxxDTO dsfSlxxDTO = idSfyjMap.get(bdcSlSfxxDO.getXmid());
                            if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb()) && dsfSlxxDTO.getQlrsfyj() != null) {
                                bdcSlSfxxDO.setSfyj(dsfSlxxDTO.getQlrsfyj());
                                updateSf = true;
                            } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb()) && dsfSlxxDTO.getYwrsfyj() != null) {
                                bdcSlSfxxDO.setSfyj(dsfSlxxDTO.getYwrsfyj());
                                updateSf = true;
                            }
                        } else if (StringUtils.isBlank(bdcSlSfxxDO.getXmid()) && idSfyjMap.containsKey(bdcSlSfxxDO.getGzlslid())) {
                            DsfSlxxDTO dsfSlxxDTO = idSfyjMap.get(bdcSlSfxxDO.getGzlslid());
                            if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb()) && dsfSlxxDTO.getQlrsfyj() != null) {
                                bdcSlSfxxDO.setSfyj(dsfSlxxDTO.getQlrsfyj());
                                updateSf = true;
                            } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb()) && dsfSlxxDTO.getYwrsfyj() != null) {
                                bdcSlSfxxDO.setSfyj(dsfSlxxDTO.getYwrsfyj());
                                updateSf = true;
                            }
                        }
                        if(StringUtils.isNoneBlank(bdcSlSfxxDO.getXmid()) &&sfxxMap.containsKey(bdcSlSfxxDO.getXmid()+bdcSlSfxxDO.getQlrlb())){
                            BdcSlSfxxDO wwSfxx =sfxxMap.get(bdcSlSfxxDO.getXmid()+bdcSlSfxxDO.getQlrlb());
                            bdcSlSfxxDO.setSfzt(wwSfxx.getSfzt());
                            bdcSlSfxxDO.setJfsbm(wwSfxx.getJfsbm());
                            updateSf =true;
                            List<BdcSlSfxmDO> bdcSlSfxmDOList =bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                            if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                                for(BdcSlSfxmDO bdcSlSfxmDO:bdcSlSfxmDOList){
                                    bdcSlSfxmDO.setSfzt(wwSfxx.getSfzt());
                                    bdcSlSfxmDO.setJfsbm(wwSfxx.getJfsbm());
                                    bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                                }

                            }
                        }
                        if (updateSf) {
                            bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                        }
                    }

                }
            }
        }


    }

    /**
     * 数据转换
     * @param bdcSlxxDTO 受理实体
     * @return 受理结果
     */
    private List<BdcSlxxInitDTO> changeBdcSlXxDTOToBdcSlxxInitDTOSingle(BdcSlxxDTO bdcSlxxDTO){
        List<BdcSlxxInitDTO> bdcSlxxInitDTOList = new ArrayList<>();
        BdcSlxxInitDTO bdcSlxxInitDTO =new BdcSlxxInitDTO();
        //基本信息
        List<BdcSlJbxxDO> bdcSlJbxxDOList = new ArrayList<>();
        bdcSlJbxxDOList.add(bdcSlxxDTO.getBdcSlJbxx());
        bdcSlxxInitDTO.setBdcSlJbxxDOList(bdcSlJbxxDOList);
        //项目
        List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
        bdcSlxxDTO.getBdcSlXmList().forEach(bdcSlXmDTO -> {
            bdcSlXmDOList.add(bdcSlXmDTO.getBdcSlXm());
        });
        bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);
        //历史
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        bdcSlxxDTO.getBdcSlXmList().forEach(bdcSlXmDTO -> {
            if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())) {
                bdcSlXmLsgxDOList.addAll(bdcSlXmDTO.getBdcSlXmLsgxList());
            }
        });
        bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);

        bdcSlxxInitDTOList.add(bdcSlxxInitDTO);
        return bdcSlxxInitDTOList;

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  处理证书序号
      */
    private void dealZsxh(BdcSlxxDTO bdcSlxxDTO) {
        if (bdcSlxxDTO != null && bdcSlxxDTO.getBdcSlJbxx() != null && StringUtils.isNotBlank(bdcSlxxDTO.getBdcSlJbxx().getGzldyid()) && CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            String gzldyid = bdcSlxxDTO.getBdcSlJbxx().getGzldyid();
            List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOS = bdcYxbdcdyKgPzService.queryBdcYxbdcdyKgPzDOListByGzldyid(gzldyid);
            //默认一本证的项目
            List<BdcSlXmDO> ybzXmList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOS)) {
                Integer mrzsxh = Constants.ZSSL_MRZ;
                for (BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO : bdcYxbdcdyKgPzDOS) {
                    if (bdcYxbdcdyKgPzDO != null && Constants.ZSSL_MRZ.equals(bdcYxbdcdyKgPzDO.getZsslmrz())) {
                        Integer finalMrzsxh = mrzsxh;
                        bdcSlxxDTO.getBdcSlXmList().forEach(bdcSlXmDTO -> {
                            //配置生成一本证,生成一本证默认证书序号相同,依次递增
                            if (StringUtils.isBlank(bdcYxbdcdyKgPzDO.getDjxl()) || StringUtils.equals(bdcYxbdcdyKgPzDO.getDjxl(), bdcSlXmDTO.getBdcSlXm().getDjxl())) {
                                if(bdcSlXmDTO.getBdcSlXm().getZsxh() ==null) {
                                    bdcSlXmDTO.getBdcSlXm().setZsxh(finalMrzsxh);
                                    ybzXmList.add(bdcSlXmDTO.getBdcSlXm());
                                }
                            }
                        });
                        mrzsxh++;
                    }
                }
            }
            //抵押项目证书序号默认处理
            Integer dyzsxh = Constants.ZSXH_DYAQ_MRZ;
            //2.不是默认一本证则找出抵押项目
            //抵押项目登记小类一致,如果存在原抵押证明号,则原抵押证明单号相同的证书序号相同
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                //抵押配置了多本证，证书序号统一设置为空（不处理）--多本证, 否则，不配置生成一本证
                boolean contin = false;
                BdcSlXmDO bdcSlXmDO = bdcSlXmDTO.getBdcSlXmDO();
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx()) && CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOS)) {
                    //判断是否配置了已选单元开关，如果配置了，循环开关，否则走默认逻辑
                    for (BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO : bdcYxbdcdyKgPzDOS) {
                        if ((StringUtils.isBlank(bdcYxbdcdyKgPzDO.getDjxl()) || StringUtils.equals(bdcYxbdcdyKgPzDO.getDjxl(), bdcSlXmDO.getDjxl()))
                                && Objects.equals(9, bdcYxbdcdyKgPzDO.getZsslmrz())) {
                            contin = true;
                            break;
                        }
                    }
                    if (contin) {
                        continue;
                    }
                }
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx()) && !ybzXmList.contains(bdcSlXmDO)) {
                    if(bdcSlXmDO.getZsxh() ==null) {
                        bdcSlXmDO.setZsxh(dyzsxh);
                    }
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理外网收费信息
     */
    private void dealWwSfxx(BdcSlxxDTO bdcSlxxDTO,String gzlslid,List<BdcXmDO> bdcXmDOList,String yhxzqdm) throws Exception{
        //判断外网推送收费信息,以外网推送为准
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        List<BdcSlSfxmDO> bdcSlSfxmDOList = new ArrayList<>();
        List<String> allxmidList =new ArrayList<>();
        //获取外网推送收费的xmid集合
        Set<String> xmidSet = new HashSet<>();
        for(BdcSlXmDTO bdcSlXmDTO:bdcSlxxDTO.getBdcSlXmList()) {
            allxmidList.add(bdcSlXmDTO.getBdcSlXm().getXmid());
            if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSfxxDTOList())){
                for (BdcSfxxDTO bdcSfxxDTO : bdcSlXmDTO.getBdcSfxxDTOList()) {
                    if (bdcSfxxDTO.getBdcSlSfxxDO() != null && CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmDOS())) {
                        bdcSfxxDTO.getBdcSlSfxxDO().setGzlslid(gzlslid);
                        bdcSlSfxxDOList.add(bdcSfxxDTO.getBdcSlSfxxDO());
                        bdcSlSfxmDOList.addAll(bdcSfxxDTO.getBdcSlSfxmDOS());
                        xmidSet.add(bdcSlXmDTO.getBdcSlXm().getXmid());
                    }
                }
            }
        }
        if(xmidSet.size() ==0){
            cshWwSfxx(bdcSlxxDTO,bdcXmDOList,yhxzqdm,null);
        }else{
            for(String xmid:allxmidList){
                if(!xmidSet.contains(xmid)){
                    //外网推送的收费信息为空，根据xmid初始化收费信息
                    cshWwSfxx(bdcSlxxDTO,bdcXmDOList,yhxzqdm,xmid);
                }
            }
            if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                entityMapper.insertBatchSelective(bdcSlSfxxDOList);
            }
            if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                entityMapper.insertBatchSelective(bdcSlSfxmDOList);
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理外网核税信息
     */
    private void dealWwHsxx(BdcSlxxDTO bdcSlxxDTO){
        //判断外网推送核税信息,以外网推送为准
        List<BdcSwxxDTO> bdcSwxxDTOList = bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSwxxDTOList();
        if(CollectionUtils.isNotEmpty(bdcSwxxDTOList) && Objects.nonNull(bdcSwxxDTOList.get(0).getBdcSlHsxxDO()) &&CheckParameter.checkAnyParameter(bdcSwxxDTOList.get(0).getBdcSlHsxxDO())){
            BdcSlHsxxDO bdcSlHsxxDO = bdcSwxxDTOList.get(0).getBdcSlHsxxDO();
            if(StringUtils.isBlank(bdcSlHsxxDO.getHsxxid())){
                bdcSlHsxxDO.setHsxxid(UUIDGenerator.generate16());
            }
            this.entityMapper.insertSelective(bdcSlHsxxDO);
        }
    }

    private void cshWwSfxx(BdcSlxxDTO bdcSlxxDTO ,List<BdcXmDO> bdcXmDOList,String yhxzqdm,String xmid) throws Exception {
        // 初始化 收费信息
        bdcSfService.cshSfxx(bdcSlxxDTO.getBdcSlJbxx().getGzlslid(), xmid);
        //处理是否月结
        dealSfyj(bdcSlxxDTO.getBdcSlXmList(), bdcSlxxDTO.getBdcSlJbxx().getGzlslid());
        // 默认false 目前合肥和盐城要配置true
        if (sfcshsfxm) {
            LOGGER.info("{}开始自动计算收费项目", bdcSlxxDTO.getBdcSlJbxx().getGzlslid());
            //外网创建时自动计算收费项目内容
            //南通的收费方式另外处理
            if (Objects.equals(2, djfjsff)) {
                bdcSfService.autoCountNtSfxx(bdcSlxxDTO.getBdcSlJbxx().getGzlslid(),yhxzqdm);
            } else {
                bdcSfService.autoCountSfxm(bdcSlxxDTO.getBdcSlJbxx().getGzlslid());
            }
            //工作流定义id和sply符合配置条件时，改变收费状态为已核验
            Integer sply = bdcXmDOList.get(0).getSply();
            if (Objects.nonNull(sply) && CollectionUtils.isNotEmpty(wwsqZdjsSfxmConfig.getLcsplymap(bdcXmDOList.get(0).getGzldyid())) && wwsqZdjsSfxmConfig.getLcsplymap(bdcXmDOList.get(0).getGzldyid()).contains(sply)) {
                bdcSfService.updatSfztYhy(bdcSlxxDTO.getBdcSlJbxx().getGzlslid());
            }
        }
    }
}
