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
 * @description 自然资源项目
 */
@Table(name = "ZRZY_XM")
@ApiModel(value = "ZrzyXmDO", description = "自然资源项目")
@Data
public class ZrzyXmDO implements Serializable {
    /**
     * 项目ID
     */
    @Id
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    /**
     * 受理编号
     */
    @NotEmpty
    @ApiModelProperty(value = "受理编号")
    private String slbh;

    /**
     * 工作流实例ID
     */
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

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
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 登记类型
     */
    @ApiModelProperty(value = "登记类型")
    private Short djlx;

    /**
     * 登记原因
     */
    @ApiModelProperty(value = "登记原因")
    private String djyy;

    /**
     * 案件状态
     */
    @ApiModelProperty(value = "案件状态")
    private Short ajzt;

    /**
     * 权属状态
     */
    @ApiModelProperty(value = "权属状态")
    private Short qszt;

    /**
     * 区县代码
     */
    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    /**
     * 受理时间
     */
    @ApiModelProperty(value = "受理时间")
    private Date slsj;

    /**
     * 受理人ID
     */
    @ApiModelProperty(value = "受理人ID")
    private String slrid;

    /**
     * 受理人
     */
    @ApiModelProperty(value = "受理人")
    private String slr;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Date jssj;

    /**
     * 登记机构
     */
    @ApiModelProperty(value = "登记机构")
    private String djjg;

    /**
     * 登记时间
     */
    @ApiModelProperty(value = "登记时间")
    private Date djsj;

    /**
     * 登簿人
     */
    @ApiModelProperty(value = "登簿人")
    private String dbr;

    /**
     * 自然资源产权证号
     */
    @ApiModelProperty(value = "自然资源产权证号")
    private String zrzycqzh;

    /**
     * 原产权证号
     */
    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;

    /**
     * 工作流定义ID
     */
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    /**
     * 工作流定义名称
     */
    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Short getDjlx() {
        return djlx;
    }

