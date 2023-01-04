package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
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
@Table(name = "KTT_FW_C")
@XmlRootElement(name = "KTT_FW_C")
@XmlAccessorType(XmlAccessType.NONE)
public class KttFwCOldDO implements Serializable, AccessData {

    private String ysdm = "6001030130";//要素代码
    @ApiModelProperty(value = "层号")
    private String ch;
    @ApiModelProperty(value = "自然幢号")
    private String zrzh;
    @ApiModelProperty(value = "实际层见")
    @Id
    private String sjc;
    @ApiModelProperty(value = "名义层见")
    private String myc;
    @ApiModelProperty(value = "层建筑面积＞0单位：平方米")
    private Double cjzmj;
    @ApiModelProperty(value = "层套内建筑面积＞0单位：平方米")
    private Double ctnjzmj;
    @ApiModelProperty(value = "层阳台面积＞=0单位：平方米")
    private Double cytmj;
    @ApiModelProperty(value = "层共有建筑面积＞=0单位：平方米")
    private Double cgyjzmj;
    @ApiModelProperty(value = "层分摊建筑面积＞=0单位：平方米")
    private Double cftjzmj;
    @ApiModelProperty(value = "层半墙面积＞0单位：平方米")
    private Double cbqmj;
    @ApiModelProperty(value = "层高＞0单位：米")
    private Double cg;
    @ApiModelProperty(value = "水平投影面积＞0单位：平方米")
    private Double sptymj;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "不动产单元号/新加")
    @Id
    private String bdcdyh;
    private Date createtime;
    private Date updatetime;
    @ApiModelProperty(value = "逻辑幢编号")
    @Id
    private String ljzbh;


    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "CH")
    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    @XmlAttribute(name = "ZRZH")
    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    @XmlAttribute(name = "SJC")
    public String getSjc() {
        return sjc;
    }

    public void setSjc(String sjc) {
        this.sjc = sjc;
    }

    @XmlAttribute(name = "MYC")
    public String getMyc() {
        return myc;
    }

    public void setMyc(String myc) {
        this.myc = myc;
    }

    @XmlAttribute(name = "CJZMJ")
    public Double getCjzmj() {
        return cjzmj;
    }

    public void setCjzmj(Double cjzmj) {
        this.cjzmj = cjzmj;
    }

    @XmlAttribute(name = "CTNJZMJ")
    public Double getCtnjzmj() {
        return ctnjzmj;
    }

    public void setCtnjzmj(Double ctnjzmj) {
        this.ctnjzmj = ctnjzmj;
    }

    @XmlAttribute(name = "CYTMJ")
    public Double getCytmj() {
        return cytmj;
    }

    public void setCytmj(Double cytmj) {
        this.cytmj = cytmj;
    }

    @XmlAttribute(name = "CGYJZMJ")
    public Double getCgyjzmj() {
        return cgyjzmj;
    }

    public void setCgyjzmj(Double cgyjzmj) {
        this.cgyjzmj = cgyjzmj;
    }

    @XmlAttribute(name = "CFTJZMJ")
    public Double getCftjzmj() {
        return cftjzmj;
    }

    public void setCftjzmj(Double cftjzmj) {
        this.cftjzmj = cftjzmj;
    }

    @XmlAttribute(name = "CBQMJ")
    public Double getCbqmj() {
        return cbqmj;
    }

    public void setCbqmj(Double cbqmj) {
        this.cbqmj = cbqmj;
    }

    @XmlAttribute(name = "CG")
    public Double getCg() {
        return cg;
    }

    public void setCg(Double cg) {
        this.cg = cg;
    }

    @XmlAttribute(name = "SPTYMJ")
    public Double getSptymj() {
        return sptymj;
    }

    public void setSptymj(Double sptymj) {
        this.sptymj = sptymj;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
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

    public String getLjzbh() {
        return ljzbh;
    }

    public void setLjzbh(String ljzbh) {
        this.ljzbh = ljzbh;
    }
}
