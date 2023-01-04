package cn.gtmap.realestate.building.service.impl.lpb;

import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.resource.InfoResource;
import cn.gtmap.realestate.building.service.lpb.LpbConfigInfoService;
import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-21
 * @description  空逻辑判断
 */
@Service
public class NvlColumnTypeServiceImpl extends LpbConfigInfoService {
    @Override
    protected LpbInfoTypeEnum getTypeEnum() {
        return LpbInfoTypeEnum.NVLCOLUMN;
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
        Map<String,Object> infoMap = infoResource.resouceDTO.getInfo();
        Map<String, Object> resultMap = new LinkedHashMap<>();
        for(ColumnBO config : configList){
            String[] arr = config.getNvl().split(",");
            if(arr.length == 2) {
                String preParam = arr[0];
                String nextParam = arr[1];
                String result = MapUtils.getString(MapUtils.getMap(infoMap, preParam), "value");
                //为空或者当前为数值且为0 的情况下
                if (StringUtils.isBlank(result) || NumberUtils.isNumber(result) && Objects.equals(0.0, NumberUtils.toDouble(result, 0.0))) {
                    result = MapUtils.getString(MapUtils.getMap(infoMap, nextParam), "value");
                }
                if (StringUtils.isBlank(result)) {
                    result = "";
                }
                Map<String, Object> temp = new HashMap<>();
                temp.put("desc", config.getDesc());
                temp.put("value", result);
                resultMap.put(config.getValue(), temp);
            }
        }
        return resultMap;
    }
}
