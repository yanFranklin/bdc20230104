package cn.gtmap.realestate.building.service.impl.lpb;

import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.resource.InfoResource;
import cn.gtmap.realestate.building.service.lpb.LpbConfigInfoService;
import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import cn.gtmap.realestate.building.util.LpbUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-28
 * @description
 */
@Service
public class ConstantTypeServiceImpl extends LpbConfigInfoService {
    @Override
    protected LpbInfoTypeEnum getTypeEnum() {
        return LpbInfoTypeEnum.CONSTANT;
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
        if(CollectionUtils.isNotEmpty(configList)){
            for(ColumnBO config : configList){
                if(StringUtils.isNotBlank(config.getValue())){
                    Map<String,Object> btnMap = LpbUtils.parseObjectToMap(config);
                    resultMap.put(config.getValue(),btnMap);
                }
            }
        }
        return resultMap;
    }
}
