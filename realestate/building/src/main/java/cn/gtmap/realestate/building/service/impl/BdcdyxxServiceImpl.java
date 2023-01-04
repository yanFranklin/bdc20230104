package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.bo.BdcxxConvertBO;
import cn.gtmap.realestate.building.service.BdcdyxxService;
import cn.gtmap.realestate.building.util.BdcdyxxUtils;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxPlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.MappingMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-05
 * @description BdcdyxxServiceImpl
 */
@Service
public class BdcdyxxServiceImpl implements BdcdyxxService {

    private static Logger LOGGER = LoggerFactory.getLogger(BdcdyxxServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private DozerBeanMapper bdcDozerMapper;

    /**
     * @param bdcdyxxPlRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量更新不动产单元信息
     */
    @Override
    public void updateBdcdyxxPl(BdcdyxxPlRequestDTO bdcdyxxPlRequestDTO) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if(bdcdyxxPlRequestDTO != null
                && CollectionUtils.isNotEmpty(bdcdyxxPlRequestDTO.getBdcdyxxRequestDTOList())){
            for(BdcdyxxRequestDTO bdcdyxxRequestDTO : bdcdyxxPlRequestDTO.getBdcdyxxRequestDTOList()){
                updateBdcdyxx(bdcdyxxRequestDTO);
            }
        }
    }

    /**
     * @param bdcdyxxRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新不动产单元信息
     */
    @Override
    @Transactional
    public void updateBdcdyxx(BdcdyxxRequestDTO bdcdyxxRequestDTO) throws InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (bdcdyxxRequestDTO == null) {
            throw new MissingArgumentException("bdcdyxxRequestDTO");
        }
        if (StringUtils.isBlank(bdcdyxxRequestDTO.getBdcdyh())) {
            throw new MissingArgumentException("bdcdyxxRequestDTO.bdcdyh");
        }

