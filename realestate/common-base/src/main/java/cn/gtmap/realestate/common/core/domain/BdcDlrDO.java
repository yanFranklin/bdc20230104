package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 代理人实体信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 10:17
 **/
@Table(name = "BDC_DLR")
@ApiModel(value = "BdcDlrDO", description = "代理人表")
public class BdcDlrDO {
    @Id
    @ApiModelProperty("代理人id")
    private String dlrid;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("权利人id")
    private String qlrid;

    @ApiModelProperty("代理人名称")
    private String dlrmc;

    @ApiModelProperty("证件种类")
    private Integer zjzl;

    @ApiModelProperty("电话")
    private String dh;

    @ApiModelProperty("证件号")
    private String zjh;

    @ApiModelProperty("代理机构")
    private String dljg;

    @ApiModelProperty("委托事项")
    private String wtsx;

    @ApiModelProperty("代理人类别1-代理人2-嘱托人")
    private String dlrlb;

    public String getDlrid() {
        return dlrid;
    }

    public void setDlrid(String dlrid) {
        this.dlrid = dlrid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getDljg() {
        return dljg;
    }

    public void setDljg(String dljg) {
        this.dljg = dljg;
    }

    public String getWtsx() {
        return wtsx;
    }

    public void setWtsx(String wtsx) {
        this.wtsx = wtsx;
    }

    public String getDlrlb() {
        return dlrlb;
    }

    public void setDlrlb(String dlrlb) {
        this.dlrlb = dlrlb;
    }
}
