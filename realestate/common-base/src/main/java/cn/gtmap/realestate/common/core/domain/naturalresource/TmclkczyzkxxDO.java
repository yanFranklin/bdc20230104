package cn.gtmap.realestate.common.core.domain.naturalresource;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.persistence.Table;

/**
 * TMCLKCZYZKXX
 * @author 
 */
@Table(name = "Tmclkczyzkxx")
@Data
public class TmclkczyzkxxDO implements Serializable {
    private String cljldw;

    private Date clgsjzr;

    private Double clgsfwmj;

    private String zrzydjdyh;

    private Double gymj;

    private Double jtmj;

    private String kczh;

    private String kqdz;

    private Double kqyqtzmj;

    private Double gtkckzzyl;

    private String qkbh;

    private Double gtkctdzyl;

    private Double yqtmdzcl;

    private Double gtkctmzyl;

    private String ysdm;

    private Double zyzfpjpw;

    private String zylx;

    private Double zyqmj;

    public String getCljldw() {
        return cljldw;
    }

    public void setCljldw(String cljldw) {
        this.cljldw = cljldw;
    }

    public Date getClgsjzr() {
        return clgsjzr;
    }

    public void setClgsjzr(Date clgsjzr) {
        this.clgsjzr = clgsjzr;
    }

    public Double getClgsfwmj() {
        return clgsfwmj;
    }

    public void setClgsfwmj(Double clgsfwmj) {
        this.clgsfwmj = clgsfwmj;
    }

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public Double getGymj() {
        return gymj;
    }

    public void setGymj(Double gymj) {
        this.gymj = gymj;
    }

    public Double getJtmj() {
        return jtmj;
    }

    public void setJtmj(Double jtmj) {
        this.jtmj = jtmj;
    }

    public String getKczh() {
        return kczh;
    }

    public void setKczh(String kczh) {
        this.kczh = kczh;
    }

    public String getKqdz() {
        return kqdz;
    }

    public void setKqdz(String kqdz) {
        this.kqdz = kqdz;
    }

    public Double getKqyqtzmj() {
        return kqyqtzmj;
    }

    public void setKqyqtzmj(Double kqyqtzmj) {
        this.kqyqtzmj = kqyqtzmj;
    }

    public Double getGtkckzzyl() {
        return gtkckzzyl;
    }

    public void setGtkckzzyl(Double gtkckzzyl) {
        this.gtkckzzyl = gtkckzzyl;
    }

    public String getQkbh() {
        return qkbh;
    }

    public void setQkbh(String qkbh) {
        this.qkbh = qkbh;
    }

    public Double getGtkctdzyl() {
        return gtkctdzyl;
    }

    public void setGtkctdzyl(Double gtkctdzyl) {
        this.gtkctdzyl = gtkctdzyl;
    }

    public Double getYqtmdzcl() {
        return yqtmdzcl;
    }

    public void setYqtmdzcl(Double yqtmdzcl) {
        this.yqtmdzcl = yqtmdzcl;
    }

    public Double getGtkctmzyl() {
        return gtkctmzyl;
    }

