package cn.gtmap.realestate.common.core.dto.exchange.yancheng.sw.wsxx;

import java.util.Date;
import java.util.List;

public class SwWsxxFcwsxxDTO {

    private String dzsphm;
    private String htbh;
    private String nsrmc;
    private String nsrsbh;
    private String pzzlDm;
    private String pzzlmc;
    private Double jehj;
    private String tpr;
    private Date kprq;
    private String spxxbase64;
    private String cxbm;

    private List<SwSkmxxDTO> skmxxList;

    public String getDzsphm() {
        return dzsphm;
    }

    public void setDzsphm(String dzsphm) {
        this.dzsphm = dzsphm;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getPzzlDm() {
        return pzzlDm;
    }

    public void setPzzlDm(String pzzlDm) {
        this.pzzlDm = pzzlDm;
    }

    public String getPzzlmc() {
        return pzzlmc;
    }

    public void setPzzlmc(String pzzlmc) {
        this.pzzlmc = pzzlmc;
    }

    public Double getJehj() {
        return jehj;
    }

    public void setJehj(Double jehj) {
        this.jehj = jehj;
    }

    public String getTpr() {
        return tpr;
    }

    public void setTpr(String tpr) {
        this.tpr = tpr;
    }

    public Date getKprq() {
        return kprq;
    }

    public void setKprq(Date kprq) {
        this.kprq = kprq;
    }

    public String getSpxxbase64() {
        return spxxbase64;
    }

    public void setSpxxbase64(String spxxbase64) {
        this.spxxbase64 = spxxbase64;
    }

    public String getCxbm() {
        return cxbm;
    }

    public void setCxbm(String cxbm) {
        this.cxbm = cxbm;
    }

    public List<SwSkmxxDTO> getSkmxxList() {
        return skmxxList;
    }

    public void setSkmxxList(List<SwSkmxxDTO> skmxxList) {
        this.skmxxList = skmxxList;
    }
}
