package cn.gtmap.realestate.building.core.decorator.status;

import cn.gtmap.realestate.building.core.decorator.StatusDecorator;
import cn.gtmap.realestate.building.core.resource.StatusResource;
import cn.gtmap.realestate.building.util.FwHsStatusEnum;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-19
 * @description 存量房预售状态
 */
public class ClfysStatus extends StatusDecorator {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param resource
     * @return
     * @description 构造函数
     */
    public ClfysStatus(StatusResource resource) {
        super(resource);
    }

    /**
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询状态数量
     */
    @Override
    public Integer getStatusCount() {
        if (this.sSjBdcdyhxsztDO != null) {
            return this.sSjBdcdyhxsztDO.getClfys();
        }
        return 0;
    }

    /**
     * @return cn.gtmap.realestate.building.util.FwHsStatusEnum
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 状态枚举
     */
    @Override
    public FwHsStatusEnum statusEnum() {
        return FwHsStatusEnum.CLFYS;
    }
}
