package cn.gtmap.realestate.building.core.resource;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-10
 * @description
 */
public class BatchInfoResource extends BatchAttributeResource{

    public BatchInfoResource(List<AbstractResource> elemResList){
        super(elemResList);
    }

    public BatchInfoResource(BatchAttributeResource batAttrRes) {
        super(batAttrRes);
    }

    /**
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理属性
     */
    @Override
    public void dealAttribute() {
        LOGGER.info("info属性开始");
        if(CollectionUtils.isNotEmpty(this.elemResList)){
            AttributeResouce infoResource ;
            for(int i = 0;i<this.elemResList.size();i++){
                infoResource = new InfoResource(elemResList.get(i)).withCode(this.configCode);
                infoResource.dealAttribute();
            }
        }
        LOGGER.info("info属性结束");
    }
}
