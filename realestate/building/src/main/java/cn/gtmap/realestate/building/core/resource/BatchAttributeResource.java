package cn.gtmap.realestate.building.core.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-10
 * @description 批量处理属性资源
 */
public abstract class BatchAttributeResource extends AbstractResource{

    protected static Logger LOGGER = LoggerFactory.getLogger(BatchAttributeResource.class);

    /**
     * 引入被装饰元素资源
     */
    protected AbstractResource elementResource;

    protected List<AbstractResource> elemResList;


    public BatchAttributeResource(List<AbstractResource> elemResList){
        this.elemResList =  elemResList;
    }


    public BatchAttributeResource(BatchAttributeResource batAttrRes){
        this.elementResource = batAttrRes;
        this.elemResList = batAttrRes.elemResList;
    }

    /**
     * @param code
     * @return cn.gtmap.realestate.building.core.resource.AbstractResource
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 指定配置标志 抽象方法 在属性和元素Resouce中分别实现
     */
    @Override
    public BatchAttributeResource withCode(String code) {
        this.configCode = code;
        return this;
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 装饰属性资源方法
     */
    @Override
    public void decorateRes() {
        if(this.elementResource != null){
            this.elementResource.decorateRes();
        }
        this.dealAttribute();
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理属性
     */
    public abstract void dealAttribute();
}
