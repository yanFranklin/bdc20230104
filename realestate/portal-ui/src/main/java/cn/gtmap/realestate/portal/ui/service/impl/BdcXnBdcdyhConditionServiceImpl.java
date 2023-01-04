package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.formclient.common.dto.FormElementConfigDTO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.portal.BdcBtxyzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-10-12
 * @description 虚拟不动产单元号 必填项验证
 */
@Service
public class BdcXnBdcdyhConditionServiceImpl extends BdcBtxYzAbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXnBdcdyhConditionServiceImpl.class);

    /**
     * @param bdcBtxyzDTO
     * @param requiredElements
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证批量流程情况
     */
    @Override
    public BdcBtxyzDTO checkPlResult(BdcBtxyzDTO bdcBtxyzDTO, List<FormElementConfigDTO> requiredElements) {
        if (CollectionUtils.isEmpty(bdcBtxyzDTO.getGzlslidSqlList())) {
            return bdcBtxyzDTO;
        }
        if (!bdcBtxyzDTO.getColumnNameSet().contains(CommonConstantUtils.BDCDYH)) {
            throw new AppException("配置的验证批量数据的SQL中不包括不动产单元号，请修改SQL！");
        }
        Set<String> set = bdcBtxyzDTO.getSet();
        bdcBtxyzDTO.getGzlslidSqlList().forEach(sql -> {
            List<Map<String, Object>> resultList = querySqlPl(bdcBtxyzDTO, sql);
            /**
             * 查询结果为空，提示 该条sql所有字段
             */
            if(CollectionUtils.isEmpty(resultList)){
                set.addAll(addNullColumn(resultList, requiredElements, bdcBtxyzDTO.getColumnNameSet()));
                return;
            }
            resultList = resultList.stream().filter(map -> !BdcdyhToolUtils.checkXnbdcdyh((String) map.get("BDCDYH"))).collect(Collectors.toList());
            /**
             * 过滤虚拟不动产单元之后结果为空，则不提示
             */
            set.addAll(addNullColumnPl(resultList, requiredElements, bdcBtxyzDTO.getColumnNameSet()));
        });
        return bdcBtxyzDTO;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public BdcBtxyzDTO checkResult(BdcBtxyzDTO bdcBtxyzDTO, List<FormElementConfigDTO> requiredElements) {
        if (CollectionUtils.isEmpty(bdcBtxyzDTO.getXmidSqlList())) {
            return bdcBtxyzDTO;
        }
        List<BdcXmDO> bdcXmDOList = queryBdcXmList(bdcBtxyzDTO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return bdcBtxyzDTO;
        }
        Set<String> set = bdcBtxyzDTO.getSet();
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            if (BdcdyhToolUtils.checkXnbdcdyh(bdcXmDO.getBdcdyh())) {
                continue;
            }
            Map param = Maps.newHashMap();
            param.put("xmid", bdcXmDO.getXmid());
            param.put("gzlslid", bdcBtxyzDTO.getGzlslid());
            param.put("taskId", bdcBtxyzDTO.getTaskId());
            param.put("bdcdyh", bdcXmDO.getBdcdyh());
            bdcBtxyzDTO.getXmidSqlList().forEach(sql -> {
                param.put("sql", sql);
                LOGGER.info("{}：必填项配置：bdcBtxyzDTO", bdcBtxyzDTO);
                List<Map<String, Object>> resultList = bdcSjyPzFeignService.yxPzSjy(param);
                set.addAll(addNullColumn(resultList, requiredElements, bdcBtxyzDTO.getColumnNameSet()));
            });
        }
        return bdcBtxyzDTO;
    }

}
