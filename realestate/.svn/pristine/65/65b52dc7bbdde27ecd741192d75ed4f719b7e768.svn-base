package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2022/8/11
 * @description 不动产持件信息DO
 */
@Table(name = "BDC_CJXX")
@ApiModel(value = "BdcCjxxDO", description = "不动产持件信息DO")
public class BdcCjxxDO implements Serializable {

    @Id
    @ApiModelProperty(value = "主键ID")
    private String cjxxid;
    @ApiModelProperty(value = "流水号")
    private String lsh;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "持件人")
    private String cjr;
    @ApiModelProperty(value = "持件时间")
    private Date cjsj;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "持件人ID")
    private String cjrid;

    public String getCjxxid() {
        return cjxxid;
    }

    public void setCjxxid(String cjxxid) {
        this.cjxxid = cjxxid;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcCjxxDO{");
        sb.append("cjxxid='").append(cjxxid).append('\'');
        sb.append(", lsh='").append(lsh).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", qlr='").append(qlr).append('\'');
        sb.append(", cjr='").append(cjr).append('\'');
        sb.append(", cjsj=").append(cjsj);
        sb.append(", bz='").append(bz).append('\'');
        sb.append(", cjrid='").append(cjrid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
