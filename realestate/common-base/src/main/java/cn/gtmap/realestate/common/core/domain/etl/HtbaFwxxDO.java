package cn.gtmap.realestate.common.core.domain.etl;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 合同备案房屋信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 11:06
 **/

@Table(name = "HTBA_FWXX")
public class HtbaFwxxDO implements Serializable {

    @Id
    @ApiModelProperty("房屋信息id")
    private String fwxxid;
    @ApiModelProperty("备案id")
    private String baid;
    @ApiModelProperty("房屋id")
    private String fwid;
    @ApiModelProperty("坐落")
    private String zl;
    @ApiModelProperty("房屋编码")
    private String fwbm;
    @ApiModelProperty("不动产单元号")
    private String bdcdyh;
    @ApiModelProperty("房屋地址")
    private String fwzl;
    @ApiModelProperty("房屋性质")
    private String fwxz;
    @ApiModelProperty("建筑面积")
    private String jzmj;
    @ApiModelProperty("房屋单价")
    private String fwdj;
    @ApiModelProperty("房屋总层数")
    private String fwzcs;
    @ApiModelProperty("房屋所在层")
    private String fwszc;
    @ApiModelProperty("层高")
    private String cg;
    @ApiModelProperty("房屋结构")
    private String fwjg;

    public String getFwxxid() {
        return fwxxid;
    }

    public void setFwxxid(String fwxxid) {
        this.fwxxid = fwxxid;
    }

    public String getBaid() {
        return baid;
    }

    public void setBaid(String baid) {
        this.baid = baid;
    }

    public String getFwid() {
        return fwid;
    }

    public void setFwid(String fwid) {
        this.fwid = fwid;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getFwdj() {
        return fwdj;
    }

    public void setFwdj(String fwdj) {
        this.fwdj = fwdj;
    }

    public String getFwzcs() {
        return fwzcs;
    }

    public void setFwzcs(String fwzcs) {
        this.fwzcs = fwzcs;
    }

    public String getFwszc() {
        return fwszc;
    }

    public void setFwszc(String fwszc) {
        this.fwszc = fwszc;
    }

    public String getCg() {
        return cg;
    }

    public void setCg(String cg) {
        this.cg = cg;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    @Override
    public String toString() {
        return "HtbaFwxxDO{" +
                "fwxxid='" + fwxxid + '\'' +
                ", baid='" + baid + '\'' +
                ", fwid='" + fwid + '\'' +
                ", zl='" + zl + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", fwdj='" + fwdj + '\'' +
                ", fwzcs='" + fwzcs + '\'' +
                ", fwszc='" + fwszc + '\'' +
                ", cg='" + cg + '\'' +
                ", fwjg='" + fwjg + '\'' +
                '}';
    }
}
