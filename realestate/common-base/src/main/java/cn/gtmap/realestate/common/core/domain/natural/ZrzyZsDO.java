package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ZRZY_ZS
 * @author 
 */
@Table(name="ZRZY_ZS")
@ApiModel(value="generate.ZrzyZsDo")
@Data
public class ZrzyZsDO implements Serializable {
    /**
     * 证书ID
     */
    @Id
    @ApiModelProperty(value="证书ID")
    private String zsid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 自然资源产权证号
     */
    @ApiModelProperty(value="自然资源产权证号")
    private String zrzycqzh;

    /**
     * 证号流水号
     */
    @ApiModelProperty(value="证号流水号")
    private String zhlsh;

    @ApiModelProperty(value = "证书序列号")
    private Integer zhxlh;

    /**
     * 年份
     */
    @ApiModelProperty(value="年份")
    private Short nf;

    /**
     * 权证印刷序列号
     */
    @ApiModelProperty(value="权证印刷序列号")
    private String qzysxlh;

    /**
     * 自然资源状况
     */
    @ApiModelProperty(value="自然资源状况")
    private String zrzyzk;

    /**
     * 附记
     */
    @ApiModelProperty(value="附记")
    private String fj;

    /**
     * 省区市简称
     */
    @ApiModelProperty(value="省区市简称")
    private String sqsjc;

    /**
     * 所在市县全称
     */
    @ApiModelProperty(value="所在市县全称")
    private String szqxqc;

    /**
     * 发证人
     */
    @ApiModelProperty(value="发证人")
    private String fzr;

    /**
     * 发证人ID
     */
    @ApiModelProperty(value="发证人ID")
    private String fzrid;

    /**
     * 发证时间
     */
    @ApiModelProperty(value="发证时间")
    private Date fzsj;

    /**
     * 缮证人
     */
    @ApiModelProperty(value="缮证人")
    private String szr;

    /**
     * 缮证人ID
     */
    @ApiModelProperty(value="缮证人ID")
    private String szrid;

    /**
     * 缮证时间
     */
    @ApiModelProperty(value="缮证时间")
    private Date szsj;

    /**
     * 二维码内容
     */
    @ApiModelProperty(value="二维码内容")
    private String ewmnr;

    /**
     * 自然资源产权证号简称
     */
    @ApiModelProperty(value="自然资源产权证号简称")
    private String zrzycqzhjc;

    /**
     * 自然资源单元号
     */
    @ApiModelProperty(value="自然资源单元号")
    private String zrzydjdyh;

    /**
     * 登记单元名称
     */
    @ApiModelProperty(value="登记单元名称")
    private String djdymc;

    /**
     * 登记单元类型
     */
    @ApiModelProperty(value="登记单元类型")
    private String djdylx;

    /**
     * 空间范围
     */
    @ApiModelProperty(value="空间范围")
    private String kjfw;

    /**
     * 所有权主体
     */
    @ApiModelProperty(value="所有权主体")
    private String syqzt;

    /**
     * 代表行使主体
     */
    @ApiModelProperty(value="代表行使主体")
    private String dbxszt;

    /**
     * 权利行使方式
     */
    @ApiModelProperty(value="权利行使方式")
    private String qlxsfs;

    /**
     * 代理行使主体
     */
    @ApiModelProperty(value="代理行使主体")
    private String dlxszt;

    /**
     * 行使内容
     */
    @ApiModelProperty(value="行使内容")
    private String xsnr;

    /**
     * 登记单元总面积
     */
    @ApiModelProperty(value="登记单元总面积")
    private Double mj;

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
     * 区县代码
     */
    @ApiModelProperty(value="区县代码")
    private String qxdm;

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZrzycqzh() {
        return zrzycqzh;
    }

