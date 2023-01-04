package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-25
 * @description
 */
public class FwYchsResource extends ElementResource<FwYchsDO>  {

    /**
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public FwYchsResource(List<FwYchsDO> resReferDOList) {
        super(null, new ResourceDTO());
        this.setResReferDOList(resReferDOList);
    }

    /**
     * @param paramObject
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public FwYchsResource(FwYchsDO paramObject) {
        super(paramObject, new ResourceDTO());
    }

    /**
     * @param
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换成DTO
     */
    @Override
    public ResourceDTO convertDTO() {
        AttributeResouce res = new InfoResource(this).withCode(this.configCode);
        res = new StatusResource(res).withCode(this.configCode);
        res = new LinkResource(res).withCode(this.configCode);
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
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        // 查询户室表列表
        if (CollectionUtils.isNotEmpty(this.getResReferDOList())) {
            List<AbstractResource> elemList = new ArrayList<>();
            for (FwYchsDO fwYchsDO : this.getResReferDOList()) {
                ElementResource elem = new FwYchsResource(fwYchsDO);
                elemList.add(elem);
            }
            resourceDTOList = batchConvertDTO(elemList);
        }
        return resourceDTOList;
    }
}
