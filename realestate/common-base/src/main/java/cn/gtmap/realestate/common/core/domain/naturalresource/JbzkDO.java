package cn.gtmap.realestate.common.core.domain.naturalresource;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * JBZK
 *
 * @author
 */
@Table(name = "Jbzk")
@Data
public class JbzkDO implements Serializable {
    private String dcjg;

    private String dcsj;

    private String zrzydjdyh;

    private String djdylx;

    private String mc;

    private String dyxxb;

    private String ysdm;

    private String zl;

    private String dyszb;

    private String dyszd;

    private String dyszn;

    private String dyszx;

    private String zrzydjdcb;

    private String zrzydjdcbbh;

    private String zrzydjdyhzhsl;

    private Double djdyzmj;

    private Double gymj;

    private Double jtmj;

    private Double zyqmj;

    private String xmqy;

    private String ssqdm;

    public String getDcjg() {
        return dcjg;
    }

    public void setDcjg(String dcjg) {
        this.dcjg = dcjg;
    }

    public String getDcsj() {
        return dcsj;
    }

    public void setDcsj(String dcsj) {
        this.dcsj = dcsj;
    }

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getDjdylx() {
        return djdylx;
    }

    public void setDjdylx(String djdylx) {
        this.djdylx = djdylx;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDyxxb() {
        return dyxxb;
    }

    public void setDyxxb(String dyxxb) {
        this.dyxxb = dyxxb;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getDyszb() {
        return dyszb;
    }

    public void setDyszb(String dyszb) {
        this.dyszb = dyszb;
    }

    public String getDyszd() {
        return dyszd;
    }

    public void setDyszd(String dyszd) {
        this.dyszd = dyszd;
    }

    public String getDyszn() {
        return dyszn;
    }

    public void setDyszn(String dyszn) {
        this.dyszn = dyszn;
    }

    public String getDyszx() {
        return dyszx;
    }

    public void setDyszx(String dyszx) {
        this.dyszx = dyszx;
    }

    public String getZrzydjdcb() {
        return zrzydjdcb;
    }

    public void setZrzydjdcb(String zrzydjdcb) {
        this.zrzydjdcb = zrzydjdcb;
    }

    public String getZrzydjdcbbh() {
        return zrzydjdcbbh;
    }

    public void setZrzydjdcbbh(String zrzydjdcbbh) {
        this.zrzydjdcbbh = zrzydjdcbbh;
    }

    public String getZrzydjdyhzhsl() {
        return zrzydjdyhzhsl;
    }

    public void setZrzydjdyhzhsl(String zrzydjdyhzhsl) {
        this.zrzydjdyhzhsl = zrzydjdyhzhsl;
    }

    public Double getDjdyzmj() {
        return djdyzmj;
    }

    public void setDjdyzmj(Double djdyzmj) {
        this.djdyzmj = djdyzmj;
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

    public Double getZyqmj() {
        return zyqmj;
    }

    public void setZyqmj(Double zyqmj) {
        this.zyqmj = zyqmj;
    }

    public String getXmqy() {
        return xmqy;
    }

    public void setXmqy(String xmqy) {
        this.xmqy = xmqy;
    }

    public String getSsqdm() {
        return ssqdm;
    }

    public void setSsqdm(String ssqdm) {
        this.ssqdm = ssqdm;
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
        JbzkDO other = (JbzkDO) that;
        return (this.getDcjg() == null ? other.getDcjg() == null : this.getDcjg().equals(other.getDcjg()))
                && (this.getDcsj() == null ? other.getDcsj() == null : this.getDcsj().equals(other.getDcsj()))
                && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
                && (this.getDjdylx() == null ? other.getDjdylx() == null : this.getDjdylx().equals(other.getDjdylx()))
                && (this.getMc() == null ? other.getMc() == null : this.getMc().equals(other.getMc()))
                && (this.getDyxxb() == null ? other.getDyxxb() == null : this.getDyxxb().equals(other.getDyxxb()))
                && (this.getYsdm() == null ? other.getYsdm() == null : this.getYsdm().equals(other.getYsdm()))
                && (this.getZl() == null ? other.getZl() == null : this.getZl().equals(other.getZl()))
                && (this.getDyszb() == null ? other.getDyszb() == null : this.getDyszb().equals(other.getDyszb()))
                && (this.getDyszd() == null ? other.getDyszd() == null : this.getDyszd().equals(other.getDyszd()))
                && (this.getDyszn() == null ? other.getDyszn() == null : this.getDyszn().equals(other.getDyszn()))
                && (this.getDyszx() == null ? other.getDyszx() == null : this.getDyszx().equals(other.getDyszx()))
                && (this.getZrzydjdcb() == null ? other.getZrzydjdcb() == null : this.getZrzydjdcb().equals(other.getZrzydjdcb()))
                && (this.getZrzydjdcbbh() == null ? other.getZrzydjdcbbh() == null : this.getZrzydjdcbbh().equals(other.getZrzydjdcbbh()))
                && (this.getZrzydjdyhzhsl() == null ? other.getZrzydjdyhzhsl() == null : this.getZrzydjdyhzhsl().equals(other.getZrzydjdyhzhsl()))
                && (this.getDjdyzmj() == null ? other.getDjdyzmj() == null : this.getDjdyzmj().equals(other.getDjdyzmj()))
                && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
                && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
                && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
                && (this.getXmqy() == null ? other.getXmqy() == null : this.getXmqy().equals(other.getXmqy()))
                && (this.getSsqdm() == null ? other.getSsqdm() == null : this.getSsqdm().equals(other.getSsqdm()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDcjg() == null) ? 0 : getDcjg().hashCode());
        result = prime * result + ((getDcsj() == null) ? 0 : getDcsj().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getDjdylx() == null) ? 0 : getDjdylx().hashCode());
        result = prime * result + ((getMc() == null) ? 0 : getMc().hashCode());
        result = prime * result + ((getDyxxb() == null) ? 0 : getDyxxb().hashCode());
        result = prime * result + ((getYsdm() == null) ? 0 : getYsdm().hashCode());
        result = prime * result + ((getZl() == null) ? 0 : getZl().hashCode());
        result = prime * result + ((getDyszb() == null) ? 0 : getDyszb().hashCode());
        result = prime * result + ((getDyszd() == null) ? 0 : getDyszd().hashCode());
        result = prime * result + ((getDyszn() == null) ? 0 : getDyszn().hashCode());
        result = prime * result + ((getDyszx() == null) ? 0 : getDyszx().hashCode());
        result = prime * result + ((getZrzydjdcb() == null) ? 0 : getZrzydjdcb().hashCode());
        result = prime * result + ((getZrzydjdcbbh() == null) ? 0 : getZrzydjdcbbh().hashCode());
        result = prime * result + ((getZrzydjdyhzhsl() == null) ? 0 : getZrzydjdyhzhsl().hashCode());
        result = prime * result + ((getDjdyzmj() == null) ? 0 : getDjdyzmj().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getXmqy() == null) ? 0 : getXmqy().hashCode());
        result = prime * result + ((getSsqdm() == null) ? 0 : getSsqdm().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dcjg=").append(dcjg);
        sb.append(", dcsj=").append(dcsj);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", djdylx=").append(djdylx);
        sb.append(", mc=").append(mc);
        sb.append(", dyxxb=").append(dyxxb);
        sb.append(", ysdm=").append(ysdm);
        sb.append(", zl=").append(zl);
        sb.append(", dyszb=").append(dyszb);
        sb.append(", dyszd=").append(dyszd);
        sb.append(", dyszn=").append(dyszn);
        sb.append(", dyszx=").append(dyszx);
        sb.append(", zrzydjdcb=").append(zrzydjdcb);
        sb.append(", zrzydjdcbbh=").append(zrzydjdcbbh);
        sb.append(", zrzydjdyhzhsl=").append(zrzydjdyhzhsl);
        sb.append(", djdyzmj=").append(djdyzmj);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", xmqy=").append(xmqy);
        sb.append(", ssqdm=").append(ssqdm);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}