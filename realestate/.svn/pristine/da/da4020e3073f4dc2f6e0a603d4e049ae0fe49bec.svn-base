package cn.gtmap.realestate.common.core.dto.etl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description
 */
public class XscqDTO{

    @XmlTransient
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "产权登记编号，取输入的产权证号的受理编号 -> bdc_xm.slbh")
    private String cqdjbh;

    @ApiModelProperty(value = "房屋编码，取不动产单元号 -> bdc_xm.bdcdyh")
    private String fwbm;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

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


    @ApiModelProperty(value = "幢号 -> bdc_fdcq.zh")
    private String zh;

    @ApiModelProperty(value = "房号，取权籍房间号 -> bdc_fdcq.fjh")
    private String fh;

    @ApiModelProperty(value = "用途大类，取权籍户室表中房屋类型字段 -> bdc_fdcq.fwlx")
    private String yt2;

    @ApiModelProperty(value = "所在区县，用不动产单元号转义 -> bdc_xm.qxdm")
    private String szqx;

    @ApiModelProperty(value = "此处应填写查封现势数量，后面其他限制权利同样规则，如果没有则返回0 -> bdc_cf")
    private String cfzt;

    @ApiModelProperty(value = "抵押状态 -> bdc_dya")
    private String dyzt;

    @ApiModelProperty(value = "预告状态 -> bdc_yg")
    private String ygzt;

    @ApiModelProperty(value = "异议状态 -> bdc_yy")
    private String yyzt;

    @ApiModelProperty(value = "土地性质，用不动产单元号转义 -> bdc_xm.qlxz")
    private String tdxz;

    @ApiModelProperty(value = "土地交易方式，取土地权利性质 -> ?")
    private String tdjyfs;

    @ApiModelProperty(value = "建筑年度，fw_ljz.jgrq")
    private String jznd;

    @ApiModelProperty(value = "小区名称，fw_ljz.fwmc")
    private String jzqh;

    @ApiModelProperty(value = "权属状态")
    private String qszt;

    @ApiModelProperty(value = "注销办结-取qszt值")
    private String zxbj;

    @ApiModelProperty(value = "遗失公告状态")
    private String gszt;

    @ApiModelProperty(value = "抵押信息集合")
    private List<XscqDyxxDTo> dyxxList;

    public List<XscqDyxxDTo> getDyxxList() {
        return dyxxList;
    }

    public void setDyxxList(List<XscqDyxxDTo> dyxxList) {
        this.dyxxList = dyxxList;
    }

    @XmlElement(name = "gszt", nillable = true)
    public String getGszt() {
        return gszt;
    }

    public void setGszt(String gszt) {
        this.gszt = gszt;
    }

    @XmlElement(name = "zxbj", nillable = true)
    public String getZxbj() {
        return zxbj;
    }

    public void setZxbj(String zxbj) {
        this.zxbj = zxbj;
    }

    @XmlElement(name = "qszt", nillable = true)
    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    @XmlElement(name = "jzqh", nillable = true)
    public String getJzqh() {
        return jzqh;
    }

    public void setJzqh(String jzqh) {
        this.jzqh = jzqh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

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

    @XmlElement(name = "gyfe", nillable = true)
    public String getGyfe() {
        return gyfe;
    }

    public void setGyfe(String gyfe) {
        this.gyfe = gyfe;
    }

    @XmlElement(name = "jyfs", nillable = true)
    public String getJyfs() {
        return jyfs;
    }

    public void setJyfs(String jyfs) {
        this.jyfs = jyfs;
    }

    @XmlElement(name = "jm", nillable = true)
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

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    @XmlElement(name = "fh", nillable = true)
    public String getFh() {
        return fh;
    }

    public void setFh(String fh) {
        this.fh = fh;
    }

    public String getYt2() {
        return yt2;
    }

    public void setYt2(String yt2) {
        this.yt2 = yt2;
    }

    public String getSzqx() {
        return szqx;
    }

    public void setSzqx(String szqx) {
        this.szqx = szqx;
    }

    public String getCfzt() {
        return cfzt;
    }

    public void setCfzt(String cfzt) {
        this.cfzt = cfzt;
    }

    public String getDyzt() {
        return dyzt;
    }

    public void setDyzt(String dyzt) {
        this.dyzt = dyzt;
    }

    public String getYgzt() {
        return ygzt;
    }

    public void setYgzt(String ygzt) {
        this.ygzt = ygzt;
    }

    public String getYyzt() {
        return yyzt;
    }

    public void setYyzt(String yyzt) {
        this.yyzt = yyzt;
    }

    public String getTdxz() {
        return tdxz;
    }

    public void setTdxz(String tdxz) {
        this.tdxz = tdxz;
    }

    public String getTdjyfs() {
        return tdjyfs;
    }

    public void setTdjyfs(String tdjyfs) {
        this.tdjyfs = tdjyfs;
    }

    @XmlElement(name = "jznd", nillable = true)
    public String getJznd() {
        return jznd;
    }

    public void setJznd(String jznd) {
        this.jznd = jznd;
    }

    @Override
    public String toString() {
        return "XscqDTO{" +
                "xmid='" + xmid + '\'' +
                ", cqdjbh='" + cqdjbh + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
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
                ", zh='" + zh + '\'' +
                ", fh='" + fh + '\'' +
                ", yt2='" + yt2 + '\'' +
                ", szqx='" + szqx + '\'' +
                ", cfzt='" + cfzt + '\'' +
                ", dyzt='" + dyzt + '\'' +
                ", ygzt='" + ygzt + '\'' +
                ", yyzt='" + yyzt + '\'' +
                ", tdxz='" + tdxz + '\'' +
                ", tdjyfs='" + tdjyfs + '\'' +
                ", jznd='" + jznd + '\'' +
                ", jzqh='" + jzqh + '\'' +
                ", qszt='" + qszt + '\'' +
                ", zxbj='" + zxbj + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof XscqDTO)) {
            return false;
        }
        XscqDTO xscqDTO = (XscqDTO) o;
        return Objects.equals(getXmid(), xscqDTO.getXmid()) &&
                Objects.equals(getCqdjbh(), xscqDTO.getCqdjbh()) &&
                Objects.equals(getFwbm(), xscqDTO.getFwbm()) &&
                Objects.equals(getCqzh(), xscqDTO.getCqzh()) &&
                Objects.equals(getXm(), xscqDTO.getXm()) &&
                Objects.equals(getZjhm(), xscqDTO.getZjhm()) &&
                Objects.equals(getFwzl(), xscqDTO.getFwzl()) &&
                Objects.equals(getZcs(), xscqDTO.getZcs()) &&
                Objects.equals(getSzcs(), xscqDTO.getSzcs()) &&
                Objects.equals(getJg(), xscqDTO.getJg()) &&
                Objects.equals(getYt(), xscqDTO.getYt()) &&
                Objects.equals(getFwxz(), xscqDTO.getFwxz()) &&
                Objects.equals(getGyfs(), xscqDTO.getGyfs()) &&
                Objects.equals(getGyfe(), xscqDTO.getGyfe()) &&
                Objects.equals(getJyfs(), xscqDTO.getJyfs()) &&
                Objects.equals(getJm(), xscqDTO.getJm()) &&
                Objects.equals(getSbgz(), xscqDTO.getSbgz()) &&
                Objects.equals(getFwcqrq(), xscqDTO.getFwcqrq()) &&
                Objects.equals(getZh(), xscqDTO.getZh()) &&
                Objects.equals(getFh(), xscqDTO.getFh()) &&
                Objects.equals(getYt2(), xscqDTO.getYt2()) &&
                Objects.equals(getSzqx(), xscqDTO.getSzqx()) &&
                Objects.equals(getCfzt(), xscqDTO.getCfzt()) &&
                Objects.equals(getDyzt(), xscqDTO.getDyzt()) &&
                Objects.equals(getYgzt(), xscqDTO.getYgzt()) &&
                Objects.equals(getYyzt(), xscqDTO.getYyzt()) &&
                Objects.equals(getTdxz(), xscqDTO.getTdxz()) &&
                Objects.equals(getTdjyfs(), xscqDTO.getTdjyfs()) &&
                Objects.equals(getJznd(), xscqDTO.getJznd()) &&
                Objects.equals(getJzqh(), xscqDTO.getJzqh()) &&
                Objects.equals(getBdcdyh(), xscqDTO.getBdcdyh());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getXmid(), getCqdjbh(), getFwbm(), getCqzh(), getXm(), getZjhm(), getFwzl(), getZcs(), getSzcs(), getJg(), getYt(), getFwxz(), getGyfs(), getGyfe(), getJyfs(), getJm(), getSbgz(), getFwcqrq(), getZh(), getFh(), getYt2(), getSzqx(), getCfzt(), getDyzt(), getYgzt(), getYyzt(), getTdxz(), getTdjyfs(), getJznd(), getJzqh(), getBdcdyh());
    }
}
