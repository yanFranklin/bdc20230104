package cn.gtmap.realestate.common.core.bo.accessnewlog;

import cn.gtmap.realestate.common.util.jaxb.JaxbStringAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/27
 * @description 当日上报详单
 */
@XmlType(
        name = "access",
        propOrder = {"bwid", "ywh", "bdcdyh"}
)
@XmlRootElement(
        name = "Access"
)
public class Access {

    private String bwid;
    private String ywh;
    private String bdcdyh;

    public Access() {
    }

    @XmlAttribute(
            name = "YWH"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }


    @XmlAttribute(
            name = "BDCDYH"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }


    @XmlAttribute(
            name = "BWID"
    )
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getBwid() {
        return bwid;
    }

    public void setBwid(String bwid) {
        this.bwid = bwid;
    }
}
