package cn.gtmap.realestate.common.core.domain.naturalresource;



import java.io.Serializable;
import lombok.Data;

import javax.persistence.Table;

/**
 * QSQGLXX
 * @author 
 */
@Table(name = "Qsqglxx")
@Data
public class QsqglxxDO implements Serializable {
    private String zrzydjdyh;

    private String qsdd;

    private String qsqr;

    private String qsxkzh;

    private String qxdm;

    private String spjg;

    private String ysdm;

    private String qsqyxqx;

    private String bdcdxxgldbsm;

    private String zrzydjdyhzh;

    private Double qsl;

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getQsdd() {
        return qsdd;
    }

    public void setQsdd(String qsdd) {
        this.qsdd = qsdd;
    }

    public String getQsqr() {
        return qsqr;
    }

    public void setQsqr(String qsqr) {
        this.qsqr = qsqr;
    }

    public String getQsxkzh() {
        return qsxkzh;
    }

    public void setQsxkzh(String qsxkzh) {
        this.qsxkzh = qsxkzh;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getSpjg() {
        return spjg;
    }

    public void setSpjg(String spjg) {
        this.spjg = spjg;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getQsqyxqx() {
        return qsqyxqx;
    }

    public void setQsqyxqx(String qsqyxqx) {
        this.qsqyxqx = qsqyxqx;
    }

    public String getBdcdxxgldbsm() {
        return bdcdxxgldbsm;
    }

    public void setBdcdxxgldbsm(String bdcdxxgldbsm) {
        this.bdcdxxgldbsm = bdcdxxgldbsm;
    }

    public String getZrzydjdyhzh() {
        return zrzydjdyhzh;
    }

    public void setZrzydjdyhzh(String zrzydjdyhzh) {
        this.zrzydjdyhzh = zrzydjdyhzh;
    }

    public Double getQsl() {
        return qsl;
    }

    public void setQsl(Double qsl) {
        this.qsl = qsl;
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
        QsqglxxDO other = (QsqglxxDO) that;
        return (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getQsdd() == null ? other.getQsdd() == null : this.getQsdd().equals(other.getQsdd()))
            && (this.getQsqr() == null ? other.getQsqr() == null : this.getQsqr().equals(other.getQsqr()))
            && (this.getQsxkzh() == null ? other.getQsxkzh() == null : this.getQsxkzh().equals(other.getQsxkzh()))
            && (this.getQxdm() == null ? other.getQxdm() == null : this.getQxdm().equals(other.getQxdm()))
            && (this.getSpjg() == null ? other.getSpjg() == null : this.getSpjg().equals(other.getSpjg()))
            && (this.getYsdm() == null ? other.getYsdm() == null : this.getYsdm().equals(other.getYsdm()))
            && (this.getQsqyxqx() == null ? other.getQsqyxqx() == null : this.getQsqyxqx().equals(other.getQsqyxqx()))
            && (this.getBdcdxxgldbsm() == null ? other.getBdcdxxgldbsm() == null : this.getBdcdxxgldbsm().equals(other.getBdcdxxgldbsm()))
            && (this.getZrzydjdyhzh() == null ? other.getZrzydjdyhzh() == null : this.getZrzydjdyhzh().equals(other.getZrzydjdyhzh()))
            && (this.getQsl() == null ? other.getQsl() == null : this.getQsl().equals(other.getQsl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getQsdd() == null) ? 0 : getQsdd().hashCode());
        result = prime * result + ((getQsqr() == null) ? 0 : getQsqr().hashCode());
        result = prime * result + ((getQsxkzh() == null) ? 0 : getQsxkzh().hashCode());
        result = prime * result + ((getQxdm() == null) ? 0 : getQxdm().hashCode());
        result = prime * result + ((getSpjg() == null) ? 0 : getSpjg().hashCode());
        result = prime * result + ((getYsdm() == null) ? 0 : getYsdm().hashCode());
        result = prime * result + ((getQsqyxqx() == null) ? 0 : getQsqyxqx().hashCode());
        result = prime * result + ((getBdcdxxgldbsm() == null) ? 0 : getBdcdxxgldbsm().hashCode());
        result = prime * result + ((getZrzydjdyhzh() == null) ? 0 : getZrzydjdyhzh().hashCode());
        result = prime * result + ((getQsl() == null) ? 0 : getQsl().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", qsdd=").append(qsdd);
        sb.append(", qsqr=").append(qsqr);
        sb.append(", qsxkzh=").append(qsxkzh);
        sb.append(", qxdm=").append(qxdm);
        sb.append(", spjg=").append(spjg);
        sb.append(", ysdm=").append(ysdm);
        sb.append(", qsqyxqx=").append(qsqyxqx);
        sb.append(", bdcdxxgldbsm=").append(bdcdxxgldbsm);
        sb.append(", zrzydjdyhzh=").append(zrzydjdyhzh);
        sb.append(", qsl=").append(qsl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}