        BdcxxConvertBO bdcxxConvertBO = new BdcxxConvertBO();
        Map<String, List> requestDOMap = new HashMap<>();
        Method[] methods = BdcdyxxRequestDTO.class.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                Object result = method.invoke(bdcdyxxRequestDTO);
                if(result != null){
                    List doList = new ArrayList();
                    String doName = "";
                    if (result instanceof List) {
                        List resultList = (List) result;
                        if (CollectionUtils.isNotEmpty(resultList)) {
                            doName = resultList.get(0).getClass().getName();
                            doList.addAll((List) result);
                        }
                    } else {
                        doName = result.getClass().getName();
                        doList.add(result);
                    }
                    requestDOMap.put(doName, doList);
                }
            }
        }
        bdcxxConvertBO.setBdcdyxxRequestDTO(bdcdyxxRequestDTO);
        bdcxxConvertBO.setRequestDOMap(requestDOMap);

        // 自动执行对照
        autoDozer(bdcxxConvertBO);

        // 先删除
        List<Object> deleteList = bdcxxConvertBO.getDeleteObjectList();
        if (CollectionUtils.isNotEmpty(deleteList)) {
            for (Object deleteObject : deleteList) {
                entityMapper.delete(deleteObject);
            }
        }

        // 插入
        List<Object> insertList = bdcxxConvertBO.getInsertList();
        if (CollectionUtils.isNotEmpty(insertList)) {
            for (Object insertObject : insertList) {
                // 存UUID主键
                insertPkUUID(insertObject);
                entityMapper.insertSelective(insertObject);
            }
        }

        // 更新
        List<Object> updateList = bdcxxConvertBO.getUpdateList();
        if (CollectionUtils.isNotEmpty(updateList)) {
            for (Object updateObject : updateList) {
                LOGGER.error("update :{}",JSONObject.toJSONString(updateObject));
                entityMapper.updateByPrimaryKeySelective(updateObject);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param insertObj
     * @return void
     * @description 给实体主键setUUID
     */
    private static void insertPkUUID(Object insertObj) throws IllegalAccessException, InvocationTargetException {
        List<Field> fieldPkList = AnnotationsUtils.getAnnotationField(insertObj, Id.class);
        // 获取 主键的 set方法
        Field pkField = fieldPkList.get(0);
        String fieldName = pkField.getName();
        String firstChar = fieldName.substring(0,1);
        String setPkMethodName = "set" + StringUtils.upperCase(firstChar) + fieldName.substring(1,fieldName.length());
        Method[] methods = insertObj.getClass().getMethods();
        for(Method method : methods){
            if(StringUtils.equals(setPkMethodName,method.getName())){
                method.invoke(insertObj,UUIDGenerator.generate());
            }
        }
    }

    /**
     * @param bdcxxConvertBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 自动执行XML中的对照关系
     */
    @Override
    public void autoDozer(BdcxxConvertBO bdcxxConvertBO) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        // 1. 获取所有对照Mapping
        MappingMetadata metadata = bdcDozerMapper.getMappingMetadata();

        // 2. 循环所有Mapping 获取 目标实体 组织成 大的Map key值为目标实体
        Map<String, List<ClassMappingMetadata>> targetDOMappingMap = new HashMap<>();
        if (metadata != null && CollectionUtils.isNotEmpty(metadata.getClassMappings())) {
            for (ClassMappingMetadata mapping : metadata.getClassMappings()) {
                String targetDOName = mapping.getDestinationClassName();
                if (targetDOMappingMap.get(targetDOName) == null) {
                    List<ClassMappingMetadata> mappingList = new ArrayList<>();
                    mappingList.add(mapping);
                    targetDOMappingMap.put(targetDOName, mappingList);
                } else {
                    targetDOMappingMap.get(targetDOName).add(mapping);
                }
            }
        }

        // 3. 遍历Map 执行map
        if (MapUtils.isNotEmpty(targetDOMappingMap)) {
            for (Map.Entry<String, List<ClassMappingMetadata>> entry : targetDOMappingMap.entrySet()) {
                String doName = entry.getKey();
                List<ClassMappingMetadata> mappingList = entry.getValue();
                dozerTargetDO(Class.forName(doName), mappingList, bdcxxConvertBO);
            }
        }
    }


    /**
     * @param targetDO
     * @param mappingList
     * @param bdcxxConvertBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 执行单个目标的转换
     */
    private void dozerTargetDO(Class targetDO, List<ClassMappingMetadata> mappingList, BdcxxConvertBO bdcxxConvertBO) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        // 1. 首先判断是否需要
        boolean checkResult = BdcdyxxUtils.checkNeedDozer(targetDO, bdcxxConvertBO.getBdcdyxxRequestDTO().getBdcdyh());
        if (checkResult) {
            // 2. 根据BDCDYH查询
            Object queryResult = BdcdyxxUtils.queryBdcdyxxByBdcdyh(targetDO, bdcxxConvertBO.getBdcdyxxRequestDTO().getBdcdyh());
            if (queryResult instanceof List) {
                List resultList = (List) queryResult;
                getDozerResult(bdcxxConvertBO, resultList, mappingList);
            } else if(queryResult != null) {
                getDozerResult(bdcxxConvertBO, queryResult, mappingList);
            }
        }
    }

    /**
     * @param bdcxxConvertBO
     * @param existObjList
     * @param mappingList
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description bdcdyh查询结果为List 且是删除后插入的更新方式 下 获取转换结果
     */
    private void getDozerResult(BdcxxConvertBO bdcxxConvertBO, List existObjList, List<ClassMappingMetadata> mappingList) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        // 已存在实体全部存放进删除List
        if(CollectionUtils.isNotEmpty(existObjList)){
            bdcxxConvertBO.getDeleteObjectList().addAll(existObjList);
        }

        // 获取 对照后的实体
        List targetList = new ArrayList();
        for (ClassMappingMetadata mapping : mappingList) {
            List sourceList = bdcxxConvertBO.getRequestDOMap().get(mapping.getSourceClassName());
            if(CollectionUtils.isNotEmpty(sourceList)){
                for (Object source : sourceList) {
                    Object target = mapping.getDestinationClass().newInstance();
                    //  如果存在外键处理方法，先执行处理外键
                    BdcdyxxUtils.setObjectFk(bdcxxConvertBO.getBdcdyxxRequestDTO().getBdcdyh(),target);
                    bdcDozerMapper.map(source, target);
                    targetList.add(target);
                }
            }
        }
        // 存放进 insertList 中
        bdcxxConvertBO.setInsertList(targetList);
    }

    /**
     * @param bdcxxConvertBO
     * @param exsitObj,
     * @param mappingList
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理单个实体更新对照
     */
    private void getDozerResult(BdcxxConvertBO bdcxxConvertBO, Object exsitObj, List<ClassMappingMetadata> mappingList) {
        boolean update = false;
        for (ClassMappingMetadata mapping : mappingList) {
            List sourceList = bdcxxConvertBO.getRequestDOMap().get(mapping.getSourceClassName());
            if(CollectionUtils.isNotEmpty(sourceList)){
                update = true;
                for (Object source : sourceList) {
                    bdcDozerMapper.map(source, exsitObj);
                }
            }
        }
        if(update){
            bdcxxConvertBO.getUpdateList().add(exsitObj);
        }
    }

}
