package cn.gtmap.realestate.common.core.domain.natural;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author
 * @description 自然资源探明储量矿产资源
 */
@Table(name="ZRZY_TMCLKCZY")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzyTmclkczyDO.class)
@ApiModel(value="ZrzyTmclkczyDO")
@Data
public class ZrzyTmclkczyDO implements Serializable,ZrzyZrzk {
    /**
     * 自然状况信息ID
     */
    @Id
    @ApiModelProperty(value="自然状况信息ID")
    private String zkxxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 自然资源单元号
     */
    @ApiModelProperty(value="自然资源单元号")
    private String zrzydjdyh;

    /**
     * 资源类型
     */
    @ApiModelProperty(value="资源类型")
    private String zrlx;

    /**
     * 区块编号
     */
    @ApiModelProperty(value="区块编号")
    private String qkbh;

    /**
     * 矿区地址
     */
    @ApiModelProperty(value="矿区地址")
    private String kqdz;

    /**
     * 储量估算基准日
     */
    @ApiModelProperty(value="储量估算基准日")
    private Date clgsjzr;

    /**
     * 国有面积
     */
    @ApiModelProperty(value="国有面积")
    private Double gymj;

    /**
     * 集体面积
     */
    @ApiModelProperty(value="集体面积")
    private Double jtmj;

    /**
     * 争议区面积
     */
    @ApiModelProperty(value="争议区面积")
    private Double zyqmj;

    /**
     * 矿区油气田总面积
     */
    @ApiModelProperty(value="矿区油气田总面积")
    private Double kqyqtzmj;

    /**
     * 储量估算范围面积
     */
    @ApiModelProperty(value="储量估算范围面积")
    private Double clgsfwmj;

    /**
     * 矿产组合
     */
    @ApiModelProperty(value="矿产组合")
    private String kczh;

    /**
     * 储量计量单位
     */
    @ApiModelProperty(value="储量计量单位")
    private String cljldw;

    /**
     * 固体矿产推断资源量
     */
    @ApiModelProperty(value="固体矿产推断资源量")
    private Double gtkctdzyl;

    /**
     * 固体矿产控制资源量
     */
    @ApiModelProperty(value="固体矿产控制资源量")
    private Double gtkckzzyl;

    /**
     * 固体矿产探明资源量
     */
    @ApiModelProperty(value="固体矿产探明资源量")
    private Double gtkctmzyl;

    /**
     * 油气探明地质储量
     */
    @ApiModelProperty(value="油气探明地质储量")
    private Double yqtmdzcl;

    /**
     * 主要组分平均品位
     */
    @ApiModelProperty(value="主要组分平均品位")
    private Double zyzfpjpw;

    public String getZkxxid() {
        return zkxxid;
    }