    public void setGtkctmzyl(Double gtkctmzyl) {
        this.gtkctmzyl = gtkctmzyl;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public Double getZyzfpjpw() {
        return zyzfpjpw;
    }

    public void setZyzfpjpw(Double zyzfpjpw) {
        this.zyzfpjpw = zyzfpjpw;
    }

    public String getZylx() {
        return zylx;
    }

    public void setZylx(String zylx) {
        this.zylx = zylx;
    }

    public Double getZyqmj() {
        return zyqmj;
    }

    public void setZyqmj(Double zyqmj) {
        this.zyqmj = zyqmj;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TmclkczyzkxxDO other = (TmclkczyzkxxDO) that;
        return (this.getCljldw() == null ? other.getCljldw() == null : this.getCljldw().equals(other.getCljldw()))
            && (this.getClgsjzr() == null ? other.getClgsjzr() == null : this.getClgsjzr().equals(other.getClgsjzr()))
            && (this.getClgsfwmj() == null ? other.getClgsfwmj() == null : this.getClgsfwmj().equals(other.getClgsfwmj()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getKczh() == null ? other.getKczh() == null : this.getKczh().equals(other.getKczh()))
            && (this.getKqdz() == null ? other.getKqdz() == null : this.getKqdz().equals(other.getKqdz()))
            && (this.getKqyqtzmj() == null ? other.getKqyqtzmj() == null : this.getKqyqtzmj().equals(other.getKqyqtzmj()))
            && (this.getGtkckzzyl() == null ? other.getGtkckzzyl() == null : this.getGtkckzzyl().equals(other.getGtkckzzyl()))
            && (this.getQkbh() == null ? other.getQkbh() == null : this.getQkbh().equals(other.getQkbh()))
            && (this.getGtkctdzyl() == null ? other.getGtkctdzyl() == null : this.getGtkctdzyl().equals(other.getGtkctdzyl()))
            && (this.getYqtmdzcl() == null ? other.getYqtmdzcl() == null : this.getYqtmdzcl().equals(other.getYqtmdzcl()))
            && (this.getGtkctmzyl() == null ? other.getGtkctmzyl() == null : this.getGtkctmzyl().equals(other.getGtkctmzyl()))
            && (this.getYsdm() == null ? other.getYsdm() == null : this.getYsdm().equals(other.getYsdm()))
            && (this.getZyzfpjpw() == null ? other.getZyzfpjpw() == null : this.getZyzfpjpw().equals(other.getZyzfpjpw()))
            && (this.getZylx() == null ? other.getZylx() == null : this.getZylx().equals(other.getZylx()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCljldw() == null) ? 0 : getCljldw().hashCode());
        result = prime * result + ((getClgsjzr() == null) ? 0 : getClgsjzr().hashCode());
        result = prime * result + ((getClgsfwmj() == null) ? 0 : getClgsfwmj().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getKczh() == null) ? 0 : getKczh().hashCode());
        result = prime * result + ((getKqdz() == null) ? 0 : getKqdz().hashCode());
        result = prime * result + ((getKqyqtzmj() == null) ? 0 : getKqyqtzmj().hashCode());
        result = prime * result + ((getGtkckzzyl() == null) ? 0 : getGtkckzzyl().hashCode());
        result = prime * result + ((getQkbh() == null) ? 0 : getQkbh().hashCode());
        result = prime * result + ((getGtkctdzyl() == null) ? 0 : getGtkctdzyl().hashCode());
        result = prime * result + ((getYqtmdzcl() == null) ? 0 : getYqtmdzcl().hashCode());
        result = prime * result + ((getGtkctmzyl() == null) ? 0 : getGtkctmzyl().hashCode());
        result = prime * result + ((getYsdm() == null) ? 0 : getYsdm().hashCode());
        result = prime * result + ((getZyzfpjpw() == null) ? 0 : getZyzfpjpw().hashCode());
        result = prime * result + ((getZylx() == null) ? 0 : getZylx().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cljldw=").append(cljldw);
        sb.append(", clgsjzr=").append(clgsjzr);
        sb.append(", clgsfwmj=").append(clgsfwmj);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", kczh=").append(kczh);
        sb.append(", kqdz=").append(kqdz);
        sb.append(", kqyqtzmj=").append(kqyqtzmj);
        sb.append(", gtkckzzyl=").append(gtkckzzyl);
        sb.append(", qkbh=").append(qkbh);
        sb.append(", gtkctdzyl=").append(gtkctdzyl);
        sb.append(", yqtmdzcl=").append(yqtmdzcl);
        sb.append(", gtkctmzyl=").append(gtkctmzyl);
        sb.append(", ysdm=").append(ysdm);
        sb.append(", zyzfpjpw=").append(zyzfpjpw);
        sb.append(", zylx=").append(zylx);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}