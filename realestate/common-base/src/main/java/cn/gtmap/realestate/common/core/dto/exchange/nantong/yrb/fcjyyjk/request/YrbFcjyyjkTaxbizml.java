package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/10
 * @description 1.11.    房产交易应缴款列表【A017】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "TAXBIZML")
public class YrbFcjyyjkTaxbizml {

    private YrbFcjyyjkRequestDTO fcjyyjskList;

    @XmlElement(name = "FCJYYJSKLIST")
    public YrbFcjyyjkRequestDTO getFcjyyjskList() {
        return fcjyyjskList;
    }

    public void setFcjyyjskList(YrbFcjyyjkRequestDTO fcjyyjskList) {
        this.fcjyyjskList = fcjyyjskList;
    }

    @Override
    public String toString() {
        return "YrbFcjyyjkTaxbizml{" +
                "fcjyyjskList=" + fcjyyjskList +
                '}';
    }
}
