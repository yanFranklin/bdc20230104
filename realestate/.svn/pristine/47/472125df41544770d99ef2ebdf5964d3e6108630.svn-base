package cn.gtmap.realestate.common.core.bo.accessnewlog;

import cn.gtmap.realestate.common.util.jaxb.JaxbStringAdapter;
import cn.gtmap.realestate.common.util.jaxb.JaxbStringAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/27
 * @description 当日登簿详单
 */
@XmlType(
        name = "register",
        propOrder = {"YWH", "DJLX", "QLLX", "BDCDYH", "ZSZMH", "SFSB", "BWID"}
)
@XmlRootElement(
        name = "Register"
)
public class Register {
    private String YWH;
    private String DJLX;
    private String QLLX;
    private String BDCDYH;
    private String ZSZMH;

    /**
     * 是否上报：1已上报；0未上报
     */
    private String SFSB;
    private String BWID;


    public Register() {
    }


    @XmlAttribute(
            name = "YWH"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getYWH() {
        return this.YWH;
    }

    public void setYWH(String YWH) {
        this.YWH = YWH;
    }


    @XmlAttribute(
            name = "DJLX"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getDJLX() {
        return this.DJLX;
    }

    public void setDJLX(String DJLX) {
        this.DJLX = DJLX;
    }

    @XmlAttribute(
            name = "QLLX"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getQLLX() {
        return QLLX;
    }

    public void setQLLX(String QLLX) {
        this.QLLX = QLLX;
    }


    @XmlAttribute(
            name = "BDCDYH"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getBDCDYH() {
        return BDCDYH;
    }

    public void setBDCDYH(String BDCDYH) {
        this.BDCDYH = BDCDYH;
    }

    @XmlAttribute(
            name = "ZSZMH"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getZSZMH() {
        return ZSZMH;
    }

    public void setZSZMH(String ZSZMH) {
        this.ZSZMH = ZSZMH;
    }

    @XmlAttribute(
            name = "SFSB"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getSFSB() {
        return SFSB;
    }


    public void setSFSB(String SFSB) {
        this.SFSB = SFSB;
    }

    @XmlAttribute(
            name = "BWID"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getBWID() {
        return BWID;
    }

    public void setBWID(String BWID) {
        this.BWID = BWID;
    }
}
