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
 * ZRZY_GDXX
 * @author 
 */
@Table(name="ZRZY_GDXX")
@Data
public class ZrzyGdxxDO implements Serializable {
    /**
     * 归档信息ID
     */
    @Id
    @ApiModelProperty(value="归档信息ID")
    private String gdxxid;

    /**
     * 项目ID
     */
    @NotEmpty
    @ApiModelProperty(value="项目ID")
    private String xmid;

    /**
     * 归档ID
     */
    @ApiModelProperty(value="归档ID")
    private String daid;

    /**
     * 目录号
     */
    @ApiModelProperty(value="目录号")
    private String mlh;

    /**
     * 案卷号
     */
    @ApiModelProperty(value="案卷号")
    private String ajh;

    /**
     * 档案模型
     */
    @ApiModelProperty(value="档案模型")
    private String damx;

    /**
     * 档案号
     */
    @ApiModelProperty(value="档案号")
    private String dah;

    /**
     * 案卷件数
     */
    @ApiModelProperty(value="案卷件数")
    private Long ajjs;

    /**
     * 案卷页数
     */
    @ApiModelProperty(value="案卷页数")
    private Long ajys;

    /**
     * 归档时间
     */
    @ApiModelProperty(value="归档时间")
    private Date gdsj;

    /**
     * 归档人员姓名
     */
    @ApiModelProperty(value="归档人员姓名")
    private String gdryxm;

    /**
     * 归档信息
     */
    @ApiModelProperty(value="归档信息")
    private String gdxx;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String bz;

    private static final long serialVersionUID = 1L;

    public String getGdxxid() {
        return gdxxid;
    }

    public void setGdxxid(String gdxxid) {
        this.gdxxid = gdxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDaid() {
        return daid;
    }

    public void setDaid(String daid) {
        this.daid = daid;
    }

    public String getMlh() {
        return mlh;
    }

    public void setMlh(String mlh) {
        this.mlh = mlh;
    }

    public String getAjh() {
        return ajh;
    }

    public void setAjh(String ajh) {
        this.ajh = ajh;
    }

    public String getDamx() {
        return damx;
    }

    public void setDamx(String damx) {
        this.damx = damx;
    }

    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    public Long getAjjs() {
        return ajjs;
    }

    public void setAjjs(Long ajjs) {
        this.ajjs = ajjs;
    }

    public Long getAjys() {
        return ajys;
    }

    public void setAjys(Long ajys) {
        this.ajys = ajys;
    }

    public Date getGdsj() {
        return gdsj;
    }

    public void setGdsj(Date gdsj) {
        this.gdsj = gdsj;
    }

    public String getGdryxm() {
        return gdryxm;
    }

    public void setGdryxm(String gdryxm) {
        this.gdryxm = gdryxm;
    }

    public String getGdxx() {
        return gdxx;
    }

    public void setGdxx(String gdxx) {
        this.gdxx = gdxx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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
        ZrzyGdxxDO other = (ZrzyGdxxDO) that;
        return (this.getGdxxid() == null ? other.getGdxxid() == null : this.getGdxxid().equals(other.getGdxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getDaid() == null ? other.getDaid() == null : this.getDaid().equals(other.getDaid()))
            && (this.getMlh() == null ? other.getMlh() == null : this.getMlh().equals(other.getMlh()))
            && (this.getAjh() == null ? other.getAjh() == null : this.getAjh().equals(other.getAjh()))
            && (this.getDamx() == null ? other.getDamx() == null : this.getDamx().equals(other.getDamx()))
            && (this.getDah() == null ? other.getDah() == null : this.getDah().equals(other.getDah()))
            && (this.getAjjs() == null ? other.getAjjs() == null : this.getAjjs().equals(other.getAjjs()))
            && (this.getAjys() == null ? other.getAjys() == null : this.getAjys().equals(other.getAjys()))
            && (this.getGdsj() == null ? other.getGdsj() == null : this.getGdsj().equals(other.getGdsj()))
            && (this.getGdryxm() == null ? other.getGdryxm() == null : this.getGdryxm().equals(other.getGdryxm()))
            && (this.getGdxx() == null ? other.getGdxx() == null : this.getGdxx().equals(other.getGdxx()))
            && (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGdxxid() == null) ? 0 : getGdxxid().hashCode());
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getDaid() == null) ? 0 : getDaid().hashCode());
        result = prime * result + ((getMlh() == null) ? 0 : getMlh().hashCode());
        result = prime * result + ((getAjh() == null) ? 0 : getAjh().hashCode());
        result = prime * result + ((getDamx() == null) ? 0 : getDamx().hashCode());
        result = prime * result + ((getDah() == null) ? 0 : getDah().hashCode());
        result = prime * result + ((getAjjs() == null) ? 0 : getAjjs().hashCode());
        result = prime * result + ((getAjys() == null) ? 0 : getAjys().hashCode());
        result = prime * result + ((getGdsj() == null) ? 0 : getGdsj().hashCode());
        result = prime * result + ((getGdryxm() == null) ? 0 : getGdryxm().hashCode());
        result = prime * result + ((getGdxx() == null) ? 0 : getGdxx().hashCode());
        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gdxxid=").append(gdxxid);
        sb.append(", xmid=").append(xmid);
        sb.append(", daid=").append(daid);
        sb.append(", mlh=").append(mlh);
        sb.append(", ajh=").append(ajh);
        sb.append(", damx=").append(damx);
        sb.append(", dah=").append(dah);
        sb.append(", ajjs=").append(ajjs);
        sb.append(", ajys=").append(ajys);
        sb.append(", gdsj=").append(gdsj);
        sb.append(", gdryxm=").append(gdryxm);
        sb.append(", gdxx=").append(gdxx);
        sb.append(", bz=").append(bz);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}