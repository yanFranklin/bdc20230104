package cn.gtmap.realestate.common.core.domain.naturalresource;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Table;

/**
 * HDZKXX
 * @author 
 */
@Table(name = "Hdzkxx")
@Data
public class HdzkxxDO implements Serializable {
    private String zrzydjdyh;

    private Double gymj;

    private String hdlx;

    private Double jtmj;

    private Double mj;

    private String bhtbsl;

    private String ysdm;

    private Double zyqmj;

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

    public String getHdlx() {
        return hdlx;
    }

    public void setHdlx(String hdlx) {
        this.hdlx = hdlx;
    }

    public Double getJtmj() {
        return jtmj;
    }

    public void setJtmj(Double jtmj) {
        this.jtmj = jtmj;
    }

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    public String getBhtbsl() {
        return bhtbsl;
    }

    public void setBhtbsl(String bhtbsl) {
        this.bhtbsl = bhtbsl;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
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
        HdzkxxDO other = (HdzkxxDO) that;
        return (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getHdlx() == null ? other.getHdlx() == null : this.getHdlx().equals(other.getHdlx()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getMj() == null ? other.getMj() == null : this.getMj().equals(other.getMj()))
            && (this.getBhtbsl() == null ? other.getBhtbsl() == null : this.getBhtbsl().equals(other.getBhtbsl()))
            && (this.getYsdm() == null ? other.getYsdm() == null : this.getYsdm().equals(other.getYsdm()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getHdlx() == null) ? 0 : getHdlx().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getMj() == null) ? 0 : getMj().hashCode());
        result = prime * result + ((getBhtbsl() == null) ? 0 : getBhtbsl().hashCode());
        result = prime * result + ((getYsdm() == null) ? 0 : getYsdm().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", gymj=").append(gymj);
        sb.append(", hdlx=").append(hdlx);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", mj=").append(mj);
        sb.append(", bhtbsl=").append(bhtbsl);
        sb.append(", ysdm=").append(ysdm);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}