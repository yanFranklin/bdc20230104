package cn.gtmap.realestate.check.utils;


import cn.gtmap.realestate.check.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hqz
 * @version 1.0, 2016-12-1
 * @description 规则日志数据库操作类
 */
@Component
public class GzjcLogUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GzjcLogUtil.class);
    /**
     * 规则日志数据库操作方法
     *
     * @param checkGzjcLogDOList 规则日志类，包含一些规则的基础信息
     * @param entityMapper       entity对象Mapper
     * @param bdcXmMapper        bdcXm对象Mapper
     * @return
     * @author lst
     * @description 根据项目proid, 规则id, 限制文号比对，相同记录更新，不同记录插入
     */
    public static void addGzjcLog(List<CheckGzjcLogDO> checkGzjcLogDOList, EntityMapper entityMapper, BdcXmMapper bdcXmMapper, List<CheckGzjcLogDO> logList) {
        LOGGER.warn("当前规则检查日志入库数据量{}", CollectionUtils.size(checkGzjcLogDOList));
        List<CheckGzjcLogDO> insertList = new ArrayList<>(10);
        List<CheckGzjcLogDO> updateNonNullList = new ArrayList<>(100);
        List<CheckGzjcLogDO> updateNullList = new ArrayList<>(100);
        for (CheckGzjcLogDO bdcGzjcLog : checkGzjcLogDOList) {
            if (StringUtils.isNotBlank(bdcGzjcLog.getXmid()) && StringUtils.isNotBlank(bdcGzjcLog.getGzid())) {
                Map<String, String> example = new HashMap<String, String>();
                example.put("xmid", bdcGzjcLog.getXmid());
                example.put("gzid", bdcGzjcLog.getGzid());
                if (StringUtils.isNotBlank(bdcGzjcLog.getXzwh())) {
                    example.put("xzwh", bdcGzjcLog.getXzwh());
                }
                if (StringUtils.isNotBlank(bdcGzjcLog.getType())) {
                    example.put("type", bdcGzjcLog.getType());
                }
                if (StringUtils.isNotBlank(bdcGzjcLog.getXzwhxmid())) {
                    example.put("xzwhxmid", bdcGzjcLog.getXzwhxmid());
                }
                List<CheckGzjcLogDO> bdcGzjcLogLst = bdcXmMapper.selectAllBdcGzjcLog(example);
                if (CollectionUtils.isNotEmpty(bdcGzjcLogLst)) {
                    //去重
                    bdcGzjcLogLst = bdcGzjcLogLst.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(CheckGzjcLogDO::getGzid))), ArrayList::new));
                    LOGGER.warn("当前规则{}已存在规则信息数量{},查询参数{}", JSON.toJSONString(bdcGzjcLog), CollectionUtils.size(bdcGzjcLogLst), JSON.toJSONString(example));
                    for (CheckGzjcLogDO oldBdcGzjcLog : bdcGzjcLogLst) {
                        Integer sfgq = oldBdcGzjcLog.getSfgq();
                        if (sfgq != null && Constants.GZJC_GQZT.equals(sfgq)) {
                            continue;
                        }
                        bdcGzjcLog.setSfgq(bdcGzjcLog.getSfgq() != null ? bdcGzjcLog.getSfgq() : sfgq);
                        bdcGzjcLog.setLogid(oldBdcGzjcLog.getLogid());
                        bdcGzjcLog.setGxsj(new Date(System.currentTimeMillis()));
                        bdcGzjcLog.setFxsj(oldBdcGzjcLog.getFxsj());
                        //如果是通过的
                        if (Constants.GZJC_JJZT.equals(bdcGzjcLog.getJjzt())) {
                            updateNonNullList.add(bdcGzjcLog);
                        } else {
                            updateNullList.add(bdcGzjcLog);
                        }
                    }
                    //没日志记录并且没通过的新增日志
                } else if (!Constants.GZJC_JJZT.equals(bdcGzjcLog.getJjzt())) {
                    bdcGzjcLog.setLogid(UUIDGenerator.generate());
                    bdcGzjcLog.setFxsj(new Date(System.currentTimeMillis()));
                    bdcGzjcLog.setJjzt(Constants.GZJC_WJJZT);
                    insertList.add(bdcGzjcLog);
                    logList.add(bdcGzjcLog);
                }
            } else {
                LOGGER.warn("当前入库日志没有项目id或者规则id{}", JSON.toJSONString(bdcGzjcLog));
            }
        }
        //1。需要新增的做批量插入
        if (CollectionUtils.isNotEmpty(insertList)) {
            String singleInsert = EnvironmentConfig.getEnvironment().getProperty("singleInsert");
            LOGGER.warn("新增日志数量{}是否单个新增{}", insertList.size(), singleInsert);
            if ("true".equals(singleInsert)) {
                for (int i = 0; i < insertList.size(); i++) {
                    LOGGER.warn("单条数据新增，第{}条，项目id={},规则id={}", i, insertList.get(i).getXmid(), insertList.get(i).getGzid());
                    entityMapper.insertSelective(insertList.get(i));
                }
            } else {
                if (CollectionUtils.size(insertList) > 400) {
                    List partList = ListUtils.subList(insertList, 400);
                    for (int i = 0; i < partList.size(); i++) {
                        List<CheckGzjcLogDO> part = (List<CheckGzjcLogDO>) partList.get(i);
                        LOGGER.warn("分组新增日志数量当前第{}组数据量{},当前应用处理器数量{}当前应用内存{},剩余内存{}", i, part.size(), Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory());
                        entityMapper.insertBatchSelective(part);
                    }
                } else {
                    entityMapper.insertBatchSelective(insertList);
                }
            }

        }
        //2. 空值更新
        if (CollectionUtils.isNotEmpty(updateNullList)) {
            LOGGER.warn("空值更新数据库数量循环更新{}", updateNullList.size());
            for (CheckGzjcLogDO checkGzjcLogDO : updateNullList) {
                entityMapper.updateByPrimaryKeyNull(checkGzjcLogDO);
            }
        }
        //3.不空值更新
        if (CollectionUtils.isNotEmpty(updateNonNullList)) {
            LOGGER.warn("非空直批量更新数据量{}", updateNonNullList.size());
            entityMapper.batchSaveSelective(updateNonNullList);
        }
    }

    /**
     * 获取空字段
     * @param source
     * @author lst
     * @return
     */
    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null){
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
