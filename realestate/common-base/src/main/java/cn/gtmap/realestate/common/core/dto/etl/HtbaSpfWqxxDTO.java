package cn.gtmap.realestate.common.core.dto.etl;

import io.swagger.annotations.ApiModelProperty;

public class HtbaSpfWqxxDTO {

    @ApiModelProperty("合同备案号")
    private String htbabm;
    @ApiModelProperty("合同备案状态")
    private String htbazt;
    @ApiModelProperty("合同签订时间")
    private String basj;
    @ApiModelProperty("卖方")
    private String kfsmc;
    @ApiModelProperty("买受人")
    private String msr;
    @ApiModelProperty("买受人证件号")
    private String gmrzjhm;
    @ApiModelProperty("地址")
    private String xmldfh;
    @ApiModelProperty("房屋性质")
    private String fwxz;
    @ApiModelProperty("建筑面积")
    private String jzmj;
    @ApiModelProperty("单价")
    private String fwdj;
    @ApiModelProperty("总层数")
    private String cs;
    @ApiModelProperty("所在层")
    private String mylc;
    @ApiModelProperty("层高")
    private String fycg;
    @ApiModelProperty("结构")
    private String fyjg;
    @ApiModelProperty("合同总价款")
    private String htzjk;
    @ApiModelProperty("用途")
    private String fwyt;
    @ApiModelProperty("住宅用地截止日期")
    private String zzsyjzrq;
    @ApiModelProperty("商业用地截止日期")
    private String sysyjzrq;
    @ApiModelProperty("土地用途")
    private String tdyt;

    public String getHtbabm() {
        return htbabm;
    }

    public void setHtbabm(String htbabm) {
        this.htbabm = htbabm;
    }

    public String getHtbazt() {
        return htbazt;
    }

    public void setHtbazt(String htbazt) {
        this.htbazt = htbazt;
    }

    public String getBasj() {
        return basj;
    }

    public void setBasj(String basj) {
        this.basj = basj;
    }

    public String getKfsmc() {
        return kfsmc;
    }

    public void setKfsmc(String kfsmc) {
        this.kfsmc = kfsmc;
    }

    public String getMsr() {
        return msr;
    }

    public void setMsr(String msr) {
        this.msr = msr;
    }

    public String getGmrzjhm() {
        return gmrzjhm;
    }

    public void setGmrzjhm(String gmrzjhm) {
        this.gmrzjhm = gmrzjhm;
    }

    public String getXmldfh() {
        return xmldfh;
    }

    public void setXmldfh(String xmldfh) {
        this.xmldfh = xmldfh;
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

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getMylc() {
        return mylc;
    }

    public void setMylc(String mylc) {
        this.mylc = mylc;
    }

    public String getFycg() {
        return fycg;
    }

    public void setFycg(String fycg) {
        this.fycg = fycg;
    }

    public String getFyjg() {
        return fyjg;
    }

    public void setFyjg(String fyjg) {
        this.fyjg = fyjg;
    }

    public String getHtzjk() {
        return htzjk;
    }

    public void setHtzjk(String htzjk) {
        this.htzjk = htzjk;
    }

    public String getZzsyjzrq() {
        return zzsyjzrq;
    }

    public void setZzsyjzrq(String zzsyjzrq) {
        this.zzsyjzrq = zzsyjzrq;
    }

    public String getSysyjzrq() {
        return sysyjzrq;
    }

    public void setSysyjzrq(String sysyjzrq) {
        this.sysyjzrq = sysyjzrq;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    @Override
    public String toString() {
        return "HtbaSpfWqxxDTO{" +
                "htbabm='" + htbabm + '\'' +
                ", htbazt='" + htbazt + '\'' +
                ", basj='" + basj + '\'' +
                ", kfsmc='" + kfsmc + '\'' +
                ", msr='" + msr + '\'' +
                ", gmrzjhm='" + gmrzjhm + '\'' +
                ", xmldfh='" + xmldfh + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", jzmj='" + jzmj + '\'' +
                ", fwdj='" + fwdj + '\'' +
                ", cs='" + cs + '\'' +
                ", mylc='" + mylc + '\'' +
                ", fycg='" + fycg + '\'' +
                ", fyjg='" + fyjg + '\'' +
                ", htzjk='" + htzjk + '\'' +
                ", fwyt='" + fwyt + '\'' +
                ", zzsyjzrq='" + zzsyjzrq + '\'' +
                ", sysyjzrq='" + sysyjzrq + '\'' +
                ", tdyt='" + tdyt + '\'' +
                '}';
    }
}