    public void setZrzycqzh(String zrzycqzh) {
        this.zrzycqzh = zrzycqzh;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public Short getNf() {
        return nf;
    }

    public void setNf(Short nf) {
        this.nf = nf;
    }

    public String getQzysxlh() {
        return qzysxlh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    public String getZrzyzk() {
        return zrzyzk;
    }

    public void setZrzyzk(String zrzyzk) {
        this.zrzyzk = zrzyzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getSqsjc() {
        return sqsjc;
    }

    public void setSqsjc(String sqsjc) {
        this.sqsjc = sqsjc;
    }

    public String getSzqxqc() {
        return szqxqc;
    }

    public void setSzqxqc(String szqxqc) {
        this.szqxqc = szqxqc;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getFzrid() {
        return fzrid;
    }

    public void setFzrid(String fzrid) {
        this.fzrid = fzrid;
    }

    public Date getFzsj() {
        return fzsj;
    }

    public void setFzsj(Date fzsj) {
        this.fzsj = fzsj;
    }

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    public String getSzrid() {
        return szrid;
    }

    public void setSzrid(String szrid) {
        this.szrid = szrid;
    }

    public Date getSzsj() {
        return szsj;
    }

    public void setSzsj(Date szsj) {
        this.szsj = szsj;
    }

    public String getEwmnr() {
        return ewmnr;
    }

    public void setEwmnr(String ewmnr) {
        this.ewmnr = ewmnr;
    }

    public String getZrzycqzhjc() {
        return zrzycqzhjc;
    }

    public void setZrzycqzhjc(String zrzycqzhjc) {
        this.zrzycqzhjc = zrzycqzhjc;
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

    public String getKjfw() {
        return kjfw;
    }

    public void setKjfw(String kjfw) {
        this.kjfw = kjfw;
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

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
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

    public Integer getZhxlh() {
        return zhxlh;
    }

    public void setZhxlh(Integer zhxlh) {
        this.zhxlh = zhxlh;
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
        ZrzyZsDO other = (ZrzyZsDO) that;
        return (this.getZsid() == null ? other.getZsid() == null : this.getZsid().equals(other.getZsid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzycqzh() == null ? other.getZrzycqzh() == null : this.getZrzycqzh().equals(other.getZrzycqzh()))
            && (this.getZhlsh() == null ? other.getZhlsh() == null : this.getZhlsh().equals(other.getZhlsh()))
            && (this.getNf() == null ? other.getNf() == null : this.getNf().equals(other.getNf()))
            && (this.getQzysxlh() == null ? other.getQzysxlh() == null : this.getQzysxlh().equals(other.getQzysxlh()))
            && (this.getZrzyzk() == null ? other.getZrzyzk() == null : this.getZrzyzk().equals(other.getZrzyzk()))
            && (this.getFj() == null ? other.getFj() == null : this.getFj().equals(other.getFj()))
            && (this.getSqsjc() == null ? other.getSqsjc() == null : this.getSqsjc().equals(other.getSqsjc()))
            && (this.getSzqxqc() == null ? other.getSzqxqc() == null : this.getSzqxqc().equals(other.getSzqxqc()))
            && (this.getFzr() == null ? other.getFzr() == null : this.getFzr().equals(other.getFzr()))
            && (this.getFzrid() == null ? other.getFzrid() == null : this.getFzrid().equals(other.getFzrid()))
            && (this.getFzsj() == null ? other.getFzsj() == null : this.getFzsj().equals(other.getFzsj()))
            && (this.getSzr() == null ? other.getSzr() == null : this.getSzr().equals(other.getSzr()))
            && (this.getSzrid() == null ? other.getSzrid() == null : this.getSzrid().equals(other.getSzrid()))
            && (this.getSzsj() == null ? other.getSzsj() == null : this.getSzsj().equals(other.getSzsj()))
            && (this.getEwmnr() == null ? other.getEwmnr() == null : this.getEwmnr().equals(other.getEwmnr()))
            && (this.getZrzycqzhjc() == null ? other.getZrzycqzhjc() == null : this.getZrzycqzhjc().equals(other.getZrzycqzhjc()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getDjdymc() == null ? other.getDjdymc() == null : this.getDjdymc().equals(other.getDjdymc()))
            && (this.getDjdylx() == null ? other.getDjdylx() == null : this.getDjdylx().equals(other.getDjdylx()))
            && (this.getKjfw() == null ? other.getKjfw() == null : this.getKjfw().equals(other.getKjfw()))
            && (this.getSyqzt() == null ? other.getSyqzt() == null : this.getSyqzt().equals(other.getSyqzt()))
            && (this.getDbxszt() == null ? other.getDbxszt() == null : this.getDbxszt().equals(other.getDbxszt()))
            && (this.getQlxsfs() == null ? other.getQlxsfs() == null : this.getQlxsfs().equals(other.getQlxsfs()))
            && (this.getDlxszt() == null ? other.getDlxszt() == null : this.getDlxszt().equals(other.getDlxszt()))
            && (this.getXsnr() == null ? other.getXsnr() == null : this.getXsnr().equals(other.getXsnr()))
            && (this.getMj() == null ? other.getMj() == null : this.getMj().equals(other.getMj()))
            && (this.getGymj() == null ? other.getGymj() == null : this.getGymj().equals(other.getGymj()))
            && (this.getJtmj() == null ? other.getJtmj() == null : this.getJtmj().equals(other.getJtmj()))
            && (this.getZyqmj() == null ? other.getZyqmj() == null : this.getZyqmj().equals(other.getZyqmj()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getZsid() == null) ? 0 : getZsid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getZrzycqzh() == null) ? 0 : getZrzycqzh().hashCode());
        result = prime * result + ((getZhlsh() == null) ? 0 : getZhlsh().hashCode());
        result = prime * result + ((getNf() == null) ? 0 : getNf().hashCode());
        result = prime * result + ((getQzysxlh() == null) ? 0 : getQzysxlh().hashCode());
        result = prime * result + ((getZrzyzk() == null) ? 0 : getZrzyzk().hashCode());
        result = prime * result + ((getFj() == null) ? 0 : getFj().hashCode());
        result = prime * result + ((getSqsjc() == null) ? 0 : getSqsjc().hashCode());
        result = prime * result + ((getSzqxqc() == null) ? 0 : getSzqxqc().hashCode());
        result = prime * result + ((getFzr() == null) ? 0 : getFzr().hashCode());
        result = prime * result + ((getFzrid() == null) ? 0 : getFzrid().hashCode());
        result = prime * result + ((getFzsj() == null) ? 0 : getFzsj().hashCode());
        result = prime * result + ((getSzr() == null) ? 0 : getSzr().hashCode());
        result = prime * result + ((getSzrid() == null) ? 0 : getSzrid().hashCode());
        result = prime * result + ((getSzsj() == null) ? 0 : getSzsj().hashCode());
        result = prime * result + ((getEwmnr() == null) ? 0 : getEwmnr().hashCode());
        result = prime * result + ((getZrzycqzhjc() == null) ? 0 : getZrzycqzhjc().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getDjdymc() == null) ? 0 : getDjdymc().hashCode());
        result = prime * result + ((getDjdylx() == null) ? 0 : getDjdylx().hashCode());
        result = prime * result + ((getKjfw() == null) ? 0 : getKjfw().hashCode());
        result = prime * result + ((getSyqzt() == null) ? 0 : getSyqzt().hashCode());
        result = prime * result + ((getDbxszt() == null) ? 0 : getDbxszt().hashCode());
        result = prime * result + ((getQlxsfs() == null) ? 0 : getQlxsfs().hashCode());
        result = prime * result + ((getDlxszt() == null) ? 0 : getDlxszt().hashCode());
        result = prime * result + ((getXsnr() == null) ? 0 : getXsnr().hashCode());
        result = prime * result + ((getMj() == null) ? 0 : getMj().hashCode());
        result = prime * result + ((getGymj() == null) ? 0 : getGymj().hashCode());
        result = prime * result + ((getJtmj() == null) ? 0 : getJtmj().hashCode());
        result = prime * result + ((getZyqmj() == null) ? 0 : getZyqmj().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", zsid=").append(zsid);
        sb.append(", xmid=").append(xmid);
        sb.append(", zrzycqzh=").append(zrzycqzh);
        sb.append(", zhlsh=").append(zhlsh);
        sb.append(", nf=").append(nf);
        sb.append(", qzysxlh=").append(qzysxlh);
        sb.append(", zrzyzk=").append(zrzyzk);
        sb.append(", fj=").append(fj);
        sb.append(", sqsjc=").append(sqsjc);
        sb.append(", szqxqc=").append(szqxqc);
        sb.append(", fzr=").append(fzr);
        sb.append(", fzrid=").append(fzrid);
        sb.append(", fzsj=").append(fzsj);
        sb.append(", szr=").append(szr);
        sb.append(", szrid=").append(szrid);
        sb.append(", szsj=").append(szsj);
        sb.append(", ewmnr=").append(ewmnr);
        sb.append(", zrzycqzhjc=").append(zrzycqzhjc);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", djdymc=").append(djdymc);
        sb.append(", djdylx=").append(djdylx);
        sb.append(", kjfw=").append(kjfw);
        sb.append(", syqzt=").append(syqzt);
        sb.append(", dbxszt=").append(dbxszt);
        sb.append(", qlxsfs=").append(qlxsfs);
        sb.append(", dlxszt=").append(dlxszt);
        sb.append(", xsnr=").append(xsnr);
        sb.append(", mj=").append(mj);
        sb.append(", gymj=").append(gymj);
        sb.append(", jtmj=").append(jtmj);
        sb.append(", zyqmj=").append(zyqmj);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}