package cn.gtmap.realestate.exchange.core.domain.exchange;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2021/0928,1.0
 * @description
 */
@Table(name = "ZTT_GY_JTCY")
@XmlRootElement(name = "ZTT_GY_JTCY")
@XmlAccessorType(XmlAccessType.NONE)
public class ZttGyJtcyDO implements Serializable, AccessData {

    private String ysdm = "6003000000";//要素代码
    @ApiModelProperty(value = "业务号")
    private String ywh;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "顺序号")
    private String sxh;
    @ApiModelProperty(value = "发证机关")
    private String fzjg;
    @ApiModelProperty(value = "所属行业 见本表注2")
    private String sshy;
    @ApiModelProperty(value = "国家/地区国家和地区字典表")
    private String gj;
    @ApiModelProperty(value = "户籍所在省市省市字典表")
    private String hjszss;
    @ApiModelProperty(value = "性别性别字典表")
    private String xb;
    @ApiModelProperty(value = "电话")
    private String dh;
    @ApiModelProperty(value = "地址")
    private String dz;
    @ApiModelProperty(value = "邮编")
    private String yb;
    @ApiModelProperty(value = "工作单位")
    private String gzdw;
    @ApiModelProperty(value = "电子邮件")
    private String dzyj;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "承包方代码")
    private String cbfbm;
    @ApiModelProperty(value = "成员姓名")
    private String cyxm;
    @ApiModelProperty(value = "家庭关系代码")
    private String jtgxdm;
    @ApiModelProperty(value = "身份证号码")
    private String sfzhm;


    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "SXH")
    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    @XmlAttribute(name = "FZJG")
    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    @XmlAttribute(name = "SSHY")
    public String getSshy() {
        return sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    @XmlAttribute(name = "GJ")
    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    @XmlAttribute(name = "HJSZSS")
    public String getHjszss() {
        return hjszss;
    }

    public void setHjszss(String hjszss) {
        this.hjszss = hjszss;
    }

    @XmlAttribute(name = "XB")
    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    @XmlAttribute(name = "DH")
    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    @XmlAttribute(name = "DZ")
    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    @XmlAttribute(name = "YB")
    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    @XmlAttribute(name = "GZDW")
    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    @XmlAttribute(name = "DZYJ")
    public String getDzyj() {
        return dzyj;
    }

    public void setDzyj(String dzyj) {
        this.dzyj = dzyj;
    }

    @XmlAttribute(name = "BZ")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @XmlAttribute(name = "CBFBM")
    public String getCbfbm() {
        return cbfbm;
    }

    public void setCbfbm(String cbfbm) {
        this.cbfbm = cbfbm;
    }

    @XmlAttribute(name = "CYXM")
    public String getCyxm() {
        return cyxm;
    }

    public void setCyxm(String cyxm) {
        this.cyxm = cyxm;
    }

    @XmlAttribute(name = "JTGXDM")
    public String getJtgxdm() {
        return jtgxdm;
    }

    public void setJtgxdm(String jtgxdm) {
        this.jtgxdm = jtgxdm;
    }

    @XmlAttribute(name = "SFZHM")
    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }
}
