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
 * @description 自然资源水流
 */
@Table(name="ZRZY_SZY")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzySzyDO.class)
@ApiModel(value="ZrzySzyDO")
@Data
public class ZrzySzyDO implements Serializable,ZrzyZrzk {
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
     * 自然资源登记单元号
     */
    @ApiModelProperty(value="自然资源登记单元号")
    private String zrzydjdyh;

    /**
     * 水流类型
     */
    @ApiModelProperty(value="水流类型")
    private String szylx;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String mc;

    /**
     * 包含图斑数量
     */
    @ApiModelProperty(value="包含图斑数量")
    private String bhtbsl;

    /**
     * 河流起讫点
     */
    @ApiModelProperty(value="河流起讫点")
    private String hlqqd;

    /**
     * 河流长度
     */
    @ApiModelProperty(value="河流长度")
    private Double hlcd;

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
     * 水面面积
     */
    @ApiModelProperty(value="水面面积")
    private Double smmj;

    /**
     * 河道等级
     */
    @ApiModelProperty(value="河道等级")
    private String hddj;

    /**
     * 多年平均径流量
     */
    @ApiModelProperty(value="多年平均径流量")
    private String dnpjjll;

    /**
     * 水质
     */
    @ApiModelProperty(value="水质")
    private String sz;

    /**
     * 年初蓄水量
     */
    @ApiModelProperty(value="年初蓄水量")
    private String ncxsl;

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

    public String getSzylx() {
        return szylx;
    }

    public void setSzylx(String szylx) {
        this.szylx = szylx;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getBhtbsl() {
        return bhtbsl;
    }

    public void setBhtbsl(String bhtbsl) {
        this.bhtbsl = bhtbsl;
    }

    public String getHlqqd() {
        return hlqqd;
    }

    public void setHlqqd(String hlqqd) {
        this.hlqqd = hlqqd;
    }

    public Double getHlcd() {
        return hlcd;
    }

    public void setHlcd(Double hlcd) {
        this.hlcd = hlcd;
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

    public Double getSmmj() {
        return smmj;
    }

    public void setSmmj(Double smmj) {
        this.smmj = smmj;
    }

    public String getHddj() {
        return hddj;
    }

    public void setHddj(String hddj) {
        this.hddj = hddj;
    }

    public String getDnpjjll() {
        return dnpjjll;
    }

    public void setDnpjjll(String dnpjjll) {
        this.dnpjjll = dnpjjll;
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz;
    }

    public String getNcxsl() {
        return ncxsl;
    }

    public void setNcxsl(String ncxsl) {
        this.ncxsl = ncxsl;
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
        ZrzySzyDO other = (ZrzySzyDO) that;
        return (this.getZkxxid() == null ? other.getZkxxid() == null : this.getZkxxid().equals(other.getZkxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getSzylx() == null ? other.getSzylx() == null : this.getSzylx().equals(other.getSzylx()))
            && (this.getMc() == null ? other.getMc() == null : this.getMc().equals(other.getMc()))
            && (this.getBhtbsl() == null ? other.getBhtbsl() == null : this.getBhtbsl().equals(other.getBhtbsl()))
            && (this.getHlqqd() == null ? other.getHlqqd() == null : this.getHlqqd().equals(other.getHlqqd()))
            && (this.getHlcd() == null ? other.getHlcd() == null : this.getHlcd().equals(other.getHlcd()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
            && (this.getSmmj() == null ? other.getSmmj() == null : this.getSmmj().equals(other.getSmmj()))
            && (this.getHddj() == null ? other.getHddj() == null : this.getHddj().equals(other.getHddj()))
            && (this.getDnpjjll() == null ? other.getDnpjjll() == null : this.getDnpjjll().equals(other.getDnpjjll()))
            && (this.getSz() == null ? other.getSz() == null : this.getSz().equals(other.getSz()))
            && (this.getNcxsl() == null ? other.getNcxsl() == null : this.getNcxsl().equals(other.getNcxsl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZkxxid() == null) ? 0 : getZkxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getSzylx() == null) ? 0 : getSzylx().hashCode());
        result = prime * result + ((getMc() == null) ? 0 : getMc().hashCode());
        result = prime * result + ((getBhtbsl() == null) ? 0 : getBhtbsl().hashCode());
        result = prime * result + ((getHlqqd() == null) ? 0 : getHlqqd().hashCode());
        result = prime * result + ((getHlcd() == null) ? 0 : getHlcd().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getSmmj() == null) ? 0 : getSmmj().hashCode());
        result = prime * result + ((getHddj() == null) ? 0 : getHddj().hashCode());
        result = prime * result + ((getDnpjjll() == null) ? 0 : getDnpjjll().hashCode());
        result = prime * result + ((getSz() == null) ? 0 : getSz().hashCode());
        result = prime * result + ((getNcxsl() == null) ? 0 : getNcxsl().hashCode());
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
        sb.append(", szylx=").append(szylx);
        sb.append(", mc=").append(mc);
        sb.append(", bhtbsl=").append(bhtbsl);
        sb.append(", hlqqd=").append(hlqqd);
        sb.append(", hlcd=").append(hlcd);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", smmj=").append(smmj);
        sb.append(", hddj=").append(hddj);
        sb.append(", dnpjjll=").append(dnpjjll);
        sb.append(", sz=").append(sz);
        sb.append(", ncxsl=").append(ncxsl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}