package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.DjxxMapper;
import cn.gtmap.realestate.building.core.service.ZdQsdcService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-19
 * @description 宗地权属调查信息
 */
@Service
public class ZdQsdcServiceImpl implements ZdQsdcService {


    @Autowired
    private DjxxMapper djxxMapper;

    /**
     *  @param qsdcTable
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询宗地权属调查信息
     */
    @Override
    public ZdQsdcDO queryQsdcByDjh(String djh,String qsdcTable) {
        if(StringUtils.isNotBlank(djh)){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("djh",djh);
            if(StringUtils.isBlank(qsdcTable)){
                qsdcTable="zd_qsdc";
            }
            paramMap.put("qsdcTable",qsdcTable);
            List<Map<String, Object>> mapList = djxxMapper.queryQsdcList(paramMap);
            if(CollectionUtils.isNotEmpty(mapList)){
                return (ZdQsdcDO)BuildingUtils.map2Bean(mapList.get(0),ZdQsdcDO.class);
            }
        }
        return null;
    }
}
