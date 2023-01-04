package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

/**
 * 登记省直房改房实体
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/08/20
 */
public class BdcDjSzfgfDTO {

    @ApiModelProperty(value = "受理编号")
    private String ywh;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "身份证号码")
    private String sfzhm;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "配偶姓名")
    private String poxm;

    @ApiModelProperty(value = "配偶身份证号码")
    private String posfzhm;

    @ApiModelProperty(value = "配偶电话")
    private String pomobile;


    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "权利人身份证号码")
    private String qlrsfzhm;

    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "房屋种类")
    private String fwzl;

    @ApiModelProperty(value = "登记时间")
    private String djsj ;

    @ApiModelProperty(value = "建筑面积")
    private String jzmj;

    @ApiModelProperty(value = "套内面积")
    private String tnmj;

    @ApiModelProperty(value = "共有分摊面积")
    private String gyftmj;


    @ApiModelProperty(value = "房屋性质")
    private String fwxz;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    @ApiModelProperty(value = "土地使用年限")
    private String tdsynx;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "附记")
    private String fuji;

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPoxm() {
        return poxm;
    }

    public void setPoxm(String poxm) {
        this.poxm = poxm;
    }

    public String getPosfzhm() {
        return posfzhm;
    }

    public void setPosfzhm(String posfzhm) {
        this.posfzhm = posfzhm;
    }

    public String getPomobile() {
        return pomobile;
    }

    public void setPomobile(String pomobile) {
        this.pomobile = pomobile;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrsfzhm() {
        return qlrsfzhm;
    }

    public void setQlrsfzhm(String qlrsfzhm) {
        this.qlrsfzhm = qlrsfzhm;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getTnmj() {
        return tnmj;
    }

    public void setTnmj(String tnmj) {
        this.tnmj = tnmj;
    }

    public String getGyftmj() {
        return gyftmj;
    }

    public void setGyftmj(String gyftmj) {
        this.gyftmj = gyftmj;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getTdsynx() {
        return tdsynx;
    }

    public void setTdsynx(String tdsynx) {
        this.tdsynx = tdsynx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getFuji() {
        return fuji;
    }

    public void setFuji(String fuji) {
        this.fuji = fuji;
    }

    @Override
    public String toString() {
        return "BdcDjSzfgfDTO{" +
                "ywh='" + ywh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", xm='" + xm + '\'' +
                ", sfzhm='" + sfzhm + '\'' +
                ", mobile='" + mobile + '\'' +
                ", poxm='" + poxm + '\'' +
                ", posfzhm='" + posfzhm + '\'' +
                ", pomobile='" + pomobile + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrsfzhm='" + qlrsfzhm + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", djsj='" + djsj + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", tnmj='" + tnmj + '\'' +
                ", gyftmj='" + gyftmj + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", ghyt='" + ghyt + '\'' +
                ", tdsynx='" + tdsynx + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", fuji='" + fuji + '\'' +
                '}';
    }
}
