package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 共享外网申请项目
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 2019/3/15 10:30
 */
@Table(name = "GX_WW_SQXX_QLR")
@ApiModel(value = "GxWwSqxxQlrDO", description = "共享外网申请项目")
public class GxWwSqxxQlrDO implements Serializable {
    @Id
    @ApiModelProperty(value = "权利人 id")
    private String qlrid;

    @ApiModelProperty(value = "申请信息表主键")
    private String sqxxid;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    @ApiModelProperty(value = "权利人身份证件种类")
    private String qlrsfzjzl;
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;
    @ApiModelProperty(value = "权利人通讯地址")
    private String qlrtxdz;
    @ApiModelProperty(value = "权利人类型")
    private String qlrlx;
    @ApiModelProperty(value = "权利比例")
    private String qlbl;
    @ApiModelProperty(value = "权利人联系电话")
    private String qlrlxdh;
    @ApiModelProperty(value = "代理人电话")
    private String dlrdh;
    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;
    @ApiModelProperty(value = "法定代表人或负责人电话")
    private String fddbrdh;
    @ApiModelProperty(value = "法定代表人或负责人")
    private String fddbrmc;
    @ApiModelProperty(value = "权利人邮编号")
    private String qlryb;
    @ApiModelProperty(value = "代理机构名称")
    private String dljgmc;
    @ApiModelProperty(value = "是否持证人")
    private String sfczr;
    @ApiModelProperty(value = "其余共有人")
    private String qygyr;
    @ApiModelProperty(value = "代理人身份证件种类")
    private String dlrsfzjzl;
    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;

    public String getQlrsfzjzl() {
        return qlrsfzjzl;
    }

    public void setQlrsfzjzl(String qlrsfzjzl) {
        this.qlrsfzjzl = qlrsfzjzl;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrtxdz() {
        return qlrtxdz;
    }

    public void setQlrtxdz(String qlrtxdz) {
        this.qlrtxdz = qlrtxdz;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public String getQlrlxdh() {
        return qlrlxdh;
    }

    public void setQlrlxdh(String qlrlxdh) {
        this.qlrlxdh = qlrlxdh;
    }

    public String getDlrdh() {
        return dlrdh;
    }

    public void setDlrdh(String dlrdh) {
        this.dlrdh = dlrdh;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getFddbrdh() {
        return fddbrdh;
    }

    public void setFddbrdh(String fddbrdh) {
        this.fddbrdh = fddbrdh;
    }

    public String getFddbrmc() {
        return fddbrmc;
    }

    public void setFddbrmc(String fddbrmc) {
        this.fddbrmc = fddbrmc;
    }

    public String getQlryb() {
        return qlryb;
    }

    public void setQlryb(String qlryb) {
        this.qlryb = qlryb;
    }

    public String getDljgmc() {
        return dljgmc;
    }

    public void setDljgmc(String dljgmc) {
        this.dljgmc = dljgmc;
    }

    public String getSfczr() {
        return sfczr;
    }

    public void setSfczr(String sfczr) {
        this.sfczr = sfczr;
    }

    public String getQygyr() {
        return qygyr;
    }

    public void setQygyr(String qygyr) {
        this.qygyr = qygyr;
    }

    public String getDlrsfzjzl() {
        return dlrsfzjzl;
    }

    public void setDlrsfzjzl(String dlrsfzjzl) {
        this.dlrsfzjzl = dlrsfzjzl;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    @Override
    public String toString() {
        return "GxWwSqxxQlrDO{" +
                "qlrid='" + qlrid + '\'' +
                ", sqxxid='" + sqxxid + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrsfzjzl='" + qlrsfzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrtxdz='" + qlrtxdz + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", qlbl='" + qlbl + '\'' +
                ", qlrlxdh='" + qlrlxdh + '\'' +
                ", dlrdh='" + dlrdh + '\'' +
                ", dlrmc='" + dlrmc + '\'' +
                ", fddbrdh='" + fddbrdh + '\'' +
                ", fddbrmc='" + fddbrmc + '\'' +
                ", qlryb='" + qlryb + '\'' +
                ", dljgmc='" + dljgmc + '\'' +
                ", sfczr='" + sfczr + '\'' +
                ", qygyr='" + qygyr + '\'' +
                ", dlrsfzjzl='" + dlrsfzjzl + '\'' +
                ", dlrzjh='" + dlrzjh + '\'' +
                '}';
    }

}
