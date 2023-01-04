package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-3-6
 * @description
 */
@Table(name = "zdzh_djxx")
public class ZdzhDjxxDO {
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键")
    private String zdzhDjxxIndex;
    /**
     * 冻结原因
     */
    @ApiModelProperty(value = "冻结原因")
    private String djyy;
    /**
     * 冻结日期
     */
    @ApiModelProperty(value = "冻结日期")
    private Date djrq;
    /**
     * 起始日期
     */
    @ApiModelProperty(value = "起始日期")
    private Date qsrq;
    /**
     * 冻结者
     */
    @ApiModelProperty(value = "冻结者")
    private String djz;
    /**
     * 顺序号
     */
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;
    /**
     * 图层名称
     */
    @ApiModelProperty(value = "图层名称")
    private String tcmc;
    /**
     * 宗地宗海代码
     */
    @ApiModelProperty(value = "宗地宗海代码")
    private String zdzhdm ;
    /**
     * 终止日期
     */
    @ApiModelProperty(value = "终止日期")
    private Date zzrq;

    @ApiModelProperty(value = "是否解冻 0为解冻 1为未解冻")
    private String isjd;

    public String getZdzhDjxxIndex() {
        return zdzhDjxxIndex;
    }

    public void setZdzhDjxxIndex(String zdzhDjxxIndex) {
        this.zdzhDjxxIndex = zdzhDjxxIndex;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }

    public Date getQsrq() {
        return qsrq;
    }

    public void setQsrq(Date qsrq) {
        this.qsrq = qsrq;
    }

    public String getDjz() {
        return djz;
    }

    public void setDjz(String djz) {
        this.djz = djz;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getTcmc() {
        return tcmc;
    }

    public void setTcmc(String tcmc) {
        this.tcmc = tcmc;
    }

    public String getZdzhdm() {
        return zdzhdm;
    }

    public void setZdzhdm(String zdzhdm) {
        this.zdzhdm = zdzhdm;
    }

    public Date getZzrq() {
        return zzrq;
    }

    public void setZzrq(Date zzrq) {
        this.zzrq = zzrq;
    }

    public String getIsjd() {
        return isjd;
    }

    public void setIsjd(String isjd) {
        this.isjd = isjd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZdzhDjxxDO{");
        sb.append("zdzhDjxxIndex='").append(zdzhDjxxIndex).append('\'');
        sb.append(", djyy='").append(djyy).append('\'');
        sb.append(", djrq=").append(djrq);
        sb.append(", qsrq=").append(qsrq);
        sb.append(", djz='").append(djz).append('\'');
        sb.append(", sxh=").append(sxh);
        sb.append(", tcmc='").append(tcmc).append('\'');
        sb.append(", zdzhdm='").append(zdzhdm).append('\'');
        sb.append(", zzrq=").append(zzrq);
        sb.append(", isjd='").append(isjd).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
