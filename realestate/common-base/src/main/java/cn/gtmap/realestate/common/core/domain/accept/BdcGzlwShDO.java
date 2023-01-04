package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/26
 * @description
 */
@Table(name = "BDC_GZLW_SH")
@ApiModel(value = "BdcGzlwShDO", description = "规则例外审核")
public class BdcGzlwShDO {
    @Id
    @ApiModelProperty(value = "规则例外id")
    private String gzlwid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "规则id")
    private String gzid;
    @ApiModelProperty(value = "规则名称")
    private String gzmc;
    @ApiModelProperty(value = "审核状态 0:新建 1:通过 2:不通过")
    private Integer shzt;
    @ApiModelProperty(value = "创建人")
    private String cjr;
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;
    @ApiModelProperty(value = "审核人")
    private String shr;
    @ApiModelProperty(value = "审核时间")
    private Date shsj;
    @ApiModelProperty(value = "审核签名")
    private String shqm;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;
    @ApiModelProperty(value = "例外原因")
    private String lwyy;
    @ApiModelProperty(value = "创建人名称")
    private String cjrmc;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "审核意见")
    private String shyj;
    @ApiModelProperty(value = "审核人名称")
    private String shrmc;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    public String getGzlwid() {
        return gzlwid;
    }

    public void setGzlwid(String gzlwid) {
        this.gzlwid = gzlwid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getGzmc() {
        return gzmc;
    }

    public void setGzmc(String gzmc) {
        this.gzmc = gzmc;
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

    public String getShr() {
        return shr;
    }

    public void setShr(String shr) {
        this.shr = shr;
    }

    public Integer getShzt() {
        return shzt;
    }

    public void setShzt(Integer shzt) {
        this.shzt = shzt;
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getShqm() {
        return shqm;
    }

    public void setShqm(String shqm) {
        this.shqm = shqm;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getLwyy() {
        return lwyy;
    }

    public void setLwyy(String lwyy) {
        this.lwyy = lwyy;
    }

    public String getCjrmc() {
        return cjrmc;
    }

    public void setCjrmc(String cjrmc) {
        this.cjrmc = cjrmc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getShrmc() {
        return shrmc;
    }

    public void setShrmc(String shrmc) {
        this.shrmc = shrmc;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @Override
    public String toString() {
        return "BdcGzlwShDO{" +
                "gzlwid='" + gzlwid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", gzid='" + gzid + '\'' +
                ", gzmc='" + gzmc + '\'' +
                ", shzt=" + shzt +
                ", cjr='" + cjr + '\'' +
                ", cjsj=" + cjsj +
                ", shr='" + shr + '\'' +
                ", shsj=" + shsj +
                ", shqm='" + shqm + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", lwyy='" + lwyy + '\'' +
                ", cjrmc='" + cjrmc + '\'' +
                ", zl='" + zl + '\'' +
                ", shyj='" + shyj + '\'' +
                ", shrmc='" + shrmc + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                '}';
    }
}
