package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @author
 * @description 自然资源登记单元
 */
@Table(name = "ZRZY_DJDY")
@ApiModel(value = "ZrzyDjdyDO", description = "自然资源登记单元")
@Data
public class ZrzyDjdyDO implements Serializable {

    /**
     * 项目ID
     */
    @Id
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    /**
     * 自然资源登记单元号
     */
    @ApiModelProperty(value = "自然资源登记单元号")
    private String zrzydjdyh;

    /**
     * 登记单元名称
     */
    @ApiModelProperty(value = "登记单元名称")
    private String djdymc;

    /**
     * 登记单元类型
     */
    @ApiModelProperty(value = "登记单元类型")
    private String djdylx;

    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 登记单元总面积
     */
    @ApiModelProperty(value = "登记单元总面积")
    private Double djdyzmj;

    /**
     * 国有面积
     */
    @ApiModelProperty(value = "国有面积")
    private Double gymj;

    /**
     * 集体面积
     */
    @ApiModelProperty(value = "集体面积")
    private Double jtmj;

    /**
     * 争议区面积
     */
    @ApiModelProperty(value = "争议区面积")
    private Double zyqmj;

    /**
     * 单元四至东
     */
    @ApiModelProperty(value = "单元四至东")
    private String dyszd;

    /**
     * 单元四至南
     */
    @ApiModelProperty(value = "单元四至南")
    private String dyszn;

    /**
     * 单元四至西
     */
    @ApiModelProperty(value = "单元四至西")
    private String dyszx;

    /**
     * 单元四至北
     */
    @ApiModelProperty(value = "单元四至北")
    private String dyszb;

    /**
     * 所有权主体
     */
    @ApiModelProperty(value = "所有权主体")
    private String syqzt;

    /**
     * 代表行使主体
     */
    @ApiModelProperty(value = "代表行使主体")
    private String dbxszt;

    /**
     * 权利行使方式
     */
    @ApiModelProperty(value = "权利行使方式")
    private String qlxsfs;

    /**
     * 代理行使主体
     */
    @ApiModelProperty(value = "代理行使主体")
    private String dlxszt;

    /**
     * 行使内容
     */
    @ApiModelProperty(value = "行使内容")
    private String xsnr;

    /**
     * 单元内自然资源总面积
     */
    @ApiModelProperty(value = "单元内自然资源总面积")
    private Double dynzrzyzmj;

    /**
     * 水流面积
     */
    @ApiModelProperty(value = "水流面积")
    private Double szymj;

    /**
     * 湿地面积
     */
    @ApiModelProperty(value = "湿地面积")
    private Double sdmj;

    /**
     * 草原面积
     */
    @ApiModelProperty(value = "草原面积")
    private Double cymj;

    /**
     * 森林面积
     */
    @ApiModelProperty(value = "森林面积")
    private Double slmj;

    /**
     * 荒地面积
     */
    @ApiModelProperty(value = "荒地面积")
    private Double hdmj;

    /**
     * 其他面积
     */
    @ApiModelProperty(value = "其他面积")
    private Double qtmj;

    /**
     * 非自然资源总面积
     */
    @ApiModelProperty(value = "非自然资源总面积")
    private Double fzrzyzmj;

    /**
     * 附记
     */
    @ApiModelProperty(value = "附记")
    private String fj;

    private static final long serialVersionUID = 1L;

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

    public String getDjdymc() {
        return djdymc;
    }

    public void setDjdymc(String djdymc) {
        this.djdymc = djdymc;
    }

    public String getDjdylx() {
        return djdylx;
    }

