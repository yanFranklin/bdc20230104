package cn.gtmap.realestate.building.core.decorator.status;

import cn.gtmap.realestate.building.core.decorator.StatusDecorator;
import cn.gtmap.realestate.building.core.resource.StatusResource;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.FwHsStatusEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-26
 * @description 登记状态
 */
public class DjStatus extends StatusDecorator {

    /**
     * @param resource
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public DjStatus(StatusResource resource) {
        super(resource);
    }

    /**
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询状态数量
     */
    @Override
    public Integer getStatusCount() {
        if (this.sSjBdcdyhxsztDO != null
                && StringUtils.equals(Constants.BDCDY_XS_ZT_DJZT, this.sSjBdcdyhxsztDO.getDjzt())) {
            return 1;
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
        return FwHsStatusEnum.DJ;
    }
}
