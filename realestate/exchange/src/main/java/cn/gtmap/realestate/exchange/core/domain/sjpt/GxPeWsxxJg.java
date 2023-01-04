package cn.gtmap.realestate.exchange.core.domain.sjpt;

import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "gx_pe_wsxx_jg")
public class GxPeWsxxJg {
    @Id
    private String jgid;
    private String xh;
    private String wjmc;
    private String wjlx;
    private String wsnr;
    private String cxsqdh;
    private String wsbh;

    public String getJgid() {
        return jgid;
    }

    public void setJgid(String jgid) {
        this.jgid = jgid;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public String getWjlx() {
        return wjlx;
    }

    public void setWjlx(String wjlx) {
        this.wjlx = wjlx;
    }

    public String getWsnr() {
        return wsnr;
    }

    public void setWsnr(String wsnr) {
        this.wsnr = wsnr;
    }

    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }
}
