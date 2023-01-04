package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/11
 * @description 不动产受理家庭成员
 */
@Table(name = "BDC_SL_JTCY")
@ApiModel(value = "BdcSlJtcyDO", description = "不动产受理家庭成员")
public class BdcSlJtcyDO implements Serializable {

    private static final long serialVersionUID = -1824231636255880477L;
    @Id
    @ApiModelProperty(value = "家庭成员ID")
    private String jtcyid;

    @ApiModelProperty(value = "申请人ID")
    private String sqrid;

    @ApiModelProperty(value = "家庭成员名称")
    private String jtcymc;

    @ApiModelProperty(value = "证件种类")
    private Integer zjzl;

    @ApiModelProperty(value = "证件号")
    private String zjh;

    @ApiModelProperty(value = "与申请人关系")
    private String ysqrgx;

    @ApiModelProperty(value = "共享标识")
    private Integer gxbs;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "户号")
    private String hh;

    @ApiModelProperty(value = "曾用名")
    private String cym;

    @ApiModelProperty(value = "婚姻状况")
    private String hyzk;

    @ApiModelProperty(value = "国籍")
    private String gj;

    public String getJtcyid() {
        return jtcyid;
    }

    public void setJtcyid(String jtcyid) {
        this.jtcyid = jtcyid;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getJtcymc() {
        return jtcymc;
    }

    public void setJtcymc(String jtcymc) {
        this.jtcymc = jtcymc;
    }

    public Integer getZjzl() {
        return zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getYsqrgx() {
        return ysqrgx;
    }

    public void setYsqrgx(String ysqrgx) {
        this.ysqrgx = ysqrgx;
    }

    public Integer getGxbs() {
        return gxbs;
    }

    public void setGxbs(Integer gxbs) {
        this.gxbs = gxbs;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getCym() {
        return cym;
    }

    public void setCym(String cym) {
        this.cym = cym;
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    @Override
    public String toString() {
        return "BdcSlJtcyDO{" +
                "jtcyid='" + jtcyid + '\'' +
                ", sqrid='" + sqrid + '\'' +
                ", jtcymc='" + jtcymc + '\'' +
                ", zjzl=" + zjzl +
                ", zjh='" + zjh + '\'' +
                ", ysqrgx='" + ysqrgx + '\'' +
                ", gxbs=" + gxbs +
                ", xb='" + xb + '\'' +
                ", hh='" + hh + '\'' +
                ", cym='" + cym + '\'' +
                ", hyzk='" + hyzk + '\'' +
                ", gj='" + gj + '\'' +
                '}';
    }
}
