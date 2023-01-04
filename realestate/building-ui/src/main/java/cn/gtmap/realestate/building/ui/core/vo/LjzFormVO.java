package cn.gtmap.realestate.building.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/24
 * @description
 */
public class LjzFormVO extends FwLjzDO {
    /**
     * 权利人list
     */
    private List<FwFcqlrDO> qlrList;


    public List<FwFcqlrDO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<FwFcqlrDO> qlrList) {
        this.qlrList = qlrList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LjzFormVO{");
        sb.append(", qlrList=").append(qlrList);
        sb.append('}');
        return sb.toString();
    }
}