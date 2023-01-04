package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.common.core.dto.building.FwhsQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: realestate
 * @description: 房屋户室包含权利人资源
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-03 09:36
 **/
public class FwhsQlrResource extends ElementResource<FwhsQlrDTO> {
    public FwhsQlrResource(List<FwhsQlrDTO> paramObject) {
        super(null, new ResourceDTO());
        this.setResReferDOList(paramObject);
    }

    /**
     * @param paramObject
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public FwhsQlrResource(FwhsQlrDTO paramObject) {
        super(paramObject, new ResourceDTO());
    }

    /**
     * @return S
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 生成DTO 抽象方法
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
            for (FwhsQlrDTO fwhsQlrDTO : this.getResReferDOList()) {
                ElementResource elem = new FwhsQlrResource(fwhsQlrDTO);
                elemList.add(elem);
            }
            resourceDTOList = this.batchConvertDTO(elemList);
        }
        return resourceDTOList;
    }
}
