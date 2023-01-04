package cn.gtmap.realestate.building.ui.core.vo;

import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-15
 * @description 车库
 */
public class LpbCkVo {

    private String wlcs;

    private String dycs;

    private int rowspan;

    private List<ResourceDTO> ckList;

    private int maxnum;

    public String getWlcs() {
        return wlcs;
    }

    public void setWlcs(String wlcs) {
        this.wlcs = wlcs;
    }

    public String getDycs() {
        return dycs;
    }

    public void setDycs(String dycs) {
        this.dycs = dycs;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }


    public List<ResourceDTO> getCkList() {
        return ckList;
    }

    public void setCkList(List<ResourceDTO> ckList) {
        this.ckList = ckList;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }
}
