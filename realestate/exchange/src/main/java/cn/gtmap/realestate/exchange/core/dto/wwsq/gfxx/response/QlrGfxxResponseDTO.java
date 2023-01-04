package cn.gtmap.realestate.exchange.core.dto.wwsq.gfxx.response;

import java.util.Date;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/10/11
 * @description
 */
public class QlrGfxxResponseDTO {
    private String qlrmc;

    private String zl;

    private String fwyt;

    private String fwxz;

    private String bdcqzh;

    private String slbh;

    private Date djsj;

    private Date zxdjsj;

    private String qszt;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public Date getZxdjsj() {
        return zxdjsj;
    }

    public void setZxdjsj(Date zxdjsj) {
        this.zxdjsj = zxdjsj;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    @Override
    public String toString() {
        return "QlrGfxxResponseDTO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", zl='" + zl + '\'' +
                ", fwyt='" + fwyt + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", djsj=" + djsj +
                ", zxdjsj=" + zxdjsj +
                ", qszt='" + qszt + '\'' +
                '}';
    }
}
