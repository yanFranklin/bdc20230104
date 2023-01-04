package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.building.util.LpbUtils;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-10
 * @description
 */
public class BatchStatusResource extends BatchAttributeResource {

    public BatchStatusResource(BatchAttributeResource batAttrRes) {
        super(batAttrRes);
    }

    private List<SSjBdcdyhxsztDO> xsztList;

    /**
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理属性
     */
    @Override
    public void dealAttribute() {
        LOGGER.info("status属性开始");
        if(CollectionUtils.isNotEmpty(this.elemResList)){
            AttributeResouce statusResource;
            // 查询 地籍号 下的 所有BDCDYH 的 现势状态
            setXsztList();
            for(int i = 0 ; i < this.elemResList.size();i++){
                String bdcdyh = MapUtils.getString(LpbUtils.parseObjectToMap(this.elemResList.get(i).paramObject), "bdcdyh");
                if(StringUtils.isNotBlank(bdcdyh)){
                    SSjBdcdyhxsztDO xszt = Container.getBean(BdcdyZtService.class).getSsjBdcdyXsztInList(bdcdyh,this.xsztList);
                    statusResource = new StatusResource(elemResList.get(i),xszt).withCode(this.configCode);
                    statusResource.dealAttribute();
                }
            }
        }
        LOGGER.info("status属性结束");
    }


    public void setXsztList(){
        if(CollectionUtils.isNotEmpty(this.elemResList)){
            for(AbstractResource elem : this.elemResList){
                Map<String, Object> paramMap = LpbUtils.parseObjectToMap(elem.paramObject);
                String fwDcbIndex = MapUtils.getString(paramMap, "fwDcbIndex");
                if(StringUtils.isNotBlank(fwDcbIndex)){
                    this.xsztList = Container.getBean(BdcdyZtService.class).querySsjBdcdyhxsztDOByFwDcbIndex(fwDcbIndex);
                    break;
                }
            }
        }
    }
}
