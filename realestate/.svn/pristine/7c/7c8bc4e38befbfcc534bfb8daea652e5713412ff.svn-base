package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.building.web.rest.FwLpbRestController;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-14
 * @description 房屋户室 资源
 */
public class FwHsResouce extends ElementResource<FwHsDO> {

    /**
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public FwHsResouce(List<FwHsDO> resReferDOList) {
        super(null, new ResourceDTO());
        this.setResReferDOList(resReferDOList);
    }

    /**
     * @param paramObject
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public FwHsResouce(FwHsDO paramObject) {
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @description 获取内部资源DTO列表
     */
    @Override
    public List<ResourceDTO> getInnerResourceDTO() {
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        // 查询户室表列表
        if (CollectionUtils.isNotEmpty(this.getResReferDOList())) {
            List<AbstractResource> elemList = new ArrayList<>();
            for (FwHsDO fwhs : this.getResReferDOList()) {
                ElementResource elem = new FwHsResouce(fwhs);
                elemList.add(elem);
            }
            resourceDTOList = this.batchConvertDTO(elemList);
        }
        return resourceDTOList;
    }
}
