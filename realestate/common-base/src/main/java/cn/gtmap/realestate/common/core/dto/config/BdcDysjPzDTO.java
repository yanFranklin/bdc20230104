package cn.gtmap.realestate.common.core.dto.config;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/2/21
 * @description 打印配置数据DTO
 */
public class BdcDysjPzDTO extends BdcDysjPzDO {
    /**
     * 字表信息
     */
    List<BdcDysjZbPzDO> bdcDysjZbPzDOList;

    public List<BdcDysjZbPzDO> getBdcDysjZbPzDOList() {
        return bdcDysjZbPzDOList;
    }

    public void setBdcDysjZbPzDOList(List<BdcDysjZbPzDO> bdcDysjZbPzDOList) {
        this.bdcDysjZbPzDOList = bdcDysjZbPzDOList;
    }

    private boolean fuZhi;

    public boolean isFuZhi() {
        return fuZhi;
    }

    public void setFuZhi(boolean fuZhi) {
        this.fuZhi = fuZhi;
    }

    @Override
    public String toString() {
        return "BdcDysjPzDTO{" +
                "bdcDysjZbPzDOList=" + bdcDysjZbPzDOList +
                '}';
    }
}
