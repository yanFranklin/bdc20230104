package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-20
 * @description 楼盘表资源服务
 */
public class LpbResource extends ElementResource<FwLjzDO> {

    public LpbResource(FwLjzDO paramObject){
        super(paramObject,new ResourceDTO());
    }

    /**
     * @param paramObject
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public LpbResource(FwLjzDO paramObject,ElementResource elementResource) {
        super(paramObject, new ResourceDTO(),elementResource);
    }


    /**
     * @param
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换DTO
     */
    @Override
    public ResourceDTO convertDTO() {
        if (paramObject != null) {
            // 获取内部资源
            if(this.getInnerElementResource() != null){
                ElementResource innerResource = this.getInnerElementResource();
                // 制定配置文件
                innerResource.withCode(this.configCode);
                this.resouceDTO.setResourceDTOList(innerResource.getInnerResourceDTO());
            }
            AttributeResouce res = new LinkResource(this, LpbConfig.FLAG_LJZ).withCode(this.configCode);
            res = new InfoResource(res, LpbConfig.FLAG_LJZ).withCode(this.configCode);
            // 装配 LINK
            res.decorateRes();
            return this.resouceDTO;
        }
        return null;
    }

    @Override
    public List<ResourceDTO> getInnerResourceDTO() {
        return null;
    }

}
