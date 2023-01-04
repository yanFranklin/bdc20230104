package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.formclient.common.dto.FormElementConfigDTO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.portal.BdcBtxyzDTO;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-10-15
 * @description
 */
@Service
public class BdcBtxYzDefaultServiceImpl extends BdcBtxYzAbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBtxYzDefaultServiceImpl.class);
    /**
     * @param bdcBtxyzDTO
     * @param requiredElements
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证配置了gzlslid的sql
     */
    @Override
    public BdcBtxyzDTO checkPlResult(BdcBtxyzDTO bdcBtxyzDTO, List<FormElementConfigDTO> requiredElements) {
        if (CollectionUtils.isEmpty(bdcBtxyzDTO.getGzlslidSqlList())) {
            return bdcBtxyzDTO;
        }
        Set<String> set = bdcBtxyzDTO.getSet();
        bdcBtxyzDTO.getGzlslidSqlList().forEach(sql -> {
            List<Map<String, Object>> resultList=querySqlPl(bdcBtxyzDTO,sql);
            set.addAll(addNullColumn(resultList, requiredElements, bdcBtxyzDTO.getColumnNameSet()));
        });
        return bdcBtxyzDTO;
    }

    /**
     * @param bdcBtxyzDTO
     * @return requiredElements
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证配置了xmid的sql
     */
    @Override
    public BdcBtxyzDTO checkResult(BdcBtxyzDTO bdcBtxyzDTO, List<FormElementConfigDTO> requiredElements) {
        if (CollectionUtils.isEmpty(bdcBtxyzDTO.getXmidSqlList())) {
            return bdcBtxyzDTO;
        }
        List<BdcXmDO> bdcXmDOList =queryBdcXmList(bdcBtxyzDTO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            return bdcBtxyzDTO;
        }
        Set<String> set = bdcBtxyzDTO.getSet();
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            /**
             * 验证条件
             */
            Map param = Maps.newHashMap();
            param.put("xmid", bdcXmDO.getXmid());
            param.put("gzlslid", bdcBtxyzDTO.getGzlslid());
            param.put("taskId", bdcBtxyzDTO.getTaskId());
            param.put("bdcdyh", bdcXmDO.getBdcdyh());
            bdcBtxyzDTO.getXmidSqlList().forEach(sql -> {
                param.put("sql", sql);
                LOGGER.info("必填项配置：{}sql:{}", bdcBtxyzDTO,sql);
                List<Map<String, Object>> resultList = bdcSjyPzFeignService.yxPzSjy(param);
                set.addAll(addNullColumn(resultList, requiredElements, bdcBtxyzDTO.getColumnNameSet()));
            });
        }
        return bdcBtxyzDTO;
    }



}