    public void setDjlx(Short djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Short getAjzt() {
        return ajzt;
    }

    public void setAjzt(Short ajzt) {
        this.ajzt = ajzt;
    }

    public Short getQszt() {
        return qszt;
    }

    public void setQszt(Short qszt) {
        this.qszt = qszt;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getSlrid() {
        return slrid;
    }

    public void setSlrid(String slrid) {
        this.slrid = slrid;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getZrzycqzh() {
        return zrzycqzh;
    }

    public void setZrzycqzh(String zrzycqzh) {
        this.zrzycqzh = zrzycqzh;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
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
        ZrzyXmDO other = (ZrzyXmDO) that;
        return (this.getXmid() == null ? other.getXmid() == null : this.getXmid().equals(other.getXmid()))
                && (this.getSlbh() == null ? other.getSlbh() == null : this.getSlbh().equals(other.getSlbh()))
                && (this.getGzlslid() == null ? other.getGzlslid() == null : this.getGzlslid().equals(other.getGzlslid()))
                && (this.getZrzydjdyh() == null ? other.getZrzydjdyh() == null : this.getZrzydjdyh().equals(other.getZrzydjdyh()))
                && (this.getZl() == null ? other.getZl() == null : this.getZl().equals(other.getZl()))
                && (this.getDjlx() == null ? other.getDjlx() == null : this.getDjlx().equals(other.getDjlx()))
                && (this.getDjyy() == null ? other.getDjyy() == null : this.getDjyy().equals(other.getDjyy()))
                && (this.getAjzt() == null ? other.getAjzt() == null : this.getAjzt().equals(other.getAjzt()))
                && (this.getQszt() == null ? other.getQszt() == null : this.getQszt().equals(other.getQszt()))
                && (this.getQxdm() == null ? other.getQxdm() == null : this.getQxdm().equals(other.getQxdm()))
                && (this.getSlsj() == null ? other.getSlsj() == null : this.getSlsj().equals(other.getSlsj()))
                && (this.getSlrid() == null ? other.getSlrid() == null : this.getSlrid().equals(other.getSlrid()))
                && (this.getJssj() == null ? other.getJssj() == null : this.getJssj().equals(other.getJssj()))
                && (this.getDjjg() == null ? other.getDjjg() == null : this.getDjjg().equals(other.getDjjg()))
                && (this.getDjsj() == null ? other.getDjsj() == null : this.getDjsj().equals(other.getDjsj()))
                && (this.getDbr() == null ? other.getDbr() == null : this.getDbr().equals(other.getDbr()))
                && (this.getZrzycqzh() == null ? other.getZrzycqzh() == null : this.getZrzycqzh().equals(other.getZrzycqzh()))
                && (this.getYcqzh() == null ? other.getYcqzh() == null : this.getYcqzh().equals(other.getYcqzh()))
                && (this.getGzldyid() == null ? other.getGzldyid() == null : this.getGzldyid().equals(other.getGzldyid()))
                && (this.getGzldymc() == null ? other.getGzldymc() == null : this.getGzldymc().equals(other.getGzldymc()))
                && (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getXmid() == null) ? 0 : getXmid().hashCode());
        result = prime * result + ((getSlbh() == null) ? 0 : getSlbh().hashCode());
        result = prime * result + ((getGzlslid() == null) ? 0 : getGzlslid().hashCode());
        result = prime * result + ((getZrzydjdyh() == null) ? 0 : getZrzydjdyh().hashCode());
        result = prime * result + ((getZl() == null) ? 0 : getZl().hashCode());
        result = prime * result + ((getDjlx() == null) ? 0 : getDjlx().hashCode());
        result = prime * result + ((getDjyy() == null) ? 0 : getDjyy().hashCode());
        result = prime * result + ((getAjzt() == null) ? 0 : getAjzt().hashCode());
        result = prime * result + ((getQszt() == null) ? 0 : getQszt().hashCode());
        result = prime * result + ((getQxdm() == null) ? 0 : getQxdm().hashCode());
        result = prime * result + ((getSlsj() == null) ? 0 : getSlsj().hashCode());
        result = prime * result + ((getSlrid() == null) ? 0 : getSlrid().hashCode());
        result = prime * result + ((getJssj() == null) ? 0 : getJssj().hashCode());
        result = prime * result + ((getDjjg() == null) ? 0 : getDjjg().hashCode());
        result = prime * result + ((getDjsj() == null) ? 0 : getDjsj().hashCode());
        result = prime * result + ((getDbr() == null) ? 0 : getDbr().hashCode());
        result = prime * result + ((getZrzycqzh() == null) ? 0 : getZrzycqzh().hashCode());
        result = prime * result + ((getYcqzh() == null) ? 0 : getYcqzh().hashCode());
        result = prime * result + ((getGzldyid() == null) ? 0 : getGzldyid().hashCode());
        result = prime * result + ((getGzldymc() == null) ? 0 : getGzldymc().hashCode());
        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", xmid=").append(xmid);
        sb.append(", slbh=").append(slbh);
        sb.append(", gzlslid=").append(gzlslid);
        sb.append(", zrzydjdyh=").append(zrzydjdyh);
        sb.append(", zl=").append(zl);
        sb.append(", djlx=").append(djlx);
        sb.append(", djyy=").append(djyy);
        sb.append(", ajzt=").append(ajzt);
        sb.append(", qszt=").append(qszt);
        sb.append(", qxdm=").append(qxdm);
        sb.append(", slsj=").append(slsj);
        sb.append(", slrid=").append(slrid);
        sb.append(", slr=").append(slr);
        sb.append(", jssj=").append(jssj);
        sb.append(", djjg=").append(djjg);
        sb.append(", djsj=").append(djsj);
        sb.append(", dbr=").append(dbr);
        sb.append(", zrzycqzh=").append(zrzycqzh);
        sb.append(", ycqzh=").append(ycqzh);
        sb.append(", gzldyid=").append(gzldyid);
        sb.append(", gzldymc=").append(gzldymc);
        sb.append(", bz=").append(bz);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}