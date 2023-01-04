package cn.gtmap.realestate.register.core.dto;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 新合并生成的单元相关信息
 */
public class BdcXhbBdcdyDTO {
    /**
     * 合并后新的项目信息
     */
    private BdcXmDO newBdcXmDO;

    /**
     * 合并后新的证书信息
     */
    private BdcZsDO newBdcZsDO;


    public BdcXmDO getNewBdcXmDO() {
        return newBdcXmDO;
    }

    public void setNewBdcXmDO(BdcXmDO newBdcXmDO) {
        this.newBdcXmDO = newBdcXmDO;
    }

    public BdcZsDO getNewBdcZsDO() {
        return newBdcZsDO;
    }

    public void setNewBdcZsDO(BdcZsDO newBdcZsDO) {
        this.newBdcZsDO = newBdcZsDO;
    }

    @Override
    public String toString() {
        return "BdcXhbBdcdyDTO{" +
                "newBdcXmDO=" + newBdcXmDO +
                ", newBdcZsDO=" + newBdcZsDO +
                '}';
    }
}
