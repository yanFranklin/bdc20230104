package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * ZRZY_PWQGLXX
 * @author 
 */
@Table(name="ZRZY_PWQGLXX")
@ApiModel(value="generate.ZrzyPwqglxxDo")
@Data
public class ZrzyPwqglxxDO implements Serializable {
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
     * 排污许可证号
     */
    @ApiModelProperty(value="排污许可证号")
    private String pwxkzh;

    /**
     * 单位名称
     */
    @ApiModelProperty(value="单位名称")
    private String dwmc;

    /**
     * 污染物种类
     */
    @ApiModelProperty(value="污染物种类")
    private String wrwzl;

    /**
     * 排放浓度限值
     */
    @ApiModelProperty(value="排放浓度限值")
    private String pfndxz;

    /**
     * 排污权有效期限
     */
    @ApiModelProperty(value="排污权有效期限")
    private String pwqyxqx;

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

    public String getPwxkzh() {
        return pwxkzh;
    }

    public void setPwxkzh(String pwxkzh) {
        this.pwxkzh = pwxkzh;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getWrwzl() {
        return wrwzl;
    }

    public void setWrwzl(String wrwzl) {
        this.wrwzl = wrwzl;
    }

    public String getPfndxz() {
        return pfndxz;
    }

    public void setPfndxz(String pfndxz) {
        this.pfndxz = pfndxz;
    }

    public String getPwqyxqx() {
        return pwqyxqx;
    }

    public void setPwqyxqx(String pwqyxqx) {
        this.pwqyxqx = pwqyxqx;
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
        ZrzyPwqglxxDO other = (ZrzyPwqglxxDO) that;
        return (this.getGlxxid() == null ? other.getGlxxid() == null : this.getGlxxid().equals(other.getGlxxid()))
            && (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
            && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
            && (this.getPwxkzh() == null ? other.getPwxkzh() == null : this.getPwxkzh().equals(other.getPwxkzh()))
            && (this.getDwmc() == null ? other.getDwmc() == null : this.getDwmc().equals(other.getDwmc()))
            && (this.getWrwzl() == null ? other.getWrwzl() == null : this.getWrwzl().equals(other.getWrwzl()))
            && (this.getPfndxz() == null ? other.getPfndxz() == null : this.getPfndxz().equals(other.getPfndxz()))
            && (this.getPwqyxqx() == null ? other.getPwqyxqx() == null : this.getPwqyxqx().equals(other.getPwqyxqx()))
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
        result = prime * result + ((getPwxkzh() == null) ? 0 : getPwxkzh().hashCode());
        result = prime * result + ((getDwmc() == null) ? 0 : getDwmc().hashCode());
        result = prime * result + ((getWrwzl() == null) ? 0 : getWrwzl().hashCode());
        result = prime * result + ((getPfndxz() == null) ? 0 : getPfndxz().hashCode());
        result = prime * result + ((getPwqyxqx() == null) ? 0 : getPwqyxqx().hashCode());
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
        sb.append(", pwxkzh=").append(pwxkzh);
        sb.append(", dwmc=").append(dwmc);
        sb.append(", wrwzl=").append(wrwzl);
        sb.append(", pfndxz=").append(pfndxz);
        sb.append(", pwqyxqx=").append(pwqyxqx);
        sb.append(", fzjg=").append(fzjg);
        sb.append(", qxdm=").append(qxdm);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}