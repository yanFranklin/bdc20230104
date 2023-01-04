package cn.gtmap.realestate.building.service.lpb;

import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.resource.InfoResource;
import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-16
 * @description 楼盘表配置INFO类型处理服务
 */
public abstract class LpbConfigInfoService {

    public LpbConfigInfoService(){
        this.lpbInfoTypeEnum = getTypeEnum();
    }

    public LpbInfoTypeEnum lpbInfoTypeEnum;

    public void dealInfo(InfoResource infoResource, List<ColumnBO> allConfigList){
        List<ColumnBO> configList = revertConfigBo(allConfigList);
        Map<String,Object> resultMap = abstractDealInfo(infoResource,configList);
        Map<String,Object> infoMap = infoResource.resouceDTO.getInfo();
        infoMap.putAll(resultMap);
    }

    public List<ColumnBO> revertConfigBo(List<ColumnBO> allConfigList){
        List<ColumnBO> columnConfigList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(allConfigList)){
            for(int i = 0 ; i < allConfigList.size();i++){
                ColumnBO columnBO = allConfigList.get(i);
                if(columnBO != null){
                    String type = StringUtils.isNotBlank(columnBO.getType())?columnBO.getType():LpbInfoTypeEnum.COLUMN.type;
                    if(StringUtils.equals(type,lpbInfoTypeEnum.type)){
                        columnConfigList.add(columnBO);
                    }
                }
            }
        }
        return columnConfigList;
    }

    protected abstract LpbInfoTypeEnum getTypeEnum();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param configList
     * @return void
     * @description 处理服务
     */
    public abstract Map<String,Object> abstractDealInfo(InfoResource infoResource, List<ColumnBO> configList);
}
