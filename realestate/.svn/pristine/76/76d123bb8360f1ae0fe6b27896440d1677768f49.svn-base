package cn.gtmap.realestate.building.service.impl.lpb;

import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.resource.InfoResource;
import cn.gtmap.realestate.building.service.lpb.LpbConfigInfoService;
import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import cn.gtmap.realestate.building.util.LpbUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-16
 * @description 字段类型配置
 */
@Service
public class ColumnTypeServiceImpl extends LpbConfigInfoService{

    @Override
    protected LpbInfoTypeEnum getTypeEnum() {
        return LpbInfoTypeEnum.COLUMN;
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
        return LpbUtils.parseObjectToMap(infoResource.paramObject,configList);
    }
}
