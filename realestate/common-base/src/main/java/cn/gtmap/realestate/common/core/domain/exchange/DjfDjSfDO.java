package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "DJF_DJ_SF")
@XmlRootElement(name = "DJF_DJ_SF")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjSfDO implements Serializable, AccessData  {

    private String ysdm = "6004040000";//要素代码
    @Id
    @ApiModelProperty(value = "收费项目id")
    private String sfxmid;
    @ApiModelProperty(value = "业务号")
    @RequiredFk
    private String ywh;
    @ApiModelProperty(value = "计费人员")
    private String jfry;
    @ApiModelProperty(value = "计费日期")
    private Date jfrq;
    @ApiModelProperty(value = "收费科目名称")
    private String sfkmmc;
    @ApiModelProperty(value = "是否额外收费")
    private String sfewsf;
    @ApiModelProperty(value = "收费基数")
    private String sfjs;
    @ApiModelProperty(value = "数量")
    private String sl;
    @ApiModelProperty(value = "收费类型")
    private String sflx;
    @ApiModelProperty(value = "应收金额")
    private String ysje;
    @ApiModelProperty(value = "折扣后应收金额")
    private String zkhysje;
    @ApiModelProperty(value = "收费人员")
    private String sfry;
    @ApiModelProperty(value = "收费日期")
    private Date sfrq;
    @ApiModelProperty(value = "付费方")
    private String fff;
    @ApiModelProperty(value = "实际付费人")
    private String sjffr;
    @ApiModelProperty(value = "实收金额")
    private String ssje;
    @ApiModelProperty(value = "收费单位")
    private String sfdw;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;
    @ApiModelProperty(value = "付费方式")
    private String fffs;
    @ApiModelProperty(value = "付费业务号")
    private String jfywh;
    @ApiModelProperty(value = "付费人微信标识")
    private String ffrwxbs;
    @ApiModelProperty(value = "应付费人")
    private String yffr;
    @ApiModelProperty(value = "付费终端")
    private String ffzd;
    @ApiModelProperty(value = "缴费状态")
    private String jfzt;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    public String getSfxmid() {
        return sfxmid;
    }

    public void setSfxmid(String sfxmid) {
        this.sfxmid = sfxmid;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "JFRY")
    public String getJfry() {
        return jfry;
    }

    public void setJfry(String jfry) {
        this.jfry = jfry;
    }

    @XmlAttribute(name = "JFRQ")
    public Date getJfrq() {
        return jfrq;
    }

    public void setJfrq(Date jfrq) {
        this.jfrq = jfrq;
    }

    @XmlAttribute(name = "SFKMMC")
    public String getSfkmmc() {
        return sfkmmc;
    }

    public void setSfkmmc(String sfkmmc) {
        this.sfkmmc = sfkmmc;
    }

    @XmlAttribute(name = "SFEWSF")
    public String getSfewsf() {
        return sfewsf;
    }

    public void setSfewsf(String sfewsf) {
        this.sfewsf = sfewsf;
    }

    @XmlAttribute(name = "SFJS")
    public String getSfjs() {
        return sfjs;
    }

    public void setSfjs(String sfjs) {
        this.sfjs = sfjs;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    @XmlAttribute(name = "SFLX")
    public String getSflx() {
        return sflx;
    }

    public void setSflx(String sflx) {
        this.sflx = sflx;
    }

    @XmlAttribute(name = "YSJE")
    public String getYsje() {
        return ysje;
    }

    public void setYsje(String ysje) {
        this.ysje = ysje;
    }

    @XmlAttribute(name = "ZKHYSJE")
    public String getZkhysje() {
        return zkhysje;
    }

    public void setZkhysje(String zkhysje) {
        this.zkhysje = zkhysje;
    }

    @XmlAttribute(name = "SFRY")
    public String getSfry() {
        return sfry;
    }

    public void setSfry(String sfry) {
        this.sfry = sfry;
    }

    @XmlAttribute(name = "SFRQ")
    public Date getSfrq() {
        return sfrq;
    }

    public void setSfrq(Date sfrq) {
        this.sfrq = sfrq;
    }

    @XmlAttribute(name = "FFF")
    public String getFff() {
        return fff;
    }

    public void setFff(String fff) {
        this.fff = fff;
    }

    @XmlAttribute(name = "SJFFR")
    public String getSjffr() {
        return sjffr;
    }

    public void setSjffr(String sjffr) {
        this.sjffr = sjffr;
    }

    @XmlAttribute(name = "SSJE")
    public String getSsje() {
        return ssje;
    }

    public void setSsje(String ssje) {
        this.ssje = ssje;
    }

    @XmlAttribute(name = "SFDW")
    public String getSfdw() {
        return sfdw;
    }

    public void setSfdw(String sfdw) {
        this.sfdw = sfdw;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getFffs() {
        return fffs;
    }

    public void setFffs(String fffs) {
        this.fffs = fffs;
    }

    public String getJfywh() {
        return jfywh;
    }

    public void setJfywh(String jfywh) {
        this.jfywh = jfywh;
    }

    public String getFfrwxbs() {
        return ffrwxbs;
    }

    public void setFfrwxbs(String ffrwxbs) {
        this.ffrwxbs = ffrwxbs;
    }

    public String getYffr() {
        return yffr;
    }

    public void setYffr(String yffr) {
        this.yffr = yffr;
    }

    public String getFfzd() {
        return ffzd;
    }

    public void setFfzd(String ffzd) {
        this.ffzd = ffzd;
    }

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }
}
