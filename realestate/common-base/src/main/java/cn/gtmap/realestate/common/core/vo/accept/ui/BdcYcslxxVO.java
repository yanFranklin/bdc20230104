package cn.gtmap.realestate.common.core.vo.accept.ui;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/1/15
 * @description 一窗受理信息VO
 */
@ApiModel(value = "BdcYcslxxVO", description = "一窗受理信息VO")
public class BdcYcslxxVO {

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "房屋信息ID")
    private String fwxxid;

    @ApiModelProperty(value = "项目ID")
    private String jyxxid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "房产证发证时间")
    private Date fczfzsj;

    @ApiModelProperty(value = "上次交易登记时间")
    private Date scjydjsj;

    @ApiModelProperty(value = "转入方")
    private String qlr;

    @ApiModelProperty(value = "转入方房屋套次")
    private String qlrfwtc;

    @ApiModelProperty(value = "转入方申报套次")
    private String qlrsbfwtc;

    @ApiModelProperty(value = "转出方")
    private String ywr;

    @ApiModelProperty(value = "转出方房屋套次")
    private String ywrfwtc;

    @ApiModelProperty(value = "转出方申报套次")
    private String ywrsbfwtc;

    @ApiModelProperty(value = "含增值税金额")
    private Double hzzsje;

    @ApiModelProperty(value = "不含增值税金额")
    private Double bhzzsje;

    @ApiModelProperty(value = "增值税金额")
    private Double zzsje;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFwxxid() {
        return fwxxid;
    }

    public void setFwxxid(String fwxxid) {
        this.fwxxid = fwxxid;
    }

    public String getJyxxid() {
        return jyxxid;
    }

    public void setJyxxid(String jyxxid) {
        this.jyxxid = jyxxid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Date getFczfzsj() {
        return fczfzsj;
    }

    public void setFczfzsj(Date fczfzsj) {
        this.fczfzsj = fczfzsj;
    }

    public Date getScjydjsj() {
        return scjydjsj;
    }

    public void setScjydjsj(Date scjydjsj) {
        this.scjydjsj = scjydjsj;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrfwtc() {
        return qlrfwtc;
    }

    public void setQlrfwtc(String qlrfwtc) {
        this.qlrfwtc = qlrfwtc;
    }

    public String getQlrsbfwtc() {
        return qlrsbfwtc;
    }

    public void setQlrsbfwtc(String qlrsbfwtc) {
        this.qlrsbfwtc = qlrsbfwtc;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYwrfwtc() {
        return ywrfwtc;
    }

    public void setYwrfwtc(String ywrfwtc) {
        this.ywrfwtc = ywrfwtc;
    }

    public String getYwrsbfwtc() {
        return ywrsbfwtc;
    }

    public void setYwrsbfwtc(String ywrsbfwtc) {
        this.ywrsbfwtc = ywrsbfwtc;
    }

    public Double getHzzsje() {
        return hzzsje;
    }

    public void setHzzsje(Double hzzsje) {
        this.hzzsje = hzzsje;
    }

    public Double getBhzzsje() {
        return bhzzsje;
    }

    public void setBhzzsje(Double bhzzsje) {
        this.bhzzsje = bhzzsje;
    }

    public Double getZzsje() {
        return zzsje;
    }

    public void setZzsje(Double zzsje) {
        this.zzsje = zzsje;
    }
}
