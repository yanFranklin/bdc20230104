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
 * @description 自然资源湿地
 */
@Table(name="ZRZY_SD")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzySdDO.class)
@ApiModel(value="ZrzySdDO")
@Data
public class ZrzySdDO implements Serializable,ZrzyZrzk {
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
     * 湿地类型
     */
    @ApiModelProperty(value="湿地类型")
    private String sdlx;

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
     * 总面积
     */
    @ApiModelProperty(value="总面积")
    private Double zmj;

    /**
     * 植被类型
     */
    @ApiModelProperty(value="植被类型")
    private String zblx;

    /**
     * 植被面积
     */
    @ApiModelProperty(value="植被面积")
    private Double zbmj;

    /**
     * 主要优势植物种
     */
    @ApiModelProperty(value="主要优势植物种")
    private String zyyszwz;

    /**
     * 国家重点保护的主要湿地鸟类
     */
    @ApiModelProperty(value="国家重点保护的主要湿地鸟类")
    private String gjzdbhdzysdnl;

    /**
     * 水质类别
     */
    @ApiModelProperty(value="水质类别")
    private String szlb;

    /**
     * 水源补给状况
     */
    @ApiModelProperty(value="水源补给状况")
    private String sybjzk;

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

    public String getSdlx() {
        return sdlx;
    }

    public void setSdlx(String sdlx) {
        this.sdlx = sdlx;
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

    public Double getZmj() {
        return zmj;
    }

    public void setZmj(Double zmj) {
        this.zmj = zmj;
    }

    public String getZblx() {
        return zblx;
    }

    public void setZblx(String zblx) {
        this.zblx = zblx;
    }

    public Double getZbmj() {
        return zbmj;
    }

    public void setZbmj(Double zbmj) {
        this.zbmj = zbmj;
    }

    public String getZyyszwz() {
        return zyyszwz;
    }

    public void setZyyszwz(String zyyszwz) {
        this.zyyszwz = zyyszwz;
    }

    public String getGjzdbhdzysdnl() {
        return gjzdbhdzysdnl;
    }

    public void setGjzdbhdzysdnl(String gjzdbhdzysdnl) {
        this.gjzdbhdzysdnl = gjzdbhdzysdnl;
    }

    public String getSzlb() {
        return szlb;
    }

    public void setSzlb(String szlb) {
        this.szlb = szlb;
    }

    public String getSybjzk() {
        return sybjzk;
    }

    public void setSybjzk(String sybjzk) {
        this.sybjzk = sybjzk;
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
        ZrzySdDO other = (ZrzySdDO) that;
        return (this.getZkxxid() == null ? other.getZkxxid() == null : this.getZkxxid().equals(other.getZkxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getSdlx() == null ? other.getSdlx() == null : this.getSdlx().equals(other.getSdlx()))
            && (this.getBhtbsl() == null ? other.getBhtbsl() == null : this.getBhtbsl().equals(other.getBhtbsl()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
            && (this.getZmj() == null ? other.getZmj() == null : this.getZmj().equals(other.getZmj()))
            && (this.getZblx() == null ? other.getZblx() == null : this.getZblx().equals(other.getZblx()))
            && (this.getZbmj() == null ? other.getZbmj() == null : this.getZbmj().equals(other.getZbmj()))
            && (this.getZyyszwz() == null ? other.getZyyszwz() == null : this.getZyyszwz().equals(other.getZyyszwz()))
            && (this.getGjzdbhdzysdnl() == null ? other.getGjzdbhdzysdnl() == null : this.getGjzdbhdzysdnl().equals(other.getGjzdbhdzysdnl()))
            && (this.getSzlb() == null ? other.getSzlb() == null : this.getSzlb().equals(other.getSzlb()))
            && (this.getSybjzk() == null ? other.getSybjzk() == null : this.getSybjzk().equals(other.getSybjzk()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZkxxid() == null) ? 0 : getZkxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getSdlx() == null) ? 0 : getSdlx().hashCode());
        result = prime * result + ((getBhtbsl() == null) ? 0 : getBhtbsl().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getZmj() == null) ? 0 : getZmj().hashCode());
        result = prime * result + ((getZblx() == null) ? 0 : getZblx().hashCode());
        result = prime * result + ((getZbmj() == null) ? 0 : getZbmj().hashCode());
        result = prime * result + ((getZyyszwz() == null) ? 0 : getZyyszwz().hashCode());
        result = prime * result + ((getGjzdbhdzysdnl() == null) ? 0 : getGjzdbhdzysdnl().hashCode());
        result = prime * result + ((getSzlb() == null) ? 0 : getSzlb().hashCode());
        result = prime * result + ((getSybjzk() == null) ? 0 : getSybjzk().hashCode());
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
        sb.append(", sdlx=").append(sdlx);
        sb.append(", bhtbsl=").append(bhtbsl);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", zmj=").append(zmj);
        sb.append(", zblx=").append(zblx);
        sb.append(", zbmj=").append(zbmj);
        sb.append(", zyyszwz=").append(zyyszwz);
        sb.append(", gjzdbhdzysdnl=").append(gjzdbhdzysdnl);
        sb.append(", szlb=").append(szlb);
        sb.append(", sybjzk=").append(sybjzk);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}