package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/2
 * @description 受理房屋套次信息
 */
@Table(name = "BDC_SL_FWTCXX")
@ApiModel(value = "BdcSlFwtcxxDO", description = "住房信息查询结果")
public class BdcSlFwtcxxDO {

    @ApiModelProperty(value = "主键")
    private String fwtcxxid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "不动产权证号（房产证号）")
    private String bdcqzh;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    @ApiModelProperty(value = "申请人类别:1-转入方 2-转出方")
    private String sqrlb;

    @ApiModelProperty(value = "申请人ID")
    private String sqrid;

    public String getFwtcxxid() {
        return fwtcxxid;
    }

    public void setFwtcxxid(String fwtcxxid) {
        this.fwtcxxid = fwtcxxid;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSqrlb() {
        return sqrlb;
    }

    public void setSqrlb(String sqrlb) {
        this.sqrlb = sqrlb;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    @Override
    public String toString() {
        return "BdcSlFwtcxxDO{" +
                "fwtcxxid='" + fwtcxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", zl='" + zl + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", sqrlb='" + sqrlb + '\'' +
                '}';
    }
}
