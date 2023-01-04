package cn.gtmap.realestate.exchange.service.impl.inf.yzw;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.SDmXzdmDO;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjSjDO;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.building.BuildingZdConvertFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.bo.yzw.BasicModel;
import cn.gtmap.realestate.exchange.core.bo.yzw.FaceModel;
import cn.gtmap.realestate.exchange.core.bo.yzw.FieldModel;
import cn.gtmap.realestate.exchange.core.bo.yzw.LicenseModel;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwTsjlDO;
import cn.gtmap.realestate.exchange.core.domain.yzw.GxYzwYzsbxxDO;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwCheckAndTsResultDTO;
import cn.gtmap.realestate.exchange.core.mapper.exchange.YzwMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjSjMapper;
import cn.gtmap.realestate.exchange.core.qo.GxYzwQO;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwShareDataService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwShareService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwTsZjkService;
import cn.gtmap.realestate.exchange.service.national.BdcExchangeZddzService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-06-26
 * @description 一张网相关服务
 */
@Service
@Validated
@ConfigurationProperties(prefix = "yzw")
public class YzwServiceImpl implements YzwService {

    private static final Logger logger = LoggerFactory.getLogger(YzwServiceImpl.class);

    /**
     * 参数对应处理
     */
    private Map<String, Map<String, String>> org = new HashMap();

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    private YzwShareDataService yzwShareDataService;
    @Autowired
    private YzwTsZjkService yzwTsZjkService;
    @Autowired
    private BuildingZdConvertFeignService buildingZdConvertFeignService;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private DjfDjSjMapper djfDjSjMapper;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcZdCache bdcZdCache;
    @Autowired
    private BdcExchangeZddzService bdcExchangeZddzService;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private ECertificateFeignService eCertificateFeignService;
    @Autowired
    private Repo gxRepository;
    @Autowired
    private YzwMapper yzwMapper;
    @Autowired
    Set<YzwShareService> yzwShareServiceSet;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 标准版一张网服务
     */
    @Resource(name = "yzwShareServiceImpl_standard")
    private YzwShareService standardyzwShareServiceImpl;

    @Value("${yzw.bjtszzxx.enable:false}")
    private boolean bjtszzxx;
    @Value("${yzw.bjtswlxx.enable:false}")
    private boolean bjtswlxx;
    @Value("${yzw.jgzzXml.enable:false}")
    private boolean jgzzXml;
    @Value("${yzw.jgzzJson.enable:false}")
    private boolean jgzzJson;
    @Value("${yzw.zzFile.enable:false}")
    private boolean zzFile;
    @Value("${infApply.area.enble:false}")
    private boolean areaInf;
    @Value("${infApply.djxl.enble:false}")
    private boolean djxlInf;
    @Value("${yzw.version:standard}")
    private String version;

    /**
     * 获取预告登记种类的配置
     */
    @Value("#{${yzw.ssqd.uuid}}")
    private Map<Integer, String> ssqduuid;

    @Autowired
    @Qualifier("sjptEntityMapper")
    private EntityMapper sjptEntityMapper;
    @Autowired
    @Qualifier("entityMapper")
    private EntityMapper entityMapper;

    @Autowired
    @Qualifier("gxEntityMapper")
    private EntityMapper gxEntityMapper;

    /**
     * 共享一张网数据
     *
     * @param gzlslid 工作流实例id
     * @param isbj    是否办结
     *                <p>
     *                过程中：
     *                1、更新inf_apply 表bj_statu
     *                2、插入inf_apply_process
     *                3、插入inf_apply_attachment
     *                4、插入inf_apply_jgzz
     *                5、插入inf_apply_wlxx
     *                办结：
     *                1、更新inf_apply 表bj_statu
     *                2、插入inf_apply_process
     *                3、插入inf_apply_result
     *                4、插入inf_apply_attachment
     *                5、插入inf_apply_jgzz
     *                6、插入inf_apply_wlxx
     */
    @Override
    public void shareYzwData(String gzlslid, boolean isbj) {
        if (StringUtils.isBlank(gzlslid)) {
            return;
        }
        //获取项目数据
        List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(listXm)) {

            String dataSource = "2";//1：预审窗口 ，2：部门交换
            List<InfApplyDO> infApplyList = new ArrayList<>();
            List<InfApplyProcessDO> infApplyProcessList = new ArrayList<>();
            List<InfApplyResultDO> infApplyResultList = new ArrayList<>();
            List<InfApplyMaterialDO> infApplyMaterialList = new ArrayList<>();
            List<InfApplyJgzzDO> infApplyJgzzList = new ArrayList<>();
            List<InfApplyWlxxDO> infApplyWlxxList = new ArrayList<>();
            String bh = null;
            //调取平台接口
            ProcessInstanceData processInstance = processInstanceClient.getProcessInstance(gzlslid);
            List<TaskData> list = processTaskClient.listProcessTask(gzlslid);
            Map<String, List<BdcExchangeZddz>> bdcExchangeZddzMap = getBdcExchangeZddzMap();
            List<TaskData> listLstTask = null;
            if (isbj) {
                listLstTask = processTaskClient.processLastTasks(gzlslid);
            }
            for (BdcXmDTO bdcXmDTO : listXm) {
                InfApplyDO infApply = getInfApplyData(processInstance, bdcXmDTO.getXmid(), bdcExchangeZddzMap);
                if (infApply != null) {
                    bh = getYzwbh(bdcXmDTO.getXmid(), infApply);
                    logger.error("推送标准一张网,项目ID:{}一张网编号为：{}", bdcXmDTO.getXmid(), bh);
                    if (StringUtils.isBlank(bh)) {
                        return;
                    }
                    infApplyProcessList.addAll(getInfApplyProcessData(list, bdcXmDTO.getGzlslid(), bh, infApply));
                    infApplyMaterialList.addAll(getInfApplyMaterialData(bdcXmDTO.getXmid(), bdcXmDTO.getGzlslid(), bh, infApply.getNo(), infApply.getOrgId()));
                    if (isbj) {
                        infApplyResultList.addAll(getInfApplyResultData(listLstTask, processInstance, bdcXmDTO.getGzlslid(), bh, infApply));
                    }
                    if (!bjtszzxx || isbj) {
                        infApplyJgzzList.addAll(getInfApplyJgzzData(bdcXmDTO.getXmid(), bdcXmDTO.getGzlslid(), bh, infApply, bdcExchangeZddzMap));
                    }
                    if (!bjtswlxx || isbj) {
                        infApplyWlxxList.addAll(getInfApplyWlxxData(bdcXmDTO.getXmid(), bdcXmDTO.getGzlslid(), bh));
                    }
                    String internal_id = getInternalId(bh);
                    infApply.setInternalId(internal_id);
                    infApply.setInternalNo(bh);
                    infApplyList.add(infApply);
                }
            }
            Example example = new Example(InfApplyDO.class);
            //原来的代码写的参数不确定是否正确  TODO lst
            example.createCriteria().andEqualTo("internalNo", infApplyList.get(0).getNo());
            List<InfApplyDO> infApplyTempList = sjptEntityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(infApplyTempList) && StringUtils.equals(infApplyTempList.get(0).getDataSources(), "1")) {
                dataSource = "1";
                infApplyList = new ArrayList<InfApplyDO>(infApplyTempList);
            }
            if (CollectionUtils.isEmpty(infApplyList)) {
                return;
            }
            for (InfApplyDO infApply : infApplyList) {
                infApply.setBjStatu(isbj ? "C" : "B");
                infApply.setDataSources(dataSource);
                infApply.setApplyWay(StringUtils.equals("2", dataSource) ? "0" : "1");
            }

