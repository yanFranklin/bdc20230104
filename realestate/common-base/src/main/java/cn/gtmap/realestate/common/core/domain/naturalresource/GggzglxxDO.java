package cn.gtmap.realestate.common.core.domain.naturalresource;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * GGGZGLXX
 * @author 
 */
@Table(name = "Gggzglxx")
@Data
public class GggzglxxDO implements Serializable {
    private String zrzydjdyh;

    private String gggzyslx;

    private String gggznr;

    private Date hdsdsj;

    private String qkbh;

    private String szdw;

    private String ysdm;

    private String gggzfqbsm;

    private String bz;

    private Double mj;

    @ApiModelProperty(value = "划定设定时间")
    private Date gdsdsj;

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getGggzyslx() {
        return gggzyslx;
    }

    public void setGggzyslx(String gggzyslx) {
        this.gggzyslx = gggzyslx;
    }

    public String getGggznr() {
        return gggznr;
    }

    public void setGggznr(String gggznr) {
        this.gggznr = gggznr;
    }

    public Date getHdsdsj() {
        return hdsdsj;
    }

    public void setHdsdsj(Date hdsdsj) {
        this.hdsdsj = hdsdsj;
    }

    public String getQkbh() {
        return qkbh;
    }

    public void setQkbh(String qkbh) {
        this.qkbh = qkbh;
    }

    public String getSzdw() {
        return szdw;
    }

    public void setSzdw(String szdw) {
        this.szdw = szdw;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getGggzfqbsm() {
        return gggzfqbsm;
    }

    public void setGggzfqbsm(String gggzfqbsm) {
        this.gggzfqbsm = gggzfqbsm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    public Date getGdsdsj() {
        return gdsdsj;
    }

    public void setGdsdsj(Date gdsdsj) {
        this.gdsdsj = gdsdsj;
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
        GggzglxxDO other = (GggzglxxDO) that;
        return (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getGggzyslx() == null ? other.getGggzyslx() == null : this.getGggzyslx().equals(other.getGggzyslx()))
            && (this.getGggznr() == null ? other.getGggznr() == null : this.getGggznr().equals(other.getGggznr()))
            && (this.getHdsdsj() == null ? other.getHdsdsj() == null : this.getHdsdsj().equals(other.getHdsdsj()))
            && (this.getQkbh() == null ? other.getQkbh() == null : this.getQkbh().equals(other.getQkbh()))
            && (this.getSzdw() == null ? other.getSzdw() == null : this.getSzdw().equals(other.getSzdw()))
            && (this.getYsdm() == null ? other.getYsdm() == null : this.getYsdm().equals(other.getYsdm()))
            && (this.getGggzfqbsm() == null ? other.getGggzfqbsm() == null : this.getGggzfqbsm().equals(other.getGggzfqbsm()))
            && (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()))
            && (this.getMj() == null ? other.getMj() == null : this.getMj().equals(other.getMj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getGggzyslx() == null) ? 0 : getGggzyslx().hashCode());
        result = prime * result + ((getGggznr() == null) ? 0 : getGggznr().hashCode());
        result = prime * result + ((getHdsdsj() == null) ? 0 : getHdsdsj().hashCode());
        result = prime * result + ((getQkbh() == null) ? 0 : getQkbh().hashCode());
        result = prime * result + ((getSzdw() == null) ? 0 : getSzdw().hashCode());
        result = prime * result + ((getYsdm() == null) ? 0 : getYsdm().hashCode());
        result = prime * result + ((getGggzfqbsm() == null) ? 0 : getGggzfqbsm().hashCode());
        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
        result = prime * result + ((getMj() == null) ? 0 : getMj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", gggzyslx=").append(gggzyslx);
        sb.append(", gggznr=").append(gggznr);
        sb.append(", hdsdsj=").append(hdsdsj);
        sb.append(", qkbh=").append(qkbh);
        sb.append(", szdw=").append(szdw);
        sb.append(", ysdm=").append(ysdm);
        sb.append(", gggzfqbsm=").append(gggzfqbsm);
        sb.append(", bz=").append(bz);
        sb.append(", mj=").append(mj);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}