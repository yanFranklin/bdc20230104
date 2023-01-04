package cn.gtmap.realestate.exchange.core.dto.nantong.sw.tsswxx.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-06-11
 * @description 南通如东推送存量房税务合同信息实体
 */
public class RdTsswxxHtxxResponseDTO {
    @ApiModelProperty("行政区划代码")
    private String XZQH;
    @ApiModelProperty("合同编号")
    private String HTBH;
    @ApiModelProperty("房屋幢号")
    private String FWZH;
    @ApiModelProperty("房屋房号")
    private String FWFH;
    @ApiModelProperty("房屋单元")
    private String FWDY;
    @ApiModelProperty("房屋性质")
    private String FWXZ;
    @ApiModelProperty("增量房存量房标志\t\t1：增量房 2：存量房")
    private String ZLFCLFBZ;
    @ApiModelProperty("xmid")
    private String XMID;
    @ApiModelProperty("街道乡镇")
    private String JDXZ;
    @ApiModelProperty("房屋朝向")
    private String FWCX;
    @ApiModelProperty("合同金额")
    private String HTJE;
    @ApiModelProperty("房屋地址")
    private String FWDZ;
    @ApiModelProperty("不动产单元号")
    private String BDCDYH;
    @ApiModelProperty("合同取得时间")
    private String htqdsj;
    @ApiModelProperty("房屋用途")
    private String FWYT;
    @ApiModelProperty("建筑面积")
    private String JZMJ;
    //52019 【南通大市】修改家庭成员界面核税信息推送接口 新增字段
    @ApiModelProperty("房产交易方式代码")
    private String fcjyfsdm;
    @ApiModelProperty("不动产项目名称")
    private String bdcxmmc;
    @ApiModelProperty("成交价是否含税标志")
    private String cjjgsfhs;
    @ApiModelProperty("楼层")
    private String lc2;
    @ApiModelProperty("建筑结构代码")
    private String jzjglxdm;
    @ApiModelProperty("楼层总数")
    private String lczs;
    @ApiModelProperty("房屋/土地总价格")
    private Double fwtdzjg;
    @ApiModelProperty("存量房物业类型")
    private String clfwylx;
    @ApiModelProperty("房屋产权证书号")
    private String fwcqzsh;
    @ApiModelProperty("单价")
    private Double dj;
    @ApiModelProperty("建筑年份")
    private String jznf;
    @ApiModelProperty("转让方承受方关系代码")
    private String zrfcsfgxdm;


    @ApiModelProperty("")
    private String BHSJE;
    @ApiModelProperty("")
    private String CKMJ;
    @ApiModelProperty("")
    private String CCSMJ;
    @ApiModelProperty("")
    private String GLMJ;
    @ApiModelProperty("")
    private String fpdm;
    @ApiModelProperty("")
    private String fphm;
    @ApiModelProperty("")
    private String FJ;


    public String getXZQH() {
        return XZQH;
    }

    public void setXZQH(String XZQH) {
        this.XZQH = XZQH;
    }

    public String getHTBH() {
        return HTBH;
    }

    public void setHTBH(String HTBH) {
        this.HTBH = HTBH;
    }

    public String getFWZH() {
        return FWZH;
    }

    public void setFWZH(String FWZH) {
        this.FWZH = FWZH;
    }

    public String getFWFH() {
        return FWFH;
    }

    public void setFWFH(String FWFH) {
        this.FWFH = FWFH;
    }

    public String getFWDY() {
        return FWDY;
    }

    public void setFWDY(String FWDY) {
        this.FWDY = FWDY;
    }

    public String getFWXZ() {
        return FWXZ;
    }

    public void setFWXZ(String FWXZ) {
        this.FWXZ = FWXZ;
    }

    public String getZLFCLFBZ() {
        return ZLFCLFBZ;
    }

    public void setZLFCLFBZ(String ZLFCLFBZ) {
        this.ZLFCLFBZ = ZLFCLFBZ;
    }

    public String getXMID() {
        return XMID;
    }

    public void setXMID(String XMID) {
        this.XMID = XMID;
    }

    public String getJDXZ() {
        return JDXZ;
    }

    public void setJDXZ(String JDXZ) {
        this.JDXZ = JDXZ;
    }

    public String getFWCX() {
        return FWCX;
    }

    public void setFWCX(String FWCX) {
        this.FWCX = FWCX;
    }

    public String getHTJE() {
        return HTJE;
    }

    public void setHTJE(String HTJE) {
        this.HTJE = HTJE;
    }

    public String getFWDZ() {
        return FWDZ;
    }

    public void setFWDZ(String FWDZ) {
        this.FWDZ = FWDZ;
    }

    public String getBDCDYH() {
        return BDCDYH;
    }

    public void setBDCDYH(String BDCDYH) {
        this.BDCDYH = BDCDYH;
    }

    public String getHtqdsj() {
        return htqdsj;
    }

    public void setHtqdsj(String htqdsj) {
        this.htqdsj = htqdsj;
    }

    public String getFWYT() {
        return FWYT;
    }

