package cn.gtmap.realestate.common.core.vo.etl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;

import java.io.Serializable;
import java.util.List;

/**
 * 批量推送共享库数据vo对象
 */
public class BatchSharedbVO implements Serializable {

    private static final long serialVersionUID = -1006795610209110256L;

    private List<BdcCzrzDO> bdcCzrzDOList;

    public List<BdcCzrzDO> getBdcCzrzDOList() {
        return bdcCzrzDOList;
    }

    public void setBdcCzrzDOList(List<BdcCzrzDO> bdcCzrzDOList) {
        this.bdcCzrzDOList = bdcCzrzDOList;
    }
}
