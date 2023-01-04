package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.sea;

import java.io.Serializable;

public class ZhYhzkDTO implements Serializable {

    private static final long serialVersionUID = 837373341947287787L;
    private String zhdm;//varchar2
    private String yhfs;//varchar2
    private Double yhmj;//number
    private String jtyt;//varchar2
    private Double syjse;//number

    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }

    public String getYhfs() {
        return yhfs;
    }

    public void setYhfs(String yhfs) {
        this.yhfs = yhfs;
    }

    public Double getYhmj() {
        return yhmj;
    }

    public void setYhmj(Double yhmj) {
        this.yhmj = yhmj;
    }

    public String getJtyt() {
        return jtyt;
    }

    public void setJtyt(String jtyt) {
        this.jtyt = jtyt;
    }

    public Double getSyjse() {
        return syjse;
    }

    public void setSyjse(Double syjse) {
        this.syjse = syjse;
    }
}
