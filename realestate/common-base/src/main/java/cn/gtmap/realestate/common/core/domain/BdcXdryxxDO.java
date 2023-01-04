package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: 3.0
 * @description: 不动产限定人员信息表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-03 09:37
 **/
@Table(name = "BDC_XDRYXX")
public class BdcXdryxxDO {
    @Id
    @ApiModelProperty(value = "ID主键")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String xm;
    @ApiModelProperty(value = "证件号")
    private String zjh;
    @ApiModelProperty(value = "通讯地址")
    private String txdz;
    @ApiModelProperty(value = "性别")
    private Integer xb;
    @ApiModelProperty(value = "电话")
    private String dh;
    @ApiModelProperty(value = "是否锁定状态")
    private Integer sfsdzt;
    @ApiModelProperty(value = "创建人")
    private String cjr;
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @ApiModelProperty(value = "锁定原因")
    private String sdyy;

    @ApiModelProperty(value = "解锁人")
    private String jsr;

    @ApiModelProperty(value = "解锁时间")
    private Date jssj;

    @ApiModelProperty(value = "解锁原因")
    private String jsyy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public Integer getXb() {
        return xb;
    }

    public void setXb(Integer xb) {
        this.xb = xb;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public Integer getSfsdzt() {
        return sfsdzt;
    }

    public void setSfsdzt(Integer sfsdzt) {
        this.sfsdzt = sfsdzt;
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

    public String getSdyy() {
        return sdyy;
    }

    public void setSdyy(String sdyy) {
        this.sdyy = sdyy;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getJsyy() {
        return jsyy;
    }

    public void setJsyy(String jsyy) {
        this.jsyy = jsyy;
    }

    @Override
    public String toString() {
        return "BdcXdryxxDO{" +
                "id='" + id + '\'' +
                ", xm='" + xm + '\'' +
                ", zjh='" + zjh + '\'' +
                ", txdz='" + txdz + '\'' +
                ", xb=" + xb +
                ", dh='" + dh + '\'' +
                ", sfsdzt=" + sfsdzt +
                ", cjr='" + cjr + '\'' +
                ", cjsj=" + cjsj +
                ", sdyy='" + sdyy + '\'' +
                ", jsr='" + jsr + '\'' +
                ", jssj=" + jssj +
                ", jsyy='" + jsyy + '\'' +
                '}';
    }
}
