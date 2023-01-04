package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @param <T> 主体参数实体
 * @version 1.0  2018-11-15
 * @description 楼盘表查询服务资源 抽象类
 */
public abstract class AbstractResource<T> {

    /**
     * 默认配置标志
     */
    protected String configCode = "default";

    /**
     * 参数实体
     */
    public T paramObject;

    /**
     * 返回资源DTO实体
     */
    public ResourceDTO resouceDTO;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param[]
     * @return
     * @description 构造函数
     */
    public AbstractResource() {
    }

    /**
     * @param paramObject
     * @param resouceDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public AbstractResource(T paramObject, ResourceDTO resouceDTO) {
        this.paramObject = paramObject;
        this.resouceDTO = resouceDTO;
    }


    /**
     * 装饰资源 属性资源抽象方法
     */
    public void decorateRes() {
    }

    /**
     * @param code
     * @return cn.gtmap.realestate.building.core.resource.AbstractResource<T   ,   S>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 指定配置标志 抽象方法 在属性和元素Resouce中分别实现
     */
    public abstract AbstractResource<T> withCode(String code);


}
