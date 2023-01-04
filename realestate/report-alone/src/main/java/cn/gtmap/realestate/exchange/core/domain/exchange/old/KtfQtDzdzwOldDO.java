package cn.gtmap.realestate.exchange.core.domain.exchange.old;

import cn.gtmap.realestate.exchange.util.jaxb.JaxbDoubleAdapter;
import cn.gtmap.realestate.exchange.util.jaxb.JaxbIntegerAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * 点状定着物信息
 * Created by xhc on 2015/11/19.
 */
@XmlRootElement(name = "KTF_QT_DZDZW")
@XmlAccessorType(XmlAccessType.NONE)
public class KtfQtDzdzwOldDO implements Serializable {

    private String ysdm = "6001990300";//要素代码
    private String bdcdyh;//不动产单元号
    private Integer bsm;//标识码
    private String zdzhdm;//宗地宗海代码
    private String dzdzwlx;//点状定着物类型
    private String dzwmc;//定着物名称
    private String mjdw;//面积单位
    private Double mj;//面积
    private String dah;//档案号
    private String zt;//状态
    private String qxdm;//区县代码

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "BSM")
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getBsm() {
        return bsm;
    }

    public void setBsm(Integer bsm) {
        this.bsm = bsm;
    }

    @XmlAttribute(name = "ZDZHDM")
    public String getZdzhdm() {
        return zdzhdm;
    }

    public void setZdzhdm(String zdzhdm) {
        this.zdzhdm = zdzhdm;
    }

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "DZDZWLX")
    public String getDzdzwlx() {
        return dzdzwlx;
    }

    public void setDzdzwlx(String dzdzwlx) {
        this.dzdzwlx = dzdzwlx;
    }

    @XmlAttribute(name = "DZWMC")
    public String getDzwmc() {
        return dzwmc;
    }

    public void setDzwmc(String dzwmc) {
        this.dzwmc = dzwmc;
    }

    @XmlAttribute(name = "MJDW")
    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    @XmlAttribute(name = "MJ")
    @XmlJavaTypeAdapter(JaxbDoubleAdapter.class)
    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    @XmlAttribute(name = "DAH")
    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    @XmlAttribute(name = "ZT")
    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }
}
