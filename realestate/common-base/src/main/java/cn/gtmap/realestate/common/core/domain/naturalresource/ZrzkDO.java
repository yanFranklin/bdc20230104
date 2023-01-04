package cn.gtmap.realestate.common.core.domain.naturalresource;

import java.io.Serializable;
import lombok.Data;

import javax.persistence.Table;

/**
 * ZRZK
 * @author 
 */
@Table(name="Zrzk")
@Data
public class ZrzkDO implements Serializable {
    private String bz;

    private String zrzydjdyh;

    private String ysdm;

    private Double cymj;

    private Double djdyzmj;

    private Double fzrzyzmj;

    private Double hdmj;

    private Double qtmj;

    private Double sdmj;

    private Double slmj;

    private Double szymj;

    private Double dynzrzyzmj;

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public Double getCymj() {
        return cymj;
    }

    public void setCymj(Double cymj) {
        this.cymj = cymj;
    }

    public Double getDjdyzmj() {
        return djdyzmj;
    }

    public void setDjdyzmj(Double djdyzmj) {
        this.djdyzmj = djdyzmj;
    }

    public Double getFzrzyzmj() {
        return fzrzyzmj;
    }

    public void setFzrzyzmj(Double fzrzyzmj) {
        this.fzrzyzmj = fzrzyzmj;
    }

    public Double getHdmj() {
        return hdmj;
    }

    public void setHdmj(Double hdmj) {
        this.hdmj = hdmj;
    }

    public Double getQtmj() {
        return qtmj;
    }

    public void setQtmj(Double qtmj) {
        this.qtmj = qtmj;
    }

    public Double getSdmj() {
        return sdmj;
    }

    public void setSdmj(Double sdmj) {
        this.sdmj = sdmj;
    }

    public Double getSlmj() {
        return slmj;
    }

    public void setSlmj(Double slmj) {
        this.slmj = slmj;
    }

    public Double getSzymj() {
        return szymj;
    }

    public void setSzymj(Double szymj) {
        this.szymj = szymj;
    }

    public Double getDynzrzyzmj() {
        return dynzrzyzmj;
    }

    public void setDynzrzyzmj(Double dynzrzyzmj) {
        this.dynzrzyzmj = dynzrzyzmj;
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
        ZrzkDO other = (ZrzkDO) that;
        return (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getYsdm() == null ? other.getYsdm() == null : this.getYsdm().equals(other.getYsdm()))
            && (this.getCymj() == null ? other.getCymj() == null : this.getCymj().equals(other.getCymj()))
            && (this.getDjdyzmj() == null ? other.getDjdyzmj() == null : this.getDjdyzmj().equals(other.getDjdyzmj()))
            && (this.getFzrzyzmj() == null ? other.getFzrzyzmj() == null : this.getFzrzyzmj().equals(other.getFzrzyzmj()))
            && (this.getHdmj() == null ? other.getHdmj() == null : this.getHdmj().equals(other.getHdmj()))
            && (this.getQtmj() == null ? other.getQtmj() == null : this.getQtmj().equals(other.getQtmj()))
            && (this.getSdmj() == null ? other.getSdmj() == null : this.getSdmj().equals(other.getSdmj()))
            && (this.getSlmj() == null ? other.getSlmj() == null : this.getSlmj().equals(other.getSlmj()))
            && (this.getSzymj() == null ? other.getSzymj() == null : this.getSzymj().equals(other.getSzymj()))
            && (this.getDynzrzyzmj() == null ? other.getDynzrzyzmj() == null : this.getDynzrzyzmj().equals(other.getDynzrzyzmj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getYsdm() == null) ? 0 : getYsdm().hashCode());
        result = prime * result + ((getCymj() == null) ? 0 : getCymj().hashCode());
        result = prime * result + ((getDjdyzmj() == null) ? 0 : getDjdyzmj().hashCode());
        result = prime * result + ((getFzrzyzmj() == null) ? 0 : getFzrzyzmj().hashCode());
        result = prime * result + ((getHdmj() == null) ? 0 : getHdmj().hashCode());
        result = prime * result + ((getQtmj() == null) ? 0 : getQtmj().hashCode());
        result = prime * result + ((getSdmj() == null) ? 0 : getSdmj().hashCode());
        result = prime * result + ((getSlmj() == null) ? 0 : getSlmj().hashCode());
        result = prime * result + ((getSzymj() == null) ? 0 : getSzymj().hashCode());
        result = prime * result + ((getDynzrzyzmj() == null) ? 0 : getDynzrzyzmj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bz=").append(bz);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", ysdm=").append(ysdm);
        sb.append(", cymj=").append(cymj);
        sb.append(", djdyzmj=").append(djdyzmj);
        sb.append(", fzrzyzmj=").append(fzrzyzmj);
        sb.append(", hdmj=").append(hdmj);
        sb.append(", qtmj=").append(qtmj);
        sb.append(", sdmj=").append(sdmj);
        sb.append(", slmj=").append(slmj);
        sb.append(", szymj=").append(szymj);
        sb.append(", dynzrzyzmj=").append(dynzrzyzmj);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}