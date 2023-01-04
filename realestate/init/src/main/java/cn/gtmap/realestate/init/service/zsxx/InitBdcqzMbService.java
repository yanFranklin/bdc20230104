package cn.gtmap.realestate.init.service.zsxx;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;

/**
 * 初始化不动产权证服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
public abstract class InitBdcqzMbService{


    /**
     * 通过服务获取证书模板
     *
     * @param bdcXmDO
     * @return
     */
    public abstract BdcZsDO queryZsMbServiceData(BdcXmDO bdcXmDO);

    /**
     * 获取服务版本
     *
     * @return
     */
    public String getVersion() {
        return null;
    }

    /**
     * 获取权利类型
     * @return
     */
    public abstract Integer[] getQllx();
}
