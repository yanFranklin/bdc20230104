package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.exchange.WorkFlowRequest;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQjtbxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSwFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.utils.AnnotationsUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.dto.SynchLpbxxDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.core.service.BdcCshFwkgService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.djbxx.InitBdcdjbAbstractService;
import cn.gtmap.realestate.init.service.other.InitDataDealService;
import cn.gtmap.realestate.init.service.other.InitDataService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.FieldMappingMetadata;
import org.dozer.metadata.MappingMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 处理服务返回数据的接口
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/9.
 * @description
 */
@Service
public class InitDataDealServiceImpl implements InitDataDealService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitDataDealServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private InitBeanFactory initBeanFactory;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private InitDataService initDataService;
    @Autowired
    private BdcCshFwkgService bdcCshFwkgService;
    // 各业务系统删除服务类
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSwFeignService bdcSwFeignService;
    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    private BdcGzlwFeignService bdcGzlwFeignService;

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;

    @Autowired
    InitServiceQoService initServiceQoService;

    @Autowired
    BdcBdcdyService bdcBdcdyService;

    @Override
    public InitResultDTO dealServiceDTO(Map<String, InitServiceDTO> serviceDTOMap) throws IllegalAccessException {
        InitResultDTO initResultDTO = new InitResultDTO();
        if (MapUtils.isNotEmpty(serviceDTOMap)) {
            //处理数据
            dealDTO(initResultDTO, serviceDTOMap.values());
        }
        return initResultDTO;
    }

    @Override
    public InitResultDTO dealServiceDTO(List<InitServiceDTO> listDTO) throws IllegalAccessException {
        InitResultDTO initResultDTO = null;
        if (CollectionUtils.isNotEmpty(listDTO)) {
            initResultDTO = new InitResultDTO();
            //处理数据
            dealDTO(initResultDTO, listDTO);
        }
        return initResultDTO;
    }

    private void dealDTO(InitResultDTO initResultDTO, Collection<InitServiceDTO> collection) throws IllegalAccessException {
        //要封装的结构
        Field[] fields = InitResultDTO.class.getDeclaredFields();
        for (Field resultField : fields) {
            resultField.setAccessible(true);
            for (InitServiceDTO initServiceDTO : collection) {
                //每个业务的数据
                Field[] dtoFields = InitServiceDTO.class.getDeclaredFields();
                for (Field dtoField : dtoFields) {
                    dtoField.setAccessible(true);
                    Object val = dtoField.get(initServiceDTO);
                    if (val != null) {
                        List list = (List) resultField.get(initResultDTO);
                        //空值的话进行赋值
                        if (list == null) {
                            resultField.set(initResultDTO, new ArrayList<>());
                        }
                        //比对转换
                        if (dtoField.getType() == List.class) {
                            if (StringUtils.equals(resultField.getGenericType().toString(), dtoField.getGenericType().toString())) {
                                ((List) resultField.get(initResultDTO)).addAll((List) val);
                                break;
                            }
                        } else {
                            //实体对象名有相近的 通过>去判断
                            if (StringUtils.contains(resultField.getGenericType().getTypeName(), dtoField.getType().getName() + ">")) {
                                ((List) resultField.get(initResultDTO)).add(val);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public Boolean dealResultDTO(InitResultDTO initResultDTO, Integer type) throws Exception {
        Boolean success = false;
        if (initResultDTO != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
            //楼盘表单独走删除逻辑
            if (Constants.DATA_TYPE_LPB.equals(type) || Constants.DATA_TYPE_LPB_DZ.equals(type)) {
                int lx = bdcXmService.bdcXmLxByAllXm(initResultDTO.getBdcXmList());
                boolean scplcl = false;
                if (lx == CommonConstantUtils.LCLX_PL) {
                    scplcl = true;
                }
                if (Constants.DATA_TYPE_LPB_DZ.equals(type)) {
                    initResultDTO.setDeleteList(queryDeleteData(initResultDTO.getBdcXmList(), true, true, scplcl, false));
                } else {
                    initResultDTO.setDeleteList(queryDeleteData(initResultDTO.getBdcXmList(), true, false, scplcl, false));
                }
            }
            //先删除
            if (CollectionUtils.isNotEmpty(initResultDTO.getDeleteList())) {
                for (Object obj : initResultDTO.getDeleteList()) {
                    Method method = AnnotationsUtils.getAnnotationsName(obj);
                    String id = null;
                    try {
                        if (method.invoke(obj) != null) {
                            id = method.invoke(obj).toString();
                        }
                    } catch (Exception e) {
                        LOGGER.error(null, e);
                    }
                    entityMapper.deleteByPrimaryKey(obj.getClass(), id);
                    LOGGER.info("数据入库前先执行删除操作，对象class：{}，主键ID：{}", obj.getClass().getName(), id);
                }
                //清空
                initResultDTO.getDeleteList().clear();
            }
            //处理家庭成员
            dealScJtcy(initResultDTO);
            // 处理量化信息数据
            dealJsydLhxx(initResultDTO);
            Field[] fields = InitResultDTO.class.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                String slbh = "";
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object val = field.get(initResultDTO);
                    //判断是否是list集合
                    if (val != null && field.getType() == List.class) {
                        List filedList = (List) val;
                        if (CollectionUtils.isNotEmpty(filedList)) {
                            //存储实体类名为主键的map
                            HashMap<String, List> map = new HashMap<>();
                            List voList;
                            for (int i = 0; i < filedList.size(); i++) {
                                //走修改逻辑的判定处理
                                if (Constants.DATA_TYPE_PT.equals(type)
                                        || (Constants.DATA_TYPE_ZS.equals(type) && filedList.get(i) instanceof BdcQlrDO)) {
                                    Method method1 = AnnotationsUtils.getAnnotationsName(filedList.get(i));
                                    String id = null;
                                    try {
                                        if (method1.invoke(filedList.get(i)) != null) {
                                            id = method1.invoke(filedList.get(i)).toString();
                                        }
                                    } catch (Exception e) {
                                        LOGGER.error(null, e);
                                    }
                                    //没有主键抛出异常
                                    if (StringUtils.isBlank(id)) {
                                        StringBuilder builder = new StringBuilder();
                                        builder.append(messageProvider.getMessage("error.0000"));
                                        builder.append(filedList.get(i).getClass().toString());
                                        throw new AppException(builder.toString());
                                    }
                                    if (entityMapper.selectByPrimaryKey(filedList.get(i).getClass(), id) != null) {
                                        entityMapper.updateByPrimaryKeyNull(filedList.get(i));
                                        continue;
                                    }
                                }
                                if (map.get(filedList.get(i).getClass().getSimpleName()) == null) {
                                    voList = new ArrayList();
                                    map.put(filedList.get(i).getClass().getSimpleName(), voList);
                                } else {
                                    voList = map.get(filedList.get(i).getClass().getSimpleName());
                                }
                                voList.add(filedList.get(i));
                            }
                            if (MapUtils.isNotEmpty(map)) {
                                for (List list : map.values()) {
                                    Set<String> xmids = new HashSet<>();
                                    if (CollectionUtils.isNotEmpty(list)) {
                                        if (list.get(0) instanceof BdcXmDO) {
                                            slbh = ((BdcXmDO) list.get(0)).getSlbh();
                                            for (Object o : list) {
                                                String xmid = ((BdcXmDO) o).getXmid();
                                                if (xmids.contains(xmid)) {
                                                    LOGGER.info("分批前项目ID重复：{}", xmid);
                                                } else {
                                                    xmids.add(xmid);
                                                }
                                            }
                                        }
                                        LOGGER.info("{} 数据入库开始:{} {} {}", list.get(0).getClass().toString(), simpleDateFormat.format(new Date()), list.size(), slbh);
                                        int size = 500;
                                        if (list.get(0) instanceof BdcXmDO || list.get(0) instanceof BdcFdcqDO || list.get(0) instanceof BdcDyaqDO) {
                                            size = 100;
                                        }
                                        List<List> partList = ListUtils.subList(list, size);
                                        Set<String> fpxmids = new HashSet<>();
                                        for (List data : partList) {
                                            if (data.get(0) instanceof BdcXmDO) {
                                                for (Object o : data) {
                                                    String xmid = ((BdcXmDO) o).getXmid();
                                                    if (fpxmids.contains(xmid)) {
                                                        LOGGER.info("分批后项目ID重复：{}", xmid);
                                                    } else {
                                                        fpxmids.add(xmid);
                                                    }
                                                }
                                            }
                                            try {
                                                entityMapper.insertBatchSelective(data);
                                                LOGGER.info("{} 数据入库中:{} {} {} 插入数据量:{}", list.get(0).getClass().toString(), simpleDateFormat.format(new Date()), list.size(), slbh, data.size());
                                            } catch (Exception e) {
                                                if (data.get(0) instanceof BdcXmDO) {
                                                    List<String> xmidList = new ArrayList<>();
                                                    for (Object o : data) {
                                                        xmidList.add(((BdcXmDO) o).getXmid());
                                                    }
                                                    LOGGER.info("主键重复的数据集合：{} {}", JSONObject.toJSONString(xmidList), slbh);
                                                }
                                                throw e;
                                            }
                                        }
                                        LOGGER.info("{} 数据入库结束:{} {} {}", list.get(0).getClass().toString(), simpleDateFormat.format(new Date()), list.size(), slbh);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            success = true;
        }
        return success;
    }

    /**
     * 根据项目id数据删除数据服务
     *
     * @param xmids
     * @return true/false
     */
    @Override
    public Boolean deleteYwsj(String[] xmids) throws Exception {
        Boolean success;
        if (ArrayUtils.isNotEmpty(xmids)) {
            List<BdcXmDO> list = new ArrayList<>();
            for (String xmid : xmids) {
                if (StringUtils.isNotBlank(xmid)) {
                    BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
                    if (bdcXmDO != null) {
                        list.add(bdcXmDO);
                    }
                }
            }
            //通过项目id的删除不做批量处理
            success = initDataService.deleteYwsj(list, false);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return success;
    }

    /**
     * 根据工作流实例ID删除业务数据服务
     *
     * @param gzlslid
     * @return true/false
     */
    @Override
    public Boolean deleteYwsj(String gzlslid) throws Exception {
        Boolean success = false;
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDO> list = bdcXmService.listBdcXm(gzlslid);
            if (CollectionUtils.isNotEmpty(list)) {
                int lx = bdcXmService.bdcXmLxByAllXm(list);
                boolean scplcl = false;
                if (lx == CommonConstantUtils.LCLX_PL) {
                    scplcl = true;
                }
                success = initDataService.deleteYwsj(list, scplcl);
            } else {
                LOGGER.error("删除业务数据时未查询到项目，gzlslid：{}", gzlslid);
            }
        }
        return success;
    }

    /**
     * 根据项目数据获取删除数据
     *
     * @param xmList
     * @param sfzqlpbsj 是否抽取楼盘表数据
     * @param sfzqlpbsj 是否对照楼盘表数据
     * @param scplcl    删除批量处理
     * @param sfdsc     是否只是删除处理
     * @return List<Object>
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> queryDeleteData(List<BdcXmDO> xmList, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean scplcl, Boolean sfdsc) throws Exception {
        //要删除的数据
        List<Object> deleteList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(xmList)) {
            //删除服务类
            List<Class> clzList = initBeanFactory.getDeleteServices();
            //删除加载类循环
            for (Class clz : clzList) {
                List<InitService> list = initBeanFactory.getTrafficMode(bdcCshFwkgService.getDefaultSl(), clz);
                //对应实现循环处理
                if (CollectionUtils.isNotEmpty(list)) {
                    //只是删除处理的话不做登记簿处理
                    if (sfdsc && list.get(0) instanceof InitBdcdjbAbstractService) {
                        continue;
                    }
                    List<Object> dataList = list.get(0).delete(xmList, sfzqlpbsj, sfdzbflpbsj, scplcl);
                    if (CollectionUtils.isNotEmpty(dataList)) {
                        deleteList.addAll(dataList);
                    }
                }
            }
        }
        return deleteList;
    }

    @Override
    public void deleteYwxxAllSubsystem(String gzlslid, String reason, String slzt) throws Exception {
        try {
            WorkFlowRequest workFlowRequest = new WorkFlowRequest();
            workFlowRequest.setProcessInsId(gzlslid);
            workFlowRequest.setOpinion(reason);
            workFlowRequest.setIsDelete(StringUtils.isEmpty(slzt) ? HlwSlztEnum.DELETE.getSlzt() : slzt);
            exchangeInterfaceFeignService.workflowSyncRequestInterface("wwsqupdateslzt", workFlowRequest);
        } catch (Exception e) {
            LOGGER.error("请求外网删除接口异常:{}", gzlslid, e);
        }
        //删除时调用取消作废税务信息接口
        try {
            bdcSwFeignService.qxzfLcSwxx(gzlslid);
        } catch (Exception e) {
            LOGGER.error("删除时请求取消作废税务接口异常:{}", gzlslid, e);
        }

        // 成功 删除受理信息
        bdcSlFeignService.deleteBdcSlxx(gzlslid);

        // 查询一次当前流程的不动产单元号信息 删除业务数据之前查询
        List<String> bdcdyhList = bdcdyZtFeignService.querySyncQjBdcdyh(gzlslid);

        BdcXmDTO bdcXmDTO = bdcXmService.getXmBfxxOnlyOne(gzlslid, "");

        //还原状态(删除登记信息前不更新权籍信息，删除后再单独更新一次权籍信息)
        registerWorkflowFeignService.revertBdcDbxxAndQsztSyncQj(gzlslid, false);

        //状态特殊逻辑处理
        bdcDbxxFeignService.updateYzxqlDbxxAndQszt(gzlslid, CommonConstantUtils.QSZT_HISTORY);

        //删除例外数据
        bdcGzlwFeignService.deleteBdcGzlwShByGzlslid(gzlslid);

        // 成功 删除业务数据
        boolean success = deleteYwsj(gzlslid);
        if (success && CollectionUtils.isNotEmpty(bdcdyhList)) {
            // 业务删除成功后，更新一次权籍单元信息
            bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList, bdcXmDTO.getQjgldm());
        } else {
            LOGGER.error("gzlslid:{},删除登记数据失败:{},或未获取到不动产单元信息：{}", gzlslid, success, JSONObject.toJSON(bdcdyhList));
        }
    }

    @Override
    public void deleteYwxxAllSubsystem(BdcDeleteYwxxDTO bdcDeleteYwxxDTO) throws Exception {
        this.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO.getGzlslid(), bdcDeleteYwxxDTO.getReason(), bdcDeleteYwxxDTO.getSlzt());
    }

    /**
     * 根据初始过程中生成的空值字段覆盖权籍中的null值
     *
     * @param serviceDTOMap
     * @return
     */
    @Override
    public void convertNullValue(Map<String, InitServiceDTO> serviceDTOMap) {
        LOGGER.info("同步权籍，处理权籍为空的数据:{}", JSON.toJSONString(serviceDTOMap));
        try {
            for (Map.Entry<String, InitServiceDTO> stringInitServiceDTOEntry : serviceDTOMap.entrySet()) {
                try {
                    InitServiceDTO initResultDTO = stringInitServiceDTOEntry.getValue();
                    //处理权利中的值
                    if (CollectionUtils.isNotEmpty(initResultDTO.getFieldNameSet())) {
                        LOGGER.info("同步权籍，处理权籍为空的数据,设置权利数据:{},{}",
                                JSON.toJSONString(initResultDTO.getBdcQl()),
                                JSON.toJSONString(initResultDTO.getFieldNameSet()));
                        // 权籍对象字段值为空，但是登记字段有值的字段集合需要重新设置空，避免页面展示时候权籍为空数据展示了登记数据的问题
                        BdcQl bdcQl = initResultDTO.getBdcQl();
                        Field[] fields = bdcQl.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            if (initResultDTO.getFieldNameSet().contains(field.getName())) {
                                field.setAccessible(true);
                                field.set(bdcQl, null);
                            }
                        }
                    }

                    //其他字段
                    Set<String> xmFieldNameSet = initResultDTO.getXmFieldNameSet();
                    if (CollectionUtils.isEmpty(xmFieldNameSet)) {
                        return;
                    }
                    Field[] lpbxxFields = InitServiceDTO.class.getDeclaredFields();
                    for (Field resultField : lpbxxFields) {
                        try {
                            resultField.setAccessible(true);
                            Object targetObj = resultField.get(initResultDTO);
                            if (targetObj == null) {
                                continue;
                            }
                            if (!(targetObj instanceof BdcXmDO || targetObj instanceof BdcXmFbDO)) {
                                continue;
                            }
                            LOGGER.info("同步权籍，处理权籍为空的数据,设置XM数据:{},{}",
                                    JSON.toJSONString(targetObj),
                                    JSON.toJSONString(xmFieldNameSet));
                            Class resultClass = targetObj.getClass();
                            //取单个对象的所有字段
                            Field[] dtoFields = resultClass.getDeclaredFields();
                            for (Field dtoField : dtoFields) {
                                //在配置中的读取
                                if (xmFieldNameSet.contains(dtoField.getName())) {
                                    dtoField.setAccessible(true);
                                    dtoField.set(targetObj, null);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("同步权籍，处理权籍为空的数据错误:{}", e.getMessage());
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理生成的家庭成员信息, 家庭成员需要去重, 并且权利人家庭成员关系hkbbm, hkbbbh保持一致
     */
    private void dealScJtcy(InitResultDTO initResultDTO) {
        if (CollectionUtils.isNotEmpty(initResultDTO.getBdcJtcyDOList()) && CollectionUtils.isNotEmpty(initResultDTO.getBdcQlrHkbGxDOList())) {
            //家庭成员需要根据证件号去重,证件号为空根据名称去重
            Map<String, BdcJtcyDO> jtcyzjhs = new HashMap<>();
            Map<String, BdcJtcyDO> jtcymcs = new HashMap<>();
            //去重后的家庭成员集合
            //存储需要修改的修改前户口簿编码和户口版本号和修改后
            Map<String, String> hkbbmAndBbh = new HashMap<>();
            List<BdcJtcyDO> bdcJtcyDOList = new ArrayList<>();
            //循环获取需要存储的家庭成员
            for (BdcJtcyDO bdcJtcyDO : initResultDTO.getBdcJtcyDOList()) {
                if (StringUtils.isNotBlank(bdcJtcyDO.getHkbbm()) && bdcJtcyDO.getHkbbbh() != null) {
                    String jtcyzjh = bdcJtcyDO.getJtcyzjh();
                    String jtcymc = bdcJtcyDO.getJtcymc();
                    if (StringUtils.isBlank(jtcyzjh)) {
                        if (jtcymcs.get(jtcymc) == null) {
                            jtcymcs.put(jtcymc, bdcJtcyDO);
                            bdcJtcyDOList.add(bdcJtcyDO);
                        } else {
                            hkbbmAndBbh.put(bdcJtcyDO.getHkbbm() + CommonConstantUtils.ZF_YW_DH + bdcJtcyDO.getHkbbbh(), jtcymcs.get(jtcymc).getHkbbm() + CommonConstantUtils.ZF_YW_DH + jtcymcs.get(jtcymc).getHkbbbh());
                        }
                    } else if (jtcyzjhs.get(jtcyzjh) == null) {
                        jtcyzjhs.put(jtcyzjh, bdcJtcyDO);
                        bdcJtcyDOList.add(bdcJtcyDO);
                    } else {
                        hkbbmAndBbh.put(bdcJtcyDO.getHkbbm() + CommonConstantUtils.ZF_YW_DH + bdcJtcyDO.getHkbbbh(), jtcyzjhs.get(jtcyzjh).getHkbbm() + CommonConstantUtils.ZF_YW_DH + jtcyzjhs.get(jtcyzjh).getHkbbbh());
                    }
                }
            }
            //修改户口簿与版本号与家庭成员保持一致
            for (BdcQlrHkbGxDO bdcQlrHkbGxDO : initResultDTO.getBdcQlrHkbGxDOList()) {
                if (StringUtils.isNotBlank(bdcQlrHkbGxDO.getHkbbm()) && bdcQlrHkbGxDO.getHkbbbh() != null && hkbbmAndBbh.get(bdcQlrHkbGxDO.getHkbbm() + CommonConstantUtils.ZF_YW_DH + bdcQlrHkbGxDO.getHkbbbh()) != null) {
                    //修改后的信息
                    String newhkbbmAndBbh = hkbbmAndBbh.get(bdcQlrHkbGxDO.getHkbbm() + CommonConstantUtils.ZF_YW_DH + bdcQlrHkbGxDO.getHkbbbh());
                    bdcQlrHkbGxDO.setHkbbm(newhkbbmAndBbh.split(CommonConstantUtils.ZF_YW_DH)[0]);
                    bdcQlrHkbGxDO.setHkbbbh(Integer.parseInt(newhkbbmAndBbh.split(CommonConstantUtils.ZF_YW_DH)[1]));
                }

            }
            initResultDTO.setBdcJtcyDOList(bdcJtcyDOList);
        }

    }

    /**
     * 处理生成量化信息，对量化信息进行去重，采用工作流实例ID+自然幢id做为去重key
     *
     * @param initResultDTO 初始化所有服务返回的所有结果集
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    private void dealJsydLhxx(InitResultDTO initResultDTO) {
        if (CollectionUtils.isNotEmpty(initResultDTO.getBdcXmJsydlhxxGxList())) {
            List<BdcXmJsydlhxxGxDO> lhxxList = initResultDTO.getBdcXmJsydlhxxGxList().stream()
                    .filter(distinctByKey(t -> t.getGzlslid() + t.getJsydzrzxxid()))
                    .collect(Collectors.toList());
            initResultDTO.setBdcXmJsydlhxxGxList(lhxxList);
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
