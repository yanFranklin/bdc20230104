package cn.gtmap.realestate.building.core.decorator.status;

import cn.gtmap.realestate.building.core.decorator.StatusDecorator;
import cn.gtmap.realestate.building.core.resource.StatusResource;
import cn.gtmap.realestate.building.util.FwHsStatusEnum;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-23
 * @description 发证状态
 */
public class BfzStatus extends StatusDecorator {

    /**
     * @param resource
     * @return
     * @description 构造函数
     */
    public BfzStatus(StatusResource resource) {
        super(resource);
    }

    /**
     * @return java.lang.Integer
     * @description 查询状态数量
     */
    @Override
    public Integer getStatusCount() {
        return 0;
    }

    /**
     * @return cn.gtmap.realestate.building.util.FwHsStatusEnum
     * @description 状态枚举
     */
    @Override
    public FwHsStatusEnum statusEnum() {
        return FwHsStatusEnum.BFZ;
    }
}
