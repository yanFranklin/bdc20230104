package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import java.util.Date;

/**
 * @program: realestate
 * @description: 电子证照信息查询组织参数
 * @author: <a href="mailto:wutao2@gtmap.cn">wutao2</a>
 * @create: 2022-08-24 13:49
 **/
public class DzzxxcxDTO {
    private String bdcdyh;
    private String zl;

    private String gyqk;
    private String djjg;

    private String qllx;

    private String qlxz;

    private String yt;

    private String syqx;

    private Date fzrq;

    private String qlrmc;

    private String qlr;

    private String qlrzjh;

    private String qlrzjlx;
    private String bdcqzh;

    private String qlqtzk;

    private String fjlx;

    private byte[] zzfj;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    public Date getFzrq() {
        return fzrq;
    }

    public void setFzrq(Date fzrq) {
        this.fzrq = fzrq;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrzjlx() {
        return qlrzjlx;
    }

    public void setQlrzjlx(String qlrzjlx) {
        this.qlrzjlx = qlrzjlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public byte[] getZzfj() {
        return zzfj;
    }

    public void setZzfj(byte[] zzfj) {
        this.zzfj = zzfj;
    }

    @Override
    public String toString() {
        return "DzzxxcxDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djjg='" + djjg + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", yt='" + yt + '\'' +
                ", syqx='" + syqx + '\'' +
                ", fzrq='" + fzrq + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrzjlx='" + qlrzjlx + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", fjlx='" + fjlx + '\'' +
                ", zzfj='" + zzfj + '\'' +
                '}';
    }
}
