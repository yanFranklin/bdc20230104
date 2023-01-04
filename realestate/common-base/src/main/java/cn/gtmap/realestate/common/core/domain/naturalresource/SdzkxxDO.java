package cn.gtmap.realestate.common.core.domain.naturalresource;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Table;

/**
 * SDZKXX
 * @author 
 */
@Table(name = "Sdzkxx")
@Data
public class SdzkxxDO implements Serializable {
    private String zrzydjdyh;

    private String sdlx;

    private String sybjzk;

    private String szlbSd;

    private String bhtbsl;

    private String ysdm;

    private String zyyszwz;

    private String zblx;

    private String gjzdbhdzysdnl;

    private Double gymj;

    private Double jtmj;

    private Double zyqmj;

    private Double zbmj;

    private Double zmj;

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getSdlx() {
        return sdlx;
    }

    public void setSdlx(String sdlx) {
        this.sdlx = sdlx;
    }

    public String getSybjzk() {
        return sybjzk;
    }

    public void setSybjzk(String sybjzk) {
        this.sybjzk = sybjzk;
    }

    public String getSzlbSd() {
        return szlbSd;
    }

    public void setSzlbSd(String szlbSd) {
        this.szlbSd = szlbSd;
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

    public String getZyyszwz() {
        return zyyszwz;
    }

    public void setZyyszwz(String zyyszwz) {
        this.zyyszwz = zyyszwz;
    }

    public String getZblx() {
        return zblx;
    }

    public void setZblx(String zblx) {
        this.zblx = zblx;
    }

    public String getGjzdbhdzysdnl() {
        return gjzdbhdzysdnl;
    }

    public void setGjzdbhdzysdnl(String gjzdbhdzysdnl) {
        this.gjzdbhdzysdnl = gjzdbhdzysdnl;
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

    public Double getZbmj() {
        return zbmj;
    }

    public void setZbmj(Double zbmj) {
        this.zbmj = zbmj;
    }

    public Double getZmj() {
        return zmj;
    }

    public void setZmj(Double zmj) {
        this.zmj = zmj;
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
        SdzkxxDO other = (SdzkxxDO) that;
        return (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getSdlx() == null ? other.getSdlx() == null : this.getSdlx().equals(other.getSdlx()))
            && (this.getSybjzk() == null ? other.getSybjzk() == null : this.getSybjzk().equals(other.getSybjzk()))
            && (this.getSzlbSd() == null ? other.getSzlbSd() == null : this.getSzlbSd().equals(other.getSzlbSd()))
            && (this.getBhtbsl() == null ? other.getBhtbsl() == null : this.getBhtbsl().equals(other.getBhtbsl()))
            && (this.getYsdm() == null ? other.getYsdm() == null : this.getYsdm().equals(other.getYsdm()))
            && (this.getZyyszwz() == null ? other.getZyyszwz() == null : this.getZyyszwz().equals(other.getZyyszwz()))
            && (this.getZblx() == null ? other.getZblx() == null : this.getZblx().equals(other.getZblx()))
            && (this.getGjzdbhdzysdnl() == null ? other.getGjzdbhdzysdnl() == null : this.getGjzdbhdzysdnl().equals(other.getGjzdbhdzysdnl()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
            && (this.getZbmj() == null ? other.getZbmj() == null : this.getZbmj().equals(other.getZbmj()))
            && (this.getZmj() == null ? other.getZmj() == null : this.getZmj().equals(other.getZmj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getSdlx() == null) ? 0 : getSdlx().hashCode());
        result = prime * result + ((getSybjzk() == null) ? 0 : getSybjzk().hashCode());
        result = prime * result + ((getSzlbSd() == null) ? 0 : getSzlbSd().hashCode());
        result = prime * result + ((getBhtbsl() == null) ? 0 : getBhtbsl().hashCode());
        result = prime * result + ((getYsdm() == null) ? 0 : getYsdm().hashCode());
        result = prime * result + ((getZyyszwz() == null) ? 0 : getZyyszwz().hashCode());
        result = prime * result + ((getZblx() == null) ? 0 : getZblx().hashCode());
        result = prime * result + ((getGjzdbhdzysdnl() == null) ? 0 : getGjzdbhdzysdnl().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getZbmj() == null) ? 0 : getZbmj().hashCode());
        result = prime * result + ((getZmj() == null) ? 0 : getZmj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", sdlx=").append(sdlx);
        sb.append(", sybjzk=").append(sybjzk);
        sb.append(", szlbSd=").append(szlbSd);
        sb.append(", bhtbsl=").append(bhtbsl);
        sb.append(", ysdm=").append(ysdm);
        sb.append(", zyyszwz=").append(zyyszwz);
        sb.append(", zblx=").append(zblx);
        sb.append(", gjzdbhdzysdnl=").append(gjzdbhdzysdnl);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", zbmj=").append(zbmj);
        sb.append(", zmj=").append(zmj);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}