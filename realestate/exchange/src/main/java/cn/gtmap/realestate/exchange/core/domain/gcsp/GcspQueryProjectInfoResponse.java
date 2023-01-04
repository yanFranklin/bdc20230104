package cn.gtmap.realestate.exchange.core.domain.gcsp;

import java.io.Serializable;

public class GcspQueryProjectInfoResponse implements Serializable {

    private static final long serialVersionUID = 2006200000391905120L;

    private String applyinstCode;
    private String localCode;
    private String gcbm;
    private String projName;
    private String projAddr;
    private String scaleContent;
    private String dtmc;
    private String certinstCode;
    private String jzmj;
    private String dsjzmj;
    private String dxjzmj;

    public String getApplyinstCode() {
        return applyinstCode;
    }

    public void setApplyinstCode(String applyinstCode) {
        this.applyinstCode = applyinstCode;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getGcbm() {
        return gcbm;
    }

    public void setGcbm(String gcbm) {
        this.gcbm = gcbm;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjAddr() {
        return projAddr;
    }

    public void setProjAddr(String projAddr) {
        this.projAddr = projAddr;
    }

    public String getScaleContent() {
        return scaleContent;
    }

    public void setScaleContent(String scaleContent) {
        this.scaleContent = scaleContent;
    }

    public String getDtmc() {
        return dtmc;
    }

    public void setDtmc(String dtmc) {
        this.dtmc = dtmc;
    }

    public String getCertinstCode() {
        return certinstCode;
    }

    public void setCertinstCode(String certinstCode) {
        this.certinstCode = certinstCode;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getDsjzmj() {
        return dsjzmj;
    }

    public void setDsjzmj(String dsjzmj) {
        this.dsjzmj = dsjzmj;
    }

    public String getDxjzmj() {
        return dxjzmj;
    }

    public void setDxjzmj(String dxjzmj) {
        this.dxjzmj = dxjzmj;
    }
}
