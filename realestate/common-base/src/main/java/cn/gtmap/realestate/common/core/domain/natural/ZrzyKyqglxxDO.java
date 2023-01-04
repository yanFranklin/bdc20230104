package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ZRZY_KYQGLXX
 * @author 
 */
@Table(name="ZRZY_KYQGLXX")
@ApiModel(value="generate.ZrzyKyqglxxDo")
@Data
public class ZrzyKyqglxxDO implements Serializable {
    /**
     * 关联信息ID
     */
    @Id
    @ApiModelProperty(value="关联信息ID")
    private String glxxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 自然资源登记单元号
     */
    @ApiModelProperty(value="自然资源登记单元号")
    private String zrzydjdyh;

    /**
     * 许可证类型
     */
    @ApiModelProperty(value="许可证类型")
    private String xkzlx;

    /**
     * 勘查采矿许可证号
     */
    @ApiModelProperty(value="勘查采矿许可证号")
    private String kcckxkzh;

    /**
     * 权利类型
     */
    @ApiModelProperty(value="权利类型")
    private Integer qllx;

    /**
     * 勘查矿区面积
     */
    @ApiModelProperty(value="勘查矿区面积")
    private Double kckqmj;

    /**
     * 矿种
     */
    @ApiModelProperty(value="矿种")
    private String kz;

    /**
     * 有效期限
     */
    @ApiModelProperty(value="有效期限")
    private String yxqx;

    /**
     * 探矿采矿权人
     */
    @ApiModelProperty(value="探矿采矿权人")
    private String tkckqr;

    /**
     * 地址
     */
    @ApiModelProperty(value="地址")
    private String dz;

    /**
     * 发证机关
     */
    @ApiModelProperty(value="发证机关")
    private String fzjg;

    /**
     * 区县代码
     */
    @ApiModelProperty(value="区县代码")
    private String qxdm;

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

    public String getXkzlx() {
        return xkzlx;
    }

    public void setXkzlx(String xkzlx) {
        this.xkzlx = xkzlx;
    }

    public String getKcckxkzh() {
        return kcckxkzh;
    }

    public void setKcckxkzh(String kcckxkzh) {
        this.kcckxkzh = kcckxkzh;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Double getKckqmj() {
        return kckqmj;
    }

    public void setKckqmj(Double kckqmj) {
        this.kckqmj = kckqmj;
    }

    public String getKz() {
        return kz;
    }

    public void setKz(String kz) {
        this.kz = kz;
    }

    public String getYxqx() {
        return yxqx;
    }

    public void setYxqx(String yxqx) {
        this.yxqx = yxqx;
    }

    public String getTkckqr() {
        return tkckqr;
    }

    public void setTkckqr(String tkckqr) {
        this.tkckqr = tkckqr;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
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
        ZrzyKyqglxxDO other = (ZrzyKyqglxxDO) that;
        return (this.getGlxxid() == null ? other.getGlxxid() == null : this.getGlxxid().equals(other.getGlxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getXkzlx() == null ? other.getXkzlx() == null : this.getXkzlx().equals(other.getXkzlx()))
            && (this.getKcckxkzh() == null ? other.getKcckxkzh() == null : this.getKcckxkzh().equals(other.getKcckxkzh()))
            && (this.getQllx() == null ? other.getQllx() == null : this.getQllx().equals(other.getQllx()))
            && (this.getKckqmj() == null ? other.getKckqmj() == null : this.getKckqmj().equals(other.getKckqmj()))
            && (this.getKz() == null ? other.getKz() == null : this.getKz().equals(other.getKz()))
            && (this.getYxqx() == null ? other.getYxqx() == null : this.getYxqx().equals(other.getYxqx()))
            && (this.getTkckqr() == null ? other.getTkckqr() == null : this.getTkckqr().equals(other.getTkckqr()))
            && (this.getDz() == null ? other.getDz() == null : this.getDz().equals(other.getDz()))
            && (this.getFzjg() == null ? other.getFzjg() == null : this.getFzjg().equals(other.getFzjg()))
            && (this.getQxdm() == null ? other.getQxdm() == null : this.getQxdm().equals(other.getQxdm()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGlxxid() == null) ? 0 : getGlxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getXkzlx() == null) ? 0 : getXkzlx().hashCode());
        result = prime * result + ((getKcckxkzh() == null) ? 0 : getKcckxkzh().hashCode());
        result = prime * result + ((getQllx() == null) ? 0 : getQllx().hashCode());
        result = prime * result + ((getKckqmj() == null) ? 0 : getKckqmj().hashCode());
        result = prime * result + ((getKz() == null) ? 0 : getKz().hashCode());
        result = prime * result + ((getYxqx() == null) ? 0 : getYxqx().hashCode());
        result = prime * result + ((getTkckqr() == null) ? 0 : getTkckqr().hashCode());
        result = prime * result + ((getDz() == null) ? 0 : getDz().hashCode());
        result = prime * result + ((getFzjg() == null) ? 0 : getFzjg().hashCode());
        result = prime * result + ((getQxdm() == null) ? 0 : getQxdm().hashCode());
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
        sb.append(", xkzlx=").append(xkzlx);
        sb.append(", kcckxkzh=").append(kcckxkzh);
        sb.append(", qllx=").append(qllx);
        sb.append(", kckqmj=").append(kckqmj);
        sb.append(", kz=").append(kz);
        sb.append(", yxqx=").append(yxqx);
        sb.append(", tkckqr=").append(tkckqr);
        sb.append(", dz=").append(dz);
        sb.append(", fzjg=").append(fzjg);
        sb.append(", qxdm=").append(qxdm);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}