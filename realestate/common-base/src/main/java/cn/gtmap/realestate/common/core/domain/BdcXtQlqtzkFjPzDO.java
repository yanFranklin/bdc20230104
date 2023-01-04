package cn.gtmap.realestate.common.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 权利其他状况与附记配置表
 */
@Table(name = "BDC_XT_QLQTZK_FJ_PZ")
public class BdcXtQlqtzkFjPzDO {
    @Id
    private String pzid;
    private String djxl;
    private Integer qllx;
    private String qlqtzksjy;
    private String qlqtzkmb;
    private String fjsjy;
    private String fjmb;
    private String zxqlfjmb;
    private String zxqlfjsjy;
    /**
     * 配置时间
     */
    private Date pzrq;

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getQlqtzksjy() {
        return qlqtzksjy;
    }

    public void setQlqtzksjy(String qlqtzksjy) {
        this.qlqtzksjy = qlqtzksjy;
    }

    public String getQlqtzkmb() {
        return qlqtzkmb;
    }

    public void setQlqtzkmb(String qlqtzkmb) {
        this.qlqtzkmb = qlqtzkmb;
    }

    public String getFjsjy() {
        return fjsjy;
    }

    public void setFjsjy(String fjsjy) {
        this.fjsjy = fjsjy;
    }

    public String getFjmb() {
        return fjmb;
    }

    public void setFjmb(String fjmb) {
        this.fjmb = fjmb;
    }


    public Date getPzrq() {
        return pzrq;
    }

    public void setPzrq(Date pzrq) {
        this.pzrq = pzrq;
    }

    public String getZxqlfjmb() {
        return zxqlfjmb;
    }

    public void setZxqlfjmb(String zxqlfjmb) {
        this.zxqlfjmb = zxqlfjmb;
    }

    public String getZxqlfjsjy() {
        return zxqlfjsjy;
    }

    public void setZxqlfjsjy(String zxqlfjsjy) {
        this.zxqlfjsjy = zxqlfjsjy;
    }

    @Override
    public String toString() {
        return "BdcXtQlqtzkFjPzDO{" +
                "pzid='" + pzid + '\'' +
                ", djxl='" + djxl + '\'' +
                ", qllx=" + qllx +
                ", qlqtzksjy='" + qlqtzksjy + '\'' +
                ", qlqtzkmb='" + qlqtzkmb + '\'' +
                ", fjsjy='" + fjsjy + '\'' +
                ", fjmb='" + fjmb + '\'' +
                ", pzrq=" + pzrq + '\'' +
                ", zxqlfjmb=" + zxqlfjmb + '\'' +
                ", zxqlfjsjy=" + zxqlfjsjy +
                '}';
    }
}
