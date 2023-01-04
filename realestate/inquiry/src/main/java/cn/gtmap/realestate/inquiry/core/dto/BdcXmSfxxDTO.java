package cn.gtmap.realestate.inquiry.core.dto;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;

public class BdcXmSfxxDTO extends BdcSlSfxxDO {
    private String qlrmc;
    private String gzldymc;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }
}
