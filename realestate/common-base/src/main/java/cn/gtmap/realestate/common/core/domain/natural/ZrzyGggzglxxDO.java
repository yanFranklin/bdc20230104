package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @description 自然资源公共管制关联信息
 */
@Table(name = "ZRZY_GGGZGLXX")
@ApiModel(value = "ZrzyGggzglxxDO", description = "自然资源公共管制关联信息")
@Data
public class ZrzyGggzglxxDO implements Serializable {
    /**
     * 关联信息ID
     */
    @Id
    @ApiModelProperty(value = "关联信息ID")
    private String glxxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    /**
     * 自然资源登记单元号
     */
    @ApiModelProperty(value = "自然资源登记单元号")
    private String zrzydjdyh;

    /**
     * 公共管制要素类型
     */
    @ApiModelProperty(value = "公共管制要素类型")
    private String gggzyslx;

    /**
     * 公共管制分区标识码
     */
    @ApiModelProperty(value = "公共管制分区标识码")
    private String gggzfqbsm;

    /**
     * 区块编号
     */
    @ApiModelProperty(value = "区块编号")
    private String qkbh;

    /**
     * 面积
     */
    @ApiModelProperty(value = "面积")
    private Double mj;

    /**
     * 公共管制内容
     */
    @ApiModelProperty(value = "公共管制内容")
    private String gggznr;

    /**
     * 划定设定时间
     */
    @ApiModelProperty(value = "划定设定时间")
    private Date gdsdsj;

    /**
     * 设置单位
     */
    @ApiModelProperty(value = "设置单位")
    private String szdw;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    public String getGlxxid() {
        return glxxid;
    }

    public void setGlxxid(String glxxid) {
        this.glxxid = glxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

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

    public String getGggzfqbsm() {
        return gggzfqbsm;
    }

    public void setGggzfqbsm(String gggzfqbsm) {
        this.gggzfqbsm = gggzfqbsm;
    }

    public String getQkbh() {
        return qkbh;
    }

    public void setQkbh(String qkbh) {
        this.qkbh = qkbh;
    }

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    public String getGggznr() {
        return gggznr;
    }

    public void setGggznr(String gggznr) {
        this.gggznr = gggznr;
    }

    public Date getGdsdsj() {
        return gdsdsj;
    }

    public void setGdsdsj(Date gdsdsj) {
        this.gdsdsj = gdsdsj;
    }

    public String getSzdw() {
        return szdw;
    }

    public void setSzdw(String szdw) {
        this.szdw = szdw;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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
        ZrzyGggzglxxDO other = (ZrzyGggzglxxDO) that;
        return (this.getGlxxid() == null ? other.getGlxxid() == null : this.getGlxxid().equals(other.getGlxxid()))
                && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
                && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
                && (this.getGggzyslx() == null ? other.getGggzyslx() == null : this.getGggzyslx().equals(other.getGggzyslx()))
                && (this.getGggzfqbsm() == null ? other.getGggzfqbsm() == null : this.getGggzfqbsm().equals(other.getGggzfqbsm()))
                && (this.getQkbh() == null ? other.getQkbh() == null : this.getQkbh().equals(other.getQkbh()))
                && (this.getMj() == null ? other.getMj() == null : this.getMj().equals(other.getMj()))
                && (this.getGggznr() == null ? other.getGggznr() == null : this.getGggznr().equals(other.getGggznr()))
                && (this.getGdsdsj() == null ? other.getGdsdsj() == null : this.getGdsdsj().equals(other.getGdsdsj()))
                && (this.getSzdw() == null ? other.getSzdw() == null : this.getSzdw().equals(other.getSzdw()))
                && (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGlxxid() == null) ? 0 : getGlxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getGggzyslx() == null) ? 0 : getGggzyslx().hashCode());
        result = prime * result + ((getGggzfqbsm() == null) ? 0 : getGggzfqbsm().hashCode());
        result = prime * result + ((getQkbh() == null) ? 0 : getQkbh().hashCode());
        result = prime * result + ((getMj() == null) ? 0 : getMj().hashCode());
        result = prime * result + ((getGggznr() == null) ? 0 : getGggznr().hashCode());
        result = prime * result + ((getGdsdsj() == null) ? 0 : getGdsdsj().hashCode());
        result = prime * result + ((getSzdw() == null) ? 0 : getSzdw().hashCode());
        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", glxxid=").append(glxxid);
        sb.append(", xmid=").append(xmid);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", gggzyslx=").append(gggzyslx);
        sb.append(", gggzfqbsm=").append(gggzfqbsm);
        sb.append(", qkbh=").append(qkbh);
        sb.append(", mj=").append(mj);
        sb.append(", gggznr=").append(gggznr);
        sb.append(", gdsdsj=").append(gdsdsj);
        sb.append(", szdw=").append(szdw);
        sb.append(", bz=").append(bz);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}