package cn.gtmap.realestate.exchange.core.bo.grdacx;

import cn.gtmap.realestate.common.core.domain.BdcBdcdjbZdjbxxDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-02
 * @description
 */
public class BdcFdcqQlxxBO extends QlxxBO<BdcFdcqDO>{

    private BdcBdcdjbZdjbxxDO zdjbxxDO;

    public BdcBdcdjbZdjbxxDO getZdjbxxDO() {
        return zdjbxxDO;
    }

    public void setZdjbxxDO(BdcBdcdjbZdjbxxDO zdjbxxDO) {
        this.zdjbxxDO = zdjbxxDO;
    }
}