    public void setZkxxid(String zkxxid) {
        this.zkxxid = zkxxid;
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

    public String getZrlx() {
        return zrlx;
    }

    public void setZrlx(String zrlx) {
        this.zrlx = zrlx;
    }

    public String getQkbh() {
        return qkbh;
    }

    public void setQkbh(String qkbh) {
        this.qkbh = qkbh;
    }

    public String getKqdz() {
        return kqdz;
    }

    public void setKqdz(String kqdz) {
        this.kqdz = kqdz;
    }

    public Date getClgsjzr() {
        return clgsjzr;
    }

    public void setClgsjzr(Date clgsjzr) {
        this.clgsjzr = clgsjzr;
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

    public Double getKqyqtzmj() {
        return kqyqtzmj;
    }

    public void setKqyqtzmj(Double kqyqtzmj) {
        this.kqyqtzmj = kqyqtzmj;
    }

    public Double getClgsfwmj() {
        return clgsfwmj;
    }

    public void setClgsfwmj(Double clgsfwmj) {
        this.clgsfwmj = clgsfwmj;
    }

    public String getKczh() {
        return kczh;
    }

    public void setKczh(String kczh) {
        this.kczh = kczh;
    }

    public String getCljldw() {
        return cljldw;
    }

    public void setCljldw(String cljldw) {
        this.cljldw = cljldw;
    }

    public Double getGtkctdzyl() {
        return gtkctdzyl;
    }

    public void setGtkctdzyl(Double gtkctdzyl) {
        this.gtkctdzyl = gtkctdzyl;
    }

    public Double getGtkckzzyl() {
        return gtkckzzyl;
    }

    public void setGtkckzzyl(Double gtkckzzyl) {
        this.gtkckzzyl = gtkckzzyl;
    }

    public Double getGtkctmzyl() {
        return gtkctmzyl;
    }

    public void setGtkctmzyl(Double gtkctmzyl) {
        this.gtkctmzyl = gtkctmzyl;
    }

    public Double getYqtmdzcl() {
        return yqtmdzcl;
    }

    public void setYqtmdzcl(Double yqtmdzcl) {
        this.yqtmdzcl = yqtmdzcl;
    }

    public Double getZyzfpjpw() {
        return zyzfpjpw;
    }

    public void setZyzfpjpw(Double zyzfpjpw) {
        this.zyzfpjpw = zyzfpjpw;
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
        ZrzyTmclkczyDO other = (ZrzyTmclkczyDO) that;
        return (this.getZkxxid() == null ? other.getZkxxid() == null : this.getZkxxid().equals(other.getZkxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getZrlx() == null ? other.getZrlx() == null : this.getZrlx().equals(other.getZrlx()))
            && (this.getQkbh() == null ? other.getQkbh() == null : this.getQkbh().equals(other.getQkbh()))
            && (this.getKqdz() == null ? other.getKqdz() == null : this.getKqdz().equals(other.getKqdz()))
            && (this.getClgsjzr() == null ? other.getClgsjzr() == null : this.getClgsjzr().equals(other.getClgsjzr()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
            && (this.getKqyqtzmj() == null ? other.getKqyqtzmj() == null : this.getKqyqtzmj().equals(other.getKqyqtzmj()))
            && (this.getClgsfwmj() == null ? other.getClgsfwmj() == null : this.getClgsfwmj().equals(other.getClgsfwmj()))
            && (this.getKczh() == null ? other.getKczh() == null : this.getKczh().equals(other.getKczh()))
            && (this.getCljldw() == null ? other.getCljldw() == null : this.getCljldw().equals(other.getCljldw()))
            && (this.getGtkctdzyl() == null ? other.getGtkctdzyl() == null : this.getGtkctdzyl().equals(other.getGtkctdzyl()))
            && (this.getGtkckzzyl() == null ? other.getGtkckzzyl() == null : this.getGtkckzzyl().equals(other.getGtkckzzyl()))
            && (this.getGtkctmzyl() == null ? other.getGtkctmzyl() == null : this.getGtkctmzyl().equals(other.getGtkctmzyl()))
            && (this.getYqtmdzcl() == null ? other.getYqtmdzcl() == null : this.getYqtmdzcl().equals(other.getYqtmdzcl()))
            && (this.getZyzfpjpw() == null ? other.getZyzfpjpw() == null : this.getZyzfpjpw().equals(other.getZyzfpjpw()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZkxxid() == null) ? 0 : getZkxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getZrlx() == null) ? 0 : getZrlx().hashCode());
        result = prime * result + ((getQkbh() == null) ? 0 : getQkbh().hashCode());
        result = prime * result + ((getKqdz() == null) ? 0 : getKqdz().hashCode());
        result = prime * result + ((getClgsjzr() == null) ? 0 : getClgsjzr().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getKqyqtzmj() == null) ? 0 : getKqyqtzmj().hashCode());
        result = prime * result + ((getClgsfwmj() == null) ? 0 : getClgsfwmj().hashCode());
        result = prime * result + ((getKczh() == null) ? 0 : getKczh().hashCode());
        result = prime * result + ((getCljldw() == null) ? 0 : getCljldw().hashCode());
        result = prime * result + ((getGtkctdzyl() == null) ? 0 : getGtkctdzyl().hashCode());
        result = prime * result + ((getGtkckzzyl() == null) ? 0 : getGtkckzzyl().hashCode());
        result = prime * result + ((getGtkctmzyl() == null) ? 0 : getGtkctmzyl().hashCode());
        result = prime * result + ((getYqtmdzcl() == null) ? 0 : getYqtmdzcl().hashCode());
        result = prime * result + ((getZyzfpjpw() == null) ? 0 : getZyzfpjpw().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", zkxxid=").append(zkxxid);
        sb.append(", xmid=").append(xmid);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", zrlx=").append(zrlx);
        sb.append(", qkbh=").append(qkbh);
        sb.append(", kqdz=").append(kqdz);
        sb.append(", clgsjzr=").append(clgsjzr);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", kqyqtzmj=").append(kqyqtzmj);
        sb.append(", clgsfwmj=").append(clgsfwmj);
        sb.append(", kczh=").append(kczh);
        sb.append(", cljldw=").append(cljldw);
        sb.append(", gtkctdzyl=").append(gtkctdzyl);
        sb.append(", gtkckzzyl=").append(gtkckzzyl);
        sb.append(", gtkctmzyl=").append(gtkctmzyl);
        sb.append(", yqtmdzcl=").append(yqtmdzcl);
        sb.append(", zyzfpjpw=").append(zyzfpjpw);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}