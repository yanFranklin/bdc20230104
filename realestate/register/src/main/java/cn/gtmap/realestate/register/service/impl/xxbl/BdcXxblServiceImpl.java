package cn.gtmap.realestate.register.service.impl.xxbl;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.LogMsgDTO;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.StatisticsProcessClient;
import cn.gtmap.gtc.workflow.domain.manage.StatisticsProcDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcBlxxDTO;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcHfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.core.builder.xxbl.BlGjFdcqBuilder;
import cn.gtmap.realestate.register.core.builder.xxbl.BlGjXmBuilder;
import cn.gtmap.realestate.register.service.BdcXnBdcdyhService;
import cn.gtmap.realestate.register.service.impl.BdcXmxxServiceImpl;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblService;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblShService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.*;
import static cn.gtmap.realestate.common.util.UUIDGenerator.generate16;

/**
 * 不动产信息补录处理Service接口定义
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2020/03/16 21:06
 */
@Service
public class BdcXxblServiceImpl implements BdcXxblService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXxblServiceImpl.class);
    /**
     * 当前类名
     */
    private static final String CLASS_NAME = BdcXmxxServiceImpl.class.getName();
    /**
     * 前端虚拟单元号后缀
     */
    private static final String XNDYH_SUFFIX = "00000000";
    /**
     * 是否关联原产权证号
     */
    @Value("${xxbl.glycqzh:true}")
    private boolean glycqzh;

    /**
     * 信息补录项目来源 默认为3 其他
     */
    @Value("${xxbl.xmly:3}")
    private String XXBL_XMLY;

    /**
     * 是否不需要审核
     */
    @Value("${xxbl.bxysh:false}")
    private boolean BLSHLX_NOSH;

    /**
     * 信息修改登记原因
     */
    @Value("${xxbl.xglcdjyy:信息修改}")
    private String djyy;

    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    private BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService initQllxFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Autowired
    private BdcXnBdcdyhService bdcXnBdcdyhService;
    @Autowired
    private BdcXxblShService bdcXxblShService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    private BdcXxblZsFeignService bdcXxblZsFeignService;
    @Autowired
    private BdcXxblFeignService bdcXxblFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;

    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;
    /**
     * 锁定证书服务
     */
    @Autowired
    private BdcSdFeignService bdcSdFeignService;
    /**
     * 流程实例管理：创建平台流程接口
     */
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    /**
     * 权利人服务
     */
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    /**
     * 初始化证书服务
     */
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;

    @Autowired
    StatisticsProcessClient statisticsProcessClient;

    @Autowired
    BdcPpFeignService bdcPpFeignService;

    @Autowired
    LogMessageClient logMessageClient;

    @Autowired
    BdcXxblLogFeignService bdcXxblLogFeignService;
    @Autowired
    BdcYxbdcdyKgPzFeignService bdcYxbdcdyKgPzFeignService;
    /**
     * 初始化补录数据
     *
     * @param bdcBlxxDTO 补录传输对象
     * @return List<BdcXmDO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcXmDO> cshBlxx(BdcBlxxDTO bdcBlxxDTO) {
        UserDto currentUser = userManagerUtils.getUserDto();
        // 1. 组织基本信息
        BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);
        bdcSlJbxxDO.setLcmc(bdcBlxxDTO.getLcmc());
        bdcSlJbxxDO.setGzldyid(bdcBlxxDTO.getGzldyid());
        // 2. 整理受理项目
        BdcSlXmDTO bdcSlXmDTO = getCshBdcSlXmDTO(bdcBlxxDTO, currentUser);
        // 3. 组装数据
        BdcSlxxDTO bdcSlxxDTO = getBdcSlxxDTO(bdcSlJbxxDO, bdcSlXmDTO);
        dealZsxh(bdcSlxxDTO,bdcBlxxDTO.getGzldyid());
        return cshXx(bdcBlxxDTO.getBdcdyh(), bdcSlxxDTO, bdcBlxxDTO.getBlshlx());
    }

    @Override
    public TaskData cshLcxx(BdcBlxxDTO bdcBlxxDTO) {
        if (StringUtils.isBlank(bdcBlxxDTO.getPtgzldyid())) {
            throw new MissingArgumentException("缺少平台工作流定义 ID");
        }
        UserDto currentUser = userManagerUtils.getUserDto();
        // 0. 创建平台项目
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcBlxxDTO.getPtgzldyid(), currentUser.getUsername(), null, "", null);
        // 1. 组织基本信息
        BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);

        bdcSlJbxxDO.setGzlslid(taskData.getProcessInstanceId());
//        bdcSlJbxxDO.setLcmc(bdcBlxxDTO.getLcmc());
//        bdcSlJbxxDO.setGzldyid(bdcBlxxDTO.getGzldyid());
        bdcSlJbxxDO.setLcmc(taskData.getProcessDefName());
        bdcSlJbxxDO.setGzldyid(bdcBlxxDTO.getPtgzldyid());

        // 2. 整理受理项目
        BdcSlXmDTO bdcSlXmDTO = getCshBdcSlXmDTO(bdcBlxxDTO, currentUser);
        // 3. 组装数据
        BdcSlxxDTO bdcSlxxDTO = getBdcSlxxDTO(bdcSlJbxxDO, bdcSlXmDTO);
        dealZsxh(bdcSlxxDTO,bdcBlxxDTO.getGzldyid());
        LOGGER.info("信息补录流程初始化bdcSlXmDTO:{}", JSON.toJSONString(bdcSlXmDTO));
        cshXx(bdcBlxxDTO.getBdcdyh(), bdcSlxxDTO, bdcBlxxDTO.getBlshlx());
        return taskData;
    }


    @Override
    public TaskData cshPlxxblLcxx(List<BdcBlxxDTO> bdcBlxxDTOS) {
        BdcBlxxDTO bdcBlxxDTO = bdcBlxxDTOS.get(0);
        if (StringUtils.isBlank(bdcBlxxDTO.getPtgzldyid())) {
            throw new MissingArgumentException("缺少平台工作流定义 ID");
        }
        UserDto currentUser = userManagerUtils.getUserDto();
        // 0. 创建平台项目
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcBlxxDTO.getPtgzldyid(), currentUser.getUsername(), null, "", null);
        // 1. 组织基本信息
        BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);

        bdcSlJbxxDO.setGzlslid(taskData.getProcessInstanceId());
        bdcSlJbxxDO.setLcmc(taskData.getProcessDefName());
        bdcSlJbxxDO.setGzldyid(bdcBlxxDTO.getPtgzldyid());
        // 2. 整理受理项目

        List<BdcSlXmDTO> bdcSlXmDTOList = new ArrayList<>();

        for (BdcBlxxDTO blxxDTO : bdcBlxxDTOS) {
            BdcSlXmDTO bdcSlXmDTO = getCshBdcSlXmDTO(blxxDTO, currentUser);
            bdcSlXmDTOList.add(bdcSlXmDTO);
        }
        // 3. 组装数据

        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);

        dealZsxh(bdcSlxxDTO,bdcBlxxDTO.getGzldyid());
        LOGGER.info("信息补录流程初始化bdcSlXmDTO:{}", JSON.toJSONString(bdcSlXmDTOList));

        cshXx(bdcBlxxDTO.getBdcdyh(), bdcSlxxDTO, bdcBlxxDTO.getBlshlx());
        return taskData;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息补录修改数据逻辑调整，只生成项目数据，不生成权利数据
     * 把当前证书数据锁定，当前流程办结后解锁证书
     * @date : 2022/11/14 15:58
     */
    @Override
    public TaskData cshModify(BdcBlxxDTO bdcBlxxDTO) {
        LOGGER.info("补录修改数据BdcBlxxDTO：{}", bdcBlxxDTO);
        if (StringUtils.isAnyBlank(bdcBlxxDTO.getPtgzldyid(), bdcBlxxDTO.getYgzlslid(), bdcBlxxDTO.getYxmid())) {
            throw new MissingArgumentException("补录修改数据缺少必要参数！");
        }
        UserDto currentUser = userManagerUtils.getUserDto();
        //1.查询原项目数据
        BdcYwxxDTO sourceYwxx = null;
        try {
            sourceYwxx = bdcInitFeignService.queryYwxx(bdcBlxxDTO.getYxmid());
        } catch (Exception e) {
            LOGGER.error("补录修改数据查询项目信息失败", e);
        }
        // 复制项目信息不存在直接返回
        if (sourceYwxx == null || sourceYwxx.getBdcXm() == null) {
            throw new MissingArgumentException("所选择的项目信息为空");
        }
        //2.锁定证书信息
        // 取第一条项目
        BdcXmDO bdcXmDO = sourceYwxx.getBdcXm();
        BdcXmDO ybdcXmDO = new BdcXmDO();
        BeanUtils.copyProperties(bdcXmDO,ybdcXmDO);
        BdcXmFbDO bdcXmFbDO = sourceYwxx.getBdcXmFb();
        String yxmid = bdcXmDO.getXmid();
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.queryBdcZsByXmid(yxmid);
        LOGGER.warn("当前选择数据的xmid{}查询到需要锁定的证书记录{}", yxmid, JSON.toJSONString(bdcZsDOS));
        if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
            if (StringUtils.isBlank(bdcBlxxDTO.getSdyy())) {
                bdcBlxxDTO.setSdyy("信息补录修改数据当前证书被锁定");
            }
            List<BdcZssdDO> bdcZssdDOSd = new ArrayList<>();
            for (BdcZsDO bdcZsDO : bdcZsDOS) {
                // 确认证书是否已被锁定过
                BdcZssdDO bdcZssdDO = new BdcZssdDO();
                bdcZssdDO.setZsid(bdcZsDO.getZsid());
                bdcZssdDO.setCqzh(bdcZsDO.getBdcqzh());
                bdcZssdDO.setSdzt(SDZT_SD);
                List<BdcZssdDO> bdcZssdDOSdzt = bdcSdFeignService.queryBdczsSd(bdcZssdDO);
                if (CollectionUtils.isEmpty(bdcZssdDOSdzt)) {
                    bdcZssdDOSd.add(bdcZssdDO);
                }
            }
            // 当存在未锁定证书时 进行锁定
            if (CollectionUtils.isNotEmpty(bdcZssdDOSd)) {
                try {
                    bdcSdFeignService.sdBdczs(bdcZssdDOSd, bdcBlxxDTO.getSdyy());
                } catch (Exception e) {
                    LOGGER.warn("初始化补录修改流程锁定证书发生异常，同步权籍数据抛出异常", e);
                }
            }
        }
        //3.创建平台流程
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcBlxxDTO.getPtgzldyid(), userManagerUtils.getCurrentUserName(), null, "", null);
        //4.生成项目数据,项目附表数据
        bdcXmDO.setXmid(UUIDGenerator.generate16());
        bdcXmDO.setGzlslid(taskData.getProcessInstanceId());
        bdcXmDO.setGzldyid(bdcBlxxDTO.getPtgzldyid());
        bdcXmDO.setGzldymc(taskData.getProcessDefName());
        bdcXmDO.setSlr(currentUser.getAlias());
        bdcXmDO.setSlrid(currentUser.getId());
        String slbh = bdcBhFeignService.queryBh(SLBH, ZZSJFW_DAY);
        bdcXmDO.setSlbh(slbh);
        // 有受理人场景，使用受理人组织机构赋值区县代码
        OrganizationDto organizationDto = currentUser.getOrgRecordList().get(0);
        if (organizationDto != null) {
            bdcXmDO.setDjjg(bdcSlJbxxFeignService.queryDjjgByUser(currentUser));
            bdcXmDO.setQxdm(organizationDto.getRegionCode());
            bdcXmDO.setDjbmdm(organizationDto.getCode());
        }
        bdcXmDO.setXmly(Integer.parseInt(XXBL_XMLY));
        bdcXmDO.setSlsj(new Date());
        bdcXmDO.setQszt(QSZT_TEMPORY);
        bdcXmDO.setAjzt(AJZT_ZB_DM);
        bdcXmDO.setDjyy(djyy);
        entityMapper.insertSelective(bdcXmDO);

        //bdc_xm_fb 数据
        if (Objects.isNull(bdcXmFbDO)) {
            //存量数据没有项目附表
            bdcXmFbDO = new BdcXmFbDO();
        }
        bdcXmFbDO.setXmid(bdcXmDO.getXmid());
        entityMapper.insertSelective(bdcXmFbDO);
        //5.新的项目历史关系
        String xmid = bdcXmDO.getXmid();
        BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
        bdcXmLsgxDO.setXmid(xmid);
        bdcXmLsgxDO.setYxmid(yxmid);
        bdcXmLsgxDO.setWlxm(SF_F_DM);
        bdcXmLsgxDO.setGxid(generate16());
        entityMapper.insertSelective(bdcXmLsgxDO);
        //6.新的初始化房屋开关实例数据
        BdcCshFwkgSlDO cshFwkgSlDO = bdcXmFeignService.queryFwkgsl(yxmid);
        if (cshFwkgSlDO == null) {
            cshFwkgSlDO = new BdcCshFwkgSlDO();
            cshFwkgSlDO.setSfzlcsh(SF_S_DM);
            cshFwkgSlDO.setSfsczs(SF_S_DM);
            cshFwkgSlDO.setId(xmid);
            if (bdcXmDO.getDjxl() != null) {
                BdcCshFwkgDO bdcCshFwkgDO = bdcCshXtPzFeignService.queryBdcCshFwKgDO(bdcXmDO.getDjxl());
                cshFwkgSlDO.setDjxl(bdcCshFwkgDO.getDjxl());
                cshFwkgSlDO.setSfzxyql(bdcCshFwkgDO.getSfzxyql());
                cshFwkgSlDO.setQlsjly(bdcCshFwkgDO.getQlsjly());
                cshFwkgSlDO.setSfscql(bdcCshFwkgDO.getSfscql());
                cshFwkgSlDO.setZszl(bdcCshFwkgDO.getZszl());
                cshFwkgSlDO.setYwrsjly(bdcCshFwkgDO.getYwrsjly());
            }
        } else {
            cshFwkgSlDO.setId(xmid);
        }
        entityMapper.insertSelective(cshFwkgSlDO);
        //7.保存相关日志数据
        this.saveBllcModifyLog(bdcBlxxDTO.getYxmid(), bdcXmDO.getXmid());
        ybdcXmDO.setDjyy(djyy);
        bdcXmFeignService.updateBdcXm(ybdcXmDO);
        BdcQl bdcQl = initQllxFeignService.queryQlxx(ybdcXmDO.getXmid());
        if(Objects.nonNull(bdcQl)) {
            JSONObject qlObj = new JSONObject();
            qlObj.put("djyy",djyy);
            BdcDjxxUpdateQO bdcQlxxUpdateQO = new BdcDjxxUpdateQO();
            bdcQlxxUpdateQO.setJsonStr(JSON.toJSONString(qlObj));
            Map qlxxWhereMap = new HashMap<>();
            qlxxWhereMap.put("xmids", Collections.singletonList(ybdcXmDO.getXmid()));
            bdcQlxxUpdateQO.setClassName(bdcQl.getClass().getName());
            bdcQlxxUpdateQO.setWhereMap(qlxxWhereMap);
            try {
                initQllxFeignService.updateBatchBdcQl(bdcQlxxUpdateQO);
            } catch (Exception e) {
                LOGGER.error("信息补录修改数据更新权利登记原因失败{}", bdcBlxxDTO.getBdcdyh(), e);
            }
        }
        //8.回写平台数据
        try {
            bdcYwsjHxFeignService.saveBdcYwsj(taskData.getProcessInstanceId());
        } catch (Exception e) {
            LOGGER.error("当前信息补录修改数据回写大云数据失败{}", bdcBlxxDTO.getBdcdyh(), e);
        }
        return taskData;
    }

    /**
     * @param bdcBlxxDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息补录删除数据
     * @date : 2022/8/30 15:05
     */
    @Override
    public TaskData cshDeleteOrDelete(BdcBlxxDTO bdcBlxxDTO) {
        if (StringUtils.isBlank(bdcBlxxDTO.getPtgzldyid())) {
            throw new MissingArgumentException("缺少平台工作流定义 ID");
        }
        UserDto currentUser = userManagerUtils.getUserDto();
        // 0. 创建平台项目
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcBlxxDTO.getPtgzldyid(), currentUser.getUsername(), null, "", null);
        // 1. 组织基本信息
        BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);
        bdcSlJbxxDO.setJbxxid(UUIDGenerator.generate16());
        bdcSlJbxxDO.setGzlslid(taskData.getProcessInstanceId());
        bdcSlJbxxDO.setLcmc(taskData.getProcessDefName());
        bdcSlJbxxDO.setGzldyid(bdcBlxxDTO.getPtgzldyid());
        StatisticsProcDto statisticsProcDto = statisticsProcessClient.queryProcessStaticsInfo(taskData.getProcessInstanceId());
        //if (statisticsProcDto != null && statisticsProcDto.getProcDueLimitDouble() != null) {
        //    bdcSlJbxxDO.setCnqx(DoubleUtils.getString(statisticsProcDto.getProcDueLimitDouble()));
        //}
        bdcBlxxDTO.setGzldyid(bdcBlxxDTO.getPtgzldyid());
        // 2. 整理受理项目
        BdcSlXmDTO bdcSlXmDTO = getCshBdcSlXmDTO(bdcBlxxDTO, currentUser);
        if (Objects.nonNull(bdcSlXmDTO.getBdcSlXm())) {
            if (StringUtils.isNotBlank(bdcBlxxDTO.getYxmid())) {
                BdcXmDO ybdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcBlxxDTO.getYxmid());
                if (Objects.nonNull(ybdcXmDO)) {
                    acceptDozerMapper.map(ybdcXmDO, bdcSlXmDTO.getBdcSlXm());
                }
                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDTO.getBdcSlXmDO().getXmid(), bdcBlxxDTO.getYxmid(), "", "", "");
                bdcSlXmLsgxFeignService.insertBdcSlXmLsgx(bdcSlXmLsgxDO);
            }
            bdcSlXmDTO.getBdcSlXm().setJbxxid(bdcSlJbxxDO.getJbxxid());
            bdcSlXmFeignService.insertBdcSlXm(bdcSlXmDTO.getBdcSlXm());
        }

        bdcSlJbxxFeignService.insertBdcSlJbxx(bdcSlJbxxDO);
        //信息回写大云
        try {
            bdcSlxxHxFeignService.hxBdcSlxx(bdcSlJbxxDO.getGzlslid());
        } catch (Exception e) {
            LOGGER.error("回写平台数据异常", e);
        }
        LOGGER.info("信息补录流程初始化bdcSlXmDTO:{}", JSON.toJSONString(bdcSlXmDTO));
        return taskData;
    }

    @Override
    public void endLc(String processInsId, String currentUserName) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺少 gzlslid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("未找到对应的项目数据！");
        }
        // 判断是否需要修改案件状态
        if (!AJZT_YB_DM.equals(bdcXmDOS.get(0).getAjzt())) {
            bdcDbxxFeignService.changeAjzt(processInsId);
        }
        // 权属状态为 0 的数据修改
        if (QSZT_TEMPORY.equals(bdcXmDOS.get(0).getQszt())) {
            if (StringUtils.isBlank(currentUserName)) {
                throw new MissingArgumentException("登簿未获取到用户信息");
            }
            registerWorkflowFeignService.updateBdcDbxxAndQsztSyncQj(processInsId, currentUserName);
        }
    }

    /**
     * @param gzlslid
     * @param userName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息修改办结后判断匹配关系
     * @date : 2022/9/20 16:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void endSjxgPpgx(String gzlslid, String userName) throws Exception {

    }

    /**
     * 补录修改流程办结事件 <br>
     * 1. 解锁证书 <br>
     * 2. 还原被修改项目的 qszt 和 ajzt
     *
     * @param gzlslid 补录修改流程的实例 id
     * @param jsyy    解锁原因
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void endModify(String gzlslid, String jsyy) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        // 获取原项目id
        String yxmid = getYxmid(gzlslid);
        // 判断证书是否锁定
        List<BdcZssdDO> bdcZssdDOSd = getBdcZssdDOS(yxmid);
        // 当存在已锁定证书时 进行解锁
        if (CollectionUtils.isNotEmpty(bdcZssdDOSd)) {
            if (StringUtils.isBlank(jsyy)) {
                throw new AppException("解锁原因不能为空");
            }
            try {
                bdcSdFeignService.jsBdczs(bdcZssdDOSd, jsyy);
            } catch (Exception e) {
                LOGGER.warn("初始化补录修改流程解锁证书发生异常，同步权籍数据抛出异常", e);
            }
        }

        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new AppException("办结补录流程未查询到对应项目信息。");
        }
        // 2. 修改当前项目的 ajzt 和 qszt
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setGzlslid(gzlslid);
        bdcXmDO.setXmid(bdcXmDTOS.get(0).getXmid());
        bdcXmDO.setAjzt(AJZT_YB_DM);
        bdcXmDO.setQszt(QSZT_HISTORY);
        bdcXmDO.setJssj(new Date());
        entityMapper.updateByPrimaryKeySelective(bdcXmDO);
        LOGGER.warn("{}：更新 gzlslid：{} 的项目案件状态和权属状态", CLASS_NAME, gzlslid);
    }

    /**
     * 删除补录修改流程数据 <br>
     * 1. 删除证书锁定数据 <br>
     * 2. 同步权籍证书锁定状态 <br>
     * 3. 还原补录修改流程数据
     *
     * @param processInsId 修改流程的工作流实例 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void deleteBllcModify(String processInsId) throws Exception {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("processInsId");
        }
        // 获取被修改流程的 xmid
        String yxmid = getYxmid(processInsId);
        LOGGER.warn("当前流程实例id{}删信息修改流程时获取到上一手xmid={}", processInsId, yxmid);
        // 获取证书是否锁定
        if (StringUtils.isNotBlank(yxmid)) {
            List<BdcZssdDO> bdcZssdDOSd = getBdcZssdDOS(yxmid);
            // 当存在已锁定证书时 进行删除锁定信息
            if (CollectionUtils.isNotEmpty(bdcZssdDOSd)) {
                LOGGER.warn("当前流程实例id{}获取到锁定的证书信息{}", processInsId, JSON.toJSONString(bdcZssdDOSd));
                bdcSdFeignService.deleteBdczsSd(bdcZssdDOSd);
                // 同步权籍证书锁定状态
                this.synQjBdcdyztSd(bdcZssdDOSd);
            }
        }
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new EntityNotFoundException("删除补录修改流程未查询到项目信息");
        }
        String xmid = bdcXmDTOS.get(0).getXmid();
//        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, yxmid);
        // 先删除证书信息再还原
//        bdcZsInitFeignService.deleteBdcZs(bdcXmDO.getGzlslid());
        bdcZsInitFeignService.deleteBdczsByXmid(yxmid);
        // 删除权利人信息
//        bdcQlrFeignService.deleteBatchBdcQlr(bdcXmDO.getGzlslid(), "");
        bdcQlrFeignService.delQlr(yxmid, "");
        // 还原补录修改流程数据
        bdcXxblLogFeignService.backBllcModifyLog(xmid);
    }

    /**
     * 权籍同步证书锁定状态
     *
     * @param bdcZssdDOSd 证书锁定信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void synQjBdcdyztSd(List<BdcZssdDO> bdcZssdDOSd) {
        List<String> bdcdyList = Lists.newArrayList();
        for (BdcZssdDO bdcZssdDO : bdcZssdDOSd) {
            if (StringUtils.isBlank(bdcZssdDO.getZsid())) {
                throw new MissingArgumentException("bdcZssdDO.getZsid()");
            }
            // 获取相关的项目信息
            List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZssdDO.getZsid());
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new MissingArgumentException("证书ID：" + bdcZssdDO.getZsid() + "未查询到对应的项目信息!");
            }
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (!QSZT_HISTORY.equals(bdcXmDO.getQszt())) {
                    bdcdyList.add(bdcXmDO.getBdcdyh());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcdyList)) {
            // 权籍同步证书锁定状态
            bdcDbxxFeignService.synQjBdcdyztSd(bdcdyList, SDZT_SD);
        }
    }

    /**
     * 获取上一手的项目 id
     *
     * @param processInsId 当前项目的 gzlslid
     * @return {String} yxmid 原项目id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private String getYxmid(String processInsId) {
        List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxBySlid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmLsgxDOS)) {
            return "";
        }
        return bdcXmLsgxDOS.get(0).getYxmid();
    }

    /**
     * 根据项目id 获取证书锁定数据
     *
     * @param xmid ximd
     * @return BdcZssdDO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private List<BdcZssdDO> getBdcZssdDOS(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            return Collections.emptyList();
        }
        // 查询到证书信息
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            LOGGER.info("{}: 未查询到 xmid ：{} 对应的证书信息", CLASS_NAME, xmid);
        }
        List<BdcZssdDO> bdcZssdDOSd = new ArrayList<>();
        for (BdcZsDO bdcZsDO : bdcZsDOS) {
            // 确认证书是否已被锁定过
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setSdzt(SDZT_SD);
            bdcZssdDO.setZsid(bdcZsDO.getZsid());
//            bdcZssdDO.setCqzh(bdcZsDO.getBdcqzh());
            List<BdcZssdDO> bdcZssdDOSdzt = bdcSdFeignService.queryBdczsSd(bdcZssdDO);
            LOGGER.warn("xmid{}获取到证书锁定的信息{}", xmid, JSON.toJSONString(bdcZssdDOSdzt));
            if (CollectionUtils.isNotEmpty(bdcZssdDOSdzt)) {
                bdcZssdDOSd.addAll(bdcZssdDOSdzt);
            }
        }
        return bdcZssdDOSd;
    }

    /**
     * 复制初始化数据
     *
     * @param yxmid  被复制的项目 id
     * @param bdcdyh 需要复制的不动产单元
     * @return {Object} 如果初始化成功返回 BdcXmDO 集合，否则返回一个 null 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcXmDO> copyBlxx(String yxmid, String bdcdyh) throws Exception {
        // 需要被复制的业务信息
        BdcYwxxDTO sourceYwxx = bdcInitFeignService.queryYwxx(yxmid);
        // 复制项目信息不存在直接返回
        if (sourceYwxx == null || sourceYwxx.getBdcXm() == null) {
            throw new MissingArgumentException("复制所选择的项目信息为空");
        }
        BdcXmDO bdcXm = sourceYwxx.getBdcXm();
        BdcXmFbDO bdcXmFb =sourceYwxx.getBdcXmFb();

        UserDto currentUser = userManagerUtils.getUserDto();

        BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);
        acceptDozerMapper.map(bdcXm, bdcSlJbxxDO);

        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(currentUser.getId());
        acceptDozerMapper.map(bdcXm, bdcSlXmDO);

        if(bdcXmFb != null){
            bdcSlXmDO.setQjgldm(bdcXmFb.getQjgldm());
        }
        // bdcdyh 保留原有的数据
        bdcSlXmDO.setBdcdyh(bdcdyh);
        if (sourceYwxx.getBdcCshFwkgSlDO() != null) {
            acceptDozerMapper.map(sourceYwxx.getBdcCshFwkgSlDO(), bdcSlXmDO);
        }

        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        // 1. 组装 受理项目
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);

        // 2. 组装 权利人信息
        List<BdcSlSqrDO> bdcSlSqrDOS = getCopyBdcSlSqr(sourceYwxx, bdcSlXmDO);
        bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOS);

        // 3. 组装 历史关系
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = getCopySlXmLsgxDOList(sourceYwxx, bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);

        // 4. 整体组装数据
        BdcSlxxDTO bdcSlxxDTO = getBdcSlxxDTO(bdcSlJbxxDO, bdcSlXmDTO);

        return cshXx(bdcdyh, bdcSlxxDTO, Constants.BLSHLX_NO);
    }

    /**
     * 挂接主房信息
     * 将传入的不动产单元号挂接到 yxmid 对应的项目 <br>
     * 场景：车库挂接主房
     * 1. 生成项目、fdcq、权利人和证书关系并且插入 cshfwkgsl <strong> zlcsh = 1 作为挂接项目的标识<strong/>
     * 2. 和 bdcdy 相关信息取权籍，其余取自原项目数据
     * 3. 受理人相关信息获取当前用户，受理时间取系统时间
     *
     * @param yxmid  被挂接的项目 id
     * @param bdcdyh 需要挂接的不动产单元
     * @return {BdcXmDO} 挂接后生成的项目信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @Transactional
    public BdcXmDO gjZfxx(String yxmid, String bdcdyh,String qjgldm) throws Exception {
        BdcYwxxDTO sourceYwxx = bdcInitFeignService.queryYwxx(yxmid);
        FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh,qjgldm);
        if (fwHsDO == null) {
            throw new EntityNotFoundException("未在房屋户室表中查询到对应的信息");
        }
        UserDto currentUser = userManagerUtils.getUserDto();
        // 构造项目信息
        BdcXmDO bdcXm = new BlGjXmBuilder().copyBdcXm(sourceYwxx.getBdcXm())
                .bdcdyh(bdcdyh)
                .fwHs(fwHsDO)
                .user(currentUser)
                .djjg(bdcSlJbxxFeignService.queryDjjgByUser(currentUser))
                .xmly(XMLY_QT_DM)
                .build();
        entityMapper.insertSelective(bdcXm);

        // 构造房地产权信息
        BdcFdcqDO fdcqDO = new BlGjFdcqBuilder().copyQl(sourceYwxx.getBdcQl())
                .fwHs(fwHsDO)
                .bdcXm(bdcXm)
                .build();
        entityMapper.insertSelective(fdcqDO);

        // 构造权利人信息
        List<BdcQlrDO> bdcQlrList = sourceYwxx.getBdcQlrList();
        for (BdcQlrDO bdcQlrDO : bdcQlrList) {
            bdcQlrDO.setXmid(bdcXm.getXmid());
            bdcQlrDO.setQlrid(generate16());
        }
        entityMapper.batchSaveSelective(bdcQlrList);

        // 构造项目证书关系
        List<BdcXmZsGxDO> bdcXmZsGxDOS = getGjXmZsGx(yxmid, bdcXm.getXmid());
        entityMapper.batchSaveSelective(bdcXmZsGxDOS);

        // 在初始化开关表中添加标识
        BdcCshFwkgSlDO cshFwkgSlDO = new BdcCshFwkgSlDO();
        cshFwkgSlDO.setSfzlcsh(SF_S_DM);
        cshFwkgSlDO.setSfsczs(SF_S_DM);
        cshFwkgSlDO.setId(bdcXm.getXmid());
        if (bdcXm.getDjxl() != null) {
            BdcCshFwkgDO bdcCshFwkgDO = bdcCshXtPzFeignService.queryBdcCshFwKgDO(bdcXm.getDjxl());
            cshFwkgSlDO.setDjxl(bdcCshFwkgDO.getDjxl());
            cshFwkgSlDO.setQlrsjly(bdcCshFwkgDO.getQlrsjly());
            cshFwkgSlDO.setYwrsjly(bdcCshFwkgDO.getYwrsjly());
            cshFwkgSlDO.setSfscql(bdcCshFwkgDO.getSfscql());
            cshFwkgSlDO.setQlsjly(bdcCshFwkgDO.getQlsjly());
            cshFwkgSlDO.setZszl(bdcCshFwkgDO.getZszl());
            cshFwkgSlDO.setSfzxyql(bdcCshFwkgDO.getSfzxyql());
        }
        entityMapper.insertSelective(cshFwkgSlDO);

        return bdcXm;
    }

    /**
     * 组织挂接项目和原项目的证书关系数据
     *
     * @param yxmid 原项目 id
     * @param xmid  新生成项目 id，对应挂接数据
     * @return 挂接数据与原项目证书的关系
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private List<BdcXmZsGxDO> getGjXmZsGx(String yxmid, String xmid) {
        // 查询出原项目的证书
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(yxmid);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        // 遍历证书集合，组织证书关系
        return bdcZsDOS.stream()
                .map(bdcZsDO -> buildXmZsGx(xmid, bdcZsDO.getZsid()))
                .collect(Collectors.toList());
    }

    private BdcXmZsGxDO buildXmZsGx(String xmid, String zsid) {
        BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
        bdcXmZsGxDO.setGxid(generate16());
        bdcXmZsGxDO.setXmid(xmid);
        bdcXmZsGxDO.setZsid(zsid);
        return bdcXmZsGxDO;
    }

    /**
     * 关联产权数据验证（判断是否可以关联产权）
     *
     * @param gzlslid 当前工作流实例 id
     * @param yxmid   上一手项目id
     * @return {String} success:可以关联， bdcdyh:不动产单元号不一致
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public String glsjyz(String gzlslid, String yxmid) {
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        BdcXmDO ybdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, yxmid);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new AppException("未查询到被关联项目信息！");
        }
        if (StringUtils.equals(bdcXmDTOS.get(0).getBdcdyh(), ybdcXm.getBdcdyh())) {
            return "success";
        } else {
            return "bdcdyh";
        }
    }

    /**
     * 关联产权
     *
     * @param gzlslid 当前工作流实例 id
     * @param xmid    上一手项目id 关联页面的
     * @param blxmid  补录页面的
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void glcq(String gzlslid, String xmid, String blxmid) {
        try {
            List<BdcXmLsgxDO> bdcXmLsgxDOList = Lists.newArrayList();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmQO.setXmid(blxmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            BdcXmDO bdcXmDO;
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                bdcXmDO = bdcXmDOList.get(0);
            } else {
                throw new AppException("未查询到要补录的项目信息");
            }
            BdcXmDO ybdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
            //bdcXmLsgxQO.setWlxm(0);//非外联的
            List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOS)) {
                // 更新上一手的qszt
                if (CollectionUtils.isNotEmpty(bdcXmLsgxDOS)) {
                    for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOS) {
                        if (ZXYQL_ZX.equals(bdcXmLsgxDO.getZxyql())) {
                            // 更新成现势
                            String yxmid = bdcXmLsgxDO.getYxmid();
                            BdcZxQO bdcZxQO = new BdcZxQO();
                            bdcZxQO.setXmid(yxmid);
                            bdcZxQO.setQszt(QSZT_VALID);
                            bdcDbxxFeignService.updateXmAndQlZxxxAndSynQjBdcdyzt(bdcZxQO);
                        }
                        // 删除lsgx
                        entityMapper.deleteByPrimaryKey(bdcXmLsgxDO.getClass(), bdcXmLsgxDO.getGxid());
                    }
                }
            }
            // 无论是否有上一手关系，都要新增新的lsgx
            BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
            bdcXmLsgxDO.setXmid(bdcXmDO.getXmid());
            bdcXmLsgxDO.setYxmid(ybdcXm.getXmid());
            String djxl = bdcXmDO.getDjxl();
            BdcCshFwkgDO bdcCshFwkgDO = new BdcCshFwkgDO();
            bdcCshFwkgDO.setDjxl(djxl);
            List<BdcCshFwkgDO> list = bdcCshXtPzFeignService.listBdcCshFwkg(bdcCshFwkgDO);
            if (CollectionUtils.isNotEmpty(list)) {
                Integer sfzxyql = list.get(0).getSfzxyql();
                sfzxyql = sfzxyql == null ? 0 : sfzxyql;
                bdcXmLsgxDO.setZxyql(sfzxyql);
                if (SF_S_DM == sfzxyql) {
                    // 新增完新的历史关系 要把下一手项目的qszt更新成历史
                    BdcZxQO bdcZxQO = new BdcZxQO();
                    bdcZxQO.setXmid(ybdcXm.getXmid());
                    bdcZxQO.setZxdjsj(new Date());
                    bdcZxQO.setZxdbr(userManagerUtils.getCurrentUserName());
                    bdcZxQO.setZxywh(ybdcXm.getSlbh());
                    bdcZxQO.setQszt(QSZT_HISTORY);
                    bdcZxQO.setZxyy("信息补录建立关联关系");
                    bdcDbxxFeignService.updateXmAndQlZxxxAndSynQjBdcdyzt(bdcZxQO);
                }
            } else {
                bdcXmLsgxDO.setZxyql(0);
            }

            bdcXmLsgxDOList.add(bdcXmLsgxDO);
            // 更新 XM 表中的 ycqzh 字段
            if (glycqzh && StringUtils.isNotBlank(ybdcXm.getBdcqzh())) {
                bdcXmDO.setYcqzh(ybdcXm.getBdcqzh());
                bdcXmFeignService.updateBdcXm(bdcXmDO);
            }
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                bdcXmFeignService.batchInsertBdcXmLsgx(bdcXmLsgxDOList);
            }

        } catch (Exception e) {
            throw new AppException("关联操作异常,补录项目id：" + blxmid + "关联项目id：" + xmid + "错误原因：" + e.getMessage());
        }
    }

    /**
     * 根据 xmid 查询是否生成证书
     * 如果不存在 BdcCshFwkgSlDO 则手动插入一条数据
     *
     * @param xmid 项目 id
     * @return {int} 是否生成证书， 0：否  1：是
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public int querySfsczs(String xmid, String djxl) {
        if (StringUtils.isBlank(xmid) && StringUtils.isBlank(djxl)) {
            throw new MissingArgumentException("缺失必要参数 XMID");
        }
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        Integer sfsczs;
        if (bdcCshFwkgSlDO == null) {
            BdcCshFwkgDO bdcCshFwkgDO = bdcCshXtPzFeignService.queryBdcCshFwKgDO(djxl);
            if (bdcCshFwkgDO == null) {
                LOGGER.error("项目 {}, 未获取到对应的初始化服务开关", xmid);
                sfsczs = -1;
            } else {
                // 手动插入一条新数据确保 重新生成证书 不抛出异常
                BdcCshFwkgSlDO cshFwkgSlDO = new BdcCshFwkgSlDO();
                cshFwkgSlDO.setYwrsjly(bdcCshFwkgDO.getYwrsjly());
                cshFwkgSlDO.setSfsczs(bdcCshFwkgDO.getSfsczs());
                cshFwkgSlDO.setDjxl(bdcCshFwkgDO.getDjxl());
                cshFwkgSlDO.setSfzxyql(bdcCshFwkgDO.getSfzxyql());
                cshFwkgSlDO.setQlsjly(bdcCshFwkgDO.getQlsjly());
                cshFwkgSlDO.setSfscql(bdcCshFwkgDO.getSfscql());
                cshFwkgSlDO.setZszl(bdcCshFwkgDO.getZszl());
                cshFwkgSlDO.setYwrsjly(bdcCshFwkgDO.getYwrsjly());
                cshFwkgSlDO.setId(xmid);
                entityMapper.insertSelective(cshFwkgSlDO);
                sfsczs = bdcCshFwkgDO.getSfsczs();
            }
        } else {
            sfsczs = bdcCshFwkgSlDO.getSfsczs();
        }
        if (sfsczs == null) {
            LOGGER.warn("项目 {}, 未获取到对应的初始化服务开关中是否生成证书配置", xmid);
            sfsczs = -1;
        }
        return sfsczs;
    }

    /**
     * 复制功能 获取历史关系
     *
     * @param sourceYwxx 业务数据
     * @param bdcSlXmDO 受理项目
     * @return 受理项目历史关系
     */
    private List<BdcSlXmLsgxDO> getCopySlXmLsgxDOList(BdcYwxxDTO sourceYwxx, BdcSlXmDO bdcSlXmDO) {
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = Lists.newArrayList();
        List<BdcXmLsgxDO> bdcXmLsgxList = sourceYwxx.getBdcXmLsgxList();
        for (BdcXmLsgxDO bdcXmLsgx : bdcXmLsgxList) {
            BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
            bdcSlXmLsgxDO.setXmid(bdcSlXmDO.getXmid());
            acceptDozerMapper.map(bdcXmLsgx, bdcSlXmLsgxDO);
            bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        }
        return bdcSlXmLsgxDOList;
    }

    private List<BdcSlXmLsgxDO> generateSlxmLsgx(BdcYwxxDTO sourceYwxx, BdcSlXmDO bdcSlXmDO) {
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = Lists.newArrayList();
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
        bdcSlXmLsgxDO.setXmid(bdcSlXmDO.getXmid());
        bdcSlXmLsgxDO.setYxmid(sourceYwxx.getBdcXm().getXmid());
        bdcSlXmLsgxDO.setSfwlzs(0);
        //补录修改数据注销原权力
        bdcSlXmLsgxDO.setZxyql(1);
        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        return bdcSlXmLsgxDOList;
    }

    /**
     * 复制功能 处理 受理申请人
     *
     * @param sourceYwxx 业务数据
     * @param bdcSlXmDO  受理项目
     * @return 受理权利人
     */
    private List<BdcSlSqrDO> getCopyBdcSlSqr(BdcYwxxDTO sourceYwxx, BdcSlXmDO bdcSlXmDO) {
        List<BdcQlrDO> bdcQlrList = sourceYwxx.getBdcQlrList();
        return bdcQlrList.stream().map(bdcQlrDO -> {
            BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
            acceptDozerMapper.map(bdcQlrDO, bdcSlSqrDO);
            bdcSlSqrDO.setSqrid(generate16());
            bdcSlSqrDO.setXmid(bdcSlXmDO.getXmid());
            return bdcSlSqrDO;
        }).collect(Collectors.toList());
    }

    /**
     * 根据 bdcqzh 获取到 xmid
     *
     * @param bdcqzh 不动产权证号
     * @return xmid 项目id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private String queryXmidByBdcqzh(String bdcqzh,String bdcdyh) {
        if (StringUtils.isBlank(bdcqzh)||StringUtils.isBlank(bdcdyh)) {
            return StringUtils.EMPTY;
        }
        List<Map> maps = bdcXmFeignService.queryBdcxmByCqzh(bdcqzh, QSZT_VALID.toString(),bdcdyh);
        return (CollectionUtils.isNotEmpty(maps) && maps.get(0) != null) ? (String) maps.get(0).get("XMID") : StringUtils.EMPTY;
    }

    /**
     * 删除补录数据，补录台账删除数据 <br>
     * 删除业务数据和补录审核数据
     * @param xmid xmid 用于判断删除的是挂接项目还是主项目
     * @param all  是否删除全部，true：删除全部，false：只删除当前项目「用于删除挂接项目」
     * @return {boolean} 是否删除成功
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public boolean deleteBlxx(String xmid, boolean all) {
        if (StringUtils.isBlank(xmid)) {
            LOGGER.error("{} 删除补录数据失败，参数 xmid 为空", CLASS_NAME);
            throw new MissingArgumentException("xmid");
        }
        // 判断是否属于挂接数据
        if (isGjxm(xmid)) {
            try {
                // 删除补录审核数据
                bdcXxblShService.deleteBlShxx(xmid);
                // 删除业务数据
                String[] xmids = {xmid};
                bdcInitFeignService.deleteYwxx(xmids);
            } catch (Exception e) {
                LOGGER.warn("{} :删除 xmid 为：{} 的数据过程中出现异常，异常信息为: {}", CLASS_NAME, xmid, e.getMessage());
                throw new AppException("删除补录信息出现异常");
            }
        } else {
            // 查询项目信息
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (bdcXmDO == null) {
                throw new EntityNotFoundException("未查询到对应的项目信息");
            }
            String gzlslid = bdcXmDO.getGzlslid();
            List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            // 除挂接项目，其余删除必须全部删除
            if (bdcXmDTOS.size() == 1 || all) {
                //还原状态
                registerWorkflowFeignService.revertBdcDbxxAndQsztSyncQj(gzlslid);
                //状态特殊逻辑处理
                bdcDbxxFeignService.updateYzxqlDbxxAndQszt(gzlslid, QSZT_HISTORY);
                try {
                    // 删除补录审核信息
                    bdcXxblShService.deleteBlShxxs(gzlslid);
                    // 删除业务数据
                    bdcInitFeignService.deleteYwxx(gzlslid);
                } catch (Exception e) {
                    LOGGER.warn("{} :删除 gzlslid 为: {} 的全部数据过程中出现异常，异常信息为: {}", CLASS_NAME, gzlslid, e.getMessage());
                    throw new AppException("删除补录信息出现异常");
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前项目是否是挂接项目 <br>
     * 挂接项目：初始化时 fwkgsl 表中的 sfzlcsh 值插入的是 1
     * @param xmid xmid
     * @return boolean true: 项目是挂接项目， false：不是挂接项目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private boolean isGjxm(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        // 查询实例对象
        BdcCshFwkgSlDO cshSl = bdcXmFeignService.queryFwkgsl(xmid);
        return null != cshSl && null != cshSl.getSfzlcsh() && cshSl.getSfzlcsh() == 1;
    }

    @Override
    public BdcXmDO querySfscQl(String xmid) {
        Integer sfscql = SF_S_DM;
        // 查询到初始化房屋开关表的实例数据
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
        if (bdcCshFwkgSlDO != null && bdcCshFwkgSlDO.getSfscql() != null) {
            sfscql = bdcCshFwkgSlDO.getSfscql();
        }

        if (SF_F_DM.equals(sfscql)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            List<BdcXmLsgxDO> bdcXmLsgxDOS = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isEmpty(bdcXmLsgxDOS)) {
                throw new AppException("当前项目未生成权利，未查询到对应的历史关系");
            }
            // 不生成权利，xmid 取 yxmid
            xmid = bdcXmLsgxDOS.get(0).getYxmid();
        }

        return entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
    }

    /**
     * 初始化信息
     *
     * @param bdcdyh     不动产单元号
     * @param bdcSlxxDTO 受理信息 DTO
     * @param blshlx     补录审核类型
     * @return List<BdcXmDO> 初始生成的项目集合
     */
    private List<BdcXmDO> cshXx(String bdcdyh, BdcSlxxDTO bdcSlxxDTO, Integer blshlx) {
        bdcSlxxDTO.setXxbl(true);
        List<BdcXmDO> csh;
        // 判断是否是虚拟的不动产单元号的前缀，是则先补齐 bdcdyh
        if (BdcdyhToolUtils.checkXnbdcdyh(bdcdyh + XNDYH_SUFFIX) && bdcdyh.length() == 20) {
            try {
                csh = bdcXnBdcdyhService.updateXnbdcdyh(bdcdyh, bdcSlxxDTO);
            } catch (Exception e) {
                LOGGER.error("{},信息补录生成虚拟不动产单元号发生错误：{}", CLASS_NAME, e.getMessage());
                throw new AppException(e.getMessage());
            }
        } else {
            LOGGER.warn("信息补录，不动产单元号初始化：{}", bdcSlxxDTO);
            try {
                csh = bdcInitFeignService.csh(bdcSlxxDTO);
            } catch (Exception e) {
                LOGGER.error("{},信息补录初始化流程发生错误：{}", CLASS_NAME, e.getMessage());
                throw new AppException(e.getMessage());
            }
        }
        if (CollectionUtils.isEmpty(csh)) {
            throw new EntityNotFoundException("未获取到初始化后的项目信息!");
        }
        //是否设置了不需要审核
        if (Constants.BLSHLX_NO.equals(blshlx) || BLSHLX_NOSH) {
            return csh;
        }
        //需要审核
        BdcXmDO bdcXmDO = csh.get(0);
        BdcBlShDO bdcBlShDO = new BdcBlShDO();
        // 补录状态设置为审核中
        bdcBlShDO.setBlzt(Constants.BLZT_ONGOING);
        // 补录类型设置为新增
        bdcBlShDO.setBllx(Constants.BLLX_NEW);
        bdcBlShDO.setBlshlx(blshlx);
        bdcBlShDO.setGzlslid(bdcXmDO.getGzlslid());
        // 补录暂只支持单个流程
        bdcBlShDO.setXmid(bdcXmDO.getXmid());
        bdcXxblShService.generateBlShxx(bdcBlShDO);
        return csh;
    }

    /**
     * 组装 受理信息 DTO
     *
     * @param bdcSlJbxxDO 受理基本信息
     * @param bdcSlXmDTO  受理项目 DTO
     * @return 受理信息 DTO
     */
    private BdcSlxxDTO getBdcSlxxDTO(BdcSlJbxxDO bdcSlJbxxDO, BdcSlXmDTO bdcSlXmDTO) {
        List<BdcSlXmDTO> bdcSlXmDTOList = Lists.newArrayList(bdcSlXmDTO);
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);
        return bdcSlxxDTO;
    }

    /**
     * 组装 受理基本信息
     *
     * @param currentUser 用户信息
     * @return 受理信息 DTO
     */
    private BdcSlJbxxDO getBdcSlJbxxDO(UserDto currentUser) {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        String slbh = bdcBhFeignService.queryBh(SLBH, ZZSJFW_DAY);
        bdcSlJbxxDO.setSlbh(slbh);
        // 信息补录不生成项目，gzlslid 用 slbh 代替
        bdcSlJbxxDO.setGzlslid(slbh);
        bdcSlJbxxDO.setSlr(currentUser.getAlias());
        bdcSlJbxxDO.setSlrid(currentUser.getId());
        if (CollectionUtils.isEmpty(currentUser.getOrgRecordList())) {
            throw new AppException("当前用户缺失组织信息");
        }
        // 有受理人场景，使用受理人组织机构赋值区县代码
        OrganizationDto organizationDto = currentUser.getOrgRecordList().get(0);
        if (organizationDto != null) {
            bdcSlJbxxDO.setDjjg(bdcSlJbxxFeignService.queryDjjgByUser(currentUser));
            bdcSlJbxxDO.setQxdm(organizationDto.getRegionCode());
        }
        bdcSlJbxxDO.setSlsj(new Date());
        bdcSlJbxxDO.setXmly(Integer.parseInt(XXBL_XMLY));
        return bdcSlJbxxDO;
    }

    /**
     * 组织初始化所需的 受理项目 DTO
     *
     * @param bdcBlxxDTO  bdcBlxxDTO
     * @param currentUser 当前用户
     * @return {BdcSlXmDTO} 受理项目 DTO
     */
    private BdcSlXmDTO getCshBdcSlXmDTO(BdcBlxxDTO bdcBlxxDTO, UserDto currentUser) {
        // dyhcxlx 默认为 1 ，暂时不支持 海域和构筑物 的判断，传入的 bdcdyh 如果不是 28 位，说明传入的是虚拟不动产单元号的前缀，补齐单元号
        Integer qllx = this.getQllx(bdcBlxxDTO);
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzFeignService.listBdcDjxlPzByGzldyid(bdcBlxxDTO.getGzldyid(), qllx);


        if (CollectionUtils.isEmpty(bdcDjxlPzDOList)) {
            throw new AppException("不动产登记小类配置（BDC_DJXL_PZ）未配置,请联系管理员");
        }

        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(currentUser.getId());
        // 判断是否是虚拟的不动产单元号，如果不是 28 位，说明传入的是虚拟不动产单元号的前缀，补齐单元号
        String bdclx = this.getBdclx(bdcBlxxDTO);
        if (StringUtils.isNotBlank(bdclx)) {
            bdcSlXmDO.setBdclx(Integer.parseInt(bdclx.split("/")[0]));
        }
        bdcSlXmDO.setQlsjly(bdcBlxxDTO.getQlsjly());
        bdcSlXmDO.setQjgldm(bdcBlxxDTO.getQjgldm());
        if (Objects.nonNull(bdcBlxxDTO.getBdcdyfwlx())) {
            bdcSlXmDO.setBdcdyfwlx(bdcBlxxDTO.getBdcdyfwlx());
        }
        String yxmid = "";
        List<BdcSlXmDO> bdcSlXmDOList = Lists.newArrayList();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = Lists.newArrayList();
        for (int i = 0; i < bdcDjxlPzDOList.size(); i++) {
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzDOList.get(i);
            // 初始化逻辑，谨慎修改
            bdcSlXmDO.setDjxl(bdcDjxlPzDO.getDjxl());
            bdcSlXmDO.setQllx(bdcDjxlPzDO.getQllx());
            bdcSlXmDO.setBdcdyh(bdcBlxxDTO.getBdcdyh());
            bdcSlXmDO.setYbdcqz(bdcBlxxDTO.getYcqzh());
            bdcSlXmDOList.add(bdcSlXmDO);
            // 组合流程第二个项目的原项目 ID 是第一个项目，此处针对于组合流程进行处理
            if (i == 0) {
                // 选择查封，yxmid 从前端获取
                if (StringUtils.isBlank(bdcSlXmDO.getYbdcqz()) && StringUtils.isNotBlank(bdcBlxxDTO.getCfwh())
                        && StringUtils.isNotBlank(bdcBlxxDTO.getYxmid())) {
                    yxmid = bdcBlxxDTO.getYxmid();
                } else {
                    yxmid = queryXmidByBdcqzh(bdcSlXmDO.getYbdcqz(),bdcSlXmDO.getBdcdyh());
                }
            } else if (bdcDjxlPzDO.getSxh() > 1 && bdcDjxlPzDO.getSxh() < bdcSlXmDOList.size() + 1) {   // 增加判断避免索引越界
                yxmid = bdcSlXmDOList.get(bdcDjxlPzDO.getSxh() - 2).getXmid();
            }
            BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), yxmid, "", "", "");
            bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        }

        // 组装受理项目 DTO
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
        return bdcSlXmDTO;
    }

    /**
     * 根据传入的不动产单元号判断 bdclx
     * <strong>补录传入的 bdcdyh 有可能是虚拟的，并且缺少最后 8 位<strong/>
     * @param bdcBlxxDTO 补录数据
     * @return {String} 不动产类型
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private String getBdclx(BdcBlxxDTO bdcBlxxDTO) {
        String bdcdyh = bdcBlxxDTO.getBdcdyh();
        if (StringUtils.length(bdcdyh) != BDCDYH_LENGTH) {
            bdcdyh += XNDYH_SUFFIX;
        }
        return BdcdyhToolUtils.queryBdclxByBdcdyh(bdcdyh, "1");
    }

    /**
     * 根据传入的不动产单元号判断 qllx
     * <strong>补录传入的 bdcdyh 有可能是虚拟的，并且缺少最后 8 位<strong/>
     * @param bdcBlxxDTO 补录数据
     * @return {String} 权利类型
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private Integer getQllx(BdcBlxxDTO bdcBlxxDTO) {
        //非限制类权利直接作为当前单元号的单元号权利类型
        if (bdcBlxxDTO.getYqllx() != null && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcBlxxDTO.getYqllx())) {
            return bdcBlxxDTO.getYqllx();
        }
        String bdcdyh = bdcBlxxDTO.getBdcdyh();
        if (StringUtils.length(bdcdyh) != BDCDYH_LENGTH) {
            bdcdyh += XNDYH_SUFFIX;
        }
        return bdcQllxFeignService.getQllxByBdcdyh(bdcdyh, "1");
    }

    /**
     * 保存被修改的业务的数据 <br>
     * 日志参数取 xmid，保存数据为 yxmid 内容以供删除时还原
     *
     * @param yxmid 被修改的项目的 xmid
     * @param xmid  当前项目 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    public void saveBllcModifyLog(String yxmid, String xmid) {
        try {
            BdcYwxxDTO bdcYwxxDTO = bdcInitFeignService.queryYwxx(yxmid);
            Map<String, String> data = new HashMap<>();
            data.put("ywxx", JSON.toJSONString(bdcYwxxDTO));
            // 使用当前修改项目的 xmid 作为查询参数
            data.put("paramCha", xmid);
            data.put(VIEW_TYPE_NAME, "信息补录修改流程初始化数据");
            data.put("eventName", LogEventEnum.BLLC_MODIFY.key());
            LogMsgDTO logMsgDTO = new LogMsgDTO();
            logMsgDTO.setEvent(LogEventEnum.BLLC_MODIFY.key());
            logMsgDTO.setTags(data);
            logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
            LOGGER.warn("xmid:{} 保存的被修改信息初始化的数据为:{}", xmid, JSON.toJSONString(bdcYwxxDTO));
            logMessageClient.save(logMsgDTO);
        } catch (Exception e) {
            LOGGER.error(CLASS_NAME + ":查询 xmid{}业务信息出现异常！", xmid, e);
        }
    }


    @Override
    public List<BdcXmDO> plbl(String yxmid,String xmidListStr) throws Exception {
        UserDto currentUser = userManagerUtils.getUserDto();
        // 需要被复制的业务信息
        BdcYwxxDTO sourceYwxx = bdcInitFeignService.queryYwxx(yxmid);
        BdcXmDO ybdcXm = sourceYwxx.getBdcXm();
        BdcQl bdcQl = sourceYwxx.getBdcQl();
        BdcDyaqDO ybdcDyaqDO = null;
        if (bdcQl != null) {
            if(bdcQl instanceof  BdcDyaqDO){
                ybdcDyaqDO = (BdcDyaqDO) bdcQl;
            }
        }
        List<BdcQlrDO> ybdcQlrList = sourceYwxx.getBdcQlrList();
        List<String> xmidList = JSON.parseArray(xmidListStr, String.class);
        for (String xmid : xmidList) {
            BdcYwxxDTO ywxx = bdcInitFeignService.queryYwxx(xmid);
            //组织基本信息
            BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);
            bdcSlJbxxDO.setLcmc(ybdcXm.getGzldymc());
            bdcSlJbxxDO.setGzldyid(ybdcXm.getGzldyid());
            //整理受理项目
            BdcBlxxDTO bdcBlxxDTO = new BdcBlxxDTO();
            bdcBlxxDTO.setBdcdyh(ywxx.getBdcXm().getBdcdyh());
            bdcBlxxDTO.setGzldyid(ybdcXm.getGzldyid());
            bdcBlxxDTO.setYcqzh(ywxx.getBdcXm().getBdcqzh());
            bdcBlxxDTO.setBlshlx(-1);
            BdcSlXmDTO bdcSlXmDTO = getCshBdcSlXmDTO(bdcBlxxDTO,currentUser);
            //组装数据
            BdcSlxxDTO bdcSlxxDTO = getBdcSlxxDTO(bdcSlJbxxDO, bdcSlXmDTO);
            //初始化
            LOGGER.info("信息补录流程初始化bdcSlXmDTO:{}", JSON.toJSONString(bdcSlXmDTO));
            List<BdcXmDO> bdcXmDOList = cshXx(bdcBlxxDTO.getBdcdyh(), bdcSlxxDTO, bdcBlxxDTO.getBlshlx());
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                bdcXmDO.setBz(ybdcXm.getBz());
                bdcXmDO.setDbr(ybdcXm.getDbr());
                bdcXmDO.setGyfs(ybdcXm.getGyfs());
                bdcXmDO.setSlbh(ybdcXm.getSlbh());
                bdcXmDO.setGzlslid(ybdcXm.getGzlslid());
                bdcXmDO.setDjsj(ybdcXm.getDjsj());
                bdcXmFeignService.updateBdcXm(bdcXmDO);
                //更新qlr信息
                copyAndUpdateBdcqlr(ybdcQlrList,ybdcXm.getXmid(),bdcXmDO.getGzlslid());
                //获取抵押权
                Example example = new Example(BdcDyaqDO.class);
                example.createCriteria().andEqualTo("xmid", bdcXmDO.getXmid());
                List<BdcDyaqDO> bdcDyaqDOList = entityMapper.selectByExample(example);
                for (BdcDyaqDO bdcDyaqDO : bdcDyaqDOList) {
                    //更新抵押权信息
                    if(ybdcDyaqDO != null){
                        copyAndUpdateBdcDyaq(ybdcDyaqDO,bdcDyaqDO);
                    }

                }
            }
        }
        //生成证书
        String bdcqzh = "";
        // 查询原不动产权证号
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(yxmid);
        if (CollectionUtils.isNotEmpty(bdcZsDOList) && StringUtils.isNotBlank(bdcZsDOList.get(0).getBdcqzh())) {
            BdcZsDO bdcZsDO = bdcZsDOList.get(0);
            LOGGER.info("原证书信息{}", JSON.toJSONString(bdcZsDO));
            bdcqzh = bdcZsDO.getBdcqzh();
        }
        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOS = bdcXmFeignService.queryFwkgslByGzlslid(ybdcXm.getGzlslid());
        for (BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlDOS) {
            bdcCshFwkgSlDO.setZsxh(1);
            bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDO);
        }
        List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.initBdcqzsSjbl(ybdcXm.getGzlslid(), false);
        LOGGER.warn("补录生成证书信息为：{}", bdcZsDOS);
        BdcZsDO newBdcZs = bdcZsDOS.get(0);
        newBdcZs.setBdcqzh(bdcqzh);
        bdcZsFeignService.updateBdcZs(newBdcZs);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(ybdcXm.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        LOGGER.warn("信息补录相关项目为：{}", bdcXmDOList);
        if (StringUtils.isNotBlank(bdcqzh)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                bdcXxblZsFeignService.updateBlBdcqzh(bdcXmDO.getXmid(), bdcqzh);
            }
        }
        try {
            bdcYwsjHxFeignService.saveBdcYwsj(ybdcXm.getGzlslid());
        } catch (Exception e) {
            LOGGER.error(CLASS_NAME + ":回写信息到大云失败！", e);
        }
        return bdcXmDOList;
    }


    /**
     * 组装 受理基本信息
     *
     * @param currentUser 用户信息
     * @return 受理信息 DTO
     */
    private BdcSlJbxxDO getCopyBdcSlJbxxDO(UserDto currentUser,BdcXmDO bdcXm) {
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlJbxxDO.setSlbh(bdcXm.getSlbh());
        bdcSlJbxxDO.setGzlslid(bdcXm.getGzlslid());
        bdcSlJbxxDO.setSlr(bdcXm.getSlr());
        bdcSlJbxxDO.setSlrid(bdcXm.getSlrid());
        bdcSlJbxxDO.setDjjg(bdcXm.getDjjg());
        bdcSlJbxxDO.setQxdm(bdcXm.getQxdm());
        bdcSlJbxxDO.setSlsj(new Date());
        bdcSlJbxxDO.setXmly(Integer.parseInt(XXBL_XMLY));
        return bdcSlJbxxDO;
    }

    /**
     * 复制权利人信息
     * @param yBdcQlrList 原权利人信息
     * @param xmid 项目ID
     * @param gzlslid
     * @return
     */
    private List<BdcQlrDO> copyAndUpdateBdcqlr(List<BdcQlrDO> yBdcQlrList,String xmid,String gzlslid){
        yBdcQlrList = yBdcQlrList.stream().filter(bdcQlrDO -> CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())).collect(Collectors.toList());
        bdcQlrFeignService.delQlr(xmid, CommonConstantUtils.QLRLB_QLR);
        for (BdcQlrDO bdcQlrDO : yBdcQlrList) {
            bdcQlrDO.setXmid(xmid);
            bdcQlrDO.setQlrid(UUIDGenerator.generate16());
        }
        bdcQlrFeignService.updateBatchBdcQlr(yBdcQlrList,gzlslid,CommonConstantUtils.QLRLB_QLR);
        bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(gzlslid);
        return yBdcQlrList;
    }
    /**
     * 批量信息补录复制抵原押权信息到新生成的抵押权
     * @param yBdcDyaqDO 原抵押权信息
     * @param destBdcDyaqDO 批量补录生成抵押权信息
     * @return
    **/
    private void copyAndUpdateBdcDyaq(BdcDyaqDO yBdcDyaqDO,BdcDyaqDO destBdcDyaqDO){
        destBdcDyaqDO.setBdbzzqse(yBdcDyaqDO.getBdbzzqse());
        destBdcDyaqDO.setDyfs(yBdcDyaqDO.getDyfs());
        destBdcDyaqDO.setSlbh(yBdcDyaqDO.getSlbh());
        destBdcDyaqDO.setZwlxqssj(yBdcDyaqDO.getZwlxqssj());
        destBdcDyaqDO.setZwlxjssj(yBdcDyaqDO.getZwlxjssj());
        destBdcDyaqDO.setDjsj(yBdcDyaqDO.getDjsj());
        destBdcDyaqDO.setDbr(yBdcDyaqDO.getDbr());
        destBdcDyaqDO.setDbfw(yBdcDyaqDO.getDbfw());
        entityMapper.updateByPrimaryKeySelective(destBdcDyaqDO);
    }

    /**
     * 信息补录恢复权利工作流事件
     * @param processInsId 工作流实例ID
     **/
    @Override
    public void hyql(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("processInsId");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", SF_F_DM);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                if (StringUtils.isNotBlank(yxmid)) {
                    BdcXmQO bdcXmQO = new BdcXmQO(yxmid);
                    List<BdcXmDO> yBdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(yBdcXmDOS)) {
                        //恢复权利验证
                        //此处不需要验证，feign接口已经有了验证
//                        yzHfQlxx(yBdcXmDOS.get(0));
                        BdcHfQO bdcHfQO = new BdcHfQO();
                        bdcHfQO.setXmid(yxmid);
                        bdcHfQO.setQszt(CommonConstantUtils.QSZT_VALID);
                        bdcDbxxFeignService.hfQl(bdcHfQO);
                    }
                }
            }
        }
    }

    /**
     * 删除信息补录
     * @param processInsId 工作流实例ID
     * @return
     **/
    @Override
    public void deleteBlxx(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("processInsId");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            //根据当前的受理项目历史关系找到原流程数据并删除
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", SF_F_DM);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                bdcXxblFeignService.deleteBlxx(yxmid, false);
            }
        }
    }

    @Override
    public TaskData cshDeleteXxblLc(BdcBlxxDTO bdcBlxxDTO) throws Exception {
        if (StringUtils.isBlank(bdcBlxxDTO.getPtgzldyid())) {
            throw new MissingArgumentException("缺少平台工作流定义 ID");
        }
        UserDto currentUser = userManagerUtils.getUserDto();
        // 0. 创建平台项目
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcBlxxDTO.getPtgzldyid(), currentUser.getUsername(), null, "",null);
        // 1. 组织基本信息
        BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);
        bdcSlJbxxDO.setGzlslid(taskData.getProcessInstanceId());
        bdcSlJbxxDO.setLcmc(taskData.getProcessDefName());
        bdcSlJbxxDO.setGzldyid(bdcBlxxDTO.getPtgzldyid());

        // 2. 整理受理项目
        BdcSlXmDTO bdcSlXmDTO = getCshDeletLcBdcSlXmDTO(bdcBlxxDTO, currentUser);
        // 3. 组装数据
        BdcSlxxDTO bdcSlxxDTO = getBdcSlxxDTO(bdcSlJbxxDO, bdcSlXmDTO);
        LOGGER.info("初始化删除信息补录流程bdcSlXmDTO:{}", JSON.toJSONString(bdcSlXmDTO));
        bdcInitFeignService.csh(bdcSlxxDTO);
        return taskData;
    }


    /**
    * 组装受理项目
    *
    **/
    public BdcSlXmDTO getCshDeletLcBdcSlXmDTO(BdcBlxxDTO bdcBlxxDTO, UserDto currentUser){
        Integer qllx = this.getQllx(bdcBlxxDTO);
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzFeignService.listBdcDjxlPzByGzldyid(bdcBlxxDTO.getPtgzldyid(), qllx);
        if(CollectionUtils.isEmpty(bdcDjxlPzDOList)){
            throw new AppException("不动产登记小类配置（BDC_DJXL_PZ）未配置,请联系管理员");
        }

        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(currentUser.getId());
        String yxmid = bdcBlxxDTO.getYxmid();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = Lists.newArrayList();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcBlxxDTO.getYxmid());
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            BdcXmDO bdcXmDO = bdcXmDOS.get(0);
            bdcSlXmDO.setBdcdyh(bdcXmDO.getBdcdyh());
            bdcSlXmDO.setBdclx(bdcXmDO.getBdclx());
            bdcSlXmDO.setZl(bdcXmDO.getZl());
        }
        for (int i = 0; i < bdcDjxlPzDOList.size(); i++) {
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzDOList.get(i);
            bdcSlXmDO.setDjxl(bdcDjxlPzDO.getDjxl());
            bdcSlXmDO.setQllx(bdcDjxlPzDO.getQllx());
            bdcSlXmDO.setBdclx(bdcDjxlPzDO.getBdclx());
            BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), yxmid, "", "", "");
            bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        }
        // 组装受理项目 DTO
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
        return bdcSlXmDTO;
    }


    @Override
    public List<BdcXmDO> selectBdcdyhPlbl(String yxmid,String bdcdyhListStr) throws Exception {
        UserDto currentUser = userManagerUtils.getUserDto();
        //原业务信息
        BdcYwxxDTO sourceYwxx = bdcInitFeignService.queryYwxx(yxmid);
        List<BdcQlrDO> ybdcQlrList = sourceYwxx.getBdcQlrList();
        BdcXmDO ybdcXm = sourceYwxx.getBdcXm();
        //生成证书
        String bdcqzh = "";
        // 查询原不动产权证号
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(yxmid);
        if (CollectionUtils.isNotEmpty(bdcZsDOList) && StringUtils.isNotBlank(bdcZsDOList.get(0).getBdcqzh())) {
            BdcZsDO bdcZsDO = bdcZsDOList.get(0);
            LOGGER.info("原证书信息{}", JSON.toJSONString(bdcZsDO));
            bdcqzh = bdcZsDO.getBdcqzh();
        }
        List<BdcBlShDO> bdcBlShDOS = bdcXxblShService.queryBlshxxByXmid(yxmid);
        BdcBlShDO yBdcBlShDO = null;
        if(CollectionUtils.isNotEmpty(bdcBlShDOS)){
            yBdcBlShDO = bdcBlShDOS.get(0);
        }
        List<Map> bdcdyhList = JSON.parseArray(bdcdyhListStr, Map.class);
        for (Map<String,String> bdcdyhMap : bdcdyhList) {
            String bdcdyh = bdcdyhMap.get("bdcdyh");
            String yqllx = bdcdyhMap.get("yqllx");
            //组织基本信息
            BdcSlJbxxDO bdcSlJbxxDO = getBdcSlJbxxDO(currentUser);
            bdcSlJbxxDO.setLcmc(ybdcXm.getGzldymc());
            bdcSlJbxxDO.setGzldyid(ybdcXm.getGzldyid());
            //整理受理项目
            BdcBlxxDTO bdcBlxxDTO = new BdcBlxxDTO();
            bdcBlxxDTO.setBdcdyh(bdcdyh);
            bdcBlxxDTO.setGzldyid(ybdcXm.getGzldyid());
            if(StringUtils.isNotBlank(yqllx)){
                bdcBlxxDTO.setYqllx(Integer.parseInt(yqllx));
            }
            if(yBdcBlShDO != null){
                bdcBlxxDTO.setBlshlx(bdcBlShDOS.get(0).getBlshlx());
            }
            BdcSlXmDTO bdcSlXmDTO = getCshBdcSlXmDTO(bdcBlxxDTO,currentUser);
            //组装数据
            BdcSlxxDTO bdcSlxxDTO = getBdcSlxxDTO(bdcSlJbxxDO, bdcSlXmDTO);
            //初始化
            LOGGER.info("信息补录流程初始化bdcSlXmDTO:{}", JSON.toJSONString(bdcSlXmDTO));
            List<BdcXmDO> bdcXmDOList = cshXx(bdcBlxxDTO.getBdcdyh(), bdcSlxxDTO, bdcBlxxDTO.getBlshlx());
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                bdcXmDO.setBz(ybdcXm.getBz());
                bdcXmDO.setDbr(ybdcXm.getDbr());
                bdcXmDO.setGyfs(ybdcXm.getGyfs());
                bdcXmDO.setSlbh(ybdcXm.getSlbh());
                bdcXmDO.setGzlslid(ybdcXm.getGzlslid());
                bdcXmDO.setDjsj(ybdcXm.getDjsj());
                bdcXmFeignService.updateBdcXm(bdcXmDO);
                //更新qlr信息
                copyAndUpdateBdcqlr(ybdcQlrList,ybdcXm.getXmid(),bdcXmDO.getGzlslid());
                //更新审核信息
                List<BdcBlShDO> bdcBlShList = bdcXxblShService.queryBlshxxByXmid(bdcXmDO.getXmid());
                if(CollectionUtils.isNotEmpty(bdcBlShList)){
                    for (BdcBlShDO bdcBlShDO : bdcBlShList) {
                        bdcBlShDO.setGzlslid(ybdcXm.getGzlslid());
                        entityMapper.updateByPrimaryKeySelective(bdcBlShDO);
                    }
                }
            }
        }
        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOS = bdcXmFeignService.queryFwkgslByGzlslid(ybdcXm.getGzlslid());
        for (BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlDOS) {
            bdcCshFwkgSlDO.setZsxh(1);
            bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDO);
        }
        List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.initBdcqzsSjbl(ybdcXm.getGzlslid(), false);
        LOGGER.warn("补录生成证书信息为：{}", bdcZsDOS);
        BdcZsDO newBdcZs = bdcZsDOS.get(0);
        newBdcZs.setBdcqzh(bdcqzh);
        bdcZsFeignService.updateBdcZs(newBdcZs);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(ybdcXm.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        LOGGER.warn("信息补录相关项目为：{}", bdcXmDOList);
        if (StringUtils.isNotBlank(bdcqzh)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                bdcXxblZsFeignService.updateBlBdcqzh(bdcXmDO.getXmid(), bdcqzh);
            }
        }
        return bdcXmDOList;
    }

    /**
     * @description  处理证书序号
     */
    private void dealZsxh(BdcSlxxDTO bdcSlxxDTO,String gzldyid) {
        if (bdcSlxxDTO != null && bdcSlxxDTO.getBdcSlJbxx() != null && StringUtils.isNotBlank(gzldyid) && CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOS = bdcYxbdcdyKgPzFeignService.queryBdcYxbdcdyKgPzDOListByGzldyid(gzldyid);
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

}
