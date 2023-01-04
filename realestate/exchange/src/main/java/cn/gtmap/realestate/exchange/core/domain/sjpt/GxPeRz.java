package cn.gtmap.realestate.exchange.core.domain.sjpt;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Table(name = "gx_pe_rz")
public class GxPeRz {
    @Id
    private String rzid;
    private String cxsqdh;
    private String wsbh;
    private String qlrmc;
    private Date jlsj;
    private String czlx;
    private String xzqdm;
    private String cxjgbs;
    private String yhm;
    private String mm;
    private String qlrzjh;
    private String zt;//1：成功 2：失败
    private String errorMsg;
    private Integer hqqqs;
    private String czr;
    private String successMsg;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
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

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public Date getJlsj() {
        return jlsj;
    }

    public void setJlsj(Date jlsj) {
        this.jlsj = jlsj;
    }

    public String getCzlx() {
        return czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getCxjgbs() {
        return cxjgbs;
    }

    public void setCxjgbs(String cxjgbs) {
        this.cxjgbs = cxjgbs;
    }

    public String getYhm() {
        return yhm;
    }

    public void setYhm(String yhm) {
        this.yhm = yhm;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getHqqqs() {
        return hqqqs;
    }

    public void setHqqqs(Integer hqqqs) {
        this.hqqqs = hqqqs;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
