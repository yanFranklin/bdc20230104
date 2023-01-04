package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-14
 * @param <T> 参数实体
 * @description 元素资源
 */
public abstract class ElementResource<T> extends AbstractResource<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElementResource.class);

    /**
     * 内部资源
     */
    private ElementResource innerElementResource;

    /**
     * 内部资源 来源实体 列表 即内部资源DTO的paramObject集合
     */
    private List<T> resReferDOList;

    public ElementResource(T paramObject,
                           ResourceDTO resouceDTO,
                           ElementResource innerElementResource){
        super(paramObject, resouceDTO);
        this.innerElementResource = innerElementResource;
    }

    /**
     * @param paramObject
     * @param resouceDTO
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public ElementResource(T paramObject, ResourceDTO resouceDTO) {
        super(paramObject, resouceDTO);
    }

    /**
     * @param code
     * @return cn.gtmap.realestate.building.core.resource.ElementResource<T   ,   S>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    @Override
    public ElementResource<T> withCode(String code) {
        this.configCode = code;
        return this;
    }

    /**
     * @param
     * @return S
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 生成DTO 抽象方法
     */
    public abstract ResourceDTO convertDTO();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param elementResourceList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @description 批量 转换 DTO
     */
    public List<ResourceDTO> batchConvertDTO(List<AbstractResource> elementResourceList) {
        LOGGER.info("批量转换资源");
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(elementResourceList)){
            BatchAttributeResource res = new BatchInfoResource(elementResourceList).withCode(this.configCode);
            res = new BatchStatusResource(res).withCode(this.configCode);
            res = new BatchLinkResource(res).withCode(this.configCode);
            LOGGER.info("资源装饰属性开始");
            res.decorateRes();
            LOGGER.info("资源装饰属性结束");
            for(AbstractResource elem : elementResourceList){
                if(elem.resouceDTO != null){
                    resourceDTOList.add(elem.resouceDTO);
                }
            }
        }
        return resourceDTOList;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.ResourceDTO>
     * @description 获取内部资源DTO列表
     */
    public abstract List<ResourceDTO> getInnerResourceDTO();

    /**
     * @param
     * @return cn.gtmap.realestate.common.core.dto.building.ResourceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 刷新DTO 重新实例化
     */
    public ResourceDTO refreshDTO() {
        return new ResourceDTO();
    }

    /**
     * @param paramObject
     * @return cn.gtmap.realestate.building.core.resource.ElementResource
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 刷新当前资源的参数实体与DTO，避免批量操作时，重新实例化元素资源
     */
    public ElementResource refresh(T paramObject) {
        this.paramObject = paramObject;
        this.resouceDTO = refreshDTO();
        return this;
    }

    public ElementResource getInnerElementResource() {
        return innerElementResource;
    }

    public void setInnerElementResource(ElementResource innerElementResource) {
        this.innerElementResource = innerElementResource;
    }

    public List<T> getResReferDOList() {
        return resReferDOList;
    }

    public void setResReferDOList(List<T> resReferDOList) {
        this.resReferDOList = resReferDOList;
    }
}
