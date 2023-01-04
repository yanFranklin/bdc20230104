package cn.gtmap.realestate.building.service.impl.lpb;

import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.resource.InfoResource;
import cn.gtmap.realestate.building.service.lpb.LpbConfigInfoService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import cn.gtmap.realestate.building.util.LpbUtils;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-17
 * @description 字典类型
 */
@Service
public class ZdColumnTypeServiceImpl extends LpbConfigInfoService {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZdColumnTypeServiceImpl.class);

    @Autowired
    private BdcZdCache bdcZdCache;

    @Override
    protected LpbInfoTypeEnum getTypeEnum() {
        return LpbInfoTypeEnum.ZDCOLUMN;
    }

    /**
     * @param infoResource
     * @param configList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理服务
     */
    @Override
    public Map<String, Object> abstractDealInfo(InfoResource infoResource, List<ColumnBO> configList) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        // 先获取 DM 值
        Map<String, Object> dmInfoMap = LpbUtils.parseObjectToMap(infoResource.paramObject);
        if(MapUtils.isNotEmpty(dmInfoMap)){
            for(ColumnBO config : configList){
                Map<String,Object> tempMap = new HashMap<>();
                tempMap.put("desc",config.getDesc());
                tempMap.put("value","");
                if(StringUtils.isNotBlank(config.getValue())
                        && StringUtils.isNotBlank(config.getZdDoClass())){
                    // 循环配置，获取代码值
                    String dmVal = MapUtils.getString(dmInfoMap,config.getValue());
                    if(StringUtils.isNotBlank(dmVal)){
                        try {
                            Class zdClass = Class.forName(Constants.COMMON_DOMAIN_PATH + "." + config.getZdDoClass());
                            List<Map> resultList = bdcZdCache.getZdTableList(zdClass.getSimpleName(),zdClass);
                            if(CollectionUtils.isNotEmpty(resultList)){
                                // 字典项 循环 与DM 值匹配
                                for(Map zdMap : resultList){
                                    if(StringUtils.equals(dmVal,MapUtils.getString(zdMap,"DM"))){
                                        tempMap.put("value",MapUtils.getString(zdMap,"MC"));
                                        tempMap.put("dm",MapUtils.getString(zdMap,"DM"));
                                    }
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            LOGGER.error("字典转换失败：{}",config.getZdDoClass(),e);
                        }
                    }
                }
                resultMap.put(config.getValue(),tempMap);
            }
        }
        return resultMap;
    }
}
