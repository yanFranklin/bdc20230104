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
 * ?????????????????????????????????
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
    // ??????????????????????????????
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
            //????????????
            dealDTO(initResultDTO, serviceDTOMap.values());
        }
        return initResultDTO;
    }

    @Override
    public InitResultDTO dealServiceDTO(List<InitServiceDTO> listDTO) throws IllegalAccessException {
        InitResultDTO initResultDTO = null;
        if (CollectionUtils.isNotEmpty(listDTO)) {
            initResultDTO = new InitResultDTO();
            //????????????
            dealDTO(initResultDTO, listDTO);
        }
        return initResultDTO;
    }

    private void dealDTO(InitResultDTO initResultDTO, Collection<InitServiceDTO> collection) throws IllegalAccessException {
        //??????????????????
        Field[] fields = InitResultDTO.class.getDeclaredFields();
        for (Field resultField : fields) {
            resultField.setAccessible(true);
            for (InitServiceDTO initServiceDTO : collection) {
                //?????????????????????
                Field[] dtoFields = InitServiceDTO.class.getDeclaredFields();
                for (Field dtoField : dtoFields) {
                    dtoField.setAccessible(true);
                    Object val = dtoField.get(initServiceDTO);
                    if (val != null) {
                        List list = (List) resultField.get(initResultDTO);
                        //????????????????????????
                        if (list == null) {
                            resultField.set(initResultDTO, new ArrayList<>());
                        }
                        //????????????
                        if (dtoField.getType() == List.class) {
                            if (StringUtils.equals(resultField.getGenericType().toString(), dtoField.getGenericType().toString())) {
                                ((List) resultField.get(initResultDTO)).addAll((List) val);
                                break;
                            }
                        } else {
                            //??????????????????????????? ??????>?????????
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
            //??????????????????????????????
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
            //?????????
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
                    LOGGER.info("?????????????????????????????????????????????class???{}?????????ID???{}", obj.getClass().getName(), id);
                }
                //??????
                initResultDTO.getDeleteList().clear();
            }
            //??????????????????
            dealScJtcy(initResultDTO);
            // ????????????????????????
            dealJsydLhxx(initResultDTO);
            Field[] fields = InitResultDTO.class.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                String slbh = "";
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object val = field.get(initResultDTO);
                    //???????????????list??????
                    if (val != null && field.getType() == List.class) {
                        List filedList = (List) val;
                        if (CollectionUtils.isNotEmpty(filedList)) {
                            //??????????????????????????????map
                            HashMap<String, List> map = new HashMap<>();
                            List voList;
                            for (int i = 0; i < filedList.size(); i++) {
                                //??????????????????????????????
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
                                    //????????????????????????
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
                                                    LOGGER.info("???????????????ID?????????{}", xmid);
                                                } else {
                                                    xmids.add(xmid);
                                                }
                                            }
                                        }
                                        LOGGER.info("{} ??????????????????:{} {} {}", list.get(0).getClass().toString(), simpleDateFormat.format(new Date()), list.size(), slbh);
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
                                                        LOGGER.info("???????????????ID?????????{}", xmid);
                                                    } else {
                                                        fpxmids.add(xmid);
                                                    }
                                                }
                                            }
                                            try {
                                                entityMapper.insertBatchSelective(data);
                                                LOGGER.info("{} ???????????????:{} {} {} ???????????????:{}", list.get(0).getClass().toString(), simpleDateFormat.format(new Date()), list.size(), slbh, data.size());
                                            } catch (Exception e) {
                                                if (data.get(0) instanceof BdcXmDO) {
                                                    List<String> xmidList = new ArrayList<>();
                                                    for (Object o : data) {
                                                        xmidList.add(((BdcXmDO) o).getXmid());
                                                    }
                                                    LOGGER.info("??????????????????????????????{} {}", JSONObject.toJSONString(xmidList), slbh);
                                                }
                                                throw e;
                                            }
                                        }
                                        LOGGER.info("{} ??????????????????:{} {} {}", list.get(0).getClass().toString(), simpleDateFormat.format(new Date()), list.size(), slbh);
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
     * ????????????id????????????????????????
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
            //????????????id???????????????????????????
            success = initDataService.deleteYwsj(list, false);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return success;
    }

    /**
     * ?????????????????????ID????????????????????????
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
                LOGGER.error("??????????????????????????????????????????gzlslid???{}", gzlslid);
            }
        }
        return success;
    }

    /**
     * ????????????????????????????????????
     *
     * @param xmList
     * @param sfzqlpbsj ???????????????????????????
     * @param sfzqlpbsj ???????????????????????????
     * @param scplcl    ??????????????????
     * @param sfdsc     ????????????????????????
     * @return List<Object>
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> queryDeleteData(List<BdcXmDO> xmList, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean scplcl, Boolean sfdsc) throws Exception {
        //??????????????????
        List<Object> deleteList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(xmList)) {
            //???????????????
            List<Class> clzList = initBeanFactory.getDeleteServices();
            //?????????????????????
            for (Class clz : clzList) {
                List<InitService> list = initBeanFactory.getTrafficMode(bdcCshFwkgService.getDefaultSl(), clz);
                //????????????????????????
                if (CollectionUtils.isNotEmpty(list)) {
                    //?????????????????????????????????????????????
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
            LOGGER.error("??????????????????????????????:{}", gzlslid, e);
        }
        //?????????????????????????????????????????????
        try {
            bdcSwFeignService.qxzfLcSwxx(gzlslid);
        } catch (Exception e) {
            LOGGER.error("?????????????????????????????????????????????:{}", gzlslid, e);
        }

        // ?????? ??????????????????
        bdcSlFeignService.deleteBdcSlxx(gzlslid);

        // ??????????????????????????????????????????????????? ??????????????????????????????
        List<String> bdcdyhList = bdcdyZtFeignService.querySyncQjBdcdyh(gzlslid);

        BdcXmDTO bdcXmDTO = bdcXmService.getXmBfxxOnlyOne(gzlslid, "");

        //????????????(???????????????????????????????????????????????????????????????????????????????????????)
        registerWorkflowFeignService.revertBdcDbxxAndQsztSyncQj(gzlslid, false);

        //????????????????????????
        bdcDbxxFeignService.updateYzxqlDbxxAndQszt(gzlslid, CommonConstantUtils.QSZT_HISTORY);

        //??????????????????
        bdcGzlwFeignService.deleteBdcGzlwShByGzlslid(gzlslid);

        // ?????? ??????????????????
        boolean success = deleteYwsj(gzlslid);
        if (success && CollectionUtils.isNotEmpty(bdcdyhList)) {
            // ??????????????????????????????????????????????????????
            bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList, bdcXmDTO.getQjgldm());
        } else {
            LOGGER.error("gzlslid:{},????????????????????????:{},???????????????????????????????????????{}", gzlslid, success, JSONObject.toJSON(bdcdyhList));
        }
    }

    @Override
    public void deleteYwxxAllSubsystem(BdcDeleteYwxxDTO bdcDeleteYwxxDTO) throws Exception {
        this.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO.getGzlslid(), bdcDeleteYwxxDTO.getReason(), bdcDeleteYwxxDTO.getSlzt());
    }

    /**
     * ????????????????????????????????????????????????????????????null???
     *
     * @param serviceDTOMap
     * @return
     */
    @Override
    public void convertNullValue(Map<String, InitServiceDTO> serviceDTOMap) {
        LOGGER.info("??????????????????????????????????????????:{}", JSON.toJSONString(serviceDTOMap));
        try {
            for (Map.Entry<String, InitServiceDTO> stringInitServiceDTOEntry : serviceDTOMap.entrySet()) {
                try {
                    InitServiceDTO initResultDTO = stringInitServiceDTOEntry.getValue();
                    //?????????????????????
                    if (CollectionUtils.isNotEmpty(initResultDTO.getFieldNameSet())) {
                        LOGGER.info("??????????????????????????????????????????,??????????????????:{},{}",
                                JSON.toJSONString(initResultDTO.getBdcQl()),
                                JSON.toJSONString(initResultDTO.getFieldNameSet()));
                        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        BdcQl bdcQl = initResultDTO.getBdcQl();
                        Field[] fields = bdcQl.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            if (initResultDTO.getFieldNameSet().contains(field.getName())) {
                                field.setAccessible(true);
                                field.set(bdcQl, null);
                            }
                        }
                    }

                    //????????????
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
                            LOGGER.info("??????????????????????????????????????????,??????XM??????:{},{}",
                                    JSON.toJSONString(targetObj),
                                    JSON.toJSONString(xmFieldNameSet));
                            Class resultClass = targetObj.getClass();
                            //??????????????????????????????
                            Field[] dtoFields = resultClass.getDeclaredFields();
                            for (Field dtoField : dtoFields) {
                                //?????????????????????
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
            LOGGER.info("????????????????????????????????????????????????:{}", e.getMessage());
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????????????????, ????????????????????????, ?????????????????????????????????hkbbm, hkbbbh????????????
     */
    private void dealScJtcy(InitResultDTO initResultDTO) {
        if (CollectionUtils.isNotEmpty(initResultDTO.getBdcJtcyDOList()) && CollectionUtils.isNotEmpty(initResultDTO.getBdcQlrHkbGxDOList())) {
            //???????????????????????????????????????,?????????????????????????????????
            Map<String, BdcJtcyDO> jtcyzjhs = new HashMap<>();
            Map<String, BdcJtcyDO> jtcymcs = new HashMap<>();
            //??????????????????????????????
            //???????????????????????????????????????????????????????????????????????????
            Map<String, String> hkbbmAndBbh = new HashMap<>();
            List<BdcJtcyDO> bdcJtcyDOList = new ArrayList<>();
            //???????????????????????????????????????
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
            //??????????????????????????????????????????????????????
            for (BdcQlrHkbGxDO bdcQlrHkbGxDO : initResultDTO.getBdcQlrHkbGxDOList()) {
                if (StringUtils.isNotBlank(bdcQlrHkbGxDO.getHkbbm()) && bdcQlrHkbGxDO.getHkbbbh() != null && hkbbmAndBbh.get(bdcQlrHkbGxDO.getHkbbm() + CommonConstantUtils.ZF_YW_DH + bdcQlrHkbGxDO.getHkbbbh()) != null) {
                    //??????????????????
                    String newhkbbmAndBbh = hkbbmAndBbh.get(bdcQlrHkbGxDO.getHkbbm() + CommonConstantUtils.ZF_YW_DH + bdcQlrHkbGxDO.getHkbbbh());
                    bdcQlrHkbGxDO.setHkbbm(newhkbbmAndBbh.split(CommonConstantUtils.ZF_YW_DH)[0]);
                    bdcQlrHkbGxDO.setHkbbbh(Integer.parseInt(newhkbbmAndBbh.split(CommonConstantUtils.ZF_YW_DH)[1]));
                }

            }
            initResultDTO.setBdcJtcyDOList(bdcJtcyDOList);
        }

    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????ID+?????????id????????????key
     *
     * @param initResultDTO ?????????????????????????????????????????????
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