    public void setFWYT(String FWYT) {
        this.FWYT = FWYT;
    }

    public String getJZMJ() {
        return JZMJ;
    }

    public void setJZMJ(String JZMJ) {
        this.JZMJ = JZMJ;
    }

    public String getFcjyfsdm() {
        return fcjyfsdm;
    }

    public void setFcjyfsdm(String fcjyfsdm) {
        this.fcjyfsdm = fcjyfsdm;
    }

    public String getBdcxmmc() {
        return bdcxmmc;
    }

    public void setBdcxmmc(String bdcxmmc) {
        this.bdcxmmc = bdcxmmc;
    }

    public String getCjjgsfhs() {
        return cjjgsfhs;
    }

    public void setCjjgsfhs(String cjjgsfhs) {
        this.cjjgsfhs = cjjgsfhs;
    }

    public String getLc2() {
        return lc2;
    }

    public void setLc2(String lc2) {
        this.lc2 = lc2;
    }

    public String getJzjglxdm() {
        return jzjglxdm;
    }

    public void setJzjglxdm(String jzjglxdm) {
        this.jzjglxdm = jzjglxdm;
    }

    public String getLczs() {
        return lczs;
    }

    public void setLczs(String lczs) {
        this.lczs = lczs;
    }

    public Double getFwtdzjg() {
        return fwtdzjg;
    }

    public void setFwtdzjg(Double fwtdzjg) {
        this.fwtdzjg = fwtdzjg;
    }

    public String getClfwylx() {
        return clfwylx;
    }

    public void setClfwylx(String clfwylx) {
        this.clfwylx = clfwylx;
    }

    public String getFwcqzsh() {
        return fwcqzsh;
    }

    public void setFwcqzsh(String fwcqzsh) {
        this.fwcqzsh = fwcqzsh;
    }

    public Double getDj() {
        return dj;
    }

    public void setDj(Double dj) {
        this.dj = dj;
    }

    public String getJznf() {
        return jznf;
    }

    public void setJznf(String jznf) {
        this.jznf = jznf;
    }

    public String getZrfcsfgxdm() {
        return zrfcsfgxdm;
    }

    public void setZrfcsfgxdm(String zrfcsfgxdm) {
        this.zrfcsfgxdm = zrfcsfgxdm;
    }

    public String getBHSJE() {
        return BHSJE;
    }

    public void setBHSJE(String BHSJE) {
        this.BHSJE = BHSJE;
    }

    public String getCKMJ() {
        return CKMJ;
    }

    public void setCKMJ(String CKMJ) {
        this.CKMJ = CKMJ;
    }

    public String getCCSMJ() {
        return CCSMJ;
    }

    public void setCCSMJ(String CCSMJ) {
        this.CCSMJ = CCSMJ;
    }

    public String getGLMJ() {
        return GLMJ;
    }

    public void setGLMJ(String GLMJ) {
        this.GLMJ = GLMJ;
    }

    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm;
    }

    public String getFJ() {
        return FJ;
    }

    public void setFJ(String FJ) {
        this.FJ = FJ;
    }

    @Override
    public String toString() {
        return "RdTsswxxHtxxResponseDTO{" +
                "XZQH='" + XZQH + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", FWZH='" + FWZH + '\'' +
                ", FWFH='" + FWFH + '\'' +
                ", FWDY='" + FWDY + '\'' +
                ", FWXZ='" + FWXZ + '\'' +
                ", ZLFCLFBZ='" + ZLFCLFBZ + '\'' +
                ", XMID='" + XMID + '\'' +
                ", JDXZ='" + JDXZ + '\'' +
                ", FWCX='" + FWCX + '\'' +
                ", HTJE='" + HTJE + '\'' +
                ", FWDZ='" + FWDZ + '\'' +
                ", BDCDYH='" + BDCDYH + '\'' +
                ", htqdsj='" + htqdsj + '\'' +
                ", FWYT='" + FWYT + '\'' +
                ", JZMJ='" + JZMJ + '\'' +
                ", fcjyfsdm='" + fcjyfsdm + '\'' +
                ", bdcxmmc='" + bdcxmmc + '\'' +
                ", cjjgsfhs='" + cjjgsfhs + '\'' +
                ", lc2='" + lc2 + '\'' +
                ", jzjglxdm='" + jzjglxdm + '\'' +
                ", lczs='" + lczs + '\'' +
                ", fwtdzjg=" + fwtdzjg +
                ", clfwylx='" + clfwylx + '\'' +
                ", fwcqzsh='" + fwcqzsh + '\'' +
                ", dj=" + dj +
                ", jznf='" + jznf + '\'' +
                ", zrfcsfgxdm='" + zrfcsfgxdm + '\'' +
                ", BHSJE='" + BHSJE + '\'' +
                ", CKMJ='" + CKMJ + '\'' +
                ", CCSMJ='" + CCSMJ + '\'' +
                ", GLMJ='" + GLMJ + '\'' +
                ", fpdm='" + fpdm + '\'' +
                ", fphm='" + fphm + '\'' +
                ", FJ='" + FJ + '\'' +
                '}';
    }
}
