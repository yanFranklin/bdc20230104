package cn.gtmap.realestate.building.core.resource;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-10
 * @description
 */
public class BatchLinkResource extends BatchAttributeResource{

    public BatchLinkResource(BatchAttributeResource batAttrRes) {
        super(batAttrRes);
    }

    /**
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理属性
     */
    @Override
    public void dealAttribute() {
        LOGGER.info("link属性开始");
        if(CollectionUtils.isNotEmpty(this.elemResList)){
            AttributeResouce linkResource ;
            for(int i = 0 ; i < this.elemResList.size() ; i++){
                linkResource = new LinkResource(elemResList.get(i)).withCode(this.configCode);
                linkResource.dealAttribute();
            }
        }
        LOGGER.info("link属性结束");
    }
}
