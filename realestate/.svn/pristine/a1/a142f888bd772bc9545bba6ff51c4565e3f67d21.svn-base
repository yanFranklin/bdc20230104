package cn.gtmap.realestate.common.core.domain.natural;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author
 * @description 自然资源森林
 */
@Table(name="ZRZY_SL")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzySlDO.class)
@ApiModel(value = "ZrzySlDO",description = "自然资源森林")
@Data
public class ZrzySlDO implements Serializable,ZrzyZrzk {
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
     * 森林类型
     */
    @ApiModelProperty(value="森林类型")
    private String sllx;

    /**
     * 包含图斑数量
     */
    @ApiModelProperty(value="包含图斑数量")
    private String bhtbsl;

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
     * 面积
     */
    @ApiModelProperty(value="面积")
    private Double mj;

    /**
     * 主导功能
     */
    @ApiModelProperty(value="主导功能")
    private String zdgn;

    /**
     * 主要树种
     */
    @ApiModelProperty(value="主要树种")
    private String zysz;

    /**
     * 林种
     */
    @ApiModelProperty(value="林种")
    private String lz;

    /**
     * 总蓄积量
     */
    @ApiModelProperty(value="总蓄积量")
    private String zxjl;

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

    public String getSllx() {
        return sllx;
    }

    public void setSllx(String sllx) {
        this.sllx = sllx;
    }

    public String getBhtbsl() {
        return bhtbsl;
    }

    public void setBhtbsl(String bhtbsl) {
        this.bhtbsl = bhtbsl;
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

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    public String getZdgn() {
        return zdgn;
    }

    public void setZdgn(String zdgn) {
        this.zdgn = zdgn;
    }

    public String getZysz() {
        return zysz;
    }

    public void setZysz(String zysz) {
        this.zysz = zysz;
    }

    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    public String getZxjl() {
        return zxjl;
    }

    public void setZxjl(String zxjl) {
        this.zxjl = zxjl;
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
        ZrzySlDO other = (ZrzySlDO) that;
        return (this.getZkxxid() == null ? other.getZkxxid() == null : this.getZkxxid().equals(other.getZkxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getSllx() == null ? other.getSllx() == null : this.getSllx().equals(other.getSllx()))
            && (this.getBhtbsl() == null ? other.getBhtbsl() == null : this.getBhtbsl().equals(other.getBhtbsl()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
            && (this.getMj() == null ? other.getMj() == null : this.getMj().equals(other.getMj()))
            && (this.getZdgn() == null ? other.getZdgn() == null : this.getZdgn().equals(other.getZdgn()))
            && (this.getZysz() == null ? other.getZysz() == null : this.getZysz().equals(other.getZysz()))
            && (this.getLz() == null ? other.getLz() == null : this.getLz().equals(other.getLz()))
            && (this.getZxjl() == null ? other.getZxjl() == null : this.getZxjl().equals(other.getZxjl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZkxxid() == null) ? 0 : getZkxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getSllx() == null) ? 0 : getSllx().hashCode());
        result = prime * result + ((getBhtbsl() == null) ? 0 : getBhtbsl().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getMj() == null) ? 0 : getMj().hashCode());
        result = prime * result + ((getZdgn() == null) ? 0 : getZdgn().hashCode());
        result = prime * result + ((getZysz() == null) ? 0 : getZysz().hashCode());
        result = prime * result + ((getLz() == null) ? 0 : getLz().hashCode());
        result = prime * result + ((getZxjl() == null) ? 0 : getZxjl().hashCode());
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
        sb.append(", sllx=").append(sllx);
        sb.append(", bhtbsl=").append(bhtbsl);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", mj=").append(mj);
        sb.append(", zdgn=").append(zdgn);
        sb.append(", zysz=").append(zysz);
        sb.append(", lz=").append(lz);
        sb.append(", zxjl=").append(zxjl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}