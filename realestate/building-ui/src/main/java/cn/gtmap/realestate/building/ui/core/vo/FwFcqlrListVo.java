package cn.gtmap.realestate.building.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;

import java.util.List;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/13
 * @description
 */
public class FwFcqlrListVo {
    String fwIndex;

    List<FwFcqlrDO> qlrList;

    public String getFwIndex() {
        return fwIndex;
    }

    public void setFwIndex(String fwIndex) {
        this.fwIndex = fwIndex;
    }

    public List<FwFcqlrDO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<FwFcqlrDO> qlrList) {
        this.qlrList = qlrList;
    }
}
