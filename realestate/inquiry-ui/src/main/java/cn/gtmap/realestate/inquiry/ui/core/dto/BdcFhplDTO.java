package cn.gtmap.realestate.inquiry.ui.core.dto;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;

public class BdcFhplDTO {
    private String slbh;
    private BdcSlSfxxDO bdcSlSfxxDO;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public BdcSlSfxxDO getBdcSlSfxxDO() {
        return bdcSlSfxxDO;
    }

    public void setBdcSlSfxxDO(BdcSlSfxxDO bdcSlSfxxDO) {
        this.bdcSlSfxxDO = bdcSlSfxxDO;
    }
}