            //插入process
            //先删后插
            if (CollectionUtils.isNotEmpty(infApplyProcessList)) {
                for (InfApplyProcessDO infApplyProcess : infApplyProcessList) {
                    infApplyProcess.setDataSources(dataSource);
                }
            }

            //插入result
            if (CollectionUtils.isNotEmpty(infApplyResultList)) {
                for (InfApplyResultDO infApplyResult : infApplyResultList) {
                    infApplyResult.setDataSources(dataSource);
                    //如果finish_date为空
                    if (infApplyResult.getFinishDate() == null) {
                        infApplyResult.setFinishDate(new Date());
                    }
                }
            }

            //收件材料
            if (CollectionUtils.isNotEmpty(infApplyMaterialList)) {
                for (InfApplyMaterialDO infApplyMaterial : infApplyMaterialList) {
                    infApplyMaterial.setDataSources(dataSource);
                }
            }

            //物流信息
            if (CollectionUtils.isNotEmpty(infApplyWlxxList)) {
                for (InfApplyWlxxDO infApplyWlxx : infApplyWlxxList) {
                    infApplyWlxx.setDataSources(dataSource);
                }
            }

            //统一入口插入
            yzwShareDataService.delAndInsertInfData(bh, infApplyList, infApplyProcessList, infApplyResultList, infApplyMaterialList, infApplyJgzzList, infApplyWlxxList);
        } else {
            logger.error("推送一张网数据,项目集合为空1");
        }

    }

    /**
     * @param xmid
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取InfApply表
     */
    @Override
    public InfApplyDO getInfApplyData(ProcessInstanceData processInstance, @NotBlank(message = "参数不能为空") String xmid, Map<String, List<BdcExchangeZddz>> map) {
        InfApplyDO infApply = new InfApplyDO();
        infApply.setNo(UUIDGenerator.generate());
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        //查询不动产项目
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            BdcXmDO bdcXmDO = bdcXmList.get(0);
            String regionCode = getXzqdm(bdcXmDO.getQxdm());
            Map orgData = MapUtils.getMap(org, regionCode);
            infApply.setOrgId(MapUtils.getString(orgData, "id"));
            infApply.setOrgName(MapUtils.getString(orgData, "name"));
            infApply.setAreaNo(regionCode);
            if (StringUtils.isNotBlank(regionCode)) {
                SDmXzdmDO sDmXzdmDO = new SDmXzdmDO();
                sDmXzdmDO.setXzdm(regionCode);
                List<SDmXzdmDO> listXzdm = buildingZdConvertFeignService.listXzdm(sDmXzdmDO);
                if (CollectionUtils.isNotEmpty(listXzdm)) {
                    infApply.setAreaName(listXzdm.get(0).getXzmc());
                }
            }
            infApply.setDepartment(MapUtils.getString(orgData, "name"));
            //处理业务编码、权利编码和办件信息、法定办结时限
            setItemIdYwbmAndHandleInfo(infApply, processInstance,bdcXmDO.getDjxl());

            //获取业务数据
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> qlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                qlrList.sort((o1, o2) -> o1.getSxh().compareTo(o2.getSxh()));
                BdcQlrDO bdcQlr = qlrList.get(0);
                String qlrxz = CommonUtil.formatEmptyValue(bdcQlr.getQlrlx());
                //法人
                if (!"1".equals(qlrxz)) {
                    infApply.setApplicantCode(CommonUtil.formatEmptyValue(bdcQlr.getZjh()));
                }
                infApply.setApplicantType(qlrxz);
                //申请人信息
                infApply.setApplicantName(CommonUtil.formatEmptyValue(bdcQlr.getQlrmc()));
                infApply.setApplicantPaperType(CommonUtil.formatEmptyValue(bdcQlr.getZjzl()));
                infApply.setApplicantPaperCode(CommonUtil.formatEmptyValue(bdcQlr.getZjh()));

                infApply.setApplicantPhone(StringUtils.isBlank(CommonUtil.formatEmptyValue(bdcQlr.getDh()))?CommonConstantUtils.YZW_DHMRZ:CommonUtil.formatEmptyValue(bdcQlr.getDh()));
                infApply.setApplicantMobile(StringUtils.isBlank(CommonUtil.formatEmptyValue(bdcQlr.getDh()))?CommonConstantUtils.YZW_DHMRZ:CommonUtil.formatEmptyValue(bdcQlr.getDh()));

                infApply.setApplicantAddress(CommonUtil.formatEmptyValue(bdcQlr.getTxdz()));
                infApply.setApplicantZipcode(CommonUtil.formatEmptyValue(bdcQlr.getYb()));
                //法定代表人
                infApply.setLinkman(CommonUtil.formatEmptyValue(bdcQlr.getFrmc()));
                infApply.setLinkmanzjhm(CommonUtil.formatEmptyValue(bdcQlr.getFrzjh()));
                infApply.setLinkmantel(CommonUtil.formatEmptyValue(bdcQlr.getFrdh()));
                //代理人、联系人
                if (StringUtils.isNotBlank(bdcQlr.getDlrmc())) {
                    infApply.setLinkmanName(CommonUtil.formatEmptyValue(bdcQlr.getDlrmc()));
                    infApply.setLinkmanPaperType(CommonUtil.formatEmptyValue(bdcQlr.getDlrzjzl()));
                    infApply.setLinkmanPaperCode(CommonUtil.formatEmptyValue(bdcQlr.getDlrzjh()));
                    infApply.setLinkmanPhone(StringUtils.isBlank(CommonUtil.formatEmptyValue(bdcQlr.getDlrdh()))?CommonConstantUtils.YZW_DHMRZ:CommonUtil.formatEmptyValue(bdcQlr.getDlrdh()));
                    infApply.setLinkmanMobile(StringUtils.isBlank(CommonUtil.formatEmptyValue(bdcQlr.getDlrdh()))?CommonConstantUtils.YZW_DHMRZ:CommonUtil.formatEmptyValue(bdcQlr.getDlrdh()));
                } else {
                    infApply.setLinkmanName(CommonUtil.formatEmptyValue(bdcQlr.getQlrmc()));
                    infApply.setLinkmanPaperType(CommonUtil.formatEmptyValue(bdcQlr.getZjzl()));
                    infApply.setLinkmanPaperCode(CommonUtil.formatEmptyValue(bdcQlr.getZjh()));

                    infApply.setLinkmanPhone(StringUtils.isBlank(CommonUtil.formatEmptyValue(bdcQlr.getDh()))?CommonConstantUtils.YZW_DHMRZ:CommonUtil.formatEmptyValue(bdcQlr.getDh()));
                    infApply.setLinkmanMobile(StringUtils.isBlank(CommonUtil.formatEmptyValue(bdcQlr.getDh()))?CommonConstantUtils.YZW_DHMRZ:CommonUtil.formatEmptyValue(bdcQlr.getDh()));

                    infApply.setLinkmanAddress(CommonUtil.formatEmptyValue(bdcQlr.getTxdz()));
                    infApply.setLinkmanZipcode(CommonUtil.formatEmptyValue(bdcQlr.getYb()));
                }
            }
            infApply.setIsrisk(0);
            infApply.setApplyDate(bdcXmDO.getSlsj());
            infApply.setCreateDate(bdcXmDO.getSlsj());
            infApply.setWapplyDate(bdcXmDO.getSlsj());
            infApply.setUpdateDate(new Date());

            infApply.setIfUrgent("false");
            infApply.setBjStatu("Y");
            if (MapUtils.isNotEmpty(map)) {
                bdcExchangeZddzService.doObj(infApply, map, null);
            }
        }
        return infApply;
    }


    /**
     * @param infApplyDO
     * @param gzlslid
     * @param bh
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取流程过程数据插入到InfApplyProcess表
     */
    @Override
    public List<InfApplyProcessDO> getInfApplyProcessData(List<TaskData> list, String gzlslid, String bh, InfApplyDO infApplyDO) {
        //获取流程过程数据
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }
        List<InfApplyProcessDO> infApplyProcessList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                TaskData taskData = list.get(i);
                InfApplyProcessDO infApplyProcess = new InfApplyProcessDO();
                infApplyProcess.setNo(UUIDGenerator.generate());
                infApplyProcess.setNoOrd(i + 1);
                infApplyProcess.setOrgId(infApplyDO.getOrgId());
                Integer promise = null;
                infApplyProcess.setItemId(infApplyDO.getDeptQlRegNo());
                infApplyProcess.setTacheName(taskData.getTaskName());
                infApplyProcess.setDepartment(infApplyDO.getDepartment());
                //获取用户信息
                UserDto userDto = getDepartment(taskData.getTaskAssigin());
                if (userDto != null) {
                    infApplyProcess.setUserName(userDto.getAlias());

                    infApplyProcess.setUserStaffCode(userDto.getJobNumber());
                }
                if (CommonConstantUtils.TASK_THZT_BTH.equals(taskData.getBackStatus())) {
                    infApplyProcess.setEventname("2");
                } else {
                    infApplyProcess.setEventname("1");
                }
                infApplyProcess.setStatus("5");
                infApplyProcess.setPromise(taskData.getTaskDueDays() != null ? taskData.getTaskDueDays() : promise);
                infApplyProcess.setPromiseType("1");
                infApplyProcess.setIsrisk(0);
                infApplyProcess.setNote("同意");
                infApplyProcess.setProcessDate(taskData.getEndTime() != null ? taskData.getEndTime() : new Date());
                infApplyProcess.setCreateDate(taskData.getStartTime());
                infApplyProcess.setUpdateDate(new Date());

                infApplyProcess.setEventCode("B9");
                infApplyProcess.setEvent_name(taskData.getTaskName());
                infApplyProcess.setStartTime(taskData.getStartTime());
                infApplyProcess.setEventTime(taskData.getTaskDueDays() != null ? taskData.getTaskDueDays() : promise);
                infApplyProcess.setEventTimeType("1");
                //流程工作流事件推送一张网,流程节点结束时间没有值,以当前时间为准
                infApplyProcess.setEndTime(taskData.getEndTime() != null ? taskData.getEndTime() : new Date());
                infApplyProcess.setInternalNo(bh);
                infApplyProcessList.add(infApplyProcess);
            }
        }
        return infApplyProcessList;
    }

    /**
     * @param bh
     * @param no
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取收件数据插入到InfApplyMaterial表
     */
    @Override
    public List<InfApplyMaterialDO> getInfApplyMaterialData(String xmid, String gzlslid, String bh, String no, String djjgdm) {
        List<InfApplyMaterialDO> infApplyMaterialList = new ArrayList<>();
        Map param = new HashMap();
        param.put("ywh", xmid);
        List<DjfDjSjDO> djfDjSjList = djfDjSjMapper.queryDjfDjSjList(param);
        if (CollectionUtils.isNotEmpty(djfDjSjList)) {
            int i = 1;
            for (DjfDjSjDO djfDjSj : djfDjSjList) {
                InfApplyMaterialDO infApplyMaterial = new InfApplyMaterialDO();
                infApplyMaterial.setNo(UUIDGenerator.generate());
                infApplyMaterial.setOrgId(djjgdm);
                infApplyMaterial.setSjFileName(djfDjSj.getSjmc());
                infApplyMaterial.setSjTaketype("1");
                infApplyMaterial.setSjTaketime(djfDjSj.getSjsj());
                if (null != djfDjSj.getSjsl()) {
                    infApplyMaterial.setSjNo(Integer.valueOf(djfDjSj.getSjsl()));
                }
                infApplyMaterial.setSjFileIf("true");
                infApplyMaterial.setSjFileStatu("true");
                infApplyMaterial.setIfEcPage("false");
                infApplyMaterial.setOrd(String.valueOf(i));
                i++;
                infApplyMaterial.setUpDate(new Date());
                infApplyMaterial.setInternalNo(bh);
                infApplyMaterial.setAttafkonlyno(no);
                infApplyMaterialList.add(infApplyMaterial);
            }
        }
        return infApplyMaterialList;
    }

    /**
     * @param gzlslid
     * @param bh
     * @param infApplyDO
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取流程结果数据插入到InfApplyResult表
     */
    @Override
    public List<InfApplyResultDO> getInfApplyResultData(List<TaskData> listLstTask, ProcessInstanceData processInstance, String gzlslid, String bh, InfApplyDO infApplyDO) {
        //获取流程结果数据
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }
        List<InfApplyResultDO> infApplyResultsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(listLstTask)) {
            logger.info("获取流程结果数据,listLstTask:{}",listLstTask.size());
            TaskData taskData = listLstTask.get(0);
            InfApplyResultDO infApplyResult = new InfApplyResultDO();
            infApplyResult.setNo(UUIDGenerator.generate());
            //权利编码
            infApplyResult.setItemId(infApplyDO.getDeptQlRegNo());
            infApplyResult.setOrgId(infApplyDO.getOrgId());
            //获取用户信息
            UserDto userDto = getDepartment(taskData.getTaskAssigin());
            if (userDto != null) {
                infApplyResult.setUserName(userDto.getAlias());
            } else {
                logger.info("一张网编号：{},用户名：{}未获取到用户姓名", bh, taskData.getTaskAssigin());
            }
            infApplyResult.setStatus("A");
            infApplyResult.setNote("同意办理");
            infApplyResult.setAttachment("无");
            //yy 办件时间和录入时间取流程创建时间和结束时间
            if (processInstance != null) {
                infApplyResult.setCreateDate(processInstance.getStartTime());
            }
            //流程工作流事件推送一张网,流程节点结束时间没有值,以当前时间为准
            infApplyResult.setFinishDate(taskData.getEndTime() != null ? taskData.getEndTime() : new Date());
            infApplyResult.setResultDate(taskData.getEndTime() != null ? taskData.getEndTime() : new Date());
            infApplyResult.setUpdateDate(new Date());
            infApplyResult.setInternalNo(bh);
            infApplyResultsList.add(infApplyResult);
        }
        return infApplyResultsList;
    }

    /**
     * @param xmid
     * @param gzlslid
     * @param bh
     * @param infApplyDO
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取结果证照信息
     */
    @Override
    public List<InfApplyJgzzDO> getInfApplyJgzzData(String xmid, String gzlslid, String bh, InfApplyDO infApplyDO, Map<String, List<BdcExchangeZddz>> map) {
        //获取流程结果数据
        List<InfApplyJgzzDO> infApplyJgzzList = new ArrayList<>();
        //通过项目id获取证书
        List<BdcZsDO> listZs = bdcZsFeignService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isNotEmpty(listZs)) {
            for (BdcZsDO bdcZsDO : listZs) {
                InfApplyJgzzDO infApplyJgzz = new InfApplyJgzzDO();
                infApplyJgzz.setNo(UUIDGenerator.generate());
                if (MapUtils.isNotEmpty(ssqduuid) && ssqduuid.containsKey(bdcZsDO.getZslx())) {
                    infApplyJgzz.setMouldId(ssqduuid.get(bdcZsDO.getZslx()));//证照
                }
                infApplyJgzz.setDzzzNo(infApplyDO.getApplicantPaperCode() + "-" + bdcZsDO.getBdcqzh());
                infApplyJgzz.setZzFile(getZzfile(gzlslid, bdcZsDO.getBdcqzh()));
                infApplyJgzz.setZzBh(bdcZsDO.getBdcqzh());
                infApplyJgzz.setUserNo(infApplyDO.getApplicantPaperCode());
                infApplyJgzz.setBzDate(bdcZsDO.getFzsj());
                infApplyJgzz.setDeptName(infApplyDO.getOrgName());
                infApplyJgzz.setUserName(infApplyDO.getApplicantName());
                infApplyJgzz.setUserType(infApplyDO.getApplicantType());
                infApplyJgzz.setUserPaperType(infApplyDO.getApplicantPaperType());
                infApplyJgzz.setZzType("A");
                infApplyJgzz.setXkZl("设立");
                infApplyJgzz.setXkSplb("登记");
                infApplyJgzz.setCreateDate(new Date());
                infApplyJgzz.setState("A");
                infApplyJgzz.setInternalNo(bh);

                if (jgzzXml) {
                    //xml
                    infApplyJgzz.setIndividuation(getIndividuationXml(infApplyJgzz, bdcZsDO, xmid));
                }
                if (jgzzJson) {
                    //json
                    infApplyJgzz.setCertInfo(getCertInfo(bdcZsDO));
                }
                if (MapUtils.isNotEmpty(map)) {
                    //字典转换
                    bdcExchangeZddzService.doObj(infApplyJgzz, map, "");
                }
                infApplyJgzzList.add(infApplyJgzz);
            }
        }
        return infApplyJgzzList;
    }

    /**
     * @param xmid
     * @param gzlslid
     * @param bh
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取EMS物流信息
     */
    @Override
    public List<InfApplyWlxxDO> getInfApplyWlxxData(String xmid, String gzlslid, String bh) {
        //获取流程结果数据
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }
        //TODO 查询物流信息
        List<InfApplyWlxxDO> infApplyWlxxList = new ArrayList<>();
        return infApplyWlxxList;
    }


    @Override
    public <T> List<T> listGxYzwDataByYzwbh(String yzwbh, Class clazz, String yzwbhname) {
        if (StringUtils.isNotBlank(yzwbh)) {
            Example example = new Example(clazz);
            example.createCriteria().andEqualTo(yzwbhname, yzwbh);
            return gxEntityMapper.selectByExampleNotNull(example);
        }
        return null;

    }

    @Override
    public Page<Map> listYzwTsxxByPageJson(Pageable pageable, GxYzwQO gxYzwQO) {
        gxYzwQO.setNowTime(DateUtil.formateTime(new Date()));
        return gxRepository.selectPaging("listYzwTsxxByPage", gxYzwQO, pageable);

    }

    @Override
    public List<Map> listYzwTsxx(GxYzwQO gxYzwQO) {
        gxYzwQO.setNowTime(DateUtil.formateTime(new Date()));
        Integer num = gxRepository.selectOne("listYzwTsxxCount", gxYzwQO);
        if (num > 10000) {
            throw new AppException("查询数据量过大,请增加查询条件!");
        }
        return gxRepository.selectList("listYzwTsxxByPage", gxYzwQO);


    }

    /**
     * @param gxYzwQO
     * @return 统计信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @Override
    public List<Map> tjTszt(GxYzwQO gxYzwQO) {
        return gxRepository.selectList("tjTszt", gxYzwQO);
    }

    @Override
    public void saveYzwCheckAndTsResultDTO(List<YzwCheckAndTsResultDTO> yzwCheckAndTsResultDTOS, boolean isDelete) {
        List<GxYzwTsjlDO> gxYzwTsjlDOList = new ArrayList<>();
        List<GxYzwYzsbxxDO> gxYzwYzsbxxDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(yzwCheckAndTsResultDTOS)) {
            for (YzwCheckAndTsResultDTO yzwCheckAndTsResultDTO : yzwCheckAndTsResultDTOS) {
                //推送信息
                GxYzwTsjlDO gxYzwTsjlDO = new GxYzwTsjlDO();
                BeanUtils.copyProperties(yzwCheckAndTsResultDTO, gxYzwTsjlDO);
                gxYzwTsjlDO.setTsxxid(UUIDGenerator.generate16());
                UserDto userDto = userManagerUtils.getCurrentUser();
                if (userDto != null) {
                    gxYzwTsjlDO.setTsrxm(userDto.getAlias());
                }
                gxYzwTsjlDOList.add(gxYzwTsjlDO);
                if (CollectionUtils.isNotEmpty(yzwCheckAndTsResultDTO.getGxYzwYzsbxxDOList())) {
                    //验证信息
                    for (GxYzwYzsbxxDO gxYzwYzsbxxDO : yzwCheckAndTsResultDTO.getGxYzwYzsbxxDOList()) {
                        gxYzwYzsbxxDO.setYzxxid(UUIDGenerator.generate16());
                        gxYzwYzsbxxDO.setTsxxid(gxYzwTsjlDO.getTsxxid());
                    }
                    gxYzwYzsbxxDOList.addAll(yzwCheckAndTsResultDTO.getGxYzwYzsbxxDOList());

                }
                if (isDelete) {
                    List<GxYzwTsjlDO> gxYzwTsjlDOS = listGxYzwDataByYzwbh(yzwCheckAndTsResultDTO.getYzwbh(), GxYzwTsjlDO.class, "yzwbh");
                    if (CollectionUtils.isNotEmpty(gxYzwTsjlDOS)) {
                        gxYzwTsjlDO.setYwid(gxYzwTsjlDOS.get(0).getYwid());
                        gxYzwTsjlDO.setYwslbh(gxYzwTsjlDOS.get(0).getYwslbh());
                        gxYzwTsjlDO.setJdmc(gxYzwTsjlDOS.get(0).getJdmc());
                        gxYzwTsjlDO.setTssj(gxYzwTsjlDOS.get(0).getTssj());
                        for (GxYzwTsjlDO yyzwTsjlDO : gxYzwTsjlDOS) {
                            Example example = new Example(GxYzwYzsbxxDO.class);
                            example.createCriteria().andEqualTo("tsxxid", yyzwTsjlDO.getTsxxid());
                            gxEntityMapper.deleteByExample(example);
                            gxEntityMapper.deleteByPrimaryKey(GxYzwTsjlDO.class, yyzwTsjlDO.getTsxxid());
                        }

                    }
                }
            }
            if (CollectionUtils.isNotEmpty(gxYzwTsjlDOList)) {
                gxEntityMapper.insertBatchSelective(gxYzwTsjlDOList);
            }
            if (CollectionUtils.isNotEmpty(gxYzwYzsbxxDOList)) {
                gxEntityMapper.insertBatchSelective(gxYzwYzsbxxDOList);
            }
        }

    }

    @Override
    public List<Map> listGxYzwYzmxxxByTsxxid(String tsxxid) {
        return yzwMapper.listGxYzwYzmxxxByTsxxid(tsxxid);
    }

    /**
     * 删除时，推送inf_apply_result表
     *
     * @param processInsId@Date 2021/2/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public void deleteTsResult(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            return;
        }
        //获取项目数据
        logger.info("删除时，推送inf_apply_result表,查询项目表数据，gzlslid：{}",processInsId);
        List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        logger.info("查询到项目数：{}",listXm.size());
        if (CollectionUtils.isNotEmpty(listXm)) {
            String dataSource = "2";//1：预审窗口 ，2：部门交换

            List<InfApplyDO> infApplyList = new ArrayList<>();
            List<InfApplyProcessDO> infApplyProcessList = new ArrayList<>();
            List<InfApplyResultDO> infApplyResultList = new ArrayList<>();
            List<InfApplyMaterialDO> infApplyMaterialList = new ArrayList<>();
            List<InfApplyJgzzDO> infApplyJgzzList = new ArrayList<>();
            List<InfApplyWlxxDO> infApplyWlxxList = new ArrayList<>();
            String bh = null;
            //调取平台接口
            ProcessInstanceData processInstance = processInstanceClient.getProcessInstance(processInsId);
            List<TaskData> list = processTaskClient.listProcessTask(processInsId);
            Map<String, List<BdcExchangeZddz>> bdcExchangeZddzMap = getBdcExchangeZddzMap();
            List<TaskData> listLstTask  = processTaskClient.processLastTasks(processInsId);;
            for (BdcXmDTO bdcXmDTO : listXm) {
                logger.info("开始查询infapply");
                InfApplyDO infApply = getInfApplyData(processInstance, bdcXmDTO.getXmid(), bdcExchangeZddzMap);
                logger.info("查询完infapply数据，开始删除推送");
                if (infApply != null) {
                    bh = getYzwbh(bdcXmDTO.getXmid(), infApply);
                    logger.info("推送result信息，已删除的小米的：{},删除一体化编号为：{}", bdcXmDTO.getXmid(), bh);

                    logger.error("删除推送inf_apply_result表，xmid:{},bh:{}", bdcXmDTO.getXmid(), bh);
                    if (StringUtils.isBlank(bh)) {
                        return;
                    }
                    List<InfApplyResultDO> infApplyResultDOS = getInfApplyResultData(listLstTask, processInstance, bdcXmDTO.getGzlslid(), bh, infApply);
                    for (InfApplyResultDO infResult : infApplyResultDOS) {
                        infResult.setStatus("A");
                        infResult.setNote("已删除");
                    }
                    logger.info("删除时推送result表数量为{}", infApplyResultDOS.size());
                    infApplyResultList.addAll(infApplyResultDOS);

                    String internal_id = getInternalId(bh);
                    infApply.setInternalId(internal_id);
                    infApply.setInternalNo(bh);
                    infApplyList.add(infApply);
                }
            }
            Example example = new Example(InfApplyDO.class);
            //原来的代码写的参数不确定是否正确  TODO lst
            example.createCriteria().andEqualTo("internalNo", infApplyList.get(0).getNo());
            List<InfApplyDO> infApplyTempList = sjptEntityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(infApplyTempList) && StringUtils.equals(infApplyTempList.get(0).getDataSources(), "1")) {
                dataSource = "1";
                infApplyList = new ArrayList<InfApplyDO>(infApplyTempList);
            }
            if (CollectionUtils.isEmpty(infApplyList)) {
                return;
            }
            //插入result
            if (CollectionUtils.isNotEmpty(infApplyResultList)) {
                for (InfApplyResultDO infApplyResult : infApplyResultList) {
                    infApplyResult.setDataSources(dataSource);
                    //如果finish_date为空
                    if (infApplyResult.getFinishDate() == null) {
                        infApplyResult.setFinishDate(new Date());
                    }
                }
            }
            logger.info("删除时推送result表,开始插入数量{}", infApplyResultList.size());
            yzwShareDataService.delInsertInfResultData(infApplyList, infApplyResultList);
            logger.info("删除时推送result表,插入结束数量{}", infApplyResultList.size());

            //TO DO
            yzwTsZjkService.tszjkEntrance(processInsId);
            logger.info("推送result表之后，推送中间库结束");
        } else {
            logger.error("restlt推送一张网数据,项目集合为空");
        }

    }


    //处理业务编码、权利编码和办件信息、法定办结时限
    private void setItemIdYwbmAndHandleInfo(InfApplyDO infApply, ProcessInstanceData processInstance,String djxl) {
        if (processInstance != null && StringUtils.isNotBlank(processInstance.getProcessDefinitionKey())) {
            Example example = new Example(BdcXtQtdjYwDO.class);
            Example.Criteria criteria = example.createCriteria();
            if (areaInf) {
                criteria.andEqualTo("xzdm", infApply.getAreaNo());
            }
            if(djxlInf){
                criteria.andEqualTo("djxl", djxl);
            }
            criteria.andEqualTo("gzldyid", processInstance.getProcessDefinitionKey());
            List<BdcXtQtdjYwDO> listQtdjYw = entityMapper.selectByExample(example);
            Integer promise = null;
            if (CollectionUtils.isNotEmpty(listQtdjYw)) {
                BdcXtQtdjYwDO bdcXtQtdjYwDO = listQtdjYw.get(0);
                //权利编码
                infApply.setItemId(bdcXtQtdjYwDO.getQlbm());
                //业务编码 和 权利编码
                infApply.setDeptQlRegNo(bdcXtQtdjYwDO.getQlbm());
                infApply.setDeptQlName(bdcXtQtdjYwDO.getQlmc());
                infApply.setDeptYwRegNo(bdcXtQtdjYwDO.getYwbm());
                infApply.setDeptYwName(bdcXtQtdjYwDO.getYwmc());
                infApply.setCatalogCode(bdcXtQtdjYwDO.getMlbm());
                infApply.setTaskVersion(bdcXtQtdjYwDO.getSsbbh());
//                infApply.set
                promise = bdcXtQtdjYwDO.getFdbjsx() != null ? bdcXtQtdjYwDO.getFdbjsx() : 0;
            }

            //承诺时限
            infApply.setPromise(promise);
            infApply.setPromiseType("1");
            //法定办结时间
            infApply.setAnticipate(promise);
            infApply.setAnticipateDayType("1");
            //办件名称
            infApply.setTransactAffairName(processInstance.getProcessDefinitionName());
            infApply.setContent("无");
            infApply.setForm(processInstance.getName());
            infApply.setStuff("");
        }
    }

    /**
     * @param xmid 不动产项目id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取一张网编号
     */
    public String getYzwbh(String xmid, InfApplyDO param) {
        String yzwbh = "";
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (bdcXmDO != null) {
                if (StringUtils.isNotBlank(bdcXmDO.getZfxzspbh())) {
                    yzwbh = bdcXmDO.getZfxzspbh();
                } else {
                    YzwShareService yzwShareService = InterfaceCodeBeanFactory.getBean(yzwShareServiceSet, version);
                    if (null != yzwShareService) {
                        yzwbh = yzwShareService.makeCaseNo(param);
                    } else {
                        yzwbh = standardyzwShareServiceImpl.makeCaseNo(param);
                    }
                    bdcXmDO.setZfxzspbh(yzwbh);
                    entityMapper.updateByPrimaryKeySelective(bdcXmDO);
                }
            }
        }
        return yzwbh;
    }

    /**
     * @param qxdm
     * @return
     * @author <a href="mailto:lisongyao@gtmap.cn">lisongyao</a>
     * @description 处理业务信息表行政区代码格式
     */
    private String getXzqdm(String qxdm) {
        if (StringUtils.isNotBlank(qxdm)) {
            String xzdm;
            /**
             * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
             * @description 行政区代码不足6位后面补0，超过6位截取前6位
             */
            if (qxdm.length() < 6) {
                xzdm = String.format("%-6s", qxdm).replace(" ", "0");
            } else {
                xzdm = StringUtils.substring(qxdm, 0, 6);
            }
            return xzdm;
        }
        return null;
    }

    /**
     * @param internal_no 一张网办件编号
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据编号获取一张网办件body（苏州特殊需求：需要保持internal_id不变）
     */
    private String getInternalId(String internal_no) {
        String str = "";
        if (StringUtils.isNotBlank(internal_no)) {
            Example infApplyExample = new Example(InfApplyDO.class);
            infApplyExample.createCriteria().andEqualTo("internalNo", internal_no);
            List<InfApplyDO> infApplyList = sjptEntityMapper.selectByExampleNotNull(infApplyExample);
            if (CollectionUtils.isNotEmpty(infApplyList)) {
                str = infApplyList.get(0).getInternalId();
            }
        }
        return str;
    }


    /**
     * @param gzlslid 工作流实例id
     * @param bdcqzh  不动产权证号
     * @return
     * @description 获取证照扫描件地址
     */
    private String getZzfile(String gzlslid, String bdcqzh) {
        StringBuilder sb = new StringBuilder();
        if (zzFile && StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(bdcqzh)) {
            sb.append(eCertificateFeignService.getECertificateFilePath(gzlslid, bdcqzh));
        }
        if (StringUtils.isBlank(sb.toString())) {
            sb.append("空");
        }
        return sb.toString();
    }

    /**
     * @param infApplyJgzz 结果证照信息
     * @param bdcZsDO      登记证书信息
     * @param xmid         项目id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 组织结果证照所需的xml
     */
    private String getIndividuationXml(InfApplyJgzzDO infApplyJgzz, BdcZsDO bdcZsDO, String xmid) {
        String xml = "";
        if (infApplyJgzz != null && bdcZsDO != null && StringUtils.isNotBlank(xmid)) {
            LicenseModel licenseModel = new LicenseModel();
            BasicModel basicModel = new BasicModel();
            FaceModel faceModel = new FaceModel();
            //Basic组织
            List<FieldModel> basicFieldModelList = new ArrayList<>();
            FieldModel fieldModel = new FieldModel();
            fieldModel.setCName("电子证照编号");
            fieldModel.setValue(StringValue(infApplyJgzz.getDzzzNo()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("证照名称");
            //转换字典项
            fieldModel.setValue(StringValue(bdcZsDO.getZslx()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("证照编号");
            fieldModel.setValue(StringValue(bdcZsDO.getBdcqzh()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("颁证时间");
            if (bdcZsDO.getFzsj() != null) {
                fieldModel.setValue(DateUtil.formateTimeYmd(bdcZsDO.getFzsj()));
            } else {
                fieldModel.setValue("");
            }
            basicFieldModelList.add(fieldModel);

            Map<String, String> map = getKsJssj(xmid);
            fieldModel = new FieldModel();
            fieldModel.setCName("有效期（起始）");
            fieldModel.setValue(map.get("kssj"));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("有效期（截止）");
            fieldModel.setValue(map.get("jssj"));
            basicFieldModelList.add(fieldModel);
            //获取开始和结束时间
            fieldModel = new FieldModel();
            fieldModel.setCName("持证者");
            fieldModel.setValue(StringValue(infApplyJgzz.getUserName()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("持证者类型");
            fieldModel.setValue(StringValue(infApplyJgzz.getUserType()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("持证者证件类型");
            fieldModel.setValue(StringValue(infApplyJgzz.getUserPaperType()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("持证者证件号码");
            fieldModel.setValue(StringValue(infApplyJgzz.getUserNo()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("颁证单位");
            fieldModel.setValue(StringValue(infApplyJgzz.getDeptName()));
            basicFieldModelList.add(fieldModel);
            fieldModel = new FieldModel();
            fieldModel.setCName("证照分类");
            fieldModel.setValue("A");
            basicFieldModelList.add(fieldModel);
            basicModel.setFieldModelList(basicFieldModelList);

            //Face组织
            List<FieldModel> faceFieldModelList;
            if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx())) {
                faceFieldModelList = zmFaceFieldModelList(bdcZsDO);
            } else {
                faceFieldModelList = zsFaceFieldModelList(bdcZsDO, map);
            }
            faceModel.setFieldModelList(faceFieldModelList);

            licenseModel.setBasicModel(basicModel);
            licenseModel.setFaceModel(faceModel);
            XmlEntityConvertUtil xmlEntityConvertUtil = new XmlEntityConvertUtil();
            xml = xmlEntityConvertUtil.entityToXml(licenseModel);
            xml = xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        }
        return xml;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取证书的<face></face>
     */
    private List<FieldModel> zsFaceFieldModelList(BdcZsDO bdcZsDO, Map<String, String> map) {
        if (bdcZsDO != null) {
            //Face组织
            List<FieldModel> faceFieldModelList = new ArrayList<>();
            FieldModel faceFieldModel = new FieldModel();
            faceFieldModel.setCName("不动产权证编号");
            faceFieldModel.setValue(StringValue(bdcZsDO.getBdcqzh()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("权利人");
            faceFieldModel.setValue(StringValue(bdcZsDO.getQlr()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("共有情况");
            faceFieldModel.setValue(StringValue(bdcZdCache.getFeildValue(BdcZdGyfsDO.class.getSimpleName(), bdcZsDO.getGyfs(), BdcZdGyfsDO.class)));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("坐落");
            faceFieldModel.setValue(StringValue(bdcZsDO.getZl()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("不动产单元号");
            faceFieldModel.setValue(StringValue(bdcZsDO.getBdcdyh()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("权利类型");
            faceFieldModel.setValue(StringValue(bdcZdCache.getFeildValue(BdcZdQllxDO.class.getSimpleName(), bdcZsDO.getQllx(), BdcZdQllxDO.class)));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("权利性质");
            faceFieldModel.setValue(StringValue(bdcZsDO.getQlxz()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("用途");
            faceFieldModel.setValue(StringValue(bdcZsDO.getYt()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("面积");
            faceFieldModel.setValue(StringValue(bdcZsDO.getMj()));
            faceFieldModelList.add(faceFieldModel);
            //获取开始和结束时间
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("使用期限起");
            faceFieldModel.setValue(map.get("kssj"));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("使用期限止");
            faceFieldModel.setValue(map.get("jssj"));
            faceFieldModelList.add(faceFieldModel);

            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("权利其他状况");
            faceFieldModel.setValue(StringValue(bdcZsDO.getQlqtzk()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("附记");
            faceFieldModel.setValue(StringValue(bdcZsDO.getFj()));
            faceFieldModelList.add(faceFieldModel);
            return faceFieldModelList;
        }
        return Collections.emptyList();
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取证明的<face></face>
     */
    private List<FieldModel> zmFaceFieldModelList(BdcZsDO bdcZsDO) {
        if (bdcZsDO != null) {
            //Face组织
            List<FieldModel> faceFieldModelList = new ArrayList<>();
            FieldModel faceFieldModel = new FieldModel();
            faceFieldModel.setCName("不动产权证编号");
            faceFieldModel.setValue(StringValue(bdcZsDO.getBdcqzh()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("证明权利或事项");
            faceFieldModel.setValue(StringValue(bdcZsDO.getZmqlsx()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("权利人(申请人)");
            faceFieldModel.setValue(StringValue(bdcZsDO.getQlr()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("义务人");
            faceFieldModel.setValue(StringValue(bdcZsDO.getYwr()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("坐落");
            faceFieldModel.setValue(StringValue(bdcZsDO.getZl()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("不动产单元号");
            faceFieldModel.setValue(StringValue(bdcZsDO.getBdcdyh()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("其他");
            faceFieldModel.setValue(StringValue(bdcZsDO.getQlqtzk()));
            faceFieldModelList.add(faceFieldModel);
            faceFieldModel = new FieldModel();
            faceFieldModel.setCName("附记");
            faceFieldModel.setValue(StringValue(bdcZsDO.getFj()));
            faceFieldModelList.add(faceFieldModel);
            return faceFieldModelList;
        }
        return Collections.emptyList();
    }


    /**
     * @param bdcZsDO
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 生成结果证照所需json
     */
    private String getCertInfo(BdcZsDO bdcZsDO) {
        JSON json = null;
        Map tmp = new HashMap();
        Map tmpChild = new HashMap();
        tmpChild.put("BDCQZH", StringValue(bdcZsDO.getBdcqzh()));
        tmpChild.put("QLR", StringValue(bdcZsDO.getQlr()));
        tmpChild.put("GYQK", StringValue(bdcZdCache.getFeildValue(BdcZdGyfsDO.class.getSimpleName(), bdcZsDO.getGyfs(), BdcZdGyfsDO.class)));
        tmpChild.put("ZL", StringValue(bdcZsDO.getZl()));
        tmpChild.put("BDCDYH", StringValue(bdcZsDO.getBdcdyh()));
        tmpChild.put("QLLX", StringValue(bdcZdCache.getFeildValue(BdcZdQllxDO.class.getSimpleName(), bdcZsDO.getQllx(), BdcZdQllxDO.class)));
        tmpChild.put("QLXZ", StringValue(bdcZsDO.getQlxz()));
        tmpChild.put("YT", StringValue(bdcZsDO.getYt()));
        tmpChild.put("MJ", StringValue(bdcZsDO.getMj()));
        tmpChild.put("SYQX", StringValue(bdcZsDO.getSyqx()));
        tmpChild.put("QLQTZK", StringValue(bdcZsDO.getQlqtzk()));
        tmpChild.put("FJ", StringValue(bdcZsDO.getFj()));
        tmp.put("CERT_INFO", StringValue(tmpChild));
        json = (JSON) JSON.toJSON(tmp);
        if (json != null) {
            return json.toJSONString();
        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param name
     * @return
     */
    private UserDto getDepartment(String name) {
        UserDto userDto = null;
        if (StringUtils.isNotBlank(name)) {
            userDto = userManagerUtils.getUserByName(name);
        }
        return userDto;
    }

    /**
     * 获取开始时间和结束时间
     *
     * @param xmid
     * @return
     */
    private Map<String, String> getKsJssj(String xmid) {
        Map<String, String> map = new HashMap<>();
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        Date kssj = null;
        Date jssj = null;
        if (bdcQl instanceof BdcFdcqDO) {
            kssj = ((BdcFdcqDO) bdcQl).getTdsyqssj();
            jssj = ((BdcFdcqDO) bdcQl).getTdsyjssj();
        } else if (bdcQl instanceof BdcDyaqDO) {
            kssj = ((BdcDyaqDO) bdcQl).getZwlxqssj();
            jssj = ((BdcDyaqDO) bdcQl).getZwlxjssj();
        } else if (bdcQl instanceof BdcGjzwsyqDO) {
            kssj = ((BdcGjzwsyqDO) bdcQl).getTdhysyqssj();
            jssj = ((BdcGjzwsyqDO) bdcQl).getTdhysyjssj();
        } else if (bdcQl instanceof BdcHysyqDO) {
            kssj = ((BdcHysyqDO) bdcQl).getSyqqssj();
            jssj = ((BdcHysyqDO) bdcQl).getSyqjssj();
        } else if (bdcQl instanceof BdcJsydsyqDO) {
            kssj = ((BdcJsydsyqDO) bdcQl).getSyqqssj();
            jssj = ((BdcJsydsyqDO) bdcQl).getSyqjssj();
        } else if (bdcQl instanceof BdcLqDO) {
            kssj = ((BdcLqDO) bdcQl).getLdsyqssj();
            jssj = ((BdcLqDO) bdcQl).getLdsyjssj();
        } else if (bdcQl instanceof BdcQtxgqlDO) {
            kssj = ((BdcQtxgqlDO) bdcQl).getQlqssj();
            jssj = ((BdcQtxgqlDO) bdcQl).getQljssj();
        } else if (bdcQl instanceof BdcTdcbnydsyqDO) {
            kssj = ((BdcTdcbnydsyqDO) bdcQl).getCbqssj();
            jssj = ((BdcTdcbnydsyqDO) bdcQl).getCbjssj();
        }
        if (kssj != null) {
            map.put("kssj", DateUtil.formateTime(kssj));
        } else {
            map.put("kssj", StringUtils.EMPTY);
        }
        if (jssj != null) {
            map.put("jssj", DateUtil.formateTime(jssj));
        } else {
            map.put("jssj", StringUtils.EMPTY);
        }
        return map;
    }

    /**
     * 获取字符串数据，空的话赋值""
     *
     * @param value
     * @return
     */
    private String StringValue(Object value) {
        String val = StringUtils.EMPTY;
        if (value != null) {
            val = value.toString();
        }
        return val;
    }

    private Map<String, List<BdcExchangeZddz>> getBdcExchangeZddzMap() {
        List<BdcExchangeZddz> zddzList = bdcdjMapper.getBdcExchangeZddzList();
        Map<String, List<BdcExchangeZddz>> bdcExchangeZddzMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(zddzList)) {
            for (BdcExchangeZddz bdcExchangeZddz : zddzList) {
                String field = StringUtils.lowerCase(bdcExchangeZddz.getZdlx());
                if (bdcExchangeZddzMap.get(field) == null) {
                    bdcExchangeZddzMap.put(field, new ArrayList<BdcExchangeZddz>());
                }
                bdcExchangeZddzMap.get(field).add(bdcExchangeZddz);
            }
        }
        return bdcExchangeZddzMap;
    }

    public Map<String, Map<String, String>> getOrg() {
        return org;
    }

    public void setOrg(Map<String, Map<String, String>> org) {
        this.org = org;
    }
}
