package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 2.21. 契税联系单获取【A021】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "TAXBIZML")
public class YrbQslxdhqTaxbizml {

    private YrbQslxdhqRequest fcjyqslxdlist;

    @XmlElement(name = "FCJYQSLXDLIST")
    public YrbQslxdhqRequest getFcjyqslxdlist() {
        return fcjyqslxdlist;
    }

    public void setFcjyqslxdlist(YrbQslxdhqRequest fcjyqslxdlist) {
        this.fcjyqslxdlist = fcjyqslxdlist;
    }


    @Override
    public String toString() {
        return "YrbQslxdhqTaxbizml{" +
                "fcjyqslxdlist=" + fcjyqslxdlist +
                '}';
    }
}
