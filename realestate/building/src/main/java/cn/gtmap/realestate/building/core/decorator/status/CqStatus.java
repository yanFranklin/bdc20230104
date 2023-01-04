package cn.gtmap.realestate.building.core.decorator.status;

import cn.gtmap.realestate.building.core.decorator.StatusDecorator;
import cn.gtmap.realestate.building.core.resource.StatusResource;
import cn.gtmap.realestate.building.util.FwHsStatusEnum;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-01
 * @description 草签状态
 */
public class CqStatus extends StatusDecorator {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param resource
     * @return
     * @description 构造函数
     */
    public CqStatus(StatusResource resource) {
        super(resource);
    }

    /**
     * @param
     * @return java.lang.Integer
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询状态数量
     */
    @Override
    public Integer getStatusCount() {
        if(this.sSjBdcdyhxsztDO != null){
            //  ks ys是00,10,11,12，代表不可售，可售，已售，草签
            Integer ks = this.sSjBdcdyhxsztDO.getKs() != null?this.sSjBdcdyhxsztDO.getKs():0;
            Integer ys = this.sSjBdcdyhxsztDO.getYs() != null?this.sSjBdcdyhxsztDO.getYs():0;
            // 根据 YS字段判断是否草签   2为 草签
            if (ks == 1 && ys == 2) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @param
     * @return cn.gtmap.realestate.building.util.FwHsStatusEnum
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 状态枚举
     */
    @Override
    public FwHsStatusEnum statusEnum() {
        return FwHsStatusEnum.CQ;
    }
}
