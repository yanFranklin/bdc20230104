package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.building.util.FwHsStatusEnum;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-02
 * @description  不动产状态资源
 */
public class BdcdyhZtResource extends ElementResource<Map<String,String>> {

    private SSjBdcdyhxsztDO sSjBdcdyhxsztDO;

    public BdcdyhZtResource(Map<String,String> paramObject){
        super(paramObject,new ResourceDTO());
    }

    public BdcdyhZtResource(SSjBdcdyhxsztDO sSjBdcdyhxsztDO){
        super(new HashMap<>(),new ResourceDTO());
        this.sSjBdcdyhxsztDO = sSjBdcdyhxsztDO;
    }
    /**
     * @param paramObject
     * @param resouceDTO
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public BdcdyhZtResource(Map<String,String> paramObject, ResourceDTO resouceDTO) {
        super(paramObject, resouceDTO);
    }

    /**
     * @return S
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 生成DTO 抽象方法
     */
    @Override
    public ResourceDTO convertDTO() {
        StatusResource res = new StatusResource(this);
        res.setXsztDO(sSjBdcdyhxsztDO);
        res.setStatusList(FwHsStatusEnum.getAllStatus());
        res.decorateRes();
        return this.resouceDTO;
    }

    /**
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取内部资源DTO列表
     */
    @Override
    public List<ResourceDTO> getInnerResourceDTO() {
        return null;
    }
}
