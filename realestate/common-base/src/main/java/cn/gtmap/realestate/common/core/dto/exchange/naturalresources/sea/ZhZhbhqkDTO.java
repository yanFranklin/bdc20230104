package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.sea;

import java.io.Serializable;
import java.util.Date;

public class ZhZhbhqkDTO implements Serializable {
    
    private static final long serialVersionUID = -8897284131434482585L;

    private String zhdm;//varchar2
    private String bhyy;//varchar2
    private String bhnr;//varchar2
    private Date djsj;//date
    private String dbr;//varchar2

    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }

    public String getBhyy() {
        return bhyy;
    }

    public void setBhyy(String bhyy) {
        this.bhyy = bhyy;
    }

    public String getBhnr() {
        return bhnr;
    }

    public void setBhnr(String bhnr) {
        this.bhnr = bhnr;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }
}
