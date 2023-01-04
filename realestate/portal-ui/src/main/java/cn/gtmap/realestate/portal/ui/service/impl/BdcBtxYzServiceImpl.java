package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.realestate.common.core.service.feign.register.BdcSjyPzFeignService;
import cn.gtmap.realestate.common.matcher.FormStateClientMatcher;
import cn.gtmap.gtc.formclient.common.client.FormThirdResourceClient;
import cn.gtmap.gtc.formclient.common.constants.FormElementTypeEnum;
import cn.gtmap.gtc.formclient.common.dto.FormElementConfigDTO;
import cn.gtmap.gtc.formclient.common.dto.FormStateDTO;
import cn.gtmap.gtc.formclient.common.dto.ValidSqlDetailDTO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.portal.BdcBtxyzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.config.ValidSqlConditionBeans;
import cn.gtmap.realestate.portal.ui.core.dto.BdcBtxpzDTO;
import cn.gtmap.realestate.portal.ui.core.vo.BdcBtxyzVO;
import cn.gtmap.realestate.portal.ui.service.BdcBtxYzService;
import cn.gtmap.realestate.portal.ui.service.BdcCheckValidSqlConditionService;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/11
 * @description 必填项验证业务类
 */
@Service
public class BdcBtxYzServiceImpl implements BdcBtxYzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBtxYzServiceImpl.class);
    @Autowired
    private FormStateClientMatcher formStateClient;
    @Autowired
    FormThirdResourceClient formThirdResourceClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSjyPzFeignService bdcSjyPzRestService;
    @Autowired
    ValidSqlConditionBeans validSqlConditionBeans;
    /**
     * 必填项提示配置模板
     */
    @Value("${msg.btx}")
    private String btxMsg;

    /**
     * @param formViewKey gzlslid taskId
     * @return BdcBtxyzVO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证必填项
     */
    @Override
    public List<BdcBtxyzVO> checkBtx(String formViewKey, String gzlslid, String taskId) {
        if(StringUtils.isAnyBlank(formViewKey,gzlslid,taskId)){
            throw new MissingArgumentException("gzlslid,taskId,formViewKey");
        }
        List<BdcBtxyzVO> bdcBtxyzVOList =Lists.newArrayList();
        List<FormStateDTO> formStateDTOList = formStateClient.listByFormViewKey(formViewKey,
                CommonConstantUtils.FORMKEY_FILTER_OPERATE_NOTLIKE, CommonConstantUtils.FORMKEY_FILTER_URL_PORTAL_UI);
        if(CollectionUtils.isEmpty(formStateDTOList)){
            throw new AppException("表单信息为空！");
        }
        formStateDTOList.forEach(formStateDTO -> {

            String formName = formStateDTO.getFormStateName();
            List<ValidSqlDetailDTO> validSqlDetailDTOS = formStateDTO.getValidSqlDetailList();
            List<FormElementConfigDTO> elementConfigDTOS = formThirdResourceClient.getElementConfig(formStateDTO.getFormStateId());
            /**
             * 过滤非必填字段
             */
            List<FormElementConfigDTO> requiredElements = elementConfigDTOS.stream().filter(formElementConfigDTO ->
                    StringUtils.equals(FormElementTypeEnum.REQUIRED.getType(), formElementConfigDTO.getFormElementType())||StringUtils.equals(FormElementTypeEnum.REQUIRED_READ_ONLY.getType(),formElementConfigDTO.getFormElementType())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(validSqlDetailDTOS)) {
                /**
                 * 配置了必填项字段 提示
                 */
                if (CollectionUtils.isNotEmpty(requiredElements)) {
                    throw new AppException("表单：" + formName + "&nbsp;&nbsp;配置了必填项字段，缺少对应的验证SQL，请检查表单配置！");
                } else {
                    return;
                }
            }
            BdcBtxyzDTO bdcBtxyzDTO=new BdcBtxyzDTO();
            bdcBtxyzDTO.setFormName(formName);
            bdcBtxyzDTO.setGzlslid(gzlslid);
            bdcBtxyzDTO.setTaskId(taskId);
            check(validSqlDetailDTOS,requiredElements,bdcBtxyzDTO);
            BdcBtxyzVO bdcBtxyzVO=processResult(bdcBtxyzDTO,requiredElements);
            if(bdcBtxyzVO!=null){
                bdcBtxyzVOList.add(bdcBtxyzVO);
            }
        });
        return bdcBtxyzVOList;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    public BdcBtxyzDTO check(List<ValidSqlDetailDTO> validSqlDetailDTOS, List<FormElementConfigDTO> requiredElements, BdcBtxyzDTO bdcBtxyzDTO) {
        /**
         * 支持多条sql,sql之间以;分隔
         */
        Set<String> set = Sets.newHashSet();
        validSqlDetailDTOS.forEach(validSqlDetailDTO -> {
            String sqlStr = validSqlDetailDTO.getValidSql();
            String condition = validSqlDetailDTO.getCondition();
            condition=StringUtils.isBlank(condition)?CommonConstantUtils.VALID_SQL_CONDITION_DEFAULT:condition;
            BdcCheckValidSqlConditionService bdcCheckValidSqlConditionService = validSqlConditionBeans.getCheckConditionBeanMap().get(condition);
            if(bdcCheckValidSqlConditionService==null){
                throw new AppException("BdcCheckValidSqlConditionService实现类为空，请检查配置！");
            }
            List<String> sqlList = Lists.newArrayList(sqlStr.split(CommonConstantUtils.ZF_YW_FH));
            List<String> xmidSqlList = Lists.newArrayList();
            List<String> gzlslidSqlList = Lists.newArrayList();
            Set<String> columnNameSet = Sets.newHashSet();
            sqlList.forEach(sql -> {
                if (StringUtils.isNotBlank(sql)) {
                    bdcCheckValidSqlConditionService.getColumnName(sql, columnNameSet);
                    if (StringUtils.indexOf(sql, Constants.PARAM_GZLSLID) > -1) {
                        gzlslidSqlList.add(sql);
                    } else {
                        xmidSqlList.add(sql);
                    }
                }
            });
            bdcBtxyzDTO.setCondition(condition);
            bdcBtxyzDTO.setColumnNameSet(columnNameSet);
            bdcBtxyzDTO.setSet(set);
            bdcBtxyzDTO.setGzlslidSqlList(gzlslidSqlList);
            bdcBtxyzDTO.setXmidSqlList(xmidSqlList);
            /**
             * 批量验证
             */
            bdcCheckValidSqlConditionService.checkPlResult(bdcBtxyzDTO, requiredElements);
            /**
             * 按照项目验证
             */
            bdcCheckValidSqlConditionService.checkResult(bdcBtxyzDTO, requiredElements);
        });
        return bdcBtxyzDTO;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    public BdcBtxyzVO processResult(BdcBtxyzDTO bdcBtxyzDTO,List<FormElementConfigDTO> requiredElements) {
        Set<String> set=bdcBtxyzDTO.getSet();
        Set<String> columnName = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(set)) {
            LOGGER.info("必填项为空字段：{}", set);
            requiredElements.forEach(element -> {
                String htmlId = StringUtils.lowerCase(element.getFormHtmlId());
                if (set.contains(htmlId)) {
                    columnName.add(element.getFormElementName());
                }
            });
            LOGGER.info("必填项为空字段名称：{}", columnName);
        }
        List<String> msgList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(columnName)) {
            msgList.add(btxMsg.replace("form", bdcBtxyzDTO.getFormName()).replace("column", StringUtils.strip(columnName.toString(), "[]")));
        }
        if (CollectionUtils.isNotEmpty(msgList)) {
            BdcBtxyzVO bdcBtxyzVO = new BdcBtxyzVO();
            bdcBtxyzVO.setBdmc(bdcBtxyzDTO.getFormName());
            bdcBtxyzVO.setYzxx(msgList);
            return bdcBtxyzVO;
        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    public Set<String> compareBtzd(List<Map<String, Object>> resultList, List<FormElementConfigDTO> requiredElements) {
        Set<String> result = Sets.newHashSet();
        if (CollectionUtils.isEmpty(resultList)) {
            return addRequiredElement(requiredElements, result);
        }
        Multimap<String, Object> multimap = ArrayListMultimap.create();
        Multiset multisetKey = HashMultiset.create();
        /**
         * 循环 处理查询结果，多个sql或者同个sql批量数据 中有可能有 查询结果为空的情况
         */
        for (Map<String, Object> map : resultList) {
            if (MapUtils.isNotEmpty(map)) {
                /**
                 * 将多个map中的key,value 放入同一个Multimap中，
                 * 在后面直接循环必填字段，直接get value判断是否为空，为空则提示
                 * 有可能有相同字段，多个值的情况，将key 方入 multisetKey，
                 * 后面比较 value 数量和multisetKey 中key的数量是否一致
                 */
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (StringUtils.indexOf(key, "-") == -1) {
                        key = StringUtils.lowerCase(key);
                    }
                    Object value = entry.getValue();
                    multisetKey.add(key);
                    if (value != null) {
                        multimap.put(key, value);
                    }
                }
            } else {
                /**
                 * 批量数据查询出结果 map为空的情况
                 */
                result.addAll(addRequiredElement(requiredElements, result));
            }
        }
        if (!multimap.isEmpty()) {
            /**
             * 添加 必填项为空的 元素名称
             */
            result = addRequiredAndNullElement(requiredElements, result, multimap, multisetKey);
        } else {
            /**
             * 处理 配置sql 查询结果全为空的情况
             */
            result = addRequiredElement(requiredElements, result);
        }
        return result;

    }


    /**
     * @return formViewKey gzlslid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取配置sql
     */
    @Override
    public BdcBtxpzDTO queryFormConfig(String formViewKey, String gzlslid, String taskId) {
        BdcBtxpzDTO bdcBtxpzDTO = new BdcBtxpzDTO();
        Map formMap = Maps.newHashMap();
        Map<String, List<FormElementConfigDTO>> configElements = Maps.newHashMap();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<FormStateDTO> formStateDTOList = formStateClient.listByFormViewKey(formViewKey,
                CommonConstantUtils.FORMKEY_FILTER_OPERATE_NOTLIKE, CommonConstantUtils.FORMKEY_FILTER_URL_PORTAL_UI);
        formStateDTOList.forEach(formStateDTO -> {
            String sqlStr = formStateDTO.getValidSql();
            String formName = formStateDTO.getFormStateName();
            /**
             *  未配置sql
             */
            if (StringUtils.isBlank(sqlStr)) {
                List<FormElementConfigDTO> noSqlelementConfigDTOS = formThirdResourceClient.getElementConfig(formStateDTO.getFormStateId());
                List<FormElementConfigDTO> requiredElements = noSqlelementConfigDTOS.stream().filter(formElementConfigDTO ->
                        StringUtils.equals(FormElementTypeEnum.REQUIRED.getType(), formElementConfigDTO.getFormElementType())).collect(Collectors.toList());
                /**
                 * 配置了必填项字段 提示
                 */
                if (CollectionUtils.isNotEmpty(requiredElements)) {
                    throw new AppException("表单：" + formName + "&nbsp;&nbsp;配置了必填项字段，缺少对应的验证SQL，请检查表单配置！");
                } else {
                    return;
                }
            }
            /**
             * 支持多条sql,sql之间以;分隔
             */
            List<String> sqlList = Lists.newArrayList(sqlStr.split(CommonConstantUtils.ZF_YW_FH));
            List<FormElementConfigDTO> elementConfigDTOS = formThirdResourceClient.getElementConfig(formStateDTO.getFormStateId());
            configElements.put(formStateDTO.getFormStateName(), elementConfigDTOS);
            List<Map> paramList = Lists.newArrayList();
            sqlList.forEach(sql -> {
                if (StringUtils.isNotBlank(sql) && CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    /**验证sql的参数，暂时不允许只配置一个gzlslid，后面会根据项目ID循环生成每个项目的验证sql，只用gzlslid作为参数，会导致查询结果集过大
                     * 内存溢出
                     */
//                    if (StringUtils.indexOf(sql, Constants.PARAM_GZLSLID) > -1 && StringUtils.indexOf(sql, Constants.PARAM_TASKID) == -1 && StringUtils.indexOf(sql, Constants.PARAM_XMID) == -1) {
//                        throw new MissingArgumentException("表单：" + formName + "&nbsp;&nbsp;配置了必填项验证SQL:" + sql + ",参数不允许只配置一个gzlslid参数，请检查表单SQL配置！");
//                    }

                    if (StringUtils.indexOf(sql, Constants.PARAM_XMID) > -1) {
                        List<BdcXmDO> bdcXmDOS = bdcXmDOList;
                        if (StringUtils.startsWith(formName, CommonConstantUtils.FORM_NAME_SH)) {
                            bdcXmDOS = bdcXmDOList.subList(0, 1);
                        }
                        bdcXmDOS.forEach(bdcXmDO -> {
                            /**
                             * 虚拟不动产单元号不验证
                             */
                            if (BdcdyhToolUtils.checkXnbdcdyh(bdcXmDO.getBdcdyh())) {
                                return;
                            }
                            Map param = Maps.newHashMap();
                            param.put("xmid", bdcXmDO.getXmid());
                            param.put("sql", sql);
                            param.put("gzlslid", gzlslid);
                            param.put("taskId", taskId);
                            paramList.add(param);
                            LOGGER.info("{}：必填项配置：xmid:" + bdcXmDO.getXmid() + "taskId:" + taskId + "sql:" + sql + "{}");
                        });
                    } else {
                        Map param = Maps.newHashMap();
                        param.put("sql", sql);
                        param.put("gzlslid", gzlslid);
                        param.put("taskId", taskId);
                        paramList.add(param);
                    }
                }
            });
            if (CollectionUtils.isNotEmpty(paramList)) {
                formMap.put(formStateDTO.getFormStateName(), paramList);
            }
        });
        bdcBtxpzDTO.setBdpz(formMap);
        bdcBtxpzDTO.setBdyspz(configElements);
        return bdcBtxpzDTO;
    }

    /**
     * @param bdcBtxpzDTO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询比较必填项字段
     */
    @Override
    public List<BdcBtxyzVO> queryBdcBtx(BdcBtxpzDTO bdcBtxpzDTO) {
        List<BdcBtxyzVO> bdcBtxyzVOList = Lists.newArrayList();
        Map formMap = bdcBtxpzDTO.getBdpz();
        Map configElements = bdcBtxpzDTO.getBdyspz();
        if (MapUtils.isNotEmpty(formMap)) {
            Map<String, List<Map<String, Object>>> sjyResult = bdcSjyPzRestService.yxPzSjyPl(formMap);
            sjyResult.forEach((formKey, resultList) -> {
                List<String> msgList = Lists.newArrayList();
                Set<String> result = Sets.newHashSet();
                List<FormElementConfigDTO> formElementConfigDTOS = (List<FormElementConfigDTO>) configElements.get(formKey);
                /**
                 * 过滤非必填字段
                 */
                List<FormElementConfigDTO> requiredElements = formElementConfigDTOS.stream().filter(formElementConfigDTO ->
                        StringUtils.equals(FormElementTypeEnum.REQUIRED.getType(), formElementConfigDTO.getFormElementType())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(resultList)) {
                    addRequiredElement(requiredElements, result);
                }
                Multimap<String, Object> multimap = ArrayListMultimap.create();
                Multiset multisetKey = HashMultiset.create();
                /**
                 * 循环 处理查询结果，多个sql或者同个sql批量数据 中有可能有 查询结果为空的情况
                 */
                resultList.forEach(map -> {
                    if (MapUtils.isNotEmpty(map)) {
                        /**
                         * 将多个map中的key,value 放入同一个Multimap中，
                         * 在后面直接循环必填字段，直接get value判断是否为空，为空则提示
                         * 有可能有相同字段，多个值的情况，将key 方入 multisetKey，
                         * 后面比较 value 数量和multisetKey 中key的数量是否一致
                         */
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            String key = entry.getKey();
                            if (StringUtils.indexOf(key, "-") == -1) {
                                key = StringUtils.lowerCase(key);
                            }
                            Object value = entry.getValue();
                            multisetKey.add(key);
                            if (value != null) {
                                multimap.put(key, value);
                            }
                        }
                    } else {
                        /**
                         * 批量数据查询出结果 map为空的情况
                         */
                        addRequiredElement(requiredElements, result);
                    }
                });
                if (!multimap.isEmpty()) {

                    /**
                     * 添加 必填项为空的 元素名称
                     */
                    addRequiredAndNullElement(requiredElements, result, multimap, multisetKey);
                } else {
                    /**
                     * 处理 配置sql 查询结果全为空的情况
                     */
                    addRequiredElement(requiredElements, result);
                }
                if (CollectionUtils.isNotEmpty(result)) {
                    msgList.add(btxMsg.replace("form", formKey).replace("column", StringUtils.strip(result.toString(), "[]")));
                }
                if (CollectionUtils.isNotEmpty(msgList)) {
                    BdcBtxyzVO bdcBtxyzVO = new BdcBtxyzVO();
                    bdcBtxyzVO.setBdmc(formKey);
                    bdcBtxyzVO.setYzxx(msgList);
                    bdcBtxyzVOList.add(bdcBtxyzVO);
                }
            });
        }
        LOGGER.info("必填项验证：验证结果，{}", JSONObject.toJSONString(bdcBtxyzVOList));
        return bdcBtxyzVOList;
    }

    /**
     * @param formElementConfigDTOS result
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 添加必填项字段
     */
    private Set<String> addRequiredElement(List<FormElementConfigDTO> formElementConfigDTOS, Set<String> result) {
        formElementConfigDTOS.forEach(element -> result.add(element.getFormElementName()));
        return result;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 添加 必填项为空的 元素名称
     */
    private Set<String> addRequiredAndNullElement(List<FormElementConfigDTO> formElementConfigDTOS, Set<String> result, Multimap<String, Object> map, Multiset multisetKey) {
        formElementConfigDTOS.forEach(formElementConfigDTO -> {
            String htmlId = formElementConfigDTO.getFormHtmlId();
            Integer size = multisetKey.count(htmlId);
            boolean valueNull = CollectionUtils.isEmpty(map.get(htmlId)) || CollectionUtils.size(map.get(htmlId)) < size;
            if (valueNull) {
                result.add(formElementConfigDTO.getFormElementName());
            }
        });
        return result;
    }
}