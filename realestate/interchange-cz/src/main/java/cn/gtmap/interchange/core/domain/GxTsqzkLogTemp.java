package cn.gtmap.interchange.core.domain;

import javax.persistence.Table;

@Table(name = "gx_tsqzk_log_temp")

public class GxTsqzkLogTemp extends GxTsqzkLog {
    private String chainlog;
    private String ylogid;

    public String getChainlog() {
        return chainlog;
    }

    public void setChainlog(String chainlog) {
        this.chainlog = chainlog;
    }

    public String getYlogid() {
        return ylogid;
    }

    public void setYlogid(String ylogid) {
        this.ylogid = ylogid;
    }
}
