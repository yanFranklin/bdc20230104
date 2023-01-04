package cn.gtmap.realestate.building.core.resource;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-14
 * @description 属性 资源 抽象类
 */
public abstract class AttributeResouce extends AbstractResource {

    /**
     * 引入被装饰元素资源
     */
    protected AbstractResource elementResource;

    /**
     * @param resource
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public AttributeResouce(AbstractResource resource) {
        this.paramObject = resource.paramObject;
        this.resouceDTO = resource.resouceDTO;
        this.elementResource = resource;
    }


    /**
     * @param code
     * @return cn.gtmap.realestate.building.core.resource.AttributeResouce
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 配置标志
     */
    @Override
    public AttributeResouce withCode(String code) {
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
        this.elementResource.decorateRes();
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
