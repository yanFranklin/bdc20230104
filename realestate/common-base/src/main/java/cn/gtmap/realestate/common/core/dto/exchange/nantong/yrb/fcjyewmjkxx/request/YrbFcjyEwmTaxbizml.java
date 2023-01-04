package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 1.12.    房产交易缴款二维码获取【A018
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "TAXBIZML")
public class YrbFcjyEwmTaxbizml {

    private List<YrbFcjyEwmRequest> fcjyewmlist;

    @XmlElement(name = "FCJYEWMLIST")
    public List<YrbFcjyEwmRequest> getFcjyewmlist() {
        return fcjyewmlist;
    }

    public void setFcjyewmlist(List<YrbFcjyEwmRequest> fcjyewmlist) {
        this.fcjyewmlist = fcjyewmlist;
    }

    @Override
    public String toString() {
        return "YrbFcjyEwmTaxbizml{" +
                "fcjyewmlist=" + fcjyewmlist +
                '}';
    }
}
