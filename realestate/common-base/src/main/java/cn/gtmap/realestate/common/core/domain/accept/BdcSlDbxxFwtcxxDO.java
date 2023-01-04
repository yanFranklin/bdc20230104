package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021-01-29
 * @description 房屋套次比对信息
 */
@Table(name = "BDC_SL_DBXX_FWTCXX")
public class BdcSlDbxxFwtcxxDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "申请人id")
    private String sqrid;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件种类")
    private Integer qlrzjzl;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "用途")
    private String yt;

    @ApiModelProperty(value = "是否通过比对")
    private String sfbdtg;

    @ApiModelProperty(value = "比对不通过原因")
    private String yy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public Integer getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(Integer qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getSfbdtg() {
        return sfbdtg;
    }

    public void setSfbdtg(String sfbdtg) {
        this.sfbdtg = sfbdtg;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }
}