    public void setDjdylx(String djdylx) {
        this.djdylx = djdylx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getDjdyzmj() {
        return djdyzmj;
    }

    public void setDjdyzmj(Double djdyzmj) {
        this.djdyzmj = djdyzmj;
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

    public String getDyszd() {
        return dyszd;
    }

    public void setDyszd(String dyszd) {
        this.dyszd = dyszd;
    }

    public String getDyszn() {
        return dyszn;
    }

    public void setDyszn(String dyszn) {
        this.dyszn = dyszn;
    }

    public String getDyszx() {
        return dyszx;
    }

    public void setDyszx(String dyszx) {
        this.dyszx = dyszx;
    }

    public String getDyszb() {
        return dyszb;
    }

    public void setDyszb(String dyszb) {
        this.dyszb = dyszb;
    }

    public String getSyqzt() {
        return syqzt;
    }

    public void setSyqzt(String syqzt) {
        this.syqzt = syqzt;
    }

    public String getDbxszt() {
        return dbxszt;
    }

    public void setDbxszt(String dbxszt) {
        this.dbxszt = dbxszt;
    }

    public String getQlxsfs() {
        return qlxsfs;
    }

    public void setQlxsfs(String qlxsfs) {
        this.qlxsfs = qlxsfs;
    }

    public String getDlxszt() {
        return dlxszt;
    }

    public void setDlxszt(String dlxszt) {
        this.dlxszt = dlxszt;
    }

    public String getXsnr() {
        return xsnr;
    }

    public void setXsnr(String xsnr) {
        this.xsnr = xsnr;
    }

    public Double getDynzrzyzmj() {
        return dynzrzyzmj;
    }

    public void setDynzrzyzmj(Double dynzrzyzmj) {
        this.dynzrzyzmj = dynzrzyzmj;
    }

    public Double getSzymj() {
        return szymj;
    }

    public void setSzymj(Double szymj) {
        this.szymj = szymj;
    }

    public Double getSdmj() {
        return sdmj;
    }

    public void setSdmj(Double sdmj) {
        this.sdmj = sdmj;
    }

    public Double getCymj() {
        return cymj;
    }

    public void setCymj(Double cymj) {
        this.cymj = cymj;
    }

    public Double getSlmj() {
        return slmj;
    }

    public void setSlmj(Double slmj) {
        this.slmj = slmj;
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

    public Double getFzrzyzmj() {
        return fzrzyzmj;
    }

    public void setFzrzyzmj(Double fzrzyzmj) {
        this.fzrzyzmj = fzrzyzmj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

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
        ZrzyDjdyDO other = (ZrzyDjdyDO) that;
        return (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
                && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
                && (this.getDjdymc() == null ? other.getDjdymc() == null : this.getDjdymc().equals(other.getDjdymc()))
                && (this.getDjdylx() == null ? other.getDjdylx() == null : this.getDjdylx().equals(other.getDjdylx()))
                && (this.getZl() == null ? other.getZl() == null : this.getZl().equals(other.getZl()))
                && (this.getDjdyzmj() == null ? other.getDjdyzmj() == null : this.getDjdyzmj().equals(other.getDjdyzmj()))
                && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
                && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
                && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()))
                && (this.getDyszd() == null ? other.getDyszd() == null : this.getDyszd().equals(other.getDyszd()))
                && (this.getDyszn() == null ? other.getDyszn() == null : this.getDyszn().equals(other.getDyszn()))
                && (this.getDyszx() == null ? other.getDyszx() == null : this.getDyszx().equals(other.getDyszx()))
                && (this.getDyszb() == null ? other.getDyszb() == null : this.getDyszb().equals(other.getDyszb()))
                && (this.getSyqzt() == null ? other.getSyqzt() == null : this.getSyqzt().equals(other.getSyqzt()))
                && (this.getDbxszt() == null ? other.getDbxszt() == null : this.getDbxszt().equals(other.getDbxszt()))
                && (this.getQlxsfs() == null ? other.getQlxsfs() == null : this.getQlxsfs().equals(other.getQlxsfs()))
                && (this.getDlxszt() == null ? other.getDlxszt() == null : this.getDlxszt().equals(other.getDlxszt()))
                && (this.getXsnr() == null ? other.getXsnr() == null : this.getXsnr().equals(other.getXsnr()))
                && (this.getDynzrzyzmj() == null ? other.getDynzrzyzmj() == null : this.getDynzrzyzmj().equals(other.getDynzrzyzmj()))
                && (this.getSzymj() == null ? other.getSzymj() == null : this.getSzymj().equals(other.getSzymj()))
                && (this.getSdmj() == null ? other.getSdmj() == null : this.getSdmj().equals(other.getSdmj()))
                && (this.getCymj() == null ? other.getCymj() == null : this.getCymj().equals(other.getCymj()))
                && (this.getSlmj() == null ? other.getSlmj() == null : this.getSlmj().equals(other.getSlmj()))
                && (this.getHdmj() == null ? other.getHdmj() == null : this.getHdmj().equals(other.getHdmj()))
                && (this.getQtmj() == null ? other.getQtmj() == null : this.getQtmj().equals(other.getQtmj()))
                && (this.getFzrzyzmj() == null ? other.getFzrzyzmj() == null : this.getFzrzyzmj().equals(other.getFzrzyzmj()))
                && (this.getFj() == null ? other.getFj() == null : this.getFj().equals(other.getFj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getDjdymc() == null) ? 0 : getDjdymc().hashCode());
        result = prime * result + ((getDjdylx() == null) ? 0 : getDjdylx().hashCode());
        result = prime * result + ((getZl() == null) ? 0 : getZl().hashCode());
        result = prime * result + ((getDjdyzmj() == null) ? 0 : getDjdyzmj().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        result = prime * result + ((getDyszd() == null) ? 0 : getDyszd().hashCode());
        result = prime * result + ((getDyszn() == null) ? 0 : getDyszn().hashCode());
        result = prime * result + ((getDyszx() == null) ? 0 : getDyszx().hashCode());
        result = prime * result + ((getDyszb() == null) ? 0 : getDyszb().hashCode());
        result = prime * result + ((getSyqzt() == null) ? 0 : getSyqzt().hashCode());
        result = prime * result + ((getDbxszt() == null) ? 0 : getDbxszt().hashCode());
        result = prime * result + ((getQlxsfs() == null) ? 0 : getQlxsfs().hashCode());
        result = prime * result + ((getDlxszt() == null) ? 0 : getDlxszt().hashCode());
        result = prime * result + ((getXsnr() == null) ? 0 : getXsnr().hashCode());
        result = prime * result + ((getDynzrzyzmj() == null) ? 0 : getDynzrzyzmj().hashCode());
        result = prime * result + ((getSzymj() == null) ? 0 : getSzymj().hashCode());
        result = prime * result + ((getSdmj() == null) ? 0 : getSdmj().hashCode());
        result = prime * result + ((getCymj() == null) ? 0 : getCymj().hashCode());
        result = prime * result + ((getSlmj() == null) ? 0 : getSlmj().hashCode());
        result = prime * result + ((getHdmj() == null) ? 0 : getHdmj().hashCode());
        result = prime * result + ((getQtmj() == null) ? 0 : getQtmj().hashCode());
        result = prime * result + ((getFzrzyzmj() == null) ? 0 : getFzrzyzmj().hashCode());
        result = prime * result + ((getFj() == null) ? 0 : getFj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZrzyDjdyDO{");
        sb.append("xmid='").append(xmid).append('\'');
        sb.append(", zrzydjdyh='").append(zrzydjdyh).append('\'');
        sb.append(", djdymc='").append(djdymc).append('\'');
        sb.append(", djdylx='").append(djdylx).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", djdyzmj=").append(djdyzmj);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", dyszd='").append(dyszd).append('\'');
        sb.append(", dyszn='").append(dyszn).append('\'');
        sb.append(", dyszx='").append(dyszx).append('\'');
        sb.append(", dyszb='").append(dyszb).append('\'');
        sb.append(", syqzt='").append(syqzt).append('\'');
        sb.append(", dbxszt='").append(dbxszt).append('\'');
        sb.append(", qlxsfs='").append(qlxsfs).append('\'');
        sb.append(", dlxszt='").append(dlxszt).append('\'');
        sb.append(", xsnr='").append(xsnr).append('\'');
        sb.append(", dynzrzyzmj=").append(dynzrzyzmj);
        sb.append(", szymj=").append(szymj);
        sb.append(", sdmj=").append(sdmj);
        sb.append(", cymj=").append(cymj);
        sb.append(", slmj=").append(slmj);
        sb.append(", hdmj=").append(hdmj);
        sb.append(", qtmj=").append(qtmj);
        sb.append(", fzrzyzmj=").append(fzrzyzmj);
        sb.append(", fj='").append(fj).append('\'');
        sb.append('}');
        return sb.toString();
    }
}