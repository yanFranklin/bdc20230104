package cn.gtmap.realestate.common.core.dto.etl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description
 */
public class BzjdDTO{

    @XmlTransient
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "产权登记编号，取输入的产权证号的受理编号 -> bdc_xm.slbh")
    private String cqdjbh;

    @ApiModelProperty(value = "房屋编码，取不动产单元号 -> bdc_xm.bdcdyh")
    private String fwbm;

    @ApiModelProperty(value = "产权证号 -> bdc_xm.bdcqzh")
    private String cqzh;

    @ApiModelProperty(value = "姓名 -> bdc_xm.qlr")
    private String xm;

    @ApiModelProperty(value = "证件号码 -> bdc_xm.qlrzjh")
    private String zjhm;

    @ApiModelProperty(value = "房屋坐落 -> bdc_xm.zl")
    private String fwzl;

    @ApiModelProperty(value = "总层数 -> bdc_fdcq.myzcs")
    private String zcs;

    @ApiModelProperty(value = "所在层数 -> bdc_fdcq.szmyc ")
    private String szcs;

    @ApiModelProperty(value = "结构 -> bdc_fdcq.fwjg")
    private String jg;

    @ApiModelProperty(value = "用途 -> bdc_xm.dzwyt")
    private String yt;

    @ApiModelProperty(value = "房屋性质 -> bdc_fdcq.fwxz")
    private String fwxz;

    @ApiModelProperty(value = "共有方式 -> bdc_xm.gyfs")
    private String gyfs;

    @ApiModelProperty(value = "如果为共同共有或者单独所有，返回空字符串 -> bdc_xm.qlbl")
    private String gyfe;

    @ApiModelProperty(value = "交易方式 -> bdc_sl_jyxx.jylx")
    private String jyfs;

    @ApiModelProperty(value = "建面，建筑面积 -> bdc_fdcq.jzmj")
    private String jm;

    @ApiModelProperty(value = "申报估值，取房产价值 -> bdc_sl_jyxx.pgjg")
    private String sbgz;

    @ApiModelProperty(value = "房屋产权日期，取登簿日期 -> bdc_fdcq.djsj")
    private String fwcqrq;


    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "权利人性质，是转让方还是受让方")
    private String qlrxz;

    @ApiModelProperty(value = "产权标记，临时或现势")
    private String cqbj;

    @ApiModelProperty(value = "进度")
    private String jd;

    @ApiModelProperty(value = "所在区县")
    private String xzqy;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getCqdjbh() {
        return cqdjbh;
    }

    public void setCqdjbh(String cqdjbh) {
        this.cqdjbh = cqdjbh;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getSzcs() {
        return szcs;
    }

    public void setSzcs(String szcs) {
        this.szcs = szcs;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyfe() {
        return gyfe;
    }

    public void setGyfe(String gyfe) {
        this.gyfe = gyfe;
    }

    public String getJyfs() {
        return jyfs;
    }

    public void setJyfs(String jyfs) {
        this.jyfs = jyfs;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public String getSbgz() {
        return sbgz;
    }

    public void setSbgz(String sbgz) {
        this.sbgz = sbgz;
    }

    public String getFwcqrq() {
        return fwcqrq;
    }

    public void setFwcqrq(String fwcqrq) {
        this.fwcqrq = fwcqrq;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getQlrxz() {
        return qlrxz;
    }

    public void setQlrxz(String qlrxz) {
        this.qlrxz = qlrxz;
    }

    public String getCqbj() {
        return cqbj;
    }

    public void setCqbj(String cqbj) {
        this.cqbj = cqbj;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getXzqy() {
        return xzqy;
    }

    public void setXzqy(String xzqy) {
        this.xzqy = xzqy;
    }

    @Override
    public String toString() {
        return "BzjdDTO{" +
                "xmid='" + xmid + '\'' +
                ", cqdjbh='" + cqdjbh + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", xm='" + xm + '\'' +
                ", zjhm='" + zjhm + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", zcs='" + zcs + '\'' +
                ", szcs='" + szcs + '\'' +
                ", jg='" + jg + '\'' +
                ", yt='" + yt + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", gyfe='" + gyfe + '\'' +
                ", jyfs='" + jyfs + '\'' +
                ", jm='" + jm + '\'' +
                ", sbgz='" + sbgz + '\'' +
                ", fwcqrq='" + fwcqrq + '\'' +
                ", htbh='" + htbh + '\'' +
                ", qlrxz='" + qlrxz + '\'' +
                ", cqbj='" + cqbj + '\'' +
                ", jd='" + jd + '\'' +
                ", xzqy='" + xzqy + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BzjdDTO)) {
            return false;
        }
        BzjdDTO bzjdDTO = (BzjdDTO) o;
        return Objects.equals(getXmid(), bzjdDTO.getXmid()) &&
                Objects.equals(getCqdjbh(), bzjdDTO.getCqdjbh()) &&
                Objects.equals(getFwbm(), bzjdDTO.getFwbm()) &&
                Objects.equals(getCqzh(), bzjdDTO.getCqzh()) &&
                Objects.equals(getXm(), bzjdDTO.getXm()) &&
                Objects.equals(getZjhm(), bzjdDTO.getZjhm()) &&
                Objects.equals(getFwzl(), bzjdDTO.getFwzl()) &&
                Objects.equals(getZcs(), bzjdDTO.getZcs()) &&
                Objects.equals(getSzcs(), bzjdDTO.getSzcs()) &&
                Objects.equals(getJg(), bzjdDTO.getJg()) &&
                Objects.equals(getYt(), bzjdDTO.getYt()) &&
                Objects.equals(getFwxz(), bzjdDTO.getFwxz()) &&
                Objects.equals(getGyfs(), bzjdDTO.getGyfs()) &&
                Objects.equals(getGyfe(), bzjdDTO.getGyfe()) &&
                Objects.equals(getJyfs(), bzjdDTO.getJyfs()) &&
                Objects.equals(getJm(), bzjdDTO.getJm()) &&
                Objects.equals(getSbgz(), bzjdDTO.getSbgz()) &&
                Objects.equals(getFwcqrq(), bzjdDTO.getFwcqrq()) &&
                Objects.equals(getHtbh(), bzjdDTO.getHtbh()) &&
                Objects.equals(getQlrxz(), bzjdDTO.getQlrxz()) &&
                Objects.equals(getCqbj(), bzjdDTO.getCqbj()) &&
                Objects.equals(getJd(), bzjdDTO.getJd()) &&
                Objects.equals(getXzqy(), bzjdDTO.getXzqy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getXmid(), getCqdjbh(), getFwbm(), getCqzh(), getXm(), getZjhm(), getFwzl(), getZcs(), getSzcs(), getJg(), getYt(), getFwxz(), getGyfs(), getGyfe(), getJyfs(), getJm(), getSbgz(), getFwcqrq(), getHtbh(), getQlrxz(), getCqbj(), getJd(), getXzqy());
    }
}
